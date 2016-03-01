package es.ait.mongoblog.config;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

/**
 * Class that configures mongodb access. On a real application needs a way to pass connection properties that
 * it's agnostic to the application. Probably like system properties or using jndi.
 * 
 *
 */
@Configuration
@EnableMongoRepositories("es.ait.mongoblog.model")	
public class MongoConfig 
{
//	public @Bean MongoClientFactoryBean mongo()
//	{
//		MongoClientFactoryBean factory = new MongoClientFactoryBean();
//		factory.setHost("localhost");
//		factory.setPort( 27017 );
//		return factory;
//	}
	
	  public @Bean MongoDbFactory mongoDbFactory() throws Exception 
	  {
		  Context initCtx = new InitialContext();
		  Context envCtx = (Context) initCtx.lookup("java:comp/env");
			  
		  return new SimpleMongoDbFactory( new MongoClient( (String)envCtx.lookup("bbdd.host"), (Integer)envCtx.lookup("bbdd.port")), "mongoblog");
	  }

	  public @Bean MongoTemplate mongoTemplate() throws Exception 
	  {
		  MongoTemplate template =  new MongoTemplate(mongoDbFactory());
		  
		  template.setWriteResultChecking( WriteResultChecking.EXCEPTION ); // Control write errors.
		  
		  return template;
	  }	
}

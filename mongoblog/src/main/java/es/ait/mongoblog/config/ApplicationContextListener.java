package es.ait.mongoblog.config;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Application Lifecycle Listener implementation class ApplicationContextListener
 *
 */
@WebListener
public class ApplicationContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ApplicationContextListener() {
    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  
    { 
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  
    {
		try
		{
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
	    	MongoClient mongoClient = new MongoClient((String)envCtx.lookup("bbdd.host"), (Integer)envCtx.lookup("bbdd.port"));
	    	MongoDatabase db = mongoClient.getDatabase("mongoblog");
	    	if ( !db.getCollection("user").find( new Document("nick", "admin")).iterator().hasNext() )
	    	{
	    		Date today = new Date();

	    		db.getCollection("user").insertOne(new Document("nick", "admin")
	    				.append("password", PasswordEncoder.encode("admin", "admin@mongoblog.org" + today.getTime()))
	    				.append("email", "admin@mongoblog.org")
	    				.append("inviteEmail", "admin@mongoblog.org")
	    				.append("inviteDate", today ));
	    	}
		}
		catch ( Exception e )
		{
			System.out.println("Error saving default user admin");
			e.printStackTrace( );
		}

    }
	
}

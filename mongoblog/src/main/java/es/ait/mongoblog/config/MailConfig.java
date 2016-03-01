package es.ait.mongoblog.config;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig 
{
	
	
	public @Bean JavaMailSenderImpl mailsender() throws Exception
	{
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		  
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost((String) envCtx.lookup("mail.host"));
		sender.setPort((Integer) envCtx.lookup("mail.port"));
		sender.setUsername((String) envCtx.lookup("mail.username"));
		sender.setPassword((String) envCtx.lookup("mail.password"));
		
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", (String) envCtx.lookup("mail.transport.protocol"));
		properties.setProperty("mail.smtp.auth", (String) envCtx.lookup("mail.smtp.auth"));
		properties.setProperty("mail.smtp.starttls.enable", (String) envCtx.lookup("mail.smtp.starttls.enable") );
		properties.setProperty("mail.debug", (String) envCtx.lookup("mail.debug"));
		
		sender.setJavaMailProperties( properties );
		return sender;
	}
}

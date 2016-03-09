package es.ait.mongoblog.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.ait.mongoblog.config.PasswordEncoder;
import es.ait.mongoblog.model.User;
import es.ait.mongoblog.model.UserRepository;

@Controller
public class ConfigController 
{
	@Autowired
	private UserRepository users;
	
	@Autowired
	private JavaMailSenderImpl sender;
	
	/**
	 * Config screen entrance method. Redirects to the logout if detects that we are trying to configure a
	 * user that it's not the logged user.
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(path="{user}/config", method=RequestMethod.GET)
	public String config(@PathVariable String user, Model model, HttpSession session )
	{
		User loggedUser = (User)session.getAttribute("loggeduser");
		if ( loggedUser != null && user.equals( loggedUser.getNick()))
		{
			loggedUser = users.findOneByNick( user );
			model.addAttribute("user", loggedUser );
			return "/user/config.jsp";
		}
		return "redirect:/logout";
	}
	
	/**
	 * Saves the changes made to the user. It redirects to the logout if it detects an attemp to modify
	 * a user diferrent than the logged user.
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(path="{user}/config/saveconfig", method=RequestMethod.POST)
	public String configPost( @PathVariable String theUser, HttpServletRequest request, HttpSession session, Model model )
	{
		User user = ( User ) session.getAttribute("loggeduser");
		if ( user == null || !user.getId().equals( request.getParameter("id")))
		{
			return "redirect:/logout";
		}
		
		user = users.findOne( request.getParameter("id"));
		User altUser = users.findOneByNick( request.getParameter("nick"));
		if ( altUser != null && !altUser.getId().equals( user.getId()))
		{
			model.addAttribute("error", "The nick " + request.getParameter("nick") + " it's already in use by another user.");
		}
		else
		{
			user.setNick( request.getParameter("nick"));
			user.setEmail( request.getParameter("email"));
			users.save( user );
			session.setAttribute("loggeduser", user );
		}
		
		return "redirect:/" + theUser + "/config/" + user.getId();
	}
	
	/**
	 * Launches the password change screen.
	 * 
	 * @param userId
	 * @param session
	 * @return
	 */
	@RequestMapping(path="/{user}/config/changepassword", method=RequestMethod.GET)
	public String changePassword( @PathVariable String user, @RequestParam(required=false) String error, HttpSession session, Model model )
	{
		User loggerdUser = ( User )session.getAttribute("loggeduser");
		if ( loggerdUser != null && loggerdUser.getNick().equals( user ))
		{
			model.addAttribute("user", loggerdUser );
			if ( "1".equals( error ))
			{
				model.addAttribute("error", "Wrong old password");
			}
			return "/user/changepassword.jsp";
		}
		return "redirect:/logout"; 
	}
	
	/**
	 * Saves a password change.
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(path="/{user}/config/savepasswordchange", method=RequestMethod.POST) 
	public String savePasswordChange(@PathVariable final String user, HttpServletRequest request, HttpSession session ) throws Exception
	{
		User loggedUser = (User) session.getAttribute("loggeduser" );
		if ( loggedUser == null || !loggedUser.getId().equals( request.getParameter("id")) || !loggedUser.getNick().equals( user ))
		{
			return "redirect:/logout"; 	
		}
		loggedUser = users.findOne( request.getParameter("id"));
		String encriptedPassword = PasswordEncoder.encode( request.getParameter("oldpassword"), loggedUser.getInviteEmail() + loggedUser.getInviteDate().getTime());
		if ( !encriptedPassword.equals( loggedUser.getPassword() ))
		{
			return "redirect:/user/changepassword/" + loggedUser.getId() + "?error=1";
		}
		loggedUser.setPassword( PasswordEncoder.encode( request.getParameter("newpassword"), loggedUser.getInviteEmail() + loggedUser.getInviteDate().getTime()));
		users.save( loggedUser );
		session.setAttribute("loggeduser",  loggedUser );
		
		return "redirect:/" + user + "/config"; 
	}
	
	
	@RequestMapping(path="/{user}/config/invite", method=RequestMethod.GET)
	public String invite( @PathVariable String user, @RequestParam(required=false) String error, HttpSession session, Model model )
	{
		User loggedUser = ( User )session.getAttribute("loggeduser");
		if ( loggedUser != null && loggedUser.getNick().equals( user ))
		{
			model.addAttribute("user", loggedUser );
			if ( "1".equals( error ))
			{
				model.addAttribute("error", "User already invited");
			}
			return "/user/invite.jsp";
		}
		return "redirect:/logout"; 
	}
	
	
	@RequestMapping(path="/{user}/config/sendinvite", method=RequestMethod.POST) 
	public String invite( HttpServletRequest request, HttpSession session) throws Exception
	{
		User sessionUser = (User) session.getAttribute("loggeduser" );
		if ( sessionUser == null || !sessionUser.getId().equals( request.getParameter("id")))
		{
			return "redirect:/logout"; 	
		}
		User duplicated = users.findOneByEmailOrInviteEmail( request.getParameter("email"), request.getParameter("email") );
		if ( duplicated != null )
		{
			return "redirect:/user/invite/" + sessionUser.getId() + "?error=1"; 
		}
			
		User user = new User();
		user.setInviteDate( new Date() );
		user.setInviteEmail( request.getParameter("email") );
		user.setEmail( request.getParameter("email") );

		String randomPassword = PasswordEncoder.randomPassword( 8 );
		user.setPassword( PasswordEncoder.encode( randomPassword, user.getInviteEmail() + user.getInviteDate().getTime()));
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo( user.getEmail());
		message.setFrom( "aitkiar@gmail.com");
		message.setText("You have been invited to use the mongo blog. Your can login using your email address: " + user.getEmail() + " and the password " + randomPassword );
		message.setSubject("Mongoblog invitation");
		sender.send( message );
		
		users.save( user );
		
		
		return "redirect:/" + sessionUser.getNick() + "/config"; 
	}
}

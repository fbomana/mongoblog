package es.ait.mongoblog.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.ait.mongoblog.config.PasswordEncoder;
import es.ait.mongoblog.model.User;
import es.ait.mongoblog.model.UserRepository;

@Controller
public class LoginController 
{

	@Autowired
	private UserRepository users;
	
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpSession session ) 
    {
    	session.removeAttribute( "loggeduser" );
    	return "redirect:/login";
    }
    
	@RequestMapping( path="/login", method=RequestMethod.GET )
	public String loginGet()
	{
		return "/login.jsp";
	}
	
	@RequestMapping( path="/login", method=RequestMethod.POST )
	public String loginPost( HttpServletRequest request, HttpSession session ) throws Exception
	{
		if ( session.getAttribute( "loggeduser" )  != null )
		{
			return "redirect:/logout";
		}
		
		User user = users.findOneByNickOrEmail( request.getParameter("user"), request.getParameter("user") );
		String password = PasswordEncoder.encode( request.getParameter("password"), user.getInviteEmail() + user.getInviteDate().getTime());
		if ( password.equals( user.getPassword()))
		{
			session.setAttribute("loggeduser", user );
			if ( user.getNick() != null )
			{
				return "redirect:/user/" + user.getNick();
			}
			else
			{
				return "redirect:/user/config/" + user.getId();
			}
		}
		return "login.jsp?error";
	}

	@RequestMapping( path="/")
	public String EmptyURL( HttpServletRequest request, HttpSession session )
	{
		User user = (User)session.getAttribute("loggeduser");
		if ( user != null )
		{
			if ( user.getNick() != null )
			{
				return "redirect:/user/" +((User)session.getAttribute( "loggeduser" )).getNick();
			}
		}
		return  "redirect:/notFound.jsp";
	}
}

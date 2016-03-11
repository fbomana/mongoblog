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
				return "redirect:/" + user.getNick() + "/home";
			}
			else
			{
				return "redirect:/" + user.getEmail() +"/config";
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
				return "redirect:/" + user.getNick() + "/home";
			}
		}
		return  "redirect:/notFound.jsp";
	}
	
	@RequestMapping( path="/redirect")
	public String redirectTo( HttpServletRequest request, HttpSession session )
	{
		User user = ( User ) session.getAttribute("loggeduser");
		User blogUser = users.findOneByNick( request.getParameter("nick"));
		if ( blogUser != null )
		{
			session.setAttribute("returnblog", blogUser.getNick());
		}
		
		session.setAttribute("from", request.getParameter("from"));
		if ( "home".equals( request.getParameter("from")))
		{
			session.setAttribute("page", request.getParameter("page"));
			session.setAttribute("nick", request.getParameter("nick"));
		}
		switch( request.getParameter("to") )
		{
			case "edit":
			{
				return "redirect:/" + (blogUser!= null ? blogUser.getNick() : user.getNick()) + "/config/editentry/" + request.getParameter("id");
			}
			case "newentry":
			{
				return "redirect:/" + (blogUser!= null ? blogUser.getNick() : user.getNick()) + "/config/newentry";
			}
			case "viewentry":
			{
				return "redirect:/" + user.getNick() + "/viewentry/" + request.getParameter("id");
			}
		}
		return "redirect:/logout";
	}
	

	@RequestMapping( path="/goback")
	public String goBack( HttpSession session )
	{
		String from = (String)session.getAttribute("from");
		session.removeAttribute("from");
		String returnBlog = (String) session.getAttribute("returnblog");
		session.removeAttribute("returnblog");
		switch ( from )
		{
			case "home":
			{
				String nick = (String)session.getAttribute("nick");
				if ( nick != null )
				{
					session.removeAttribute("nick");
					return "redirect:/" + nick + "/home";
				}
				break;
			}
			case "unpublished":
			{
				User user = ( User )session.getAttribute("loggeduser");
				if ( user != null )
				{
					return "redirect:/" + ( returnBlog != null ? returnBlog : user.getNick() ) + "/config/unpublished";
				}
				break;
			}
			case "config":
			{
				User user = ( User )session.getAttribute("loggeduser");
				if ( user != null )
				{
					return "redirect:/" + ( returnBlog != null ? returnBlog : user.getNick() ) + "/config";
				}
				break;
			}
			
		}
		
		return "redirect:/logout";
	}
}

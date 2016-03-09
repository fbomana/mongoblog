package es.ait.mongoblog.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.ait.mongoblog.model.Entry;
import es.ait.mongoblog.model.EntryRepository;
import es.ait.mongoblog.model.User;

@Controller
public class BlogController 
{
	@Autowired
	private MongoOperations mongo;
	
	@Autowired
	private EntryRepository entries;
	
	@RequestMapping("/{user}/home")
	public String portadaBlog( @PathVariable String user, Model model, HttpSession session )
	{
		User theUser = mongo.findOne(Query.query( Criteria.where("nick").is( user )), User.class );
		if ( theUser  != null )
		{
			if ( session.getAttribute("page") != null )
			{
				model.addAttribute("page", session.getAttribute("page"));
				session.removeAttribute("page");
			}
			model.addAttribute("user", user );
			return "/user/portada.jsp";	
		}
		return "redirect:/notFound.jsp";
	}
	
	/**
	 * Show a list of all entries with a publish date greater tharn the actual date.
	 * 
	 * @param userId
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/{user}/config/unpublished")
	public String unpublishedEntries( @PathVariable String user, HttpSession session, Model model )
	{
		User loggedUser = ( User )session.getAttribute("loggeduser");
		if ( loggedUser == null )
		{
			return "redirect:/logout";
		}
		
		model.addAttribute("entries", entries.findByAuthorAndPublishDateAfterOrderByPublishDateAsc( user, new Date()));
		
		return "/user/unpublished.jsp";
	}
	
	/**
	 * Launches the edit entry screen for an existing entry
	 * @param entryId
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/{user}/config/editentry/{entryId}")
	public String editEntry( @PathVariable String user, @PathVariable String entryId, HttpSession session, Model model )
	{
		User loggedUser = ( User )session.getAttribute("loggeduser");
		if ( loggedUser == null )
		{
			return "redirect:/logout";
		}
		
		Entry entry = entries.findOne( entryId );
		model.addAttribute( "entry", entry );
		model.addAttribute("urlPrefix", "../");
		model.addAttribute("publishDate", new SimpleDateFormat("yyyy-MM-dd").format( entry.getPublishDate()));
		model.addAttribute("publishTime", new SimpleDateFormat("HH:mm").format( entry.getPublishDate()));
		
		return "/user/editentry.jsp";
	}
	
	/**
	 * Launches the edit entry screen for a new entry 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/{user}/config/newentry")
	public String newentry( HttpSession session, Model model )
	{
		User loggedUser = ( User )session.getAttribute("loggeduser");
		if ( loggedUser == null )
		{
			return "redirect:/logout";
		}
		
		return "/user/editentry.jsp";
	}
	
	/**
	 * Saves the results of the edit entry screen on BBDD.
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(path="/{user}/config/saveentry", method=RequestMethod.POST)
	public String saveEntry( @PathVariable String user, HttpServletRequest request, HttpSession session ) throws Exception
	{
		User loggedUser = ( User )session.getAttribute("loggeduser");
		Entry entry;
		if ( request.getParameter("id") != null && !"".equals( request.getParameter("id")))
		{
			entry = entries.findOne( request.getParameter("id"));
		}
		else
		{
			entry = new Entry();
		}
		entry.setTitle( request.getParameter("title"));
		entry.setEntry( request.getParameter("entry"));
		entry.setResume( request.getParameter("resume"));
		if ( entry.getId() == null )
		{
			entry.setAuthor( loggedUser.getNick());
			entry.setBlog( user );
		}
		entry.setPublishDate( new SimpleDateFormat( "yyyy-MM-dd HH:mm").parse( request.getParameter("publishDate") + " " + request.getParameter("publishTime") ));
		entries.save( entry );
		return "redirect:/goback";
	}
	
	@RequestMapping("/{user}/viewentry/{entryId}")
	public String viewEntry( @PathVariable String user, @PathVariable("entryId") String entryId, Model model )
	{		
		Entry entry = entries.findOne( entryId );
		if ( entry == null || !entry.getBlog().equals( user ) )
		{
			return "redirect:/logout";
		}
		model.addAttribute( "entry", entry );
		return "/user/viewentry.jsp";
	}
	
}

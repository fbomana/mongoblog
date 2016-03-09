package es.ait.mongoblog.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.ait.mongoblog.model.Entry;
import es.ait.mongoblog.model.EntryRepository;

@RestController
@RequestMapping("/services")
public class RestEntryController 
{

	@Autowired
	private EntryRepository entries;
	
	/**
	 * Returns the text of an entry from it's id. If the id or the entry are not found returns an
	 * empty text.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(path="/entry", produces="application/json;charset=UTF-8", method=RequestMethod.GET)
	public Entry getEntryText( HttpServletRequest request ) throws Exception
	{
		if ( request.getParameter("id") != null && !request.getParameter("id").trim().equals(""))
		{
			Entry entry = entries.findOne( request.getParameter( "id" ));
			if ( entry != null )
			{
				return entry;
			}
		}
		return new Entry();
	}
	
	@RequestMapping( path="/entries", produces = "application/json;charset=UTF-8", method=RequestMethod.GET)
	public Page<Entry> getEntries( HttpServletRequest request )
	{
		return entries.findByBlogAndPublishDateBeforeOrderByPublishDateDesc( request.getParameter("blog"), new Date(), new PageRequest( Integer.parseInt( request.getParameter("page")), Integer.parseInt( request.getParameter("size"))));
		
	}
}

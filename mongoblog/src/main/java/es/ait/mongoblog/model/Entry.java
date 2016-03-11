package es.ait.mongoblog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Entry 
{
	private String id;
	private String title;
	private String entry;
	private String resume;
	private String blog;
	private String author;
	private Date publishDate;
	private List<Comment> comments;	
	
	public String getId() 
	{
		return id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getTitle() 
	{
		return title;
	}
	
	public void setTitle(String title) 
	{
		this.title = title;
	}
	
	public String getEntry() 
	{
		return entry;
	}
	
	public void setEntry(String entry) 
	{
		this.entry = entry;
	}
	
	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getBlog() 
	{
		return blog;
	}
	
	public void setBlog(String blog) 
	{
		this.blog = blog;
	}
	
	public String getAuthor() 
	{
		return author;
	}
	
	public void setAuthor(String author) 
	{
		this.author = author;
	}

	public Date getPublishDate() 
	{
		return publishDate;
	}

	public void setPublishDate(Date publishDate) 
	{
		this.publishDate = publishDate;
	}
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public void addComment( String comment, String author, Date date )
	{
		if ( this.comments == null )
		{
			this.comments = new ArrayList<Comment>();
		}
		comments.add( new Comment( comment, author, date ) );
	}

	public class Comment 
	{
		public String author;
		public String comment;
		public Date date;
		
		public Comment( String comment, String author, Date date )
		{
			this.comment = comment;
			this.author = author;
			this.date = date;
		}
		
		public String getAuthor() 
		{
			return author;
		}
		
		public void setAuthor(String author) 
		{
			this.author = author;
		}
		
		public String getComment() 
		{
			return comment;
		}
		
		public void setComment(String comment) 
		{
			this.comment = comment;
		}
		
		public Date getDate() 
		{
			return date;
		}
		
		public void setDate(Date date) 
		{
			this.date = date;
		}
	}
}


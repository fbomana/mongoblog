package es.ait.mongoblog.model;

import java.util.Date;

public class Entry 
{
	private String id;
	private String title;
	private String entry;
	private String resume;
	private String blog;
	private String author;
	private Date publishDate;
	
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
}

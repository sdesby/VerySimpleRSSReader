package fr.sdesby.rssParser;

public class Item 
{
	/**
	 * This class is an implementation of an RSS 2.0 item structure
	 **/
	
	private String title;
	private String description;
	private String link;
	private String category;
	private String pubDate;
	
	Item()
	{
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	
	

}

package fr.sdesby.rssParser;

import java.util.List;
import java.util.Vector;

public class Feed 
{
	private String title;
	private String link;
	private String description;
	private String pubDate;
	
	private int itemCount;
	private List<Item> listItem;
	
	Feed()
	{
		itemCount = 0;
		listItem = new Vector<Item>(0);
	}

	public int addItem(Item item)
	{
		listItem.add(item);
		return itemCount++;
	}
	
	public Item getItem(int location)
	{
		return listItem.get(location);
	}
	
	public List<Item> getAllItems()
	{
		return listItem;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	public List<Item> getListItem() {
		return listItem;
	}

	public void setListItem(List<Item> listItem) {
		this.listItem = listItem;
	}
	
	
}

package fr.sdesby.rssParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Handler extends DefaultHandler
{
	private Feed feed;
	private Item item;

	final int TITLE = 1;
	final int LINK = 2;
	final int DESCRIPTION = 3;
	final int PUBDATE = 4;
	final int CATEGORY = 5;

	private int currentState;

	public Handler()
	{
		currentState = 0;
	}

	public void startDocument() throws SAXException
	{
		feed = new Feed();
		item = new Item();
	}

	public void endDocument()
	{

	}

	/**
	 * On enregistre l'Žlement en cours pour le traiter dans characters()
	 */
	public void startElement(String uri, String localName, String elementName, Attributes attributes)
	{

		if(localName.equalsIgnoreCase("item"))
		{
			item = new Item();
			return;
		}

		if(localName.equalsIgnoreCase("title"))
		{
			currentState = TITLE;
			return;
		}

		if(localName.equalsIgnoreCase("link"))
		{
			currentState = LINK;
			return;
		}

		if(localName.equalsIgnoreCase("description"))
		{
			currentState = DESCRIPTION;
			return;
		}

		if(localName.equalsIgnoreCase("image"))
		{
			feed.setTitle(item.getTitle());
			return;
		}

		if(localName.equalsIgnoreCase("pubdate"))
		{
			currentState = PUBDATE;
			return;
		}

		if(localName.equalsIgnoreCase("category"))
		{
			currentState = CATEGORY;
			return;
		}
	}

	public void endElement(String namespaceURI, String localName, String qName) throws SAXException
	{
		if (localName.equalsIgnoreCase("item"))
		{
			// add our item to the list!
			feed.addItem(item);
			return;
		}
	}

	public void characters(char ch[], int start, int length)
	{
		String m_string = new String(ch, start, length);

		switch(currentState)
		{
			case TITLE: item.setTitle(m_string);
			currentState = 0;
			break;
	
			case LINK: item.setLink(m_string);
			currentState = 0;
			break;
	
			case DESCRIPTION: item.setDescription(m_string);
			currentState = 0;
			break;
			
			case PUBDATE: item.setPubDate(m_string);
			currentState = 0;
			break;
			
			case CATEGORY: item.setCategory(m_string);
			currentState = 0;
			break;
			
			default: currentState = 0;
			break;

		}
	}

	public Feed getFeed() {
		return feed;
	}
}

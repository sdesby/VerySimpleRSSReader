package fr.sdesby.rssParser;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.os.StrictMode;
import fr.sdesby.rssParser.Handler;

public class FeedManager 
{
	public FeedManager()
	{
		
	}
	
	public Feed getFeed(String feedURL)
	{
		//Permet de se connecter ˆ Internet
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);

				try
				{
					URL url = new URL(feedURL);
					// create the factory
					SAXParserFactory factory = SAXParserFactory.newInstance();
					// create a parser
					SAXParser parser = factory.newSAXParser();
					// create the reader (scanner)
					XMLReader xmlreader = parser.getXMLReader();

					Handler handler = new Handler();
					// assign our handler
					xmlreader.setContentHandler(handler);
					// get our data via the url class
					InputSource is = new InputSource(url.openStream());
					// perform the synchronous parse           
					xmlreader.parse(is);
					// get the results - should be a fully populated RSSFeed instance, or null on error
					return handler.getFeed();
				}
				catch(Exception e)
				{
					return null;
				}
	}
}

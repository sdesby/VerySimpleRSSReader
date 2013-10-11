package fr.sdesby.verysimplerssreader;

import fr.sdesby.verysimplerssreader.R;

import fr.sdesby.rssParser.Feed;
import fr.sdesby.rssParser.FeedManager;
import fr.sdesby.rssParser.ItemArrayAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ShowFeedItemsActivity extends Activity 
{
	private ListView listViewItemsList;
	private TextView feedTitleTextView;
	
	private String rssFeedToShow;
	
	private Feed feed;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_feed_items);
		
		Intent intent = getIntent();
		if(intent != null)
		{
			//On récupère le bundle envoyé par ChooseFeedActivity et contenant les données à afficher
			Bundle b = intent.getBundleExtra("android.intent.extra.INTENT");
			
			if(b != null)
			{
				rssFeedToShow = b.getString("rssFeedToLoad");
				
				FeedManager feedManager = new FeedManager();
				feed = feedManager.getFeed(rssFeedToShow);
				
				listViewItemsList = (ListView)findViewById(R.id.itemsListView);
				
				display();	
			}
			
			else
			{
				//On revient à la page de choix de flux et on transfert un message d'erreur
				Intent errIntent = new Intent(ShowFeedItemsActivity.this, ChooseFeedActivity.class);
				Bundle errBundle = new Bundle();
				errBundle.putString("ERROR", "Feed loading has failed");
				errIntent.putExtra("android.intent.extra.INTENT", b);
		        startActivity(errIntent);
			}
		}
		
	}
	
	//Affiche la liste des items du flux en cours
	public void display()
	{
		if(feed == null)
		{
			feedTitleTextView.setText(R.string.no_rss_feed);
		}
		
		else
		{
			if(feed.getTitle() != null)
				feedTitleTextView.setText(feed.getTitle());
			
			ItemArrayAdapter adapter = new ItemArrayAdapter(this.getBaseContext(), feed.getAllItems());
			
			listViewItemsList.setAdapter(adapter);
			listViewItemsList.setOnItemClickListener(new OnItemClickListener() 
			{

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
				{
					 Intent itemintent = new Intent(ShowFeedItemsActivity.this, ItemDescriptionActivity.class);
			         
			    	 Bundle b = new Bundle();
			    	 b.putString("title", feed.getItem(position).getTitle());
			    	 b.putString("description", feed.getItem(position).getDescription());
			    	 b.putString("link", feed.getItem(position).getLink());
			    	 
			    	 itemintent.putExtra("android.intent.extra.INTENT", b);
			         
			         startActivity(itemintent);
				}
			});
			listViewItemsList.setSelection(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_feed_items, menu);
		return true;
	}

}

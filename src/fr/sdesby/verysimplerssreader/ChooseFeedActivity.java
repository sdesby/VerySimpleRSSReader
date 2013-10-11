package fr.sdesby.verysimplerssreader;

import java.util.ArrayList;
import java.util.List;

import fr.sdesby.fileIO.FileManager;
import fr.sdesby.verysimplerssreader.R;
import fr.sdesby.verysimplerssreader.ShowFeedItemsActivity;
import fr.sdesby.rssParser.Feed;
import fr.sdesby.rssParser.FeedManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ChooseFeedActivity extends Activity 
{
	private List<String> feedList;
	private FileManager fileManager;
	private ListView feedListView;

	private EditText editTxtNewFeed;
	private Button buttonNewFeed;
	private String filename;
	
	private int itemPosition;


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_feed);
		
		//On vérifie qu'aucun message d'erreur n'a été envoyé par une autre activité
		Intent intent = getIntent();
		Bundle b = intent.getBundleExtra("android.intent.extra.INTENT");
		if(b != null)
		{
			String errMessage = b.getString("ERROR");
			Toast.makeText(getBaseContext(), errMessage, Toast.LENGTH_LONG).show();
		}
		
		feedList = new ArrayList<String>();
		fileManager = new FileManager();
		filename = "myRssFeeds.txt";

		if(fileManager.readFile(getBaseContext(), filename, feedList))
		{
			if(!feedList.isEmpty())
			{
				feedListView = (ListView)findViewById(R.id.feedListView);
				ArrayAdapter<String> array = new ArrayAdapter<String>(getBaseContext(), 
						android.R.layout.simple_list_item_1, android.R.id.text1,feedList);
				feedListView.setAdapter(array);
				
				feedListView.setOnItemClickListener(new OnItemClickListener() 
				{
					//Lorsque l'utilisateur choisit un flux à lire, on l'envoit vers l'activité de lecture du flux (ShowFeedItemsActivity)
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
					{
						String url = feedList.get(position);

						Intent showFeedItemsActivityIntent = new Intent(ChooseFeedActivity.this, ShowFeedItemsActivity.class);
						Bundle b = new Bundle();
						b.putString("rssFeedToLoad", url);
						showFeedItemsActivityIntent.putExtra("android.intent.extra.INTENT", b);
						startActivity(showFeedItemsActivityIntent);
					}
				});
				
				feedListView.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,View view, int position, long id) 
					{
						itemPosition = position;
						
						new AlertDialog.Builder(ChooseFeedActivity.this)
						.setTitle("Delete a feed")
						.setMessage("Do you really want to delete "+ feedList.get(position)+" ?")
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
						{
						    public void onClick(DialogInterface dialog, int whichButton) 
						    {
						        fileManager.deleteLine(ChooseFeedActivity.this, filename, itemPosition);
						        refresh();
						    }
						})
						 .setNegativeButton(android.R.string.no, null).show();
						
						return true;
					}
					
				});
			}

			else
			{
				noRssFeed();
			}	
		}

		else
		{
			noRssFeed();
		}
	}

	/*********
	 * 
	 *  Fonctions utiles
	 * 
	 *********/
	
	private void refresh()
	{
		finish();
		Intent intent = getIntent();
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
	}

	private void noRssFeed()
	{
		TextView noFeed = (TextView)findViewById(R.id.noFeedTextView);
		noFeed.setVisibility(View.VISIBLE);
		noFeed.setText(R.string.no_rss_feed);
	}

	/*********
	 * 
	 *  Menu
	 *  
	 *********/

	public void activateNewFeedButton(Menu menu)
	{
		//On récupère la vue du menuItem
		View v = (View) menu.findItem(R.id.menu_addNewFeed).getActionView();

		editTxtNewFeed = ( EditText ) v.findViewById(R.id.addNewFeed_editText);
		buttonNewFeed = (Button) v.findViewById(R.id.addNewFeed_button);

		/*
		 *  Lorsque l'utilisateur clique sur le bouton :
		 */
		buttonNewFeed.setOnClickListener(new OnClickListener() 
		{
			//TODO : verifier que le flux n'existe pas déjà
			@Override
			public void onClick(View view) 
			{
				String rssFeedToAdd = editTxtNewFeed.getText().toString();

				// On vérifie que l'utilisateur a bien entré une url
				if(rssFeedToAdd.length() > 0)
				{
					//Si c'est le cas, on essaie de récupérer le flux
					FeedManager feedManager = new FeedManager();
					Feed feed = feedManager.getFeed(rssFeedToAdd);

					//On vérifie qu'ona bien récupéré un flux
					if(feed != null)
					{
						//On rajoute le flux au fichier
						rssFeedToAdd += "\n";
						try
						{
							fileManager.writeLine(getBaseContext(), "myRssFeeds.txt", rssFeedToAdd);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						
						refresh();
						
					}
					
					else
					{
						Toast.makeText(getBaseContext(), "This feed doesn't exist", Toast.LENGTH_SHORT).show();
					}
				}
				
				else
				{
					Toast.makeText(getBaseContext(), "Please enter a feed before validate", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_feed, menu);
		activateNewFeedButton(menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch (item.getItemId()) 
		{
		case R.id.menu_addNewFeed:
	        View v = (View) item.getActionView();
	        editTxtNewFeed = ( EditText ) v.findViewById(R.id.addNewFeed_editText);
	        if(editTxtNewFeed.length() > 0)
	        	editTxtNewFeed.setText(R.string.http);
			return true;
			
		default:
			return false;
		}
	}
}

package fr.sdesby.verysimplerssreader;

import fr.sdesby.verysimplerssreader.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

public class ItemDescriptionActivity extends Activity 
{
	TextView txtViewTitle;
	TextView txtViewDescription;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_description);
		
		Intent intent = getIntent();
		String title;
		String desc;
		
		if(intent != null)
		{
			//On r�cup�re le bundle envoy� par ShowFeedItemsActivity et contenant les donn�es � afficher
			Bundle b = intent.getBundleExtra("android.intent.extra.INTENT");
			if(b == null)
			{
				title = "Probl�me";
				desc = "L'envoie de donn�es � �chouer";
			}
			else
			{
				title = b.getString("title");
				desc =  Html.fromHtml(b.getString("description").replace('\n',' ')) + "..." + "\n\nMore information:\n" + b.getString("link");
			}
		}
		else
		{
			title = "Aucune donn�e trouv�e";
			desc ="";
		}
		
		txtViewTitle = (TextView)findViewById(R.id.titleTextView);
		txtViewTitle.setText(title);
		txtViewDescription = (TextView)findViewById(R.id.descriptionTextView);
		txtViewDescription.setText(desc);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_description, menu);
		return true;
	}

}

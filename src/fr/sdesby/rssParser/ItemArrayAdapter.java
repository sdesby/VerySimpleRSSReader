package fr.sdesby.rssParser;

import java.util.List;

import fr.sdesby.verysimplerssreader.R;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

final class ViewHolder {
	TextView title;
	TextView description;
	
}
public class ItemArrayAdapter extends ArrayAdapter<Item>
{
	private LayoutInflater inflater;
	private List<Item> listItems;

	public ItemArrayAdapter(Context context, List<Item> objects) 
	{
		super(context, R.layout.feed_item_display, objects);
		
		inflater = LayoutInflater.from(context);
		this.listItems = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder;
		
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.feed_item_display, null);
			holder = new ViewHolder();
			
			holder.title = (TextView)convertView.findViewById(R.id.title);
			holder.description = (TextView)convertView.findViewById(R.id.description);
			
			convertView.setTag(holder);
		}
		
		else
			holder = (ViewHolder)convertView.getTag();
		
		holder.title.setText(listItems.get(position).getTitle());
		String desc = listItems.get(position).getDescription();
		
		//On limite l'affichage de la description ˆ 140 caractres
		if(desc.length() > 140)
			desc = desc.substring(0,140);
		desc = desc +"...";
		holder.description.setText(Html.fromHtml(desc));
		
		return convertView;
	}
	

}

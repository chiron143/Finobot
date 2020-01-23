package com.purplepath.purplepath.adapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.finobot.finobot.R;
import java.util.ArrayList;


public class SettingListAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final ArrayList<String> settingsList;
	private int settingsItemsCount = 0;
	
	public SettingListAdapter(Activity context, ArrayList<String> settingsList) {
		super(context, 0);
		this.context = context;
		this.settingsList = settingsList;
		settingsItemsCount = settingsList.size();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return settingsItemsCount;
	}
	
	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return settingsList.get(position);
	}

	@Override
	public View getView(final int position, View rowView, ViewGroup parent) {
		ViewHolder holder = null;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.settinglistview, null);
			holder = new ViewHolder();
			holder.txtTitle = rowView
					.findViewById(R.id.settingListTextView);
			holder.imageView = rowView
					.findViewById(R.id.settinglistviewImage);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		
		holder.txtTitle.setText(settingsList.get(position).toString());
				/*rowView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				//	MainFragment.startReplyMessage(settingsList.get(position).getFrom_user_id(),settingsList.get(position).getMessage());
				}			
			});*/
			
		return rowView;
	}


	 static class ViewHolder {
		TextView txtTitle;
		ImageView imageView;

	}


}

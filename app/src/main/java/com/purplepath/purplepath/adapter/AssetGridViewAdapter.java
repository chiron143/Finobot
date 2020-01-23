package com.purplepath.purplepath.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.finobot.finobot.R;


/**
 * Created by Bert on 20-May-16.
 */
public class AssetGridViewAdapter extends ArrayAdapter {
    private Context context;
    private int ImageID[];

    public AssetGridViewAdapter(Context context, int image_id[]) {
        super(context, 0);
        this.context = context;
        ImageID = image_id;
    }

    public int getCount() {
        return ImageID.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.adapter_asset_row_item, parent, false);
            holder = new ViewHolder();
            holder.image = row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.image.setImageResource(ImageID[position]);
        return row;
    }

    static class ViewHolder {
        ImageView image;
    }
}
package com.purplepath.purplepath.document.documentGridview.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.quickMenu.models.GirdviewText;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bertrandrussellsakthees on 20/06/17.
 */

public class CustomDocumentAdapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<GirdviewText> gridlist;

    private HashMap<String,String> getcountdigital_version;
    public CustomDocumentAdapter(Context mcontext, ArrayList<GirdviewText> gridListinnerMenu, HashMap<String, String> digital_version) {
        mContext= mcontext;
        gridlist = gridListinnerMenu;
        getcountdigital_version  = digital_version;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return gridlist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single_adapter, null);
            TextView textView = grid.findViewById(R.id.grid_text);
            ImageView imageView = grid.findViewById(R.id.grid_image);
            textView.setText(gridlist.get(position).getMyDataText());
            imageView.setImageResource(gridlist.get(position).getMyDataImage());
            Log.i("CustomDocumentAdapter","getcountdigital_version "+getcountdigital_version.size());

            if(getcountdigital_version!= null) {
                if(getcountdigital_version.get(gridlist.get(position).getMyDataText()) != null) {
                    textView.setText(gridlist.get(position).getMyDataText() + " ( "+
                            getcountdigital_version.get(gridlist.get(position).getMyDataText()) +" ) ");
                }else{
                    textView.setText(gridlist.get(position).getMyDataText());
                }
                imageView.setImageResource(gridlist.get(position).getMyDataImage());
            }else{
                textView.setText(gridlist.get(position).getMyDataText());
                imageView.setImageResource(gridlist.get(position).getMyDataImage());
            }
        } else {
            grid = convertView;
        }

        return grid;
    }
}


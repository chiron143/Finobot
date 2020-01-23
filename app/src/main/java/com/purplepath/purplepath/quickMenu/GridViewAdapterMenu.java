package com.purplepath.purplepath.quickMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.quickMenu.models.GirdviewText;

import java.util.ArrayList;

/**
 * Created by bertrandrussellsakthees on 20/03/17.
 */

public class GridViewAdapterMenu extends BaseAdapter{

    private Context mcontext;
    private ArrayList<GirdviewText> griddataList;
    private int case_value;



    public GridViewAdapterMenu(Context mContext, ArrayList<GirdviewText> gridQuickmenuList, int case_period) {

        mcontext = mContext;
        case_value = case_period;
        this.griddataList = gridQuickmenuList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return griddataList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mcontext);
            grid = inflater.inflate(R.layout.grid_single, null);
            TextView textView = grid.findViewById(R.id.grid_text);
            ImageView imageView = grid.findViewById(R.id.grid_image);
            if(case_value ==1){
                textView.setText(griddataList.get(position).getMyPlanText());
                imageView.setImageResource(griddataList.get(position).getMyPlanImage());
            }
            else if(case_value == 0) {
                textView.setText(griddataList.get(position).getMyDataText());
                imageView.setImageResource(griddataList.get(position).getMyDataImage());
            }
            else if(case_value == 6) {
                textView.setText(griddataList.get(position).getMyPlanText());
                imageView.setImageResource(griddataList.get(position).getMyPlanImage());
            }
        } else {
            grid = convertView;
        }

        return grid;
    }
}

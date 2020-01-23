package com.purplepath.purplepath.goalanalysis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.goaltimeline.model.Goal_tmln;
import com.purplepath.purplepath.quickMenu.models.GirdviewText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.service_base_url;

/**
 * Created by Suresh on 18/07/17.
 */

public class MultipleGoalListadapter extends BaseAdapter {

    private Context mContext;

    private ArrayList<Goal_tmln> gridlist;


    public MultipleGoalListadapter(Context mcontext, ArrayList<Goal_tmln> gridListinnergoals) {
        mContext= mcontext;
        gridlist = gridListinnergoals;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return gridlist.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return gridlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.goal_list_items, null);
            ImageView mgoldimageView = grid.findViewById(R.id.imagelistitem);

            try {
                Picasso.with(mContext)
                        .load(service_base_url + gridlist.get(position).getImg_url())
                        .placeholder(R.drawable.icon_1)
                        .resize(100, 100)
                        .centerCrop()
                        .into(mgoldimageView);
            }catch (Exception e){e.printStackTrace();}
        } else {
            grid = convertView;
        }

        return grid;
    }
    }


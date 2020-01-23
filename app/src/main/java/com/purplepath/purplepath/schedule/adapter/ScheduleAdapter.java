package com.purplepath.purplepath.schedule.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;

/**
 * Created by Suresh on 20/06/17.
 */

public class ScheduleAdapter extends BaseAdapter {
    private  Context mContext;
    public ScheduleAdapter(Context mcontext) {
        mContext = mcontext;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.pendingadapterview, null);


        } else {
            grid = convertView;
        }

        return grid;
    }

}

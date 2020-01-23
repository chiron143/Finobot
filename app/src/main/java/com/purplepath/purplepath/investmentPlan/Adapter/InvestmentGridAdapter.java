package com.purplepath.purplepath.investmentPlan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.investmentPlan.models.IPS_Results;

import java.util.ArrayList;

public class InvestmentGridAdapter extends ArrayAdapter<IPS_Results> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<IPS_Results> data ;

    public InvestmentGridAdapter(Context context, int layoutResourceId,
                                 ArrayList<IPS_Results> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.text_goal_name);
             row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        String item = data.get(position).getGoal_name();
        holder.txtTitle.setText(item);
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;

    }
}
package com.purplepath.purplepath.goalanalysis.adapter;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.finobot.finobot.R;
import com.purplepath.purplepath.goalanalysis.model.Short_goal;

import java.util.List;

/**
 * Created by dinesh on 02/08/17.
 */

public class GoalsListViewAdapter extends ArrayAdapter<Short_goal> {
    public GoalsListViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Short_goal> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.goal_list_view, parent, false);
        AppCompatTextView goalNameTxt= convertView.findViewById(R.id.textViewId);
        goalNameTxt.setText(getItem(position).getGoal_name());
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Nullable
    @Override
    public Short_goal getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

}

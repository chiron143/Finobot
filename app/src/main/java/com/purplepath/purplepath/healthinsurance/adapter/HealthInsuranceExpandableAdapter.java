package com.purplepath.purplepath.healthinsurance.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.purplepath.purplepath.propertyinsurance.healthinsurancemodel.Available;
import java.util.ArrayList;

/**
 * Created by pravinr on 2/22/18.
 */

public class HealthInsuranceExpandableAdapter extends BaseExpandableListAdapter {


    private TextView mCover_name;

    private ImageView imageuparrow;

    Context mContext;

    ArrayList<ArrayList<Available>> mfilterarray;

    public HealthInsuranceExpandableAdapter(Context context, ArrayList<ArrayList<Available>> mfilterarray) {
        this.mContext = context;
        this.mfilterarray = mfilterarray;
    }

    @Override
    public int getGroupCount() {
        return mfilterarray.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(mfilterarray==null){
            return 0;
        }else {
            return mfilterarray.get(groupPosition).size();
        }
    }

    @Override
    public ArrayList<Available> getGroup(int groupPosition) {
        return mfilterarray.get(groupPosition);
    }

    @Override
    public Available getChild(int groupPosition, int childPosition) {
        return mfilterarray.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.individual_header_exp_listview, parent, false);
        }
        imageuparrow = convertView.findViewById(R.id.imageuparrow);

        mCover_name= convertView.findViewById(R.id.cover_name);

        if(mfilterarray.get(groupPosition).get(0).getFamily_name()!=null) {

            mCover_name.setText(mfilterarray.get(groupPosition).get(0).getFamily_name());
        }

        if (isExpanded) {
            imageuparrow.setImageResource(R.drawable.ic_mine_icon);
        } else {
            imageuparrow.setImageResource(R.drawable.ic_add_icon);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.individual_child_listview, parent, false);

        TextView family_name= view.findViewById(R.id.family_name);
        TextView cover_amount= view.findViewById(R.id.family_cover_amount);

        if(mfilterarray.get(groupPosition).get(childPosition).getFamily_name()!=null) {

            family_name.setText("" + mfilterarray.get(groupPosition).get(childPosition).getFamily_name());
        }

        if(mfilterarray.get(groupPosition).get(childPosition).getCoverage()!=null) {

            cover_amount.setText("" + mfilterarray.get(groupPosition).get(childPosition).getCoverage());
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}

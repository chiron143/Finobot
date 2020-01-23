package com.purplepath.purplepath.networthanalysis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.networthanalysis.model.NetworkAnalysisModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bertrandrussellsakthees on 06/07/17.
 */

public class NetworthExpandableadapter extends BaseExpandableListAdapter  {

    private Context mcontext;

    private NetworkAnalysisModel mnetworthAnalysismodel;

    private HashMap<Integer, ArrayList<String>> getArrayList;

    private HashMap<Integer, ArrayList<Integer>> getColorList;
    ImageView imageuparrow;


    public NetworthExpandableadapter(Context mContext, HashMap<Integer, ArrayList<String>> addArrayList, HashMap<Integer, ArrayList<Integer>> addColorList) {
        this.mcontext = mContext;
        this.getArrayList = addArrayList;
        this.getColorList = addColorList;
    }

    @Override
    public int getGroupCount() {
        return getArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
      if(groupPosition==0){
          return 0;
      }else {
          return getArrayList.get(groupPosition).size()-1;
      }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return getArrayList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getArrayList.get(groupPosition).get(childPosition);
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

            convertView = LayoutInflater.from(mcontext).inflate(R.layout.expandable_networth_parent_textview, parent, false);
            TextView textview_networth = convertView.findViewById(R.id.textview_networth);
            TextView mcolorlayout = convertView.findViewById(R.id.colorid_linearlayout);

            imageuparrow  = convertView.findViewById(R.id.imageuparrows);

            textview_networth.setText(getArrayList.get(groupPosition).get(0));
            mcolorlayout.setBackgroundColor(getColorList.get(groupPosition).get(0));


        if (isExpanded) {
            imageuparrow.setImageResource(R.drawable.ic_mine_icon);
        } else {
            imageuparrow.setImageResource(R.drawable.ic_add_icon);
        }

        return  convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.expandable_networth_child_textview, parent, false);
        TextView textview_networth = view.findViewById(R.id.textview_networth_childview);
        TextView mcolorlayout = view.findViewById(R.id.colorid_linearlayout);
        textview_networth.setText(getArrayList.get(groupPosition).get(childPosition +1));
        mcolorlayout.setBackgroundColor(getColorList.get(groupPosition).get(childPosition +1));
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

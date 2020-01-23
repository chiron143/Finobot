/*

package com.purplepath.purplepath.goaltimeline.Goaltimedetailicon;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.goaltimeline.model.GoalAgeTimeLineModel;
import com.purplepath.purplepath.goaltimeline.model.Goal_tmln;

import java.util.ArrayList;
import java.util.HashMap;

import static com.purplepath.purplepath.R.id.goals;
import static com.purplepath.purplepath.R.id.holiday;
import static com.purplepath.purplepath.R.id.pros;


public class GoaTimelineDetailadapter extends BaseExpandableListAdapter {
    private Context mContext;
    private HashMap<String, ArrayList<Goal_tmln>> mListDataChild;
    private ArrayList<GoalAgeTimeLineModel> mListDataGroup;
    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;

    private String name,age,total;
    private String year,holiday,goals,pros;


    public GoaTimelineDetailadapter(Context context,ArrayList<GoalAgeTimeLineModel> listDataGroup, HashMap<String,
            ArrayList<Goal_tmln>> listDataChild) {

        mContext = context;
        mListDataGroup = listDataGroup;
        mListDataChild = listDataChild;

    }
    @Override
    public int getGroupCount() {
        return mListDataGroup.size();
    }
    @Override
    public GoalAgeTimeLineModel getGroup(int groupPosition) {
        return mListDataGroup.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
        final int mGroupPos= groupPosition;
        //Log.e("mGroupPos",""+mGroupPos);
        name = String.valueOf(getGroup(groupPosition).getmAge());
        age = String.valueOf(getGroup(groupPosition).getmAge());
        total = String.valueOf(getGroup(groupPosition).getmAge());

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_group, null);

        groupViewHolder = new GroupViewHolder();

        groupViewHolder.mGroupText = (TextView) convertView.findViewById(R.id.name);
        groupViewHolder.mGroupText = (TextView) convertView.findViewById(R.id.age);
        groupViewHolder.mGroupText = (TextView) convertView.findViewById(R.id.total);

        convertView.setTag(groupViewHolder);
        groupViewHolder.mGroupText.setText(name);
        groupViewHolder.mGroupText.setText(age);
        groupViewHolder.mGroupText.setText(total);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListDataChild.get(mListDataGroup.get(groupPosition).getmGoalAgeTimeLine()).size();
    }
    @Override
    public Goal_tmln getChild(int groupPosition, int childPosition) {
        return mListDataChild.get(mListDataGroup.get(groupPosition).getmGoalAgeTimeLine()).get(childPosition);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;

        LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_child_item, null);

        year = getChild(mGroupPosition, mChildPosition).getGoal_start_datetime();
        holiday = getChild(mGroupPosition, mChildPosition).getGoal_start_datetime();
        goals = getChild(mGroupPosition, mChildPosition).getGoal_start_datetime();
        pros = getChild(mGroupPosition, mChildPosition).getGoal_start_datetime();

        childViewHolder = new ChildViewHolder();
        childViewHolder.year = (TextView) convertView.findViewById(R.id.year);
        childViewHolder.holiday = (TextView) convertView.findViewById(holiday);
        childViewHolder.goals = (TextView) convertView.findViewById(goals);
        childViewHolder.pros = (TextView) convertView.findViewById(pros);

        convertView.setTag(childViewHolder);

        childViewHolder.mChildText.setText(year);
        childViewHolder.mChildText.setText(holiday);
        childViewHolder.mChildText.setText(goals);
        childViewHolder.mChildText.setText(pros);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public final class GroupViewHolder {
        TextView mGroupText;
    }

    public final class ChildViewHolder {
        TextView mChildText;
        public TextView year,holiday,goals,pros;
    }
}
*/

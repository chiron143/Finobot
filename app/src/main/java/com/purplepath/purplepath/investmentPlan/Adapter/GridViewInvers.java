/*
package com.purplepath.purplepath.investmentPlan.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.goalanalysis.singlegoal.SingleGoalDonutview;
import com.purplepath.purplepath.investmentPlan.models.IPS_Results;

import java.util.ArrayList;

public class GridViewInvers extends RecyclerView.Adapter<GridViewInvers.ViewHolder> {
    private ArrayList<IPS_Results >ips_results;
    public View view;
    private Activity context;

    public GridViewInvers(ArrayList<IPS_Results >ivp, Activity activity) {
        this.ips_results = ivp;
        this.context = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text_goal_name;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            text_goal_name = view.findViewById(R.id.text_goal_name);


        }
    }

    @Override
    public GridViewInvers.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_name_adapter, parent, false);
        GridViewInvers.ViewHolder viewHolder = new GridViewInvers.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GridViewInvers.ViewHolder holder, int position) {

         String key_name = ips_results.get(position).getGoal_name();
        holder.text_goal_name.setText(key_name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.addFragmenttoStack(SingleGoalDonutview.newInstance(totalgoalObj.get(position).getId(),
                            totalgoalObj.get(position).getGoal_name(), "GoalView",
                            totalgoalObj.get(position).getGoal_years(), totalgoalObj.get(position).getExpected_increment()));

            }
        });


    }

    @Override
    public int getItemCount() {

        return ips_results.size();

    }
}
*/

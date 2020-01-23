package com.purplepath.purplepath.goaltimeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.goaltimeline.model.GoalTimeLineModel;
import com.purplepath.purplepath.goaltimeline.model.Goal_tmln;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class GoalTimeLineFragmentMainpageAdapter extends RecyclerView.Adapter<GoalTimeLineFragmentMainpageAdapter.ViewHolder> {
    Context context;
    private ArrayList<Goal_tmln> mGoalTimeLineModel;
    GoalTimeLineModel timeLineModel;
    private String getDobDate, getMarriageDate;

    public GoalTimeLineFragmentMainpageAdapter(Context mContext, ArrayList<Goal_tmln> mGoalTimeLineModel, GoalTimeLineModel timeLineModel) {
        this.context = mContext;
        this.mGoalTimeLineModel=mGoalTimeLineModel;
       this.timeLineModel=timeLineModel;
    }



    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_goaltimeline_mainpage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.goal_name .setText (mGoalTimeLineModel.get(position).getGoal_name());
        holder.family_name.setText(mGoalTimeLineModel.get(position).getName());
        //holder.start_date.setText(mGoalTimeLineModel.get(position).getGoal_start_datetime());
       // holder.end_date.setText(mGoalTimeLineModel.get(position).getGoal_end_date());



        if(mGoalTimeLineModel.get(position).getGoal_start_datetime()!=null) {
            int onGoingYear = Calendar.getInstance().get(Calendar.YEAR);
            String dob = UtileKit.getTextFromObjects((mGoalTimeLineModel.get(position).getGoal_start_datetime()));
            if (UtileKit.validateObjectValues(dob)) {
                try {
                    holder.start_date.setText(parseDate(dob));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        if(mGoalTimeLineModel.get(position).getGoal_end_datetime()!=null) {
            int onGoingYear = Calendar.getInstance().get(Calendar.YEAR);
            String dob1 = UtileKit.getTextFromObjects((mGoalTimeLineModel.get(position).getGoal_end_datetime()));
            if (UtileKit.validateObjectValues(dob1)) {
                try {
                    holder.end_date.setText(parseDate(dob1));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public String parseDate(String time) {
        String inputPattern = "yyyy";
        String outputPattern = "yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public int getItemCount() {
        return mGoalTimeLineModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView goal_name,family_name,start_date,end_date;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            goal_name = view.findViewById(R.id.goal_name);
            family_name = view.findViewById(R.id.family_name);
            start_date = view.findViewById(R.id.start_date);
            end_date = view.findViewById(R.id.end_date);
        }
    }
}

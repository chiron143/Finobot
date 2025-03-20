package com.purplepath.purplepath.schedule.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.schedule.Interface.UpdateDetailsFromAdapter;
import com.purplepath.purplepath.schedule.models.ScheduleModel;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Pratheep.S on 06-07-2017.
 */

public class ScheduleRecyclerAdapter extends RecyclerView.Adapter<ScheduleRecyclerAdapter.ViewHolder> {

    public ScheduleModel scheduleModel;
    private  Context context;

    public UpdateDetailsFromAdapter updateDetailsFromAdapter;
    int insuranceItemSize,liabilityItemSize;
    private final int TYPE_CODE_INSURANCE=0,TYPE_CODE_LIABILITY=1;
    int typeCode;
     private String TAG="spcheck";

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView amount_txt,date_text,category_text,category_type,alreadyPaidTxt;
        public ViewHolder(View itemView) {
            super(itemView);
            amount_txt= itemView.findViewById(R.id.amount_text);
            category_text= itemView.findViewById(R.id.category_text);
            category_type= itemView.findViewById(R.id.category_type);
            date_text= itemView.findViewById(R.id.date_text);
            alreadyPaidTxt= itemView.findViewById(R.id.alreadyPaidTxt);

        }
    }

    public ScheduleRecyclerAdapter(UpdateDetailsFromAdapter updateDetailsFromAdapter,ScheduleModel scheduleModel, Context context,int typeCode) {
        this.scheduleModel=scheduleModel;
        this.context=context;
        this.typeCode=typeCode;

        insuranceItemSize=scheduleModel.getData().getPending().getIns().size();
        liabilityItemSize=scheduleModel.getData().getPending().getLiab().size();
        Log.i(TAG, "insuranceItemSize :"+insuranceItemSize+"\n liabilityItemSize :"+liabilityItemSize);
        this.updateDetailsFromAdapter=updateDetailsFromAdapter;
    }

    @Override
    public ScheduleRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_single_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ScheduleRecyclerAdapter.ViewHolder holder, final int position) {
        float value=0;
        if(typeCode==TYPE_CODE_LIABILITY) {
            if(!StringUtils.isEmpty(scheduleModel.getData().getPending().getLiab().get(position).getCurrent_emi().toString()))
            {
                value=Float.parseFloat(scheduleModel.getData().getPending().getLiab().get(position).getCurrent_emi().toString());
                holder.amount_txt.setText("₹"+UtileKit.formatedNumber(value));
            }
            holder.category_text.setText(scheduleModel.getData().getPending().getLiab().get(position).getLiab_name().toString());
            holder.category_type.setText(scheduleModel.getData().getPending().getLiab().get(position).getType().toString());
            holder.date_text.setText(scheduleModel.getData().getPending().getLiab().get(position).getNext_due_date().toString());
            final String id=scheduleModel.getData().getPending().getLiab().get(position).getId();
            holder.alreadyPaidTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick:position liab "+position);
                    updateDetailsFromAdapter.update_details(id,"liab",position);
                }
            });
        }else if(typeCode==TYPE_CODE_INSURANCE){
            if(!StringUtils.isEmpty(scheduleModel.getData().getPending().getIns().get(position).getAnnual_prem().toString()))
            {
                value=Float.parseFloat(scheduleModel.getData().getPending().getIns().get(position).getAnnual_prem().toString());
                holder.amount_txt.setText("₹"+UtileKit.formatedNumber(value));
            }
            holder.category_text.setText(scheduleModel.getData().getPending().getIns().get(position).getPolicy_name().toString());
            holder.category_type.setText(scheduleModel.getData().getPending().getIns().get(position).getIns_type().toString());
            holder.date_text.setText(scheduleModel.getData().getPending().getIns().get(position).getNext_prem_date().toString());
            final String id=scheduleModel.getData().getPending().getIns().get(position).getId();
            holder.alreadyPaidTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick:position insurance "+position);
                    updateDetailsFromAdapter.update_details(id,"ins",position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int count=0;
        if(typeCode==TYPE_CODE_INSURANCE){
            count=scheduleModel.getData().getPending().getIns().size();
           // Log.i("spcheck", "getItemCount:Ins "+count);
        }else {
            count=scheduleModel.getData().getPending().getLiab().size();
            //Log.i("spcheck", "getItemCount:Liab "+count);
        }
        return count;
        //(scheduleModel.getData().getPending().getIns().size()+scheduleModel.getData().getPending().getLiab().size());
    }
}

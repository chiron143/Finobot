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

/**
 * Created by Pratheep.S on 10-07-2017.
 */

public class UpcomingScheduleRecyclerViewAdapter extends RecyclerView.Adapter<UpcomingScheduleRecyclerViewAdapter.ViewHolder> {

    public ScheduleModel scheduleModel;
    static Context context;
    int typeCode;
    public UpdateDetailsFromAdapter updateDetailsFromAdapter;
    private static String TAG = "spcheck";
    int insuranceItemSize, liabilityItemSize;
    private final int TYPE_CODE_INSURANCE = 0, TYPE_CODE_LIABILITY = 1;

    public UpcomingScheduleRecyclerViewAdapter(UpdateDetailsFromAdapter updateDetailsFromAdapter, ScheduleModel scheduleModel, Context context, int typeCode) {
        this.scheduleModel = scheduleModel;
        UpcomingScheduleRecyclerViewAdapter.context = context;
        this.typeCode = typeCode;
        this.updateDetailsFromAdapter = updateDetailsFromAdapter;
        insuranceItemSize = scheduleModel.getData().getUpcom_sch().getIns().size();
        liabilityItemSize = scheduleModel.getData().getUpcom_sch().getLiab().size();

        Log.i(TAG, "upcoming:insuranceItemSize :" + insuranceItemSize + "\n uocoming:liabilityItemSize :" + liabilityItemSize);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView amount_txt, date_text, category_text, alreadyPaidTxt, category_type;

        public ViewHolder(View view) {
            super(view);
            amount_txt = itemView.findViewById(R.id.amount_text);
            category_text = itemView.findViewById(R.id.category_text);
            category_type = itemView.findViewById(R.id.category_type);
            date_text = itemView.findViewById(R.id.date_text);
            alreadyPaidTxt = itemView.findViewById(R.id.alreadyPaidTxt);
        }
    }

    @Override
    public UpcomingScheduleRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_schedule_single_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(UpcomingScheduleRecyclerViewAdapter.ViewHolder holder, final int position) {
        float value = 0;
        String typeText = null;
        if (typeCode == TYPE_CODE_INSURANCE) {
            value = Float.parseFloat(scheduleModel.getData().getUpcom_sch().getIns().get(position).getAnnual_prem().toString());
            holder.amount_txt.setText("₹" + UtileKit.formatedNumber(value));
            typeText = scheduleModel.getData().getUpcom_sch().getIns().get(position).getIns_type().toString();
            if (typeText.equalsIgnoreCase("Institutions")) {
                holder.category_type.setText("Bank");
            } else if (typeText.equalsIgnoreCase("Individuals")) {
                holder.category_type.setText("Friends");
            } else {
                holder.category_type.setText(typeText);
            }
            holder.category_text.setText(scheduleModel.getData().getUpcom_sch().getIns().get(position).getPolicy_name().toString());
            holder.date_text.setText(scheduleModel.getData().getUpcom_sch().getIns().get(position).getNext_prem_date().toString());
            final String id = scheduleModel.getData().getUpcom_sch().getIns().get(position).getId();
            holder.alreadyPaidTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick:position insurance " + position);
                    updateDetailsFromAdapter.update_details(id, "ins", position);
                }
            });
        } else if (typeCode == TYPE_CODE_LIABILITY) {
            value = Float.parseFloat(scheduleModel.getData().getUpcom_sch().getLiab().get(position).getCurrent_emi().toString());
            holder.amount_txt.setText("₹" + UtileKit.formatedNumber(value));
            holder.category_text.setText(scheduleModel.getData().getUpcom_sch().getLiab().get(position).getLiab_name().toString());
            typeText = scheduleModel.getData().getUpcom_sch().getLiab().get(position).getType().toString();
            if (typeText.equalsIgnoreCase("Institutions")) {
                holder.category_type.setText("Bank");
            } else if (typeText.equalsIgnoreCase("Individuals")) {
                holder.category_type.setText("Friends");
            } else {
                holder.category_type.setText(typeText);
            }
            holder.date_text.setText(scheduleModel.getData().getUpcom_sch().getLiab().get(position).getNext_due_date().toString());
            final String id = scheduleModel.getData().getUpcom_sch().getLiab().get(position).getId();
            holder.alreadyPaidTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick:position liab " + position);
                    updateDetailsFromAdapter.update_details(id, "liab", position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (typeCode == TYPE_CODE_INSURANCE) {
            count = scheduleModel.getData().getUpcom_sch().getIns().size();

        } else if (typeCode == TYPE_CODE_LIABILITY) {
            count = scheduleModel.getData().getUpcom_sch().getLiab().size();

        }
        return count;
    }


}

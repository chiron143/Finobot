package com.purplepath.purplepath.investmentPlan.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finobot.finobot.R;

/**
 * Created by Pratheep.S on 31-07-2017.
 */

public class InvestmentPlanAdapter extends RecyclerView.Adapter<InvestmentPlanAdapter.ViewHolder> {

    String [][] details;
    public InvestmentPlanAdapter( String [][] details) {
        this.details=details;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView planValue,actualValue,card_title,varianceRupee_value,variancePercent_value;

        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            planValue= view.findViewById(R.id.plan_value);
            actualValue= view.findViewById(R.id.actual_value);
            card_title= view.findViewById(R.id.card_title);
            varianceRupee_value= view.findViewById(R.id.varianceRupee_value);
            variancePercent_value= view.findViewById(R.id.variancePercent_value);
        }
    }

    @Override
    public InvestmentPlanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.investment_plan_single_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InvestmentPlanAdapter.ViewHolder holder, int position) {
        holder.planValue.setText(details[position][0]);
        holder.actualValue.setText(details[position][1]);
        holder.card_title.setText(details[position][4]);

        holder.varianceRupee_value.setText(details[position][2]);
        holder.variancePercent_value.setText(details[position][3]);


    }

    @Override
    public int getItemCount() {
        return details.length;
    }


}

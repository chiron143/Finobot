package com.purplepath.purplepath.investmentPlan.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finobot.finobot.R;
import com.purplepath.purplepath.investmentPlan.models.IPS_Sum;
import com.purplepath.purplepath.investmentPlan.models.InvestmentPlan;

import java.util.ArrayList;

/**
 * Created by Pratheep.S on 20-01-2017.
 */

public class InvestmentStatementAdapter extends RecyclerView.Adapter<InvestmentStatementAdapter.ViewHolder> {
    private ArrayList<IPS_Sum> list_data;
    public InvestmentStatementAdapter(ArrayList<IPS_Sum> ivp) {
        this.list_data = ivp;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {

            super(itemView);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.investment_planning_statement_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {

        return list_data.size();

    }
}

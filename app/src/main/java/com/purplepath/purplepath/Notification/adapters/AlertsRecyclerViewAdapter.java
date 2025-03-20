package com.purplepath.purplepath.Notification.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.purplepath.purplepath.Notification.Models.AlertsModel;
import com.finobot.finobot.R;

/**
 * Created by Pratheep.S on 20-01-2017.
 */

public class AlertsRecyclerViewAdapter extends RecyclerView.Adapter<AlertsRecyclerViewAdapter.ViewHolder> {
    public AlertsModel alertsModel;
    public String priority;

    public AlertsRecyclerViewAdapter(AlertsModel alertsModel) {
        this.alertsModel = alertsModel;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView alertLogo;
        public TextView title, date, discription;

        public ViewHolder(View itemView) {

            super(itemView);
            alertLogo = itemView.findViewById(R.id.iv_alert);
            title = itemView.findViewById(R.id.tv_alert_title);
            date = itemView.findViewById(R.id.tv_alert_date);
            discription = itemView.findViewById(R.id.tv_alert_content);
        }
    }

    @Override
    public AlertsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alerts_single_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlertsRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.title.setText("Alerts ");
        holder.date.setText(alertsModel.getData().getUser_alerts().get(position).getCreated_datetime());
        holder.discription.setText(alertsModel.getData().getUser_alerts().get(position).getAlert_text());
        priority = alertsModel.getData().getUser_alerts().get(position).getAlert_priority();
        if (priority.equals("high")) {
            holder.alertLogo.setImageResource(R.drawable.ic_alert_icon_red);
        } else if (priority.equals("low")) {
            holder.alertLogo.setImageResource(R.drawable.ic_alert_icon_green);
        } else if (priority.equals("medium")) {
            holder.alertLogo.setImageResource(R.drawable.ic_alert_icon_yellow);
        }

    }

    @Override
    public int getItemCount() {
       /* if(!alertsModel.getStatus().equalsIgnoreCase("error"))
        { return alertsModel.getData().getUser_alerts().size();
        }else {
            return 0;
        }*/
        return alertsModel.getData().getUser_alerts().size();

    }
}

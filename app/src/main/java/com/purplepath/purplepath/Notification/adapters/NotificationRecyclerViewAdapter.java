package com.purplepath.purplepath.Notification.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.purplepath.purplepath.Notification.Models.NotificationsModel;
import com.finobot.finobot.R;

/**
 * Created by Pratheep.S on 18-01-2017.
 */

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder>{
   private NotificationsModel notificationsModel;
    private int mCode;
   public NotificationRecyclerViewAdapter(NotificationsModel notificationsModel,int code){
       //notificationsModel.getData().getUser_notifications().get()
     this.notificationsModel=notificationsModel;
       mCode=code;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public  TextView mTextViewContent,mTextViewDate,mTextViewTitle;
        public ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewContent= itemView.findViewById(R.id.tv_notification_content);
            mTextViewDate= itemView.findViewById(R.id.tv_notification_date);
            mTextViewTitle= itemView.findViewById(R.id.tv_notification_title);
            mImageView= itemView.findViewById(R.id.iv_notification);

        }
    }
    @Override
    public NotificationRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_single_item,parent,false);

        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationRecyclerViewAdapter.ViewHolder holder, int position) {
        switch(mCode) {
            case 1:
               holder.mTextViewContent.setText(notificationsModel.getData().getUser_notifications().get(position).getNotification_text());
                holder.mTextViewDate.setText(notificationsModel.getData().getUser_notifications().get(position).getCreated_datetime());
                break;

              case 2:
                  holder.mTextViewTitle.setText("Announcements");
                  holder.mTextViewContent.setText(notificationsModel.getData().getOther_notifications().get(position).getNotification_text());
                  holder.mTextViewDate.setText(notificationsModel.getData().getOther_notifications().get(position).getCreated_datetime());
                  holder.mImageView.setImageResource(R.drawable.ic_announcement_icon);
                break;
        }

//        Log.i("spcheck","onBindViewHolder: size "+notificationsModel.getData().getUser_notifications().size());
    }

    @Override
    public int getItemCount() {
        int notificationSize=0;
        int annoucementSize=0;
        if(notificationsModel.getData().getUser_notifications()!=null) {
            notificationSize = notificationsModel.getData().getUser_notifications().size();
        }
        if(notificationsModel.getData().getOther_notifications()!=null){
        annoucementSize=notificationsModel.getData().getOther_notifications().size();
        }
        switch (mCode) {
            case 1:
                return notificationSize;
            //break;

            case 2:
                return annoucementSize;
            //break;
        }
      return (notificationSize>annoucementSize)?notificationSize:annoucementSize;
    }


}

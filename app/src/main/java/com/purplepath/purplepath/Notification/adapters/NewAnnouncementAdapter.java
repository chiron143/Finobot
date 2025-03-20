package com.purplepath.purplepath.Notification.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.model.homeCardModel.HomeCardsModel;

/**
 * Created by Pratheep.S on 14-10-2017.
 */

public class NewAnnouncementAdapter extends RecyclerView.Adapter<NewAnnouncementAdapter.ViewHolder>{

    private HomeCardsModel homeCardsModel;
    public NewAnnouncementAdapter(HomeCardsModel homeCardsModel){
        this.homeCardsModel= homeCardsModel;
       // mCode=code;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewContent,mTextViewDate,mTextViewTitle;
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
    public NewAnnouncementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_single_item,parent,false);

        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewAnnouncementAdapter.ViewHolder holder, int position) {

                holder.mTextViewTitle.setText("Announcement "+(position+1));
                holder.mTextViewContent.setText(homeCardsModel.getData().getCard2().get(position).getTitle());
                holder.mTextViewDate.setText(homeCardsModel.getData().getCard2().get(position).getText());
                holder.mImageView.setImageResource(R.drawable.ic_announcement_icon);

    }

    @Override
    public int getItemCount() {

        return homeCardsModel.getData().getCard2().size();
    }

}

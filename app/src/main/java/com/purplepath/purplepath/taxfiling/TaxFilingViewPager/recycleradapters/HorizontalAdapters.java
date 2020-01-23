package com.purplepath.purplepath.taxfiling.TaxFilingViewPager.recycleradapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.taxfiling.TaxFilingViewPager.TaxFilingChatViewPager;
import java.util.List;
import static com.purplepath.purplepath.taxfiling.TaxFilingViewPager.TaxFilingChatViewPager.tabPageStates;

/**
 * Created by pravinr on 8/10/18.
 */

public class HorizontalAdapters extends RecyclerView.Adapter<HorizontalAdapters.MyViewHolder> {

    private List<String> horizontalLists;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtView;
        public LinearLayout master_layout;

        public MyViewHolder(View view) {
            super(view);
            txtView = (TextView) view.findViewById(R.id.txtView);
            master_layout = view.findViewById(R.id.master_bg);

        }
    }


    public HorizontalAdapters(List<String> horizontalList) {
        this.horizontalLists = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtView.setText(horizontalLists.get(position));

        if (TaxFilingChatViewPager.tapPageOriginalStates.contains(position)) {
            holder.master_layout.setBackgroundResource(R.drawable.tab_background_green);
        }


        if (position == TaxFilingChatViewPager.page_numbers) {
            holder.master_layout.setBackgroundResource(R.drawable.tab_background);
        }

        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tabPageStates.contains(position)){
                    TaxFilingChatViewPager.viewPagers.setCurrentItem(position);
                    TaxFilingChatViewPager.page_numbers = position;
                    notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalLists.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
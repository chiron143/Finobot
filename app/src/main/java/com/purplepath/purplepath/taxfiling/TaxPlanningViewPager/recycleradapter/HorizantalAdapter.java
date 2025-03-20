package com.purplepath.purplepath.taxfiling.TaxPlanningViewPager.recycleradapter;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.taxfiling.TaxPlanningViewPager.TaxPlanningChatViewPager;

import java.util.List;

import static com.purplepath.purplepath.taxfiling.TaxPlanningViewPager.TaxPlanningChatViewPager.tabPageState;


/**
 * Created by pravinr on 8/1/18.
 */

public class HorizantalAdapter extends RecyclerView.Adapter<HorizantalAdapter.MyViewHolder> {

    private List<String> horizontalList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtView;
        public LinearLayout master_layout;

        public MyViewHolder(View view) {
            super(view);
            txtView = (TextView) view.findViewById(R.id.txtView);
            master_layout = view.findViewById(R.id.master_bg);

        }
    }


    public HorizantalAdapter(List<String> horizontalList) {
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_item_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.txtView.setText(horizontalList.get(position));

        //   if(UtileKit.avoidRefreshFlag == 1) {
        if (TaxPlanningChatViewPager.tapPageOriginalState.contains(position)) {
            holder.master_layout.setBackgroundResource(R.drawable.tab_background_green);
            Log.d("Tab_completed", " show " + TaxPlanningChatViewPager.tapPageOriginalState);
        }
        if (position == TaxPlanningChatViewPager.page_number) {
            holder.master_layout.setBackgroundResource(R.drawable.tab_background);
            Log.d("Tab_place", " show " + position);
        }
//            if (position == horizontalList.size() - 1) {
//                Log.d("avoid", " refresh " + position);
//                UtileKit.avoidRefreshFlag = 0;
//            }

        //  }


        holder.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tab_place_arraypage", " Tab_place_arraypage " + tabPageState);
                Log.d("Adapter Onclic RAJA", " Tab_place_arraypage " + tabPageState);

                if (tabPageState.contains(position)) {
                    //  UtileKit.avoidRefreshFlag = 1;
                    TaxPlanningChatViewPager.viewPager.setCurrentItem(position);
                    TaxPlanningChatViewPager.page_number = position;
                    notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
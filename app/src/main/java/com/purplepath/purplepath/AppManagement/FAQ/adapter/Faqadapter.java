package com.purplepath.purplepath.AppManagement.FAQ.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purplepath.purplepath.AppManagement.FAQ.model.FAQ;
import com.purplepath.purplepath.AppManagement.FAQ.model.Faqmodel;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.goaltimeline.GoalTimeLineFragmentMainpageAdapter;
import com.purplepath.purplepath.goaltimeline.model.GoalTimeLineModel;
import com.purplepath.purplepath.goaltimeline.model.Goal_tmln;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pravinr on 5/18/17.
 */

public class Faqadapter extends RecyclerView.Adapter<Faqadapter.ViewHolder> {
    Context context;
    private ArrayList<FAQ> faq;
    Faqmodel faqmodel;
    boolean isFirstViewClick=false;
    boolean isSecondViewClick=false;

    public Faqadapter(Context mContext, ArrayList<FAQ> faq, Faqmodel faqmodel) {
        this.context = mContext;
        this.faq=faq;
        this.faqmodel=faqmodel;
    }



    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public Faqadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_faq_adapter, parent, false);
        return new Faqadapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Faqadapter.ViewHolder holder, int position) {
       // holder.mtxt_categories .setText (faq.get(position).getCategory());
        holder.mtxt_question.setText(faq.get(position).getFaq_que());
        holder.mtxt_answer.setText(faq.get(position).getFaq_ans());
       // holder.mtxt_date.setText(faq.get(position).getModified_datetime());



        if(isFirstViewClick==false){
            holder.imageuparrow.setBackgroundResource(R.drawable.ic_add_icon);
            holder.mtxt_answer.setVisibility(View.GONE);
        }
        else{
            holder.imageuparrow.setBackgroundResource(R.drawable.ic_mine_icon);
            holder.mtxt_answer.setVisibility(View.VISIBLE);
        }

        holder.linear_layout_first.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(isFirstViewClick==false){
                    isFirstViewClick=true;
                    holder.imageuparrow.setBackgroundResource(R.drawable.ic_mine_icon);
                    holder.mtxt_answer.setVisibility(View.VISIBLE);

                }else{
                    isFirstViewClick=false;
                    holder.imageuparrow.setBackgroundResource(R.drawable.ic_add_icon);
                    holder.mtxt_answer.setVisibility(View.GONE);
                }
                return false;
            }
        });


        if(faq.get(position).getModified_datetime()!=null) {
            String dob1 = UtileKit.getTextFromObjects((faq.get(position).getModified_datetime()));
            if (UtileKit.validateObjectValues(dob1)) {
                try {
                    holder.mtxt_date.setText(parseDate(dob1));
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
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yy";
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
        return faq.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mtxt_question,mtxt_answer,mtxt_date;
        LinearLayout linear_layout_first,linear_layout_second;
        ImageView imageuparrow;

        public ViewHolder(View view) {
            super(view);
            mView = view;
           // mtxt_categories = (TextView) view.findViewById(R.id.txt_categories);
            mtxt_question = view.findViewById(R.id.txt_question);
            mtxt_answer = view.findViewById(R.id.txt_answer);

            imageuparrow= view.findViewById(R.id.imageuparrow);

            linear_layout_first= view.findViewById(R.id.linear_layout_first);
           // linear_layout_second=(LinearLayout)view.findViewById(R.id.linear_layout_second);
            mtxt_date= view.findViewById(R.id.txt_date);

        }
    }
}

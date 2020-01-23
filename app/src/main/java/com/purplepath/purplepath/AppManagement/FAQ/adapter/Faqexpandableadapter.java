package com.purplepath.purplepath.AppManagement.FAQ.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.purplepath.purplepath.AppManagement.FAQ.model.FAQ;
import com.purplepath.purplepath.AppManagement.FAQ.model.Faqmodel;
import com.purplepath.purplepath.AppManagement.Glossaries.Models.GlossariesModel;
import com.finobot.finobot.R;
import com.purplepath.purplepath.AppManagement.Knowledge.model.Knowledgemodel;

import com.purplepath.purplepath.apputiles.UtileKit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by pravinr on 6/7/17.
 */

public class Faqexpandableadapter extends BaseExpandableListAdapter {


    TextView headerTextView,headerAlphabetTextView;
    ImageView headerImageView;
    String str;
    Typeface typface;

    ImageView imageuparrow;
    TextView txt_question,txt_date;

    Context context;
    private ArrayList<FAQ> faq;
    Faqmodel faqmodel;

    public Faqexpandableadapter(Context context,  Faqmodel faqmodel) {
        this.context = context;
        this.faqmodel = faqmodel;
    }



    @Override
    public int getGroupCount() {
        return faqmodel.getData().getFaq().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        if (faqmodel.getData().getFaq().get(groupPosition) != null) {
            childCount = 1;
        }
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.faq_header_exp_listview, parent, false);
        }
        imageuparrow = convertView.findViewById(R.id.imageuparrow);
        txt_question= convertView.findViewById(R.id.txt_question);
        txt_date = convertView.findViewById(R.id.txt_date);


        txt_question.setText(faqmodel.getData().getFaq().get(groupPosition).getFaq_que());

        if(faqmodel.getData().getFaq().get(groupPosition).getModified_datetime()!=null) {
            String dob1 = UtileKit.getTextFromObjects((faqmodel.getData().getFaq().get(groupPosition).getModified_datetime()));
            if (UtileKit.validateObjectValues(dob1)) {
                try {
                    //holder.mtxt_date.setText(parseDate(dob1));
                    txt_date.setText(parseDate(dob1));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        if (isExpanded) {
            imageuparrow.setImageResource(R.drawable.ic_mine_icon);
        } else {
            imageuparrow.setImageResource(R.drawable.ic_add_icon);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.faq_child_listview, parent, false);
        TextView childTextView= view.findViewById(R.id.childTextView);
        childTextView.setText(faqmodel.getData().getFaq().get(groupPosition).getFaq_ans());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public String parseDate(String time) {
        String str = null;
        DateFormat oldFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ");
        Date oldDate = null;
        try {
            oldDate = oldFormatter .parse(time);
            str =formatter.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(formatter.format(oldDate));

        return str;
    }

}

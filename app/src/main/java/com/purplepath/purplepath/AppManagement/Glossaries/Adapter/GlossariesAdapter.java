package com.purplepath.purplepath.AppManagement.Glossaries.Adapter;

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

import com.purplepath.purplepath.AppManagement.Glossaries.Models.GlossariesModel;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pratheep.S on 19-05-2017.
 */

public class GlossariesAdapter extends BaseExpandableListAdapter {
    Context context;
    GlossariesModel glossariesModel;
    TextView headerTextView,headerAlphabetTextView;
    ImageView headerImageView;
    String str;
    Typeface typface;
    public GlossariesAdapter(Context context, GlossariesModel glossariesModel) {
        this.context = context;
        this.glossariesModel = glossariesModel;
    }

    @Override
    public int getGroupCount() {
        return glossariesModel.getData().getGlossaries().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        if (glossariesModel.getData().getGlossaries().get(groupPosition) != null) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.glossary_header_exp_listview, parent, false);
        }
        headerTextView = convertView.findViewById(R.id.header);
        headerAlphabetTextView= convertView.findViewById(R.id.headerAlphabet);
        headerImageView = convertView.findViewById(R.id.headerImgView);

        Typeface.createFromAsset(context.getAssets(),"MyriadWebPro-Italic.ttf");
        headerAlphabetTextView.setTypeface(typface);
        headerAlphabetTextView.setText(glossariesModel.getData().getGlossaries().get(groupPosition).getGloss_alphabet());
        str=glossariesModel.getData().getGlossaries().get(groupPosition).getGloss_word().toString();
        SpannableString spannableString=new SpannableString(str);
        spannableString.setSpan(new UnderlineSpan(),0,str.length(),0);
        headerTextView.setText(spannableString);

        if (isExpanded) {
            headerImageView.setImageResource(R.drawable.ic_arrow_icon_ups);
        } else {
            headerImageView.setImageResource(R.drawable.ic_arrow_icon_dwons);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.glossary_child_listview, parent, false);
        TextView childTextView= view.findViewById(R.id.childTextView);
        TextView childdateView= view.findViewById(R.id.childdateView);
        if(glossariesModel.getData().getGlossaries().get(groupPosition).getGloss_description()!= null) {

            //childTextView.setText(glossariesModel.getData().getGlossaries().get(groupPosition).getGloss_description());

         //   UtileKit.setHtml(glossariesModel.getData().getGlossaries().get(groupPosition).getGloss_description());

            childTextView.setText(UtileKit.setHtml(glossariesModel.getData().getGlossaries().get(groupPosition).getGloss_description()));

        }
        if(glossariesModel.getData().getGlossaries().get(groupPosition).getModified_datetime()!= null) {
            try {
                childdateView.setText(parseDate(glossariesModel.getData().getGlossaries().get(groupPosition).getModified_datetime()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
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

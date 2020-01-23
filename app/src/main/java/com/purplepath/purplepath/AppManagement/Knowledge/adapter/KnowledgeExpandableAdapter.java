package com.purplepath.purplepath.AppManagement.Knowledge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.AppManagement.Knowledge.model.Knowledge;
import com.purplepath.purplepath.AppManagement.Knowledge.model.Knowledgemodel;

import java.util.ArrayList;

import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.service_base_url;
import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.service_base_url_sub;

/**
 * Created by pravinr on 8/21/17.
 */

public class KnowledgeExpandableAdapter extends BaseExpandableListAdapter {


    Context mContext;
    private ArrayList<Knowledge> knowledge;
    Knowledgemodel knowledgemodels;

    ImageView imageuparrow;
    TextView knowledge_heading;
    //WebView knowledge_heading;

    private WebView webView;

    public KnowledgeExpandableAdapter(Context mContext, Knowledgemodel knowledgemodel) {
        this.mContext = mContext;
        this.knowledgemodels=knowledgemodel;
    }

    @Override
    public int getGroupCount() {
        return knowledgemodels.getData().getKnowledge().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        if (knowledgemodels.getData().getKnowledge().get(groupPosition)!= null) {
            childCount = 1;
        }
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.knowledge_header_exp_listview, parent, false);
        }
        imageuparrow = convertView.findViewById(R.id.imageuparrow);
        knowledge_heading= convertView.findViewById(R.id.knowledge_heading);
        knowledge_heading.setText(knowledgemodels.getData().getKnowledge().get(groupPosition).getTopic());


        /*knowledge_heading=(WebView)convertView.findViewById(R.id.knowledge_heading);
        knowledge_heading.setVerticalScrollBarEnabled(true);
        knowledge_heading.setHorizontalScrollBarEnabled(true);
        knowledge_heading.loadUrl(knowledgemodels.getData().getKnowledge().get(groupPosition).getTopic());*/


        if (isExpanded) {
            imageuparrow.setImageResource(R.drawable.ic_mine_icon);
        } else {
            imageuparrow.setImageResource(R.drawable.ic_add_icon);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.knowledge_child_listview, parent, false);
        }

        webView = convertView.findViewById(R.id.webView_knowledge);
        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(true);


        if(null!=service_base_url_sub+knowledgemodels.getData().getKnowledge().get(groupPosition).getWeb_url()) {

            webView.loadUrl(service_base_url_sub.concat(knowledgemodels.getData().getKnowledge().get(groupPosition).getWeb_url()));
            webView.invalidate();

        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


}

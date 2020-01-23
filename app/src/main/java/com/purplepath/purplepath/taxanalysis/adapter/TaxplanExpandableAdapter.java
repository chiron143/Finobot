package com.purplepath.purplepath.taxanalysis.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.Ded_by_prod;
import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

/**
 * Created by pravinr on 1/5/18.
 */

public class TaxplanExpandableAdapter extends BaseExpandableListAdapter {


    ImageView imageuparrow;

    TextView expand_tax_section;

    ArrayList<ArrayList<Ded_by_prod>> ded_by_prod;

    Context context;

    public TaxplanExpandableAdapter(Context context, ArrayList<ArrayList<Ded_by_prod>> faqmodel) {
        this.context = context;
        this.ded_by_prod=faqmodel;
    }

    @Override
    public int getGroupCount() {
        return ded_by_prod.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        if (ded_by_prod.get(groupPosition) != null) {
            childCount = 1;
        }
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return ded_by_prod.get(groupPosition);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.tax_header_exp_listview, parent, false);
        }

        imageuparrow = convertView.findViewById(R.id.imageuparrow);
        expand_tax_section= convertView.findViewById(R.id.expand_tax_section);

        Log.d("ded_by_prodsize","ded_by_prodsize"+ded_by_prod.size());

        //ArrayList<Ded_by_prod> draftArray = ded_by_prod.get(groupPosition);

        int length=ded_by_prod.size();
        for(int i=0;i<length;i++) {


                expand_tax_section.setText(ded_by_prod.get(i).get(groupPosition).getTax_section());

                // Log.d("expand_tax_section", "expand_tax_section" + ded_by_prod.get(i).get(groupPosition).getTax_section());

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

      //  childTextView.setText(ded_by_prod.getData().getFaq().get(groupPosition).getFaq_ans());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }



}

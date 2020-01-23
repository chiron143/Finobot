package com.purplepath.purplepath.taxfiling.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.taxfiling.getchecklist.Result;

import java.util.ArrayList;

import static com.finobot.finobot.MyApplication.getContext;
import static com.finobot.finobot.R.id.txt_proceeds;


/**
 * Created by pravinr on 3/28/18.
 */

public class TaxFileCheckListAdapter extends BaseAdapter {

    Context mContext;

    private ArrayList<Result> arrayListResult=new ArrayList<>();


    public TaxFileCheckListAdapter(Context mContext, ArrayList<Result> results) {
        this.mContext = mContext;
        this.arrayListResult=results;
    }

    @Override
    public int getCount() {
        return arrayListResult.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListResult.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_checklist_item, null);
            holder = new ViewHolder();

            holder.msection = convertView.findViewById(R.id.section);
            holder.mlayout_upload= convertView.findViewById(R.id.layout_upload);
            holder.layout_tick_upload= convertView.findViewById(R.id.layout_tick_upload);
            holder.mcount=convertView.findViewById(R.id.count);
            holder.minsufficient=convertView.findViewById(R.id.insufficient);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.msection.setText(arrayListResult.get(i).getSection());

        String upload_flag=arrayListResult.get(i).getUploaded();
        String rejected_files=arrayListResult.get(i).getRejected_count();
        String insufficient_files=arrayListResult.get(i).getInsuff_files();

        if(!rejected_files.equalsIgnoreCase("0")){
        holder.mcount.setVisibility(View.VISIBLE);
        holder.mcount.setText("(".concat(rejected_files.concat(")").concat("Files Rejected")));
        }else {
            holder.mcount.setVisibility(View.GONE);
        }


        if(upload_flag.equalsIgnoreCase("true")){
            holder.layout_tick_upload.setVisibility(View.VISIBLE);
            holder.mlayout_upload.setVisibility(View.GONE);
        }else {
            holder.mlayout_upload.setVisibility(View.VISIBLE);
            holder.layout_tick_upload.setVisibility(View.GONE);
        }

      //  Animation startAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anim_blinking);
       // holder.minsufficient.startAnimation(startAnimation);

        if(insufficient_files.equalsIgnoreCase("true")){
            holder.minsufficient.setVisibility(View.VISIBLE);
            holder.minsufficient.setText("Insufficient Files");
        }else {
            holder.minsufficient.setVisibility(View.GONE);
        }

        String section26=arrayListResult.get(i).getSection();

        if(section26.equalsIgnoreCase("Form 26AS")){
            holder.layout_tick_upload.setVisibility(View.INVISIBLE);
            holder.mlayout_upload.setVisibility(View.INVISIBLE);
        }




        return convertView;
    }



    private class ViewHolder {
        private TextView msection,mcount,minsufficient;
        private RelativeLayout mlayout_upload,layout_tick_upload;
    }



}

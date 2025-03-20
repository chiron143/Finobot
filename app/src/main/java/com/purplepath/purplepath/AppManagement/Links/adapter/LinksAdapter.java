package com.purplepath.purplepath.AppManagement.Links.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purplepath.purplepath.AppManagement.Links.model.Links;
import com.purplepath.purplepath.AppManagement.Links.model.Linksmodel;
import com.finobot.finobot.R;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.service_base_url;
import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.service_base_url_sub;

/**
 * Created by pravinr on 5/19/17.
 */

public class LinksAdapter extends BaseAdapter {

    Context mContext;
    private ArrayList<Links> links;
    Linksmodel linksmodels;

    public LinksAdapter(Context mContext, ArrayList<Links> link, Linksmodel linksmodel) {
        this.mContext = mContext;
        this.links=link;
        this.linksmodels=linksmodel;
    }

    @Override
    public int getCount() {
        return links.size();
    }

    @Override
    public Object getItem(int position) {
        return links.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_links_item, null);
            holder = new ViewHolder();
            holder.mtitle = convertView.findViewById(R.id.title);
            holder.murl = convertView.findViewById(R.id.url);
            holder.mlink_image= convertView.findViewById(R.id.link_image);
            holder.mdescription= convertView.findViewById(R.id.description);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.mtitle.setText(links.get(i).getTitle());
        holder.murl.setText(links.get(i).getUrl());
        holder.murl.setPaintFlags(holder.murl.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        holder.mdescription.setText(links.get(i).getDescription());


        if(null!=service_base_url_sub+links.get(i).getImage()) {
            Picasso.with(mContext)
                    .load(service_base_url_sub + links.get(i).getImage())
                    .placeholder(R.drawable.ic_link_dummy)
                    .resize(150, 150)
                    .centerCrop()
                    .into(holder.mlink_image);
        }



        return convertView;
    }



    private class ViewHolder {
        private  View mView;
        private TextView mtitle,mdescription;
        private  TextView murl;
        ImageView mlink_image;
    }



}

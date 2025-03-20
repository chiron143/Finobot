package com.purplepath.purplepath.AppManagement.Vendors.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.purplepath.purplepath.AppManagement.Vendors.Models.VendorDetailsModel;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.document.Interface.OnItemClickListenerInterface;

/**
 * Created by Pratheep.S on 18-05-2017.
 */

public class VendorAdapter extends RecyclerView.Adapter<VendorAdapter.ViewHolder> {
    VendorDetailsModel vendorDetailsModel;
    Context context;
    private OnItemClickListenerInterface mOnclickInterface;



    public VendorAdapter(VendorDetailsModel vendorDetailsModel, Context context, OnItemClickListenerInterface getpositionInterface) {
        this.vendorDetailsModel = vendorDetailsModel;
        this.context = context;
        this.mOnclickInterface= getpositionInterface;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView vendorName, vendorTitle, vendorCompany, vendorAdress;
        public ImageView vendorImage;
        public LinearLayout website_layout, send_mail_layout, call_now_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            vendorName = itemView.findViewById(R.id.txt_vendor_name);
            vendorTitle = itemView.findViewById(R.id.txt_title);
            vendorCompany = itemView.findViewById(R.id.txt_company);
            vendorAdress = itemView.findViewById(R.id.txt_address);
            vendorImage = itemView.findViewById(R.id.img_vendor_image);
            call_now_layout = itemView.findViewById(R.id.call_now_layout);
            send_mail_layout = itemView.findViewById(R.id.send_mail_layout);
            website_layout = itemView.findViewById(R.id.website_layout);
        }
    }

    @Override
    public VendorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vendor_single_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VendorAdapter.ViewHolder holder, final int position) {
        holder.vendorName.setText(vendorDetailsModel.getData().getVendors().get(position).getVendor_name());
        holder.vendorTitle.setText(vendorDetailsModel.getData().getVendors().get(position).getTitle());
        holder.vendorCompany.setText(vendorDetailsModel.getData().getVendors().get(position).getCompany());
        holder.vendorAdress.setText(vendorDetailsModel.getData().getVendors().get(position).getAddress());
        holder.call_now_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String phoneNo = vendorDetailsModel.getData().getVendors().get(position).getPhone_number();
                if (UtileKit.validateObjectValues(phoneNo)) {
                    mOnclickInterface.onClick(v,position);
                } else {
                    Toast.makeText(context, "No Phone no given", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.send_mail_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = vendorDetailsModel.getData().getVendors().get(position).getEmail();
                if (UtileKit.validateObjectValues(email)) {
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setData(Uri.parse("mailto:" + email));
                    try {
                        context.startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, "No email clients installed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "No Email ID given", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.website_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link= vendorDetailsModel.getData().getVendors().get(position).getWebsite();
                if (UtileKit.validateObjectValues(link)) {
                    Intent i=new Intent(Intent.ACTION_VIEW);
                    if(!link.startsWith("http://")||!link.startsWith("https://")) {
                        i.setData(Uri.parse("http://"+link));
                    }else{
                        i.setData(Uri.parse(link));
                    }
                    try{
                        context.startActivity(i);
                    }catch(ActivityNotFoundException e){
                        Toast.makeText(context, "No application can handle this request."
                                + " Please install a webbrowser",  Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "No Website details given", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return vendorDetailsModel.getData().getVendors().size();
    }

}








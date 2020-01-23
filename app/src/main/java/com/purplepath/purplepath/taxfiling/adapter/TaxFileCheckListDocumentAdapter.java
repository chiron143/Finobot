package com.purplepath.purplepath.taxfiling.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.document.Interface.OnItemClickListenerInterface;
import com.purplepath.purplepath.taxfiling.getchecklist.Result;
import com.purplepath.purplepath.taxfiling.getchecklistdocument.Document_details;
import com.purplepath.purplepath.taxfiling.getchecklistdocument.GetChecklistModel;

import java.util.ArrayList;

import static com.purplepath.purplepath.Notification.adapters.PromptsRecyclerViewAdapter.context;

/**
 * Created by pravinr on 3/28/18.
 */

public class TaxFileCheckListDocumentAdapter extends BaseAdapter {

    Context mContext;

    GetChecklistModel getChecklistModel;

    private ArrayList<Document_details> document_details;

    private OnItemClickListenerInterface mOnclickInterface;


    public TaxFileCheckListDocumentAdapter(Context mContext, ArrayList<Document_details> document_details,
                                           GetChecklistModel getChecklistModel,
                                           OnItemClickListenerInterface getpositionInterface) {
        this.mContext = mContext;
        this.document_details = document_details;
        this.getChecklistModel = getChecklistModel;
        this.mOnclickInterface= getpositionInterface;
    }

    @Override
    public int getCount() {
        return document_details.size();
    }

    @Override
    public Object getItem(int position) {
        return document_details.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_checklist_document_item, null);
            holder = new ViewHolder();

            holder.mdocument_image= convertView.findViewById(R.id.document_image);
            holder.mdocument_name = convertView.findViewById(R.id.document_name);
            holder.mdocument_date = convertView.findViewById(R.id.document_date);
            holder.mdelete= convertView.findViewById(R.id.delete);

            holder.mrelativelayout=convertView.findViewById(R.id.relativelayout);
            holder.mstatus=convertView.findViewById(R.id.status);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mdocument_name.setText(document_details.get(position).getDoc_name());
        holder.mdocument_date.setText(document_details.get(position).getModified_datetime());
        String fileSelected =document_details.get(position).getDoc_url();
        String vefified_status=document_details.get(position).getVerified_status();
        String rejected_status=document_details.get(position).getRejected_status();

        if(vefified_status.equalsIgnoreCase("Y")){
            holder.mrelativelayout.setBackgroundResource(R.color.white);
            UtileKit.setSvgImageviewDrawable(holder.mdelete, mContext,R.drawable.ic_delet_icon);
            //holder.mstatus.setTextColor(R.color.ForestGreen);
            holder.mstatus.setTextColor(Color.parseColor("#228B22"));
            holder.mstatus.setText("Verified");

        }else if(rejected_status.equalsIgnoreCase("Y")){
            holder.mrelativelayout.setBackgroundResource(R.color.gray);
            UtileKit.setSvgImageviewDrawable(holder.mdelete, mContext,R.drawable.ic_delet_icon_red);
            //holder.mstatus.setTextColor(R.color.reds);
            holder.mstatus.setTextColor(Color.parseColor("#FF0000"));
            holder.mstatus.setText("Rejected");

        }else {
            holder.mrelativelayout.setBackgroundResource(R.color.white);
            UtileKit.setSvgImageviewDrawable(holder.mdelete, mContext,R.drawable.ic_delet_icon);
           // holder.mstatus.setTextColor(R.color.blue_text_glossaries);
            holder.mstatus.setTextColor(Color.parseColor("#005aef"));
            holder.mstatus.setText("Yet to verify");

        }


        if(fileSelected.contains(".")) {
            String type = fileSelected.substring(fileSelected.lastIndexOf("."));
            switch (type) {
                case ".pdf":
                    filetype(type, R.drawable.ic_pdf,mContext,holder.mdocument_image);
                    break;
                case ".png":
                    filetype(type, R.drawable.ic_png,mContext,holder.mdocument_image);
                    break;
                case ".jpg":
                    filetype(type, R.drawable.ic_jpg, mContext, holder.mdocument_image);
                    break;
                case ".jpeg":
                    filetype(type, R.drawable.ic_jpg, mContext, holder.mdocument_image);
                    break;
                case ".docx":
                    filetype(type, R.drawable.ic_docx_48, mContext, holder.mdocument_image);
                    break;
                case ".doc":
                    filetype(type, R.drawable.ic_docx_48, mContext, holder.mdocument_image);
                    break;
                case ".gif":
                    filetype(type, R.drawable.ic_gif, mContext, holder.mdocument_image);
                    break;
                case ".xls":
                    filetype(type, R.drawable.ic_xls, mContext, holder.mdocument_image);
                    break;
                case ".xlsx":
                    filetype(type, R.drawable.ic_xlsx_48, mContext, holder.mdocument_image);
                    break;
                case ".zip":
                    filetype(type, R.drawable.ic_zip, mContext, holder.mdocument_image);
                    break;
                case ".csv":
                    filetype(type, R.drawable.ic_csv, mContext, holder.mdocument_image);
                    break;
                default:
                    filetype(type, R.drawable.icon_media, mContext, holder.mdocument_image);
                    break;
            }
        }
        else {
            Toast.makeText(mContext, "file extension not showing", Toast.LENGTH_SHORT).show();
        }




        //Delete the files
        holder.mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=document_details.get(position).getId()){
                    int id_position = Integer.parseInt(document_details.get(position).getId());
                    mOnclickInterface.onClick(v,id_position);
                    document_details.remove(position);
                }
            }
        });


        return convertView;
    }

    public void updateResults(String documentId) {
        int size = document_details.size();
        for(int i=0;i<size;i++){
            if(documentId.equalsIgnoreCase(document_details.get(i).getId())){
                document_details.remove(i);

                notifyDataSetChanged();
            }
        }
    }


    private class ViewHolder {
        public  TextView mdocument_name,mdocument_date,mstatus;
        public ImageView mdocument_image,mdelete;
        public RelativeLayout mrelativelayout;
    }
    private void filetype(String type, int icon, Context mContext, ImageView mdocument_image) {
        mdocument_image.setImageResource(icon);
    }


}

package com.purplepath.purplepath.taxfiling.dialogistructioncsreenadapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.finobot.finobot.R;
import com.purplepath.purplepath.taxfiling.getchecklistdocument.Document_details;
import com.purplepath.purplepath.taxfiling.getchecklistdocument.GetChecklistModel;
import com.purplepath.purplepath.taxfiling.getdialogforminstruction.Samples;

import java.util.ArrayList;

/**
 * Created by pravinr on 7/3/18.
 */

public class TaxFilingFormABadapter extends BaseAdapter {

    Context mContext;

    private ArrayList<Samples> arrayListResult=new ArrayList<>();


    public TaxFilingFormABadapter(Context mContext, ArrayList<Samples> arrayListResult) {
        this.mContext = mContext;
        this.arrayListResult=arrayListResult;
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_formab_single_item, null);
            holder = new ViewHolder();

            holder.mdocument_image= convertView.findViewById(R.id.document_image);
            holder.mdocument_name = convertView.findViewById(R.id.document_name);
            holder.view_details=convertView.findViewById(R.id.view_details);


            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mdocument_name.setText(arrayListResult.get(position).getName());
        String fileSelected =arrayListResult.get(position).getUrl();


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



        return convertView;
    }



    private class ViewHolder {
        public TextView mdocument_name,view_details;
        public ImageView mdocument_image;
    }
    private void filetype(String type, int icon, Context mContext, ImageView mdocument_image) {
        mdocument_image.setImageResource(icon);
    }


}

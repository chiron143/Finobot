package com.purplepath.purplepath.document.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.document.Interface.OnItemClickListenerInterface;
import com.purplepath.purplepath.document.getfilemodels.Document_details;
import com.purplepath.purplepath.document.getfilemodels.GetDocumentModels;

import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.finobot.finobot.R.id.layout_section_product;
import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.download_file_url;


/**
 * Created by pravinr on 6/20/17.
 */

public class DocumentListAdapter extends BaseAdapter implements Filterable,  DownloadInterface {

    Context mContext;
    GetDocumentModels mdocumentlinksmodels;
    private OnItemClickListenerInterface mOnclickInterface;
    Uri uri;
    String sharing_content_start="like to share the following file with you. Click here";
    String sharing_content_end="to view the file";
    String name;
    int REQUEST_STORAGE=1;
    private ArrayList<Document_details> document_details;
    private ArrayList<Document_details> mFilteredList=new ArrayList<>();
    private int STORAGE_PERMISSION_CODE = 23;

    public DocumentListAdapter(Context mContext, ArrayList<Document_details> document_details,
                               GetDocumentModels getDocumentModels, OnItemClickListenerInterface getpositionInterface) {
        this.mContext = mContext;
        this.document_details = document_details;
        this.mFilteredList.addAll(document_details);
        this.mdocumentlinksmodels = getDocumentModels;
        this.mOnclickInterface= getpositionInterface;
    }
    public void updateResults(String results) {
        int size = document_details.size();
        for(int i=0;i<size;i++){
            if(results.equalsIgnoreCase(document_details.get(i).getId())){
                document_details.remove(i);

                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getCount() {
        return mFilteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFilteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_document_listview_item, null);
            holder = new ViewHolder();
            holder.mdocument_image= convertView.findViewById(R.id.document_image);
            holder.mdocument_name = convertView.findViewById(R.id.document_name);
            holder. mdocument_date = convertView.findViewById(R.id.document_date);
            holder.mrelativelayout = convertView.findViewById(R.id.relativelayout);
            holder.mdownload= convertView.findViewById(R.id.upload);
            holder.mdelete= convertView.findViewById(R.id.delete);
            holder.mshare= convertView.findViewById(R.id.share);

            holder.layout_section_product=convertView.findViewById(R.id.layout_section_product);
            holder.mSection=convertView.findViewById(R.id.section);
            holder.mProduct=convertView.findViewById(R.id.product);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mdocument_name.setText(mFilteredList.get(position).getDoc_name());
        holder.mdocument_date.setText(mFilteredList.get(position).getModified_datetime());

        String taxSection=mFilteredList.get(position).getSub_digital_version();
        String taxProduct=mFilteredList.get(position).getProduct();

        if(taxSection!=null&&!taxSection.equalsIgnoreCase("")){
            holder.layout_section_product.setVisibility(View.VISIBLE);
            holder.mSection.setText(taxSection);

            if(taxProduct!=null)
            holder.mProduct.setText(taxProduct);
        }else {
            holder.layout_section_product.setVisibility(View.GONE);
        }





        String fileSelected =mFilteredList.get(position).getDoc_url();
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


        //Download the files
                holder.mdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(null!=mFilteredList.get(position).getDoc_url()&& null!=mFilteredList.get(position).getDoc_name()){
                     uri = Uri.parse(download_file_url + mFilteredList.get(position).getDoc_url());
                     name = mFilteredList.get(position).getDoc_name();
                    if(isReadStorageAllowed()){
                        downlondMethodFromWeb(uri,name);
                        return;
                    }
                    requestStoragePermission();
                }
            }
        });
        //Delete the files
        holder.mdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mFilteredList.get(position).getId()){
                    int id_position = Integer.parseInt(mFilteredList.get(position).getId());
                    mOnclickInterface.onClick(v,id_position);
                    mFilteredList.remove(position);
                }
            }
        });
        //Share the file url
        holder.mshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null!=mFilteredList.get(position).getId()){
                    if(null!=download_file_url+mFilteredList.get(position).getDoc_url()){
                        String getprefuserName = UtileKit.getPersistedPurplePathPref("name_services",null);
                        if(getprefuserName!=null) {
                            String name = (getprefuserName+" "+sharing_content_start+" ("+download_file_url +
                                    mFilteredList.get(position).getDoc_url()+") "+sharing_content_end);
                        Log.d("hi","namekkkkkk"+name);
                    showPdf(mContext,name);  }
                    }
                }
            }
        });
        return convertView;
    }
    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,Manifest.permission.READ_EXTERNAL_STORAGE)){
        }
        ActivityCompat.requestPermissions((Activity) mContext,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(mContext,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(mContext,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }
    private class ViewHolder {
        public  View mView;
        public  TextView mdocument_name,mdocument_date,mSection,mProduct;
        public  RelativeLayout mrelativelayout,layout_section_product;
        ImageView mdocument_image,mdownload,mdelete,mshare;
    }

    //Download the file method
    private void downlondMethodFromWeb(Uri uri, String name) {
        DownloadManager.Request r = new DownloadManager.Request(uri);
        r.setDescription("Finobot").setTitle("Finobot");
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);
        r.allowScanningByMediaScanner();
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager dm = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }

        //Filter the adapter interface
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase(Locale.getDefault());
                FilterResults filterResults = new FilterResults();
                if (charString==null||charString.length()==0||charString.isEmpty()) {
                    filterResults.values = document_details;
                    filterResults.count = document_details.size();
                } else {
                    ArrayList<Document_details> filteredList = new ArrayList<>();
                    for (Document_details docum : document_details) {
                        if (docum.getDoc_name().toLowerCase(Locale.getDefault()).contains(charString)) {
                            filteredList.add(docum);
                            Log.d("hihi","added"+docum.getDoc_name());
                        }
                        else if (docum.getModified_datetime().toLowerCase(Locale.getDefault()).contains(charString)) {
                            filteredList.add(docum);
                            Log.d("hihi","added"+docum.getDoc_name());
                        }
                    }
                    filterResults.values = filteredList;
                    filterResults.count = document_details.size();
                }
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList.clear();
                mFilteredList.addAll((ArrayList<Document_details>) filterResults.values);
                Log.d("hihi","filterrrr"+mFilteredList);
                notifyDataSetChanged();
            }
        };
    }

    private void filetype(String type, int icon, Context mContext, ImageView mdocument_image) {
        mdocument_image.setImageResource(icon);
    }


    private void showPdf(Context context,String url) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, url);
        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }
}
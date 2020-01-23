package com.purplepath.purplepath.document.adapter;

/**
 * Created by pravinr on 6/22/17.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.document.Interface.Constants;
import com.purplepath.purplepath.document.ui.FileChooserActivity;
import com.purplepath.purplepath.document.ui.FileInfo;

import java.util.List;


@SuppressLint("DefaultLocale")
public class FileArrayAdapter extends ArrayAdapter<FileInfo> {

    private Context context;
    private int resorceID;
    private List<FileInfo> items;

    public FileArrayAdapter(Context context, List<FileInfo> objects) {
        super(context, 0 ,objects);
        this.context = context;
        this.items = objects;
    }

    public FileInfo getItem(int i) {
        return items.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.file_upload_row, null);
            viewHolder = new ViewHolder();
            viewHolder.icon = convertView.findViewById(R.id.icon);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.details = convertView.findViewById(R.id.details);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        FileInfo option = items.get(position);
        if (option != null) {

            if (option.getData().equalsIgnoreCase(Constants.FOLDER)) {
                viewHolder.icon.setImageResource(R.drawable.folder);
            } else if (option.getData().equalsIgnoreCase(Constants.PARENT_FOLDER)) {
                viewHolder.icon.setImageResource(R.drawable.back);
            } else {
                String name = option.getName().toLowerCase();
                if (name.endsWith(Constants.XLS) || name.endsWith(Constants.XLSX))
                    viewHolder.icon.setImageResource(R.drawable.ic_xls);
                else if (name.endsWith(Constants.DOC) || name.endsWith(Constants.DOCX))
                    viewHolder.icon.setImageResource(R.drawable.ic_docx_48);

                else if (name.endsWith(Constants.PDF))
                    viewHolder.icon.setImageResource(R.drawable.ic_pdf);
                else if (name.endsWith(Constants.APK))
                    viewHolder.icon.setImageResource(R.drawable.apk);

                else if (name.endsWith(Constants.JPG) || name.endsWith(Constants.JPEG))
                    viewHolder.icon.setImageResource(R.drawable.ic_jpg);
                else if (name.endsWith(Constants.PNG))
                    viewHolder.icon.setImageResource(R.drawable.ic_png);
                else if (name.endsWith(Constants.ZIP))
                    viewHolder.icon.setImageResource(R.drawable.ic_zip);

                else if (name.endsWith(Constants.GIF))
                    viewHolder.icon.setImageResource(R.drawable.ic_gif);

                else if (name.endsWith(Constants.ACC))
                    viewHolder.icon.setImageResource(R.drawable.aac);
                else
                    viewHolder.icon.setImageResource(R.drawable.blank);
            }

            viewHolder.name.setText(option.getName());
            viewHolder.details.setText(option.getData());

        }
        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView name;
        TextView details;
    }

}
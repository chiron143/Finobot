package com.purplepath.purplepath.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.finobot.finobot.R;

import java.util.List;

/**
 * Created by dinesh on 24/07/16.
 */
public class CustomSpinerAdapter extends ArrayAdapter<String> {

    LayoutInflater inflater;
    List<String> objects;
    public CustomSpinerAdapter(Context context, List<String> objects) {
        super(context, 0, objects);
        // TODO Auto-generated constructor stub
        this.objects=objects;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View v;
        if (position == 0) {
            TextView tv = new TextView(getContext());
            tv.setHeight(0);
            tv.setVisibility(View.GONE);
            v = tv;
        }
        else {
            v= getCustomView(position, convertView, parent);
        }
        return v;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View mySpinner = inflater.inflate(R.layout.sinmple_text_view, parent,
                false);

        TextView spinnerTxtView= mySpinner.findViewById(android.R.id.text1);
        try {
            spinnerTxtView.setText(objects.get(position));
//            Log.i("CustomSpinerAdapter", "CustomSpinerAdapter" + objects.get(position));
//        DisplayMetrics metrics = parent.getResources().getDisplayMetrics();
//        float dp = 5f;
//        float fpixels = metrics.density * dp;
//        int pixels = (int) (fpixels + 0.5f);
//
//        spinnerTxtView.setHeight(pixels);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return mySpinner;
    }


}
package com.purplepath.purplepath.customview;

import android.content.Context;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.github.mikephil.charting.components2.MarkerView;
import com.github.mikephil.charting.data2.CandleEntry;
import com.github.mikephil.charting.data2.Entry;
import com.github.mikephil.charting.highlight2.Highlight;
import com.github.mikephil.charting.utils2.MPPointF;
import com.github.mikephil.charting.utils2.Utils;

/**
 * Created by dinesh on 12/03/18.
 */

public class MyMarkerView extends MarkerView {

    private TextView tvContent;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = findViewById(R.id.tvContent);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {

            tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true));
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
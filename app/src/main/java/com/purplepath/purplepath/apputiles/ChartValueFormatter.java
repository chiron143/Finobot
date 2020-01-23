package com.purplepath.purplepath.apputiles;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by bertrandrussellsakthees on 23/05/17.
 */



public class ChartValueFormatter implements com.github.mikephil.charting.formatter.ValueFormatter , YAxisValueFormatter {

    private DecimalFormat mFormat;

    public ChartValueFormatter() {
        mFormat = new DecimalFormat("#,##,###");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mFormat.format(value);
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
//        //Log.e("ChartValueFormatter", "getFormattedValue" + value);
        String decimalvalueintext =  UtileKit.currToCharConversionabsolute(value);
//        //Log.e("ChartValueFormatter", "getFormattedValue" + decimalvalueintext);
//            return mFormat.format(Math.abs(value))+ "cr" ;
        return decimalvalueintext;
    }
}


/*
package com.purplepath.purplepath.emergencyfundAnalysis;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.numetriclabz.numandroidcharts.ChartData;
import com.numetriclabz.numandroidcharts.WaterFallChart;
import com.finobot.finobot.R;
import com.purplepath.purplepath.emergencyfundAnalysis.model.EmergencyFundModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by dinesh on 14/10/16.
 *//*

public class EmergencyGooglechartFragment extends BaseFragment {
    CheckBox ef_planCheckBox,ef_actCheckBox;
    WaterFallChart waterFallChart,waterFallChart2;
    List<ChartData> value1 = new ArrayList();
    List<ChartData> value2 = new ArrayList ();
    EmergencyFundModel emergencyFundModel;
    Toolbar toolbar;
    private Context mContext;
    private OnActivityBackPressedListener mCallBackListener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
            value1= (List<ChartData>) getArguments().getSerializable("chart1");
        value2= (List<ChartData>) getArguments().getSerializable("chart2");
        mContext = getContext();
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mCallBackListener.setActionBarTitle("Emergency Fund");
        View emergencyFundView =  inflater.inflate(R.layout.fragment_water_fall_layout, container, false);
        waterFallChart = (WaterFallChart)emergencyFundView. findViewById(R.id.waterfallView1Id);
        waterFallChart2 = (WaterFallChart)emergencyFundView. findViewById(R.id.waterfallView2Id);
        ef_planCheckBox=(CheckBox) emergencyFundView.findViewById(R.id.ef_planId);
        ef_actCheckBox=(CheckBox) emergencyFundView.findViewById(R.id.ef_actId);
        ef_planCheckBox.setVisibility(View.GONE);
        ef_actCheckBox.setVisibility(View.GONE);

//        ef_actCheckBox.setOnCheckedChangeListener(this);
//        ef_planCheckBox.setOnCheckedChangeListener(this);
//        ef_planCheckBox.setChecked(true);

//        value1.add(new ChartData(12f, "plan_savings_acc_per"));
//        value1.add(new ChartData(6f,"plan_curr_acc_per"));
//        value1.add(new ChartData(16f,"plan_curr_acc_per"));
//        value1.add(new ChartData(24f, "plan_fix_recc_dep_per"));
        try {
            waterFallChart.setData(value1);
//        value2.add(new ChartData((float)0, "act_overall_per"));
            waterFallChart2.setData(value2);
        }catch ( NullPointerException e)
        {

        }
        catch (Exception e)
        {}
        //                + "    <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
//                + "   <script>"
//                + "  $(document).ready(function() {"
//                + "  $(\".char_style\").css('width',"+width+");"
//                + "   });"
//                + "    </script>"
//                + "    <script type=\"text/javascript\">"
//                + "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
//                + "      google.setOnLoadCallback(drawChart);"
//                + "      function drawChart() {"
//                + "        var data = google.visualization.arrayToDataTable(["
//                + "          ['Year', 'Sales', 'Expenses'],"
//                + "          ['2010',  1000,      400],"
//                + "          ['2011',  1170,      460],"
//                + "          ['2012',  660,       1120],"
//                + "          ['2013',  1030,      540]"
//                + "        ]);"
//                + "        var options = {"
//                + "          title: 'Truiton Performance',"
//                + "          hAxis: {title: 'Year', titleTextStyle: {color: 'red'}}"
//                + "        };"
//                + "        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));"
//                + "        chart.draw(data, options);"
//                + "      }"
//                + "    </script>"
//                + "  </head>"
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        WebView webview = (WebView) emergencyFundView.findViewById(R.id.webView1);
        String content = "<html>"
                + "  <head>"
                + " <style>"
                + "  text{ font-size:30px; }"
                + "   </style>"
                + "    <script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>"
                + "   <script>"
                + "  $(document).ready(function() {"
                + "  $(\".char_style\").css('width',"+width+");"
                + "   });"
                + "    </script>"
                + " <script type=\"text/javascript\">"
                + " google.charts.load('current', {'packages':['corechart']});"
                + " google.charts.setOnLoadCallback(drawChart);"
                + " function drawChart() {"
                + "  var data = google.visualization.arrayToDataTable(["
                + " ['Mon', 0,0, 100, 100],"
                + " ['Tue', 55, 55, 75, 75],"
                + " ['Wed', 55, 55, 37, 37],"
                + " ['Thu', 37, 37, 46, 46],"
                + " ['Fri', 46, 46, 02, 02]"
                + " ], true);"

                + "  var options = {"
                + "     legend: 'none',"
                + "    bar: { groupWidth: '100%' }, "
                + "   candlestick: {"
                + "       fallingColor: { strokeWidth: 0, fill: '#a52714' }, "
                + "      risingColor: { strokeWidth: 0, fill: '#0f9d58' }   "
                + "  }"
                + " };"

                + "  var chart = new google.visualization.CandlestickChart(document.getElementById('chart_div'));"
                + "  chart.draw(data, options);"
                + " }"
                + " </script>"
                + " </head>"
                + "  <body>"
                + "    <div class=\"test\"></div>"
                + "  <div id=\"chart_div\" style=\"width: "+width+"px; height:"+height+"px;\"</div>"
                + "  </body>" + "</html>";

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.requestFocusFromTouch();
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);
        webview.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        webview.loadDataWithBaseURL( "file:///android_asset/", content, "text/html", "utf-8", null );
        //webview.loadUrl("file:///android_asset/Code.html"); // Can be used in this way too.
        return emergencyFundView;
    }

}
*/

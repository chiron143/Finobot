package com.purplepath.purplepath.desiproAllModules.amortizationSchedule;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.mikephil.charting.charts2.LineChart;
import com.github.mikephil.charting.components2.Legend;
import com.github.mikephil.charting.components2.XAxis;
import com.github.mikephil.charting.data2.Entry;
import com.github.mikephil.charting.data2.LineData;
import com.github.mikephil.charting.data2.LineDataSet;
import com.github.mikephil.charting.formatter2.IValueFormatter;
import com.github.mikephil.charting.interfaces2.datasets.ILineDataSet;
import com.github.mikephil.charting.utils2.ColorTemplate;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models.AmortizationScheduleModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by Pratheep.S on 01-06-2017.
 */
public class AmortizationGraph extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.lineChart)
    LineChart mChart;

    @BindView(R.id.noChartData)
    TextView noChartData_txt;

    @BindView(R.id.legendLayout1)
    LinearLayout legendLayout1;

    @BindView(R.id.legendLayout2)
    LinearLayout legendLayout2;


    @BindView(R.id.lineChart2)
    LineChart mChart2;

    @BindView(R.id.lineChart4)
    LineChart mChart4;

    @BindView(R.id.legendLayout4)
    LinearLayout legendLayout4;

    Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private int[] mColors = new int[] {
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };

    private AmortizationScheduleModel amort_model;
    private OnActivityBackPressedListener mCallBackListener;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.networth_summary_menu,menu);
        menu.findItem(R.id.menu_graph).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_details:
                ((OnActivityBackPressedListener)mContext).onActivityBackPressed();
                break;
            case android.R.id.home:
               // ((OnActivityBackPressedListener)mContext).onActivityBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public static AmortizationGraph newInstance(AmortizationScheduleModel amort_model) {
        
        Bundle args = new Bundle();
        args.putSerializable("amort_model",amort_model);
        AmortizationGraph fragment = new AmortizationGraph();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            setHasOptionsMenu(true);
            mContext=getContext();
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_amortization_graph, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);

        mCallBackListener.setActionBarTitle("DeciPro - Amortization Schedule");

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        Bundle args=getArguments();
        if(args!=null) {
            if (args.containsKey("amort_model")) {
                try {
                    amort_model = (AmortizationScheduleModel) args.getSerializable("amort_model");
                    if (amort_model.getStatus_code().equals(UtileKit.SUCCESSCODE) && amort_model.getData().getAmtz_sch().size() > 0) {
                        initializeGraph_1();
                        initializeGraph_2();
                        intializeGraph_4();

                    } else {
                        showNoChartData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showNoChartData();
            }
        }
        return view;
    }



    private void showNoChartData() {
        noChartData_txt.setVisibility(View.VISIBLE);
        mChart.setVisibility(View.GONE);
    }

    private void initializeGraph_1() {

        mChart.setDrawGridBackground(false);
        //mChart.getDescription().setEnabled(false);
        mChart.setDrawBorders(false);

        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisRight().setDrawAxisLine(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        //mChart.getXAxis().setDrawAxisLine(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> values = new ArrayList<Entry>();

        int size=amort_model.getData().getAmtz_sch().size();
        for(int i=0;i<size;i++){
            String tenusrevalue = UtileKit.currToCharConversionabsolute(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getTenure()));
            String loan_interest = UtileKit.currToCharConversionabsolute(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getLoan_interest()));

            Log.i("Amortization Graph"," initializeGraph_1 tenusrevalue "+ tenusrevalue);
            Log.i("Amortization Graph"," initializeGraph_1 loan_interest "+ loan_interest);

            values.add(new Entry(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getTenure()),Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getLoan_interest())));
        }
        LineDataSet d = new LineDataSet(values, "Interest");
        d.setLineWidth(2.5f);
        d.setCircleRadius(.5f);
        //int color = mColors[1 % mColors.length];
        int color= ColorTemplate.rgb("#FFC107");
        d.setDrawValues(false);
        d.setColor(color);
        d.setCircleColor(color);
        d.setValueFormatter(new MyValueFormatter());
        dataSets.add(d);

        ArrayList<Entry> values2 = new ArrayList<Entry>();
        for(int i=0;i<size;i++){
            values2.add(new Entry(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getTenure()),Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getLoan_principal())));
        }

        LineDataSet d2 = new LineDataSet(values2, "Principal");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(.5f);
        d2.setDrawValues(false);
        //int color = mColors[1 % mColors.length];
        int color2= ColorTemplate.rgb("#53adfc");
        d2.setColor(color2);
        d2.setCircleColor(color2);
        d2.setValueFormatter(new MyValueFormatter());
        dataSets.add(d2);
        int colors[]={color,color2};
        ArrayList<String> elements=new ArrayList<>(Arrays.asList("Interest","Principal"));
        showLegend(elements,colors,legendLayout1);

        // make the first DataSet dashed
      //  ((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);
       // ((LineDataSet) dataSets.get(0)).setColors(ColorTemplate.VORDIPLOM_COLORS);
        //((LineDataSet) dataSets.get(0)).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);
        Legend legend=mChart.getLegend();
        legend.setEnabled(false);

        LineData data = new LineData(dataSets);
        mChart.setDescription(null);
        mChart.setData(data);
        mChart.invalidate();
    }

    private void initializeGraph_2() {

        mChart2.setDrawGridBackground(false);
        //mChart.getDescription().setEnabled(false);
        mChart2.setDrawBorders(false);

        mChart2.getAxisRight().setEnabled(false);
        mChart2.getAxisRight().setDrawAxisLine(false);
        mChart2.getAxisRight().setDrawGridLines(false);
        mChart2.getAxisLeft().setDrawGridLines(false);
        //mChart.getXAxis().setDrawAxisLine(false);
        mChart2.getXAxis().setDrawGridLines(false);
        mChart2.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        // enable touch gestures
        mChart2.setTouchEnabled(true);

        // enable scaling and dragging
        mChart2.setDragEnabled(true);
        mChart2.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart2.setPinchZoom(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> values = new ArrayList<Entry>();

        int size=amort_model.getData().getAmtz_sch().size();
        for(int i=0;i<size;i++){
            values.add(new Entry(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getTenure()),Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getOut_bal_end())));
        }
        LineDataSet d = new LineDataSet(values, "Balance");
        d.setLineWidth(2.5f);
        d.setCircleRadius(.5f);
        //int color = mColors[1 % mColors.length];
        int color= ColorTemplate.rgb("#FFC107");
        d.setDrawValues(false);
        d.setColor(color);
        d.setCircleColor(color);
        d.setValueFormatter(new MyValueFormatter());
        dataSets.add(d);

        ArrayList<Entry> values2 = new ArrayList<Entry>();
        for(int i=0;i<size;i++){
            values2.add(new Entry(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getTenure()),Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getCum_payment())));
        }
        LineDataSet d2 = new LineDataSet(values2, "Payment");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(.5f);
        d2.setDrawValues(false);
        //int color = mColors[1 % mColors.length];
        int color2= ColorTemplate.rgb("#53adfc");
        d2.setColor(color2);
        d2.setCircleColor(color2);
        d2.setValueFormatter(new MyValueFormatter());
        dataSets.add(d2);

        //ArrayList<Integer> colors= Arrays.asList({(Integer)color,(Integer)color2});
         int colors[]={color,color2};
        ArrayList<String> elements=new ArrayList<>(Arrays.asList("Balance","Payment"));
        showLegend(elements,colors,legendLayout2);
        // make the first DataSet dashed
        //  ((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);
        // ((LineDataSet) dataSets.get(0)).setColors(ColorTemplate.VORDIPLOM_COLORS);
        //((LineDataSet) dataSets.get(0)).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);

        Legend legend=mChart2.getLegend();
        legend.setEnabled(false);

        LineData data = new LineData(dataSets);
        mChart2.setDescription(null);
        mChart2.setData(data);
        mChart2.invalidate();
    }


    private void intializeGraph_4() {

        mChart4.setDrawGridBackground(false);
        mChart4.setDrawBorders(false);

        mChart4.getAxisRight().setEnabled(false);
        mChart4.getAxisRight().setDrawAxisLine(false);
        mChart4.getAxisRight().setDrawGridLines(false);
        mChart4.getAxisLeft().setDrawGridLines(false);
        //mChart.getXAxis().setDrawAxisLine(false);
        mChart4.getXAxis().setDrawGridLines(false);
        mChart4.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        // enable touch gestures
        mChart4.setTouchEnabled(true);

        // enable scaling and dragging
        mChart4.setDragEnabled(true);
        mChart4.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart4.setPinchZoom(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> values = new ArrayList<Entry>();

        int size=amort_model.getData().getAmtz_sch().size();
        for(int i=0;i<size;i++){
            values.add(new Entry(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getTenure()),Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getOut_bal_end())));
        }
        LineDataSet d = new LineDataSet(values, "Outstanding Balance");
        d.setLineWidth(2.5f);
        d.setCircleRadius(.5f);
        //int color = mColors[1 % mColors.length];
        int color= ColorTemplate.rgb("#FFC107");
        d.setDrawValues(false);
        d.setColor(color);
        d.setValueFormatter(new MyValueFormatter());
        d.setCircleColor(color);
        dataSets.add(d);

        ArrayList<Entry> values2 = new ArrayList<Entry>();
        for(int i=0;i<size;i++){
            values2.add(new Entry(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getTenure()),Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getCum_principal())));
        }
        LineDataSet d2 = new LineDataSet(values2, "Cummulative Principal");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(.5f);
        d2.setDrawValues(false);
        //int color = mColors[1 % mColors.length];
        int color2= ColorTemplate.rgb("#53adfc");
        d2.setColor(color2);
        d2.setCircleColor(color2);
        d2.setValueFormatter(new MyValueFormatter());
        dataSets.add(d2);


        ArrayList<Entry> values3 = new ArrayList<Entry>();
        for(int i=0;i<size;i++){
            values3.add(new Entry(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getTenure()),Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getCum_interest())));
        }
        LineDataSet d3 = new LineDataSet(values3, "Cummulative Interest");
        d3.setLineWidth(2.5f);
        d3.setCircleRadius(.5f);
        d3.setDrawValues(false);
        //int color = mColors[1 % mColors.length];
        int color3= ColorTemplate.rgb("#c3cc2f");
        d3.setColor(color3);
        d3.setCircleColor(color3);
        d3.setValueFormatter(new MyValueFormatter());
        dataSets.add(d3);


        //ArrayList<Integer> colors= Arrays.asList({(Integer)color,(Integer)color2});
        int colors[]={color,color2,color3};
        ArrayList<String> elements=new ArrayList<>(Arrays.asList("Outstanding Balance","Cummulative Principal","Cummulative Interest"));
        showLegend(elements,colors,legendLayout4);
        // make the first DataSet dashed
        //  ((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);
        // ((LineDataSet) dataSets.get(0)).setColors(ColorTemplate.VORDIPLOM_COLORS);
        //((LineDataSet) dataSets.get(0)).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);

        Legend legend=mChart4.getLegend();
        legend.setEnabled(false);

        LineData data = new LineData(dataSets);

        mChart4.setDescription(null);
        mChart4.setData(data);
        mChart4.invalidate();

    }


    private void showLegend(ArrayList<String> elements,int[] colors,LinearLayout legendLayout) {
        for (int i = 0; i < elements.size(); i++) {

            LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout parent_layout = new LinearLayout(mContext);
            parent_layout.setWeightSum(2);
            if(colors.length==3) {
                parent_layout.setOrientation(LinearLayout.VERTICAL);
            }else{
                parent_layout.setOrientation(LinearLayout.HORIZONTAL);
            }
            parent_param_layout.setMargins(10, 0, 0, 10);
            parent_layout.setLayoutParams(parent_param_layout);

            LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_left_layout.weight = 1F;
            LinearLayout left_layout = new LinearLayout(mContext);
            left_layout.setOrientation(LinearLayout.HORIZONTAL);
            left_layout.setGravity(Gravity.LEFT);
            left_layout.setLayoutParams(parms_left_layout);

            LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(45, 45);
            parms_legen_layout.setMargins(20, 20, 20, 20);
            LinearLayout legend_layout = new LinearLayout(mContext);
            legend_layout.setLayoutParams(parms_legen_layout);
            legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            legend_layout.setBackgroundColor(colors[i]);
            left_layout.addView(legend_layout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            TextView txt_unit = new TextView(mContext);
            txt_unit.setMaxLines(2);
            // params.setMargins(0, 0, 20, 0);

            txt_unit.setLayoutParams(params);
            txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
            txt_unit.setText( elements.get(i));
            left_layout.addView(txt_unit);
            i++;
            if ( (elements.size()) == i) {
                parent_layout.addView(left_layout);
                legendLayout.addView(parent_layout);

                break;
            }

            LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_right_layout.weight = 1F;
            LinearLayout right_layout = new LinearLayout(mContext);
            right_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_layout.setGravity(Gravity.LEFT);
            parent_param_layout.setMargins(10, 0, 0, 10);
            right_layout.setLayoutParams(parms_right_layout);


            LinearLayout.LayoutParams parms_rightlegend_layout = new LinearLayout.LayoutParams(45, 45);
            parms_rightlegend_layout.setMargins(20, 20, 20, 20);
            LinearLayout right_legend_layout = new LinearLayout(mContext);
            right_legend_layout.setLayoutParams(parms_rightlegend_layout);
            right_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_legend_layout.setBackgroundColor(colors[i]);
            right_layout.addView(right_legend_layout);
            LinearLayout.LayoutParams right_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            TextView right_txt_unit = new TextView(mContext);
            right_txt_unit.setMaxLines(2);
            //right_params.setMargins(0, 0, 20, 0);
            right_txt_unit.setLayoutParams(right_params);
            right_txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
            right_txt_unit.setText( elements.get(i));
            right_layout.addView(right_txt_unit);

            parent_layout.addView(right_layout);
            parent_layout.addView(left_layout);
            legendLayout.addView(parent_layout);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;

            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                startActivity(i);
                break;
        }

    }

    private class MyValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("#,##,##0.0"); // use one decimal
        }
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, com.github.mikephil.charting.utils2.ViewPortHandler viewPortHandler) {
            //Log.e("ChartValueFormatter", "getFormattedValue" + value);
            String decimalvalueintext =  UtileKit.currToCharConversionabsolute(value);
            //Log.e("ChartValueFormatter", "getFormattedValue" + decimalvalueintext);
//            return mFormat.format(Math.abs(value))+ "cr" ;
            return decimalvalueintext;
        }

    }
}

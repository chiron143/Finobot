package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models.CarVsLeaseModel;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models.BuyVsRentModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Suresh on 23/01/18.
 */

public class GraphCarBuyVsLeaseFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.lineChart)
    LineChart mChart;

    @Bind(R.id.legendLayout)
    LinearLayout legendLayout;

    Bundle args;
    CarVsLeaseModel carVsLeaseModel;

    int showAllGraphs;

    public GraphCarBuyVsLeaseFragment() {
        // Required empty public constructor
    }

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    private Context mContext;

    public static GraphCarBuyVsLeaseFragment newInstance(CarVsLeaseModel carVsLeaseModel, int showAllGraphs) {

        Bundle args = new Bundle();
        args.putSerializable("carVsLeaseModel",carVsLeaseModel);
        args.putInt("showAllGraphs",showAllGraphs);
        GraphCarBuyVsLeaseFragment fragment = new GraphCarBuyVsLeaseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
            setHasOptionsMenu(true);

        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        /*inflater.inflate(R.menu.networth_summary_menu,menu);
        menu.findItem(R.id.menu_graph).setVisible(false);*/
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_detail,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            //case R.id.menu_details:
            case R.id.menu_detail:
                // ((ActivityMethodsInterface)mContext).onActivityBackPressed();
                //addFragment(DetailsBuyVsRentFragment.newInstance(buyVsRentModel));
                addFragmenttoStack(DetailsCarBuyVsLeaseFragment.newInstance(carVsLeaseModel));
                break;
            case R.id.menu_summary:
                //addFragmentToActivity(ResultBuyVsRentFragment.newInstance(buyVsRentModel));
                addFragmenttoStack(DetailsCarBuyVsLeaseFragment.newInstance(carVsLeaseModel));

                break;
            /*case android.R.id.home:
                // ((OnActivityBackPressedListener)mContext).onActivityBackPressed();
                break;*/

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_graph_car_buy_vs_rent, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        args=getArguments();
        mContext=getContext();

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setVisibility(View.GONE);

        if(null!=args){
            if(args.containsKey("showAllGraphs")) showAllGraphs=args.getInt("showAllGraphs");

            if(args.containsKey("carVsLeaseModel")){
                carVsLeaseModel= (CarVsLeaseModel) args.getSerializable("carVsLeaseModel");
                if(carVsLeaseModel.getStatus_code().equals(UtileKit.SUCCESSCODE)&&carVsLeaseModel.getData()!=null){
                    initializeGraph();
                }
            }

        }


        return view;
    }

    private void initializeGraph() {
        int color = ColorTemplate.rgb("#FFC107");
        int color2 = ColorTemplate.rgb("#53adfc");
        int color3=ColorTemplate.rgb("#900C3F");
        boolean flag=true;

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
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        ArrayList<Entry> values = new ArrayList<Entry>();

        if(carVsLeaseModel.getData().getCar_lease()!=null) {
            int size = carVsLeaseModel.getData().getCar_lease().getCashflow().size();
            for (int i = 0; i < size; i++) {
                /*String tenusrevalue = UtileKit.currToCharConversionabsolute(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getTenure()));
                String loan_interest = UtileKit.currToCharConversionabsolute(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getLoan_interest()));

                Log.i("Amortization Graph", " initializeGraph_1 tenusrevalue " + tenusrevalue);
                Log.i("Amortization Graph", " initializeGraph_1 loan_interest " + loan_interest);*/
                values.add(new Entry(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().getCashflow().get(i).getTenure()),
                        Float.parseFloat(carVsLeaseModel.getData().getCar_lease().getCashflow().get(i).getYearly_lease_amt())));
            }
            LineDataSet d = new LineDataSet(values, "Lease");
            d.setLineWidth(2.5f);
            d.setCircleRadius(.5f);
            //int color = mColors[1 % mColors.length];

            d.setDrawValues(false);
            d.setColor(color);
            d.setCircleColor(color);
            d.setValueFormatter(new MyValueFormatter());
            dataSets.add(d);
        }

        if(carVsLeaseModel.getData().getCar_cash()!=null) {
            int size = carVsLeaseModel.getData().getCar_cash().getCashflow().size();
            ArrayList<Entry> values2 = new ArrayList<Entry>();
            for (int i = 1; i < size; i++) {
                values2.add(new Entry(Float.parseFloat( carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getTenure()),
                        Float.parseFloat(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getTot_cost_buying_cash())));
            }

            LineDataSet d2 = new LineDataSet(values2, "Buy-Cash");
            d2.setLineWidth(2.5f);
            d2.setCircleRadius(.5f);
            d2.setDrawValues(false);
            //int color = mColors[1 % mColors.length];

            d2.setColor(color2);
            d2.setCircleColor(color2);
            d2.setValueFormatter(new MyValueFormatter());
            dataSets.add(d2);
        }

        if(carVsLeaseModel.getData().getCar_loan()!=null) {
            int size = carVsLeaseModel.getData().getCar_loan().getCashflow().size();
            ArrayList<Entry> values2 = new ArrayList<Entry>();
            for (int i = 1; i < size; i++) {
                values2.add(new Entry(Float.parseFloat( carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getTenure()),
                        Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getTot_cost_buying_loan())));
            }

            LineDataSet d3 = new LineDataSet(values2, "Buy-CarLoan");
            d3.setLineWidth(2.5f);
            d3.setCircleRadius(.5f);
            d3.setDrawValues(false);
            //int color = mColors[1 % mColors.length];

            d3.setColor(color3);
            d3.setCircleColor(color3);
            d3.setValueFormatter(new MyValueFormatter());
            dataSets.add(d3);
        }else{
            int colors[]={color,color2};
            ArrayList<String> elements=new ArrayList<>(Arrays.asList("Rent","Buy-Cash"));
            showLegend(elements,colors,legendLayout);
            flag=false;
        }
        if(flag){
            int colors[]={color,color2,color3};
            ArrayList<String> elements=new ArrayList<>(Arrays.asList("Rent","Buy-Cash","Buy-CarLoan"));
            showLegend(elements,colors,legendLayout);
        }

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


    private class MyValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("#,##,##0.0"); // use one decimal
        }
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, com.github.mikephil.charting.utils2.ViewPortHandler viewPortHandler) {

            String decimalvalueintext =  UtileKit.currToCharConversionabsolute(value);

            return decimalvalueintext;
        }

    }

    private void addFragmentToActivity(Fragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();

    }
    private void addFragment(Fragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
        //ft.addToBackStack(null);
        ft.commitAllowingStateLoss();

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_left_arrow: {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home: {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            break;
        }
    }

}
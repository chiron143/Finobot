package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent;


import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.purplepath.purplepath.apputiles.ChartValueFormatter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.interfaces.ActivityMethodsInterface;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models.BuyVsRentModel;
import com.purplepath.purplepath.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Pratheep.S
 */
public class ResultBuyVsRentFragment extends BaseFragment {

    ActivityMethodsInterface methodsInterface;
    public ResultBuyVsRentFragment() {
        // Required empty public constructor
    }
    private BuyVsRentModel buyVsRentModel;
    private Bundle args;

    @BindView(R.id.buyUsingFinanceValue)
    TextView buyUsingFinanceValue;

    @BindView(R.id.buyUsingCash)
    TextView buyUsingCash;

    @BindView(R.id.costOfRentingValue)
    TextView costOfRentingValue;

    @BindView(R.id.barChart)
    BarChart barChart;

    ArrayList<String> labels;
    double rent=0,cash=0,loan=0;
    private Typeface tf;

    public static ResultBuyVsRentFragment newInstance(BuyVsRentModel buyVsRentModel) {

        Bundle args = new Bundle();
        args.putSerializable("buyVsRentModel",buyVsRentModel);
        ResultBuyVsRentFragment fragment = new ResultBuyVsRentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String TAG="spcheck";

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_detail_and_chart,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_detail:
                //addFragmentToActivity(DetailsBuyVsRentFragment.newInstance(buyVsRentModel));
                addFragmenttoStack(DetailsBuyVsRentFragment.newInstance(buyVsRentModel));
                break;

            case R.id.menu_chart:
                //addFragmentToActivity(GraphBuyVsRentFragment.newInstance(buyVsRentModel,1));
                addFragmenttoStack(GraphBuyVsRentFragment.newInstance(buyVsRentModel,1));
               break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_result_house_buy_vs_rent, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this,view);
        labels= new ArrayList<>(Arrays.asList("Renting Cost","Buy - Cash","Buy - Loan"));


        args=getArguments();
        if(null!=args){
            if(args.containsKey("buyVsRentModel")){
                buyVsRentModel= (BuyVsRentModel) args.getSerializable("buyVsRentModel");
                setValuesFromModel();
            }
        }




        return view;
    }

    private void setValuesFromModel() {
        if(buyVsRentModel!=null&& buyVsRentModel.getData()!=null) {
            if (null != buyVsRentModel.getData().getHome_rent()) {
                rent = Double.parseDouble(buyVsRentModel.getData().getHome_rent().getOverall_renting_cost().toString());
                costOfRentingValue.setText("₹ " + UtileKit.formatedNumber(rent));
                Log.i(TAG, "setValuesFromModel:costOfRentingValue=" + buyVsRentModel.getData().getHome_rent().getOverall_renting_cost());
            }

            if (null != buyVsRentModel.getData().getHome_cash()) {
                cash = Double.parseDouble(buyVsRentModel.getData().getHome_cash().getTot_net_cost_of_buying().toString());
                buyUsingCash.setText("₹ " + UtileKit.formatedNumber(cash));
                Log.i(TAG, "setValuesFromModel: buyUsingCash=" + buyVsRentModel.getData().getHome_cash().getTot_net_cost_of_buying());
            }

            if (null != buyVsRentModel.getData().getHome_loan()) {
                loan = Double.parseDouble(buyVsRentModel.getData().getHome_loan().getTot_net_annu_cost_of_buying().toString());
                buyUsingFinanceValue.setText("₹ " + UtileKit.formatedNumber(loan));
                Log.i(TAG, "setValuesFromModel: buyUsingFinanceValue=" + buyVsRentModel.getData().getHome_loan().getTot_net_annu_cost_of_buying());
            }
            showChart();
            
        }
    }

    private void showChart() {
        int [] colour=new int[]{ColorTemplate.rgb("#53adfc"),ColorTemplate.rgb("#53fcf3"),ColorTemplate.rgb("#fc8553")};

        chartSetUp();
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry((float) rent,0));
        entries.add(new BarEntry((float) cash,1));
        entries.add(new BarEntry((float) loan,2));

        BarDataSet dataSet=new BarDataSet(entries,"rent");
        dataSet.setValueFormatter(new ChartValueFormatter());
        dataSet.setColors(colour);

        BarData data=new BarData(labels,dataSet);
        barChart.setScaleEnabled(false);
        barChart.setExtraOffsets(0,0,0,10);
        barChart.setData(data);
        barChart.setDescription("");
        barChart.invalidate();

    }

   /* private void showChart() {
        chartSetUp();
        List<BarEntry> entries1 = new ArrayList<>();
        List<BarEntry> entries2 = new ArrayList<>();
        List<BarEntry> entries3 = new ArrayList<>();
        entries1.add(new BarEntry(rent,0));
        entries2.add(new BarEntry(cash,1));
        entries3.add(new BarEntry(loan,2));

        IBarDataSet dataSet1=new BarDataSet(entries1,"Rent");
        IBarDataSet dataSet2=new BarDataSet(entries2,"Cash");
        IBarDataSet dataSet3=new BarDataSet(entries3,"Loan");
        ArrayList<IBarDataSet> dataSets=new ArrayList<>();
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);
        dataSets.add(dataSet3);

        BarData data=new BarData(labels,dataSets);
        barChart.setData(data);
        barChart.setDescription("");
        barChart.invalidate();


    }*/

    private void chartSetUp() {
        LargeValueFormatter custom = new LargeValueFormatter();
        tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);

        YAxis yl = barChart.getAxisRight();
        yl.setTypeface(tf);
        yl.setDrawLabels(false);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.1f);
        yl.setValueFormatter(new ChartValueFormatter());

//        yl.setAxisMinValue(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);
        yl.setStartAtZero(false);
//        yl.setValueFormatter(custom);//this value is to display in thousands in to "k"
        YAxis yr =barChart.getAxisLeft();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setStartAtZero(false);
        yr.setValueFormatter(new ChartValueFormatter());


        Legend l = barChart.getLegend();
        l.setEnabled(false);
        l.setTextSize(8 * getResources().getDisplayMetrics().density);
        l.setFormSize(15f);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(15f);

    }

    private void addFragmentToActivity(Fragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();

    }

}

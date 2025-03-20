package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
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
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models.CarVsLeaseModel;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.interfaces.ActivityMethodsInterface;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models.BuyVsRentModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suresh on 23/01/18.
 */

public class ResultCarBuyVsRentFragment extends BaseFragment implements View.OnClickListener{

    ActivityMethodsInterface methodsInterface;
    public ResultCarBuyVsRentFragment() {
        // Required empty public constructor
    }
    private CarVsLeaseModel carVsLeaseModel;
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

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    private Context mContext;

    public static ResultCarBuyVsRentFragment newInstance(CarVsLeaseModel carVsLeaseModel) {

        Bundle args = new Bundle();
        args.putSerializable("carVsLeaseModel",carVsLeaseModel);
        ResultCarBuyVsRentFragment fragment = new ResultCarBuyVsRentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String TAG="spcheck";


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
        menu.clear();
        inflater.inflate(R.menu.menu_detail_and_chart,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_detail:
                //addFragmentToActivity(DetailsBuyVsRentFragment.newInstance(buyVsRentModel));
                addFragmenttoStack(DetailsCarBuyVsLeaseFragment.newInstance(carVsLeaseModel));
                break;

            case R.id.menu_chart:
                //addFragmentToActivity(GraphBuyVsRentFragment.newInstance(buyVsRentModel,1));
                addFragmenttoStack(GraphCarBuyVsLeaseFragment.newInstance(carVsLeaseModel,1));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_result_buy_vs_rent, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this,view);


        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setVisibility(View.GONE);


        labels= new ArrayList<>(Arrays.asList("Lease Cost","Buy - Cash","Buy - Loan"));


        args=getArguments();
        if(null!=args){
            if(args.containsKey("carVsLeaseModel")){
                carVsLeaseModel= (CarVsLeaseModel) args.getSerializable("carVsLeaseModel");
                setValuesFromModel();
            }
        }




        return view;
    }

    private void setValuesFromModel() {
        if(carVsLeaseModel!=null&& carVsLeaseModel.getData()!=null) {
            if (null != carVsLeaseModel.getData().getCar_lease()) {
                rent = Double.parseDouble(carVsLeaseModel.getData().getCar_lease().getOverall_lease_value().toString());
                costOfRentingValue.setText("₹ " + UtileKit.formatedNumber(rent));
                Log.i(TAG, "setValuesFromModel:costOfRentingValue=" + carVsLeaseModel.getData().getCar_lease().getOverall_lease_value());
            }

            if (null != carVsLeaseModel.getData().getCar_cash()) {
                cash = Double.parseDouble(carVsLeaseModel.getData().getCar_cash().getOverall_cash_value().toString());
                buyUsingCash.setText("₹ " + UtileKit.formatedNumber(cash));
                Log.i(TAG, "setValuesFromModel: buyUsingCash=" + carVsLeaseModel.getData().getCar_cash().getOverall_cash_value());
            }

            if (null != carVsLeaseModel.getData().getCar_loan()) {
                loan = Double.parseDouble(carVsLeaseModel.getData().getCar_loan().getOverall_loan_value().toString());
                buyUsingFinanceValue.setText("₹ " + UtileKit.formatedNumber(loan));
                Log.i(TAG, "setValuesFromModel: buyUsingFinanceValue=" + carVsLeaseModel.getData().getCar_loan().getOverall_loan_value());
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

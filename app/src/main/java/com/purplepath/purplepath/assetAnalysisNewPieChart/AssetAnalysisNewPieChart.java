package com.purplepath.purplepath.assetAnalysisNewPieChart;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assetAnalysisNewPieChart.Model.AssetAnalysisNewModel;
import com.purplepath.purplepath.assetAnalysisNewPieChart.Model.Asst_analysis;
import com.purplepath.purplepath.assets.AssetsDetailsFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssetAnalysisNewPieChart extends BaseFragment implements View.OnClickListener {

    com.github.mikephil.charting.charts.PieChart mChart;

    private LinearLayout bottomBar,chartLayout,titleLayout;
    private RelativeLayout relative_center_home, relative_left_arrow;
    private OnActivityBackPressedListener backPressedListener;
    private AssetAnalysisNewModel assetAnalysisModel;
    private Context mContext;
    private TextView noChartData;
    private TextView errorTextview;

    private ScrollView scrollView;

    private ArrayList<Entry> entries = new ArrayList<Entry>();
    private ArrayList<Float> xValue = new ArrayList<Float>();
    private ArrayList<String> title=new ArrayList<String>();

    private int chartsize ,marginsize;
    private FloatingActionButton mEditassetFabBtn;

    public AssetAnalysisNewPieChart() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backPressedListener = (OnActivityBackPressedListener) getContext();
        mContext = getContext();
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_asset_analysis_new_pie_chart, container, false);
        bottomBar = view.findViewById(R.id.bottom_bar_layout);
        backPressedListener.setActionBarTitle("Asset Analysis");
        chartLayout= view.findViewById(R.id.chartLayout);
        titleLayout = view.findViewById(R.id.titleLayout);
        mChart = new com.github.mikephil.charting.charts.PieChart(mContext);
        scrollView= view.findViewById(R.id.scrollView);
        noChartData= view.findViewById(R.id.noChartData);
        errorTextview = view.findViewById(R.id.empty_chart_display);
        relative_left_arrow = view.findViewById(R.id.relative_left_arrow);
        relative_center_home = view.findViewById(R.id.relative_center_home);
        mEditassetFabBtn= view.findViewById(R.id.assets_fab_id);
        relative_left_arrow.setOnClickListener(this);
        relative_center_home.setOnClickListener(this);
        mEditassetFabBtn.setOnClickListener(this);
        callAssetAnalysisService();
        return view;
    }

    private void callAssetAnalysisService() {
        UtileKit.showSpinnerDialog(mContext, false);
        Call<AssetAnalysisNewModel> call = ServiceGenerator.createService(WebServiceCalls.class).callAssetAnalysisNewService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<AssetAnalysisNewModel>() {
            @Override
            public void onResponse(Call<AssetAnalysisNewModel> call, Response<AssetAnalysisNewModel> response) {
                UtileKit.dismisssSpinnerDialog();
                assetAnalysisModel = response.body();
                if (assetAnalysisModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    scrollView.setVisibility(View.VISIBLE);
                    entries = new ArrayList<Entry>();
                    xValue = new ArrayList<Float>();
                    title=new ArrayList<String>();
                    addDataToChart();
                } else {
                   // scrollView.setVisibility(View.GONE);
                    //errorTextview.setVisibility(View.VISIBLE);
                   // errorTextview.setText(HomePageActivity.errorMessageInChart);

                   // Log.d("hi","hhhhhhhh"+errorTextview);

                    //UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                }
                UtileKit.dismisssSpinnerDialog();


            }

            @Override
            public void onFailure(Call<AssetAnalysisNewModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
            }
        });
    }

    private void addDataToChart() {

        if (assetAnalysisModel.getData().getAsst_analysis() != null) {
            Asst_analysis assetAnalysis = assetAnalysisModel.getData().getAsst_analysis();
            if (assetAnalysis.getComm_gold_per().equals("0") && assetAnalysis.getEmp_ben_per().equals("0") && assetAnalysis.getEqu_per().equals("0")
                    && assetAnalysis.getFix_inc_per().equals("0") && assetAnalysis.getHou_asst_per().equals("0") && assetAnalysis.getLiq_per().equals("0")
                    && assetAnalysis.getReal_prop_per().equals("0") && assetAnalysis.getOth_asst_per().equals("0")) {
                hideGraphShowNoDataText();

            } else {
                if (!assetAnalysis.getComm_gold_per().equals("0")) {
                    xValue.add(Float.parseFloat(assetAnalysis.getComm_gold_per()));
                    title.add("Commodity Gold");
                }

                if (!assetAnalysis.getEmp_ben_per().equals("0")) {
                    xValue.add(Float.parseFloat(assetAnalysis.getEmp_ben_per()));
                    title.add("Employee Benefits");
                }
                if (!assetAnalysis.getEqu_per().equals("0")) {
                    xValue.add(Float.parseFloat(assetAnalysis.getEqu_per()));
                    title.add("Equity");
                }
                if (!assetAnalysis.getFix_inc_per().equals("0")) {
                    xValue.add(Float.parseFloat(assetAnalysis.getFix_inc_per()));
                    title.add("Fixed Income");
                }
                if (!assetAnalysis.getHou_asst_per().equals("0")) {
                    xValue.add(Float.parseFloat(assetAnalysis.getHou_asst_per()));
                    title.add("Household asset");
                }
                if (!assetAnalysis.getLiq_per().equals("0")) {
                    xValue.add(Float.parseFloat(assetAnalysis.getLiq_per()));
                    title.add("Liquid Cash");
                }
                if (!assetAnalysis.getReal_prop_per().equals("0")) {
                    xValue.add(Float.parseFloat(assetAnalysis.getReal_prop_per()));
                    title.add("Real estate property");
                }
                if (!assetAnalysis.getOth_asst_per().equals("0")) {
                    xValue.add(Float.parseFloat(assetAnalysis.getOth_asst_per()));
                    title.add("Other Assets");
                }

                for(int i=0;i<xValue.size();i++){
                    entries.add(new Entry(xValue.get(i),i));
                }

                ArrayList<Integer> colors = new ArrayList<Integer>();

//                for (int c : ColorTemplate.VORDIPLOM_COLORS)
//                    colors.add(c);
//
//                for (int c : ColorTemplate.JOYFUL_COLORS)
//                    colors.add(c);
//
//                for (int c : ColorTemplate.COLORFUL_COLORS)
//                    colors.add(c);
//
//                for (int c : ColorTemplate.LIBERTY_COLORS)
//                    colors.add(c);
//
//                for (int c : ColorTemplate.PASTEL_COLORS)
//                    colors.add(c);

                String[] colorsTxt = mContext.getResources().getStringArray(R.array.arrays_colors);
                for (int i = 0; i < colorsTxt.length; i++) {
                    int newColor = Color.parseColor(colorsTxt[i]);
                    colors.add(newColor);
                }

                PieDataSet dataSet=new PieDataSet(entries,"");
                dataSet.setColors(colors);

                PieData data=new PieData();
                data.addDataSet(dataSet);

                data.setValueFormatter(new PercentFormatter());
                data.setValueTextSize(11f);
                data.setValueTextColor(Color.BLACK);

                mChart.setData(data);



                int width = getDeviceWidth();
                double d = width*0.25;
                chartsize = (int)(d);
                width = width-chartsize;

                double margindouble = width*0.03;
                marginsize = (int)(margindouble);


                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width,width);
                //new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.gravity= Gravity.CENTER;

                mChart.setDrawHoleEnabled(false);
                mChart.setDescription("");

                mChart.invalidate();
                mChart.setLayoutParams(layoutParams);
                chartLayout.addView(mChart);


                //chart background programatically fixing with tab
                ShapeDrawable sd = new ShapeDrawable();
                sd.setShape(new RectShape());
                sd.getPaint().setColor(Color.GRAY);
                sd.getPaint().setStrokeWidth(5f);
                sd.getPaint().setStyle(Paint.Style.STROKE);
                chartLayout.setBackground(sd);


                for (int i = 0; i <  title.size(); i++) {

                    LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    LinearLayout parent_layout = new LinearLayout(mContext);
                    parent_layout.setWeightSum(2);
                    parent_layout.setOrientation(LinearLayout.HORIZONTAL);
                    parent_param_layout.setMargins(10, 0, 0, 10);
                    parent_layout.setLayoutParams(parent_param_layout);

                    LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    parms_left_layout.weight = 1F;
                    LinearLayout left_layout = new LinearLayout(mContext);
                    left_layout.setOrientation(LinearLayout.HORIZONTAL);
                     left_layout.setGravity(Gravity.LEFT);
                    left_layout.setLayoutParams(parms_left_layout);

                    LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(45, 45);
                    parms_legen_layout.setMargins(20, 10, 20, 10);
                    LinearLayout legend_layout = new LinearLayout(mContext);
                    legend_layout.setLayoutParams(parms_legen_layout);
                    legend_layout.setOrientation(LinearLayout.HORIZONTAL);
                    legend_layout.setBackgroundColor(colors.get(i));
                    left_layout.addView(legend_layout);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    TextView txt_unit = new TextView(mContext);
                    // params.setMargins(0, 0, 20, 0);

                    txt_unit.setLayoutParams(params);
                    txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                    txt_unit.setText( title.get(i));
                    left_layout.addView(txt_unit);
                    i++;
                    if ( (title.size()) == i) {
                        parent_layout.addView(left_layout);
                        titleLayout.addView(parent_layout);

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
                    parms_rightlegend_layout.setMargins(20, 10, 20, 10);
                    LinearLayout right_legend_layout = new LinearLayout(mContext);
                    right_legend_layout.setLayoutParams(parms_rightlegend_layout);
                    right_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
                    right_legend_layout.setBackgroundColor(colors.get(i));
                    right_layout.addView(right_legend_layout);
                    LinearLayout.LayoutParams right_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    TextView right_txt_unit = new TextView(mContext);
                    //right_params.setMargins(0, 0, 20, 0);
                    right_txt_unit.setLayoutParams(right_params);
                    right_txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                    right_txt_unit.setText(title.get(i));
                    right_layout.addView(right_txt_unit);

                    parent_layout.addView(right_layout);
                    parent_layout.addView(left_layout);
                    titleLayout.addView(parent_layout);
                }



            }

        }
    }

    public int getDeviceWidth(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return width;
    }


    private void hideGraphShowNoDataText() {
     scrollView.setVisibility(View.GONE);
       // noChartData.setVisibility(View.VISIBLE);
       // noChartData.setText(HomePageActivity.errorMessageInChart);

        errorTextview.setVisibility(View.VISIBLE);
        errorTextview.setText(HomePageActivity.errorMessageInChart);

        Log.d("hi","hhhhhhhh"+errorTextview);

        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_detail,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_detail);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

           case R.id.menu_detail:
                try{
                    addFragmenttoStack(new AssetAnalysisNewPieDetails());
                    // mCallBackListener.onActivityBackPressed();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:

                try{
                    addFragmenttoStack(new AssetAnalysisPieSummary());
                    //  mCallBackListener.onActivityBackPressed();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;

            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.assets_fab_id:
                addFragmenttoStack(new AssetsDetailsFragment());
                break;
        }
    }
}

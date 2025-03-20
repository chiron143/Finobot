package com.purplepath.purplepath.liabilitiesanaysis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.liabilities.LiabilitiesTabViewFragment;
import com.purplepath.purplepath.liabilitiesanaysis.model.Liab_Anaysis_Model;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiabilitiesAnaysisFragments extends BaseFragment implements View.OnClickListener{

    private RelativeLayout mainLayout;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private PieChart mChart;
    private ScrollView scrolllinearlayout;

    private Float[] yData;
    private ArrayList<String> xData;
    private Context mContext;
    private LinearLayout titleLayout, checkboxLayout;
    private  ArrayList<CheckBox>  userNameCheckBox=new ArrayList<>();
    DisplayMetrics displaymetrics = new DisplayMetrics();
    private Liab_Anaysis_Model mliab_anaysis_model;
    private int chartsize, marginsize;
    private TextView errorTextview;
    private OnActivityBackPressedListener mCallBackListener;
    private FloatingActionButton mEditFloatingBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getContext();
        try {
            // setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
        callLiabilityanaysisService();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View incomeView =  inflater.inflate(R.layout.fragment_liabilities_anaysis_fragments, container, false);
        mainLayout = incomeView.findViewById(R.id.mainLayout);
        titleLayout = incomeView.findViewById(R.id.titleLayout);
        scrolllinearlayout = incomeView.findViewById(R.id.layout_Scroll);
        errorTextview = incomeView.findViewById(R.id.empty_chart_display);
        checkboxLayout = incomeView.findViewById(R.id.checkboxLayout);
        mleftRelativeLayout = incomeView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = incomeView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = incomeView.findViewById(R.id.relative_right_arrow);
        mEditFloatingBtn= incomeView.findViewById(R.id.liab_fab_id);
        mRightRelativeLayout.setVisibility(View.GONE);
        mCallBackListener.setActionBarTitle("Liability Analysis");
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        int width = getDeviceWidth();
        double d = width*0.25;
        chartsize = (int)(d);
        width = width-chartsize;

        double margindouble = width*0.03;
        marginsize = (int)(margindouble);

        mChart = new PieChart(getActivity());
        // add pie chart to main layout
        mChart.setLayoutParams(new FrameLayout.LayoutParams(width, width));

        RelativeLayout.LayoutParams title_params = (RelativeLayout.LayoutParams)titleLayout.getLayoutParams();
        title_params.setMargins(0, marginsize, 0, marginsize);
        titleLayout.setLayoutParams(title_params);


        mainLayout.addView(mChart);
        //mainLayout.setBackgroundColor(Color.parseColor("#ffffff"));


     //chart background programatically fixing with tab
        ShapeDrawable sd = new ShapeDrawable();
        sd.setShape(new RectShape());
        sd.getPaint().setColor(Color.GRAY);
        sd.getPaint().setStrokeWidth(5f);
        sd.getPaint().setStyle(Paint.Style.STROKE);
        mainLayout.setBackground(sd);


        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setDrawSliceText(false);

        // enable hole and configure
        mChart.setDrawHoleEnabled(false);
//        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);
        mChart.setDrawSliceText(false);
        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if(e==null){
                    return;
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mEditFloatingBtn.setOnClickListener(this);
        // add data
        // customize legends
        Legend l = mChart.getLegend();
        l.setEnabled(false);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(10);
        l.setYEntrySpace(10);
        return incomeView;
    }

    public static LiabilitiesAnaysisFragments newInstance() {
        Bundle args = new Bundle();
        LiabilitiesAnaysisFragments fragment = new LiabilitiesAnaysisFragments();
        fragment.setArguments(args);
        return fragment;
    }

    private void addData( ArrayList<String> xData, Float[] yData) {
        mChart.clear();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        try {
            for (int i = 0; i < xData.size() ; i++) {
                if (yData[i] != null)
                    yVals1.add(new Entry(yData[i], i));
                else
                    yVals1.add(new Entry(0, i));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        ArrayList<String> xVals = new ArrayList<String>();
        try {
            for (int i = 0; i <  xData.size(); i++)
                xVals.add( xData.get(i));
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(0);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        for (int i = 0; i <  xData.size(); i++) {

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
            parms_legen_layout.setMargins(20, 20, 20, 20);
            LinearLayout legend_layout = new LinearLayout(mContext);
            legend_layout.setLayoutParams(parms_legen_layout);
            legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            legend_layout.setBackgroundColor(colors.get(i));
            left_layout.addView(legend_layout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            TextView txt_unit = new TextView(mContext);
           // params.setMargins(0, 0, 20, 0);
            LinearLayout.LayoutParams txtLeft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            txt_unit.setLayoutParams(txtLeft_params);
            txt_unit.setLayoutParams(params);
            txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
            txt_unit.setText( xData.get(i));
            left_layout.addView(txt_unit);
            i++;
            if ( (xData.size()) == i) {
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
            parms_rightlegend_layout.setMargins(20, 20, 20, 20);
            LinearLayout right_legend_layout = new LinearLayout(mContext);
            right_legend_layout.setLayoutParams(parms_rightlegend_layout);
            right_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_legend_layout.setBackgroundColor(colors.get(i));
            right_layout.addView(right_legend_layout);
            LinearLayout.LayoutParams right_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            TextView right_txt_unit = new TextView(mContext);
          //  right_params.setMargins(0, 0, 20, 0);
            LinearLayout.LayoutParams txtRight_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            right_txt_unit.setLayoutParams(txtRight_params);
            right_txt_unit.setLayoutParams(right_params);
            right_txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
            right_txt_unit.setText( xData.get(i));
            right_layout.addView(right_txt_unit);

            parent_layout.addView(right_layout);
            parent_layout.addView(left_layout);
            titleLayout.addView(parent_layout);
        }
        mChart.setData(data);
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();

    }



    public void callLiabilityanaysisService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext,false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Liab_Anaysis_Model> call = webServiceObj.callLiabilityanaysisService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Liab_Anaysis_Model>() {
            @Override
            public void onResponse(Call<Liab_Anaysis_Model> call, Response<Liab_Anaysis_Model> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success",""+response.body());
                mliab_anaysis_model = response.body();
//                ArrayList<Float> chartdatalist = new ArrayList<Float>();
//                ArrayList<String> chartitledatalist = new ArrayList<String>();

                if (mliab_anaysis_model.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    scrolllinearlayout.setVisibility(View.VISIBLE);
                    if(null!=mliab_anaysis_model.getData().getLiab_analysis())
                    {
                        addSingleUserChart(mliab_anaysis_model);
                    }else{
                        scrolllinearlayout.setVisibility(View.GONE);
                        errorTextview.setVisibility(View.VISIBLE);
                        errorTextview.setText(HomePageActivity.errorMessageInChart);
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                }else{
                    scrolllinearlayout.setVisibility(View.GONE);
                    errorTextview.setVisibility(View.VISIBLE);
                    errorTextview.setText(HomePageActivity.errorMessageInChart);
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }

                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<Liab_Anaysis_Model> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }




    private void addSingleUserChart(Liab_Anaysis_Model mliab_anaysis_model) {
        try {
            if (mliab_anaysis_model.getData().getLiab_analysis() != null) {
                Float yDataPercent[] = new Float[6];
                ArrayList<String> xDataName = new ArrayList<>();
                int i = -1;

                if (mliab_anaysis_model.getData().getLiab_analysis().getCcard_per() != null) {

                    if (!mliab_anaysis_model.getData().getLiab_analysis().getCcard_per() .equalsIgnoreCase("0")) {
                        i = i + 1;
                        yDataPercent[i] = Float.parseFloat(mliab_anaysis_model.getData().getLiab_analysis().getCcard_per());
                        xDataName.add("Credit card");
                    }
                }
                if (mliab_anaysis_model.getData().getLiab_analysis().getLn_off_per() != null) {

                    if (!mliab_anaysis_model.getData().getLiab_analysis().getLn_off_per().equalsIgnoreCase("0")) {
                        i = i + 1;
                        yDataPercent[i] = Float.parseFloat(mliab_anaysis_model.getData().getLiab_analysis().getLn_off_per());
                        xDataName.add("Loan Offer Percentage");
                    }
                }


                if (mliab_anaysis_model.getData().getLiab_analysis().getLn_per() != null) {

                    if (!mliab_anaysis_model.getData().getLiab_analysis().getLn_per().equalsIgnoreCase("0")) {
                        i = i + 1;
                        yDataPercent[i] = Float.parseFloat(mliab_anaysis_model.getData().getLiab_analysis().getLn_per());
                        xDataName.add("Loan Percentage");
                    }
                }

                if (mliab_anaysis_model.getData().getLiab_analysis().getRf_dep_per() != null) {
                    if (!mliab_anaysis_model.getData().getLiab_analysis().getRf_dep_per().equalsIgnoreCase("0")) {
                        i = i + 1;
                        yDataPercent[i] = Float.parseFloat(mliab_anaysis_model.getData().getLiab_analysis().getRf_dep_per());
                        xDataName.add("Refundable deposit");
                    }
                }


                if (mliab_anaysis_model.getData().getLiab_analysis().getUnpd_bills_per() != null) {
                    if (!mliab_anaysis_model.getData().getLiab_analysis().getUnpd_bills_per().equalsIgnoreCase("0")) {
                        i = i + 1;
                        yDataPercent[i] = Float.parseFloat(mliab_anaysis_model.getData().getLiab_analysis().getUnpd_bills_per());
                        xDataName.add("Unpaid percentage");
                    }
                }


                if (mliab_anaysis_model.getData().getLiab_analysis().getOth_liab_per() != null) {
                    if (!mliab_anaysis_model.getData().getLiab_analysis().getOth_liab_per().equalsIgnoreCase("0")) {
                        i = i + 1;
                        yDataPercent[i] = Float.parseFloat(mliab_anaysis_model.getData().getLiab_analysis().getOth_liab_per());
                        xDataName.add("Other Liability Percentage");
                    }
                }


                titleLayout.removeAllViews();
                addData(xDataName, yDataPercent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }






    public int getDeviceWidth(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return width;
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

            case R.id.menu_summary:

                try{
                    addFragmenttoStack(new LiabilitiesMainPageFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;


            case R.id.menu_detail:
                try{
                    addFragmenttoStack(new LiabilitiesDetailPageFragment());
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
            {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
//                getActivity().finish();
            }
            break;
            case R.id.liab_fab_id:
                addFragmenttoStack(new LiabilitiesTabViewFragment());


        }
    }

}

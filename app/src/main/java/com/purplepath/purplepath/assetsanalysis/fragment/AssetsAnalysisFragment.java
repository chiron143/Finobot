package com.purplepath.purplepath.assetsanalysis.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.AssetsDetailsFragment;
import com.purplepath.purplepath.assetsanalysis.AssertanaysisMainPageFragment;
import com.purplepath.purplepath.assetsanalysis.AssetanaysisdetailFragment;
import com.purplepath.purplepath.assetsanalysis.model.AssestAnalysisModel;
import com.purplepath.purplepath.cashmanaganalysis.CashManagemntAnalysis;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomechartdetail.model.IncomeAnalysisModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vishnu on 28-09-2016.
 */
public class AssetsAnalysisFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private PieChart mChart, mrecentChart;
    // we're going to display pie chart for smartphones martket shares
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private ArrayList<Float> yData;
    private ArrayList<String> xData;
    private ArrayList<Float> yDatarecent;
    private ArrayList<String> xDatarecent;
    private Context mContext;
    private LinearLayout checkboxLayout;
    private  ArrayList<CheckBox>  userNameCheckBox=new ArrayList<>();
    DisplayMetrics displaymetrics = new DisplayMetrics();
    private IncomeAnalysisModel incomeAnalysisModel;
    private AssestAnalysisModel mAssetsAnalysisModel;
    private int chartsize, marginsize;
    private CheckBox mcurrentAllocation, mrecentAllocation;
    private OnActivityBackPressedListener mCallBackListener;
    private  LinearLayout right_layout, scrolllinearlayout;
    private TextView empty_chart_display;
    private FloatingActionButton mEditFloatingBtn;
    String gold,employment_benefit,equity,fixed_income,householde_asset,liguid,realestate,otherasset;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View incomeView =  inflater.inflate(R.layout.fragment_assets_allocation_pie_chart_view, container, false);
//        mainLayout = (RelativeLayout) incomeView.findViewById(R.id.mainLayout);
//        recentLayout = (RelativeLayout) incomeView.findViewById(R.id.recentLayout);
//        titleLayout = (LinearLayout) incomeView.findViewById(R.id.titleLayout);
        mCallBackListener.setActionBarTitle("My Asset Allocation");
        scrolllinearlayout = incomeView.findViewById(R.id.scrolllinearlayout);
        empty_chart_display = incomeView.findViewById(R.id.empty_chart_display);
        checkboxLayout = incomeView.findViewById(R.id.chart_layout);
        mcurrentAllocation = incomeView.findViewById(R.id.current_allocation);
        mrecentAllocation = incomeView.findViewById(R.id.recent_allocation);
        mcurrentAllocation.setOnCheckedChangeListener(this);
        mrecentAllocation.setOnCheckedChangeListener(this);
        mleftRelativeLayout = incomeView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = incomeView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = incomeView.findViewById(R.id.relative_right_arrow);
        mEditFloatingBtn= incomeView.findViewById(R.id.assets_fab_id);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        mEditFloatingBtn.setOnClickListener(this);
//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle(R.string.assetallocation);
        callAssetsAnalysisService();
        int width = getDeviceWidth();
        double d = width*0.25;
        chartsize = (int)(d);
        width = width-chartsize;

        double margindouble = width*0.03;
        marginsize = (int)(margindouble);
        mChart = new PieChart(getActivity());
        mrecentChart = new PieChart(getActivity());

        // add pie chart to main layout
        LinearLayout.LayoutParams chart_frame_layout = new LinearLayout.LayoutParams(width,width);
        //  chart_frame_layout.gravity = Gravity.CENTER_HORIZONTAL;
        // mChart.setLayoutParams(chart_frame_layout);
//        mChart.setLayoutParams(new FrameLayout.LayoutParams(width, width));
        //     mrecentChart.setLayoutParams(new FrameLayout.LayoutParams(width, width));
        //FrameLayout.LayoutParams chart_frame_layout = new FrameLayout.LayoutParams(width,width);
        chart_frame_layout.gravity = Gravity.CENTER;
        mChart.setLayoutParams(chart_frame_layout);
        //mChart.setLayoutParams(new FrameLayout.LayoutParams(width, width));
        LinearLayout.LayoutParams recent_chart_frame_layout = new LinearLayout.LayoutParams(width,width);
        recent_chart_frame_layout.gravity = Gravity.CENTER;
        mrecentChart.setLayoutParams(recent_chart_frame_layout);
        //mrecentChart.setLayoutParams(new FrameLayout.LayoutParams(width, width));

//        RelativeLayout.LayoutParams title_params = (RelativeLayout.LayoutParams)titleLayout.getLayoutParams();
//        title_params.setMargins(0, marginsize, 0, marginsize);
//        titleLayout.setLayoutParams(title_params);

        //checkboxLayout.addView(mChart);
        //mainLayout.setBackgroundColor(Color.parseColor("#ffffff"));


        //chart background programatically fixing with tab
        ShapeDrawable sd = new ShapeDrawable();
        sd.setShape(new RectShape());
        sd.getPaint().setColor(Color.GRAY);
        sd.getPaint().setStrokeWidth(5f);
        sd.getPaint().setStyle(Paint.Style.STROKE);
        checkboxLayout.setBackground(sd);


        // configure pie chart

        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mrecentChart.setUsePercentValues(true);
        mrecentChart.setDescription("");
        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mrecentChart.setDrawHoleEnabled(true);
//        mChart.setHoleColorTransparent(true);
//        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);
        mrecentChart.setTransparentCircleRadius(10);
        mChart.setDrawSliceText(false);
        mrecentChart.setDrawSliceText(false);
        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mrecentChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);
        mrecentChart.setRotationEnabled(true);
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

        mrecentChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
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
        // add data
        // customize legends
        Legend l = mChart.getLegend();
        l.setEnabled(false);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(10);
        l.setYEntrySpace(10);

        Legend legend = mrecentChart.getLegend();
        legend.setEnabled(false);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setXEntrySpace(10);
        legend.setYEntrySpace(10);

        return incomeView;
    }

    public static AssetsAnalysisFragment newInstance() {
        Bundle args = new Bundle();
        AssetsAnalysisFragment fragment = new AssetsAnalysisFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void addData(ArrayList<String> xVals, ArrayList<Float> yData,PieChart mChart ) {
        try {

            checkboxLayout.addView(mChart);
            ArrayList<Entry> yVals1 = new ArrayList<Entry>();


                for (int i = 0; i < yData.size(); i++) {
                    if(yData.get(i) !=null) {
                        yVals1.add(new Entry(yData.get(i), i));
                    }
                }


//            ArrayList<String> xVals = new ArrayList<String>();
//
//            for (int i = 0; i < xData.size() - 1; i++) {
//                if(xData.get(i)!=null) {
//                    xVals.add(xData[i]);
//                }
//            }

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

            for (int i = 0; i < xVals.size(); i++) {


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

                TextView txt_unit = new TextView(mContext);
                LinearLayout.LayoutParams left_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txt_unit.setLayoutParams(left_params);
                txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                txt_unit.setText(xVals.get(i));
                left_layout.addView(txt_unit);
                i++;
                if ((xVals.size() ) == i) {
                    parent_layout.addView(left_layout);
                    checkboxLayout.addView(parent_layout);
                    break;
                }

                LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parms_right_layout.weight = 1F;
                right_layout = new LinearLayout(mContext);
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

                TextView right_txt_unit = new TextView(mContext);
                LinearLayout.LayoutParams right_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                right_txt_unit.setLayoutParams(right_params);
                right_txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                right_txt_unit.setText(xVals.get(i));

                right_layout.addView(right_txt_unit);

                parent_layout.addView(right_layout);
                parent_layout.addView(left_layout);
                checkboxLayout.addView(parent_layout);
            }
            //  AddCheckBoxView( xData,  yData);
            mChart.setData(data);
            mrecentChart.setData(data);
            // undo all highlights
            mChart.highlightValues(null);
            mrecentChart.highlightValues(null);
            // update pie chart
            mChart.invalidate();
            mrecentChart.invalidate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void AddCheckBoxView(String[] xData, Float[] yData) {
        for (int i = 0; i < xData.length; i++) {
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

//            //Log.e("Possition To Add L", "" + i);
            userNameCheckBox.add( new CheckBox(mContext));
            userNameCheckBox.get(i).setId(i);
            UtileKit.setTextAppearance(mContext,android.R.style.TextAppearance_Small,userNameCheckBox.get(i));
            userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = compoundButton.getId();
                    checkBoxOnClick(position,b);
//                    //Log.e("Possition To Add L", "" + position);

                }
            });
            userNameCheckBox.get(i).setText(xData[i]);
            userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext,R.color.app_text_color_gray));
            left_layout.addView(userNameCheckBox.get(i));
            i++;
            if (xData.length == i) {
                parent_layout.addView(left_layout);
                checkboxLayout.addView(parent_layout);
                break;
            }

            LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_right_layout.weight = 1F;
            LinearLayout right_layout = new LinearLayout(mContext);
            right_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_layout.setGravity(Gravity.LEFT);
            parent_param_layout.setMargins(10, 0, 0, 10);
            right_layout.setLayoutParams(parms_right_layout);

//            //Log.e("Possition To Add R", "" + i);
            userNameCheckBox.add(new CheckBox(mContext));
            userNameCheckBox.get(i).setId(i);
            UtileKit.setTextAppearance(mContext,android.R.style.TextAppearance_Small,userNameCheckBox.get(i));
            userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = compoundButton.getId();
                    checkBoxOnClick(position,b);
                }
            });
            userNameCheckBox.get(i).setText(xData[i]);
            right_layout.addView(userNameCheckBox.get(i));

            parent_layout.addView(right_layout);
            parent_layout.addView(left_layout);
            checkboxLayout.addView(parent_layout);

        }
        if(!userNameCheckBox.isEmpty())
            userNameCheckBox.get(userNameCheckBox.size()-1).setChecked(true);
    }

    private void checkBoxOnClick(int position, boolean b) {
        if(position==userNameCheckBox.size()-1&b)
        {
            for (int i=0;i<userNameCheckBox.size()-1;i++)
            {
                userNameCheckBox.get(i).setChecked(false);
            }
            checkboxLayout.removeAllViews();
            addData(xData,yData,mChart);
        }
        else if(b)
        {

            for (int i=0;i<userNameCheckBox.size();i++)
            {
                if(i!=position)
                    userNameCheckBox.get(i).setChecked(false);
            }
            addSingleUserChart(position);
        }
        else
        {
            //userNameCheckBox.get(userNameCheckBox.size()-1).setChecked(false);
        }
    }

    private void addSingleUserChart(int position) {
        ArrayList<Float>  yDataPercent=new ArrayList<Float>();
        ArrayList<String> xDataName= new ArrayList<String>();
        if (incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getSi_percent() != null) {
            yDataPercent.add(Float.parseFloat(incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getSi_percent()));
            xDataName.add("Salary Income");
        }
//        else {
//            yDataPercent[0] = Float.valueOf(0);
//            xDataName[0]="Salary Income";
//        }
        if (incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getIp_percent() != null) {
            yDataPercent.add( Float.parseFloat(incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getIp_percent()));
            xDataName.add("Income from Property");
        }
//        else {
//            yDataPercent[1] = Float.valueOf(0);
//            xDataName[1]="Income from Property";
//        }
        if (incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getIb_percent() != null) {
            yDataPercent.add(Float.parseFloat(incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getIb_percent()));
            xDataName.add("Income from Business");
        }
//        else {
//            yDataPercent[2] = Float.valueOf(0);
//            xDataName[2]="Income from Business";
//        }
        if (incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getCg_percent() != null) {
            yDataPercent.add( Float.parseFloat(incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getCg_percent()));
            xDataName.add("Capital Gains");
        }
//        else {
//            yDataPercent[3] = Float.valueOf(0);
//            xDataName[3]="Capital Gains";
//        }
        if (incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getIfs_percent() != null) {
            yDataPercent.add( Float.parseFloat(incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getIfs_percent()));
            xDataName.add("Income from Other Sources");
        }
//        else {
//            yDataPercent[4] = Float.valueOf(0);
//            xDataName[4]="Income from Other Sources";
//        }
        yDataPercent.add(Float.valueOf(0));
        xDataName.add("Total");
        checkboxLayout.removeAllViews();
        addData(xDataName,yDataPercent,mChart);
    }

    public void callAssetsAnalysisService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext,false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AssestAnalysisModel> call = webServiceObj.callAssetsAllocationAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<AssestAnalysisModel>() {
            @Override
            public void onResponse(Call<AssestAnalysisModel> call, Response<AssestAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
//                //Log.e("success",""+response.body());
                //incomeAnalysisModel = response.body();
                mAssetsAnalysisModel = response.body();
//                ArrayList<Float> chartdatalist = new ArrayList<Float>();
//                ArrayList<String> chartitledatalist = new ArrayList<String>();

                if (mAssetsAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    if(null != mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det() && null != mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det() ) {
                        try {

                            yData = new ArrayList<Float>();
                            xData = new ArrayList<String>();
                            yDatarecent = new ArrayList<Float>();
                            xDatarecent = new ArrayList<String>();

                            gold = mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getComm_gold();
                            employment_benefit = mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getEmp_ben();
                            equity = mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getEqu();
                            fixed_income = mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getFix_inc();
                            householde_asset= mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getHou_asst();
                            liguid= mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getLiq();
                            realestate= mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getReal_prop();
                            otherasset = mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getOth_asst();

                            if (gold.equalsIgnoreCase("0") && employment_benefit.equalsIgnoreCase("0") && equity.equalsIgnoreCase("0")
                                    && fixed_income.equalsIgnoreCase("0") && householde_asset.equalsIgnoreCase("0") &&
                                    liguid.equalsIgnoreCase("0") && realestate.equalsIgnoreCase("0") &&
                                    otherasset.equalsIgnoreCase("0")) {

                                scrolllinearlayout.setVisibility(View.GONE);
                                empty_chart_display.setVisibility(View.VISIBLE);
                                empty_chart_display.setText(HomePageActivity.errorMessageInChart);
                                UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                            }else {
                                if (!mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getComm_gold().equalsIgnoreCase("0")) {

                                    xData.add("Commodity gold");
                                    yData.add(Float.valueOf(mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getComm_gold()));
                                }
                                if (!mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getEmp_ben().equalsIgnoreCase("0")) {

                                    xData.add("Employee benefit");
                                    yData.add(Float.valueOf(mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getEmp_ben()));
                                }
                                if (!mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getEqu().equalsIgnoreCase("0")) {

                                    xData.add("Equity");
                                    yData.add(Float.valueOf(mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getEqu()));
                                }
                                if (!mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getFix_inc().equalsIgnoreCase("0")) {

                                    xData.add("Fixed income");
                                    yData.add(Float.valueOf(mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getFix_inc()));
                                }
                                if (!mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getHou_asst().equalsIgnoreCase("0")) {

                                    xData.add("Household asset");
                                    yData.add(Float.valueOf(mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getHou_asst()));
                                }
                                if (!mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getLiq().equalsIgnoreCase("0")) {

                                    xData.add("Liquid");
                                    yData.add(Float.valueOf(mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getLiq()));
                                }
                                if (!mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getReal_prop().equalsIgnoreCase("0")) {

                                    xData.add("Real Estate property");
                                    yData.add(Float.valueOf(mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getReal_prop()));
                                }
                                if (!mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getOth_asst().equalsIgnoreCase("0")) {

                                    xData.add("Other asset");
                                    yData.add(Float.valueOf(mAssetsAnalysisModel.getData().getCur_alloc().getCur_alloc_det().getOth_asst()));
                                }


                                if (!mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getComm_gold().equalsIgnoreCase("0")) {

                                    xDatarecent.add("Commodity gold");
                                    yDatarecent.add(Float.valueOf(mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getComm_gold()));
                                }
                                if (!mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getEmp_ben().equalsIgnoreCase("0")) {

                                    xDatarecent.add("Employee benefit");
                                    yDatarecent.add(Float.valueOf(mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getEmp_ben()));
                                }
                                if (!mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getEqu().equalsIgnoreCase("0")) {

                                    xDatarecent.add("Equity");
                                    yDatarecent.add(Float.valueOf(mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getEqu()));
                                }
                                if (!mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getFix_inc().equalsIgnoreCase("0")) {

                                    xDatarecent.add("Fixed income");
                                    yDatarecent.add(Float.valueOf(mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getFix_inc()));
                                }
                                if (!mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getHou_asst().equalsIgnoreCase("0")) {

                                    xDatarecent.add("House asset");
                                    yDatarecent.add(Float.valueOf(mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getHou_asst()));
                                }
                                if (!mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getLiq().equalsIgnoreCase("0")) {

                                    xDatarecent.add("Liquid");
                                    yDatarecent.add(Float.valueOf(mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getLiq()));
                                }
                                if (!mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getReal_prop().equalsIgnoreCase("0")) {
                                    xDatarecent.add("Real Estate property");
                                    yDatarecent.add(Float.valueOf(mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getReal_prop()));
                                }
                                if (!mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getOth_asst().equalsIgnoreCase("0")) {
                                    xDatarecent.add("Other asset");
                                    yDatarecent.add(Float.valueOf(mAssetsAnalysisModel.getData().getRec_alloc().getRec_alloc_det().getOth_asst()));
                                }
                            }
                            //mChart.setCenterText();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
                try {
                    if (yData != null && yDatarecent != null) {
                        //AddCheckBoxView(xData,yData);
                        addData(xData, yData, mChart);
                        //addData(xDatarecent,yDatarecent,mrecentChart);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<AssestAnalysisModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();

            }
        });

    }

    private void setChartDataAsList(ArrayList<Float> chartdatalist, ArrayList<String> chartitledatalist , String value){

        if(UtileKit.validateObjectValues(value) && !value.equalsIgnoreCase("0")) {
            Float  valuepervalue = Float.parseFloat(value);
            chartdatalist.add(valuepervalue);
            chartitledatalist.add(value);
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
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(mrecentAllocation.isChecked() && mcurrentAllocation.isChecked()){
            checkboxLayout.removeAllViews();
            if(yData!=null && yDatarecent != null) {
                //AddCheckBoxView(xData,yData);
                addData(xData,yData,mChart);
                addData(xDatarecent,yDatarecent,mrecentChart);
            }
        }else if(mrecentAllocation.isChecked()){
            checkboxLayout.removeAllViews();
            if(yDatarecent != null){
                addData(xDatarecent,yDatarecent,mrecentChart);
            }
        } else if(mcurrentAllocation.isChecked()){
            checkboxLayout.removeAllViews();
            if(yData != null){
                addData(xData,yData,mChart);
            }
        }else{
            checkboxLayout.removeAllViews();
        }
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
                    addFragmenttoStack(new AssetanaysisdetailFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:

                try{
                    addFragmenttoStack(new AssertanaysisMainPageFragment());
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
            case R.id.relative_right_arrow:
            {
                addFragmenttoStack(new CashManagemntAnalysis());
            }
            break;
            case R.id.assets_fab_id:
                addFragmenttoStack(new AssetsDetailsFragment());
                break;

        }
    }

}
package com.purplepath.purplepath.cashmanaganalysis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import android.widget.SeekBar;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.purplepath.purplepath.apputiles.ChartValueFormatter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.cashflowmanagmentchart.model.Cash_mang_det;
import com.purplepath.purplepath.cashmanaganalysis.model.CashManagentAnalyisModel;
import com.purplepath.purplepath.expenseEDCOC.ExpenseTabMainFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomedetails.fragment.IncomeDetail;
import com.purplepath.purplepath.incomedetails.fragment.IncomefromFamilyDetails;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dinesh on 05/10/16.
 */
public class CashManagemntAnalysis   extends BaseFragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener, View.OnClickListener {
    private Context mContext;
    protected HorizontalBarChart mChart;
    private LinearLayout parentView;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    //    private SeekBar mSeekBarX, mSeekBarY;
    String tot_exp,over_all_contr,deflict,over_all_inc,over_all_commt,over_all_obli,over_all_exp;
    private Typeface tf;
    private ScrollView scrolllinearlayout;
    private  TextView errorTextview;
    private OnActivityBackPressedListener mCallBackListener;
    private FloatingActionButton mEditFloatBtn;

    private ArrayList<BarEntry> BARENTRYHORIZONTAL ;
    private BarDataSet  BardatasetHorizontal ;
    private Cash_mang_det value;
    FloatingActionMenu mcashmanagement_floating_action_menu;
    com.github.clans.fab.FloatingActionButton  mfab_income, mfab_expense;
    ArrayList<Integer> colors = new ArrayList<Integer>();
    CashManagentAnalyisModel  cashMangModel;
    private RelativeLayout relativeCharts;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View incomeView =  inflater.inflate(R.layout.fragment_cash_mang_chart, container, false);
        mContext=getContext();
        mCallBackListener.setActionBarTitle("Cash Management");
        parentView= incomeView.findViewById(R.id.parentViewId);
        scrolllinearlayout = incomeView.findViewById(R.id.layout_Scroll);
        errorTextview = incomeView.findViewById(R.id.empty_chart_display);

        relativeCharts= incomeView.findViewById(R.id.relativeCharts);

        mleftRelativeLayout = incomeView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = incomeView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = incomeView.findViewById(R.id.relative_right_arrow);
       // mEditFloatBtn=(FloatingActionButton)incomeView.findViewById(R.id.expense_fab_id);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
      //  mEditFloatBtn.setOnClickListener(this);

        BARENTRYHORIZONTAL = new ArrayList<>();
   try {
       value = (Cash_mang_det) getArguments().getSerializable("user_tax_position");
   }catch (Exception e){
       e.printStackTrace();
   }
        mcashmanagement_floating_action_menu = incomeView.findViewById(R.id.cashmanagement_floating_action_menu);
        mfab_income = incomeView.findViewById(R.id.fab_income);
        mfab_expense = incomeView.findViewById(R.id.fab_expense);

        UtileKit.setSvgButtonDrawableFloatingButton(mfab_income,mContext,R.drawable.ic_incomefloat_icon);
        UtileKit.setSvgButtonDrawableFloatingButton(mfab_expense,mContext,R.drawable.ic_expensefloat_icon);

        mfab_income.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try{
                    addFragmenttoStack(new IncomeDetail());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        mfab_expense.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    addFragmenttoStack(new ExpenseTabMainFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mChart = incomeView.findViewById(R.id.chart1);

        int actualwidth = getDeviceWidth();
        double d = actualwidth*0.10;
        int chartsize = (int)(d);
        int width = actualwidth-chartsize;

        double margindouble = width*0.03;
//         add pie chart to main layout
        mChart.setLayoutParams(new RelativeLayout.LayoutParams(actualwidth, width));
        mChart.setOnChartValueSelectedListener(this);
        // mChart.setHighlightEnabled(false);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        mChart.setDrawGridBackground(false);

        // mChart.setDrawYLabels(false);
        mChart.setDoubleTapToZoomEnabled(false);

        mChart.setTouchEnabled(false);

        tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);


        YAxis yl = mChart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawLabels(false);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(44f);
        yl.setStartAtZero(false);
        yl.setValueFormatter(new ChartValueFormatter());

        YAxis yr = mChart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setStartAtZero(false);
        yr.setValueFormatter(new ChartValueFormatter());


        Legend l = mChart.getLegend();
        l.setEnabled(false);
        l.setTextSize(8 * getResources().getDisplayMetrics().density);
        l.setFormSize(15f);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(15f);


        //chart background programatically fixing with tab
        ShapeDrawable sd = new ShapeDrawable();
        sd.setShape(new RectShape());
        sd.getPaint().setColor(Color.GRAY);
        sd.getPaint().setStrokeWidth(5f);
        sd.getPaint().setStyle(Paint.Style.STROKE);
        relativeCharts.setBackground(sd);

        if(value!= null){
            if (Float.parseFloat(value.getDeflict()) < 0) {
                setDeflict(value);

            } else {
                setCashInFlow(value);
            }
        }else {
            if (cashMangModel != null) {
                if (cashMangModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    if (null != cashMangModel.getData().getCash_mang_det()) {

                        if (Float.parseFloat(cashMangModel.getData().getCash_mang_det().getDeflict()) < 0) {
                            setDeflict(cashMangModel.getData().getCash_mang_det());

                        } else {
                            setCashInFlow(cashMangModel.getData().getCash_mang_det());
                        }
                    }
                } else {
                    callCashManagmentService();
                }
            }
        }
        return incomeView;
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

//        tvX.setText("" + (mSeekBarX.getProgress() + 1));
//        tvY.setText("" + (mSeekBarY.getProgress()));
//
//        setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());
//        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }




//    private void setData(float[] add, float[] add2,String[]  xValueString,int[] colour) {
//        try {
//            mChart.clear();
//            mChart.setDoubleTapToZoomEnabled(false);
//            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//            ArrayList<String> xVals = new ArrayList<String>();
//            xVals.add("Cash Outflow");
//            yVals1.add(new BarEntry(add2, 1));
//            xVals.add("Cash Inflow");
//            yVals1.add(new BarEntry(add, 0));
//            mChart.setDrawValueAboveBar(false);
//            BarDataSet set1;
//
//            if (mChart.getData() != null &&
//                    mChart.getData().getDataSetCount() > 0) {
//                set1 = (BarDataSet) mChart.getData().getDataSetByIndex(0);
//                set1.setYVals(yVals1);
//                set1.setBarSpacePercent(50f);
//                mChart.getData().setXVals(xVals);
//                mChart.getData().notifyDataChanged();
//                mChart.notifyDataSetChanged();
//            } else {
//                set1 = new BarDataSet(yVals1, "");
//                set1.setColors(colour);
//                set1.setDrawValues(false);
//                set1.setBarSpacePercent(50f);
//                set1.setStackLabels(xValueString);
//                ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//                dataSets.add(set1);
//
//                BarData data = new BarData(xVals, dataSets);
//                data.setValueTextSize(7 * getResources().getDisplayMetrics().density);
//                data.setValueTypeface(tf);
//
//
//                mChart.setData(data);
//
//                for (int i = 0; i < xValueString.length - 1; i++) {
//
//                    LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    LinearLayout parent_layout = new LinearLayout(mContext);
//                    parent_layout.setWeightSum(2);
//                    parent_layout.setOrientation(LinearLayout.HORIZONTAL);
//                    parent_param_layout.setMargins(10, 0, 0, 10);
//                    parent_layout.setLayoutParams(parent_param_layout);
//
//                    LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    parms_left_layout.weight = 1F;
//                    LinearLayout left_layout = new LinearLayout(mContext);
//                    left_layout.setOrientation(LinearLayout.HORIZONTAL);
//                    left_layout.setGravity(Gravity.LEFT);
//                    left_layout.setLayoutParams(parms_left_layout);
//
//                    LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(45, 45);
//                    parms_legen_layout.setMargins(20, 10, 20, 10);
//                    LinearLayout legend_layout = new LinearLayout(mContext);
//                    legend_layout.setLayoutParams(parms_legen_layout);
//                    legend_layout.setOrientation(LinearLayout.HORIZONTAL);
//                    legend_layout.setBackgroundColor(colour[i]);
//                    left_layout.addView(legend_layout);
//
//                    TextView txt_unit = new TextView(mContext);
//                    txt_unit.setText(xValueString[i]);
//                    LinearLayout.LayoutParams left_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//                    txt_unit.setLayoutParams(left_params);
//                    txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
//                    left_layout.addView(txt_unit);
//                    i++;
//                    if ((xValueString.length - 1) == i) {
//                        parent_layout.addView(left_layout);
//                        parentView.addView(parent_layout);
//                        break;
//                    }
//
//                    LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    parms_right_layout.weight = 1F;
//                    LinearLayout right_layout = new LinearLayout(mContext);
//                    right_layout.setOrientation(LinearLayout.HORIZONTAL);
//                    right_layout.setGravity(Gravity.LEFT);
//                    parent_param_layout.setMargins(10, 0, 0, 10);
//                    right_layout.setLayoutParams(parms_right_layout);
//
//
//                    LinearLayout.LayoutParams parms_rightlegend_layout = new LinearLayout.LayoutParams(45, 45);
//                    parms_rightlegend_layout.setMargins(20, 10, 20, 10);
//                    LinearLayout right_legend_layout = new LinearLayout(mContext);
//                    right_legend_layout.setLayoutParams(parms_rightlegend_layout);
//                    right_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
//                    right_legend_layout.setBackgroundColor(colour[i]);
//                    right_layout.addView(right_legend_layout);
//
//                    TextView right_txt_unit = new TextView(mContext);
//                    LinearLayout.LayoutParams right_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//                    right_txt_unit.setLayoutParams(right_params);
//                    right_txt_unit.setText(xValueString[i]);
//                    right_txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
//                    right_layout.addView(right_txt_unit);
//
//                    parent_layout.addView(right_layout);
//                    parent_layout.addView(left_layout);
//                    parentView.addView(parent_layout);
//                }
//                mChart.animateY(1000);
//            }
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }


    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Cash Outflow");
        xAxis.add("Cash Inflow");
        return xAxis;
    }
    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mChart.getBarBounds((BarEntry) e);
        PointF position = mChart.getPosition(e, mChart.getData().getDataSetByIndex(dataSetIndex)
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());
    }

    public void onNothingSelected() {
    }

    public void callCashManagmentService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext,false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<CashManagentAnalyisModel> call = webServiceObj.callCashMangChartService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<CashManagentAnalyisModel>() {
            @Override
            public void onResponse(Call<CashManagentAnalyisModel> call, Response<CashManagentAnalyisModel> response) {
            try{
                UtileKit.dismisssSpinnerDialog();
                float[]  yData=null;
                float[]  yIncomeData=null;
                String[] xData=null;
                int [] colour;
                cashMangModel = response.body();

                if (cashMangModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    if(null!=cashMangModel.getData().getCash_mang_det()) {

                            if (Float.parseFloat(cashMangModel.getData().getCash_mang_det().getDeflict()) < 0) {
                                setDeflict(cashMangModel.getData().getCash_mang_det());

                            } else {
                                setCashInFlow(cashMangModel.getData().getCash_mang_det());
                            }
                        }

//                    BARENTRYHORIZONTAL.add(new BarEntry(yData,0));
//                    BARENTRYHORIZONTAL.add(new BarEntry(yIncomeData,1));
//                    BardatasetHorizontal = new BarDataSet(BARENTRYHORIZONTAL, "");
//                    BardatasetHorizontal.setColors(colour);
//                    BardatasetHorizontal.setValueFormatter(new ChartValueFormatter());
//                    BardatasetHorizontal.setBarSpacePercent(50f);
//                    BarData data;
//                    data = new BarData(getXAxisValues(), BardatasetHorizontal);
//                    data.setValueTextSize(7 * getResources().getDisplayMetrics().density);
//                    mChart.setData(data);
//                    mChart.setDescription(" ");
//                    data.setDrawValues(false);
//                    Legend horizontalline = mChart.getLegend();
//                    horizontalline.setEnabled(false);
//                    horizontalline.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
//                    mChart.setPinchZoom(false);
//                    mChart.setDoubleTapToZoomEnabled(false);
//                    mChart.setTouchEnabled(false);
//                    mChart.invalidate();
//                    setDataForCheckbox(xData, colour);
//                    setData(yData, yIncomeData, xData, colour);

                }else{
                    scrolllinearlayout.setVisibility(View.GONE);
                    errorTextview.setVisibility(View.VISIBLE);
                    errorTextview.setText(HomePageActivity.errorMessageInChart);
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }

                UtileKit.dismisssSpinnerDialog();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CashManagentAnalyisModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
                scrolllinearlayout.setVisibility(View.GONE);
                errorTextview.setVisibility(View.VISIBLE);
                errorTextview.setText(HomePageActivity.errorMessageInChart);
                UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
            }
        });

    }

    private void setDeflict(Cash_mang_det cash_mang_det) {

        float[]  yData = new float[8];
        String[]  xData = new String[13];
        float[]  yIncomeData = new float[6];
        int[] colour=new int[13];
        try{
            String diviedResult;
            Float mhunder , getOver_all_exp, getOver_all_commt , getOver_all_contr, getOver_all_obli;
            Float minusgetOver_all_exp,minusgetOver_all_commt,minusgetOver_all_contr,minusgetOver_all_obli;
            BigInteger multiplyhunder;
//            if (cashMangModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                if (null != cash_mang_det) {
                    if (UtileKit.validateObjectValues(cash_mang_det.getOver_all_inc())
                            && UtileKit.validateObjectValues(cash_mang_det.getTot_exp())) {

                        /*
                        *
                        * Take overall income and Total exp divide the both value
                        * and mutiplied with 100 to find percentagevalue..
                        *
                        * Take oveall value and mutiplied with percentage value to display
                        * for expenses etc....
                        * For mutipliying we using method call
                        * validationIfElseCondition(value, percentagevalue)
                        *
                        * */
                        diviedResult = divide(cash_mang_det.getOver_all_inc(),
                                cash_mang_det.getTot_exp());
                        mhunder = roundoftwodecimalpoint(Float.valueOf(diviedResult) * 100);
                        getOver_all_exp = Float.valueOf(validationIfElseCondition(cash_mang_det.getOver_all_exp(), mhunder));
                        getOver_all_commt = Float.valueOf(validationIfElseCondition(cash_mang_det.getOver_all_commt(), mhunder));
                        getOver_all_contr = Float.valueOf(validationIfElseCondition(cash_mang_det.getOver_all_contr(), mhunder));
                        getOver_all_obli = Float.valueOf(validationIfElseCondition(cash_mang_det.getOver_all_obli(), mhunder));
                        yData[0] = getOver_all_exp;
                        xData[0] = "Expense : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(getOver_all_exp));
                        colour[0] = ColorTemplate.rgb("#da53fc");

                        yData[1] = getOver_all_commt;
                        xData[1] = "Commitment : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(getOver_all_commt));
                        colour[1] = ColorTemplate.rgb("#c18911");

                        yData[2] = getOver_all_obli;
                        xData[2] = "Obligation : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(getOver_all_obli));
                        colour[2] = ColorTemplate.rgb("#FFC107");

                        yData[3] = getOver_all_contr;
                        xData[3] = "Contribution : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(getOver_all_contr));
                        colour[3] = ColorTemplate.rgb("#5386fc");
                        /*
                         *  how to find deficit value
                         *  To find the Deficit value ........
                         *  Take oveall value and minius with original overvalue value to display
                         *  for expenses etc....
                         *  then Deficit value we be display
                         * */

                        try {
                            minusgetOver_all_commt = (getOver_all_commt - Float.parseFloat(cash_mang_det.getOver_all_commt()));
                            minusgetOver_all_exp = (getOver_all_exp - Float.parseFloat(cash_mang_det.getOver_all_exp()));
                            minusgetOver_all_contr = (getOver_all_contr - Float.parseFloat(cash_mang_det.getOver_all_contr()));
                            minusgetOver_all_obli = (getOver_all_obli - Float.parseFloat(cash_mang_det.getOver_all_obli()));

                            colour[4] = ColorTemplate.rgb("#ff8080");
                            yData[4] = minusgetOver_all_commt;
                            xData[4] = "Deficit Commitment : " + UtileKit.concatdinateRupeeSymbol(
                                    UtileKit.formatedNumber(minusgetOver_all_commt));
                            colour[5] = ColorTemplate.rgb("#ff6666");
                            yData[5] = minusgetOver_all_exp;
                            xData[5] = "Deficit Expense : " + UtileKit.concatdinateRupeeSymbol(
                                    UtileKit.formatedNumber(minusgetOver_all_exp));
                            colour[6] = ColorTemplate.rgb("#ff4d4d");
                            yData[6] = minusgetOver_all_contr;
                            xData[6] = "Deficit Contribution : " + UtileKit.concatdinateRupeeSymbol(
                                    UtileKit.formatedNumber(minusgetOver_all_contr));
                            colour[7] = ColorTemplate.rgb("#ff3333");
                            yData[7] = minusgetOver_all_obli;
                            xData[7] = "Deficit Obligation : " + UtileKit.concatdinateRupeeSymbol(
                                    UtileKit.formatedNumber(minusgetOver_all_obli));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (UtileKit.validateObjectValues(cash_mang_det.getSi_total())) {
                            yIncomeData[0] = Float.parseFloat(cash_mang_det.getSi_total());
                            xData[8] = "Salary : " + UtileKit.concatdinateRupeeSymbol(
                                    UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getSi_total())));
                            colour[8] = ColorTemplate.rgb("#FF7F50");
                        } else {
                            yIncomeData[0] = 0;
                            xData[8] = "0";
                            colour[8] = ColorTemplate.rgb("#FF7F50");
                        }

                        if (UtileKit.validateObjectValues(cash_mang_det.getIp_total())) {
                            yIncomeData[1] = Float.parseFloat(cash_mang_det.getIp_total());
                            xData[9] = "Property : " + UtileKit.concatdinateRupeeSymbol(
                                    UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getIp_total())));
                            colour[9] = ColorTemplate.rgb("#FFC0CB");
                        } else {
                            yIncomeData[1] = 0;
                            xData[9] = "0";
                            colour[9] = ColorTemplate.rgb("#FFC0CB");
                        }

                        if (UtileKit.validateObjectValues(cash_mang_det.getIb_total())) {
                            yIncomeData[2] = Float.parseFloat(cash_mang_det.getIb_total());
                            xData[10] = "Business : " + UtileKit.concatdinateRupeeSymbol(
                                    UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getIb_total())));
                            colour[10] = ColorTemplate.rgb("#00CED1");
                        } else {
                            yIncomeData[2] = 0;
                            xData[10] = "Business : " + "0";
                            colour[10] = ColorTemplate.rgb("#00CED1");
                        }

                        if (UtileKit.validateObjectValues(cash_mang_det.getCg_total())) {
                            yIncomeData[3] = Float.parseFloat(cash_mang_det.getCg_total());
                            xData[11] = "Capital Gain  : " + UtileKit.concatdinateRupeeSymbol(
                                    UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getCg_total())));
                            colour[11] = ColorTemplate.rgb("#6B8E23");
                        } else {
                            yIncomeData[3] = 0;
                            xData[11] = "Capital Gain  : " + "0";
                            colour[11] = ColorTemplate.rgb("#6B8E23");
                        }

                        if (UtileKit.validateObjectValues(cash_mang_det.getIfs_total())) {
                            yIncomeData[4] = Float.parseFloat(cash_mang_det.getIfs_total());
                            xData[12] = "Other : " + UtileKit.concatdinateRupeeSymbol(
                                    UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getIfs_total())));
                            colour[12] = ColorTemplate.rgb("#FFD700");
                        } else {
                            yIncomeData[4] = 0;
                            xData[12] = "Other : " + "0";
                            colour[12] = ColorTemplate.rgb("#FFD700");
                        }
                    } else {

                    }

                }
//            }
            setDataBarEntry(yData,yIncomeData ,colour, xData);



        }catch (Exception e){
            e.printStackTrace();
        }


    }
    private String validationIfElseCondition(String cashmanagementmodule, Float mhunder) {
        Float  getOver_all_exp = 0.0f;
        if(UtileKit.validateObjectValues(cashmanagementmodule)){
            getOver_all_exp = Float.parseFloat(cashmanagementmodule)*( mhunder);
            return String.valueOf(getOver_all_exp/100);
        }

        return String.valueOf(getOver_all_exp);
    }


    private void setDataBarEntry(float[] yData, float[] yIncomeData, int[] colour, String[] xData) {
        BARENTRYHORIZONTAL.add(new BarEntry(yData,0));
        BARENTRYHORIZONTAL.add(new BarEntry(yIncomeData,1));

        BardatasetHorizontal = new BarDataSet(BARENTRYHORIZONTAL, "");

        BardatasetHorizontal.setColors(colour);
        BardatasetHorizontal.setValueFormatter(new ChartValueFormatter());
        BardatasetHorizontal.setBarSpacePercent(50f);
        BarData data;
        data = new BarData(getXAxisValues(), BardatasetHorizontal);
        data.setValueTextSize(7 * getResources().getDisplayMetrics().density);
        mChart.setData(data);
        mChart.setDescription(" ");
        data.setDrawValues(false);
        Legend horizontalline = mChart.getLegend();
        horizontalline.setEnabled(false);
        horizontalline.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        mChart.setPinchZoom(false);
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.setTouchEnabled(false);
        mChart.invalidate();
        setDataForCheckbox(xData, colour);
    }

    public  String divide(String over_all_inc, String tot_exp) {

        BigDecimal v1 = new BigDecimal(over_all_inc);

        BigDecimal v2 = new BigDecimal(tot_exp);

        return v1.divide(v2, 4, RoundingMode.HALF_UP).toPlainString();

    }

    public  float roundoftwodecimalpoint(float value) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            return Float.parseFloat(decimalFormat.format(value));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    private void setCashInFlow(Cash_mang_det cash_mang_det) {
        int [] colour  = new int[10];
        float[] yData = new float[5];
        String[] xData = new String[12];
        float[]  yIncomeData = new float[6];
        try {

            if(UtileKit.validateObjectValues(cash_mang_det.getOver_all_exp())) {
                yData[0] = Float.parseFloat(cash_mang_det.getOver_all_exp());
                xData[0] = "Expense : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getOver_all_exp())));
                colour[0] = ColorTemplate.rgb("#da53fc");
            }else{
                yData[0] = 0;
                xData[0] = "Expense : "+"0";
                colour[0] = ColorTemplate.rgb("#da53fc");
            }

            if(UtileKit.validateObjectValues(cash_mang_det.getOver_all_commt())) {
                yData[1] = Float.parseFloat(cash_mang_det.getOver_all_commt());
                xData[1] = "Commitment : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getOver_all_commt())));
                colour[1] = ColorTemplate.rgb("#c18911");
            }else{
                yData[1] = 0;
                xData[1] = "Commitment : "+"0";
                colour[1] = ColorTemplate.rgb("#c18911");
            }

            if(UtileKit.validateObjectValues(cash_mang_det.getOver_all_contr())) {
                yData[2] = Float.parseFloat(cash_mang_det.getOver_all_obli());
                xData[2] = "Obligation : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getOver_all_obli())));
                colour[2] = ColorTemplate.rgb("#FFC107");
            }else {
                yData[2] = 0;
                xData[2] = "Obligation : " + "0";
                colour[2] = ColorTemplate.rgb("#FFC107");
            }
            if(UtileKit.validateObjectValues(cash_mang_det.getOver_all_contr())) {
                yData[3] = Float.parseFloat(cash_mang_det.getOver_all_contr());
                xData[3] = "Contribution : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getOver_all_contr())));
                colour[3] = ColorTemplate.rgb("#5386fc");
            }else{
                yData[3] = 0;
                xData[3] = "Contribution : "+"0";
                colour[3] = ColorTemplate.rgb("#5386fc");
            }


            if(UtileKit.validateObjectValues(cash_mang_det.getDeflict())) {
                xData[4] = "Surplus : " + UtileKit.concatdinateRupeeSymbol(
                        UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getDeflict())));
                yData[4] = Float.parseFloat(cash_mang_det.getDeflict());
                colour[4] = ColorTemplate.rgb("#adff00");
            }else{
                yData[4] = 0;
                xData[4] = "Surplus : " + 0;
                colour[4] = ColorTemplate.rgb("#adff00");
            }


        if(UtileKit.validateObjectValues(cash_mang_det.getSi_total())){
            yIncomeData[0] = Float.parseFloat(cash_mang_det.getSi_total());
            xData[5] = "Salary : " + UtileKit.concatdinateRupeeSymbol(
                    UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getSi_total())));
            colour[5] = ColorTemplate.rgb("#FF7F50");
        }else{
            yIncomeData[0] = 0;
            xData[5] = "0";
            colour[5] = ColorTemplate.rgb("#FF7F50");
        }

        if(UtileKit.validateObjectValues(cash_mang_det.getIp_total())){
            yIncomeData[1] = Float.parseFloat(cash_mang_det.getIp_total());
            xData[6] = "Property : " + UtileKit.concatdinateRupeeSymbol(
                    UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getIp_total())));
            colour[6] = ColorTemplate.rgb("#FFC0CB");
        }else{
            yIncomeData[1] = 0;
            xData[6] = "0";
            colour[6] = ColorTemplate.rgb("#FFC0CB");
        }

        if(UtileKit.validateObjectValues(cash_mang_det.getIb_total())){
            yIncomeData[2] = Float.parseFloat(cash_mang_det.getIb_total());
            xData[7] = "Business : " + UtileKit.concatdinateRupeeSymbol(
                    UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getIb_total())));
            colour[7] = ColorTemplate.rgb("#00CED1");
        }else{
            yIncomeData[2] = 0;
            xData[7] = "Business : " +"0";
            colour[7] = ColorTemplate.rgb("#00CED1");
        }

        if(UtileKit.validateObjectValues(cash_mang_det.getCg_total())){
            yIncomeData[3] = Float.parseFloat(cash_mang_det.getCg_total());
            xData[8] = "Capital Gain  : " + UtileKit.concatdinateRupeeSymbol(
                    UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getCg_total())));
            colour[8] = ColorTemplate.rgb("#6B8E23");
        }else{
            yIncomeData[3] = 0;
            xData[8] = "Capital Gain  : " +"0";
            colour[8] = ColorTemplate.rgb("#6B8E23");
        }

        if(UtileKit.validateObjectValues(cash_mang_det.getIfs_total())){
            yIncomeData[4] = Float.parseFloat(cash_mang_det.getIfs_total());
            xData[9] = "Other : " + UtileKit.concatdinateRupeeSymbol(
                    UtileKit.formatedNumber(Float.valueOf(cash_mang_det.getIfs_total())));
            colour[9] = ColorTemplate.rgb("#FFD700");
        }else{
            yIncomeData[4] = 0;
            xData[9] = "Other : " + "0";
            colour[9] = ColorTemplate.rgb("#FFD700");
        }

            setDataBarEntry(yData,yIncomeData ,colour,xData);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void setDataForCheckbox(String[] xDataCheckbox, int [] colour) {
        try {
            Log.i("InsuranceAnalysis", " InsuranceAnalysis setDataForCheckbox is " + xDataCheckbox.length +" colour : " + colour.length);

            for (int i = 0; i <  xDataCheckbox.length; i++) {

                LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout parent_layout = new LinearLayout(mContext);
                parent_layout.setWeightSum(2);
                // parent_param_layout.weight = 1F;

                parent_layout.setOrientation(LinearLayout.HORIZONTAL);
                parent_param_layout.setMargins(10, 0, 0, 10);
                parent_layout.setLayoutParams(parent_param_layout);

                LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
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
                legend_layout.setBackgroundColor(colour[i]);
                left_layout.addView(legend_layout);

                TextView txt_unit = new TextView(mContext);
                txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                LinearLayout.LayoutParams txtLeft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txt_unit.setLayoutParams(txtLeft_params);
                txt_unit.setText( xDataCheckbox[i]);
                left_layout.addView(txt_unit);
                parent_layout.addView(left_layout);
                parentView.addView(parent_layout);
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
            case R.id.menu_detail:

                try{
                addFragmenttoStack(new CashmanagementdetailFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:
                try{
                    addFragmenttoStack(new CashmanagementInOutFragment());
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

            }
            break;
            case R.id.expense_fab_id:
                addFragmenttoStack(new IncomefromFamilyDetails());
                break;

        }
    }


    public static Fragment newInstance(Cash_mang_det cash_mang_det) {
        CashManagemntAnalysis fragment = new CashManagemntAnalysis();
        Bundle args = new Bundle();
        if (cash_mang_det != null) {
            args.putSerializable("user_tax_position", cash_mang_det);

            fragment.setArguments(args);
        }
        return fragment;
    }
}

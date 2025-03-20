package com.purplepath.purplepath.networthanalysis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.BarChart;
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
import com.purplepath.purplepath.assets.AssetsDetailsFragment;
import com.purplepath.purplepath.assetsanalysis.fragment.AssetsAnalysisFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.liabilities.LiabilitiesTabViewFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.networthanalysis.adapter.NetworthExpandableadapter;
import com.purplepath.purplepath.networthanalysis.model.NetworkAnalysisModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by dinesh on 27/09/16.
 */
public class BarChartActivitySinus extends BaseFragment implements
        OnChartValueSelectedListener, View.OnClickListener {
    protected BarChart mChart;
    private Typeface tf;
    private Context mContext;
    private LinearLayout parentView,scrolllinearlayout;
    private TextView empty_chart_display;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private ExpandableListView mnetworth_expandlist;
    private NetworthExpandableadapter mnetworthadapter;
    private NetworkAnalysisModel incomeAnalysisModel;

    HashMap<Integer,ArrayList<String>> addArrayList=new HashMap<>();

    HashMap<Integer, ArrayList<Integer>> addColorList=new HashMap<>();

    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton  mfab_assert, mfab_liability;


    private ArrayList<Float> comm_goldArray,emp_benArray,  equArray,  fix_incArray,
            hou_asstArray, liqArray,  real_propArray,oth_asstArray,mCcardOtherList, mLn_offOtherList,
            mLnOtherList,  mRfOtherList, mUnpd_billsOtherList,mOth_liabOtherList;

    private ArrayList <Integer> colorarray ,comm_goldArraycolor, emp_benArraycolor , equArraycolor ,
            fix_incArrayArraycolor, hou_asstArraycolor , liqArraycolor,
            real_propArraycolor , oth_asstArraycolor , colorForBarYellow ,
            colorForBarGreen ,  colorForBarRed , colorForBarBlue ,
            colorForBarBlack , colorForBarRose , colorForBarR ,
            colorForBarY,colorForBarZ, nameothercolor,
            cc_Arraycolor, loan_Arraycolor, loanOffer_Arraycolor ,
            refusedable_Arraycolor ,unbill_Arraycolor , otherlib_Arraycolor;

    private ArrayList <String> namecomm_goldArray,nameemp_benArray , nameequArray , namefix_incArray ,
            namehou_asstArray, nameliqArray , namereal_propArray ,
            nameoth_asstArray , nameotherArraylist , namecreditcardArray,
            nameloanOffersArray , namerefundableDepositArray , nameunpaidBillsArray ,
            nameLoanArray , nameotherLiabilitiesArraylist;



    String gold,employment_benefit,equity,fixed_income,householde_asset,liguid,realestate,otherasset;
//    String creditcard,loanOffers,refundableDeposit,unpaidBills,Loan,otherLiabilities;
    private String linkto[] = {"Commodities/Gold", "Employment Benefit", "Equity", "Fixed Income/Debt",
            "Household Asset", "Liquid","Real Estate/Property","other Asset"};
    private String linktoInAlphabert[] = {"A", "B", "C", "D", "E", "F","G","H","I","J","K","L","M","N"};
    String[] xDataValues=null;
    int[] chartColor = {Color.rgb(181, 205, 26), Color.rgb(2, 71, 254),
            Color.rgb(254, 39, 18), Color.rgb(102, 177, 50),
            Color.rgb(251, 153, 2), Color.rgb(3, 146, 206),
            Color.rgb(253, 83, 8), Color.rgb(102, 177, 50), Color.rgb(62, 1, 164),
            Color.rgb(249, 188, 2), Color.rgb(208, 233, 43),
            Color.rgb(138, 237, 69), Color.rgb(18, 201, 254),
            Color.rgb(255, 121, 108), Color.rgb(198, 121, 108),
            Color.rgb(198, 160, 46), Color.rgb(255, 255, 2),
            Color.rgb(252, 101, 0), Color.rgb(252, 29, 0),
            Color.rgb(109, 135, 100), Color.rgb(191, 143, 0),
            Color.rgb(204, 16, 153), Color.rgb(73, 93, 26)
    };
    private OnActivityBackPressedListener mCallBackListener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mnetworth_expandlist= view.findViewById(R.id.networth_expandlist);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View barView = inflater.inflate(R.layout.activity_barchart_sinus, container, false);
        setHasOptionsMenu(true);
        parentView = barView.findViewById(R.id.parentViewId);
        scrolllinearlayout = barView.findViewById(R.id.scrolllinearlayout);
        empty_chart_display = barView.findViewById(R.id.empty_chart_display);
        mleftRelativeLayout = barView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = barView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = barView.findViewById(R.id.relative_right_arrow);

        inizalizeAllArraylist();

        materialDesignFAM = barView.findViewById(R.id.material_design_android_floating_action_menu);
        mfab_assert = barView.findViewById(R.id.fab_assert);
        mfab_liability = barView.findViewById(R.id.fab_liability);

        UtileKit.setSvgButtonDrawableFloatingButton(mfab_assert,mContext,R.drawable.ic_assetsfloat_icon);
        UtileKit.setSvgButtonDrawableFloatingButton(mfab_liability,mContext,R.drawable.ic_liabilityfloat_icon);




        mfab_assert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try{
                    addFragmenttoStack(new AssetsDetailsFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        mfab_liability.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    addFragmenttoStack(new LiabilitiesTabViewFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        mCallBackListener.setActionBarTitle("Networth ");
        int width = getDeviceWidth();
        double d = width * 0.25;
        int chartsize = (int) (d);
//        width = width-chartsize;


//        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mChart = barView.findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setLayoutParams(new LinearLayout.LayoutParams(width, width + width / 4));
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(false);


//        mChart.getAxisRight().setAxisMaxValue(25f);
//        mChart.getAxisRight().setAxisMinValue(-25f);
        mChart.getAxisRight().setDrawGridLines(true);
//        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setDrawZeroLine(true);
        mChart.getAxisRight().setLabelCount(7, false);
        mChart.getAxisRight().setValueFormatter(new ChartValueFormatter());
        mChart.getAxisRight().setTextSize(9f);
        mChart.getLegend().setEnabled(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(9f);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
        mChart.setTouchEnabled(false);
        mChart.setDoubleTapToZoomEnabled(false);
        // IMPORTANT: When using negative values in stacked bars, always make sure the negative values are in the array first

        callNetworkAnalysisService();

        return barView;
    }

    private void inizalizeAllArraylist() {

        comm_goldArray= new ArrayList<>();emp_benArray= new ArrayList<>();  equArray= new ArrayList<>();  fix_incArray= new ArrayList<>();
                hou_asstArray= new ArrayList<>(); liqArray= new ArrayList<>();  real_propArray= new ArrayList<>();
                oth_asstArray= new ArrayList<>();mCcardOtherList= new ArrayList<>(); mLn_offOtherList= new ArrayList<>();
                mLnOtherList= new ArrayList<>();  mRfOtherList= new ArrayList<>(); mUnpd_billsOtherList= new ArrayList<>();
                mOth_liabOtherList= new ArrayList<>();

        colorarray = new ArrayList<>();comm_goldArraycolor = new ArrayList<>(); emp_benArraycolor = new ArrayList<>(); equArraycolor = new ArrayList<>();
                fix_incArrayArraycolor = new ArrayList<>(); hou_asstArraycolor = new ArrayList<>(); liqArraycolor = new ArrayList<>();
                real_propArraycolor = new ArrayList<>(); oth_asstArraycolor = new ArrayList<>(); colorForBarYellow =  new ArrayList<>();
                colorForBarGreen =  new ArrayList<>(); colorForBarRed =  new ArrayList<>(); colorForBarBlue =  new ArrayList<>();
                colorForBarBlack =  new ArrayList<>(); colorForBarRose =  new ArrayList<>(); colorForBarR =  new ArrayList<>();
                colorForBarY =  new ArrayList<>();colorForBarZ =  new ArrayList<>(); nameothercolor = new ArrayList<>();
                cc_Arraycolor =  new ArrayList<>(); loan_Arraycolor =  new ArrayList<>(); loanOffer_Arraycolor =  new ArrayList<>();
                refusedable_Arraycolor =  new ArrayList<>();unbill_Arraycolor =  new ArrayList<>(); otherlib_Arraycolor = new ArrayList<>();

        namecomm_goldArray = new ArrayList<>();nameemp_benArray = new ArrayList<>(); nameequArray = new ArrayList<>(); namefix_incArray = new ArrayList<>();
                namehou_asstArray = new ArrayList<>(); nameliqArray = new ArrayList<>(); namereal_propArray = new ArrayList<>();
                nameoth_asstArray = new ArrayList<>(); nameotherArraylist = new ArrayList<>(); namecreditcardArray = new ArrayList<>();
                nameloanOffersArray = new ArrayList<>(); namerefundableDepositArray = new ArrayList<>(); nameunpaidBillsArray = new ArrayList<>();
                nameLoanArray = new ArrayList<>(); nameotherLiabilitiesArraylist = new ArrayList<>();

        colorForBarYellow.add(ColorTemplate.rgb("#F57F17"));
        colorForBarYellow.add(ColorTemplate.rgb("#F9A825"));
        colorForBarYellow.add(ColorTemplate.rgb("#FBC02D"));
        colorForBarYellow.add(ColorTemplate.rgb("#FDD835"));
        colorForBarYellow.add(ColorTemplate.rgb("#FFEB3B"));
        colorForBarYellow.add(ColorTemplate.rgb("#FFEE58"));
        colorForBarYellow.add(ColorTemplate.rgb("#FFF176"));
        colorForBarYellow.add(ColorTemplate.rgb("#FFF59D"));
        colorForBarYellow.add(ColorTemplate.rgb("#FFF9C4"));
        colorForBarYellow.add(ColorTemplate.rgb("#FFFDE7"));


        colorForBarGreen.add(ColorTemplate.rgb("#4CAF50"));
        colorForBarGreen.add(ColorTemplate.rgb("#1B5E20"));
        colorForBarGreen.add(ColorTemplate.rgb("#2E7D32"));
        colorForBarGreen.add(ColorTemplate.rgb("#388E3C"));
        colorForBarGreen.add(ColorTemplate.rgb("#43A047"));
        colorForBarGreen.add(ColorTemplate.rgb("#4CAF50"));
        colorForBarGreen.add(ColorTemplate.rgb("#66BB6A"));
        colorForBarGreen.add(ColorTemplate.rgb("#81C784"));
        colorForBarGreen.add(ColorTemplate.rgb("#A5D6A7"));
        colorForBarGreen.add(ColorTemplate.rgb("#C8E6C9"));
        colorForBarGreen.add(ColorTemplate.rgb("#E8F5E9"));
        colorForBarGreen.add(ColorTemplate.rgb("#4CAF50"));


        colorForBarRed.add(ColorTemplate.rgb("#B71C1C"));
        colorForBarRed.add(ColorTemplate.rgb("#C62828"));
        colorForBarRed.add(ColorTemplate.rgb("#D32F2F"));
        colorForBarRed.add(ColorTemplate.rgb("#E53935"));
        colorForBarRed.add(ColorTemplate.rgb("#F44336"));
        colorForBarRed.add(ColorTemplate.rgb("#EF5350"));
        colorForBarRed.add(ColorTemplate.rgb("#E57373"));
        colorForBarRed.add(ColorTemplate.rgb("#EF9A9A"));
        colorForBarRed.add(ColorTemplate.rgb("#FFCDD2"));
        colorForBarRed.add(ColorTemplate.rgb("#FFEBEE"));


        colorForBarBlue.add(ColorTemplate.rgb("#01579B"));
        colorForBarBlue.add(ColorTemplate.rgb("#0277BD"));
        colorForBarBlue.add(ColorTemplate.rgb("#0288D1"));
        colorForBarBlue.add(ColorTemplate.rgb("#039BE5"));
        colorForBarBlue.add(ColorTemplate.rgb("#03A9F4"));
        colorForBarBlue.add(ColorTemplate.rgb("#29B6F6"));
        colorForBarBlue.add(ColorTemplate.rgb("#4FC3F7"));
        colorForBarBlue.add(ColorTemplate.rgb("#81D4FA"));
        colorForBarBlue.add(ColorTemplate.rgb("#B3E5FC"));
        colorForBarBlue.add(ColorTemplate.rgb("#E1F5FE"));

        colorForBarBlack.add(ColorTemplate.rgb("#000000"));
        colorForBarBlack.add(ColorTemplate.rgb("#1b1b1b"));
        colorForBarBlack.add(ColorTemplate.rgb("#2c2c2c"));
        colorForBarBlack.add(ColorTemplate.rgb("#484848"));
        colorForBarBlack.add(ColorTemplate.rgb("#080404"));
        colorForBarBlack.add(ColorTemplate.rgb("#120606"));
        colorForBarBlack.add(ColorTemplate.rgb("#0c0909"));
        colorForBarBlack.add(ColorTemplate.rgb("#110101"));
        colorForBarBlack.add(ColorTemplate.rgb("#787878"));
        colorForBarBlack.add(ColorTemplate.rgb("#231919"));


        colorForBarRose.add(ColorTemplate.rgb("#ff12aa"));
        colorForBarRose.add(ColorTemplate.rgb("#ff00a4"));
        colorForBarRose.add(ColorTemplate.rgb("#de008e"));
        colorForBarRose.add(ColorTemplate.rgb("#bd0079"));
        colorForBarRose.add(ColorTemplate.rgb("#a9006c"));
        colorForBarRose.add(ColorTemplate.rgb("#cd3597"));
        colorForBarRose.add(ColorTemplate.rgb("#c00e80"));
        colorForBarRose.add(ColorTemplate.rgb("#e036a3"));
        colorForBarRose.add(ColorTemplate.rgb("#ff97da"));
        colorForBarRose.add(ColorTemplate.rgb("#ba3e8e"));
        colorForBarRose.add(ColorTemplate.rgb("#60023e"));


        colorForBarR.add(ColorTemplate.rgb("#869c9b"));
        colorForBarR.add(ColorTemplate.rgb("#7eaeac"));
        colorForBarR.add(ColorTemplate.rgb("#598482"));
        colorForBarR.add(ColorTemplate.rgb("#58b1ad"));
        colorForBarR.add(ColorTemplate.rgb("#37aba6"));
        colorForBarR.add(ColorTemplate.rgb("#1f827d"));
        colorForBarR.add(ColorTemplate.rgb("#35d5ce"));
        colorForBarR.add(ColorTemplate.rgb("#05c2b9"));
        colorForBarR.add(ColorTemplate.rgb("#2ed8d0"));
        colorForBarR.add(ColorTemplate.rgb("#62fff8"));
        colorForBarR.add(ColorTemplate.rgb("#237975"));
        colorForBarR.add(ColorTemplate.rgb("#13726e"));


        colorForBarY.add(ColorTemplate.rgb("#cc0000"));
        colorForBarY.add(ColorTemplate.rgb("#ff0000"));
        colorForBarY.add(ColorTemplate.rgb("#fe5a5a"));
        colorForBarY.add(ColorTemplate.rgb("#ff2020"));
        colorForBarY.add(ColorTemplate.rgb("#ff9090"));
        colorForBarY.add(ColorTemplate.rgb("#c51414"));
        colorForBarY.add(ColorTemplate.rgb("#8e0202"));
        colorForBarY.add(ColorTemplate.rgb("#921717"));
        colorForBarY.add(ColorTemplate.rgb("#d35e5e"));
        colorForBarY.add(ColorTemplate.rgb("#970b0b"));


        colorForBarZ.add(ColorTemplate.rgb("#c67a00"));
        colorForBarZ.add(ColorTemplate.rgb("#ffbe60"));
        colorForBarZ.add(ColorTemplate.rgb("#ffd99b"));
        colorForBarZ.add(ColorTemplate.rgb("#e9bb71"));
        colorForBarZ.add(ColorTemplate.rgb("#e8ac4a"));
        colorForBarZ.add(ColorTemplate.rgb("#af7922"));
        colorForBarZ.add(ColorTemplate.rgb("#c77f09"));
        colorForBarZ.add(ColorTemplate.rgb("#7e5718"));

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//    }

//    private void setData(List<Data> dataList) {
//
//        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
//        String[] dates = new String[dataList.size()];
//        List<Integer> colors = new ArrayList<Integer>();
//
//        int green = Color.rgb(110, 190, 102);
//        int red = Color.rgb(211, 74, 88);
//
//        for (int i = 0; i < dataList.size(); i++) {
//
//            Data d = dataList.get(i);
//            BarEntry entry = new BarEntry(d.yValue, d.xIndex);
//            values.add(entry);
//
//            dates[i] = dataList.get(i).xAxisValue;
//
//            // specific colors
//            if (d.yValue >= 0)
//                colors.add(red);
//            else
//                colors.add(green);
//        }
//
//        BarDataSet set;
//
//        if (mChart.getData() != null &&
//                mChart.getData().getDataSetCount() > 0) {
//            set = (BarDataSet)mChart.getData().getDataSetByIndex(0);
//            set.setYVals(values);
//            mChart.getData().notifyDataChanged();
//            mChart.notifyDataSetChanged();
//        } else {
//            set = new BarDataSet(values, "Values");
//            set.setBarSpacePercent(50f);
//            set.setColors(colors);
//            set.setValueTextColors(colors);
//            BarData data = new BarData(dates, set);
//            data.setValueTextSize(13f);
////            data.setValueTypeface(mTf);
//            data.setValueFormatter(new ValueFormatter());
//            data.setDrawValues(false);
//            mChart.setData(data);
//            mChart.invalidate();
//        }
//    }


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    /**
     * Demo class representing data.
     */
    private class Data {

        public String xAxisValue;
        public float yValue;
        public int xIndex;

        public Data(int xIndex, float yValue, String xAxisValue) {
            this.xAxisValue = xAxisValue;
            this.yValue = yValue;
            this.xIndex = xIndex;
        }
    }

    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }



    public void callNetworkAnalysisService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<NetworkAnalysisModel> call = webServiceObj.callNetworkAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<NetworkAnalysisModel>() {
            @Override
            public void onResponse(Call<NetworkAnalysisModel> call, Response<NetworkAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success", "" + response.body());
                 incomeAnalysisModel = response.body();
//                ArrayList<Float> chartdatalist = new ArrayList<Float>();
//                ArrayList<String> chartitledatalist = new ArrayList<String>();
                float[] yData = null;
                float[] yIncomeData = null;
                String[] xData = null;
                int[] colour = new int[11];
                xDataValues= new String[8];
                ArrayList<BarEntry> yAxisChartValues = new ArrayList<BarEntry>();
//        yValues.add(new BarEntry(new float[]{ -10, -10,12 }, 0));


//                ArrayList<String> xVals=new ArrayList<String>();
                if (incomeAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    scrolllinearlayout.setVisibility(View.VISIBLE);
//                    List<Data> data = new ArrayList<>();
                    if (null != incomeAnalysisModel.getData().getNet_worth()) {
                        try {
                            String overallAmt = (incomeAnalysisModel.getData().getNet_worth().getAsst_det().getOver_annu_contri());
                            Float comm_gold = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getAsst_det().getComm_gold());
                            Float emp_ben = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getAsst_det().getEmp_ben());
                            Float equ = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getAsst_det().getEqu());
                            Float fix_inc = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getAsst_det().getFix_inc());
                            Float hou_asst = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getAsst_det().getHou_asst());
                            Float liq = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getAsst_det().getLiq());
                            Float real_prop = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getAsst_det().getReal_prop());
                            Float oth_asst = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getAsst_det().getOth_asst());

//                            Float credit_card = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getLiab_det().getCcard().getVal());
//                            Float loan_offer = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getLiab_det().getLn_off().getVal());
//                            Float loan = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getLiab_det().getLn().getVal());
//                            Float refundable_deposit = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getLiab_det().getRf_dep().getVal());
//                            Float unpalatable_bill = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getLiab_det().getUnpd_bills().getVal());
//                            Float other_liabilities = floatConvertion(incomeAnalysisModel.getData().getNet_worth().getLiab_det().getOth_liab().getVal());

                            String overalloanAmt = (incomeAnalysisModel.getData().getNet_worth().getLiab_det().getOver_loan_amt());
                        if(UtileKit.validateObjectValuesAndCheckZero(overalloanAmt)&&
                                UtileKit.validateObjectValuesAndCheckZero(overallAmt)) {
                            gold = incomeAnalysisModel.getData().getNet_worth().getAsst_det().getComm_gold();
                            employment_benefit = incomeAnalysisModel.getData().getNet_worth().getAsst_det().getEmp_ben();
                            equity = incomeAnalysisModel.getData().getNet_worth().getAsst_det().getEqu();
                            fixed_income = incomeAnalysisModel.getData().getNet_worth().getAsst_det().getFix_inc();
                            householde_asset = incomeAnalysisModel.getData().getNet_worth().getAsst_det().getHou_asst();
                            liguid = incomeAnalysisModel.getData().getNet_worth().getAsst_det().getLiq();
                            realestate = incomeAnalysisModel.getData().getNet_worth().getAsst_det().getReal_prop();
                            otherasset = incomeAnalysisModel.getData().getNet_worth().getAsst_det().getOth_asst();

//                            creditcard = incomeAnalysisModel.getData().getNet_worth().getLiab_det().getCcard().getVal();
//                            loanOffers = incomeAnalysisModel.getData().getNet_worth().getLiab_det().getLn_off().getVal();
//                            Loan = incomeAnalysisModel.getData().getNet_worth().getLiab_det().getLn().getVal();
//                            refundableDeposit = incomeAnalysisModel.getData().getNet_worth().getLiab_det().getRf_dep().getVal();
//                            unpaidBills = incomeAnalysisModel.getData().getNet_worth().getLiab_det().getUnpd_bills().getVal();
//                            otherLiabilities = incomeAnalysisModel.getData().getNet_worth().getLiab_det().getOth_liab().getVal();


                            if (comm_gold != null) {
                                comm_goldArray.add(comm_gold);
                                comm_goldArraycolor.add(ColorTemplate.rgb("#FF6D00"));
                                namecomm_goldArray.add("A - Commodities/Gold : " + "₹ " + (UtileKit.formatedNumber(comm_gold)));
                                Log.i("namecomm_goldArray", "namecomm_goldArray" + "A - Commodities/Gold : " + (UtileKit.formatedNumber(comm_gold)));

                            } else {
                                comm_goldArray.add(Float.valueOf(0));
                                comm_goldArraycolor.add(ColorTemplate.rgb("#FF6D00"));
                                namecomm_goldArray.add("A - Commodities/Gold : " + "₹ " + 0);
                                Log.i("namecomm_goldArray", "namecomm_goldArray" + "A - Commodities/Gold : " + (UtileKit.formatedNumber(comm_gold)));
                            }
                            if (emp_ben != null) {
                                emp_benArray.add(emp_ben);
                                emp_benArraycolor.add(ColorTemplate.rgb("#53adfc"));
                                nameemp_benArray.add("B - Employment Benefit : " + "₹ " + (UtileKit.formatedNumber(emp_ben)));
                            } else {
                                emp_benArray.add(Float.valueOf(0));
                                emp_benArraycolor.add(ColorTemplate.rgb("#53adfc"));
                                nameemp_benArray.add("B - Employment Benefit : " + "₹ " + 0);
                            }
                            if (equ != null) {
                                equArray.add(equ);
                                equArraycolor.add(ColorTemplate.rgb("#7d53fc"));
                                nameequArray.add("C - Equity : " + "₹ " + (UtileKit.formatedNumber(equ)));
                            } else {
                                equArray.add(Float.valueOf(0));
                                equArraycolor.add(ColorTemplate.rgb("#7d53fc"));
                                nameequArray.add("C - Equity : " + "₹ " + 0);
                            }
                            if (fix_inc != null) {
                                fix_incArray.add(fix_inc);
                                fix_incArrayArraycolor.add(ColorTemplate.rgb("#8BC34A"));
                                namefix_incArray.add("D - Fixed Income/Debt : " + "₹ " + (UtileKit.formatedNumber(fix_inc)));
                            } else {
                                fix_incArray.add(Float.valueOf(0));
                                fix_incArrayArraycolor.add(ColorTemplate.rgb("#8BC34A"));
                                namefix_incArray.add("D - Fixed Income/Debt : " + "₹ " + 0);
                            }
                            if (hou_asst != null) {
                                hou_asstArray.add(hou_asst);
                                hou_asstArraycolor.add(ColorTemplate.rgb("#FFC107"));
                                namehou_asstArray.add("E - Household Asset : " + "₹ " + (UtileKit.formatedNumber(hou_asst)));
                            } else {
                                hou_asstArray.add(Float.valueOf(0));
                                hou_asstArraycolor.add(ColorTemplate.rgb("#FFC107"));
                                namehou_asstArray.add("E - Household Asset : " + "₹ " + 0);
                            }
                            if (liq != null) {
                                liqArray.add(liq);
                                liqArraycolor.add(ColorTemplate.rgb("#5386fc"));
                                nameliqArray.add("F - Liquid : " + "₹ " + (UtileKit.formatedNumber(liq)));
                            } else {
                                liqArray.add(Float.valueOf(0));
                                liqArraycolor.add(ColorTemplate.rgb("#5386fc"));
                                nameliqArray.add("F - Liquid : " + "₹ " + 0);
                            }
                            if (real_prop != null) {
                                real_propArray.add(real_prop);
                                real_propArraycolor.add(ColorTemplate.rgb("#c18911"));
                                namereal_propArray.add("G - Real Estate/Property : " + "₹ " + (UtileKit.formatedNumber(real_prop)));
                            } else {
                                real_propArray.add(Float.valueOf(0));
                                real_propArraycolor.add(ColorTemplate.rgb("#c18911"));
                                DecimalFormat myFormatter = new DecimalFormat("#,##,###");
                                // String output = myFormatter.format(0);
                                namereal_propArray.add("G - Real Estate/Property : " + "₹ " + 0);
                            }
                            if (oth_asst != null) {
                                oth_asstArray.add(oth_asst);
                                oth_asstArraycolor.add(ColorTemplate.rgb("#da53fc"));
                                nameoth_asstArray.add("H - Other Asset : " + "₹ " + (UtileKit.formatedNumber(oth_asst)));
                            } else {
                                oth_asstArray.add(Float.valueOf(0));
                                oth_asstArraycolor.add(ColorTemplate.rgb("#da53fc"));
                                nameoth_asstArray.add("H - Other Asset : " + "₹ " + 0);
                            }


                            ArrayList<String[]> LinkedArray = new ArrayList<>();

                            addLinkedArrayList(incomeAnalysisModel.getData().getNet_worth().getLiab_det().getCcard().getLink_to()
                            ,mCcardOtherList,"Credit Card",ColorTemplate.rgb("#0091EA"),LinkedArray);

                            addLinkedArrayList(incomeAnalysisModel.getData().getNet_worth().getLiab_det().getLn().getLink_to()
                                    ,mLnOtherList,"Loan",ColorTemplate.rgb("#006064"),LinkedArray);

                            addLinkedArrayList(incomeAnalysisModel.getData().getNet_worth().getLiab_det().getLn_off().getLink_to()
                                    ,mLn_offOtherList,"Loan off",ColorTemplate.rgb("#00BFA5"),LinkedArray);

                            addLinkedArrayList(incomeAnalysisModel.getData().getNet_worth().getLiab_det().getRf_dep().getLink_to()
                                    ,mRfOtherList,"Refundable Deposit",ColorTemplate.rgb("#00C853"),LinkedArray);

                            addLinkedArrayList(incomeAnalysisModel.getData().getNet_worth().getLiab_det().getUnpd_bills().getLink_to()
                                    ,mUnpd_billsOtherList,"Unpaid bills",ColorTemplate.rgb("#64DD17"),LinkedArray);



                            addLinkedArrayList(incomeAnalysisModel.getData().getNet_worth().getLiab_det().getOth_liab().getLink_to()
                                    ,mOth_liabOtherList,"Other liabilites",ColorTemplate.rgb("#ffc3a0"),LinkedArray);



//                            LinkedArray = new ArrayList<>();
//                            LinkedArray.add((incomeAnalysisModel.getData().getNet_worth().getLiab_det().getRf_dep().getLink_to()));
//                            setAssetLink(LinkedArray, mRfOtherList, "Refundable Deposit", ColorTemplate.rgb("#00C853"));
//                            LinkedArray = new ArrayList<>();
//                            LinkedArray.add((incomeAnalysisModel.getData().getNet_worth().getLiab_det().getUnpd_bills().getLink_to()));
//                            setAssetLink(LinkedArray, mUnpd_billsOtherList, "Unpaid bills", ColorTemplate.rgb("#64DD17"));
//                            LinkedArray = new ArrayList<>();
//                            LinkedArray.add((incomeAnalysisModel.getData().getNet_worth().getLiab_det().getOth_liab().getLink_to()));
//                            setAssetLink(LinkedArray, mOth_liabOtherList, "Other liabilites", ColorTemplate.rgb("#ffc3a0"));

                            addcolorInValueLinks(mCcardOtherList, "I - Credit Card  ", namecreditcardArray,
                                    cc_Arraycolor, "#0091EA");
                            addcolorInValueLinks(mLn_offOtherList, "J - Loan Offers  ", nameloanOffersArray,
                                    loanOffer_Arraycolor, "#00BFA5");
                            addcolorInValueLinks(mLnOtherList, "K - Loan  ", nameLoanArray, loan_Arraycolor, "#006064");
                            addcolorInValueLinks(mRfOtherList, "L - Refundable Deposit "
                                    , namerefundableDepositArray, refusedable_Arraycolor, "#00C853");
                            addcolorInValueLinks(mUnpd_billsOtherList, "M - Unpaid Bills "
                                    , nameunpaidBillsArray, unbill_Arraycolor, "#64DD17");
                            addcolorInValueLinks(mOth_liabOtherList, "N - Other Liabilities "
                                    , nameotherLiabilitiesArraylist, otherlib_Arraycolor, "#ffc3a0");
//                            if (!mCcardOtherList.isEmpty()) {
//
//                                mCcardOtherList.add(credit_card);
//                                cc_Arraycolor.add(ColorTemplate.rgb("#0091EA"));
//                                namecreditcardArray.add("I - Credit Card  " );
//                                for (Float mccards: mCcardOtherList) {
//                                    namecreditcardArray.add("I - Credit Card : " +"₹ "+ (UtileKit.formatedNumber(mccards)));
//
//                                }
//                            }
//                            else{
//                                mCcardOtherList.add(Float.valueOf(0));
//                                cc_Arraycolor.add(ColorTemplate.rgb("#0091EA"));
//                                namecreditcardArray.add("I - Credit Card : " +"₹ "+ 0);
//                            }
//                            if (loan_offer != null) {
//                                mLn_offOtherList.add(loan_offer);
//                                loanOffer_Arraycolor.add(ColorTemplate.rgb("#00BFA5"));
//                                nameloanOffersArray.add("J - Loan Offers : " +"₹ "+ (UtileKit.formatedNumber(loan_offer)));
//                            }
//                            else{
//                                mLn_offOtherList.add(Float.valueOf(0));
//                                loanOffer_Arraycolor.add(ColorTemplate.rgb("#00BFA5"));
//                                nameloanOffersArray.add("J - Loan Offers : " +"₹ "+ 0);
//                            }
//                            if (loan != null) {
//                                mLnOtherList.add(loan);
//                                loan_Arraycolor.add(ColorTemplate.rgb("#006064"));
//                                nameLoanArray.add("K - Loan : " +"₹ "+ (UtileKit.formatedNumber(loan)));
//                            }
//                            else{
//                                mLnOtherList.add(Float.valueOf(0));
//                                loan_Arraycolor.add(ColorTemplate.rgb("#006064"));
//                                nameLoanArray.add("K - Loan : " +"₹ "+ 0);
//                            }
//                            if (refundable_deposit != null) {
//                                mRfOtherList.add(refundable_deposit);
//                                refusedable_Arraycolor.add(ColorTemplate.rgb("#00C853"));
//                                namerefundableDepositArray.add("L - Refundable Deposit : " +"₹ "+ (UtileKit.formatedNumber(refundable_deposit)));
//                            }
//                            else{
//                                mRfOtherList.add(Float.valueOf(0));
//                                refusedable_Arraycolor.add(ColorTemplate.rgb("#00C853"));
//                                namerefundableDepositArray.add("L - Refundable Deposit : " +"₹ "+ 0);
//                            }


//                            if (unpalatable_bill != null) {
//                                mUnpd_billsOtherList.add(unpalatable_bill);
//                                unbill_Arraycolor.add(ColorTemplate.rgb("#64DD17"));
//                                nameunpaidBillsArray.add("M - Unpaid Bills : " +"₹ "+ (UtileKit.formatedNumber(unpalatable_bill)));
//                            }
//                            else{
//                                mUnpd_billsOtherList.add(Float.valueOf(0));
//                                unbill_Arraycolor.add(ColorTemplate.rgb("#64DD17"));
//                                nameunpaidBillsArray.add("M - Unpaid Bills  : " +"₹ "+ 0);
//                            }

//                            if (other_liabilities != null) {
//                                mOth_liabOtherList.add(other_liabilities);
//                                otherlib_Arraycolor.add(ColorTemplate.rgb("#ffc3a0"));
//                                nameotherLiabilitiesArraylist.add("N - Other Liabilities : " +"₹ "+ (UtileKit.formatedNumber(other_liabilities)));
//                            }
//                            else{
//                                mOth_liabOtherList.add(Float.valueOf(0));
//                                otherlib_Arraycolor.add(ColorTemplate.rgb("#ffc3a0"));
//                                nameotherLiabilitiesArraylist.add("N - Other Liabilities  : " +"₹ "+ 0);
//                            }


                            if (!mOth_liabOtherList.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(mOth_liabOtherList), 13));

                            if (!mUnpd_billsOtherList.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(mUnpd_billsOtherList), 12));

                            if (!mRfOtherList.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(mRfOtherList), 11));

                            if (!mLnOtherList.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(mLnOtherList), 10));

                            if (!mLn_offOtherList.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(mLn_offOtherList), 9));


                            if (!mCcardOtherList.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(mCcardOtherList), 8));

                            if (!oth_asstArray.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(oth_asstArray), 7));
//                                    colour[7] = ColorTemplate.rgb("#da53fc");
                            if (!real_propArray.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(real_propArray), 6));
//                                    colour[6] = ColorTemplate.rgb("#c18911");
                            if (!liqArray.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(liqArray), 5));
//                                    colour[5] = ColorTemplate.rgb("#5386fc");
                            if (!hou_asstArray.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(hou_asstArray), 4));
//                                    colour[4] = ColorTemplate.rgb("#FFC107");
                            if (!fix_incArray.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(fix_incArray), 3));
//                                    colour[3] = ColorTemplate.rgb("#8BC34A");
                            if (!equArray.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(equArray), 2));
//                                    colour[2]=   ColorTemplate.rgb("#7d53fc");
                            if (!emp_benArray.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(emp_benArray), 1));
//                                    colour[1] = ColorTemplate.rgb("#53fcf3");
                            if (!comm_goldArray.isEmpty())
                                yAxisChartValues.add(new BarEntry(getFloatArray(comm_goldArray), 0));
//                                    colour[0] = ColorTemplate.rgb("#53adfc");
//                                xVals.add("CommGold");


                            colorarray.addAll(otherlib_Arraycolor);
                            colorarray.addAll(unbill_Arraycolor);
                            colorarray.addAll(refusedable_Arraycolor);
                            colorarray.addAll(loan_Arraycolor);
                            colorarray.addAll(loanOffer_Arraycolor);
                            colorarray.addAll(cc_Arraycolor);
//                                colorarray.addAll(nameothercolor);
                            colorarray.addAll(oth_asstArraycolor);
                            colorarray.addAll(real_propArraycolor);
                            colorarray.addAll(liqArraycolor);
                            colorarray.addAll(hou_asstArraycolor);
                            colorarray.addAll(fix_incArrayArraycolor);
                            colorarray.addAll(equArraycolor);
                            colorarray.addAll(emp_benArraycolor);
                            colorarray.addAll(comm_goldArraycolor);


                            BarDataSet set = new BarDataSet(yAxisChartValues, "");
                            set.setValueFormatter(new ChartValueFormatter());
                            set.setValueTextSize(7f);
                            set.setAxisDependency(YAxis.AxisDependency.LEFT);
                            set.setBarSpacePercent(40f);
                            set.setColors(colorarray);
//                                ArrayList<Integer> obj=new ArrayList<Integer>(Arrays.asList(chartColor));
//                                set.setColor(obj);
                            set.setDrawValues(false);
//                        set.setStackLabels(linkto);

                            //    String[] xVals = new String[]{"0-10", "10-20", "20-30", "30-40", "40-50", "50-60", "60-70", "70-80", "80-90", "90-100", "100+"};

                            BarData data = new BarData(linktoInAlphabert, set);
                            mChart.setData(data);
                            mChart.invalidate();
                            mChart.setDrawValueAboveBar(false);
                            mChart.setExtraOffsets(5, 10, 20, 5);
                            Legend l = mChart.getLegend();
                            l.setEnabled(false);
                            l.setTextSize(8 * getResources().getDisplayMetrics().density);
                            l.setFormSize(15f);
                            l.setWordWrapEnabled(true);
                            l.setXEntrySpace(15f);
                            mChart.getLegend().setEnabled(false);
                            setDataForCheckbox(namecomm_goldArray, comm_goldArraycolor, parentView, mContext);
                            setDataForCheckbox(nameemp_benArray, emp_benArraycolor, parentView, mContext);
                            setDataForCheckbox(nameequArray, equArraycolor, parentView, mContext);
                            setDataForCheckbox(namefix_incArray, fix_incArrayArraycolor, parentView, mContext);
                            setDataForCheckbox(namehou_asstArray, hou_asstArraycolor, parentView, mContext);
                            setDataForCheckbox(nameliqArray, liqArraycolor, parentView, mContext);
                            setDataForCheckbox(namereal_propArray, real_propArraycolor, parentView, mContext);
                            setDataForCheckbox(nameoth_asstArray, oth_asstArraycolor, parentView, mContext);
                            // libaties start to set data in lib
                            setDataForCheckbox(namecreditcardArray, cc_Arraycolor, parentView, mContext);
                            setDataForCheckbox(nameloanOffersArray, loanOffer_Arraycolor, parentView, mContext);
                            setDataForCheckbox(nameLoanArray, loan_Arraycolor, parentView, mContext);
                            setDataForCheckbox(nameunpaidBillsArray, unbill_Arraycolor, parentView, mContext);
                            setDataForCheckbox(namerefundableDepositArray, refusedable_Arraycolor, parentView, mContext);
                            setDataForCheckbox(nameotherLiabilitiesArraylist, otherlib_Arraycolor, parentView, mContext);


//                                addArrayList=new HashMap<Integer,String>();
                            addArrayList.put(0, namecomm_goldArray);
                            addArrayList.put(1, nameemp_benArray);
                            addArrayList.put(2, nameequArray);
                            addArrayList.put(3, namefix_incArray);
                            addArrayList.put(4, namehou_asstArray);
                            addArrayList.put(5, nameliqArray);
                            addArrayList.put(6, namereal_propArray);
                            addArrayList.put(7, nameoth_asstArray);
                            addArrayList.put(8, namecreditcardArray);
                            addArrayList.put(9, nameloanOffersArray);
                            addArrayList.put(10, nameLoanArray);
                            addArrayList.put(11, namerefundableDepositArray);
                            addArrayList.put(12, nameunpaidBillsArray);
                            addArrayList.put(13, nameotherLiabilitiesArraylist);

                            addColorList.put(0, comm_goldArraycolor);
                            addColorList.put(1, emp_benArraycolor);
                            addColorList.put(2, equArraycolor);
                            addColorList.put(3, fix_incArrayArraycolor);
                            addColorList.put(4, hou_asstArraycolor);
                            addColorList.put(5, liqArraycolor);
                            addColorList.put(6, real_propArraycolor);
                            addColorList.put(7, oth_asstArraycolor);
                            addColorList.put(8, cc_Arraycolor);
                            addColorList.put(9, loanOffer_Arraycolor);
                            addColorList.put(10, loan_Arraycolor);
                            addColorList.put(11, refusedable_Arraycolor);
                            addColorList.put(12, unbill_Arraycolor);
                            addColorList.put(13, otherlib_Arraycolor);


                            mnetworthadapter = new NetworthExpandableadapter(mContext, addArrayList, addColorList);
                            mnetworth_expandlist.setAdapter(mnetworthadapter);
                            for (int i = 0; i < mnetworthadapter.getGroupCount(); i++)
                                mnetworth_expandlist.expandGroup(i);
                            setListViewHeight(mnetworth_expandlist);
//                            }
                        }else{
                            empty_chart_display.setVisibility(View.VISIBLE);
                            empty_chart_display.setText(HomePageActivity.errorMessageInChart);
                            UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                            scrolllinearlayout.setVisibility(View.GONE);
                        }
                        }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }

                    UtileKit.dismisssSpinnerDialog();
                }


            @Override
            public void onFailure(Call<NetworkAnalysisModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }

    private void addLinkedArrayList(String[] link_to, ArrayList<Float> mCcardOtherList, String typeOfLink,
                                    int rgb, ArrayList<String[]> linkedArray) {
        linkedArray = new ArrayList<>();
        if(link_to!= null){
            linkedArray.add((link_to));
            setAssetLink(linkedArray, mCcardOtherList, typeOfLink, rgb);
        }

    }

    private void addcolorInValueLinks(ArrayList<Float> mList, String mString,
                                      ArrayList<String> nameArray, ArrayList<Integer> mArraycolor, String colorString) {

        if (!mList.isEmpty()) {
            for (Float mccards: mList) {
                nameArray.add(mString + " : " +"₹ "+ (UtileKit.formatedNumber(Math.abs(mccards))));
                mArraycolor.add(ColorTemplate.rgb(colorString));
            }
        }
        else{
            mList.add(Float.valueOf(0));
            mArraycolor.add(ColorTemplate.rgb(colorString));
            nameArray.add(mString + " : " +"₹ "+ 0);
        }

    }

    private void setAssetLink(ArrayList<String[]> LinkedArray, ArrayList<Float> ccard, String loan, int rgb) {

            int size = LinkedArray.size();
            if (size != 0) {

                for (int i = 0; i < size; i++) {

                    String[] commgold = LinkedArray.get(i);
                    for (String splitObj : commgold) {
                        String splitArray[] = splitObj.split("-");
                        //Log.e("success", "splitObj " + splitObj);

                            if (splitArray[0].trim().replace(" ", "").contains(linkto[0].trim().replace(" ", ""))) {
                                if (splitArray[1].length() != 0 && UtileKit.validateObjectValues(comm_goldArray.size())) {
                                    comm_goldArray.add(-floatConvertion(splitArray[1]));
                                    comm_goldArraycolor.add(colorForBarYellow.get(comm_goldArray.size()));
                                    namecomm_goldArray.add(splitArray[0] + " : "+"₹ "+(UtileKit.formatedNumber(Float.valueOf(splitArray[1]))));

                                }
                            }
                            else if (splitArray[0].trim().replace(" ", "").contains(linkto[1].trim().replace(" ", ""))) {
                                if (splitArray[1].length() != 0 && UtileKit.validateObjectValues(emp_benArray.size())) {
                                    emp_benArray.add(-floatConvertion(splitArray[1]));
                                    emp_benArraycolor.add(colorForBarBlue.get(emp_benArray.size()));
                                    nameemp_benArray.add(splitArray[0]+ " : "+"₹ "+(UtileKit.formatedNumber(Float.valueOf(splitArray[1]))));
                                }
                            } else if (splitArray[0].trim().replace(" ", "").contains(linkto[2].trim().replace(" ", ""))) {
                            if (splitArray[1].length() != 0 && UtileKit.validateObjectValues(equArray.size())) {
                                equArray.add(-floatConvertion(splitArray[1]));
                                equArraycolor.add(colorForBarRed.get(equArray.size()));
                                nameequArray.add(splitArray[0]+ " : "+"₹ "+(UtileKit.formatedNumber(Float.valueOf(splitArray[1]))));
                            }
                        } else if (splitArray[0].trim().replace(" ", "").contains(linkto[3].trim().replace(" ", ""))) {
                            if (splitArray[1].length() != 0 && UtileKit.validateObjectValues(fix_incArray.size())) {
                                fix_incArray.add(-floatConvertion(splitArray[1]));
                                fix_incArrayArraycolor.add(colorForBarGreen.get(fix_incArray.size()));
                                namefix_incArray.add(splitArray[0]+ " : "+"₹ "+(UtileKit.formatedNumber(Float.valueOf(splitArray[1]))));
                            }
                        } else if (splitArray[0].trim().replace(" ", "").contains(linkto[4].trim().replace(" ", ""))) {
                            if (splitArray[1].length() != 0 && UtileKit.validateObjectValues(hou_asstArray.size())) {
                                hou_asstArray.add(-floatConvertion(splitArray[1]));
                                hou_asstArraycolor.add(colorForBarRose.get(hou_asstArray.size()));
                                namehou_asstArray.add(splitArray[0]+ " : "+"₹ "+(UtileKit.formatedNumber(Float.valueOf(splitArray[1]))));
                            }
                        } else if (splitArray[0].trim().replace(" ", "").contains(linkto[5].trim().replace(" ", ""))) {
                            if (splitArray[1].length() != 0 && UtileKit.validateObjectValues(liqArray.size())) {
                                liqArray.add(-floatConvertion(splitArray[1]));
                                liqArraycolor.add(colorForBarBlack.get(liqArray.size()));
                                nameliqArray.add(splitArray[0]+ " : "+"₹ "+splitArray[1]);
                            }
                        } else if (splitArray[0].trim().replace(" ", "").contains(linkto[6].trim().replace(" ", ""))) {
                            if (splitArray[1].length() != 0 && UtileKit.validateObjectValues(splitArray[1]) && real_propArray!= null) {
                                real_propArray.add(-floatConvertion(splitArray[1]));
                                real_propArraycolor.add(colorForBarY.get(real_propArray.size()));
                                namereal_propArray.add(splitArray[0]+ " : "+"₹ "+(UtileKit.formatedNumber(Float.valueOf(splitArray[1]))));
                            }
                        } else if (splitArray[0].trim().replace(" ", "").contains(linkto[7].trim().replace(" ", ""))) {
                            if (splitArray[1].length() != 0 && UtileKit.validateObjectValues(oth_asstArray.size())) {
                                oth_asstArray.add(-floatConvertion(splitArray[1]));
                                oth_asstArraycolor.add(colorForBarR.get(oth_asstArray.size()));
                                nameoth_asstArray.add(splitArray[0]+ " : "+"₹ "+(UtileKit.formatedNumber(Float.valueOf(splitArray[1]))));
                            }
                        }
                        else  if(splitArray[0].trim().replace(" ","").length()==0)
                            {
                                if (splitArray[1].length() != 0)
                                {
                                    ccard.add(-floatConvertion(splitArray[1]));
                                    nameothercolor.add(rgb);
                                    nameotherArraylist.add(loan);
                                }
                            }
                    }
                }

            }


    }

    private float[] getFloatArray(List<Float> comm_goldArray) {
        float array[]=new float[comm_goldArray.size()];
        for (int i=0;i<comm_goldArray.size();i++)
        {
            array[i]=comm_goldArray.get(i);
        }
        return array;
    }

    private float floatConvertion(String val) {
        float convVal = 0;
        try {
            convVal = Float.parseFloat(val);
        } catch (NumberFormatException e) {

        }
        return convVal;
    }

    public int getDeviceWidth() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_detail:
                NetworthDetailsFragment networthDetailsFragment = new NetworthDetailsFragment();
                showFragment(networthDetailsFragment);
                break;
            case R.id.menu_summary:
                NetworthSummaryFragment networthSummaryFragment = new NetworthSummaryFragment();
                showFragment(networthSummaryFragment);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
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
//                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // ExpensesDetailsFragment fragment = new ExpensesDetailsFragment();
                //ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
//                AssetsAnalysisFragment fragment =
                  addFragmenttoStack(new AssetsAnalysisFragment());
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();

            }
            break;

        }
    }
    private  void setDataForCheckbox(ArrayList<String> xDataCheckbox, ArrayList colour,
                                     LinearLayout parentView, Context mContext) {
        try {
            Log.i("InsuranceAnalysis", " InsuranceAnalysis setDataForCheckbox is " + xDataCheckbox.size() );

            for (int i = 0; i <  xDataCheckbox.size(); i++) {

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
                TextView txt_unit = new TextView(mContext);
                if(i >=1){
                    parms_legen_layout.setMargins(50, 0, 0, 0);
                    txt_unit.setPadding(20,0,0,0);
                    txt_unit.setText(xDataCheckbox.get(i));
                }else{
                    txt_unit.setText(xDataCheckbox.get(i));
                }
                legend_layout.setBackgroundColor((Integer) colour.get(i));
                left_layout.addView(legend_layout);
                Log.i("InsuranceAnalysis", " InsuranceAnalysis setDataForCheckbox is xDataCheckbox " + xDataCheckbox.get(i) );
                Log.i("InsuranceAnalysis", " InsuranceAnalysis setDataForCheckbox is color " + colour.get(i) );
                txt_unit.setText(xDataCheckbox.get(i));
                left_layout.addView(txt_unit);
//                i++;
//                if ( (xDataCheckbox.length) == i) {
//                    parent_layout.addView(left_layout);
//                    parentView.addView(parent_layout);
//                    break;
//                }
//
//                LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                parms_right_layout.weight = 1F;
//                LinearLayout right_layout = new LinearLayout(mContext);
//                right_layout.setOrientation(LinearLayout.HORIZONTAL);
//                right_layout.setGravity(Gravity.LEFT);
//                parent_param_layout.setMargins(10, 0, 0, 10);
//                right_layout.setLayoutParams(parms_right_layout);
//
//
//                LinearLayout.LayoutParams parms_rightlegend_layout = new LinearLayout.LayoutParams(45, 45);
//                parms_rightlegend_layout.setMargins(0, 0, 20, 0);
//                LinearLayout right_legend_layout = new LinearLayout(mContext);
//                right_legend_layout.setLayoutParams(parms_rightlegend_layout);
//                right_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
//                right_legend_layout.setBackgroundColor(colour[i]);
//                right_layout.addView(right_legend_layout);
//
//                TextView right_txt_unit = new TextView(mContext);
//                right_txt_unit.setText( xDataCheckbox[i]);
//                right_layout.addView(right_txt_unit);
//
//                parent_layout.addView(right_layout);
                parent_layout.addView(left_layout);
                parentView.addView(parent_layout);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
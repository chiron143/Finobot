package com.purplepath.purplepath.cashflowmanagmentchart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
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
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.ChartValueFormatter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.cashflowmanagmentchart.model.Cash_mang_det;
import com.purplepath.purplepath.cashflowmanagmentchart.model.Cashflow;
import com.purplepath.purplepath.cashflowmanagmentchart.model.GetcashflowAssetflowModel;
import com.purplepath.purplepath.cashflowmanagmentchart.model.GetcashflowinoutflowModel;
import com.purplepath.purplepath.cashflowmanagmentchart.model.IncomeExpenseCashFlowModel;
import com.purplepath.purplepath.cashmanaganalysis.CashManagemntAnalysis;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.insuranceAnalysis.InsuranceAnalysis;
import com.purplepath.purplepath.model.GetcashflowGoalInvestmentModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.User_tax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.getPersistedPurplePathPref;

/**
 * Created by dinesh on 25/11/16.
 */
public class CashflowChartFragment extends BaseFragment implements
        OnChartValueSelectedListener, View.OnClickListener {
    protected HorizontalBarChart mChart;
    //    private String linkto[] = {"Commodities/Gold", "Employment Benefit", "Equity", "Fixed Income/Debt", "Household Asset", "Liquid","Real Estate/Property","other Asset"};
    int[] chartColor = {Color.rgb(181, 205, 26), Color.rgb(2, 71, 254),

    };

    int[] chartColor1 = {Color.rgb(181, 205, 26), Color.rgb(2, 71, 254), Color.rgb(4, 100, 23),

    };

    private TaxCashFlowModel taxAnalysisCashFlowChartModel;
    ArrayList<Integer> colorList = new ArrayList<Integer>(Arrays.asList(ColorTemplate.rgb("#da53fc"),
            ColorTemplate.rgb("#c18911"),
            ColorTemplate.rgb("#5386fc")
            /*ColorTemplate.rgb("#FFC107"),
            ColorTemplate.rgb("#8BC34A"),
            ColorTemplate.rgb("#7d53fc"),
            ColorTemplate.rgb("#53fcf3"),
            ColorTemplate.rgb("#53adfc"),
            Color.rgb(181, 205, 26)*/
    ));
    private int incExpStackSize = 9;
    int[] incExpColorArray = new int[]{ColorTemplate.rgb("#da53fc"),
            ColorTemplate.rgb("#c18911"),
            ColorTemplate.rgb("#5386fc"),
            ColorTemplate.rgb("#FFC107"),
            ColorTemplate.rgb("#8BC34A"),
            ColorTemplate.rgb("#7d53fc"),
            ColorTemplate.rgb("#53fcf3"),
            ColorTemplate.rgb("#53adfc"),
            Color.rgb(181, 205, 26)};

    int[] incAssetColorArray = new int[]{
            ColorTemplate.rgb("#da53fc"),
            ColorTemplate.rgb("#c18911"),
            ColorTemplate.rgb("#FFC107"),
            ColorTemplate.rgb("#8BC34A"),
            };

    float cashFlow;
    private int[] surplusDeficitColor;
    private LinearLayout checkboxLayout;
    private Typeface tf;
    private Context mContext;
    private LinearLayout parentView, scrolllinearlayout;
    private ArrayList<CheckBox> userNameCheckBox = new ArrayList<>();
    int checkbox_position = 0;
    String xData[] = {"Overall Cash Flow", "Surplus and Deficit", "Income Expense Cash Flow", "Tax Cash Flow Chart", "Asset Liability", "Goal and Investment"};
    private GetcashflowinoutflowModel mGetcashflowinoutflowModel;
    private GetcashflowAssetflowModel mGetcashflowassetflowModel;
    private GetcashflowGoalInvestmentModel getcashflowGoalInvestmentModel;
    private IncomeExpenseCashFlowModel incomeExpenseCashFlowModel;
    private OnActivityBackPressedListener mCallBackListener;
    private TextView errorTextview, textViewUnitApperances;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    ArrayList<Float> unitCount = new ArrayList<Float>();
    ArrayList<Float> unitCountCashFlow = new ArrayList<Float>();
    int calculate = 1;
    String[] xDataString = {"Cash In Flow", "Cash Out Flow"};
    String[] xAssetString = {"Years Pass", "Years Remain"};
    String[] xDataSurplusDeficit = {"Deficit", "Surplus"};

    private LinearLayout parent_layout;
    View barView;


    int checkbox_position_return = 0;


    public static CashflowChartFragment newInstance(int checkbox_position) {
        CashflowChartFragment CashflowChartFragment = new CashflowChartFragment();
        Bundle args = new Bundle();
        if (checkbox_position != 0) {
            args.putSerializable("checkbox_position", checkbox_position);
        }
        CashflowChartFragment.setArguments(args);
        return CashflowChartFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        if (barView == null) {
            barView = inflater.inflate(R.layout.fragment_cash_flowmang_chart, container, false);
            parentView = barView.findViewById(R.id.parentViewId);
            checkboxLayout = barView.findViewById(R.id.check_group1Id);
//        checkboxLayout.removeAllViews(); parentView.removeAllViews();
            scrolllinearlayout = barView.findViewById(R.id.scrolllinearlayout);
            errorTextview = barView.findViewById(R.id.empty_chart_display);
            textViewUnitApperances = barView.findViewById(R.id.textViewUnitApperances);
            mCallBackListener.setActionBarTitle("My Cash Flow");
            mleftRelativeLayout = barView.findViewById(R.id.relative_left_arrow);
            mcenterRelativeLayout = barView.findViewById(R.id.relative_center_home);
            mRightRelativeLayout = barView.findViewById(R.id.relative_right_arrow);
            mRightRelativeLayout.setVisibility(View.GONE);
            mleftRelativeLayout.setOnClickListener(this);
            mcenterRelativeLayout.setOnClickListener(this);
            mRightRelativeLayout.setOnClickListener(this);

            Bundle args = getArguments();
            //checkbox selection getting from cashflowchart fragment
            try {
                if (args != null) {
                    if (args.containsKey("checkbox_position")) {
                        checkbox_position_return = args.getInt("checkbox_position");

                        Log.d("checkbox_position", "checkbox_position_returnss" + checkbox_position_return);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                int width = getDeviceWidth();
                double d = width * 0.25;
                int chartsize = (int) (d);
                mChart = barView.findViewById(R.id.chart1);
                mChart.setOnChartValueSelectedListener(this);
                mChart.setLayoutParams(new LinearLayout.LayoutParams(width, width + width / 4));
                mChart.setDrawGridBackground(false);
                mChart.setDescription("");
                mChart.getXAxis().setEnabled(true);
                mChart.getAxisLeft().setEnabled(false);
                mChart.getAxisRight().setEnabled(true);

                mChart.setPinchZoom(false);
                mChart.setDoubleTapToZoomEnabled(false);
                mChart.setDrawBarShadow(false);
                mChart.setDrawValueAboveBar(false);
                mChart.setTouchEnabled(true);

                mChart.getAxisRight().setDrawGridLines(false);
                mChart.getAxisRight().setDrawZeroLine(true);
                mChart.getAxisRight().setLabelCount(7, false);

                mChart.getAxisRight().setValueFormatter(new ChartValueFormatter());
                mChart.getAxisRight().setTextSize(9f);

                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                xAxis.setDrawGridLines(false);
                xAxis.setDrawAxisLine(false);
                xAxis.setTextSize(9f);

                Legend l = mChart.getLegend();
                l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
                l.setFormSize(8f);
                l.setFormToTextSpace(4f);
                l.setXEntrySpace(6f);

                callTaxAnalysisService();// Services call here ;
                callCashflowAnalysisService();
                callIncomeExpenseCashFlowService();
                callAssetService();
                callGoasInvestService();
                //callCashflowAssetService();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        try{
//            if(checkbox_position_return ==0) {
////                checkBoxOnClick(checkbox_position_return, true);
//                AddCheckBoxView(xData);
//            }else if(checkbox_position_return ==1) {
////                checkBoxOnClick(checkbox_position_return, true);
//                AddCheckBoxView(xData);
//            }
//            else if(checkbox_position_return ==2) {
////                checkBoxOnClick(checkbox_position_return, true);
//                AddCheckBoxView(xData);
//            }else if(checkbox_position_return ==3) {
////                checkBoxOnClick(checkbox_position_return, true);
//                AddCheckBoxView(xData);
//            }
//
//
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        return barView;
    }

    private void descriptiondatapass(User_tax user_tax) {
        Fragment fragment = new TaxAnalysisDrillDown();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();

        if (user_tax != null) {
            bundle.putSerializable("user_tax_position_cash_flow", user_tax);
            Log.i("TaxCashFlowChart", "getTotal_savings pass" + user_tax.getTotal_savings());
        }

        fragment.setArguments(bundle);
        addFragmenttoStack(fragment);
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
    }

    private void descriptiondatapass1(Cashflow user_tax) {
        Fragment fragment = new TaxAnalysisDrillDown();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();

        if (user_tax != null) {
            bundle.putSerializable("user_tax_position_cash_flow", user_tax);
            Log.i("TaxCashFlowChart", "getTotal_savings pass" + user_tax.getAssetArray());
        }

        fragment.setArguments(bundle);
        addFragmenttoStack(fragment);
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
    }





    private void callAssetService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetcashflowAssetflowModel> call = obj.callCashFlowAssetService(getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetcashflowAssetflowModel>() {
            @Override
            public void onResponse(Call<GetcashflowAssetflowModel> call, Response<GetcashflowAssetflowModel> response) {

                UtileKit.dismisssSpinnerDialog();
                mGetcashflowassetflowModel = response.body();
                Log.i("Cash Flow Chart", "get_income_expense_cashflow_by_user incomeExpenseCashFlowModel" + incomeExpenseCashFlowModel);
                if (mGetcashflowassetflowModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    scrolllinearlayout.setVisibility(View.VISIBLE);
                } else {
                    ErrorChartView();
                }

            }

            @Override
            public void onFailure(Call<GetcashflowAssetflowModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }



    private void callGoasInvestService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetcashflowGoalInvestmentModel> call = obj.callCashFlowInvestService(getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetcashflowGoalInvestmentModel>() {
            @Override
            public void onResponse(Call<GetcashflowGoalInvestmentModel> call, Response<GetcashflowGoalInvestmentModel> response) {

                UtileKit.dismisssSpinnerDialog();
                getcashflowGoalInvestmentModel = response.body();
                Log.i("Cash Flow Chart", "get_income_expense_cashflow_by_user incomeExpenseCashFlowModel" + incomeExpenseCashFlowModel);
                if (getcashflowGoalInvestmentModel.getStatusCode().equals(UtileKit.SUCCESSCODE)) {
                    scrolllinearlayout.setVisibility(View.VISIBLE);
                } else {
                    ErrorChartView();
                }

            }

            @Override
            public void onFailure(Call<GetcashflowGoalInvestmentModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }

    private void callTaxAnalysisService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxCashFlowModel> call = webServiceObj.callinsurance_tax_Cash_Flow_Service(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxCashFlowModel>() {
            @Override
            public void onResponse(Call<TaxCashFlowModel> call, Response<TaxCashFlowModel> response) {
                try {
                    Log.i("Tax Analysis", "Tax Analysis user id" + UtileKit.getPersistedPurplePathPref("user_id"));
                    UtileKit.dismisssSpinnerDialog();

                    taxAnalysisCashFlowChartModel = response.body();
                    if (taxAnalysisCashFlowChartModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if (null != taxAnalysisCashFlowChartModel.getData().getUser_tax()) {
                            scrolllinearlayout.setVisibility(View.VISIBLE);
//                            Log.i("Tax Analysis", "Tax Analysis  Session" + taxAnalysisCashFlowChartModel.getData().getUser_tax().get(0).getSections().get(0).getName());

                        } else {
                            ErrorChartView();
                        }
                    } else {
                        ErrorChartView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxCashFlowModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
            }
        });


    }


    private void passFlowchartData(final TaxCashFlowModel taxAnalysisCashFlowChartModel) {

        String[] xData = {"Total Savings", "Total Tax Payable"};
        int[] chartColor = {Color.rgb(181, 205, 26), Color.rgb(2, 71, 254)};
        ArrayList<BarEntry> yAxisChartValues = new ArrayList<BarEntry>();
        try {
            if (taxAnalysisCashFlowChartModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                if (taxAnalysisCashFlowChartModel != null) {
                    int size = taxAnalysisCashFlowChartModel.getData().getUser_tax().size();
                    String[] xVals = new String[size];
//                    for (int j = 0; j < size; j++) {
//                        unitCount.add(floatConvertion(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(j).getTotal_savings()));
//                        unitCount.add(-floatConvertion(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(j).getTotal_tax_payable()));
//                        Log.i("checktheCountValue", " checktheCountValue calculate passFlowchartData in flow" + floatConvertion(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(j).getTotal_savings()));
//                        Log.i("checktheCountValue", " checktheCountValue calculate passFlowchartData out flow" + (-floatConvertion(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(j).getTotal_tax_payable())));
//                    }
//                    calculate = UtileKit.unitCalculation(unitCount);
//                    Log.i("checktheCountValue", " checktheCountValue calculate passFlowchartData" + calculate);


                    for (int i = 0; i < size; i++) {
                        ArrayList<Float> cashFlowArray = new ArrayList<Float>();
                        cashFlowArray.add(floatConvertion(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(size - 1 - i).getTotal_savings()));
                        cashFlowArray.add(-floatConvertion(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(size - 1 - i).getTotal_tax_payable()));
                        yAxisChartValues.add(new BarEntry(getFloatArray(cashFlowArray), i));
                        xVals[i] = taxAnalysisCashFlowChartModel.getData().getUser_tax().get(size - 1 - i).getTax_age();

                    }
                    BarDataSet set = new BarDataSet(yAxisChartValues, "");
                    set.setValueTextSize(7f);
                    set.setAxisDependency(YAxis.AxisDependency.RIGHT);
                    set.setBarSpacePercent(40f);
                    set.setColors(chartColor);
                    set.setDrawValues(false);
                    set.setValueFormatter(new ChartValueFormatter());

                    BarData data = new BarData(xVals, set);
                    mChart.setData(data);
                    mChart.setDescription("");
                    mChart.invalidate();
                    mChart.setDrawValueAboveBar(false);
                    mChart.setOnChartValueSelectedListener(this);

                    Legend l = mChart.getLegend();
                    l.setEnabled(false);
                    l.setTextSize(8 * getResources().getDisplayMetrics().density);
                    l.setFormSize(15f);
                    l.setWordWrapEnabled(true);
                    l.setXEntrySpace(15f);
                } else {
                    ErrorChartView();
                }
                setDataForCheckbox(xData, chartColor);
            } else {
                ErrorChartView();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ErrorChartView() {
        scrolllinearlayout.setVisibility(View.VISIBLE);
        errorTextview.setVisibility(View.GONE);
        errorTextview.setText(HomePageActivity.errorMessageInChart);
        // UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
    }






    private void callIncomeExpenseCashFlowService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<IncomeExpenseCashFlowModel> call = obj.getIncomeExpenseCashflowService(getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<IncomeExpenseCashFlowModel>() {
            @Override
            public void onResponse(Call<IncomeExpenseCashFlowModel> call, Response<IncomeExpenseCashFlowModel> response) {

                UtileKit.dismisssSpinnerDialog();
                incomeExpenseCashFlowModel = response.body();
                Log.i("Cash Flow Chart", "get_income_expense_cashflow_by_user incomeExpenseCashFlowModel" + incomeExpenseCashFlowModel);
                if (incomeExpenseCashFlowModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    scrolllinearlayout.setVisibility(View.VISIBLE);
                } else {
                    ErrorChartView();
                }

            }

            @Override
            public void onFailure(Call<IncomeExpenseCashFlowModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        try {
//            checkbox_position0 -  overcashflow
//            checkbox_position1 -  surplus and deflects
//            checkbox_position2 -  income and Expenses cash flow
//            checkbox_position3 - tax cash Flow chart
            Log.d("Rajasekar check....", " cash flow..");

            if (!userNameCheckBox.isEmpty()) {
                if (checkbox_position == 0) {

                } else if (checkbox_position == 1) {
                    if (incomeExpenseCashFlowModel != null) {
                        Log.i("TaxCashFlowChart", "position pass" + incomeExpenseCashFlowModel.getData().getCash_mang_det().get(e.getXIndex()));
                        dirllDowncashmanagemet(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(e.getXIndex()), new CashManagemntAnalysis());
                    }
                } else if (checkbox_position == 2) {
                    if (incomeExpenseCashFlowModel != null) {
                        Log.i("TaxCashFlowChart", "position pass" + incomeExpenseCashFlowModel.getData().getCash_mang_det().get(e.getXIndex()));
                        dirllDowncashmanagemet(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(e.getXIndex()), new CashManagemntAnalysis());
                    }
                } else if (checkbox_position == 3) {
                    Log.i("cashflowChartFragment", "checkbox_position" + checkbox_position);
                    Log.i("TaxCashFlowChart", "on click the Entry e" + e.getXIndex());
                    if (taxAnalysisCashFlowChartModel != null) {
                        Log.i("TaxCashFlowChart", "position pass" + taxAnalysisCashFlowChartModel.getData().getUser_tax().get(e.getXIndex()));
                        descriptiondatapass(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(e.getXIndex()));
                    }
                } /*else if (checkbox_position == 4) {
                    Log.i("cashflowChartFragment", "checkbox_position" + checkbox_position);
                    Log.i("TaxCashFlowChart", "on click the Entry e" + e.getXIndex());
                    if (mGetcashflowassetflowModel != null) {
                        Log.i("TaxCashFlowChart", "position pass" + mGetcashflowassetflowModel.getData().getCashflow().get(e.getXIndex()));
                        descriptiondatapass1(mGetcashflowassetflowModel.getData().getCashflow().get(e.getXIndex()));
                    }
                }else if (checkbox_position == 5) {
                    Log.i("cashflowChartFragment", "checkbox_position" + checkbox_position);
                    Log.i("TaxCashFlowChart", "on click the Entry e" + e.getXIndex());
                    if (mGetcashflowassetflowModel != null) {
                        Log.i("TaxCashFlowChart", "position pass" + mGetcashflowassetflowModel.getData().getCashflow().get(e.getXIndex()));
                        descriptiondatapass1(mGetcashflowassetflowModel.getData().getCashflow().get(e.getXIndex()));
                    }
                }*/
            }


        } catch (Exception ix) {
            ix.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected() {

    }

    private void dirllDowncashmanagemet(Cash_mang_det fin_cash_flow, CashManagemntAnalysis cashManagemntAnalysis) {
        Fragment fragment = cashManagemntAnalysis;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();

        if (fin_cash_flow != null) {
            bundle.putSerializable("user_tax_position", fin_cash_flow);

        }

        fragment.setArguments(bundle);
        addFragmenttoStack(fragment);


    }




    public void callCashflowAnalysisService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetcashflowinoutflowModel> call = webServiceObj.callCashFlowAnalysisService(getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetcashflowinoutflowModel>() {
            @Override
            public void onResponse(Call<GetcashflowinoutflowModel> call, Response<GetcashflowinoutflowModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success", "" + response.body());
                mGetcashflowinoutflowModel = response.body();

                Log.i("Cash Flow Chart", "callCashflowAnalysisService :" + mGetcashflowinoutflowModel.getData());
                if (mGetcashflowinoutflowModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    scrolllinearlayout.setVisibility(View.VISIBLE);
                    AddCheckBoxView(xData);
                } else {
                    scrolllinearlayout.setVisibility(View.GONE);
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                }
            }

            @Override
            public void onFailure(Call<GetcashflowinoutflowModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }






    private void setOverallChart() {
        try {
            ArrayList<BarEntry> yAxisChartValues = new ArrayList<BarEntry>();
            if (mGetcashflowinoutflowModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                scrolllinearlayout.setVisibility(View.VISIBLE);
                errorTextview.setVisibility(View.GONE);
                int size = mGetcashflowinoutflowModel.getData().getFin_cash_flow().size();

//                for(int j = 0; j < size; j++){
//                    unitCount.add(floatConvertion(mGetcashflowinoutflowModel.getData().getFin_cash_flow().get(j).getIn_flow()));
//                    unitCount.add(-floatConvertion(mGetcashflowinoutflowModel.getData().getFin_cash_flow().get(j).getOut_flow()));
//                    Log.i("checktheCountValue", " checktheCountValue calculate setOverallChart out flow" + (-floatConvertion(mGetcashflowinoutflowModel.getData().getFin_cash_flow().get(j).getOut_flow())));
//                }
//                calculate = UtileKit.unitCalculation(unitCount);
                String[] xVals = new String[size];
                String[] xValsdecendingOverall = new String[xVals.length];
                for (int i = 0; i < size; i++) {
                    ArrayList<Float> comm_goldArray = new ArrayList<Float>();
                    ArrayList<Float> comm_goldArrayReverse = new ArrayList<Float>();
                    comm_goldArray.add(floatConvertion(mGetcashflowinoutflowModel.getData().getFin_cash_flow().get(size - 1 - i).getIn_flow()) / calculate);
                    comm_goldArray.add(-floatConvertion(mGetcashflowinoutflowModel.getData().getFin_cash_flow().get(size - 1 - i).getOut_flow()) / calculate);
//                    comm_goldArrayReverse = reverseFloat(comm_goldArray ,1);
                    yAxisChartValues.add(new BarEntry(getFloatArray(comm_goldArray), i));
                    xVals[i] = mGetcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getCash_age();
                }
                xValsdecendingOverall = reverse(xVals);
                BarDataSet set = new BarDataSet(yAxisChartValues, "");
                set.setValueFormatter(new ChartValueFormatter());
                set.setValueTextSize(7f);
                set.setAxisDependency(YAxis.AxisDependency.RIGHT);
                set.setBarSpacePercent(40f);
                set.setColors(chartColor);
                set.setDrawValues(false);


                BarData data = new BarData(xValsdecendingOverall, set);
                mChart.setData(data);
                mChart.invalidate();
                mChart.setDrawValueAboveBar(false);
                XAxis xAxis = mChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawAxisLine(false);
                xAxis.setDrawGridLines(true);
                xAxis.enableGridDashedLine(10f, 10f, 2f);

                YAxis leftAxis = mChart.getAxisLeft();
                leftAxis.setDrawAxisLine(false);
                leftAxis.setDrawGridLines(true);
                leftAxis.enableGridDashedLine(10f, 10f, 0f);
                leftAxis.setEnabled(false);


                YAxis rightAxis = mChart.getAxisRight();
                rightAxis.setDrawGridLines(true);
                rightAxis.setDrawAxisLine(false);
                rightAxis.enableGridDashedLine(10f, 10f, 0f);
                rightAxis.setEnabled(false);
                mChart.setOnChartValueSelectedListener(this);

                mChart.getXAxis().setEnabled(true);
                mChart.getAxisLeft().setEnabled(false);
                mChart.getAxisRight().setEnabled(true);
                Legend l = mChart.getLegend();
                l.setEnabled(false);
                l.setTextSize(8 * getResources().getDisplayMetrics().density);
                l.setFormSize(15f);
                l.setWordWrapEnabled(true);
                l.setXEntrySpace(15f);

                mChart.invalidate();
                setDataForCheckbox(xDataString, chartColor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    private float[] getFloatArray(List<Float> comm_goldArray) {
        float array[] = new float[comm_goldArray.size()];
        for (int i = 0; i < comm_goldArray.size(); i++) {
            array[i] = comm_goldArray.get(i);
        }
        return array;
    }


    private float floatWithNegativeConvertion(String val) {
        float convVal = 0;
        try {
            convVal = Math.round(Float.parseFloat(val));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return convVal;
    }

    private float floatConvertion(String val) {
        float convVal = 0;
        try {
            convVal = Math.round(Float.parseFloat(val));
        } catch (NumberFormatException e) {
            e.printStackTrace();

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

    private void checkBoxOnClick(int position, boolean b) {
        if (b) {

            for (int i = 0; i < userNameCheckBox.size(); i++) {
                if (i != position)
                    userNameCheckBox.get(i).setChecked(false);
            }
            if (position == 0) {
                if (mGetcashflowinoutflowModel != null) {
                    if (UtileKit.getPersistedPurplePathBoolPref("Overall Cash Flow")) {
                        setOverallChart();
                    } else {
                        UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                    }
                } else {
                    callCashflowAnalysisService();
                }
            } else if (position == 1) {
                if (mGetcashflowinoutflowModel != null) {
                    if (UtileKit.getPersistedPurplePathBoolPref("Surplus and Deficit")) {
                        setSuplesandDeflet();
                    } else {
                        UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                    }
                } else {
                    callCashflowAnalysisService();
                }

            } else if (position == 2) {
                if (incomeExpenseCashFlowModel != null) {
                    if (UtileKit.getPersistedPurplePathBoolPref("Income Expense Cash Flow")) {
                        setIncomeExpenseChart();
                    } else {
                        UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                    }
                } else {
                    callIncomeExpenseCashFlowService();
                }
            } else if (position == 3) {
                if (taxAnalysisCashFlowChartModel != null) {
                    if (taxAnalysisCashFlowChartModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        scrolllinearlayout.setVisibility(View.VISIBLE);
                        errorTextview.setVisibility(View.GONE);
                        if (UtileKit.getPersistedPurplePathBoolPref("Tax Cash Flow Chart")) {
                            passFlowchartData(taxAnalysisCashFlowChartModel);
                        } else {
                            UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                        }
                    } else {
                        callTaxAnalysisService();
                    }
                } else {
                    callTaxAnalysisService();
                }
            }
            if (position == 4) {

                if (mGetcashflowassetflowModel != null) {
                    if (mGetcashflowassetflowModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        scrolllinearlayout.setVisibility(View.VISIBLE);
                        errorTextview.setVisibility(View.GONE);
                        //passFlowchartData1(mGetcashflowassetflowModel);

                        //setIncomeExpenseChart12();
                        setIncomeExpenseChart1();
                        /*if (UtileKit.getPersistedPurplePathBoolPref("Asset")) {
                            passFlowchartData1(mGetcashflowassetflowModel);
                        } else {
                            UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                        }*/
                    } else {
                        callAssetService();
                    }
                } else {
                    callAssetService();
                }


            } else if (position == 5) {
                if (getcashflowGoalInvestmentModel != null) {
                    if (getcashflowGoalInvestmentModel.getStatusCode().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        scrolllinearlayout.setVisibility(View.VISIBLE);
                        errorTextview.setVisibility(View.GONE);
                        //passFlowchartData1(mGetcashflowassetflowModel);

                        //setIncomeExpenseChart12();
                        setIncomeExpenseChart12();
                        /*if (UtileKit.getPersistedPurplePathBoolPref("Asset")) {
                            passFlowchartData1(mGetcashflowassetflowModel);
                        } else {
                            UtileKit.intitializeAlertDialog("please upgrade your pack", mContext);
                        }*/
                    } else {
                        callGoasInvestService();
                    }
                } else {
                    callGoasInvestService();
                }

            }
        }
    }

    private void setIncomeExpenseChart() {
        ArrayList<BarEntry> yAxisChartValues = new ArrayList<BarEntry>();
        int[] colorsArray = new int[9];
        if (incomeExpenseCashFlowModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
            scrolllinearlayout.setVisibility(View.VISIBLE);
            errorTextview.setVisibility(View.GONE);
            int size = incomeExpenseCashFlowModel.getData().getCash_mang_det().size();
//                for(int j = 0; j < size; j++){
//                    unitCount.add(floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(j).getOver_all_inc()));
//                    unitCount.add(-floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(j).getTot_exp()));
//                }
//                calculate = UtileKit.unitCalculation(unitCount);
            String[] xVals = new String[size];
            String[] xValsdecendingOverall = new String[xVals.length];
            for (int i = 0; i < size; i++) {
                ArrayList<Float> inc_expArray = new ArrayList<Float>();
                inc_expArray.add(floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(size - 1 - i).getSi_total()) / calculate);
                inc_expArray.add(floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(size - 1 - i).getIp_total()) / calculate);
                inc_expArray.add(floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(size - 1 - i).getIb_total()) / calculate);
                inc_expArray.add(floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(size - 1 - i).getCg_total()) / calculate);
                inc_expArray.add(floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(size - 1 - i).getIfs_total()) / calculate);


                inc_expArray.add(-floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(size - 1 - i).getOver_all_exp()) / calculate);
                inc_expArray.add(-floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(size - 1 - i).getOver_all_obli()) / calculate);
                inc_expArray.add(-floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(size - 1 - i).getOver_all_commt()) / calculate);
                inc_expArray.add(-floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(size - 1 - i).getOver_all_contr()) / calculate);


                yAxisChartValues.add(new BarEntry(getFloatArray(inc_expArray), i));
                xVals[i] = incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getCash_age();
            }
            xValsdecendingOverall = reverse(xVals);
            BarDataSet set = new BarDataSet(yAxisChartValues, "");
            set.setValueFormatter(new ChartValueFormatter());
            set.setValueTextSize(7f);
            set.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set.setBarSpacePercent(40f);
            set.setColors(incExpColorArray);
            set.setDrawValues(false);

            BarData data = new BarData(xValsdecendingOverall, set);
            mChart.setData(data);
            mChart.invalidate();
            mChart.setDrawValueAboveBar(false);
            mChart.getXAxis().setEnabled(true);
            mChart.getAxisLeft().setEnabled(false);
            mChart.getAxisRight().setEnabled(true);
            Legend l = mChart.getLegend();
            mChart.setOnChartValueSelectedListener(this);
            l.setEnabled(false);
            l.setTextSize(8 * getResources().getDisplayMetrics().density);
            l.setFormSize(15f);
            l.setWordWrapEnabled(true);
            l.setXEntrySpace(15f);
            mChart.invalidate();
            String[] xDataIncomeExpense = {"Income from Salary", "Income from Property", "Income from Business", "Income from Capital Gains", "Income from Other Sources",
                    "Expenses", "Obligations", "Commitments", "Contributions"};
            setDataForCheckboxincome(xDataIncomeExpense, incExpColorArray);


        }
    }


    private void setIncomeExpenseChart1() {
        ArrayList<BarEntry> yAxisChartValues = new ArrayList<BarEntry>();
        int[] colorsArray = new int[9];
        if (mGetcashflowassetflowModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
            scrolllinearlayout.setVisibility(View.VISIBLE);
            errorTextview.setVisibility(View.GONE);
            int size = mGetcashflowassetflowModel.getData().getCashflow().size();
//                for(int j = 0; j < size; j++){
//                    unitCount.add(floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(j).getOver_all_inc()));
//                    unitCount.add(-floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(j).getTot_exp()));
//                }
//                calculate = UtileKit.unitCalculation(unitCount);
            String[] xVals = new String[size];
            String[] xValsdecendingOverall = new String[xVals.length];
            for (int i = 0; i < size; i++) {
                ArrayList<Float> inc_expArray = new ArrayList<Float>();
                inc_expArray.add(-floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getAssetArray().getLiquid()) / calculate);
                inc_expArray.add(-floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getAssetArray().getCommGold()) / calculate);
                inc_expArray.add(-floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getAssetArray().getEmpBenf()) / calculate);
                inc_expArray.add(floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getAssetArray().getFixedInc()) / calculate);
                inc_expArray.add(floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getAssetArray().getRealEstate()) / calculate);
                inc_expArray.add(floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getAssetArray().getEquity()) / calculate);

              /*  inc_expArray.add(-floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getLiabArray().getCreditCard()) / calculate);
                inc_expArray.add(-floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getLiabArray().getLoanOffer()) / calculate);
                inc_expArray.add(-floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getLiabArray().getLoan()) / calculate);
                inc_expArray.add(-floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getLiabArray().getRefDeposit()) / calculate);
                inc_expArray.add(-floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getLiabArray().getUnpaidBills()) / calculate);
             */   yAxisChartValues.add(new BarEntry(getFloatArray(inc_expArray), i));
                xVals[i] = mGetcashflowassetflowModel.getData().getCashflow().get(i).getCashAge();

            }
            xValsdecendingOverall = reverse(xVals);
            BarDataSet set = new BarDataSet(yAxisChartValues, "");
            set.setValueFormatter(new ChartValueFormatter());
            set.setValueTextSize(7f);
            set.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set.setBarSpacePercent(40f);
            set.setColors(colorList);
            set.setDrawValues(false);

            BarData data = new BarData(xValsdecendingOverall, set);
            mChart.setData(data);
            mChart.invalidate();
            mChart.setDrawValueAboveBar(false);
            mChart.getXAxis().setEnabled(true);
            mChart.getAxisLeft().setEnabled(false);
            mChart.getAxisRight().setEnabled(true);
            Legend l = mChart.getLegend();
            mChart.setOnChartValueSelectedListener(this);
            l.setEnabled(false);
            l.setTextSize(8 * getResources().getDisplayMetrics().density);
            l.setFormSize(15f);
            l.setWordWrapEnabled(true);
            l.setXEntrySpace(15f);
            mChart.invalidate();
            String[] xAssetString = {"Liquid", "6", "emp_benf", "fixed_inc", "real_estate", "equity", "credit_card", "loan_offer", "loan", "ref_deposit", "unpaid_bills"};
            setDataForCheckboxincome1(xAssetString, incAssetColorArray);


        }
    }


    private void setIncomeExpenseChart12() {
        ArrayList<BarEntry> yAxisChartValues = new ArrayList<BarEntry>();
        int[] colorsArray = new int[9];
        if (getcashflowGoalInvestmentModel.getStatusCode().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
            scrolllinearlayout.setVisibility(View.VISIBLE);
            errorTextview.setVisibility(View.GONE);
            int size = getcashflowGoalInvestmentModel.getData().getCashflow().size();
//                for(int j = 0; j < size; j++){
//                    unitCount.add(floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(j).getOver_all_inc()));
//                    unitCount.add(-floatConvertion(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(j).getTot_exp()));
//                }
//                calculate = UtileKit.unitCalculation(unitCount);
            String[] xVals = new String[size];
            String[] xValsdecendingOverall = new String[xVals.length];
            for (int i = 0; i < size; i++) {
                ArrayList<Float> inc_expArray = new ArrayList<Float>();
               /* inc_expArray.add(floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getAssetArray().getLiquid()) / calculate);
                inc_expArray.add(floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getAssetArray().getCommGold()) / calculate);
                inc_expArray.add(floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getAssetArray().getEmpBenf()) / calculate);
                inc_expArray.add(floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getAssetArray().getFixedInc()) / calculate);
                inc_expArray.add(floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getAssetArray().getRealEstate()) / calculate);
                inc_expArray.add(floatConvertion(mGetcashflowassetflowModel.getData().getCashflow().get(size - 1 - i).getAssetArray().getEquity()) / calculate);
*/
//               String s = String.valueOf(getcashflowGoalInvestmentModel.getData().getCashflow().get(i).getGoalArray().get(i).getInvestArray().size());
              if (getcashflowGoalInvestmentModel.getData().getCashflow().get(i).getGoalArray().size() != 0) {
                  for (int j = 0; j < getcashflowGoalInvestmentModel.getData().getCashflow().get(i).getGoalArray().size(); j++) {
                      if (getcashflowGoalInvestmentModel.getData().getCashflow().get(i).getGoalArray().get(j).getInvestArray() == null) {
                          inc_expArray.add(-floatConvertion("0"));
                          inc_expArray.add(floatConvertion("0"));
                      } else {
                          inc_expArray.add(-floatConvertion(getcashflowGoalInvestmentModel.getData().getCashflow().get(i).getGoalArray()/*.get(j).getInvestArray()*/.size()+""));
                          for (int k = 0; k < getcashflowGoalInvestmentModel.getData().getCashflow().get(i).getGoalArray().get(j).getInvestArray().size(); k++) {
                              inc_expArray.add(floatConvertion(getcashflowGoalInvestmentModel.getData().getCashflow().get(i).getGoalArray().get(j).getInvestArray().get(k).getCurrentValue()) / calculate);
                          }
                      }
                  }
              }

              // }
                //inc_expArray.add(floatConvertion(String.valueOf(getcashflowGoalInvestmentModel.getData().getCashflow().get(size - 1 - i).getGoalArray().get(i).getInvestArray().size())) / calculate);
                //inc_expArray.add(-floatConvertion(getcashflowGoalInvestmentModel.getData().getCashflow().get(size - 1 - i).getGoalArray().get(i).getInvestArray().get(i).getCurrentValue()) / calculate);
                yAxisChartValues.add(new BarEntry(getFloatArray(inc_expArray), i));
                xVals[i] = getcashflowGoalInvestmentModel.getData().getCashflow().get(i).getCashAge();

            }
            xValsdecendingOverall = reverse(xVals);
            BarDataSet set = new BarDataSet(yAxisChartValues, "");
            set.setValueFormatter(new ChartValueFormatter());
            set.setValueTextSize(7f);
            set.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set.setBarSpacePercent(40f);
            set.setColors(colorList);
            set.setColors(colorList);
            set.setDrawValues(false);

            BarData data = new BarData(xValsdecendingOverall, set);
            mChart.setData(data);
            mChart.invalidate();
            mChart.setDrawValueAboveBar(false);
            mChart.getXAxis().setEnabled(true);
            mChart.getAxisLeft().setEnabled(false);
            mChart.getAxisRight().setEnabled(true);
            Legend l = mChart.getLegend();
            mChart.setOnChartValueSelectedListener(this);
            l.setEnabled(false);
            l.setTextSize(8 * getResources().getDisplayMetrics().density);
            l.setFormSize(15f);
            l.setWordWrapEnabled(true);
            l.setXEntrySpace(15f);
            mChart.invalidate();
            String[] xAssetString = {"Liquid", "6", "emp_benf", "fixed_inc", "real_estate", "equity", "credit_card", "loan_offer", "loan", "ref_deposit", "unpaid_bills"};
            setDataForCheckboxincome1(xAssetString, incAssetColorArray);


        }
    }

    private void setDataForCheckboxincome1(String[] xDataCheckbox, int[] colour) {
        try {
            Log.i("InsuranceAnalysis", " InsuranceAnalysis setDataForCheckbox is " + xDataCheckbox.length);

            for (int i = 0; i < xDataCheckbox.length; i++) {

                LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout parent_layout = new LinearLayout(mContext);
                parent_layout.setWeightSum(2);
                // parent_param_layout.weight = 1F;

                parent_layout.setOrientation(LinearLayout.HORIZONTAL);
                parent_param_layout.setMargins(10, 0, 0, 10);
                parent_layout.setLayoutParams(parent_param_layout);

                LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
                txt_unit.setGravity(Gravity.LEFT | Gravity.CENTER);
                LinearLayout.LayoutParams txtLeft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txt_unit.setLayoutParams(txtLeft_params);
                txt_unit.setText(xDataCheckbox[i]);
                left_layout.addView(txt_unit);
                parent_layout.addView(left_layout);
                parentView.addView(parent_layout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void setDataForCheckboxincome(String[] xDataCheckbox, int[] colour) {
        try {
            Log.i("InsuranceAnalysis", " InsuranceAnalysis setDataForCheckbox is " + xDataCheckbox.length);

            for (int i = 0; i < xDataCheckbox.length; i++) {

                LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout parent_layout = new LinearLayout(mContext);
                parent_layout.setWeightSum(2);
                // parent_param_layout.weight = 1F;

                parent_layout.setOrientation(LinearLayout.HORIZONTAL);
                parent_param_layout.setMargins(10, 0, 0, 10);
                parent_layout.setLayoutParams(parent_param_layout);

                LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
                txt_unit.setGravity(Gravity.LEFT | Gravity.CENTER);
                LinearLayout.LayoutParams txtLeft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txt_unit.setLayoutParams(txtLeft_params);
                txt_unit.setText(xDataCheckbox[i]);
                left_layout.addView(txt_unit);
                parent_layout.addView(left_layout);
                parentView.addView(parent_layout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setSuplesandDeflet() {
        ArrayList<BarEntry> yAxisChartValues = new ArrayList<BarEntry>();
        try {
            if (mGetcashflowinoutflowModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                scrolllinearlayout.setVisibility(View.VISIBLE);
                errorTextview.setVisibility(View.GONE);
                int size = mGetcashflowinoutflowModel.getData().getFin_cash_flow().size();
                String[] xVals = new String[size];
                surplusDeficitColor = new int[size];
                int j = 0;
                for (int i = 0; i < size; i++) {
                    cashFlow = Float.parseFloat(mGetcashflowinoutflowModel.getData().getFin_cash_flow().get(size - 1 - i).getCash_flow());
                    ArrayList<Float> comm_goldArray = new ArrayList<Float>();

                    comm_goldArray.add(floatWithNegativeConvertion(
                            mGetcashflowinoutflowModel.getData().getFin_cash_flow().get(size - 1 - i).getCash_flow()));

                    yAxisChartValues.add(new BarEntry(getFloatArray(comm_goldArray), i));
                    xVals[i] = mGetcashflowinoutflowModel.getData().getFin_cash_flow().get(size - 1 - i).getCash_age();
                    if (cashFlow > 0) {
                        surplusDeficitColor[j++] = Color.rgb(181, 205, 26);
                    } else if (cashFlow < 0) {
                        surplusDeficitColor[j++] = Color.rgb(2, 71, 254);
                    }

                }
                BarDataSet set = new BarDataSet(yAxisChartValues, "");
                set.setValueTextSize(7f);
                set.setAxisDependency(YAxis.AxisDependency.RIGHT);
                set.setBarSpacePercent(40f);
                set.setColors(surplusDeficitColor);
                set.setDrawValues(false);
                BarData data = new BarData(xVals, set);
                mChart.setData(data);
                mChart.invalidate();
                mChart.setDrawValueAboveBar(false);
                mChart.getXAxis().setEnabled(true);
                mChart.getAxisLeft().setEnabled(false);
                mChart.getAxisRight().setEnabled(true);
                mChart.setOnChartValueSelectedListener(this);
                Legend l = mChart.getLegend();
                l.setEnabled(false);
                l.setTextSize(8 * getResources().getDisplayMetrics().density);
                l.setFormSize(15f);
                l.setWordWrapEnabled(true);
                l.setXEntrySpace(15f);

                mChart.invalidate();
            } else {
                ErrorChartView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setDataForCheckbox(xDataSurplusDeficit, chartColor);
    }

    public String[] reverse(String[] nums) {
        Log.i("checktheCountValue", " checktheCountValue nums" + nums.length);
        String[] reversed = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            reversed[i] = nums[nums.length - 1 - i];
        }
        return reversed;
    }

    public ArrayList<Float> reverseFloat(String[] nums) {
        Log.i("checktheCountValue", " checktheCountValue nums reverseFloat size " + nums.length);
        ArrayList<Float> reversed = new ArrayList<>();
        int size = nums.length;
        for (int i = 0; i < size; i++) {
            try {
                Log.i("checktheCountValue", " checktheCountValue nums reverseFloat all size " + i);
                reversed.add(Float.parseFloat(nums[size - 1 - i]));
                Log.i("checktheCountValue", " checktheCountValue nums reverseFloat all " + reversed);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                reversed.add(0.0f);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return reversed;


    }

    /*
     *   Check box will be assign int the order of :
     *   "Overall Cash Flow","Surplus and Deficit","Income Expense Cash Flow","Tax Cash Flow Chart"
     *        0                       1                       2                          3
     * */


    private void AddCheckBoxView(String[] xData) {
        try {
            checkboxLayout.removeAllViews();
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

               /* LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(40, 40);
                parms_legen_layout.setMargins(20, 20, 20, 20);
                LinearLayout legend_layout = new LinearLayout(mContext);
                legend_layout.setLayoutParams(parms_legen_layout);
                legend_layout.setOrientation(LinearLayout.HORIZONTAL);
                //legend_layout.setBackgroundColor(colour[i]);
                left_layout.addView(legend_layout);*/

                //Log.e("Possition To Add L", "" + i);
                userNameCheckBox.add(new CheckBox(mContext));
                userNameCheckBox.get(i).setId(i);
                UtileKit.setTextAppearance(mContext, android.R.style.TextAppearance_Small, userNameCheckBox.get(i));
                userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        int position = compoundButton.getId();
                        checkBoxOnClick(position, b);
                        //Log.e("Possition To Add L", "" + position);
                        checkbox_position = position;
                    }
                });
                userNameCheckBox.get(i).setText(xData[i]);
                userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.app_text_color_gray));
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


                userNameCheckBox.add(new CheckBox(mContext));
                userNameCheckBox.get(i).setId(i);
                UtileKit.setTextAppearance(mContext, android.R.style.TextAppearance_Small, userNameCheckBox.get(i));
                userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        int position = compoundButton.getId();
                        checkBoxOnClick(position, b);

                        checkbox_position = position;
                    }
                });
                userNameCheckBox.get(i).setText(xData[i]);
                userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.app_text_color_gray));
                right_layout.addView(userNameCheckBox.get(i));

                parent_layout.addView(right_layout);
                parent_layout.addView(left_layout);
                checkboxLayout.addView(parent_layout);


            }

            if (!userNameCheckBox.isEmpty())
                userNameCheckBox.get(0).setChecked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setDataForCheckbox(String[] xDataCheckbox, int[] colour) {
        try {
            Log.i("Tax Analysis", " Tax Analysis setDataForCheckbox is " + xDataCheckbox.length);
            parentView.removeAllViews();
            for (int i = 0; i < xDataCheckbox.length; i++) {

                LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent_layout = new LinearLayout(mContext);
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
                legend_layout.setBackgroundColor(colour[i]);
                left_layout.addView(legend_layout);
                LinearLayout.LayoutParams left_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                TextView txt_unit = new TextView(mContext);
                txt_unit.setLayoutParams(left_params);
                txt_unit.setGravity(Gravity.LEFT | Gravity.CENTER);
                txt_unit.setText(xDataCheckbox[i]);
                left_layout.addView(txt_unit);
                i++;
                if ((xDataCheckbox.length) == i) {
                    parent_layout.addView(left_layout);
                    parentView.addView(parent_layout);
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
                right_legend_layout.setBackgroundColor(colour[i]);
                right_layout.addView(right_legend_layout);

                TextView right_txt_unit = new TextView(mContext);
                LinearLayout.LayoutParams right_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                right_txt_unit.setLayoutParams(right_params);
                right_txt_unit.setGravity(Gravity.LEFT | Gravity.CENTER);
                right_txt_unit.setText(xDataCheckbox[i]);
                right_layout.addView(right_txt_unit);

                parent_layout.addView(right_layout);
                parent_layout.addView(left_layout);
                parentView.addView(parent_layout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_cashdetails, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {

            case R.id.menu_details:


                fragment = CashflowDetails1.newInstance(mGetcashflowinoutflowModel, incomeExpenseCashFlowModel,
                        taxAnalysisCashFlowChartModel, checkbox_position);
                addToActivity(fragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void addToActivity(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).
                commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow: {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home: {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
//                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow: {
                addFragmenttoStack(new InsuranceAnalysis());

            }
            break;

        }
    }


}
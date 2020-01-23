package com.purplepath.purplepath.taxanalysis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.ChartValueFormatter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.riskAssesment.RiskProfile;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.User_tax;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Suresh on 07/01/17.
 */

public class TaxCashFlowChart extends BaseFragment implements View.OnClickListener {

    private TaxCashFlowModel taxAnalysisCashFlowChartModel;
    protected HorizontalBarChart mChart;
    int[] chartColor = {Color.rgb(181, 205, 26), Color.rgb(2, 71, 254)};
    private OnActivityBackPressedListener mCallBackListener;
    private Context mContext;
    private LinearLayout parentView;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private ScrollView scrolllinearlayout;
    private  TextView errorTextview, textViewUnitApperances;
    ArrayList<Float> unitCount = new ArrayList<Float>();
    ArrayList<Float> unitCountCashFlow = new ArrayList<Float>();
    int calculate = 100;
    private RelativeLayout mainLayout;

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
        View taxAnalysisFlowChartView = inflater.inflate(R.layout.frag_tax_cash_flow_analysis, container, false);
        setHasOptionsMenu(true);
        mChart = taxAnalysisFlowChartView.findViewById(R.id.chart1);
        parentView= taxAnalysisFlowChartView.findViewById(R.id.parentViewId);
        mCallBackListener.setActionBarTitle("Tax Cash Flow Chart");
        scrolllinearlayout = taxAnalysisFlowChartView.findViewById(R.id.layout_Scroll);
        errorTextview = taxAnalysisFlowChartView.findViewById(R.id.errorTextview);
        textViewUnitApperances = taxAnalysisFlowChartView.findViewById(R.id.textViewUnitApperances);
        mleftRelativeLayout = taxAnalysisFlowChartView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = taxAnalysisFlowChartView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = taxAnalysisFlowChartView.findViewById(R.id.relative_right_arrow);
        mainLayout= taxAnalysisFlowChartView.findViewById(R.id.mainLayout);

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        callTaxAnalysisService();// Services call here ;

// setOnChartValueSelectedListener is used to click the bar and get the position
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.i("TaxCashFlowChart", "on click the Entry e" +  e.getXIndex());

                if(taxAnalysisCashFlowChartModel!=null){
                    Log.i("TaxCashFlowChart", "position pass" +  taxAnalysisCashFlowChartModel.getData().getUser_tax().get(e.getXIndex()));
                    descriptiondatapass(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(e.getXIndex()));
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        return taxAnalysisFlowChartView;
    }



    private void descriptiondatapass(User_tax user_tax) {
        Fragment fragment = new TaxAnalysis();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();

        if (user_tax != null) {
            bundle.putSerializable("user_tax_position", user_tax);
            Log.i("TaxCashFlowChart", "getTotal_savings pass" +  user_tax.getTotal_savings());
        }

        fragment.setArguments(bundle);
        addFragmenttoStack(fragment);
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
}


    private void callTaxAnalysisService() {
        UtileKit.showSpinnerDialog(mContext,false);
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
                            Log.i("Tax Analysis", "Tax Analysis  Session" + taxAnalysisCashFlowChartModel.getData().getUser_tax().get(0).getSections().get(0).getName());
                            passFlowchartData(taxAnalysisCashFlowChartModel);
                        }else{
                            scrolllinearlayout.setVisibility(View.GONE);
                            errorTextview.setVisibility(View.VISIBLE);
                            errorTextview.setText(HomePageActivity.errorMessageInChart);
                            UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                        }
                    }
                    else{
                        scrolllinearlayout.setVisibility(View.GONE);
                        errorTextview.setVisibility(View.VISIBLE);
                        errorTextview.setText(HomePageActivity.errorMessageInChart);
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
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

    private void passFlowchartData(TaxCashFlowModel taxAnalysisCashFlowChartModel) {
        String[] xData={"Total Savings","Total Tax Payable"};
        int[] chartColor = {Color.rgb(181, 205, 26), Color.rgb(2, 71, 254)};
        ArrayList<BarEntry> yAxisChartValues = new ArrayList<BarEntry>();
        int size =taxAnalysisCashFlowChartModel.getData().getUser_tax().size();
        String[] xVals = new String[size];
        try {
            if (taxAnalysisCashFlowChartModel != null) {

                for(int j = 0; j < size; j++){
                    unitCount.add(floatConvertion(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(j).getTotal_savings()));
                    unitCount.add(-floatConvertion(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(j).getTotal_tax_payable()));
                    Log.i("checktheCountValue", " checktheCountValue calculate passFlowchartData in flow" + floatConvertion(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(j).getTotal_savings()));
                    Log.i("checktheCountValue", " checktheCountValue calculate passFlowchartData out flow" + (-floatConvertion(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(j).getTotal_tax_payable())));
                }
//                calculate = UtileKit.unitCalculation(unitCount);
//                Log.i("checktheCountValue", " checktheCountValue calculate passFlowchartData" + calculate);


                for (int i = 0; i < size; i++) {
                    ArrayList<Float> cashFlowArray = new ArrayList<Float>();
                    cashFlowArray.add(floatConvertion(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getTotal_savings()));
                    cashFlowArray.add(-floatConvertion(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getTotal_tax_payable()));
                    yAxisChartValues.add(new BarEntry(getFloatArray(cashFlowArray), i));
                    xVals[i] = taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getTotal_savings();
                }
            } else{
                scrolllinearlayout.setVisibility(View.GONE);
                errorTextview.setVisibility(View.VISIBLE);
                errorTextview.setText(HomePageActivity.errorMessageInChart);
                UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
            }

            BarDataSet set = new BarDataSet(yAxisChartValues, "");
            set.setValueTextSize(7f);
            set.setAxisDependency(YAxis.AxisDependency.RIGHT);
            set.setBarSpacePercent(40f);
            set.setColors(chartColor);
            set.setDrawValues(false);
            set.setValueFormatter(new ChartValueFormatter());
            //chart background programatically fixing with tab
            ShapeDrawable sd = new ShapeDrawable();
            sd.setShape(new RectShape());
            sd.getPaint().setColor(Color.GRAY);
            sd.getPaint().setStrokeWidth(5f);
            sd.getPaint().setStyle(Paint.Style.STROKE);
            mainLayout.setBackground(sd);

            BarData data = new BarData(xVals, set);
            mChart.setData(data);
            mChart.setDescription("");
            mChart.invalidate();
            mChart.setDrawValueAboveBar(false);
            Legend l = mChart.getLegend();
            l.setEnabled(false);
            l.setTextSize(8 * getResources().getDisplayMetrics().density);
            l.setFormSize(15f);
            l.setWordWrapEnabled(true);
            l.setXEntrySpace(15f);

            setDataForCheckbox(xData,chartColor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float floatConvertion(String val) {
        float convVal = 0;
        try {
            convVal = Math.abs(Float.parseFloat(val));
        } catch (NumberFormatException e) {

        }
        return convVal;
    }

    private float[] getFloatArray(List<Float> comm_goldArray) {
        float array[] = new float[comm_goldArray.size()];
        for (int i = 0; i < comm_goldArray.size(); i++) {
            array[i] = comm_goldArray.get(i);
        }
        return array;
    }
    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }
    private class CustomFormatter implements com.github.mikephil.charting.formatter.ValueFormatter, YAxisValueFormatter {

        private DecimalFormat mFormat;

        public CustomFormatter() {
            mFormat = new DecimalFormat("#,##,###");
        }

        // data
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(Math.abs(value)) ;
        }

        // YAxis
        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            return mFormat.format(Math.abs(value)) ;
        }
    }

    private void setDataForCheckbox(String[] xDataCheckbox, int [] colour) {
        try {
            Log.i("Tax Analysis", " Tax Analysis setDataForCheckbox is " + xDataCheckbox.length );

            for (int i = 0; i <  xDataCheckbox.length; i++) {

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
                parms_legen_layout.setMargins(20,10,20,10);
                LinearLayout legend_layout = new LinearLayout(mContext);
                legend_layout.setLayoutParams(parms_legen_layout);
                legend_layout.setOrientation(LinearLayout.HORIZONTAL);
                legend_layout.setBackgroundColor(colour[i]);
                left_layout.addView(legend_layout);
                TextView txt_unit = new TextView(mContext);
                LinearLayout.LayoutParams txtLeft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txt_unit.setLayoutParams(txtLeft_params);
                txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                parms_legen_layout.setMargins(20, 10, 20, 10);
                txt_unit.setText(xDataCheckbox[i]);
                left_layout.addView(txt_unit);
                i++;
                if ( (xDataCheckbox.length) == i) {
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
                LinearLayout.LayoutParams txtRight_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                right_txt_unit.setLayoutParams(txtRight_params);
                right_txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                right_txt_unit.setText( xDataCheckbox[i]);
                right_layout.addView(right_txt_unit);

                parent_layout.addView(right_layout);
                parent_layout.addView(left_layout);
                parentView.addView(parent_layout);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

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
//                RiskProfile fragment =
                  addFragmenttoStack(new RiskProfile());
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();

            }
            break;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_detail:
                //TaxPlanDetails taxCashFlowChart = new TaxPlanDetails();
                //showFragment(taxCashFlowChart);

                TaxPlanDetailViewPager taxPlanDetailViewPager=new TaxPlanDetailViewPager();
                showFragment(taxPlanDetailViewPager);

                break;
            case R.id.menu_summary:
                TaxPlanSummary taxPlanSummary = new TaxPlanSummary();
                showFragment(taxPlanSummary);
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

}

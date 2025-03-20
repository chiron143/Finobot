package com.purplepath.purplepath.insuranceSummary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyTextView;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.insurance.fragment.InsuranceDetailsFragment;
import com.purplepath.purplepath.insuranceAnalysis.InsuranceAnalysis;
import com.purplepath.purplepath.insuranceAnalysis.models.InsuranceChartModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.math.BigInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Suresh on 14/06/17.
 */

public class InsuranceTableView extends BaseFragment implements  View.OnClickListener {
    private Context mContext;

    OnActivityBackPressedListener backPressedListener;

    @BindView(R.id.estimate_expenses_value)
    DefaultCurrencyTextView estimate_expenses_value;

    @BindView(R.id.current_obligation_value)
    DefaultCurrencyTextView current_obligation_value;

    @BindView(R.id.future_requirement_value)
    DefaultCurrencyTextView future_requirement_value;

    @BindView(R.id.savingInversment_value)
    CurrencyTextView savingInversment_value;

    @BindView(R.id.currentInsurances_value)
    CurrencyTextView currentInsurances_value;

    @BindView(R.id.total_Overall_value)
    CurrencyTextView total_Overall_value;

    @BindView(R.id.total_Expanse_Savings_value)
    DefaultCurrencyTextView total_Expanse_Savings_value;

    @BindView(R.id.relative_left_arrow)
    RelativeLayout mleftRelativeLayout;

    @BindView(R.id.relative_center_home)
    RelativeLayout mcenterRelativeLayout;

    @BindView(R.id.relative_right_arrow)
    RelativeLayout mRightRelativeLayout;

    private TextView errorTextview;

    private  ScrollView scrollview;

    private String current_saving;
    private Float min_Surrival_cover;
    private Float min_requried_cover;
    private Float max_recommended_cover;

    private String current_Insurance;

    private String current_expenses, outstandingdept, future_obligation;

    private InsuranceChartModel insuranceChartModel;

    private OnActivityBackPressedListener mCallBackListener;


    private FloatingActionButton fab_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            setHasOptionsMenu(true);

            mCallBackListener = (OnActivityBackPressedListener) (mContext);

            backPressedListener= (OnActivityBackPressedListener) mContext;
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
        View insuranceView =  inflater.inflate(R.layout.fragment_insurance_tableview, container, false);
        ButterKnife.bind(this,insuranceView);
        mCallBackListener.setActionBarTitle("Life Insurance ");
        errorTextview = insuranceView.findViewById( R.id.empty_chart_display);
        scrollview = insuranceView.findViewById( R.id.scrollview);
        fab_id = insuranceView.findViewById(R.id.fab_id);
        callInsuranceAnalysisService();// Services call here ;
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        fab_id.setOnClickListener(this);
        return insuranceView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_chart);
        // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_summary:

                try{

                    addFragmenttoStack(new InsuranceSummaryview());

//                    mCallBackListener.onActivityBackPressed();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;




            case R.id.menu_chart:

                try{

                            addFragmenttoStack(new InsuranceAnalysis());

                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public  void callInsuranceAnalysisService(){
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<InsuranceChartModel> call = webServiceObj.callinsurance_plan_Service(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<InsuranceChartModel>() {
            @Override
            public void onResponse(Call<InsuranceChartModel> call, Response<InsuranceChartModel> response) {
                try {
                    Log.i("InsuranceTableView","InsuranceTableView user id"+UtileKit.getPersistedPurplePathPref("user_id"));
                    UtileKit.dismisssSpinnerDialog();

                    insuranceChartModel = response.body();
                    if (insuranceChartModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if(null!= insuranceChartModel.getData().getIns_plan()) {
                            scrollview.setVisibility(View.VISIBLE);
                            current_Insurance = (insuranceChartModel.getData().getIns_plan().getCurr_ins());
                            current_saving = (insuranceChartModel.getData().getIns_plan().getTot_asset());
                            min_Surrival_cover = floatConvertion(insuranceChartModel.getData().getIns_plan().getMin_sur_cov_req());
                            min_requried_cover = floatConvertion(insuranceChartModel.getData().getIns_plan().getReq_cov_req());
                            max_recommended_cover = floatConvertion(insuranceChartModel.getData().getIns_plan().getRecom_cov_req());
                            current_expenses = insuranceChartModel.getData().getIns_plan().getCurr_exp();
                            outstandingdept = insuranceChartModel.getData().getIns_plan().getTot_liab();
                            future_obligation = insuranceChartModel.getData().getIns_plan().getFut_req();
                            Log.i("InsuranceTableView", " InsuranceTableView current_Insurance services is " + current_Insurance
                                    + " current_saving " + current_saving + " min_Surrival_cover " + min_Surrival_cover + " min_requried_cover "
                                    + min_requried_cover + " max_recommended_cover " + Math.abs(max_recommended_cover) + " current_expenses " + current_expenses
                                    + " outstandingdept " + outstandingdept + " future_obligation " + future_obligation);



                            if (insuranceChartModel.getData().getIns_plan().getCurr_ins().equalsIgnoreCase("0") &&
                                    insuranceChartModel.getData().getIns_plan().getCurr_contr().equalsIgnoreCase("0") &&
                                    insuranceChartModel.getData().getIns_plan().getMin_sur_cov_req().equalsIgnoreCase("0")
                                    &&insuranceChartModel.getData().getIns_plan().getReq_cov_req().equalsIgnoreCase("0")
                                    && insuranceChartModel.getData().getIns_plan().getRecom_cov_req().equalsIgnoreCase("0")) {
                                scrollview.setVisibility(View.GONE);
                                errorTextview.setVisibility(View.VISIBLE);
                                errorTextview.setText(HomePageActivity.errorMessageInChart);
                                UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                            }
                            else {
                                    if(current_expenses!= null){
                                        estimate_expenses_value.setText(current_expenses);
                                    }
                                    if(outstandingdept!= null){
                                        current_obligation_value.setText(outstandingdept);
                                    }

                                    if(future_obligation!= null){
                                        future_requirement_value.setText(future_obligation);
                                    }

                                    if(current_saving!= null){
                                        savingInversment_value.setText("₹ "+String.valueOf(UtileKit.formatedNumbers(new BigInteger(current_saving))));
                                    }
                                    if(current_Insurance!= null){
                                        currentInsurances_value.setText("₹ "+String.valueOf(UtileKit.formatedNumbers(new BigInteger(current_Insurance))));
                                    }

                                    if(current_saving!= null && current_Insurance!= null){
                                        UtileKit.addIntegervalue(current_saving,current_Insurance);
//                                        String totalExpanseandSavings= String.valueOf(
//                                                UtileKit.formatedNumbers(savingValue.add(insuranceValue)));
                                        total_Expanse_Savings_value.setText("₹ "+UtileKit.addIntegervalue(current_saving,current_Insurance));
                                    }

                                if(current_expenses!= null && outstandingdept!= null && future_obligation!= null){

                                    BigInteger mcurrent_express = (new BigInteger(UtileKit.getStringwithoutCurreny(current_expenses)));
                                    BigInteger moutstandingdept = (new BigInteger(UtileKit.getStringwithoutCurreny(outstandingdept)));
                                    BigInteger mfuture_obligation = (new BigInteger(UtileKit.getStringwithoutCurreny(future_obligation)));
                                    BigInteger totalExpanseandSavings= mcurrent_express.add(moutstandingdept).add(mfuture_obligation);
                                    total_Overall_value.setText("₹ "+UtileKit.formatedNumbers(totalExpanseandSavings));
                                }
                            }
                        }else{
//                            scrolllinearlayout.setVisibility(View.GONE);
//                            errorTextview.setVisibility(View.VISIBLE);
//                            errorTextview.setText(HomePageActivity.errorMessageInChart);
//                            UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                        }
                    }else{
                        scrollview.setVisibility(View.GONE);
                        errorTextview.setVisibility(View.VISIBLE);
                        errorTextview.setText(HomePageActivity.errorMessageInChart);
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<InsuranceChartModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.fab_id:
            {
                addFragmenttoStack(new InsuranceDetailsFragment());
            }
            break;
        }

    }
    private float floatConvertion(String val) {
        float convVal = 0;
        try {
            convVal = Float.parseFloat(val);
        } catch (NumberFormatException e) {

        }
        return convVal;
    }


}

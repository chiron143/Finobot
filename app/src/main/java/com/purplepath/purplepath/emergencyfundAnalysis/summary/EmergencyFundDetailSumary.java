package com.purplepath.purplepath.emergencyfundAnalysis.summary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.emergencyfundAnalysis.EmergencyFundParent;
import com.purplepath.purplepath.emergencyfundAnalysis.model.EmergencyFundModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.networthanalysis.model.Net_worth;
import com.purplepath.purplepath.networthanalysis.model.NetworkAnalysisModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by dinesh on 26/04/17.
 */

public class EmergencyFundDetailSumary extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.cash_value_id)
    DefaultCurrencyTextView cashTextView;

    @BindView(R.id.saving_value_id)
    DefaultCurrencyTextView savingTxtView;


    @BindView(R.id.current_accout_value_id)
    DefaultCurrencyTextView currentAccountView;

    @BindView(R.id.fixedReqcurringDepValue_id)
    DefaultCurrencyTextView fixedReqDepositView;

    @BindView(R.id.termDepValue_id)
    DefaultCurrencyTextView termDepView;




    @BindView(R.id.cash_actual_value_id)
    DefaultCurrencyTextView cashactualTextView;

    @BindView(R.id.saving_actual_value_id)
    DefaultCurrencyTextView savingActualTxtView;

    @BindView(R.id.current_actual_accout_value_id)
    DefaultCurrencyTextView currentActualAccountView;

    @BindView(R.id.fixedReqcurringDep_actual_Value_id)
    DefaultCurrencyTextView fixedReqActualDepositView;

    @BindView(R.id.termDepValue_actual_id)
    DefaultCurrencyTextView termActualDepView;

    @BindView(R.id.overall_percentage)
    TextView overall_percentage;

    private FloatingActionButton fab_id;

    EmergencyFundModel mEmergencyFundModel;
    public static EmergencyFundDetailSumary newInstance(EmergencyFundModel mEmergencyFundModel) {

        Bundle args = new Bundle();
        args.putSerializable("emergencyKey",mEmergencyFundModel);

        EmergencyFundDetailSumary fragment = new EmergencyFundDetailSumary();
        fragment.setArguments(args);
        return fragment;
    }
    Context mContext;
    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;


    @Override
    public void onAttach(Context context) {
        backPressedListener = (OnActivityBackPressedListener) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_emerge_summary_detail_view, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        backPressedListener.setActionBarTitle("Emergency Fund");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        fab_id = view.findViewById(R.id.fab_id);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        fab_id.setOnClickListener(this);

        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey("emergencyKey")) {
                mEmergencyFundModel = (EmergencyFundModel) getArguments().getSerializable("emergencyKey");
                if (mEmergencyFundModel != null && mEmergencyFundModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    showAssetDetails(mEmergencyFundModel);
                }else {
                }
            }

        }else {
            callEmergFundAnalyService();
        }

        return view;
    }
    public void callEmergFundAnalyService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<EmergencyFundModel> call = webServiceObj.callEmergencyFundChartService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<EmergencyFundModel>() {
            @Override
            public void onResponse(Call<EmergencyFundModel> call, Response<EmergencyFundModel> response) {
                UtileKit.dismisssSpinnerDialog();
                mEmergencyFundModel = response.body();
                if (mEmergencyFundModel != null && mEmergencyFundModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    showAssetDetails(mEmergencyFundModel);
                }
                else
                    {
                   // UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                }
                }
            @Override
            public void onFailure(Call<EmergencyFundModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }

    private void showAssetDetails(EmergencyFundModel mEmergencyFundModel) {
        try {
            if (UtileKit.validateObjectValues(mEmergencyFundModel.getData().getEf_result().getEf_plan().getPlan_cash_per())) {
                cashTextView.setVisibility(View.VISIBLE);
                cashTextView.setText("₹ "+(UtileKit.rounddecimalNumber(mEmergencyFundModel.getData().
                        getEf_result().getEf_plan().getPlan_cash_per())));
            } else {
//                cashTextView.setVisibility(View.GONE);
            }
            if (UtileKit.validateObjectValues(mEmergencyFundModel.getData().getEf_result().getEf_plan().getPlan_savings_acc_per())) {
                savingTxtView.setVisibility(View.VISIBLE);
                savingTxtView.setText("₹ "+(UtileKit.rounddecimalNumber(mEmergencyFundModel.getData().
                        getEf_result().getEf_plan().getPlan_savings_acc_per())));
            } else {
//                savingTxtView.setVisibility(View.GONE);
            }
            if (UtileKit.validateObjectValues(mEmergencyFundModel.getData().getEf_result().getEf_plan().getPlan_curr_acc_per())) {
                currentAccountView.setVisibility(View.VISIBLE);
                currentAccountView.setText("₹ "+(UtileKit.rounddecimalNumber(mEmergencyFundModel.getData().
                        getEf_result().getEf_plan().getPlan_curr_acc_per())));
            } else {
//                currentAccountView.setVisibility(View.GONE);
            }
            if (UtileKit.validateObjectValues(mEmergencyFundModel.getData().getEf_result().getEf_plan().getPlan_fix_recc_dep_per())) {
                fixedReqDepositView.setVisibility(View.VISIBLE);
                fixedReqDepositView.setText("₹ "+(UtileKit.rounddecimalNumber(mEmergencyFundModel.getData().
                        getEf_result().getEf_plan().getPlan_fix_recc_dep_per())));
            } else {
//                fixedReqDepositView.setVisibility(View.GONE);
            }
            if (UtileKit.validateObjectValues(mEmergencyFundModel.getData().getEf_result().getEf_plan().getPlan_term_dep_per())) {
                termDepView.setVisibility(View.VISIBLE);
                termDepView.setText("₹ "+(UtileKit.rounddecimalNumber(mEmergencyFundModel.getData().
                        getEf_result().getEf_plan().getPlan_term_dep_per())));
            } else {
//                termDepView.setVisibility(View.GONE);
            }
            if (UtileKit.validateObjectValues(mEmergencyFundModel.getData().getEf_result().getEf_act().getAct_cash_per())) {
                cashactualTextView.setVisibility(View.VISIBLE);
                cashactualTextView.setText("₹ "+(UtileKit.rounddecimalNumber(mEmergencyFundModel.getData().
                        getEf_result().getEf_act().getAct_cash_per())));
            } else {
//                cashactualTextView.setVisibility(View.GONE);
            }
            if (UtileKit.validateObjectValues(mEmergencyFundModel.getData().getEf_result().getEf_act().getAct_savings_acc_per())) {
                savingActualTxtView.setVisibility(View.VISIBLE);
                savingActualTxtView.setText("₹ "+(UtileKit.rounddecimalNumber(mEmergencyFundModel.getData().
                        getEf_result().getEf_act().getAct_savings_acc_per())));
            } else {
//                savingActualTxtView.setVisibility(View.GONE);
            }
            if (UtileKit.validateObjectValues(mEmergencyFundModel.getData().getEf_result().getEf_act().getAct_curr_acc_per())) {
                currentActualAccountView.setVisibility(View.VISIBLE);
                currentActualAccountView.setText("₹ "+(UtileKit.rounddecimalNumber(mEmergencyFundModel.getData().
                        getEf_result().getEf_act().getAct_curr_acc_per())));
            } else {
//                currentActualAccountView.setVisibility(View.GONE);
            }
            if (UtileKit.validateObjectValues(mEmergencyFundModel.getData().getEf_result().getEf_act().getAct_fix_recc_dep_per())) {
                fixedReqActualDepositView.setVisibility(View.VISIBLE);
                fixedReqActualDepositView.setText("₹ "+(UtileKit.rounddecimalNumber(mEmergencyFundModel.getData().
                        getEf_result().getEf_act().getAct_fix_recc_dep_per())));
            } else {
//                fixedReqActualDepositView.setVisibility(View.GONE);
            }
            if (UtileKit.validateObjectValues(mEmergencyFundModel.getData().getEf_result().getEf_act().getAct_term_dep_per())) {
                termActualDepView.setVisibility(View.VISIBLE);
                termActualDepView.setText("₹ "+(UtileKit.rounddecimalNumber(mEmergencyFundModel.getData().
                        getEf_result().getEf_act().getAct_term_dep_per())));
            } else {
//                termActualDepView.setVisibility(View.GONE);
            }

            String overall_emergency=mEmergencyFundModel.getData().getEf_result().getEf_act().getAct_overall_per();
            overall_percentage.setText(overall_emergency+"%");


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_chart);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.menu_chart:
                fragment = new EmergencyFundParent();
                addToActivity(fragment);
                break;
            case R.id.menu_summary:
                fragment = new EmergencyFundSummaryFrag();
                addToActivity(fragment);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void addToActivity(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        androidx.fragment.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
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
            //            case R.id.fab_id:
//            {
//                addFragmenttoStack(new InsuranceDetailsFragment());
//            }
//            break;

        }
    }
}
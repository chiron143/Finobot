package com.purplepath.purplepath.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.ScoreChartAnalysis.ScoreChartActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.financialratio.CibilScoreFragment;
import com.purplepath.purplepath.financialratio.DebtExpenseRatioFragment;
import com.purplepath.purplepath.financialratio.DebtToIncomeRatioFragment;
import com.purplepath.purplepath.financialratio.ExpenseRatioFragment;
import com.purplepath.purplepath.financialratio.LeverageRatioFragment;
import com.purplepath.purplepath.financialratio.LiabilityRatioFragment;
import com.purplepath.purplepath.financialratio.SavingRatiosFragement;
import com.purplepath.purplepath.financialratio.SavingsToIncomeRatioFragment;
import com.purplepath.purplepath.financialratio.SolvancyRatioFragment;
import com.purplepath.purplepath.financialratio.model.FinanceRatioModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.getPersistedPurplePathPref;

/**
 * Created by Pratheep.S on 25-01-2017.
 */

public class FinancialRatiosHomePageFragment extends BaseFragment implements View.OnClickListener{


    private RelativeLayout relativeLayout;
    private ImageView mCenter_image;
    private OnActivityBackPressedListener mCallBackListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private LinearLayout mcenterImage;

    Fragment fragment;
    private Context mContext;
    private CustomTextView mHeading;
    private ImageView miv_savingRatios,msavingToIncomeRatios,mmiv_debtToIncomeRatios,miv_LeverageRatios,
            miv_SolvencyRatio,miv_debtToExpenseRatio,miv_liabilityRatio,miv_expenseRatios,property_score,cibil_score;
    FinanceRatioModel   getFinanceRatioModel;
    private CustomTextView mtv_savingRatios,mtv_expenseRatios,mtv_liabilityRatio,mtv_debtToExpenseRatio,
            mtv_savingToIncomeRatio,mtv_debtToIncomeRatios,mtv_LeverageRatios,mtv_SolvencyRatio;
    private  double ratio=0.0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getFinanceRatioModel==null)

        mContext=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_financial_ratio_hp,container,false);
        mHeading= view.findViewById(R.id.tv_heading);
        mHeading.setBold();


        miv_savingRatios= view.findViewById(R.id.iv_savingRatios);
        msavingToIncomeRatios= view.findViewById(R.id.savingToIncomeRatios);
        mmiv_debtToIncomeRatios= view.findViewById(R.id.iv_debtToIncomeRatios);
        miv_LeverageRatios= view.findViewById(R.id.iv_LeverageRatios);
        miv_SolvencyRatio= view.findViewById(R.id.iv_SolvencyRatio);
        miv_debtToExpenseRatio= view.findViewById(R.id.iv_debtToExpenseRatio);
        miv_liabilityRatio= view.findViewById(R.id.iv_liabilityRatio);
        miv_expenseRatios= view.findViewById(R.id.iv_expenseRatios);

        mcenterImage= view.findViewById(R.id.centerImage);
        property_score= view.findViewById(R.id.property_score);
        cibil_score= view.findViewById(R.id.cibil_score);

        property_score.setOnClickListener(this);
        cibil_score.setOnClickListener(this);

        miv_savingRatios.setOnClickListener(this);
        msavingToIncomeRatios.setOnClickListener(this);
        mmiv_debtToIncomeRatios.setOnClickListener(this);
        miv_LeverageRatios.setOnClickListener(this);
        miv_SolvencyRatio.setOnClickListener(this);
        miv_debtToExpenseRatio.setOnClickListener(this);
        miv_liabilityRatio.setOnClickListener(this);
        miv_expenseRatios.setOnClickListener(this);
        mcenterImage.setOnClickListener(this);

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        mtv_savingRatios= view.findViewById(R.id.tv_savingRatios);
        mtv_expenseRatios= view.findViewById(R.id.tv_expenseRatios);
        mtv_liabilityRatio= view.findViewById(R.id.tv_liabilityRatio);
        mtv_debtToExpenseRatio= view.findViewById(R.id.tv_debtToExpenseRatio);
        mtv_savingToIncomeRatio= view.findViewById(R.id.tv_savingToIncomeRatio);
        mtv_debtToIncomeRatios= view.findViewById(R.id.tv_debtToIncomeRatios);
        mtv_LeverageRatios= view.findViewById(R.id.tv_LeverageRatios);
        mtv_SolvencyRatio= view.findViewById(R.id.tv_SolvencyRatio);

        callGetFinancialRatioService();
        return view;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.property_score:
            {
                fragment=new ScoreChartActivity();
                addFragmentToActivity(fragment);
            }
            break;

            case R.id.cibil_score: {
                fragment=new CibilScoreFragment();
                addFragmentToActivity(fragment);
            }
            break;

            case R.id.iv_savingRatios:
            {
                try {
                    ratio = Double.parseDouble(getFinanceRatioModel.getData().getRatios().getSavings_ratio());
                }catch (Exception e){}

                fragment= SavingRatiosFragement.newInstance(ratio,"Savings Ratio");
                addFragmentToActivity(fragment);
            }
            break;

            case  R.id.savingToIncomeRatios:{

                try {
                    ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getSavings_income_ratio());
                }catch (Exception e){}

                fragment= SavingsToIncomeRatioFragment.newInstance(ratio,"Savings To Income Ratio");
                addFragmentToActivity(fragment);
                break;
            }
            case  R.id.iv_debtToIncomeRatios:{
                try {
                    ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getDebt_income_ratio());
                }catch (Exception e){}

                fragment= DebtToIncomeRatioFragment.newInstance(ratio,"Debt To Income Ratio");
                addFragmentToActivity(fragment);
            }
            break;
            case  R.id.iv_LeverageRatios:{
                try {
                    ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getLeverage_ratio());
                }catch (Exception e){}

                fragment= LeverageRatioFragment.newInstance(ratio,"Leverage Ratio");
                addFragmentToActivity(fragment);
            }
            break;
            case  R.id.iv_SolvencyRatio:{
                try {
                    ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getSolvency_ratio());
                }catch (Exception e){}

                fragment= SolvancyRatioFragment.newInstance(ratio,"Solvency Ratio");
                addFragmentToActivity(fragment);
            }
            break;
            case  R.id.iv_debtToExpenseRatio:{
                try {
                    ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getDebt_equit_ratio());
                }catch (Exception e){}

                fragment= DebtExpenseRatioFragment.newInstance(ratio,"Debt To Expense Ratio");
                addFragmentToActivity(fragment);
            }
            break;
            case  R.id.iv_liabilityRatio:{
                try {
                    ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getLiquidity_ratio());
                }catch (Exception e){}

                fragment= LiabilityRatioFragment.newInstance(ratio,"Liquidity Ratio");
                addFragmentToActivity(fragment);
            }
            break;
            case  R.id.iv_expenseRatios:{
                try {
                    ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getExpense_ratio());
                }catch (Exception e){}

                fragment= ExpenseRatioFragment.newInstance(ratio,"Expense Ratio");
                addFragmentToActivity(fragment);
            }
            break;
            case R.id.relative_left_arrow:
            {
                mCallBackListener.onActivityBackPressed();
                // fragment=new GoalTimelineFragmentMainPage();
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
        }
    }

    private void addFragmentToActivity(Fragment fragment) {
     addFragmenttoStack(fragment);

    }
    public void callGetFinancialRatioService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<FinanceRatioModel> call = webServiceObj.getFinanceRatioService(getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<FinanceRatioModel>() {
            @Override
            public void onResponse(Call<FinanceRatioModel> call, Response<FinanceRatioModel> response) {
                UtileKit.dismisssSpinnerDialog();
                FinanceRatioModel getLocFinanceRatioModel = response.body();
                if (getLocFinanceRatioModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    getFinanceRatioModel= getLocFinanceRatioModel;
                    try {
                    Double double_saving_ratio = Double.parseDouble(getFinanceRatioModel.getData().getRatios().getSavings_ratio());
                    Double double_expense_ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getExpense_ratio());
                    Double double_liability_ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getLiquidity_ratio());
                    Double double_debt_expense_ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getDebt_equit_ratio());
                    Double double_savin_income_ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getSavings_income_ratio());
                    Double double_debt_income_ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getDebt_income_ratio());
                    Double double_liq_ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getLeverage_ratio());
                    Double double_solve_ratio =  Double.parseDouble(getFinanceRatioModel.getData().getRatios().getSolvency_ratio());

                    mtv_savingRatios.setText("Savings Ratio"+" ["+Float.valueOf(Math.round(double_saving_ratio))+"]");
                    mtv_expenseRatios.setText("Expense Ratio"+" ["+Float.valueOf(Math.round(double_expense_ratio))+"]");
                    mtv_liabilityRatio.setText("Liquidity Ratio"+" ["+Float.valueOf(Math.round(double_liability_ratio))+"]");
                    mtv_debtToExpenseRatio.setText("Debt To Expense Ratio"+" ["+Float.valueOf(Math.round(double_debt_expense_ratio))+"]");
                    mtv_savingToIncomeRatio.setText("Savings To Income Ratio"+" ["+Float.valueOf(Math.round(double_savin_income_ratio))+"]");
                    mtv_debtToIncomeRatios.setText("Debt To Income Ratio"+" ["+Float.valueOf(Math.round(double_debt_income_ratio))+"]");
                    mtv_LeverageRatios.setText("Leverage Ratio"+" ["+Float.valueOf(Math.round(double_liq_ratio))+"]");
                    mtv_SolvencyRatio.setText("Solvency Ratio"+" ["+Float.valueOf(Math.round(double_solve_ratio))+"]");
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<FinanceRatioModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });


    }
}

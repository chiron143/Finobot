package com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.ui;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyDefaultEdt;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.interfaces.UpdateDateCallBackInterface;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.models.OutstandingBalModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.incomedetails.IncomeDynamicDetail.PARENT_CLASS_SOURCE;


/**
 * Created by Pratheep.S on 31-03-2017.
 */

public class OutstandingBalanceDialogFrag extends DialogFragment implements View.OnClickListener{

    @BindView(R.id.textview_calculate)
    TextView calculateBtn;

    @BindView(R.id.loan_amount_edt)
    CurrencyGhostView loan_amount_edt;

    @BindView(R.id.et_interest_rate)
    PercentageEditText et_interest_rate;

    @BindView(R.id.total_tenure_edt)
    NumberEditText total_tenure_edt;

    @BindView(R.id.emi_amount_edt)
    CurrencyGhostView emi_amount_edt;

    @BindView(R.id.tenure_completed_edt)
    NumberEditText tenure_completed_edt;

    @BindView(R.id.tv_balanceAmount)
    TextView tv_balanceAmount;

    @BindView(R.id.closebtnId)
    ImageView closeButton;

    @BindView(R.id.loan_amount_edt_calculaterImgView)
    CustomCalenderImageView loan_amount_edt_calculaterImgView;
    @BindView(R.id.emi_amount_edt_calculaterImgView)
    CustomCalenderImageView emi_amount_edt_calculaterImgView;



    public static final String TITLE = "";
    View nameEditview;
    Context mContext;

    OutstandingBalModel outStandingBalanceModel;
    static UpdateDateCallBackInterface updateDateCallBackInterface;
    String balanceAmt;
    public static OutstandingBalanceDialogFrag newInstance(UpdateDateCallBackInterface obj){
        updateDateCallBackInterface=obj;
        OutstandingBalanceDialogFrag fragment=new OutstandingBalanceDialogFrag();
        Bundle bundle =new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setStyle(DialogFragment.STYLE_NO_FRAME, R.style.APNA_DIALOG);

      //  mContext=getContext();
    }

    @Override
    public void onResume() {
        super.onResume();
        int height=getResources().getDisplayMetrics().heightPixels;
        int width=getResources().getDisplayMetrics().widthPixels;
        getDialog().getWindow().setLayout((int)(width*.90),(int)(height*.95));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.dialog_outstanding_bal,container,false);
        ButterKnife.bind(this,view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.white);
        calculateBtn.setOnClickListener(this);
        closeButton.setOnClickListener(this);

        loan_amount_edt.setfullHintTxt(getString(R.string.hint_home_loan_dailog_outsandingbalance));
        et_interest_rate.setHintText(getString(R.string.hint_home_loan_dailog_interset), ((TextInputLayout)
                (et_interest_rate.getParent()).getParent()));
        total_tenure_edt.setHintText(getString(R.string.hint_home_loan_dailog_total_tern), ((TextInputLayout)
                (total_tenure_edt.getParent()).getParent()));
        tenure_completed_edt.setHintText(getString(R.string.hint_home_loan_dailog_tenure_comp), ((TextInputLayout)
                (tenure_completed_edt.getParent()).getParent()));
        emi_amount_edt.setfullHintTxt(getString(R.string.hint_home_loan_dailog_emi));

        loan_amount_edt_calculaterImgView.setOnClickListener(this);
        emi_amount_edt_calculaterImgView.setOnClickListener(this);
        loan_amount_edt.setTextHint("Loan Amount");
        emi_amount_edt.setTextHint("EMI");


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textview_calculate:
                Log.i("spcheck", "onClick: ");
                UtileKit.showSpinnerDialog(mContext,false);
                calculateOutStandingBalanceService();
                break;
            case R.id.closebtnId:
                getDialog().dismiss();
                break;


            case R.id.loan_amount_edt_calculaterImgView:
                showCalDialog(loan_amount_edt);
                break;
            case R.id.emi_amount_edt_calculaterImgView:
                showCalDialog(emi_amount_edt);
                break;


        }
    }
    private void showCalDialog(View view) {
        nameEditview = view;
        String calculaterValue = ((CurrencyGhostView) nameEditview).getText().toString();
        Intent calculatorIntent = new Intent(getActivity(), CalculatorAct.class);
        calculatorIntent.putExtra(CalculatorAct.TITLE_ACTIVITY, TITLE);
        calculatorIntent.putExtra(CalculatorAct.PARENT_ACTIVITY, PARENT_CLASS_SOURCE);
        calculatorIntent.putExtra(CalculatorAct.VALUE, calculaterValue);
        startActivityForResult(calculatorIntent, CalculatorAct.REQUEST_RESULT_SUCCESSFUL);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CalculatorAct.REQUEST_RESULT_SUCCESSFUL) {
            String result = data.getStringExtra(CalculatorAct.RESULT);
            ((CurrencyGhostView) nameEditview).setText(result);
            ((CurrencyGhostView) nameEditview).getEditText().setBackgroundResource(R.drawable.edittextbackgrounggreen);
        }
    }
    private void calculateOutStandingBalanceService() {
        Call<OutstandingBalModel> call=(ServiceGenerator.createService(WebServiceCalls.class)).
                callOutStandingBalService(UtileKit.getStringwithoutDefaultCurreny(loan_amount_edt.getEditText()),
                et_interest_rate.getText().toString(),total_tenure_edt.getText().toString(),
                        tenure_completed_edt.getText().toString(),
                        UtileKit.getStringwithoutDefaultCurreny(emi_amount_edt.getEditText()));
        call.enqueue(new Callback<OutstandingBalModel>() {
            @Override
            public void onResponse(Call<OutstandingBalModel> call, Response<OutstandingBalModel> response) {
                //Log.i("spcheck", "onResponse: "+response.body());
                UtileKit.dismisssSpinnerDialog();
                outStandingBalanceModel=response.body();
                if(outStandingBalanceModel.getStatus_code().equals(UtileKit.SUCCESSCODE)){
                    balanceAmt=outStandingBalanceModel.getData().getOutst_bal();
                    if(!(balanceAmt.equals(null))) {
                        updateDateCallBackInterface.updateOutStandingBalance(balanceAmt);
                        tv_balanceAmount.setText("₹ "+(balanceAmt));
                    }
                }

            }


            @Override
            public void onFailure(Call<OutstandingBalModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }
}

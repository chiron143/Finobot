package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.dialog.Calculate_lease_payment_dialog;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.interfaces.CalculateLeaseInterface;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.interfaces.UpdateValueInFragmentInterface;
import com.purplepath.purplepath.fragments.BaseFragment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dinesh on 16/09/17.
 */

public class LeaseDetailFragment extends BaseFragment implements View.OnClickListener, CalculateLeaseInterface {


    UpdateValueInFragmentInterface updateInterface;
    CalculateLeaseInterface mCalculateLeaseInterface;

    @BindView(R.id.total_upfront_secqpay_edt_id)
    CurrencyGhostView total_upfront_secqpay_edt;

//    @BindView(R.id.total_upfront_secqpay_cal_id)
//    CustomCalenderImageView total_upfront_secqpay_cal;

    @BindView(R.id.monthlyLeasepayment_edt_id)
    CurrencyGhostView monthlyLeasepayment_edt;

    @BindView(R.id.monthlyLeasepayment_cal_id)
    CustomCalenderImageView monthlyLeasepayment_cal;


    @BindView(R.id.tax_edit_id)
    PercentageEditText tax_edit;

    @BindView(R.id.lease_tax_edit_id)
    PercentageEditText lease_tax_edit;



    @BindView(R.id.leaseforexpense_edt_id)
    CurrencyGhostView leaseforexpense_edt;

    @BindView(R.id.text_calculate_lease_label)
    TextView text_calculate_lease_label;


    @BindView(R.id.leaseforexpense_cal_id)
    CustomCalenderImageView leaseforexpense_cal;

    GhostViewTextWatchers total_upfront_secqpay_TW,monthlyLeasepayment_TW,leaseforexpense_TW;

    GeneralTextWatcher tax_Edit_TW;

    public static final String PARENT_CLASS_SOURCE = "com.gp89developers.example.MainActivity";
    public static final String TITLE = "";

    View nameEditview;

    public static LeaseDetailFragment newInstance(UpdateValueInFragmentInterface carBuyVsLeaseInterface) {
        
        Bundle args = new Bundle();
        
        LeaseDetailFragment fragment = new LeaseDetailFragment();
        fragment.updateInterface=carBuyVsLeaseInterface;
        fragment.setArguments(args);

        return fragment;
    }
    private void setUpdateValueInFragmentInterface(CalculateLeaseInterface calculateLeaseInterface) {
        mCalculateLeaseInterface = calculateLeaseInterface;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpdateValueInFragmentInterface(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.car_vs_lease_leaseview,container,false);
        ButterKnife.bind(this, view);
        return  view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inititializeVieW();

    }

    private void inititializeVieW() {

        total_upfront_secqpay_edt.setTextHint(getString(R.string.Totalupforntsecuritypay));
        total_upfront_secqpay_edt.setfullHintTxt(getString(R.string.hint_carbuyvslease_totalupfrontsecuritypay));

        monthlyLeasepayment_edt.setTextHint(getString(R.string.monthlyleasepay));
        monthlyLeasepayment_edt.setfullHintTxt(getString(R.string.hint_carbuyvslease_monthlyleasepay));

        leaseforexpense_edt.setTextHint(getString(R.string.leaseforexpense));
        leaseforexpense_edt.setfullHintTxt(getString(R.string.hint_carbuyvslease_leaseforexpense));

        total_upfront_secqpay_TW=new GhostViewTextWatchers(total_upfront_secqpay_edt);
        total_upfront_secqpay_edt.getEditText().addTextChangedListener(total_upfront_secqpay_TW);
        total_upfront_secqpay_edt.setEditTextId(R.id.total_upfront_secqpay_id);

        monthlyLeasepayment_TW=new GhostViewTextWatchers(monthlyLeasepayment_edt);
        monthlyLeasepayment_edt.getEditText().addTextChangedListener(monthlyLeasepayment_TW);
        monthlyLeasepayment_edt.setEditTextId(R.id.monthlyLeasepayment_id);

        leaseforexpense_TW=new GhostViewTextWatchers(leaseforexpense_edt);
        leaseforexpense_edt.getEditText().addTextChangedListener(leaseforexpense_TW);
        leaseforexpense_edt.setEditTextId(R.id.leaseforexpense_id);

        tax_Edit_TW=new GeneralTextWatcher(tax_edit);
        tax_edit.addTextChangedListener(tax_Edit_TW);

        GeneralTextWatcher lease_tax_edit_TW=new GeneralTextWatcher(lease_tax_edit);
        lease_tax_edit.addTextChangedListener(lease_tax_edit_TW);

        tax_edit.setHintText(getString(R.string.hint_carbuyvslease_rateofinterest), ((TextInputLayout)
                (tax_edit.getParent()).getParent()));

//        total_upfront_secqpay_cal.setOnClickListener(this);
        monthlyLeasepayment_cal.setOnClickListener(this);
        leaseforexpense_cal.setOnClickListener(this);
        text_calculate_lease_label.setOnClickListener(this);
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
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
//            case R.id.total_upfront_secqpay_cal_id:
//                showCalDialog(total_upfront_secqpay_edt);
//            break;
            case R.id.monthlyLeasepayment_cal_id:
                showCalDialog(monthlyLeasepayment_edt);
                break;
            case R.id.leaseforexpense_cal_id:
                showCalDialog(leaseforexpense_edt);
                break;
            case R.id.text_calculate_lease_label:

                showOutStandingBalDialog();
                break;

        }
    }

    private void showCalDialog(CurrencyGhostView view) {
        nameEditview = view;
        String calculaterValue = ((CurrencyGhostView) nameEditview).getText().toString();
        Intent calculatorIntent = new Intent(getActivity(), CalculatorAct.class);
        calculatorIntent.putExtra(CalculatorAct.TITLE_ACTIVITY, TITLE);
        calculatorIntent.putExtra(CalculatorAct.PARENT_ACTIVITY, PARENT_CLASS_SOURCE);
        calculatorIntent.putExtra(CalculatorAct.VALUE, calculaterValue);
        startActivityForResult(calculatorIntent, CalculatorAct.REQUEST_RESULT_SUCCESSFUL);
    }

    private void showOutStandingBalDialog() {
        Calculate_lease_payment_dialog fragment = Calculate_lease_payment_dialog.newInstance(mCalculateLeaseInterface);
        fragment.show(getActivity().getFragmentManager(), "Outstanding bal");

    }

    public Boolean mCarVsLeaseValidation(HashMap<String, String> homeValues) {
        if(UtileKit.validateObjectValuesAndCheckZero(homeValues.get("tot_upf_sec_pay")))
        {
            if(UtileKit.validateObjectValuesAndCheckZero(homeValues.get("mon_lease_pay")))
            {
                return true;
            }
            else {
                getFocusCurrency(monthlyLeasepayment_edt.getEditText());
                ((TextInputLayout) monthlyLeasepayment_edt.getEditText().getParent().getParent()).setError(getString(R.string.errormon_lease_pay));
                return false;

            }
        }
        else {
            getFocusCurrency(total_upfront_secqpay_edt.getEditText());
            ((TextInputLayout) total_upfront_secqpay_edt.getEditText().getParent().getParent()).setError(getString(R.string.errormon_total_upfront_secqpay));
            return false;
        }


    }
    private void getFocusCurrency(CurrencyGhostView editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
    private void getFocusCurrency(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void updateLeaseValue(String result, String message) {
        Log.e("Tag++++++++++++"+message,roundBigDecimal(new BigDecimal(message)).toPlainString());
        monthlyLeasepayment_edt.setText(""+roundBigDecimal(new BigDecimal(message)).toPlainString());
    }
    public static BigDecimal roundBigDecimal(final BigDecimal input){
        return input.round(
                new MathContext(
                        input.toBigInteger().toString().length(),
                        RoundingMode.HALF_UP
                )
        );
    }
    private class GeneralTextWatcher implements TextWatcher {
        EditText editText;

        public GeneralTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


        }

        @Override
        public void afterTextChanged(Editable s) {
            ((TextInputLayout) (editText.getParent()).getParent()).setErrorEnabled(false);
            ((TextInputLayout) (editText.getParent()).getParent()).setError(null);
            switch (editText.getId()) {
                case R.id.tax_edit_id:
                    updateInterface.updateCarValue( "tax_edit_id",editText.getText().toString());
                    break;
                case R.id.lease_tax_edit_id:
                    BigDecimal resultValue=BigDecimal.ZERO;
                    try {
                        BigDecimal percentValue = new BigDecimal(Double.parseDouble(editText.getText().toString()) / 100);
                        resultValue = new BigDecimal(UtileKit.getStringwithoutCurreny(monthlyLeasepayment_edt.getText().toString())).multiply(percentValue);
                    }catch (Exception e)
                    {e.printStackTrace();}
                    updateInterface.updateCarValue( "lease_tax",""+ resultValue.setScale(2, BigDecimal.ROUND_UP));

                    break;


            }
        }
    }

    private class GhostViewTextWatchers implements TextWatcher {
        CurrencyGhostView currencyGhostView;

        public GhostViewTextWatchers(CurrencyGhostView editText) {
            this.currencyGhostView = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateInterface.updateCarValue("total_upfront_secqpay_edt",s.toString() );
        }

        @Override
        public void afterTextChanged(Editable s) {
            ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setErrorEnabled(false);
            ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setError(null);
            String value = null;
            switch (currencyGhostView.getId()) {
                case R.id.total_upfront_secqpay_edt_id:
                    value = UtileKit.getStringwithoutCurreny(s.toString());
                    updateInterface.updateCarValue("tot_upf_sec_pay",value);
                    break;
                case R.id.monthlyLeasepayment_edt_id:
                    value = UtileKit.getStringwithoutCurreny(s.toString());
                    updateInterface.updateCarValue( "mon_lease_pay",value);
                    BigDecimal resultValue=BigDecimal.ZERO;
                    try {
                        BigDecimal percentValue = new BigDecimal(Double.parseDouble(lease_tax_edit.getText().toString()) / 100);
                        resultValue = new BigDecimal(UtileKit.getStringwithoutCurreny(monthlyLeasepayment_edt.getText().toString())).multiply(percentValue);
                        updateInterface.updateCarValue( "lease_tax",""+ resultValue.setScale(2, BigDecimal.ROUND_UP));
                    }catch (Exception e)
                    {e.printStackTrace();}
                    break;
                case R.id.leaseforexpense_edt_id:
                    value = UtileKit.getStringwithoutCurreny(s.toString());
                    updateInterface.updateCarValue( "lease_ter_exp",value);
                    break;


            }
        }
    }
}

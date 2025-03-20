package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.interfaces.UpdateValueInFragmentInterface;
import com.purplepath.purplepath.fragments.BaseFragment;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dinesh on 16/09/17.
 */

public class CarDetailFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    public static final String PARENT_CLASS_SOURCE = "com.gp89developers.example.MainActivity";
    public static final String TITLE = "";
    @BindView(R.id.loanReq_yes_RadioBtn)
    RadioButton laonReqYesBtn;
    @BindView(R.id.loanReq_no_RadioBtn)
    RadioButton laonReqNoBtn;
    @BindView(R.id.loanReq_RadioRg)
    RadioGroup loanReq_RadioGroup;
    @BindView(R.id.loanViewLayoutId)
    LinearLayout loanViewLayoutId;
    Context mContext;
    UpdateValueInFragmentInterface updateInterface;
    @BindView(R.id.downpayment_edt_id)
    CurrencyGhostView downpayment_edt;
    @BindView(R.id.downpayment_edt_cal_id)
    CustomCalenderImageView downpayment_edt_cal;
    @BindView(R.id.loanamount_edt_id)
    CurrencyGhostView loanamount_edt;
    @BindView(R.id.loanamount_edt_cal_id)
    CustomCalenderImageView loanamount_edt_cal;
    @BindView(R.id.loan_tenure_id)
    NumberEditText loan_tenure_edit;
    @BindView(R.id.loan_interest_id)
    PercentageEditText loan_interest_edit;
    @BindView(R.id.car_purchase_price_edt_id)
    CurrencyGhostView car_purchase_price_edt;
    @BindView(R.id.car_purchase_price_cal_id)
    CustomCalenderImageView car_purchase_price_cal;
    @BindView(R.id.eset_car_value_at_end_id)
    CurrencyGhostView eset_car_value_at_end;
    @BindView(R.id.eset_car_value_at_end_cal_id)
    CustomCalenderImageView eset_car_value_at_end_cal;
    View nameEditview;
//    ArrayList<String> carLoanmanditoryField = new ArrayList<String>() {{
//        add("loan_req");
//        add("car_pur_price");
//        add("down_pay");
//        add("loan_amt");
//        add("loan_tenure");
//        add("loan_int_rate");
//    }};
    private GhostViewTextWatchers downpayment_TW;
    private GhostViewTextWatchers loanamount_TW;
    private GhostViewTextWatchers car_purchase_price_TW;
    private GhostViewTextWatchers eset_car_value_at_TW;
    private GeneralTextWatcher loan_tenure_TW;
    private GeneralTextWatcher loan_interest_TW;

    public static CarDetailFragment newInstance(UpdateValueInFragmentInterface updateInterface) {

        Bundle args = new Bundle();

        CarDetailFragment fragment = new CarDetailFragment();
        fragment.updateInterface = updateInterface;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            updateInterface.updateCarValue("loan_req", "No");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.car_vs_lease_car_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
        downpayment_edt.setTextHint(getString(R.string.text_Down_Payment));
        loanamount_edt.setTextHint(getString(R.string.text_loan_amount));
        car_purchase_price_edt.setTextHint(getString(R.string.carpurchaseprice));
        loanReq_RadioGroup.setOnCheckedChangeListener(this);
    }

    private void initializeView() {
        downpayment_TW = new GhostViewTextWatchers(downpayment_edt);
        downpayment_edt.getEditText().addTextChangedListener(downpayment_TW);
        downpayment_edt.setEditTextId(R.id.downpayment_id);

        downpayment_edt.setfullHintTxt(getString(R.string.hint_carbuyvslease_downpayment));

        loanamount_TW = new GhostViewTextWatchers(loanamount_edt);
        loanamount_edt.setEditTextId(R.id.loanamount_id);
        loanamount_edt.getEditText().addTextChangedListener(loanamount_TW);

        loanamount_edt.setfullHintTxt(getString(R.string.hint_carbuyvslease_loanamount));


        car_purchase_price_TW = new GhostViewTextWatchers(car_purchase_price_edt);
        car_purchase_price_edt.setEditTextId(R.id.car_purchase_pricid);
        car_purchase_price_edt.getEditText().addTextChangedListener(car_purchase_price_TW);

        car_purchase_price_edt.setfullHintTxt(getString(R.string.hint_carbuyvslease_carpurchaseprice));


        eset_car_value_at_TW = new GhostViewTextWatchers(eset_car_value_at_end);
        eset_car_value_at_end.setEditTextId(R.id.eset_car_value_at_end_Id);
        eset_car_value_at_end.getEditText().addTextChangedListener(eset_car_value_at_TW);


        loan_tenure_TW = new GeneralTextWatcher(loan_tenure_edit);
        loan_tenure_edit.addTextChangedListener(loan_tenure_TW);

        loan_tenure_edit.setHintText(getString(R.string.hint_carbuyvslease_loantenure), ((TextInputLayout)
                (loan_tenure_edit.getParent()).getParent()));

        loan_interest_TW = new GeneralTextWatcher(loan_interest_edit);
        loan_interest_edit.addTextChangedListener(loan_interest_TW);

        loan_interest_edit.setHintText(getString(R.string.hint_carbuyvslease_loaninterestrate), ((TextInputLayout)
                (loan_interest_edit.getParent()).getParent()));

        downpayment_edt_cal.setOnClickListener(this);
        loanamount_edt_cal.setOnClickListener(this);
        car_purchase_price_cal.setOnClickListener(this);
        eset_car_value_at_end_cal.setOnClickListener(this);


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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == laonReqYesBtn.getId()) {
            UtileKit.getSwitchYesBtnView(laonReqYesBtn, laonReqNoBtn, mContext);
            updateInterface.updateCarValue("loan_req","Yes");
            loanViewLayoutId.setVisibility(View.VISIBLE);

        } else if (checkedId == laonReqNoBtn.getId()) {
            UtileKit.getSwitchNoBtnView(laonReqYesBtn, laonReqNoBtn, mContext);
            updateInterface.updateCarValue( "loan_req","No");
            loanViewLayoutId.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.downpayment_edt_cal_id:
                showCalDialog(downpayment_edt);
                break;
            case R.id.loanamount_edt_cal_id:
                showCalDialog(loanamount_edt);
                break;

            case R.id.car_purchase_price_cal_id:
                showCalDialog(car_purchase_price_edt);
                break;
            case R.id.eset_car_value_at_end_cal_id:
                showCalDialog(eset_car_value_at_end);

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

    public Boolean mCarVsLeaseValidation(HashMap<String, String> homeValues) {
        try {
            if (UtileKit.validateObjectValuesAndCheckZero(homeValues.get("car_pur_price"))) {
                if (homeValues.get("loan_req").equalsIgnoreCase("Yes")) {
                    if (UtileKit.validateObjectValuesAndCheckZero(homeValues.get("down_pay"))) {
                        if (UtileKit.validateObjectValuesAndCheckZero(homeValues.get("loan_amt"))) {
                            if (UtileKit.validateObjectValuesAndCheckZero(homeValues.get("loan_tenure"))) {
                                if (UtileKit.validateObjectValuesAndCheckZero(homeValues.get("loan_int_rate"))) {
                                    return true;
                                } else {
                                    getFocusCurrency(loan_interest_edit);
                                    loan_interest_edit.setError(getString(R.string.errordownpayment));
                                    return false;
                                }

                            } else {
                                getFocusCurrency(loan_tenure_edit);
                                loan_tenure_edit.setError(getString(R.string.errordownpayment));
                                return false;
                            }


                        } else {
                            getFocusCurrency(loanamount_edt.getEditText());
                            ((TextInputLayout) loanamount_edt.getEditText().getParent().getParent()).setError(getString(R.string.errordownpayment));
                            return false;
                        }

                    } else {
                        getFocusCurrency(downpayment_edt.getEditText());
                        ((TextInputLayout) downpayment_edt.getEditText().getParent().getParent()).setError(getString(R.string.errordownpayment));
                        return false;
                    }

                } else if (homeValues.get("loan_req").equalsIgnoreCase("No")) {
                    return true;
                }
            } else {
                getFocusCurrency(car_purchase_price_edt.getEditText());
                ((TextInputLayout) car_purchase_price_edt.getEditText().getParent().getParent()).setError(getString(R.string.errorcarprice));
                return false;
            }
            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return  false;
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
                case R.id.loan_tenure_id:
                    updateInterface.updateCarValue( "loan_tenure",editText.getText().toString());
                    break;
                case R.id.loan_interest_id:
                    updateInterface.updateCarValue( "loan_int_rate",editText.getText().toString());
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

        }

        @Override
        public void afterTextChanged(Editable s) {
            ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setErrorEnabled(false);
            ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setError(null);
            String value = null;
            switch (currencyGhostView.getId()) {
                case R.id.downpayment_edt_id:
                    value = UtileKit.getStringwithoutCurreny(s.toString());
                    updateInterface.updateCarValue( "down_pay",value);
                    break;
                case R.id.loanamount_edt_id:
                    value = UtileKit.getStringwithoutCurreny(s.toString());
                    updateInterface.updateCarValue( "loan_amt",value);
                    break;
                case R.id.car_purchase_price_edt_id:
                    value = UtileKit.getStringwithoutCurreny(s.toString());
                    updateInterface.updateCarValue("car_pur_price",value );
                    break;
                case R.id.eset_car_value_at_end_id:
                    value = UtileKit.getStringwithoutCurreny(s.toString());
                    updateInterface.updateCarValue("eset_car_value_at_end_id",value);
                    break;

            }
        }
    }
}

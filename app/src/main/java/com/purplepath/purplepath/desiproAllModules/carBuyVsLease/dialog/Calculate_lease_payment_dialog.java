package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.interfaces.CalculateLeaseInterface;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models.CalculateLeasePaymentModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Suresh on 24/01/18.
 */

public class Calculate_lease_payment_dialog extends DialogFragment implements View.OnClickListener {


    @BindView(R.id.closebtnId)
    ImageView closeButton;

    @BindView(R.id.depreciation_calculaterImgView)
    CustomCalenderImageView depreciation_calculaterImgView;

    @BindView(R.id.residual_value_calculaterImgView)
    CustomCalenderImageView residual_value_calculaterImgView;

    @BindView(R.id.capitized_cost_calculaterImgView)
    CustomCalenderImageView capitized_cost_calculaterImgView;

    @BindView(R.id.capitized_cost_reduction_edt)
    CurrencyGhostView capitized_cost_reduction_edt;

    @BindView(R.id.car_pur_price_edt)
    CurrencyGhostView car_pur_price_edt;

    @BindView(R.id.car_pur_price_calculaterImgView)
    CustomCalenderImageView car_pur_price_calculaterImgView;


    @BindView(R.id.residual_value_edt)
    CurrencyGhostView residual_value_edt;

    @BindView(R.id.depreciation_edt)
    CurrencyGhostView depreciation_edt;

    @BindView(R.id.total_tenure_edt)
    NumberEditText total_tenure_edt;


        @BindView(R.id.et_interest_rate)
    PercentageEditText et_interest_rate;

    @BindView(R.id.sale_tax_interest_rate)
    PercentageEditText sale_tax_interest_rate;

    CalculateLeaseInterface mCalculateLeaseInterface;

    @BindView(R.id.fab_id)
    FloatingActionButton leaseCalBtn;

    public static final String TITLE = "";
    View nameEditview;
    Context mContext;

    CalculateLeasePaymentModel mCalculateLeasePaymentModel;

    String balanceAmt,mMessage;
    public  static  final String PARENT_CLASS_SOURCE = "com.gp89developers.example.MainActivity";

    public static Calculate_lease_payment_dialog newInstance(CalculateLeaseInterface obj) {
        Calculate_lease_payment_dialog fragment = new Calculate_lease_payment_dialog();
        fragment.mCalculateLeaseInterface = obj;
        Bundle bundle = new Bundle();
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
        int height = getResources().getDisplayMetrics().heightPixels;
        int width = getResources().getDisplayMetrics().widthPixels;
        getDialog().getWindow().setLayout((int) (width * .90), (int) (height * .95));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_calculate_lease_payment, container, false);
        ButterKnife.bind(this, view);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.white);

        closeButton.setOnClickListener(this);

//        capitized_cost_reduction_edt.setfullHintTxt(getString(R.string.calculate_lease_capitise_reduction));
//        residual_value_edt.setfullHintTxt(getString(R.string.calculate_lease_residual));
//        depreciation_edt.setfullHintTxt(getString(R.string.calculate_lease_depreciation));
//        et_interest_rate.setHintText(getString(R.string.hint_home_loan_dailog_interset), ((TextInputLayout)
//                (et_interest_rate.getParent()).getParent()));
        car_pur_price_edt.setTextHint(getString(R.string.car_pur_price));
        capitized_cost_reduction_edt.setTextHint(getString(R.string.calculate_lease_capitise_reduction));
        residual_value_edt.setTextHint(getString(R.string.calculate_lease_residual));
        depreciation_edt.setTextHint(getString(R.string.calculate_lease_depreciation));

        GhostViewTextWatchers capitized_cost_reduction_TW=new GhostViewTextWatchers(capitized_cost_reduction_edt);
        capitized_cost_reduction_edt.getEditText().addTextChangedListener(capitized_cost_reduction_TW);

        GhostViewTextWatchers residual_value_TW=new GhostViewTextWatchers(residual_value_edt);
        residual_value_edt.getEditText().addTextChangedListener(residual_value_TW);

        GhostViewTextWatchers depreciation_TW=new GhostViewTextWatchers(depreciation_edt);
        depreciation_edt.getEditText().addTextChangedListener(depreciation_TW);

        GhostViewTextWatchers car_pur_price_TW=new GhostViewTextWatchers(car_pur_price_edt);
        car_pur_price_edt.getEditText().addTextChangedListener(car_pur_price_TW);



        GenericTextWatcher total_tenure_Tw= new GenericTextWatcher(total_tenure_edt);
        total_tenure_edt.addTextChangedListener(total_tenure_Tw);


        GenericTextWatcher et_interest_rate_Tw= new GenericTextWatcher(et_interest_rate);
        et_interest_rate.addTextChangedListener(et_interest_rate_Tw);

        GenericTextWatcher sale_tax_interest_rateTw= new GenericTextWatcher(sale_tax_interest_rate);
        sale_tax_interest_rate.addTextChangedListener(sale_tax_interest_rateTw);

        car_pur_price_calculaterImgView.setOnClickListener(this);
        depreciation_calculaterImgView.setOnClickListener(this);
        residual_value_calculaterImgView.setOnClickListener(this);
        capitized_cost_calculaterImgView.setOnClickListener(this);
        leaseCalBtn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textview_calculate:
                Log.i("spcheck", "onClick: ");
                UtileKit.showSpinnerDialog(mContext, false);
                calculateLeasePaymentService();
                break;
            case R.id.closebtnId:
                getDialog().dismiss();
                break;


            case R.id.capitized_cost_calculaterImgView:
                showCalDialog(capitized_cost_reduction_edt);
                break;
            case R.id.depreciation_calculaterImgView:
                showCalDialog(depreciation_edt);
                break;

            case R.id.residual_value_calculaterImgView:
                showCalDialog(residual_value_edt);
                break;
            case R.id.car_pur_price_calculaterImgView:
                showCalDialog(car_pur_price_edt);
                break;

            case R.id.fab_id:
                if (UtileKit.validateObjectValuesAndCheckZero(capitized_cost_reduction_edt.getText().toString())) {
                    if (UtileKit.validateObjectValuesAndCheckZero(residual_value_edt.getText().toString())) {
                        if (UtileKit.validateObjectValuesAndCheckZero(total_tenure_edt.getText().toString())) {
                            if (UtileKit.validateObjectValuesAndCheckZero(et_interest_rate.getText().toString())) {
                                if (UtileKit.validateObjectValuesAndCheckZero(sale_tax_interest_rate.getText().toString())) {
                                    calculateLeasePaymentService();
                                }else {

                                    ( (TextInputLayout) (sale_tax_interest_rate.getParent().getParent())).setError(getString(R.string.error_salestax));

                                    getFocusCurrency(sale_tax_interest_rate);
                                }

                            }else {

                                ( (TextInputLayout) (et_interest_rate.getParent().getParent())).setError(getString(R.string.error_salestax));

                                getFocusCurrency(et_interest_rate);
                            }
                        }
                        else{
                            ( (TextInputLayout) (total_tenure_edt.getParent().getParent())).setError(getString(R.string.error_interstrate));
                            getFocusCurrency(total_tenure_edt);

                        }


                    } else {

                                ((TextInputLayout) (residual_value_edt.getEditText().getParent()).getParent()).setError(getString(R.string.error_residual));
                        getFocusCurrencyGhost(residual_value_edt);
                    }

                } else
                {

                    ((TextInputLayout) (capitized_cost_reduction_edt.getEditText().getParent()).getParent()).setError(getString(R.string.error_capitalizedcostreduction));
                    getFocusCurrencyGhost(capitized_cost_reduction_edt);
                }








                break;


        }
    }
    private void getFocusCurrency(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
    private void getFocusCurrencyGhost(CurrencyGhostView editText) {
        editText.getEditText().requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
    private class GenericTextWatcher implements TextWatcher {
        EditText editText;

        public GenericTextWatcher(EditText editText) {
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

    private void calculateLeasePaymentService() {
        UtileKit.showSpinnerDialog(mContext,false);
        Call<CalculateLeasePaymentModel> call = (ServiceGenerator.createService(WebServiceCalls.class)).
                callCalculateLeasePayment(UtileKit.getStringwithoutCurreny(car_pur_price_edt.getText().toString()),UtileKit.getStringwithoutCurreny(car_pur_price_edt.getText().toString()),
                        UtileKit.getStringwithoutCurreny(capitized_cost_reduction_edt.getText().toString()),UtileKit.getStringwithoutCurreny((residual_value_edt.getText().toString())),total_tenure_edt.getText().toString(),
                        et_interest_rate.getText().toString(), sale_tax_interest_rate.getText().toString());
        call.enqueue(new Callback<CalculateLeasePaymentModel>() {
            @Override
            public void onResponse(Call<CalculateLeasePaymentModel> call, Response<CalculateLeasePaymentModel> response) {
                //Log.i("spcheck", "onResponse: "+response.body());
                UtileKit.dismisssSpinnerDialog();
                mCalculateLeasePaymentModel = response.body();
                if (mCalculateLeasePaymentModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    balanceAmt = mCalculateLeasePaymentModel.getData().getResult();
                    mMessage = mCalculateLeasePaymentModel.getData().getResult();
                    if (!(balanceAmt.equals(null))) {
                        if (!(mMessage.equals(null))) {
                            mCalculateLeaseInterface.updateLeaseValue(balanceAmt,mMessage);
                            dismiss();
//                        tv_balanceAmount.setText("₹ " + (balanceAmt));
                        }
                    }
                }

            }


            @Override
            public void onFailure(Call<CalculateLeasePaymentModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
            }
        });
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
            String value=null;
            switch (currencyGhostView.getId()) {



            }
        }
    }
}
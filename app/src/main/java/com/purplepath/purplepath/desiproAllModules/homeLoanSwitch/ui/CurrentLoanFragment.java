package com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.interfaces.UpdateDateCallBackInterface;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.interfaces.UpdateValueInActivityInterface;
import com.purplepath.purplepath.fragments.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.finobot.finobot.R.string.off;


/**
 * @author Pratheep
 */
@SuppressWarnings("ResourceType")
public class CurrentLoanFragment extends BaseFragment implements View.OnClickListener, UpdateDateCallBackInterface, RadioGroup.OnCheckedChangeListener {


    @Bind(R.id.payment_calculatorimage)
    CustomCalenderImageView mpayment_calculatorimage;

    @Bind(R.id.foreclosure_calculatorimage)
    CustomCalenderImageView mforeclosure_calculatorimage;

    @Bind(R.id.administration_fee_calculatorimage)
    CustomCalenderImageView madministration_fee_calculatorimage;

    @Bind(R.id.edt_out_standing_calculaterImgView)
    CustomCalenderImageView medt_out_standing_calculaterImgView;

    @Bind(R.id.emi_more_calculaterImgView)
    CustomCalenderImageView memi_more_calculaterImgView;


 /*   @Bind(R.id.percentage_payment_calculatorimage)
    CustomCalenderImageView percentage_payment_calculatorimage;
    @Bind(R.id.percentage_foreclosure_calculatorimage)
    CustomCalenderImageView percentage_foreclosure_calculatorimage;
    @Bind(R.id.percentage_administration_fee_calculatorimage)
    CustomCalenderImageView percentage_administration_fee_calculatorimage;*/


    //Init clickable TextView
    @Bind(R.id.outstanding_bal_label)
    TextView outstandingTxtView;

//    //Init TextInputLayout
      /*@Bind(R.id.edt_out_standing_layout)
      TextInputLayout edtOutStandingLayout;*/

    @Bind(R.id.edt_interest_layout)
    TextInputLayout edtInterestLayout;

    @Bind(R.id.edt_total_tenure_layout)
    TextInputLayout edt_total_tenure_layout;

    @Bind(R.id.edt_balance_tenure_layout)
    TextInputLayout edtBalTenureLayout;

    /*@Bind(R.id.edt_emi_layout)
    TextInputLayout edtEmiLayout;*/

    @Bind(R.id.edt_prepayment_layout)
    TextInputLayout edtPrepaymentLayout;

    @Bind(R.id.edt_prepaymentRupee_layout)
    TextInputLayout edtPrepaymentRupeeLayout;

    @Bind(R.id.edt_foreclosure_layout)
    TextInputLayout edtForeclosureLayout;

    @Bind(R.id.edt_administrationFee_layout)
    TextInputLayout edt_administrationFee_layout;

    @Bind(R.id.edt_insuranceFee_layout)
    TextInputLayout edt_insuranceFee_layout;

    @Bind(R.id.edt_foreclosureRupee_layout)
    TextInputLayout edtForeclosureRupeeLayout;

    @Bind(R.id.edt_administrationFeeRupee_layout)
    TextInputLayout edt_administrationFeeRupee_layout;

    @Bind(R.id.edt_insuranceFeeRupee_layout)
    TextInputLayout edt_insuranceFeeRupee_layout;

    // Init Edit text
    @Bind(R.id.edt_out_standing_view_id)
    CurrencyGhostView outstandingEdtView;

    @Bind(R.id.edt_interest_view_id)
    PercentageEditText interestEdtView;

    @Bind(R.id.edt_total_tenure_view_id)
    NumberEditText total_tenure_view_id;

    @Bind(R.id.edt_balance_tenure_view_id)
    NumberEditText tenureEdtView;

    @Bind(R.id.edt_emi_view_id)
    CurrencyGhostView emiEdtView;

    @Bind(R.id.edt_prepayment_view_id)
    PercentageEditText prepaymentEdtView;

    @Bind(R.id.edt_foreclosure_view_id)
    PercentageEditText foreClosureEdtView;

    @Bind(R.id.edt_insuranceFee_view_id)
    EditText insuranceFeeEdtView;

    @Bind(R.id.edt_administrationFee_view_id)
    PercentageEditText administrationFeeEdtView;

    @Bind(R.id.edt_prepaymentRupee_view_id)
    CurrencyGhostView prepaymentRupeeEdtView;

    @Bind(R.id.edt_foreclosureRupee_view_id)
    CurrencyGhostView foreClosureRupeeEdtView;

    @Bind(R.id.edt_insuranceFeeRupee_view_id)
    EditText insuranceFeeRupeeEdtView;

    @Bind(R.id.edt_administrationFeeRupee_view_id)
    CurrencyGhostView administrationFeeRupeeEdtView;


    //Init ImageView
    @Bind(R.id.emi_more_pop_id)
    ImageView emiMorepopView;

    //Radio Groups & Radio button
    @Bind(R.id.prepayement_rg)
    RadioGroup prepayement_rg;

    @Bind(R.id.prepayement_rupeee_RadioBtn)
    RadioButton prepayement_rupeee_RadioBtn;
    private static final int prepayement_rupeee_RadioBtnID = 1001;


    @Bind(R.id.prepayement_percent_RadioBtn)
    RadioButton prepayement_percent_RadioBtn;
    public static final int prepayement_percent_RadioBtnID = 1002;

    @Bind(R.id.foreclosure_rg)
    RadioGroup foreclosure_rg;

    @Bind(R.id.foreclosure_rupee_RadioBtn)
    RadioButton foreclosure_rupee_RadioBtn;
    public static final int foreclosuer_rupeee_RadioBtnID = 1003;

    @Bind(R.id.foreclosure_percent_RadioBtn)
    RadioButton foreclosure_percent_RadioBtn;
    public static final int foreclosuer_percent_RadioBtnID = 1004;

    @Bind(R.id.administrationFee_rg)
    RadioGroup administrationFee_rg;

    @Bind(R.id.administrationFee_rupee_RadioBtn)
    RadioButton administrationFee_rupee_RadioBtn;
    public static final int administrationFee_rupeee_RadioBtnID = 1005;

    @Bind(R.id.administrationFee_percent_RadioBtn)
    RadioButton administrationFee_percent_RadioBtn;
    public static final int administrationFee_percent_RadioBtnID = 1006;

    @Bind(R.id.insuranceFee_rg)
    RadioGroup insuranceFee_rg;

    @Bind(R.id.insuranceFee_rupee_RadioBtn)
    RadioButton insuranceFee_rupee_RadioBtn;
    public static final int insuranceFee_rupeee_RadioBtnID = 1007;

    @Bind(R.id.insuranceFee_percent_RadioBtn)
    RadioButton insuranceFee_percent_RadioBtn;
    public static final int insuranceFee_percent_RadioBtnID = 1008;

    String loanStartDate, firstEmiPaidDate, lastEmiPaidDate, nextEmiDueDate, finalEmiDate;

    UpdateValueInActivityInterface updateInterface;
    UpdateDateCallBackInterface updateDateCallBackInterface;

    Context mContext;
    public static final String TITLE = "";
    View nameEditview;

    View namePercentage;
    public static final String PARENT_CLASS_SOURCE = "com.gp89developers.example.MainActivity";
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mContext = getContext();
//            updateInterface = (UpdateValueInActivityInterface) mContext;
//            updateDateCallBackInterface = (UpdateDateCallBackInterface) mContext;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_current_loan, container, false);

        ButterKnife.bind(this, view);

        outstandingEdtView.setEditTextId(R.id.outstandingEdtView_id);
        emiEdtView.setEditTextId(R.id.emiEdtView_id);

        mpayment_calculatorimage.setOnClickListener(this);
        mforeclosure_calculatorimage.setOnClickListener(this);
        madministration_fee_calculatorimage.setOnClickListener(this);
        medt_out_standing_calculaterImgView.setOnClickListener(this);
        memi_more_calculaterImgView.setOnClickListener(this);

        emiMorepopView.setOnClickListener(this);
        outstandingTxtView.setOnClickListener(this);

        outstandingEdtView.getEditText().addTextChangedListener(new GenericTextWatchers(outstandingEdtView));
        emiEdtView.getEditText().addTextChangedListener(new GenericTextWatchers(emiEdtView));

        interestEdtView.addTextChangedListener(new GenericTextWatcher(interestEdtView));
        interestEdtView.setHintText(getString(R.string.hint_home_loan_current_intersetrateperannum), ((TextInputLayout)
                (interestEdtView.getParent()).getParent()));

        total_tenure_view_id.addTextChangedListener(new GenericTextWatcher(total_tenure_view_id));
        total_tenure_view_id.setHintText(getString(R.string.hint_home_loan_current_totaltenure), ((TextInputLayout)
                (total_tenure_view_id.getParent()).getParent()));

        tenureEdtView.addTextChangedListener(new GenericTextWatcher(tenureEdtView));
        tenureEdtView.setHintText(getString(R.string.hint_home_loan_current_balancetenure), ((TextInputLayout)
                (tenureEdtView.getParent()).getParent()));


        prepaymentRupeeEdtView.getEditText().addTextChangedListener(new GenericTextWatchers(prepaymentRupeeEdtView));
        foreClosureRupeeEdtView.getEditText().addTextChangedListener(new GenericTextWatchers(foreClosureRupeeEdtView));
        administrationFeeRupeeEdtView.getEditText().addTextChangedListener(new GenericTextWatchers(administrationFeeRupeeEdtView));
        //insuranceFeeRupeeEdtView.addTextChangedListener(new GenericTextWatcher(insuranceFeeRupeeEdtView));


        prepaymentEdtView.addTextChangedListener(new GenericTextWatcher(prepaymentEdtView));
        prepaymentEdtView.setHintText(getString(R.string.hint_home_loan_current_prepaymentcharges), ((TextInputLayout)
                (prepaymentEdtView.getParent()).getParent()));

        foreClosureEdtView.addTextChangedListener(new GenericTextWatcher(foreClosureEdtView));
        foreClosureEdtView.setHintText(getString(R.string.hint_home_loan_current_foreclosurecharges), ((TextInputLayout)
                (foreClosureEdtView.getParent()).getParent()));

        administrationFeeEdtView.addTextChangedListener(new GenericTextWatcher(administrationFeeEdtView));
        administrationFeeEdtView.setHintText(getString(R.string.hint_home_loan_current_administrationfee), ((TextInputLayout)
                (administrationFeeEdtView.getParent()).getParent()));
        //insuranceFeeEdtView.addTextChangedListener(new GenericTextWatcher(insuranceFeeEdtView));

        prepayement_rg.setOnCheckedChangeListener(this);
        foreclosure_rg.setOnCheckedChangeListener(this);
        administrationFee_rg.setOnCheckedChangeListener(this);
        insuranceFee_rg.setOnCheckedChangeListener(this);

        outstandingEdtView.setTextHint("Outstanding Balance");
        outstandingEdtView.setfullHintTxt(getString(R.string.hint_home_loan_current_outsandingbalance));

        emiEdtView.setTextHint("EMI");
        emiEdtView.setfullHintTxt(getString(R.string.hint_home_loan_current_EMI));

        prepaymentRupeeEdtView.setTextHint("Prepayment Charges");
        prepaymentRupeeEdtView.setfullHintTxt(getString(R.string.hint_home_loan_current_prepaymentcharges));

        foreClosureRupeeEdtView.setTextHint("Foreclosure Charges");
        foreClosureRupeeEdtView.setfullHintTxt(getString(R.string.hint_home_loan_current_foreclosurecharges));

        administrationFeeRupeeEdtView.setTextHint("Administration Fee");
        administrationFeeRupeeEdtView.setfullHintTxt(getString(R.string.hint_home_loan_current_administrationfee));

        setCheckedValueInRadioButton();

        return view;
    }

    private void setCheckedValueInRadioButton() {
        UtileKit.getSwitchYesBtnView(prepayement_rupeee_RadioBtn, prepayement_percent_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnView(foreclosure_rupee_RadioBtn, foreclosure_percent_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnView(administrationFee_rupee_RadioBtn, administrationFee_percent_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnView(insuranceFee_rupee_RadioBtn, insuranceFee_percent_RadioBtn, mContext);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.emi_more_pop_id:
                showEmiDialogFragment();
                break;
            case R.id.outstanding_bal_label:
                showOutStandingBalDialog();
                break;


            case R.id.payment_calculatorimage:
                showCalDialog(prepaymentRupeeEdtView);
                break;

            case R.id.foreclosure_calculatorimage:
                showCalDialog(foreClosureRupeeEdtView);
                break;

            case R.id.administration_fee_calculatorimage:
                showCalDialog(administrationFeeRupeeEdtView);
                break;
            case R.id.edt_out_standing_calculaterImgView:
                showCalDialog(outstandingEdtView);
                break;
            case R.id.emi_more_calculaterImgView:
                showCalDialog(emiEdtView);
                break;



           /* case R.id.percentage_payment_calculatorimage:
                showCalDialog1(prepaymentEdtView);
                break;
            case R.id.percentage_foreclosure_calculatorimage:
                showCalDialog1(foreClosureEdtView);
                break;
            case R.id.percentage_administration_fee_calculatorimage:
                showCalDialog1(administrationFeeEdtView);
                break;*/


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

    private void showOutStandingBalDialog() {
        OutstandingBalanceDialogFrag fragment = OutstandingBalanceDialogFrag.newInstance(this);
        fragment.show(getActivity().getFragmentManager(), "Outstanding bal");

    }

    private void showEmiDialogFragment() {
        EmiDialogFragment fragment = EmiDialogFragment.newInstance(this, loanStartDate, firstEmiPaidDate, lastEmiPaidDate, nextEmiDueDate, finalEmiDate);
        fragment.show(getFragmentManager(), "emi");
    }


    @Override
    public void updateAllDates(String loanStartDate, String firstEmiPaidDate, String lastEmiPaidDate, String nextEmiDueDate, String finalEmiDate) {
        this.loanStartDate = loanStartDate;
        this.firstEmiPaidDate = firstEmiPaidDate;
        this.lastEmiPaidDate = lastEmiPaidDate;
        this.nextEmiDueDate = nextEmiDueDate;
        this.finalEmiDate = finalEmiDate;
        updateDateCallBackInterface.updateAllDates(loanStartDate, firstEmiPaidDate, lastEmiPaidDate, nextEmiDueDate, finalEmiDate);
    }

    @Override
    public void updateOutStandingBalance(String balanceAmount) {
        outstandingEdtView.setText(String.valueOf(Math.round(Float.parseFloat(balanceAmount))));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()) {
            case R.id.prepayement_rg:
                if (checkedId == R.id.prepayement_rupeee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(prepayement_rupeee_RadioBtn, prepayement_percent_RadioBtn, mContext);
                    edtPrepaymentRupeeLayout.setVisibility(View.VISIBLE);
                    edtPrepaymentLayout.setVisibility(View.GONE);

                    mpayment_calculatorimage.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.prepayement_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(prepayement_rupeee_RadioBtn, prepayement_percent_RadioBtn, mContext);
                    edtPrepaymentRupeeLayout.setVisibility(View.GONE);
                    edtPrepaymentLayout.setVisibility(View.VISIBLE);

                    mpayment_calculatorimage.setVisibility(View.GONE);
                    calculatePrepayementPercentageValues();
                    getFocus(prepaymentEdtView);
                }

                break;
            case R.id.foreclosure_rg:
                if (checkedId == R.id.foreclosure_rupee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(foreclosure_rupee_RadioBtn, foreclosure_percent_RadioBtn, mContext);
                    edtForeclosureRupeeLayout.setVisibility(View.VISIBLE);
                    edtForeclosureLayout.setVisibility(View.GONE);

                    mforeclosure_calculatorimage.setVisibility(View.VISIBLE);
                    //percentage_foreclosure_calculatorimage.setVisibility(View.GONE);

                } else if (checkedId == R.id.foreclosure_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(foreclosure_rupee_RadioBtn, foreclosure_percent_RadioBtn, mContext);
                    edtForeclosureRupeeLayout.setVisibility(View.GONE);
                    edtForeclosureLayout.setVisibility(View.VISIBLE);

                    mforeclosure_calculatorimage.setVisibility(View.GONE);
                    //percentage_foreclosure_calculatorimage.setVisibility(View.VISIBLE);

                    calculateForeclosurePercentageValues();
                    getFocus(foreClosureEdtView);
                }

                break;
            case R.id.administrationFee_rg:

                if (checkedId == R.id.administrationFee_rupee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(administrationFee_rupee_RadioBtn, administrationFee_percent_RadioBtn, mContext);
                    edt_administrationFeeRupee_layout.setVisibility(View.VISIBLE);
                    edt_administrationFee_layout.setVisibility(View.GONE);


                    madministration_fee_calculatorimage.setVisibility(View.VISIBLE);
                    //percentage_administration_fee_calculatorimage.setVisibility(View.GONE);


                } else if (checkedId == R.id.administrationFee_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(administrationFee_rupee_RadioBtn, administrationFee_percent_RadioBtn, mContext);
                    edt_administrationFeeRupee_layout.setVisibility(View.GONE);
                    edt_administrationFee_layout.setVisibility(View.VISIBLE);

                    madministration_fee_calculatorimage.setVisibility(View.GONE);
                   // percentage_administration_fee_calculatorimage.setVisibility(View.VISIBLE);

                    calculateAdministrationFeePercentValues();
                    getFocus(administrationFeeEdtView);
                }
                break;
            case R.id.insuranceFee_rg:
                if (checkedId == R.id.insuranceFee_rupee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(insuranceFee_rupee_RadioBtn, insuranceFee_percent_RadioBtn, mContext);
                    edt_insuranceFeeRupee_layout.setVisibility(View.VISIBLE);
                    edt_insuranceFee_layout.setVisibility(View.GONE);
                } else if (checkedId == R.id.insuranceFee_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(insuranceFee_rupee_RadioBtn, insuranceFee_percent_RadioBtn, mContext);
                    edt_insuranceFeeRupee_layout.setVisibility(View.GONE);
                    edt_insuranceFee_layout.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public static CurrentLoanFragment newInstance(UpdateDateCallBackInterface updateDateCallBackInterface, UpdateValueInActivityInterface updateInterface) {

        CurrentLoanFragment fragment=new CurrentLoanFragment();
        fragment.updateDateCallBackInterface = updateDateCallBackInterface;
        fragment.updateInterface = updateInterface;

        return fragment;
    }

    class GenericTextWatchers implements TextWatcher {
        CurrencyGhostView currencyGhostView;

        public GenericTextWatchers(CurrencyGhostView currencyGhostView) {
            this.currencyGhostView = currencyGhostView;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            switch (currencyGhostView.getId()) {
                case R.id.edt_out_standing_view_id:
                    ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setError(null);
                    updateInterface.sendValue(s.toString(), 0);
                   // edtOutStandingLayout.setError(null);
                    calculatePrepayementPercentageValues();
                    calculateForeclosurePercentageValues();
                    calculateAdministrationFeePercentValues();
                    break;
                case R.id.edt_emi_view_id:
                    ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setError(null);
                   // edtEmiLayout.setError(null);
                    updateInterface.sendValue(s.toString(), 4);
                    break;
            }
        }
    }






    class GenericTextWatcher implements TextWatcher {
        EditText currencyGhostView;

        public GenericTextWatcher(EditText currencyGhostView) {
            this.currencyGhostView = currencyGhostView;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            switch (currencyGhostView.getId()) {
                case R.id.edt_interest_view_id:
                    edtInterestLayout.setErrorEnabled(false);
                    edtInterestLayout.setError(null);
                    updateInterface.sendValue(s.toString(), 1);
                    break;

                case R.id.edt_total_tenure_view_id:
                    edt_total_tenure_layout.setErrorEnabled(false);
                    edt_total_tenure_layout.setError(null);
                    updateInterface.sendValue(s.toString(), 2);
                    break;

                case R.id.edt_balance_tenure_view_id:
                    edtBalTenureLayout.setErrorEnabled(false);
                    edtBalTenureLayout.setError(null);
                    updateInterface.sendValue(s.toString(), 3);
                    break;


                case R.id.edt_prepayment_view_id:
                    calculatePrepayementPercentageValues();

                    break;

                case R.id.edt_foreclosure_view_id:
                    calculateForeclosurePercentageValues();

                    break;
                case R.id.edt_administrationFee_view_id:
                    calculateAdministrationFeePercentValues();

                    break;

                 /*case R.id.edt_insuranceFee_view_id:
                     if(insuranceFee_rg.getCheckedRadioButtonId()==R.id.insuranceFee_percent_RadioBtn) {
                         edt_insuranceFee_layout.setError(null);
                         updateInterface.sendValue(s.toString(), 7);
                     }
                     break;*/

                case R.id.edt_prepaymentRupee_view_id:
                    if (prepayement_rg.getCheckedRadioButtonId() == R.id.prepayement_rupeee_RadioBtn) {
                        edtPrepaymentRupeeLayout.setError(null);
                        updateInterface.sendValue(s.toString(), 5);
                    }

                    break;

                case R.id.edt_foreclosureRupee_view_id:
                    if (foreclosure_rg.getCheckedRadioButtonId() == R.id.foreclosure_rupee_RadioBtn) {
                        edtForeclosureRupeeLayout.setErrorEnabled(false);
                        edtForeclosureRupeeLayout.setError(null);
                        updateInterface.sendValue(s.toString(), 6);
                    }
                    break;
                case R.id.edt_administrationFeeRupee_view_id:
                    if (administrationFee_rg.getCheckedRadioButtonId() == R.id.administrationFee_rupee_RadioBtn) {
                        edt_administrationFeeRupee_layout.setErrorEnabled(false);
                        edt_administrationFeeRupee_layout.setError(null);
                        updateInterface.sendValue(s.toString(), 7);
                    }
                    break;

               /*  case R.id.edt_insuranceFeeRupee_view_id:
                     if(insuranceFee_rg.getCheckedRadioButtonId()==R.id.insuranceFee_rupee_RadioBtn) {
                         edt_insuranceFeeRupee_layout.setError(null);
                         updateInterface.sendValue(s.toString(), 7);
                     }
                     break;*/
            }
        }
    }







    private void updateAdministrationFeeRupeeValue() {
        if(UtileKit.validateObjectValues(administrationFeeRupeeEdtView.getText())) {
            if (administrationFee_rg.getCheckedRadioButtonId() == R.id.administrationFee_rupee_RadioBtn) {
                edt_administrationFeeRupee_layout.setErrorEnabled(false);
                edt_administrationFeeRupee_layout.setError(null);
                updateInterface.sendValue(administrationFeeRupeeEdtView.getText().toString(), 7);
            }
        }

    }

    private void updateForeClosureRupeeValue() {
        if(UtileKit.validateObjectValues(foreClosureRupeeEdtView.getText())) {
            if (foreclosure_rg.getCheckedRadioButtonId() == R.id.foreclosure_rupee_RadioBtn) {
                edtForeclosureRupeeLayout.setErrorEnabled(false);
                edtForeclosureRupeeLayout.setError(null);
                updateInterface.sendValue(foreClosureRupeeEdtView.getText().toString(), 6);
            }
        }
    }

    private void updatePrepayementRupeeValue() {
        if (prepayement_rg.getCheckedRadioButtonId() == R.id.prepayement_rupeee_RadioBtn) {
            if(prepaymentRupeeEdtView.getText()!=null&&prepaymentRupeeEdtView.getText().equals("")) {
                edtPrepaymentRupeeLayout.setErrorEnabled(false);
                edtPrepaymentRupeeLayout.setError(null);
                updateInterface.sendValue(prepaymentRupeeEdtView.getText().toString(), 5);
            }
        }
    }

    private void calculateAdministrationFeePercentValues() {
        if (administrationFee_rg.getCheckedRadioButtonId() == R.id.administrationFee_percent_RadioBtn) {
            if (checkOutstandingBalanceValueNotEmpty()) {
                if (administrationFeeEdtView.getText().toString() != null && !(administrationFeeEdtView.getText().toString().equals(""))) {
                    edt_administrationFee_layout.setErrorEnabled(false);
                    edt_administrationFee_layout.setError(null);
                    //updateInterface.sendValue(s.toString(), 7);
                    Float amount = ((Float.parseFloat(administrationFeeEdtView.getText().toString())) / 100) * Float.parseFloat(UtileKit.getStringwithoutCurreny(outstandingEdtView.getText().toString()));
                    updateInterface.sendValue(amount+"", 7);
                    Log.i("spcheck", "afterTextChanged: prepayement" + amount);
                }else{
                    updateInterface.sendValue(0+"", 7);
                }
            }
        }

    }

    private void calculateForeclosurePercentageValues() {
        if (foreclosure_rg.getCheckedRadioButtonId() == R.id.foreclosure_percent_RadioBtn) {
            if (checkOutstandingBalanceValueNotEmpty()) {
                if (foreClosureEdtView.getText().toString() != null && !(foreClosureEdtView.getText().toString().equals(""))) {
                    edtForeclosureLayout.setErrorEnabled(false);
                    edtForeclosureLayout.setError(null);
                    //updateInterface.sendValue(s.toString(), 6);
                    Float amount = ((Float.parseFloat(foreClosureEdtView.getText().toString())) / 100) * Float.parseFloat(UtileKit.getStringwithoutCurreny(outstandingEdtView.getText().toString()));
                    updateInterface.sendValue(amount+"", 6);
                    Log.i("spcheck", "afterTextChanged: prepayement" + amount);
                }else{
                    updateInterface.sendValue(0+"", 6);
                }
            }
        }
    }

    private void calculatePrepayementPercentageValues() {
        if (prepayement_rg.getCheckedRadioButtonId() == R.id.prepayement_percent_RadioBtn) {
            if (checkOutstandingBalanceValueNotEmpty()) {
                if (prepaymentEdtView.getText().toString() != null && !(prepaymentEdtView.getText().toString().equals(""))) {
                    edtPrepaymentLayout.setErrorEnabled(false);
                    edtPrepaymentLayout.setError(null);
                    //updateInterface.sendValue(s.toString(), 5);
                    Float amount = ((Float.parseFloat(prepaymentEdtView.getText().toString())) / 100) * Float.parseFloat(UtileKit.getStringwithoutCurreny(outstandingEdtView.getText().toString()));
                    updateInterface.sendValue(amount+"", 5);

                } else{
                    updateInterface.sendValue(0+"",5);
                }
            }
        }
    }


    private boolean checkOutstandingBalanceValueNotEmpty() {
        boolean isNotEmpty = true;
        if (outstandingEdtView.getText().toString() == null || outstandingEdtView.getText().toString().equals("0") || outstandingEdtView.getText().toString().equals("")) {
            //UtileKit.alertDialog("Outstanding balance is empty","Please fill Outstanding balance first",mContext);
            //UtileKit.alertRetrofitExceptionalert("Please fill Outstanding balance first",mContext);
            /*UtileKit.showAlertDialog(mContext,"Please fill Outstanding balance first");
            setCustomError(edtOutStandingLayout, "Please fill Outstanding balance first");
            outstandingEdtView.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(tenureEdtView, InputMethodManager.SHOW_IMPLICIT);*/
            isNotEmpty = false;
        }
        return isNotEmpty;
    }

    public void setErrorCurrentLoan(int index) {
        switch (index) {
            case 0:
                setError((TextInputLayout) (outstandingEdtView.getEditText().getParent()).getParent());
                getFocusCurrencyGhost(outstandingEdtView);
               // Log.i("spcheck", "setError CurrentLoan: is called method 1");
                break;
            case 1:
                setError(edtInterestLayout);
                getFocus(interestEdtView);
                break;

            case 2:
                setError(edt_total_tenure_layout);
                getFocus(total_tenure_view_id);
                break;

            case 3:
                setError(edtBalTenureLayout);
                getFocus(tenureEdtView);
                break;

            case 4:
                setError(((TextInputLayout)(emiEdtView.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(emiEdtView);
                break;

            case 5:
                if (prepayement_rg.getCheckedRadioButtonId() == R.id.prepayement_rupeee_RadioBtn) {
                    setError(edtPrepaymentRupeeLayout);
                    getFocusCurrencyGhost(prepaymentRupeeEdtView);
                } else if (prepayement_rg.getCheckedRadioButtonId() == R.id.prepayement_percent_RadioBtn) {
                    setError(edtPrepaymentLayout);
                    getFocus(prepaymentEdtView);
                }
                break;

            case 6:
                if (foreclosure_rg.getCheckedRadioButtonId() == R.id.foreclosure_rupee_RadioBtn) {
                    setError(edtForeclosureRupeeLayout);
                    getFocusCurrencyGhost(foreClosureRupeeEdtView);
                } else if (foreclosure_rg.getCheckedRadioButtonId() == R.id.foreclosure_percent_RadioBtn) {
                    setError(edtForeclosureLayout);
                    getFocus(foreClosureEdtView);
                }

                break;

            case 7:
                if (administrationFee_rg.getCheckedRadioButtonId() == R.id.administrationFee_rupee_RadioBtn) {
                    setError(edt_administrationFeeRupee_layout);
                    getFocusCurrencyGhost(administrationFeeRupeeEdtView);
                } else if(administrationFee_rg.getCheckedRadioButtonId() == R.id.administrationFee_percent_RadioBtn){
                    setError(edt_administrationFee_layout);
                    getFocus(administrationFeeEdtView);
                }
                break;

               /*case 8:
                   if(insuranceFee_rg.getCheckedRadioButtonId()==R.id.insuranceFee_rupee_RadioBtn) {
                       setError(edt_insuranceFeeRupee_layout);
                   }else {
                       setError(edt_insuranceFee_layout);
                   }
                  break;*/

        }

    }

    private void getFocus(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void getFocusCurrencyGhost(CurrencyGhostView currencyGhostView) {
        currencyGhostView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(currencyGhostView, InputMethodManager.SHOW_IMPLICIT);
    }

    public void setError(TextInputLayout ti) {
        ti.setError("Please fill the Missing value");
        Log.i("spcheck", "setError CurrentLoan: is called method 2");
    }

    public void setCustomError(TextInputLayout ti, String errorText) {
        ti.setError(errorText);

    }

    public void setInvalidTenureError() {

        edtBalTenureLayout.setError("Balance tenure should be less than Total tenure");
        tenureEdtView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(tenureEdtView, InputMethodManager.SHOW_IMPLICIT);
    }


    public void setErrorInEmiDialog(String loanStartDate, String firstEmiPaidDate, String lastEmiPaidDate, String nextEmiDueDate, String finalEmiDate, boolean[] emptyDates) {
        EmiDialogFragment fragment = EmiDialogFragment.newInstance(this, loanStartDate, firstEmiPaidDate, lastEmiPaidDate, nextEmiDueDate, finalEmiDate, emptyDates);
        fragment.show(getFragmentManager(), "setError");

    }
}

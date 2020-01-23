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
import android.widget.RadioButton;
import android.widget.RadioGroup;

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


/**
 * @author Pratheep.S
 */

public class NewLoanFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    //Edit text
    @Bind(R.id.edt_out_standing_view_id)
    CurrencyGhostView edt_out_standing;

    @Bind(R.id.edt_interest_view_id)
    PercentageEditText edt_interest;

    @Bind(R.id.edt_balance_tenure_view_id)
    NumberEditText edt_balance_tenure;

    @Bind(R.id.edt_emi_view_id)
    CurrencyGhostView edt_emi;

    @Bind(R.id.edt_processingFee_view_id)
    CurrencyGhostView edt_processingFee;

    @Bind(R.id.edt_legal_view_id)
    CurrencyGhostView edt_legal;

    @Bind(R.id.edt_administrationFee_view_id)
    CurrencyGhostView edt_administrationFee;


    @Bind(R.id.edt_processingFeePercent_view_id)
    PercentageEditText edt_processingFeePercent;

    @Bind(R.id.edt_legalPercent_view_id)
    PercentageEditText edt_legalPercent;

    @Bind(R.id.edt_administrationFeePercent_view_id)
    PercentageEditText edt_administrationFeePercent;

    @Bind(R.id.edt_insuranceFeeRupee_view_id)
    CurrencyGhostView insuranceFeeRupeeEdtView;

    @Bind(R.id.edt_insuranceFee_view_id)
    PercentageEditText insuranceFeeEdtView;

    //Text Input layout

    /*@Bind(R.id.edt_out_standing_layout)
    TextInputLayout edt_out_standing_layout;*/

    @Bind(R.id.edt_interest_layout)
    TextInputLayout edt_interest_layout;

    @Bind(R.id.edt_balance_tenure_layout)
    TextInputLayout edt_balance_tenure_layout;

   /* @Bind(R.id.edt_emi_layout)
    TextInputLayout edt_emi_layout;*/

    @Bind(R.id.edt_processingFee_layout)
    TextInputLayout edt_processingFee_layout;

    @Bind(R.id.edt_legal_layout)
    TextInputLayout edt_legal_layout;

    @Bind(R.id.edt_administration_fee_layout)
    TextInputLayout edt_administration_fee_layout;

    @Bind(R.id.edt_processingFeePercent_layout)
    TextInputLayout edt_processingFeePercent_layout;

    @Bind(R.id.edt_legalPercent_layout)
    TextInputLayout edt_legalPercent_layout;

    @Bind(R.id.edt_administrationFeePercent_layout)
    TextInputLayout edt_administrationFeePercent_layout;

    @Bind(R.id.edt_insuranceFeeRupee_layout)
    TextInputLayout edt_insuranceFeeRupee_layout;

    @Bind(R.id.edt_insuranceFee_layout)
    TextInputLayout edt_insuranceFee_layout;

    @Bind(R.id.processingFee_rg)
    RadioGroup processingFeeRG;

    @Bind(R.id.processingFee_percent_RadioBtn)
    RadioButton processingFeePercent;

    @Bind(R.id.processingFee_rupeee_RadioBtn)
    RadioButton processingFeeRupee;

    @Bind(R.id.administrationFee_rg_newLoan)
    RadioGroup admimistrationFeeRG;

    @Bind(R.id.administrationFee_percent_RadioBtn_newLoan)
    RadioButton admimistrationFeePercent;

    @Bind(R.id.administrationFee_rupeee_RadioBtn_newLoan)
    RadioButton admimistrationFeeRupee;

    @Bind(R.id.legalFee_rg)
    RadioGroup legalFeeRG;

    @Bind(R.id.legalFee_percent_RadioBtn)
    RadioButton legalFeePercent;

    @Bind(R.id.legalFee_rupeee_RadioBtn)
    RadioButton legalFeeRupee;


    @Bind(R.id.insuranceFee_rg)
    RadioGroup insuranceFee_rg;

    @Bind(R.id.insuranceFee_rupee_RadioBtn)
    RadioButton insuranceFee_rupee_RadioBtn;
    //public static final int insuranceFee_rupeee_RadioBtnID=1007;

    @Bind(R.id.insuranceFee_percent_RadioBtn)
    RadioButton insuranceFee_percent_RadioBtn;
   // public static final int insuranceFee_percent_RadioBtnID=1008;

    UpdateValueInActivityInterface updateInterface;

    Context mContext;


    @Bind(R.id.process_fee_calculatorimage)
    CustomCalenderImageView process_fee_calculatorimage;
    @Bind(R.id.legal_calculatorimage)
    CustomCalenderImageView legal_calculatorimage;
    @Bind(R.id.administrativefee_calculatorimage)
    CustomCalenderImageView administrativefee_calculatorimage;
    @Bind(R.id.insurancefee_calculatorimage)
    CustomCalenderImageView insurancefee_calculatorimage;

    @Bind(R.id.emi_more_calculaterImgView)
    CustomCalenderImageView emi_more_calculaterImgView;
    @Bind(R.id.edt_out_standing_calculaterImgView)
    CustomCalenderImageView edt_out_standing_calculaterImgView;



    public static final String TITLE = "";
    View nameEditview;
    public static final String PARENT_CLASS_SOURCE = "com.gp89developers.example.MainActivity";
    View view;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_new_loan, container, false);

        ButterKnife.bind(this, view);

        edt_out_standing.setEditTextId(R.id.outstandingEdtView_id);
        edt_emi.setEditTextId(R.id.edt_emi_id);

        process_fee_calculatorimage.setOnClickListener(this);
        legal_calculatorimage.setOnClickListener(this);
        administrativefee_calculatorimage.setOnClickListener(this);
        insurancefee_calculatorimage.setOnClickListener(this);

        emi_more_calculaterImgView.setOnClickListener(this);
        edt_out_standing_calculaterImgView.setOnClickListener(this);

        edt_out_standing.getEditText().addTextChangedListener(new GenericTextWatchers(edt_out_standing));
        edt_out_standing.setfullHintTxt(getString(R.string.hint_home_loan_new_outsandingbalance));

        edt_interest.addTextChangedListener(new GenericTextWatcher(edt_interest));
        edt_interest.setHintText(getString(R.string.hint_home_loan_new_intersetrateperannum), ((TextInputLayout)
                (edt_interest.getParent()).getParent()));

        edt_balance_tenure.addTextChangedListener(new GenericTextWatcher(edt_balance_tenure));
        edt_balance_tenure.setHintText(getString(R.string.hint_home_loan_new_totaltenure), ((TextInputLayout)
                (edt_balance_tenure.getParent()).getParent()));


        edt_emi.getEditText().addTextChangedListener(new GenericTextWatchers(edt_emi));
        edt_emi.setfullHintTxt(getString(R.string.hint_home_loan_new_EMI));

        edt_processingFee.getEditText().addTextChangedListener(new GenericTextWatcher(edt_processingFee));
        edt_processingFee.setfullHintTxt(getString(R.string.hint_home_loan_new_processingfee));

        edt_legal.getEditText().addTextChangedListener(new GenericTextWatcher(edt_legal));
        edt_legal.setfullHintTxt(getString(R.string.hint_home_loan_new_legalfee));

        edt_administrationFee.getEditText().addTextChangedListener(new GenericTextWatcher(edt_administrationFee));
        edt_administrationFee.setfullHintTxt(getString(R.string.hint_home_loan_new_administrationfee));

        insuranceFeeRupeeEdtView.getEditText().addTextChangedListener(new GenericTextWatcher(insuranceFeeRupeeEdtView));
        insuranceFeeRupeeEdtView.setfullHintTxt(getString(R.string.hint_home_loan_new_insurancefee));
        //

        edt_processingFeePercent.addTextChangedListener(new GenericTextWatcher(edt_processingFeePercent));
        edt_processingFeePercent.setHintText(getString(R.string.hint_home_loan_new_processingfee), ((TextInputLayout)
                (edt_processingFeePercent.getParent()).getParent()));

        edt_legalPercent.addTextChangedListener(new GenericTextWatcher(edt_legalPercent));
        edt_legalPercent.setHintText(getString(R.string.hint_home_loan_new_legalfee), ((TextInputLayout)
                (edt_legalPercent.getParent()).getParent()));

        edt_administrationFeePercent.addTextChangedListener(new GenericTextWatcher(edt_administrationFeePercent));
        edt_administrationFeePercent.setHintText(getString(R.string.hint_home_loan_new_administrationfee), ((TextInputLayout)
                (edt_administrationFeePercent.getParent()).getParent()));

        insuranceFeeEdtView.addTextChangedListener(new GenericTextWatcher(insuranceFeeEdtView));
        insuranceFeeEdtView.setHintText(getString(R.string.hint_home_loan_new_insurancefee), ((TextInputLayout)
                (insuranceFeeEdtView.getParent()).getParent()));

        admimistrationFeeRG.setOnCheckedChangeListener(this);
        legalFeeRG.setOnCheckedChangeListener(this);
        processingFeeRG.setOnCheckedChangeListener(this);
        insuranceFee_rg.setOnCheckedChangeListener(this);

        edt_out_standing.setTextHint("Outstanding Balance");
        edt_emi.setTextHint("EMI");
        edt_processingFee.setTextHint("Processing fee");
        edt_legal.setTextHint("Legal fee");
        edt_administrationFee.setTextHint("Administration fee");
        insuranceFeeRupeeEdtView.setTextHint("Insurance Fee");


        setCheckedButtonView();

        return view;
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
    private void setCheckedButtonView() {
        UtileKit.getSwitchYesBtnView(admimistrationFeeRupee, admimistrationFeePercent, mContext);
        UtileKit.getSwitchYesBtnView(processingFeeRupee, processingFeePercent, mContext);
        UtileKit.getSwitchYesBtnView(legalFeeRupee,legalFeePercent, mContext);
        UtileKit.getSwitchYesBtnView(insuranceFee_rupee_RadioBtn,insuranceFee_percent_RadioBtn,mContext);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mContext = getContext();
//            updateInterface = (UpdateValueInActivityInterface) mContext;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

    }
    public void setOutstandingBalance(String value){
        edt_out_standing.setText(value);
    }

    public void setBalanceTenure(String value){
        edt_balance_tenure.setText(value);
    }

    public void setErrorNewLoanFragment(int index) {
        switch (index) {
            case 0:
               // setError(edt_out_standing_layout);
                setError(((TextInputLayout)(edt_out_standing.getEditText().getParent()).getParent()));
                Log.i("spcheck", "setError NewLoan: is called method 1");
                break;
            case 1:
                setError(edt_interest_layout);
                getFocus(edt_interest);
                break;

            case 2:
                setError(edt_balance_tenure_layout);
                getFocus(edt_balance_tenure);
                break;

            case 3:
                setError(((TextInputLayout)(edt_emi.getEditText().getParent()).getParent()));
                //setError(edt_emi_layout);
                getFocusCurrencyGhost(edt_emi);
                break;

            case 4:
                if(processingFeeRG.getCheckedRadioButtonId()==R.id.processingFee_rupeee_RadioBtn) {
                    setError(edt_processingFee_layout);
                    getFocusCurrencyGhost(edt_processingFee);
                }else     if(processingFeeRG.getCheckedRadioButtonId()== R.id.processingFee_percent_RadioBtn) {
                    setError(edt_processingFeePercent_layout);
                    getFocus(edt_processingFeePercent);
                }
                break;

            case 5:
                if(legalFeeRG.getCheckedRadioButtonId()==R.id.legalFee_rupeee_RadioBtn) {
                    setError(edt_legal_layout);
                    getFocusCurrencyGhost(edt_legal);
                }else if(legalFeeRG.getCheckedRadioButtonId()==R.id.legalFee_percent_RadioBtn) {
                    setError(edt_legalPercent_layout);
                    getFocus(edt_legalPercent);
                }
                break;

            case 6:
                if(admimistrationFeeRG.getCheckedRadioButtonId()==R.id.administrationFee_rupeee_RadioBtn_newLoan) {
                    setError(edt_administration_fee_layout);
                    getFocusCurrencyGhost(edt_administrationFee);
                }else  if(admimistrationFeeRG.getCheckedRadioButtonId()==R.id.administrationFee_percent_RadioBtn_newLoan) {
                    setError(edt_administrationFeePercent_layout);
                    getFocus(edt_administrationFeePercent);
                }
                break;
            case 7:
                if(insuranceFee_rg.getCheckedRadioButtonId()==R.id.insuranceFee_rupee_RadioBtn) {
                    setError(edt_insuranceFeeRupee_layout);
                    getFocusCurrencyGhost(insuranceFeeRupeeEdtView);
                }else if(insuranceFee_rg.getCheckedRadioButtonId()==R.id.insuranceFee_percent_RadioBtn) {
                    setError(edt_insuranceFee_layout);
                    getFocus(insuranceFeeEdtView);
                }
                break;

        }

    }

    public void setError(TextInputLayout ti) {
        try {
            ti.setError("Please fill the Missing value");
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()) {
            case R.id.administrationFee_rg_newLoan:
                if (checkedId == R.id.administrationFee_rupeee_RadioBtn_newLoan) {
                    UtileKit.getSwitchYesBtnView(admimistrationFeeRupee, admimistrationFeePercent, mContext);
                    edt_administration_fee_layout.setVisibility(View.VISIBLE);
                    edt_administrationFeePercent_layout.setVisibility(View.GONE);

                    administrativefee_calculatorimage.setVisibility(View.VISIBLE);


                } else if (checkedId == R.id.administrationFee_percent_RadioBtn_newLoan) {
                    UtileKit.getSwitchNoBtnView(admimistrationFeeRupee, admimistrationFeePercent, mContext);
                    edt_administration_fee_layout.setVisibility(View.GONE);
                    edt_administrationFeePercent_layout.setVisibility(View.VISIBLE);

                    administrativefee_calculatorimage.setVisibility(View.GONE);

                    calculateAdministrationFeePercentValues();
                    getFocus(edt_administrationFeePercent);
                }
                break;
            case R.id.processingFee_rg:
                if (checkedId == R.id.processingFee_rupeee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(processingFeeRupee, processingFeePercent, mContext);
                    edt_processingFee_layout.setVisibility(View.VISIBLE);
                    edt_processingFeePercent_layout.setVisibility(View.GONE);

                    process_fee_calculatorimage.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.processingFee_percent_RadioBtn) {

                    UtileKit.getSwitchNoBtnView(processingFeeRupee, processingFeePercent, mContext);
                    edt_processingFee_layout.setVisibility(View.GONE);
                    edt_processingFeePercent_layout.setVisibility(View.VISIBLE);

                    process_fee_calculatorimage.setVisibility(View.GONE);

                    calculateProcessingFeePercentValues();
                    getFocus(edt_processingFeePercent);
                }
                break;
            case R.id.legalFee_rg:
                if (checkedId == R.id.legalFee_rupeee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(legalFeeRupee,legalFeePercent, mContext);
                    edt_legal_layout.setVisibility(View.VISIBLE);
                    edt_legalPercent_layout.setVisibility(View.GONE);

                    legal_calculatorimage.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.legalFee_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(legalFeeRupee,legalFeePercent,mContext);
                    edt_legal_layout.setVisibility(View.GONE);
                    edt_legalPercent_layout.setVisibility(View.VISIBLE);

                    legal_calculatorimage.setVisibility(View.GONE);

                    calculateLegalFeePercentValues();
                    getFocus(edt_legalPercent);
                }
                break;

            case R.id.insuranceFee_rg:
                if(checkedId==R.id.insuranceFee_rupee_RadioBtn){
                    UtileKit.getSwitchYesBtnView(insuranceFee_rupee_RadioBtn,insuranceFee_percent_RadioBtn,mContext);
                    edt_insuranceFeeRupee_layout.setVisibility(View.VISIBLE);
                    edt_insuranceFee_layout.setVisibility(View.GONE);

                    insurancefee_calculatorimage.setVisibility(View.VISIBLE);

                } else if(checkedId==R.id.insuranceFee_percent_RadioBtn){
                    UtileKit.getSwitchNoBtnView(insuranceFee_rupee_RadioBtn,insuranceFee_percent_RadioBtn,mContext);
                    edt_insuranceFeeRupee_layout.setVisibility(View.GONE);
                    edt_insuranceFee_layout.setVisibility(View.VISIBLE);

                    insurancefee_calculatorimage.setVisibility(View.GONE);

                    calculateInsuranceFeePercentValues();
                    getFocus(insuranceFeeEdtView);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.process_fee_calculatorimage:
                showCalDialog(edt_processingFee);
                break;
            case R.id.legal_calculatorimage:
                showCalDialog(edt_legal);
                break;

            case R.id.administrativefee_calculatorimage:
                showCalDialog(edt_administrationFee);
                break;

            case R.id.insurancefee_calculatorimage:
                showCalDialog(insuranceFeeRupeeEdtView);
                break;

            case R.id.edt_out_standing_calculaterImgView:
                showCalDialog(edt_out_standing);
                break;
            case R.id.emi_more_calculaterImgView:
                showCalDialog(edt_emi);
                break;



        }
    }

    public static NewLoanFragment newInstance(UpdateValueInActivityInterface updateInterface) {
        NewLoanFragment fragment=new NewLoanFragment();
        fragment.updateInterface = updateInterface;

        return fragment;
    }


    class GenericTextWatcher implements TextWatcher {
        View view;
        GenericTextWatcher(View v) {
            view = v;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {

            switch (view.getId()) {
               /* case R.id.edt_out_standing_view_id:
                    edt_out_standing_layout.setError(null);
                    updateInterface.updateValudFromNewLoan(s.toString(), 0);
                    calculateProcessingFeePercentValues();
                    calculateLegalFeePercentValues();
                    calculateAdministrationFeePercentValues();
                    calculateInsuranceFeePercentValues();
                    break;*/
                case R.id.edt_interest_view_id:
                    edt_interest_layout.setErrorEnabled(false);
                    edt_interest_layout.setError(null);
                    updateInterface.updateValudFromNewLoan(s.toString(), 1);

                    break;
                case R.id.edt_balance_tenure_view_id:
                    edt_balance_tenure_layout.setErrorEnabled(false);
                    edt_balance_tenure_layout.setError(null);
                    updateInterface.updateValudFromNewLoan(s.toString(), 2);
                    break;

               /* case R.id.edt_emi_view_id:
                    edt_emi_layout.setError(null);
                    updateInterface.updateValudFromNewLoan(s.toString(), 3);
                    break;*/

                case R.id.edt_processingFee_view_id:
                    if(processingFeeRG.getCheckedRadioButtonId()==R.id.processingFee_rupeee_RadioBtn) {
                        edt_processingFee_layout.setErrorEnabled(false);
                        edt_processingFee_layout.setError(null);
                        updateInterface.updateValudFromNewLoan(s.toString(), 4);
                    }
                    break;

                case R.id.edt_legal_view_id:
                    if(legalFeeRG.getCheckedRadioButtonId()==R.id.legalFee_rupeee_RadioBtn) {
                        edt_legal_layout.setErrorEnabled(false);
                        edt_legal_layout.setError(null);
                        updateInterface.updateValudFromNewLoan(s.toString(), 5);
                    }else if(legalFeeRG.getCheckedRadioButtonId()==R.id.legalFee_percent_RadioBtn)
                    {
                        edt_legal_layout.setErrorEnabled(false);
                        edt_legal_layout.setError(null);
                        updateInterface.updateValudFromNewLoan(s.toString(), 5);

                    }

                    break;

                case R.id.edt_administrationFee_view_id:
                    if(admimistrationFeeRG.getCheckedRadioButtonId()==R.id.administrationFee_rupeee_RadioBtn_newLoan) {
                        edt_administration_fee_layout.setErrorEnabled(false);
                        edt_administration_fee_layout.setError(null);
                        updateInterface.updateValudFromNewLoan(s.toString(), 6);
                    }
                    break;

                case R.id.edt_processingFeePercent_view_id:
                    calculateProcessingFeePercentValues();
                    break;

                case R.id.edt_legalPercent_view_id:
                    calculateLegalFeePercentValues();
                    break;

                case R.id.edt_administrationFeePercent_view_id:
                    calculateAdministrationFeePercentValues();
                    break;

                case R.id.edt_insuranceFee_view_id:
                    calculateInsuranceFeePercentValues();
                    break;

                case R.id.edt_insuranceFeeRupee_view_id:
                    if(insuranceFee_rg.getCheckedRadioButtonId()==R.id.insuranceFee_rupee_RadioBtn) {
                        edt_insuranceFeeRupee_layout.setErrorEnabled(false);
                        edt_insuranceFeeRupee_layout.setError(null);
                        updateInterface.updateValudFromNewLoan(s.toString(), 7);
                    }
                    break;
            }
        }
    }





    class GenericTextWatchers implements TextWatcher {
        CurrencyGhostView currencyGhostView;
        GenericTextWatchers(CurrencyGhostView currencyGhostView) {
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
                   // edt_out_standing_layout.setError(null);
                    ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setErrorEnabled(false);
                    ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setError(null);
                    updateInterface.updateValudFromNewLoan(s.toString(), 0);
                    calculateProcessingFeePercentValues();
                    calculateLegalFeePercentValues();
                    calculateAdministrationFeePercentValues();
                    calculateInsuranceFeePercentValues();
                    break;
                case R.id.edt_emi_view_id:
                    ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setErrorEnabled(false);
                    ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setError(null);
                    //edt_emi_layout.setError(null);
                    updateInterface.updateValudFromNewLoan(s.toString(), 3);
                    break;
            }
        }
    }






    private void calculateInsuranceFeePercentValues() {
        if(insuranceFee_rg.getCheckedRadioButtonId()==R.id.insuranceFee_percent_RadioBtn) {
            if (checkOutstandingBalanceValueNotEmpty()) {
                if (insuranceFeeEdtView.getText().toString() != null && !(insuranceFeeEdtView.getText().toString().equals(""))) {
                    edt_insuranceFee_layout.setErrorEnabled(false);
                    edt_insuranceFee_layout.setError(null);
                    //updateInterface.sendValue(s.toString(), 5);
                    Float amount = ((Float.parseFloat(insuranceFeeEdtView.getText().toString())) / 100) * Float.parseFloat(UtileKit.getStringwithoutCurreny(edt_out_standing.getText().toString()));
                    updateInterface.updateValudFromNewLoan(amount+"", 7);
                    //Log.i("spcheck", "afterTextChanged: prepayement" + amount);
                } else{
                    updateInterface.updateValudFromNewLoan(0+"",7);
                }
            }
        }
    }

    private void calculateAdministrationFeePercentValues() {
        if(admimistrationFeeRG.getCheckedRadioButtonId()==R.id.administrationFee_percent_RadioBtn_newLoan) {
            if (checkOutstandingBalanceValueNotEmpty()) {
                if (edt_administrationFeePercent.getText().toString() != null && !(edt_administrationFeePercent.getText().toString().equals(""))) {
                    edt_administrationFeePercent_layout.setErrorEnabled(false);
                    edt_administrationFeePercent_layout.setError(null);

                    Float amount = ((Float.parseFloat(edt_administrationFeePercent.getText().toString())) / 100) * Float.parseFloat(UtileKit.getStringwithoutCurreny(edt_out_standing.getText().toString()));
                    updateInterface.updateValudFromNewLoan(amount+"", 6);
                    //Log.i("spcheck", "afterTextChanged: prepayement" + amount);
                } else{
                    updateInterface.updateValudFromNewLoan(0+"",6);
                }
            }
        }
    }

    private void calculateLegalFeePercentValues() {
        if(legalFeeRG.getCheckedRadioButtonId()==R.id.legalFee_percent_RadioBtn) {
            if (checkOutstandingBalanceValueNotEmpty()) {
                if (edt_legalPercent.getText().toString() != null && !(edt_legalPercent.getText().toString().equals(""))) {
                    edt_legalPercent_layout.setErrorEnabled(false);
                    edt_legalPercent_layout.setError(null);

                    Float amount = ((Float.parseFloat(edt_legalPercent.getText().toString())) / 100) * Float.parseFloat(UtileKit.getStringwithoutCurreny(edt_out_standing.getText().toString()));
                    updateInterface.updateValudFromNewLoan(amount+"", 5);
                    //Log.i("spcheck", "afterTextChanged: prepayement" + amount);
                } else{
                    updateInterface.updateValudFromNewLoan(0+"",5);
                }
            }
        }
    }

    private void calculateProcessingFeePercentValues() {
        if(processingFeeRG.getCheckedRadioButtonId()== R.id.processingFee_percent_RadioBtn) {
            try {
                if (checkOutstandingBalanceValueNotEmpty()) {
                    if (edt_processingFeePercent.getText().toString() != null && !(edt_processingFeePercent.getText().toString().equals(""))) {
                        edt_processingFeePercent_layout.setErrorEnabled(false);
                        edt_processingFeePercent_layout.setError(null);

                        Float amount = ((Float.parseFloat(edt_processingFeePercent.getText().toString())) / 100) * Float.parseFloat(UtileKit.getStringwithoutCurreny(edt_out_standing.getText().toString()));
                        updateInterface.updateValudFromNewLoan(amount + "", 4);
                        //Log.i("spcheck", "afterTextChanged: prepayement" + amount);
                    } else {
                        updateInterface.updateValudFromNewLoan(0 + "", 4);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private boolean checkOutstandingBalanceValueNotEmpty() {
        boolean isNotEmpty = true;
        if (edt_out_standing.getText().toString() == null || edt_out_standing.getText().toString().equals("0") || edt_out_standing.getText().toString().equals("")) {
            //UtileKit.alertDialog("Outstanding balance is empty","Please fill Outstanding balance first",mContext);
            //UtileKit.alertRetrofitExceptionalert("Please fill Outstanding balance first",mContext);

           /* UtileKit.showAlertDialog(mContext,"Please fill Outstanding balance first");
            setCustomError(edtOutStandingLayout, "Please fill Outstanding balance first");
            outstandingEdtView.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(tenureEdtView, InputMethodManager.SHOW_IMPLICIT);*/

            // Toast.makeText(mContext,"Please Enter value for Outstanding balance in Current Loan",Toast.LENGTH_LONG).show();
            isNotEmpty = false;
        }
        return isNotEmpty;
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
}

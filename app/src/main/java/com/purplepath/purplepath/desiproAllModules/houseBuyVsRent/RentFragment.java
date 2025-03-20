package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.interfaces.ActivityMethodsInterface;
import com.purplepath.purplepath.fragments.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Pratheep.S
 */
public class RentFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.rentMonthlyCalendar)
    CustomCalenderImageView rentMonthlyCalendar;

    @BindView(R.id.rentAnnuallyCalendar)
    CustomCalenderImageView rentAnnuallyCalendar;

    @BindView(R.id.securityDeposit_calculaterImgView)
    CustomCalenderImageView securityDeposit_calculaterImgView;

    @BindView(R.id.rentalEscalation_calculatorimage)
    CustomCalenderImageView rentalEscalation_calculatorimage;

    @BindView(R.id.utilitiesCalendar)
    CustomCalenderImageView utilitiesCalendar;

    @BindView(R.id.utilitiesAnnuallyCalendar)
    CustomCalenderImageView utilitiesAnnuallyCalendar;

    @BindView(R.id.tenantsInsurance_calculaterImgView)
    CustomCalenderImageView tenantsInsurance_calculaterImgView;

     @BindView(R.id.rentMonthly_lyt)
    CurrencyGhostView  rentMonthly_lyt;

    @BindView(R.id.rentAnnually_lyt)
    CurrencyGhostView rentAnnually_lyt;

    @BindView(R.id.securityDeposit)
    CurrencyGhostView securityDeposit;

    @BindView(R.id.edt_rentalEscalation)
    CurrencyGhostView edt_rentalEscalation;

    @BindView(R.id.utilitiesMonthly_lyt)
    CurrencyGhostView utilitiesMonthly_lyt;

    @BindView(R.id.utilitiesAnnually_lyt)
    CurrencyGhostView utilitiesAnnually_lyt;

    @BindView(R.id.tenantsInsurance_edt)
    CurrencyGhostView tenantsInsurance_edt;

    @BindView(R.id.rentalEscalation_rg)
    RadioGroup rentalEscalation_rg;

    @BindView(R.id.rentalEscalation_rupeee_RadioBtn)
    RadioButton rentalEscalation_rupeee_RadioBtn;


    @BindView(R.id.rentalEscalation_percent_RadioBtn)
    RadioButton rentalEscalation_percent_RadioBtn;

    @BindView(R.id.rentalEscalationPercent_layout)
    TextInputLayout rentalEscalationPercent_layout;

    @BindView(R.id.rentalEscalationPercent_edt)
    PercentageEditText rentalEscalationPercent_edt;


    @BindView(R.id.rentalEscalationRupee_layout)
    TextInputLayout rentalEscalationRupee_layout;

    @BindView(R.id.intrestforegone_layout)
    TextInputLayout intrestforegone_layout;

    @BindView(R.id.intrestforegone_edt)
    EditText intrestforegone_edt;

    public static final String PARENT_CLASS_SOURCE = "com.gp89developers.example.MainActivity";
    public static final String TITLE = "";
    View nameEditview;

    private Context mContext;
    GhostViewTextWatchers rentMonthly_TV ;
    GhostViewTextWatchers rentAnnually_TV ;
    GhostViewTextWatchers utilitiesMonthly_TV;
    GhostViewTextWatchers utilitiesAnnually_TV;
    View view;

    public boolean isInitialized=false;

    public RentFragment() {
        // Required empty public constructor
    }

    ActivityMethodsInterface updateInActivity;

    public static RentFragment newInstance() {

        Bundle args = new Bundle();
        RentFragment fragment = new RentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(isInitialized==false) {
            view = inflater.inflate(R.layout.fragment_rent, container, false);
        }

        ButterKnife.bind(this,view);
        mContext=getContext();
        updateInActivity=(ActivityMethodsInterface) getActivity();

        return view;
    }

    @Override
    public void onViewCreated(View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(isInitialized==false)  initializeViews();

    }

    private void initializeViews() {
        rentMonthly_lyt.setEditTextId(R.id.rentMonthly_lytId);
        rentAnnually_lyt.setEditTextId(R.id.rentAnnually_lytId);
        securityDeposit.setEditTextId(R.id.securityDepositId);
        utilitiesMonthly_lyt.setEditTextId(R.id.utilitiesMonthly_rent_id);
        utilitiesAnnually_lyt.setEditTextId(R.id.utilitiesAnnually_rent_id);
        tenantsInsurance_edt.setEditTextId(R.id.tenantsInsurance_edt_id);
        edt_rentalEscalation.setEditTextId(R.id.edt_rentalEscalation_id);

        rentalEscalation_rg.setOnCheckedChangeListener(this);
        rentMonthly_lyt.setTextHint("Monthly");
        rentMonthly_lyt.setfullHintTxt(getString(R.string.hint_buyvsrent_currentrentmonth));

        rentAnnually_lyt.setTextHint("Annually");
        rentAnnually_lyt.setfullHintTxt(getString(R.string.hint_buyvsrent_currentrentyear));


        securityDeposit.setTextHint("Security Deposit");
        securityDeposit.setfullHintTxt(getString(R.string.hint_buyvsrent_securitydeposit));

        edt_rentalEscalation.setTextHint("Rental Escalation");
        utilitiesMonthly_lyt.setTextHint("Monthly");
        utilitiesMonthly_lyt.setfullHintTxt(getString(R.string.hint_buyvsrent_utilitiessmonth));

        utilitiesAnnually_lyt.setTextHint("Annually");
        utilitiesAnnually_lyt.setfullHintTxt(getString(R.string.hint_buyvsrent_utilitiessyear));

        tenantsInsurance_edt.setTextHint("Tenants Insurance");
        tenantsInsurance_edt.setfullHintTxt(getString(R.string.hint_buyvsrent_tenantsinsurance));

        UtileKit.getSwitchYesBtnView(rentalEscalation_rupeee_RadioBtn, rentalEscalation_percent_RadioBtn, mContext);

        rentMonthlyCalendar.setOnClickListener(this);
        rentAnnuallyCalendar.setOnClickListener(this);
        securityDeposit_calculaterImgView.setOnClickListener(this);
        rentalEscalation_calculatorimage.setOnClickListener(this);
        utilitiesCalendar.setOnClickListener(this);
        utilitiesAnnuallyCalendar.setOnClickListener(this);
        tenantsInsurance_calculaterImgView.setOnClickListener(this);

        seListeners();
        isInitialized=true;
    }

    private void seListeners() {
        rentMonthly_TV=new GhostViewTextWatchers(rentMonthly_lyt);
        rentAnnually_TV=new GhostViewTextWatchers(rentAnnually_lyt);
        utilitiesMonthly_TV=new GhostViewTextWatchers(utilitiesMonthly_lyt);
        utilitiesAnnually_TV=new GhostViewTextWatchers(utilitiesAnnually_lyt);

        rentMonthly_lyt.getEditText().addTextChangedListener(rentMonthly_TV);
        rentAnnually_lyt.getEditText().addTextChangedListener(rentAnnually_TV);
        securityDeposit.getEditText().addTextChangedListener(new GhostViewTextWatchers(securityDeposit));

        edt_rentalEscalation.getEditText().addTextChangedListener(new GhostViewTextWatchers(edt_rentalEscalation));
        utilitiesMonthly_lyt.getEditText().addTextChangedListener(utilitiesMonthly_TV);
        utilitiesAnnually_lyt.getEditText().addTextChangedListener(utilitiesAnnually_TV);

        tenantsInsurance_edt.getEditText().addTextChangedListener(new GhostViewTextWatchers(tenantsInsurance_edt));
        rentalEscalationPercent_edt.addTextChangedListener(new GenericTextWatcher(rentalEscalationPercent_edt));
        rentalEscalationPercent_edt.setHintText(getString(R.string.hint_buyvsrent_rentalescalation),((TextInputLayout)
                (rentalEscalationPercent_edt.getParent()).getParent()));
        //intrestforegone_edt.addTextChangedListener(new GenericTextWatcher(intrestforegone_edt));
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
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        switch (group.getId()) {
            case R.id.rentalEscalation_rg:
                if (checkedId == R.id.rentalEscalation_rupeee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(rentalEscalation_rupeee_RadioBtn, rentalEscalation_percent_RadioBtn, mContext);
                    rentalEscalationRupee_layout.setVisibility(View.VISIBLE);
                    edt_rentalEscalation.setVisibility(View.VISIBLE);
                    rentalEscalation_calculatorimage.setVisibility(View.VISIBLE);
                    rentalEscalationPercent_layout.setVisibility(View.GONE);

                } else if (checkedId == R.id.rentalEscalation_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(rentalEscalation_rupeee_RadioBtn, rentalEscalation_percent_RadioBtn, mContext);
                    rentalEscalationRupee_layout.setVisibility(View.GONE);
                    edt_rentalEscalation.setVisibility(View.GONE);
                    rentalEscalation_calculatorimage.setVisibility(View.GONE);
                    rentalEscalationPercent_layout.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rentMonthlyCalendar:
                showCalDialog(rentMonthly_lyt);
                break;
            case R.id.rentAnnuallyCalendar:
                showCalDialog(rentAnnually_lyt);
                break;
            case R.id.securityDeposit_calculaterImgView:
                showCalDialog(securityDeposit);
                break;
            case R.id.rentalEscalation_calculatorimage:
                showCalDialog(edt_rentalEscalation);
                break;
            case R.id.utilitiesCalendar:
                showCalDialog(utilitiesMonthly_lyt);
                break;
            case R.id.utilitiesAnnuallyCalendar:
                showCalDialog(utilitiesAnnually_lyt);
                break;
            case R.id.tenantsInsurance_calculaterImgView:
                showCalDialog(tenantsInsurance_edt);
                break;
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
            String value=null;
            switch (currencyGhostView.getId()) {

                case R.id.rentMonthly_lyt :
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    if(UtileKit.validateObjectValues(value)){
                        updateGhostViewValue(rentAnnually_lyt,Float.parseFloat(value)*12,rentAnnually_TV);
                        updateInActivity.updateValuesFromRentFragment("current_rent",(Float.parseFloat(value)*12)+"");
                    }
                    break;

                case R.id.rentAnnually_lyt:
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    if(UtileKit.validateObjectValues(value)) updateGhostViewValue(rentMonthly_lyt,Float.parseFloat(value)/12,rentMonthly_TV);
                    updateInActivity.updateValuesFromRentFragment("current_rent",value);
                    break;

                case R.id.securityDeposit:
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    updateInActivity.updateValuesFromRentFragment("rent_sec_dep",value);
                    break;

                case R.id.edt_rentalEscalation://server accepts rental escalation only in % values
                   /* String rentValue=UtileKit.getStringwithoutCurreny(rentAnnually_lyt.getEditText().getText().toString());
                    if(UtileKit.validateObjectValues(rentValue)){
                        value=UtileKit.getStringwithoutCurreny(s.toString());
                        Float result=(Float.parseFloat(value)/Float.parseFloat(rentValue))*100;
                        updateInActivity.updateValuesFromRentFragment("rent_esc",result+"");
                    }else{
                        setErrorRentFragment(0);
                    }*/

                    break;

                case R.id.utilitiesMonthly_lyt :
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    if(UtileKit.validateObjectValues(value)){
                        updateGhostViewValue(utilitiesAnnually_lyt,Float.parseFloat(value)*12,utilitiesAnnually_TV);
                        updateInActivity.updateValuesFromRentFragment("rent_util",(Float.parseFloat(value)*12)+"");
                    }
                    break;

                case R.id.utilitiesAnnually_lyt :
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    if(UtileKit.validateObjectValues(value)) updateGhostViewValue(utilitiesMonthly_lyt,Float.parseFloat(value)/12,utilitiesMonthly_TV);
                    updateInActivity.updateValuesFromRentFragment("rent_util",value);
                    break;

                case R.id.tenantsInsurance_edt:
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    updateInActivity.updateValuesFromRentFragment("rent_insurance",value);
                    break;

                /*case R.id.downPayement_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "downPayement", fragmentID);
                    break;*/
                //loan_tenure_edt_id

            }
        }
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
            switch (editText.getId()) {

                case R.id.intrestforegone_edt:
                    //updateInActivity.updateValuesFromRentFragment("int_fgone_sec_dep_rate",s.toString());
                    break;
                case R.id.rentalEscalationPercent_edt:
                    updateInActivity.updateValuesFromRentFragment("rent_esc",s.toString());
                    break;
            }
        }
    }

    public void setErrorRentFragment(int index) {
        switch (index) {
            case 0: //"current_rent",
                setError(((TextInputLayout) (rentAnnually_lyt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(rentAnnually_lyt);
                //Log.i("spcheck", "setError NewLoan: is called method 1");
                break;

            case 1://"rent_sec_dep",
                setError(((TextInputLayout) (securityDeposit.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(securityDeposit);

                break;

            case 2://"rent_esc",
                //Log.i("spcheck", "setErrorRentFragment: ");
                /*if (rentalEscalation_rg.getCheckedRadioButtonId() == R.id.rentalEscalation_rupeee_RadioBtn) {
                    Log.i("spcheck", "setErrorRentFragment:1 ");
                    setError(((TextInputLayout) (edt_rentalEscalation.getEditText().getParent()).getParent()));
                    getFocusCurrencyGhost(edt_rentalEscalation);
                    Log.i("spcheck", "setErrorRentFragment: 1");
                } else if (rentalEscalation_rg.getCheckedRadioButtonId() == R.id.rentalEscalation_percent_RadioBtn) {
                    Log.i("spcheck", "setErrorRentFragment:2 ");
                    setError(rentalEscalationPercent_layout);
                    getFocus(rentalEscalationPercent_edt);
                }*/
                setError(rentalEscalationPercent_layout);
                getFocus(rentalEscalationPercent_edt);
                break;

            case 3://"rent_util",
                setError(((TextInputLayout) (utilitiesAnnually_lyt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(utilitiesAnnually_lyt);

                break;

            case 4://"rent_insurance",
                setError(((TextInputLayout) (tenantsInsurance_edt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(tenantsInsurance_edt);


                break;

            case 5://"int_fgone_sec_dep_rate"
                /*setError(intrestforegone_layout);
                getFocus(intrestforegone_edt);*/

                break;

        }
    }

    public void setError(TextInputLayout ti) {
        ti.setError("Please fill the Missing value");
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

    public void updateGhostViewValue(CurrencyGhostView view,float value,GhostViewTextWatchers textWatcher){
        ((TextInputLayout) (view.getEditText().getParent()).getParent()).setError(null);
        view.getEditText().removeTextChangedListener(textWatcher);
        view.getEditText().setText(((int)value)+"");
        view.getEditText().addTextChangedListener(textWatcher);
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



}

package com.purplepath.purplepath.desiproAllModules.loanEligibility.view;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyBlackGhostview;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.loanEligibility.view.models.LoanEligibilityModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.incomedetails.IncomeDynamicDetail.PARENT_CLASS_SOURCE;


/**
 * Created by Suresh on 23/08/17.
 */

public class LoanEligibility extends BaseFragment implements  AdapterView.OnItemSelectedListener, View.OnClickListener ,RadioGroup.OnCheckedChangeListener {

    private final static String TAG = LoanEligibility.class.getCanonicalName();
    private Context mContext;
    private OnActivityBackPressedListener mCallBackListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private FloatingActionButton fab;
    private Spinner loantypespinner;
    private CurrencyGhostView edt_downpayment;
    private CurrencyGhostView edt_loanreq;
    private CurrencyGhostView loanFund_Requirement_edit,loanFund_payment_capacity_edt,loan_fund_total_income_edt
            ,loan_fund_total_payments_edt;
    private PercentageEditText loanFund_rate_edt,loanFund_ltv_edit, edt_prepayment_view_id_percentage;
    private NumberEditText loanFund_credit_scroce_layout;
    private EditText loanFund_tenure_edt;
    private LoanEligibilityModel mLoanEligibilityModel;

    String loan_type,fund_req,down_payment,loan_req,loan_tenure,int_rate,payment_capacity,tot_inc,tot_payment,loan_to_value,credit_score;

    private String[] mgoalTypeArray;

    private RadioButton edt_downpayment_rupeee_RadioBtn,edt_downpayment_percent_RadioBtn, edt_loanreq_yes_RadioBtn,edt_loanreq_no_RadioBtn;

    RadioGroup processingFee_rg, loanreq_rg;
    View nameEditview;

    String fundReq, downpayment;
    public static final String TITLE = "";
    private CustomCalenderImageView loanFund_Requirement_edit_calc,loanFund_payment_capacity_edt_calc,
            loan_fund_total_income_edt_calc,loan_fund_total_payments_edt_calc,downpayment_calculatorimage_calc;
    boolean status;
    private TextInputLayout loanFund_tenure_lyt,loanFund_rate_lyt,loanFund_ltv_layout,loanFund_credit_layout,edt_prepayment_layout;
    private TextView selectedTextView;
    private CustomCalenderImageView downpayment_calculatorimage;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setHasOptionsMenu(true);
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loan_eligiblity_fragment, container, false);
        mCallBackListener.setActionBarTitle("DeciPro - Loan Eligibility");
        intilaizeview(view);
        mgoalTypeArray = getResources().getStringArray(R.array.loan_type_eligibility);
        setSpinnerAdapter(loantypespinner, mgoalTypeArray);
        setAllRadioButtonsToCheckedState();
        return view;
    }

    private void intilaizeview(View view) {
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        fab = view.findViewById(R.id.goalaff_fab_id);

        loantypespinner = view.findViewById(R.id.loantypespinner);

        loanFund_Requirement_edit = view.findViewById(R.id.loanFund_Requirement_edit);
        loanFund_Requirement_edit.setTextHint("Fund Requirement");
        loanFund_Requirement_edit.setfullHintTxt(getString(R.string.hint_loaneligibility_fundrequirement));

        loanFund_Requirement_edit.getEditText().addTextChangedListener(new CommonTextWatcher(loanFund_Requirement_edit));

        loanFund_tenure_edt = view.findViewById(R.id.loanFund_tenure_edt);
        loanFund_tenure_edt.addTextChangedListener(new CommonTextWatchers(loanFund_tenure_edt));
       // loanFund_tenure_edt.setHintText("Enter the total period of time for the loan in months", ((TextInputLayout)
         //       (loanFund_tenure_edt.getParent()).getParent()));

        loanFund_tenure_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    if(loanFund_tenure_edt.getText().length()!=0){
                        loanFund_tenure_lyt.setErrorEnabled(false);
                        loanFund_tenure_lyt.setError(null);
                    }else {
                        loanFund_tenure_lyt.setError(getString(R.string.hint_loaneligibility_tenure));
                        loanFund_tenure_lyt.setErrorTextAppearance(R.style.errorHintEdit);
                    }
                }
            }
        });

        loanFund_credit_scroce_layout = view.findViewById(R.id.loanFund_credit_scroce_layout);
        loanFund_credit_scroce_layout.addTextChangedListener(new CommonTextWatchers(loanFund_credit_scroce_layout));
        loanFund_credit_scroce_layout.setHintText(getString(R.string.hint_loaneligibility_creditscore), ((TextInputLayout)
                (loanFund_credit_scroce_layout.getParent()).getParent()));

        loanFund_rate_edt = view.findViewById(R.id.loanFund_rate_edt);
        loanFund_rate_edt.setHintText(getString(R.string.hint_loaneligibility_interestrate), ((TextInputLayout)
                (loanFund_rate_edt.getParent()).getParent()));

        loanFund_ltv_edit = view.findViewById(R.id.loanFund_ltv_edit);
        loanFund_ltv_edit.setHintText(getString(R.string.hint_loaneligibility_loantovalue), ((TextInputLayout)
                (loanFund_ltv_edit.getParent()).getParent()));

        edt_prepayment_view_id_percentage = view.findViewById(R.id.edt_prepayment_view_id);
        edt_prepayment_view_id_percentage.addTextChangedListener(new CommonTextWatcherPercentage(edt_prepayment_view_id_percentage));
        loanFund_payment_capacity_edt = view.findViewById(R.id.loanFund_payment_capacity_edt);
        loanFund_payment_capacity_edt.setTextHint("Payment Capacity");
        loanFund_payment_capacity_edt.setfullHintTxt(getString(R.string.hint_loaneligibility_paymentcapacity));

        loanFund_payment_capacity_edt.getEditText().addTextChangedListener(new CommonTextWatcher(loanFund_payment_capacity_edt));

        loan_fund_total_income_edt = view.findViewById(R.id.loan_fund_total_income_edt);
        loan_fund_total_income_edt.setTextHint("Total Income");
        loan_fund_total_income_edt.setfullHintTxt(getString(R.string.hint_loaneligibility_totalincome));
        loan_fund_total_income_edt.getEditText().addTextChangedListener(new CommonTextWatcher(loan_fund_total_income_edt));

        loan_fund_total_payments_edt = view.findViewById(R.id.loan_fund_total_payments_edt);
        loan_fund_total_payments_edt.setTextHint("Total Payments");
        loan_fund_total_payments_edt.setfullHintTxt(getString(R.string.hint_loaneligibility_totalpayments));
        loan_fund_total_payments_edt.getEditText().addTextChangedListener(new CommonTextWatcher(loan_fund_total_payments_edt));

        edt_downpayment = view.findViewById(R.id.edt_downpayment);
        edt_downpayment.setTextHint("Down Payment");
        edt_downpayment.setfullHintTxt(getString(R.string.hint_loaneligibility_downpayment));
        edt_downpayment.getEditText().addTextChangedListener(new CommonTextWatcher(edt_downpayment));

        edt_loanreq = view.findViewById(R.id.edt_loanreq);
        edt_loanreq.setTextHint("Loan Requirement");
        edt_loanreq.setfullHintTxt(getString(R.string.hint_loaneligibility_loanrequirement));


        edt_loanreq.getEditText().addTextChangedListener(new CommonTextWatcher(edt_loanreq));

        edt_prepayment_layout = view.findViewById(R.id.edt_prepayment_layout);

        edt_downpayment_rupeee_RadioBtn = view.findViewById(R.id.edt_downpayment_rupeee_RadioBtn);
        edt_downpayment_percent_RadioBtn= view.findViewById(R.id.edt_downpayment_percent_RadioBtn);
//        edt_loanreq_yes_RadioBtn = (RadioButton) view.findViewById(R.id.edt_loanreq_yes_RadioBtn);
//        edt_loanreq_no_RadioBtn= (RadioButton) view.findViewById(R.id.edt_loanreq_no_RadioBtn);
        processingFee_rg = view.findViewById(R.id.processingFee_rg);
//        loanreq_rg = (RadioGroup) view.findViewById(R.id.loanreq_rg);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setVisibility(View.GONE);
        processingFee_rg.setOnCheckedChangeListener(this);
//        loanreq_rg.setOnCheckedChangeListener(this);

        loanFund_Requirement_edit_calc= view.findViewById(R.id.loanFund_Requirement_edit_calc);
        loanFund_payment_capacity_edt_calc= view.findViewById(R.id.loanFund_payment_capacity_edt_calc);
        loan_fund_total_income_edt_calc= view.findViewById(R.id.loan_fund_total_income_edt_calc);
        loan_fund_total_payments_edt_calc= view.findViewById(R.id.loan_fund_total_payments_edt_calc);
        loanFund_Requirement_edit_calc.setOnClickListener(this);
        loanFund_payment_capacity_edt_calc.setOnClickListener(this);
        loan_fund_total_income_edt_calc.setOnClickListener(this);
        loan_fund_total_payments_edt_calc.setOnClickListener(this);

        loanFund_tenure_lyt= view.findViewById(R.id.loanFund_tenure_lyt);
        loanFund_rate_lyt= view.findViewById(R.id.loanFund_rate_lyt);
        loanFund_ltv_layout= view.findViewById(R.id.loanFund_ltv_layout);
        loanFund_credit_layout= view.findViewById(R.id.loanFund_credit_layout);

        downpayment_calculatorimage_calc= view.findViewById(R.id.downpayment_calculatorimage_calc);
        downpayment_calculatorimage_calc.setOnClickListener(this);



        fab.setOnClickListener(this);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.loanFund_Requirement_edit_calc:
            {
                showCalDialog(loanFund_Requirement_edit);
            }
            break;
            case R.id.loanFund_payment_capacity_edt_calc:
            {
                showCalDialog(loanFund_payment_capacity_edt);
            }
            break;
            case R.id.loan_fund_total_income_edt_calc:
            {
                showCalDialog(loan_fund_total_income_edt);
            }
            break;
            case R.id.loan_fund_total_payments_edt_calc:
            {
                showCalDialog(loan_fund_total_payments_edt);
            }
            break;
            case R.id.downpayment_calculatorimage_calc:
            {
                showCalDialog(edt_downpayment);
            }
            break;


            case R.id.relative_left_arrow:
            {

                Log.i("spcheck", " relative_left_arrow is clicked"  );
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
                startHomeActivity();
            }
            break;
            case R.id.relative_right_arrow:
            {

            }
            break;
            case R.id.goalaff_fab_id:{
                validateAllValues();
                View selectedView = loantypespinner.getSelectedView();
                if (selectedView != null && selectedView instanceof TextView) {
                    loantypespinner.requestFocus();
                    selectedTextView = (TextView) selectedView;

                    if(selectedTextView.getText().length()!=0){
                        selectedTextView.setError(null);
                    }
                }
            }
        }
    }


    private void validateAllValues() {
        fund_req= UtileKit.getStringwithoutCurreny(loanFund_Requirement_edit.getText().toString());
        down_payment= UtileKit.getStringwithoutCurreny(edt_downpayment.getEditText().getText().toString());
        loan_req= UtileKit.getStringwithoutCurreny(edt_loanreq.getEditText().getText().toString());
        loan_tenure= UtileKit.getStringwithoutCurreny(loanFund_tenure_edt.getText().toString());
        int_rate= UtileKit.getStringwithoutCurreny(loanFund_rate_edt.getText().toString());
        payment_capacity = UtileKit.getStringwithoutCurreny(loanFund_payment_capacity_edt.getText().toString());
        tot_inc= UtileKit.getStringwithoutCurreny(loan_fund_total_income_edt.getText().toString());
        tot_payment= UtileKit.getStringwithoutCurreny(loan_fund_total_payments_edt.getText().toString());
        loan_to_value=UtileKit.getStringwithoutCurreny(loanFund_ltv_edit.getText().toString());
        credit_score= UtileKit.getStringwithoutCurreny(loanFund_credit_scroce_layout.getText().toString());

        if(isValid(1)&&isValid(2)&&isValid(3)&&isValid(4)&&isValid(5)&&isValid(6)&&isValid(7)&&isValid(8)&&isValid(9)
                &&isValid(10)&&isValid(11)){
            updateWebservice(loan_type, fund_req, down_payment,
                    loan_req, loan_tenure, int_rate, payment_capacity
                    ,tot_inc,tot_payment,loan_to_value,credit_score);
        }
    }

    boolean isValid(int position)
    {
        boolean flag=false;
        switch (position){
            case 1:
                flag=setSpinnerError(loantypespinner,loan_type);
                break;
            case 2:
                flag=checkValuesAndSetErrors((TextInputLayout)loanFund_Requirement_edit.getEditText().getParent().getParent(),
                        fund_req,loanFund_Requirement_edit);
                getFocusCurrencyGhost(loanFund_Requirement_edit);
                break;
            case 3:
                flag=checkValuesAndSetErrors((TextInputLayout)edt_downpayment.getEditText().getParent().getParent(),
                        down_payment,edt_downpayment);
                getFocusCurrencyGhost(edt_downpayment);
                break;
            case 4:
                flag=checkValuesAndSetErrors((TextInputLayout)edt_loanreq.getEditText().getParent().getParent(),
                        loan_req,edt_loanreq);
                getFocusCurrencyGhost(edt_loanreq);
                break;
            case 5:
                flag=checkValuesAndSetError(loanFund_tenure_lyt,loan_tenure,loanFund_tenure_edt);
                break;
            case 6:
                flag=checkValuesAndSetError(loanFund_rate_lyt,int_rate,loanFund_rate_edt);
                break;
            case 7:
                flag=checkValuesAndSetErrors((TextInputLayout)loanFund_payment_capacity_edt.getEditText().getParent().getParent(),
                        payment_capacity,loanFund_payment_capacity_edt);
                getFocusCurrencyGhost(loanFund_payment_capacity_edt);
                break;
            case 8:
                flag=checkValuesAndSetErrors((TextInputLayout)loan_fund_total_income_edt.getEditText().getParent().getParent(),
                        tot_inc,loan_fund_total_income_edt);
                getFocusCurrencyGhost(loan_fund_total_income_edt);
                break;
            case 9:
                flag=checkValuesAndSetErrors((TextInputLayout)loan_fund_total_payments_edt.getEditText().getParent().getParent(),
                        tot_payment,loan_fund_total_payments_edt);
                getFocusCurrencyGhost(loan_fund_total_payments_edt);
                break;
            case 10:
                flag=checkValuesAndSetError(loanFund_ltv_layout,loan_to_value,loanFund_ltv_edit);
                break;
            case 11:
                flag=checkValuesAndSetError(loanFund_credit_layout,credit_score,loanFund_credit_scroce_layout);
                break;
        }

        return flag;
    }




    public boolean setSpinnerError(Spinner spinner, String error){
        status=false;
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            status=true;
            spinner.requestFocus();
            selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error");
            selectedTextView.setText(error);
        }
        else if(selectedTextView.getText().length()!=0){
            selectedTextView.setError(null);
        }
        return status;
    }

    public class CommonTextWatcher implements TextWatcher {
        RelativeLayout editText;
        public CommonTextWatcher(RelativeLayout editText){
            this.editText=editText;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (editText.getId()){
                case R.id.loanFund_Requirement_edit:
                    Log.i("LoanEligibility", "onTextChanged : calls loanFund_Requirement_edit");
                    if (s.length() != 0) {
                        try{
                            downpayment= (edt_downpayment.getText().toString());
                            if(UtileKit.validateObjectValuesAndCheckZero(downpayment)){
                                if(UtileKit.validateObjectValues(s.toString())) {
                                    String subvalue = sub(s.toString(), downpayment);
                                    edt_loanreq.setText((subvalue));
                                }else{
                                    String subvalue = sub("0", downpayment);
                                    edt_loanreq.setText((subvalue));
                                }
                            }else if(UtileKit.validateObjectValuesAndCheckZero(s.toString())){
                                String subvalue = sub(s.toString(), "0");
                                edt_loanreq.setText((subvalue));
                            }else{
                                String subvalue = sub("0", "0");
                                edt_loanreq.setText((subvalue));
                            }
                        }catch (Exception e)
                        {e.printStackTrace();}
                    }
                    break;

                case R.id.edt_downpayment:
                    Log.i("LoanEligibility", "onTextChanged : calls  edt_downpayment");
                    if (s.length() != 0) {
                        try{
                            fundReq= (loanFund_Requirement_edit.getText().toString());
                            if(UtileKit.validateObjectValuesAndCheckZero(fundReq)){
                                if(UtileKit.validateObjectValues(s.toString())) {
                                    String subvalue = sub(s.toString(), fundReq);
                                    edt_loanreq.setText((subvalue));
                                }else{
                                    String subvalue = sub("0", fundReq);
                                    edt_loanreq.setText((subvalue));
                                }
                            }else if(UtileKit.validateObjectValuesAndCheckZero(s.toString())){
                                String subvalue = sub(s.toString(), "0");
                                edt_loanreq.setText((subvalue));
                            }else{
                                String subvalue = sub("0", "0");
                                edt_loanreq.setText((subvalue));
                            }
                        }catch (Exception e)
                        {e.printStackTrace();}
                    }
                    break;
        }

        }

        @Override
        public void afterTextChanged(Editable s) {

            switch (editText.getId()){
                case R.id.loanFund_Requirement_edit:
                    ((TextInputLayout)loanFund_Requirement_edit.getEditText().getParent().getParent()).setErrorEnabled(false);
                    ((TextInputLayout)loanFund_Requirement_edit.getEditText().getParent().getParent()).setError(null);

                    break;
                case R.id.edt_loanreq:
                    ((TextInputLayout)edt_loanreq.getEditText().getParent().getParent()).setErrorEnabled(false);
                    ((TextInputLayout)edt_loanreq.getEditText().getParent().getParent()).setError(null);

                    break;
                case R.id.loanFund_payment_capacity_edt:
                    ((TextInputLayout) loanFund_payment_capacity_edt.getEditText().getParent().getParent()).setErrorEnabled(false);
                    ((TextInputLayout) loanFund_payment_capacity_edt.getEditText().getParent().getParent()).setError(null);
                    break;
                case R.id.loan_fund_total_income_edt:
                    ((TextInputLayout) loan_fund_total_income_edt.getEditText().getParent().getParent()).setErrorEnabled(false);
                    ((TextInputLayout) loan_fund_total_income_edt.getEditText().getParent().getParent()).setError(null);
                    break;
                case R.id.loan_fund_total_payments_edt:
                    ((TextInputLayout) loan_fund_total_payments_edt.getEditText().getParent().getParent()).setErrorEnabled(false);
                    ((TextInputLayout) loan_fund_total_payments_edt.getEditText().getParent().getParent()).setError(null);
                    break;
                case R.id.edt_prepayment_view_id:
                    if (processingFee_rg.getCheckedRadioButtonId() == R.id.edt_downpayment_percent_RadioBtn) {
                        edt_prepayment_layout.setErrorEnabled(false);
                        edt_prepayment_layout.setError(null);
                    }
                    break;
                case R.id.edt_downpayment:
                    ((TextInputLayout)edt_downpayment.getEditText().getParent().getParent()).setError(null);
                    if (processingFee_rg.getCheckedRadioButtonId() == R.id.edt_downpayment_rupeee_RadioBtn) {
                        edt_prepayment_layout.setErrorEnabled(false);
                        edt_prepayment_layout.setError(null);
                    }
                    break;
            }

        }
    }


    public class CommonTextWatcherPercentage implements TextWatcher {
        PercentageEditText editText;
        public CommonTextWatcherPercentage(PercentageEditText editText){
            this.editText=editText;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (editText.getId()){

                case R.id.edt_prepayment_view_id:
                    Log.i("LoanEligibility", "onTextChanged : calls  edt_prepayment_view_id");
//                    if (s.length() != 0) {
                        try{
                            fundReq= (loanFund_Requirement_edit.getText().toString());
                            if(UtileKit.validateObjectValuesAndCheckZero(fundReq)){
                                if(UtileKit.validateObjectValues(s.toString())){
                                    BigDecimal per_calc = (percentageCalculation(s.toString(), fundReq));
                                    edt_loanreq.setText(per_calc.toString());

                                }else {
                                    BigDecimal per_calc = (percentageCalculation("0", fundReq));
                                    edt_loanreq.setText(per_calc.toString());
                                }
                            }else if(UtileKit.validateObjectValuesAndCheckZero(s.toString())){
                                String subvalue = sub(s.toString(), "0");
                                edt_loanreq.setText((subvalue));
                            }else{
                                String subvalue = sub("0", "0");
                                edt_loanreq.setText((subvalue));
                            }
                        }catch (Exception e)
                        {e.printStackTrace();}
//                    }
                    break;
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private BigDecimal percentageCalculation(String edittextvalue, String fundReq) {

        BigDecimal calcPercentagevalue,percentageamount;
        BigDecimal editvalue =  new BigDecimal(edittextvalue);
        BigDecimal fundReqment =  new BigDecimal(UtileKit.getStringwithoutCurreny(fundReq));
        BigDecimal hunder =  new BigDecimal(100);

        percentageamount = new BigDecimal(String.valueOf((editvalue.divide(hunder)).multiply(fundReqment)));

        calcPercentagevalue = fundReqment.subtract(percentageamount);

        return calcPercentagevalue.setScale(0,BigDecimal.ROUND_UP);
    }


    public  String sub(String editTextvalue, String strvalue) {
        try {
            BigInteger v1 = new BigInteger(UtileKit.getStringwithoutCurreny(editTextvalue));

            BigInteger v2 = new BigInteger(UtileKit.getStringwithoutCurreny(strvalue));

            return String.valueOf((v1.subtract(v2).abs()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


    private class CommonTextWatchers implements TextWatcher{
        EditText editText;
        public CommonTextWatchers(EditText editText){
            this.editText=editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (editText.getId()){
                case R.id.loanFund_tenure_edt:
                    loanFund_tenure_lyt.setErrorEnabled(false);
                    loanFund_tenure_lyt.setError(null);
                    break;
                case R.id.loanFund_credit_scroce_layout:
                    loanFund_credit_layout.setErrorEnabled(false);
                    loanFund_credit_layout.setError(null);
                    break;
            }
        }
    }




    private boolean checkValuesAndSetErrors(TextInputLayout textInputLayout, String text, CurrencyGhostView editText) {
        status=false;
        if(UtileKit.validateObjectValues(text))
        {
            status=true;
        }else{
            textInputLayout.setError("Please enter missing value");
            getFocusCurrencyGhost(editText);
        }
        return status;
    }
    private boolean checkValuesAndSetCurrencyBlackErrors(TextInputLayout textInputLayout, String text, CurrencyBlackGhostview editText) {
        status=false;
        if(UtileKit.validateObjectValues(text))
        {
            status=true;
        }else{
            textInputLayout.setError("Please enter missing value");
            getFocusCurrencyBlackGhost(editText);
        }
        return status;
    }

    private boolean checkValuesAndSetError(TextInputLayout textInputLayout, String text, EditText editText) {
        status=false;
        if(UtileKit.validateObjectValues(text))
        {
            status=true;
        }else{
            textInputLayout.setError("Please enter missing value");
            getFocus(editText);
        }
        return status;

    }
    private void getFocus(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
    private void getFocusCurrencyGhost(CurrencyGhostView editText) {
        editText.getEditText().requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText.getEditText(), InputMethodManager.SHOW_IMPLICIT);
    }
    private void getFocusCurrencyBlackGhost(CurrencyBlackGhostview editText) {

        editText.getEditText().requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText.getEditText(), InputMethodManager.SHOW_IMPLICIT);
    }
    public void setSpinnerAdapter(Spinner mMyMartialSpinner, String[] myStringArray) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("");
        for (String s : myStringArray) {
            stringList.add(s);
        }
        CustomSpinerAdapter adapter_state = new CustomSpinerAdapter(mContext, stringList);
        mMyMartialSpinner.setAdapter(adapter_state);
        mMyMartialSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.loantypespinner:
                loan_type = loantypespinner.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void updateWebservice(String loan_type, String fund_req, String down_payment, String loan_req,
                                  String loan_tenure, String int_rate, String payment_capacity, String tot_inc,
                                  String tot_payment, String loan_to_value,String credit_score) {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls callObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<LoanEligibilityModel> call=callObj.getloaneligibility(loan_type,
                fund_req,down_payment,loan_req,loan_tenure,int_rate,payment_capacity,tot_inc,
                tot_payment,credit_score);

        call.enqueue(new Callback<LoanEligibilityModel>() {
            @Override
            public void onResponse(Call<LoanEligibilityModel> call, Response<LoanEligibilityModel> response) {
                UtileKit.dismisssSpinnerDialog();
                mLoanEligibilityModel = response.body();
                if(mLoanEligibilityModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    checkAffordableOrNot(mLoanEligibilityModel);
                }

            }
            @Override
            public void onFailure(Call<LoanEligibilityModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }
    private void setAllRadioButtonsToCheckedState() {
        UtileKit.getSwitchYesBtnView(edt_downpayment_rupeee_RadioBtn ,edt_downpayment_percent_RadioBtn, mContext);
//
    }
    private void checkAffordableOrNot(LoanEligibilityModel mLoanEligibilityModel) {
        if(mLoanEligibilityModel!= null){
            if(mLoanEligibilityModel.getData().getResult().equalsIgnoreCase("Not Affordable")){

                FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
                DialogFragment newFragment = LoanEligibilityDialog.newInstance(mLoanEligibilityModel, mContext);

                newFragment.show(fm, "dialog");
            }else{
                FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
                DialogFragment newFragment = LoanEligibilityDialog.newInstance(mLoanEligibilityModel, mContext);

                newFragment.show(fm, "dialog");
            }
        }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()) {
            case R.id.processingFee_rg:
                if (checkedId == R.id.edt_downpayment_rupeee_RadioBtn) {
                  //  edt_loanreq.setText("");
                    String dummyvalue = "0";
                    UtileKit.getSwitchYesBtnView(edt_downpayment_rupeee_RadioBtn ,edt_downpayment_percent_RadioBtn, mContext);
                    edt_prepayment_view_id_percentage.setVisibility(View.GONE);
                    edt_downpayment.setVisibility(View.VISIBLE);
                    edt_prepayment_layout.setVisibility(View.GONE);
                    downpayment_calculatorimage_calc.setVisibility(View.VISIBLE);
                   // edt_downpayment.setText("");
                    edt_prepayment_view_id_percentage.setText(dummyvalue);

                } else if (checkedId == R.id.edt_downpayment_percent_RadioBtn) {
                  //  edt_loanreq.setText("");
                    String dummyvalue = "0";
                    UtileKit.getSwitchNoBtnView(edt_downpayment_rupeee_RadioBtn ,edt_downpayment_percent_RadioBtn, mContext);
                    edt_prepayment_layout.setVisibility(View.VISIBLE);
                    edt_prepayment_view_id_percentage.setVisibility(View.VISIBLE);
                    edt_downpayment.setVisibility(View.GONE);
                    downpayment_calculatorimage_calc.setVisibility(View.GONE);
                    edt_downpayment.getEditText().setText(dummyvalue);
                  //  edt_loanreq.getEditText().setText("");
                }
                break;
//            case R.id.loanreq_rg:
//                if (checkedId == R.id.edt_loanreq_yes_RadioBtn) {
//                    UtileKit.getSwitchYesBtnView(edt_loanreq_yes_RadioBtn,edt_loanreq_no_RadioBtn, mContext);
//
//                } else if (checkedId == R.id.edt_loanreq_no_RadioBtn) {
//                    UtileKit.getSwitchNoBtnView(edt_loanreq_yes_RadioBtn,edt_loanreq_no_RadioBtn, mContext);
//
//                }
//                break;
        }
    }


}

package com.purplepath.purplepath.desiproAllModules.depositcomparison;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.IdRes;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.finobot.finobot.R;
import com.calculator.CalculatorAct;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.interfaces.UpdateValueInActivityInterface;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.ui.NewLoanFragment;
import com.purplepath.purplepath.desiproAllModules.loanComparison.interfaces.LoanComparisonInterface;
import com.purplepath.purplepath.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.os.Build.VERSION_CODES.O;
import static com.purplepath.purplepath.incomedetails.IncomeDynamicDetail.PARENT_CLASS_SOURCE;

/**
 * Created by dinesh on 21/06/17.
 */

public class Deposit1Fragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener{

    static final int LOAN_1_ID = 1, LOAN_2_ID = 2, LOAN_3_ID = 3;
    private String[]period_spinner_array = {"Yearly", "Half Yearly", "Quarterly", "Monthly", "Weekly", "Daily"},
            paymentType_spinner_array = {"Period Ending", "Period Beginning"},
            investment_type_arry={"Lumpsum","Periodical","Both"};

    @BindView(R.id.initial_value_edt)
    CurrencyGhostView initial_value_edt;


    @BindView(R.id.periodical_edt)
    CurrencyGhostView periodical_edt;


    @BindView(R.id.tenure_lyt)
    TextInputLayout tenure_lyt;

    @BindView(R.id.tenure_edt)
    NumberEditText tenure_edt;

    @BindView(R.id.rateOfInterest_lyt)
    TextInputLayout rateOfInterest_lyt;

    @BindView(R.id.rateOfInterest_edt)
    PercentageEditText rateOfInterest_edt;

    @BindView(R.id.entrychargeFeeRupee_layout)
    TextInputLayout entrychargeFeeRupee_layout;

    @BindView(R.id.entrychargePercent_layout)
    TextInputLayout entrychargePercent_layout;


    @BindView(R.id.edt_entrychargeRupee_edit)
    CurrencyGhostView edt_entrychargeRupee_edit;


    @BindView(R.id.exitcharge_percent_layout)
    TextInputLayout exitcharge_percent_layout;
    @BindView(R.id.exitcharge_rupeee_layout)
    TextInputLayout exitcharge_rupeee_layout;

    @BindView(R.id.edt_exitchatege_edit)
    CurrencyGhostView edt_exitchatege_edit;



    @BindView(R.id.entrycharge_rg)
    RadioGroup entrycharge_rg;

    @BindView(R.id.entrycharge_rupeee_RadioBtn)
    RadioButton entrycharge_rupeee_RadioBtn;

    @BindView(R.id.entrycharge_percent_RadioBtn)
    RadioButton entrycharge_percent_RadioBtn;


    @BindView(R.id.exitcharge_rg)
    RadioGroup exitcharge_rg;

    @BindView(R.id.exitcharge_rupeee_RadioBtn)
    RadioButton exitcharge_rupeee_RadioBtn;

    @BindView(R.id.exitcharge_percent_RadioBtn)
    RadioButton exitcharge_percent_RadioBtn;

    @BindView(R.id.edt_peneltychargeFeeRupee_view_id)
    CurrencyGhostView edt_peneltychargeFeeRupee_view_id;

    @BindView(R.id.peneltychargeFeeRupee_layout)
    TextInputLayout peneltychargeFeeRupee_layout;

    @BindView(R.id.peneltychargeFee_percent_layout)
    TextInputLayout peneltychargeFee_percent_layout;

    @BindView(R.id.peneltychargeFee_rg)
    RadioGroup peneltychargeFee_rg;


    @BindView(R.id.peneltychargeFee_rupee_RadioBtn)
    RadioButton peneltychargeFee_rupee_RadioBtn;

    @BindView(R.id.peneltychargeFee_percent_RadioBtn)
    RadioButton peneltychargeFee_percent_RadioBtn;

    @BindView(R.id.paymenttype_spinner)
    Spinner paymenttype_spinner;

    @BindView(R.id.paymentperiod_spinner)
    Spinner paymentperiod_spinner;

    @BindView(R.id.investmenttype_spinner)
    Spinner investmenttype_spinner;


    @BindView(R.id.initial_value_edt_calculaterImgView)
    CustomCalenderImageView initial_value_edt_calculaterImgView;
    @BindView(R.id.periodical_edt_calculaterImgView)
    CustomCalenderImageView periodical_edt_calculaterImgView;

    @BindView(R.id.entrycharge_calculatorimage)
    CustomCalenderImageView entrycharge_calculatorimage;
    @BindView(R.id.exitcharge_calculatorimage)
    CustomCalenderImageView exitcharge_calculatorimage;
    @BindView(R.id.peneltycharge_fee_calculatorimage)
    CustomCalenderImageView peneltycharge_fee_calculatorimage;


    private Context mContext;
    private LoanComparisonInterface loanComparisonInterface;
    private int fragmentID;
    View nameEditview;
    public static final String TITLE = "";
    View view;

    public boolean isInitialized=false;

    public static Deposit1Fragment newInstance(int fragmentID, LoanComparisonInterface mLoanComparisonInterface) {
        Bundle args = new Bundle();
        args.putInt("fragmentID", fragmentID);
        Deposit1Fragment fragment = new Deposit1Fragment();
        fragment.loanComparisonInterface = mLoanComparisonInterface;
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isInitialized==false)  initializeViews();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(isInitialized==false) {
            view = inflater.inflate(R.layout.fragment_deposit_view, container, false);
        }
        ButterKnife.bind(this, view);
        Bundle args = getArguments();

        if (args != null) {
            if (args.containsKey("fragmentID")) {
                fragmentID = args.getInt("fragmentID");
            }
        }
        return view;
    }



    private void initializeViews() {
        initial_value_edt.setEditTextId(R.id.initial_value_edt_id);
        periodical_edt.setEditTextId(R.id.periodical_edt_id);

        entrycharge_rg.setOnCheckedChangeListener(this);
        exitcharge_rg.setOnCheckedChangeListener(this);
        peneltychargeFee_rg.setOnCheckedChangeListener(this);

        // loanComparisonInterface = (LoanComparisonInterface) getActivity();

        setAllRadioButtonsToCheckedState();

        initial_value_edt.getEditText().addTextChangedListener(new GenericTextWatchers(initial_value_edt));
        periodical_edt.getEditText().addTextChangedListener(new GenericTextWatchers(periodical_edt));
        rateOfInterest_edt.addTextChangedListener(new GenericTextWatcher(rateOfInterest_edt));
        rateOfInterest_edt.setHintText(getString(R.string.hint_deposit_rateofinterest), ((TextInputLayout)(rateOfInterest_edt.getParent()).getParent()));

        tenure_edt.addTextChangedListener(new GenericTextWatcher(tenure_edt));
        tenure_edt.setHintText(getString(R.string.hint_deposit_tenure), ((TextInputLayout)(tenure_edt.getParent()).getParent()));

        edt_entrychargeRupee_edit.getEditText().addTextChangedListener(new GenericTextWatcher(edt_entrychargeRupee_edit.getEditText()));
        edt_exitchatege_edit.getEditText().addTextChangedListener(new GenericTextWatcher(edt_exitchatege_edit.getEditText()));
        edt_peneltychargeFeeRupee_view_id.getEditText().addTextChangedListener(new GenericTextWatcher(edt_peneltychargeFeeRupee_view_id.getEditText()));

        setSpinner(paymentperiod_spinner,period_spinner_array);
        setSpinner(paymenttype_spinner,paymentType_spinner_array);
        setSpinner(investmenttype_spinner,investment_type_arry);


        initial_value_edt.setTextHint("Initial");
        initial_value_edt.setfullHintTxt(getString(R.string.hint_deposit_initial));

        periodical_edt.setTextHint("Periodical");
        periodical_edt.setfullHintTxt(getString(R.string.hint_deposit_periodical));


        edt_entrychargeRupee_edit.setTextHint("Entry Charge");
        edt_exitchatege_edit.setTextHint("Exit Charge");
        edt_peneltychargeFeeRupee_view_id.setTextHint("Penelty Charge");


        initial_value_edt_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(initial_value_edt);
            }
        });
        periodical_edt_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(periodical_edt);
            }
        });
        entrycharge_calculatorimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(edt_entrychargeRupee_edit);
            }
        });
        exitcharge_calculatorimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(edt_exitchatege_edit);
            }
        });
        peneltycharge_fee_calculatorimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(edt_peneltychargeFeeRupee_view_id);
            }
        });

        isInitialized=true;
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

    private void setSpinner(Spinner spinnerView, String[] spinner_array) {
        ArrayList obj=new ArrayList();
        obj.add("");
        Collections.addAll(obj, spinner_array);
       CustomSpinerAdapter spinner_adapter = new CustomSpinerAdapter(mContext, obj);
        spinnerView.setAdapter(spinner_adapter);
    }

    private void setAllRadioButtonsToCheckedState() {
        UtileKit.getSwitchYesBtnView(entrycharge_rupeee_RadioBtn, entrycharge_percent_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnView(exitcharge_rupeee_RadioBtn, exitcharge_percent_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnView(peneltychargeFee_rupee_RadioBtn, peneltychargeFee_percent_RadioBtn, mContext);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()) {
            case R.id.entrycharge_rg:
                if (checkedId == R.id.entrycharge_rupeee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(entrycharge_rupeee_RadioBtn, entrycharge_percent_RadioBtn, mContext);
                    entrychargeFeeRupee_layout.setVisibility(View.VISIBLE);
                    entrychargePercent_layout.setVisibility(View.GONE);
                    entrycharge_calculatorimage.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.entrycharge_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(entrycharge_rupeee_RadioBtn, entrycharge_percent_RadioBtn, mContext);
                    entrychargeFeeRupee_layout.setVisibility(View.GONE);
                    entrychargePercent_layout.setVisibility(View.VISIBLE);
                    entrycharge_calculatorimage.setVisibility(View.GONE);
                }
                break;

            case R.id.exitcharge_rg:
                if (checkedId == R.id.exitcharge_rupeee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(exitcharge_rupeee_RadioBtn, exitcharge_percent_RadioBtn, mContext);
                    exitcharge_rupeee_layout.setVisibility(View.VISIBLE);
                    exitcharge_percent_layout.setVisibility(View.GONE);
                    exitcharge_calculatorimage.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.exitcharge_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(exitcharge_rupeee_RadioBtn, exitcharge_percent_RadioBtn, mContext);
                    exitcharge_rupeee_layout.setVisibility(View.GONE);
                    exitcharge_percent_layout.setVisibility(View.VISIBLE);
                    exitcharge_calculatorimage.setVisibility(View.GONE);
                }
                break;

            case R.id.peneltychargeFee_rg:
                if (checkedId == R.id.peneltychargeFee_rupee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(peneltychargeFee_rupee_RadioBtn, peneltychargeFee_percent_RadioBtn, mContext);
                    peneltychargeFeeRupee_layout.setVisibility(View.VISIBLE);
                    peneltychargeFee_percent_layout.setVisibility(View.GONE);
                    peneltycharge_fee_calculatorimage.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.peneltychargeFee_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(peneltychargeFee_rupee_RadioBtn, peneltychargeFee_percent_RadioBtn, mContext);
                    peneltychargeFeeRupee_layout.setVisibility(View.GONE);
                    peneltychargeFee_percent_layout.setVisibility(View.VISIBLE);
                    peneltycharge_fee_calculatorimage.setVisibility(View.GONE);
                }
                break;


        }
    }

 public void setErrorInLoanFragment(int index) {
        switch (index) {
            case 0:
                // setError(edt_out_standing_layout);
                setError(((TextInputLayout) (initial_value_edt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(initial_value_edt);
                Log.i("spcheck", "setError NewLoan: is called method 1");
                break;
            case 1:
                setError(((TextInputLayout) (periodical_edt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(periodical_edt);
                break;

            case 2:
                setError(tenure_lyt);
                getFocus(tenure_edt);

                break;

            case 3:
                setError(rateOfInterest_lyt);
                getFocus(rateOfInterest_edt);
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


    private class GenericTextWatchers implements TextWatcher {
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
            try {
                ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setErrorEnabled(false);
                ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setError(null);
                switch (currencyGhostView.getId()) {
                    case R.id.initial_value_edt:
                        loanComparisonInterface.updateLoanValues(s.toString(), "dp_init_val", fragmentID);
                        break;
                    case R.id.periodical_edt:
                        loanComparisonInterface.updateLoanValues(s.toString(), "dp_period_val", fragmentID);
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
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
            try{
            switch (editText.getId()) {
               /* case R.id.initial_value_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "dp_init_val", fragmentID);
                    break;
                case R.id.periodical_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "dp_period_val", fragmentID);
                    break;*/

                case R.id.tenure_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "dp_tenure", fragmentID);
                    tenure_lyt.setErrorEnabled(false);
                    tenure_lyt.setError(null);
                    break;
                case R.id.rateOfInterest_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "dp_rate", fragmentID);
                    rateOfInterest_lyt.setErrorEnabled(false);
                    rateOfInterest_lyt.setError(null);
                    break;
//                case R.id.edt_entrychargeRupee_edit:
//                    loanComparisonInterface.updateLoanValues(s.toString(), "rateOfInterest", fragmentID);
//                    rateOfInterest_lyt.setError(null);
//                    break;
              /*  case R.id.:
                    break;

                case R.id.:
                    break;

                case R.id.:
                    break;*/
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        }


    }
}
package com.purplepath.purplepath.desiproAllModules.loanComparison;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyDefaultEdittext;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.loanComparison.interfaces.LoanComparisonInterface;
import com.purplepath.purplepath.fragments.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.finobot.finobot.R.id.loan;
import static com.purplepath.purplepath.incomedetails.IncomeDynamicDetail.PARENT_CLASS_SOURCE;

/**
 * Created by Pratheep.S on 12-06-2017.
 */

public class Loan1Fragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, LoanComparisonInterface {

    static final int LOAN_1_ID = 1, LOAN_2_ID = 2, LOAN_3_ID = 3;

    @Bind(R.id.purchase_value_lyt)
    TextInputLayout purchase_value_lyt;

    @Bind(R.id.purchase_value_edt)
    CurrencyGhostView purchase_value_edt;

    @Bind(R.id.downPayement_lyt)
    TextInputLayout downPayement_lyt;

    @Bind(R.id.downPayement_edt)
    CurrencyGhostView downPayement_edt;

   /* @Bind(R.id.finaceValue_lyt)
    TextInputLayout finaceValue_lyt;*/

    @Bind(R.id.finaceValue_edt)
    CurrencyGhostView finaceValue_edt;

    @Bind(R.id.tenure_lyt)
    TextInputLayout tenure_lyt;

    @Bind(R.id.tenure_edt)
    NumberEditText tenure_edt;

    @Bind(R.id.rateOfInterest_lyt)
    TextInputLayout rateOfInterest_lyt;

    @Bind(R.id.rateOfInterest_edt)
    PercentageEditText rateOfInterest_edt;

    @Bind(R.id.emi_lyt)
    TextInputLayout emi_lyt;

    @Bind(R.id.emi_edt)
    CurrencyDefaultEdittext emi_edt;

    @Bind(R.id.residualValue_lyt)
    TextInputLayout residualValue_lyt;

    @Bind(R.id.residualValue_edt)
    CurrencyDefaultEdittext residualValue_edt;

    @Bind(R.id.processingFeeRupee_layout)
    TextInputLayout processingFeeRupee_layout;

    @Bind(R.id.edt_processingFeeRupee_view_id)
    CurrencyGhostView edt_processingFeeRupee_view_id;

    @Bind(R.id.processingFeePercent_layout)
    TextInputLayout processingFeePercent_layout;

    @Bind(R.id.edt_processingFeePercent_view_id)
    PercentageEditText edt_processingFeePercent_view_id;

    @Bind(R.id.processingFee_calculatorimage)
    CustomCalenderImageView processingFee_calculatorimage;

    @Bind(R.id.processingFee_rg)
    RadioGroup processingFee_rg;

    @Bind(R.id.processingFee_rupeee_RadioBtn)
    RadioButton processingFee_rupeee_RadioBtn;

    @Bind(R.id.processingFee_percent_RadioBtn)
    RadioButton processingFee_percent_RadioBtn;

    @Bind(R.id.legal_rg)
    RadioGroup legal_rg;

    @Bind(R.id.legal_rupee_RadioBtn)
    RadioButton legal_rupee_RadioBtn;

    @Bind(R.id.legal_percent_RadioBtn)
    RadioButton legal_percent_RadioBtn;

    @Bind(R.id.administrationFee_rg)
    RadioGroup administrationFee_rg;

    @Bind(R.id.administrationFee_rupee_RadioBtn)
    RadioButton administrationFee_rupee_RadioBtn;

    @Bind(R.id.administrationFee_percent_RadioBtn)
    RadioButton administrationFee_percent_RadioBtn;

    @Bind(R.id.insuranceCover_rg)
    RadioGroup insuranceCover_rg;

    @Bind(R.id.insuranceCover_rupee_RadioBtn)
    RadioButton insuranceCover_rupee_RadioBtn;

    @Bind(R.id.insuranceCover_percent_RadioBtn)
    RadioButton insuranceCover_percent_RadioBtn;

    @Bind(R.id.legalRupee_layout)
    TextInputLayout legalRupee_layout;

    @Bind(R.id.edt_legalRupee_view_id)
    CurrencyGhostView edt_legalRupee_view_id;

    @Bind(R.id.legal_percent_layout)
    TextInputLayout legal_percent_layout;

    @Bind(R.id.edt_legal_percent_view_id)
    PercentageEditText edt_legal_percent_view_id;

    @Bind(R.id.administrationFeeRupee_layout)
    TextInputLayout administrationFeeRupee_layout;

    @Bind(R.id.edt_administrationFeeRupee_view_id)
    CurrencyGhostView edt_administrationFeeRupee_view_id;

    @Bind(R.id.administrationFee_percent_layout)
    TextInputLayout administrationFee_percent_layout;

    @Bind(R.id.edt_administrationFee_percent_view_id)
    PercentageEditText edt_administrationFee_percent_view_id;

    @Bind(R.id.insuranceCoverRupee_layout)
    TextInputLayout insuranceCoverRupee_layout;

    @Bind(R.id.edt_insuranceCoverRupee_view_id)
    CurrencyGhostView edt_insuranceCoverRupee_view_id;

    @Bind(R.id.insuranceCover_percent_layout)
    TextInputLayout insuranceCover_percent_layout;

    @Bind(R.id.edt_insuranceCover_percent_view_id)
    PercentageEditText edt_insuranceCover_percent_view_id;


    @Bind(R.id.legal_calculatorimage)
    CustomCalenderImageView legal_calculatorimage;

    @Bind(R.id.administration_fee_calculatorimage)
    CustomCalenderImageView administration_fee_calculatorimage;

    @Bind(R.id.insuranceCover_calculatorimage)
    CustomCalenderImageView insuranceCover_calculatorimage;

    @Bind(R.id.payementPeriod_spinner)
    Spinner payementPeriod_spinner;

    @Bind(R.id.payementType_spinner)
    Spinner payementType_spinner;


    @Bind(R.id.purchase_value_edt_calculaterImgView)
    CustomCalenderImageView purchase_value_edt_calculaterImgView;
    @Bind(R.id.downPayement_edt_calculaterImgView)
    CustomCalenderImageView downPayement_edt_calculaterImgView;
    @Bind(R.id.finaceValue_edt_calculaterImgView)
    CustomCalenderImageView finaceValue_edt_calculaterImgView;
    View nameEditview;
    public static final String TITLE = "";
    View view;
    public boolean isInitialized=false;

    private Context mContext;
    private LoanComparisonInterface loanComparisonInterface;
    private int fragmentID;
    private String[] period_Spinner,type_Spinner;


    public static Loan1Fragment newInstance(int fragmentID, LoanComparisonInterface mLoanComparisonInterface) {
        Bundle args = new Bundle();
        args.putInt("fragmentID", fragmentID);
        Loan1Fragment fragment = new Loan1Fragment();
        fragment.loanComparisonInterface = mLoanComparisonInterface;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
//        loanComparisonInterface = this;
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
            view = inflater.inflate(R.layout.fragment_loan1, container, false);
        }
        ButterKnife.bind(this, view);
        return view;

    }
    private void initializeViews() {

        purchase_value_edt.setEditTextId(R.id.purchase_value_edt_id);
        downPayement_edt.setEditTextId(R.id.downPayement_edt_id);
        finaceValue_edt.setEditTextId(R.id.finaceValue_edt_id);

        edt_processingFeeRupee_view_id.setEditTextId(R.id.edt_processingFeeRupee_view_id_id);
        edt_legalRupee_view_id.setEditTextId(R.id.edt_legalRupee_view_id_id);
        edt_administrationFeeRupee_view_id.setEditTextId(R.id.edt_administrationFeeRupee_view_id_id);
        edt_insuranceCoverRupee_view_id.setEditTextId(R.id.edt_insuranceCoverRupee_view_id_id);

        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey("fragmentID")) {
                fragmentID = args.getInt("fragmentID");
            }
        }

        period_Spinner=new String[] {
                "Daily", "Weekly", "Monthly", "Quarterly", "Half Yearly","Yearly","Continuous"
        };

        type_Spinner=new String[]{
                "Period Beginning", "Period Ending"
        };


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),
                R.layout.sinmple_text_view, period_Spinner);

        payementPeriod_spinner.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                R.layout.sinmple_text_view, type_Spinner);

        payementType_spinner.setAdapter(adapter2);

        processingFee_rg.setOnCheckedChangeListener(this);
        legal_rg.setOnCheckedChangeListener(this);
        administrationFee_rg.setOnCheckedChangeListener(this);
        insuranceCover_rg.setOnCheckedChangeListener(this);

//        loanComparisonInterface = (LoanComparisonInterface) getContext();

        setAllRadioButtonsToCheckedState();

        //purchase_value_edt.addTextChangedListener(new GenericTextWatcher(purchase_value_edt));
        //downPayement_edt.addTextChangedListener(new GenericTextWatcher(downPayement_edt));
        finaceValue_edt.getEditText().addTextChangedListener(new GenericTextWatchers(finaceValue_edt));
        edt_processingFeeRupee_view_id.getEditText().addTextChangedListener(new GenericTextWatchers(edt_processingFeeRupee_view_id));
        edt_legalRupee_view_id.getEditText().addTextChangedListener(new GenericTextWatchers(edt_legalRupee_view_id));
        edt_administrationFeeRupee_view_id.getEditText().addTextChangedListener(new GenericTextWatchers(edt_administrationFeeRupee_view_id));
        edt_insuranceCoverRupee_view_id.getEditText().addTextChangedListener(new GenericTextWatchers(edt_insuranceCoverRupee_view_id));


        tenure_edt.addTextChangedListener(new GenericTextWatcher(tenure_edt));
        tenure_edt.setHintText(getString(R.string.hint_loan_tenure), ((TextInputLayout)(tenure_edt.getParent()).getParent()));

        rateOfInterest_edt.addTextChangedListener(new GenericTextWatcher(rateOfInterest_edt));
        rateOfInterest_edt.setHintText(getString(R.string.hint_loan_rateofinterest),((TextInputLayout)(rateOfInterest_edt.getParent()).getParent()));

        emi_edt.addTextChangedListener(new GenericTextWatcher(emi_edt));
        emi_edt.setHintText(getString(R.string.hint_loan_EMI), ((TextInputLayout)(emi_edt.getParent()).getParent()));

        residualValue_edt.addTextChangedListener(new GenericTextWatcher(residualValue_edt));
        residualValue_edt.setHintText(getString(R.string.hint_loan_residualvalue), ((TextInputLayout)(residualValue_edt.getParent()).getParent()));

        edt_processingFeePercent_view_id.addTextChangedListener(new GenericTextWatcher( edt_processingFeePercent_view_id));
        edt_processingFeePercent_view_id.setHintText(getString(R.string.hint_loan_processingfee),((TextInputLayout)
                (edt_processingFeePercent_view_id.getParent()).getParent()));

        edt_legal_percent_view_id.addTextChangedListener(new GenericTextWatcher(edt_legal_percent_view_id));
        edt_legal_percent_view_id.setHintText(getString(R.string.hint_loan_legalfee), ((TextInputLayout)(edt_legal_percent_view_id.getParent()).getParent()));
        edt_administrationFee_percent_view_id.addTextChangedListener(new GenericTextWatcher(edt_administrationFee_percent_view_id));

        edt_administrationFee_percent_view_id.setHintText(getString(R.string.hint_loan_administrationfee), ((TextInputLayout)
                (edt_administrationFee_percent_view_id.getParent()).getParent()));

        edt_insuranceCover_percent_view_id.addTextChangedListener(new GenericTextWatcher(edt_insuranceCover_percent_view_id));
        edt_insuranceCover_percent_view_id.setHintText(getString(R.string.hint_loan_insurancecover), ((TextInputLayout)
                (edt_insuranceCover_percent_view_id.getParent()).getParent()));

        purchase_value_edt.setTextHint("Purchase value");
        purchase_value_edt.setfullHintTxt(getString(R.string.hint_loan_purchasevalue));

        downPayement_edt.setTextHint("Down Payment");
        downPayement_edt.setfullHintTxt(getString(R.string.hint_loan_downpayment));

        finaceValue_edt.setTextHint("Finance Value");
        finaceValue_edt.setfullHintTxt(getString(R.string.hint_loan_financevalue));


        edt_processingFeeRupee_view_id.setTextHint("Processing Fees");
        edt_processingFeeRupee_view_id.setfullHintTxt(getString(R.string.hint_loan_processingfee));

        edt_legalRupee_view_id.setTextHint("Legal fee");
        edt_legalRupee_view_id.setfullHintTxt(getString(R.string.hint_loan_legalfee));


        edt_administrationFeeRupee_view_id.setTextHint("Administration Fee");
        edt_administrationFeeRupee_view_id.setfullHintTxt(getString(R.string.hint_loan_administrationfee));

        edt_insuranceCoverRupee_view_id.setTextHint("Insurance Cover");
        edt_insuranceCoverRupee_view_id.setfullHintTxt(getString(R.string.hint_loan_insurancecover));


        purchase_value_edt_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(purchase_value_edt);
            }
        });
        downPayement_edt_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(downPayement_edt);
            }
        });
        finaceValue_edt_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(finaceValue_edt);
            }
        });

        processingFee_calculatorimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(edt_processingFeeRupee_view_id);
            }
        });
        legal_calculatorimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(edt_legalRupee_view_id);
            }
        });
        administration_fee_calculatorimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(edt_administrationFeeRupee_view_id);
            }
        });
        insuranceCover_calculatorimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(edt_insuranceCoverRupee_view_id);
            }
        });

        isInitialized=true;
    }


    private void showCalDialog(View view) {
        nameEditview = view;
//        nameEditview.setTag(view.getTag());
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
    private void setAllRadioButtonsToCheckedState() {
        UtileKit.getSwitchYesBtnView(processingFee_rupeee_RadioBtn, processingFee_percent_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnView(legal_rupee_RadioBtn, legal_percent_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnView(administrationFee_rupee_RadioBtn, administrationFee_percent_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnView(insuranceCover_rupee_RadioBtn, insuranceCover_percent_RadioBtn, mContext);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()) {
            case R.id.processingFee_rg:
                if (checkedId == R.id.processingFee_rupeee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(processingFee_rupeee_RadioBtn, processingFee_percent_RadioBtn, mContext);
                    processingFeeRupee_layout.setVisibility(View.VISIBLE);
                    processingFeePercent_layout.setVisibility(View.GONE);
                    processingFee_calculatorimage.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.processingFee_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(processingFee_rupeee_RadioBtn, processingFee_percent_RadioBtn, mContext);
                    processingFeeRupee_layout.setVisibility(View.GONE);
                    processingFeePercent_layout.setVisibility(View.VISIBLE);
                    processingFee_calculatorimage.setVisibility(View.GONE);
                    calculateprocessingFeePercentValues();
                }
                break;

            case R.id.legal_rg:
                if (checkedId == R.id.legal_rupee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(legal_rupee_RadioBtn, legal_percent_RadioBtn, mContext);
                    legalRupee_layout.setVisibility(View.VISIBLE);
                    legal_percent_layout.setVisibility(View.GONE);
                    legal_calculatorimage.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.legal_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(legal_rupee_RadioBtn, legal_percent_RadioBtn, mContext);
                    legalRupee_layout.setVisibility(View.GONE);
                    legal_percent_layout.setVisibility(View.VISIBLE);
                    legal_calculatorimage.setVisibility(View.GONE);
                    calculateLegalFeePercentValues();
                }
                break;

            case R.id.administrationFee_rg:
                if (checkedId == R.id.administrationFee_rupee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(administrationFee_rupee_RadioBtn, administrationFee_percent_RadioBtn, mContext);
                    administrationFeeRupee_layout.setVisibility(View.VISIBLE);
                    administrationFee_percent_layout.setVisibility(View.GONE);
                    administration_fee_calculatorimage.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.administrationFee_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(administrationFee_rupee_RadioBtn, administrationFee_percent_RadioBtn, mContext);
                    administrationFeeRupee_layout.setVisibility(View.GONE);
                    administrationFee_percent_layout.setVisibility(View.VISIBLE);
                    administration_fee_calculatorimage.setVisibility(View.GONE);
                    calculateAdministationFeePercentValues();
                }
                break;

            case R.id.insuranceCover_rg:
                if (checkedId == R.id.insuranceCover_rupee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(insuranceCover_rupee_RadioBtn, insuranceCover_percent_RadioBtn, mContext);
                    insuranceCoverRupee_layout.setVisibility(View.VISIBLE);
                    insuranceCover_percent_layout.setVisibility(View.GONE);
                    insuranceCover_calculatorimage.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.insuranceCover_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(insuranceCover_rupee_RadioBtn, insuranceCover_percent_RadioBtn, mContext);
                    insuranceCoverRupee_layout.setVisibility(View.GONE);
                    insuranceCover_percent_layout.setVisibility(View.VISIBLE);
                    insuranceCover_calculatorimage.setVisibility(View.GONE);
                    calculateInsuranceCoverPercentValues();
                }
                break;

        }
    }

    @Override
    public void updateLoanValues(String value, String key, int fragmentID) {

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
               /* case R.id.purchase_value_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "purchaseValue", fragmentID);
                    break;
                case R.id.downPayement_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "downPayement", fragmentID);
                    break;*/

                case R.id.finaceValue_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "finaceValue", fragmentID);
                   // finaceValue_lyt.setError(null);
                    calculateprocessingFeePercentValues();
                    calculateLegalFeePercentValues();
                    calculateAdministationFeePercentValues();
                    calculateInsuranceCoverPercentValues();
                    break;

                case R.id.tenure_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "tenure", fragmentID);
                    tenure_lyt.setErrorEnabled(false);
                    tenure_lyt.setError(null);
                    break;

                case R.id.rateOfInterest_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "rateOfInterest", fragmentID);
                    rateOfInterest_lyt.setErrorEnabled(false);
                    rateOfInterest_lyt.setError(null);
                    break;

                case R.id.emi_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "emi", fragmentID);
                    emi_lyt.setErrorEnabled(false);
                    emi_lyt.setError(null);
                    break;
                case R.id.residualValue_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "residualValue", fragmentID);
                    break;
                case R.id.edt_processingFeePercent_view_id:
                    calculateprocessingFeePercentValues();
                    break;
                case R.id.edt_legal_percent_view_id:
                    calculateLegalFeePercentValues();
                    break;
                case R.id.edt_administrationFee_percent_view_id:
                    calculateAdministationFeePercentValues();
                    break;
                case R.id.edt_insuranceCover_percent_view_id:
                    calculateInsuranceCoverPercentValues();
                    break;
            }
        }
    }
    private class GenericTextWatchers implements TextWatcher {
        CurrencyGhostView currencyGhostView;

        public GenericTextWatchers(CurrencyGhostView editText) {
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

            switch (currencyGhostView.getId()) {
                case R.id.purchase_value_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "purchaseValue", fragmentID);
                    break;

                case R.id.downPayement_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "downPayement", fragmentID);
                    break;

                case R.id.finaceValue_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "finaceValue", fragmentID);
                    //   finaceValue_lyt.setError(null);
                    break;

                case R.id.edt_processingFeeRupee_view_id:
                    loanComparisonInterface.updateLoanValues(s.toString(), "processingFee", fragmentID);
                    break;

                case R.id.edt_legalRupee_view_id:
                    loanComparisonInterface.updateLoanValues(s.toString(), "legalFee", fragmentID);
                    break;
                case R.id.edt_administrationFeeRupee_view_id:
                    loanComparisonInterface.updateLoanValues(s.toString(), "administrationFee", fragmentID);
                    break;
                case R.id.edt_insuranceCoverRupee_view_id:
                    loanComparisonInterface.updateLoanValues(s.toString(), "insuranceCoverFee", fragmentID);
                    break;
            }
        }
    }

    private void calculateprocessingFeePercentValues() {
        if (processingFee_rg.getCheckedRadioButtonId() == R.id.processingFee_percent_RadioBtn) {
            if (checkOutstandingFinanceValueNotEmpty()) {
                if (edt_processingFeePercent_view_id.getText().toString() != null && !(edt_processingFeePercent_view_id.getText().toString().equals(""))) {
                    processingFeePercent_layout.setErrorEnabled(false);
                    processingFeePercent_layout.setError(null);
                    //updateInterface.sendValue(s.toString(), 5);
                    Float amount = ((Float.parseFloat(edt_processingFeePercent_view_id.getText().toString())) / 100) * Float.parseFloat(UtileKit.getStringwithoutCurreny(finaceValue_edt.getText().toString()));
                    loanComparisonInterface.updateLoanValues(amount+"", "processingFee", fragmentID);

                } else{
                    loanComparisonInterface.updateLoanValues(0+"", "processingFee", fragmentID);
                }
            }
        }
    }

    private void calculateLegalFeePercentValues() {
        if (legal_rg.getCheckedRadioButtonId() == R.id.legal_percent_RadioBtn) {
            if (checkOutstandingFinanceValueNotEmpty()) {
                if (edt_legal_percent_view_id.getText().toString() != null && !(edt_legal_percent_view_id.getText().toString().equals(""))) {
                    legal_percent_layout.setErrorEnabled(false);
                    legal_percent_layout.setError(null);
                    //updateInterface.sendValue(s.toString(), 5);
                    Float amount = ((Float.parseFloat(edt_legal_percent_view_id.getText().toString())) / 100) * Float.parseFloat(UtileKit.getStringwithoutCurreny(finaceValue_edt.getText().toString()));
                    loanComparisonInterface.updateLoanValues(amount+"", "legalFee", fragmentID);

                } else{
                    loanComparisonInterface.updateLoanValues(0+"", "legalFee", fragmentID);

                }
            }
        }
    }

    private void calculateAdministationFeePercentValues() {
        if (administrationFee_rg.getCheckedRadioButtonId() == R.id.administrationFee_percent_RadioBtn) {
            if (checkOutstandingFinanceValueNotEmpty()) {
                if (edt_administrationFee_percent_view_id.getText().toString() != null && !(edt_administrationFee_percent_view_id.getText().toString().equals(""))) {
                    administrationFee_percent_layout.setErrorEnabled(false);
                    administrationFee_percent_layout.setError(null);
                    //updateInterface.sendValue(s.toString(), 5);
                    Float amount = ((Float.parseFloat(edt_administrationFee_percent_view_id.getText().toString())) / 100) * Float.parseFloat(UtileKit.getStringwithoutCurreny(finaceValue_edt.getText().toString()));
                    loanComparisonInterface.updateLoanValues(amount+"", "administrationFee", fragmentID);

                } else{
                    loanComparisonInterface.updateLoanValues(0+"", "administrationFee", fragmentID);

                }
            }
        }
    }

    private void calculateInsuranceCoverPercentValues() {
        if (insuranceCover_rg.getCheckedRadioButtonId() ==R.id.insuranceCover_percent_RadioBtn) {
            if (checkOutstandingFinanceValueNotEmpty()) {
                if (edt_insuranceCover_percent_view_id.getText().toString() != null && !(edt_insuranceCover_percent_view_id.getText().toString().equals(""))) {
                    insuranceCover_percent_layout.setErrorEnabled(false);
                    insuranceCover_percent_layout.setError(null);
                    //updateInterface.sendValue(s.toString(), 5);
                    Float amount = ((Float.parseFloat(edt_insuranceCover_percent_view_id.getText().toString())) / 100) * Float.parseFloat(UtileKit.getStringwithoutCurreny(finaceValue_edt.getText().toString()));
                    loanComparisonInterface.updateLoanValues(amount+"", "insuranceCoverFee", fragmentID);

                } else{
                    loanComparisonInterface.updateLoanValues(0+"", "insuranceCoverFee", fragmentID);
                }
            }
        }
    }

    private boolean checkOutstandingFinanceValueNotEmpty() {
        boolean isNotEmpty = true;
        if (finaceValue_edt.getText().toString() == null || finaceValue_edt.getText().toString().equals("0") || finaceValue_edt.getText().toString().equals("")) {
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


    public void setErrorInLoanFragment(int index) {
        switch (index) {
            case 0:
                setError(((TextInputLayout) (finaceValue_edt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(finaceValue_edt);
                Log.i("spcheck", "setError NewLoan: is called method 1");
                break;
            case 1:
                setError(rateOfInterest_lyt);
                getFocus(rateOfInterest_edt);
                break;

            case 2:
                setError(tenure_lyt);
                getFocus(tenure_edt);
                break;

            case 3:
                setError(emi_lyt);
                getFocus(emi_edt);
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
}

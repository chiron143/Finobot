package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.interfaces.ActivityMethodsInterface;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models.EmiModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuyFragment extends BaseFragment implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.propertyPrice_calculatorImgView)
    CustomCalenderImageView propertyPrice_calculatorImgView;

    @BindView(R.id.section80C_calculaterImgView)
    CustomCalenderImageView section80C_calculaterImgView;

    @BindView(R.id.section24c_calculaterImgView1)
    CustomCalenderImageView section24c_calculaterImgView1;

    @BindView(R.id.down_payement_calculaterImgView)
    CustomCalenderImageView down_payement_calculaterImgView;

    @BindView(R.id.loan_amount_calculaterImgView)
    CustomCalenderImageView loan_amount_calculaterImgView;

    @BindView(R.id.propertyPrice_edt)
    CurrencyGhostView propertyPrice_edt;

    @BindView(R.id.maintenanceYearly_lyt)
    CurrencyGhostView maintenanceYearly_lyt;

    @BindView(R.id.maintenanceMonthly_lyt)
    CurrencyGhostView maintenanceMonthly_lyt;

    @BindView(R.id.utilitiesMonthly_lyt)
    CurrencyGhostView utilitiesMonthly_lyt;

    @BindView(R.id.utilitiesAnnually_lyt)
    CurrencyGhostView utilitiesAnnually_lyt;


    @BindView(R.id.insuranceMonthly_lyt)
    CurrencyGhostView insuranceMonthly_lyt;

    @BindView(R.id.insuranceAnnually_lyt)
    CurrencyGhostView insuranceAnnually_lyt;


    @BindView(R.id.down_payement_edt)
    CurrencyGhostView down_payement_edt;

    @BindView(R.id.loan_amount_edt)
    CurrencyGhostView loan_amount_edt;

    @BindView(R.id.section80C_edt)
    CurrencyGhostView section80C_edt;

    @BindView(R.id.section24c_edt)
    CurrencyGhostView section24c_edt;

    @BindView(R.id.status_spnr)
    Spinner status_spnr;

    @BindView(R.id.propertyUsage_spnr)
    Spinner propertyUsage_spnr;

    @BindView(R.id.yearsToPossesion_lyt)
    TextInputLayout yearsToPossesion_lyt;

    @BindView(R.id.loanRequired_rg)
    RadioGroup loanRequired_rg;

    @BindView(R.id.taxCredits_rg)
    RadioGroup taxCredits_rg;

    @BindView(R.id.taxCredits_yes_RadioBtn)
    RadioButton taxCredits_yes_RadioBtn;

    @BindView(R.id.taxCredits_no_RadioBtn)
    RadioButton taxCredits_no_RadioBtn;

    @BindView(R.id.loanRequired_yes_RadioBtn)
    RadioButton loanRequired_yes_RadioBtn;

    @BindView(R.id.loanRequired_no_RadioBtn)
    RadioButton loanRequired_no_RadioBtn;


    @BindView(R.id.loan_tenure_layout)
    TextInputLayout loan_tenure_layout;

    @BindView(R.id.interest_rate_layout)
    TextInputLayout interest_rate_layout;


    @BindView(R.id.down_payement_lyt)
    LinearLayout down_payement_lyt;

    @BindView(R.id.loan_amount_lyt)
    LinearLayout loan_amount_lyt;

    @BindView(R.id.mortgage_lyt)
    LinearLayout mortgage_lyt;

    @BindView(R.id.mortgage_tv)
    CustomTextView mortgage_tv;

    @BindView(R.id.section80C_lyt)
    LinearLayout section80C_lyt;

    @BindView(R.id.section24c_lyt)
    LinearLayout section24c_lyt;

    @BindView(R.id.propertyUsage_tv)
    CustomTextView propertyUsage_tv;

    @BindView(R.id.propertyAppreciation_calculatorimage)
    CustomCalenderImageView propertyAppreciation_calculatorimage;


    @BindView(R.id.realEstateTaxes_calculatorimage)
    CustomCalenderImageView realEstateTaxes_calculatorimage;

    @BindView(R.id.propertyAppreciation_rg)
    RadioGroup propertyAppreciation_rg;

    @BindView(R.id.propertyAppreciation_rupeee_RadioBtn)
    RadioButton propertyAppreciation_rupeee_RadioBtn;

    @BindView(R.id.propertyAppreciation_percent_RadioBtn)
    RadioButton propertyAppreciation_percent_RadioBtn;

    @BindView(R.id.edt_propertyAppreciation)
    CurrencyGhostView edt_propertyAppreciation;

    @BindView(R.id.propertyAppreciationPercent_layout)
    TextInputLayout propertyAppreciationPercent_layout;

    @BindView(R.id.propertyAppreciationRupee_layout)
    TextInputLayout propertyAppreciationRupee_layout;

    @BindView(R.id.propertyAppreciationPercent_edt)
    PercentageEditText propertyAppreciationPercent_edt;

    @BindView(R.id.realEstateTaxes_rg)
    RadioGroup realEstateTaxes_rg;

    @BindView(R.id.realEstateTaxes_rupeee_RadioBtn)
    RadioButton realEstateTaxes_rupeee_RadioBtn;

    @BindView(R.id.realEstateTaxes_percent_RadioBtn)
    RadioButton realEstateTaxes_percent_RadioBtn;

    @BindView(R.id.edt_realEstateTaxes)
    CurrencyGhostView edt_realEstateTaxes;

    @BindView(R.id.realEstateTaxesPercent_layout)
    TextInputLayout realEstateTaxesPercent_layout;

    @BindView(R.id.realEstateTaxesPercent_edt)
    EditText realEstateTaxesPercent_edt;

    @BindView(R.id.realEstateTaxesRupee_layout)
    TextInputLayout realEstateTaxesRupee_layout;

    @BindView(R.id.tax_credits)
    RelativeLayout tax_credits;

    @BindView(R.id.mortgageeMonthly_lyt)
    CurrencyGhostView mortgageeMonthly_lyt;

    @BindView(R.id.mortgageAnnually_lyt)
    CurrencyGhostView mortgageAnnually_lyt;

    @BindView(R.id.dont_know_label)
    TextView dont_know_label;

    @BindView(R.id.loan_tenure_edt_id)
    EditText loan_tenure_edt_id;

    @BindView(R.id.interest_rate_id)
    PercentageEditText interest_rate_id;

    @BindView(R.id.intrestforegone_layout)
    TextInputLayout intrestforegone_layout;

    @BindView(R.id.intrestforegone_edt)
    EditText intrestforegone_edt;

    @BindView(R.id.yearsToPossesion_edt)
    EditText yearsToPossesion_edt;

    public static final String PARENT_CLASS_SOURCE = "com.gp89developers.example.MainActivity";
    public static final String TITLE = "";
    View nameEditview;

    HouseBuyVsRentActivity activity;
    ActivityMethodsInterface updateInActivity;

    public String TAG="spcheck";

    View view;
    GhostViewTextWatchers maintenanceMonthly_TV;
    GhostViewTextWatchers maintenanceYearly_TV;
    GhostViewTextWatchers utilitiesMonthly_TV;
    GhostViewTextWatchers utilitiesAnnually_TV;
    GhostViewTextWatchers insuranceAnnually_TV;
    GhostViewTextWatchers insuranceMonthly_TV;
    GhostViewTextWatchers mortgageeMonthly_TV;
    GhostViewTextWatchers mortgageAnnually_TV;

    String[] statusSpinner, propertyUsage;
    ArrayAdapter statusSpinnerAdapter, propertyUsageSpinnerAdapter;
    public Context mContext;
   // public boolean isInitialized=false;

    public HashMap<String, String> loanRequiredEmiFields;//has details of loan amount,loan tenure,Interest rate fields
    private GhostViewTextWatchers propertyPrice_edt_TV;
    private GhostViewTextWatchers loan_amount_edt_TV;
    private GenericTextWatcher loan_tenure_edt_TV;
    private GenericTextWatcher interest_rate_TV;
    private GhostViewTextWatchers edt_propertyAppreciation_TV;
    private GenericTextWatcher propertyAppreciationPercent_edt_TV;
    private GhostViewTextWatchers edt_realEstateTaxes_TV;
    private GenericTextWatcher realEstateTaxesPercent_edt_TV,yearsToPossesion_edt_TV;
    private GhostViewTextWatchers section80C_edt_TV,section24c_edt_TV;
    private GhostViewTextWatchers down_payement_edt_TV;

    public BuyFragment() {

    }

    private EmiModel emiModel;

    public static BuyFragment newInstance() {

        Bundle args = new Bundle();
        BuyFragment fragment = new BuyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState==null) {
            view = inflater.inflate(R.layout.fragment_buy, container, false);
        }
        ButterKnife.bind(this, view);
        mContext = getContext();

        return view;
    }

    @Override
    public void onViewCreated(View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            updateInActivity=(ActivityMethodsInterface) getActivity();
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        //if(isInitialized==false)
            initiliazeViews();
    }

    private void initiliazeViews() {
        Log.i(TAG, "initiliazeViews: ");
        try {
            activity = (HouseBuyVsRentActivity) getActivity();
        }catch (ClassCastException e){e.printStackTrace();}


        statusSpinner = new String[]{"Ready To Move", "Under Construction"};
        propertyUsage = new String[]{"Self-Occupied", "Non-Self Occupied"};

        dont_know_label.setOnClickListener(this);

        loanRequiredEmiFields = new HashMap<>();

        statusSpinnerAdapter = new ArrayAdapter(mContext, R.layout.sinmple_text_view, statusSpinner);
        propertyUsageSpinnerAdapter = new ArrayAdapter(mContext, R.layout.sinmple_text_view, propertyUsage);

        /*if ((activity.isResultShown)&&(activity.buyValues != null)) {
           String value=activity.buyValues.get("real_estate_tax");
            if(!UtileKit.validateObjectValues(value)){
                value=edt_realEstateTaxes.getEditText().getText().toString();
                Log.i("spcheck", "initiliazeViews: Real estate tax :value="+value);
                activity.buyValues.put("real_estate_tax",value);
            }
        }*/

        propertyPrice_edt.setTextHint("Property Price");
        propertyPrice_edt.setfullHintTxt(getString(R.string.hint_buyvsrent_propertyprice));
        //appreciationRupee_lyt.setTextHint("per year in ₹");
        //mortgage_edt.setTextHint("Mortgage Payement(Annually)");
        //realEstateTaxes_edt.setTextHint("Real Estate Taxes");
        maintenanceMonthly_lyt.setTextHint("Monthly");
        maintenanceMonthly_lyt.setfullHintTxt(getString(R.string.hint_buyvsrent_maintenanceandrepairsmonth));

        maintenanceYearly_lyt.setTextHint("Yearly");
        maintenanceYearly_lyt.setfullHintTxt(getString(R.string.hint_buyvsrent_maintenanceandrepairsyear));

        utilitiesMonthly_lyt.setTextHint("Monthly");
        utilitiesMonthly_lyt.setfullHintTxt(getString(R.string.hint_buyvsrent_utilitiesmonth));

        utilitiesAnnually_lyt.setTextHint("Yearly");
        utilitiesAnnually_lyt.setfullHintTxt(getString(R.string.hint_buyvsrent_utilitiesyear));

        insuranceMonthly_lyt.setTextHint("Monthly");
        insuranceMonthly_lyt.setfullHintTxt(getString(R.string.hint_buyvsrent_insurancemonth));

        insuranceAnnually_lyt.setTextHint("Yearly");
        insuranceAnnually_lyt.setfullHintTxt(getString(R.string.hint_buyvsrent_insuranceyear));

        mortgageeMonthly_lyt.setTextHint("Monthly");
        mortgageeMonthly_lyt.setfullHintTxt(getString(R.string.hint_buyvsrent_mortgagepaymentmonth));

        mortgageAnnually_lyt.setTextHint("Yearly");
        mortgageAnnually_lyt.setfullHintTxt(getString(R.string.hint_buyvsrent_mortgagepaymentyear));

        down_payement_edt.setTextHint("Down payment");
        down_payement_edt.setfullHintTxt(getString(R.string.hint_buyvsrent_downpayment));

        loan_amount_edt.setTextHint("Loan amount");
        loan_amount_edt.setfullHintTxt(getString(R.string.hint_buyvsrent_loanamount));

        section80C_edt.setTextHint("Section 80C - On Principal Amount");
        section80C_edt.setfullHintTxt(getString(R.string.hint_buyvsrent_section80c));

        section24c_edt.setTextHint("Section 24 - On Interest Amount");
        section24c_edt.setfullHintTxt(getString(R.string.hint_buyvsrent_section24));
      /*  edt_propertyAppreciation.setTextHint("Expected PropertyAppreciation");
        edt_realEstateTaxes.setTextHint("Real Estate Taxes");*/
        edt_realEstateTaxes.setfullHintTxt(getString(R.string.hint_buyvsrent_Realestatetaxes));

        status_spnr.setAdapter(statusSpinnerAdapter);
        propertyUsage_spnr.setAdapter(propertyUsageSpinnerAdapter);

        status_spnr.setOnItemSelectedListener(this);
        propertyUsage_spnr.setOnItemSelectedListener(this);

        loanRequired_rg.setOnCheckedChangeListener(this);
        taxCredits_rg.setOnCheckedChangeListener(this);
        propertyAppreciation_rg.setOnCheckedChangeListener(this);
        realEstateTaxes_rg.setOnCheckedChangeListener(this);

        setCheckedValueInRadioButton();
        propertyPrice_edt_TV =new GhostViewTextWatchers(propertyPrice_edt);
        propertyPrice_edt.setEditTextId(R.id.propertyPrice_Id);
        propertyPrice_edt.getEditText().addTextChangedListener(propertyPrice_edt_TV);

        loan_amount_edt_TV=new GhostViewTextWatchers(loan_amount_edt);
        loan_amount_edt.getEditText().addTextChangedListener(loan_amount_edt_TV);
        loan_amount_edt.setEditTextId(R.id.loan_amount_edt_Id);

        loan_tenure_edt_TV=new GenericTextWatcher(loan_tenure_edt_id);
        loan_tenure_edt_id.addTextChangedListener(loan_tenure_edt_TV);
        loan_tenure_edt_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    if(loan_tenure_edt_id.getText().length()!=0){
                        loan_tenure_layout.setErrorEnabled(false);
                        loan_tenure_layout.setError(null);
                    }else {
                        loan_tenure_layout.setError(getString(R.string.hint_buyvsrent_loantenure));
                        loan_tenure_layout.setErrorTextAppearance(R.style.errorHintEdit);
                    }
                }
            }
        });

        interest_rate_TV=new GenericTextWatcher(interest_rate_id);
        interest_rate_id.addTextChangedListener(interest_rate_TV);
        interest_rate_id.setHintText(getString(R.string.hint_buyvsrent_loaninterestrate),((TextInputLayout)(interest_rate_id.getParent()).getParent()));

        edt_propertyAppreciation_TV=new GhostViewTextWatchers(edt_propertyAppreciation);
        edt_propertyAppreciation.setEditTextId(R.id.edt_propertyAppreciationId);
        edt_propertyAppreciation.getEditText().addTextChangedListener(edt_propertyAppreciation_TV);

        propertyAppreciationPercent_edt_TV=new GenericTextWatcher(propertyAppreciationPercent_edt);
        propertyAppreciationPercent_edt.addTextChangedListener(propertyAppreciationPercent_edt_TV);
        propertyAppreciationPercent_edt.setHintText(getString(R.string.hint_buyvsrent_expectedpropertyappreciation), ((TextInputLayout)
                (propertyAppreciationPercent_edt.getParent()).getParent()));

        edt_realEstateTaxes_TV=new GhostViewTextWatchers(edt_realEstateTaxes);
        edt_realEstateTaxes.setEditTextId(R.id.edt_realEstateTaxesbtn_Id);
        edt_realEstateTaxes.getEditText().addTextChangedListener(edt_realEstateTaxes_TV);

        realEstateTaxesPercent_edt_TV=new GenericTextWatcher(realEstateTaxesPercent_edt);
        realEstateTaxesPercent_edt.addTextChangedListener(realEstateTaxesPercent_edt_TV);
        // intrestforegone_edt.addTextChangedListener(new GenericTextWatcher(intrestforegone_edt));
        yearsToPossesion_edt_TV=new GenericTextWatcher(yearsToPossesion_edt);
        yearsToPossesion_edt.addTextChangedListener(yearsToPossesion_edt_TV);

        section80C_edt_TV=new GhostViewTextWatchers(section80C_edt);
        section24c_edt_TV=new GhostViewTextWatchers(section24c_edt);
        section80C_edt.setEditTextId(R.id.section80C_edtId);
        section24c_edt.setEditTextId(R.id.section24c_edtId);
        section80C_edt.getEditText().addTextChangedListener(section80C_edt_TV);
        section24c_edt.getEditText().addTextChangedListener(section24c_edt_TV);

        maintenanceMonthly_TV=new GhostViewTextWatchers(maintenanceMonthly_lyt);
        maintenanceYearly_TV=new GhostViewTextWatchers(maintenanceYearly_lyt);
        utilitiesMonthly_TV=new GhostViewTextWatchers(utilitiesMonthly_lyt);
        utilitiesAnnually_TV=new GhostViewTextWatchers(utilitiesAnnually_lyt);
        insuranceAnnually_TV=new GhostViewTextWatchers(insuranceAnnually_lyt);
        insuranceMonthly_TV=new GhostViewTextWatchers(insuranceMonthly_lyt);
        mortgageAnnually_TV= new GhostViewTextWatchers(mortgageAnnually_lyt);
        mortgageeMonthly_TV= new GhostViewTextWatchers(mortgageeMonthly_lyt);


        maintenanceMonthly_lyt.setEditTextId(R.id.maintenanceMonthly_lytId);
        maintenanceYearly_lyt.setEditTextId(R.id.maintenanceYearly_lytId);
        utilitiesMonthly_lyt.setEditTextId(R.id.utilitiesMonthly_lytId);
        utilitiesAnnually_lyt.setEditTextId(R.id.utilitiesAnnually_lytId);
        insuranceAnnually_lyt.setEditTextId(R.id.insuranceAnnually_lytId);
        insuranceMonthly_lyt.setEditTextId(R.id.insuranceMonthly_lytId);
        mortgageeMonthly_lyt.setEditTextId(R.id.mortgageeMonthly_lytId);
        mortgageAnnually_lyt.setEditTextId(R.id.mortgageAnnually_lytId);

        maintenanceMonthly_lyt.getEditText().addTextChangedListener(maintenanceMonthly_TV);
        maintenanceYearly_lyt.getEditText().addTextChangedListener(maintenanceYearly_TV);
        utilitiesMonthly_lyt.getEditText().addTextChangedListener(utilitiesMonthly_TV);
        utilitiesAnnually_lyt.getEditText().addTextChangedListener(utilitiesAnnually_TV);
        insuranceAnnually_lyt.getEditText().addTextChangedListener(insuranceAnnually_TV);
        insuranceMonthly_lyt.getEditText().addTextChangedListener(insuranceMonthly_TV);
        mortgageeMonthly_lyt.getEditText().addTextChangedListener(mortgageeMonthly_TV);
        mortgageAnnually_lyt.getEditText().addTextChangedListener(mortgageAnnually_TV);

        down_payement_edt_TV=new GhostViewTextWatchers(down_payement_edt);
        down_payement_edt.setEditTextId(R.id.down_payement_edt);
        down_payement_edt.getEditText().addTextChangedListener(down_payement_edt_TV);
        propertyUsage_spnr.setSelection(0);
        status_spnr.setSelection(0);

        propertyPrice_calculatorImgView.setOnClickListener(this);
        section80C_calculaterImgView.setOnClickListener(this);
        section24c_calculaterImgView1.setOnClickListener(this);
        down_payement_calculaterImgView.setOnClickListener(this);
        loan_amount_calculaterImgView.setOnClickListener(this);
        propertyAppreciation_calculatorimage.setOnClickListener(this);
        realEstateTaxes_calculatorimage.setOnClickListener(this);
 /*        if ((activity.buyValues != null)) {
            if (!activity.buyValues.isEmpty()&& activity.buyValues.size()>5) setAllValues();
        }
*/

    }

    private void setAllValues() {
        String value;
        //activity.buyValues.get("loan_req"),
        value=activity.buyValues.get("property_price");
        if(UtileKit.validateObjectValues(value))
        propertyPrice_edt.getEditText().setText(value);

        //activity.buyValues.get("status"),activity.buyValues.get("year_to_posses"),
        value=activity.buyValues.get("prop_appre");
        propertyAppreciationPercent_edt.setText(value);

        value=activity.buyValues.get("real_estate_tax");
        edt_realEstateTaxes.getEditText().setText(value);

        value=activity.buyValues.get("main_repair_annu");
        maintenanceYearly_lyt.getEditText().setText(value);

        value=activity.buyValues.get("util_annu");
        utilitiesAnnually_lyt.getEditText().setText(value);

        value=activity.buyValues.get("insurance");
        insuranceAnnually_lyt.getEditText().setText(value);

        //buyValues.get("loan_req");

        value=activity.buyValues.get("down_pay");

        down_payement_edt.getEditText().setText(value);

        value=activity.buyValues.get("loan_amt");

        loan_amount_edt.getEditText().setText(value);


        value=activity.buyValues.get("loan_tenure");
        loan_tenure_edt_id.setText(value);

        value=activity.buyValues.get("loan_int_rate");
        interest_rate_id.setText(value);

        value=activity.buyValues.get("mortage_pay");
        mortgageAnnually_lyt.getEditText().setText(value);

        value=activity.buyValues.get("tax_80c");
        section80C_edt.getEditText().setText(value);

        value=activity.buyValues.get("tax_24");
        section24c_edt.getEditText().setText(value);

        //activity.buyValues.get("int_foregone_down_pay")
    }

    private void setCheckedValueInRadioButton() {
        UtileKit.getSwitchYesBtnView(loanRequired_yes_RadioBtn, loanRequired_no_RadioBtn, mContext);
        updateInActivity.updateValuesFromBuyFragment("loan_req","Yes");
        UtileKit.getSwitchYesBtnView(taxCredits_yes_RadioBtn, taxCredits_no_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnView(propertyAppreciation_rupeee_RadioBtn, propertyAppreciation_percent_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnView(realEstateTaxes_rupeee_RadioBtn, realEstateTaxes_percent_RadioBtn, mContext);
    }

    @Override
    public void onItemSelected(AdapterView<?> spinner, View view, int i, long l) {
        String selecteditem;
        switch (spinner.getId()) {
            case R.id.status_spnr:
                selecteditem = (String) spinner.getSelectedItem();
                if (selecteditem.equalsIgnoreCase("Ready To Move")) {
                    yearsToPossesion_lyt.setVisibility(View.GONE);
                    updateInActivity.updateValuesFromBuyFragment("status","Ready To Move");
                    updateInActivity.updateValuesFromBuyFragment("year_to_posses","0");
                } else if (selecteditem.equalsIgnoreCase("Under Construction")) {
                    yearsToPossesion_lyt.setVisibility(View.VISIBLE);
                    updateInActivity.updateValuesFromBuyFragment("status","Under Construction");

                }


                break;

            case R.id.propertyUsage_spnr:

                selecteditem = (String) spinner.getSelectedItem();
                if (selecteditem.equalsIgnoreCase("Self-Occupied")) {
                     updateInActivity.updateValuesFromBuyFragment("loan_prop_usage","Self Occupied");

                } else if (selecteditem.equalsIgnoreCase("Non-Self Occupied")) {
                    updateInActivity.updateValuesFromBuyFragment("loan_prop_usage","Non Self Occupied");
                }

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
            case R.id.loanRequired_rg:

                if (checkedId == R.id.loanRequired_yes_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(loanRequired_yes_RadioBtn, loanRequired_no_RadioBtn, mContext);

                    interest_rate_layout.setVisibility(View.VISIBLE);
                    loan_tenure_layout.setVisibility(View.VISIBLE);
                    loan_amount_lyt.setVisibility(View.VISIBLE);
                    down_payement_lyt.setVisibility(View.VISIBLE);
                    mortgage_lyt.setVisibility(View.VISIBLE);
                    mortgage_tv.setVisibility(View.VISIBLE);
                    tax_credits.setVisibility(View.VISIBLE);
                    dont_know_label.setVisibility(View.VISIBLE);
                    taxCreditsShowAllFields();
                    updateInActivity.updateValuesFromBuyFragment("loan_req","Yes");

                } else if (checkedId == R.id.loanRequired_no_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(loanRequired_yes_RadioBtn, loanRequired_no_RadioBtn, mContext);

                    interest_rate_layout.setVisibility(View.GONE);
                    loan_tenure_layout.setVisibility(View.GONE);
                    loan_amount_lyt.setVisibility(View.GONE);
                    down_payement_lyt.setVisibility(View.GONE);
                    mortgage_lyt.setVisibility(View.GONE);
                    mortgage_tv.setVisibility(View.GONE);
                    tax_credits.setVisibility(View.GONE);
                    dont_know_label.setVisibility(View.GONE);
                    taxCreditsHideAllFields();
                    updateInActivity.updateValuesFromBuyFragment("loan_req","No");
                }

                break;
            case R.id.taxCredits_rg:
                if (checkedId == R.id.taxCredits_yes_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(taxCredits_yes_RadioBtn, taxCredits_no_RadioBtn, mContext);
                    taxCreditsShowAllFields();

                } else if (checkedId == R.id.taxCredits_no_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(taxCredits_yes_RadioBtn, taxCredits_no_RadioBtn, mContext);
                    taxCreditsHideAllFields();

                }
                break;

            case R.id.propertyAppreciation_rg:
                if (checkedId == R.id.propertyAppreciation_rupeee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(propertyAppreciation_rupeee_RadioBtn, propertyAppreciation_percent_RadioBtn, mContext);

                    propertyAppreciationRupee_layout.setVisibility(View.VISIBLE);
                    propertyAppreciation_calculatorimage.setVisibility(View.VISIBLE);
                    propertyAppreciationPercent_layout.setVisibility(View.GONE);

                } else if (checkedId == R.id.propertyAppreciation_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(propertyAppreciation_rupeee_RadioBtn, propertyAppreciation_percent_RadioBtn, mContext);
                    propertyAppreciationRupee_layout.setVisibility(View.GONE);
                    propertyAppreciation_calculatorimage.setVisibility(View.GONE);
                    propertyAppreciationPercent_layout.setVisibility(View.VISIBLE);
                   /* updateInActivity.updateValuesFromBuyFragment("prop_appre",
                            propertyAppreciationPercent_edt.getText().toString());*/
                }
                break;

            case R.id.realEstateTaxes_rg:
                if (checkedId == R.id.realEstateTaxes_rupeee_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(realEstateTaxes_rupeee_RadioBtn, realEstateTaxes_percent_RadioBtn, mContext);
                    realEstateTaxesRupee_layout.setVisibility(View.VISIBLE);
                    realEstateTaxes_calculatorimage.setVisibility(View.VISIBLE);
                    realEstateTaxesPercent_layout.setVisibility(View.GONE);
                    updateInActivity.updateValuesFromBuyFragment("real_estate_tax",
                            edt_realEstateTaxes.getEditText().getText().toString());

                } else if (checkedId == R.id.realEstateTaxes_percent_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(realEstateTaxes_rupeee_RadioBtn, realEstateTaxes_percent_RadioBtn, mContext);
                    realEstateTaxesRupee_layout.setVisibility(View.GONE);
                    realEstateTaxes_calculatorimage.setVisibility(View.GONE);
                    realEstateTaxesPercent_layout.setVisibility(View.VISIBLE);
                    updateInActivity.updateValuesFromBuyFragment("real_estate_tax",
                            realEstateTaxesPercent_edt.getText().toString());
                }
                break;


        }

    }

    private void taxCreditsHideAllFields() {
        propertyUsage_spnr.setVisibility(View.GONE);
        propertyUsage_tv.setVisibility(View.GONE);
        section24c_lyt.setVisibility(View.GONE);
        section80C_lyt.setVisibility(View.GONE);
    }

    private void taxCreditsShowAllFields() {

        propertyUsage_spnr.setVisibility(View.VISIBLE);
        propertyUsage_tv.setVisibility(View.VISIBLE);
        section24c_lyt.setVisibility(View.VISIBLE);
        section80C_lyt.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.dont_know_label:
                checkForNullValuesInEmi();
                break;

            case R.id.propertyPrice_calculatorImgView:
                showCalDialog(propertyPrice_edt);
                break;

            case R.id.section80C_calculaterImgView:
                showCalDialog(section80C_edt);
                break;

            case R.id.down_payement_calculaterImgView:
                showCalDialog(down_payement_edt);
                break;

            case R.id.loan_amount_calculaterImgView:
                showCalDialog(loan_amount_edt);
                break;

            case R.id.propertyAppreciation_calculatorimage:
                showCalDialog(edt_propertyAppreciation);
                break;

            case R.id.realEstateTaxes_calculatorimage:
                showCalDialog(edt_realEstateTaxes);
                break;

            case R.id.section24c_calculaterImgView1:
                showCalDialog(section24c_edt);
                break;

        }
    }

    private void callGetEMIservice() {
        //loanRequiredEmiFields
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<EmiModel> call = obj.getEMIservice(
                UtileKit.getStringwithoutCurreny(loan_amount_edt.getEditText().getText().toString()),
                UtileKit.getStringwithoutCurreny(loan_tenure_edt_id.getText().toString()),
                UtileKit.getStringwithoutCurreny(interest_rate_id.getText().toString()));
        call.enqueue(new Callback<EmiModel>() {
            @Override
            public void onResponse(Call<EmiModel> call, Response<EmiModel> response) {
                UtileKit.dismisssSpinnerDialog();
                emiModel=response.body();
                if(emiModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)){
                    if(null!=emiModel.getData()){
                        float result=Float.parseFloat(emiModel.getData().getResult().toString());
                        mortgageAnnually_lyt.getEditText().setText(Math.round(result)+"");
                    }

                }
            }

            @Override
            public void onFailure(Call<EmiModel> call, Throwable t) {

                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }

    private void checkForNullValuesInEmi() {
        if (UtileKit.validateObjectValuesAndCheckZero(loan_amount_edt.getEditText().getText().toString())) {
            if (UtileKit.validateObjectValuesAndCheckZero(loan_tenure_edt_id.getText().toString())) {
                if (UtileKit.validateObjectValuesAndCheckZero(interest_rate_id.getText().toString())) {
                    callGetEMIservice();
                } else {
                    //setErrorForEmiSerivice(2);
                    setErrorBuyFragment(103);
                }
            } else {
                //setErrorForEmiSerivice(1);
                setErrorBuyFragment(102);
            }
        }else {
            //setErrorForEmiSerivice(0);
            setErrorBuyFragment(101);
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

                case R.id.propertyPrice_edt:
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    addLoanRequiredEmiFields(value, "property_price");
                    updateInActivity.updateValuesFromBuyFragment("property_price",value);
                    if(UtileKit.validateObjectValues(down_payement_edt.getEditText().getText().toString())&&
                            UtileKit.validateObjectValues(loan_amount_edt.getEditText().getText().toString()))
                    {
                        Double propertyPrice=Double.parseDouble(UtileKit.getStringwithoutCurreny(value));
                        Double total=Double.parseDouble(UtileKit.getStringwithoutCurreny(down_payement_edt.getEditText().getText().toString()))+
                                Double.parseDouble(UtileKit.getStringwithoutCurreny(loan_amount_edt.getEditText().getText().toString()));
                        Log.i(TAG, "afterTextChanged: Property price ="+propertyPrice+"  total="+total);
                        if(propertyPrice.compareTo(total)>0||propertyPrice.compareTo(total)<0) {
                            /*setErrorWithMessage(((TextInputLayout) (loan_amount_edt.getEditText().getParent()).getParent())
                                    ,"Ensure Property price = Down payment + Loan Amount ");*/
                        }else {
                            ((TextInputLayout) (loan_amount_edt.getEditText().getParent()).getParent()).setErrorEnabled(false);
                            ((TextInputLayout) (loan_amount_edt.getEditText().getParent()).getParent()).setError(null);
                        }
                    }
                    break;

                case R.id.maintenanceMonthly_lyt :
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    if(UtileKit.validateObjectValues(value)) {
                        updateGhostViewValue(maintenanceYearly_lyt, Float.parseFloat(value) * 12, maintenanceYearly_TV);
                        updateInActivity.updateValuesFromBuyFragment("main_repair_annu", (Float.parseFloat(value) * 12)+"");
                    }
                   /* ((TextInputLayout) (maintenanceYearly_lyt.getEditText().getParent()).getParent()).setErrorEnabled(false);
                    ((TextInputLayout) (maintenanceYearly_lyt.getEditText().getParent()).getParent()).setError(null);*/

                    break;

                case R.id.maintenanceYearly_lyt :
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                     if(UtileKit.validateObjectValues(value)) updateGhostViewValue(maintenanceMonthly_lyt,Float.parseFloat(value)/12,maintenanceMonthly_TV);
                    updateInActivity.updateValuesFromBuyFragment("main_repair_annu",value);
                   /* ((TextInputLayout) (maintenanceMonthly_lyt.getEditText().getParent()).getParent()).setErrorEnabled(false);
                    ((TextInputLayout) (maintenanceMonthly_lyt.getEditText().getParent()).getParent()).setError(null);*/

                    break;

                case R.id.utilitiesMonthly_lyt :
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                     if(UtileKit.validateObjectValues(value)) {
                         updateGhostViewValue(utilitiesAnnually_lyt, Float.parseFloat(value) * 12, utilitiesAnnually_TV);
                         updateInActivity.updateValuesFromBuyFragment("util_annu",(Float.parseFloat(value) * 12)+"");
                     }
                    /*((TextInputLayout) (utilitiesAnnually_lyt.getEditText().getParent()).getParent()).setErrorEnabled(false);
                    ((TextInputLayout) (utilitiesAnnually_lyt.getEditText().getParent()).getParent()).setError(null);*/

                    break;

                case R.id.utilitiesAnnually_lyt :
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                     if(UtileKit.validateObjectValues(value)) updateGhostViewValue(utilitiesMonthly_lyt,Float.parseFloat(value)/12,utilitiesMonthly_TV);
                    updateInActivity.updateValuesFromBuyFragment("util_annu",value);
                    /*((TextInputLayout) (utilitiesMonthly_lyt.getEditText().getParent()).getParent()).setErrorEnabled(false);
                    ((TextInputLayout) (utilitiesMonthly_lyt.getEditText().getParent()).getParent()).setError(null);*/
                    break;

                case R.id.insuranceAnnually_lyt :
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                     if(UtileKit.validateObjectValues(value)) updateGhostViewValue(insuranceMonthly_lyt,Float.parseFloat(value)/12,insuranceMonthly_TV);
                    updateInActivity.updateValuesFromBuyFragment("insurance",value);
                    /*((TextInputLayout) (insuranceMonthly_lyt.getEditText().getParent()).getParent()).setErrorEnabled(false);
                    ((TextInputLayout) (insuranceMonthly_lyt.getEditText().getParent()).getParent()).setError(null);*/
                    break;

                case R.id.insuranceMonthly_lyt :
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                     if(UtileKit.validateObjectValues(value)) {
                        updateGhostViewValue(insuranceAnnually_lyt, Float.parseFloat(value) * 12, insuranceAnnually_TV);
                         updateInActivity.updateValuesFromBuyFragment("insurance",(Float.parseFloat(value) * 12)+"");
                     }
                   /* ((TextInputLayout) (insuranceAnnually_lyt.getEditText().getParent()).getParent()).setErrorEnabled(false);
                    ((TextInputLayout) (insuranceAnnually_lyt.getEditText().getParent()).getParent()).setError(null);*/
                    break;

                case R.id.mortgageeMonthly_lyt :
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                     if(UtileKit.validateObjectValues(value)){
                         updateGhostViewValue(mortgageAnnually_lyt,Float.parseFloat(value) * 12,mortgageAnnually_TV);
                         updateInActivity.updateValuesFromBuyFragment("mortage_pay",(Float.parseFloat(value) * 12)+"");
                     }
                    /*((TextInputLayout) (mortgageAnnually_lyt.getEditText().getParent()).getParent()).setErrorEnabled(false);
                    ((TextInputLayout) (mortgageAnnually_lyt.getEditText().getParent()).getParent()).setError(null);*/
                    break;

                case R.id.mortgageAnnually_lyt :
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                     if(UtileKit.validateObjectValues(value)) updateGhostViewValue(mortgageeMonthly_lyt,Float.parseFloat(value)/12,mortgageeMonthly_TV);
                      updateInActivity.updateValuesFromBuyFragment("mortage_pay",value);
                   /* ((TextInputLayout) (mortgageeMonthly_lyt.getEditText().getParent()).getParent()).setErrorEnabled(false);
                    ((TextInputLayout) (mortgageeMonthly_lyt.getEditText().getParent()).getParent()).setError(null);*/
                    break;

                case R.id.edt_propertyAppreciation:
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    updateInActivity.updateValuesFromBuyFragment("prop_appre",value);
                    break;

                case R.id.edt_realEstateTaxes:
                    if(realEstateTaxes_rg.getCheckedRadioButtonId()==R.id.realEstateTaxes_rupeee_RadioBtn) {
                        value = UtileKit.getStringwithoutCurreny(s.toString());
                        updateInActivity.updateValuesFromBuyFragment("real_estate_tax", value);
                    }
                    break;

                case R.id.loan_amount_edt:
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    addLoanRequiredEmiFields(value, "loan_amount");
                    updateInActivity.updateValuesFromBuyFragment("loan_amt",value);
                    if(UtileKit.validateObjectValues(propertyPrice_edt.getEditText().getText().toString())){
                        Double propertyPrice=Double.parseDouble(UtileKit.getStringwithoutCurreny(propertyPrice_edt.getEditText().getText().toString()));
                        Double total=Double.parseDouble(UtileKit.getStringwithoutCurreny(down_payement_edt.getEditText().getText().toString()))+Double.parseDouble(value);
                        //Log.i(TAG, "afterTextChanged: Property price ="+propertyPrice+"  total="+total);
                        if(propertyPrice.compareTo(total)>0||propertyPrice.compareTo(total)<0) {
                            setErrorWithMessage(((TextInputLayout) (loan_amount_edt.getEditText().getParent()).getParent())
                                    ,"Ensure Property price = Down payment + Loan Amount ");
                        }
                    }

                    break;

                case R.id.down_payement_edt:
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    updateInActivity.updateValuesFromBuyFragment("down_pay",value);
                    if(UtileKit.validateObjectValues(propertyPrice_edt.getEditText().getText().toString())){
                        Double loanValue=Double.parseDouble(UtileKit.getStringwithoutCurreny(propertyPrice_edt.getEditText().getText().toString()))
                                -Double.parseDouble(value);
                        if(loanValue>=0){  loan_amount_edt.getEditText().setText(loanValue.longValue()+"");}
                        else {
                            setErrorWithMessage(((TextInputLayout) (down_payement_edt.getEditText().getParent()).getParent())
                            ,"Down payment should be less than Property price");
                        }
                    }
                    break;
                case R.id.section80C_edt:
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    updateInActivity.updateValuesFromBuyFragment("tax_80c",value);
                    break;
                case R.id.section24c_edt:
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    updateInActivity.updateValuesFromBuyFragment("tax_24",value);
                    break;

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

                case R.id.yearsToPossesion_edt:
                    updateInActivity.updateValuesFromBuyFragment("year_to_posses",s.toString());
                    break;

                case R.id.loan_tenure_edt_id:
                    //loanComparisonInterface.updateLoanValues(s.toString(), "purchaseValue", fragmentID);
                    addLoanRequiredEmiFields(s.toString(), "loan_tenure");
                    updateInActivity.updateValuesFromBuyFragment("loan_tenure",s.toString());
                    break;
                case R.id.interest_rate_id:
                    //loanComparisonInterface.updateLoanValues(s.toString(), "downPayement", fragmentID);
                    addLoanRequiredEmiFields(s.toString(), "interest_rate");
                    updateInActivity.updateValuesFromBuyFragment("loan_int_rate",s.toString());
                    break;

                case R.id.propertyAppreciationPercent_edt:
                    updateInActivity.updateValuesFromBuyFragment("prop_appre",s.toString());
                    break;

                case R.id.realEstateTaxesPercent_edt:
                    if(realEstateTaxes_rg.getCheckedRadioButtonId()==R.id.realEstateTaxes_percent_RadioBtn) {
                        updateInActivity.updateValuesFromBuyFragment("real_estate_tax", s.toString());
                    }
                    break;

                case R.id.intrestforegone_edt:
                   // updateInActivity.updateValuesFromBuyFragment("int_foregone_down_pay",s.toString());
                    break;


               /* case R.id.finaceValue_edt:
                    loanComparisonInterface.updateLoanValues(s.toString(), "finaceValue", fragmentID);
                    // finaceValue_lyt.setError(null);
                    calculateprocessingFeePercentValues();
                    calculateLegalFeePercentValues();
                    calculateAdministationFeePercentValues();
                    calculateInsuranceCoverPercentValues();
                    break;

                */
            }
        }
    }

    public void setErrorForEmiSerivice(int index) {
        switch (index) {
            case 0:
                setError(((TextInputLayout) (loan_amount_edt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(loan_amount_edt);
                //Log.i("spcheck", "setError NewLoan: is called method 1");
                break;
            case 1:
                setError(loan_tenure_layout);
                getFocus(loan_tenure_edt_id);
                break;
            case 2:
                setError(interest_rate_layout);
                getFocus(interest_rate_id);
                break;
        }
    }

    public void setErrorBuyFragment(int index) {
        switch (index) {
            case 0: //propertyPrice
                setError(((TextInputLayout) (propertyPrice_edt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(propertyPrice_edt);
                //Log.i("spcheck", "setError NewLoan: is called method 1");
                break;
            case 1://"prop_appre",
               /* if (propertyAppreciation_rg.getCheckedRadioButtonId() == R.id.propertyAppreciation_rupeee_RadioBtn) {
                    setError(((TextInputLayout) (edt_propertyAppreciation.getEditText().getParent()).getParent()));
                    getFocusCurrencyGhost(edt_propertyAppreciation);
                } else if (propertyAppreciation_rg.getCheckedRadioButtonId() == R.id.propertyAppreciation_percent_RadioBtn) {
                    setError(propertyAppreciationPercent_layout);
                    getFocus(propertyAppreciationPercent_edt);
                }*/
                setError(propertyAppreciationPercent_layout);
                getFocus(propertyAppreciationPercent_edt);
                break;


            case 2://"real_estate_tax",
                if (realEstateTaxes_rg.getCheckedRadioButtonId() == R.id.realEstateTaxes_rupeee_RadioBtn) {
                    setError(((TextInputLayout) (edt_realEstateTaxes.getEditText().getParent()).getParent()));
                    getFocusCurrencyGhost(edt_realEstateTaxes);
                } else if (realEstateTaxes_rg.getCheckedRadioButtonId() == R.id.realEstateTaxes_percent_RadioBtn) {
                    setError(realEstateTaxesPercent_layout);
                    getFocus(realEstateTaxesPercent_edt);
                }

                break;

            case 3://"main_repair_annu",
                setError(((TextInputLayout) (maintenanceYearly_lyt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(maintenanceYearly_lyt);


                break;

            case 4://"util_annu",
                setError(((TextInputLayout) (utilitiesAnnually_lyt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(utilitiesAnnually_lyt);

                break;

            case 5://"insurance",
                setError(((TextInputLayout) (insuranceAnnually_lyt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(insuranceAnnually_lyt);
                break;

            case 7://"int_foregone_down_pay" //
               /* setError(intrestforegone_layout);
                getFocus(intrestforegone_edt);*/
                break;

            case 8:
                setError(yearsToPossesion_lyt);
                getFocus(yearsToPossesion_edt);
                break;

            case 100://"down_pay",
                setError(((TextInputLayout) (down_payement_edt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(down_payement_edt);
                break;

            case 101:// "loan_amt"," //0
                setError(((TextInputLayout) (loan_amount_edt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(loan_amount_edt);
                break;

            case 102://loan_tenure",
                setError(loan_tenure_layout);
                getFocus(loan_tenure_edt_id);
                break;

            case 103://"loan_int_rate",
                setError(interest_rate_layout);
                getFocus(interest_rate_id);
                break;

            case 104://"mortage_pay",
                setError(((TextInputLayout) (mortgageAnnually_lyt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(mortgageAnnually_lyt);

                break;

            case 105://"loan_prop_usage"
                break;


        }
    }

    private void addLoanRequiredEmiFields(String value, String key) {
        loanRequiredEmiFields.put(key, value);
    }

    public void setError(TextInputLayout ti) {
        ti.setError("Please fill the Missing value");
    }

    public void setErrorWithMessage(TextInputLayout ti,String message) {
        ti.setError(message);
    }

    private void getFocus(EditText editText) {
        try {
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }catch (Exception e){e.printStackTrace();}
    }

    private void getFocusCurrencyGhost(CurrencyGhostView currencyGhostView) {
        try {
            currencyGhostView.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(currencyGhostView, InputMethodManager.SHOW_IMPLICIT);
        }catch(Exception e){
            e.printStackTrace();
        }
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

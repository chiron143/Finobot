package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.ui;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by dinesh on 13/09/17.
 */

public class CarBuyVsLeaseFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.fab)
    FloatingActionButton mdoneFloatingBtn;

    @BindView(R.id.relative_left_arrow)
     RelativeLayout mleftRelativeLayout;

    @BindView(R.id.relative_center_home)
     RelativeLayout mcenterRelativeLayout;

    @BindView(R.id.relative_right_arrow)
     RelativeLayout mRightRelativeLayout;

    @BindView(R.id.opertunitycostId)
    PercentageEditText oppurtunityCost_edt;

    @BindView(R.id.plannedoccupation_Edt_id)
    NumberEditText plannedoccupation_Edt;

    @BindView(R.id.mainRepairEditId)
    CurrencyGhostView mainRepairEditId;

    @BindView(R.id.fualReapirExpEditId)
    CurrencyGhostView fualReapirExpEdit;

    @BindView(R.id.insuranceEditId)
    CurrencyGhostView insuranceEdit;


    private GhostViewTextWatchers plannedoccupation_TV;
    private GhostViewTextWatchers mainRepairEdit_TV;
    private GhostViewTextWatchers fualReapirExpEdit_TV;
    private GhostViewTextWatchers insuranceEdit_TV;

    GenericTextWatcher Opportunity_Edit_TW, plannedOccupation_TW;


    @BindView(R.id.opertunitycostLayId)
    TextInputLayout opertunitycostLayId;

    @BindView(R.id.cityType_spnr)
    Spinner cityType_spnr;

    @BindView(R.id.taxSlab_spnr)
    Spinner taxSlab_spnr;

    Context mContext;
    ArrayAdapter taxSlabAdapter, cityTypeAdapter;

    String[] taxSlabArray = new String[]{"30%", "20%", "10%"}, cityTypeArray = new String[]{"Metro", "Non-Metro"};

    HashMap<String,String> buyValues,rentValues,homeValues;

    OnActivityBackPressedListener  mCallBackListener;
    ArrayList<String> buyMandatoryKey=new ArrayList<>(Arrays.asList(
            "property_price","prop_appre","real_estate_tax","main_repair_annu","util_annu",
            "insurance","loan_req","status","year_to_posses"
    ));

    ArrayList<String> buyLoanYesMandatoryKey=new ArrayList<>(Arrays.asList("down_pay","loan_amt","loan_tenure",
            "loan_int_rate","mortage_pay","loan_prop_usage"));

    private String loan_req="Yes";

    ArrayList<String> rentMandatoryKey=new ArrayList<>(Arrays.asList("current_rent", "rent_sec_dep",
            "rent_esc", "rent_util", "rent_insurance"
    ));

    ArrayList<String> homeMandatoryKey=new ArrayList<>(Arrays.asList("gross_salary","oppur_cost","planned_occupation",
            "city_type","tax_slab"
    ));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        homeValues=new HashMap<>();
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
        View view=inflater.inflate(R.layout.fragment_car_vs_lease,container,false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        mCallBackListener.setActionBarTitle("DeciPro - Car - Buy vs Lease");

//        plannedOccupation_TW=new GenericTextWatcher(plannedoccupation_Edt);
//        oppurtunityCost_edt.addTextChangedListener(plannedOccupation_TW);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews();
            mdoneFloatingBtn.setOnClickListener(this);
            mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);


    }
    private void initializeViews() {

        plannedoccupation_Edt.setHint(getString(R.string.plannedoccuation));
        mainRepairEditId.setTextHint(getString(R.string.mainrepair));
        fualReapirExpEdit.setTextHint(getString(R.string.fualrunngexp));
        insuranceEdit.setTextHint(getString(R.string.insurance));
        plannedoccupation_Edt.setHintText(getString(R.string.hint_carbuyvslease_plannedoccupation),((TextInputLayout)(plannedoccupation_Edt.getParent()).getParent()));
        mainRepairEditId.setfullHintTxt(getString(R.string.hint_carbuyvslease_mainrepair));
        fualReapirExpEdit.setfullHintTxt(getString(R.string.hint_carbuyvslease_fuelrunningexpense));
        insuranceEdit.setfullHintTxt(getString(R.string.hint_carbuyvslease_insurance));


        taxSlabAdapter = new ArrayAdapter<String>(mContext,
                R.layout.sinmple_text_view, taxSlabArray);

        cityTypeAdapter = new ArrayAdapter<String>(mContext,
                R.layout.sinmple_text_view, cityTypeArray);

        taxSlab_spnr.setAdapter(taxSlabAdapter);
        cityType_spnr.setAdapter(cityTypeAdapter);
        cityType_spnr.setOnItemSelectedListener(this);
        taxSlab_spnr.setOnItemSelectedListener(this);


//        plannedoccupation_TV=new GhostViewTextWatchers(plannedoccupation_Edt);
        mainRepairEdit_TV=new GhostViewTextWatchers(mainRepairEditId);
        fualReapirExpEdit_TV=new GhostViewTextWatchers(fualReapirExpEdit);
        insuranceEdit_TV=new GhostViewTextWatchers(insuranceEdit);
//        plannedoccupation_Edt.getEditText().setId(R.id.plannedoccupation_id);
//        plannedoccupation_Edt.getEditText().addTextChangedListener(plannedoccupation_TV);
        GenericTextWatcher  plannedoccupation_TW=new GenericTextWatcher(plannedoccupation_Edt);
        plannedoccupation_Edt.addTextChangedListener(plannedoccupation_TW);
        mainRepairEditId.getEditText().setId(R.id.mainRepair_id);
        mainRepairEditId.getEditText().addTextChangedListener(mainRepairEdit_TV);
        fualReapirExpEdit.getEditText().setId(R.id.fualReapirExp_id);
        fualReapirExpEdit.getEditText().addTextChangedListener(fualReapirExpEdit_TV);
        insuranceEdit.getEditText().setId(R.id.insurance_id);
        insuranceEdit.getEditText().addTextChangedListener(insuranceEdit_TV);
        Opportunity_Edit_TW=new GenericTextWatcher(oppurtunityCost_edt);
//        oppurtunityCost_edt.setId(R.id.oppurtCost_id);
        oppurtunityCost_edt.addTextChangedListener(Opportunity_Edit_TW);

        oppurtunityCost_edt.setHintText(getString(R.string.hint_carbuyvslease_opportunitycost), ((TextInputLayout)
                (oppurtunityCost_edt.getParent()).getParent()));
//
//        plannedoccupation_Edt.getEditText().addTextChangedListener(new GhostViewTextWatchers(plannedoccupation_Edt));
//        plannedoccupation_Edt.setTextHint(getString(R.string.plannedoccuation));
//        mainRepairEditId.getEditText().addTextChangedListener(new GhostViewTextWatchers(mainRepairEditId));
//        mainRepairEditId.setTextHint(getString(R.string.plannedoccuation));
//                fualReapirExpEdit.getEditText().addTextChangedListener(new GhostViewTextWatchers(grossSalary_edt));
//        plannedoccupation_Edt.setTextHint(getString(R.string.plannedoccuation));
//        insuranceEdit
//        oppurtunityCost_edt.addTextChangedListener(new GenericTextWatcher(oppurtunityCost_edt));
//        plannedOccupation_edt.addTextChangedListener(new GenericTextWatcher(plannedOccupation_edt));
//
//        grossSalary_calculaterImgView.setOnClickListener(this);

        cityType_spnr.setSelection(0);
        taxSlab_spnr.setSelection(0);

       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodsInterface.showViewPager();

            }
        });*/



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (resultCode == CalculatorAct.REQUEST_RESULT_SUCCESSFUL) {
            String result = data.getStringExtra(CalculatorAct.RESULT);
            ((CurrencyGhostView) nameEditview).setText(result);
            ((CurrencyGhostView) nameEditview).getEditText().setBackgroundResource(R.drawable.edittextbackgrounggreen);
        }*/
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fab:

                Boolean isCarValidOppCost =mCarVsLeaseValidation(homeValues);

                if(isCarValidOppCost)
                {
                    addFragmenttoStack( CarBuyVsLeaseTabFragment.newInstance(homeValues));
                }

//                addFragmenttoStack( CarBuyVsLeaseTabFragment.newInstance(homeValues));
                break;
            case R.id.relative_left_arrow:
            {

                Log.i("spcheck", " relative_left_arrow is clicked"  );
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
//                Log.i(TAG, " relative_center_home is clicked"  );
             startHomeActivity();
            }

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> spinner, View view, int i, long l) {
        String selecteditem;
        switch (spinner.getId()) {
            case R.id.cityType_spnr:
                selecteditem = (String) spinner.getSelectedItem();
                if (selecteditem.equalsIgnoreCase("Metro")) {
                    updateCarVsLeaseValues("city_type", "Metro");

                } else if (selecteditem.equalsIgnoreCase("Non-Metro")) {
                    updateCarVsLeaseValues("city_type", "Non-Metro");
                }


                break;

            case R.id.taxSlab_spnr:

                selecteditem = (String) spinner.getSelectedItem();
                if (selecteditem.equalsIgnoreCase("30%")) {
                    updateCarVsLeaseValues("tax_slab", "30");
                } else if (selecteditem.equalsIgnoreCase("20%")) {
                    updateCarVsLeaseValues("tax_slab", "20");
                } else if (selecteditem.equalsIgnoreCase("10%")) {
                    updateCarVsLeaseValues("tax_slab", "10");
                } else if (selecteditem.equalsIgnoreCase("0%")) {
                    updateCarVsLeaseValues("tax_slab", "0");
                }

                break;
        }
    }

    private void updateCarVsLeaseValues(String key, String value) {
        homeValues.put(key,value);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    private class GhostViewTextWatchers implements TextWatcher {
//        CurrencyGhostView currencyGhostView;
//
//        public GhostViewTextWatchers(CurrencyGhostView editText) {
//            this.currencyGhostView = editText;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setErrorEnabled(false);
//            ((TextInputLayout) (currencyGhostView.getEditText().getParent()).getParent()).setError(null);
//            String value = null;
//            switch (currencyGhostView.getId()) {
//
//                case grossSalary_edt:
//                    value = UtileKit.getStringwithoutCurreny(s.toString());
//                    updateCarVsLeaseValues("gross_salary", value);
//                    break;
//
//                /*case R.id.downPayement_edt:
//                    loanComparisonInterface.updateLoanValues(s.toString(), "downPayement", fragmentID);
//                    break;*/
//                //loan_tenure_edt_id
//
//            }
//        }
//    }
public Boolean mCarVsLeaseValidation(HashMap<String, String> homeValues) {

    if(UtileKit.validateObjectValuesAndCheckZero(homeValues.get("oppur_cost")))
    {
        if(UtileKit.validateObjectValuesAndCheckZero(homeValues.get("planned_occupation")))
        {
            return true;
        }
        else {

            plannedoccupation_Edt.setError(getString(R.string.errorplanned_occupation));
            getFocusCurrency(plannedoccupation_Edt);
            return false;

        }
    }
    else {
        opertunitycostLayId.setError(getString(R.string.erroroppur_cost));
        getFocusCurrency(oppurtunityCost_edt);
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

                case R.id.opertunitycostId:
                    updateCarVsLeaseValues("oppur_cost", s.toString());
                    break;
                case R.id.plannedoccupation_Edt_id:
                    updateCarVsLeaseValues("planned_occupation",s.toString());

                    break;


            }
        }
    }

    public void setErrorHomeFragment(int index) {
        switch (index) {
            case 0: //"gross_salary",
//                setError(((TextInputLayout) (grossSalary_edt.getEditText().getParent()).getParent()));
//                getFocusCurrencyGhost(grossSalary_edt);
                break;
            case 1://,"oppur_cost",
//                setError(oppurtunityCost_layout);
//                getFocus(oppurtunityCost_edt);
                break;
            case 2://"planned_occupation"
//                setError(plannedOccupation_lyt);
//                getFocus(plannedOccupation_edt);
                break;

        }
    }
//    private void showCalDialog(View view) {
//        nameEditview = view;
//        String calculaterValue = ((CurrencyGhostView) nameEditview).getText().toString();
//        Intent calculatorIntent = new Intent(getActivity(), CalculatorAct.class);
//        calculatorIntent.putExtra(CalculatorAct.TITLE_ACTIVITY, TITLE);
//        calculatorIntent.putExtra(CalculatorAct.PARENT_ACTIVITY, PARENT_CLASS_SOURCE);
//        calculatorIntent.putExtra(CalculatorAct.VALUE, calculaterValue);
//        startActivityForResult(calculatorIntent, CalculatorAct.REQUEST_RESULT_SUCCESSFUL);
//    }
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
                case R.id.oppurtunityCost_edt:
                    updateCarVsLeaseValues("oppur_cost", s.toString());
                    break;

                case R.id.plannedOccupation_edt:
                    updateCarVsLeaseValues("planned_occupation", s.toString());
                    break;
                case R.id.propertyPrice_edt:
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    updateCarVsLeaseValues("property_price",value );

                    break;

                case R.id.mainRepairEditId:
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    updateCarVsLeaseValues("main_repair",value );

                    break;


                case R.id.fualReapirExpEditId:

                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    updateCarVsLeaseValues("fuel_run_exp",value );

                    break;

                case R.id.insuranceEditId:
                    value=UtileKit.getStringwithoutCurreny(s.toString());
                    updateCarVsLeaseValues("insurance",value );
                break;


            }
        }
    }
}

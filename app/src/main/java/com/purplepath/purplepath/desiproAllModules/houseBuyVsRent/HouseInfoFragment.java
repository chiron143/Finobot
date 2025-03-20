package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent;


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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.interfaces.ActivityMethodsInterface;
import com.purplepath.purplepath.fragments.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Pratheep.S
 */
public class HouseInfoFragment extends BaseFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @BindView(R.id.grossSalary_calculaterImgView)
    CustomCalenderImageView grossSalary_calculaterImgView;

    @BindView(R.id.cityType_tv)
    TextView cityType_tv;

    @BindView(R.id.grossSalary_edt)
    CurrencyGhostView grossSalary_edt;

    @BindView(R.id.cityType_spnr)
    Spinner cityType_spnr;

    @BindView(R.id.taxSlab_spnr)
    Spinner taxSlab_spnr;

    @BindView(R.id.oppurtunityCost_layout)
    TextInputLayout oppurtunityCost_layout;

    @BindView(R.id.oppurtunityCost_edt)
    PercentageEditText oppurtunityCost_edt;

    @BindView(R.id.plannedOccupation_lyt)
    TextInputLayout plannedOccupation_lyt;

    @BindView(R.id.plannedOccupation_edt)
    NumberEditText plannedOccupation_edt;

    String[] taxSlabArray = new String[]{"30%", "20%", "10%"}, cityTypeArray = new String[]{"Metro", "Non-Metro"};

    ActivityMethodsInterface updateInActivity;

    ArrayAdapter taxSlabAdapter, cityTypeAdapter;
    public ActivityMethodsInterface methodsInterface;
    View view;
    Context mContext;

    public HouseInfoFragment() {

    }

    public boolean isInitialized = false;

    public static final String PARENT_CLASS_SOURCE = "com.gp89developers.example.MainActivity";
    public static final String TITLE = "";
    View nameEditview;

    HouseBuyVsRentActivity activity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //methodsInterface.hideFAB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            view = inflater.inflate(R.layout.fragment_house_info, container, false);
        }
        ButterKnife.bind(this, view);
        mContext = getContext();
        try {
            updateInActivity = (ActivityMethodsInterface) getActivity();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        //initializeViews();

        return view;
    }

    @Override
    public void onViewCreated(View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //if(isInitialized==false)
        initializeViews();
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

    private void showCalDialog(View view) {
        nameEditview = view;
        String calculaterValue = ((CurrencyGhostView) nameEditview).getText().toString();
        Intent calculatorIntent = new Intent(getActivity(), CalculatorAct.class);
        calculatorIntent.putExtra(CalculatorAct.TITLE_ACTIVITY, TITLE);
        calculatorIntent.putExtra(CalculatorAct.PARENT_ACTIVITY, PARENT_CLASS_SOURCE);
        calculatorIntent.putExtra(CalculatorAct.VALUE, calculaterValue);
        startActivityForResult(calculatorIntent, CalculatorAct.REQUEST_RESULT_SUCCESSFUL);
    }

    private void initializeViews() {

        grossSalary_edt.setTextHint("Gross salary");
        grossSalary_edt.setfullHintTxt(getString(R.string.hint_buyvsrent_grosssalarye));

        taxSlabAdapter = new ArrayAdapter<String>(mContext,
                R.layout.sinmple_text_view, taxSlabArray);

        cityTypeAdapter = new ArrayAdapter<String>(mContext,
                R.layout.sinmple_text_view, cityTypeArray);

        taxSlab_spnr.setAdapter(taxSlabAdapter);
        cityType_spnr.setAdapter(cityTypeAdapter);
        cityType_spnr.setOnItemSelectedListener(this);
        taxSlab_spnr.setOnItemSelectedListener(this);


        try {
            methodsInterface = (ActivityMethodsInterface) mContext;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        activity = (HouseBuyVsRentActivity) getActivity();
        if ((activity.homeValues != null)) {
            if (!activity.homeValues.isEmpty()) setAllValues();
        }
        isInitialized = true;


        grossSalary_edt.getEditText().addTextChangedListener(new GhostViewTextWatchers(grossSalary_edt));
        oppurtunityCost_edt.addTextChangedListener(new GenericTextWatcher(oppurtunityCost_edt));
        oppurtunityCost_edt.setHintText(getString(R.string.hint_buyvsrent_opportunitycost), ((TextInputLayout)
                (oppurtunityCost_edt.getParent()).getParent()));

        plannedOccupation_edt.addTextChangedListener(new GenericTextWatcher(plannedOccupation_edt));
        plannedOccupation_edt.setHintText(getString(R.string.hint_buyvsrent_plannedoccupation), ((TextInputLayout)
                (plannedOccupation_edt.getParent()).getParent()));

        grossSalary_calculaterImgView.setOnClickListener(this);

        cityType_spnr.setSelection(0);
        taxSlab_spnr.setSelection(0);

       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methodsInterface.showViewPager();

            }
        });*/



    }

    private void setAllValues() {
        String value;
        if (activity.homeValues.get("city_type").equalsIgnoreCase("Metro"))
            cityType_spnr.setSelection(0);
        else
            cityType_spnr.setSelection(1);

        value=activity.homeValues.get("gross_salary");
        grossSalary_edt.getEditText().setText(value);
        value=activity.homeValues.get("oppur_cost");
        oppurtunityCost_edt.setText(value);
        value=activity.homeValues.get("planned_occupation");
        plannedOccupation_edt.setText(value);
    }

    @Override
    public void onItemSelected(AdapterView<?> spinner, View view, int i, long l) {

        String selecteditem;
        switch (spinner.getId()) {
            case R.id.cityType_spnr:
                selecteditem = (String) spinner.getSelectedItem();
                if (selecteditem.equalsIgnoreCase("Metro")) {
                    updateInActivity.updateValuesFromHomeFragment("city_type", "Metro");

                } else if (selecteditem.equalsIgnoreCase("Non-Metro")) {
                    updateInActivity.updateValuesFromHomeFragment("city_type", "Non-Metro");
                }


                break;

            case R.id.taxSlab_spnr:

                selecteditem = (String) spinner.getSelectedItem();
                if (selecteditem.equalsIgnoreCase("30%")) {
                    updateInActivity.updateValuesFromHomeFragment("tax_slab", "30");
                } else if (selecteditem.equalsIgnoreCase("20%")) {
                    updateInActivity.updateValuesFromHomeFragment("tax_slab", "20");
                } else if (selecteditem.equalsIgnoreCase("10%")) {
                    updateInActivity.updateValuesFromHomeFragment("tax_slab", "10");
                } else if (selecteditem.equalsIgnoreCase("0%")) {
                    updateInActivity.updateValuesFromHomeFragment("tax_slab", "0");
                }

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.grossSalary_calculaterImgView:
                showCalDialog(grossSalary_edt);
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
            String value = null;
            switch (currencyGhostView.getId()) {

                case R.id.grossSalary_edt:
                    value = UtileKit.getStringwithoutCurreny(s.toString());
                    updateInActivity.updateValuesFromHomeFragment("gross_salary", value);
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

                case R.id.oppurtunityCost_edt:
                    updateInActivity.updateValuesFromHomeFragment("oppur_cost", s.toString());
                    break;

                case R.id.plannedOccupation_edt:
                    updateInActivity.updateValuesFromHomeFragment("planned_occupation", s.toString());
                    break;

            }
        }
    }

    public void setErrorHomeFragment(int index) {
        switch (index) {
            case 0: //"gross_salary",
                setError(((TextInputLayout) (grossSalary_edt.getEditText().getParent()).getParent()));
                getFocusCurrencyGhost(grossSalary_edt);
                break;
            case 1://,"oppur_cost",
                setError(oppurtunityCost_layout);
                getFocus(oppurtunityCost_edt);
                break;
            case 2://"planned_occupation"
                setError(plannedOccupation_lyt);
                getFocus(plannedOccupation_edt);
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

    public void updateGhostViewValue(CurrencyGhostView view, float value, GhostViewTextWatchers textWatcher) {
        view.getEditText().removeTextChangedListener(textWatcher);
        view.getEditText().setText(((int) value) + "");
        view.getEditText().addTextChangedListener(textWatcher);
    }


}

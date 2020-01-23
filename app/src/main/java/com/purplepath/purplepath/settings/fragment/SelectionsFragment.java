package com.purplepath.purplepath.settings.fragment;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.settings.Interfaces.UpdateOnNavigation;
import com.purplepath.purplepath.settings.UpdateModels.SelectionsUpdateModel;
import com.purplepath.purplepath.settings.models.SelectionsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pratheep.S on 04-01-2017.
 */

public class SelectionsFragment extends BaseFragment implements UpdateOnNavigation, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    private int id[] = {R.id.et_retirement_age, R.id.et_life_expectency, R.id.et_spouse_life_expectancy_age, R.id.et_spouse_retirement_age
            , R.id.et_percentage_of_expense, R.id.et_tax_consideration, R.id.et_duration_consideration, R.id.et_input_details, R.id.et_processing,
            R.id.et_Output_report, R.id.et_debt_ratio};

    private EditText etRetirement_age, et_life_expectency, et_spouse_life_expectancy_age, et_spouse_retirement_age, et_percentage_of_expense,
            et_tax_consideration, et_duration_consideration, et_input_details, et_processing, et_Output_report, et_debt_ratio;

    private EditText editTexts[] = {etRetirement_age, et_life_expectency, et_spouse_life_expectancy_age, et_spouse_retirement_age, et_percentage_of_expense
            , et_tax_consideration, et_duration_consideration, et_input_details, et_processing, et_Output_report, et_debt_ratio};

    private View view;

    private SelectionsModel selectionsModel;

    private boolean isDataLoaded=false;

    private RadioGroup taxConsideration_rg,firstHome_rg;

    private RadioButton taxConsideration_yes_RadioBtn,taxConsideration_no_RadioBtn,firstHome_yes_RadioBtn,firstHome_no_RadioBtn;

    private TextInputLayout til_retirement_age,til_spouse_retirement_age,til_life_expectency,til_spouse_life_expectancy_age;

    private Context mContext;

    private Spinner duration_consideration_spinner,inputdetails_spinner,processing_spinner,outputReport_spinner;

    private String mduration_consideration_spinner, mprocessing_spinner, minputdetails_spinnerm, moutputReport_spinner;

    private  String mTaxConsiderationRadio ="" , mfirstHome_radioString ="" ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_selections, container, false);
        mContext=getContext();
        initializeAllViews();
        callSelectionsWebService();
        return view;
    }


    private void callSelectionsWebService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls callInterfaceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<SelectionsModel> call = callInterfaceObj.callSelectionService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<SelectionsModel>() {
            @Override
            public void onResponse(Call<SelectionsModel> call, Response<SelectionsModel> response) {
                UtileKit.dismisssSpinnerDialog();
                selectionsModel = response.body();
                if(selectionsModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    updateEditText(response.body());
                    isDataLoaded = true;
                    Log.i("spcheck", "getAllFields:Selections response success");
                }else if(selectionsModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESS_OVERRIDE_CODE)){
                    isDataLoaded = true;  //when no data service returns status code 100
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }
            }

            @Override
            public void onFailure(Call<SelectionsModel> call, Throwable t) {
                isDataLoaded = false;
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( getActivity(),t);
            }
        });

    }

    private void updateEditText(SelectionsModel selectionsModel) {

        try{
        if (UtileKit.validateObjectValues(selectionsModel.getData().getUser_per_det_Selec().get(0).getPlanned_retirement_age())) {
            etRetirement_age.setText(selectionsModel.getData().getUser_per_det_Selec().get(0).getPlanned_retirement_age());
            etRetirement_age.setSelection(etRetirement_age.getText().length());
        }
        if (UtileKit.validateObjectValues(selectionsModel.getData().getUser_per_det_Selec().get(0).getLife_expectancy_age())) {
            et_life_expectency.setText(selectionsModel.getData().getUser_per_det_Selec().get(0).getLife_expectancy_age());
            etRetirement_age.setSelection(et_life_expectency.getText().length());
        }
        if (UtileKit.validateObjectValues(selectionsModel.getData().getUser_per_det_Selec().get(0).getSpouse_life_expectancy_age())) {
            et_spouse_life_expectancy_age.setText(selectionsModel.getData().getUser_per_det_Selec().get(0).getSpouse_life_expectancy_age());
            et_spouse_retirement_age.setSelection(et_spouse_retirement_age.getText().length());

        }
        if (UtileKit.validateObjectValues(selectionsModel.getData().getUser_per_det_Selec().get(0).getSpouse_retirement_age())) {
            et_spouse_retirement_age.setText(selectionsModel.getData().getUser_per_det_Selec().get(0).getSpouse_retirement_age());
            et_spouse_retirement_age.setSelection(et_spouse_retirement_age.getText().length());
        }
        if (UtileKit.validateObjectValues(selectionsModel.getData().getUser_per_det_Selec().get(0).getExp_tax_per())) {
            et_percentage_of_expense.setText(selectionsModel.getData().getUser_per_det_Selec().get(0).getExp_tax_per());
            et_percentage_of_expense.setSelection(et_percentage_of_expense.getText().length());
        }
        if (UtileKit.validateObjectValues(selectionsModel.getData().getUser_per_det_Selec().get(0).getTax_cons())) {

                if (selectionsModel.getData().getUser_per_det_Selec().get(0).getTax_cons().equalsIgnoreCase("Yes")) {
                    UtileKit.getSwitchYesBtnView(taxConsideration_yes_RadioBtn,taxConsideration_no_RadioBtn, mContext);
                    Log.i("spcheck", "taxConsideration_rg yes"+" a");
                    mTaxConsiderationRadio = "Yes";

                } else if (selectionsModel.getData().getUser_per_det_Selec().get(0).getTax_cons().equalsIgnoreCase("No")) {
                    UtileKit.getSwitchNoBtnView(taxConsideration_yes_RadioBtn,taxConsideration_no_RadioBtn, mContext);
                    Log.i("spcheck", "taxConsideration_rg no"+" b");
                    mTaxConsiderationRadio = "No";
                }
            }else{
//            UtileKit.getSwitchEmptyBtnView(taxConsideration_yes_RadioBtn,taxConsideration_no_RadioBtn, mContext);

        }


        if (UtileKit.validateObjectValues(selectionsModel.getData().getUser_per_det_Selec().get(0).getDuration())) {

            SetAdapter(selectionsModel.getData().getUser_per_det_Selec().get(0).getDuration(),
                    getResources().getStringArray(R.array.duration_array),duration_consideration_spinner);

        }
        if (UtileKit.validateObjectValues(selectionsModel.getData().getUser_per_det_Selec().get(0).getInput_details())) {

            SetAdapter(selectionsModel.getData().getUser_per_det_Selec().get(0).getInput_details(),
                    getResources().getStringArray(R.array.duration_array),inputdetails_spinner);
        }

        if (UtileKit.validateObjectValues(selectionsModel.getData().getUser_per_det_Selec().get(0).getProcessing())) {

            SetAdapter(selectionsModel.getData().getUser_per_det_Selec().get(0).getProcessing(),
                    getResources().getStringArray(R.array.duration_array),processing_spinner);
        }
        if (UtileKit.validateObjectValues(selectionsModel.getData().getUser_per_det_Selec().get(0).getOutput_report())) {

            SetAdapter(selectionsModel.getData().getUser_per_det_Selec().get(0).getOutput_report(),
                    getResources().getStringArray(R.array.duration_array),outputReport_spinner);
        }

        if (UtileKit.validateObjectValues(selectionsModel.getData().getUser_per_det_Selec().get(0).getDebt_ratio())) {
            et_debt_ratio.setText(selectionsModel.getData().getUser_per_det_Selec().get(0).getDebt_ratio());
            et_debt_ratio.setSelection(et_debt_ratio.getText().length());
        }


            if (UtileKit.validateObjectValues(selectionsModel.getData().getUser_per_det_Selec().get(0).getFirst_home())) {

                if (selectionsModel.getData().getUser_per_det_Selec().get(0).getFirst_home().equalsIgnoreCase("Yes")) {
                    UtileKit.getSwitchYesBtnView(firstHome_yes_RadioBtn,firstHome_no_RadioBtn, mContext);
                    Log.i("spcheck", "getFirst_home yes"+" a");
                    mfirstHome_radioString = "Yes";

                } else if (selectionsModel.getData().getUser_per_det_Selec().get(0).getFirst_home().equalsIgnoreCase("No")) {
                    UtileKit.getSwitchNoBtnView(firstHome_yes_RadioBtn,firstHome_no_RadioBtn, mContext);
                    Log.i("spcheck", "getFirst_home no"+" b");
                    mfirstHome_radioString = "No";
                }
            }
            else{
//                UtileKit.getSwitchEmptyBtnView(taxConsideration_yes_RadioBtn,taxConsideration_no_RadioBtn, mContext);

            }
        isDataLoaded = true;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void SetAdapter(String mStringValue, String[] stringArray, Spinner mSpinner) {
        try {

            for (int i = 0; i < stringArray.length; i++) {
                if (mStringValue.equalsIgnoreCase(stringArray[i])) {
                    mSpinner.setSelection(i);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    private void initializeAllViews() {

        duration_consideration_spinner= view.findViewById(R.id.duration_consideration_spinner);
        inputdetails_spinner= view.findViewById(R.id.inputdetails_spinner);
        processing_spinner= view.findViewById(R.id.processing_spinner);
        outputReport_spinner= view.findViewById(R.id.outputReport_spinner);

        ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(getContext(),
                R.layout.sinmple_text_view,getResources().getStringArray(R.array.duration_array));
        duration_consideration_spinner.setAdapter(spinner_adapter);
        inputdetails_spinner.setAdapter(spinner_adapter);
        processing_spinner.setAdapter(spinner_adapter);
        outputReport_spinner.setAdapter(spinner_adapter);
        duration_consideration_spinner.setOnItemSelectedListener(this);
        inputdetails_spinner.setOnItemSelectedListener(this);
        processing_spinner.setOnItemSelectedListener(this);
        outputReport_spinner.setOnItemSelectedListener(this);

        taxConsideration_rg= view.findViewById(R.id.taxConsideration_rg);
        taxConsideration_yes_RadioBtn= view.findViewById(R.id.taxConsideration_yes_RadioBtn);
        taxConsideration_no_RadioBtn= view.findViewById(R.id.taxConsideration_no_RadioBtn);
//        UtileKit.getSwitchYesBtnView(taxConsideration_yes_RadioBtn, taxConsideration_no_RadioBtn, mContext);
        taxConsideration_rg.setOnCheckedChangeListener(this);

        firstHome_rg= view.findViewById(R.id.firstHome_rg);
        firstHome_yes_RadioBtn= view.findViewById(R.id.firstHome_yes_RadioBtn);
        firstHome_no_RadioBtn= view.findViewById(R.id.firstHome_no_RadioBtn);
//        UtileKit.getSwitchYesBtnView(firstHome_yes_RadioBtn,firstHome_no_RadioBtn, mContext);
        firstHome_rg.setOnCheckedChangeListener(this);

        etRetirement_age = view.findViewById(id[0]);
        et_life_expectency = view.findViewById(id[1]);
        et_spouse_life_expectancy_age = view.findViewById(id[2]);

        et_spouse_retirement_age = view.findViewById(id[3]);

        et_percentage_of_expense = view.findViewById(id[4]);
        et_tax_consideration = view.findViewById(id[5]);

        et_duration_consideration = view.findViewById(id[6]);
        et_input_details = view.findViewById(id[7]);
        et_processing = view.findViewById(id[8]);

        et_Output_report = view.findViewById(id[9]);
        et_debt_ratio = view.findViewById(id[10]);

        etRetirement_age.getText().clear();
        et_life_expectency.getText().clear();
        et_spouse_life_expectancy_age.getText().clear();
        et_spouse_retirement_age.getText().clear();

        til_retirement_age= view.findViewById(R.id.il_retirement_age);
        etRetirement_age.addTextChangedListener(new GenericTextWatcher(etRetirement_age));

        til_spouse_retirement_age= view.findViewById(R.id.il_spouse_retirement_age);
        et_spouse_retirement_age.addTextChangedListener(new GenericTextWatcher(et_spouse_retirement_age));

        til_life_expectency= view.findViewById(R.id.il_life_expectency);
        et_life_expectency.addTextChangedListener(new GenericTextWatcher(et_life_expectency));

        til_spouse_life_expectancy_age= view.findViewById(R.id.il_spouse_life_expectancy_age);
        et_spouse_life_expectancy_age.addTextChangedListener(new GenericTextWatcher(et_spouse_life_expectancy_age));

        try {
            if ( UtileKit.validateObjectValues(UtileKit.getPersistedPurplePathPref(getString(R.string.MartialStatus)))   &&
                    UtileKit.validateObjectValues(UtileKit.getPersistedPurplePathPref(getString(R.string.IsSpouseWorking)))) {
                if ((UtileKit.getPersistedPurplePathPref(getString(R.string.MartialStatus)).toString().equalsIgnoreCase("No"))) {
                    til_spouse_retirement_age.setVisibility(View.GONE);
                    til_spouse_life_expectancy_age.setVisibility(View.GONE);
                } else if ((UtileKit.getPersistedPurplePathPref(getString(R.string.IsSpouseWorking), "yes").toString()).equalsIgnoreCase("No")) {
                    til_spouse_retirement_age.setVisibility(View.GONE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("spcheck", "onResume:Selections ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("spcheck", "onPause: Selections ");
    }

    @Override
    public void updateAllFields() {
        if(isDataLoaded) {
            Log.i("spcheck", "updateAllFields:Selections ");
            updateSelectionsWebservice();
        }

    }

    private void updateSelectionsWebservice() {
        Log.i("spcheck", "updateSelectionsWebservice: ");
        UtileKit.showSpinnerDialog(mContext, false);
            WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);
            Call<SelectionsUpdateModel> call = obj.callSelectionsUpdateService(UtileKit.getPersistedPurplePathPref("user_id"),
                    etRetirement_age.getText().toString(), et_life_expectency.getText().toString(),
                    et_spouse_life_expectancy_age.getText().toString(), et_spouse_retirement_age.getText().toString(),
                    et_percentage_of_expense.getText().toString(),
                  mTaxConsiderationRadio,
                   mduration_consideration_spinner,minputdetails_spinnerm,mprocessing_spinner, moutputReport_spinner,
                    et_debt_ratio.getText().toString(),mfirstHome_radioString);
            call.enqueue(new Callback<SelectionsUpdateModel>() {
                @Override
                public void onResponse(Call<SelectionsUpdateModel> call, Response<SelectionsUpdateModel> response) {
                    UtileKit.dismisssSpinnerDialog();
                    Log.i("spcheck", "onResponse: SelectionsUpdateService update Success");
                }

                @Override
                public void onFailure(Call<SelectionsUpdateModel> call, Throwable t) {
                    UtileKit.dismisssSpinnerDialog();
                    UtileKit.alertRetrofitExceptionDialog( getActivity(),t);
                }
            });
        }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()){
            case R.id.taxConsideration_rg:
                if (checkedId == R.id.taxConsideration_yes_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(taxConsideration_yes_RadioBtn,taxConsideration_no_RadioBtn, mContext);
                    Log.i("spcheck", "taxConsideration_rg yes"+" a");
                    mTaxConsiderationRadio = "Yes";

                } else if (checkedId == R.id.taxConsideration_no_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(taxConsideration_yes_RadioBtn,taxConsideration_no_RadioBtn, mContext);
                    Log.i("spcheck", "taxConsideration_rg no"+" b");
                    mTaxConsiderationRadio = "No";
                }
                break;

            case R.id.firstHome_rg:
                if (checkedId == R.id.firstHome_yes_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(firstHome_yes_RadioBtn,firstHome_no_RadioBtn, mContext);
                    Log.i("spcheck", "firstHome_rg yes"+" a");
                    mfirstHome_radioString = "Yes";
                } else if (checkedId == R.id.firstHome_no_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(firstHome_yes_RadioBtn,firstHome_no_RadioBtn, mContext);
                    Log.i("spcheck", "firstHome_rg no"+" b");
                    mfirstHome_radioString = "No";
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.duration_consideration_spinner:
                mduration_consideration_spinner =  duration_consideration_spinner.getSelectedItem().toString();
                Log.i("spcheck", "onItemSelected  mduration_consideration_spinner"+ mduration_consideration_spinner);
                break;
            case R.id.inputdetails_spinner:
                minputdetails_spinnerm =  inputdetails_spinner.getSelectedItem().toString();
                Log.i("spcheck", "onItemSelected  inputdetails_spinner"+ minputdetails_spinnerm);
                break;
            case R.id.processing_spinner:
                mprocessing_spinner =  processing_spinner.getSelectedItem().toString();
                Log.i("spcheck", "onItemSelected  mprocessing_spinner"+ mprocessing_spinner);
                break;
            case R.id.outputReport_spinner:
                moutputReport_spinner =  outputReport_spinner.getSelectedItem().toString();
                Log.i("spcheck", "onItemSelected  moutputReport_spinner"+ moutputReport_spinner);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    class GenericTextWatcher implements TextWatcher{
        View view;

        GenericTextWatcher(View view){
            this.view=view;

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
                case R.id.et_retirement_age:
                    try{
                    String getPlanned_retirement_age= selectionsModel.getData().getUser_per_det_Selec().get(0).getPlanned_retirement_age();
                    if(getPlanned_retirement_age!= null) {
                        setRetirementAgeErrorMessage(til_retirement_age, etRetirement_age, getPlanned_retirement_age);
                    }
                    }catch(Exception e){
                    e.printStackTrace();
                    }
                    break;
                case  R.id.et_spouse_retirement_age:
                    try{
                    String getSpouse_retirement_age= selectionsModel.getData().getUser_per_det_Selec().get(0).getSpouse_retirement_age();
                    if(getSpouse_retirement_age!= null) {
                        setRetirementAgeErrorMessage(til_spouse_retirement_age, et_spouse_retirement_age, getSpouse_retirement_age);
                    }
                    }catch(Exception e){
                e.printStackTrace();
            }
                        break;
                case R.id.et_life_expectency:
                    try{
                    String getLife_expectancy_age= selectionsModel.getData().getUser_per_det_Selec().get(0).getPlanned_retirement_age();
                    if(getLife_expectancy_age!= null) {
                        setAgeErrorMessage(til_life_expectency, et_life_expectency, getLife_expectancy_age);
                    }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;

                case R.id.et_spouse_life_expectancy_age:
                    try{
                    String getSpouse_life_expectancy_age= selectionsModel.getData().getUser_per_det_Selec().get(0).getSpouse_life_expectancy_age();
                    if(getSpouse_life_expectancy_age!= null) {
                        setAgeErrorMessage(til_spouse_life_expectancy_age, et_spouse_life_expectancy_age, getSpouse_life_expectancy_age);
                    }}catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }

    void setRetirementAgeErrorMessage(TextInputLayout til, EditText et, String age){
        try{
                if (Integer.parseInt(age) > Integer.parseInt(et.getText().toString())) {
                    til.setError("Retirement Age is less than " + age + ", seems incorrect");
                }else if(Integer.parseInt(et.getText().toString())>120){
                    til.setError("Retirement Age is more than 120, seems incorrect");
                }else {
                    til.setErrorEnabled(false);
                    til.setError(null);
                }

        }catch (NumberFormatException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void setAgeErrorMessage(TextInputLayout til, EditText et, String age){
        try{
                if(Integer.parseInt(age) > Integer.parseInt(et.getText().toString())) {
                    til.setError("Life Expectancy Age is more than " + age + ", seems incorrect");
                }
                 else if(Integer.parseInt(et.getText().toString())>120){
                     til.setError("Life Expectancy Age is more than 120, seems incorrect");
                 }else {
                    til.setErrorEnabled(false);
                     til.setError(null);
                 }

        }catch (NumberFormatException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

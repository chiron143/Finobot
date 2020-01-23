package com.purplepath.purplepath.desiproAllModules.timeValueOfMoney;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyBlackGhostview;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.timeValueOfMoney.models.TimeValueOfMoneyModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.validateObjectValues;

/**
 * @Author: Pratheep.S
 */

public class TimeValueOfMoneyFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private final static String TAG = TimeValueOfMoneyFragment.class.getCanonicalName();
    @Bind(R.id.tenure_lyt)
    TextInputLayout tenure_lyt;

    @Bind(R.id.rate_lyt)
    TextInputLayout rate_lyt;

//    @Bind(R.id.present_value_lyt)
//    TextInputLayout present_value_lyt ;

    @Bind(R.id.future_value_lyt)
    TextInputLayout future_value_lyt;

    @Bind(R.id.payement_lyt)
    TextInputLayout payement_lyt;

    @Bind(R.id.tenure_spinner)
    Spinner tenure_spinner;

    @Bind(R.id.paymentType_spinner)
    Spinner paymentType_spinner;

    @Bind(R.id.compoundingPeriod_spinner)
    Spinner compoundingPeriod_spinner;

    @Bind(R.id.payementPeriod_spinner)
    Spinner payementPeriod_spinner;

    @Bind(R.id.presenValue_rg)
    RadioGroup presenValue_rg;

    @Bind(R.id.presenValue_plus_RadioBtn)
    RadioButton presenValue_plus_RadioBtn;

    @Bind(R.id.presenValue_minus_RadioBtn)
    RadioButton presenValue_minus_RadioBtn;

    @Bind(R.id.futureValue_rg)
    RadioGroup futureValue_rg;

    @Bind(R.id.futureValue_plus_RadioBtn)
    RadioButton futureValue_plus_RadioBtn;

    @Bind(R.id.futureValue_minus_RadioBtn)
    RadioButton futureValue_minus_RadioBtn;

    @Bind(R.id.payment_rg)
    RadioGroup payment_rg;

    @Bind(R.id.payment_plus_RadioBtn)
    RadioButton payment_plus_RadioBtn;

    @Bind(R.id.payment_minus_RadioBtn)
    RadioButton payment_minus_RadioBtn;

    @Bind(R.id.tenure_btn)
    TextView tenure_btn;

    @Bind(R.id.rate_btn)
    TextView rate_btn;

    @Bind(R.id.presentValue_btn)
    TextView presentValue_btn;

    @Bind(R.id.futureValue_btn)
    TextView futureValue_btn;

    @Bind(R.id.payment_btn)
    TextView payment_btn;

    @Bind(R.id.tenure_edt)
    EditText tenure_edt;

    @Bind(R.id.rate_edt)
    PercentageEditText rate_edt;

    @Bind(R.id.present_value_edt)
    CurrencyBlackGhostview present_value_edt;

    @Bind(R.id.future_value_edt)
    CurrencyBlackGhostview future_value_edt;

    @Bind(R.id.payement_edt)
    CurrencyBlackGhostview payement_edt;

    private String[] tenure_spinner_array = {"Year(s)", "Half Year(s)", "Quarter(s)", "Month(s)", "Week(s)", "Day(s)"}, paymentType_spinner_array = {"Period Ending", "Period Beginning"},
            period_spinner_array = {"Yearly", "Half Yearly", "Quarterly", "Monthly", "Weekly", "Daily"};

    private ArrayAdapter tenure_spinner_adapter, paymentType_spinner_adapter, payment_period_spinner_adapter,
            compounding_period_spinner_adapter;

    private Context mContext;

    private TimeValueOfMoneyModel timeValueOfMoneyModel;

    private ArrayList<String> allFlagValues = new ArrayList<>(Arrays.asList("tn", "rate", "pv", "fv", "pmt"));

    private Map<Integer, Integer> periodMap = new HashMap<Integer, Integer>();

    private List<String> mandatoryFieldsList=new ArrayList<>();

    private String tenureValue,presentValue,futureValue,payment;

    private OnActivityBackPressedListener mCallBackListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_value_of_money, container, false);
        ButterKnife.bind(this, view);
       // mCallBackListener = (OnActivityBackPressedListener) (mContext);
       // mContext = getContext();
        mCallBackListener.setActionBarTitle("DeciPro - Time Value of Money");

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        initializeAllSpinners();
        initializeHashMap();
        present_value_edt.setTextHint("Present Value");
        future_value_edt.setTextHint("Future Value");
        payement_edt.setTextHint("Payment");
        setAllRadioButtonsToCheckedState();
        setClickAndCheckedListeners();
        return view;
    }

    private void initializeHashMap() {
       periodMap.put(0,1);
       periodMap.put(1,6);
       periodMap.put(2,4);
       periodMap.put(3,12);
       periodMap.put(4,52);
       periodMap.put(5,365);
    }

    private void setClickAndCheckedListeners() {
        tenure_btn.setOnClickListener(this);
        rate_btn.setOnClickListener(this);
        presentValue_btn.setOnClickListener(this);
        futureValue_btn.setOnClickListener(this);
        payment_btn.setOnClickListener(this);

        presenValue_rg.setOnCheckedChangeListener(this);
        futureValue_rg.setOnCheckedChangeListener(this);
        payment_rg.setOnCheckedChangeListener(this);

        tenure_edt.addTextChangedListener(new CommonTextWatcher(tenure_edt));
        tenure_edt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    if(tenure_edt.getText().length()!=0){
                        tenure_lyt.setErrorEnabled(false);
                        tenure_lyt.setError(null);
                    }else {
                        tenure_lyt.setError(getString(R.string.hint_time_tenure));
                        tenure_lyt.setErrorTextAppearance(R.style.errorHintEdit);
                    }
                }
            }
        });
        rate_edt.addTextChangedListener(new CommonTextWatcher(rate_edt));
        rate_edt.setHintText(getString(R.string.hint_time_rateperannum), ((TextInputLayout)(rate_edt.getParent()).getParent()));
        present_value_edt.getEditText().addTextChangedListener(new CommonTextWatcher(present_value_edt.getEditText()));
        present_value_edt.setfullHintTxt(getString(R.string.hint_time_presentvalue));
        future_value_edt.getEditText().addTextChangedListener(new CommonTextWatcher(future_value_edt.getEditText()));
        future_value_edt.setfullHintTxt(getString(R.string.hint_time_futurevalue));
        payement_edt.getEditText().addTextChangedListener(new CommonTextWatcher(payement_edt.getEditText()));
        payement_edt.setfullHintTxt(getString(R.string.hint_time_payment));

    }

    private void callTimeValueMoneyService(String flag, String tenure, String tenure_period, String rate, String presentValue, String futureValue,
        String pmt, String paymentType, String compoundingPeriodPerYear, String payementPeriodPerYear) {

        UtileKit.showSpinnerDialog(mContext,false);

        WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TimeValueOfMoneyModel> call = obj.getTimeValueOfMoneyService(flag, tenure, tenure_period, rate, presentValue, futureValue, pmt,
                paymentType, compoundingPeriodPerYear, payementPeriodPerYear);
        call.enqueue(new Callback<TimeValueOfMoneyModel>() {
            @Override
            public void onResponse(Call<TimeValueOfMoneyModel> call, Response<TimeValueOfMoneyModel> response) {
                UtileKit.dismisssSpinnerDialog();
                timeValueOfMoneyModel = response.body();
                if (timeValueOfMoneyModel != null) {
                    if (timeValueOfMoneyModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        String flag = timeValueOfMoneyModel.getData().getFlag();
                        int size = allFlagValues.size();
                        for (int i = 0; i < size; i++) {
                            if (flag.equals(allFlagValues.get(i))) {
                                setValueFromService(i, timeValueOfMoneyModel.getData().getResult());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TimeValueOfMoneyModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
            }
        });


    }



    //("tn","rate","pv", "fv", "pmt")
    private void setValueFromService(int i, String result) {
        Log.i(TAG," setValueFromService "+ result);
        switch (i) {
            case 0:
                tenure_edt.setText(roundoffdecimalvaluetwodot(result));
                Log.i(TAG, "setValueFromService: "+ roundoffdecimalvaluetwodot(result));
                break;
            case 1:
                rate_edt.setText(roundoffdecimalvaluetwodot(result));
                
                break;
            case 2:
                present_value_edt.setText(roundoffdecimalvaluetwodot(result));
                break;
            case 3:
                future_value_edt.setText(roundoffdecimalvaluetwodot(result));
                break;
            case 4:
                payement_edt.setText(roundoffdecimalvaluetwodot(result));
                break;
        }
    }

    private String roundoffdecimalvaluetwodot(String result) {

        BigDecimal parsed;
        String formated = "";
        try {
            parsed = new BigDecimal(result.toString());
            formated = (new DecimalFormat("#0.00").format(parsed));
            return formated;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            formated = "0.00";

        }
        return result;
    }

    private void initializeAllSpinners() {
        tenure_spinner_adapter = new ArrayAdapter(mContext, R.layout.sinmple_text_view, tenure_spinner_array);
        tenure_spinner.setAdapter(tenure_spinner_adapter);
        tenure_spinner.setSelection(0);

        paymentType_spinner_adapter = new ArrayAdapter(mContext, R.layout.sinmple_text_view, paymentType_spinner_array);
        paymentType_spinner.setAdapter(paymentType_spinner_adapter);
        paymentType_spinner.setSelection(0);


        payment_period_spinner_adapter = new ArrayAdapter(mContext, R.layout.sinmple_text_view, period_spinner_array);
        payementPeriod_spinner.setAdapter(payment_period_spinner_adapter);
        payementPeriod_spinner.setSelection(0);

        compounding_period_spinner_adapter = new ArrayAdapter(mContext, R.layout.sinmple_text_view, period_spinner_array);
        compoundingPeriod_spinner.setAdapter(compounding_period_spinner_adapter);
        compoundingPeriod_spinner.setSelection(0);
    }

    private void setAllRadioButtonsToCheckedState() {
        UtileKit.getSwitchYesBtnViewSmall(presenValue_plus_RadioBtn, presenValue_minus_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnViewSmall(futureValue_plus_RadioBtn, futureValue_minus_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnViewSmall(payment_plus_RadioBtn, payment_minus_RadioBtn, mContext);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()) {
            case R.id.presenValue_rg:
                if (checkedId == R.id.presenValue_plus_RadioBtn) {
                    UtileKit.getSwitchYesBtnViewSmall(presenValue_plus_RadioBtn, presenValue_minus_RadioBtn, mContext);

                } else if (checkedId == R.id.presenValue_minus_RadioBtn) {
                    UtileKit.getSwitchNoBtnViewSmall(presenValue_plus_RadioBtn, presenValue_minus_RadioBtn, mContext);
                }
                break;

            case R.id.futureValue_rg:
                if (checkedId == R.id.futureValue_plus_RadioBtn) {
                    UtileKit.getSwitchYesBtnViewSmall(futureValue_plus_RadioBtn, futureValue_minus_RadioBtn, mContext);
                } else if (checkedId == R.id.futureValue_minus_RadioBtn) {
                    UtileKit.getSwitchNoBtnViewSmall(futureValue_plus_RadioBtn, futureValue_minus_RadioBtn, mContext);
                }

                break;

            case R.id.payment_rg:
                if (checkedId == R.id.payment_plus_RadioBtn) {
                    UtileKit.getSwitchYesBtnViewSmall(payment_plus_RadioBtn, payment_minus_RadioBtn, mContext);
                } else if (checkedId == R.id.payment_minus_RadioBtn) {
                    UtileKit.getSwitchNoBtnViewSmall(payment_plus_RadioBtn, payment_minus_RadioBtn, mContext);
                }

                break;

        }
    }

    //("tn","rate","pv", "fv", "pmt")

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relative_left_arrow :

                mCallBackListener.onActivityBackPressed();
                break;

            case R.id.relative_center_home:
                Log.i("spcheck", " relative_center_home is clicked"  );
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                startActivity(i);
                break;


            case R.id.tenure_btn:
                check_RadioButtonState_AndGet_Values();
                Log.i(TAG, "onClick: presentValue="+presentValue+" , futureValue ="+futureValue);
                if(!isMandatoryFieldsEmpty(0)) {
                    callTimeValueMoneyService("tn", "",periodMap.get(tenure_spinner.getSelectedItemPosition()) + "",rate_edt.getText().toString(), presentValue, futureValue,
                            payment, paymentType_spinner.getSelectedItemPosition() + "",
                            periodMap.get(compoundingPeriod_spinner.getSelectedItemPosition()) + "",
                            periodMap.get(payementPeriod_spinner.getSelectedItemPosition()) + "");
                }
                break;
            case R.id.rate_btn:
                check_RadioButtonState_AndGet_Values();
                if(!isMandatoryFieldsEmpty(10)) {
                    callTimeValueMoneyService("rate", tenure_edt.getText().toString(), periodMap.get(tenure_spinner.getSelectedItemPosition()) + "",
                            "", presentValue, futureValue,payment, paymentType_spinner.getSelectedItemPosition() + "",
                            periodMap.get(compoundingPeriod_spinner.getSelectedItemPosition()) + "",
                            periodMap.get(payementPeriod_spinner.getSelectedItemPosition()) + "");
                }
                break;
            case R.id.presentValue_btn:
                check_RadioButtonState_AndGet_Values();
                if(!isMandatoryFieldsEmpty(1)) {
                    callTimeValueMoneyService("pv", tenure_edt.getText().toString(), periodMap.get(tenure_spinner.getSelectedItemPosition()) + "",
                            rate_edt.getText().toString(), "", futureValue,
                            payment, paymentType_spinner.getSelectedItemPosition() + "",
                            periodMap.get(compoundingPeriod_spinner.getSelectedItemPosition()) + "",
                            periodMap.get(payementPeriod_spinner.getSelectedItemPosition()) + "");
                }
                break;
            case R.id.futureValue_btn:
                check_RadioButtonState_AndGet_Values();
                if(!isMandatoryFieldsEmpty(2)) {
                    callTimeValueMoneyService("fv", tenure_edt.getText().toString(), periodMap.get(tenure_spinner.getSelectedItemPosition()) + "",
                            rate_edt.getText().toString(), presentValue, "",
                            payment, paymentType_spinner.getSelectedItemPosition() + "",
                            periodMap.get(compoundingPeriod_spinner.getSelectedItemPosition()) + ""
                            , periodMap.get(payementPeriod_spinner.getSelectedItemPosition()) + "");
                }
                break;
            case R.id.payment_btn:
                check_RadioButtonState_AndGet_Values();
                if(!isMandatoryFieldsEmpty(3)) {
                    callTimeValueMoneyService("pmt", tenure_edt.getText().toString(), periodMap.get(tenure_spinner.getSelectedItemPosition()) + "",
                            rate_edt.getText().toString(), presentValue, futureValue,"", paymentType_spinner.getSelectedItemPosition() + "",
                            periodMap.get(compoundingPeriod_spinner.getSelectedItemPosition()) + "",
                            periodMap.get(payementPeriod_spinner.getSelectedItemPosition()) + "");
                }
                break;
        }

    }

    private boolean isMandatoryFieldsEmpty(int code) {
        boolean isEmpty=false;
        int size=mandatoryFieldsList.size();
        for(int i=0;i<size;i++){
            if(i!=code){
                if((null==mandatoryFieldsList.get(i))||(mandatoryFieldsList.get(i).equals(""))){
                    setErrorForMissingValues(i);
                    isEmpty=true;
                }
            }
        }
        return isEmpty;
    }

    private void check_RadioButtonState_AndGet_Values() {
        tenureValue=tenure_edt.getText().toString();
        if(validateObjectValues(present_value_edt.getText().toString())) {
            presentValue = (presenValue_rg.getCheckedRadioButtonId() == R.id.presenValue_plus_RadioBtn) ? UtileKit.getStringwithoutCurreny(present_value_edt.getText().toString()) : ((new BigDecimal(-1)).multiply(new BigDecimal(UtileKit.getStringwithoutCurreny(present_value_edt.getText().toString())))) + "";
        }
        if(validateObjectValues(future_value_edt.getText().toString())) {
            futureValue = (futureValue_rg.getCheckedRadioButtonId() == R.id.futureValue_plus_RadioBtn) ? UtileKit.getStringwithoutCurreny(future_value_edt.getText().toString()) : ((new BigDecimal(-1)).multiply(new BigDecimal(UtileKit.getStringwithoutCurreny(future_value_edt.getText().toString())))) + "";
        }
        if(validateObjectValues(payement_edt.getText().toString())) {
            payment = (payment_rg.getCheckedRadioButtonId() == R.id.payment_plus_RadioBtn) ? UtileKit.getStringwithoutCurreny(payement_edt.getText().toString()) : ((new BigDecimal(-1)).multiply(new BigDecimal(UtileKit.getStringwithoutCurreny(payement_edt.getText().toString())))) + "";
        }
        mandatoryFieldsList=Arrays.asList(tenureValue,presentValue,futureValue,payment);
    }

    public void setErrorForMissingValues(int index) {
        switch (index) {
            case 0:
                setError(tenure_lyt);
                getFocus(tenure_edt);
                break;
            case 1:
                setError((TextInputLayout) present_value_edt.getEditText().getParent().getParent());
                getFocusCurrencyGhost(present_value_edt);
                break;

            case 2:
                setError(future_value_lyt);
                getFocusCurrencyGhost(future_value_edt);
                break;

            case 3:
                setError(payement_lyt);
                getFocusCurrencyGhost(payement_edt);
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

    private void getFocusCurrencyGhost(CurrencyBlackGhostview editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public class CommonTextWatcher implements TextWatcher{

        EditText editText;
        public CommonTextWatcher(EditText editText){
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
                case R.id.tenure_edt:
                    tenure_lyt.setErrorEnabled(false);
                    tenure_lyt.setError(null);
                    break;
                case R.id.rate_edt :
                    rate_lyt.setErrorEnabled(false);
                    rate_lyt.setError(null);
                    break;
                case R.id.present_value_edt :
                    ((TextInputLayout) present_value_edt.getEditText().getParent().getParent()).setErrorEnabled(false);
                    ((TextInputLayout) present_value_edt.getEditText().getParent().getParent()).setError(null);
                    break;
                case R.id.future_value_edt :
                    future_value_lyt.setErrorEnabled(false);
                    future_value_lyt.setError(null);
                    break;
                case R.id.payement_edt :
                    payement_lyt.setErrorEnabled(false);
                    payement_lyt.setError(null);
                    break;
            }

        }
    }

}

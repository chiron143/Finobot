package com.purplepath.purplepath.desiproAllModules.amortizationSchedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.calculator.CalculatorAct;
import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.NumberEditText;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models.AmortizationScheduleModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.Locale;
import butterknife.OnClick;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.incomedetails.IncomeDynamicDetail.PARENT_CLASS_SOURCE;

/**
 * Created by Pratheep.S on 30-05-2017.
 */

public class AmortizationMainFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.outStanding_bal_lyt)
    TextInputLayout outStanding_bal_lyt;

    @BindView(R.id.rate_lyt)
    TextInputLayout rate_lyt;

    @BindView(R.id.tenure_lyt)
    TextInputLayout tenure_lyt;

    @BindView(R.id.service_tax_rate_lyt)
    TextInputLayout service_tax_rate_lyt;

    @BindView(R.id.outStanding_bal_edt)
    CurrencyGhostView outStanding_bal_edt;

    @BindView(R.id.rate_edt)
    PercentageEditText rate_edt;

    @BindView(R.id.tenure_edt)
    NumberEditText tenure_edt;

    @BindView(R.id.service_tax_rate_edt)
    PercentageEditText service_tax_rate_edt;

    @BindView(R.id.amortization_fab)
    FloatingActionButton amortization_fab;

    @BindView(R.id.outStanding_bal_edt_calculaterImgView)
    CustomCalenderImageView outStanding_bal_edt_calculaterImgView;

    AmortizationStatementView amort_table_fragment;
    private AmortizationScheduleModel amort_model;
    private String outStanding_bal,rate,tenure,service_tax;
    private Context mContext;
    private OnActivityBackPressedListener mCallBackListener;
    boolean status;
    String TAG="spcheck";
    Locale indianlocal =  new Locale("en", "IN");
    View nameEditview;
    public static final String TITLE = "";
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
      //  inflater.inflate(R.menu.desipro_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.done_menu:
                validateAllValues();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setHasOptionsMenu(true);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_amortization_main,container,false);
        ButterKnife.bind(this,view);
       // setHasOptionsMenu(true);
//        outStanding_bal_edt.setLocale(indianlocal);
        mCallBackListener.setActionBarTitle("DeciPro - Amortization Schedule");
        outStanding_bal_edt.getEditText().addTextChangedListener(new CommonTextWatcher(outStanding_bal_edt.getEditText()));

        tenure_edt.addTextChangedListener(new CommonTextWatcher(tenure_edt));
        tenure_edt.setHintText(getString(R.string.hint_amartization_totaltenure), ((TextInputLayout)
                (tenure_edt.getParent()).getParent()));

        rate_edt.addTextChangedListener(new CommonTextWatcher(rate_edt));
        rate_edt.setHintText(getString(R.string.hint_amartization_interestrate), ((TextInputLayout)
                (rate_edt.getParent()).getParent()));
        amortization_fab.setOnClickListener(this);

        outStanding_bal_edt.setTextHint("Outstanding Balance");
        outStanding_bal_edt.setfullHintTxt(getString(R.string.hint_amartization_outsandingbalance));

        service_tax_rate_edt.setHintText(getString(R.string.hint_amartization_servicetax), ((TextInputLayout)
                (service_tax_rate_edt.getParent()).getParent()));

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view. findViewById(R.id.relative_right_arrow);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        outStanding_bal_edt_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(outStanding_bal_edt);
            }
        });

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
    private void validateAllValues() {
        outStanding_bal=UtileKit.getStringwithoutCurreny(outStanding_bal_edt.getText().toString());
        rate=rate_edt.getText().toString();
        tenure=tenure_edt.getText().toString();
        service_tax=service_tax_rate_edt.getText().toString();

        if(isValid(1)&&isValid(2)&&isValid(3)){
            callAmortizationScheduleService();
        }

    }

    boolean isValid(int position)
    {
        boolean flag=false;
        switch (position){
            case 1:
                flag=checkValuesAndSetErrors(outStanding_bal_lyt,outStanding_bal,outStanding_bal_edt);
                Log.i(TAG, "isValid: outStanding_bal ");
                break;
            case 2:
                flag=checkValuesAndSetError(rate_lyt,rate,rate_edt);
                Log.i(TAG, "isValid: rate ");
                break;
            case 3:
                flag=checkValuesAndSetError(tenure_lyt,tenure,tenure_edt);
                break;
        }

        return flag;
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
    private void callAmortizationScheduleService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls obj= ServiceGenerator.createService(WebServiceCalls.class);
        Call<AmortizationScheduleModel> call= obj.getAmortizationSchedule(outStanding_bal,rate,tenure,service_tax);
        Log.i(TAG, "callAmorizationScheduleService: "+outStanding_bal+rate+tenure+service_tax);
        call.enqueue(new Callback<AmortizationScheduleModel>() {
            @Override
            public void onResponse(Call<AmortizationScheduleModel> call, Response<AmortizationScheduleModel> response) {
                UtileKit.dismisssSpinnerDialog();
                amort_model=response.body();
               // Toast.makeText(mContext,"WS complete",Toast.LENGTH_LONG).show();
                amort_table_fragment=AmortizationStatementView.newInstance(amort_model);
                showFragment(amort_table_fragment);

            }

            @Override
            public void onFailure(Call<AmortizationScheduleModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    private void getFocus(EditText editText) {
        try {
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }catch (Exception e){e.printStackTrace();}
    }

    private void getFocusCurrencyGhost(CurrencyGhostView currencyGhostview) {
        try {
        currencyGhostview.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(currencyGhostview, InputMethodManager.SHOW_IMPLICIT);
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.amortization_fab:
                validateAllValues();
                break;
            case R.id.relative_left_arrow:
                Log.i("spcheck", " relative_left_arrow is clicked"  );
                mCallBackListener.onActivityBackPressed();
                break;

            case R.id.relative_center_home:
                Log.i(TAG, " relative_center_home is clicked"  );
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                startActivity(i);
                break;

        }
    }

    private class CommonTextWatcher implements TextWatcher{
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
                case R.id.outStanding_bal_edt:
                    outStanding_bal_lyt.setErrorEnabled(false);
                    outStanding_bal_lyt.setError(null);
                    outStanding_bal=UtileKit.getStringwithoutCurreny(s.toString());
                    break;

                case R.id.rate_edt:
                    rate_lyt.setErrorEnabled(false);
                    rate_lyt.setError(null);
                    rate=s.toString();
                    break;

                case R.id.tenure_edt:
                    tenure_lyt.setErrorEnabled(false);
                    tenure_lyt.setError(null);
                    tenure=s.toString();
                    break;
            }
        }
    }
}

package com.purplepath.purplepath.desiproAllModules.goalaffordability.ui;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;
import com.purplepath.purplepath.customview.PercentageEditText;
import com.purplepath.purplepath.desiproAllModules.goalaffordability.ui.DialogFragment.GoalAffordabilityDialog;
import com.purplepath.purplepath.desiproAllModules.goalaffordability.ui.models.GoalAffordabilityModels;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.incomedetails.IncomeDynamicDetail.PARENT_CLASS_SOURCE;

/**
 * Created by Suresh on 17/08/17.
 */

public class GoalAffordabilityFragment extends BaseFragment implements  AdapterView.OnItemSelectedListener, View.OnClickListener{

    private final static String TAG = GoalAffordabilityFragment.class.getCanonicalName();
    private Context mContext;
    private Spinner mgoaltypespinner;
    private EditText mgoalTimeframe_edit;
    private PercentageEditText mgoalExpectedincreaseEdit,mgoalExpectedreturnEdit;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private FloatingActionButton fab;
    private String[] mgoalTypeArray;

    private String mgoaltypeString,mgoalTimeframe_String,mgoalcurrentcostrupeeString,
            mgoalcurrentsavingsString,mgoalanuualsavingsString,mgoalExpectedincreaseString,mgoalExpectedreturnString;

    private OnActivityBackPressedListener mCallBackListener;
    private GoalAffordabilityModels mGoalAffordabilityModels;

    private CustomCalenderImageView goalcurrentcostrupee_calculaterImgView,
            goalcurrentsavingsEdit_calculaterImgView,
            goalanuualsavingsEdit_calculaterImgView;
    private CurrencyGhostView mgoalcurrentcostrupee,mgoalcurrentsavingsEdit,
            mgoalanuualsavingsEdit;
    View nameEditview;
    public static final String TITLE = "";
    private List<String> mandatoryFieldsList=new ArrayList<>();
    boolean status;
    private TextInputLayout goalExpectedincrease,goalExpectedreturn,goalTimeframe;
    TextView selectedTextView;
    TextView tvInvisibleError;

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
        View view=inflater.inflate(R.layout.fragment_goalaffordability, container, false);
        mCallBackListener.setActionBarTitle("DeciPro - Goal Affordability");

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        fab = view.findViewById(R.id.goalaff_fab_id);

        mgoaltypespinner = view.findViewById(R.id.goaltypespinner);
        mgoalTimeframe_edit = view.findViewById(R.id.goalTimeframe_edit);


        mgoalTimeframe_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    if(mgoalTimeframe_edit.getText().length()!=0){
                        goalTimeframe.setErrorEnabled(false);
                        goalTimeframe.setError(null);
                    }else {
                        goalTimeframe.setError(getString(R.string.hint_goalaffordability_timeframe));
                        goalTimeframe.setErrorTextAppearance(R.style.errorHintEdit);
                    }
                }
            }
        });


        mgoalExpectedincreaseEdit = view.findViewById(R.id.goalExpectedincreaseEdit);
        mgoalExpectedincreaseEdit.setHintText(getString(R.string.hint_goalaffordability_expectedincrement), ((TextInputLayout)
                (mgoalExpectedincreaseEdit.getParent()).getParent()));

        mgoalExpectedreturnEdit = view.findViewById(R.id.goalExpectedreturnEdit);
        mgoalExpectedreturnEdit.setHintText(getString(R.string.hint_goalaffordability_expectedreturn), ((TextInputLayout)
                (mgoalExpectedreturnEdit.getParent()).getParent()));

        mgoalTimeframe_edit.addTextChangedListener(new CommonTextWatchers(mgoalTimeframe_edit));

        mgoalcurrentcostrupee= view.findViewById(R.id.goalcurrentcostrupee);
        mgoalcurrentsavingsEdit= view.findViewById(R.id.goalcurrentsavingsEdit);
        mgoalanuualsavingsEdit= view.findViewById(R.id.goalanuualsavingsEdit);

        mgoalcurrentcostrupee.getEditText().addTextChangedListener(new CommonTextWatcher(mgoalcurrentcostrupee));
        mgoalcurrentsavingsEdit.getEditText().addTextChangedListener(new CommonTextWatcher(mgoalcurrentsavingsEdit));
        mgoalanuualsavingsEdit.getEditText().addTextChangedListener(new CommonTextWatcher(mgoalanuualsavingsEdit));

        goalExpectedincrease= view.findViewById(R.id.goalExpectedincrease);
        goalExpectedreturn= view.findViewById(R.id.goalExpectedreturn);
        goalTimeframe= view.findViewById(R.id.goalTimeframe);


        mgoalcurrentcostrupee.setTextHint("Current cost");
        mgoalcurrentcostrupee.setfullHintTxt(getString(R.string.hint_goalaffordability_currentcost));

        mgoalcurrentsavingsEdit.setTextHint("Current Savings");
        mgoalcurrentsavingsEdit.setfullHintTxt(getString(R.string.hint_goalaffordability_currentsavings));

        mgoalanuualsavingsEdit.setTextHint("Aunnal Savings");
        mgoalanuualsavingsEdit.setfullHintTxt(getString(R.string.hint_goalaffordability_annualsavings));


        goalcurrentcostrupee_calculaterImgView= view.findViewById(R.id.goalcurrentcostrupee_calculaterImgView);
        goalcurrentsavingsEdit_calculaterImgView= view.findViewById(R.id.goalcurrentsavingsEdit_calculaterImgView);
        goalanuualsavingsEdit_calculaterImgView= view.findViewById(R.id.goalanuualsavingsEdit_calculaterImgView);

        goalcurrentcostrupee_calculaterImgView.setOnClickListener(this);
        goalcurrentsavingsEdit_calculaterImgView.setOnClickListener(this);
        goalanuualsavingsEdit_calculaterImgView.setOnClickListener(this);

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        fab.setOnClickListener(this);
        mgoalTypeArray = getResources().getStringArray(R.array.goal_type_affordability);
        setSpinnerAdapter(mgoaltypespinner, mgoalTypeArray);

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
            case R.id.goaltypespinner:
                mgoaltypeString = mgoaltypespinner.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goalcurrentcostrupee_calculaterImgView:{
                showCalDialog(mgoalcurrentcostrupee);
            }
            break;

            case R.id.goalcurrentsavingsEdit_calculaterImgView:{
                showCalDialog(mgoalcurrentsavingsEdit);
            }
            break;
            case R.id.goalanuualsavingsEdit_calculaterImgView:{
                  showCalDialog(mgoalanuualsavingsEdit);
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
                Log.i(TAG, " relative_center_home is clicked"  );
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                startActivity(i);
            }
            break;
            case R.id.relative_right_arrow:
            {

            }
            break;
            case R.id.goalaff_fab_id:{
                validateAllValues();
                View selectedView = mgoaltypespinner.getSelectedView();
                if (selectedView != null && selectedView instanceof TextView) {
                    mgoaltypespinner.requestFocus();
                    selectedTextView = (TextView) selectedView;

                    if(selectedTextView.getText().length()!=0){
                        selectedTextView.setError(null);
                    }
                }
            }
        }
    }

    private void validateAllValues() {
        mgoalTimeframe_String = mgoalTimeframe_edit.getText().toString();

        mgoalanuualsavingsString= UtileKit.getStringwithoutCurreny(mgoalanuualsavingsEdit.getEditText().getText().toString());
        mgoalcurrentsavingsString= UtileKit.getStringwithoutCurreny(mgoalcurrentsavingsEdit.getEditText().getText().toString());
        mgoalcurrentcostrupeeString= UtileKit.getStringwithoutCurreny(mgoalcurrentcostrupee.getEditText().getText().toString());

        mgoalExpectedincreaseString= mgoalExpectedincreaseEdit.getText().toString();
        mgoalExpectedreturnString= mgoalExpectedreturnEdit.getText().toString();

        if(isValid(1)&&isValid(2)&&isValid(3)&&isValid(4)&&isValid(5)&&isValid(6)&&isValid(7)){
            updateWebservice(mgoaltypeString,mgoalTimeframe_String,mgoalanuualsavingsString,
                    mgoalcurrentsavingsString,mgoalExpectedincreaseString
                    ,mgoalExpectedreturnString,mgoalcurrentcostrupeeString);
        }
    }
    boolean isValid(int position)
    {
        boolean flag=false;
        switch (position){
            case 1:
                flag=setSpinnerError(mgoaltypespinner,mgoaltypeString);
                break;
            case 2:
                flag=checkValuesAndSetError(goalTimeframe,mgoalTimeframe_String,mgoalTimeframe_edit);
                break;
            case 3:
                flag=checkValuesAndSetErrors((TextInputLayout)mgoalcurrentcostrupee.getEditText().getParent().getParent(),
                        mgoalcurrentcostrupeeString,mgoalcurrentcostrupee);
                getFocusCurrencyGhost(mgoalcurrentcostrupee);
                break;
            case 4:
                flag=checkValuesAndSetErrors((TextInputLayout)mgoalcurrentsavingsEdit.getEditText().getParent().getParent(),
                        mgoalcurrentsavingsString,mgoalcurrentsavingsEdit);
                getFocusCurrencyGhost(mgoalcurrentsavingsEdit);
                break;
            case 5:
                flag=checkValuesAndSetErrors((TextInputLayout)mgoalanuualsavingsEdit.getEditText().getParent().getParent(),
                        mgoalanuualsavingsString,mgoalanuualsavingsEdit);
                getFocusCurrencyGhost(mgoalanuualsavingsEdit);
                break;
            case 6:
                flag=checkValuesAndSetError(goalExpectedincrease,mgoalExpectedincreaseString,mgoalExpectedincreaseEdit);
                break;
            case 7:
                flag=checkValuesAndSetError(goalExpectedreturn,mgoalExpectedreturnString,mgoalExpectedreturnEdit);
                break;

        }
        return flag;
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

        CurrencyGhostView editText;
        public CommonTextWatcher(CurrencyGhostView editText){
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
                case R.id.goalcurrentcostrupee:
                    ((TextInputLayout)mgoalcurrentcostrupee.getEditText().getParent().getParent()).setErrorEnabled(false);
                    ((TextInputLayout)mgoalcurrentcostrupee.getEditText().getParent().getParent()).setError(null);
                    break;
                case R.id.goalcurrentsavingsEdit :
                    ((TextInputLayout)mgoalcurrentsavingsEdit.getEditText().getParent().getParent()).setErrorEnabled(false);
                    ((TextInputLayout)mgoalcurrentsavingsEdit.getEditText().getParent().getParent()).setError(null);
                    break;
                case R.id.goalanuualsavingsEdit :
                    ((TextInputLayout) mgoalanuualsavingsEdit.getEditText().getParent().getParent()).setErrorEnabled(false);
                    ((TextInputLayout) mgoalanuualsavingsEdit.getEditText().getParent().getParent()).setError(null);
                    break;
            }

        }
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
                case R.id.goalTimeframe_edit:
                    goalTimeframe.setErrorEnabled(false);
                    goalTimeframe.setError(null);
                    break;
            }
        }
    }




    private void updateWebservice(String mgoaltypeString, String mgoalTimeframe_String, String mgoalanuualsavingsString, String mgoalcurrentsavingsString,
                                  String mgoalExpectedincreaseString, String mgoalExpectedreturnString, String mgoalcurrentcostrupeeString) {
        WebServiceCalls callObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalAffordabilityModels> call=callObj.getGoalAffordability(mgoaltypeString,
                mgoalTimeframe_String,mgoalcurrentcostrupeeString,
                mgoalExpectedincreaseString,mgoalanuualsavingsString
        ,mgoalcurrentsavingsString,mgoalExpectedreturnString);
        call.enqueue(new Callback<GoalAffordabilityModels>() {
            @Override
            public void onResponse(Call<GoalAffordabilityModels> call, Response<GoalAffordabilityModels> response) {
                Log.i("WebServiceCalls", "onResponse: success GoalAffordabilityModels : "+ response.body());
                mGoalAffordabilityModels = response.body();
                if(mGoalAffordabilityModels.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    checkAffordableOrNot(mGoalAffordabilityModels);
                }

            }
            @Override
            public void onFailure(Call<GoalAffordabilityModels> call, Throwable t) {
                Log.i("WebServiceCalls", "onResponse: failure"); UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }

    private void checkAffordableOrNot(GoalAffordabilityModels mGoalAffordabilityModels) {
        if(mGoalAffordabilityModels!= null){
            if(mGoalAffordabilityModels.getData().getResult().equalsIgnoreCase("Not Affordable")){

                FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
                DialogFragment newFragment = GoalAffordabilityDialog.newInstance(mGoalAffordabilityModels, mContext);

                newFragment.show(fm, "dialog");
            }else{
                FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
                DialogFragment newFragment = GoalAffordabilityDialog.newInstance(mGoalAffordabilityModels, mContext);

                newFragment.show(fm, "dialog");
            }
        }

    }


    private void getFocusCurrencyGhost(CurrencyGhostView editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }


    private void getFocus(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }




}

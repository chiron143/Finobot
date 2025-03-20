package com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.ui;



import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.dialog.AssetDatePickerDialogFragment;
import com.purplepath.purplepath.calenderNumberPicker.calenderUi.CalenderTabs;
import com.purplepath.purplepath.customview.CalendarEditText;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.interfaces.UpdateDateCallBackInterface;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pratheep.S on 29-03-2017.
 */

public class EmiDialogFragment extends DialogFragment implements View.OnClickListener,DatePickerCallBackInterface {
    @BindView(R.id.loanStartDate_edt)
    CalendarEditText loanStartDateEdt;

    @BindView(R.id.firstEmiPaidDate_edt)
    CalendarEditText firstEmiPaidDateEdt;

    @BindView(R.id.lastEmiPaidDate_edt)
    CalendarEditText lastEmiPaidDateEdt;

    @BindView(R.id.nextEmiDueDate_edt)
    CalendarEditText nextEmiDueDateEdt;

    @BindView(R.id.finalEmiDate_edt)
    CalendarEditText finalEmiDateEdt;

    //Text Input Layout

    @BindView(R.id.loanStartDateInputLayout)
    TextInputLayout loanStartDate;

    @BindView(R.id.firstEmiPaidDateInputLayout)
    TextInputLayout firstEmiPaidDate;

    @BindView(R.id.lastEmiPaidDateInputLayout)
    TextInputLayout lastEmiPaidDate;

    @BindView(R.id.nextEmiDueDateInputLayout)
    TextInputLayout nextEmiDueDate;

    @BindView(R.id.finalEmiInputLayout)
    TextInputLayout finalEmiInputLayout;
/*
    @BindView(R.id.iv_done)
    ImageView doneButton;*/

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.closebtnId)
    ImageView closeButton;

    static UpdateDateCallBackInterface updateValInFragment;

    public static EmiDialogFragment newInstance(UpdateDateCallBackInterface obj, String loanStartDate, String firstEmiPaidDate, String lastEmiPaidDate, String nextEmiDueDate, String finalEmiDate){
        updateValInFragment = obj;
        EmiDialogFragment fragment=new EmiDialogFragment();
        Bundle args=new Bundle();
        if(loanStartDate!=null)
        args.putString("loanStartDate",loanStartDate);
        args.putString("firstEmiPaidDate",firstEmiPaidDate);
        args.putString("lastEmiPaidDate",lastEmiPaidDate);
        args.putString("nextEmiDueDate",nextEmiDueDate);
        args.putString("finalEmiDateEdt",finalEmiDate);
        fragment.setArguments(args);
        return fragment;

    }

    public static EmiDialogFragment newInstance(UpdateDateCallBackInterface obj, String loanStartDate, String firstEmiPaidDate, String lastEmiPaidDate, String nextEmiDueDate, String finalEmiDate,boolean [] emptyDates){
        updateValInFragment = obj;
        EmiDialogFragment fragment=new EmiDialogFragment();
        Bundle args=new Bundle();
        args.putString("loanStartDate",loanStartDate);
        args.putString("firstEmiPaidDate",firstEmiPaidDate);
        args.putString("lastEmiPaidDate",lastEmiPaidDate);
        args.putString("nextEmiDueDate",nextEmiDueDate);
        args.putString("finalEmiDateEdt",finalEmiDate);
        args.putBooleanArray("emptyDates",emptyDates);
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.dialog_fragment_emi,container,false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this,view);
        loanStartDateEdt.setOnClickListener(this);
        editTextDrawableClick(loanStartDateEdt,"Loan Start Date",true);
        editTextDrawableClick(firstEmiPaidDateEdt,"First Emi Paid Date",true);
        editTextDrawableClick(lastEmiPaidDateEdt,"Last EMI Paid Date",true);
        editTextDrawableClick(nextEmiDueDateEdt,"Next EMI Due Date",false);
        editTextDrawableClick(finalEmiDateEdt,"Final EMI Date",false);

        loanStartDateEdt.addTextChangedListener(new GenericTextWatcher(loanStartDateEdt));
        firstEmiPaidDateEdt.addTextChangedListener(new GenericTextWatcher(firstEmiPaidDateEdt));
        lastEmiPaidDateEdt.addTextChangedListener(new GenericTextWatcher(lastEmiPaidDateEdt));
        nextEmiDueDateEdt.addTextChangedListener(new GenericTextWatcher(nextEmiDueDateEdt));
        finalEmiDateEdt.addTextChangedListener(new GenericTextWatcher(finalEmiDateEdt));


        getBundleArguments(getArguments());
        //doneButton.setOnClickListener(this);
        fab.setOnClickListener(this);
        closeButton.setOnClickListener(this);



        return view;
    }

    private void getBundleArguments(Bundle args) {
        if(args!=null){
            if(args.containsKey("loanStartDate")){
                loanStartDateEdt.setText(args.getString("loanStartDate"));
              }
            if(args.containsKey("firstEmiPaidDate")){
                firstEmiPaidDateEdt.setText(args.getString("firstEmiPaidDate"));
            }
            if(args.containsKey("lastEmiPaidDate")){
                lastEmiPaidDateEdt.setText(args.getString("lastEmiPaidDate"));
            }
            if(args.containsKey("nextEmiDueDate")){
                nextEmiDueDateEdt.setText(args.getString("nextEmiDueDate"));
            }
            if(args.containsKey("finalEmiDateEdt")){
                finalEmiDateEdt.setText(args.getString("finalEmiDateEdt"));
            }
            if(args.containsKey("emptyDates")){
                setErrorForEmptyDates(args.getBooleanArray("emptyDates"));
            }
        }

    }

    private void setErrorForEmptyDates(boolean[] emptyDates) {

        for(int i=0;i<emptyDates.length;i++){
            if(emptyDates[i]==true){
                switch (i){
                    case 0:
                        setError(loanStartDate);
                        break;
                    case 1:
                        setError(firstEmiPaidDate);
                        break;
                    case 2:
                        setError(lastEmiPaidDate);
                        break;
                    case 3:
                        setError(nextEmiDueDate);
                        break;
                    case 4:
                        setError(finalEmiInputLayout);
                        break;
                }
            }
        }
    }

    private void setError(TextInputLayout ti) {
        ti.setError("For current loan Please select the missing date");
    }

    @Override
    public void onResume() {
        int height=getResources().getDisplayMetrics().heightPixels;
        int width=getResources().getDisplayMetrics().widthPixels;
        Log.i("spcheck", "onResume: "+height+" "+width);
        getDialog().getWindow().setLayout((int)(width*.95),(int)(height*.95));
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*case R.id.iv_done:
                updateValInFragment.updateAllDates(loanStartDateEdt.getText().toString(),firstEmiPaidDateEdt.getText().toString(),
                   lastEmiPaidDateEdt.getText().toString(),nextEmiDueDateEdt.getText().toString(), finalEmiDateEdt.getText().toString());
                getDialog().dismiss();
                break;*/

            case R.id.fab:
                updateValInFragment.updateAllDates(loanStartDateEdt.getText().toString(),firstEmiPaidDateEdt.getText().toString(),
                        lastEmiPaidDateEdt.getText().toString(),nextEmiDueDateEdt.getText().toString(), finalEmiDateEdt.getText().toString());
                getDialog().dismiss();

                break;

            case R.id.closebtnId:
                getDialog().dismiss();
                break;
        }

    }
    public void editTextDrawableClick(final EditText EdtText, final String title, final boolean limitToCurrentDate) {

        EdtText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (event.getRawX() >= (EdtText.getRight() - EdtText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    //setEdtText(EdtText);
                    showMonthCalanderonClick(EdtText,title,limitToCurrentDate);
                    return true;
//                    }
                }
                return false;
            }
        });
    }

    private void showMonthCalanderonClick(EditText EdtText,String title,boolean limitToCurrentDate) {
        Bundle args=new Bundle();
        String date=EdtText.getText().toString();
//        AssetDatePickerDialogFragment  assetDatePickerDialogFragment = AssetDatePickerDialogFragment.newInstance(this, title, limitToCurrentDate, Boolean.FALSE, Boolean.FALSE,date);
//
//        if(UtileKit.validateObjectValues(date)){
//            args.putString(AssetDatePickerDialogFragment.FULLDATE,date);
//            assetDatePickerDialogFragment.setArguments(args);
//        }

//        assetDatePickerDialogFragment.show(getActivity().getFragmentManager(),"show");

        CalenderTabs mcalenderTabs =  CalenderTabs.newInstance(this,
                title,limitToCurrentDate,Boolean.FALSE,Boolean.FALSE,"1", date);
        if(UtileKit.validateObjectValues(date)) {
            args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
            mcalenderTabs.setArguments(args);
        }
        mcalenderTabs.show(getFragmentManager(),title);

    }


    @Override
    public void updateEditTextValue(String value, String title) {

        if(title.equalsIgnoreCase("Loan Start Date")){
            loanStartDateEdt.setText(value);
        }else if(title.equalsIgnoreCase("First Emi Paid Date")){
            firstEmiPaidDateEdt.setText(value);
        }else if(title.equalsIgnoreCase("Last EMI Paid Date")){
            lastEmiPaidDateEdt.setText(value);
        }else if(title.equalsIgnoreCase("Next EMI Due Date")){
            nextEmiDueDateEdt.setText(value);
        }else if(title.equalsIgnoreCase("Final EMI Date")){
            finalEmiDateEdt.setText(value);
        }

    }

    @Override
    public void updateIndividualEditTextValue(String value, String title) {

    }

    public class GenericTextWatcher implements TextWatcher{
        EditText et;

        public GenericTextWatcher(EditText et ) {
           this.et=et;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            switch (et.getId()){
                case R.id.loanStartDate_edt:
                    loanStartDate.setErrorEnabled(false);
                    loanStartDate.setError(null);
                    break;
                case  R.id.firstEmiPaidDate_edt:
                    firstEmiPaidDate.setErrorEnabled(false);
                    firstEmiPaidDate.setError(null);
                    break;
                case R.id.lastEmiPaidDate_edt:
                    lastEmiPaidDate.setErrorEnabled(false);
                    lastEmiPaidDate.setError(null);
                    break;
                 case R.id.nextEmiDueDate_edt:
                     nextEmiDueDate.setErrorEnabled(false);
                     nextEmiDueDate.setError(null);
                    break;
                case R.id.finalEmiDate_edt:
                    finalEmiInputLayout.setErrorEnabled(false);
                    finalEmiInputLayout.setError(null);
                   break;


            }
        }
    }
}


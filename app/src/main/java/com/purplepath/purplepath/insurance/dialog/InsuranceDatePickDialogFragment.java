//package com.purplepath.purplepath.insurance.dialog;
//
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.NumberPicker;
//import android.widget.TextView;
//
//import com.fourmob.datetimepicker.Utils;
//import com.finobot.finobot.R;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.insurance.DialogFragmentInsurancesCallbackInterface;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.Locale;
//
///**
// * Created by Suresh on 06/02/17.
// */
//
//
//
//public class InsuranceDatePickDialogFragment extends DialogFragment implements View.OnClickListener{
//    private final static String TAG = InsuranceDatePickDialogFragment.class.getCanonicalName();
//    private DialogFragmentInsurancesCallbackInterface callbackInterfaceInsurances;
//
//    public static InsuranceDatePickDialogFragment newInstance(DialogFragmentInsurancesCallbackInterface callbackInterface, String dob, String age, String servicesText) {
//
//        Bundle args = new Bundle();
//        args.putString("DateOfBirth", dob);
//        args.putString("Age", age);
//        args.putString("GotServiceText", servicesText);
//        InsuranceDatePickDialogFragment fragment = new InsuranceDatePickDialogFragment();
//        fragment.callbackInterfaceInsurances = callbackInterface;
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//    private static final String[] DATES = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
//            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
//    private static final String[] MONTHS_IN_ENGLISH = new String[]{"January"," February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//    private NumberPicker mDayPicker, mMonthPicker, mYearPicker;
//    private FloatingActionButton numberPicker_fab;
//    private  TextView textview_Display_year_Age, calenderHeadingTextview ;
//    boolean isDateset = false;
//    private int Year1[];
//    private ImageView uparrow1,uparrow2,downarrow1,downarrow2;
//    private String yearToSave, monthToSave, dayToSave, calenderHeading, CurrentBelowTextToBeUpdate , gotServiceText , dobyearToSave, dobmonthToSave, dobdayToSave;
//    private Button  clear, set;
//    private String Birthdate;
//    private Dialog addView;
//    private String year, month, day;
//    private  LinearLayout yearPickerlayout;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        View calenderpickerview = inflater.inflate(R.layout.calender_number_date_picker, container, false);
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        uparrow1 = (ImageView) calenderpickerview.findViewById(R.id.uparrow1);
//        uparrow2 = (ImageView) calenderpickerview.findViewById(R.id.uparrow2);
//        downarrow1 = (ImageView) calenderpickerview.findViewById(R.id.downarrow1);
//        downarrow2 = (ImageView) calenderpickerview.findViewById(R.id.downarrow2);
//        calenderHeadingTextview = (TextView) calenderpickerview.findViewById(R.id.Calender_heading);
//        mDayPicker = (NumberPicker) calenderpickerview.findViewById(R.id.numberPicker1);
//        mMonthPicker = (NumberPicker) calenderpickerview.findViewById(R.id.numberPicker2);
//        mYearPicker = (NumberPicker) calenderpickerview.findViewById(R.id.numberPicker3);
//        textview_Display_year_Age = (TextView) calenderpickerview.findViewById(R.id.textview_Display_year_Age);
//        set = (Button) calenderpickerview.findViewById(R.id.pickerSetButton);
//        clear = (Button) calenderpickerview.findViewById(R.id.pickerClearButton);
//        yearPickerlayout = (LinearLayout) calenderpickerview.findViewById(R.id.yearPicker);
//        numberPicker_fab = (FloatingActionButton) calenderpickerview.findViewById(R.id.numberPicker_fab);
//        calenderHeading = (String) getArguments().get("DateOfBirth");
//        CurrentBelowTextToBeUpdate = (String) getArguments().getSerializable("Age");
//        gotServiceText = (String) getArguments().getSerializable("GotServiceText");
//        Log.i(TAG, " New Instance text should be update  " + " calenderHeading " + calenderHeading + "CurrentBelowTextToBeUpdate" + CurrentBelowTextToBeUpdate + gotServiceText);
//        textview_Display_year_Age.setVisibility(View.GONE);
//        numberPicker_fab.setVisibility(View.GONE);
//        set.setOnClickListener(this);
//        clear.setOnClickListener(this);
//        numberPicker_fab.setOnClickListener(this);
//
//        if (calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mterm_years)) {
//
//
//            calenderHeadingTextview.setText(calenderHeading);
//            textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate);
//            Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);
//            getCurrentDate();
//            pickerdisplayOnlyYear(gotServiceText);
//        }
//        else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mpolicy_issue_date)){
//
//            calenderHeadingTextview.setText(calenderHeading);
//            textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate );
//            Log.i(TAG, " Policy Issue Date gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);
//
//            UtileKit.pickerviewpastYear(mDayPicker,mMonthPicker,mYearPicker,gotServiceText);
//
//
//        }
//        else  if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mlast_prem_date)){
//
//
//            calenderHeadingTextview.setText(calenderHeading);
//            textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate );
//            Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);
//            UtileKit.pickerviewpastYear(mDayPicker,mMonthPicker,mYearPicker,gotServiceText);
//
//        }
//        else  if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mnext_prem_date)){
//            calenderHeadingTextview.setText(calenderHeading);
//            textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate );
//            Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);
//            UtileKit.pickerviewFuture(mDayPicker,mMonthPicker,mYearPicker,gotServiceText);
//
//        }
//        else  if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mprem_due_date)){
//
//
//            calenderHeadingTextview.setText(calenderHeading);
//            textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate );
//            Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);
//            UtileKit.pickerviewFuture(mDayPicker,mMonthPicker,mYearPicker,gotServiceText);
//
//        }
//        else  if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mpolicy_end_date)){
//
//
//            calenderHeadingTextview.setText(calenderHeading);
//            textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate );
//            Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);
//
//            UtileKit.pickerviewFuture(mDayPicker,mMonthPicker,mYearPicker,gotServiceText);
//
//        }
//        else  if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mmaturity_date)){
//            calenderHeadingTextview.setText(calenderHeading);
//            textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate );
//            Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);
//
//            UtileKit.pickerviewFuture(mDayPicker,mMonthPicker,mYearPicker,gotServiceText);
//
//        }
//
//        return calenderpickerview;
//    }
//    private void pickerdisplayOnlyYear(String gotServiceText){
//        mDayPicker.setVisibility(View.GONE);
//        mMonthPicker.setVisibility(View.GONE);
//        uparrow1.setVisibility(View.GONE);
//        uparrow2.setVisibility(View.GONE);
//        downarrow1.setVisibility(View.GONE);
//        downarrow2.setVisibility(View.GONE);
//        mMonthPicker.setMinValue(0);
//        mMonthPicker.setMaxValue(MONTHS_IN_ENGLISH.length - 1);
//        mMonthPicker.setDisplayedValues(MONTHS_IN_ENGLISH);
//        mMonthPicker.setWrapSelectorWheel(true);
//        mMonthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//
//
//        yearGeneratorFuture();
//        String[] yearString = Arrays.toString(Year1).split("[\\[\\]]")[1].split(", ");
//        Log.i(TAG, yearString[0] + "=yearString[0] " + yearString[116] + "=yearString[116]");
//        Log.i(TAG, "Current Year " + yearString.length);
//
//        mYearPicker.setMinValue(0);
//        try {
////            mYearPicker.setMaxValue(yearString.length - 1);
//            mYearPicker.setMaxValue(Integer.parseInt(year) - 1900 );
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mYearPicker.setDisplayedValues(yearString);
//        mYearPicker.setWrapSelectorWheel(true);
//        mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//
//
//        mDayPicker.setMinValue(0);
//
//        mDayPicker.setMaxValue(maxDaysInMonth(mYearPicker.getValue() + 1900, mMonthPicker.getValue()) - 1);
//        mDayPicker.setDisplayedValues(DATES);
//        mDayPicker.setWrapSelectorWheel(true);
//        mDayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//
//        Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);
//        try{
//            if (!gotServiceText.equalsIgnoreCase(" ")) {
//                if (!gotServiceText.equals("0") ) {
//                    Log.i(TAG, " gotServiceText from services  in condition" + CurrentBelowTextToBeUpdate + " " + gotServiceText);
//                    int yy = Integer.parseInt(gotServiceText);
//                    Log.i(TAG, " gotServiceText updateyear yy " + yy);
//                    mYearPicker.setValue(yy - 27);
//                } else {
//                    int yy = Integer.parseInt(year);
//                    Log.i(TAG, " gotServiceText updateyear yy in else" + yy);
//                    mYearPicker.setValue(yy-27);
//                }
//            }
//        }catch (Exception e){
//            int yy = Integer.parseInt(year);
//            Log.i(TAG, " gotServiceText updateyear yy in else" + yy);
//            mYearPicker.setValue(yy-27);
//            e.printStackTrace();
//        }
//
//
//        View edtView = mMonthPicker.getFocusedChild();
//
//        if (null != edtView && edtView instanceof EditText) {
//            edtView.setEnabled(false);
//        }
//
//        mMonthPicker.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return false;
//            }
//        });
//
//        mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                Log.v(TAG, "oldVal: " + oldVal + "newVal: " + newVal);
//                int maxdays = maxDaysInMonth(mYearPicker.getValue() + 1900, newVal);
//                mDayPicker.setMaxValue(maxdays - 1);
//
//                if ((mYearPicker.getValue() + 1900) == Integer.parseInt(year)) {
//                    mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
//                    if (newVal == Integer.parseInt(month) - 1) {
//                        mDayPicker.setMaxValue(Integer.parseInt(day) - 1);
//                    }
//                }
//                monthToSave = " ";
//                monthToSave = getMonthString(newVal);
//                Log.i(TAG, "Current monthToSave " + monthToSave);
//            }
//        });
//        mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//
//
//                yearToSave = newVal + 2017 + "";
//                Log.i(TAG, "setOnValueChangedListener newVal " + newVal);
//                Log.i(TAG, "setOnValueChangedListener newValyearToSave " + yearToSave);
//            }
//        });
//
//
//        mDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//
//                mDayPicker.setMaxValue(maxDaysInMonth(mYearPicker.getValue() + 1900, mMonthPicker.getValue()) - 1);
//                if ((mYearPicker.getValue() + 1900) == Integer.parseInt(year)) {
//                    mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
//                    if (mMonthPicker.getValue() == (Integer.parseInt(month) - 1)) {
//                        mDayPicker.setMaxValue(Integer.parseInt(day) - 1);
//                    }
//                }
//                dayToSave = " ";
//                dayToSave = newVal + 1 + "";
//                Log.i(TAG, "Current dayToSave " + dayToSave);
//            }
//        });
//
//    }
//    private String displayYearCalulation(String monthtosetinEdittext) {
//
//        Log.i(TAG,"displayYearCalulation parameter year"+ monthtosetinEdittext );
//
//        int selectedYear  = Integer.parseInt(monthtosetinEdittext);
//        String yearValue= "";
//        try {
//            GregorianCalendar calendar = new GregorianCalendar();
//            final int currentYear = calendar.get(Calendar.YEAR);
//            if (selectedYear < currentYear) {
//                yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYear(selectedYear));
//                textview_Display_year_Age.setText(yearValue);
//            } else {
//                yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYearisGreater(selectedYear));
//                textview_Display_year_Age.setText(yearValue);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return yearValue;
//    }
//
//    private void getCurrentDate() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
//        Date date = new Date();
//        Log.i("getCurrentDate", "  getCurrentDate();date" + dateFormat.format(date));
//        String[] items1 = dateFormat.format(date).split("/");
//        year = items1[0];
//        month = items1[1];
//        day = items1[2];
//        Log.i("getCurrentDate", "date" + year + " <-year" + month + " <-month" + day + " <-day");
//
//    }
//
//
//
//
//    private int getMonthIndex(String month) {
//        Log.i("DatePickDialog","getMonthIndex month "+   month);
//        int index = 11, length = MONTHS_IN_ENGLISH.length;
//        for (int i = 0; i < length; i++) {
//            if (month.equalsIgnoreCase(MONTHS_IN_ENGLISH[i]))
//                index = i ;
//        }
//
//
//        return index;
//    }
//
//
//    private String getMonthString(int i) {
//
//        return MONTHS_IN_ENGLISH[i];
//    }
//
//
//    private int maxDaysInMonth(int year, int month) {
//
//        Calendar mycal = new GregorianCalendar(year, month, 1);
//        return mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
//
//    }
//
//
//
//    private void yearGeneratorFuture() {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
//        Date date = new Date();
//        Log.i("yearGeneratorFuture", "yearGeneratorFuture date" + dateFormat.format(date));
//        String[] items1 = dateFormat.format(date).split("/");
//        year = items1[0];
//        month = items1[1];
//        day = items1[2];
//        Log.i("yearGeneratorFuture", "yearGeneratorFuture year" + year);
//        int currentYear = Integer.parseInt(year);
//        Year1 = new int[118];
//        for (int i = 0; i < 118; i++) {
//            Year1[i] =  i + currentYear;
//            Log.i("yearGeneratorFuture", "yearGeneratorFuture Year1[i]" + Year1[i]);
//
//        }
//    }
//
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.pickerSetButton: {
//
//                if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mterm_years)){
//                    try {
//                        isDateset = true;
//                        String dateMonthYear =  setdateandYearonlyyear();
//
//                        dobyearToSave = setDateFormat(dateMonthYear);
//                        Log.i("insurance DatePicker","pickerSetButton dateMonthYear "+ dateMonthYear + "dobyearToSave "+dobyearToSave );
//                        callbackInterfaceInsurances.upadateInsurancesYears( dobyearToSave);
//                        getDialog().dismiss();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mpolicy_issue_date)){
//                    try {
//                        isDateset = true;
//                        String dateMonthYear =  setdateandYearPastyear();
//                        Log.i("insurance DatePicker","pickerSetButton dateMonthYear Policy Issue Date "+ dateMonthYear );
//                        callbackInterfaceInsurances.upadatepolicy_issue_date( dateMonthYear);
//                        getDialog().dismiss();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mlast_prem_date)){
//                    try {
//                        isDateset = true;
//                        String dateMonthYear =  setdateandYearPastyear();
//                        Log.i("insurance DatePicker","pickerSetButton dateMonthYear Last Premium Paid Date "+ dateMonthYear );
//                        callbackInterfaceInsurances.upadatelast_Premium_Paid_Date( dateMonthYear);
//                        getDialog().dismiss();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mnext_prem_date)){
//                    try {
//                        isDateset = true;
//                        String dateMonthYear =  setdateandYear();
//                        Log.i("insurance DatePicker","pickerSetButton dateMonthYear Next Premium Due Date "+ dateMonthYear );
//                        callbackInterfaceInsurances.upadatenext_Premium_Due_Date( dateMonthYear);
//                        getDialog().dismiss();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mprem_due_date)){
//                    try {
//                        isDateset = true;
//                        String dateMonthYear =  setdateandYear();
//                        Log.i("insurance DatePicker","pickerSetButton dateMonthYear Premium Due Till Date "+ dateMonthYear );
//                        callbackInterfaceInsurances.upadatePremium_Due_Till_Date( dateMonthYear);
//                        getDialog().dismiss();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mpolicy_end_date)){
//                    try {
//                        isDateset = true;
//                        String dateMonthYear =  setdateandYear();
//                        Log.i("insurance DatePicker","pickerSetButton dateMonthYear Policy End Date "+ dateMonthYear );
//                        callbackInterfaceInsurances.upadatepolicy_end_date( dateMonthYear);
//                        getDialog().dismiss();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mmaturity_date)){
//                    try {
//                        isDateset = true;
//                        String dateMonthYear =  setdateandYear();
//                        Log.i("insurance DatePicker","pickerSetButton dateMonthYear Maturity Date "+ dateMonthYear );
//                        callbackInterfaceInsurances.upadatematurity_Date( dateMonthYear);
//                        getDialog().dismiss();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//
//            break;
//            case R.id.pickerClearButton: {
//
//                if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mterm_years)){
//                    try {
//                        String ageOfcalulation = clearthepickeryearOnly();
//
//                        callbackInterfaceInsurances.upadateInsurancesYears(" ");
////                        callbackInterfaceInsurances.upadateInsurancesYears(ageOfcalulation);
//                        getDialog().dismiss();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mpolicy_issue_date)){
//                    try {
//                        String ageOfcalulation = clearthepickerdetails();
//
//                        callbackInterfaceInsurances.upadatepolicy_issue_date(ageOfcalulation);
//                        getDialog().dismiss();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mlast_prem_date)){
//                    try {
//                        isDateset = true;
//                        String dateMonthYear = clearthepickerdetails();
//                        Log.i("insurance DatePicker","pickerSetButton dateMonthYear Last Premium Paid Date "+ dateMonthYear );
//                        callbackInterfaceInsurances.upadatelast_Premium_Paid_Date( dateMonthYear);
//                        getDialog().dismiss();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mnext_prem_date)){
//                    try {
//                        isDateset = true;
//                        String dateMonthYear =  clearthepickerdetails();
//                        Log.i("insurance DatePicker","pickerSetButton dateMonthYear Last Premium Paid Date "+ dateMonthYear );
//                        callbackInterfaceInsurances.upadatenext_Premium_Due_Date( dateMonthYear);
//                        getDialog().dismiss();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mprem_due_date)){
//                    try {
//                        isDateset = true;
//                        String dateMonthYear =  clearthepickerdetails();
//                        Log.i("insurance DatePicker","pickerSetButton dateMonthYear Premium Due Till Date "+ dateMonthYear );
//                        callbackInterfaceInsurances.upadatePremium_Due_Till_Date( dateMonthYear);
//                        getDialog().dismiss();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mpolicy_end_date)){
//                    try {
//                        isDateset = true;
//                        String dateMonthYear =  clearthepickerdetails();
//                        Log.i("insurance DatePicker","pickerSetButton dateMonthYear Policy End Date "+ dateMonthYear );
//                        callbackInterfaceInsurances.upadatepolicy_end_date( dateMonthYear);
//                        getDialog().dismiss();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                else if(calenderHeading.equalsIgnoreCase(InsuranceDialogFragment.mmaturity_date)){
//                    try {
//                        isDateset = true;
//                        String dateMonthYear =  clearthepickerdetails();
//                        Log.i("insurance DatePicker","pickerSetButton dateMonthYear Maturity Date "+ dateMonthYear );
//                        callbackInterfaceInsurances.upadatematurity_Date( dateMonthYear);
//                        getDialog().dismiss();
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            break;
//
//            case R.id.numberPicker_fab: {
//                try {
//                    getDialog().dismiss();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            break;
//        }
//    }
//
//    private String setdateandYear() {
//        dayToSave = (mDayPicker.getValue() + 1) + "";
//        int year = Calendar.getInstance().get(Calendar.YEAR);
//        dobyearToSave = (mYearPicker.getValue() + year  ) + "";
////        dobyearToSave = String.valueOf(year);
//        monthToSave = getMonthString(mMonthPicker.getValue());
//        Log.i(TAG, "Current dayToSave onclick " + dayToSave);
//        Log.i(TAG, "Current dobyearToSave onclick " + dobyearToSave);
//        Log.i(TAG, "Current yearToSave onclick " + yearToSave);
//        int monthtosetinEdittext = getMonthIndex(monthToSave) + 1;
//        String ageOfcalulation = displayYearCalulation(dobyearToSave);
//        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
//        Log.i("DatePickDialog", " Dob year" + ageOfcalulation);
//        Log.i("DatePickDialog", " Dob 2" + dayToSave + " " + monthtosetinEdittext + " " + dobyearToSave);
//        String dateMonthYear = dayToSave+"-"+monthtosetinEdittext+"-"+dobyearToSave ;
//        return dateMonthYear;
//    }
//    private String setdateandYearPastyear() {
//        dayToSave = (mDayPicker.getValue() + 1) + "";
//        int year = Calendar.getInstance().get(Calendar.YEAR);
//        dobyearToSave = (mYearPicker.getValue() + 1900  ) + "";
////        dobyearToSave = String.valueOf(year);
//        monthToSave = getMonthString(mMonthPicker.getValue());
//        Log.i(TAG, "Current dayToSave onclick " + dayToSave);
//        Log.i(TAG, "Current dobyearToSave onclick " + dobyearToSave);
//        Log.i(TAG, "Current yearToSave onclick " + yearToSave);
//        int monthtosetinEdittext = getMonthIndex(monthToSave) + 1;
//        String ageOfcalulation = displayYearCalulation(dobyearToSave);
//        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
//        Log.i("DatePickDialog", " Dob year" + ageOfcalulation);
//        Log.i("DatePickDialog", " Dob 2" + dayToSave + " " + monthtosetinEdittext + " " + dobyearToSave);
//        String dateMonthYear = dayToSave+"-"+monthtosetinEdittext+"-"+dobyearToSave ;
//        return dateMonthYear;
//    }
//    private String setdateandYearonlyyear() {
//        dayToSave = (mDayPicker.getValue() + 1) + "";
//        int year = Calendar.getInstance().get(Calendar.YEAR);
//        dobyearToSave = (mYearPicker.getValue() + year) + "";
//        monthToSave = getMonthString(mMonthPicker.getValue());
//        Log.i(TAG, " setdateandYearonlyyear Current dayToSave onclick " + dayToSave);
//        Log.i(TAG, "setdateandYearonlyyear Current dobyearToSave onclick " + dobyearToSave);
//        Log.i(TAG, "setdateandYearonlyyear Current yearToSave onclick " + yearToSave);
//        int monthtosetinEdittext = getMonthIndex(monthToSave) + 1;
//        String ageOfcalulation = displayYearCalulation(dobyearToSave);
//        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
//        Log.i("DatePickDialog", "setdateandYearonlyyear  Dob year" + ageOfcalulation);
//        Log.i("DatePickDialog", " setdateandYearonlyyear Dob 2" + dayToSave + " " + monthtosetinEdittext + " " + dobyearToSave);
//        String dateMonthYear = dobyearToSave ;
//        return dateMonthYear;
//    }
//
//
//
//
//    private String clearthepickeryearOnly() {
//        int clearyear = Calendar.getInstance().get(Calendar.YEAR);
//        int clearmonth = Calendar.getInstance().get(Calendar.MONTH);
//        int cleardate = Calendar.getInstance().get(Calendar.DATE);
//        mYearPicker.setValue(Integer.parseInt(year) - clearyear);
//        mMonthPicker.setValue(Integer.parseInt(month) - 1);
//        mDayPicker.setValue(Integer.parseInt(day) - 1);
//
//
//        dayToSave = String.valueOf(cleardate);
//        monthToSave = String.valueOf(clearmonth) + 1;
//        yearToSave = String.valueOf(year);
//
////        String ageOfcalulation = displayYearCalulation(yearToSave);
//        String ageOfcalulation =  yearToSave;
//        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
////                        callbackInterface.upadateDOB(dayToSave + "-" + monthToSave + "-" + yearToSave);
//        Log.i(TAG,"Date MOnth Year " + dayToSave + " "+ monthToSave + " " + yearToSave);
//
//
//        return ageOfcalulation;
//    }
//    private String clearthepickerdetails() {
//        dayToSave = " ";
//        monthToSave = " ";
//        yearToSave = " ";
//
//        Log.i(TAG,"Date MOnth Year " + dayToSave + " "+ monthToSave + " " + yearToSave);
//
//        if(dayToSave ==" "&&  monthToSave ==" " &&  yearToSave ==" "){
//            String datemonthYear = " ";
//            return datemonthYear;
//        } else {
//            String datemonthYear = dayToSave + "-" + monthToSave + "-" + yearToSave;
//            return datemonthYear;
//        }
//
//
//    }
//
//    private String setDateFormat(String date) {
//        String output = "";
//        try {
//            if (date.trim().length() != 0) {
//                String dateAray[] = date.split("-");
//                output = dateAray[2];
//                Log.i(TAG, " Date Splits " + output);
//
//            }
//        } catch (ArrayIndexOutOfBoundsException e) {
//            return date;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return date;
//        }
//        return output;
//    }
//
//
//}
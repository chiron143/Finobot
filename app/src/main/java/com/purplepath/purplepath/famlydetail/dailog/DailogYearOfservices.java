package com.purplepath.purplepath.famlydetail.dailog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.finobot.finobot.R;

import java.util.Arrays;

/**
 * Created by bertrandrussellsakthees on 09/02/17.
 */

public class DailogYearOfservices extends DialogFragment implements View.OnClickListener{
    private final static String TAG = DailogYearOfservices.class.getCanonicalName();
    private DialogNoyearInterface callbackInterface;

    public static DailogYearOfservices newInstance(DialogNoyearInterface callbackInterface, String dob, String age, String servicesText) {

        Bundle args = new Bundle();
        args.putString("DateOfBirth", dob);
        args.putString("Age", age);
        args.putString("GotServiceText", servicesText);
        DailogYearOfservices fragment = new DailogYearOfservices();
        fragment.callbackInterface = callbackInterface;
        fragment.setArguments(args);
        return fragment;
    }

    private static final String[] DATES = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private static final String[] MONTHS_IN_TAMIL = new String[]{"January", " February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private NumberPicker mDayPicker, mMonthPicker, mYearPicker;
    private FloatingActionButton numberPicker_fab;
    private TextView textview_Display_year_Age, calenderHeadingTextview;
    boolean isDateset = false;
    private int Year1[];
    private ImageView uparrow1, uparrow2, downarrow1, downarrow2;
    private String yearToSave, monthToSave, dayToSave, calenderHeading, CurrentBelowTextToBeUpdate, gotServiceText, dobyearToSave, dobmonthToSave, dobdayToSave;
    private Button clear, set;
    private String Birthdate;
    private Dialog addView;
    private String year, month, day;
    private LinearLayout yearPickerlayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View calenderpickerview = inflater.inflate(R.layout.calender_number_date_picker, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.white);
        uparrow1 = calenderpickerview.findViewById(R.id.uparrow1);
        uparrow2 = calenderpickerview.findViewById(R.id.uparrow2);
        downarrow1 = calenderpickerview.findViewById(R.id.downarrow1);
        downarrow2 = calenderpickerview.findViewById(R.id.downarrow2);
        calenderHeadingTextview = calenderpickerview.findViewById(R.id.Calender_heading);
        mDayPicker = calenderpickerview.findViewById(R.id.numberPicker1);
        mMonthPicker = calenderpickerview.findViewById(R.id.numberPicker2);
        mYearPicker = calenderpickerview.findViewById(R.id.numberPicker3);
        textview_Display_year_Age = calenderpickerview.findViewById(R.id.textview_Display_year_Age);
        set = calenderpickerview.findViewById(R.id.pickerSetButton);
        clear = calenderpickerview.findViewById(R.id.pickerClearButton);
        yearPickerlayout = calenderpickerview.findViewById(R.id.yearPicker);
        numberPicker_fab = calenderpickerview.findViewById(R.id.numberPicker_fab);
        calenderHeading = (String) getArguments().get("DateOfBirth");
        CurrentBelowTextToBeUpdate = (String) getArguments().getSerializable("Age");
        gotServiceText = (String) getArguments().getSerializable("GotServiceText");
        Log.i(TAG, " New Instance text should be update  " + " calenderHeading " + calenderHeading + "CurrentBelowTextToBeUpdate" + CurrentBelowTextToBeUpdate + gotServiceText);
        set.setOnClickListener(this);
        clear.setOnClickListener(this);
        numberPicker_fab.setOnClickListener(this);
        if (calenderHeading.equalsIgnoreCase("Years of Service")) {

            calenderHeadingTextview.setText(calenderHeading);
            textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate);

            mDayPicker.setVisibility(View.GONE);
            mMonthPicker.setVisibility(View.GONE);
            uparrow1.setVisibility(View.GONE);
            uparrow2.setVisibility(View.GONE);
            downarrow1.setVisibility(View.GONE);
            downarrow2.setVisibility(View.GONE);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.weight = 3.0f;
            params.gravity = Gravity.CENTER;

            yearPickerlayout.setLayoutParams(params);


//           LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
//                   yearPickerlayout.getLayoutParams();
//           params.weight = 2.0f;
//           yearPickerlayout.setLayoutParams(params);
            yearGeneratorFuture();
            String[] yearString = Arrays.toString(Year1).split("[\\[\\]]")[1].split(", ");
            Log.i(TAG, yearString[0] + "=yearString[0] " + yearString[59] + "=yearString[59]");
            mYearPicker.setMinValue(0);
            try {
                mYearPicker.setMaxValue(yearString.length - 1);
//                mYearPicker.setMaxValue(Integer.parseInt(year) - 1900);

            } catch (Exception e) {
                e.printStackTrace();
            }
            mYearPicker.setDisplayedValues(yearString);
            mYearPicker.setWrapSelectorWheel(true);
            mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            try {
                if (!gotServiceText.equalsIgnoreCase("null")) {
//                    Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);
//
//                    String yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYear(Integer.parseInt(gotServiceText)));
////                   Log.i(TAG, " gotServiceText CurrentOrganization year " +yearValue );
//                    textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + " " + gotServiceText);
//
                    int yy = Integer.parseInt(gotServiceText);
                    Log.i(TAG, " gotServiceText updateyear yy " + yy);

                    mYearPicker.setValue(yy);

                }
                else{
                    mYearPicker.setValue(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mYearPicker.setValue(0);
            }

            mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {


                    yearToSave = newVal + 0 + "";
                    Log.i(TAG, "setOnValueChangedListener newVal " + newVal);
                    Log.i(TAG, "setOnValueChangedListener newValyearToSave " + yearToSave);
                }
            });


        }
        return calenderpickerview;
    }

    private void yearGeneratorFuture() {

        Year1 = new int[60];
        for (int i = 0; i < 60; i++) {
            Year1[i] =  0+ i;

        }
        Log.i(TAG, "Year1[0]" + Year1[0] + "Year1[116]" + Year1[59]);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pickerSetButton: {

                if(calenderHeading.equalsIgnoreCase("Years of Service")){
                    try {
                        isDateset = true;

                        yearToSave = (mYearPicker.getValue() ) + "";
                        Log.i("DatePickDialog", " setOnClickListener Dob yearToSave " + yearToSave);
                        String ageOfcalulation = yearToSave;
                        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                        callbackInterface.upadateYearofServices( ageOfcalulation);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }


            break;
            case R.id.pickerClearButton: {

                if (calenderHeading.equalsIgnoreCase("Years of Service")) {
                    try {
                        mYearPicker.setValue(0);
                        callbackInterface.upadateYearofServices("");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }


            break;

            case R.id.numberPicker_fab: {



                    try {
                        getDialog().dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
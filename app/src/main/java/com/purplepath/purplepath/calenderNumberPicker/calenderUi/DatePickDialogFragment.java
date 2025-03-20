package com.purplepath.purplepath.calenderNumberPicker.calenderUi;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.fourmob.datetimepicker.Utils;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.calenderNumberPicker.DialogFragmentCallbackInterface;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.fragments.PersonalDetailsFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;




public class DatePickDialogFragment extends BaseFragment implements View.OnClickListener{
    private final static String TAG = DatePickDialogFragment.class.getCanonicalName();
    private DialogFragmentCallbackInterface callbackInterface;

    public static DatePickDialogFragment newInstance(DialogFragmentCallbackInterface callbackInterface, String dob, String age, String servicesText) {

        Bundle args = new Bundle();
        args.putString("DateOfBirth", dob);
        args.putString("Age", age);
        args.putString("GotServiceText", servicesText);
        DatePickDialogFragment fragment = new DatePickDialogFragment();
        fragment.callbackInterface = callbackInterface;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private static final String[] DATES = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private static final String[] MONTHS_IN_TAMIL = new String[]{"January"," February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private NumberPicker mDayPicker, mMonthPicker, mYearPicker;
    private FloatingActionButton numberPicker_fab;
    private  TextView textview_Display_year_Age, calenderHeadingTextview ;
    boolean isDateset = false;
    private int Year1[];
    private ImageView uparrow1,uparrow2,downarrow1,downarrow2;
    private String yearToSave, monthToSave, dayToSave, calenderHeading, CurrentBelowTextToBeUpdate , gotServiceText , dobyearToSave, dobmonthToSave, dobdayToSave;
    private Button  clear, set;
    private String Birthdate;
    private Dialog addView;
    private String year, month, day;
    private  LinearLayout yearPickerlayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View calenderpickerview = inflater.inflate(R.layout.calender_number_date_picker, container, false);
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.white);
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
        calenderHeading =(String) getArguments().get("DateOfBirth");
        CurrentBelowTextToBeUpdate = (String) getArguments().getSerializable("Age");
        gotServiceText = (String) getArguments().getSerializable("GotServiceText");
        Log.i(TAG," New Instance text should be update  " +" calenderHeading "+ calenderHeading + "CurrentBelowTextToBeUpdate :" + CurrentBelowTextToBeUpdate + gotServiceText);
        set.setOnClickListener(this);
        clear.setOnClickListener(this);
        numberPicker_fab.setOnClickListener(this);
        Log.i(TAG," PersonalDetailsFragment.life_of_expectancy_age 01 :" + PersonalDetailsFragment.life_of_expectancy_age );

       if(calenderHeading.equalsIgnoreCase(PersonalDetailsFragment.life_of_expectancy_age)){
           Log.i(TAG," PersonalDetailsFragment.life_of_expectancy_age " + PersonalDetailsFragment.life_of_expectancy_age );
           calenderHeadingTextview.setText(calenderHeading);


           UtileKit.displayOnlyYearCalenderFuture(mDayPicker,
                   mMonthPicker,mYearPicker,uparrow1,uparrow2,downarrow1,downarrow2,
                   yearPickerlayout,textview_Display_year_Age, gotServiceText, PersonalDetailsFragment.expectancy_age);

       }
       else if(calenderHeading.equalsIgnoreCase(PersonalDetailsFragment.planned_retirement_age)){
           calenderHeadingTextview.setText(calenderHeading);

           UtileKit.displayOnlyYearCalenderFuture(mDayPicker,
                   mMonthPicker,mYearPicker,uparrow1,uparrow2,downarrow1,downarrow2,
                   yearPickerlayout,textview_Display_year_Age, gotServiceText, CurrentBelowTextToBeUpdate);



       }
       else if(calenderHeading.equalsIgnoreCase("CurrentResidence")){
           calenderHeadingTextview.setText(calenderHeading);
           textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate);

           mDayPicker.setVisibility(View.GONE);
           mMonthPicker.setVisibility(View.GONE);
           uparrow1.setVisibility(View.GONE);
           uparrow2.setVisibility(View.GONE);
           downarrow1.setVisibility(View.GONE);
           downarrow2.setVisibility(View.GONE);
           yearGeneratorpastYear();
           String[] yearString = Arrays.toString(Year1).split("[\\[\\]]")[1].split(", ");
           Log.i(TAG, yearString[0] + "=yearString[0] " + yearString[116] + "=yearString[116]");
           mYearPicker.setMinValue(0);
           try {
               //mYearPicker.setMaxValue(yearString.length - 1);
               mYearPicker.setMaxValue(Integer.parseInt(year) - 1900);

           } catch (Exception e) {
               e.printStackTrace();
           }
           mYearPicker.setDisplayedValues(yearString);
           mYearPicker.setWrapSelectorWheel(true);
           mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

           try{
               if (!gotServiceText.equalsIgnoreCase("null")) {
                   Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);

                   String yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYear(Integer.parseInt(gotServiceText)));
//                   Log.i(TAG, " gotServiceText CurrentOrganization year " +yearValue );
                   textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + " " + gotServiceText);

                   int yy = Integer.parseInt(gotServiceText)  ;
                   Log.i(TAG, " gotServiceText updateyear yy " + yy  );

                   mYearPicker.setValue(yy);

               }
           }catch (Exception e){
               e.printStackTrace();
           }

           mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
               @Override
               public void onValueChange(NumberPicker picker, int oldVal, int newVal) {


                   yearToSave = newVal + 2017 + "";
                   Log.i(TAG, "setOnValueChangedListener newVal " + newVal);
                   Log.i(TAG, "setOnValueChangedListener newValyearToSave " + yearToSave);
               }
           });



       }
        else if(calenderHeading.equalsIgnoreCase(PersonalDetailsFragment.current_organization)){
            calenderHeadingTextview.setText(calenderHeading);
           textview_Display_year_Age.setVisibility(View.GONE);
           numberPicker_fab.setVisibility(View.GONE);
           textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate);
           Log.i(TAG, "PersonalDetailsFragment.current_organization  " + PersonalDetailsFragment.current_organization);
           try{
               if (!gotServiceText.equalsIgnoreCase("null")) {


                   Log.i(TAG, " gotServiceText  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);
                   textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + " " + gotServiceText);
                   int year = Calendar.getInstance().get(Calendar.YEAR);
                   int serviceYearvalue = Integer.parseInt(gotServiceText);
                   int updateyear = year - serviceYearvalue ;
                   Log.i(TAG, "updateyear " +updateyear);
                   String[] yearString = Arrays.toString(Year1).split("[\\[\\]]")[1].split(", ");
                   Log.i(TAG, yearString[0] + "=yearString[0] " + yearString[116] + "=yearString[116]");
                   mYearPicker.setDisplayedValues(new String[]{yearString[updateyear]});
//                   for (int i=0;i<yearString.length;i++)
//                   {
//
//                       if(yearString[i].equalsIgnoreCase(String.valueOf(updateyear)))
//                       {
//                           Log.i(TAG, "updateyear " +yearString[i]);
//                           mYearPicker.setValue(Integer.parseInt(yearString[i]) );
//                       }
//                   }
               }
           }catch (Exception e){
               e.printStackTrace();
           }
            mDayPicker.setVisibility(View.GONE);
            mMonthPicker.setVisibility(View.GONE);
            uparrow1.setVisibility(View.GONE);
            uparrow2.setVisibility(View.GONE);
            downarrow1.setVisibility(View.GONE);
            downarrow2.setVisibility(View.GONE);
           yearGeneratorpastYear();
            String[] yearString = Arrays.toString(Year1).split("[\\[\\]]")[1].split(", ");
            Log.i(TAG, yearString[0] + "=yearString[0] " + yearString[116] + "=yearString[116]");
           Log.i(TAG, " CurrentOrganization year " + year);
           mYearPicker.setValue(Integer.parseInt(year) - 1900);
            mYearPicker.setMinValue(0);
            try {
                //mYearPicker.setMaxValue(yearString.length - 1);
                mYearPicker.setMaxValue(Integer.parseInt(year) - 1900);

            } catch (Exception e) {
                e.printStackTrace();
            }
            mYearPicker.setDisplayedValues(yearString);
            mYearPicker.setWrapSelectorWheel(true);
            mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

           try{
               if (!gotServiceText.equalsIgnoreCase("null")) {
                   Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);

                   String yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYear(Integer.parseInt(gotServiceText)));
//                   Log.i(TAG, " gotServiceText CurrentOrganization year " +yearValue );
                   textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + " " + gotServiceText);

                   int yy = Integer.parseInt(gotServiceText)  ;
                   Log.i(TAG, " gotServiceText updateyear yy " + yy  );

                   mYearPicker.setValue(yy);

               }
           }catch (Exception e){
               e.printStackTrace();
           }

            mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {


                    yearToSave = newVal + 1900 + "";
                    Log.i(TAG, "setOnValueChangedListener newVal " + newVal);
                    Log.i(TAG, "setOnValueChangedListener newValyearToSave " + yearToSave);
                }
            });



        } else  if(calenderHeading.equalsIgnoreCase("Marriage Date")){

           calenderHeadingTextview.setText(calenderHeading);
//           textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate);

           getCurrentDate();

           if (null == mDayPicker) return mDayPicker;
           if (null == mMonthPicker) return mMonthPicker;
           if (null == mYearPicker) return mYearPicker;


           mMonthPicker.setMinValue(0);
           mMonthPicker.setMaxValue(MONTHS_IN_TAMIL.length - 1);
           mMonthPicker.setDisplayedValues(MONTHS_IN_TAMIL);
           mMonthPicker.setWrapSelectorWheel(true);
           mMonthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


           yearGenerator();
           String[] yearString = Arrays.toString(Year1).split("[\\[\\]]")[1].split(", ");
//            Log.i("spcheck", yearString[0] + "=yearString[0] " + yearString[116] + "=yearString[116]");

           mYearPicker.setMinValue(0);
           try {
               //mYearPicker.setMaxValue(yearString.length - 1);
               mYearPicker.setMaxValue(Integer.parseInt(year) - 1900);

           } catch (Exception e) {
               e.printStackTrace();
           }
           mYearPicker.setDisplayedValues(yearString);
           mYearPicker.setWrapSelectorWheel(true);
           mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


           mDayPicker.setMinValue(0);

           mDayPicker.setMaxValue(maxDaysInMonth(mYearPicker.getValue() + 1900, mMonthPicker.getValue()) - 1);
           mDayPicker.setDisplayedValues(DATES);
           mDayPicker.setWrapSelectorWheel(true);
           mDayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
           setCurrentDate();



           View edtView = mMonthPicker.getFocusedChild();

           if (null != edtView && edtView instanceof EditText) {
               edtView.setEnabled(false);
           }
           try{
               if (!gotServiceText.equalsIgnoreCase("null")) {
                   Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);

                   String split[] = gotServiceText.split("-");
                   Log.i(TAG, " gotServiceText split[0] year " +split[0]);
                   Log.i(TAG, " gotServiceText split[1] month " +split[1]);
                   Log.i(TAG, " gotServiceText split[2] date " +split[2]);
                   String ageCalculation = displayYearCalulation(split[0]);
                   textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + " " + ageCalculation);

                   int dd= Integer.parseInt(split[2]) - 1 ;
                   int yy = Integer.parseInt(split[0]) - 1900 ;
                   Log.i(TAG, " gotServiceText updateyear dd " +dd + "yy " + yy  +" month"+ split[1]);

                   mDayPicker.setValue(dd);
                   mYearPicker.setValue(yy);
                   mMonthPicker.setValue(Integer.parseInt(split[1]) - 1);
               }
           }catch (Exception e){
               e.printStackTrace();
           }
           mMonthPicker.setOnTouchListener(new View.OnTouchListener() {
               @Override
               public boolean onTouch(View v, MotionEvent event) {
                   return false;
               }
           });

           mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
               @Override
               public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                   Log.v("spcheck", "oldVal: " + oldVal + "newVal: " + newVal);
                   int maxdays = maxDaysInMonth(mYearPicker.getValue() + 1900, newVal);
                   mDayPicker.setMaxValue(maxdays - 1);

                   if ((mYearPicker.getValue() + 1900) == Integer.parseInt(year)) {
                       mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                       if (newVal == Integer.parseInt(month) - 1) {
                           mDayPicker.setMaxValue(Integer.parseInt(day) - 1);
                       }
                   }
                   monthToSave = getMonthString(newVal);

               }
           });

           mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
               @Override
               public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                   if ((newVal + 1900) == Integer.parseInt(year)) {
                       mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                       if (mMonthPicker.getValue() == Integer.parseInt(month) - 1) {
                           try {
                               mDayPicker.setMaxValue(Integer.parseInt(day));
                           }catch (Exception e){
                               e.printStackTrace();
                           }
                       }
                   } else {
                       mMonthPicker.setMaxValue(MONTHS_IN_TAMIL.length - 1);

                   }
                   if (mMonthPicker.getValue() == 1) {
                       int maxdays = maxDaysInMonth(newVal + 1900, 1);
                       mDayPicker.setMaxValue(maxdays - 1);
                       if ((newVal + 1900) == Integer.parseInt(year)) {
                           mDayPicker.setMaxValue(Integer.parseInt(day));
                       }
                   }

//                   yearToSave = newVal + 1900 + "";
                   dobyearToSave = newVal + 1900 + "";
               }
           });

           mDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
               @Override
               public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                   mDayPicker.setMaxValue(maxDaysInMonth(mYearPicker.getValue() + 1900, mMonthPicker.getValue()) - 1);
                   if ((mYearPicker.getValue() + 1900) == Integer.parseInt(year)) {
                       mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                       if (mMonthPicker.getValue() == (Integer.parseInt(month) - 1)) {
                           mDayPicker.setMaxValue(Integer.parseInt(day) - 1);
                       }
                   }
                   dayToSave = newVal + 1 + "";

               }
           });




       }
        else {

           calenderHeadingTextview.setText(calenderHeading);
           textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate );
           Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);


           getCurrentDate();

           if (null == mDayPicker) return mDayPicker;
           if (null == mMonthPicker) return mMonthPicker;
           if (null == mYearPicker) return mYearPicker;


           mMonthPicker.setMinValue(0);
           mMonthPicker.setMaxValue(MONTHS_IN_TAMIL.length - 1);
           mMonthPicker.setDisplayedValues(MONTHS_IN_TAMIL);
           mMonthPicker.setWrapSelectorWheel(true);
           mMonthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


           yearGenerator();
           String[] yearString = Arrays.toString(Year1).split("[\\[\\]]")[1].split(", ");
            Log.i(TAG, yearString[0] + "=yearString[0] " + yearString[116] + "=yearString[116]");
           Log.i(TAG, "Current Year " + yearString.length);

           mYearPicker.setMinValue(0);
           try {
               //mYearPicker.setMaxValue(yearString.length - 1);
               mYearPicker.setMaxValue(Integer.parseInt(year) - 1900);

           } catch (Exception e) {
               e.printStackTrace();
           }
           mYearPicker.setDisplayedValues(yearString);
           mYearPicker.setWrapSelectorWheel(true);
           mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


           mDayPicker.setMinValue(0);

           mDayPicker.setMaxValue(maxDaysInMonth(mYearPicker.getValue() + 1900, mMonthPicker.getValue()) - 1);
           mDayPicker.setDisplayedValues(DATES);
           mDayPicker.setWrapSelectorWheel(true);
           mDayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

           setCurrentDate();


           View edtView = mMonthPicker.getFocusedChild();

           if (null != edtView && edtView instanceof EditText) {
               edtView.setEnabled(false);
           }

           try{
               if (gotServiceText!=null) {
                   Log.i(TAG, " gotServiceText from services  " + CurrentBelowTextToBeUpdate + " " + gotServiceText);

                   String split[] = gotServiceText.split("-");
                   Log.i(TAG, " gotServiceText split[0] year " +split[0]);
                   Log.i(TAG, " gotServiceText split[1] month " +split[1]);
                   Log.i(TAG, " gotServiceText split[2] date " +split[2]);
                   String ageCalculation = displayYearCalulation(split[0]);
                   textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + " " + ageCalculation);

                   int dd= Integer.parseInt(split[2]) - 1 ;
                   int yy = Integer.parseInt(split[0]) - 1900 ;
                   Log.i(TAG, " gotServiceText updateyear dd " +dd + "yy " + yy  +" month"+ split[1]);

                   mDayPicker.setValue(dd);
                   mYearPicker.setValue(yy);
                   mMonthPicker.setValue(Integer.parseInt(split[1]) - 1);
               }
           }catch (Exception e){
               e.printStackTrace();
           }


           mMonthPicker.setOnTouchListener(new View.OnTouchListener() {
               @Override
               public boolean onTouch(View v, MotionEvent event) {
                   return false;
               }
           });

           mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
               @Override
               public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                   Log.v(TAG, "oldVal: " + oldVal + "newVal: " + newVal);
                   int maxdays = maxDaysInMonth(mYearPicker.getValue() + 1900, newVal);
                   mDayPicker.setMaxValue(maxdays - 1);

                   if ((mYearPicker.getValue() + 1900) == Integer.parseInt(year)) {
                       mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                       if (newVal == Integer.parseInt(month) - 1) {
                           mDayPicker.setMaxValue(Integer.parseInt(day) - 1);
                       }
                   }
                   monthToSave = " ";
                   monthToSave = getMonthString(newVal);
                   Log.i(TAG, "Current monthToSave " + monthToSave);
               }
           });

           mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
               @Override
               public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                   if ((newVal + 1900) == Integer.parseInt(year)) {
                       mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                       if (mMonthPicker.getValue() == Integer.parseInt(month) - 1) {
                           try {
                               mDayPicker.setMaxValue(Integer.parseInt(day));
                           }catch (Exception e){
                               e.printStackTrace();
                           }
                       }
                   } else {
                       mMonthPicker.setMaxValue(MONTHS_IN_TAMIL.length - 1);

                   }
                   if (mMonthPicker.getValue() == 1) {
                       int maxdays = maxDaysInMonth(newVal + 1900, 1);
                       mDayPicker.setMaxValue(maxdays - 1);
                       if ((newVal + 1900) == Integer.parseInt(year)) {
                           mDayPicker.setMaxValue(Integer.parseInt(day));
                       }
                   }
                   dobyearToSave = " ";
                   dobyearToSave = newVal + 1900 + "";
                   Log.i(TAG, "Current yearToSave " + dobyearToSave);
               }
           });

           mDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
               @Override
               public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                   mDayPicker.setMaxValue(maxDaysInMonth(mYearPicker.getValue() + 1900, mMonthPicker.getValue()) - 1);
                   if ((mYearPicker.getValue() + 1900) == Integer.parseInt(year)) {
                       mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                       if (mMonthPicker.getValue() == (Integer.parseInt(month) - 1)) {
                           mDayPicker.setMaxValue(Integer.parseInt(day) - 1);
                       }
                   }
                   dayToSave = " ";
                   dayToSave = newVal + 1 + "";
                   Log.i(TAG, "Current dayToSave " + dayToSave);
               }
           });



       }

        return calenderpickerview;
    }

    private String displayYearCalulation(String monthtosetinEdittext) {

        Log.i(TAG,"displayYearCalulation parameter year"+ monthtosetinEdittext );

        int selectedYear  = Integer.parseInt(monthtosetinEdittext);
        String yearValue= "";
        try {
            GregorianCalendar calendar = new GregorianCalendar();
            final int currentYear = calendar.get(Calendar.YEAR);
            if (selectedYear < currentYear) {
                 yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYear(selectedYear));
                textview_Display_year_Age.setText(yearValue);
            } else {
                 yearValue = String.valueOf(Utils.getAgeDataSubFromCurrentYearisGreater(selectedYear));
                textview_Display_year_Age.setText(yearValue);
            }
            }catch(Exception e){
            e.printStackTrace();
        }
        return yearValue;
    }

    private void getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = new Date();
        Log.i("spcheck", "date" + dateFormat.format(date));
        String[] items1 = dateFormat.format(date).split("/");
        year = items1[0];
        month = items1[1];
        day = items1[2];
        Log.i("spcheck", "date" + year + " <-year" + month + " <-month" + day + " <-day");

    }

    private void setCurrentDate() {
        mYearPicker.setValue(Integer.parseInt(year) - 1900);
        mMonthPicker.setValue(Integer.parseInt(month) - 1);
        mDayPicker.setValue(Integer.parseInt(day) - 1);
    }

    private int getMonthIndex(String month) {
        Log.i("DatePickDialog","getMonthIndex month "+   month);
        int index = 11, length = MONTHS_IN_TAMIL.length;
        for (int i = 0; i < length; i++) {
            if (month.equalsIgnoreCase(MONTHS_IN_TAMIL[i]))
                index = i ;
        }


        return index;
    }


    private String getMonthString(int i) {

        return MONTHS_IN_TAMIL[i];
    }


    private int maxDaysInMonth(int year, int month) {

        Calendar mycal = new GregorianCalendar(year, month, 1);
        return mycal.getActualMaximum(Calendar.DAY_OF_MONTH);

    }

    private void yearGenerator() {
        Year1 = new int[118];
        for (int i = 0; i < 118; i++) {
            Year1[i] = 1900 + i;
        }
         Log.i(TAG, "Year1[0]" + Year1[0] + "Year1[116]" + Year1[116]);
    }


    private void yearGeneratorpastYear() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = new Date();
        Log.i(TAG, "yearGeneratorpastYear date" + dateFormat.format(date));
        String[] items1 = dateFormat.format(date).split("/");
        year = items1[0];
        month = items1[1];
        day = items1[2];
        int currentYear = Integer.parseInt(year);
        Year1 = new int[118];
        for (int i = 0; i < 118; i++) {
            Year1[i] =    currentYear - i;
        }
    }

    private void yearGeneratorFuture() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = new Date();
        Log.i("spcheck", "yearGeneratorFuture date" + dateFormat.format(date));
        String[] items1 = dateFormat.format(date).split("/");
        year = items1[0];
        month = items1[1];
        day = items1[2];
        int currentYear = Integer.parseInt(year);
        Year1 = new int[118];
        for (int i = 0; i < 118; i++) {
            Year1[i] =  i + currentYear;

        }
        Log.i(TAG, "Year1[0]" + Year1[0] + "Year1[116]" + Year1[116]);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pickerSetButton: {

                if(calenderHeading.equalsIgnoreCase(PersonalDetailsFragment.life_of_expectancy_age)){
                    try {
                        isDateset = true;
                        int year = Calendar.getInstance().get(Calendar.YEAR);
                        yearToSave = (mYearPicker.getValue() + year) + "";
                        Log.i("DatePickDialog", " setOnClickListener Dob yearToSave " + yearToSave);
                        String ageOfcalulation = displayYearCalulation(yearToSave);
                        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                        Log.i("DatePickDialog", " Dob year" + ageOfcalulation);
                        callbackInterface.upadateExpectedLife( ageOfcalulation);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
                else if(calenderHeading.equalsIgnoreCase(PersonalDetailsFragment.planned_retirement_age)){
                    try {
                        isDateset = true;
                        int year = Calendar.getInstance().get(Calendar.YEAR);
                        yearToSave = (mYearPicker.getValue() + year) + "";
                        Log.i("DatePickDialog", " setOnClickListener Dob yearToSave " + yearToSave);
                        String ageOfcalulation = displayYearCalulation(yearToSave);
                        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                        Log.i("DatePickDialog", " Dob year" + ageOfcalulation);
                        callbackInterface.upadateReterimentLife( ageOfcalulation);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else if(calenderHeading.equalsIgnoreCase("CurrentResidence")){
                    try {
                        isDateset = true;
                        int year = Calendar.getInstance().get(Calendar.YEAR);
                        yearToSave = (mYearPicker.getValue() + year) + "";
                        Log.i("DatePickDialog", " setOnClickListener Dob yearToSave " + yearToSave);
                        String ageOfcalulation = displayYearCalulation(yearToSave);
                        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                        Log.i("DatePickDialog", " Dob year" + ageOfcalulation);
                        callbackInterface.upadatecurrentResidency( ageOfcalulation);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                else if(calenderHeading.equalsIgnoreCase(PersonalDetailsFragment.current_organization)){
                    try {
                        isDateset = true;
                        int year = Calendar.getInstance().get(Calendar.YEAR);
                        yearToSave = (mYearPicker.getValue() + year) + "";
                        Log.i("DatePickDialog", " setOnClickListener Dob yearToSave " + yearToSave);
                        String ageOfcalulation = displayYearCalulation(yearToSave);
                        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                        Log.i("DatePickDialog", " Dob year" + ageOfcalulation);
                        callbackInterface.upadatecurrentOrgnazation( ageOfcalulation);
//                        getDialog().dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else  if(calenderHeading.equalsIgnoreCase("Marriage Date")){
                    try {
                        isDateset = true;

                        dayToSave = (mDayPicker.getValue() + 1) + "";
                        int year = Calendar.getInstance().get(Calendar.YEAR);

                        dobyearToSave = (mYearPicker.getValue() + 1900 ) + "";
                        monthToSave = getMonthString(mMonthPicker.getValue());
//                        int mYear = Integer.parseInt(yearToSave);
//                        int mMonth = mMonthPicker.getValue();
//                        int mDate = Integer.parseInt(dayToSave);
                        Log.i(TAG, "Current dayToSave onclick " + dayToSave);
                        Log.i(TAG, "Current dobyearToSave onclick " + dobyearToSave);
                        Log.i(TAG, "Current yearToSave onclick " + yearToSave);

                        int monthtosetinEdittext = getMonthIndex(monthToSave) + 1;
                        String ageOfcalulation = displayYearCalulation(dobyearToSave);
                        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                        Log.i("DatePickDialog", " Dob year" + ageOfcalulation);
                        Log.i("DatePickDialog", " Dob 2" + dayToSave + " " + monthtosetinEdittext + " " + dobyearToSave);
                        callbackInterface.upadateMaraigeDate(dayToSave + "-" + monthtosetinEdittext + "-" + dobyearToSave);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {

                        isDateset = true;

                        dayToSave = (mDayPicker.getValue() + 1) + "";
                        int year = Calendar.getInstance().get(Calendar.YEAR);

                        dobyearToSave = (mYearPicker.getValue() + 1900 ) + "";
                        monthToSave = getMonthString(mMonthPicker.getValue());
//                        int mYear = Integer.parseInt(yearToSave);
//                        int mMonth = mMonthPicker.getValue();
//                        int mDate = Integer.parseInt(dayToSave);
                        Log.i(TAG, "Current dayToSave onclick " + dayToSave);
                        Log.i(TAG, "Current dobyearToSave onclick " + dobyearToSave);
                        Log.i(TAG, "Current yearToSave onclick " + yearToSave);

                        int monthtosetinEdittext = getMonthIndex(monthToSave) + 1;
                        String ageOfcalulation = displayYearCalulation(dobyearToSave);
                        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                        Log.i("DatePickDialog", " Dob year" + ageOfcalulation);
                        Log.i("DatePickDialog", " Dob 2" + dayToSave + " " + monthtosetinEdittext + " " + dobyearToSave);
                        callbackInterface.upadateDOB(dayToSave + "-" + monthtosetinEdittext + "-" + dobyearToSave);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                }


            break;
            case R.id.pickerClearButton: {

                if(calenderHeading.equalsIgnoreCase(PersonalDetailsFragment.life_of_expectancy_age)){
                    try {
//                        Log.i("DatePickDialog", " Dob year clear" + Integer.parseInt(year));
                        if(year==null) {
                            year = "0";
                            int year = Calendar.getInstance().get(Calendar.YEAR);

                            mYearPicker.setValue(year - 27);

                            yearToSave = String.valueOf(year);

                            String ageOfcalulation = displayYearCalulation(yearToSave);
                            textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                            callbackInterface.upadateExpectedLife(ageOfcalulation);
                        }
                        else{
                            int year = Calendar.getInstance().get(Calendar.YEAR);

                            mYearPicker.setValue(year - 27);

                            yearToSave = String.valueOf(year);

                            String ageOfcalulation = displayYearCalulation(yearToSave);
                            textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                            callbackInterface.upadateExpectedLife(ageOfcalulation);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(calenderHeading.equalsIgnoreCase(PersonalDetailsFragment.planned_retirement_age)){


                    try {
//                        Log.i("DatePickDialog", " Dob year clear" + Integer.parseInt(year));
                        if(year==null) {
                            year = "0";
                            int year = Calendar.getInstance().get(Calendar.YEAR);

                            mYearPicker.setValue(year - 27);

                            yearToSave = String.valueOf(year);

                            String ageOfcalulation = displayYearCalulation(yearToSave);
                            textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                            callbackInterface.upadateReterimentLife( ageOfcalulation);
                        }
                        else{
                            int year = Calendar.getInstance().get(Calendar.YEAR);

                            mYearPicker.setValue(year - 27);

                            yearToSave = String.valueOf(year);

                            String ageOfcalulation = displayYearCalulation(yearToSave);
                            textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                            callbackInterface.upadateReterimentLife( ageOfcalulation);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                else if(calenderHeading.equalsIgnoreCase("CurrentResidence")){
                    try {
                        Log.i("DatePickDialog", " Dob year clear" + Integer.parseInt(year));
                        int year = Calendar.getInstance().get(Calendar.YEAR);

                        mYearPicker.setValue(year-27 );

                        yearToSave = String.valueOf(year);

                        String ageOfcalulation = displayYearCalulation(yearToSave);
                        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                        callbackInterface.upadatecurrentResidency( ageOfcalulation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                else if(calenderHeading.equalsIgnoreCase(PersonalDetailsFragment.current_organization)){
                    try {
                        Log.i("DatePickDialog", " Dob year clear" + Integer.parseInt(year));
                        int year = Calendar.getInstance().get(Calendar.YEAR);

                        mYearPicker.setValue(year-27 );

                        yearToSave = String.valueOf(year);

                        String ageOfcalulation = displayYearCalulation(yearToSave);
                        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                        callbackInterface.upadatecurrentOrgnazation( ageOfcalulation);
//                        getDialog().dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else  if(calenderHeading.equalsIgnoreCase("Marriage Date")){
                    try {
                        mYearPicker.setValue(Integer.parseInt(year) - 1900);
                        mMonthPicker.setValue(Integer.parseInt(month) - 1);
                        mDayPicker.setValue(Integer.parseInt(day) - 1);
                        int year = Calendar.getInstance().get(Calendar.YEAR);
                        int month = Calendar.getInstance().get(Calendar.MONTH);
                        int date = Calendar.getInstance().get(Calendar.DATE);

                        dayToSave = String.valueOf(date);
                        monthToSave = String.valueOf(month) + 1;
                        yearToSave = String.valueOf(year);

                        String ageOfcalulation = displayYearCalulation(yearToSave);
                        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                        callbackInterface.upadateDOB(dayToSave + "-" + monthToSave + "-" + yearToSave);
//                        getDialog().dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {

                        mYearPicker.setValue(Integer.parseInt(year) - 1900);
                        mMonthPicker.setValue(Integer.parseInt(month) - 1);
                        mDayPicker.setValue(Integer.parseInt(day) - 1);
                        int year = Calendar.getInstance().get(Calendar.YEAR);
                        int month = Calendar.getInstance().get(Calendar.MONTH);
                        int date = Calendar.getInstance().get(Calendar.DATE);

                        dayToSave = String.valueOf(date);
                        monthToSave = String.valueOf(month) + 1;
                        yearToSave = String.valueOf(year);

                        String ageOfcalulation = displayYearCalulation(yearToSave);
                        textview_Display_year_Age.setText(CurrentBelowTextToBeUpdate + "  " + ageOfcalulation);
                        callbackInterface.upadateDOB("");
                        Log.i(TAG,"Date MOnth Year " + dayToSave + " "+ monthToSave + " " + yearToSave);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            break;

            case R.id.numberPicker_fab: {


                    try {
//                        getDialog().dismiss();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }

        }
    }

    private String setDateFormat(String date) {
        String output = "";
        try {
            if (date.trim().length() != 0) {
                String dateAray[] = date.split("-");
                output = dateAray[0];
                Log.i(TAG, " Date Splits " + output);

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
        return output;
    }
}

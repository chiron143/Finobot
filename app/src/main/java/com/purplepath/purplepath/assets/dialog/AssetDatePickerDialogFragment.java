package com.purplepath.purplepath.assets.dialog;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.calenderNumberPicker.calenderUi.Age;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationFieldType;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.purplepath.purplepath.apputiles.UtileKit.maxDaysInMonth;

//import com.purplepath.purplepath.calenderNumberPicker.calenderUi.SpanCalenderView;

/**
 * Created by Pratheep.S on 11-02-2017.
 */

public class AssetDatePickerDialogFragment  extends BaseFragment{
    private final static String TAG = AssetDatePickerDialogFragment.class.getCanonicalName();


    private static final String[] DATES = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private static final String[] MONTHS_IN_ENGLISH = new String[]{"January"," February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    private static NumberPicker mDayPicker;
    private static NumberPicker mMonthPicker;
    private static NumberPicker mYearPicker;

    private static String yearToSave;
    private static String monthToSave;
    private static String dayToSave;
    private static String title;
    private static String numberToSave;

    private Button  clear, set;

    private static String year;
    private static String month;
    private static String day;
    private String savedDates;
    private String savedDate;
    private String savedNumberToSet;
    private String yearToSet;

    private TextView calenderHeading;
    private static TextView calenderTextview;

    private static Boolean limitToCurrentDate;
    private static Boolean hideDayMonth;
    private static Boolean showOnlyNumbers;

    public static int allmight;

    private static DatePickerCallBackInterface callBackInterface;

    private ImageView uparrow1,uparrow2,downarrow1,downarrow2;

    private LinearLayout yeare , monthe, daye;

    private int[] numbers,Year1;

    private Bundle args;//=new Bundle();

    public static final String FULLDATE="fullDate",ONLYNUMBER="numberToSet",YEARONLY="yearToSet";

    boolean individual_date_boolean = false;

    /**
     *
     * @param callBackInterface    :Pass this Interface instance to update edit text with date selected
     * @param title                : Title of the popup
     * @param limitToCurrentDate   :Set to true , to show till current day, future dates will not be shown
     * @param hideDayMonth         :set to true , to show only year
     * @param showOnlyNumbers      :set to true , to show no of years (1-99) in numbers, also set hideDay month to true
     *
     *    @param date              : is used to get value from parent class and display DDMMYYYY in textview
     * @return
     */
    public static AssetDatePickerDialogFragment newInstance(DatePickerCallBackInterface callBackInterface,
                                                            String title,Boolean limitToCurrentDate,
                                                            Boolean hideDayMonth,Boolean showOnlyNumbers, String date){

        AssetDatePickerDialogFragment fragment=new AssetDatePickerDialogFragment();
        AssetDatePickerDialogFragment.callBackInterface =callBackInterface;
        AssetDatePickerDialogFragment.title =title;
        AssetDatePickerDialogFragment.limitToCurrentDate =limitToCurrentDate;
        AssetDatePickerDialogFragment.hideDayMonth =hideDayMonth;
        AssetDatePickerDialogFragment.showOnlyNumbers =showOnlyNumbers;
        fragment.savedDates=date;
        Log.i("AssetDatePicker","AssetDatePickerDialogFragment savedDate"+ fragment.savedDates);
        return fragment;

    }


    @Override
    public void onResume() {
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.80);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.80);
//        getDialog().getWindow().setLayout(width, height);
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.generic_date_picker_dialog,container,false);
//        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.white);
        initializeElements(view);
        getCurrentDate();



        if(!(hideDayMonth)&&!(showOnlyNumbers)) {
            savedDate= savedDates;
            getBundleArgsForFullDate();
        }else if((hideDayMonth)&&(showOnlyNumbers)){
            savedNumberToSet = savedDates;
            getBundleArgsForShowOnlyNumbers();
        }else if((hideDayMonth)&&!(showOnlyNumbers)){
            yearToSet = savedDates;
            getBundleArgsForYear();

        }

        mMonthPicker.setMinValue(0);
        mMonthPicker.setMaxValue(MONTHS_IN_ENGLISH.length - 1);
        mMonthPicker.setDisplayedValues(MONTHS_IN_ENGLISH);
        mMonthPicker.setWrapSelectorWheel(true);
        mMonthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        if (hideDayMonth) {
            hideDayAndMonth();
        }

        yearGenerator();
        String[] yearString = Arrays.toString(Year1).split("[\\[\\]]")[1].split(", ");
        Log.i("spcheck", yearString[0] + "=yearString[0] " + yearString[116] + "=yearString[116]");

        mYearPicker.setMinValue(0);
        if(showOnlyNumbers)
        {
            mYearPicker.setMaxValue(99);
            numberGenerator();
            String[] numberString = Arrays.toString(numbers).split("[\\[\\]]")[1].split(", ");
            mYearPicker.setDisplayedValues(numberString);
        }else {
            try {
                //mYearPicker.setMaxValue(yearString.length - 1);
                if (limitToCurrentDate) {
                    mYearPicker.setMaxValue(Integer.parseInt(year) - 1900);
                } else {
                    mYearPicker.setMaxValue(249);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            mYearPicker.setDisplayedValues(yearString);
        }
        mYearPicker.setWrapSelectorWheel(true);
        mYearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        mDayPicker.setMinValue(0);
        mDayPicker.setMaxValue(maxDaysInMonth(mYearPicker.getValue() + 1900, mMonthPicker.getValue()) - 1);
        mDayPicker.setDisplayedValues(DATES);
        mDayPicker.setWrapSelectorWheel(true);
        mDayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        //if(!(hideDayMonth)&&!(showOnlyNumbers)) {
        if(!(showOnlyNumbers)) {
            setCurrentDate();
        }

        if(UtileKit.validateObjectValues(savedDate) &&(!(hideDayMonth)&&!(showOnlyNumbers))){
            showFullDateFromEditText();
        }else if(UtileKit.validateObjectValues(savedNumberToSet) &&((hideDayMonth)&&(showOnlyNumbers))){
            setNumberFromEditText();
        }else if(UtileKit.validateObjectValues(yearToSet)&&((hideDayMonth)&&!(showOnlyNumbers))){
            setYearFromEditText();
        }

        mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.v("spcheck", "oldVal: " + oldVal + "newVal: " + newVal);
                Log.i("VEGE","GOKU"+newVal);
                Calendar calendar = Calendar.getInstance();
                int month1 = calendar.get(Calendar.MONTH);
                int monthset = month1 - newVal  +1;
                String pickerMonth = String.valueOf(Math.abs(monthset));


                Calendar calendar2 = Calendar.getInstance();
                int year1 = calendar2.get(Calendar.YEAR);
                String yearchange = String.valueOf(mYearPicker.getValue());
                //Toast.makeText(getContext(),yearchange,Toast.LENGTH_SHORT).show();
                String correctyear = null;
                int newVal2 = 0;
                if(mYearPicker.getValue()<10){

                    correctyear = "190"+yearchange;
                    newVal2  = Integer.parseInt(correctyear);
                }
                else if((10<= mYearPicker.getValue()) && (mYearPicker.getValue() < 100) ){
                    correctyear = "19"+yearchange;
                    newVal2  = Integer.parseInt(correctyear);
                }
                else if(mYearPicker.getValue() >= 100){
                    String substring = yearchange.substring(Math.max(yearchange.length() - 2, 0));
                    correctyear = "20"+substring;
                    newVal2  = Integer.parseInt(correctyear);
                }

                int yearset = year1 - newVal2;
                String pickerage = String.valueOf(Math.abs(yearset));




                Calendar calendar3 = Calendar.getInstance();
                int date1 = calendar3.get(Calendar.DATE);
                int dateset = date1 - mDayPicker.getValue() + 1;
                dateset =dateset - 1;
                String pickerdate = String.valueOf(Math.abs(dateset));

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date birthDate = null;
                try {
                    birthDate = sdf.parse(mDayPicker.getValue()+"/"+newVal+"/"+newVal2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Age age = calculateAge(birthDate);

                calenderTextview.setText(Math.abs(age.getYears()) + "Years, " + Math.abs(age.getMonths() - 1) + "Month, " + Math.abs(age.getDays() - 1) + " Days");
                int maxdays = maxDaysInMonth(mYearPicker.getValue() + 1900, newVal);
                mDayPicker.setMaxValue(maxdays - 1);

                if(limitToCurrentDate){
                    if ((mYearPicker.getValue() + 1900) == Integer.parseInt(year)) {
                        mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                        if (newVal == Integer.parseInt(month) - 1) {
                            mDayPicker.setMaxValue(Integer.parseInt(day) - 1);
                        }
                    }
                    monthToSave = getMonthString(newVal);
                }
            }
        });

        mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                Log.i("VEGE","GOKU"+newVal);
                Calendar calendar = Calendar.getInstance();
                int month1 = calendar.get(Calendar.MONTH);
                int monthset = month1 - mMonthPicker.getValue() + 1;
                String pickerMonth = String.valueOf(Math.abs(monthset));


                Calendar calendar2 = Calendar.getInstance();
                int year1 = calendar2.get(Calendar.YEAR);
                String yearchange = String.valueOf(newVal);
                String correctyear = null;
                int newVal2 = 0;
                if(newVal<10){

                    correctyear = "190"+yearchange;
                    newVal2  = Integer.parseInt(correctyear);
                }
                else if((10<= newVal) && (newVal < 100) ){
                    correctyear = "19"+yearchange;
                    newVal2  = Integer.parseInt(correctyear);
                }
                else if(newVal >= 100){
                    String substring = yearchange.substring(Math.max(yearchange.length() - 2, 0));
                    correctyear = "20"+substring;
                    newVal2  = Integer.parseInt(correctyear);
                }

                int yearset = year1 - newVal2;
                String pickerage = String.valueOf(Math.abs(yearset));




                Calendar calendar3 = Calendar.getInstance();
                int date1 = calendar3.get(Calendar.DATE);
                int dateset = date1 - mDayPicker.getValue() + 1;
                dateset =dateset - 1;
                String pickerdate = String.valueOf(Math.abs(dateset));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date birthDate = null;
                try {
                    birthDate = sdf.parse(mDayPicker.getValue()+"/"+mMonthPicker.getValue()+"/"+newVal2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Age age = calculateAge(birthDate);

                allmight = Math.abs(age.getYears());
                if(title.equals("Select End Year") || title.equals("Select Start Year")){
                    allmight = Math.abs(age.getYears());
                    calenderTextview.setText("       "+Math.abs(age.getYears()) + "Years");
                }
                else {
                    calenderTextview.setText(Math.abs(age.getYears()) + "Years, " + Math.abs(age.getMonths() - 1) + "Month, " + Math.abs(age.getDays() - 1) + " Days");

                }if(showOnlyNumbers){
                    numberToSave = newVal+1+ "";
                }
                else {
                    try {
                        if (limitToCurrentDate) {
                            if ((newVal + 1900) == Integer.parseInt(year)) {
                                mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                                if (mMonthPicker.getValue() == Integer.parseInt(month) - 1) {
                                    mDayPicker.setMaxValue(Integer.parseInt(day));
                                }
                            } else {
                                mMonthPicker.setMaxValue(MONTHS_IN_ENGLISH.length - 1);
                            }
                        }
                        if (mMonthPicker.getValue() == 1) {
                            int maxdays = maxDaysInMonth(newVal + 1900, 1);
                            mDayPicker.setMaxValue(maxdays - 1);
                            if (limitToCurrentDate) {
                                if ((newVal + 1900) == Integer.parseInt(year)) {
                                    mDayPicker.setMaxValue(Integer.parseInt(day));
                                }
                            }
                        }

                        yearToSave = newVal + 1900 + "";
                /*if(mMonthPicker.getValue()==1){
                    int maxdays=maxDaysInMonth(newVal+1900,1);
                    mDayPicker.setMaxValue(maxdays-1);
                }
                yearToSave=newVal+1900+"";*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        mDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                Log.i("VEGE","GOKU"+newVal);
                Calendar calendar = Calendar.getInstance();
                int month1 = calendar.get(Calendar.MONTH);
                int monthset = month1 - mMonthPicker.getValue() + 1;
                String pickerMonth = String.valueOf(Math.abs(monthset));


                Calendar calendar2 = Calendar.getInstance();
                int year1 = calendar2.get(Calendar.YEAR);
                String yearchange = String.valueOf(mYearPicker.getValue());
                //Toast.makeText(getContext(),yearchange,Toast.LENGTH_SHORT).show();
                String correctyear = null;
                int newVal2 = 0;
                if(mYearPicker.getValue()<10){

                    correctyear = "190"+yearchange;
                    newVal2  = Integer.parseInt(correctyear);
                }
                else if((10<= mYearPicker.getValue()) && (mYearPicker.getValue() < 100) ){
                    correctyear = "19"+yearchange;
                    newVal2  = Integer.parseInt(correctyear);
                }
                else if(mYearPicker.getValue() >= 100){
                    String substring = yearchange.substring(Math.max(yearchange.length() - 2, 0));
                    correctyear = "20"+substring;
                    newVal2  = Integer.parseInt(correctyear);
                }

                int yearset = year1 - newVal2;
                String pickerage = String.valueOf(Math.abs(yearset));




                Calendar calendar3 = Calendar.getInstance();
                int date1 = calendar3.get(Calendar.DATE);
                int dateset = date1 - newVal;
                dateset =dateset - 1;
                String pickerdate = String.valueOf(Math.abs(dateset));

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date birthDate = null;
                try {
                    birthDate = sdf.parse(newVal+"/"+mMonthPicker.getValue()+"/"+newVal2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Age age = calculateAge(birthDate);

                calenderTextview.setText(Math.abs(age.getYears()) + "Years, " + Math.abs(age.getMonths() - 1) + "Month, " + Math.abs(age.getDays()) + " Days");

                mDayPicker.setMaxValue(maxDaysInMonth(mYearPicker.getValue() + 1900, mMonthPicker.getValue()) - 1);
                if(limitToCurrentDate) {
                    if ((mYearPicker.getValue() + 1900) == Integer.parseInt(year)) {
                        mMonthPicker.setMaxValue(Integer.parseInt(month) - 1);
                        if (mMonthPicker.getValue() == (Integer.parseInt(month) - 1)) {
                            mDayPicker.setMaxValue(Integer.parseInt(day) - 1);
                        }
                    }
                }

                dayToSave = newVal + 1 + "";

            }
        });


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*SettingsContentResolver.writeString(getActivity(), "DOB", dayToSave + " " + monthToSave + " " + yearToSave);
                SettingsContentResolver.writeString(getActivity(), "dobInNo", dayToSave + "/" + (getMonthIndex(monthToSave) + 1) + "/" + yearToSave);
                Log.i("spcheck", "new saved values" + SettingsContentResolver.readString(getActivity(), SettingsContentResolver.DOB));*/
               /* callbackInterface.upadateDOB(dayToSave + " " + getMonthString(mMonthPicker.getValue()) + " " + yearToSave);*/

                if(hideDayMonth&&showOnlyNumbers){
                    numberToSave=(mYearPicker.getValue()+1)+"";
                    callBackInterface.updateEditTextValue(numberToSave, title);
                }
                else {
                    dayToSave = (mDayPicker.getValue() + 1) + "";
                    yearToSave = (mYearPicker.getValue() + 1900) + "";
                    monthToSave = (mMonthPicker.getValue()+1)+"";


                    if (hideDayMonth) {
                        callBackInterface.updateEditTextValue(yearToSave, title);
                    } else {
                        callBackInterface.updateEditTextValue(dayToSave + "-" + (mMonthPicker.getValue() + 1) + "-" + yearToSave, title);
                        try {
                            if (!hideDayMonth && limitToCurrentDate && !showOnlyNumbers) {
                                instancegetcurrentValueSetEditText(Integer.valueOf(year), Integer.valueOf(yearToSave), Integer.valueOf(month), Integer.valueOf(mMonthPicker.getValue()+1), Integer.valueOf(day), Integer.valueOf(dayToSave), calenderTextview);
                            } else if (!hideDayMonth && !limitToCurrentDate && !showOnlyNumbers) {

                                if(Integer.valueOf(year)< Integer.valueOf(yearToSave)){
                                    Log.i("spcheck", "year is less than get current year"+ year + "got year"+ yearToSave );
                                    instancegetcurrentValueSetEditText(Integer.valueOf(yearToSave),Integer.valueOf(year),Integer.valueOf(monthToSave),Integer.valueOf(month),Integer.valueOf(dayToSave),Integer.valueOf(day), calenderTextview);
                                }else{
                                    Log.i("spcheck",   "got year"+ yearToSave + "year is less than get current year"+ year );
                                    instancegetcurrentValueSetEditText(Integer.valueOf(year),Integer.valueOf(yearToSave),Integer.valueOf(month),Integer.valueOf(monthToSave),Integer.valueOf(day),Integer.valueOf(dayToSave), calenderTextview);
                                }
//                              instancegetcurrentValueSetEditText(Integer.valueOf(yearToSave), Integer.valueOf(year), Integer.valueOf(mMonthPicker.getValue()+1), Integer.valueOf(month), Integer.valueOf(dayToSave), Integer.valueOf(day), calenderTextview);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

//                getDialog().dismiss();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBackInterface.updateEditTextValue("", title);
                calenderTextview.setText("Your " + title);
                setCurrentDate();
              /*  dayToSave = (mDayPicker.getValue() + 1) + "";
                yearToSave = (mYearPicker.getValue() + 1900) + "";
                monthToSave = getMonthString(mMonthPicker.getValue());
               callbackInterface.upadateDOB(dayToSave + " " + getMonthString(mMonthPicker.getValue()) + " " + yearToSave);*/
//                getDialog().dismiss();
            }
        });


        return view;
    }

    private void setYearFromEditText() {

        Log.i(TAG,"setYearFromEditText"+ yearToSet);
        mYearPicker.setValue(Integer.parseInt(yearToSet) -1900);
    }


    private void setNumberFromEditText() {
        int number=Integer.parseInt(savedNumberToSet);
        if(number<101) {
            mYearPicker.setValue(number - 1);
        }
    }



    private void initializeElements(View view) {
        uparrow1 = view.findViewById(R.id.uparrow1);
        uparrow2 = view.findViewById(R.id.uparrow2);
        downarrow1 = view.findViewById(R.id.downarrow1);
        downarrow2 = view.findViewById(R.id.downarrow2);

        calenderHeading= view.findViewById(R.id.Calender_heading);
        calenderHeading.setText(title);
        calenderTextview = view.findViewById(R.id.textview_Display_year_Age);
        mDayPicker = view.findViewById(R.id.numberPicker1);
        mMonthPicker = view.findViewById(R.id.numberPicker2);
        mYearPicker = view.findViewById(R.id.numberPicker3);

        yeare = view.findViewById(R.id.yearPicker);
        monthe = view.findViewById(R.id.mon);
        daye = view.findViewById(R.id.da);

        if(title.equals("Select End Year") || title.equals("Select Start Year")){
            monthe.setVisibility(View.GONE);
            daye.setVisibility(View.GONE);
        }
        else{
            monthe.setVisibility(View.VISIBLE);
            daye.setVisibility(View.VISIBLE);
        }

        set = view.findViewById(R.id.pickerSetButton);
        clear = view.findViewById(R.id.pickerClearButton);

    }

    private void showFullDateFromEditText() {
        //to set stored date values in picker
        try {

            String split[] = savedDate.split("-");
//            Log.i("spcheck", "datesaved" + savedDate + split[0] + "<-0 " + split[1] + "<-1 " + split[2] + "<-2 ");
            dayToSave = split[0];
            monthToSave = split[1];
            yearToSave = split[2];
            mDayPicker.setValue(Integer.parseInt(split[0].trim()) - 1);
            mYearPicker.setValue(Integer.parseInt(split[2].trim()) - 1900);
            //mMonthPicker.setValue(UtileKit.getMonthIndex(split[1]));
            mMonthPicker.setValue(Integer.parseInt(split[1].trim()) - 1);
            Log.i("spcheck", "onCreateView: " + UtileKit.getMonthIndex(split[1]));
            if(!hideDayMonth && limitToCurrentDate && !showOnlyNumbers){

                /**
                 *
                 * @param year : is current year is store
                 * @param yearToSave : is current year is store
                 *
                 *
                 * **/

                instancegetcurrentValueSetEditText(Integer.valueOf(year),Integer.valueOf(yearToSave),Integer.valueOf(month),Integer.valueOf(monthToSave),Integer.valueOf(day),Integer.valueOf(dayToSave), calenderTextview);
            }else if(!hideDayMonth && !limitToCurrentDate && !showOnlyNumbers){
                if(Integer.valueOf(year)< Integer.valueOf(yearToSave)){
                    Log.i("spcheck", "year is less than get current year"+ year + "got year"+ yearToSave );
                    instancegetcurrentValueSetEditText(Integer.valueOf(yearToSave),Integer.valueOf(year),Integer.valueOf(monthToSave),Integer.valueOf(month),Integer.valueOf(dayToSave),Integer.valueOf(day), calenderTextview);
                }else{
                    Log.i("spcheck",   "got year"+ yearToSave + "year is less than get current year"+ year );
                    instancegetcurrentValueSetEditText(Integer.valueOf(year),Integer.valueOf(yearToSave),Integer.valueOf(month),Integer.valueOf(monthToSave),Integer.valueOf(day),Integer.valueOf(dayToSave), calenderTextview);
                }

            }




        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void instancegetcurrentValueSetEditText(Integer year_2, Integer year_1, Integer month_2, Integer month_1, Integer day_2, Integer day_1, TextView calenderTextview) {
        DateTimeZone timeZone = DateTimeZone.forID("America/Montreal");

        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd").withZone(timeZone);
//        //Log.e("Sample", "year2: " + year_2 + " ,month2 :" + month_2 + " day2 :" + day_2);
//        //Log.e("Sample1", "year1: " + year_1 + " ,month1 :" + month_1 + " day1 :" + day_1);
        DateTime dateTimeStart = formatter.parseDateTime(year_1 + "/" + (month_1) + "/" + day_1);
        DateTime dateTimeStop = formatter.parseDateTime(year_2 + "/" + (month_2) + "/" + day_2);


        Period period = new Period(dateTimeStart, dateTimeStop, PeriodType.forFields(
                new DurationFieldType[]{
                        DurationFieldType.years(),
                        DurationFieldType.months(),
                        DurationFieldType.days(),
                        DurationFieldType.hours(),
                        DurationFieldType.minutes(),
                        DurationFieldType.seconds(),
                        DurationFieldType.millis(),
                }));


        PeriodFormatter periodFormatter = PeriodFormat.getDefault();

        String output = periodFormatter.print(period);
//        //Log.e("Sample1", "instancegetcurrentValue output" + output);
//        //Log.e("Sample1", "instancegetcurrentValue output length" + output.length());
        String[] splityears = output.split("\\s");
        if(output.length()<=8){
            // only years should be display

            if (splityears[0] != null){

                calenderTextview.setText(splityears[0] + "Years, " + "0" + "Month, " + "0" + "Days");

            }

        }else if(output.length()>14 && output.length()<=21){
//            //Log.e("Sample1", "instancegetcurrentValue splityears" + splityears[0]);
//            //Log.e("Sample1", "instancegetcurrentValue splitmonth" + splityears[3]);
            // only years and month should be display
            if(splityears[1].equalsIgnoreCase("month")&& splityears[4].equalsIgnoreCase("days")) {
                calenderTextview.setText("0" + "Years, " + splityears[0] + "Month, " + splityears[3] + " Days");

            }
            else if(splityears[1].equalsIgnoreCase("month")&& splityears[4].equalsIgnoreCase("day")) {
                calenderTextview.setText("0" + "Years, " + splityears[0] + "Month, " + splityears[3] + " Days");

            }else if(splityears[1].equalsIgnoreCase("months")&& splityears[4].equalsIgnoreCase("days")) {
                calenderTextview.setText("0" + "Years, " + splityears[0] + "Month, " + splityears[3] + " Days");

            }else {
                if (splityears[0] != null && splityears[3] != null) {
                    //suresh
                    calenderTextview.setText(day_2 + "Years, " + splityears[0] + "Month, " + "0" + "Days");

                }
            }

        }else if(output.length()<=14){

            calenderTextview.setText("0" + "Years, " + "0" + "Month, " + "0" + "Days");
        }
        else if(output.length()<=0){

            calenderTextview.setText("0" + " Years, " + "0" + "Month, " + "0" + "Days");
        }

        else {
            // only years , months and Dates should be display

            calenderTextview.setText(splityears[0]+ " Years, " + splityears[2]+ " Month, " + splityears[5] + " Days");

        }

    }

    private void getBundleArgsForFullDate() {
        try {
            args=getArguments();
            if(args!= null) {
                savedDate = args.getString("fullDate");
                Log.i("spcheck", "getBundleArgs: fulldate" + savedDate);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getBundleArgsForYear() {
        try{
            args=getArguments();
            if(args!=null)
                yearToSet=args.getString(AssetDatePickerDialogFragment.YEARONLY);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getBundleArgsForShowOnlyNumbers() {
        try {
            args = getArguments();
            savedNumberToSet = args.getString("numberToSet");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void numberGenerator() {
        numbers=new int[100];
        for(int  i=0;i<100;i++)
        {
            numbers[i]=i+1;
        }
    }

    private void hideDayAndMonth() {

        mDayPicker.setVisibility(View.VISIBLE);
        mMonthPicker.setVisibility(View.VISIBLE);
        uparrow1.setVisibility(View.VISIBLE);
        uparrow2.setVisibility(View.VISIBLE);
        downarrow1.setVisibility(View.VISIBLE);
        downarrow2.setVisibility(View.VISIBLE);
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

    private void yearGenerator() {

        Year1 = new int[250];
        for (int i = 0; i < 250; i++) {
            Year1[i] = 1900 + i;
        }
        Log.i("spcheck", "Year1[0]" + Year1[0] + "Year1[116]" + Year1[116]);

    }

    private void setCurrentDate() {
        mYearPicker.setValue(Integer.parseInt(year) - 1900);
        mMonthPicker.setValue(Integer.parseInt(month) - 1);
        mDayPicker.setValue(Integer.parseInt(day) - 1);
        //callbackInterface.upadateDOB(day+" "+(getMonthString(Integer.parseInt(month)-1)+" "+year));
    }

    private String getMonthString(int i) {

        return MONTHS_IN_ENGLISH[i];
    }

    public static void asstr(){

        if(title.equals("Select End Year") || title.equals("Select Start Year")){
            String resultYearGetmonth = String.valueOf(allmight);
            Log.i(TAG, "SpanClender resultYears for add" + resultYearGetmonth);


            callBackInterface.updateEditTextValue(resultYearGetmonth, title);
            //tax file individual
            callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);

        }
        else
        if(hideDayMonth&&showOnlyNumbers){
            numberToSave=(mYearPicker.getValue()+1)+"";
            callBackInterface.updateEditTextValue(numberToSave, title);
            //tax file individual
            callBackInterface.updateIndividualEditTextValue(numberToSave, title);
        }
        else {
            dayToSave = (mDayPicker.getValue() + 1) + "";
            yearToSave = (mYearPicker.getValue() + 1900) + "";
            monthToSave = (mMonthPicker.getValue()+1)+"";


            if (hideDayMonth) {
                callBackInterface.updateEditTextValue(yearToSave, title);
                //tax file individual
                callBackInterface.updateIndividualEditTextValue(yearToSave, title);

            } else {
                callBackInterface.updateEditTextValue(dayToSave + "-" + (mMonthPicker.getValue() + 1) + "-" + yearToSave, title);
               //tax file individual
                callBackInterface.updateIndividualEditTextValue(dayToSave + "-" + (mMonthPicker.getValue() + 1) + "-" + yearToSave, title);
                try {
                    if (!hideDayMonth && limitToCurrentDate && !showOnlyNumbers) {
                        instancegetcurrentValueSetEditText(Integer.valueOf(year), Integer.valueOf(yearToSave), Integer.valueOf(month), Integer.valueOf(mMonthPicker.getValue()+1), Integer.valueOf(day), Integer.valueOf(dayToSave), calenderTextview);
                    } else if (!hideDayMonth && !limitToCurrentDate && !showOnlyNumbers) {

                        if(Integer.valueOf(year)< Integer.valueOf(yearToSave)){
                            Log.i("spcheck", "year is less than get current year"+ year + "got year"+ yearToSave );
                            instancegetcurrentValueSetEditText(Integer.valueOf(yearToSave),Integer.valueOf(year),Integer.valueOf(monthToSave),Integer.valueOf(month),Integer.valueOf(dayToSave),Integer.valueOf(day), calenderTextview);
                        }else{
                            Log.i("spcheck",   "got year"+ yearToSave + "year is less than get current year"+ year );
                            instancegetcurrentValueSetEditText(Integer.valueOf(year),Integer.valueOf(yearToSave),Integer.valueOf(month),Integer.valueOf(monthToSave),Integer.valueOf(day),Integer.valueOf(dayToSave), calenderTextview);
                        }
//                              instancegetcurrentValueSetEditText(Integer.valueOf(yearToSave), Integer.valueOf(year), Integer.valueOf(mMonthPicker.getValue()+1), Integer.valueOf(month), Integer.valueOf(dayToSave), Integer.valueOf(day), calenderTextview);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

//                getDialog().dismiss();

    }

    private static Age calculateAge(Date birthDate)
    {
        int years = 0;
        int months = 0;
        int days = 0;
        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());
        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        //Get difference between months
        months = currMonth - birthMonth;
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0)
        {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
        {
            years--;
            months = 11;
        }
        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
        {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else
        {
            days = 0;
            if (months == 12)
            {
                years++;
                months = 0;
            }
        }
        //Create new Age object
        return new Age(days, months, years);
    }

}
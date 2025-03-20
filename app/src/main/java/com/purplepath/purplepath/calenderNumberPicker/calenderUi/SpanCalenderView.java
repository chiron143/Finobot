package com.purplepath.purplepath.calenderNumberPicker.calenderUi;


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

import com.finobot.finobot.R;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



/**
 * Created by Suresh on 26/05/17.
 */

public class SpanCalenderView extends BaseFragment implements  View.OnClickListener{

    private final static String TAG = SpanCalenderView.class.getCanonicalName();

    private static DatePickerCallBackInterface callBackInterface;

    private Button set,clear;

    private static NumberPicker mDayPicker;
    private static NumberPicker mMonthPicker;
    private static NumberPicker mYearPicker;

    private ImageView uparrow1,uparrow2,downarrow1,downarrow2;

    private static String  title;
    private static String savedYear;
    private static String savedMonth;
    private static String saveddays;

    private static String  pickerdate =null;
    private static String pickerMonth=null;
    private static String pickerage =null;
    private static String agecalculation;
    private String check_flag;
    private String savedDate ;

    private LinearLayout yeare , monthe, daye;

    private TextView calenderHeadingTextview;
    private static TextView calenderTextview;

    public static int allmight2;

    private static final String[] DATES = new String[]{"00","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    private static final String[] MONTHS_IN_ENGLISH = new String[]{"00","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",};

    private static String[] age;

    private static Integer currentdate;
    private static Integer currentmonth;
    private static Integer currentyear ;

    private static Boolean limitToCurrentDate;
    private static Boolean hideDayMonth;
    private static Boolean showOnlyNumbers;

    private static int getpickerdates ;
    private static int getpickermonth ;
    private static int getpickerYears ;

    public static SpanCalenderView newInstance(DatePickerCallBackInterface callBackInterface,
                                               String title,Boolean limitToCurrentDate,
                                               Boolean hideDayMonth,Boolean showOnlyNumbers , String flag, String date ) {

        SpanCalenderView fragment=new SpanCalenderView();
        SpanCalenderView.callBackInterface =callBackInterface;
        SpanCalenderView.title =title;
        SpanCalenderView.limitToCurrentDate =limitToCurrentDate;
        SpanCalenderView.hideDayMonth =hideDayMonth;
        SpanCalenderView.showOnlyNumbers =showOnlyNumbers;
        fragment.check_flag = flag;
        fragment.savedDate = date;
//        Log.i(TAG,"savedDate"+fragment.savedDate);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view  =inflater.inflate(R.layout.span_calender_layout,container,false);
        ageGenerator();
        initializeViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            firstTimegetCurrentDate();
            set.setOnClickListener(this);
            clear.setOnClickListener(this);
            if (title != null) {
                calenderHeadingTextview.setText(title);
            }
            if(savedDate!= null && !savedDate.isEmpty()){
                try {
                    if(!hideDayMonth &&  !showOnlyNumbers){
                        displaysDateMonthsYears(savedDate);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else {
                // pass all date , month , year value in number picker
                pickerViewPassArray(mDayPicker, DATES);
                pickerViewPassArray(mMonthPicker, MONTHS_IN_ENGLISH);
                pickerViewPassArray(mYearPicker, age);
            }

            mDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                    LocalDate today = LocalDate.now();

                    pickerdate = String.valueOf(Math.abs(newVal));
                    Calendar calendar = Calendar.getInstance();
                    int date = calendar.get(Calendar.DATE);
                    int dateset = newVal + date;
                    if(dateset > 30)
                        dateset = dateset - 30;
                    Calendar calendar1 = Calendar.getInstance();
                    int month = calendar1.get(Calendar.MONTH);
                    int monthset =  mMonthPicker.getValue() + month + 1;
                    if(monthset > 12)
                        monthset = monthset - 12;
                    Calendar calendar2 = Calendar.getInstance();
                    int year = calendar2.get(Calendar.YEAR);
                    int yearset = mMonthPicker.getValue() + year;
                    String pickerMonth11 = String.valueOf(Math.abs(monthset));
                    String pickerage11 = String.valueOf(Math.abs(yearset));
                    String pickerdate11 = String.valueOf(Math.abs(dateset));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date birthDate = null;
                    try {
                        birthDate = sdf.parse(newVal+"/"+mMonthPicker.getValue()+"/"+mYearPicker.getValue());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Age age = calculateAge(birthDate);

                    if(title.equals("Next Premium Due Date") || title.equals("Premium Due Till Date") || title.equals("Insurance Paid Due Date") || title.equals("Policy End Date") || title.equals("Maturity Date")) {
                        calenderTextview.setText("Date " + pickerdate11 + ", " + " Month" + pickerMonth11 + ", " + " Year" + pickerage11);

                    }
                    else {
                        calenderTextview.setText("Date " + age.getDays() + ", " + " Month" + age.getMonths() + ", " + " Year" + age.getYears());

                    }
//                    Log.i("", "pickerdate date" + pickerdate);

                }
            });

            mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                    pickerMonth = String.valueOf(Math.abs(newVal));
                    Calendar calendar = Calendar.getInstance();
                    int date = calendar.get(Calendar.DATE);
                    int dateset =  mDayPicker.getValue() + date;
                    if(dateset > 30)
                        dateset = dateset - 30;
                    Calendar calendar1 = Calendar.getInstance();
                    int month = calendar1.get(Calendar.MONTH);
                    int monthset =  newVal + month + 1;
                    if(monthset > 12)
                        monthset = monthset - 12;
                    Calendar calendar2 = Calendar.getInstance();
                    int year = calendar2.get(Calendar.YEAR);
                    int yearset = mYearPicker.getValue() + year;
                    // Log.i("Goku","Veg"+yearset);
                    String pickerage12 = String.valueOf(Math.abs(yearset));
                    String pickerdate12 = String.valueOf(Math.abs(dateset));
                    String pickerMonth12 = String.valueOf(Math.abs(monthset));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date birthDate = null;
                    try {
                        birthDate = sdf.parse(mDayPicker.getValue()+"/"+newVal+"/"+mYearPicker.getValue());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Age age = calculateAge(birthDate);
                    if(title.equals("Next Premium Due Date") || title.equals("Premium Due Till Date") || title.equals("Insurance Paid Due Date") || title.equals("Policy End Date") || title.equals("Maturity Date")) {
                        calenderTextview.setText("Date " + pickerdate12 + ", " + " Month" + pickerMonth12 + ", " + " Year" + pickerage12);

                    }
                    else {
                        calenderTextview.setText("Date " + age.getDays() + ", " + " Month" + age.getMonths() + ", " + " Year" + age.getYears());
                    }
//                    Log.i("", "pickerMonth date" + pickerMonth);

                }
            });
            mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                    pickerage = String.valueOf(Math.abs(newVal));
                    Calendar calendar = Calendar.getInstance();
                    int date = calendar.get(Calendar.DATE);
                    int dateset = mDayPicker.getValue() + date;
                    if(dateset > 30)
                        dateset = dateset - 30;
                    Calendar calendar1 = Calendar.getInstance();
                    int month = calendar1.get(Calendar.MONTH);

                    int monthset = mMonthPicker.getValue() + month + 1;
                    if(monthset > 12)
                        monthset = monthset - 12;
                    Calendar calendar2 = Calendar.getInstance();
                    int year = calendar2.get(Calendar.YEAR);
                    int yearset = newVal + year;
                    int yearset13 = year - newVal;
                    String pickerage13 = String.valueOf(Math.abs(yearset));
                    String pickerdate13 = String.valueOf(Math.abs(dateset));
                    String pickerMonth13 = String.valueOf(Math.abs(monthset));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date birthDate = null;
                    try {
                        birthDate = sdf.parse(mDayPicker.getValue()+"/"+mMonthPicker.getValue()+"/"+Math.abs(newVal));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Age age = calculateAge(birthDate);
                    //Toast.makeText(getContext(),title,Toast.LENGTH_SHORT).show();
                    if(title.equals("Next Premium Due Date") || title.equals("Premium Due Till Date") || title.equals("Insurance Paid Due Date") || title.equals("Policy End Date") || title.equals("Maturity Date")) {
                        calenderTextview.setText("Date " + pickerdate13 + ", " + " Month" + pickerMonth13 + ", " + " Year" + pickerage13);

                    }
                    else  if(title.equals("Select End Year") || title.equals("Select Start Year")){
                        calenderTextview.setText("         "+pickerage13 + "Year ");
                        allmight2 = yearset;
                    }
                    else{
                        calenderTextview.setText("Date " + age.getDays() + ", " + " Month" + age.getMonths() + ", " + " Year" + age.getYears());
                    }
//                    Log.i("", "pickerage date" + pickerage);
                    agecalculation = getcurrentYear(Integer.parseInt(pickerage));
//                    Log.i("", "pickerage getcurrentageYear" + agecalculation);

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void displaysDateMonthsYears(String savedDate) {
        try {

            Date date = null;
            try {
                date = new SimpleDateFormat("dd-MM-yyyy").parse(savedDate);
            } catch (ParseException e) {
                e.printStackTrace();

            }
            String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
//            Log.i(TAG, "dateString2 " + dateString2);
            String[] splitdate = dateString2.split("\\-");
            savedYear = splitdate[0];
            savedMonth = splitdate[1];
            saveddays = splitdate[2];
//            Log.i(TAG, "year" + savedYear + "month " + savedMonth + "days " + saveddays);
            Log.i(TAG, "currentdate" + currentdate + "currentmonth " + currentmonth + "currentyear " + currentyear);
            String yearsToUpdate = getcurrentYear(Integer.parseInt(savedYear));
//            Log.i(TAG, "currentdate" + currentdate + "currentmonth " + currentmonth + "currentyear " + currentyear);
//            Log.i(TAG, "yearsToUpdate onViewCreated " + yearsToUpdate);
            calenderHeadingTextview.setText(title);
            if(!hideDayMonth &&  !showOnlyNumbers) {
                if (limitToCurrentDate) {
                    if(currentdate.equals(saveddays)&& currentmonth.equals(savedMonth)&& currentyear.equals(savedYear)){
                        mDayPicker.setValue(0);
                        mMonthPicker.setValue(0);
                        mYearPicker.setValue(0);
                    }else {
                        instancegetcurrentValue(currentyear, Integer.valueOf(savedYear), currentmonth, Integer.valueOf(savedMonth), currentdate, Integer.valueOf(saveddays));
//                        callBackInterface.updateEditTextValue(savedDate, title);
                    }

                } else {
                    instancegetcurrentValue(Integer.valueOf(savedYear), currentyear, Integer.valueOf(savedMonth), currentmonth, Integer.valueOf(saveddays), currentdate);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            pickerViewPassArray(mDayPicker, DATES);
            pickerViewPassArray(mMonthPicker, MONTHS_IN_ENGLISH);
            pickerViewPassArray(mYearPicker, age);
        }
    }
    private void firstTimegetCurrentDate() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = new Date();
//        Log.i("", "yearGeneratorpastYear date" + dateFormat.format(date));
        String[] items1 = dateFormat.format(date).split("/");
//        Log.i("", "yearGeneratorpastYear year" + items1[0]);
        currentdate= Integer.parseInt(items1[2]);
        currentmonth= Integer.parseInt(items1[1]);
        currentyear= Integer.parseInt(items1[0]);
    }


    String getcurrentYear (int getyear){
        String years = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            Date date = new Date();
//            Log.i("", "yearGeneratorpastYear date" + dateFormat.format(date));
            String[] items1 = dateFormat.format(date).split("/");
//            Log.i("", "yearGeneratorpastYear year" + items1[0]);
            currentdate= Integer.parseInt(items1[2]);
            currentmonth= Integer.parseInt(items1[1]);
            currentyear= Integer.parseInt(items1[0]);
            int updateyears =1;
            if(getyear < currentyear){
                updateyears = currentyear - getyear;
            }else{
                updateyears = getyear - currentyear;
            }

//            Log.i("", "yearGeneratorpastYear updateyears" + updateyears);
            years = String.valueOf(updateyears);
            return years;
        }catch (Exception e){
            e.printStackTrace();
        }
        return years;
    }
    private static void pickerViewPassArrayCurrentvalue(NumberPicker myPicker, String[] dates, String years) {
//        Log.i(TAG, "pickerViewPassArrayCurrentvalue dates"+ dates.length);
        try {
//            if(years.equalsIgnoreCase("0")){
//                years ="1";
//            }
            myPicker.setMinValue(0);
            myPicker.setMaxValue(dates.length);
            myPicker.setValue(Integer.valueOf(years));
            myPicker.setWrapSelectorWheel(true);
            myPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void pickerViewPassArray(NumberPicker myPicker, String[] dates) {
        myPicker.setMinValue(0);
        myPicker.setMaxValue(dates.length );
        myPicker.setWrapSelectorWheel(true);
        myPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
    }


    private void initializeViews(View view ) {

        uparrow1 = view.findViewById(R.id.uparrow1);
        uparrow2 = view.findViewById(R.id.uparrow2);
        downarrow1 = view.findViewById(R.id.downarrow1);
        downarrow2 = view.findViewById(R.id.downarrow2);

        calenderHeadingTextview= view.findViewById(R.id.Calender_heading);
        calenderTextview = view.findViewById(R.id.textview_Display_year_Age);

        mDayPicker = view.findViewById(R.id.numberPicker1);
        mMonthPicker = view.findViewById(R.id.numberPicker2);
        mYearPicker = view.findViewById(R.id.numberPicker3);

        set = view.findViewById(R.id.pickerSetButton);
        clear = view.findViewById(R.id.pickerClearButton);

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
    }



    private void ageGenerator() {
        try {
            age = new String[120];
            for (int i = 1; i < 120; i++) {
                age[i] = String.valueOf(i);
                // Log.i("spcheck", "age[0]" + age[i]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pickerSetButton: {
                try{

                 /*
                    *          Author Suresh
                    *       this method is used set the value in edit text
                    *        in Future calender
                    *
                    *
                    * */
                    if(!hideDayMonth && !limitToCurrentDate && !showOnlyNumbers){

                        if(pickerage!= null && pickerMonth!= null && pickerdate!= null) {
                            String resultYearGetmonth = spancalculationmethod(Integer.valueOf(pickerage), Integer.valueOf(pickerMonth), Integer.valueOf(pickerdate), "Add");
//                            Log.i(TAG, "SpanClender resultYears for add" + resultYearGetmonth);
                            callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                            setEditTextvalue(resultYearGetmonth,calenderTextview);
                        }else if(pickerage!= null && pickerMonth== null && pickerdate== null){
                            if(saveddays!= null && savedMonth!= null) {
                                getpickerdates = mDayPicker.getValue();
                                getpickermonth = mMonthPicker.getValue();
                                String resultYearGetmonth = spancalculationmethod(Integer.valueOf(pickerage), Integer.valueOf(getpickermonth), Integer.valueOf(getpickerdates), "Add");
//                                Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                                setEditTextvalue(resultYearGetmonth,calenderTextview);

                            }else{
                                saveddays ="1";savedMonth="1";
                                instancegetcurrentValue(Integer.valueOf(agecalculation), currentyear, Integer.valueOf(savedMonth), currentmonth, Integer.valueOf(saveddays), currentdate);
                            }
                        }
                        else if(pickerage!= null && pickerMonth!= null && pickerdate== null){
                            if(saveddays!=null){

                                getpickerdates = mDayPicker.getValue();
                                String resultYearGetmonth = spancalculationmethod(Integer.valueOf(pickerage), Integer.valueOf(pickerMonth), Integer.valueOf(getpickerdates), "Add");
//                                Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                                setEditTextvalue(resultYearGetmonth,calenderTextview);

                            }else{
                                saveddays="1";
                                instancegetcurrentValue(Integer.valueOf(agecalculation),currentyear,Integer.valueOf(pickerMonth),currentmonth,Integer.valueOf(saveddays),currentdate);
                            }
                        }else if(pickerage== null && pickerMonth!= null && pickerdate!= null){
                            if(savedYear!= null){
                                getpickerYears = mYearPicker.getValue();
                                String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(pickerMonth), Integer.valueOf(pickerdate), "Add");
//                                Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                                setEditTextvalue(resultYearGetmonth,calenderTextview);
                            }else{
                                savedYear="1";
                                agecalculation= yearcalculation(currentyear,savedYear);
                                instancegetcurrentValue(Integer.valueOf(agecalculation),currentyear,Integer.valueOf(pickerMonth),currentmonth,Integer.valueOf(pickerdate),currentdate);
                            }
                        }else if(pickerage== null && pickerMonth== null && pickerdate!= null){
                            if(savedYear!= null){
                                getpickerYears = mYearPicker.getValue();
                                getpickermonth = mMonthPicker.getValue();
                                String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(getpickermonth), Integer.valueOf(pickerdate), "Add");
                                Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                                setEditTextvalue(resultYearGetmonth,calenderTextview);
                            }else{

                                getpickerYears = mYearPicker.getValue();
                                getpickerdates = mDayPicker.getValue();
                                getpickermonth = mMonthPicker.getValue();
                                String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(getpickermonth), Integer.valueOf(getpickerdates), "Add");
//                                Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                                setEditTextvalue(resultYearGetmonth,calenderTextview);
                            }
                        }
                        else{
                            if(saveddays.equals("")&& savedMonth.equals("")&& savedYear.equals("")) {
                                calenderTextview.setText("Your " + title);
                            }else {
                                getpickerYears = mYearPicker.getValue();
                                getpickerdates = mDayPicker.getValue();
                                getpickermonth = mMonthPicker.getValue();
                                String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(getpickermonth), Integer.valueOf(getpickerdates), "Add");
//                                Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                                setEditTextvalue(resultYearGetmonth, calenderTextview);
                            }
                        }
                    }
                    else  if(!hideDayMonth && limitToCurrentDate && !showOnlyNumbers){
                        if (pickerage != null && pickerMonth != null && pickerdate != null) {
                            String resultYearGetmonth = spancalculationmethod(Integer.valueOf(pickerage), Integer.valueOf(pickerMonth), Integer.valueOf(pickerdate), "Sub");
//                            Log.i(TAG, "SpanClender resultYears for add" + resultYearGetmonth);
                            callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                            setEditTextvalue(resultYearGetmonth, calenderTextview);
                        } else if (pickerage != null && pickerMonth == null && pickerdate == null) {
                            if (saveddays != null && savedMonth != null) {
                                getpickerdates = mDayPicker.getValue();
                                getpickermonth = mMonthPicker.getValue();
                                String resultYearGetmonth = spancalculationmethod(Integer.valueOf(pickerage), Integer.valueOf(getpickermonth), Integer.valueOf(getpickerdates), "Sub");
//                                Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                                setEditTextvalue(resultYearGetmonth, calenderTextview);

                            } else {
                                saveddays = "1";
                                savedMonth = "1";
                                instancegetcurrentValue(currentyear, Integer.valueOf(agecalculation), currentmonth, Integer.valueOf(savedMonth), currentdate, Integer.valueOf(saveddays));
                            }
                        } else if (pickerage != null && pickerMonth != null && pickerdate == null) {
                            if (saveddays != null) {
                                getpickerdates = mDayPicker.getValue();
                                String resultYearGetmonth = spancalculationmethod(Integer.valueOf(pickerage), Integer.valueOf(pickerMonth), Integer.valueOf(getpickerdates), "Sub");
//                                Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                                setEditTextvalue(resultYearGetmonth, calenderTextview);

                            } else {
                                saveddays = "1";
                                instancegetcurrentValue(currentyear, Integer.valueOf(agecalculation), currentmonth, Integer.valueOf(savedMonth), currentdate, Integer.valueOf(saveddays));
                            }
                        } else if (pickerage == null && pickerMonth != null && pickerdate != null) {
                            if (savedYear != null) {
                                getpickerYears = mYearPicker.getValue();
                                String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(pickerMonth), Integer.valueOf(pickerdate), "Sub");
//                                Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                                setEditTextvalue(resultYearGetmonth, calenderTextview);
                            } else {
                                savedYear = "1";
                                agecalculation = yearcalculation(currentyear, savedYear);
                                instancegetcurrentValue(currentyear, Integer.valueOf(agecalculation), currentmonth, Integer.valueOf(savedMonth), currentdate, Integer.valueOf(saveddays));
                            }
                        } else if (pickerage == null && pickerMonth == null && pickerdate != null) {
                            if (savedYear != null) {
                                getpickerYears = mYearPicker.getValue();
                                getpickermonth = mMonthPicker.getValue();
                                String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(getpickermonth), Integer.valueOf(pickerdate), "Sub");
//                                Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                                setEditTextvalue(resultYearGetmonth, calenderTextview);
                            } else {
                                getpickerYears = mYearPicker.getValue();
                                getpickerdates = mDayPicker.getValue();
                                getpickermonth = mMonthPicker.getValue();
                                String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(getpickermonth), Integer.valueOf(getpickerdates), "Sub");
//                                Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                                setEditTextvalue(resultYearGetmonth, calenderTextview);
                            }
                        } else {
                            if(saveddays.equals("")&& savedMonth.equals("")&& savedYear.equals("")) {
                                calenderTextview.setText("Your " + title);
                            }else {
                                getpickerYears = mYearPicker.getValue();
                                getpickerdates = mDayPicker.getValue();
                                getpickermonth = mMonthPicker.getValue();
                                String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(getpickermonth), Integer.valueOf(getpickerdates), "Sub");
//                                Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                                setEditTextvalue(resultYearGetmonth, calenderTextview);
                            }
                        }
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            break;
            case R.id.pickerClearButton:{
                try {
                    if (title != null) {
                        calenderTextview.setText("Your " + title);
                        pickerViewPassArrayCurrentvalue(mDayPicker, DATES,"0");
                        pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH,"0");
                        pickerViewPassArrayCurrentvalue(mYearPicker, age,"0");
                        savedYear="";savedMonth="";saveddays="";
                        pickerdate =null;pickerMonth=null;pickerage =null;
                        callBackInterface.updateEditTextValue("", title);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            break;
            case R.id.numberPicker_fab: {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void setEditTextvalue(String resultYearGetmonth, TextView calenderTextview) {

        try{
            Date date = null;
            try {
                date = new SimpleDateFormat("dd-MM-yyyy").parse(resultYearGetmonth);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dateString2 = new SimpleDateFormat("yyyy-MM-dd").format(date);
//            Log.i(TAG, "dateString2 " + dateString2);
            String[] splitdate = dateString2.split("\\-");
            String setsavedYear = splitdate[0];
            String setsavedMonth = splitdate[1];
            String setsaveddays = splitdate[2];
//            Log.i(TAG, "year" + setsavedYear + "month " + setsavedMonth + "days " + setsaveddays);
//            Log.i(TAG, "currentdate" + currentdate + "currentmonth " + currentmonth + "currentyear " + currentyear);
            calenderTextview.setText("Date "+   splitdate[2] +", "+ "Month " + splitdate[1]  + ", " + "Year " + splitdate[0] );

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String concatinatewithDateFormat(String resultYearGetmonth) {
        String output = null;
        try{
            String[] splitdateyers = resultYearGetmonth.split("\\-");
            String splitYear = splitdateyers[0];
            String splitMonth = splitdateyers[1];
            String splitDays = splitdateyers[2];
//            Log.i(TAG, "splitYear" + splitYear + "splitMonth " + splitMonth + "splitDays " + splitDays);
            String postionMonth = getMonthString(splitdateyers[1]);
//            Log.i(TAG, "postionMonth" + postionMonth);
            output = splitYear+"-"+ postionMonth+"-"+splitDays;
        }catch (Exception e){
            e.printStackTrace();
        }
        return output;
    }
    private static String getMonthString(String i) {
        String output = null;
        try {
            if (i.equalsIgnoreCase("JAN")) {
                output = "01";
            } else if (i.equalsIgnoreCase("FEB")) {
                output = "02";
            } else if (i.equalsIgnoreCase("MAR")) {
                output = "03";
            } else if (i.equalsIgnoreCase("APR")) {
                output = "04";
            } else if (i.equalsIgnoreCase("MAY")) {
                output = "05";
            } else if (i.equalsIgnoreCase("JUN")) {
                output = "06";
            } else if (i.equalsIgnoreCase("JUL")) {
                output = "07";
            } else if (i.equalsIgnoreCase("AUG")) {
                output = "08";
            } else if (i.equalsIgnoreCase("SEP")) {
                output = "09";
            } else if (i.equalsIgnoreCase("OCT")) {
                output = "10";
            } else if (i.equalsIgnoreCase("NOV")) {
                output = "11";
            } else if (i.equalsIgnoreCase("DEC")) {
                output = "12";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return output;
    }
    /*
    *        Author Suresh
    *     spancalculationmethod is used for calculating the
    *     years both passed and future.
    *     in parameter addOrSubtract is used for mention what type of
    *     calculation you need , Wheather to add r sub.
    *
    *
    * */

    private static String spancalculationmethod(Integer year, Integer month, Integer days, String addOrSubtract) {
        String yearsmonthdays = null;
        try{
            if (addOrSubtract.equalsIgnoreCase("Add")) {

                DateTimeZone timeZone = DateTimeZone.forID("America/Montreal");

                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd").withZone(timeZone);

                DateTime dateTimeStart = formatter.parseDateTime(currentyear + "/" + (currentmonth) + "/" + currentdate);
                //  DateTime dateTimeStop = formatter.parseDateTime(year_2 + "/" + (month_2) + "/" + day_2);

                DateTime dateTime = new DateTime(dateTimeStart);
                System.out.println(dateTime);
                System.out.println(dateTime.toString("yyyy/MM/dd"));
                PeriodFormatter periodFormatter = PeriodFormat.wordBased();
                dateTime = dateTime.plusYears(year).plusMonths(month).plusDays(days);
                //Log.e("TAG", "new" + dateTime.toString("yyyy-MMM-dd"));

                // String daycon=String.valueOf(day1);
                String YYMMDD = String.valueOf(dateTime.toString("dd-MMM-yyyy"));
                yearsmonthdays= concatinatewithDateFormat(YYMMDD);
                return  yearsmonthdays;
            }
            else {
                DateTimeZone timeZone = DateTimeZone.forID("America/Montreal");

                DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd").withZone(timeZone);

                DateTime dateTimeStart = formatter.parseDateTime(currentyear + "/" + (currentmonth) + "/" + currentdate);
                //  DateTime dateTimeStop = formatter.parseDateTime(year_2 + "/" + (month_2) + "/" + day_2);

                DateTime dateTime = new DateTime(dateTimeStart);
                System.out.println(dateTime);
                System.out.println(dateTime.toString("dd-MMM-yyyy"));
                PeriodFormatter periodFormatter = PeriodFormat.wordBased();
                dateTime = dateTime.minusYears(year).minusMonths(month).minusDays(days);
                //Log.e("TAG", "new" + dateTime.toString("yyyy-MMM-dd"));
                String YYMMDD = String.valueOf(dateTime.toString("dd-MMM-yyyy"));
                yearsmonthdays= concatinatewithDateFormat(YYMMDD);
                return  yearsmonthdays;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  yearsmonthdays;
    }

    private static String yearcalculation(Integer currentyear, String agecalculation) {
        String years = null;
        int updateyears=1;
        if(Integer.valueOf(agecalculation) < currentyear){
            updateyears = currentyear - Integer.valueOf(agecalculation);
        }else{
            updateyears = Integer.valueOf(agecalculation) - currentyear;
        }
//        Log.i("", "yearGeneratorpastYear updateyears" + updateyears);
        years = String.valueOf(updateyears);
        return years;
    }

    /*
    *      Author by Suresh
    *     instancegetcurrentValue is used to get current number
    *     for eg: years 2019 = 2 years, 2months = 2 month
    *
    *
    *
    * */

    private static void instancegetcurrentValue(int year_2, int year_1, int month_2, int month_1, int day_2, int day_1) {
        try {
            DateTimeZone timeZone = DateTimeZone.forID("America/Montreal");
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd").withZone(timeZone);
            //Log.e("Sample", "year2: " + year_2 + " ,month2 :" + month_2 + " day2 :" + day_2);
            //Log.e("Sample1", "year1: " + year_1 + " ,month1 :" + month_1 + " day1 :" + day_1);
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
            //Log.e("Sample1", "instancegetcurrentValue output" + output);
            //Log.e("Sample1", "instancegetcurrentValue output length" + output.length());
            String[] splityears = output.split("\\s");
            if(output.length()<=8){
                // only years should be display
                pickerViewPassArrayCurrentvalue(mDayPicker, DATES, "0");
                pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH,"0" );
                if (splityears[0] != null){
                    pickerViewPassArrayCurrentvalue(mYearPicker, age, String.valueOf(splityears[0]));
                    calenderTextview.setText("Date "+  currentdate +" ,"+ "Month " + currentmonth + "," + "Year " + savedYear );
                    callBackInterface.updateEditTextValue(currentdate + "-" + currentmonth + "-" + year_2, title);
                }else{
                    pickerViewPassArrayCurrentvalue(mYearPicker, age, "1");
                    calenderTextview.setText("Date "+  "0" +", "+ " Month" + "0" + ", " + "Year " + "1" );
                    callBackInterface.updateEditTextValue(currentdate + "-" + currentmonth + "-" + currentyear, title);
                }

            }else if(output.length()>14 && output.length()<=21){
                //Log.e("Sample1", "instancegetcurrentValue splityears" + splityears[0]);
                //Log.e("Sample1", "instancegetcurrentValue splitmonth" + splityears[3]);
                // only years and month should be display
                if(splityears[1].equalsIgnoreCase("month")&& splityears[4].equalsIgnoreCase("days"))
                {
                    pickerViewPassArrayCurrentvalue(mYearPicker, age, String.valueOf("0"));
                    if (splityears[0] != null) {
                        pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH, String.valueOf(splityears[0]));
                    } else {
                        pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH, String.valueOf(currentmonth));
                    }
                    if (splityears[3] != null) {
                        pickerViewPassArrayCurrentvalue(mDayPicker, DATES, String.valueOf(splityears[3]));
                    } else {
                        pickerViewPassArrayCurrentvalue(mDayPicker, DATES, String.valueOf("0"));
                    }
                    if (splityears[0] != null && splityears[3] != null) {
                        calenderTextview.setText("Date " + saveddays + ", " + " Month" + savedMonth + " ," + " Year" + savedYear);
                        callBackInterface.updateEditTextValue(day_2 + "-" + month_2 + "-" + year_2, title);
                    } else {
                        calenderTextview.setText("Date " + currentdate + ", " + " Month" + currentmonth + ", " + " Year" + currentyear);
                        callBackInterface.updateEditTextValue("", title);
                    }
                }else if(splityears[1].equalsIgnoreCase("month")&& splityears[4].equalsIgnoreCase("day")){
                    pickerViewPassArrayCurrentvalue(mYearPicker, age, String.valueOf("0"));
                    if (splityears[0] != null) {
                        pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH, String.valueOf(splityears[0]));
                    } else {
                        pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH, String.valueOf(currentmonth));
                    }
                    if (splityears[3] != null) {
                        pickerViewPassArrayCurrentvalue(mDayPicker, DATES, String.valueOf(splityears[3]));
                    } else {
                        pickerViewPassArrayCurrentvalue(mDayPicker, DATES, String.valueOf("0"));
                    }
                    if (splityears[0] != null && splityears[3] != null) {
                        calenderTextview.setText("Date " + saveddays + ", " + " Month" + savedMonth + " ," + " Year" + savedYear);
                        callBackInterface.updateEditTextValue(day_2 + "-" + month_2 + "-" + year_2, title);
                    } else {
                        calenderTextview.setText("Date " + currentdate + ", " + " Month" + currentmonth + ", " + " Year" + currentyear);
                        callBackInterface.updateEditTextValue("", title);
                    }
                }
                else if(splityears[1].equalsIgnoreCase("months")&& splityears[4].equalsIgnoreCase("days")){
                    pickerViewPassArrayCurrentvalue(mYearPicker, age, String.valueOf("0"));
                    if (splityears[0] != null) {
                        pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH, String.valueOf(splityears[0]));
                    } else {
                        pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH, String.valueOf(currentmonth));
                    }
                    if (splityears[3] != null) {
                        pickerViewPassArrayCurrentvalue(mDayPicker, DATES, String.valueOf(splityears[3]));
                    } else {
                        pickerViewPassArrayCurrentvalue(mDayPicker, DATES, String.valueOf("0"));
                    }
                    if (splityears[0] != null && splityears[3] != null) {
                        calenderTextview.setText("Date " + saveddays + ", " + " Month" + savedMonth + " ," + " Year" + savedYear);
                        callBackInterface.updateEditTextValue(day_2 + "-" + month_2 + "-" + year_2, title);
                    } else {
                        calenderTextview.setText("Date " + currentdate + ", " + " Month" + currentmonth + ", " + " Year" + currentyear);
                        callBackInterface.updateEditTextValue("", title);
                    }
                }else {
                    pickerViewPassArrayCurrentvalue(mDayPicker, DATES, "0");
                    if (splityears[3] != null) {
                        pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH, String.valueOf(splityears[3]));
                    } else {
                        pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH, String.valueOf(currentmonth));
                    }
                    if (splityears[0] != null) {
                        pickerViewPassArrayCurrentvalue(mYearPicker, age, String.valueOf(splityears[0]));
                    } else {
                        pickerViewPassArrayCurrentvalue(mYearPicker, age, String.valueOf("0"));
                    }
                    if (splityears[0] != null && splityears[3] != null) {
                        calenderTextview.setText("Date " + "0" + ", " + " Month" + savedMonth + " ," + " Year" + savedYear);
                        callBackInterface.updateEditTextValue(day_2 + "-" + month_2 + "-" + year_2, title);
                    } else {
                        calenderTextview.setText("Date " + currentdate + ", " + " Month" + currentmonth + ", " + " Year" + currentyear);
                        callBackInterface.updateEditTextValue("", title);
                    }
                }

            }else if(output.length()<=14){
                pickerViewPassArray(mDayPicker, DATES);
                pickerViewPassArray(mMonthPicker, MONTHS_IN_ENGLISH);
                pickerViewPassArray(mYearPicker, age);
                calenderTextview.setText("Date "+  "0" +","+ " Month" + "0" + ", " + "Year " + "0" );
            }
            else if(output.length()<=0){
                pickerViewPassArray(mDayPicker, DATES);
                pickerViewPassArray(mMonthPicker, MONTHS_IN_ENGLISH);
                pickerViewPassArray(mYearPicker, age);
                calenderTextview.setText("Date "+  "0" +","+ " Month" + "0" + "," + " Year " + "0" );
            }

            else {
                // only years , months and Dates should be display
                if (splityears[5] != null) {

                    pickerViewPassArrayCurrentvalue(mDayPicker, DATES, String.valueOf(splityears[5]));
                } else {
                    pickerViewPassArrayCurrentvalue(mDayPicker, DATES, String.valueOf(currentdate));
                }
                if (splityears[2] != null) {
                    pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH, String.valueOf(splityears[2]));
                } else {
                    pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH, String.valueOf(currentmonth));
                }
                if (splityears[0] != null) {
                    pickerViewPassArrayCurrentvalue(mYearPicker, age, String.valueOf(splityears[0]));
                } else {
                    pickerViewPassArrayCurrentvalue(mMonthPicker, MONTHS_IN_ENGLISH, String.valueOf("1"));
                }
                if (splityears[0] != null && splityears[2] != null && splityears[5] != null) {
                    if(savedYear!= null){
                        calenderTextview.setText("Date "+  saveddays +", "+ "Month " + savedMonth + "," + " Year " + savedYear );
                    }else{
                        int yearupdate = currentyear - Integer.parseInt(splityears[0]);
                        calenderTextview.setText("Date "+  saveddays +", "+ "Month " + savedMonth + "," + " Year " + yearupdate );
                    }

                    callBackInterface.updateEditTextValue(day_2 + "-" + month_2 + "-" + year_2, title);
                } else {
                    calenderTextview.setText("Date "+  currentdate +", "+ " Month" + currentmonth + "," + " Year " + savedYear );
                    callBackInterface.updateEditTextValue("", title);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            pickerViewPassArray(mDayPicker, DATES);
            pickerViewPassArray(mMonthPicker, MONTHS_IN_ENGLISH);
            pickerViewPassArray(mYearPicker, age);
        }
    }

    public static void spantr(){

        try{

                 /*
                    *          Author Suresh
                    *       this method is used set the value in edit text
                    *        in Future calender
                    *
                    *
                    * */
            if(title.equals("Select End Year") || title.equals("Select Start Year")){
                String resultYearGetmonth = String.valueOf(allmight2);
//                Log.i(TAG, "SpanClender resultYears for add" + resultYearGetmonth);
                callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                //Tax file date individual
                callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);

                setEditTextvalue(resultYearGetmonth,calenderTextview);
            }
            else
            if(!hideDayMonth && !limitToCurrentDate && !showOnlyNumbers){

                if(pickerage!= null && pickerMonth!= null && pickerdate!= null) {
                    String resultYearGetmonth = spancalculationmethod(Integer.valueOf(pickerage), Integer.valueOf(pickerMonth), Integer.valueOf(pickerdate), "Add");

                    callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                    //Tax file date individual
                    callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);


                    setEditTextvalue(resultYearGetmonth,calenderTextview);
                }else if(pickerage!= null && pickerMonth== null && pickerdate== null){
                    if(saveddays!= null && savedMonth!= null) {
                        getpickerdates = mDayPicker.getValue();
                        getpickermonth = mMonthPicker.getValue();
                        String resultYearGetmonth = spancalculationmethod(Integer.valueOf(pickerage), Integer.valueOf(getpickermonth), Integer.valueOf(getpickerdates), "Add");

                        callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                        //Tax file date individual
                        callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);

                        setEditTextvalue(resultYearGetmonth,calenderTextview);

                    }else{
                        saveddays ="1";savedMonth="1";
                        instancegetcurrentValue(Integer.valueOf(agecalculation), currentyear, Integer.valueOf(savedMonth), currentmonth, Integer.valueOf(saveddays), currentdate);
                    }
                }
                else if(pickerage!= null && pickerMonth!= null && pickerdate== null){
                    if(saveddays!=null){

                        getpickerdates = mDayPicker.getValue();
                        String resultYearGetmonth = spancalculationmethod(Integer.valueOf(pickerage), Integer.valueOf(pickerMonth), Integer.valueOf(getpickerdates), "Add");

                        callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                        //Tax file date individual
                        callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);

                        setEditTextvalue(resultYearGetmonth,calenderTextview);

                    }else{
                        saveddays="1";
                        instancegetcurrentValue(Integer.valueOf(agecalculation),currentyear,Integer.valueOf(pickerMonth),currentmonth,Integer.valueOf(saveddays),currentdate);
                    }
                }else if(pickerage== null && pickerMonth!= null && pickerdate!= null){
                    if(savedYear!= null){
                        getpickerYears = mYearPicker.getValue();
                        String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(pickerMonth), Integer.valueOf(pickerdate), "Add");

                        callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                        //Tax file date individual
                        callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);

                        setEditTextvalue(resultYearGetmonth,calenderTextview);
                    }else{
                        savedYear="1";
                        agecalculation= yearcalculation(currentyear,savedYear);
                        instancegetcurrentValue(Integer.valueOf(agecalculation),currentyear,Integer.valueOf(pickerMonth),currentmonth,Integer.valueOf(pickerdate),currentdate);
                    }
                }else if(pickerage== null && pickerMonth== null && pickerdate!= null){
                    if(savedYear!= null){
                        getpickerYears = mYearPicker.getValue();
                        getpickermonth = mMonthPicker.getValue();
                        String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(getpickermonth), Integer.valueOf(pickerdate), "Add");

                        callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                        //Tax file date individual
                        callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);

                        setEditTextvalue(resultYearGetmonth,calenderTextview);
                    }else{

                        getpickerYears = mYearPicker.getValue();
                        getpickerdates = mDayPicker.getValue();
                        getpickermonth = mMonthPicker.getValue();
                        String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(getpickermonth), Integer.valueOf(getpickerdates), "Add");

                        callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                        //Tax file date individual
                        callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);

                        setEditTextvalue(resultYearGetmonth,calenderTextview);
                    }
                }
                else{
                    if(saveddays.equals("")&& savedMonth.equals("")&& savedYear.equals("")) {
                        calenderTextview.setText("Your " + title);
                    }else {
                        getpickerYears = mYearPicker.getValue();
                        getpickerdates = mDayPicker.getValue();
                        getpickermonth = mMonthPicker.getValue();
                        String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(getpickermonth), Integer.valueOf(getpickerdates), "Add");

                        callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                        //Tax file date individual
                        callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);
                        setEditTextvalue(resultYearGetmonth, calenderTextview);
                    }
                }
            }
            else  if(!hideDayMonth && limitToCurrentDate && !showOnlyNumbers){
                if (pickerage != null && pickerMonth != null && pickerdate != null) {
                    String resultYearGetmonth = spancalculationmethod(Integer.valueOf(pickerage), Integer.valueOf(pickerMonth), Integer.valueOf(pickerdate), "Sub");

                    callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                    //Tax file date individual
                    callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);
                    setEditTextvalue(resultYearGetmonth, calenderTextview);
                } else if (pickerage != null && pickerMonth == null && pickerdate == null) {
                    if (saveddays != null && savedMonth != null) {
                        getpickerdates = mDayPicker.getValue();
                        getpickermonth = mMonthPicker.getValue();
                        String resultYearGetmonth = spancalculationmethod(Integer.valueOf(pickerage), Integer.valueOf(getpickermonth), Integer.valueOf(getpickerdates), "Sub");

                        callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                        //Tax file date individual
                        callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);
                        setEditTextvalue(resultYearGetmonth, calenderTextview);

                    } else {
                        saveddays = "1";
                        savedMonth = "1";
                        instancegetcurrentValue(currentyear, Integer.valueOf(agecalculation), currentmonth, Integer.valueOf(savedMonth), currentdate, Integer.valueOf(saveddays));
                    }
                } else if (pickerage != null && pickerMonth != null && pickerdate == null) {
                    if (saveddays != null) {
                        getpickerdates = mDayPicker.getValue();
                        String resultYearGetmonth = spancalculationmethod(Integer.valueOf(pickerage), Integer.valueOf(pickerMonth), Integer.valueOf(getpickerdates), "Sub");

                        callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                        //Tax file date individual
                        callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);
                        setEditTextvalue(resultYearGetmonth, calenderTextview);

                    } else {
                        saveddays = "1";
                        instancegetcurrentValue(currentyear, Integer.valueOf(agecalculation), currentmonth, Integer.valueOf(savedMonth), currentdate, Integer.valueOf(saveddays));
                    }
                } else if (pickerage == null && pickerMonth != null && pickerdate != null) {
                    if (savedYear != null) {
                        getpickerYears = mYearPicker.getValue();
                        String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(pickerMonth), Integer.valueOf(pickerdate), "Sub");

                        callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                        //Tax file date individual
                        callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);
                        setEditTextvalue(resultYearGetmonth, calenderTextview);
                    } else {
                        savedYear = "1";
                        agecalculation = yearcalculation(currentyear, savedYear);
                        instancegetcurrentValue(currentyear, Integer.valueOf(agecalculation), currentmonth, Integer.valueOf(savedMonth), currentdate, Integer.valueOf(saveddays));
                    }
                } else if (pickerage == null && pickerMonth == null && pickerdate != null) {
                    if (savedYear != null) {
                        getpickerYears = mYearPicker.getValue();
                        getpickermonth = mMonthPicker.getValue();
                        String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(getpickermonth), Integer.valueOf(pickerdate), "Sub");
//                        Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                        callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                        //Tax file date individual
                        callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);
                        setEditTextvalue(resultYearGetmonth, calenderTextview);
                    } else {
                        getpickerYears = mYearPicker.getValue();
                        getpickerdates = mDayPicker.getValue();
                        getpickermonth = mMonthPicker.getValue();
                        String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(getpickermonth), Integer.valueOf(getpickerdates), "Sub");
//                        Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                        callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                        //Tax file date individual
                        callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);
                        setEditTextvalue(resultYearGetmonth, calenderTextview);
                    }
                } else {
                    if(saveddays.equals("")&& savedMonth.equals("")&& savedYear.equals("")) {
                        calenderTextview.setText("Your " + title);
                    }else {
                        getpickerYears = mYearPicker.getValue();
                        getpickerdates = mDayPicker.getValue();
                        getpickermonth = mMonthPicker.getValue();
                        String resultYearGetmonth = spancalculationmethod(Integer.valueOf(getpickerYears), Integer.valueOf(getpickermonth), Integer.valueOf(getpickerdates), "Sub");
//                        Log.i(TAG, "SpanClender resultYears for pickerage!= null" + resultYearGetmonth);
                        callBackInterface.updateEditTextValue(resultYearGetmonth, title);
                        //Tax file date individual
                        callBackInterface.updateIndividualEditTextValue(resultYearGetmonth, title);
                        setEditTextvalue(resultYearGetmonth, calenderTextview);
                    }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
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
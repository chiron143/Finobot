package com.finobot.finobot.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CalendarView;

import com.finobot.finobot.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarDemoActivity extends Activity implements
        CalendarView.OnDateChangeListener {
    CalendarView calendar=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        calendar= findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(this);
    }

    @Override
    public void onSelectedDayChange(CalendarView view, int year,
                                    int monthOfYear, int dayOfMonth) {
        Calendar then=new GregorianCalendar(year, monthOfYear, dayOfMonth);


    }

}

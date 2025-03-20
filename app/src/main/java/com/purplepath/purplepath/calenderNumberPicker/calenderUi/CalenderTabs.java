package com.purplepath.purplepath.calenderNumberPicker.calenderUi;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.dialog.AssetDatePickerDialogFragment;
import com.purplepath.purplepath.calenderNumberPicker.DialogFragmentCallbackInterface;
import com.purplepath.purplepath.calenderNumberPicker.adapter.CalenderPagerAdapter;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;

import static com.purplepath.purplepath.apputiles.UtileKit.dialog;


/**
 * Created by Suresh on 26/05/17.
 */

public class CalenderTabs  extends androidx.fragment.app.DialogFragment {
    private final static String TAG = CalenderTabs.class.getCanonicalName();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DatePickDialogFragment mDatePickDialogFragment;
    private SpanCalenderView mSpanCalenderView;
    private AssetDatePickerDialogFragment massetDatePickerDialogFragment;
    private DialogFragmentCallbackInterface callbackInterface;
    private DatePickerCallBackInterface callBackInterface;
    private Context mcontext;
    private String datepickerdob;
    private String datepickerage;
    private String datepickergetDobDate;
    private String tabTobecheck;
    private  String savedDate;
    private FloatingActionButton fab;
    private Boolean limitToCurrentDate,hideDayMonth,showOnlyNumbers;
    private Bundle args;//=new Bundle();
    public int pagechange = 0;
    public AssetDatePickerDialogFragment adpd = new AssetDatePickerDialogFragment();
    public SpanCalenderView scv = new SpanCalenderView();

    CalenderPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View calenderview = inflater.inflate(R.layout.calender_tab_fragment, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.white);
        viewPager = (ViewPager) calenderview.findViewById(R.id.calender_viewpager);
        tabLayout = (TabLayout) calenderview.findViewById(R.id.calender_tabs);
        fab = (FloatingActionButton) calenderview.findViewById(R.id.fab);


        args=getArguments();

        mSpanCalenderView = SpanCalenderView.newInstance(callBackInterface, datepickerdob
                , limitToCurrentDate, hideDayMonth, showOnlyNumbers,"1",savedDate);
        massetDatePickerDialogFragment = AssetDatePickerDialogFragment.newInstance(callBackInterface, datepickerdob
                , limitToCurrentDate, hideDayMonth, showOnlyNumbers ,savedDate);
        adapter = new CalenderPagerAdapter(getChildFragmentManager());


        String getBoth = UtileKit.getPersistedPurplePathPref("Setting_Calender",null);

        Log.d("","getBoth"+getBoth);

        if(getBoth!=null)
        {
            setupViewPager(viewPager,getBoth);
        }else {
            setupViewPagers(viewPager);
        }





        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagechange = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return  calenderview;
    }




    private void setupViewPager(ViewPager viewPager, String getBoth) {


        if(getBoth.equalsIgnoreCase("Both")) {

            if (datepickerdob.equalsIgnoreCase("Date Of Birth")) {
                if (tabTobecheck.equalsIgnoreCase("0")) {
                    adapter.addFragment(mDatePickDialogFragment, "Choose Date");
                    adapter.addFragment(mSpanCalenderView, "Choose Age");
                } else {
                    adapter.addFragment(massetDatePickerDialogFragment, "Choose Date");
                    adapter.addFragment(mSpanCalenderView, "Choose Age");
                }
            } else {
                if (tabTobecheck.equalsIgnoreCase("0")) {
                    adapter.addFragment(mDatePickDialogFragment, "Choose Date");
                    adapter.addFragment(mSpanCalenderView, "Choose Duration");
                } else {
                    adapter.addFragment(massetDatePickerDialogFragment, "Choose Date");
                    adapter.addFragment(mSpanCalenderView, "Choose Duration");
                }
            }
        }

        else if(getBoth.equalsIgnoreCase("Default")){

            if (datepickerdob.equalsIgnoreCase("Date Of Birth")) {
                if (tabTobecheck.equalsIgnoreCase("0")) {
                    adapter.addFragment(mDatePickDialogFragment, "Choose Date");
                  //  adapter.addFragment(mSpanCalenderView, "Choose Age");
                } else {
                    adapter.addFragment(massetDatePickerDialogFragment, "Choose Date");
                 //   adapter.addFragment(mSpanCalenderView, "Choose Age");
                }
            } else {
                if (tabTobecheck.equalsIgnoreCase("0")) {
                    adapter.addFragment(mDatePickDialogFragment, "Choose Date");
                  //  adapter.addFragment(mSpanCalenderView, "Choose Duration");
                } else {
                    adapter.addFragment(massetDatePickerDialogFragment, "Choose Date");
                  //  adapter.addFragment(mSpanCalenderView, "Choose Duration");
                }
            }
        }
        else {
            if (datepickerdob.equalsIgnoreCase("Date Of Birth")) {
                if (tabTobecheck.equalsIgnoreCase("0")) {
                   // adapter.addFragment(mDatePickDialogFragment, "Choose Date");
                     adapter.addFragment(mSpanCalenderView, "Choose Age");
                } else {
                   // adapter.addFragment(massetDatePickerDialogFragment, "Choose Date");
                       adapter.addFragment(mSpanCalenderView, "Choose Age");
                }
            } else {
                if (tabTobecheck.equalsIgnoreCase("0")) {
                    //adapter.addFragment(mDatePickDialogFragment, "Choose Date");
                      adapter.addFragment(mSpanCalenderView, "Choose Duration");
                } else {
                    //adapter.addFragment(massetDatePickerDialogFragment, "Choose Date");
                      adapter.addFragment(mSpanCalenderView, "Choose Duration");
                }
            }
        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPagers(ViewPager viewPager) {

        if (datepickerdob.equalsIgnoreCase("Date Of Birth")) {
            if (tabTobecheck.equalsIgnoreCase("0")) {
                adapter.addFragment(mDatePickDialogFragment, "Choose Date");
                adapter.addFragment(mSpanCalenderView, "Choose Age");
            } else {
                adapter.addFragment(massetDatePickerDialogFragment, "Choose Date");
                adapter.addFragment(mSpanCalenderView, "Choose Age");
            }
        } else {
            if (tabTobecheck.equalsIgnoreCase("0")) {
                adapter.addFragment(mDatePickDialogFragment, "Choose Date");
                adapter.addFragment(mSpanCalenderView, "Choose Duration");
            } else {
                adapter.addFragment(massetDatePickerDialogFragment, "Choose Date");
                adapter.addFragment(mSpanCalenderView, "Choose Duration");
            }
        }
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (pagechange == 0) {
            AssetDatePickerDialogFragment.asstr();

        } else if(pagechange == 1){
            SpanCalenderView.spantr();
        }

    }





    //not used
    public static CalenderTabs newInstance(Context mContext, String dob, String age, String getDobDate,
                                           DialogFragmentCallbackInterface callbackInterface, String checkingtabs) {

        CalenderTabs fragment=new CalenderTabs();
        fragment.mcontext=mContext;
        fragment.datepickerdob=dob;
        fragment.datepickerage=age;
        fragment.datepickergetDobDate=getDobDate;
        fragment.callbackInterface = callbackInterface;
        fragment.tabTobecheck = checkingtabs;
        Log.i(TAG,"datepickerdob tabTobecheck"+ fragment.tabTobecheck);
        Log.i(TAG,"datepickerdob"+ fragment.datepickerdob);
        Log.i(TAG,"datepickerage"+ fragment.datepickerage);
        Log.i(TAG,"datepickergetDobDate"+ fragment.datepickergetDobDate);
        return fragment;
    }


    public static CalenderTabs newInstance(DatePickerCallBackInterface callBackInterface,
                                           String title, Boolean limitToCurrentDate,
                                           Boolean hideDayMonth, Boolean showOnlyNumbers, String checkingtabs, String date) {

        CalenderTabs fragment=new CalenderTabs();
        fragment.callBackInterface = callBackInterface;
        fragment.datepickerdob=title;
        fragment.limitToCurrentDate=limitToCurrentDate;
        fragment.hideDayMonth=hideDayMonth;
        fragment.showOnlyNumbers=showOnlyNumbers;
        fragment.tabTobecheck = checkingtabs;
        fragment.savedDate = date;
        Log.i(TAG,"datepickerdob tabTobecheck"+ fragment.tabTobecheck);
        Log.i(TAG,"datepickerdob"+ fragment.datepickerdob);
        Log.i(TAG,"datepickerage"+ fragment.datepickerage);
        Log.i(TAG,"datepickergetDobDate"+ fragment.datepickergetDobDate);
        return fragment;
    }



}

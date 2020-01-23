package com.fourmob.datetimepicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.accessibility.AccessibilityManager;

import com.finobot.finobot.R;
import com.nineoldandroids.animation.Keyframe;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Utils {

    public static final int PULSE_ANIMATOR_DURATION = 544;

	public static int getDaysInMonth(int month, int year) {
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            case Calendar.FEBRUARY:
                return (year % 4 == 0) ? 29 : 28;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
	}

	public static ObjectAnimator getPulseAnimator(View labelToAnimate, float decreaseRatio, float increaseRatio) {
        Keyframe k0 = Keyframe.ofFloat(0f, 1f);
        Keyframe k1 = Keyframe.ofFloat(0.275f, decreaseRatio);
        Keyframe k2 = Keyframe.ofFloat(0.69f, increaseRatio);
        Keyframe k3 = Keyframe.ofFloat(1f, 1f);

        PropertyValuesHolder scaleX = PropertyValuesHolder.ofKeyframe("scaleX", k0, k1, k2, k3);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofKeyframe("scaleY", k0, k1, k2, k3);
        ObjectAnimator pulseAnimator = ObjectAnimator.ofPropertyValuesHolder(labelToAnimate, scaleX, scaleY);
        pulseAnimator.setDuration(PULSE_ANIMATOR_DURATION);

        return pulseAnimator;
    }

	public static boolean isJellybeanOrLater() {
		return Build.VERSION.SDK_INT >= 16;
	}

    /**
     * Try to speak the specified text, for accessibility. Only available on JB or later.
     * @param text Text to announce.
     */
    @SuppressLint("NewApi")
    public static void tryAccessibilityAnnounce(View view, CharSequence text) {
        if (isJellybeanOrLater() && view != null && text != null) {
            view.announceForAccessibility(text);
        }
    }

    public static boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager) {
        if (Build.VERSION.SDK_INT >= 14) {
            return accessibilityManager.isTouchExplorationEnabled();
        } else {
            return false;
        }
    }


    public static ArrayList<String> setMyCalandarMonth(){
        String[] monthDataStr = new String[]{"JANUARY","FEBRUARY","MARCH","APRIL","MAY",
                "JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
        ArrayList<String> monthData = new ArrayList<String>(Arrays.asList(monthDataStr));
        return monthData;
    }

    public static ArrayList<Integer> setMyCalandarDays(int  month, int year){
//        Integer[] daysDataStr = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23
//                ,24,25,26,27,28,29,30,31};
        Integer[] daysDataStr;
        ArrayList<Integer> daysData;
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                daysDataStr = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23
                ,24,25,26,27,28,29,30,31};
                daysData = new ArrayList<Integer>(Arrays.asList(daysDataStr));
                return daysData;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                daysDataStr = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23
                        ,24,25,26,27,28,29,30};
                 daysData = new ArrayList<Integer>(Arrays.asList(daysDataStr));
                return daysData;
            case Calendar.FEBRUARY:
                int y =(year % 4 == 0) ? 29 : 28;
                if(y==29){
                    daysDataStr = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23
                            ,24,25,26,27,28,29};
                    daysData = new ArrayList<Integer>(Arrays.asList(daysDataStr));
                    return daysData;
                }else{
                    daysDataStr = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23
                            ,24,25,26,27,28};
                    daysData = new ArrayList<Integer>(Arrays.asList(daysDataStr));
                    return daysData;
                }
            default:
                throw new IllegalArgumentException("Invalid Month");
        }

    }

    public static ArrayList<String> setMyCalandarYear(Activity activity){
        String[] yearData = activity.getResources().getStringArray(R.array.age);
        ArrayList<String> daysData = new ArrayList<String>(Arrays.asList(yearData));
        return daysData;
    }

    public static ArrayList<Integer> setMyCalandarIntegerMonth(){
        Integer[] daysDataStr = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12};
        ArrayList<Integer> daysData = new ArrayList<Integer>(Arrays.asList(daysDataStr));
        return daysData;
    }

    public static ArrayList<Integer> setMyCalandarSelectIntegerMonth(){
        Integer[] daysDataStr = new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11};
        ArrayList<Integer> daysData = new ArrayList<Integer>(Arrays.asList(daysDataStr));
        return daysData;
    }

    public static int getYearDataAddFromCurrentAge(int age){
        Calendar  calendar = new GregorianCalendar();
        int currYear =calendar.get(Calendar.YEAR)+age;
        return currYear;
    }

    public static int getYearDataSubFromCurrentAge(int age){
        Calendar  calendar = new GregorianCalendar();
        int currentYear = calendar.get(Calendar.YEAR)-age;
        return currentYear;
    }

    public static int getAgeDataSubFromCurrentYear(int year){
        Calendar  calendar = new GregorianCalendar();
        int age = calendar.get(Calendar.YEAR)-year;
//        Log.i("hjagsdfhjs","cureent year" + calendar.get(Calendar.YEAR));
//        Log.i("hjagsdfhjs"," year" + year);
//        Log.i("hjagsdfhjs"," year" + age);
        if(age<=0){
         age=0;
        }
        return age;
    }
    public static int getAgeDataSubFromCurrentYearisGreater(int year){
        Calendar  calendar = new GregorianCalendar();
        int age = year-calendar.get(Calendar.YEAR);
        if(age<=0){
            age=0;
        }
        return age;
    }

    public static int getAgeFromCurrentYear(int year){
        Calendar  calendar = new GregorianCalendar();
        int j=0,k=0, age=0;
//        for(int i=0; i<199 ; i++ ){
//            int yearincr =  calendar.get(Calendar.YEAR);
//            if(year == yearincr){
//                break;
//            }
//            j++;
//        }
//
//        for(int n=0; n<199 ; n++ ){
//            int yearincr =  year;
//            if(year == yearincr){
//                break;
//            }
//            k++;
//        }
        int currentYear =  calendar.get(Calendar.YEAR);
        age = currentYear-year;
        if(age<=0){
            age=0;
        }
        return age;
    }

    public static int getAllyearFromCalendar(int myYear){
        int j=0;
        Calendar  calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, 1902);
        for(int i=0; i<199 ; i++ ){
          int year =  calendar.get(Calendar.YEAR)+i;
            if(year == myYear){
                break;
            }
            j++;
        }
        return j;
    }


    public static int getMonthValue(String month){
        switch (month) {
            case "JANUARY":
                return 0;
            case "FEBRUARY":
                return 1;
            case "MARCH":
                return 2;
            case "APRIL":
                return 3;
            case "MAY":
                return 4;
            case "JUNE":
                return 5;
            case "JULY":
                return 6;
            case "AUGUST":
                return 7;
            case "SEPTEMBER":
                return 8;
            case "OCTOBER":
                return 9;
            case "NOVEMBER":
                return 10;
            case "DECEMBER":
                return 11;
            default:
                return 0;
        }
    }


    public static int getpostion(String data){
        switch (data) {
            case "a":
                return 0;
            case "b":
                return 1;
            case "c":
                return 2;
            case "d":
                return 3;
            default:
                return 0;
        }
    }


}

package com.purplepath.purplepath.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;

import static com.finobot.finobot.activity.HomePageActivity.isClicked;

/**
 * Created by dinesh on 12/02/17.
 */
public class BaseFragment extends Fragment {
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }
    static void run1()
    {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     *  Starting  Home Page Activty
     *
     */
    public void startHomeActivity() {
        Intent i = new Intent(getActivity(), HomePageActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
    public void startSettingHomeActivity() {
        Intent i = new Intent(getActivity(), HomePageActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    /**
     * Start fragment in R.id.fragment_container
     *
     * @param mfagment
     */
    public void addFragmenttoStack(Fragment mfagment) {
        try {
            android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
            if (!(currentFragment.getClass().equals(mfagment.getClass())))
            if (!mfagment.isVisible()) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, mfagment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
//                HomePageActivity.collapse(mDropdownLayout);

            }
            if((getActivity() instanceof  HomePageActivity))
            {
                if(!isClicked)
                ((HomePageActivity)getActivity()).closeDropDownTab();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Start fragment in R.id.fragment_container
     *
     * @param mfagment
     *
     * addToBackStack is romove
     */

    public void removeFragmenttoStack(Fragment mfagment) {
        try {
            if (!mfagment.isVisible()) {
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, mfagment);
                fragmentTransaction.commitAllowingStateLoss();
//                HomePageActivity.collapse(mDropdownLayout);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Get Device width
     * @return
     */
    public int getDeviceWidth(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return width;
    }



}

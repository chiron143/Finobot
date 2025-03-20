package com.purplepath.purplepath.fragments;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finobot.finobot.R;

/**
 * Created by dinesh on 10/05/16.
 */

public class SplashScreenFragment extends BaseFragment {
    private int SPLASH_TIME_OUT = 3000;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {

			/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                try {

                    addFragmenttoStack(new EmailLoginFragment());

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View emailLoginView = inflater.inflate(R.layout.splash_screen,
                container, false);

        return emailLoginView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

    }

}

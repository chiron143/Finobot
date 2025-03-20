package com.finobot.finobot;

import android.app.Application;
import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.analytics.FirebaseAnalytics;

//import com.finobot.finobot.activity.ActivityLifeCycle;

/**
 * Created by Naso-08 on 5/19/2015.
 */
public class MyApplication extends Application {

    private static final String TAG = Application.class.getSimpleName();
    private static Application mInstance = null;
    private static Context mContext;

    boolean isInBackground = true;
    public static FirebaseAnalytics mFirebaseAnalytics;
    public static Application getInstance() {

        return mInstance;
    }

    @Override
    public void onCreate() {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        mInstance = this;
        MyApplication.mContext = getApplicationContext();
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

      // registerActivityLifecycleCallbacks(new ActivityLifeCycle());

    }


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {

        mInstance = null;
        super.onTerminate();
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        MyApplication.mContext = mContext;
    }


}

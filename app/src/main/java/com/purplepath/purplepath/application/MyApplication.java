//package com.purplepath.purplepath.application;
//
//import android.app.Application;
//import android.content.Context;
//import android.support.multidex.MultiDex;
//import android.support.v7.app.AppCompatDelegate;
//
///**
// * Created by Naso-08 on 5/19/2015.
// */
//public class MyApplication extends android.app.Application {
//
//    private static final String TAG = Application.class.getSimpleName();
//    private static Application mInstance = null;
//    private static Context mContext;
//
//    public static Application getInstance() {
//
//        return mInstance;
//    }
//
//    @Override
//    public void onCreate() {
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        mInstance = this;
//        MyApplication.mContext = getApplicationContext();
//        super.onCreate();
//
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//
//    }
//
//
//    @Override
//    protected void attachBaseContext(Context context) {
//        super.attachBaseContext(context);
//        MultiDex.install(this);
//    }
//
//    @Override
//    public void onTerminate() {
//
//        mInstance = null;
//        super.onTerminate();
//    }
//
//    public static Context getContext() {
//        return mContext;
//    }
//
//    public static void setContext(Context mContext) {
//        MyApplication.mContext = mContext;
//    }
//
//
//
//}

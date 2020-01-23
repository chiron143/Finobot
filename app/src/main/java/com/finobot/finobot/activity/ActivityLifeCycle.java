/*
package com.finobot.finobot.activity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;
import com.finobot.finobot.MyApplication;


*/
/**
 * Created by pravinr on 1/2/18.
 *//*



public class ActivityLifeCycle implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    Activity lastActivity;

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity != null && activity == lastActivity)
        {
            Toast.makeText(MyApplication.getContext(), "app visible!", Toast.LENGTH_LONG).show();
        }

        lastActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (activity != null && activity == lastActivity)
        {
            Toast.makeText(MyApplication.getContext(), "app invisible!", Toast.LENGTH_LONG).show();
        }

        lastActivity = activity;

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}*/

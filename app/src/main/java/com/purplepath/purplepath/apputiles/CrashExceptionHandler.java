package com.purplepath.purplepath.apputiles;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.LoginandSignUpActivity;
import com.google.firebase.crash.FirebaseCrash;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by dinesh on 12/10/17.
 */

public class CrashExceptionHandler implements
        java.lang.Thread.UncaughtExceptionHandler {
    private  Activity mActivity;
    private final Class<?> myActivityClass;

    public CrashExceptionHandler(Activity mActivity, Class<?> c) {

        mActivity = mActivity;
        myActivityClass = c;
    }

    public void uncaughtException(Thread thread, Throwable exception) {

        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        System.err.println(stackTrace);// You can use LogCat too
//        Intent intent = new Intent(myContext, myActivityClass);
//        String s = stackTrace.toString();
//        //you can use this String to know what caused the exception and in which Activity
//        intent.putExtra("uncaughtException",
//                "Exception is: " + stackTrace.toString());
//        intent.putExtra("stacktrace", s);
//        myContext.startActivity(intent);
        //for restarting the Activity
//        Process.killProcess(Process.myPid());
//        System.exit(0);
        FirebaseCrash.report(new Exception(stackTrace.toString()));
        Intent intent = new Intent(mActivity, LoginandSignUpActivity.class);
        intent.putExtra("crash", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getInstance().getBaseContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) MyApplication.getInstance().getBaseContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        mActivity.finish();
        System.exit(2);

    }

    public void intitializeAlertDialogQuiz(String Title, String string, Context mContext) {
        LayoutInflater inflater;
        View dialogView;
        final AlertDialog alertDialogs;
        try {
            inflater = LayoutInflater.from(mContext);
            dialogView = inflater.inflate(R.layout.alert_message_layout, null);
            alertDialogs = new AlertDialog.Builder(mContext).create();
            alertDialogs.setView(dialogView);
            TextView erroreMessage = dialogView.findViewById(R.id.textViewDilog);
            TextView title = dialogView.findViewById(R.id.textViewAlert);
//            Log.i(TAG, "intitializeAlertDialog " + string);
            title.setText(Title);
            erroreMessage.setText(string);
            dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ActivityCompat.finishAffinity(mActivity);
                    } else
                        mActivity.finish();

                }
            });
//            dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    alertDialog.dismiss();
//                }
//            });
            alertDialogs.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

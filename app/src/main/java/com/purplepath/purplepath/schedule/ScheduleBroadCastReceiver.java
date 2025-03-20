package com.purplepath.purplepath.schedule;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.gson.Gson;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.schedule.models.Liab;
import com.purplepath.purplepath.schedule.models.ScheduleModel;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by Pratheep
 */

public class ScheduleBroadCastReceiver extends BroadcastReceiver {
    private static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    public static final String ACTION_PAYEMENT_REMAINDER = "com.purplepath.purplepath.ACTION_DUE_REMAINDER";
    private static String TAG = "spcheck";
    String scheduleModelAsString;
    ScheduleModel scheduleModel;
    Gson gson = new Gson();
    Liab liab;
    String dateLiab, dateIns;
    String dateLiabOnSplit[];
    DateTimeFormatter formatter;
    LocalDate date1, date2;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION_BOOT_COMPLETED)) {
            scheduleThroughAlarmManager(context);
        } else {
            Log.i(TAG, "scheduleThroughAlarmManager in broadcast Receiver through intent: ");
            scheduleModelAsString = UtileKit.getPersistedPurplePathPref("scheduleModel");
            if (UtileKit.validateObjectValues(scheduleModelAsString)) {
                scheduleModel = gson.fromJson(scheduleModelAsString, ScheduleModel.class);
                if (null != scheduleModel) {
                    formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                    LocalDate currentDate = formatter.parseLocalDate((new LocalDate()).toString());
                    Log.i(TAG, "onReceive: currentDate " + currentDate);
                    if (null != scheduleModel.getData().getPending()) {

                        if (null != scheduleModel.getData().getPending().getLiab()) {
                            int size = scheduleModel.getData().getPending().getLiab().size();
                            if (size > 0) {
                                for (int i = 0; i < size; i++) {
                                    try {
                                        dateLiab = scheduleModel.getData().getPending().getLiab().get(i).getNext_due_date();
                                        if(!dateLiab.equalsIgnoreCase("0000-00-00")) {
                                            date1 = formatter.parseLocalDate(dateLiab);
                                            {
                                                if (currentDate.isEqual(date1) || currentDate.isAfter(date1)) {
                                                    showNotification(context, scheduleModel.getData().getPending().getLiab().get(i).getLiab_name()
                                                            , scheduleModel.getData().getPending().getLiab().get(i).getId(), dateLiab);
                                                } else if (currentDate.isBefore(date1)) {
                                                    break;
                                                }
                                            }
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        if (null != scheduleModel.getData().getPending().getIns()) {
                            int size = scheduleModel.getData().getPending().getIns().size();
                            if (size > 0) {
                                for (int i = 0; i < size; i++) {
                                    dateIns = scheduleModel.getData().getPending().getIns().get(i).getNext_prem_date();
                                    if(!dateIns.equals("0000-00-00")) {
                                        date2 = formatter.parseLocalDate(dateIns);
                                        if (currentDate.isEqual(date2) || currentDate.isAfter(date2)) {
                                            showNotification(context, scheduleModel.getData().getPending().getIns().get(i).getPolicy_name()
                                                    , "999" + scheduleModel.getData().getPending().getIns().get(i).getId(),
                                                    dateIns);
                                        } else if (currentDate.isBefore(date2)) {
                                            break;
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }

           /* showNotification(context,"test","07");
            showNotification(context,"insurance","999"+"07");*/
        }

    }

    private void showNotification(Context context, String name, String id, String date) {
        Intent notificationIntent = new Intent(context, HomePageActivity.class);
        notificationIntent.putExtra("showSchedule", "showSchedulePage");
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        androidx.core.app.TaskStackBuilder builder = androidx.core.app.TaskStackBuilder.create(context);
        builder.addNextIntentWithParentStack(notificationIntent);
        PendingIntent pendingIntent = builder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notification_builder = new NotificationCompat.Builder(context);

        Notification notification = notification_builder.setContentTitle("Remainder for Payment")
                .setContentText("Payment due on " + changeDateFormat(date) + " for " + name)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon))
                .setSmallIcon(R.drawable.app_icon)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Integer.parseInt(id), notification);
    }

    @SuppressLint("WrongConstant")
    private void scheduleThroughAlarmManager(Context context) {
        Log.i(TAG, "scheduleThroughAlarmManager in broadcast Receiver after boot: ");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        Intent notificationIntent = new Intent(context, ScheduleBroadCastReceiver.class);
        notificationIntent.setAction(ACTION_PAYEMENT_REMAINDER);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 54);
        //calendar.add(Calendar.SECOND, 15);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),broadcast);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,broadcast);
        }else {
            //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),broadcast);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,broadcast);
        }*/
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);
    }

    private String changeDateFormat(String date) {
        String newDate = "";
        try {
            String[] date_split = date.split("-");
            newDate = date_split[2] + "-" + date_split[1] + "-" + date_split[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDate;
    }


}

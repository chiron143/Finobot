package com.purplepath.purplepath.schedule;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.google.gson.Gson;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.calenderNumberPicker.calenderUi.CalenderTabs;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.schedule.Interface.RefreshUpcomingScheduleFragment;
import com.purplepath.purplepath.schedule.Interface.UpdateDetailsFromAdapter;
import com.purplepath.purplepath.schedule.adapter.ScheduleRecyclerAdapter;
import com.purplepath.purplepath.schedule.models.ScheduleModel;
import com.purplepath.purplepath.schedule.models.UpdateScheduleModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.schedule.ScheduleBroadCastReceiver.ACTION_PAYEMENT_REMAINDER;

/**
 * Created by Pratheep
 */

public class Pending_Fragment extends Fragment implements RadioGroup.OnCheckedChangeListener,UpdateDetailsFromAdapter,DatePickerCallBackInterface {

    private  Context mcontext;

    private RecyclerView insurance_recycler_view,liablilty_recycler_view;
    private static ScheduleRecyclerAdapter insuranceAdapter,liabilityAdapter;

    private ScheduleRecyclerAdapter adapter;

    private Bundle args;
    private LinearLayoutManager layoutManager;

    private ScheduleModel scheduleModel;

    private final int TYPE_CODE_INSURANCE=0,TYPE_CODE_LIABILITY=1;
    private static String TAG="spcheck";

    private String id=null,flag=null;
    private static int adapter_position;
    CalenderTabs dialog;

    private RadioGroup category_rg;
    private RadioButton insurance_rb,liability_rb;
    static RefreshUpcomingScheduleFragment scheduleFragment;
    private TextView noItemInsurance,noItemLiability;
    private boolean noItemInsuranceFlag=false,noItemLiabilityFlag=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext = getActivity();
    }

    public static Pending_Fragment newInstance(ScheduleModel scheduleModel,RefreshUpcomingScheduleFragment refreshUpcomingScheduleFragment) {
        Bundle args = new Bundle();
        args.putSerializable("scheduleModel",scheduleModel);
        scheduleFragment=refreshUpcomingScheduleFragment;
        Pending_Fragment fragment = new Pending_Fragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pending_view_fragment, container, false);
       // scheduleThroughAlarmManager();
       /* setNotificationForPendidingLiablity();
        setNotificationForPendidingInsurance();*/
        //setBroadCastReceiver();

        if(!UtileKit.getPersistedPurplePathBoolPref("isNotificationTriggered")) {
            scheduleThroughAlarmManagerNew();
        }

        noItemLiability= view.findViewById(R.id.noItemLiability);
        noItemInsurance= view.findViewById(R.id.noItemInsurance);

        insurance_recycler_view= view.findViewById(R.id.insurance_recycler_view);
        liablilty_recycler_view= view.findViewById(R.id.liablilty_recycler_view);

        category_rg= view.findViewById(R.id.category_rg);
        liability_rb= view.findViewById(R.id.liability_rb);
        insurance_rb= view.findViewById(R.id.insurance_rb);
        category_rg.setOnCheckedChangeListener(this);
        mcontext=getContext();

        layoutManager=new LinearLayoutManager(mcontext);
        args=getArguments();
        if(args!=null){
            if(args.containsKey("scheduleModel")){
                scheduleModel= (ScheduleModel) args.get("scheduleModel");
                showContents();
            } else {
                UtileKit.intitializeAlertDialog("No Data to show",mcontext);
            }
        }

        return  view;
    }

    private void setNotificationForPendidingLiablity() {
    }

    private void setNotificationForPendidingInsurance(){

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        switch (group.getId()){
            case R.id.category_rg:
                if(checkedId==R.id.liability_rb){
                    if (noItemLiabilityFlag) {
                        noItemLiability.setVisibility(View.VISIBLE);
                        liablilty_recycler_view.setVisibility(View.GONE);
                        noItemInsurance.setVisibility(View.GONE);
                        insurance_recycler_view.setVisibility(View.GONE);
                    }else{
                        noItemLiability.setVisibility(View.GONE);
                        liablilty_recycler_view.setVisibility(View.VISIBLE);
                        noItemInsurance.setVisibility(View.GONE);
                        insurance_recycler_view.setVisibility(View.GONE);
                    }


                }else if(checkedId==R.id.insurance_rb){
                    if(noItemInsuranceFlag) {
                        noItemInsurance.setVisibility(View.VISIBLE);
                        insurance_recycler_view.setVisibility(View.GONE);
                        liablilty_recycler_view.setVisibility(View.GONE);
                        noItemLiability.setVisibility(View.GONE);
                    }else {
                        insurance_recycler_view.setVisibility(View.VISIBLE);
                        liablilty_recycler_view.setVisibility(View.GONE);
                        noItemInsurance.setVisibility(View.GONE);
                        noItemLiability.setVisibility(View.GONE);
                    }

                }
                break;
        }

    }

    @Override
    public void updateEditTextValue(String value, String title) {
        dialog.dismiss();
        if(UtileKit.validateObjectValues(id)&&UtileKit.validateObjectValues(flag)&&UtileKit.validateObjectValues(value)) {
            //Log.i(TAG, "after format change "+changeDateFormat(value));
            update_schedule_details(id, flag,changeDateFormat(value));
            //  Log.i(TAG, "id,  "+id+" flag : "+flag+" value :"+value);
        }

    }

    @Override
    public void updateIndividualEditTextValue(String value, String title) {

    }

    private String changeDateFormat(String value) {
        String[] date=value.split("-");
        return (date[2]+"-"+date[1]+"-"+date[0]);

    }

    @Override
    public void update_details(String id, String flag, int position) {
        this.id=id;
        this.flag=flag;
        adapter_position=position;
        dialog= CalenderTabs.newInstance(this,"Enter paid date",false,false,false,"1","");
        dialog.show(getChildFragmentManager(),"date dialog");

    }

    public  void update_schedule_details(String id, final String flag, String paid_date){
        UtileKit.showSpinnerDialog(getContext(),false);
        WebServiceCalls obj= ServiceGenerator.createService(WebServiceCalls.class);
        Call<UpdateScheduleModel> call=obj.call_update_schedule(id,flag,paid_date);
        call.enqueue(new Callback<UpdateScheduleModel>() {
            @Override
            public void onResponse(Call<UpdateScheduleModel> call, Response<UpdateScheduleModel> response) {
                UtileKit.dismisssSpinnerDialog();
                UpdateScheduleModel updateScheduleModel=response.body();
                if(updateScheduleModel.getStatus_code().equals(UtileKit.SUCCESSCODE)){
                    Toast.makeText(mcontext,"Updated Successfully",Toast.LENGTH_SHORT).show();
                    refreshRecyclerView(flag);
                    callGetSchedulesByUserService();
                    showContents();
                }
            }

            @Override
            public void onFailure(Call<UpdateScheduleModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void callGetSchedulesByUserService() {
        WebServiceCalls obj= ServiceGenerator.createService(WebServiceCalls.class);
        Call call=obj.call_schedules_by_user(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                UtileKit.dismisssSpinnerDialog();
                scheduleModel= (ScheduleModel) response.body();
                Log.i(TAG, "update in other tab(upcomming) ws succes ");
                Gson gson=new Gson();
                String str=gson.toJson(scheduleModel);
                UtileKit.persistingPurplePathPref("scheduleModel",str);
                scheduleFragment.updateUpcomingScheduleFragmentValues(scheduleModel);

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();

            }
        });
    }


    private static void refreshRecyclerView(String flag) {
        if(flag.equals("ins")){
            Log.i(TAG, "onClick:position refresh ins "+adapter_position);
            insuranceAdapter.scheduleModel.getData().getPending().getIns().remove(adapter_position);
            //insuranceAdapter.notifyItemRemoved(adapter_position);
            insuranceAdapter.notifyDataSetChanged();
        }else if(flag.equals("liab")){
            //liabilityAdapter.notifyDataSetChanged();
            Log.i(TAG, "onClick:position refresh liab "+adapter_position);
            liabilityAdapter.scheduleModel.getData().getPending().getLiab().remove(adapter_position);
            //liabilityAdapter.notifyItemRemoved(adapter_position);
            liabilityAdapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("WrongConstant")
    private void scheduleThroughAlarmManagerNew() {
        Log.i(TAG, "scheduleThroughAlarmManager in pending fragment: ");
        AlarmManager alarmManager= (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);
        java.util.Calendar calendar= java.util.Calendar.getInstance();
        Intent notificationIntent = new Intent(mcontext,ScheduleBroadCastReceiver.class);
        notificationIntent.setAction(ACTION_PAYEMENT_REMAINDER);
        PendingIntent broadcast = PendingIntent.getBroadcast(mcontext, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 54);
        //calendar.add(Calendar.SECOND, 15);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),broadcast);
        }else {
            //alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),broadcast);
        }*/
       // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,broadcast);
        //1000 * 60 * 60*24
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,broadcast);
         UtileKit.persistingPurplePathPref("isNotificationTriggered",true);
    }

    @SuppressLint("WrongConstant")
    private void scheduleThroughAlarmManager() {
        Log.i(TAG, "scheduleThroughAlarmManager in pending fragment: ");
        AlarmManager alarmManager= (AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);
        //java.util.Calendar calendar= java.util.Calendar.getInstance();
        Intent notificationIntent = new Intent(mcontext,ScheduleBroadCastReceiver.class);
        notificationIntent.setAction(ACTION_PAYEMENT_REMAINDER);
        java.util.Calendar cal= java.util.Calendar.getInstance();
        if(null !=scheduleModel.getData().getPending().getLiab().get(0)) {
            String date = scheduleModel.getData().getPending().getLiab().get(0).getNext_due_date();
            if(UtileKit.validateObjectValues(date)){
                String[] date_split=date.split("-"); //date from service is of the form YYYY-MM-DD

                cal.set(Calendar.DATE, Integer.parseInt(date_split[2]));  //1-31
                cal.set(Calendar.MONTH, (Integer.parseInt(date_split[1])-1));  //first month is 0!!! January is zero!!!
                cal.set(Calendar.YEAR,Integer.parseInt(date_split[0]));
                cal.set(Calendar.HOUR_OF_DAY, 10);  //HOUR
                cal.set(Calendar.MINUTE, 00);       //MIN
                cal.set(Calendar.SECOND, 00);

            }
        }

        PendingIntent broadcast = PendingIntent.getBroadcast(mcontext, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //calendar.add(Calendar.SECOND, 15);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),broadcast);
        }else {
            alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),broadcast);
        }
    }
    private  void setBroadCastReceiver() {

        AlarmManager alarmManager= (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        java.util.Calendar calendar= java.util.Calendar.getInstance();
        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(getContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        calendar.add(Calendar.SECOND, 15);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),broadcast);
        }else {
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),broadcast);
        }


    }

    void showContents(){
        if(null!=scheduleModel.getData().getPending()) {
            if(null!=scheduleModel.getData().getPending().getIns()&&
                    scheduleModel.getData().getPending().getIns().size()>0){
                if (category_rg.getCheckedRadioButtonId() == R.id.insurance_rb) {
                    noItemInsurance.setVisibility(View.GONE);
                    insurance_recycler_view.setVisibility(View.VISIBLE);
                    noItemLiability.setVisibility(View.GONE);
                    liablilty_recycler_view.setVisibility(View.GONE);
                }
                noItemInsuranceFlag=false;
                insurance_recycler_view.setLayoutManager(layoutManager);
                insuranceAdapter = new ScheduleRecyclerAdapter(this, scheduleModel, mcontext, TYPE_CODE_INSURANCE);
                insurance_recycler_view.setAdapter(insuranceAdapter);
                //insurance_recycler_view.setNestedScrollingEnabled(true);
            }else {
                noItemInsuranceFlag = true;
                if (category_rg.getCheckedRadioButtonId() == R.id.insurance_rb) {
                    noItemInsurance.setVisibility(View.VISIBLE);
                    insurance_recycler_view.setVisibility(View.GONE);
                    noItemLiability.setVisibility(View.GONE);
                    liablilty_recycler_view.setVisibility(View.GONE);

                }

            }
            if(null!=scheduleModel.getData().getPending().getLiab() &&
                    scheduleModel.getData().getPending().getLiab().size()>0) {
                liabilityAdapter = new ScheduleRecyclerAdapter(this, scheduleModel, mcontext, TYPE_CODE_LIABILITY);
                liablilty_recycler_view.setLayoutManager(new LinearLayoutManager(mcontext));
                liablilty_recycler_view.setAdapter(liabilityAdapter);
                // liablilty_recycler_view.setNestedScrollingEnabled(true);
                noItemLiabilityFlag=false;
                if (category_rg.getCheckedRadioButtonId() == R.id.liability_rb) {
                    noItemLiability.setVisibility(View.GONE);
                    liablilty_recycler_view.setVisibility(View.VISIBLE);
                    noItemInsurance.setVisibility(View.GONE);
                    insurance_recycler_view.setVisibility(View.GONE);
                }
            }else {
                noItemLiabilityFlag=true;
                if (category_rg.getCheckedRadioButtonId() == R.id.liability_rb) {
                    noItemLiability.setVisibility(View.VISIBLE);
                    liablilty_recycler_view.setVisibility(View.GONE);
                    noItemInsurance.setVisibility(View.GONE);
                    insurance_recycler_view.setVisibility(View.GONE);
                }
            }
        }else {
            UtileKit.intitializeAlertDialog("No Data to show",mcontext);
        }
    }


}

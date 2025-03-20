package com.purplepath.purplepath.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.schedule.Interface.RefreshUpcomingScheduleFragment;
import com.purplepath.purplepath.schedule.adapter.ScheduleViewPagerAdapter;
import com.purplepath.purplepath.schedule.models.ScheduleModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScheduleFragment extends Fragment implements RefreshUpcomingScheduleFragment,View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private LinearLayout parentViewId;

    private String[] tabTitles = new String[]{" Pending ", "Upcoming"};

    private TabLayout mTabLayout;
    private ViewPager viewPager;

    private ScheduleViewPagerAdapter pagerAdapter;
    private ScheduleModel scheduleModel;
    private Context mContext;
    private ArrayList<Fragment> fragments=new ArrayList<Fragment>();
    private TextView noItmeText;
    private FloatingActionButton fab_myschedule;
    private RelativeLayout layout_tab;
    UpcomingSchedule upcomingSchedule;


    public ScheduleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_schedule, container, false);
        backPressedListener.setActionBarTitle("PayTracker");

        mContext=getContext();
        layout_tab= view.findViewById(R.id.layout_tab);
        mTabLayout = view.findViewById(R.id.tab_layout_id);
        viewPager = view.findViewById(R.id.viewpager);
        noItmeText= view.findViewById(R.id.noItmeText);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        parentViewId = view.findViewById(R.id.parentViewId);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);


        fab_myschedule = view.findViewById(R.id.fab_myschedule);

        callGetSchedulesByUserService();

        return view;
    }

    private void callGetSchedulesByUserService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls obj= ServiceGenerator.createService(WebServiceCalls.class);
        Call call=obj.call_schedules_by_user(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                UtileKit.dismisssSpinnerDialog();
                scheduleModel= (ScheduleModel) response.body();
                if(scheduleModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    try {
                        fragments.add(Pending_Fragment.newInstance(scheduleModel,ScheduleFragment.this));
                        upcomingSchedule=UpcomingSchedule.newInstance(scheduleModel);
                        fragments.add(upcomingSchedule);
                        pagerAdapter = new ScheduleViewPagerAdapter(getChildFragmentManager(), fragments, tabTitles);
                        viewPager.setAdapter(pagerAdapter);
                        mTabLayout.setupWithViewPager(viewPager);
                        Gson gson = new Gson();
                        String str = gson.toJson(scheduleModel);
                        UtileKit.persistingPurplePathPref("scheduleModel", str);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    UtileKit.intitializeAlertDialog("No Data to show",mContext);
                    noItmeText.setVisibility(View.VISIBLE);
                    noItmeText.setText("You do not have any items in Paytracker");
                    layout_tab.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.intitializeAlertDialog("Network Error",mContext);

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }

    }

    @Override
    public void updateUpcomingScheduleFragmentValues(ScheduleModel scheduleModel) {
        upcomingSchedule.setValuesInAdapter(scheduleModel);
    }
}

package com.finobot.finobot.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.purplepath.purplepath.Notification.adapters.NotificationViewPagerAdapter;
import com.purplepath.purplepath.Notification.fragments.AlertsFragment;
import com.purplepath.purplepath.Notification.fragments.AnnouncementFragment;
import com.purplepath.purplepath.Notification.fragments.NotificationFragment;
import com.purplepath.purplepath.Notification.fragments.PromptsFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;

/**
 * Created by Pratheep.S on 17-01-2017.
 */

public class NotificationActivity  extends BaseFragment implements View.OnClickListener{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private NotificationViewPagerAdapter notificationAdapter;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
   private String titles[]={"Alert","Prompt","Notification","Announcement"};
  //  private String titles[]={"Notification","Announcements","Prompts","Alerts"};
    private ArrayList<Fragment> fragments;
    private OnActivityBackPressedListener mCallBackListener;
    private Context mContext;
    private Bundle args;
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public static NotificationActivity newInstance(int launchFragmentCode) {

        Bundle args = new Bundle();
        args.putInt("launchFragmentCode",launchFragmentCode);
        NotificationActivity fragment = new NotificationActivity();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        try {
           // setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener)mContext ;

        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return    inflater.inflate(R.layout.activity_notifications,
                container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view. findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mCallBackListener.setActionBarTitle(getString(R.string.Notifications));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener()
//        { @Override public void onClick(View v) { onBackPressed(); } });

        tabLayout= view.findViewById(R.id.tab_layout_Notifications);
        viewPager= view.findViewById(R.id.viewPager_notifications);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        fragments=new ArrayList<>();
        fragments.add(new AlertsFragment());
        //fragments.add(new RiskAssesmentResultFragment());
        fragments.add(new PromptsFragment());
        fragments.add(new NotificationFragment());
        fragments.add(new AnnouncementFragment());
        notificationAdapter=new NotificationViewPagerAdapter(getChildFragmentManager(),titles,fragments);
        viewPager.setAdapter(notificationAdapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
        try{
            args=getArguments();
        }catch (Exception e){e.printStackTrace();}
        if(args!=null){
            if(args.containsKey("launchFragmentCode")){
                int code=args.getInt("launchFragmentCode");
                viewPager.setCurrentItem(code);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relative_left_arrow: {
              mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home: {
                startHomeActivity();
//                Intent i = new Intent(mContext, HomePageActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);
//                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow: {

            }
            break;

        }
    }
}

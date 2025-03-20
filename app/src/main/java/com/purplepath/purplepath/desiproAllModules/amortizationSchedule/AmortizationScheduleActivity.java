package com.purplepath.purplepath.desiproAllModules.amortizationSchedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.CrashExceptionHandler;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

public class AmortizationScheduleActivity extends BaseFragment implements OnActivityBackPressedListener, View.OnClickListener {

    private Toolbar toolbar;
    private TextView toolbarTextview;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private Context mContext;
    LayoutInflater inflater;
    View dialogView,dialogViewLogout;
    AlertDialog alertDialog,alertDialogLogout;
    private OnActivityBackPressedListener mCallBackListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setHasOptionsMenu(true);
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_amortization_schedule, container, false);
                Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(getActivity(),
                AmortizationScheduleActivity.class));
        mCallBackListener.setActionBarTitle("DeciPro - Amortization Schedule");
//        ActionBar actionBar=getSupportActionBar();
//       // actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setTitle("");
        if(savedInstanceState==null)
        showAmortizationMainFragment();


        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view. findViewById(R.id.relative_right_arrow);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
//        intitializeAlertDialog();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_amortization_schedule);
//

//    }





    private void showAmortizationMainFragment() {
        Fragment fragment=new AmortizationMainFragment();
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
        ft.commitAllowingStateLoss();
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onActivityBackPressed() {
        try {
//            onBackPressed();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setActionBarTitle(String mTitle) {

    }

    @Override
    public void setActionBarExpTitle(String mTitle) {

    }

    @Override
    public void closeDropDownTab() {

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
                int count=getFragmentManager().getBackStackEntryCount();
                if(count==0){
//                    mContext.finish();
                }else if(count>0){
                    Log.i("spcheck", " relative_left_arrow is clicked"  );
                    mCallBackListener.onActivityBackPressed();
                }

                break;
            case R.id.relative_center_home:
                Log.i("spcheck", " relative_center_home is clicked"  );
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                startActivity(i);
                break;

        }
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
//                && keyCode == KeyEvent.KEYCODE_BACK
//                && event.getRepeatCount() == 0) {
////            if (event.getDownTime() - lastPressedTime < PERIOD) {
////                alertDialog.show();
////                //finish();
////                return true;
////            } else {
////                Toast.makeText(getApplicationContext(), "Press again to exit.",
////                        Toast.LENGTH_SHORT).show();
////                lastPressedTime = event.getEventTime();
////                Snackbar.make(coordinatorLayout, "press one moret time to Exit", Snackbar.LENGTH_SHORT);
////                return false;
////            }
//
//            alertDialog.show();
//
//        }
//        return super.onKeyDown(keyCode, event);
//    }

//    private void intitializeAlertDialog() {
//        inflater= LayoutInflater.from(mContext);
//        dialogView=inflater.inflate(R.layout.yes_or_no_system_back,null);
//        alertDialog=new AlertDialog.Builder(mContext).create();
//        alertDialog.setView(dialogView);
//        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                onBackPressed();
////                finish();
//                Intent loginActivity = new Intent(v.getContext(), LoginandSignUpActivity.class);
//                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(loginActivity);
//                finish();
//            }
//        });
//        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        dialogViewLogout=inflater.inflate(R.layout.yes_no_dialog,null);
//        alertDialogLogout=new AlertDialog.Builder(mContext).create();
//        alertDialogLogout.setView(dialogViewLogout);
//        dialogViewLogout.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // isGroupClicked = true;
//                HomePageActivity.getExpensesDetailsModel = null;
//                HomePageActivity.expensesDetailsSize = 0;
//                alertDialogLogout.dismiss();
//
//                Intent loginActivity = new Intent(v.getContext(), LoginandSignUpActivity.class);
//                loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(loginActivity);
//                finish();
//            }
//        });
//        dialogViewLogout.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                alertDialogLogout.dismiss();
//            }
//        });
//
//
//    }
}

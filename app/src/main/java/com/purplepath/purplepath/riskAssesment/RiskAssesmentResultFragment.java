package com.purplepath.purplepath.riskAssesment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.adapter.HomeViewPagerAdapter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.model.riskmodel.RiskDailyUpdateModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pratheep.S on 25-01-2017.
 */

public class RiskAssesmentResultFragment extends BaseFragment implements View.OnClickListener {

    private RiskDailyUpdateModel mRiskScoreForUserModel;
    private String mRiskCapacity, mRiskAppettite;
    private ImageView mVeryConservativeCapability, mVeryConservativeAppetite, mConservativeCapability, mConservativeAppetite,
            mBalancedAppetite, mBalancedCapability, mAggresiveAppetiite, mAggresiveCapability, mVeryAggresiveCapability, mVeryAggresiveAppetiite;

    //-------------------------------------------------------------------------
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments=new ArrayList<Fragment>();
    private HomeViewPagerAdapter mHomeViewPagerAdapter;
    private PageIndicator mIndicator = null;
    private DisplayMetrics displayMetrics;
    private Context mContext;
    private OnActivityBackPressedListener mCallBackListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_risk_profile, container, false);
        mCallBackListener.setActionBarTitle("My Risk Tolerance");
        mVeryAggresiveAppetiite = view.findViewById(R.id.iv_very_aggresive_appetite);
        mVeryAggresiveCapability = view.findViewById(R.id.iv_very_aggresive);

        mAggresiveAppetiite = view.findViewById(R.id.iv_Aggresive_appetite);
        mAggresiveCapability = view.findViewById(R.id.iv_aggresive);

        mConservativeCapability = view.findViewById(R.id.iv_conservative);
        mConservativeAppetite = view.findViewById(R.id.iv_very_conservative_appetite);

        mVeryConservativeAppetite = view.findViewById(R.id.iv_very_conservative_appetite);
        mVeryConservativeCapability = view.findViewById(R.id.iv_very_conservative);

        mBalancedAppetite = view.findViewById(R.id.iv_balanced_appetite);
        mBalancedCapability = view.findViewById(R.id.iv_balanced);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        callRiskScoreWebService();

        return view;
    }

    private void callRiskScoreWebService() {
        UtileKit.showSpinnerDialog(mContext,false);
        String mVersionName = null;
        try {
            mVersionName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            //VersionCode = Integer.toString(mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Call<RiskDailyUpdateModel> call = (ServiceGenerator.createService(WebServiceCalls.class)).getRiskSoreByUser(UtileKit.getPersistedPurplePathPref("user_id"),mVersionName);
        call.enqueue(new Callback<RiskDailyUpdateModel>() {
            @Override
            public void onResponse(Call<RiskDailyUpdateModel> call, Response<RiskDailyUpdateModel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    mRiskScoreForUserModel = response.body();
                    mRiskCapacity = mRiskScoreForUserModel.getData().getRisk_capacity().getResult();
                    mRiskAppettite = mRiskScoreForUserModel.getData().getRisk_appetite().getResult();
                    changeButtonImage(mRiskCapacity, true);
                    changeButtonImage(mRiskAppettite, false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RiskDailyUpdateModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
                UtileKit.dismisssSpinnerDialog();
//                UtileKit.showToastShort(mContext,"Please check  Internet connectivity "+mContext.getString(R.string.check_internet_settings));
            }
        });
    }

    private void changeButtonImage(String value, boolean flag) {
        switch (Integer.parseInt(value)) {
            case 1:
                if (flag) {
                    mVeryConservativeCapability.setImageResource(R.drawable.ic_circle_right_big);
                } else {
                    mVeryConservativeAppetite.setImageResource(R.drawable.ic_circle_right_big);
                }
                break;
            case 2:
                if (flag) {
                    mConservativeCapability.setImageResource(R.drawable.ic_circle_right_big);
                } else {
                    mConservativeAppetite.setImageResource(R.drawable.ic_circle_right_big);
                }

                break;
            case 3:
                if (flag) {
                    mBalancedCapability.setImageResource(R.drawable.ic_circle_right_big);
                } else {
                    mBalancedAppetite.setImageResource(R.drawable.ic_circle_right_big);

                }
                break;
            case 4:
                if (flag) {
                    mAggresiveCapability.setImageResource(R.drawable.ic_circle_right_big);
                } else {
                    mAggresiveAppetiite.setImageResource(R.drawable.ic_circle_right_big);
                }
                break;
            case 5:
                if (flag) {
                    mVeryConservativeCapability.setImageResource(R.drawable.ic_circle_right_big);
                } else {
                    mVeryAggresiveAppetiite.setImageResource(R.drawable.ic_circle_right_big);
                }
                break;

        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relative_left_arrow:
            {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
//                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow:
            {
//                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                // ExpensesDetailsFragment fragment = new ExpensesDetailsFragment();
//                //ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
//                ExpensesAnalysisFragment fragment = new ExpensesAnalysisFragment();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();

            }
            break;

        }
    }



}

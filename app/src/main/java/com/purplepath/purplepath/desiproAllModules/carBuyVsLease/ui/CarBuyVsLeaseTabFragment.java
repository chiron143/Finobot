package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.interfaces.UpdateValueInFragmentInterface;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models.CarVsLeaseModel;
import com.purplepath.purplepath.desiproAllModules.loanComparison.adapter.DeciproAdapter;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dinesh on 15/09/17.
 */

public class CarBuyVsLeaseTabFragment extends BaseFragment implements View.OnClickListener, UpdateValueInFragmentInterface {


    public boolean isResultShown = false;
    @BindView(R.id.viewPager_id)
    ViewPager viewPager;
    @BindView(R.id.tab_layout_id)
    TabLayout mTabLayout;
    @BindView(R.id.relative_left_arrow)
    RelativeLayout mleftRelativeLayout;
    @BindView(R.id.relative_center_home)
    RelativeLayout mcenterRelativeLayout;
    @BindView(R.id.relative_right_arrow)
    RelativeLayout mRightRelativeLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    UpdateValueInFragmentInterface updateInterface;
    DeciproAdapter viewPagerAdapter;
    CarDetailFragment carDetailFragment;
    LeaseDetailFragment leaseDetailFragment;
    Context mContext;
    HashMap<String, String> homeValues;
    CarVsLeaseModel carVsLeaseModel;
    private ArrayList<String> titles = new ArrayList<>(Arrays.asList("Car", "Lease"));
    private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
    private OnActivityBackPressedListener mCallBackListener;

    public static CarBuyVsLeaseTabFragment newInstance(HashMap<String, String> homeValues) {

        Bundle args = new Bundle();

        CarBuyVsLeaseTabFragment fragment = new CarBuyVsLeaseTabFragment();
        args.putSerializable("hashmap", homeValues);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        homeValues = new HashMap<>();
        mCallBackListener = (OnActivityBackPressedListener) (mContext);
        if (getArguments().containsKey("hashmap")) {
            homeValues = (HashMap<String, String>) getArguments().getSerializable("hashmap");
        }
        setUpdateValueInFragmentInterface(this);
        carDetailFragment = CarDetailFragment.newInstance(updateInterface);
        leaseDetailFragment = LeaseDetailFragment.newInstance(updateInterface);
        fragmentsList.add(carDetailFragment);
        fragmentsList.add(leaseDetailFragment);

    }

    private void setUpdateValueInFragmentInterface(UpdateValueInFragmentInterface carBuyVsLeaseInterface) {
        updateInterface = carBuyVsLeaseInterface;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_vs_lease_tab_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPagerAdapter = new DeciproAdapter(getChildFragmentManager(), fragmentsList, titles);
        viewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(viewPager);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        fab.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();

                break;
            case R.id.relative_center_home:
                Intent i = new Intent(mContext, HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.fab:
                Boolean isCarValid = carDetailFragment.mCarVsLeaseValidation(homeValues);
                Boolean isLeaseValid=false;
                if(isCarValid) {
                    isLeaseValid = leaseDetailFragment.mCarVsLeaseValidation(homeValues);
                    viewPager.setCurrentItem(2);
                }
                else {
                    viewPager.setCurrentItem(0);
                }

                if (isCarValid && isLeaseValid) {
                    callCarVsLeaseService();
                }

                break;

        }
    }

    private void callCarVsLeaseService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);

        Call<CarVsLeaseModel> call = obj.getCarVsLeaseservice(homeValues.get("city_type"),
                homeValues.get("tax_slab"),
                homeValues.get("oppur_cost"),
                homeValues.get("planned_occupation"),
                homeValues.get("loan_req"),
                homeValues.get("car_pur_price"),
                homeValues.get("tot_upf_sec_pay"),
                homeValues.get("mon_lease_pay"),
                homeValues.get("down_pay"),
                homeValues.get("loan_amt"),
                homeValues.get("loan_tenure"),
                homeValues.get("loan_int_rate"),
                homeValues.get("main_repair"),
                homeValues.get("fuel_run_exp"),
                homeValues.get("insurance"),
                homeValues.get("lease_tax"),
                homeValues.get("lease_ter_exp"));
        call.enqueue(new Callback<CarVsLeaseModel>() {
            @Override
            public void onResponse(Call<CarVsLeaseModel> call, Response<CarVsLeaseModel> response) {
                UtileKit.dismisssSpinnerDialog();
                carVsLeaseModel = response.body();
                if (carVsLeaseModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    if (null != carVsLeaseModel.getData()) {
                        addFragmenttoStack(ResultCarBuyVsRentFragment.newInstance(carVsLeaseModel));
                        fab.setVisibility(View.GONE);
                        isResultShown = true;
                    } else {

                    }
                } else if (carVsLeaseModel.getStatus_code().equals(UtileKit.SUCCESS_OVERRIDE_CODE)) {
                    if (carVsLeaseModel.getData() != null && carVsLeaseModel.getData().getMessage() != null)
                        UtileKit.intitializeAlertDialog(carVsLeaseModel.getData().getMessage().toString(), mContext);
                }
            }

            @Override
            public void onFailure(Call<CarVsLeaseModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }

    private void mCarVsLeaseValidation() {

    }

    @Override
    public void updateCarValue(String key, String value) {
        UtileKit.logTest("Sucess" + key, "" + value);
        homeValues.put(key, value);
    }

    @Override
    public void updateLeaseValue(String key, String value) {
        UtileKit.logTest("Sucess" + key, "" + value);
        homeValues.put(key, value);
    }
}

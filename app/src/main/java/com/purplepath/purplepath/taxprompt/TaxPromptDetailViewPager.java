package com.purplepath.purplepath.taxprompt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxanalysis.adapter.TaxplanAdapter;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.TaxPromptNewModel;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Murugesan on 1/22/18.
 */

public class TaxPromptDetailViewPager extends BaseFragment implements View.OnClickListener {


    private ViewPager viewPager;

    private TabLayout tabLayout;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;

    private TextView errorTextview;

    OnActivityBackPressedListener mListener;

    private Context mContext;

    private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();

    private TaxplanAdapter adapter;

    View view;

    private TaxPromptNewModel taxPromptNewModel;


    private Bundle args;

    public static TaxPromptDetailViewPager newInstance(TaxPromptNewModel taxPromptNewModel) {
        TaxPromptDetailViewPager fragment = new TaxPromptDetailViewPager();
        Bundle args = new Bundle();
        if (taxPromptNewModel != null) {
            args.putSerializable("taxPromptNewModel", taxPromptNewModel);
            fragment.setArguments(args);
        }
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        mListener=(OnActivityBackPressedListener)mContext;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mListener.setActionBarTitle("Tax Plan Details");
        if(view==null){
            view=inflater.inflate(R.layout.fragment_taxplan_viewpager, container, false);
        }
        viewPager= view.findViewById(R.id.viewPager_tax);

        adapter = TaxplanAdapter.newInstance(getChildFragmentManager());
        tabLayout= view.findViewById(R.id.tab_layout_taxplan);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        errorTextview = view.findViewById(R.id.errorTextview);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        errorTextview = view.findViewById(R.id.errorTextview);

        args = getArguments();
        if (args != null) {
            if (args.containsKey("taxPromptNewModel")) {
                taxPromptNewModel = (TaxPromptNewModel) args.getSerializable("taxPromptNewModel");
                try {
                    if (taxPromptNewModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        adapter.addFragment(TaxPromptPlanDetail.newInstance(taxPromptNewModel), getString(R.string.taxplansavings));
                        adapter.addFragment(TaxPromptProductDetail.newInstance(taxPromptNewModel), getString(R.string.taxplanproducts));

                        viewPager.setAdapter(adapter);
                        tabLayout.setupWithViewPager(viewPager);
                        viewPager.setOffscreenPageLimit(fragmentsList.size());
                    } else {
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }else {

            callTaxProductService();
        }
        return view;
    }


    private void callTaxProductService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String finYr = String.valueOf(year-1);
        Call<TaxPromptNewModel> call = webServiceObj.callTaxPromptService_new(UtileKit.getPersistedPurplePathPref("user_id"),"FY"+finYr);
        call.enqueue(new Callback<TaxPromptNewModel>() {
            @Override
            public void onResponse(Call<TaxPromptNewModel> call, Response<TaxPromptNewModel> response) {
                UtileKit.dismisssSpinnerDialog();
                taxPromptNewModel= response.body();

                try {
                    if (taxPromptNewModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {


                        adapter.addFragment(TaxPromptPlanDetail.newInstance(taxPromptNewModel), getString(R.string.taxplansavings));
                        adapter.addFragment(TaxPromptProductDetail.newInstance(taxPromptNewModel), getString(R.string.taxplanproducts));

                        viewPager.setAdapter(adapter);
                        tabLayout.setupWithViewPager(viewPager);
                        viewPager.setOffscreenPageLimit(fragmentsList.size());
                    } else {
                        errorTextview.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<TaxPromptNewModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_chart);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_chart:
                try{

                //addFragmenttoStack(new TaxAnaylsisBarChart());
                   addFragmenttoStack(new TaxAnaylsisBarChart());

                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:
                try{
                    addFragmenttoStack(new TaxPromptSummary());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    private void showFragment(Fragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment).addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
                mListener.onActivityBackPressed();
                break;

            case R.id.relative_center_home:
                Intent i= new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }
    }


}

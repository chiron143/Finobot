package com.purplepath.purplepath.taxanalysis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.taxanalysis.adapter.TaxplanAdapter;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
import com.purplepath.purplepath.taxfunnel.TaxFunnelFragment;
import java.util.ArrayList;
/**
 * Created by pravinr on 1/18/18.
 */

public class TaxAnalysisViewPager extends BaseFragment implements View.OnClickListener {

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;

    private TextView errorTextview;

    OnActivityBackPressedListener mListener;

    private Context mContext;

    private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();

    private TaxplanAdapter adapter;

    View view;

    private TaxCashFlowModel value;

    public static TaxAnalysisViewPager newInstance(TaxCashFlowModel taxAnalysisCashFlowChartModel) {
        TaxAnalysisViewPager fragment = new TaxAnalysisViewPager();
        Bundle args = new Bundle();
        if (taxAnalysisCashFlowChartModel != null) {
            args.putSerializable("TaxPlanDetails", taxAnalysisCashFlowChartModel);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        mListener=(OnActivityBackPressedListener)mContext;
        adapter = TaxplanAdapter.newInstance(getChildFragmentManager());
        adapter.addFragment(TaxAnalysis.newInstance(value), getString(R.string.taxanalysischart));
        adapter.addFragment(new TaxFunnelFragment(), getString(R.string.taxplanfunnel));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

       // value = (TaxCashFlowModel)getArguments().getSerializable("TaxPlanDetails");

        if(null!=getArguments())
        {
            if(getArguments().containsKey("TaxPlanDetails"))
                value = (TaxCashFlowModel) getArguments().getSerializable("TaxPlanDetails");
        }

        mListener.setActionBarTitle("Tax Plan Analysis");
        if(view==null){
            view=inflater.inflate(R.layout.fragment_taxplan_viewpager, container, false);
        }
        viewPager= view.findViewById(R.id.viewPager_tax);


        tabLayout= view.findViewById(R.id.tab_layout_taxplan);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        errorTextview = view.findViewById(R.id.errorTextview);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        errorTextview = view.findViewById(R.id.errorTextview);



        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(fragmentsList.size());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_detail,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_detail);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_detail:

                TaxPlanDetailViewPager taxPlanDetailViewPager=new TaxPlanDetailViewPager();
                showFragment(taxPlanDetailViewPager);
                break;

            case R.id.menu_summary:
                TaxPlanSummary taxPlanSummary = new TaxPlanSummary();
                showFragment(taxPlanSummary);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
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

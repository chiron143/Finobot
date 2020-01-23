package com.purplepath.purplepath.investmentPlan;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models.AmortizationScheduleModel;
import com.purplepath.purplepath.investmentPlan.Adapter.InvestmentPlanAdapter;
import com.purplepath.purplepath.investmentPlan.models.InvestmentPlanModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: Pratheep.S
 */
public class InvestmentPlanMain extends Fragment implements View.OnClickListener {

    LinearLayout bottom_bar_layout;
    RelativeLayout relative_left_arrow, relative_center_home;
    OnActivityBackPressedListener onActivityBackPressedListener;
    private InvestmentPlanModel investmentModel;
    private RecyclerView investmentPlan_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private InvestmentPlanAdapter adapter;
    String[][] details = new String[8][5];

    Context mContext;

    public static InvestmentPlanMain newInstance() {
        //Bundle args = new Bundle();
        InvestmentPlanMain fragment = new InvestmentPlanMain();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.networth_summary_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.menu_details:
                fragment = InvestmentPlanDetails.newInstance();
                showFragment(fragment);
                break;
            case R.id.menu_graph:
               //  fragment = InvestmentPlanningGraf.newInstance(amortizationScheduleModel);
              //  showFragment(fragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_investment_plan_main, container, false);
        setHasOptionsMenu(true);
        mContext = getContext();

        onActivityBackPressedListener = (OnActivityBackPressedListener) getActivity();
        onActivityBackPressedListener.setActionBarTitle("Investment Plan");
        bottom_bar_layout = view.findViewById(R.id.bottom_bar_layout);
        relative_left_arrow = bottom_bar_layout.findViewById(R.id.relative_left_arrow);
        relative_center_home = bottom_bar_layout.findViewById(R.id.relative_center_home);

        investmentPlan_recyclerView = view.findViewById(R.id.investmentPlan_recyclerView);
        layoutManager = new LinearLayoutManager(mContext);
        investmentPlan_recyclerView.setLayoutManager(layoutManager);

        investmentPlan_recyclerView.setNestedScrollingEnabled(false);
        relative_center_home.setOnClickListener(this);
        relative_left_arrow.setOnClickListener(this);

        callInvestmentPlanService();

        return view;
    }


    private void callInvestmentPlanService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<InvestmentPlanModel> call = obj.getInvestmentPlan(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<InvestmentPlanModel>() {
            @Override
            public void onResponse(Call<InvestmentPlanModel> call, Response<InvestmentPlanModel> response) {
                UtileKit.dismisssSpinnerDialog();
                investmentModel = response.body();
                if (investmentModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {


                    initializeValues();
                }
            }

            @Override
            public void onFailure(Call<InvestmentPlanModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void initializeValues() {

        details[0][0] = investmentModel.getData().getComm_gold().get(0).getCumulative_plan();
        details[0][1] = investmentModel.getData().getComm_gold().get(0).getCumulative_actual();
        details[0][2] = investmentModel.getData().getComm_gold().get(0).getCumulative_variance_val();
        details[0][3] = investmentModel.getData().getComm_gold().get(0).getCumulative_variance_per();
        details[0][4] = "Commodity Gold";

        details[1][0] = investmentModel.getData().getEmp_ben().get(0).getCumulative_plan();
        details[1][1] = investmentModel.getData().getEmp_ben().get(0).getCumulative_actual();
        details[1][2] = investmentModel.getData().getEmp_ben().get(0).getCumulative_variance_val();
        details[1][3] = investmentModel.getData().getEmp_ben().get(0).getCumulative_variance_per();
        details[1][4] = "Employee Benifits";

        details[2][0] = investmentModel.getData().getEqu().get(0).getCumulative_plan();
        details[2][1] = investmentModel.getData().getEqu().get(0).getCumulative_actual();
        details[2][2] = investmentModel.getData().getEqu().get(0).getCumulative_variance_val();
        details[2][3] = investmentModel.getData().getEqu().get(0).getCumulative_variance_per();
        details[2][4] = "Equity";

        details[3][0] = investmentModel.getData().getFix_inc().get(0).getCumulative_plan();
        details[3][1] = investmentModel.getData().getFix_inc().get(0).getCumulative_actual();
        details[3][2] = investmentModel.getData().getFix_inc().get(0).getCumulative_variance_val();
        details[3][3] = investmentModel.getData().getFix_inc().get(0).getCumulative_variance_per();
        details[3][4] = "Fixed Income";

        details[4][0] = investmentModel.getData().getHou_asst().get(0).getCumulative_plan();
        details[4][1] = investmentModel.getData().getHou_asst().get(0).getCumulative_actual();
        details[4][2] = investmentModel.getData().getHou_asst().get(0).getCumulative_variance_val();
        details[4][3] = investmentModel.getData().getHou_asst().get(0).getCumulative_variance_per();
        details[4][4] = "House hold Asset";

        details[5][0] = investmentModel.getData().getLiq().get(0).getCumulative_plan();
        details[5][1] = investmentModel.getData().getLiq().get(0).getCumulative_actual();
        details[5][2] = investmentModel.getData().getLiq().get(0).getCumulative_variance_val();
        details[5][3] = investmentModel.getData().getLiq().get(0).getCumulative_variance_per();
        details[5][4] = "Liquid";

        details[6][0] = investmentModel.getData().getReal_prop().get(0).getCumulative_plan();
        details[6][1] = investmentModel.getData().getReal_prop().get(0).getCumulative_actual();
        details[6][2] = investmentModel.getData().getReal_prop().get(0).getCumulative_variance_val();
        details[6][3] = investmentModel.getData().getReal_prop().get(0).getCumulative_variance_per();
        details[6][4] = "Real Estate Propery";

        details[7][0] = investmentModel.getData().getOth_asst().get(0).getCumulative_plan();
        details[7][1] = investmentModel.getData().getOth_asst().get(0).getCumulative_actual();
        details[7][2] = investmentModel.getData().getOth_asst().get(0).getCumulative_variance_val();
        details[7][3] = investmentModel.getData().getOth_asst().get(0).getCumulative_variance_per();
        details[7][4] = "Other Assets";

        adapter = new InvestmentPlanAdapter(details);
        investmentPlan_recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

            case R.id.relative_left_arrow:
                onActivityBackPressedListener.onActivityBackPressed();
                break;

        }
    }
}

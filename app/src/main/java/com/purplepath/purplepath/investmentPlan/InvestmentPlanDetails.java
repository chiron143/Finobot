package com.purplepath.purplepath.investmentPlan;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models.AmortizationScheduleModel;
import com.purplepath.purplepath.investmentPlan.models.InvestmentPlanModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Author: Pratheep.S
 */
public class InvestmentPlanDetails extends Fragment implements View.OnClickListener {
    @BindView(R.id.comm_gold_tr)
    TableRow comm_gold_tr;

    @BindView(R.id.employeeBenifit_tr)
    TableRow employeeBenifit_tr;

    @BindView(R.id.equity_tr)
    TableRow equity_tr;

    @BindView(R.id.fixedIncome_tr)
    TableRow fixedIncome_tr;

    @BindView(R.id.house_asset)
    TableRow house_asset;

    @BindView(R.id.liquid)
    TableRow liquid;

    @BindView(R.id.realEstate_tr)
    TableRow realEstate_tr;

    @BindView(R.id.other_asset_tr)
    TableRow other_asset_tr;

    LinearLayout bottom_bar_layout;
    RelativeLayout relative_left_arrow, relative_center_home;
    OnActivityBackPressedListener onActivityBackPressedListener;
    private String TAG = "spcheck";
    private InvestmentPlanModel investmentModel;

    private final int POS = 0;

    public static InvestmentPlanDetails newInstance() {

        // Bundle args = new Bundle();
        InvestmentPlanDetails fragment = new InvestmentPlanDetails();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.networth_summary_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId())
        {
            case R.id.menu_details:
                fragment=InvestmentPlanMain.newInstance();
                showFragment(fragment);
                break;
            case R.id.menu_graph:
                 //fragment=InvestmentPlanningGraf.newInstance(investmentModel );
              //  showFragment(fragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_investment_plan_details, container, false);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        onActivityBackPressedListener = (OnActivityBackPressedListener) getActivity();
        onActivityBackPressedListener.setActionBarTitle("Investment Plan");
        bottom_bar_layout = view.findViewById(R.id.bottom_bar_layout);
        relative_left_arrow = bottom_bar_layout.findViewById(R.id.relative_left_arrow);
        relative_center_home = bottom_bar_layout.findViewById(R.id.relative_center_home);

        relative_center_home.setOnClickListener(this);
        relative_left_arrow.setOnClickListener(this);

        callInvestmentPlanService();

        return view;
    }

    private void callInvestmentPlanService() {
        WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<InvestmentPlanModel> call = obj.getInvestmentPlan(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<InvestmentPlanModel>() {
            @Override
            public void onResponse(Call<InvestmentPlanModel> call, Response<InvestmentPlanModel> response) {
                investmentModel = response.body();
                if (investmentModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    showValuesInTable(investmentModel);
                }
            }

            @Override
            public void onFailure(Call<InvestmentPlanModel> call, Throwable t) {

            }
        });
    }

    private void showValuesInTable(InvestmentPlanModel investmentModel) {
    /*    investmentModel.getData().intializeHashMap();
        ((ArrayList<Comm_gold>)investmentModel.getData().getHashmap().get(1)).get()*/
        try {
            ((TextView) comm_gold_tr.getChildAt(1)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_plan());
            ((TextView) comm_gold_tr.getChildAt(2)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_actual());
            ((TextView) comm_gold_tr.getChildAt(3)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_val());
            ((TextView) comm_gold_tr.getChildAt(4)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_per());

            ((TextView) employeeBenifit_tr.getChildAt(1)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_plan());
            ((TextView) employeeBenifit_tr.getChildAt(2)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_actual());
            ((TextView) employeeBenifit_tr.getChildAt(3)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_val());
            ((TextView) employeeBenifit_tr.getChildAt(4)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_per());

            ((TextView) equity_tr.getChildAt(1)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_plan());
            ((TextView) equity_tr.getChildAt(2)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_actual());
            ((TextView) equity_tr.getChildAt(3)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_val());
            ((TextView) equity_tr.getChildAt(4)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_per());

            ((TextView) fixedIncome_tr.getChildAt(1)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_plan());
            ((TextView) fixedIncome_tr.getChildAt(2)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_actual());
            ((TextView) fixedIncome_tr.getChildAt(3)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_val());
            ((TextView) fixedIncome_tr.getChildAt(4)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_per());


            ((TextView) house_asset.getChildAt(1)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_plan());
            ((TextView) house_asset.getChildAt(2)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_actual());
            ((TextView) house_asset.getChildAt(3)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_val());
            ((TextView) house_asset.getChildAt(4)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_per());

            ((TextView) liquid.getChildAt(1)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_plan());
            ((TextView) liquid.getChildAt(2)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_actual());
            ((TextView) liquid.getChildAt(3)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_val());
            ((TextView) liquid.getChildAt(4)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_per());

            ((TextView) realEstate_tr.getChildAt(1)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_plan());
            ((TextView) realEstate_tr.getChildAt(2)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_actual());
            ((TextView) realEstate_tr.getChildAt(3)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_val());
            ((TextView) realEstate_tr.getChildAt(4)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_per());

            ((TextView) other_asset_tr.getChildAt(1)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_plan());
            ((TextView) other_asset_tr.getChildAt(2)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_actual());
            ((TextView) other_asset_tr.getChildAt(3)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_val());
            ((TextView) other_asset_tr.getChildAt(4)).setText(investmentModel.getData().getComm_gold().get(POS).getCumulative_variance_per());


        } catch (ClassCastException e) {
            e.printStackTrace();
        }

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

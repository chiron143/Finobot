package com.purplepath.purplepath.desiproAllModules.depositcomparison;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.CrashExceptionHandler;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.dialoge.DepositRecomendationDialog;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.model.DepositCompModel;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.ui.DesiproActivity;
import com.purplepath.purplepath.desiproAllModules.loanComparison.adapter.DeciproAdapter;
import com.purplepath.purplepath.desiproAllModules.loanComparison.interfaces.LoanComparisonInterface;
import com.purplepath.purplepath.desiproAllModules.loanComparison.models.LoanComparisonModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dinesh on 20/06/17.
 */

public class DepositComparisonActivity  extends AppCompatActivity implements View.OnClickListener, LoanComparisonInterface {

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    //  @BindView(R.id.loanComparisonViewPager)
    ViewPager depositComparisonViewPager;

    //  @BindView(R.id.depositComparisonTabLayout)
    TabLayout depositComparisonTabLayout;

    private FloatingActionButton depositComparison_fab;
    private Toolbar mtoolbar;

    private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
    private DeciproAdapter adapter;
    private ArrayList<String> titles = new ArrayList<>(Arrays.asList("Deposit1", "Deposit2", "Deposit3"));

    private String TAG = "spcheck";

    private Context mContext;

    private HashMap<String, String> deposit_1_values = new HashMap<>(), deposit_2_values = new HashMap<>(), deposit_3_values = new HashMap<>();
    private LoanComparisonModel depositComparisonModel;
    private ArrayList<String> mandatoryKeyList=new ArrayList<>(Arrays.asList("dp_init_val","dp_period_val","dp_tenure","dp_rate"));
    Deposit1Fragment deposit1,deposit2,deposit3;
    DepositRecomendationDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_comparison);

        Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(this,DepositComparisonActivity.class));  //  ButterKnife.bind(this);

        mContext = this;
        mtoolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(null);
        AppCompatTextView mTitle = mtoolbar.findViewById(R.id.title_viewId);
        mTitle.setText("DeciPro - Deposit Comparison");
        depositComparisonViewPager = (ViewPager) findViewById(R.id.loanComparisonViewPager);
        depositComparisonTabLayout = (TabLayout) findViewById(R.id.loanComparisonTabLayout);

//        deposit1=Deposit1Fragment.newInstance(1);
//        deposit2=Deposit1Fragment.newInstance(2);
//        deposit3=Deposit1Fragment.newInstance(3);
        fragmentsList.add(deposit1);
        fragmentsList.add(deposit2);
        fragmentsList.add(deposit3);
        //fragmentsList.add(new Loan2Fragment());
        //fragmentsList.add(new Loan3Fragment());

        adapter = new DeciproAdapter(getSupportFragmentManager(), fragmentsList, titles);
        depositComparisonViewPager.setAdapter(adapter);
        depositComparisonTabLayout.setupWithViewPager(depositComparisonViewPager);
        depositComparisonViewPager.setOffscreenPageLimit(fragmentsList.size());

        mleftRelativeLayout = (RelativeLayout) findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) findViewById(R.id.relative_right_arrow);

        depositComparison_fab = (FloatingActionButton) findViewById(R.id.loanComparison_fab);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        depositComparison_fab.setOnClickListener(this);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.desipro_menu, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow:
                onBackPressed();
                break;

            case R.id.relative_center_home:
                Intent i = new Intent(this, HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

            case R.id.loanComparison_fab:

                if(!isMandatoryFileldsEmpty(1,deposit_1_values)&&!isMandatoryFileldsEmpty(2,deposit_2_values)){
                    if(deposit_3_values.isEmpty())
                    callLoanComparisonService();
                    else
                    {
                        if(!isMandatoryFileldsEmpty(3,deposit_3_values))
                            callLoanComparisonService();
                    }
                }
                break;


        }
    }

    private boolean isMandatoryFileldsEmpty(int fragmentID,HashMap loanValues) {
        boolean isEmpty=false;
        Deposit1Fragment fragment=null;
        switch (fragmentID){
            case 1:
                fragment=deposit1;
                break;
            case 2:
                fragment=deposit2;
                break;
            case 3:
                fragment=deposit3;
                break;
        }
        for (int i = 0; i < (mandatoryKeyList.size()); i++) {
            try {
                if (loanValues.get(mandatoryKeyList.get(i)) == null || (loanValues.get(mandatoryKeyList.get(i)).toString()).equalsIgnoreCase("")) {
                    depositComparisonViewPager.setCurrentItem(fragmentID-1);
                    fragment.setErrorInLoanFragment(i);
                    isEmpty = true;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }


        return isEmpty;
    }

    private void callLoanComparisonService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<DepositCompModel> call = obj.getDepositComparisonService( UtileKit.getStringwithoutCurreny(deposit_1_values.get("dp_init_val")), UtileKit.getStringwithoutCurreny(deposit_1_values.get("dp_period_val")), deposit_1_values.get("dp_tenure"),
                deposit_1_values.get("dp_rate"),  UtileKit.getStringwithoutCurreny(deposit_2_values.get("dp_init_val")),  UtileKit.getStringwithoutCurreny(deposit_2_values.get("dp_period_val")), deposit_2_values.get("dp_tenure"), deposit_2_values.get("dp_rate"),
                UtileKit.getStringwithoutCurreny(deposit_3_values.get("dp_init_val")), UtileKit.getStringwithoutCurreny(deposit_3_values.get("dp_period_val")), deposit_3_values.get("dp_tenure"), deposit_3_values.get("dp_rate"));
//        Log.i(TAG, "callLoanComparisonService: " + "deposit_1_dp_init_val" + deposit_1_values.get("dp_init_val") + "deposit_1_dp_period_val" + deposit_1_values.get("dp_period_val") + "tenure1dp_tenure" + deposit_1_values.get("dp_tenure") +
//                "dp1_rate" + deposit_1_values.get("dp_rate") + "finaceValue2dp_init_val" + deposit_2_values.get("dp_init_val") + "dp_period_val2 " + deposit_2_values.get("dp_period_val") + "tenure2dp_tenure " + deposit_2_values.get("dp_tenure") + "emi2dp_rate " + deposit_2_values.get("dp_rate") +
//                "finac3dp_init_val " + deposit_3_values.get("dp_init_val") + "rateOf3dp_period_val " + deposit_3_values.get("dp_period_val") + "tenure3dp_tenure" + deposit_3_values.get("dp_tenure") + "emi3 dp_rate" + deposit_3_values.get("dp_rate"));
        call.enqueue(new Callback<DepositCompModel>() {
            @Override
            public void onResponse(Call<DepositCompModel> call, Response<DepositCompModel> response) {
                Log.i(TAG, "onResponse: success");
                DepositCompModel depositComparisonModel = response.body();
                if (depositComparisonModel != null) {
                    if (depositComparisonModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        dialog = DepositRecomendationDialog.newInstance(depositComparisonModel);
                        dialog.show(getSupportFragmentManager(), "LoanRecomendation");
                    }
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<DepositCompModel> call, Throwable t) {
                Log.i(TAG, "onResponse: error");
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext,t);

            }
        });
    }

    @Override
    public void updateLoanValues(String value, String key, int fragmentID) {
        switch (fragmentID) {
            case 1:
                deposit_1_values.put(key, value);
                //  Log.i(TAG, "updateLoanValues: "+"key "+key+" value "+value+"fragmentID"+fragmentID);
                break;

            case 2:
                deposit_2_values.put(key, value);
                //  Log.i(TAG, "updateLoanValues: "+"key "+key+" value "+value+"fragmentID"+fragmentID);
                break;

            case 3:
                deposit_3_values.put(key, value);
                // Log.i(TAG, "updateLoanValues: "+"key "+key+" value "+value+"fragmentID"+fragmentID);
                break;
        }
    }
}
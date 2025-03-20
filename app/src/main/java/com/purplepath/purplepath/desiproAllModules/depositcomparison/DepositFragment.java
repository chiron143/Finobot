package com.purplepath.purplepath.desiproAllModules.depositcomparison;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.dialoge.DepositRecomendationDialog;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.dialoge.DepositRecommentationSummary;
import com.purplepath.purplepath.desiproAllModules.depositcomparison.model.DepositCompModel;
import com.purplepath.purplepath.desiproAllModules.loanComparison.adapter.DeciproAdapter;
import com.purplepath.purplepath.desiproAllModules.loanComparison.interfaces.LoanComparisonInterface;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by muruga on 11/22/17.
 */

public class DepositFragment extends BaseFragment implements View.OnClickListener,LoanComparisonInterface {

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    ViewPager depositComparisonViewPager;
    TabLayout depositComparisonTabLayout;

    private FloatingActionButton depositComparison_fab;


    private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
    private DeciproAdapter adapter;
    private ArrayList<String> titles = new ArrayList<>(Arrays.asList("Deposit1", "Deposit2", "Deposit3"));

    private String TAG = "spcheck";
    private Context mContext;

    private HashMap<String, String> deposit_1_values = new HashMap<>(), deposit_2_values = new HashMap<>(), deposit_3_values = new HashMap<>();
    private ArrayList<String> mandatoryKeyList=new ArrayList<>(Arrays.asList("dp_init_val","dp_period_val","dp_tenure","dp_rate"));
    Deposit1Fragment deposit1,deposit2,deposit3;
    DepositRecomendationDialog dialog;

    private OnActivityBackPressedListener mCallBackListener;
    private LoanComparisonInterface  mLoanComparisonInterface;
    View view;
    DepositCompModel depositComparisonModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setHasOptionsMenu(true);



        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
            setListener(this);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {

        }
        deposit1=Deposit1Fragment.newInstance(1,mLoanComparisonInterface);
        deposit2=Deposit1Fragment.newInstance(2,mLoanComparisonInterface);
        deposit3=Deposit1Fragment.newInstance(3,mLoanComparisonInterface);
        fragmentsList.add(deposit1);
        fragmentsList.add(deposit2);
        fragmentsList.add(deposit3);
        adapter = new DeciproAdapter(getChildFragmentManager(), fragmentsList, titles);
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }
    public void setListener(LoanComparisonInterface callbackInterface){
        this.mLoanComparisonInterface=callbackInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null) {
            view = inflater.inflate(R.layout.activity_loan_comparison, container, false);
        }
        mCallBackListener.setActionBarTitle("DeciPro - Deposit Comparison");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        depositComparisonViewPager = view.findViewById(R.id.loanComparisonViewPager);
        depositComparisonTabLayout = view.findViewById(R.id.loanComparisonTabLayout);



        depositComparisonViewPager.setAdapter(adapter);
        depositComparisonTabLayout.setupWithViewPager(depositComparisonViewPager);
        depositComparisonViewPager.setOffscreenPageLimit(fragmentsList.size());

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        depositComparison_fab = view.findViewById(R.id.loanComparison_fab);
        depositComparison_fab.setOnClickListener(this);



        return view;
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
                depositComparisonModel = response.body();
                if (depositComparisonModel != null) {
                    if (depositComparisonModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                      //  dialog = DepositRecomendationDialog.newInstance(depositComparisonModel);
                       // dialog.show(getChildFragmentManager(), "LoanRecomendation");

                        addFragmenttoStack(DepositRecommentationSummary.newInstance(depositComparisonModel));

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
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                startSettingHomeActivity();
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

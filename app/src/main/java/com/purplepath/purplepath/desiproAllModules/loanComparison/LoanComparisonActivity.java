package com.purplepath.purplepath.desiproAllModules.loanComparison;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.loanComparison.adapter.DeciproAdapter;
import com.purplepath.purplepath.desiproAllModules.loanComparison.interfaces.LoanComparisonInterface;
import com.purplepath.purplepath.desiproAllModules.loanComparison.models.LoanComparisonModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.getStringwithoutCurreny;

public class LoanComparisonActivity extends BaseFragment implements View.OnClickListener, LoanComparisonInterface {

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    //  @BindView(R.id.loanComparisonViewPager)
    ViewPager loanComparisonViewPager;

    //  @BindView(R.id.loanComparisonTabLayout)
    TabLayout loanComparisonTabLayout;

    private FloatingActionButton loanComparison_fab;
    private Toolbar toolbar;

    private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
    private DeciproAdapter adapter;
    private ArrayList<String> titles = new ArrayList<>(Arrays.asList("Loan1", "Loan2", "Loan3"));

    private String TAG = "spcheck";

    private Context mContext;

    private HashMap<String, String> loan_1_values = new HashMap<>(), loan_2_values = new HashMap<>(), loan_3_values = new HashMap<>();
    private LoanComparisonModel loanComparisonModel;
    private ArrayList<String> mandatoryKeyList = new ArrayList<>(Arrays.asList("finaceValue", "rateOfInterest", "tenure", "emi"));
    private ArrayList<String> mandatoryKeyList2 = new ArrayList<>(Arrays.asList("finaceValue", "rateOfInterest", "tenure"));
    private List<String> associatedChargesKeyList=Arrays.asList("processingFee","legalFee","administrationFee","insuranceCoverFee");
    private Double associatedCharges1,associatedCharges2,associatedCharges3;
    Loan1Fragment loan1, loan2, loan3;
    LoanRecomendationDialog dialog;
    private LoanComparisonInterface  mLoanComparisonInterface;
    private OnActivityBackPressedListener mCallBackListener;

    View view;
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
        {}
        loan1 = Loan1Fragment.newInstance(1,mLoanComparisonInterface);
        loan2 = Loan1Fragment.newInstance(2,mLoanComparisonInterface);
        loan3 = Loan1Fragment.newInstance(3,mLoanComparisonInterface);
        fragmentsList.add(loan1);
        fragmentsList.add(loan2);
        fragmentsList.add(loan3);
        adapter = new DeciproAdapter(getChildFragmentManager(), fragmentsList, titles);

//        setContentView(R.layout.activity_loan_comparison);
//        //  ButterKnife.bind(this, (Activity) mContext);

        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}

    }

    public void setListener(LoanComparisonInterface callbackInterface){
        this.mLoanComparisonInterface=callbackInterface;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(R.layout.activity_loan_comparison, container, false);
        }

        loanComparisonViewPager = view.findViewById(R.id.loanComparisonViewPager);
        loanComparisonTabLayout = view.findViewById(R.id.loanComparisonTabLayout);
        mCallBackListener.setActionBarTitle(" DeciPro - Loan Comparison");

        //fragmentsList.add(new Loan2Fragment());
        //fragmentsList.add(new Loan3Fragment());


        loanComparisonViewPager.setAdapter(adapter);
        loanComparisonTabLayout.setupWithViewPager(loanComparisonViewPager);
        loanComparisonViewPager.setOffscreenPageLimit(fragmentsList.size());

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        loanComparison_fab = view.findViewById(R.id.loanComparison_fab);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        loanComparison_fab.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow:
                Log.i("spcheck", " relative_left_arrow is clicked"  );
                mCallBackListener.onActivityBackPressed();
                break;

            case R.id.relative_center_home:
                Log.i(TAG, " relative_center_home is clicked"  );
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                startActivity(i);
                break;

            case R.id.loanComparison_fab:

                if (!isMandatoryFileldsEmpty(1, loan_1_values,mandatoryKeyList) && !isMandatoryFileldsEmpty(2, loan_2_values,mandatoryKeyList2)
                        ) {
                    if(loan_3_values.isEmpty()) {
                        associatedCharges1 = getAssociatedCharges(loan_1_values);
                        associatedCharges2 = getAssociatedCharges(loan_2_values);
                        callLoanComparisonService();
                    }
                    else if(!isMandatoryFileldsEmpty(3, loan_3_values,mandatoryKeyList2)){
                        associatedCharges1 = getAssociatedCharges(loan_1_values);
                        associatedCharges2 = getAssociatedCharges(loan_2_values);
                        associatedCharges3 = getAssociatedCharges(loan_3_values);
                        callLoanComparisonService();
                    }

                }
                break;


        }
    }

    private double getAssociatedCharges(HashMap<String, String> loan_values) {
        double associatedCharges=0.0f;
        for(String key:associatedChargesKeyList) {
            if(null!=loan_values.get(key)&&!loan_values.get(key).equals("")) {
                //associatedCharges += Double.parseDouble(getStringwithoutCurreny(loan_values.get(key)));
                associatedCharges += Double.parseDouble((loan_values.get(key).contains(",")||loan_values.get(key).contains("₹"))?getStringwithoutCurreny(loan_values.get(key)):loan_values.get(key));
                //    Log.i(TAG, "getAssociatedCharges: associatedCharges  "+associatedCharges +"loan_values.get(key) "+loan_values.get(key));
            }
        }
        //  Log.i(TAG, "Total AssociatedCharges: "+associatedCharges);
        return associatedCharges;
    }

    private boolean isMandatoryFileldsEmpty(int fragmentID, HashMap loanValues,ArrayList<String> mandatoryKeyList) {
        boolean isEmpty = false;
        Loan1Fragment fragment = null;
        switch (fragmentID) {
            case 1:
                fragment = loan1;
                break;
            case 2:
                fragment = loan2;
                break;
            case 3:
                fragment = loan3;
                break;
        }
        for (int i = 0; i < (mandatoryKeyList.size()); i++) {
            try {
                if (loanValues.get(mandatoryKeyList.get(i)) == null || (loanValues.get(mandatoryKeyList.get(i)).toString()).equalsIgnoreCase("")) {
                    loanComparisonViewPager.setCurrentItem(fragmentID - 1);
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
        Call<LoanComparisonModel> call = obj.getLoanComparisonService(getStringwithoutCurreny(loan_1_values.get("finaceValue")),loan_1_values.get("rateOfInterest"), getStringwithoutCurreny(loan_1_values.get("tenure")),
                getStringwithoutCurreny(loan_1_values.get("emi")),associatedCharges1+"", getStringwithoutCurreny(loan_1_values.get("residualValue")), getStringwithoutCurreny(loan_2_values.get("finaceValue")),
                loan_2_values.get("rateOfInterest"), getStringwithoutCurreny( loan_2_values.get("tenure")), getStringwithoutCurreny( loan_2_values.get("emi")),
                associatedCharges2+"", getStringwithoutCurreny(loan_2_values.get("residualValue")), getStringwithoutCurreny(loan_3_values.get("finaceValue")), loan_3_values.get("rateOfInterest"),
                getStringwithoutCurreny(loan_3_values.get("tenure")), getStringwithoutCurreny( loan_3_values.get("emi")),
                associatedCharges3+"", getStringwithoutCurreny(loan_3_values.get("residualValue")));
        Log.i(TAG, "callLoanComparisonService: residualValue 1 "+loan_1_values.get("residualValue")+"residualValue 2  "+loan_2_values.get("residualValue")+"residualValue  3  "+loan_3_values.get("residualValue"));

        Log.i(TAG, "callLoanComparisonService: " + "finaceValue1" + loan_1_values.get("finaceValue") + "rateOfInterest1" + loan_1_values.get("rateOfInterest") + "tenure1" + loan_1_values.get("tenure") +
                "emi1" + loan_1_values.get("emi") + "finaceValue2" + loan_2_values.get("finaceValue") + "rateOfInterest2 " + loan_2_values.get("rateOfInterest") + "tenure2 " + loan_2_values.get("tenure") + "emi2 " + loan_2_values.get("emi") +
                "finaceValue3 " + loan_3_values.get("finaceValue") + "rateOfInterest3 " + loan_3_values.get("rateOfInterest") + "tenure3" + loan_3_values.get("tenure") + "emi3 " + loan_3_values.get("emi"));
        call.enqueue(new Callback<LoanComparisonModel>() {
            @Override
            public void onResponse(Call<LoanComparisonModel> call, Response<LoanComparisonModel> response) {
                Log.i(TAG, "onResponse: success");
                loanComparisonModel = response.body();
                if (loanComparisonModel != null) {

                    if (loanComparisonModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                       // dialog = LoanRecomendationDialog.newInstance(loanComparisonModel);
                      //  dialog.show(getFragmentManager(), "LoanRecomendation");

                        addFragmenttoStack(LoanComparisonSummary.newInstance(loanComparisonModel));
                    }
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<LoanComparisonModel> call, Throwable t) {
                Log.i(TAG, "onResponse: error");
                UtileKit.dismisssSpinnerDialog();

            }
        });
    }

    @Override
    public void updateLoanValues(String value, String key, int fragmentID) {
        switch (fragmentID) {
            case 1:
                if(!StringUtils.isEmpty(value))
                    loan_1_values.put(key, value);
                //  Log.i(TAG, "updateLoanValues: "+"key "+key+" value "+value+"fragmentID"+fragmentID);
                break;

            case 2:
                if(!StringUtils.isEmpty(value))
                    loan_2_values.put(key, value);
                //  Log.i(TAG, "updateLoanValues: "+"key "+key+" value "+value+"fragmentID"+fragmentID);
                break;

            case 3:
                if(!StringUtils.isEmpty(value))
                    loan_3_values.put(key, value);
                // Log.i(TAG, "updateLoanValues: "+"key "+key+" value "+value+"fragmentID"+fragmentID);
                break;
        }
    }
}

package com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.CrashExceptionHandler;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.adapter.DesiproPagerAdapter;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.interfaces.UpdateDateCallBackInterface;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.interfaces.UpdateValueInActivityInterface;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.models.LoanRecomendationModel;
import com.purplepath.purplepath.desiproAllModules.loanComparison.LoanComparisonActivity;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DesiproActivity extends BaseFragment implements UpdateValueInActivityInterface, UpdateDateCallBackInterface, View.OnClickListener {

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context   mContext;
    private OnActivityBackPressedListener mCallBackListener;
    int currenLoanSize = 8, newLoanSize = 8;
    int currentLoanMandatoryItemSize = 5, newLoanMandatoryItemSize = 4;
    String[] currentLoanValues = new String[currenLoanSize];
    String[] newLoanValues = new String[newLoanSize];

    String loanStartDate, firstEmiPaidDate, lastEmiPaidDate, nextEmiDueDate, finalEmiDate, exitCharge, entryCharge;

    String loanStartDateInFormat, firstEmiPaidDateInFormat, lastEmiPaidDateInFormat, nextEmiDueDateInFormat, finalEmiDateInFormat, exitChargeInFormat, entryChargeInFormat;

    CurrentLoanFragment currentLoanFragment;
    int currentLoanFragmentIndex = 0, newLoanFragmentIndex = 1;
    NewLoanFragment newLoanFragment;
    UpdateValueInActivityInterface updateInterface;
    UpdateDateCallBackInterface updateDateCallBackInterface;
    boolean[] emptyDates = new boolean[5];

    LoanRecomendationModel loanRecomendationModel;
    RecomendationDialogFragment recomendationDialog;
    FloatingActionButton homeloan_switch_fab;
    DesiproPagerAdapter adapter;
    private String TAG = "spcheck";
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        super.onCreate(savedInstanceState);
        mContext = getContext();
        setHasOptionsMenu(true);
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
//            updateInterface = (UpdateValueInActivityInterface) mContext;
//            updateDateCallBackInterface = (UpdateDateCallBackInterface) mContext;
            setListener(this);
            setcallbackListener(this);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        currentLoanFragment =  CurrentLoanFragment.newInstance(updateDateCallBackInterface,updateInterface);
        newLoanFragment =  NewLoanFragment.newInstance(updateInterface);
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    private void setcallbackListener(UpdateDateCallBackInterface desiproActivity) {
        this.updateDateCallBackInterface=desiproActivity;
    }

    public void setListener(UpdateValueInActivityInterface callbackInterface){
        this.updateInterface=callbackInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
             view = inflater.inflate(R.layout.activity_desipro, container, false);
        }

        Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(getActivity(),LoanComparisonActivity.class));
        viewPager = view.findViewById(R.id.desipro_viewpager);
        tabLayout = view.findViewById(R.id.desipro_tabs);


        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        homeloan_switch_fab= view.findViewById(R.id.homeloan_switch_fab);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        homeloan_switch_fab.setOnClickListener(this);

        mCallBackListener.setActionBarTitle("DeciPro - Home Loan Switch");


        setupViewPager(viewPager);
        return  view;
    }




//        @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(this,
//                DesiproActivity.class));
//        setContentView(R.layout.activity_desipro);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(null);

//    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return true;
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        /*MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.desipro_menu, menu);*/
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.done_menu) {
            if (!checkForNullInCurrentLoan() && !validateTenureValues() && !checkForNullInNewLoan()) {
                try {
                    exitCharge = calculateEntryAndExitCharge(currentLoanMandatoryItemSize, currenLoanSize, currentLoanValues);
                    entryCharge = calculateEntryAndExitCharge(newLoanMandatoryItemSize, newLoanSize, newLoanValues);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                callRecomendationWebService();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private String calculateEntryAndExitCharge(int madatoryItemSize, int totalsize, String[] allValues) {
        float sum = 0;
        for (int i = madatoryItemSize; i < totalsize; i++) {
            if (UtileKit.validateObjectValues(allValues[i])) {
                //sum += Float.parseFloat(UtileKit.getStringwithoutCurreny(allValues[i]));
                sum += Float.parseFloat(((allValues[i].contains(",")||allValues[i].contains("₹"))?UtileKit.getStringwithoutCurreny(allValues[i]):allValues[i]));
                //    Log.i(TAG, "calculateEntryAndExitCharge: allValues[i]"+allValues[i]+" sum  "+sum);
            }
        }
        return sum +"";
    }

    private void callRecomendationWebService() {
        UtileKit.showSpinnerDialog(mContext, false);
        //     Toast.makeText(this,"webService call",Toast.LENGTH_LONG).show();
        WebServiceCalls obj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<LoanRecomendationModel> call = obj.callLoanRecomendationService(UtileKit.getStringwithoutCurreny(currentLoanValues[0]), currentLoanValues[1], currentLoanValues[2], currentLoanValues[3], UtileKit.getStringwithoutCurreny(currentLoanValues[4]),
                newLoanValues[1], newLoanValues[2], UtileKit.getStringwithoutCurreny(newLoanValues[3]), loanStartDate, firstEmiPaidDate, lastEmiPaidDate, nextEmiDueDate, finalEmiDate, exitCharge, entryCharge);
        Log.i(TAG, " exitCharge  " + exitCharge);
        Log.i(TAG, "entryCharge " + entryCharge);
        call.enqueue(new Callback<LoanRecomendationModel>() {
            @Override
            public void onResponse(Call<LoanRecomendationModel> call, Response<LoanRecomendationModel> response) {
                UtileKit.dismisssSpinnerDialog();
                loanRecomendationModel = response.body();
                if (loanRecomendationModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
//                    recomendationDialog = RecomendationDialogFragment.newInstance(response.body());
//                    FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
//                    recomendationDialog.show(fm, "RecomendationPopup");

                   // addFragmenttoStack(RecomendationDialogFragment.newInstance(loanRecomendationModel));

                    addFragmenttoStack(HomeLoanSummary.newInstance(loanRecomendationModel));
                }

            }

            @Override
            public void onFailure(Call<LoanRecomendationModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
            }
        });

        // showRecomendation();
    }

    private boolean validateTenureValues() {
        boolean isValueInvalid = false;
        try {
            if (currentLoanValues[2] != null && currentLoanValues[3] != null) {
                if (Integer.parseInt(currentLoanValues[2]) <= Integer.parseInt(currentLoanValues[3])) {
                    isValueInvalid = true;
                    viewPager.setCurrentItem(currentLoanFragmentIndex);
                    currentLoanFragment.setInvalidTenureError();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValueInvalid;
    }

    private boolean checkForNullInNewLoan() {
        boolean isNullValuePresent = false;
        for (int i = 0; i < (newLoanMandatoryItemSize); i++) {
            try {
                if (newLoanValues[i] == null || newLoanValues[i].equalsIgnoreCase("")) {
                    viewPager.setCurrentItem(newLoanFragmentIndex);
                    newLoanFragment.setErrorNewLoanFragment(i);
                    isNullValuePresent = true;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        return isNullValuePresent;
    }

    private boolean checkForNullInCurrentLoan() {
        boolean isNullValuePresent = false;
        for (int i = 0; i < currentLoanMandatoryItemSize; i++) {
            try {
                if ((currentLoanValues[i] == null || currentLoanValues[i].equalsIgnoreCase(""))) {
                    isNullValuePresent = true;
                    viewPager.setCurrentItem(currentLoanFragmentIndex);
                    currentLoanFragment.setErrorCurrentLoan(i);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return isNullValuePresent;
    }

    private boolean checkForNullInDates() {
        boolean isNullValuePresent = false;

        emptyDates[0] = checkDateEmpty(loanStartDate);
        emptyDates[1] = checkDateEmpty(firstEmiPaidDate);
        emptyDates[2] = checkDateEmpty(lastEmiPaidDate);
        emptyDates[3] = checkDateEmpty(nextEmiDueDate);
        emptyDates[4] = checkDateEmpty(finalEmiDate);

        for (int i = 0; i < emptyDates.length; i++) {
            if (emptyDates[i] == true) {
                isNullValuePresent = true;
                currentLoanFragment.setErrorInEmiDialog(loanStartDateInFormat, firstEmiPaidDateInFormat, lastEmiPaidDateInFormat,
                        nextEmiDueDateInFormat, finalEmiDateInFormat, emptyDates);
                break;
            }
        }
        return isNullValuePresent;
    }

    private boolean checkDateEmpty(String date) {
        boolean isEmpty = false;
        if (date == null || date.equals("")) {
            isEmpty = true;
        }
        return isEmpty;
    }

//    private void showRecomendation() {
//        Toast.makeText(this, "on click", Toast.LENGTH_SHORT).show();
//    }

    private void setupViewPager(ViewPager viewPager) {
        if(adapter==null){
            adapter = new DesiproPagerAdapter(getChildFragmentManager());
            adapter.addFragment(currentLoanFragment, "Current Loan");
            adapter.addFragment(newLoanFragment, "New Loan");

           viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void sendValue(String value, int index) {
        currentLoanValues[index] = value;
        if (index == 0) {
            newLoanFragment.setOutstandingBalance(value);
        } else if (index == 3) {
            newLoanFragment.setBalanceTenure(value);
        }
        // Log.i("spcheck", "sendValue: "+currentLoanValues[index]);
    }

    @Override
    public void updateValudFromNewLoan(String value, int index) {
        newLoanValues[index] = value;

    }

    @Override
    public void updateAllDates(String loanStartDate, String firstEmiPaidDate, String lastEmiPaidDate, String nextEmiDueDate, String finalEmiDate) {

        storeDataInStandardFormaat(loanStartDate, firstEmiPaidDate, lastEmiPaidDate, nextEmiDueDate, finalEmiDate);
        this.loanStartDate = setDateFormat(loanStartDate);
        this.firstEmiPaidDate = setDateFormat(firstEmiPaidDate);
        this.lastEmiPaidDate = setDateFormat(lastEmiPaidDate);
        this.nextEmiDueDate = setDateFormat(nextEmiDueDate);
        this.finalEmiDate = setDateFormat(finalEmiDate);

    }

    private void storeDataInStandardFormaat(String loanStartDate, String firstEmiPaidDate, String lastEmiPaidDate, String nextEmiDueDate, String finalEmiDate) {
        loanStartDateInFormat = loanStartDate;
        firstEmiPaidDateInFormat = firstEmiPaidDate;
        lastEmiPaidDateInFormat = lastEmiPaidDate;
        nextEmiDueDateInFormat = nextEmiDueDate;
        finalEmiDateInFormat = finalEmiDate;
    }

    @Override
    public void updateOutStandingBalance(String balanceAmount) {

    }

    private String setDateFormat(String date) {
        String output = "";
        try {
            if (date.trim().length() != 0) {
                String dateAray[] = date.split("-");
                output = dateAray[2].concat("-").concat(dateAray[1].concat("-").concat(dateAray[0]));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
        return output;
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }

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
            case R.id.homeloan_switch_fab:
                if (!checkForNullInCurrentLoan() && !validateTenureValues() && !checkForNullInNewLoan()) {
                    try {
                        exitCharge = calculateEntryAndExitCharge(currentLoanMandatoryItemSize, currenLoanSize, currentLoanValues);
                        entryCharge = calculateEntryAndExitCharge(newLoanMandatoryItemSize, newLoanSize, newLoanValues);
                        Log.i(TAG, " exitCharge  " + exitCharge);
                        Log.i(TAG, "entryCharge " + entryCharge);
                        //exitCharge = (Integer.parseInt(UtileKit.getStringwithoutCurreny(currentLoanValues[5])) + Integer.parseInt(UtileKit.getStringwithoutCurreny(currentLoanValues[6]))+ Integer.parseInt(UtileKit.getStringwithoutCurreny(currentLoanValues[7])))+"";
                        //entryCharge = (Integer.parseInt(UtileKit.getStringwithoutCurreny(newLoanValues[4])) + Integer.parseInt(UtileKit.getStringwithoutCurreny(newLoanValues[5])) +
                        // Integer.parseInt(UtileKit.getStringwithoutCurreny(newLoanValues[6])) + Integer.parseInt(UtileKit.getStringwithoutCurreny(newLoanValues[7])))+ "";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    callRecomendationWebService();
                }

                break;

        }
    }

}

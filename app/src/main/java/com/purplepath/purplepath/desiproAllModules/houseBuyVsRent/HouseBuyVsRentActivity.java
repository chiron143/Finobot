package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.CrashExceptionHandler;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.interfaces.ActivityMethodsInterface;
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models.BuyVsRentModel;
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
 * Created by Pratheep.S
 */

public class HouseBuyVsRentActivity extends AppCompatActivity implements ActivityMethodsInterface, View.OnClickListener {
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    public Context mContext;

    private BuyVsRentModel buyVsRentModel;

    public boolean isResultShown=false;

    HashMap<String,String> buyValues,rentValues,homeValues;
    ArrayList<String> buyMandatoryKey=new ArrayList<>(Arrays.asList(
            "property_price","prop_appre","real_estate_tax","main_repair_annu","util_annu",
            "insurance","loan_req","status","year_to_posses"
    ));

    ArrayList<String> buyLoanYesMandatoryKey=new ArrayList<>(Arrays.asList("down_pay","loan_amt","loan_tenure",
            "loan_int_rate","mortage_pay","loan_prop_usage"));

    private String loan_req="Yes";

    ArrayList<String> rentMandatoryKey=new ArrayList<>(Arrays.asList("current_rent", "rent_sec_dep",
            "rent_esc", "rent_util", "rent_insurance"
    ));

    ArrayList<String> homeMandatoryKey=new ArrayList<>(Arrays.asList("gross_salary","oppur_cost","planned_occupation",
            "city_type","tax_slab"
    ));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_buy_vs_rent);
        Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(this,HouseBuyVsRentActivity.class));
        ButterKnife.bind(this);

        buyValues=new HashMap<>();
        rentValues=new HashMap<>();
        homeValues=new HashMap<>();

        mContext=this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container,new HouseInfoFragment());
            if (!isFinishing())
                fragmentTransaction.commitAllowingStateLoss();
        }

        //if(savedInstanceState==null) addFragmentToActivity(new HouseInfoFragment());

        mleftRelativeLayout = (RelativeLayout) findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) findViewById(R.id.relative_right_arrow);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (fragment != null ) {
                    if (fragment instanceof HouseInfoFragment) {
                        if(!ishomeFragmentValuesEmpty(fragment)) {
                            showViewPager();
                        }
                    }else if(fragment instanceof BuyVsRentViewPagerFragment ){
                        Log.i("spcheck", "onClick: "+buyValues.get("real_estate_tax"));
                        if(isBuyFragmentFieldsempty(fragment) && !isRentFragmentEmpty(fragment)){
                            callBuyVsRentService();
                        }else {
                            isResultShown=false;
                        }
                    }
                }
            }
        });
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        }catch (Exception e){}
    }

    private void callBuyVsRentService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls obj= ServiceGenerator.createService(WebServiceCalls.class);
        Log.i("spcheck", "callBuyVsRentService: city_type="+homeValues.get("city_type")+"\n \n gross_salary="+homeValues.get("gross_salary")
                +"\n tax_slab="+homeValues.get("tax_slab")+"\n oppur_cost="+homeValues.get("oppur_cost")+"\n planned_occupation="+homeValues.get("planned_occupation")
                +"\n loan_req="+buyValues.get("loan_req")+"\n property_price="+buyValues.get("property_price")
                +"\n status="+buyValues.get("status")+"\n year_to_posses="+buyValues.get("year_to_posses")+"\n prop_appre="+buyValues.get("prop_appre")+
                "mortage_pay="+buyValues.get("mortage_pay")+"\n eal_estate_tax="+
                buyValues.get("real_estate_tax")+"\n main_repair_annu="+buyValues.get("main_repair_annu")+"\n util_annu="+buyValues.get("util_annu")+"\n insurance="+
                buyValues.get("insurance")+"\n int_foregone_down_pay="+buyValues.get("int_foregone_down_pay")+"\n current_rent="+rentValues.get("current_rent")+"\n rent_sec_dep="+
                rentValues.get("rent_sec_dep")+"\n rent_esc="+rentValues.get("rent_esc")+"\n rent_util="+rentValues.get("rent_util")+"\n rent_insurance="+rentValues.get("rent_insurance")
                +"\n int_fgone_sec_dep_rate="+rentValues.get("int_fgone_sec_dep_rate")+"\n down_pay="+buyValues.get("down_pay")+"\n loan_amt="+buyValues.get("loan_amt")+"\n loan_tenure="+buyValues.get("loan_tenure")+
                "loan_int_rate="+buyValues.get("loan_int_rate")+"\n loan_prop_usage="+buyValues.get("loan_prop_usage")+
                "tax_24="+buyValues.get("tax_24")+"\n tax_80c="+buyValues.get("tax_80c"));

        Call<BuyVsRentModel> call=obj.getBuyVsRentservice(homeValues.get("city_type"),homeValues.get("gross_salary"),
         homeValues.get("tax_slab"),homeValues.get("oppur_cost"),homeValues.get("planned_occupation")
        ,buyValues.get("loan_req"),buyValues.get("property_price")
        ,buyValues.get("status"),buyValues.get("year_to_posses"),buyValues.get("prop_appre"),buyValues.get("mortage_pay"),
         buyValues.get("real_estate_tax"),buyValues.get("main_repair_annu"),buyValues.get("util_annu"),
         buyValues.get("insurance"),buyValues.get("int_foregone_down_pay"),rentValues.get("current_rent"),
         rentValues.get("rent_sec_dep"),rentValues.get("rent_esc"),rentValues.get("rent_util"),rentValues.get("rent_insurance")
        ,rentValues.get("int_fgone_sec_dep_rate"),buyValues.get("down_pay"),buyValues.get("loan_amt"),buyValues.get("loan_tenure"),
         buyValues.get("loan_int_rate"),buyValues.get("loan_prop_usage"),buyValues.get("tax_24"),buyValues.get("tax_80c"));
         call.enqueue(new Callback<BuyVsRentModel>() {
             @Override
             public void onResponse(Call<BuyVsRentModel> call, Response<BuyVsRentModel> response) {
                 UtileKit.dismisssSpinnerDialog();
                 buyVsRentModel=response.body();
                     if(buyVsRentModel.getStatus_code().equals(UtileKit.SUCCESSCODE)){
                     if(null!=buyVsRentModel.getData()){
                         addFragmentToActivity(ResultBuyVsRentFragment.newInstance(buyVsRentModel));
                         fab.setVisibility(View.GONE);
                         isResultShown=true;
                     }else {

                     }
                 }else if(buyVsRentModel.getStatus_code().equals(UtileKit.SUCCESS_OVERRIDE_CODE)){
                     if(buyVsRentModel.getData()!=null&&buyVsRentModel.getData().getMessage()!=null)
                         UtileKit.intitializeAlertDialog(buyVsRentModel.getData().getMessage().toString(),mContext);
                 }
             }
             @Override
             public void onFailure(Call<BuyVsRentModel> call, Throwable t) {
                 UtileKit.dismisssSpinnerDialog();
                 UtileKit.alertRetrofitExceptionDialog(mContext, t);
             }
         });
    }

    private boolean ishomeFragmentValuesEmpty(Fragment fragment) {
        String value=null;
        boolean isEmpty=false;
        for(int i=0;i<homeMandatoryKey.size();i++){
            value=homeValues.get(homeMandatoryKey.get(i));
            Log.i("spcheck", "homeMMandatoryFieldsEmpty:value="+value+"key="+homeMandatoryKey.get(i));
            if(value!=null && !value.equals("")){

            }else {
                isEmpty=true;
                ((HouseInfoFragment)fragment).setErrorHomeFragment(i);
            }
        }
        return isEmpty;
    }

    private boolean isRentFragmentEmpty(Fragment fragment) {
        String value=null;
        boolean isEmpty=false;
        for(int i=0;i<rentMandatoryKey.size();i++){
            value=rentValues.get(rentMandatoryKey.get(i));
            Log.i("spcheck", "rentMMandatoryFieldsEmpty:value="+value+"key="+rentMandatoryKey.get(i));
            if(value!=null && !value.equals("")){

            }else {
                isEmpty=true;
                ((BuyVsRentViewPagerFragment)fragment).setErrorInRentFragment(i);
            }
        }
        return isEmpty;
    }

    private boolean isBuyFragmentLoanFieldsEmpty(Fragment fragment) {

        return (isLoanRequired()) && isbuyMandatoryFieldsEmpty(fragment, buyLoanYesMandatoryKey, 1);
    }


    private boolean isLoanRequired() {
        if(UtileKit.validateObjectValues(buyValues.get("loan_req"))) loan_req=buyValues.get("loan_req");
        return loan_req.equalsIgnoreCase("Yes");
    }

    private boolean isbuyMandatoryFieldsEmpty(Fragment fragment,ArrayList keys,int code) {
        Log.i("spcheck", "isbuyMandatoryFieldsEmpty: code="+code);
        String value=null;
        boolean isEmpty=false;
        for(int i=0;i<keys.size();i++){
            value=buyValues.get(keys.get(i));
            Log.i("spcheck", "isbuyMandatoryFieldsEmpty:value="+value+"key="+keys.get(i));
            if(value!=null && !value.equals("")){

            }else {
                isEmpty=true;
                if(code==0)((BuyVsRentViewPagerFragment)fragment).setErrorInBuyFragment(i);
                else if (code==1) ((BuyVsRentViewPagerFragment)fragment).setErrorInBuyFragment(100+i);
            }
        }

        return isEmpty;
    }

    private boolean isBuyFragmentFieldsempty(Fragment fragment) {
        return !isbuyMandatoryFieldsEmpty(fragment,buyMandatoryKey,0)&&!isBuyFragmentLoanFieldsEmpty(fragment)
                &&!isYearsToPossesionEmpty(fragment);
    }

    private boolean isYearsToPossesionEmpty(Fragment fragment) {
        boolean value=false;
        if (buyValues.get("status").equalsIgnoreCase("Under Construction"))//? : ;
        {
            if(UtileKit.validateObjectValuesAndCheckZero(buyValues.get("year_to_posses"))){}
            else {
                ((BuyVsRentViewPagerFragment)fragment).setErrorInBuyFragment(8);
                value=true;
            }
        }
        return value;
    }


    /*private void addFragmentToActivity(Fragment fragment) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }
*/
    public void addFragmentToActivity(Fragment fragment) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            if (!isFinishing())
                fragmentTransaction.commitAllowingStateLoss();
    }


    @Override
    public void showViewPager() {
        addFragmentToActivity(new BuyVsRentViewPagerFragment());
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFAB() {
        fab.setVisibility(View.GONE);
    }

    @Override
    public void showFAB() {
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFragment(Fragment fragment) {
        addFragmentToActivity(fragment);

    }

    @Override
    public void updateValuesFromBuyFragment(String key, String value) {
     buyValues.put(key,value);
        Log.i("spcheck", "updateValuesFromBuyFragment: "+buyValues.get(key));
    }

    @Override
    public void updateValuesFromRentFragment(String key, String value) {
        rentValues.put(key,value);
        Log.i("spcheck", "updateValuesFromBuyFragment: "+rentValues.get(key));
    }

    @Override
    public void updateValuesFromHomeFragment(String key, String value) {
        homeValues.put(key,value);
        Log.i("spcheck", "updateValuesFromBuyFragment: key"+homeValues.get(key));
    }

    @Override
    public void onActivityBackPressed() {
        try {
            onBackPressed();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.relative_left_arrow:
                int count=getSupportFragmentManager().getBackStackEntryCount();
                if(count<1){
                    this.finish();
                }else if(count>=1){
                    onBackPressed();
                }

                break;
            case R.id.relative_center_home:
                Intent i=new Intent(this, HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }
    }
}

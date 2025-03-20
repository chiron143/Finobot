package com.purplepath.purplepath.autoinsurance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.autoinsurance.ChartView.AutoInsuranceAllChart;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.propertyinsurance.model.Motor_Proper_Health_Model;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.math.BigInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.R.id.vehicle_value;

/**
 * Created by pravinr on 8/1/17.
 */

public class AutoinsuranceSummary extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private ScrollView scrollview;

    Motor_Proper_Health_Model motor_proper_Health_model;

    private TextView errorTextview;

    private TextView mvehicle_value,minsurance_value;

    private TextView bike_car_count;

    private BigInteger vehile_total_value=BigInteger.ZERO;
    private BigInteger insurance_total_value=BigInteger.ZERO;

    @Override
    public void onAttach(Context context) {


        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        backPressedListener= (OnActivityBackPressedListener) getContext();
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_autoinsurance_summary, container, false);
        backPressedListener.setActionBarTitle("Auto Insurance");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        scrollview= view.findViewById(R.id.scrollview);
        errorTextview = view.findViewById(R.id.empty_text);

        bike_car_count= view.findViewById(R.id.bike_car_count);


        mvehicle_value= view.findViewById(vehicle_value);
        minsurance_value= view.findViewById(R.id.insurance_value);

        if(motor_proper_Health_model != null){
            getvaluefromservices(motor_proper_Health_model);
        }else{
            callGetMotorService();
        }


        return view;
    }
    public void callGetMotorService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Motor_Proper_Health_Model> call = webServiceObj.callGetPropertyService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Motor_Proper_Health_Model>() {
            @Override
            public void onResponse(Call<Motor_Proper_Health_Model> call, Response<Motor_Proper_Health_Model> response) {
                UtileKit.dismisssSpinnerDialog();
                motor_proper_Health_model = response.body();
               getvaluefromservices(motor_proper_Health_model);
            }
            @Override
            public void onFailure(Call<Motor_Proper_Health_Model> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void getvaluefromservices(Motor_Proper_Health_Model motor_proper_health_model) {
        if (motor_proper_health_model.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)){
            try {
            if(motor_proper_health_model.getData().getMotor_ins_plan()!=null){

                String str_bike_count=motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getOverall_bike_count();
                String str_car_count=motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getOverall_car_count();

                vehile_total_value=new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().
                        getVechicle_asset().getOverall_value());

                insurance_total_value=new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().
                        getVechicle_insurance().getOverall_value());


                if(str_bike_count.equalsIgnoreCase("0")&&str_car_count.equalsIgnoreCase("0")){
                    bike_car_count.setText("You dont have Two wheeler and Four wheeler");
                }
                else if(!str_bike_count.equalsIgnoreCase("0")&&str_car_count.equalsIgnoreCase("0")){
                    bike_car_count.setText(UtileKit.fromHtml("You have " +"<b>" +"<u>"+str_bike_count+"</u>" + "</b>" +" Two wheeler"));
                }
                else if(str_bike_count.equalsIgnoreCase("0")&&!str_car_count.equalsIgnoreCase("0")){
                    bike_car_count.setText(UtileKit.fromHtml("You have "  +"<b>" +"<u>"+str_car_count+"</u>" + "</b>"  +" Four wheeler"));
                }
                else {
                    bike_car_count.setText(UtileKit.fromHtml("You have " +"<b>" +"<u>"+str_bike_count+"</u>" + "</b>" +" Two wheeler and "+"<b>" +"<u>" + str_car_count+"</u>" + "</b>" +" Four wheeler"));
                }

                mvehicle_value.setText("₹ " + UtileKit.formatedNumbers(vehile_total_value));
                minsurance_value.setText("₹ " + UtileKit.formatedNumbers(insurance_total_value));

            }
        else {
            errorTextview.setVisibility(View.VISIBLE);
        }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    }



   /* private void getvaluefromservices(Motor_Proper_Health_Model motor_proper_Health_model) {
        if(motor_proper_Health_model.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
            try {
                if(null!= motor_proper_Health_model.getData().getProp_ins_plan()) {
                    if(UtileKit.validateObjectValues(motor_proper_Health_model.getData().getProp_ins_plan().getAsset_value() )&&
                            UtileKit.validateObjectValues(motor_proper_Health_model.getData().getProp_ins_plan().getAsset_value())) {
                        scrollview.setVisibility(View.VISIBLE);

                        BigInteger str_asset_value = (new BigInteger(motor_proper_Health_model.getData().getMotor_ins_plan().getAsset_value()));
                        BigInteger str_ins_value = (new BigInteger(motor_proper_Health_model.getData().getMotor_ins_plan().getIns_value()));
                        if (str_asset_value.equals("0") && str_ins_value.equals("0")) {
                            mins_value.setText("Your are not insured and you dont have any assets");
                        } else if (!str_asset_value.equals("0") && str_ins_value.equals("0")) {
                            mins_value.setText("Your are not insured ,you need to take insurance");
                        } else if (str_asset_value.equals("0") && !str_ins_value.equals("0")) {
                            mins_value.setText("You dont have any assets");
                        } else {
                            int res;
                            res = str_asset_value.compareTo(str_ins_value);

                            //Both values are equal
                            if (res == 0) {
                                mins_value.setText(str_ins_value + "Your already under insured");
                            }
                            //First Value is greater
                            else if (res == 1) {
                                BigInteger difference = str_asset_value.subtract(str_ins_value);
                                mins_value.setText(difference + "You need more insurance");
                            }
                            //Second value is greater
                            else if (res == -1) {
                                mins_value.setText(str_ins_value + "Your already under insured");
                            }

                        }
                    }
                }
                else {
                    scrollview.setVisibility(View.GONE);
                    errorTextview.setVisibility(View.VISIBLE);
                }
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_detail);
        MenuItem items=menu.findItem(R.id.menu_chart);
        // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_detail:

                try{
                    addFragmenttoStack(new AutoInsuranceDetails());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_chart:

                try{
                   // addFragmenttoStack(new AutoInsuranceCharts());

                    addFragmenttoStack(AutoInsuranceAllChart.newInstance(motor_proper_Health_model));

                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }

    }
}

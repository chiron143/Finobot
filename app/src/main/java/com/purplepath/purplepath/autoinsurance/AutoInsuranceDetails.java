package com.purplepath.purplepath.autoinsurance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.autoinsurance.ChartView.AutoInsuranceAllChart;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.propertyinsurance.model.Motor_Proper_Health_Model;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by pravinr on 11/6/17.
 */

public class AutoInsuranceDetails extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private Context mContext;

    private DefaultCurrencyTextView vehile_Overall_value,insurance_overall_value;
    Motor_Proper_Health_Model motor_proper_Health_model;
    private TextView errorTextview;
    private LinearLayout layout_vehicle_value,layout_insurance_value;




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
        View view=inflater.inflate(R.layout.fragment_autoinsurance_detail, container, false);
        backPressedListener.setActionBarTitle("Auto Insurance");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        vehile_Overall_value= view.findViewById(R.id.vehile_Overall_value);
        insurance_overall_value= view.findViewById(R.id.insurance_overall_value);

        layout_vehicle_value= view.findViewById(R.id.layout_vehicle_value);
        layout_insurance_value= view.findViewById(R.id.layout_insurance_value);

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
                    LayoutInflater inflater = LayoutInflater.from(mContext);

                    String str_vehile_overall=motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getOverall_value();
                    String str_ins_overall=motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().getOverall_value();

                    vehile_Overall_value.setText(str_vehile_overall);
                    insurance_overall_value.setText(str_ins_overall);

                    //vehile value
                    int vehile_length=motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().size();
                    for (int i=0;i<vehile_length;i++){
                        final View vehile_view = inflater.inflate(R.layout.house_content_single_item, null);
                        TextView txt_car_bike= vehile_view.findViewById(R.id.txt_land);
                        TextView car_bike_value= vehile_view.findViewById(R.id.land_value);

                        if(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(i).getLev2_name()==null)
                        {
                            txt_car_bike.setText(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(i).
                            getAsset_name()+ "(Others)");
                        }else {
                            txt_car_bike.setText(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(i).
                                    getAsset_name() + "(" +motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().
                                    get(i).getLev2_name()+ ")");
                        }
                        car_bike_value.setText(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(i).
                                getCurrent_value());

                        layout_vehicle_value.addView(vehile_view);
                    }
                    //vehile value

                    //insurance value
                    int ins_length=motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().getIns_by_motor_type().size();

                    for (int j=0;j<ins_length;j++){
                        final View ins_view = inflater.inflate(R.layout.house_content_single_item, null);

                        TextView txt_motor_typ= ins_view.findViewById(R.id.txt_land);
                        TextView cover_value= ins_view.findViewById(R.id.land_value);

                        if(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().
                                getIns_by_motor_type().get(j).getMotor_type()==null){
                            txt_motor_typ.setText(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().
                                    getIns_by_motor_type().get(j).getIns_sub_type()+ "(Others)");
                        }else {
                            txt_motor_typ.setText(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().
                                    getIns_by_motor_type().get(j).getIns_sub_type() + "(" +motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().
                                    getIns_by_motor_type().get(j).getMotor_type()+ ")");
                        }


                        cover_value.setText(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().
                                getIns_by_motor_type().get(j).getCover_availed());

                        layout_insurance_value.addView(ins_view);
                    }
                    //insurance value



                }

                else {
                    errorTextview.setVisibility(View.VISIBLE);
                }


            }catch (Exception e) {
                e.printStackTrace();
            }
            }
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
                    //addFragmenttoStack(new AutoInsuranceCharts());

                    addFragmenttoStack(AutoInsuranceAllChart.newInstance(motor_proper_Health_model));

                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            case R.id.menu_summary:

                try{
                    addFragmenttoStack(new AutoinsuranceSummary());
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

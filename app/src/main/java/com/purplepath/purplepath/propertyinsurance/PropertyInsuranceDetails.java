package com.purplepath.purplepath.propertyinsurance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
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

public class PropertyInsuranceDetails extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private Context mContext;
    private ScrollView scrollview;
    Motor_Proper_Health_Model motor_proper_Health_model;
    private TextView errorTextview;
    private DefaultCurrencyTextView house_Overall_value,rent_overall_value;

    private LinearLayout mlayout_house_value,mlayout_rent_content_value;

    private CardView card_own_house,card_rent_house;


    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_propertyinsurance_detail, container, false);
        backPressedListener.setActionBarTitle("Property Insurance");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        house_Overall_value= view.findViewById(R.id.house_Overall_value);
        rent_overall_value= view.findViewById(R.id.rent_overall_value);

        mlayout_house_value= view.findViewById(R.id.layout_house_value);
        mlayout_rent_content_value= view.findViewById(R.id.layout_rent_content_value);

        card_own_house= view.findViewById(R.id.card_own_house);
        card_rent_house= view.findViewById(R.id.card_rent_house);

        if(motor_proper_Health_model !=null){
            getvaluefromservices(motor_proper_Health_model);
        }else {
            callGetPropertyService();
        }

        return view;
    }


    public void callGetPropertyService() {
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
        if(motor_proper_Health_model.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
            try {
                if(null!= motor_proper_Health_model.getData().getProp_ins_plan()) {

                    LayoutInflater inflater = LayoutInflater.from(mContext);

                    String own_or_rent=motor_proper_health_model.getData().getProp_ins_plan().getIs_own_house();

                    String str_house_value=motor_proper_health_model.getData().getProp_ins_plan().getHouse_value().getOverall_value();
                    String str_house_content_value=motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().getOverall_value();
                    String str_house_insurance_coverage_value=motor_proper_health_model.getData().getProp_ins_plan().getInsurance().getOverall_value();

                    if(own_or_rent.equalsIgnoreCase("true")){

                        card_own_house.setVisibility(View.VISIBLE);
                        card_rent_house.setVisibility(View.VISIBLE);

                        house_Overall_value.setText(str_house_value);
                        rent_overall_value.setText(str_house_content_value);
                        //house value
                        int house_length=motor_proper_health_model.getData().getProp_ins_plan().getHouse_value().getHouse_val_by_cat().size();
                        for(int i=0;i<house_length;i++){
                            final View house_view = inflater.inflate(R.layout.house_content_single_item, null);
                           TextView txt_land= house_view.findViewById(R.id.txt_land);
                           TextView land_value= house_view.findViewById(R.id.land_value);

                            txt_land.setText(motor_proper_health_model.getData().getProp_ins_plan().getHouse_value().
                                    getHouse_val_by_cat().get(i).getLev2_name());
                            land_value.setText(motor_proper_health_model.getData().getProp_ins_plan().getHouse_value().
                                    getHouse_val_by_cat().get(i).getCurrent_value());

                            mlayout_house_value.addView(house_view);
                        }
                        //house value

                        //content value
                        int content_lenth=motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().getAssets().size();

                        if(motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().getAssets()!=null) {
                            for (int j = 0; j < content_lenth; j++) {
                                final View content_view = inflater.inflate(R.layout.house_content_single_item, null);

                                TextView txt_land = content_view.findViewById(R.id.txt_land);
                                TextView land_value = content_view.findViewById(R.id.land_value);

                                if (motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().
                                        getAssets().get(j).getLev2_name() == null) {
                                    txt_land.setText(motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().
                                            getAssets().get(j).getAsset_name() + "(Others)");
                                } else {
                                    txt_land.setText(motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().
                                            getAssets().get(j).getAsset_name() + "(" + motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().
                                            getAssets().get(j).getLev2_name() + ")");
                                }

                                land_value.setText(motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().getAssets().
                                        get(j).getCurrent_value());
                                mlayout_rent_content_value.addView(content_view);
                            }
                            //content value
                        }
                    }
                    else {
                        card_own_house.setVisibility(View.GONE);
                        card_rent_house.setVisibility(View.VISIBLE);

                        rent_overall_value.setText(str_house_content_value);

                        //content value
                        int content_lenth=motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().getAssets().size();
                        if(motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().getAssets()!=null){

                        for(int j=0;j<content_lenth;j++){
                            final View content_view = inflater.inflate(R.layout.house_content_single_item, null);

                            TextView txt_land= content_view.findViewById(R.id.txt_land);
                            TextView land_value= content_view.findViewById(R.id.land_value);

                            if(motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().
                                    getAssets().get(j).getLev2_name()==null){
                                txt_land.setText(motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().
                                        getAssets().get(j).getAsset_name()+"(Others)");
                            }else {
                            txt_land.setText(motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().
                                    getAssets().get(j).getAsset_name()+"("+motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().
                                    getAssets().get(j).getLev2_name()+")");
                            }


                            land_value.setText(motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().getAssets().
                                    get(j).getCurrent_value());
                            mlayout_rent_content_value.addView(content_view);
                        }
                        //content value

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
                    addFragmenttoStack(new PropertyInsuranceChart());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;


            case R.id.menu_summary:

                try{
                    addFragmenttoStack(new ProperyInsuranceSummary());
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

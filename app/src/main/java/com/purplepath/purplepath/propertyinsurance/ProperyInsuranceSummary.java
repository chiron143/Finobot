package com.purplepath.purplepath.propertyinsurance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by pravinr on 8/1/17.
 */

public class ProperyInsuranceSummary extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;


    private Context mContext;
    private ScrollView scrollview;
    Motor_Proper_Health_Model motor_proper_Health_model;
    private TextView errorTextview;
    private DefaultCurrencyTextView house_value,house_content_value,house_insurance_coverage_value,
            rent_content_value,rent_insurance_coverage_value;
    private CardView card_own_house,card_rent_house;
    private TextView txt_ownhouse,txt_rent_house;


    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_propertyinsurance_summary, container, false);
        backPressedListener.setActionBarTitle("Property Insurance");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        scrollview= view.findViewById(R.id.scrollview);
        errorTextview = view.findViewById( R.id.empty_text);

        house_value= view.findViewById(R.id.house_value);
        house_content_value= view.findViewById(R.id.house_content_value);
        house_insurance_coverage_value= view.findViewById(R.id.house_insurance_coverage_value);

        rent_content_value= view.findViewById(R.id.rent_content_value);
        rent_insurance_coverage_value= view.findViewById(R.id.rent_insurance_coverage_value);

        card_own_house= view.findViewById(R.id.card_own_house);
        card_rent_house= view.findViewById(R.id.card_rent_house);

        txt_ownhouse= view.findViewById(R.id.txt_ownhouse);
        txt_rent_house= view.findViewById(R.id.txt_rent_house);

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

                String own_or_rent=motor_proper_health_model.getData().getProp_ins_plan().getIs_own_house();

                String str_house_value=motor_proper_health_model.getData().getProp_ins_plan().getHouse_value().getOverall_value();
                String str_house_content_value=motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().getOverall_value();
                String str_house_insurance_coverage_value=motor_proper_health_model.getData().getProp_ins_plan().getInsurance().getOverall_value();

                if(own_or_rent.equalsIgnoreCase("true")){
                    card_own_house.setVisibility(View.VISIBLE);
                    card_rent_house.setVisibility(View.GONE);
                    txt_ownhouse.setVisibility(View.VISIBLE);
                    txt_ownhouse.setText("Your property residence is a Own house");
                    house_value.setText(str_house_value);
                    house_content_value.setText(str_house_content_value);
                    house_insurance_coverage_value.setText(str_house_insurance_coverage_value);

                }
                else {
                    card_rent_house.setVisibility(View.VISIBLE);
                    card_own_house.setVisibility(View.GONE);
                    txt_rent_house.setVisibility(View.VISIBLE);
                    txt_rent_house.setText("Your property residence is a Rent house");
                    rent_content_value.setText(str_house_content_value);
                    rent_insurance_coverage_value.setText(str_house_insurance_coverage_value);
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

   /*private void getvaluefromservices(Motor_Proper_Health_Model motor_proper_Health_model) {
        if(motor_proper_Health_model.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
            try {
                if(null!= motor_proper_Health_model.getData().getProp_ins_plan()) {
                    if(UtileKit.validateObjectValues(motor_proper_Health_model.getData().getProp_ins_plan().getAsset_value() )&&
                            UtileKit.validateObjectValues(motor_proper_Health_model.getData().getProp_ins_plan().getAsset_value())) {
                        scrollview.setVisibility(View.VISIBLE);
                        BigInteger str_asset_value = (new BigInteger(motor_proper_Health_model.getData().getProp_ins_plan().getAsset_value()));
                        BigInteger str_ins_value = (new BigInteger(motor_proper_Health_model.getData().getProp_ins_plan().getIns_value()));
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
                    addFragmenttoStack(new PropertyInsuranceDetails());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_chart:

                try{
                    addFragmenttoStack(new PropertyInsuranceChart());
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

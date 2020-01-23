package com.purplepath.purplepath.recommendation.recommendationTables;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.recommendation.model.RecommendData;
import com.purplepath.purplepath.recommendation.recommendationAllViews.RecommendationCashManagement;
import com.purplepath.purplepath.recommendation.recommendationAllViews.RecommendationEmergency;
import com.purplepath.purplepath.recommendation.recommendationAllViews.RecommendationGoals;
import com.purplepath.purplepath.recommendation.recommendationAllViews.RecommendationHealthInsurance;
import com.purplepath.purplepath.recommendation.recommendationAllViews.RecommendationLifeInsurance;
import com.purplepath.purplepath.recommendation.recommendationAllViews.RecommendationMotor;
import com.purplepath.purplepath.recommendation.recommendationAllViews.RecommendationNetworth;
import com.purplepath.purplepath.recommendation.recommendationAllViews.RecommendationProperty;
import com.purplepath.purplepath.recommendation.recommendationAllViews.RecommendationSavingsInvestment;
import com.purplepath.purplepath.recommendation.recommendationAllViews.RecommendationTexation;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.key;

/**
 * Created by Murugesan on 2/23/18.
 */

public class RecommendationTableViews extends BaseFragment implements View.OnClickListener  {

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    private ScrollView scrollview;

    RecommendData recommendData;

    private RelativeLayout layout_networth,layout_cash,layout_goals,layout_retirement,layout_taxation,layout_savings,
                           layout_emergency,layout_life_ins,layout_health,layout_auto,layout_property;

    private ImageView networth_img,cash_img,goal_img,retite_img,taxation_img,saving_img,
                      emer_img,life_img,health_img,motor_img,property_img;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommendation_tables, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallBackListener.setActionBarTitle("My Action Plan");

        scrollview= view.findViewById(R.id.scrollview);

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        layout_networth= view.findViewById(R.id.layout_networth);
        layout_networth.setOnClickListener(this);

        layout_cash= view.findViewById(R.id.layout_cash);
        layout_cash.setOnClickListener(this);

        layout_goals= view.findViewById(R.id.layout_goals);
        layout_goals.setOnClickListener(this);

        layout_retirement= view.findViewById(R.id.layout_retirement);
        layout_retirement.setOnClickListener(this);

        layout_taxation= view.findViewById(R.id.layout_taxation);
        layout_taxation.setOnClickListener(this);

        layout_savings= view.findViewById(R.id.layout_savings);
        layout_savings.setOnClickListener(this);

        layout_emergency= view.findViewById(R.id.layout_emergency);
        layout_emergency.setOnClickListener(this);

        layout_life_ins= view.findViewById(R.id.layout_life_ins);
        layout_life_ins.setOnClickListener(this);

        layout_health= view.findViewById(R.id.layout_health);
        layout_health.setOnClickListener(this);

        layout_auto= view.findViewById(R.id.layout_auto);
        layout_auto.setOnClickListener(this);

        layout_property= view.findViewById(R.id.layout_property);
        layout_property.setOnClickListener(this);

        networth_img= view.findViewById(R.id.networth_img);
        cash_img= view.findViewById(R.id.cash_img);
        goal_img= view.findViewById(R.id.goal_img);
        retite_img= view.findViewById(R.id.retite_img);

        taxation_img= view.findViewById(R.id.taxation_img);
        saving_img= view.findViewById(R.id.saving_img);
        emer_img= view.findViewById(R.id.emer_img);
        life_img= view.findViewById(R.id.life_img);

        health_img= view.findViewById(R.id.health_img);
        motor_img= view.findViewById(R.id.motor_img);
        property_img= view.findViewById(R.id.property_img);


        setTint(networth_img,"Networth",R.drawable.ic_networth,R.drawable.ic_networth_tint);
        setTint(cash_img,"Cash Management",R.drawable.ic_cash_management_recommend,R.drawable.ic_cash_management_recommend_tint);
        setTint(goal_img,"Goals",R.drawable.ic_goals_recommend,R.drawable.ic_goal_tint);

        setTint(saving_img,"Savings and Investments",R.drawable.ic_save_investment,R.drawable.ic_save_investment_tint);
        setTint(emer_img,"Emergency Fund Plan",R.drawable.ic_emergency_fund_plan,R.drawable.ic_emergency_fund_plan_tint);
        setTint(life_img,"Life Insurance Plan",R.drawable.ic_life_lnsurances,R.drawable.ic_life_lnsurances_tint);
        setTint(health_img,"Health Insurance Plan",R.drawable.ic_health_lnsurance_recommend,R.drawable.ic_health_lnsurance_recommend_tint);
        setTint(motor_img,"Motor Insurance Plan",R.drawable.ic_automobile_lnsurance,R.drawable.ic_automobile_lnsurance_tint);
        setTint(property_img,"Property Insurance Plan",R.drawable.ic_property_lnsurance,R.drawable.ic_property_lnsurance_tint);

        callAllInsuranceService();
    }

   void setTint(ImageView image,String key,int without_tint_image,int tint_image){

       if(UtileKit.getPersistedPurplePathBoolPref(key)) {
           image.setImageResource(without_tint_image);
       }else {
           image.setImageResource(tint_image);
       }
   }

    private void callAllInsuranceService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<RecommendData> call = webServiceObj.triggerRecommendationService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<RecommendData>() {
            @Override
            public void onResponse(Call<RecommendData> call, Response<RecommendData> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    recommendData = response.body();

                    if (recommendData.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {


                    }
                    else{

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RecommendData> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }







    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.layout_networth:
            {
                if(UtileKit.getPersistedPurplePathBoolPref("Networth")) {
                    addFragmenttoStack(new RecommendationNetworth());
                } else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
                }
            }
            break;

            case R.id.layout_cash:
            {
                if(UtileKit.getPersistedPurplePathBoolPref("Cash Management")) {
                    addFragmenttoStack(new RecommendationCashManagement());
                }else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
                }
            }
            break;

            case R.id.layout_goals:
            {
                if(UtileKit.getPersistedPurplePathBoolPref("Goals")) {
                    addFragmenttoStack(new RecommendationGoals());
                }else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
                }
            }
            break;

            case R.id.layout_retirement:
            {

            }
            break;
            case R.id.layout_taxation:
            {
                addFragmenttoStack(new RecommendationTexation());
            }
            break;

            case R.id.layout_savings:
            {
                if(UtileKit.getPersistedPurplePathBoolPref("Savings and Investments")) {
                    addFragmenttoStack(new RecommendationSavingsInvestment());
                }
                else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
                }
            }
            break;

            case R.id.layout_emergency:
            {
                if(UtileKit.getPersistedPurplePathBoolPref("Emergency Fund Plan")) {
                    addFragmenttoStack(RecommendationEmergency.newInstance(recommendData));
                }
                else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
                }
            }
            break;
            case R.id.layout_life_ins:
            {
                if(UtileKit.getPersistedPurplePathBoolPref("Life Insurance Plan")) {
                    addFragmenttoStack(RecommendationLifeInsurance.newInstance(recommendData));
                }else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
                }
            }
            break;
            case R.id.layout_health:
            {
                if(UtileKit.getPersistedPurplePathBoolPref("Health Insurance Plan")) {
                    addFragmenttoStack(RecommendationHealthInsurance.newInstance(recommendData));
                } else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
                }
            }
            break;
            case R.id.layout_property:
            {
                if(UtileKit.getPersistedPurplePathBoolPref("Property Insurance Plan")) {
                    addFragmenttoStack(RecommendationProperty.newInstance(recommendData));
                } else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
                }
            }
            break;
            case R.id.layout_auto:
            {
                if(UtileKit.getPersistedPurplePathBoolPref("Motor Insurance Plan")) {
                    addFragmenttoStack(RecommendationMotor.newInstance(recommendData));
                } else {
                    UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
                }
            }
            break;

            case R.id.relative_left_arrow:
            {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            break;

        }
    }
}

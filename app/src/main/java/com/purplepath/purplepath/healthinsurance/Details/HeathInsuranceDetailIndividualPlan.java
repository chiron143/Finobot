package com.purplepath.purplepath.healthinsurance.Details;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.propertyinsurance.model.Motor_Proper_Health_Model;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import java.math.BigInteger;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pravinr on 2/20/18.
 */

public class HeathInsuranceDetailIndividualPlan extends BaseFragment implements View.OnClickListener {


    Context mContext;
    TextView errorTextview;

    Motor_Proper_Health_Model motor_Proper_Health_Model;

    private ScrollView layout_Scroll;

    private ArrayList<String> familyMemberNames;

    private LinearLayout checkboxLayout;
    private  ArrayList<CheckBox>  userNameCheckBox=new ArrayList<>();

    private AddFamilyDetailModel addFamilyDetailModel;
    private BigInteger individual_suggessted;

    private BigInteger total_rider=BigInteger.ZERO;
    private BigInteger total_topup=BigInteger.ZERO;
    private BigInteger total_super_topup=BigInteger.ZERO;
    private BigInteger total_basic_plan=BigInteger.ZERO;
    private BigInteger total_add_all;
    private BigInteger total_sub_all;
    private BigInteger total_coverage_subract;
    private BigInteger total_coverage = BigInteger.ZERO;


    private BigInteger employer_total_rider=BigInteger.ZERO;
    private BigInteger employer_total_topup=BigInteger.ZERO;
    private BigInteger employer_total_super_topup=BigInteger.ZERO;
    private BigInteger employer_total_basic_plan=BigInteger.ZERO;
    private BigInteger employer_total_add_all;
    private BigInteger employer_total_sub_all;

    private BigInteger personally_total_rider=BigInteger.ZERO;
    private BigInteger personally_total_topup=BigInteger.ZERO;
    private BigInteger personally_total_super_topup=BigInteger.ZERO;
    private BigInteger personally_total_basic_plan=BigInteger.ZERO;
    private BigInteger personally_total_add_all;
    private BigInteger personally_total_sub_all;
    private String mPlan_type="";

    private TextView mSuggessted;

    private TextView mbasic_plan_value,mtop_up_value,msuper_top_up_value,mrider_value,mrecommended_value;

    private TextView memp_basic_plan_value,memp_top_up_value,memp_super_top_up_value,memp_rider_value,memp_recommended_value;

    private TextView mper_basic_plan_value,mper_top_up_value,mper_super_top_up_value,mper_rider_value,mper_recommended_value;



    public static HeathInsuranceDetailIndividualPlan newInstance(Motor_Proper_Health_Model motor_proper_health_model) {
        Bundle args = new Bundle();
        args.putSerializable("motor_Proper_Health_Model",motor_proper_health_model);
        HeathInsuranceDetailIndividualPlan fragment = new HeathInsuranceDetailIndividualPlan();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View individualview=inflater.inflate(R.layout.fragment_individual_detail_healthplan, container, false);

        errorTextview = individualview.findViewById(R.id.empty_chart_display);

        layout_Scroll= individualview.findViewById(R.id.layout_Scroll);

        checkboxLayout= individualview.findViewById(R.id.check_group1Id);


        mSuggessted = individualview.findViewById(R.id.suggessted_value);


        mbasic_plan_value = individualview.findViewById(R.id.basic_plan_value);
        mtop_up_value = individualview.findViewById(R.id.top_up_value);
        msuper_top_up_value = individualview.findViewById(R.id.super_top_up_value);
        mrider_value = individualview.findViewById(R.id.rider_value);
        mrecommended_value = individualview.findViewById(R.id.recommended_value);

        memp_basic_plan_value = individualview.findViewById(R.id.emp_basic_plan_value);
        memp_top_up_value = individualview.findViewById(R.id.emp_top_up_value);
        memp_super_top_up_value = individualview.findViewById(R.id.emp_super_top_up_value);
        memp_rider_value = individualview.findViewById(R.id.emp_rider_value);
        memp_recommended_value = individualview.findViewById(R.id.emp_recommended_value);

        mper_basic_plan_value = individualview.findViewById(R.id.per_basic_plan_value);
        mper_top_up_value = individualview.findViewById(R.id.per_top_up_value);
        mper_super_top_up_value = individualview.findViewById(R.id.per_super_top_up_value);
        mper_rider_value = individualview.findViewById(R.id.per_rider_value);
        mper_recommended_value = individualview.findViewById(R.id.per_recommended_value);


        if(null!=getArguments())
        {
            if(getArguments().containsKey("motor_Proper_Health_Model"))
                motor_Proper_Health_Model = (Motor_Proper_Health_Model) getArguments().getSerializable("motor_Proper_Health_Model");


        }
        callGetFamilyDetail(UtileKit.getPersistedPurplePathPref("user_id"));
        return individualview;
    }





    public void callGetFamilyDetail(String userId) {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddFamilyDetailModel> call = webServiceObj.callFamilyDetailsService(userId);
        call.enqueue(new Callback<AddFamilyDetailModel>() {
            @Override
            public void onResponse(Call<AddFamilyDetailModel> call, Response<AddFamilyDetailModel> response) {
                UtileKit.dismisssSpinnerDialog();
                addFamilyDetailModel = response.body();
                if (addFamilyDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {


                    if (null != addFamilyDetailModel.getData().getFamily_details()) {
                        int size = addFamilyDetailModel.getData().getFamily_details().size();
                        familyMemberNames = new ArrayList<String>();
                        familyMemberNames.add(UtileKit.getPersistedPurplePathPref("name_services", null));
                        for (int i = 0; i < size; i++) {
                            if (addFamilyDetailModel.getData().getFamily_details().get(i) != null) {

                                if (addFamilyDetailModel.getData().getFamily_details().get(i).getName().equalsIgnoreCase("")) {
                                    familyMemberNames.add(UtileKit.getPersistedPurplePathPref("name_services", null));
                                    Log.d("","familyMemberNames"+familyMemberNames);

                                } else {
                                    familyMemberNames.add(addFamilyDetailModel.getData().getFamily_details().get(i).getName());

                                }
                            }
                        }
                        AddCheckBoxView(familyMemberNames);
                    }
                } else {

                }
            }
            @Override
            public void onFailure(Call<AddFamilyDetailModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }





    private void AddCheckBoxView(ArrayList<String> xData) {
        try {
            checkboxLayout.removeAllViews();
            for (int i = 0; i < xData.size(); i++) {
                LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout parent_layout = new LinearLayout(mContext);
                parent_layout.setWeightSum(2);
                parent_layout.setOrientation(LinearLayout.HORIZONTAL);
                parent_param_layout.setMargins(10, 0, 0, 10);
                parent_layout.setLayoutParams(parent_param_layout);

                LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parms_left_layout.weight = 1F;
                LinearLayout left_layout = new LinearLayout(mContext);
                left_layout.setOrientation(LinearLayout.HORIZONTAL);
                left_layout.setGravity(Gravity.LEFT);
                left_layout.setLayoutParams(parms_left_layout);

                userNameCheckBox.add(new CheckBox(mContext));
                userNameCheckBox.get(i).setId(i);
                UtileKit.setTextAppearance(mContext, android.R.style.TextAppearance_Small, userNameCheckBox.get(i));
                userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        int position = compoundButton.getId();
                        checkBoxOnClick(position, b);
                    }
                });
                userNameCheckBox.get(i).setText(xData.get(i));
                userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.app_text_color_gray));
                left_layout.addView(userNameCheckBox.get(i));
                i++;
                if (xData.size() == i) {
                    parent_layout.addView(left_layout);
                    checkboxLayout.addView(parent_layout);
                    break;
                }
                LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parms_right_layout.weight = 1F;
                LinearLayout right_layout = new LinearLayout(mContext);
                right_layout.setOrientation(LinearLayout.HORIZONTAL);
                right_layout.setGravity(Gravity.LEFT);
                parent_param_layout.setMargins(10, 0, 0, 10);
                right_layout.setLayoutParams(parms_right_layout);

                userNameCheckBox.add(new CheckBox(mContext));
                userNameCheckBox.get(i).setId(i);
                UtileKit.setTextAppearance(mContext, android.R.style.TextAppearance_Small, userNameCheckBox.get(i));
                userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        int position = compoundButton.getId();
                        checkBoxOnClick(position, b);
                    }
                });
                userNameCheckBox.get(i).setText(xData.get(i));
                userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.app_text_color_gray));
                right_layout.addView(userNameCheckBox.get(i));

                parent_layout.addView(right_layout);
                parent_layout.addView(left_layout);
                checkboxLayout.addView(parent_layout);
            }
            if (!userNameCheckBox.isEmpty())
                userNameCheckBox.get(0).setChecked(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    private void checkBoxOnClick(int position, boolean b) {

        if(b){
            /**
             * family Details
             */
            //  Log.e("family","NAme"+addFamilyDetailModel.getData().getFamily_details().get(position-1).getId());
            for (int i=0;i<userNameCheckBox.size();i++){
                if(i!=position)
                    userNameCheckBox.get(i).setChecked(false);
            }
            if(position==0){
                addSingleUserChart(position,"0");
            }
            else {
                addSingleUserChart(position,addFamilyDetailModel.getData().getFamily_details().get(position-1).getId());
                Log.e("family","NAme"+addFamilyDetailModel.getData().getFamily_details().get(position-1).getId());
            }

        }
    }

    private void addSingleUserChart(int position,String familId) {


        if (motor_Proper_Health_Model.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

            try{
                if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().getSuggested()!=null) {


                    total_add_all=BigInteger.ZERO;
                    total_sub_all=BigInteger.ZERO;
                    total_rider=BigInteger.ZERO;
                    total_topup=BigInteger.ZERO;
                    total_super_topup=BigInteger.ZERO;
                    total_basic_plan=BigInteger.ZERO;
                    total_coverage=BigInteger.ZERO;

                    employer_total_rider=BigInteger.ZERO;
                    employer_total_topup=BigInteger.ZERO;
                    employer_total_super_topup=BigInteger.ZERO;
                    employer_total_basic_plan=BigInteger.ZERO;
                    employer_total_add_all=BigInteger.ZERO;
                    employer_total_sub_all=BigInteger.ZERO;

                    personally_total_rider=BigInteger.ZERO;
                    personally_total_topup=BigInteger.ZERO;
                    personally_total_super_topup=BigInteger.ZERO;
                    personally_total_basic_plan=BigInteger.ZERO;
                    personally_total_add_all=BigInteger.ZERO;
                    personally_total_sub_all=BigInteger.ZERO;

                    int lenth_sugg = motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                            getSuggested().size();

                    for (int i = 0; i < lenth_sugg; i++) {

                        //Compare family id two service
                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().getSuggested().
                                get(i).getFamily_id().equalsIgnoreCase(familId)) {
                            //Individual suggested bar
                            individual_suggessted = (new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().
                                    getIndividual_health_plan().getSuggested().get(i).getCover()));
                        }
                    }


                    employer_total_rider=BigInteger.ZERO;
                    employer_total_topup=BigInteger.ZERO;
                    employer_total_super_topup=BigInteger.ZERO;
                    employer_total_basic_plan=BigInteger.ZERO;
                    employer_total_add_all=BigInteger.ZERO;
                    employer_total_sub_all=BigInteger.ZERO;

                    personally_total_rider=BigInteger.ZERO;
                    personally_total_topup=BigInteger.ZERO;
                    personally_total_super_topup=BigInteger.ZERO;
                    personally_total_basic_plan=BigInteger.ZERO;
                    personally_total_add_all=BigInteger.ZERO;
                    personally_total_sub_all=BigInteger.ZERO;
                    total_coverage=BigInteger.ZERO;

                    int lenth_sugg_aval = motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                            getAvailable().size();


                    for (int k = 0; k < lenth_sugg_aval; k++) {

                        //Sub Insurance find ins_product_categories BASIC,TOP,SUPER,RIDER-RECOMMENTED
                        int sub_length=motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                getAvailable().get(k).getSub_insur().size();

                        //individual coverage minus BASIC,TOP,SUPER,RIDER
                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable().
                                get(k).getFamily_id().equalsIgnoreCase(familId)) {
                            total_coverage = total_coverage.add(new BigInteger(motor_Proper_Health_Model.getData().
                                    getHealth_ins_plan().
                                    getIndividual_health_plan().getAvailable().get(k).getCoverage()));
                            Log.d("", "total_coverage" + total_coverage);
                        }


                        mPlan_type=motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable().
                                get(k).getPlan_type();
                        if(mPlan_type.equalsIgnoreCase("Employer Provided")){

                            for (int j=0;j<sub_length;j++){

                                if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable().
                                        get(k).getFamily_id().equalsIgnoreCase(familId)) {

                                    //Sub Insurance find ins_product_categories BASIC,TOP,SUPER,RIDER-RECOMMENTED
                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Rider")) {
                                            total_rider = total_rider.add(new BigInteger(motor_Proper_Health_Model.getData().
                                                    getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("", "total_rider" + total_rider);
                                        }
                                    }

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Top Up")) {
                                            total_topup = total_topup.add(new BigInteger(motor_Proper_Health_Model.getData().
                                                    getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("", "total_topup" + total_topup);
                                        }
                                    }

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Super Top Up")) {
                                            total_super_topup = total_super_topup.add(new BigInteger(motor_Proper_Health_Model.getData().
                                                    getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("", "total_super_topup" + total_super_topup);
                                        }
                                    }

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Basic Plan")) {
                                            total_basic_plan = total_basic_plan.add(new BigInteger(motor_Proper_Health_Model.getData().
                                                    getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("", "total_basic_plan" + total_basic_plan);
                                        }
                                    }

                                    /**
                                     * employer provided
                                     */


                                    if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()){
                                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Rider")){
                                            employer_total_rider=employer_total_rider.add(new BigInteger(motor_Proper_Health_Model.
                                                    getData().getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("","employer_total_rider"+employer_total_rider);
                                        }
                                    }

                                    if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()){
                                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Top Up")){
                                            employer_total_topup=employer_total_topup.add(new BigInteger(motor_Proper_Health_Model.
                                                    getData().getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("","employer_total_topup"+employer_total_topup);
                                        }
                                    }

                                    if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Super Top Up")) {
                                            employer_total_super_topup = employer_total_super_topup.add(new BigInteger(motor_Proper_Health_Model.
                                                    getData().getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("", "employer_total_super_topup" + employer_total_super_topup);
                                        }
                                    }

                                    if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()){
                                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Basic Plan")){
                                            employer_total_basic_plan=employer_total_basic_plan.add(new BigInteger(motor_Proper_Health_Model.
                                                    getData().getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("","employer_total_basic_plan"+employer_total_basic_plan);
                                        }
                                    }
                                    /**
                                     * personally subscribed
                                     */


                                }

                            }
                        } else if(mPlan_type.equalsIgnoreCase("Personally Subscribed")){

                            for (int j=0;j<sub_length;j++){

                                if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable().
                                        get(k).getFamily_id().equalsIgnoreCase(familId)) {

                                    //Sub Insurance find ins_product_categories BASIC,TOP,SUPER,RIDER-RECOMMENTED
                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Rider")) {
                                            total_rider = total_rider.add(new BigInteger(motor_Proper_Health_Model.getData().
                                                    getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("", "total_rider" + total_rider);
                                        }
                                    }

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Top Up")) {
                                            total_topup = total_topup.add(new BigInteger(motor_Proper_Health_Model.getData().
                                                    getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("", "total_topup" + total_topup);
                                        }
                                    }

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Super Top Up")) {
                                            total_super_topup = total_super_topup.add(new BigInteger(motor_Proper_Health_Model.getData().
                                                    getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("", "total_super_topup" + total_super_topup);
                                        }
                                    }

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Basic Plan")) {
                                            total_basic_plan = total_basic_plan.add(new BigInteger(motor_Proper_Health_Model.getData().
                                                    getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("", "total_basic_plan" + total_basic_plan);
                                        }
                                    }

                                    /**
                                     * employer provided
                                     */



                                    /**
                                     * personally subscribed
                                     */

                                    if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()){
                                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Rider")){
                                            personally_total_rider=personally_total_rider.add(new BigInteger(motor_Proper_Health_Model.
                                                    getData().getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("","personally_total_rider"+personally_total_rider);
                                        }
                                    }

                                    if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()){
                                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Top Up")){
                                            personally_total_topup=employer_total_topup.add(new BigInteger(motor_Proper_Health_Model.
                                                    getData().getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("","personally_total_topup"+personally_total_topup);
                                        }
                                    }

                                    if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Super Top Up")) {
                                            personally_total_super_topup = personally_total_super_topup.add(new BigInteger(motor_Proper_Health_Model.
                                                    getData().getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("", "personally_total_super_topup" + personally_total_super_topup);
                                        }
                                    }

                                    if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                            getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()){
                                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Basic Plan")){
                                            personally_total_basic_plan=personally_total_basic_plan.add(new BigInteger(motor_Proper_Health_Model.
                                                    getData().getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                            Log.d("","personally_total_basic_plan"+personally_total_basic_plan);
                                        }
                                    }




                                }

                            }

                        }



                    }


                    total_add_all=total_rider.add(total_topup).add(total_super_topup).add(total_basic_plan);
                    total_sub_all=individual_suggessted.subtract(total_add_all);

                    total_coverage_subract=total_coverage.subtract(total_add_all);

                    employer_total_add_all=employer_total_rider.add(employer_total_topup).add(employer_total_super_topup).add(employer_total_basic_plan);
                    employer_total_sub_all=individual_suggessted.subtract(employer_total_add_all);

                    personally_total_add_all=personally_total_rider.add(personally_total_topup).add(personally_total_super_topup).add(personally_total_basic_plan);
                    personally_total_sub_all=individual_suggessted.subtract(personally_total_add_all);


                    if (individual_suggessted.equals(0)) {
                        errorTextview.setVisibility(View.VISIBLE);
                        layout_Scroll.setVisibility(View.GONE);
                    } else {
                        /**
                         * 0th suggested bar
                         */

                        mSuggessted.setText("₹ "+String.valueOf(individual_suggessted));

                        /**
                         * 1st Barchart topup,supertopup,basic,rider
                         */

                        mbasic_plan_value.setText("₹ "+String.valueOf(total_basic_plan));

                        mtop_up_value.setText("₹ "+String.valueOf(total_topup));

                        msuper_top_up_value.setText("₹ "+String.valueOf(total_super_topup));

                        mrider_value.setText("₹ "+String.valueOf(total_rider));

                        mrecommended_value.setText("₹ "+String.valueOf(total_sub_all));

                        /**
                         * 2nd bar employer bar
                         */

                        memp_basic_plan_value.setText("₹ "+String.valueOf(employer_total_basic_plan));

                        memp_top_up_value.setText("₹ "+String.valueOf(employer_total_topup));

                        memp_super_top_up_value.setText("₹ "+String.valueOf(employer_total_super_topup));

                        memp_rider_value.setText("₹ "+String.valueOf(employer_total_rider));

                        memp_recommended_value.setText("₹ "+String.valueOf(employer_total_sub_all));


                        /**
                         * 3rd bar Personally provider
                         */

                        mper_basic_plan_value.setText("₹ "+String.valueOf(personally_total_basic_plan));

                        mper_top_up_value.setText("₹ "+String.valueOf(personally_total_topup));

                        mper_super_top_up_value.setText("₹ "+String.valueOf(personally_total_super_topup));

                        mper_rider_value.setText("₹ "+String.valueOf(personally_total_rider));

                        mper_recommended_value.setText("₹ "+String.valueOf(personally_total_sub_all));

                    }

                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else {

        }
    }





    @Override
    public void onClick(View v) {

    }
}

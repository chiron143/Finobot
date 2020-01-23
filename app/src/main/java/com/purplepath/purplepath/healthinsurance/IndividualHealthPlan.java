package com.purplepath.purplepath.healthinsurance;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.finobot.finobot.activity.HomePageActivity;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.purplepath.purplepath.apputiles.ChartValueFormatter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.propertyinsurance.model.Motor_Proper_Health_Model;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.CamcorderProfile.get;
import static com.finobot.finobot.R.id.scrolllinearlayout;
import static com.finobot.finobot.R.id.titleLayout;


/**
 * Created by pravinr on 11/7/17.
 */

public class IndividualHealthPlan extends BaseFragment implements View.OnClickListener {


    Context mContext;
    TextView errorTextview;
    private LinearLayout parentView;
    private HorizontalBarChart individual_chart;
    Motor_Proper_Health_Model motor_Proper_Health_Model;
    private ArrayList<BarEntry> BARENTRYHORIZONTAL ;
    private ArrayList<Float> unitCount ;
    private Typeface tf;
    private BarDataSet BardatasetHorizontal ;
    private ScrollView layout_Scroll;

    private ArrayList<String> familyMemberNames;

    private LinearLayout checkboxLayout;
    private  ArrayList<CheckBox>  userNameCheckBox=new ArrayList<>();

    private AddFamilyDetailModel addFamilyDetailModel;
    private BigInteger individual_suggessted;
    ArrayList<String> xDatadetailscolor = new ArrayList<String>();
    ArrayList<String> xDatadetailsData = new ArrayList<String>();

    float[]  xBARENTRYPERSONALLY=null;
    float[]  xBARENTRYEMPLOYER=null;
    float[]  xBARENTRYCOVERAGE=null;
    float[]  xBARENTRYSUGGESSTED=null;

    int [] colour=new int[17];

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

    private BigInteger recommend=BigInteger.ZERO;

    public static IndividualHealthPlan newInstance(Motor_Proper_Health_Model motor_proper_health_model) {
        Bundle args = new Bundle();
        args.putSerializable("motor_Proper_Health_Model",motor_proper_health_model);
        IndividualHealthPlan fragment = new IndividualHealthPlan();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View individualview=inflater.inflate(R.layout.fragment_individual_healthplan, container, false);
        errorTextview = individualview.findViewById(R.id.errorTextview);
        individual_chart = individualview.findViewById(R.id.individual_chart);
        parentView= individualview.findViewById(R.id.parentViewId);
        layout_Scroll= individualview.findViewById(R.id.layout_Scroll);

        checkboxLayout= individualview.findViewById(R.id.check_group1Id);


        BARENTRYHORIZONTAL = new ArrayList<>();
        unitCount = new ArrayList<>();
        tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        XAxis xl = individual_chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);


        YAxis yl = individual_chart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawLabels(false);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.1f);
        yl.setStartAtZero(false);
        YAxis yr = individual_chart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setStartAtZero(false);
        yr.setValueFormatter(new ChartValueFormatter());

        Legend l = individual_chart.getLegend();
        l.setEnabled(false);
        l.setTextSize(8 * getResources().getDisplayMetrics().density);
        l.setFormSize(15f);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(15f);

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

                    BARENTRYHORIZONTAL = new ArrayList<>();
                    xDatadetailsData= new ArrayList<>();
                    xDatadetailscolor = new ArrayList<>();

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

                    recommend=BigInteger.ZERO;

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
                            int sub_length = motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                    getAvailable().get(k).getSub_insur().size();

                            //individual coverage minus BASIC,TOP,SUPER,RIDER
                            if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable().
                                    get(k).getFamily_id().equalsIgnoreCase(familId)) {
                                total_coverage = total_coverage.add(new BigInteger(motor_Proper_Health_Model.getData().
                                        getHealth_ins_plan().
                                        getIndividual_health_plan().getAvailable().get(k).getCoverage()));
                                Log.d("", "total_coverage" + total_coverage);
                            }

if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable().
        get(k).getPlan_type()!=null) {

    mPlan_type = motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable().
            get(k).getPlan_type();
}

                            if (mPlan_type.equalsIgnoreCase("Employer Provided")) {

                                for (int j = 0; j < sub_length; j++) {

                                    if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable().
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


                                        if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                            if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Rider")) {
                                                employer_total_rider = employer_total_rider.add(new BigInteger(motor_Proper_Health_Model.
                                                        getData().getHealth_ins_plan().getIndividual_health_plan().
                                                        getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                                Log.d("", "employer_total_rider" + employer_total_rider);
                                            }
                                        }

                                        if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                            if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Top Up")) {
                                                employer_total_topup = employer_total_topup.add(new BigInteger(motor_Proper_Health_Model.
                                                        getData().getHealth_ins_plan().getIndividual_health_plan().
                                                        getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                                Log.d("", "employer_total_topup" + employer_total_topup);
                                            }
                                        }

                                        if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                            if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Super Top Up")) {
                                                employer_total_super_topup = employer_total_super_topup.add(new BigInteger(motor_Proper_Health_Model.
                                                        getData().getHealth_ins_plan().getIndividual_health_plan().
                                                        getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                                Log.d("", "employer_total_super_topup" + employer_total_super_topup);
                                            }
                                        }

                                        if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                            if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().
                                                    getAvailable().get(k).getSub_insur().get(j).getIns_prod_cat().equals("Basic Plan")) {
                                                employer_total_basic_plan = employer_total_basic_plan.add(new BigInteger(motor_Proper_Health_Model.
                                                        getData().getHealth_ins_plan().getIndividual_health_plan().
                                                        getAvailable().get(k).getSub_insur().get(j).getSum_assured()));
                                                Log.d("", "employer_total_basic_plan" + employer_total_basic_plan);
                                            }
                                        }
                                        /**
                                         * personally subscribed
                                         */


                                    }

                                }
                            }


                               else if(mPlan_type.equalsIgnoreCase("Personally Subscribed")){

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


                    //If less than zero value come below condition apply
                    if(total_sub_all.signum() == 1){
                        recommend=total_sub_all;
                    }else {
                        recommend=BigInteger.ZERO;
                    }



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
                                xBARENTRYSUGGESSTED = new float[1];
                                if (individual_suggessted != null) {
                                    xBARENTRYSUGGESSTED[0] = Float.parseFloat(String.valueOf(individual_suggessted));
                                    colour[0] = ColorTemplate.rgb("#53adfc");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#53adfc")));
                                    xDatadetailsData.add("Suggessted : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(individual_suggessted)))));
                                }
                                /**
                                 * 1st Barchart topup,supertopup,basic,rider
                                 */
                                xBARENTRYCOVERAGE=new float[6];
                                if (total_coverage_subract != null) {
                                    xBARENTRYCOVERAGE[0] = Float.parseFloat(String.valueOf(total_coverage_subract));
                                    colour[1] = ColorTemplate.rgb("#FFC107");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FFC107")));
                                    xDatadetailsData.add("Total Coverage : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(total_coverage_subract)))));
                                }

                                if (total_basic_plan != null) {
                                    xBARENTRYCOVERAGE[1] = Float.parseFloat(String.valueOf(total_basic_plan));
                                    colour[2] = ColorTemplate.rgb("#008000");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#008000")));
                                    xDatadetailsData.add("Basic Plan : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(total_basic_plan)))));
                                }
                                if (total_topup != null) {
                                    xBARENTRYCOVERAGE[2] = Float.parseFloat(String.valueOf(total_topup));
                                    colour[3] = ColorTemplate.rgb("#00FFFF");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#00FFFF")));
                                    xDatadetailsData.add("Topup : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(total_topup)))));
                                }
                                if (total_super_topup != null) {
                                    xBARENTRYCOVERAGE[3] = Float.parseFloat(String.valueOf(total_super_topup));
                                    colour[4] = ColorTemplate.rgb("#FF00FF");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF00FF")));
                                    xDatadetailsData.add("Super Topup : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(total_super_topup)))));
                                }

                                if (total_rider != null) {
                                    xBARENTRYCOVERAGE[4] = Float.parseFloat(String.valueOf(total_rider));
                                    colour[5] = ColorTemplate.rgb("#0000FF");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#0000FF")));
                                    xDatadetailsData.add("Rider : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(total_rider)))));
                                }

                                if (recommend != null) {
                                    xBARENTRYCOVERAGE[5] = Float.parseFloat(String.valueOf(recommend));
                                    colour[6] = ColorTemplate.rgb("#FF0000");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF0000")));
                                    xDatadetailsData.add("Recommended : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(recommend)))));
                                }
                                /**
                                 * 2nd bar employer bar
                                 */
                                xBARENTRYEMPLOYER=new float[5];
                                if (employer_total_basic_plan != null) {
                                    xBARENTRYEMPLOYER[0] = Float.parseFloat(String.valueOf(employer_total_basic_plan));
                                    colour[7] = ColorTemplate.rgb("#2E64FE");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#2E64FE")));
                                    xDatadetailsData.add("Employer Basic Plan : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(employer_total_basic_plan)))));
                                }
                                if (employer_total_topup != null) {
                                    xBARENTRYEMPLOYER[1] = Float.parseFloat(String.valueOf(employer_total_topup));
                                    colour[8] = ColorTemplate.rgb("#DF3A01");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#DF3A01")));
                                    xDatadetailsData.add("Employer Topup : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(employer_total_topup)))));
                                }
                                if (employer_total_super_topup != null) {
                                    xBARENTRYEMPLOYER[2] = Float.parseFloat(String.valueOf(employer_total_super_topup));
                                    colour[9] = ColorTemplate.rgb("#610B5E");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#610B5E")));
                                    xDatadetailsData.add("Employer Super Topup : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(employer_total_super_topup)))));
                                }

                                if (employer_total_rider != null) {
                                    xBARENTRYEMPLOYER[3] = Float.parseFloat(String.valueOf(employer_total_rider));
                                    colour[10] = ColorTemplate.rgb("#3ADF00");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#3ADF00")));
                                    xDatadetailsData.add("Employer Rider : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(employer_total_rider)))));
                                }

                                //Removing Employer recommended for  client change

                              /*  if (employer_total_sub_all != null) {
                                    xBARENTRYEMPLOYER[4] = Float.parseFloat(String.valueOf(employer_total_sub_all));
                                    colour[11] = ColorTemplate.rgb("#FF0000");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF0000")));
                                    xDatadetailsData.add("Employer Recommended : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(employer_total_sub_all)))));
                                }*/


                                /**
                                 * 3rd bar Personally provider
                                 */
                                xBARENTRYPERSONALLY=new float[5];
                                if (personally_total_basic_plan != null) {
                                    xBARENTRYPERSONALLY[0] = Float.parseFloat(String.valueOf(personally_total_basic_plan));
                                    colour[12] = ColorTemplate.rgb("#F5A9F2");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#F5A9F2")));
                                    xDatadetailsData.add("Personally Basic Plan : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(personally_total_basic_plan)))));
                                }
                                if (personally_total_topup != null) {
                                    xBARENTRYPERSONALLY[1] = Float.parseFloat(String.valueOf(personally_total_topup));
                                    colour[13] = ColorTemplate.rgb("#424242");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#424242")));
                                    xDatadetailsData.add("Personally Topup : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(personally_total_topup)))));
                                }
                                if (personally_total_super_topup != null) {
                                    xBARENTRYPERSONALLY[2] = Float.parseFloat(String.valueOf(personally_total_super_topup));
                                    colour[14] = ColorTemplate.rgb("#2EFE64");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#2EFE64")));
                                    xDatadetailsData.add("Personally Super Topup : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(personally_total_super_topup)))));
                                }

                                if (personally_total_rider != null) {
                                    xBARENTRYPERSONALLY[3] = Float.parseFloat(String.valueOf(personally_total_rider));
                                    colour[15] = ColorTemplate.rgb("#F78181");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#F78181")));
                                    xDatadetailsData.add("Personally Rider : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(personally_total_rider)))));
                                }

                                //Removing Personally recommended for  client change

                             /*   if (personally_total_sub_all != null) {
                                    xBARENTRYPERSONALLY[4] = Float.parseFloat(String.valueOf(personally_total_sub_all));
                                    colour[16] = ColorTemplate.rgb("#FF0000");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF0000")));
                                    xDatadetailsData.add("Personally Recommended : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(personally_total_sub_all)))));
                                }*/
                            }




                    parentView.removeAllViews();

                }




                BARENTRYHORIZONTAL.add(new BarEntry(xBARENTRYSUGGESSTED, 0));
                BARENTRYHORIZONTAL.add(new BarEntry(xBARENTRYCOVERAGE,1));
                BARENTRYHORIZONTAL.add(new BarEntry(xBARENTRYEMPLOYER,2));
                BARENTRYHORIZONTAL.add(new BarEntry(xBARENTRYPERSONALLY,3));
                BardatasetHorizontal = new BarDataSet(BARENTRYHORIZONTAL, "");



                BardatasetHorizontal.setColors(colour);
                BardatasetHorizontal.setValueFormatter(new ChartValueFormatter());
                BarData data;
                data = new BarData(getXAxisValues(), BardatasetHorizontal);
                individual_chart.setData(data);
                individual_chart.setDescription(" ");
                data.setDrawValues(false);
                Legend horizontalline = individual_chart.getLegend();
                horizontalline.setEnabled(false);
                horizontalline.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                individual_chart.setPinchZoom(false);
                individual_chart.setDoubleTapToZoomEnabled(false);
                individual_chart.setTouchEnabled(false);
                individual_chart.invalidate();
                setDataForCheckbox(xDatadetailsData, xDatadetailscolor);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else {

        }
    }


    private void setDataForCheckbox(ArrayList<String> xDataCheckbox, ArrayList<String> colour) {
        try {
            Log.i("InsuranceAnalysis", " InsuranceAnalysis setDataForCheckbox is " + xDataCheckbox.size() );

            for (int i = 0; i <  xDataCheckbox.size(); i++) {

                LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout parent_layout = new LinearLayout(mContext);
                parent_layout.setWeightSum(2);
                parent_layout.setOrientation(LinearLayout.HORIZONTAL);
                parent_param_layout.setMargins(10, 0, 0, 10);
                parent_layout.setLayoutParams(parent_param_layout);

                LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                parms_left_layout.weight = 1F;
                LinearLayout left_layout = new LinearLayout(mContext);
                left_layout.setOrientation(LinearLayout.HORIZONTAL);
                left_layout.setGravity(Gravity.LEFT);
                left_layout.setLayoutParams(parms_left_layout);

                LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(45, 45);
                parms_legen_layout.setMargins(20, 10, 20, 10);
                LinearLayout legend_layout = new LinearLayout(mContext);
                legend_layout.setLayoutParams(parms_legen_layout);
                legend_layout.setOrientation(LinearLayout.HORIZONTAL);
                legend_layout.setBackgroundColor(Integer.parseInt(colour.get(i)));
                left_layout.addView(legend_layout);

                TextView txt_unit = new TextView(mContext);
                txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                LinearLayout.LayoutParams txtLeft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txt_unit.setLayoutParams(txtLeft_params);

                txt_unit.setText(xDataCheckbox.get(i));
                left_layout.addView(txt_unit);

                parent_layout.addView(left_layout);
                parentView.addView(parent_layout);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Suggested");
        xAxis.add("Total Coverage");
        xAxis.add("Employer Provided");
        xAxis.add("Personally Subscribed");
        return xAxis;
    }



    @Override
    public void onClick(View v) {

    }
}

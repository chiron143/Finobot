package com.purplepath.purplepath.healthinsurance;

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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.healthinsurance.Details.HealthInsuranceDetailViewpager;
import com.purplepath.purplepath.healthinsurance.adapter.HealthInsuranceExpandableAdapter;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.propertyinsurance.healthinsurancemodel.Available;
import com.purplepath.purplepath.propertyinsurance.healthinsurancemodel.Suggested;
import com.purplepath.purplepath.propertyinsurance.model.Motor_Proper_Health_Model;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by pravinr on 8/1/17.
 */

public class HealthInsuranceSummary extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private ScrollView scrollview;

    private TextView mDependents;

    private TextView mSpouse,mChildren,mParents,mSiblings,mOthers;

    private TextView mOverall_health_ins_cover_amount,mIndivial_plan,mFamily_floater_plan;

    private TextView mCurrent_insurances_cover_amount,mEmployer_provided,mPersonally_subscribed;

    private TextView mSelf_amount,mFamily_amount,mParents_amount,mSiblings_amount;

    private Motor_Proper_Health_Model motor_Proper_Health_Model;

    private BigInteger overall_health_insurance_cover=BigInteger.ZERO;
    private BigInteger individual_plan = BigInteger.ZERO;
    private BigInteger family_floater_plan=BigInteger.ZERO;

    private String mPlan_type="";

    private BigInteger employer_provided_individual=BigInteger.ZERO;
    private BigInteger personally_provided_individual=BigInteger.ZERO;

    private BigInteger employer_provided_familyplan=BigInteger.ZERO;
    private BigInteger personally_provided_familyplan=BigInteger.ZERO;

    private BigInteger total_employer_provided=BigInteger.ZERO;
    private BigInteger total_personally_subscribed=BigInteger.ZERO;

    private BigInteger total_current_insurance_cover=BigInteger.ZERO;

    private LinearLayout mLayout_individual_plan;

    private ExpandableListView mExpandable_listview;

    HashSet<String> familyid_hashset = new HashSet<String>();

    HealthInsuranceExpandableAdapter healthinsuranceexpandableadapter;

    boolean flagToggleButton = false;

    ArrayList<ArrayList<Available>> mfilterarray = new ArrayList<>();

    ArrayList<Available> arrayListSpouse = new ArrayList<Available>();
    ArrayList<Available> arrayListChildren = new ArrayList<Available>();
    ArrayList<Available> arrayListParent = new ArrayList<Available>();
    ArrayList<Available> arrayListSiblings= new ArrayList<Available>();
    ArrayList<Available> arrayListOthers = new ArrayList<Available>();

    ArrayList<Available> arrayListSelf = new ArrayList<Available>();


    private BigInteger total_family_suggested=BigInteger.ZERO;
    private BigInteger total_family_availed_cover=BigInteger.ZERO;
    private BigInteger total_family_availed=BigInteger.ZERO;

    LinearLayout layout_card_spouse,layout_card_children,layout_card_parents,layout_card_siblings,
                 layout_card_others;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        backPressedListener= (OnActivityBackPressedListener) mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_healthinsurancesummary, container, false);
        backPressedListener.setActionBarTitle("Health Insurance");
        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        mLayout_individual_plan=(LinearLayout)view.findViewById(R.id.layout_individual_plan);
        mLayout_individual_plan.setOnClickListener(this);

        mExpandable_listview=(ExpandableListView)view.findViewById(R.id.expandable_listview);

        scrollview=(ScrollView)view.findViewById(R.id.scrollview);

        mDependents=(TextView)view.findViewById(R.id.dependents);

        mSpouse=(TextView)view.findViewById(R.id.spouse);
        mChildren=(TextView)view.findViewById(R.id.children);
        mParents=(TextView)view.findViewById(R.id.parents);
        mSiblings=(TextView)view.findViewById(R.id.siblings);
        mOthers=(TextView)view.findViewById(R.id.others);

        layout_card_spouse=(LinearLayout)view.findViewById(R.id.layout_card_spouse);
        layout_card_children=(LinearLayout)view.findViewById(R.id.layout_card_children);
        layout_card_parents=(LinearLayout)view.findViewById(R.id.layout_card_parents);
        layout_card_siblings=(LinearLayout)view.findViewById(R.id.layout_card_siblings);
        layout_card_others=(LinearLayout)view.findViewById(R.id.layout_card_others);

        mOverall_health_ins_cover_amount=(TextView)view.findViewById(R.id.overall_health_ins_cover_amount);
        mIndivial_plan=(TextView)view.findViewById(R.id.indivial_plan);
        mFamily_floater_plan=(TextView)view.findViewById(R.id.family_floater_plan);

        mCurrent_insurances_cover_amount=(TextView)view.findViewById(R.id.current_insurances_cover_amount);
        mEmployer_provided=(TextView)view.findViewById(R.id.employer_provided);
        mPersonally_subscribed=(TextView)view.findViewById(R.id.personally_subscribed);

        mSelf_amount=(TextView)view.findViewById(R.id.self_amount);
        mFamily_amount=(TextView)view.findViewById(R.id.family_amount);
        mParents_amount=(TextView)view.findViewById(R.id.parents_amount);
        mSiblings_amount=(TextView)view.findViewById(R.id.siblings_amount);

        callHealthInsuranceService();

        return view;
    }


    public void callHealthInsuranceService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Motor_Proper_Health_Model> call = webServiceObj.callGetPropertyService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Motor_Proper_Health_Model>() {
            @Override
            public void onResponse(Call<Motor_Proper_Health_Model> call, Response<Motor_Proper_Health_Model> response) {
                UtileKit.dismisssSpinnerDialog();
                motor_Proper_Health_Model = response.body();
                if (motor_Proper_Health_Model.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    overallInsuranceCoverSecondCard(motor_Proper_Health_Model);

                    groupingSecondCard(motor_Proper_Health_Model);

                    currentInsuranceCoverThirdCard(motor_Proper_Health_Model);

                    familyFloaterSubractSuggessted(motor_Proper_Health_Model);

                }
            }

            @Override
            public void onFailure(Call<Motor_Proper_Health_Model> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void familyFloaterSubractSuggessted(Motor_Proper_Health_Model motor_proper_health_model) {

        total_family_suggested=new BigInteger(motor_proper_health_model.getData().getHealth_ins_plan().
                getFamily_floater_plan().getSuggested());

        int lenth=motor_proper_health_model.getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().size();

        total_family_availed_cover=BigInteger.ZERO;
        for(int i=0;i<lenth;i++){
           total_family_availed_cover =total_family_availed_cover.add(new BigInteger(motor_proper_health_model.getData().getHealth_ins_plan().
                   getFamily_floater_plan().getAvailable().get(i).getCoverage()));

        }
        /**
         * @
         */
        total_family_availed=total_family_suggested.subtract(total_family_availed_cover);

        mFamily_amount.setText("₹ "+UtileKit.formatedNumbers(total_family_availed));
    }

    private void overallInsuranceCoverSecondCard(Motor_Proper_Health_Model motor_proper_health_model) {

        int length=motor_proper_health_model.getData().getHealth_ins_plan().getIndividual_health_plan().getSuggested().size();

        individual_plan=BigInteger.ZERO;

        for(int i=0;i<length;i++){
            individual_plan=individual_plan.add(new BigInteger(motor_proper_health_model.getData().getHealth_ins_plan().
                    getIndividual_health_plan().getSuggested().get(i).getCover()));
        }

        family_floater_plan=new BigInteger(motor_proper_health_model.getData().getHealth_ins_plan().getFamily_floater_plan().getSuggested());

        overall_health_insurance_cover=individual_plan.add(family_floater_plan);

        mOverall_health_ins_cover_amount.setText("Your overall health insurance cover reqirement is"+"₹ " +UtileKit.formatedNumbers(overall_health_insurance_cover));
        mIndivial_plan.setText("Individual Plan"+"₹ " +UtileKit.formatedNumbers(individual_plan));
        mFamily_floater_plan.setText("Family Floter Plan"+"₹ " +UtileKit.formatedNumbers(family_floater_plan));
    }

    private void groupingSecondCard(Motor_Proper_Health_Model motor_proper_health_model) {

        if (motor_proper_health_model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable() != null) {

            int length = motor_proper_health_model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable().size();

            for (int i = 0; i < length; i++) {
                familyid_hashset.add(motor_proper_health_model.getData().getHealth_ins_plan().
                        getIndividual_health_plan().getAvailable().get(i).getFamily_id());
            }
            ArrayList<String> arrayList = new ArrayList<String>(familyid_hashset);

            for (int j = 0; j < arrayList.size(); j++) {
                String str_obj = arrayList.get(j);
                if (str_obj != null) {
                    ArrayList<Available> heading = new ArrayList<Available>();

                    for (int k = 0; k < length; k++) {
                        if (str_obj.equals(motor_proper_health_model.getData().getHealth_ins_plan().
                                getIndividual_health_plan().getAvailable().get(k).getFamily_id())) {
                            heading.add(motor_proper_health_model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable().get(k));
                        }
                    }
                    mfilterarray.add(heading);
                }
            }
            filterSection(mfilterarray);

            groupingfamilyPersonsFourthCard(mfilterarray);
        }
    }

    private void filterSection(ArrayList<ArrayList<Available>> mfilterarray) {

        healthinsuranceexpandableadapter=new HealthInsuranceExpandableAdapter(mContext,mfilterarray);
        mExpandable_listview.setAdapter(healthinsuranceexpandableadapter);
    }

    private void currentInsuranceCoverThirdCard(Motor_Proper_Health_Model motor_proper_health_model) {

        int individual_lenth=motor_proper_health_model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable().size();

        employer_provided_individual=BigInteger.ZERO;
        personally_provided_individual=BigInteger.ZERO;

        for(int i=0;i<individual_lenth;i++){

            mPlan_type=motor_proper_health_model.getData().getHealth_ins_plan().getIndividual_health_plan().getAvailable().get(i).getPlan_type();


            if(mPlan_type!=null) {
                if (mPlan_type.equalsIgnoreCase("Employer Provided")) {
                    employer_provided_individual = employer_provided_individual.add(new BigInteger(motor_proper_health_model.getData().
                            getHealth_ins_plan().getIndividual_health_plan().getAvailable().get(i).getCoverage()));
                } else {
                    personally_provided_individual = personally_provided_individual.add(new BigInteger(motor_proper_health_model.getData().
                            getHealth_ins_plan().getIndividual_health_plan().getAvailable().get(i).getCoverage()));
                }
            }
        }

        int family_lenth=motor_proper_health_model.getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().size();

        employer_provided_familyplan=BigInteger.ZERO;
        personally_provided_familyplan=BigInteger.ZERO;

        for(int j=0;j<family_lenth;j++) {
            mPlan_type = motor_proper_health_model.getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().get(j).getPlan_type();

        if(mPlan_type!=null){

        if(mPlan_type.equalsIgnoreCase("Employer Provided")){
            employer_provided_familyplan=employer_provided_familyplan.add(new BigInteger(motor_proper_health_model.
                    getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().get(j).getCoverage()));
        }else {
            personally_provided_familyplan=personally_provided_familyplan.add(new BigInteger(motor_proper_health_model.
                    getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().get(j).getCoverage()));
        }
        }
        }

        total_employer_provided=employer_provided_individual.add(employer_provided_familyplan);
        total_personally_subscribed=personally_provided_individual.add(personally_provided_familyplan);

        mEmployer_provided.setText("₹ " +UtileKit.formatedNumbers(total_employer_provided));
        mPersonally_subscribed.setText("₹ " +UtileKit.formatedNumbers(total_personally_subscribed));

        total_current_insurance_cover=total_employer_provided.add(total_personally_subscribed);

        mCurrent_insurances_cover_amount.setText("Your current insurance covers upto"+"₹ " +UtileKit.formatedNumbers(total_current_insurance_cover)+"which is adequate/inadequate");

    }

    private void groupingfamilyPersonsFourthCard(ArrayList<ArrayList<Available>> mfilterarray) {

        int lenth=mfilterarray.size();

        for(int i=0;i<lenth;i++){

            int innerlength=mfilterarray.get(i).size();

              if(innerlength!=0) {

                if(mfilterarray.get(i).get(0).getFamily_relation()==null||
                   mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Other")){

                    if(mfilterarray.get(i).get(0).getFamily_id().equalsIgnoreCase("0")){
                        arrayListSelf.addAll(mfilterarray.get(i));
                    }else {
                        arrayListOthers.addAll(mfilterarray.get(i));
                    }

                }
                else if(mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Spouse")||
                        mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Husband")||
                        mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Wife")){

                    arrayListSpouse.addAll(mfilterarray.get(i));
                }
                else if(mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Son")||
                        mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Daughter")||
                        mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Grand Son")||
                        mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Grand Daughter")){

                    arrayListChildren.addAll(mfilterarray.get(i));
                }
                else if(mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Brother")||
                        mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Sister")||
                        mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Siblings")){

                    arrayListSiblings.addAll(mfilterarray.get(i));
                }
                else if(mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Father")||
                        mfilterarray.get(i).get(0).getFamily_relation().equalsIgnoreCase("Mother")){

                    arrayListParent.addAll(mfilterarray.get(i));
                }
            }
        }



        mDependents.setText("You have"+(arrayListSpouse.size()+arrayListChildren.size()+arrayListParent.size()
                +arrayListSiblings.size()+arrayListOthers.size())+"dependents");

        arrayListLinearlayout(arrayListSpouse,layout_card_spouse);
        arrayListLinearlayout(arrayListChildren,layout_card_children);
        arrayListLinearlayout(arrayListParent,layout_card_parents);
        arrayListLinearlayout(arrayListSiblings,layout_card_siblings);
        arrayListLinearlayout(arrayListOthers,layout_card_others);

        mSpouse.setText("Spouse - "+arrayListSpouse.size());
        mChildren.setText("Children - "+arrayListChildren.size());
        mParents.setText("Parents - "+arrayListParent.size());
        mSiblings.setText("Siblings - "+arrayListSiblings.size());
        mOthers.setText("Others - "+arrayListOthers.size());


        mSelf_amount.setText("₹ "+UtileKit.formatedNumbers(sumofCoverages(arrayListSelf)));
      //  mFamily_amount.setText("₹ "+sumofCoverages(arrayListSiblings));
        mParents_amount.setText("₹ "+UtileKit.formatedNumbers(sumofCoverages(arrayListParent)));
        mSiblings_amount.setText("₹ "+UtileKit.formatedNumbers(sumofCoverages(arrayListSiblings)));


    }


    void arrayListLinearlayout(ArrayList<Available> arrayList,LinearLayout linearLayout){
        if(arrayList.size()!=0){
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    BigInteger sumofCoverages(ArrayList<Available> availables){

            HashSet<String> familyId=new HashSet<>();

            BigInteger mAmount=BigInteger.ZERO;

             BigInteger finalTotal;

             for (Available obj:availables) {

                 mAmount=mAmount.add(new BigInteger(obj.getCoverage()));

                 familyId.add(obj.getFamily_id());
            }
            BigInteger msugestedAmount=BigInteger.ZERO;

            for (Suggested obj2:motor_Proper_Health_Model.getData().getHealth_ins_plan().getIndividual_health_plan().getSuggested()) {

                if(familyId.contains(obj2.getFamily_id())) {

                    msugestedAmount = msugestedAmount.add(new BigInteger(obj2.getCover()));

                }
            }
            finalTotal = msugestedAmount.subtract(mAmount);

            return finalTotal;
        }







    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_detail);
        MenuItem items=menu.findItem(R.id.menu_chart);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_detail:
                try{
                 //   addFragmenttoStack(new HealthInsuranceDetail());

                    addFragmenttoStack(new HealthInsuranceDetailViewpager());

                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_chart:
                try{
                    addFragmenttoStack(new HealthInsuranceChart());
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

            case R.id.layout_individual_plan:
                if (!(flagToggleButton)) {

                    mExpandable_listview.setVisibility(View.VISIBLE);

                    flagToggleButton=true;
                }else {
                    mExpandable_listview.setVisibility(View.GONE);

                    flagToggleButton = false;
                }
                break;

        }

    }
}

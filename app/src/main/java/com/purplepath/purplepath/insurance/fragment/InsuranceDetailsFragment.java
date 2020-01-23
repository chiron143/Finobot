package com.purplepath.purplepath.insurance.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.chatprompt.PromptChatFragment1;
import com.purplepath.purplepath.chatprompt.insertmodel.InsertModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goal.GoalFamilyDetailsModel;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.adapter.InsuranceViewPagerAdapter;
import com.purplepath.purplepath.insurance.model.InsuranceCatogoryModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retirementbenefits.RetirementBenefitsFragment;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.INSURANCE_CATAGORY_PREF;


public class InsuranceDetailsFragment extends BaseFragment implements View.OnClickListener,FirstTimeDoneInterface {

    private CheckBox saleryViewDropBtn,basicDetailViewDrop,allowanceViewDrop,
            incomefromPropertyViewDrop,incomefromBusinessViewDrop,capitalGainViewDrop,
            incomefromOtherViewDrop;
    private LinearLayout basicaleryView,allowanceView,incomFromPropertyView,
            incomFromBusinessView,incomeFromCapitalGainView,incomeFromOtherSourceView;
    private RelativeLayout basisaleryView;
    private InsuranceCatogoryModel mInsuranceCatModel;
    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private Context mContext;
    private InsuranceViewPagerAdapter pagerAdapter;
    private String[] tabTitles = new String[]{"Life Insurance", "General Insurance"};
    private Button assetButton;
    static OnCustomGeneralInsurTabChange onCustomGeneralInsTabChange;
    static OnCustomLifeInsurTabChange onLifeInsCustomTabChange;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private OnActivityBackPressedListener mCallBackListener;
    public static GoalFamilyDetailsModel addFamilyDetailModel;

    InsertModel insertModel;

    Boolean isSignUp = false;
    ArrayList<String> formArray = new ArrayList<String>();
    private LinearLayout bottom_bar_layout,bottom_bar_donelayout;
    private RelativeLayout relative_finish_later,relative_done_arrow;
    FirstTimeDoneInterface firstTimeDoneInterface;


    public static InsuranceDetailsFragment newInstance() {
        InsuranceDetailsFragment personal = new InsuranceDetailsFragment();

        return personal;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        firstTimeDoneInterface = this;

        try{
            if (getArguments() != null) {
                if (getArguments().containsKey("IsSignUp"))
                    isSignUp = getArguments().getBoolean("IsSignUp");
            }

            if (getArguments() != null) {
                if (getArguments().containsKey("form_array")) {
                    formArray = (ArrayList<String>) getArguments().getSerializable("form_array");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }

        catch(Exception e)
        {}
        String catgoryJsonString= UtileKit.getPersistedPurplePathPref(INSURANCE_CATAGORY_PREF);
        if(catgoryJsonString==null)
        {
            callInsuranceCatagory();
        }
        else
        {
            try {
                Gson gson = new Gson();
                mInsuranceCatModel = gson.fromJson(catgoryJsonString, InsuranceCatogoryModel.class);
            } catch (Exception ex) {
                callInsuranceCatagory();
            }
        }
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }
    public void setListener(FirstTimeDoneInterface firstTimeDoneInterface){
        this.firstTimeDoneInterface=firstTimeDoneInterface;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_insurance_deetails, container, false);
    }

    @Override
    public void onViewCreated(View expensesdetailView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(expensesdetailView, savedInstanceState);
        mCallBackListener.setActionBarTitle("Insurance Details");
        mTabLayout = expensesdetailView.findViewById(R.id.expenses_details_tab_layout_id);
        viewPager = expensesdetailView.findViewById(R.id.expenses_viewpager);
        mleftRelativeLayout = expensesdetailView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = expensesdetailView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = expensesdetailView.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);


        relative_finish_later= expensesdetailView.findViewById(R.id.relative_finish_later);
        relative_done_arrow= expensesdetailView.findViewById(R.id.relative_done_arrow);
        relative_finish_later.setOnClickListener(this);
        relative_done_arrow.setOnClickListener(this);

        bottom_bar_layout= expensesdetailView.findViewById(R.id.bottom_bar_layout);
        UtileKit.mandatoryFieldLinearLayout(isSignUp,bottom_bar_layout);

        bottom_bar_donelayout= expensesdetailView.findViewById(R.id.bottom_bar_donelayout);
        UtileKit.mandatoryFieldDoneLayout(isSignUp,bottom_bar_donelayout);


        pagerAdapter = new InsuranceViewPagerAdapter(getChildFragmentManager(), tabTitles,isSignUp,formArray,firstTimeDoneInterface);
        viewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                if(tab.getPosition()==1){
//                    onLifeInsCustomTabChange.onLifeInsurTabChangeCallService();
//                }else{
//                    onCustomGeneralInsTabChange.onGeneralInsurTabChangeCallService();
//                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });
        callFamilyDetailsService();


        if(isSignUp==true) {
            firstTimeDoneButtonGone("N");
        }
    }



    private void firstTimeDoneButtonGone(String done) {
        if(done.equalsIgnoreCase("Y")){
            relative_done_arrow.setVisibility(View.VISIBLE);
        }else {
            relative_done_arrow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


            case R.id.relative_finish_later:{
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
            break;
            case R.id.relative_done_arrow:{
                callUpdateInsertFlagService();
                addFragmenttoStack(new PromptChatFragment1());
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
//                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow:
            {
             addFragmenttoStack(new RetirementBenefitsFragment());
            }
            break;


        }
    }

    public void callUpdateInsertFlagService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<InsertModel> call = webServiceObj.callUpdateInsertFlagService(UtileKit.getPersistedPurplePathPref("user_id"),
                "users_insur_details","Y","Y");
        call.enqueue(new Callback<InsertModel>() {
            @Override
            public void onResponse(Call<InsertModel> call, Response<InsertModel> response) {
                UtileKit.dismisssSpinnerDialog();
                insertModel = response.body();
                if(insertModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                }
            }
            @Override
            public void onFailure(Call<InsertModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }
    public static void setCustomOnGenInsurTabChangeListener(OnCustomGeneralInsurTabChange onGeneralTabChange){
        onCustomGeneralInsTabChange = onGeneralTabChange;
    }

    public static void setCustomOnLifeInsurTabChangeListener(OnCustomLifeInsurTabChange onLifeTabChange){
        onLifeInsCustomTabChange = onLifeTabChange;
    }


    public interface OnCustomGeneralInsurTabChange{
        void onGeneralInsurTabChangeCallService();
    }

    public interface OnCustomLifeInsurTabChange{
        void onLifeInsurTabChangeCallService();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.ic_done_btn:

                try{

                    mCallBackListener.onActivityBackPressed();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();

    }
    public void callInsuranceCatagory() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<InsuranceCatogoryModel> call = webServiceObj.callCatagoryService();
        call.enqueue(new Callback<InsuranceCatogoryModel>() {
            @Override
            public void onResponse(Call<InsuranceCatogoryModel> call, Response<InsuranceCatogoryModel> response) {
                UtileKit.dismisssSpinnerDialog();
                InsuranceCatogoryModel   getInsuranceCatagoryModel = response.body();
                if (getInsuranceCatagoryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    mInsuranceCatModel=getInsuranceCatagoryModel;
                    try {


                        Gson gson = new Gson();
                        UtileKit.persistingPurplePathPref(INSURANCE_CATAGORY_PREF, gson.toJson(mInsuranceCatModel));
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<InsuranceCatogoryModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }
    private void callFamilyDetailsService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalFamilyDetailsModel> call = webServiceObj.callFamilyDetailsListService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GoalFamilyDetailsModel>() {
            @Override
            public void onResponse(Call<GoalFamilyDetailsModel> call, Response<GoalFamilyDetailsModel> response) {
                //Log.e("CallBack", " family is " + call.toString());
                addFamilyDetailModel = response.body();
                UtileKit.dismisssSpinnerDialog();
                if (addFamilyDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//                    familyDetails = addFamilyDetailModel.getData().getFamily_details();
//                    if (UtileKit.validateObjectValues(familyDetails)) {
//                        if(!familyDetails.isEmpty()) {
//                            for (int i = 0; i < familyDetails.size(); i++) {
////                                mgoalBelongsToArrayList.add(familyDetails.get(i).getName());
////                                mgoalBelongsToArrayListId.add(familyDetails.get(i).getId());
//                                //Log.e("CallBack", " family is " + familyDetails.get(i).getName());
//                            }
//                            //minsurance_family_layout.setVisibility(View.VISIBLE);
//                        }
                        //  UtileKit.setArrayListSpinnerAdapter(mInsurBelongToSpinner, mgoalBelongsToArrayList, activity);
                        //Log.e("CallBack", "InsuranceSpinnerbelongtoarraylist" + mgoalBelongsToArrayList);
                        //setSpinnerAdapter(mInsurBelongToSpinner, mgoalBelongsToArrayList, activity);
                        //Log.e("CallBack", "InsuranceSpinnerbelongtoarraylist2" + mgoalBelongsToArrayList);
//                    }
                }
            }
            @Override
            public void onFailure(Call<GoalFamilyDetailsModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }

    @Override
    public void firstTimeDone(String done) {
        firstTimeDoneButtonGone(done);
    }
}

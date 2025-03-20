package com.purplepath.purplepath.liabilities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.chatprompt.PromptChatFragment1;
import com.purplepath.purplepath.chatprompt.insertmodel.InsertModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.fragment.InsuranceDetailsFragment;
import com.purplepath.purplepath.liabilities.adapter.LiabilitiesTabAdapter;
import com.purplepath.purplepath.liabilities.fragmentview.IndividualsFragment;
import com.purplepath.purplepath.liabilities.fragmentview.InstitutionFragment;
import com.purplepath.purplepath.liabilities.model.AddLiabilityModel;
import com.purplepath.purplepath.liabilities.model.LiabCategoryModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dinesh on 05/07/16.
 */
    public class LiabilitiesTabViewFragment extends BaseFragment implements View.OnClickListener ,FirstTimeDoneInterface {

  private TabLayout mTabLayoutIncome;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private OnActivityBackPressedListener mCallBackListener;
    private LiabilitiesTabAdapter adapter;
    private ViewPager mViewPager;
    Context mContext;
    Toolbar toolbar;
    List<String> tabTitles = new ArrayList<String>();

    private  LiabCategoryModel liablityCategoryModel;
    private  AddLiabilityModel  mLiablityList;
    private  final String SUCCESSCODE = "200";
    private View liabilityView;
    public HashMap<String,View> errorMapView=new HashMap<>();

    Boolean isSignUp = false;
    ArrayList<String> formArray = new ArrayList<String>();
    InsertModel insertModel;
    private LinearLayout bottom_bar_layout,bottom_bar_donelayout;
    private RelativeLayout relative_finish_later,relative_done_arrow;

    FirstTimeDoneInterface firstTimeDoneInterface;




    public static LiabilitiesTabViewFragment newInstance() {
        LiabilitiesTabViewFragment liabilitiesTabViewFragment = new LiabilitiesTabViewFragment();
        return liabilitiesTabViewFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        mContext=getContext();

        firstTimeDoneInterface = this;
        try {


            if (getArguments() != null) {
                if (getArguments().containsKey("IsSignUp"))
                    isSignUp = getArguments().getBoolean("IsSignUp");
            }

            if (getArguments() != null) {
                if (getArguments().containsKey("form_array")) {
                    formArray = (ArrayList<String>) getArguments().getSerializable("form_array");
                }
            }

            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }

        catch(Exception e)
        {}
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    public void setListener(FirstTimeDoneInterface firstTimeDoneInterface){
        this.firstTimeDoneInterface=firstTimeDoneInterface;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mCallBackListener.setActionBarTitle("Liability Details");
        tabTitles = new ArrayList<String>();

        if(mLiablityList == null) {
            Log.i("CallBack", "liablityCategoryModel is null ");
            liabilityView = inflater.inflate(R.layout.fragment_liablity_detail_view, container, false);
            mleftRelativeLayout = liabilityView.findViewById(R.id.relative_left_arrow);
            mcenterRelativeLayout = liabilityView.findViewById(R.id.relative_center_home);
            mRightRelativeLayout = liabilityView.findViewById(R.id.relative_right_arrow);
            mleftRelativeLayout.setOnClickListener(this);
            mcenterRelativeLayout.setOnClickListener(this);
            mRightRelativeLayout.setOnClickListener(this);
            mTabLayoutIncome = liabilityView.findViewById(R.id.tab_layout_id);
            mViewPager = liabilityView.findViewById(R.id.pager);
            adapter = LiabilitiesTabAdapter.newInstance(getFragmentManager());
            mViewPager.setAdapter(adapter);
            mTabLayoutIncome.setupWithViewPager(mViewPager);



            relative_finish_later= liabilityView.findViewById(R.id.relative_finish_later);
            relative_done_arrow= liabilityView.findViewById(R.id.relative_done_arrow);
            relative_finish_later.setOnClickListener(this);
            relative_done_arrow.setOnClickListener(this);

            bottom_bar_layout= liabilityView.findViewById(R.id.bottom_bar_layout);
            UtileKit.mandatoryFieldLinearLayout(isSignUp,bottom_bar_layout);

            bottom_bar_donelayout= liabilityView.findViewById(R.id.bottom_bar_donelayout);
            UtileKit.mandatoryFieldDoneLayout(isSignUp,bottom_bar_donelayout);

            getLiablityCatagory();
        }else {
            getLiablityCatagory();

        }
        Log.i("CallBack", "liablityCategoryModel is not  null ");
        if(isSignUp==true) {
            firstTimeDoneButtonGone("N");
        }

            return liabilityView;

    }

    private void firstTimeDoneButtonGone(String done) {
        if(done.equalsIgnoreCase("Y")){
            relative_done_arrow.setVisibility(View.VISIBLE);
        }else {
            relative_done_arrow.setVisibility(View.GONE);
        }
    }


    private void getLiablityCatagory() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);

        Call<LiabCategoryModel> call = webServiceObj.callLiablityCategory();
        call.enqueue(new Callback<LiabCategoryModel>() {
            @Override
            public void onResponse(Call<LiabCategoryModel> call, Response<LiabCategoryModel> response) {
                UtileKit.dismisssSpinnerDialog();
                LiabCategoryModel getLiabModel;
                getLiabModel = response.body();
                if (getLiabModel.getStatus_code().equalsIgnoreCase(SUCCESSCODE)) {
                    liablityCategoryModel = 	getLiabModel;
                    WebServiceCalls webServiceObj2;
                    webServiceObj2 = ServiceGenerator.createService(WebServiceCalls.class);

                    Call<AddLiabilityModel> call2 = webServiceObj2.callGetLiablityByUser(UtileKit.getPersistedPurplePathPref("user_id"));
                    call2.enqueue(new Callback<AddLiabilityModel>() {
                        @Override
                        public void onResponse(Call<AddLiabilityModel> call, Response<AddLiabilityModel> response) {
                            try {

                                //Log.e("CallBack", "------- getLiablityCatagory  ");
                            AddLiabilityModel getLiabModel;
                            getLiabModel = response.body();
                                mLiablityList = getLiabModel;
                                adapter = LiabilitiesTabAdapter.newInstance(getFragmentManager());
                                if (getLiabModel.getStatus_code().equalsIgnoreCase(SUCCESSCODE)) {
                                try {


        adapter.addFragment(InstitutionFragment.newInstance(liablityCategoryModel,mLiablityList,isSignUp,formArray,firstTimeDoneInterface), getString(R.string.institutions));
        adapter.addFragment(IndividualsFragment.newInstance(liablityCategoryModel,mLiablityList,isSignUp,formArray,firstTimeDoneInterface), getString(R.string.Individuals));

                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }

                                UtileKit.dismisssSpinnerDialog();




                            }
                            else
                            {
                                mLiablityList = 	null;
                                adapter.addFragment(InstitutionFragment.newInstance(liablityCategoryModel,mLiablityList, isSignUp, formArray, firstTimeDoneInterface), getString(R.string.institutions));
                                adapter.addFragment(IndividualsFragment.newInstance(liablityCategoryModel,mLiablityList, isSignUp, formArray, firstTimeDoneInterface), getString(R.string.Individuals));

                            }
                                mViewPager.setAdapter(adapter);
                                mTabLayoutIncome.setupWithViewPager(mViewPager);

                            UtileKit.dismisssSpinnerDialog();
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                            }

                        }

                        @Override
                        public void onFailure(Call<AddLiabilityModel> call, Throwable t) {
                            //Log.e("CallBack", " failure is " + t);
                            UtileKit.alertRetrofitExceptionDialog( mContext,t);
                            UtileKit.dismisssSpinnerDialog();
                        }
                    });
//                    adapter = new LiabilitiesTabAdapter(getFragmentManager(), tabTitles,liablityCategoryModel);
//                    mViewPager.setAdapter(adapter);
//                    mTabLayoutIncome.setupWithViewPager(mViewPager);
//                    mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayoutIncome));

                }

//                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<LiabCategoryModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }



    private void emptyErrorValidation(ArrayList<String> emptyArrayList) {
        for(String obj:emptyArrayList)
        {
            View view=errorMapView.get(obj);
            UtileKit.emptyErrorViewList(view);
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){


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
               startHomeActivity();
//                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow:
            {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                InsuranceDetailsFragment fragment =
                  addFragmenttoStack(new InsuranceDetailsFragment());
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();

            }
        }
    }

    public void callUpdateInsertFlagService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<InsertModel> call = webServiceObj.callUpdateInsertFlagService(UtileKit.getPersistedPurplePathPref("user_id"),
                "users_liabilities","Y","Y");
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
    @Override
    public void firstTimeDone(String done) {
        firstTimeDoneButtonGone(done);
    }
}

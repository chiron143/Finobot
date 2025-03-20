package com.purplepath.purplepath.famlydetail.fragmentview;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.famlydetail.adapterview.FamilyTabFragmentView;
import com.purplepath.purplepath.famlydetail.adapterview.PagerAdapter;
import com.purplepath.purplepath.famlydetail.addTabinterface.OnAddTabChange;
import com.purplepath.purplepath.famlydetail.interfacetab.OnAddTabInterface;
import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
import com.purplepath.purplepath.famlydetail.model.DeleteFamilyDetailModel;
import com.purplepath.purplepath.famlydetail.model.Family_details;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**

 */
public class FamilyDetailFragment extends BaseFragment implements OnAddTabChange, OnAddTabInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public static FloatingActionButton mAddTabView, mDeleteTabView;
    public static TabLayout mTabLayout = null;
    public static AddFamilyDetailModel mFamilyDetailModel = null;
    public static String mfamilyId;
    public PagerAdapter adapter = null;
    public ViewPager mViewPager = null;
    public List<String> tabTitles;
    public OnAddTabChange onAddTabChange;
    public HorizontalScrollView horizontalScroll;
    Context mContext;
    Toolbar toolbar;
    View familyDetailView = null;
    OnAddTabInterface mFamiltAddTabInterface;
    private OnActivityBackPressedListener mCallBackListener;
    Boolean isSignUp = false;
    ArrayList<String> formArray = new ArrayList<String>();

    public static FamilyDetailFragment newInstance() {
        FamilyDetailFragment familyDetailFragment = new FamilyDetailFragment();
        return familyDetailFragment;
    }

    public void setOnAddTabChange(OnAddTabChange onAddTabChange) {
        this.onAddTabChange = onAddTabChange;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setOnAddTabChange(this);
        try {

            if (getArguments() != null) {
                if (getArguments().containsKey("IsSignUp"))
                    isSignUp = getArguments().getBoolean("IsSignUp");
            }


            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);

        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mCallBackListener.setActionBarTitle("Family Details");
        familyDetailView = inflater.inflate(R.layout.fragment_family_detail, container, false);

        mTabLayout = familyDetailView.findViewById(R.id.tab_layout_id);
        mAddTabView = familyDetailView.findViewById(R.id.addTabViewId);
        horizontalScroll = familyDetailView.findViewById(R.id.horizontalScrollId);
        mViewPager = familyDetailView.findViewById(R.id.pager);
        callGetFamilyDetail(UtileKit.getPersistedPurplePathPref("user_id"));

        if (getArguments() != null) {
            if (getArguments().containsKey("form_array")) {
                formArray = (ArrayList<String>) getArguments().getSerializable("form_array");
            }
        }


        return familyDetailView;

    }

    private void calldeleteFamilyDetail(String fid) {


        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<DeleteFamilyDetailModel> call = webServiceObj.callDeleteFamilyDetailsService(UtileKit.getPersistedPurplePathPref("user_id"), fid);
        call.enqueue(new Callback<DeleteFamilyDetailModel>() {
            @Override
            public void onResponse(Call<DeleteFamilyDetailModel> call, Response<DeleteFamilyDetailModel> response) {
                //Log.e("CallBack", " response is " + call.toString());

                DeleteFamilyDetailModel categoryModel = response.body();
                if (categoryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    mfamilyId = null;
//                    mFamilyDetailModel = categoryModel;
                    callGetFamilyDetail(UtileKit.getPersistedPurplePathPref("user_id"));
                } else {
//                    UtileKit.alertDialog(
//                            categoryModel.getData().getMessage(),
//                            mContext);
                    addTabView(null);
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<DeleteFamilyDetailModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }

    private void callGetFamilyDetail(String userId) {


        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddFamilyDetailModel> call = webServiceObj.callFamilyDetailsService(userId);
        call.enqueue(new Callback<AddFamilyDetailModel>() {
            @Override
            public void onResponse(Call<AddFamilyDetailModel> call, Response<AddFamilyDetailModel> response) {
                //Log.e("CallBack", " response is " + call.toString());

                AddFamilyDetailModel categoryModel = response.body();
                if (categoryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    FamilyDetailFragment.mFamilyDetailModel = categoryModel;
                    addTabView(mFamilyDetailModel.getData().getFamily_details());
                } else {
//                    UtileKit.alertDialog(
//                            categoryModel.getData().getMessage(),
//                            mContext);
                    mFamilyDetailModel = null;
                    addTabView(null);
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<AddFamilyDetailModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.activity_main_drawer, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_delete:

                try {
                    //Log.e("FamilyId,",""+mfamilyId);
                    if (mfamilyId != null)
                        calldeleteFamilyDetail(mfamilyId);
                    else {
                        callGetFamilyDetail(UtileKit.getPersistedPurplePathPref("user_id"));
                    }
//                    FamilyTabFragmentView fragment = (FamilyTabFragmentView) adapter.getItem(tab.getPosition());
//
//                    if (fragment.getArguments() != null) {
//                        if (getArguments().containsKey("family_details")) {
//                            Family_details mFamilyDetModel = (Family_details) getArguments().getSerializable("family_details");
//                            familyId=Integer.parseInt(mFamilyDetModel.getId());
//                            //Log.e("FamilyId","familyId"+mFamilyDetModel.getId()+mFamilyDetModel.getName());
//                        }
//                    }
//                    mCallBackListener.onActivityBackPressed();
                } catch (Exception e) {
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


    private void addTabView(final ArrayList<Family_details> mFamilyDetailModel) {
        mfamilyId = null;
        FamilyTabFragmentView.mPreviusPageNumber = 0;
        tabTitles = new ArrayList<String>();

        if (mFamilyDetailModel != null) {

            try {
                for (int i = 0; i < mFamilyDetailModel.size(); i++) {

                    mTabLayout.addTab(mTabLayout.newTab().setText("" + mFamilyDetailModel.get(i).getName()));
                    mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
                  //  mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
                    tabTitles.add("" + mFamilyDetailModel.get(i).getName());
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {
            mTabLayout.addTab(mTabLayout.newTab().setText("Person1"));
            mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
           // mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
            tabTitles.add("Person1");
        }

        adapter = new PagerAdapter(getChildFragmentManager(), tabTitles, mFamilyDetailModel, onAddTabChange,isSignUp,formArray);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == mTabLayout.getTabCount() - 1) {
                    mAddTabView.setVisibility(View.VISIBLE);
                } else {
                    mAddTabView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public void onTabAdd(AddFamilyDetailModel mFamilyDetailModel) {
        //Log.e("TabCount",""+mTabLayout.getTabCount());
        String name;
        ArrayList tabTitles = new ArrayList<String>();
        FamilyDetailFragment.mFamilyDetailModel = mFamilyDetailModel;
        if (mFamilyDetailModel != null) {

            try {
                mTabLayout.removeAllTabs();
                for (int i = 0; i < mFamilyDetailModel.getData().getFamily_details().size(); i++) {

                    mTabLayout.addTab(FamilyDetailFragment.mTabLayout.newTab().setText("" + mFamilyDetailModel.getData().getFamily_details().get(i).getName()));
                    mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
                  //  mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
                    tabTitles.add("" + mFamilyDetailModel.getData().getFamily_details().get(i).getName());
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            name = "Person" + (FamilyDetailFragment.mTabLayout.getTabCount() + 1);
            mTabLayout.addTab(FamilyDetailFragment.mTabLayout.newTab().setText(name));
            mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
          //  mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
            tabTitles.add(name);

        } else {
            mTabLayout.addTab(FamilyDetailFragment.mTabLayout.newTab().setText("Person1"));
            mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
          //  mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
            tabTitles.add("Person1");
            name = "Person1";
        }

        adapter.updateView(tabTitles);
//                    (getChildFragmentManager(), tabTitles,mFamilyDetailModel, onAddTabChange);
//            mViewPager.setAdapter(adapter);
//            mTabLayout.setupWithViewPager(mViewPager);
//            mTabLayout.setScrollPosition(mTabLayout.getTabCount(), 0f, true);
//            //Log.e("TabCount",""+mTabLayout.getTabCount());
        mViewPager.setCurrentItem(mTabLayout.getTabCount());
//        mTabLayout.setSmoothScrollingEnabled(true);
//        mTabLayout.setScrollPosition(tabTitles.size()-1, 0f, true);
//        mTabLayout.getTabAt(tabTitles.size()-1).select();
        horizontalScroll.postDelayed(new Runnable() {
            public void run() {
                horizontalScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);
    }

    @Override
    public void onStop() {
        super.onStop();
        FamilyTabFragmentView.mPreviusPageNumber = 0;
        mfamilyId = null;
    }
}

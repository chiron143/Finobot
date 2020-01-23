package com.purplepath.purplepath.incomedetails.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomedetails.fragment.adapter.IncomeFamilyTabAdapter;
import com.purplepath.purplepath.incomedetails.fragment.model.GetIncomeModel;
import com.purplepath.purplepath.incomedetails.fragment.model.IncomeCategoryModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinesh on 27/06/16.
 */
public class IncomefromFamilyDetails extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "familyObj";
    private static final String ARG_PARAM2 = "categoryObj";
    private static final String ARG_PARAM3 = "incomeDetailObj";
    private static final String ARG_PARAM4 = "incomeDetailFamilyId";
    public static ImageView mAddTabView, mDeleteTabView;
    public static TabLayout mincomeTabLayout;
    public static IncomeFamilyTabAdapter adapter;
    public static ViewPager mincomeViewPager;
    private AddFamilyDetailModel mFamilyDetailModel;
    private   IncomeCategoryModel mCategoryModel;
    private  GetIncomeModel mIncomeDetailModel;
    private  View incomeDetailView;
    private OnActivityBackPressedListener mCallBackListener;
    Context mContext;
    Toolbar toolbar;
    String familyDetailsId = " ";
    int currentPosition = 0;
    private List<String> incometabTitles = new ArrayList<String>();

    public static IncomefromFamilyDetails newInstance(AddFamilyDetailModel param1, IncomeCategoryModel mCategoryModel, GetIncomeModel mIncomeDetailModel, FragmentManager fragmentManager, String familyID) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2,mCategoryModel);
        args.putSerializable(ARG_PARAM3,mIncomeDetailModel);
        args.putSerializable(ARG_PARAM4,familyID);
        IncomefromFamilyDetails fragment = new IncomefromFamilyDetails();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        if (getArguments() != null) {
            if(getArguments().containsKey(ARG_PARAM1))
            mFamilyDetailModel = (AddFamilyDetailModel) getArguments().getSerializable(ARG_PARAM1);
            if(getArguments().containsKey(ARG_PARAM2))
                mCategoryModel = (IncomeCategoryModel) getArguments().getSerializable(ARG_PARAM2);
            if(getArguments().containsKey(ARG_PARAM3))
                mIncomeDetailModel = (GetIncomeModel) getArguments().getSerializable(ARG_PARAM3);
            if(getArguments().containsKey(ARG_PARAM4))
                 familyDetailsId = (String) getArguments().getSerializable(ARG_PARAM4);
            Log.i("FamilyDetailsId", "IncomefromFamilyDetails FamilyDetailsId " + familyDetailsId);
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(incomeDetailView==null) {
            incomeDetailView = inflater.inflate(R.layout.fragment_income_form_details, container, false);

            mincomeTabLayout = incomeDetailView.findViewById(R.id.income_tab_layout_id);
            toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Income Details");
            mCallBackListener.setActionBarTitle("Income Details");
            mincomeViewPager = incomeDetailView.findViewById(R.id.income_pager);

            addTabView(mFamilyDetailModel, familyDetailsId);

            return incomeDetailView;
        }else {
            return incomeDetailView;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

//    private void callGetFamilyDetail(String userId) {
//
//
//        UtileKit.showSpinnerDialog(mContext, false);
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator
//                .createService(WebServiceCalls.class);
//        Call<AddFamilyDetailModel> call = webServiceObj.callFamilyDetailsService(userId);
//        call.enqueue(new Callback<AddFamilyDetailModel>() {
//            @Override
//            public void onResponse(Call<AddFamilyDetailModel> call, Response<AddFamilyDetailModel> response) {
//                //Log.e("CallBack", " response is " + call.toString());
//                UtileKit.dismisssSpinnerDialog();
//                AddFamilyDetailModel categoryModel = response.body();
//                if (categoryModel.getStatusCode().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//                    addTabView(categoryModel);
//
////                    addViewto(fragmentview,mIncomeCategoryModel)
//                } else {
//                    UtileKit.alertDialog(
//                            categoryModel.getData().getMessage(),
//                            mContext);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AddFamilyDetailModel> call, Throwable t) {
//
//            }
//        });
//    }

    private void addTabView(AddFamilyDetailModel categoryModel, String familyDetailsId) {
        mincomeTabLayout.addTab(mincomeTabLayout.newTab().setText("" + "1"));
        mincomeTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
       // mincomeTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        incometabTitles.add("" +"Self");
        if (categoryModel != null)
            for (int i = 0; i < categoryModel.getData().getFamily_details().size(); i++) {
                try {

                    mincomeTabLayout.addTab(mincomeTabLayout.newTab().setText("Tab" + "1"));
                    mincomeTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
                  //  mincomeTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
                    incometabTitles.add("" + categoryModel.getData().getFamily_details().get(i).getName());
                    if(familyDetailsId.equalsIgnoreCase(categoryModel.getData().getFamily_details().get(i).getFid())){
                        currentPosition = i+1;
                        Log.i("setCurrentItem", i + "currentPosition "+ currentPosition );
                        Log.i("setCurrentItem", i + "currentPosition "+ currentPosition + "familyDetailsId"+ familyDetailsId + "categoryModel.getData().getFamily_details().get(i).getFid() "+ categoryModel.getData().getFamily_details().get(i).getFid());

                    }
//                    //Log.e("Tab1", i + "");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        adapter = new IncomeFamilyTabAdapter(getChildFragmentManager(), incometabTitles,mCategoryModel,mFamilyDetailModel,mIncomeDetailModel);
        mincomeViewPager.setAdapter(adapter);
        mincomeTabLayout.setupWithViewPager(mincomeViewPager);
        mincomeViewPager.setOffscreenPageLimit(incometabTitles.size());
        mincomeTabLayout.setSmoothScrollingEnabled(true);
        mincomeTabLayout.setScrollPosition(incometabTitles.size()-1, 0f, true);
        mincomeViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mincomeTabLayout));
        IncomefromFamilyDetails.mincomeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                  mincomeViewPager.setCurrentItem(tab.getPosition());



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if(currentPosition!=0)
        mincomeViewPager.setCurrentItem(currentPosition);
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mincomeTabLayout.setSmoothScrollingEnabled(true);
                    mincomeTabLayout.setScrollPosition(incometabTitles.size()-1, 0f, true);
            }
        });

    }


}
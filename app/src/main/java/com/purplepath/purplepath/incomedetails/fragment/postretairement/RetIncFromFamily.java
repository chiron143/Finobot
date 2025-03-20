package com.purplepath.purplepath.incomedetails.fragment.postretairement;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.purplepath.purplepath.customview.CurrencyGroupView;
import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomedetails.fragment.adapter.RetIncomeFamilyTabAdapter;
import com.purplepath.purplepath.incomedetails.fragment.model.GetIncomeModel;
import com.purplepath.purplepath.incomedetails.fragment.model.IncomeCategoryModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinesh on 05/01/17.
 */
public class RetIncFromFamily  extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "familyObj";
    private static final String ARG_PARAM2 = "categoryObj";
    private static final String ARG_PARAM3 = "incomeDetailObj";
    private static final String ARG_PARAM4 = "postretir_familyId";
    public static ImageView mAddTabView, mDeleteTabView;
    public static TabLayout  mRetIncomeTabLayout;
    public static RetIncomeFamilyTabAdapter adapter;
    public static ViewPager mRetIncomeViewPager;
    private AddFamilyDetailModel mFamilyDetailModel;
    private IncomeCategoryModel mCategoryModel;
    private GetIncomeModel mIncomeDetailModel;
    private View incomeDetailView;
    private OnActivityBackPressedListener mCallBackListener;
    Context mContext;
    Toolbar toolbar;
    String familyDetailsId = " ";
    int currentPosition = 0;
    private List<String> incometabTitles = new ArrayList<String>();

    public static RetIncFromFamily newInstance(AddFamilyDetailModel param1, IncomeCategoryModel mCategoryModel, GetIncomeModel mIncomeDetailModel, FragmentManager fragmentManager, String familyID) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2,mCategoryModel);
        args.putSerializable(ARG_PARAM3,mIncomeDetailModel);
        args.putSerializable(ARG_PARAM4,familyID);
        RetIncFromFamily fragment = new RetIncFromFamily();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            if(getArguments().containsKey(ARG_PARAM1))
                mFamilyDetailModel = (AddFamilyDetailModel) getArguments().getSerializable(ARG_PARAM1);
            if(getArguments().containsKey(ARG_PARAM2))
                mCategoryModel = (IncomeCategoryModel) getArguments().getSerializable(ARG_PARAM2);
            if(getArguments().containsKey(ARG_PARAM3))
                mIncomeDetailModel = (GetIncomeModel) getArguments().getSerializable(ARG_PARAM3);
            if(getArguments().containsKey(ARG_PARAM4))
                familyDetailsId = (String) getArguments().getSerializable(ARG_PARAM4);
            Log.i("FamilyDetailsId", "RetIncFromFamily FamilyDetailsId " + familyDetailsId);
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

             mRetIncomeTabLayout = incomeDetailView.findViewById(R.id.income_tab_layout_id);
            toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Income Details");
            mCallBackListener.setActionBarTitle("Income Details");
            mRetIncomeViewPager = incomeDetailView.findViewById(R.id.income_pager);

            addTabView(mFamilyDetailModel);

            return incomeDetailView;
        }else {
            return incomeDetailView;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
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

    private void addTabView(AddFamilyDetailModel categoryModel) {
         mRetIncomeTabLayout.addTab( mRetIncomeTabLayout.newTab().setText("" + "1"));
         mRetIncomeTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
       //  mRetIncomeTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        incometabTitles.add("" +"Self");
        if (categoryModel != null)
            for (int i = 0; i < categoryModel.getData().getFamily_details().size(); i++) {
                try {

                     mRetIncomeTabLayout.addTab( mRetIncomeTabLayout.newTab().setText("Tab" + "1"));
                     mRetIncomeTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
                    // mRetIncomeTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
                    incometabTitles.add("" + categoryModel.getData().getFamily_details().get(i).getName());

                    if(familyDetailsId.equalsIgnoreCase(categoryModel.getData().getFamily_details().get(i).getFid())){
                        currentPosition = i+1;
                        Log.i("setCurrentItem", i + "currentPosition "+ currentPosition );
                        Log.i("setCurrentItem", i + "currentPosition "+ currentPosition + "familyDetailsId"+ familyDetailsId + "categoryModel.getData().getFamily_details().get(i).getFid() "+ categoryModel.getData().getFamily_details().get(i).getFid());

                    }
                    //Log.e("Tab1", i + "");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        adapter = new RetIncomeFamilyTabAdapter(getChildFragmentManager(), incometabTitles,mCategoryModel,mFamilyDetailModel,mIncomeDetailModel);
        mRetIncomeViewPager.setAdapter(adapter);
        mRetIncomeViewPager.setOffscreenPageLimit(incometabTitles.size());
         mRetIncomeTabLayout.setupWithViewPager(mRetIncomeViewPager);
        mRetIncomeViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener( mRetIncomeTabLayout));
        mRetIncomeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                mRetIncomeViewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(currentPosition!=0)
            mRetIncomeViewPager.setCurrentItem(currentPosition);

    }


}
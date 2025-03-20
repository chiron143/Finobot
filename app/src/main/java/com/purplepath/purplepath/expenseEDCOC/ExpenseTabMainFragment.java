package com.purplepath.purplepath.expenseEDCOC;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.adapter.TabViewAdapter;
import com.purplepath.purplepath.assets.AssetsDetailsFragment;
import com.purplepath.purplepath.expenseEDCOC.PostRetirementExpence.PostRetirementExpenseList;
import com.purplepath.purplepath.expenseEDCOC.PreRetirementExpence.PreRetirementExpenseList;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.myinterface.OnSelectedDoneClickListener;
import com.purplepath.purplepath.myinterface.OnSelectedDoneGetDataListener;

/**
 * Created by dinesh on 19/05/17.
 */

public class ExpenseTabMainFragment extends BaseFragment implements View.OnClickListener, OnSelectedDoneClickListener {

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    Toolbar toolbar;
    OnSelectedDoneClickListener onSelectedDoneClickListener;
    private CheckBox saleryViewDropBtn, basicDetailViewDrop, allowanceViewDrop,
            incomefromPropertyViewDrop, incomefromBusinessViewDrop, capitalGainViewDrop, incomefromOtherViewDrop;
    private LinearLayout basicaleryView, allowanceView, incomFromPropertyView,
            incomFromBusinessView, incomeFromCapitalGainView, incomeFromOtherSourceView;
    private RelativeLayout basisaleryView;
    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private Context mContext;
    private TabViewAdapter pagerAdapter;
    private String[] tabTitles = new String[]{"Pre-Retirement", "Post-Retirement"};
    private OnActivityBackPressedListener mCallBackListener;
    private int mTabPosition;
    private PreRetirementExpenseList preRetirementFragObj;
    private PostRetirementExpenseList postRetirementFragObj;

    public static ExpenseTabMainFragment newInstance(int mTabposition) {

        Bundle args = new Bundle();
        args.putInt("tabPosition", mTabposition);
        ExpenseTabMainFragment fragment = new ExpenseTabMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static ExpenseTabMainFragment newInstance() {

        Bundle args = new Bundle();
        ExpenseTabMainFragment fragment = new ExpenseTabMainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   callExpensesCategoriesService();
        setOnDoneClickListener(this);
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (getContext());
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        if (getArguments() != null)
            if (getArguments().containsKey("tabPosition")) {
                mTabPosition = getArguments().getInt("tabPosition");
            }
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View expensesdetailView = inflater.inflate(R.layout.fragment_expenses_details, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        // toolbar.setTitle("Expenses Details");
        mCallBackListener.setActionBarExpTitle("Expense Details");
        mTabLayout = expensesdetailView.findViewById(R.id.expenses_details_tab_layout_id);
        viewPager = expensesdetailView.findViewById(R.id.expenses_viewpager);


        mleftRelativeLayout = expensesdetailView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = expensesdetailView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = expensesdetailView.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        pagerAdapter = new TabViewAdapter(getChildFragmentManager());
        preRetirementFragObj=PreRetirementExpenseList.newInstance();
        postRetirementFragObj=PostRetirementExpenseList.newInstance();
        pagerAdapter.addFragment(preRetirementFragObj,"Pre-Retirement");
        pagerAdapter.addFragment(postRetirementFragObj,"Post-Retirement");
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(mTabPosition);
        mTabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        return expensesdetailView;
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

                try {

                    mCallBackListener.onActivityBackPressed();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
            case R.id.relative_right_arrow:
            {

                 addFragmenttoStack(new AssetsDetailsFragment());
             break;

            }
        }
    }

    public void setOnDoneClickListener(OnSelectedDoneClickListener onSelectedDoneClickListen) {
        onSelectedDoneClickListener = onSelectedDoneClickListen;
    }

    @Override
    public void onDoneSelected(OnSelectedDoneGetDataListener onSelectedDoneGetDataListener) {

    }
}
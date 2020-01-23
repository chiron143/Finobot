package com.purplepath.purplepath.incomedetails.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.expenseEDCOC.ExpenseTabMainFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomedetails.fragment.adapter.IncomeTypeTabAdapter;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinesh on 17/06/16.
 */
public class IncomeDetail extends BaseFragment implements View.OnClickListener {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private OnActivityBackPressedListener mCallBackListener;
    private TabLayout mTabLayoutIncome;
    private IncomeTypeTabAdapter adapter;
    private ViewPager mViewPager;
    Context mContext;
    Toolbar toolbar;
    List<String> tabTitles = new ArrayList<String>();
    private  View incomeDetailView=null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext=getContext();
        try {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mCallBackListener.setActionBarTitle("Income Details");
        if(incomeDetailView==null) {
            incomeDetailView = inflater.inflate(R.layout.fragment_income_detail_view, container, false);
            mleftRelativeLayout = incomeDetailView.findViewById(R.id.relative_left_arrow);
            mcenterRelativeLayout = incomeDetailView.findViewById(R.id.relative_center_home);
            mRightRelativeLayout = incomeDetailView.findViewById(R.id.relative_right_arrow);
            mleftRelativeLayout.setOnClickListener(this);
            mcenterRelativeLayout.setOnClickListener(this);
            mRightRelativeLayout.setOnClickListener(this);
            mTabLayoutIncome = incomeDetailView.findViewById(R.id.tab_layout_id);
            try {
                mTabLayoutIncome.addTab(mTabLayoutIncome.newTab().setText("Tab" + "1"));
                mTabLayoutIncome.setTabGravity(TabLayout.GRAVITY_FILL);
             //   mTabLayoutIncome.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
                tabTitles.add("Pre-Retirement ");
                tabTitles.add("Post-Retirement ");
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            mViewPager = incomeDetailView.findViewById(R.id.pager);

            adapter = new IncomeTypeTabAdapter(getChildFragmentManager(), tabTitles);
            mViewPager.setAdapter(adapter);
            mTabLayoutIncome.setupWithViewPager(mViewPager);
            mViewPager.setOffscreenPageLimit(tabTitles.size());
            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayoutIncome));
            FloatingActionButton fab = incomeDetailView.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                   // ExpensesDetailsFragment fragment = new ExpensesDetailsFragment();
                    //ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
//                    ExpenseTabMainFragment fragment =
                      addFragmenttoStack(ExpenseTabMainFragment.newInstance(0));
//                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commitAllowingStateLoss();

                }
            });
            return incomeDetailView;
        }
        else
        return incomeDetailView;
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
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.relative_left_arrow:
            {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
            startHomeActivity();
            }
            break;
            case R.id.relative_right_arrow:
            {
              addFragmenttoStack(ExpenseTabMainFragment.newInstance(0));
            }
        }
    }
}

//package com.purplepath.purplepath.expenses;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import com.google.android.material.tabs.TabLayout;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.viewpager.widget.ViewPager;
//import androidx.appcompat.widget.Toolbar;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.expenses.adapter.ExpensesViewPagerAdapter;
//import com.purplepath.purplepath.expensesRedesign.ExpensesReDesignDetailsFragment;
//import com.purplepath.purplepath.fragments.BaseFragment;
//import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
//
//public class ExpensesDetailsFragment extends BaseFragment implements View.OnClickListener{
//
//    private CheckBox saleryViewDropBtn,basicDetailViewDrop,allowanceViewDrop,
//            incomefromPropertyViewDrop,incomefromBusinessViewDrop,capitalGainViewDrop,incomefromOtherViewDrop;
//
//    private LinearLayout basicaleryView,allowanceView,incomFromPropertyView,
//            incomFromBusinessView,incomeFromCapitalGainView,incomeFromOtherSourceView;
//
//    private RelativeLayout basisaleryView;
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//    private OnActivityBackPressedListener mCallBackListener;
//    private TabLayout mTabLayout;
//    private ViewPager viewPager;
//    private Activity activity;
//    private Context mContext;
//    private ExpensesViewPagerAdapter pagerAdapter;
//    private String[] tabTitles = new String[]{"Essential", "Discretionary"};
//    Toolbar toolbar;
//    static OnCustomEssentialTabChange onCustomTabChange;
//    static OnCustomDiscretionaryTabChange onDisCustomTabChange;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        activity = getActivity();
//     //   callExpensesCategoriesService();
//
//          }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View expensesdetailView = inflater.inflate(R.layout.fragment_expenses_details, container, false);
//        mleftRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_left_arrow);
//        mcenterRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_center_home);
//        mRightRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_right_arrow);
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//        mRightRelativeLayout.setOnClickListener(this);
//        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle("Expenses Details");
//
//        mTabLayout = (TabLayout) expensesdetailView.findViewById(R.id.expenses_details_tab_layout_id);
//        viewPager = (ViewPager) expensesdetailView.findViewById(R.id.expenses_viewpager);
//
//        pagerAdapter = new ExpensesViewPagerAdapter(getChildFragmentManager(), tabTitles);
//        viewPager.setAdapter(pagerAdapter);
//        mTabLayout.setupWithViewPager(viewPager);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//                if(tab.getPosition()==1){
//                    onCustomTabChange.onEssentialTabChangeCallService();
//                }else{
//                    //onCustomTabChange.onEssentialTabChangeCallService();
//                    onDisCustomTabChange.onDiscretionaryTabChangeCallService();
//                 }
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//
//            }
//        });
//
//        return expensesdetailView;
//    }
//
//    public static void setCustomOnEssentialTabChangeListener(OnCustomEssentialTabChange onTabChange){
//        onCustomTabChange = onTabChange;
//    }
//
//    public static void setCustomOnDiscretionaryTabChangeListener(OnCustomDiscretionaryTabChange onDisTabChange){
//        onDisCustomTabChange = onDisTabChange;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mContext = context;
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.relative_left_arrow:
//            {
//                mCallBackListener.onActivityBackPressed();
//            }
//            break;
//            case R.id.relative_center_home:
//            {
//                Intent i = new Intent(activity, HomePageActivity.class);
//                startActivity(i);
//            }
//            break;
//            case R.id.relative_right_arrow:
//            {
//                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                ExpensesReDesignDetailsFragment fragment = new ExpensesReDesignDetailsFragment();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.remove(this);
//                fragmentTransaction.commitAllowingStateLoss();
//
//            }
//        }
//    }
//
//
//    public interface OnCustomEssentialTabChange{
//        void onEssentialTabChangeCallService();
//    }
//
//    public interface OnCustomDiscretionaryTabChange{
//        void onDiscretionaryTabChangeCallService();
//    }
//
//}

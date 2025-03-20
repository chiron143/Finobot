//package com.purplepath.purplepath.expensesRedesign;
//
//import android.content.Context;
//import android.os.Bundle;
//import com.google.android.material.tabs.TabLayout;
//import androidx.viewpager.widget.ViewPager;
//import androidx.appcompat.widget.Toolbar;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import com.finobot.finobot.R;
//import com.purplepath.purplepath.expensesRedesign.adapter.ExpensesReDesignViewPagerAdapter;
//import com.purplepath.purplepath.fragments.BaseFragment;
//import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
//import com.purplepath.purplepath.myinterface.OnSelectedDoneClickListener;
//import com.purplepath.purplepath.myinterface.OnSelectedDoneGetDataListener;
//
//public class ExpensesReDesignDetailsFragment extends BaseFragment implements View.OnClickListener, OnSelectedDoneClickListener {
//
//
//    private CheckBox saleryViewDropBtn,basicDetailViewDrop,allowanceViewDrop,
//            incomefromPropertyViewDrop,incomefromBusinessViewDrop,capitalGainViewDrop,incomefromOtherViewDrop;
//
//    private LinearLayout basicaleryView,allowanceView,incomFromPropertyView,
//            incomFromBusinessView,incomeFromCapitalGainView,incomeFromOtherSourceView;
//
//    private RelativeLayout basisaleryView;
//
//    private TabLayout mTabLayout;
//    private ViewPager viewPager;
//    private Context mContext;
//    private ExpensesReDesignViewPagerAdapter pagerAdapter;
//    private String[] tabTitles = new String[]{"Pre-Retirement", "Post-Retirement"};
//    Toolbar toolbar;
//    OnSelectedDoneClickListener onSelectedDoneClickListener;
//    private OnActivityBackPressedListener mCallBackListener;
//    private  int mTabPosition;
//
//    public static ExpensesReDesignDetailsFragment newInstance(int mTabposition) {
//
//        Bundle args = new Bundle();
//        args.putInt("tabPosition",mTabposition);
//        ExpensesReDesignDetailsFragment fragment = new ExpensesReDesignDetailsFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //   callExpensesCategoriesService();
//        setOnDoneClickListener(this);
//        try {
//            setHasOptionsMenu(true);
//            mCallBackListener = (OnActivityBackPressedListener) (getContext());
//        }catch(ClassCastException e)
//        {
//            e.printStackTrace();
//        }
//        if(getArguments()!=null)
//            if(getArguments().containsKey("tabPosition")) {
//                mTabPosition =getArguments().getInt("tabPosition");
//            }
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View expensesdetailView = inflater.inflate(R.layout.fragment_expenses_details, container, false);
//        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.getMenu().clear();
//       // toolbar.setTitle("Expenses Details");
//        mCallBackListener.setActionBarExpTitle("Expense Details");
//        mTabLayout = (TabLayout) expensesdetailView.findViewById(R.id.expenses_details_tab_layout_id);
//        viewPager = (ViewPager) expensesdetailView.findViewById(R.id.expenses_viewpager);
//
//        pagerAdapter = new ExpensesReDesignViewPagerAdapter(getChildFragmentManager(), tabTitles, onSelectedDoneClickListener);
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setCurrentItem(mTabPosition);
//        mTabLayout.setupWithViewPager(viewPager);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
////        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
////            @Override
////            public void onTabSelected(TabLayout.Tab tab) {
////                viewPager.setCurrentItem(tab.getPosition());
//////                if(tab.getPosition()==1){
//////                    onCustomTabChange.onEssentialTabChangeCallService();
//////                }else{
//////                    //onCustomTabChange.onEssentialTabChangeCallService();
//////                    onDisCustomTabChange.onDiscretionaryTabChangeCallService();
//////                }
////            }
////            @Override
////            public void onTabUnselected(TabLayout.Tab tab) {
////
////            }
////
////            @Override
////            public void onTabReselected(TabLayout.Tab tab) {
////
////            }
////        });
//
//        return expensesdetailView;
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.menu_empty_items, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//
//
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//
//            case R.id.ic_done_btn:
//
//                try{
//
//                    mCallBackListener.onActivityBackPressed();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                return true;
//        }
//        return super.onOptionsItemSelected(menuItem);
//    }
//
//    @Override
//    public void onDestroyOptionsMenu() {
//        super.onDestroyOptionsMenu();
//
//    }
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mContext = context;
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    public void setOnDoneClickListener(OnSelectedDoneClickListener onSelectedDoneClickListen){
//        onSelectedDoneClickListener = onSelectedDoneClickListen;
//    }
//
//    @Override
//    public void onDoneSelected(OnSelectedDoneGetDataListener onSelectedDoneGetDataListener) {
//
//    }
//}

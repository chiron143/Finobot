//package com.purplepath.purplepath.expensesRedesign.expensesselection;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import com.google.android.material.tabs.TabLayout;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.viewpager.widget.ViewPager;
//import androidx.appcompat.widget.Toolbar;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.assets.AssetsDetailsFragment;
//import com.purplepath.purplepath.expensesRedesign.adapter.ExpensesSelectionViewPagerAdapter;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.User_expense;
//import com.purplepath.purplepath.fragments.BaseFragment;
//import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
//import com.purplepath.purplepath.myinterface.OnCategoriesSelectedItemListener;
//import com.purplepath.purplepath.myinterface.OnSelectedDoneClickListener;
//import com.purplepath.purplepath.myinterface.OnSelectedDoneGetDataListener;
//
//import java.util.ArrayList;
//
//public class ExpensesSelectionDetailsFragment extends BaseFragment implements View.OnClickListener, OnCategoriesSelectedItemListener ,OnSelectedDoneClickListener {
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
//    private ExpensesSelectionViewPagerAdapter pagerAdapter;
//    private String[] tabTitles = new String[]{"Essential", "Discretionary"};
//    Toolbar toolbar;
//    OnCategoriesSelectedItemListener onCategoriesSelectedItem;
//    OnSelectedDoneClickListener onSelectedDoneClickListener;
//    private OnActivityBackPressedListener mCallBackListener;
//    private ArrayList<String> maddSelectionlevelOneList = new ArrayList<String>();
//    private ArrayList<String> maddSelectionlevelTwoList = new ArrayList<String>();
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//    private int size;
//    private int mtabPosition=0;
//
//    public static ExpensesSelectionDetailsFragment newInstance(int mTabPosition) {
//
//        Bundle args = new Bundle();
//        args.putInt("tabPosition",mTabPosition);
//        ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //   callExpensesCategoriesService();
//        setOnCategoriesSelectedItemListener(this);
//        try {
//            mCallBackListener = (OnActivityBackPressedListener) (getContext());
//        }catch(ClassCastException e)
//        {
//            e.printStackTrace();
//        }
//        if(getArguments()!=null)
//            if(getArguments().containsKey("tabPosition")) {
//                mtabPosition =getArguments().getInt("tabPosition");
//            }
//        setOnDoneClickListener(this);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View expensesdetailView = inflater.inflate(R.layout.fragment_expenses_details, container, false);
//
////        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
////        toolbar.setTitle("Expenses Details");
//       // toolbar.getMenu().clear();
//        mCallBackListener.setActionBarExpTitle("Expense Details");
////        mleftRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_left_arrow);
////        mcenterRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_center_home);
////        mRightRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_right_arrow);
////        mleftRelativeLayout.setOnClickListener(this);
////        mcenterRelativeLayout.setOnClickListener(this);
////        mRightRelativeLayout.setOnClickListener(this);
//        mTabLayout = (TabLayout) expensesdetailView.findViewById(R.id.expenses_details_tab_layout_id);
//        viewPager = (ViewPager) expensesdetailView.findViewById(R.id.expenses_viewpager);
//
//        pagerAdapter = new ExpensesSelectionViewPagerAdapter(getChildFragmentManager(), tabTitles, onCategoriesSelectedItem, onSelectedDoneClickListener,mtabPosition);
//        viewPager.setAdapter(pagerAdapter);
//        mTabLayout.setupWithViewPager(viewPager);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
//        viewPager.setCurrentItem(mtabPosition);
//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
////               if(tab.getPosition()==1){
////                    onCustomTabChange.onEssentialTabChangeCallService();
////                }else{
////                    //onCustomTabChange.onEssentialTabChangeCallService();
////                    onDisCustomTabChange.onDiscretionaryTabChangeCallService();
////                 }
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
//
//    public void setOnCategoriesSelectedItemListener(OnCategoriesSelectedItemListener onCategoriesSelectedItemListener){
//        onCategoriesSelectedItem = onCategoriesSelectedItemListener;
//    }
//
//    public void setOnDoneClickListener(OnSelectedDoneClickListener onSelectedDoneClickListen){
//        onSelectedDoneClickListener = onSelectedDoneClickListen;
//    }
//
//
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mContext = context;
//        try {
//            mCallBackListener = (OnActivityBackPressedListener) (context);
//        }catch(ClassCastException e)
//        {
//            e.printStackTrace();
//        }
//
//        catch(Exception e)
//        {}
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
//                Intent i = new Intent(getActivity(), HomePageActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
////                getActivity().finish();
//            }
//            break;
//            case R.id.relative_right_arrow:
//            {
//                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                AssetsDetailsFragment fragment = new AssetsDetailsFragment();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();
//
//            }
//        }
//    }
//
//    @Override
//    public void onSelectedItem(String id, String key, String leveltype,String groupId, ArrayList<User_expense> user_expenses) {
//        boolean flag = true;
//        if(leveltype.equalsIgnoreCase("leveltwo")) {
//            if(key.equalsIgnoreCase("add")) {
//                if(!maddSelectionlevelTwoList.contains(id)) {
//                    maddSelectionlevelTwoList.add(id);
//                }
//                if(!maddSelectionlevelOneList.contains(groupId))
//                {
//                    maddSelectionlevelOneList.add(groupId);
//                }
//                //Log.e("Selected ","leveltwo"+id);
//                for (int i = 0; i < maddSelectionlevelOneList.size(); i++) {
//                    //Log.e("Lev1List",""+maddSelectionlevelOneList.get(i));
//                }
//                for (int i = 0; i < maddSelectionlevelTwoList.size(); i++) {
//                    //Log.e("Lev2List",""+maddSelectionlevelTwoList.get(i));
//                }
//            }else if(key.equalsIgnoreCase("remove")){
//                if(!maddSelectionlevelTwoList.isEmpty()) {
//                    for (int i = 0; i < maddSelectionlevelTwoList.size(); i++) {
//                        if (maddSelectionlevelTwoList.get(i).equalsIgnoreCase(id)) {
//                            //Log.e("indetailsfragmentRemove",""+maddSelectionlevelTwoList.get(i));
//                            maddSelectionlevelTwoList.remove(i);
//                            try {
//                                ArrayList<String>groupIdArray=new ArrayList<>();
//
//
//                                for (int k = 0; k < maddSelectionlevelTwoList.size(); k++) {
//                                    for (int j = 0; j < user_expenses.get(0).getExpense_cat_lev2().size(); j++) {
//                                        if (maddSelectionlevelTwoList.get(k).equalsIgnoreCase(user_expenses.get(0).getExpense_cat_lev2().get(j).getId())) {
//                                            if (user_expenses.get(0).getExpense_cat_lev2().get(j).getLev1_id().equalsIgnoreCase(groupId)) {
//                                                groupIdArray.add(user_expenses.get(0).getExpense_cat_lev2().get(j).getLev1_id());
//                                                //Log.e("GroupNameDelete", "" + user_expenses.get(0).getExpense_cat_lev2().get(j).getLev2_name());
//                                            }
//                                        }
//                                    }
//                                }
//                                if(groupIdArray.isEmpty())
//                                {
//                                    if(!maddSelectionlevelOneList.isEmpty()) {
//                                        for (int l = 0; l < maddSelectionlevelOneList.size(); l++) {
//                                            if (maddSelectionlevelOneList.get(l).equalsIgnoreCase(groupId)) {
//                                                maddSelectionlevelOneList.remove(l);
//                                                //Log.e("Sucess","Done");
//                                            }
//                                        }
//                                    }
//                                }
//                            }catch (ArrayIndexOutOfBoundsException e1)
//                            {
//                                e1.printStackTrace();
//                            }
//                            catch (Exception e)
//                            {
//                                e.printStackTrace();
//                            }
//
//                        }
//                    }
//                }
//            }
//        }else if(leveltype.equalsIgnoreCase("levelone")){
//            if(key.equalsIgnoreCase("add")) {
//                if(!maddSelectionlevelOneList.isEmpty()) {
//                    for (int i = 0; i < maddSelectionlevelOneList.size(); i++) {
//                        //  //Log.e("indetailslevelone",""+maddSelectionlevelOneList.get(i));
//                        if (maddSelectionlevelOneList.get(i) == id) {
//                            flag = false;
//                        }
//                    }
//                }
//                  if(flag) {
//                      maddSelectionlevelOneList.add(id);
//                  }
//
//            }else if(key.equalsIgnoreCase("remove")){
//                if(!maddSelectionlevelOneList.isEmpty()) {
//                    for (int i = 0; i < maddSelectionlevelOneList.size(); i++) {
//                        if (maddSelectionlevelOneList.get(i) == id) {
//                            maddSelectionlevelOneList.remove(i);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onDoneSelected(OnSelectedDoneGetDataListener selectedDoneGetDataListener) {
//        //Log.e("Done",""+maddSelectionlevelTwoList.toArray().toString());
//        selectedDoneGetDataListener.onDoneSelectedGetData(maddSelectionlevelOneList, maddSelectionlevelTwoList);
//    }
//
//
//}

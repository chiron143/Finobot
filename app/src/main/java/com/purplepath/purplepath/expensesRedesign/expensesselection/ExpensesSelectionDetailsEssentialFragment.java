//package com.purplepath.purplepath.expensesRedesign.expensesselection;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ExpandableListView;
//import android.widget.RelativeLayout;
//
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.assets.AssetsDetailsFragment;
//import com.purplepath.purplepath.expenseEDCOC.ExpenseTabMainFragment;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesDetailModel;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelOneData;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelThreeData;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelTwoData;
//
//import com.purplepath.purplepath.expensesRedesign.adapter.ExpListViewAdapterWithCheckbox;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.ExpensesSelectionAddModel;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.GetExpensesDetailsModel;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.User_expense;
//import com.purplepath.purplepath.expensesRedesign.updateexpensedetailsmodel.ExpensesUpdateModel;
//import com.purplepath.purplepath.fragments.BaseFragment;
//import com.purplepath.purplepath.myinterface.OnCategoriesSelectedItemListener;
//import com.purplepath.purplepath.myinterface.OnSelectedDoneClickListener;
//import com.purplepath.purplepath.myinterface.OnSelectedDoneGetDataListener;
//import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
//import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class ExpensesSelectionDetailsEssentialFragment extends BaseFragment implements View.OnClickListener , OnSelectedDoneGetDataListener{
//    // http://stackoverflow.com/questions/16516107/how-can-i-store-hashmapstring-arrayliststring-inside-a-list
//    private ArrayList<ExpensesLevelOneData> expensesLevelOneData;
//    private ArrayList<ExpensesLevelTwoData> expensesLevelTwoData;
//    private ArrayList<ExpensesLevelThreeData> expensesLevelThreeData;
//    private ArrayList<ExpensesLevelOneData> myLeveloneData;
//    private ExpensesLevelOneData mexpensesOverallData;
//    private HashMap<String, ArrayList<ExpensesLevelTwoData>> mEssentialListDataChild;
//    private ArrayList<ExpensesLevelOneData> mEssentialListDataGroup;
//    private HashMap<String, ArrayList<ExpensesLevelTwoData>> mDiscretionaryListDataChild;
//    private ArrayList<ExpensesLevelOneData> mDiscretionaryListDataGroup ;
//    private ArrayList<String> mArrayListDataChild ;
//
//    private ExpensesDetailModel mExpensesDetailModel;
//    private ExpListViewAdapterWithCheckbox navigationAdapter;
//    private Context mContext;
//    private ExpandableListView navigationView;
//    public  static SharedPreferences anonymePref;
//    public static String updateexpenseId;
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//    FragmentManager fragmentManager;
//    FragmentTransaction fragmentTransaction;
//    OnCategoriesSelectedItemListener onCategoriesSelectedItmListener;
//    OnSelectedDoneClickListener onSelectedDoneClickListener;
//    OnSelectedDoneGetDataListener onSelectedDoneGetDataListener;
//    String levelZero="",leveloneData,leveltwoData;
//    ExpensesSelectionAddModel expensesSelectionAddModel;
//    int size,mTabposition;
//    private ArrayList<User_expense> user_expenses;
//
//    GetExpensesDetailsModel  getExpensesDetailsModel;
//    public ExpensesSelectionDetailsEssentialFragment(OnCategoriesSelectedItemListener onCategoriesSelectedItm, OnSelectedDoneClickListener onSelectedDoneClickListe,int mTabposition){
//        onCategoriesSelectedItmListener = onCategoriesSelectedItm;
//        onSelectedDoneClickListener=  onSelectedDoneClickListe;
//        this.mTabposition=mTabposition;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mContext = getContext();
////        anonymePref = getActivity().getSharedPreferences("service", Context.MODE_PRIVATE);
//        setRetainInstance(true);
//        setHasOptionsMenu(true);
//        setOnDoneSelectedGetDataListener(this);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View expensesdetailView = inflater.inflate(R.layout.fragment_expenses_selection_essential, container, false);
//        size = HomePageActivity.expensesDetailsSize;
//
//        fragmentManager = getActivity().getSupportFragmentManager();
//        mleftRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_left_arrow);
//        mcenterRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_center_home);
//        mRightRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_right_arrow);
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//        mRightRelativeLayout.setOnClickListener(this);
//
//        expensesLevelOneData = new ArrayList<ExpensesLevelOneData>();
//        expensesLevelTwoData = new ArrayList<ExpensesLevelTwoData>();
//        expensesLevelThreeData = new ArrayList<ExpensesLevelThreeData>();
//        myLeveloneData = new ArrayList<ExpensesLevelOneData>();
//        mexpensesOverallData = new ExpensesLevelOneData();
//        mEssentialListDataChild = new HashMap<String, ArrayList<ExpensesLevelTwoData>>();
//        mEssentialListDataGroup = new ArrayList<ExpensesLevelOneData>();
//        mDiscretionaryListDataChild = new HashMap<String, ArrayList<ExpensesLevelTwoData>>();
//        mDiscretionaryListDataGroup = new ArrayList<ExpensesLevelOneData>();
//        mArrayListDataChild = new ArrayList<String>();
//
//        navigationView = (ExpandableListView) expensesdetailView.findViewById(R.id.expenses_redesign_essential_view);
//
//        FloatingActionButton fab = (FloatingActionButton) expensesdetailView.findViewById(R.id.redesign_essential_fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                //ExpensesReDesignDetailsFragment fragment = new ExpensesReDesignDetailsFragment();
////                AssetsDetailsFragment fragment = new AssetsDetailsFragment();
////                fragmentTransaction.replace(R.id.fragment_container, fragment);
////                fragmentTransaction.addToBackStack(null);
////                fragmentTransaction.commitAllowingStateLoss();
//            }
//        });
//        callExpensesCategoriesService();
//        return expensesdetailView;
//    }
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//    }
//
//
//    private void callExpensesCategoriesService(){
//        //UtileKit.showSpinnerDialog(mContext, false);
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<ExpensesDetailModel> call = webServiceObj.callExpensesCategoriesService();
//        call.enqueue(new Callback<ExpensesDetailModel>() {
//            @Override
//            public void onResponse(Call<ExpensesDetailModel> call, Response<ExpensesDetailModel> response) {
//                //Log.e("CallBack", " response is " + call.toString());
//                mExpensesDetailModel = response.body();
//                UtileKit.dismisssSpinnerDialog();
//                getAsArrayList(mExpensesDetailModel);
//            }
//            @Override
//            public void onFailure(Call<ExpensesDetailModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
//                UtileKit.dismisssSpinnerDialog();
//                UtileKit.alertRetrofitExceptionDialog( mContext, t);
//            }
//        });
//    }
//
//
//    private void getAsArrayList(ExpensesDetailModel expensesDetailModel){
//        if (expensesDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//            expensesLevelOneData = expensesDetailModel.getData().getExpense_cat_lev1();
//            expensesLevelTwoData = expensesDetailModel.getData().getExpense_cat_lev2();
//            expensesLevelThreeData  = expensesDetailModel.getData().getExpense_cat_lev3();
//            if(expensesLevelOneData !=null) {
//                for (int i = 0; i < expensesLevelOneData.size(); i++) {
//                    String level1Id = expensesLevelOneData.get(i).getId();
//                    myLeveloneData.add(expensesLevelOneData.get(i));
//                    // //Log.e("myLevelOneData",""+expensesLevelOneData.get(i).getLev1_name());
//                    ArrayList<ExpensesLevelTwoData> myLevelTwolist = new ArrayList<ExpensesLevelTwoData>();
//                    for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                        String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                        String level3Id = null;
//                        if (level1Id.equalsIgnoreCase(level2Id)) {
//                            myLevelTwolist.add(expensesLevelTwoData.get(j));
//                            ////Log.e("myLevelTwoData",""+expensesLevelTwoData.get(j).getLev2_name());
//                            level3Id = expensesLevelTwoData.get(j).getId();
//                            myLeveloneData.get(i).setExpensesLevelTwoData(myLevelTwolist);
//                        }
//                        ArrayList<ExpensesLevelThreeData> myLevelThreelist = new ArrayList<ExpensesLevelThreeData>();
//                        if(expensesLevelThreeData !=null) {
//                            for (int k = 0; k < expensesLevelThreeData.size(); k++) {
//                                String mylevel3Id = expensesLevelThreeData.get(k).getLev2_id();
//                                if(level3Id != null) {
//                                    if (level3Id.equalsIgnoreCase(mylevel3Id)) {
//                                        myLevelThreelist.add(expensesLevelThreeData.get(k));
//                                        // //Log.e("myLevelThreeData", "" + expensesLevelThreeData.get(k).getLev3_name());
//                                        ////Log.e("myLeveltwosizeData", "jjjj"+j+"myj" + myLeveloneData.get(i).getExpensesLevelTwoData());
//                                        myLeveloneData.get(i).getExpensesLevelTwoData().get(myLeveloneData.get(i).getExpensesLevelTwoData().size()-1).setExpensesLevelThreeData(myLevelThreelist);
//                                        //myLevelTwolist.get(k).setExpensesLevelThreeData(myLevelThreelist);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                mexpensesOverallData.setExpensesLevelOneData(myLeveloneData);
//            }
//        }
//        addEssentialExpensesData();
//    }
//
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        //  AddExpensesService();
//    }
//
//
//    private void addDiscretionaryExpensesData(){
//        for (int m = 0; m < mexpensesOverallData.getExpensesLevelOneData().size(); m++) {
//            // //Log.e("mygetLevelOneData", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getLev1_name());
//            if (mexpensesOverallData.getExpensesLevelOneData().get(m).getType().equalsIgnoreCase("Discretionary")) {
//                String levelOneName = mexpensesOverallData.getExpensesLevelOneData().get(m).getLev1_name();
//                ExpensesLevelOneData expensesLevelOneData = new ExpensesLevelOneData();
//                expensesLevelOneData = mexpensesOverallData.getExpensesLevelOneData().get(m);
//                mDiscretionaryListDataGroup.add(expensesLevelOneData);
//                if (mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData() != null) {
//                    if (!mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().isEmpty()) {
//                        ArrayList<ExpensesLevelTwoData> expensesLevelTwoData = new ArrayList<ExpensesLevelTwoData>();
//                        for (int l = 0; l < mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().size(); l++) {
//                            // //Log.e("mygetLevelTwoData", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getLev2_name());
//                            String levelTwoName = mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getLev2_name();
//                            expensesLevelTwoData.add(mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l));
//                            mDiscretionaryListDataChild.put(levelOneName, expensesLevelTwoData);
//                            if (mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getExpensesLevelThreeData() != null) {
//                                if (!mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getExpensesLevelThreeData().isEmpty()) {
//                                    for (int n = 0; n < mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getExpensesLevelThreeData().size(); n++) {
//                                        //  //Log.e("mygetLevelThreeData", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getExpensesLevelThreeData().get(n).getLev3_name());
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        ArrayList<ExpensesLevelTwoData> expensesLevelTwoData = new ArrayList<ExpensesLevelTwoData>();
//                        mDiscretionaryListDataChild.put(levelOneName, expensesLevelTwoData);
//                    }
//                } else {
//                    ArrayList<ExpensesLevelTwoData> expensesLevelTwoData = new ArrayList<ExpensesLevelTwoData>();
//                    mDiscretionaryListDataChild.put(levelOneName, expensesLevelTwoData);
//                }
//            }
//        }
//
//        navigationAdapter = new ExpListViewAdapterWithCheckbox(mContext, mDiscretionaryListDataGroup, mDiscretionaryListDataChild, "Discretionary", onCategoriesSelectedItmListener);
//        navigationView.setAdapter(navigationAdapter);
//    }
//
//
//    private void addEssentialExpensesData(){
//        for (int m = 0; m < mexpensesOverallData.getExpensesLevelOneData().size(); m++) {
//            // //Log.e("mygetLevelOneData", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getLev1_name());
//          //  //Log.e("mygetLevelOneData", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getType());
//           // //Log.e("mygetLevelgetLev1_name", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getLev1_name());
//            if (mexpensesOverallData.getExpensesLevelOneData().get(m).getType().equalsIgnoreCase("Essential")) {
//                String levelOneName = mexpensesOverallData.getExpensesLevelOneData().get(m).getLev1_name();
//                ExpensesLevelOneData expensesLevelOneData = new ExpensesLevelOneData();
//                expensesLevelOneData = mexpensesOverallData.getExpensesLevelOneData().get(m);
//                mEssentialListDataGroup.add(expensesLevelOneData);
//
//                if (mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData() != null) {
//                    if (!mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().isEmpty()) {
//                        ArrayList<ExpensesLevelTwoData> expensesLevelTwoData = new ArrayList<ExpensesLevelTwoData>();
//                        for (int l = 0; l < mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().size(); l++) {
//                            // //Log.e("mygetLevelTwoData", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getLev2_name());
//                            String levelTwoName = mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getLev2_name();
//                            expensesLevelTwoData.add(mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l));
//                            mEssentialListDataChild.put(levelOneName, expensesLevelTwoData);
//
//                            if (mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getExpensesLevelThreeData() != null) {
//                                if (!mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getExpensesLevelThreeData().isEmpty()) {
//                                    for (int n = 0; n < mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getExpensesLevelThreeData().size(); n++) {
//                                        //  //Log.e("mygetLevelThreeData", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getExpensesLevelThreeData().get(n).getLev3_name());
//                                    }
//                                }
//                            }
//                        }
//                    } else {
//                        ArrayList<ExpensesLevelTwoData> expensesLevelTwoData = new ArrayList<ExpensesLevelTwoData>();
//                        mEssentialListDataChild.put(levelOneName, expensesLevelTwoData);
//                    }
//                } else {
//                    ArrayList<ExpensesLevelTwoData> expensesLevelTwoData = new ArrayList<ExpensesLevelTwoData>();
//                    mEssentialListDataChild.put(levelOneName, expensesLevelTwoData);
//                }
//            }
//        }
//        navigationAdapter = new ExpListViewAdapterWithCheckbox(mContext, mEssentialListDataGroup, mEssentialListDataChild, "Essential", onCategoriesSelectedItmListener);
//        navigationView.setAdapter(navigationAdapter);
//    }
//
//
////    public static void removePersistedAnonymePref(String key) {
////        anonymePref.edit().remove(key).commit();
////    }
////
////    public static void persistingAnonymePref(String key, String valString) {
////        anonymePref.edit().putString(key, valString).commit();
////    }
////
////    public static void persistingAnonymePref(String key, boolean valBool) {
////        anonymePref.edit().putBoolean(key, valBool).commit();
////    }
////
////    public static String getPersistedAnonymePref(String key) {
////        if (anonymePref != null && anonymePref.contains(key)) {
////            return anonymePref.getString(key, null);
////        } else {
////            return null;
////        }
////    }
////
////    public static Boolean getPersistedAnonymeBoolPref(String key) {
////        if (anonymePref != null && anonymePref.contains(key)) {
////            return anonymePref.getBoolean(key, false);
////        } else {
////            return false;
////        }
////    }
////
////    public static String getPersistedAnonymePref(String key, String _defaultValue) {
////        return anonymePref.getString(key, _defaultValue);
////    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.relative_left_arrow:
//            {
//                getActivity().getSupportFragmentManager().popBackStack();
////                onSelectedDoneClickListener.onDoneSelected(onSelectedDoneGetDataListener);
//            }
//            break;
//            case R.id.relative_center_home:
//            {
////                onSelectedDoneClickListener.onDoneSelected(onSelectedDoneGetDataListener);
//                Intent i = new Intent(getActivity(), HomePageActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//            }
//            break;
//            case R.id.relative_right_arrow:
//            {
////                onSelectedDoneClickListener.onDoneSelected(onSelectedDoneGetDataListener);
//                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                AssetsDetailsFragment fragment = new AssetsDetailsFragment();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(fragment.getClass().getName());
//                fragmentTransaction.commitAllowingStateLoss();
//
//            }
//        }
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.menu_risk, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.ic_clear_btn:
//                if (menuItem.getItemId() == R.id.ic_clear_btn)
//
//                    return true;
//            case R.id.ic_done_btn:
//                onSelectedDoneClickListener.onDoneSelected(onSelectedDoneGetDataListener);
//                UtileKit.showSpinnerDialog(mContext,false);
////                if (menuItem.getItemId() == R.id.ic_done_btn)
////                    fragmentTransaction = fragmentManager.beginTransaction();
////                // ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
////                ExpensesReDesignDetailsFragment fragment = new ExpensesReDesignDetailsFragment();
////                // AssetsDetailsFragment fragment = new AssetsDetailsFragment();
////                fragmentTransaction.replace(R.id.fragment_container, fragment);
////                fragmentTransaction.addToBackStack(null);
////                fragmentTransaction.commitAllowingStateLoss();
//                return true;
//        }
//        return super.onOptionsItemSelected(menuItem);
//    }
//
//    public void setOnDoneSelectedGetDataListener(OnSelectedDoneGetDataListener onSelectedDoneGetDataLis){
//        onSelectedDoneGetDataListener = onSelectedDoneGetDataLis;
//    }
//
//    @Override
//    public void onDoneSelectedGetData(ArrayList<String> levelone, ArrayList<String> leveltwo) {
//
//        if (!leveltwo.isEmpty()) {
//            if (leveltwo.size() == 1) {
//                leveltwoData = leveltwo.get(0);
//            } else {
//                for (int i = 0; i < leveltwo.size(); i++) {
//                    if (i == 0) {
//                        leveltwoData = leveltwo.get(i).concat(",");
//                    } else {
//                        leveltwoData += leveltwo.get(i).concat(",");
//                    }
//                }
//            }
//        }
//
//        if (!levelone.isEmpty()) {
//            if (levelone.size() == 1) {
//                leveloneData = levelone.get(0);
//            } else {
//                for (int i = 0; i < levelone.size(); i++) {
//                    if (i == 0) {
//                        leveloneData = levelone.get(i).concat(",");
//                    } else {
//                        leveloneData += levelone.get(i).concat(",");
//                    }
//                }
//            }
//        }
//        if(HomePageActivity.getExpensesDetailsModel != null) {
//            if(HomePageActivity.getExpensesDetailsModel.getData()!=null) {
//                if (HomePageActivity.getExpensesDetailsModel.getData().getUser_expense() != null) {
//                    if (!HomePageActivity.getExpensesDetailsModel.getData().getUser_expense().isEmpty()) {
//                        user_expenses = HomePageActivity.getExpensesDetailsModel.getData().getUser_expense();
//                        callGetExpensesUpdateModelService(leveloneData, leveltwoData, user_expenses.get(0).getId());
//                    }
//                }
//            }
//        else {
//                callAddExpensesService(leveloneData, leveltwoData);
//            }
//        }else {
//            callAddExpensesService(leveloneData, leveltwoData);
//        }
//    }
//
//    public void callAddExpensesService(String levelOneData, String leveltwoData){
//        UtileKit.showSpinnerDialog(mContext,false);
//        WebServiceCalls webServiceObj;
//        String levetone=levelOneData, levelthree="1";
//        expensesSelectionAddModel = new ExpensesSelectionAddModel();
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<ExpensesSelectionAddModel> call = webServiceObj.callGetExpensesAddModelService(
//                UtileKit.getPersistedPurplePathPref("user_id"), "Y",levelZero, levetone, leveltwoData, levelthree,"0");
//        call.enqueue(new Callback<ExpensesSelectionAddModel>() {
//            @Override
//            public void onResponse(Call<ExpensesSelectionAddModel> call, Response<ExpensesSelectionAddModel> response) {
//                //Log.e("callAddExpensesService", " selectionresponse is " + call.toString());
//                expensesSelectionAddModel = response.body();
//                //Log.e("callAddExpensesService", " selectionresponse is " + call.toString());
//                UtileKit.dismisssSpinnerDialog();
//                callGetExpensesService();
//                            }
//            @Override
//            public void onFailure(Call<ExpensesSelectionAddModel> call, Throwable t) {
////                //Log.e("callAddExpenesesCallBack", " selectionfailure is " + t);
//                UtileKit.dismisssSpinnerDialog();
//                UtileKit.alertRetrofitExceptionDialog( mContext, t);
//            }
//        });
//
//    }
//
//
//    public   void callGetExpensesUpdateModelService(String levelOneData, String leveltwoData, String exp_id){
//        UtileKit.showSpinnerDialog(mContext,false);
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<ExpensesUpdateModel> call = webServiceObj.callGetExpUpdateModelService(
//                UtileKit.getPersistedPurplePathPref("user_id"), levelOneData, leveltwoData, "1","Y","0");
////                UtileKit.getPersistedPurplePathPref("user_id"), levelOneData, leveltwoData, "1", exp_id);
//        call.enqueue(new Callback<ExpensesUpdateModel>() {
//            @Override
//            public void onResponse(Call<ExpensesUpdateModel> call, Response<ExpensesUpdateModel> response) {
//                UtileKit.dismisssSpinnerDialog();
//                callGetExpensesService();
//
//            }
//            @Override
//            public void onFailure(Call<ExpensesUpdateModel> call, Throwable t) {
//
//                UtileKit.dismisssSpinnerDialog();
//                UtileKit.alertRetrofitExceptionDialog( mContext, t);
//            }
//        });
//
//    }
//
//
//    public void callGetExpensesService(){
//        WebServiceCalls webServiceObj;
//        getExpensesDetailsModel = null;
//        UtileKit.showSpinnerDialog(mContext,false);
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<GetExpensesDetailsModel> call = webServiceObj.callGetExpensesDetailsService(UtileKit.getPersistedPurplePathPref("user_id"));
//        call.enqueue(new Callback<GetExpensesDetailsModel>() {
//            @Override
//            public void onResponse(Call<GetExpensesDetailsModel> call, Response<GetExpensesDetailsModel> response) {
//                HomePageActivity.getExpensesDetailsModel = response.body();
//                UtileKit.dismisssSpinnerDialog();
//                if(HomePageActivity.getExpensesDetailsModel.getData().getUser_expense() !=null) {
//                    if (!HomePageActivity.getExpensesDetailsModel.getData().getUser_expense().isEmpty()) {
//                        try {
//                            HomePageActivity.expensesDetailsSize = HomePageActivity.getExpensesDetailsModel.getData().getUser_expense().size();
//
//                            fragmentTransaction = fragmentManager.beginTransaction();
//                            getActivity().getSupportFragmentManager().popBackStack();
//                            // ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
//                            ExpenseTabMainFragment fragment =  ExpenseTabMainFragment.newInstance(mTabposition);
//                            // AssetsDetailsFragment fragment = new AssetsDetailsFragment();
//                            fragmentTransaction.replace(R.id.fragment_container, fragment);
//                            fragmentTransaction.addToBackStack(null);
//                            fragmentTransaction.commitAllowingStateLoss();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            }
//            @Override
//            public void onFailure(Call<GetExpensesDetailsModel> call, Throwable t) {
//
//                UtileKit.dismisssSpinnerDialog();
//                UtileKit.alertRetrofitExceptionDialog( mContext, t);
//            }
//        });
//
//    }
//}
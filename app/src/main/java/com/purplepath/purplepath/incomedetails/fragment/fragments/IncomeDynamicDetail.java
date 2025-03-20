//package com.purplepath.purplepath.incomedetails.fragment.fragments;
//
//import android.app.DialogFragment;
//import android.app.FragmentManager;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import androidx.annotation.Nullable;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.tabs.TabLayout;
//import com.google.android.material.textfield.TextInputLayout;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.appcompat.widget.Toolbar;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.calculator.CalculatorAct;
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.customview.CurrencyEditText;
//import com.purplepath.purplepath.customview.CustomCalenderImageView;
//import com.purplepath.purplepath.dia//Log.expectedIncrementDialogFragment;
//import com.purplepath.purplepath.expenseEDCOC.ExpenseTabMainFragment;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelZeroData;
//import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
//import com.purplepath.purplepath.incomedetails.fragment.IncomefromFamilyDetails;
//import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncLev2SelLisInterface;
//import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncSelecOnDismisInterf;
//import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dialog.LevelOneSelectorDaialog;
//import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dialog.LevelSecondSelectionDialog;
//import com.purplepath.purplepath.incomedetails.fragment.model.GetIncomeModel;
//import com.purplepath.purplepath.incomedetails.fragment.model.IncomeCategoryModel;
//import com.purplepath.purplepath.incomedetails.fragment.model.IncomeInputModel;
//import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev1;
//import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev2;
//import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev3;
//import com.purplepath.purplepath.incomedetails.fragment.model.User_incomes;
//import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
//import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
//import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//
///**
// * Created by dinesh on 19/08/16.
// */
//public class IncomeDynamicDetail extends Fragment implements View.OnClickListener, ExpectedIncrementDialogFragment.OnExpectedValueListener, CurrencyEditText.OnTextChangeEditText, IncSelecOnDismisInterf, IncLev2SelLisInterface {
//    private static String ARG_PARAM1 = "position";
//    private static String ARG_PARAM2 = "familyObj";
//    private static String ARG_PARAM3 = "categoryObj";
//    private static String ARG_PARAM4 = "incomeObj";
//    String UserId, familyId = "", mOwnUser, currentTabName;
//    ArrayList<String> lifeExpactancyList = new ArrayList<>();
//    ArrayList<String> selLev1List = new ArrayList<>(), selLev2List = new ArrayList<>(), selLev3List = new ArrayList<>();
//    private int mPagePosition;
//    private int mPrevPosition;
//    private IncomeCategoryModel mCategoryModel;
//    private AddFamilyDetailModel mFamilyDetailModel;
//    private HashMap<String, Income_cat_lev1> incomeFilterHashMap = new HashMap<>();
//    private HashMap<String, ArrayList<CurrencyEditText>> curencyListMap = new HashMap<>();
//    private HashMap<String, ArrayList<ImageView>> lifeExpactancyImgView = new HashMap<>();
//    private User_incomes userIncomeDetailModel;
//    private GetIncomeModel mIncomeModel;
//    private JSONArray jsonArray = new JSONArray();
//    private Context mContext;
//    private View incomeDetailView;
//    private IncSelecOnDismisInterf incomeLev1SelectLisaner;
//    private IncLev2SelLisInterface incLev2SelLisInterface;
//    private LinearLayout viewParentLinerView;
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//    private OnActivityBackPressedListener mCallBackListener;
//    private Toolbar toolbar;
//
//
//    public static final String PARENT_CLASS_SOURCE = "com.gp89developers.example.MainActivity";
//    public static final String TITLE = "";
//    private EditText editText;
//
//    CurrencyEditText nameEditview ;
//
//
//
//    public static Fragment newInstance(int position, IncomeCategoryModel mCategoryModel, AddFamilyDetailModel mFamilyDetailModel, GetIncomeModel incomeModel) {
//        IncomeDynamicDetail fragment = new IncomeDynamicDetail();
//        Bundle args = new Bundle();
//        args.putInt(ARG_PARAM1, position);
//        args.putSerializable(ARG_PARAM2, mFamilyDetailModel);
//        args.putSerializable(ARG_PARAM3, mCategoryModel);
//        args.putSerializable(ARG_PARAM4, incomeModel);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //Log.e("Income", "onCreate");
////        setRetainInstance(true);
//        setIncomeLev1SelectLisaner(this);
//        setIncomeLev2SelectionLisaner(this);
//        incomeFilterHashMap = new HashMap<>();
//
//            if (getArguments() != null) {
//                if (getArguments().containsKey(ARG_PARAM1)) {
//                    mPagePosition = (getArguments().getInt(ARG_PARAM1));
//                    Log.i("IncomeDynamic position", "" + mPagePosition);
//                }
//
//                if (mFamilyDetailModel == null)
//                    if (getArguments().containsKey(ARG_PARAM2))
//                        mFamilyDetailModel = (AddFamilyDetailModel) getArguments().getSerializable(ARG_PARAM2);
//                if (mCategoryModel == null)
//                    if (getArguments().containsKey(ARG_PARAM3))
//                        mCategoryModel = (IncomeCategoryModel) getArguments().getSerializable(ARG_PARAM3);
//
//                if (mCategoryModel != null) {
//                    int size = mCategoryModel.getData().getIncome_cat_lev1().size();
//                    for (int i = 0; i < size; i++) {
//                        //Log.e("LevCheck", "" + mCategoryModel.getData().getIncome_cat_lev1().get(i).getLev1_name());
//                        Income_cat_lev1 obj = mCategoryModel.getData().getIncome_cat_lev1().get(i);
//                        obj.setCat_lev2List(getLev2CatgoryList(mCategoryModel.getData().getIncome_cat_lev1().get(i).getId(), mCategoryModel));
//
//                        incomeFilterHashMap.put("" + i, obj);
//                    }
////                    addParentView(incomeFilterHashMap,viewParentLinerView);
//
//                }
//                if (mIncomeModel == null)
//                    if (getArguments().containsKey(ARG_PARAM4)) {
//                        mIncomeModel = (GetIncomeModel) getArguments().getSerializable(ARG_PARAM4);
//
//                    }
//            }
//
//
//            if(mPagePosition== 0 && incomeFilterHashMap.isEmpty()){
//                    familyId = "" + 0;
//                    mOwnUser = "Y";
//                //Log.e("Level1______",""+selLev1List.size());
//                FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
//                DialogFragment newFragment = LevelOneSelectorDaialog.newInstance(familyId, incomeFilterHashMap,
//                        incomeLev1SelectLisaner, selLev1List);
//                newFragment.show(fm, "dialog");
//
//            }
//
//        }
//
//
//    /**
//     * private ArrayList<String> getLev3CatgoryList(String lev1headerTag, String lev2headerTag) {
//     * ArrayList<String> arrayList = new ArrayList<>();
//     * for (int i = 0; i < mCategoryModel.getData().getIncome_cat_lev1().size() - 1; i++) {
//     * if (mCategoryModel.getData().getIncome_cat_lev1().get(i).getLev1_name().equals(lev1headerTag)) {
//     * <p>
//     * for (int j = 0; j < mCategoryModel.getData().getIncome_cat_lev2().size() - 1; j++) {
//     * if (mCategoryModel.getData().getIncome_cat_lev2().get(j).getLev2_name().equals(lev2headerTag)) {
//     * String allowanceId = mCategoryModel.getData().getIncome_cat_lev2().get(j).getId();
//     * for (int k = 0; k < mCategoryModel.getData().getIncome_cat_lev3().size() - 1; k++) {
//     * if (allowanceId.equalsIgnoreCase(mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev2_id())) {
//     * arrayList.add(mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev3_name());
//     * //Log.e("List " + lev2headerTag, "" + arrayList.get(arrayList.size() - 1));
//     * }
//     * }
//     * <p>
//     * break;
//     * }
//     * }
//     * }
//     * }
//     * return arrayList;
//     * }
//     */
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //Log.e("Income", "onCreateView");
//        mContext = getContext();
//        try {
//            mCallBackListener = (OnActivityBackPressedListener) (mContext);
//        } catch (ClassCastException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//        }
//        mCallBackListener.setActionBarTitle("Income Details");
////        if (incomeDetailView == null) {
//        incomeDetailView = inflater.inflate(R.layout.fragment_inc_usr_dyn_detil, container, false);
//        mleftRelativeLayout = (RelativeLayout) incomeDetailView.findViewById(R.id.relative_left_arrow);
//        mcenterRelativeLayout = (RelativeLayout) incomeDetailView.findViewById(R.id.relative_center_home);
//        mRightRelativeLayout = (RelativeLayout) incomeDetailView.findViewById(R.id.relative_right_arrow);
//
//
//           nameEditview = (CurrencyEditText) incomeDetailView.findViewById(R.id.currencyEditTxt);
//
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//        mRightRelativeLayout.setOnClickListener(this);
//        viewParentLinerView = (LinearLayout) incomeDetailView.findViewById(R.id.addIncomeView);
//        try {
//            if (mIncomeModel != null) {
//                //Log.e("error Possition", "" + mPagePosition);
//                userIncomeDetailModel = null;
//                if (mPagePosition != 0) {
//                    if (mFamilyDetailModel != null) {
//                        if (mPagePosition <= mFamilyDetailModel.getData().getFamily_details().size()) {
//                            familyId = mFamilyDetailModel.getData().getFamily_details().get(mPagePosition - 1).getFid();
//                            for (int i = 0; i < mIncomeModel.getData().getUser_incomes().size(); i++) {
//                                if (familyId.equalsIgnoreCase(mIncomeModel.getData().getUser_incomes().get(i).getFamily_id()))
//                                    userIncomeDetailModel = mIncomeModel.getData().getUser_incomes().get(i);
//                            }
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < mIncomeModel.getData().getUser_incomes().size(); i++) {
//                        if (mIncomeModel.getData().getUser_incomes().get(i).getFamily_id().equalsIgnoreCase("0"))
//                            userIncomeDetailModel = mIncomeModel.getData().getUser_incomes().get(i);
//                    }
//                }
//            }
//            if (userIncomeDetailModel != null) {
//                if (userIncomeDetailModel.getLevel_1_ids() != null) {
//                    //Log.e("Level1______", "" + userIncomeDetailModel.getLevel_1_ids());
//                    selLev1List = new ArrayList<String>(Arrays.asList(userIncomeDetailModel.getLevel_1_ids().split(",")));
//                    //Log.e("Level1______", "" + selLev1List.size());
//                }
//                if (userIncomeDetailModel.getLevel_2_ids() != null)
//                    selLev2List = new ArrayList<String>(Arrays.asList(userIncomeDetailModel.getLevel_2_ids().split(",")));
//
//                if (userIncomeDetailModel.getLevel_3_ids() != null)
//                    selLev3List = new ArrayList<String>(Arrays.asList(userIncomeDetailModel.getLevel_3_ids().split(",")));
//
//                int size = userIncomeDetailModel.getIncome_cat_lev1().size();
//                HashMap<String, Income_cat_lev1> incomeHashMap = new HashMap<>();
//                for (int i = 0; i < size; i++) {
//                    //Log.e("Lev1Check____", "" + userIncomeDetailModel.getIncome_cat_lev1().get(i).getLev1_name());
//                    Income_cat_lev1 obj = userIncomeDetailModel.getIncome_cat_lev1().get(i);
//                    obj.setCat_lev2List(getLev2CatgoryList(userIncomeDetailModel.getIncome_cat_lev1().get(i).getId(), userIncomeDetailModel));
//
//                    incomeHashMap.put("" + i, obj);
//                }
////                if(userIncomeDetailModel.getFamily_id().equalsIgnoreCase(familyId))
//
//                addParentView(incomeHashMap, viewParentLinerView);
//
//            }
//        } catch (NullPointerException e1) {
//            e1.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        FloatingActionButton fab = (FloatingActionButton) incomeDetailView.findViewById(R.id.incomefabId);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveIncomeDetail(mPagePosition, false);
//
//                if (mPagePosition == 0) {
//                    familyId = "" + 0;
//                    mOwnUser = "Y";
//
//                } else {
//                    familyId = mFamilyDetailModel.getData().getFamily_details().get(mPagePosition - 1).getFid();
//                    mOwnUser = "N";
//
//                }
//                //Log.e("Level1______",""+selLev1List.size());
//                FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
//                DialogFragment newFragment = LevelOneSelectorDaialog.newInstance(familyId, incomeFilterHashMap,
//                        incomeLev1SelectLisaner, selLev1List);
//                newFragment.show(fm, "dialog");
///*
//                    saveIncomeDetail(mPagePosition);
////                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                    ExpensesReDesignDetailsFragment fragment = new ExpensesReDesignDetailsFragment();
////                    fragmentTransaction.replace(R.id.fragment_container, fragment);
////                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
////                    fragmentTransaction.commitAllowingStateLoss();
//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    // ExpensesDetailsFragment fragment = new ExpensesDetailsFragment();
//                    //ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
//                    ExpensesReDesignFragment fragment = new ExpensesReDesignFragment();
//                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commitAllowingStateLoss();
//                    */
//
//            }
//        });
//
//        IncomefromFamilyDetails.mincomeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                if (mPrevPosition != tab.getPosition()) {
//                    saveIncomeDetail(mPrevPosition, false);
//
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//                mPrevPosition = tab.getPosition() - 1;
//                //Log.e("mPrevPosition", "" + mPrevPosition);
//                if (tab != null)
//                    mPrevPosition = tab.getPosition();
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
////            addBtn.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    if (mPagePosition == 0) {
////                        familyId = "" + 0;
////                        mOwnUser="Y";
////
////                    } else {
////                        familyId = mFamilyDetailModel.getData().getFamily_details().get(mPagePosition-1).getFid();
////                        mOwnUser="N";
////
////                    }
////                    FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
////                    DialogFragment newFragment = LevelOneSelectorDaialog.newInstance(familyId,incomeFilterHashMap,incomeLev1SelectLisaner,selLev1List);
////                    newFragment.show(fm, "dialog");
////                }
////            });
//
//        if (userIncomeDetailModel != null) {
//            setIncomeData(userIncomeDetailModel);
//        }
//
//        return incomeDetailView;
////        } else
////            return incomeDetailView;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        //Log.e("Income", "onActivityCreated");
//    }
//
//   /* void setOnTextChangeLisaner(HashMap<String, ArrayList<CurrencyEditText>> curencyListMap) {
//
//        if (!curencyListMap.isEmpty()) {
//
//            for (int i = 0; i < curencyListMap.size(); i++) {
//                if (curencyListMap.get("" + i) != null) {
//                    int lev2Size = curencyListMap.get("" + i).size();
//                    for (int j = 0; j < lev2Size; j++) {
//                        if (curencyListMap.get("" + i).get(j) != null) {
//                            CurrencyEditText currencyEditTxt = curencyListMap.get("" + i).get(j);
////                            currencyEditTxt.setOnTextChangeEditTextListerner(this);
//
//                        }
//                    }
//                }
//
//            }
//        }
//
//    }
//*/
//    private ArrayList<Income_cat_lev2> getLev2CatgoryList(String lev1headerTagId, User_incomes mCategoryModel) {
//        ArrayList<Income_cat_lev2> incomeCatLev2List = new ArrayList<>();
//        if (mCategoryModel.getIncome_cat_lev2() != null)
//            for (int j = 0; j < mCategoryModel.getIncome_cat_lev2().size(); j++) {
//                if (mCategoryModel.getIncome_cat_lev2().get(j).getLev1_id().equals(lev1headerTagId)) {
//                    Income_cat_lev2 ob = mCategoryModel.getIncome_cat_lev2().get(j);
//                    String allowanceId = ob.getId();
////                //Log.e("Lev2",""+ob.getLev2_name()+"Lev1"+ob.getLev1_id());
//                    ArrayList<Income_cat_lev3> lev3Obj = new ArrayList<>();
//                    if (mCategoryModel.getIncome_cat_lev3() != null) {
//                        for (int k = 0; k < mCategoryModel.getIncome_cat_lev3().size(); k++) {
//                            if (allowanceId.equalsIgnoreCase(mCategoryModel.getIncome_cat_lev3().get(k).getLev2_id())) {
//                                lev3Obj.add(mCategoryModel.getIncome_cat_lev3().get(k));
////                            //Log.e("Lev3", "" + mCategoryModel.getIncome_cat_lev3().get(k).getLev3_name());
//
////                            //Log.e("Lev2", "" + ob.getLev2_name() + "Lev1" + ob.getLev1_id() + "Lev3" + mCategoryModel.getIncome_cat_lev3().get(k).getId());
//                            }
//                        }
//                    }
//                    ob.setIncome_cat_lev3List(lev3Obj);
//                    incomeCatLev2List.add(ob);
//
//                }
//
//
//            }
//        return incomeCatLev2List;
//    }
//
//    private ArrayList<Income_cat_lev2> getLev2CatgoryList(String lev1headerTagId, IncomeInputModel mCategoryModel) {
//        ArrayList<Income_cat_lev2> incomeCatLev2List = new ArrayList<>();
//
//        for (int j = 0; j < mCategoryModel.getData().getIncome_cat_lev2().size(); j++) {
//            if (mCategoryModel.getData().getIncome_cat_lev2().get(j).getLev1_id().equals(lev1headerTagId)) {
//                Income_cat_lev2 ob = mCategoryModel.getData().getIncome_cat_lev2().get(j);
//                String allowanceId = ob.getId();
//                //Log.e("Lev2", "" + ob.getLev2_name() + "Lev1" + ob.getLev1_id());
//                ArrayList<Income_cat_lev3> lev3Obj = new ArrayList<>();
//                for (int k = 0; k < mCategoryModel.getData().getIncome_cat_lev3().size(); k++) {
//                    if (allowanceId.equalsIgnoreCase(mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev2_id())) {
//                        lev3Obj.add(mCategoryModel.getData().getIncome_cat_lev3().get(k));
//                        //Log.e("Lev3", "" + mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev3_name());
//
//                        //Log.e("Lev2", "" + ob.getLev2_name() + "Lev1" + ob.getLev1_id() + "Lev3" + mCategoryModel.getData().getIncome_cat_lev3().get(k).getId());
//                    }
//                }
//                ob.setIncome_cat_lev3List(lev3Obj);
//                incomeCatLev2List.add(ob);
//
//            }
//
//
//        }
//        return incomeCatLev2List;
//    }
//
//    private ArrayList<Income_cat_lev2> getLev2CatgoryList(String lev1headerTagId, IncomeCategoryModel mCategoryModel) {
//
//        ArrayList<Income_cat_lev2> incomeCatLev2List = new ArrayList<>();
//        try {
//            for (int j = 0; j < mCategoryModel.getData().getIncome_cat_lev2().size(); j++) {
//                if (mCategoryModel.getData().getIncome_cat_lev2().get(j).getLev1_id().equals(lev1headerTagId)) {
//                    Income_cat_lev2 ob = mCategoryModel.getData().getIncome_cat_lev2().get(j);
//                    String allowanceId = ob.getId();
////                //Log.e("Lev2",""+ob.getLev2_name()+"Lev1"+ob.getLev1_id());
//                    ArrayList<Income_cat_lev3> lev3Obj = new ArrayList<>();
//                    for (int k = 0; k < mCategoryModel.getData().getIncome_cat_lev3().size(); k++) {
//                        if (allowanceId.equalsIgnoreCase(mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev2_id())) {
//                            lev3Obj.add(mCategoryModel.getData().getIncome_cat_lev3().get(k));
////                        //Log.e("Lev3",""+ mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev3_name());
//
////                        //Log.e("Lev2",""+ob.getLev2_name()+"Lev1"+ob.getLev1_id()+"Lev3"+ mCategoryModel.getData().getIncome_cat_lev3().get(k).getId());
//                        }
//                    }
//                    ob.setIncome_cat_lev3List(lev3Obj);
//                    incomeCatLev2List.add(ob);
//
//                }
//
//
//            }
//        }catch (Exception e)
//        {
//            return  null;
//        }
//        return incomeCatLev2List;
//    }
//
//    private void setIncomeData(User_incomes userIncomeDetailModel) {
//        if (!curencyListMap.isEmpty()) {
//            for (int i = 0; i < curencyListMap.size(); i++) {
//                if (curencyListMap.get("" + i) != null) {
//                    int lev2Size = curencyListMap.get("" + i).size();
//                    for (int j = 0; j < lev2Size; j++) {
//                        if (curencyListMap.get("" + i).get(j) != null) {
//                            CurrencyEditText currencyEditTxt = curencyListMap.get("" + i).get(j);
//                            if (UtileKit.validateObjectValues(UtileKit.getStringwithoutCurreny(currencyEditTxt))) {
//                                String currencyValueTxt = UtileKit.getStringwithoutCurreny(currencyEditTxt);
//
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
//
//    }
//
//    private void saveIncomeDetail(int position, boolean isback) {
//        if (mPagePosition == position) {
//            int total = 0;
//            String mTotalSalery = "0",
//                    mTotalIncomeFromProperty = "0",
//                    mTotalBussinesIncome = "0",
//                    mTotalCapitalGain = "0",
//                    mTotalIncomeFromOtherSource = "0", mOwnUser;
//            JSONObject jsonIncomeObject = null;
//
//            UserId = UtileKit.getPersistedPurplePathPref("user_id");
//            if (mPagePosition == 0) {
//                familyId = "" + 0;
//                mOwnUser = "Y";
//
//            } else {
//                familyId = mFamilyDetailModel.getData().getFamily_details().get(mPagePosition - 1).getFid();
//                mOwnUser = "N";
//
//            }
//            if (!curencyListMap.isEmpty()) {
//
//                for (int i = 0; i < curencyListMap.size(); i++) {
//                    if (curencyListMap.get("" + i) != null) {
//                        int mLev1Total=0;
//                        int lev2Size = curencyListMap.get("" + i).size();
//                        CurrencyEditText currencyTitleEditTxt = curencyListMap.get("" + i).get(0);
//                        if(lev2Size>=2) {
//                            for (int j = 1; j < lev2Size; j++) {
//                                try {
//                                    CurrencyEditText currencyEditTxt = curencyListMap.get("" + i).get(j);
//                                    ImageView lifeExpEditTxt = lifeExpactancyImgView.get("" + i).get(j);
//                                    int mLifeExpVal = Integer.parseInt(lifeExpEditTxt.getTag().toString());
//                                    String currentValue = UtileKit.getStringwithoutCurreny(currencyEditTxt);
//                                    int curentAmout=0;
//
//                                    if (UtileKit.validateObjectValues(currentValue)) {
//                                        try {
//                                            if (currentValue.length() != 0) {
//                                                 curentAmout = Integer.parseInt(currentValue);
//                                                total = total + curentAmout;
//                                                mLev1Total = mLev1Total + curentAmout;
//                                                //Log.e("Self not in list", "total plus" + total + "" + curentAmout + "totalLev1" + mLev1Total);
//
//                                            }
//                                        }catch (Exception e){e.printStackTrace();}
//                                        addToJsonObject(currencyEditTxt.getTag().toString(), ""+curentAmout);
//                                        if (!lifeExpactancyList.get(mLifeExpVal - 1).equalsIgnoreCase("")) {
//                                            addToJsonObject(currencyEditTxt.getTag().toString() + "_info", lifeExpactancyList.get(mLifeExpVal - 1));
//                                        }
//
//                                    }
//                                } catch (NumberFormatException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                        else {
//                            if (currencyTitleEditTxt.getText().length() != 0)
//                                mLev1Total = Integer.parseInt(UtileKit.getStringwithoutCurreny(currencyTitleEditTxt));
//                            total = total+mLev1Total;
//                        }
//                        addToJsonObject(currencyTitleEditTxt.getTag().toString(), ""+mLev1Total);
//                        //Log.e("list", "mLev1Total plus" + "" +mLev1Total);
//                    }
//
//                }
//            }
//
//            try {
//                if (jsonArray.length() != 0) {
//                    jsonIncomeObject = new JSONObject();
//                    jsonIncomeObject.put("inc_det", jsonArray);
////                    //Log.e("Vlue", "" + jsonIncomeObject.toString());
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            String overallTotal = "";
//            try {
//                overallTotal = String.valueOf(total);
////                Log.i("Self not in list", "IncomeListAdapter name overallTotal " + overallTotal);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if (jsonIncomeObject != null) {
//                addIncomeDetailService(UserId,
//                        familyId, overallTotal, mTotalSalery, mTotalIncomeFromProperty, mTotalBussinesIncome, mTotalCapitalGain, mTotalIncomeFromOtherSource, "", jsonIncomeObject.toString(), mOwnUser,isback);
//            }else
//            {
//                if(isback)
//                    mCallBackListener.onActivityBackPressed();
//            }
//
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.relative_left_arrow: {
//                saveIncomeDetail(mPagePosition,true);
//
//            }
//            break;
//            case R.id.relative_center_home: {
//                saveIncomeDetail(mPagePosition, false);
//                Intent i = new Intent(getActivity(), HomePageActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
////                getActivity().finish();
//            }
//            break;
//            case R.id.relative_right_arrow: {
//                saveIncomeDetail(mPagePosition, false);
////                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                ExpensesReDesignDetailsFragment fragment = new ExpensesReDesignDetailsFragment();
////                fragmentTransaction.replace(R.id.fragment_container, fragment);
////                fragmentTransaction.addToBackStack(fragment.getClass().getName());
////                fragmentTransaction.commitAllowingStateLoss();
//                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                // ExpensesDetailsFragment fragment = new ExpensesDetailsFragment();
//                //ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
//                ExpenseTabMainFragment fragment =  ExpenseTabMainFragment.newInstance(0);
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();
//            }
//        }
//
//    }
//
//    private void addToJsonObject(String field, String value) {
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("field", field);
//            jsonObject.put("value", value);
//
//            jsonArray.put(jsonObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Adding View Dynamicaly with incomeCatHashList
//     *
//     * @param incomeCatHashList
//     * @param parentView
//     */
//    private void addParentView(HashMap<String, Income_cat_lev1> incomeCatHashList, LinearLayout parentView) {
//        try {
//            parentView.removeAllViews();
//            int ViewId = 0;
//            //Log.e("incomeCatHashList", "" + incomeCatHashList.toString());
//            curencyListMap = new HashMap<>();
//            lifeExpactancyImgView = new HashMap<>();
//            lifeExpactancyList = new ArrayList<>();
//
//            if (!incomeCatHashList.isEmpty()) {
//                for (int i = 0; i < incomeCatHashList.size(); i++) {
//                    /**
//                     * level one  Header view
//                     */
//                    ArrayList<CurrencyEditText> currencyEditTexts = new ArrayList<>();
//                    View view = LayoutInflater.from(mContext).inflate(R.layout.add_income_total_header, null);
//                    TextView totalTextview = (TextView) view.findViewById(R.id.incomeTotalTxtId);
//                    totalTextview.setText("" + incomeCatHashList.get("" + i).getLev1_name());
//                    CurrencyEditText totalEditview = (CurrencyEditText) view.findViewById(R.id.incomeTotalEditId);
//                    totalEditview.setId(ViewId++);
//                    totalEditview.setTag(incomeCatHashList.get("" + i).getTb_field_name());
//                    currencyEditTexts.add(totalEditview);
//                    totalEditview.setOnTextChangeEditTextListerner(this);
//                    if (incomeCatHashList.get("" + i).getValue() != null) {
//                        totalEditview.setText("" + incomeCatHashList.get("" + i).getValue());
//                    }
//                    ImageView lifeExpView = (ImageView) view.findViewById(R.id.lifeExptImgPopView);
//                    lifeExpView.setId(i);
//
//
//
//
//                    ArrayList<ImageView> lifeExpImgListView = new ArrayList<>();
//                    if (incomeCatHashList.get("" + i).getInfo_value() != null) {
//                        lifeExpactancyList.add("" + incomeCatHashList.get("" + i).getInfo_value());
////                    //Log.e("incomeCatHashList",  ""+incomeCatHashList.get("" + i).getInfo_value());
//                    } else
//                        lifeExpactancyList.add("");
//                    lifeExpView.setTag(lifeExpactancyList.size());
//                    lifeExpView.setId(lifeExpactancyList.size());
//
//
//
//                    lifeExpImgListView.add(lifeExpView);
//                    totalEditview.setTag("" + incomeCatHashList.get("" + i).getTb_field_name());
//                    if (incomeCatHashList.get("" + i).getCat_lev2List() != null)
//                        parentView.addView(view);
//                    /**
//                     * adding Level2SubView
//                     */
//                    if (incomeCatHashList.get("" + i).getCat_lev2List() != null) {
//
//                        int lev2Size = incomeCatHashList.get("" + i).getCat_lev2List().size();
//                        for (int j = 0; j < lev2Size; j++) {
//                            View lev2view = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
//                             nameEditview = (CurrencyEditText) lev2view.findViewById(R.id.currencyEditTxt);
//                            currencyEditTexts.add(nameEditview);
//                            nameEditview.setOnTextChangeEditTextListerner(this);
//                            TextInputLayout hintText = (TextInputLayout) lev2view.findViewById(R.id.textInput);
//                            ImageView lifeExpLev2View = (ImageView) lev2view.findViewById(R.id.lifeExptImgPopView);
//                            CustomCalenderImageView lifeExpLev2CalView = (CustomCalenderImageView) lev2view.findViewById(R.id.calculaterImgView);
//                            if (incomeCatHashList.get("" + i).getCat_lev2List().get(j).getInfo_value() != null) {
//                                lifeExpactancyList.add("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getInfo_value());
//                            } else
//                                lifeExpactancyList.add("");
//
//                            lifeExpLev2View.setTag(lifeExpactancyList.size());
//                            lifeExpLev2View.setId(lifeExpactancyList.size());
//                            lifeExpImgListView.add(lifeExpLev2View);
//                            //if( incomeCatHashList.get("" + i).getCat_lev2List().get(j).g)
//                            /**
//                             *  need work under process
//                             */
//
//                            nameEditview.setTag("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getTb_field_name());
//                            lifeExpLev2CalView.setTag("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getTb_field_name());
//                            lifeExpLev2CalView.setId(ViewId++);
//                            nameEditview.setId(ViewId++);
//                            if (incomeCatHashList.get("" + i).getCat_lev2List().get(j).getValue() != null) {
//                                nameEditview.setText("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getValue());
//                                //Log.e("incomeCatHashList", "" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getTb_field_name() + "" + incomeCatHashList.get("" + i).getValue().toString());
//                            }
//                            lifeExpLev2CalView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    showCalDialog(v, "");
//                                }
//                            });
//
//
//
////Calculator changes muruga//
//                            lifeExpLev2CalView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    Intent calculatorIntent = new Intent(getActivity(), CalculatorAct.class);
//                                    calculatorIntent.putExtra(CalculatorAct.TITLE_ACTIVITY, TITLE);
//                                    calculatorIntent.putExtra(CalculatorAct.PARENT_ACTIVITY, PARENT_CLASS_SOURCE);
//                                    calculatorIntent.putExtra(CalculatorAct.VALUE, nameEditview.getText().toString());
//                                    startActivityForResult(calculatorIntent, CalculatorAct.REQUEST_RESULT_SUCCESSFUL);
//                                }
//                            });
//
//
////                        nameEditview.setDefaultHintEnabled(true);
////                        //Log.e("Tag-------->", "" + nameEditview.getTag());
//                            hintText.setHint("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getLev2_name());
//
//                            parentView.addView(lev2view);
//                            /**
//                             * Adding Level 3view
//                             */
//                            if (incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List() != null) {
//                                int lev3Size = incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().size();
//                                for (int k = 0; k < lev3Size; k++) {
//                                    View lev3view = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
//                                    CurrencyEditText nameLev3Editview = (CurrencyEditText) lev3view.findViewById(R.id.currencyEditTxt);
//                                    currencyEditTexts.add(nameLev3Editview);
//                                    nameLev3Editview.setOnTextChangeEditTextListerner(this);
//                                    TextInputLayout hintLev3Text = (TextInputLayout) lev3view.findViewById(R.id.textInput);
//                                    ImageView lifeExpLev3View = (ImageView) lev3view.findViewById(R.id.lifeExptImgPopView);
//                                    CustomCalenderImageView lifeExpLev3CalView = (CustomCalenderImageView) lev3view.findViewById(R.id.calculaterImgView);
//                                    if (incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getInfo_value() != null) {
//                                        lifeExpactancyList.add("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getInfo_value());
//                                    } else
//                                        lifeExpactancyList.add("");
//
//                                    lifeExpLev3View.setTag(lifeExpactancyList.size());
//                                    lifeExpLev3View.setId(lifeExpactancyList.size());
//
//                                    lifeExpImgListView.add(lifeExpLev3View);
//
//                                    nameLev3Editview.setTag("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getTb_field_name());
//                                    lifeExpLev3CalView.setTag("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getTb_field_name());
//                                    nameLev3Editview.setId(ViewId++);
//                                    lifeExpLev3CalView.setId(ViewId++);
//                                    if (incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getValue() != null) {
//                                        nameLev3Editview.setText("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getValue());
//                                        //Log.e("incomeCatHashList", "" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getTb_field_name() + "" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getValue());
//                                    }
//                                    lifeExpLev3CalView.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            showCalDialog(v, "");
//                                        }
//                                    });
////                        nameEditview.setDefaultHintEnabled(true);
//
//                                    hintLev3Text.setHint("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getLev3_name());
//
//                                    parentView.addView(lev3view);
//                                }
//
//                            }
//                        }
//                    }
//                    curencyListMap.put("" + i, currencyEditTexts);
//                    lifeExpactancyImgView.put("" + i, lifeExpImgListView);
//
////                cashFlowReceiptsArrayEditTxt.add((CurrencyEditText) view.findViewById(R.id.viewEditTextId));
////                cashFlowReceiptsQuestionArray.add((AutoCompleteTextView) view.findViewById(R.id.viewAutoCompleteId));
////                addButton.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        view.setVisibility(View.GONE);
////                        addCashFlowReceiptsView(cashFlowReceiptsAddView, "cashFlow", cashFlowReceiptsQuestionArray.size());
////                    }
////                });
//
//                }
//                int lifeExp=0;
//                for (int m = 0; m < lifeExpactancyImgView.size(); m++) {
//
//                    for (int n = 0; n < lifeExpactancyImgView.get("" + m).size(); n++) {
//                        lifeExpactancyImgView.get("" + m).get(n).setId(lifeExp);
//                        lifeExp++;
//                        lifeExpactancyImgView.get("" + m).get(n).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                try {
//                                    //Log.e("Tag " + view.getTag().toString(), "lifeExpactancyList" + lifeExpactancyList.get(Integer.parseInt(view.getTag().toString())-1));
//                                    showPopupDialog(view, lifeExpactancyList.get(Integer.parseInt(view.getTag().toString())-1));
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//
//                }
//            }
//            onChangeTextEditText("");
//        } catch (ArrayIndexOutOfBoundsException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void showPopupDialog(View view, String s) {
//        FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
//        DialogFragment newFragment = ExpectedIncrementDialogFragment.newInstance(this, view, s);
//
//        newFragment.show(fm, "dialog");
//    }
//
//    private void showCalDialog(View view, String s) {
//
//
//    }
//
//    private void addIncomeDetailService(String userId,
//                                        final String family_id,
//                                        String over_all_total,
//                                        String si_total,
//                                        String ip_total,
//                                        String ib_total,
//                                        String cg_total,
//                                        String ifs_total,
//                                        String notes,
//                                        String income_details, String isOwn,final boolean isBackpress) {
//
//
//        UtileKit.showSpinnerDialog(mContext, false);
//        if (userIncomeDetailModel == null) {
//            WebServiceCalls webServiceObj;
//            webServiceObj = ServiceGenerator
//                    .createService(WebServiceCalls.class);
//            Call<IncomeInputModel> call = webServiceObj.callAddIncomeService(userId,
//                    family_id,
//                    selLev1List.toString().replace("[", "").replace("]", "").replace("\\s+", ""),
//                    selLev2List.toString().replace("[", "").replace("]", "").replace("\\s+", ""),
//                    selLev3List.toString().replace("[", "").replace("]", "").replace("\\s+", ""),
//                    over_all_total, income_details, isOwn);
////                    si_total,
////                    ip_total,
////                    ib_total,
////                    cg_total,
////                    ifs_total,
////                    notes,
//
//            call.enqueue(new Callback<IncomeInputModel>() {
//                @Override
//                public void onResponse(Call<IncomeInputModel> call, Response<IncomeInputModel> response) {
//
//                    UtileKit.dismisssSpinnerDialog();
//                    IncomeInputModel categoryModel = response.body();
//                    //Log.e("CallBack", " response is " + categoryModel.toString());
//                    if (categoryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//                        userIncomeDetailModel = new User_incomes();
//                        if (family_id.equalsIgnoreCase(categoryModel.getData().getInput().getFamily_id())) {
//                            if (categoryModel.getData().getIncome_cat_lev1() != null)
//                                userIncomeDetailModel.setIncome_cat_lev1(categoryModel.getData().getIncome_cat_lev1());
//                            if (categoryModel.getData().getIncome_cat_lev2() != null)
//                                userIncomeDetailModel.setIncome_cat_lev2(categoryModel.getData().getIncome_cat_lev2());
//                            if (categoryModel.getData().getIncome_cat_lev3() != null)
//                                userIncomeDetailModel.setIncome_cat_lev3(categoryModel.getData().getIncome_cat_lev3());
//                        }
////                    addViewto(fragmentview,mIncomeCategoryModel)
//
//                    } else {
//                        UtileKit.intitializeAlertDialog(
//                                categoryModel.getData().getMessage(),
//                                mContext);
////                        UtileKit.alertDialog(
////                                categoryModel.getData().getMessage(),
////                                mContext);
//
//                    }
//                    if(isBackpress)
//                    {
//                        mCallBackListener.onActivityBackPressed();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<IncomeInputModel> call, Throwable t) {
//                    UtileKit.dismisssSpinnerDialog();
//                    if(isBackpress)
//                    {
//                        mCallBackListener.onActivityBackPressed();
//                    }
//                    UtileKit.alertRetrofitExceptionDialog( mContext,t);
//                }
//            });
//        } else {
//            WebServiceCalls webServiceObj;
//            webServiceObj = ServiceGenerator
//                    .createService(WebServiceCalls.class);
//            Call<IncomeInputModel> call = webServiceObj.callAddIncomeService(userId,
//                    family_id,
//                    selLev1List.toString().replace("[", "").replace("]", "").replace("\\s+", ""),
//                    selLev2List.toString().replace("[", "").replace("]", "").replace("\\s+", ""),
//                    selLev3List.toString().replace("[", "").replace("]", "").replace("\\s+", ""),
//                    over_all_total,
//                    income_details,
//                    isOwn);
////            si_total,
////                    ip_total,
////                    ib_total,
////                    cg_total,
////                    ifs_total,
////                    notes,
//            call.enqueue(new Callback<IncomeInputModel>() {
//                @Override
//                public void onResponse(Call<IncomeInputModel> call, Response<IncomeInputModel> response) {
//                    //Log.e("CallBack", " response is " + call.toString());
//                    UtileKit.dismisssSpinnerDialog();
//                    IncomeInputModel categoryModel = response.body();
//                    if (categoryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//                        if (userIncomeDetailModel.getFamily_id().equalsIgnoreCase(categoryModel.getData().getInput().getFamily_id()))
//                            if (categoryModel.getData().getIncome_cat_lev1() != null)
//                                userIncomeDetailModel.setIncome_cat_lev1(categoryModel.getData().getIncome_cat_lev1());
//                        if (categoryModel.getData().getIncome_cat_lev2() != null)
//                            userIncomeDetailModel.setIncome_cat_lev2(categoryModel.getData().getIncome_cat_lev2());
//                        if (categoryModel.getData().getIncome_cat_lev3() != null)
//                            userIncomeDetailModel.setIncome_cat_lev3(categoryModel.getData().getIncome_cat_lev3());
//
////                    addViewto(fragmentview,mIncomeCategoryModel)
//                    } else {
//                        UtileKit.intitializeAlertDialog(
//                                categoryModel.getData().getMessage(),
//                                mContext);
//
////                        UtileKit.alertDialog(
////                                categoryModel.getData().getMessage(),
////                                mContext);
//                    }
//                    if(isBackpress)
//                    {
//                        mCallBackListener.onActivityBackPressed();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<IncomeInputModel> call, Throwable t) {
//                    UtileKit.dismisssSpinnerDialog();
//                    if(isBackpress)
//                    {
//                        mCallBackListener.onActivityBackPressed();
//                    }
//                    UtileKit.alertRetrofitExceptionDialog( mContext,t);
//                }
//            });
//        }
//    }
//
//    private void showPopupDialog(int Id) {
//        FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
//        DialogFragment newFragment = ExpectedIncrementDialogFragment.newInstance(this, Id);
//        newFragment.show(fm, "dialog");
//    }
//
//    public String getStringwithoutCurreny(CurrencyEditText edt) {
//        String str = edt.getText().toString().replace("₹", "");
//        String str1 = str.replace("\u00A0", "");
//        return str1;
//    }
//
//    @Override
//    public void onValueSet(ExpectedIncrementDialogFragment dialog, String age, String percentage, int id) throws JSONException {
//
//        lifeExpactancyList.set(id-1, "" + age + "," + percentage);
//    }
//
//    @Override
//    public void onChangeTextEditText(String s) {
//        if (!this.curencyListMap.isEmpty()) {
//            for (int i = 0; i < this.curencyListMap.size(); i++) {
//                if (this.curencyListMap.get("" + i) != null) {
//                    BigInteger mTotal = BigInteger.valueOf(0);
//                    int lev2Size = this.curencyListMap.get("" + i).size();
//                    for (int j = 1; j < lev2Size; j++) {
//                        if (this.curencyListMap.get("" + i).get(j) != null) {
//                            BigInteger ammount = BigInteger.valueOf(0);
//                            CurrencyEditText currencyEditTxt = this.curencyListMap.get("" + i).get(j);
//                            if (UtileKit.validateObjectValues(UtileKit.getStringwithoutCurreny(currencyEditTxt))) {
//                                String currencyValueTxt = UtileKit.getStringwithoutCurreny(currencyEditTxt);
//                                if (currencyValueTxt.length() != 0) {
//                                    try {
//                                        ammount = BigInteger.valueOf(Long.parseLong((currencyValueTxt)));
////                                        //Log.e("amount ", "" + ammount);
//                                    } catch (NumberFormatException e) {
////                                        ammount = BigInteger.valueOf(0);
//                                    } catch (Exception e) {
////                                        ammount = BigInteger.valueOf(0);
//                                    }
//                                    mTotal = mTotal.add(ammount) ;
//                                }
//                            }
//
//                        }
//
//                    }
//                    if(lev2Size>1)
//                        this.curencyListMap.get("" + i).get(0).setText(""+ UtileKit.currencyCunvertion(mTotal));
//                    //Log.e("Currency Total",""+ UtileKit.currencyCunvertion(mTotal));
//                }
//            }
//        }
//    }
//
//    @Override
//    public void incomeSelectedLevOneList(ArrayList<Income_cat_lev1> selectedList) {
//        selLev1List = new ArrayList<>();
//        for (int i = 0; i < selectedList.size(); i++) {
//            selLev1List.add(selectedList.get(i).getId());
//        }
//
//        android.support.v4.app.FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();
//        android.support.v4.app.DialogFragment newFragment = LevelSecondSelectionDialog.newInstance(familyId, selectedList, incomeFilterHashMap, incLev2SelLisInterface, selLev2List, selLev3List);
//        newFragment.show(fm, "dialog");
//
//    }
//
//    @Override
//    public void selectionNextButtonClick() {
//        nextExpancePage();
//    }
//
//    @Override
//    public void expenseSelectedLevOneList(ArrayList<ExpensesLevelZeroData> selectedList) {
//
//    }
//
//
//
//    public void setIncomeLev1SelectLisaner(IncomeDynamicDetail incomeLev1SelectLisaner) {
//        this.incomeLev1SelectLisaner = incomeLev1SelectLisaner;
//    }
//
//    @Override
//    public void incomeSelectedLev2List(ArrayList<String> seleLev2List) {
//        //Log.e("setIncomeLev2SelnLi", "Works***_______*****" + seleLev2List.toString());
//    }
//
//    @Override
//    public void incomeSelectedLev3List(ArrayList<String> seleLev3List) {
//        //Log.e("setIncomeLev3SelnLi", "Works***_______*****" + seleLev3List.toString());
//    }
//
//    public void setIncomeLev2SelectionLisaner(IncomeDynamicDetail incomeLev2SelectionLisaner) {
//        this.incLev2SelLisInterface = incomeLev2SelectionLisaner;
//        //Log.e("setIncomeLev2SelnLi", "Works***_______*****");
//    }
//
//    @Override
//    public void incomeSelectedIncomeCat() {
//
//        //Log.e("Done", "Works*****____________******");
//
//        if (!selLev1List.isEmpty()) {
//
//            WebServiceCalls webServiceObj;
//            webServiceObj = ServiceGenerator
//                    .createService(WebServiceCalls.class);
//            String is_own_user;
//            if (familyId.equalsIgnoreCase("0")) {
//                is_own_user = "Y";
//            } else {
//                is_own_user = "N";
//            }
//            Call<IncomeInputModel> callIncome = webServiceObj.callAddIncomeSelectService(UtileKit.getPersistedPurplePathPref("user_id"),
//                    selLev1List.toString().replace("[", "").replace("]", ""),
//                    selLev2List.toString().replace("[", "").replace("]", ""),
//                    selLev3List.toString().replace("[", "").replace("]", ""),
//                    familyId, is_own_user
//            );
//            UtileKit.showSpinnerDialog(mContext, false);
//            callIncome.enqueue(new Callback<IncomeInputModel>() {
//                @Override
//                public void onResponse(Call<IncomeInputModel> call, Response<IncomeInputModel> response) {
//                    UtileKit.dismisssSpinnerDialog();
//                    IncomeInputModel categoryModel = response.body();
//                    if (categoryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//                        //  mIncomeInputModel = categoryModel;
////                        getIncomeDetail();
////                    addViewto(fragmentview,mIncomeCategoryModel)
//                        if (categoryModel != null) {
//                            if (userIncomeDetailModel == null)
//                                userIncomeDetailModel = new User_incomes();
//                            HashMap<String, Income_cat_lev1> incomeSelectedHashMap = new HashMap<>();
//                            if (categoryModel.getData().getIncome_cat_lev1() != null) {
//
//                                int size = categoryModel.getData().getIncome_cat_lev1().size();
//                                for (int i = 0; i < size; i++) {
////                                            //Log.e("Lev1", "" + categoryModel.getData().getIncome_cat_lev1().get(i).getLev1_name());
//
//                                    Income_cat_lev1 obj = categoryModel.getData().getIncome_cat_lev1().get(i);
////                                    userIncomeDetailModel.setIncome_cat_lev1(categoryModel.getData().getIncome_cat_lev1());
//                                    obj.setCat_lev2List(getLev2CatgoryList(categoryModel.getData().getIncome_cat_lev1().get(i).getId(), categoryModel));
//                                    incomeSelectedHashMap.put("" + i, obj);
//                                }
//                                viewParentLinerView.removeAllViews();
//                                addParentView(incomeSelectedHashMap, viewParentLinerView);
//                            }
//
//                        }
//
//                    } else {
//                        UtileKit.intitializeAlertDialog(
//                                categoryModel.getData().getMessage(),
//                                mContext);
//
////                        UtileKit.alertDialog(
////                                categoryModel.getData().getMessage(),
////                                mContext);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<IncomeInputModel> call, Throwable t) {
//                    UtileKit.dismisssSpinnerDialog();
//                    UtileKit.alertRetrofitExceptionDialog( mContext,t);
//                }
//            });
//        }
//    }
//
//    @Override
//    public void dialogNextButtonClick() {
//        nextExpancePage();
//    }
//
//    private void getIncomeDetail() {
//
//        UtileKit.showSpinnerDialog(mContext, false);
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator
//                .createService(WebServiceCalls.class);
//        Call<GetIncomeModel> call = webServiceObj.GetIncomeDetailService(UtileKit.getPersistedPurplePathPref("user_id"));
//        call.enqueue(new Callback<GetIncomeModel>() {
//            @Override
//            public void onResponse(Call<GetIncomeModel> call, Response<GetIncomeModel> response) {
//                //Log.e("CallBack", " response is " + call.toString());
//                UtileKit.dismisssSpinnerDialog();
//                GetIncomeModel incomeDetail = response.body();
//                if (incomeDetail.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//
//                    getActivity().getSupportFragmentManager().popBackStack();
//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//
//                    Fragment incomeFrag=IncomefromFamilyDetails.newInstance(mFamilyDetailModel,mCategoryModel,incomeDetail, fragmentManager, familyId);
//                    // LifeInsuranceListFragment fragment = new LifeInsuranceListFragment();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragment_container, incomeFrag);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commitAllowingStateLoss();
////
////         addViewto(fragmentview,mIncomeCategoryModel)
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetIncomeModel> call, Throwable t) {
//                UtileKit.dismisssSpinnerDialog();
//                UtileKit.alertRetrofitExceptionDialog( mContext,t);
//            }
//        });
//    }
//
//    void nextExpancePage()
//    {
//        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        ExpenseTabMainFragment fragment =  ExpenseTabMainFragment.newInstance(0);
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
//    }
//
//    @Override
//    public void onChangeTextEditText(String s, TextView currencySpanTxtView) {
//
//    }
//}

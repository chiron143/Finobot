package com.purplepath.purplepath.incomedetails.fragment.postretairement;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyEditText;
import com.purplepath.purplepath.customview.CurrencyGroupView;
import com.purplepath.purplepath.dialog.ExpectedIncrementDialogFragment;
import com.purplepath.purplepath.expenseEDCOC.ExpenseTabMainFragment;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelZeroData;
import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncLev2SelLisInterface;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncSelecOnDismisInterf;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dialog.LevelOneSelectorDaialog;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dialog.LevelSecondSelectionDialog;
import com.purplepath.purplepath.incomedetails.fragment.model.GetIncomeModel;
import com.purplepath.purplepath.incomedetails.fragment.model.IncomeCategoryModel;
import com.purplepath.purplepath.incomedetails.fragment.model.IncomeInputModel;
import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev1;
import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev2;
import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev3;
import com.purplepath.purplepath.incomedetails.fragment.model.User_incomes;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.incomedetails.fragment.postretairement.RetIncFromFamily.mRetIncomeTabLayout;

/**
 * Created by dinesh on 05/01/17.
 */
public class RetIncomeDynamicDetail extends BaseFragment implements View.OnClickListener, ExpectedIncrementDialogFragment.OnExpectedValueListener ,CurrencyEditText.OnTextChangeEditText,IncSelecOnDismisInterf,IncLev2SelLisInterface {
    private static String ARG_PARAM1 = "position";
    private static String ARG_PARAM2 = "familyObj";
    private static String ARG_PARAM3 = "categoryObj";
    private static String ARG_PARAM4 = "incomeObj";
    public static final String TITLE = "";
    String UserId, familyId = "",mOwnUser,currentTabName;
    private int mPagePosition;
    private  int mPrevPosition;
    private IncomeCategoryModel mCategoryModel;
    private AddFamilyDetailModel mFamilyDetailModel;
    private HashMap<String,Income_cat_lev1> incomeFilterHashMap=new HashMap<>();
    private HashMap<String, ArrayList<ArrayList<CurrencyGroupView>>> curencyListMap = new HashMap<>();
    private HashMap<String, ArrayList<ArrayList<ImageView>>> lifeExpactancyImgView = new HashMap<>();
    ArrayList<String> lifeExpactancyList = new ArrayList<>();
    private User_incomes userIncomeDetailModel;
    private GetIncomeModel mIncomeModel;
    private JSONArray jsonArray = new JSONArray();
    private Context mContext;
    private View incomeDetailView;
    private IncSelecOnDismisInterf incomeLev1SelectLisaner;
    private IncLev2SelLisInterface incLev2SelLisInterface;
    private LinearLayout viewParentLinerView;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private OnActivityBackPressedListener mCallBackListener;
    View nameEditview;
    private Toolbar toolbar;
    ArrayList<String>selLev1List=new ArrayList<>(), selLev2List=new ArrayList<>(),selLev3List=new ArrayList<>();
    private ArrayList<String> selectedLev0List,selectedLev1List,selectedLev2List;
    public static Fragment newInstance(int position, IncomeCategoryModel mCategoryModel, AddFamilyDetailModel mFamilyDetailModel, GetIncomeModel incomeModel) {
        RetIncomeDynamicDetail fragment = new RetIncomeDynamicDetail();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        args.putSerializable(ARG_PARAM2, mFamilyDetailModel);
        args.putSerializable(ARG_PARAM3, mCategoryModel);
        args.putSerializable(ARG_PARAM4,incomeModel);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selLev1List = new ArrayList<>();
        selLev2List = new ArrayList<>();
        selLev3List = new ArrayList<>();
        selectedLev0List= new ArrayList<>();
        selectedLev1List= new ArrayList<>();
        selectedLev2List= new ArrayList<>();
        setRetainInstance(true);
        setIncomeLev1SelectLisaner(this);
        setIncomeLev2SelectionLisaner(this);
        mContext = getContext();
        incomeFilterHashMap=new HashMap<>();
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_PARAM1)) {
                mPagePosition = (getArguments().getInt(ARG_PARAM1));
                //Log.e("position",""+mPagePosition);
            }

            if (getArguments().containsKey(ARG_PARAM2))
                mFamilyDetailModel = (AddFamilyDetailModel) getArguments().getSerializable(ARG_PARAM2);
            if (getArguments().containsKey(ARG_PARAM3))
                mCategoryModel = (IncomeCategoryModel) getArguments().getSerializable(ARG_PARAM3);
            if (mCategoryModel != null) {
                int size= mCategoryModel.getData().getIncome_cat_lev1().size();
                for(int i=0;i<size;i++)
                {
                    //Log.e("Lev1",""+mCategoryModel.getData().getIncome_cat_lev1().get(i).getLev1_name());
                    Income_cat_lev1 obj=mCategoryModel.getData().getIncome_cat_lev1().get(i);
                    obj.setCat_lev2List(getLev2CatgoryList(mCategoryModel.getData().getIncome_cat_lev1().get(i).getId(),mCategoryModel));

                    incomeFilterHashMap.put(""+i,obj);
                }
//                    addParentView(incomeFilterHashMap,viewParentLinerView);

            }
            if (getArguments().containsKey(ARG_PARAM4))
            {
                mIncomeModel = (GetIncomeModel) getArguments().getSerializable(ARG_PARAM4);

            }

            if(mPagePosition== 0 && mIncomeModel == null){
                familyId = "" + 0;
                mOwnUser = "Y";
                //Log.e("Level1______",""+selLev1List.size());
                FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
                DialogFragment newFragment = LevelOneSelectorDaialog.newInstance(familyId,incomeFilterHashMap,
                        incomeLev1SelectLisaner,selLev1List);
                newFragment.show(fm, "dialog");

            }
        }

    }
    /**
     *
     private ArrayList<String> getLev3CatgoryList(String lev1headerTag, String lev2headerTag) {
     ArrayList<String> arrayList = new ArrayList<>();
     for (int i = 0; i < mCategoryModel.getData().getIncome_cat_lev1().size() - 1; i++) {
     if (mCategoryModel.getData().getIncome_cat_lev1().get(i).getLev1_name().equals(lev1headerTag)) {

     for (int j = 0; j < mCategoryModel.getData().getIncome_cat_lev2().size() - 1; j++) {
     if (mCategoryModel.getData().getIncome_cat_lev2().get(j).getLev2_name().equals(lev2headerTag)) {
     String allowanceId = mCategoryModel.getData().getIncome_cat_lev2().get(j).getId();
     for (int k = 0; k < mCategoryModel.getData().getIncome_cat_lev3().size() - 1; k++) {
     if (allowanceId.equalsIgnoreCase(mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev2_id())) {
     arrayList.add(mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev3_name());
     //Log.e("List " + lev2headerTag, "" + arrayList.get(arrayList.size() - 1));
     }
     }

     break;
     }
     }
     }
     }
     return arrayList;
     }
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }

        catch(Exception e)
        {}
        mCallBackListener.setActionBarTitle("Income Details");
//        if (incomeDetailView == null) {
            incomeDetailView = inflater.inflate(R.layout.fragment_inc_usr_dyn_detil, container, false);
            mleftRelativeLayout = (RelativeLayout) incomeDetailView.findViewById(R.id.relative_left_arrow);
            mcenterRelativeLayout = (RelativeLayout) incomeDetailView.findViewById(R.id.relative_center_home);
            //mRightRelativeLayout = (RelativeLayout) incomeDetailView.findViewById(R.id.relative_right_arrow);
            mleftRelativeLayout.setOnClickListener(this);
            mcenterRelativeLayout.setOnClickListener(this);
            //mRightRelativeLayout.setOnClickListener(this);
            viewParentLinerView=(LinearLayout)incomeDetailView.findViewById(R.id.addIncomeView);
            try {
            if(mIncomeModel!=null)
            {
                //Log.e("error Possition",""+mPagePosition);
                userIncomeDetailModel=null;
                if(mPagePosition!=0) {
                    if (mFamilyDetailModel != null) {
                        if (mPagePosition <= mFamilyDetailModel.getData().getFamily_details().size()) {
                            familyId = mFamilyDetailModel.getData().getFamily_details().get(mPagePosition-1).getFid();
                            for (int i = 0; i < mIncomeModel.getData().getUser_incomes().size(); i++) {
                                if (familyId.equalsIgnoreCase(mIncomeModel.getData().getUser_incomes().get(i).getFamily_id()))
                                    userIncomeDetailModel = mIncomeModel.getData().getUser_incomes().get(i);
                            }
                        }
                    }
                }
                else
                {
                    for (int i = 0; i < mIncomeModel.getData().getUser_incomes().size(); i++) {
                        if (mIncomeModel.getData().getUser_incomes().get(i).getFamily_id().equalsIgnoreCase("0"))
                            userIncomeDetailModel = mIncomeModel.getData().getUser_incomes().get(i);
                    }
                }
            }
            if(userIncomeDetailModel!=null)
            {
                if(userIncomeDetailModel.getLevel_1_ids()!=null) {
                    String selLe1String=userIncomeDetailModel.getLevel_1_ids().replaceAll("\\s+","");
                    selLev1List = new ArrayList<String>(Arrays.asList(selLe1String.split(",")));
                    selectedLev0List.clear();
                    selectedLev0List.addAll(selLev1List);
                }
                if(userIncomeDetailModel.getLevel_2_ids()!=null) {
                    String selLe2String=userIncomeDetailModel.getLevel_2_ids().replaceAll("\\s+","");
                    selLev2List = new ArrayList<String>(Arrays.asList(selLe2String.split(",")));
                    selectedLev1List.clear();
                    selectedLev1List.addAll(selLev2List);
                }

                if(userIncomeDetailModel.getLevel_3_ids()!=null) {
                    String selLe3String=userIncomeDetailModel.getLevel_3_ids().replaceAll("\\s+","");
                    selLev3List = new ArrayList<String>(Arrays.asList(selLe3String.split(",")));
                    selectedLev2List.clear();
                    selectedLev2List.addAll(selLev3List);
                }

                int size= userIncomeDetailModel.getIncome_cat_lev1().size();
                HashMap<String,Income_cat_lev1> incomeHashMap=new HashMap<>();
                for(int i=0;i<size;i++)
                {
                    //Log.e("Lev1",""+userIncomeDetailModel.getIncome_cat_lev1().get(i).getLev1_name());
                    Income_cat_lev1 obj=userIncomeDetailModel.getIncome_cat_lev1().get(i);
                    obj.setCat_lev2List(getLev2CatgoryList(userIncomeDetailModel.getIncome_cat_lev1().get(i).getId(),userIncomeDetailModel));

                    incomeHashMap.put(""+i,obj);
                }
//                if(userIncomeDetailModel.getFamily_id().equalsIgnoreCase(familyId))
                addParentView(incomeHashMap,viewParentLinerView);
            }
            } catch (NullPointerException e1) {
                e1.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FloatingActionButton fab = (FloatingActionButton) incomeDetailView.findViewById(R.id.incomefabId);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveIncomeDetail(mPagePosition, false);
                    if (mPagePosition == 0) {
                        familyId = "" + 0;
                        mOwnUser="Y";

                    } else {
                        familyId = mFamilyDetailModel.getData().getFamily_details().get(mPagePosition-1).getFid();
                        mOwnUser="N";
                    }
                    selectedLev0List.clear();
                    selectedLev0List.addAll(selLev1List);
                    FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
                    DialogFragment newFragment = LevelOneSelectorDaialog.newInstance(familyId,incomeFilterHashMap,incomeLev1SelectLisaner,selectedLev0List);
                    newFragment.show(fm, "dialog");
/*
                    saveIncomeDetail(mPagePosition);
//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    ExpensesReDesignDetailsFragment fragment = new ExpensesReDesignDetailsFragment();
//                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
//                    fragmentTransaction.commitAllowingStateLoss();
                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    // ExpensesDetailsFragment fragment = new ExpensesDetailsFragment();
                    //ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
                    ExpensesReDesignFragment fragment = new ExpensesReDesignFragment();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
                    */

                }
            });

            mRetIncomeTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    if(mPrevPosition!=tab.getPosition()) {
                        saveIncomeDetail(mPrevPosition, false);
//                        //Log.e("CurrentTab","@@@@@@@@@"+tab.getPosition());
//                        //Log.e("mPreviusPageNumber","@@@@@@@@@"+mPreviusPageNumber);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                    mPrevPosition=tab.getPosition()-1;
                    //Log.e("mPrevPosition",""+mPrevPosition);
                    if(tab!=null)
                        mPrevPosition= tab.getPosition();

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
//            addBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mPagePosition == 0) {
//                        familyId = "" + 0;
//                        mOwnUser="Y";
//
//                    } else {
//                        familyId = mFamilyDetailModel.getData().getFamily_details().get(mPagePosition-1).getFid();
//                        mOwnUser="N";
//
//                    }
//                    FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
//                    DialogFragment newFragment = LevelOneSelectorDaialog.newInstance(familyId,incomeFilterHashMap,incomeLev1SelectLisaner,selLev1List);
//                    newFragment.show(fm, "dialog");
//                }
//            });

//            if (userIncomeDetailModel != null) {
//                setIncomeData(userIncomeDetailModel);
//            }

            return incomeDetailView;
//        } else
//            return incomeDetailView;
    }
    private ArrayList<Income_cat_lev2> getLev2CatgoryList(String lev1headerTagId, User_incomes mCategoryModel) {
        ArrayList<Income_cat_lev2> incomeCatLev2List=new ArrayList<>();
        if(mCategoryModel.getIncome_cat_lev2()!=null)
            for (int j = 0; j < mCategoryModel.getIncome_cat_lev2().size(); j++) {
                if (mCategoryModel.getIncome_cat_lev2().get(j).getLev1_id().equals(lev1headerTagId)) {
                    Income_cat_lev2 ob= mCategoryModel.getIncome_cat_lev2().get(j);
                    String allowanceId = ob.getId();
//                //Log.e("Lev2",""+ob.getLev2_name()+"Lev1"+ob.getLev1_id());
                    ArrayList<Income_cat_lev3> lev3Obj=new ArrayList<>();
                    if(mCategoryModel.getIncome_cat_lev3()!=null) {
                        for (int k = 0; k < mCategoryModel.getIncome_cat_lev3().size(); k++) {
                            if (allowanceId.equalsIgnoreCase(mCategoryModel.getIncome_cat_lev3().get(k).getLev2_id())) {
                                lev3Obj.add(mCategoryModel.getIncome_cat_lev3().get(k));
//                            //Log.e("Lev3", "" + mCategoryModel.getIncome_cat_lev3().get(k).getLev3_name());

//                            //Log.e("Lev2", "" + ob.getLev2_name() + "Lev1" + ob.getLev1_id() + "Lev3" + mCategoryModel.getIncome_cat_lev3().get(k).getId());
                            }
                        }
                    }
                    ob.setIncome_cat_lev3List(lev3Obj);
                    incomeCatLev2List.add(ob);

                }


            }
        return incomeCatLev2List;
    }
    private ArrayList<Income_cat_lev2> getLev2CatgoryList(String lev1headerTagId, IncomeInputModel mCategoryModel) {
        ArrayList<Income_cat_lev2> incomeCatLev2List=new ArrayList<>();

        for (int j = 0; j < mCategoryModel.getData().getIncome_cat_lev2().size(); j++) {
            if (mCategoryModel.getData().getIncome_cat_lev2().get(j).getLev1_id().equals(lev1headerTagId)) {
                Income_cat_lev2 ob= mCategoryModel.getData().getIncome_cat_lev2().get(j);
                String allowanceId = ob.getId();
                //Log.e("Lev2",""+ob.getLev2_name()+"Lev1"+ob.getLev1_id());
                ArrayList<Income_cat_lev3> lev3Obj=new ArrayList<>();
                for (int k = 0; k < mCategoryModel.getData().getIncome_cat_lev3().size(); k++) {
                    if (allowanceId.equalsIgnoreCase(mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev2_id())) {
                        lev3Obj.add(mCategoryModel.getData().getIncome_cat_lev3().get(k));
                        //Log.e("Lev3",""+ mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev3_name());

                        //Log.e("Lev2",""+ob.getLev2_name()+"Lev1"+ob.getLev1_id()+"Lev3"+ mCategoryModel.getData().getIncome_cat_lev3().get(k).getId());
                    }
                }
                ob.setIncome_cat_lev3List(lev3Obj);
                incomeCatLev2List.add(ob);

            }


        }
        return incomeCatLev2List;
    }
    private ArrayList<Income_cat_lev2> getLev2CatgoryList(String lev1headerTagId, IncomeCategoryModel mCategoryModel) {
        ArrayList<Income_cat_lev2> incomeCatLev2List=new ArrayList<>();

        for (int j = 0; j < mCategoryModel.getData().getIncome_cat_lev2().size(); j++) {
            if (mCategoryModel.getData().getIncome_cat_lev2().get(j).getLev1_id().equals(lev1headerTagId)) {
                Income_cat_lev2 ob= mCategoryModel.getData().getIncome_cat_lev2().get(j);
                String allowanceId = ob.getId();
//                //Log.e("Lev2",""+ob.getLev2_name()+"Lev1"+ob.getLev1_id());
                ArrayList<Income_cat_lev3> lev3Obj=new ArrayList<>();
                for (int k = 0; k < mCategoryModel.getData().getIncome_cat_lev3().size(); k++) {
                    if (allowanceId.equalsIgnoreCase(mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev2_id())) {
                        lev3Obj.add(mCategoryModel.getData().getIncome_cat_lev3().get(k));
//                        //Log.e("Lev3",""+ mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev3_name());

//                        //Log.e("Lev2",""+ob.getLev2_name()+"Lev1"+ob.getLev1_id()+"Lev3"+ mCategoryModel.getData().getIncome_cat_lev3().get(k).getId());
                    }
                }
                ob.setIncome_cat_lev3List(lev3Obj);
                incomeCatLev2List.add(ob);

            }


        }
        return incomeCatLev2List;
    }
   /* private void setIncomeData(User_incomes userIncomeDetailModel) {
        if (!curencyListMap.isEmpty()) {
            for (int i = 0; i < curencyListMap.size(); i++) {
                if (curencyListMap.get("" + i) != null) {
                    int lev2Size = curencyListMap.get("" + i).size();
                    for (int j = 0; j < lev2Size; j++) {
                        if (curencyListMap.get("" + i).get(j) != null) {
                            CurrencyEditText currencyEditTxt = curencyListMap.get("" + i).get(j);
                            if (UtileKit.validateObjectValues(UtileKit.getStringwithoutCurreny(currencyEditTxt))) {
                                String currencyValueTxt = UtileKit.getStringwithoutCurreny(currencyEditTxt);

                            }

                        }
                    }
                }
            }
        }

    }
    */
    private void saveIncomeDetail(int position, boolean isback) {
        if (mPagePosition == position) {
            int total = 0;
            String mTotalSalery = "0",
                    mTotalIncomeFromProperty = "0",
                    mTotalBussinesIncome = "0",
                    mTotalCapitalGain = "0",
                    mTotalIncomeFromOtherSource = "0", mOwnUser;
            JSONObject jsonIncomeObject = null;

            UserId = UtileKit.getPersistedPurplePathPref("user_id");
            if (mPagePosition == 0) {
                familyId = "" + 0;
                mOwnUser = "Y";

            } else {
                familyId = mFamilyDetailModel.getData().getFamily_details().get(mPagePosition - 1).getFid();
                mOwnUser = "N";

            }
            if (!curencyListMap.isEmpty()) {

                for (int i = 0; i < curencyListMap.size(); i++) {
                    if (curencyListMap.get("" + i) != null) {
                        int mLev1Total = 0;
                        int lev2Size = curencyListMap.get("" + i).size();
                        CurrencyGroupView currencyTitleEditTxt = curencyListMap.get("" + i).get(0).get(0);
                        for (int k = 1; k <lev2Size; k++) {
                            if (lev2Size >= 2) {
                                int lev3size=curencyListMap.get("" + i).get(k).size();
                                for (int j = 0; j < lev3size; j++) {
                                    try {
                                        CurrencyGroupView currencyEditTxt = curencyListMap.get("" + i).get(k).get(j);
                                        ImageView lifeExpEditTxt = lifeExpactancyImgView.get("" + i).get(k).get(j);
                                        int mLifeExpVal = Integer.parseInt(lifeExpEditTxt.getTag().toString());
                                        String currentValue = UtileKit.getStringwithoutCurreny(currencyEditTxt.getEditText());
                                        int curentAmout = 0;

                                        if (UtileKit.validateObjectValues(currentValue)) {
                                            try {
                                                if (currentValue.length() != 0) {
                                                    curentAmout = Integer.parseInt(currentValue);
                                                    if(j==0)
                                                    {
                                                        /**
                                                         * Skip Lev 2 Header on Toatal
                                                         */
                                                        if(lev3size==1)
                                                        {
                                                            total = total + curentAmout;
                                                            mLev1Total = mLev1Total + curentAmout;
                                                        }
                                                    }
                                                    else {
                                                        total = total + curentAmout;
                                                        mLev1Total = mLev1Total + curentAmout;
                                                        //Log.e("Self not in list", "total plus" + total + "" + curentAmout + "totalLev1" + mLev1Total);
                                                    }



                                                }
//                                                else if (currentValue.length() != 0&&j==0)
//                                                {
//                                                    curentAmout   =Integer.parseInt(currentValue);
//                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            addToJsonObject(currencyEditTxt.getTag().toString(), "" + curentAmout);
                                            if (!lifeExpactancyList.get(mLifeExpVal - 1).equalsIgnoreCase("")) {
                                                addToJsonObject(currencyEditTxt.getTag().toString() + "_info", lifeExpactancyList.get(mLifeExpVal - 1));
                                            }

                                        }
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                if (currencyTitleEditTxt.getText().length() != 0)
                                    mLev1Total = Integer.parseInt(UtileKit.getStringwithoutCurreny(currencyTitleEditTxt.getEditText()));
                                total = total + mLev1Total;
                            }

                        }
                        addToJsonObject(currencyTitleEditTxt.getTag().toString(), "" + mLev1Total);
                        //Log.e("list", "mLev1Total plus" + "" + mLev1Total);
                    }

                }
            }

            try {
                if (jsonArray.length() != 0) {
                    jsonIncomeObject = new JSONObject();
                    jsonIncomeObject.put("inc_det", jsonArray);
//                    //Log.e("Vlue", "" + jsonIncomeObject.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String overallTotal = "";
            try {
                overallTotal = String.valueOf(total);
//                Log.i("Self not in list", "IncomeListAdapter name overallTotal " + overallTotal);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonIncomeObject != null) {
                addIncomeDetailService(UserId,
                        familyId, overallTotal, mTotalSalery, mTotalIncomeFromProperty, mTotalBussinesIncome, mTotalCapitalGain, mTotalIncomeFromOtherSource, "", jsonIncomeObject.toString(), mOwnUser, isback);
            } else {
                if (isback)
                    mCallBackListener.onActivityBackPressed();
            }

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
            {
                saveIncomeDetail(mPagePosition,true);

            }
            break;
            case R.id.relative_center_home:
            {
                saveIncomeDetail(mPagePosition, false);
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
//                getActivity().finish();
            }
            break;
//            case R.id.relative_right_arrow:
//            {
//                saveIncomeDetail(mPagePosition, true);
//
//            }
        }

    }

    private void addToJsonObject(String field, String value) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("field", field);
            jsonObject.put("value", value);

            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch(Exception e)
        {

        }
    }

    /**
     * Adding View Dynamicaly with incomeCatHashList
     *
     * @param incomeCatHashList
     * @param parentView
     */
    private void addParentView(HashMap<String, Income_cat_lev1> incomeCatHashList, LinearLayout parentView) {
        try {
            parentView.removeAllViews();
            int ViewId = 0;
            //Log.e("incomeCatHashList", "" + incomeCatHashList.toString());
            curencyListMap = new HashMap<>();
            lifeExpactancyImgView = new HashMap<>();
            lifeExpactancyList = new ArrayList<>();

            if (!incomeCatHashList.isEmpty()) {
                for (int i = 0; i < incomeCatHashList.size(); i++) {
/**
 level one  Header view
 */
                    ArrayList<ArrayList<ImageView>> lifeExpectancyImageView = new ArrayList<>();
                    ArrayList<ArrayList<CurrencyGroupView>> currencyArrayEditTexts = new ArrayList<>();
                    ArrayList<CurrencyGroupView> currencyEditTexts = new ArrayList<>();
                    View view = LayoutInflater.from(mContext).inflate(R.layout.add_income_total_header, null);
                    TextView totalTextview = view.findViewById(R.id.incomeTotalTxtId);
                    totalTextview.setText("" + incomeCatHashList.get("" + i).getLev1_name());
                    CurrencyGroupView totalEditview = view.findViewById(R.id.incomeTotalEditId);
                    totalEditview.setId(ViewId++);
                    totalEditview.setTag(incomeCatHashList.get("" + i).getTb_field_name());
                    totalEditview.setTextHint("Total");
                    totalEditview.getEditText().setId(generateViewId());
                    currencyEditTexts.add(totalEditview);
                    currencyArrayEditTexts.add(currencyEditTexts);
//                    totalEditview.setOnTextChangeEditTextListerner(this);
                    if (incomeCatHashList.get("" + i).getValue() != null) {
                        totalEditview.setText("" + incomeCatHashList.get("" + i).getValue());
                    }
                    ImageView lifeExpView = view.findViewById(R.id.lifeExptImgPopView);
                    lifeExpView.setId(i);
                    ArrayList<ImageView> lifeExpImgListView = new ArrayList<>();
                    if (incomeCatHashList.get("" + i).getInfo_value() != null) {
                        lifeExpactancyList.add("" + incomeCatHashList.get("" + i).getInfo_value());
//                    //Log.e("incomeCatHashList",  ""+incomeCatHashList.get("" + i).getInfo_value());
                    } else
                        lifeExpactancyList.add("");
                    lifeExpView.setTag(lifeExpactancyList.size());
                    lifeExpView.setId(lifeExpactancyList.size());


                    lifeExpImgListView.add(lifeExpView);
                    totalEditview.setTag("" + incomeCatHashList.get("" + i).getTb_field_name());
//                    if (incomeCatHashList.get("" + i).getCat_lev2List() != null)
                    lifeExpectancyImageView.add(lifeExpImgListView);
                    parentView.addView(view);

/**
 * adding Level2SubView
 */

                    if (incomeCatHashList.get("" + i).getCat_lev2List() != null) {

                        int lev2Size = incomeCatHashList.get("" + i).getCat_lev2List().size();

                        for (int j = 0; j < lev2Size; j++) {
                            ArrayList<CurrencyGroupView> currencyEditLEv2List = new ArrayList<>();
                            ArrayList<ImageView> lifeExpLEV2ImgViewList = new ArrayList<>();
                            int lev3Size = 0;
                            if (incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List() != null)
                                lev3Size = incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().size();
                            if (lev3Size == 0) {
                                View lev2view = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
                                final CurrencyGroupView nameEditview = lev2view.findViewById(R.id.currencyEditTxt);
                                currencyEditLEv2List.add(nameEditview);
                                nameEditview.getEditText().setId(generateViewId());
                                nameEditview.getEditText().setOnTextChangeEditTextListerner(this,nameEditview.getAmountInWords());
                                TextInputLayout hintText = lev2view.findViewById(R.id.textInput);
                                ImageView lifeExpLev2View = lev2view.findViewById(R.id.lifeExptImgPopView);
                                ImageView lifeExpLev2CalView = lev2view.findViewById(R.id.calculaterImgView);
                                if (incomeCatHashList.get("" + i).getCat_lev2List().get(j).getInfo_value() != null) {
                                    lifeExpactancyList.add("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getInfo_value());
                                } else
                                    lifeExpactancyList.add("");

                                lifeExpLev2View.setTag(lifeExpactancyList.size());
                                lifeExpLev2View.setId(lifeExpactancyList.size());
//                                lifeExpImgListView.add(lifeExpLev2View);
                                lifeExpLEV2ImgViewList.add(lifeExpLev2View);
                                //if( incomeCatHashList.get("" + i).getCat_lev2List().get(j).g)
/*
                                 *  need work under process
                                 */
                                nameEditview.setTag("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getTb_field_name());
                                lifeExpLev2CalView.setTag("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getTb_field_name());
                                lifeExpLev2CalView.setId(ViewId++);
                                nameEditview.setId(ViewId++);
                                if (incomeCatHashList.get("" + i).getCat_lev2List().get(j).getValue() != null) {
                                    nameEditview.setText("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getValue());
                                    //Log.e("incomeCatHashList", "" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getTb_field_name() + "" + incomeCatHashList.get("" + i).getValue().toString());
                                }
                                lifeExpLev2CalView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showCalDialog(nameEditview);
                                    }
                                });
//                        nameEditview.setDefaultHintEnabled(true);
//                        //Log.e("Tag-------->", "" + nameEditview.getTag());
                                /**
                                 * dinesh currency
                                 * hintText.setHint("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getLev2_name());
                                 */
                                nameEditview.setTextHint("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getLev2_name());
                                parentView.addView(lev2view);
                            } else {
                                View lev2Leadview = LayoutInflater.from(mContext).inflate(R.layout.add_income_total_header, null);
                                TextView lev2TotTextview = lev2Leadview.findViewById(R.id.incomeTotalTxtId);
                                lev2TotTextview.setText("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getLev2_name());
                                final CurrencyGroupView lev2Editview = lev2Leadview.findViewById(R.id.incomeTotalEditId);
                                ImageView lifeExpTitleLev2View = lev2Leadview.findViewById(R.id.lifeExptImgPopView);
                                ImageView lifeExpTitleLev2CalView = lev2Leadview.findViewById(R.id.calExpImgListView);
                                currencyEditLEv2List.add(lev2Editview);
//                            lev2Editview.setOnTextChangeEditTextListerner(this);
                                lev2Editview.setTextHint("Total");



                                if (incomeCatHashList.get("" + i).getCat_lev2List().get(j).getInfo_value() != null) {
                                    lifeExpactancyList.add("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getInfo_value());
                                } else
                                    lifeExpactancyList.add("");

                                lifeExpTitleLev2View.setTag(lifeExpactancyList.size());
                                lifeExpTitleLev2View.setId(lifeExpactancyList.size());
//                                lifeExpImgListView.add(lifeExpTitleLev2View);
                                lifeExpLEV2ImgViewList.add(lifeExpTitleLev2View);
                                lev2Editview.setTag("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getTb_field_name());
                                lifeExpTitleLev2CalView.setTag("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getTb_field_name());
                                lifeExpTitleLev2CalView.setId(ViewId++);
                                lev2Editview.setId(ViewId++);
                                if (incomeCatHashList.get("" + i).getCat_lev2List().get(j).getValue() != null) {
                                    lev2Editview.setText("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getValue());
                                    //Log.e("incomeCatHashList", "" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getTb_field_name() + "" + incomeCatHashList.get("" + i).getValue().toString());
                                }
                                lifeExpTitleLev2View.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        showCalDialog(lev2Editview);
                                    }
                                });
                                parentView.addView(lev2Leadview);
                                for (int k = 0; k < lev3Size; k++) {
                                    View lev3view = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
                                    final CurrencyGroupView nameLev3Editview = lev3view.findViewById(R.id.currencyEditTxt);
                                    currencyEditLEv2List.add(nameLev3Editview);
                                    nameLev3Editview.getEditText().setId(generateViewId());
                                    nameLev3Editview.getEditText().setOnTextChangeEditTextListerner(this,nameLev3Editview.getAmountInWords());
                                    TextInputLayout hintLev3Text = lev3view.findViewById(R.id.textInput);
                                    ImageView lifeExpLev3View = lev3view.findViewById(R.id.lifeExptImgPopView);
                                    ImageView lifeExpLev3CalView = lev3view.findViewById(R.id.calculaterImgView);
                                    if (incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getInfo_value() != null) {
                                        lifeExpactancyList.add("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getInfo_value());
                                    } else
                                        lifeExpactancyList.add("");

                                    lifeExpLev3View.setTag(lifeExpactancyList.size());
                                    lifeExpLev3View.setId(lifeExpactancyList.size());

//                                    lifeExpImgListView.add(lifeExpLev3View);
                                    lifeExpLEV2ImgViewList.add(lifeExpLev3View);
                                    nameLev3Editview.setTag("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getTb_field_name());
                                    lifeExpLev3CalView.setTag("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getTb_field_name());
                                    nameLev3Editview.setId(ViewId++);
                                    lifeExpLev3CalView.setId(ViewId++);
                                    if (incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getValue() != null) {
                                        nameLev3Editview.setText("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getValue());
                                        //Log.e("incomeCatHashList", "" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getTb_field_name() + "" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getValue());
                                    }
                                    lifeExpLev3CalView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            showCalDialog(nameLev3Editview);
                                        }
                                    });
//                        nameEditview.setDefaultHintEnabled(true);
/**
 *  dinesh
 *   hintLev3Text.setHint("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getLev3_name());
 */
                                    nameLev3Editview.setTextHint("" + incomeCatHashList.get("" + i).getCat_lev2List().get(j).getIncome_cat_lev3List().get(k).getLev3_name());

                                    parentView.addView(lev3view);
                                }
                            }
/*
* Adding Level 3view
*/

                            currencyArrayEditTexts.add(currencyEditLEv2List);
                            lifeExpectancyImageView.add(lifeExpLEV2ImgViewList);
                        }
                    }

                    curencyListMap.put("" + i, currencyArrayEditTexts);
                    lifeExpactancyImgView.put("" + i, lifeExpectancyImageView);

//                cashFlowReceiptsArrayEditTxt.add((CurrencyEditText) view.findViewById(R.id.viewEditTextId));
//                cashFlowReceiptsQuestionArray.add((AutoCompleteTextView) view.findViewById(R.id.viewAutoCompleteId));
//                addButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        view.setVisibility(View.GONE);
//                        addCashFlowReceiptsView(cashFlowReceiptsAddView, "cashFlow", cashFlowReceiptsQuestionArray.size());
//                    }
//                });

                }
                int lifeExp = 0;
                for (int m = 0; m < lifeExpactancyImgView.size(); m++) {

                    for (int n = 0; n < lifeExpactancyImgView.get("" + m).size(); n++) {
                        for (int l = 0; l < lifeExpactancyImgView.get("" + m).get(n).size() ; l++) {

                            lifeExpactancyImgView.get("" + m).get(n).get(l).setId(lifeExp);
                            lifeExp++;
                            lifeExpactancyImgView.get("" + m).get(n).get(l).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        //Log.e("Tag " + view.getTag().toString(), "lifeExpactancyList" + lifeExpactancyList.get(Integer.parseInt(view.getTag().toString()) - 1));
                                        showPopupDialog(view, lifeExpactancyList.get(Integer.parseInt(view.getTag().toString()) - 1));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }


                    }

                }
            }
            onChangeTextEditText("");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showPopupDialog(View view, String s) {
        FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
        DialogFragment newFragment = ExpectedIncrementDialogFragment.newInstance(this,view,s);
        newFragment.show(fm, "dialog");
    }
    private void showCalDialog(View view) {

        nameEditview = view;
//        nameEditview.setTag(view.getTag());
        String calculaterValue = ((CurrencyGroupView) nameEditview).getText().toString();
        Intent calculatorIntent = new Intent(getActivity(), CalculatorAct.class);
        calculatorIntent.putExtra(CalculatorAct.TITLE_ACTIVITY, TITLE);
//        calculatorIntent.putExtra(CalculatorAct.PARENT_ACTIVITY, PARENT_CLASS_SOURCE);
        calculatorIntent.putExtra(CalculatorAct.VALUE, calculaterValue);
        startActivityForResult(calculatorIntent, CalculatorAct.REQUEST_RESULT_SUCCESSFUL);

    }

    private void addIncomeDetailService(String userId,
                                        final String family_id,
                                        String over_all_total,
                                        String si_total,
                                        String ip_total,
                                        String ib_total,
                                        String cg_total,
                                        String ifs_total,
                                        String notes,
                                        String income_details, String isOwn, final boolean isback) {


        UtileKit.showSpinnerDialog(mContext, false);
        if(userIncomeDetailModel==null) {
            WebServiceCalls webServiceObj;
            webServiceObj = ServiceGenerator
                    .createService(WebServiceCalls.class);
            Call<IncomeInputModel> call = webServiceObj.callAddIncomepostretService(userId,
                    family_id,
                    selLev1List.toString().replace("[","").replace("]","").replaceAll("\\s+",""),
                    selLev2List.toString().replace("[","").replace("]","").replaceAll("\\s+",""),
                    selLev3List.toString().replace("[","").replace("]","").replaceAll("\\s+",""),
                    over_all_total, income_details,isOwn);
//                    si_total,
//                    ip_total,
//                    ib_total,
//                    cg_total,
//                    ifs_total,
//                    notes,

            call.enqueue(new Callback<IncomeInputModel>() {
                @Override
                public void onResponse(Call<IncomeInputModel> call, Response<IncomeInputModel> response) {

                    UtileKit.dismisssSpinnerDialog();
                    IncomeInputModel categoryModel = response.body();
                    //Log.e("CallBack", " response is " +categoryModel.toString());
                    if (categoryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        userIncomeDetailModel =new User_incomes();
                        if(family_id.equalsIgnoreCase(categoryModel.getData().getInput().getFamily_id())) {
                            if (categoryModel.getData().getIncome_cat_lev1() != null)
                                userIncomeDetailModel.setIncome_cat_lev1(categoryModel.getData().getIncome_cat_lev1());
                            if (categoryModel.getData().getIncome_cat_lev2() != null)
                                userIncomeDetailModel.setIncome_cat_lev2(categoryModel.getData().getIncome_cat_lev2());
                            if (categoryModel.getData().getIncome_cat_lev3() != null)
                                userIncomeDetailModel.setIncome_cat_lev3(categoryModel.getData().getIncome_cat_lev3());
                        }

//                    addViewto(fragmentview,mIncomeCategoryModel)
                    } else {
                        UtileKit.intitializeAlertDialog(
                                categoryModel.getData().getMessage(),
                                mContext);
//                        UtileKit.alertDialog(
//                                categoryModel.getData().getMessage(),
//                                mContext);
                    }
                    if(isback)
                    {
                        mCallBackListener.onActivityBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<IncomeInputModel> call, Throwable t) {
                    UtileKit.dismisssSpinnerDialog();
                    if(isback)
                    {
                        mCallBackListener.onActivityBackPressed();
                    }
                    UtileKit.alertRetrofitExceptionDialog( mContext,t);
                }
            });
        }else
        {
            WebServiceCalls webServiceObj;
            webServiceObj = ServiceGenerator
                    .createService(WebServiceCalls.class);
            Call<IncomeInputModel> call = webServiceObj.callAddIncomepostretService(userId,
                    family_id,
                    selLev1List.toString().replace("[","").replace("]","").replace("\\s+",""),
                    selLev2List.toString().replace("[","").replace("]","").replace("\\s+",""),
                    selLev3List.toString().replace("[","").replace("]","").replace("\\s+",""),
                    over_all_total,
                    income_details,
                    isOwn);
//            si_total,
//                    ip_total,
//                    ib_total,
//                    cg_total,
//                    ifs_total,
//                    notes,
            call.enqueue(new Callback<IncomeInputModel>() {
                @Override
                public void onResponse(Call<IncomeInputModel> call, Response<IncomeInputModel> response) {
                    //Log.e("CallBack", " response is " + call.toString());
                    UtileKit.dismisssSpinnerDialog();
                    IncomeInputModel categoryModel = response.body();
                    if (categoryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if(userIncomeDetailModel.getFamily_id().equalsIgnoreCase(categoryModel.getData().getInput().getFamily_id()))
                            if(categoryModel.getData().getIncome_cat_lev1()!=null)
                                userIncomeDetailModel.setIncome_cat_lev1(categoryModel.getData().getIncome_cat_lev1());
                        if(categoryModel.getData().getIncome_cat_lev2()!=null)
                            userIncomeDetailModel.setIncome_cat_lev2(categoryModel.getData().getIncome_cat_lev2());
                        if(categoryModel.getData().getIncome_cat_lev3()!=null)
                            userIncomeDetailModel.setIncome_cat_lev3(categoryModel.getData().getIncome_cat_lev3());

//                    addViewto(fragmentview,mIncomeCategoryModel)
                    } else {
                        UtileKit.intitializeAlertDialog(
                                categoryModel.getData().getMessage(),
                                mContext);

//                        UtileKit.alertDialog(
//                                categoryModel.getData().getMessage(),
//                                mContext);
                    }
                    if(isback)
                    {
                        mCallBackListener.onActivityBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<IncomeInputModel> call, Throwable t) {
                    UtileKit.dismisssSpinnerDialog();
                    if(isback)
                    {
                        mCallBackListener.onActivityBackPressed();
                    }
                    UtileKit.alertRetrofitExceptionDialog( mContext,t);
                }
            });
        }
    }
    private void showPopupDialog(int Id){
        FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
        DialogFragment newFragment = ExpectedIncrementDialogFragment.newInstance(this,Id);
        newFragment.show(fm, "dialog");
    }

    public String getStringwithoutCurreny(CurrencyEditText edt){
        String str =  edt.getText().toString().replace("₹","");
        String str1 = str.replace("\u00A0","");
        return  str1;
    }

    @Override
    public void onValueSet(ExpectedIncrementDialogFragment dialog, String age, String percentage, int id) throws JSONException {

        lifeExpactancyList.set(id-1,""+age+","+percentage);
    }

    @Override
    public void onChangeTextEditText(String s, TextView spanTextView) {
        try {
            if (spanTextView != null) {
                spanTextView.setVisibility(View.VISIBLE);
                spanTextView.setText(UtileKit.currToCharConversion(s));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!this.curencyListMap.isEmpty()) {
            for (int i = 0; i < this.curencyListMap.size(); i++) {
                BigInteger mTotal = BigInteger.valueOf(0);
                int lev2Size = this.curencyListMap.get("" + i).size();
                for (int j = 1; j < lev2Size; j++) {
                    int sizeArray = this.curencyListMap.get("" + i).get(j).size();
                    if (sizeArray == 1) {
                        BigInteger ammount = BigInteger.valueOf(0);
                        CurrencyGroupView currencyEditTxt = this.curencyListMap.get("" + i).get(j).get(0);
                        //Log.e("currencL2vTxt ", "" + currencyEditTxt.getTag());
                        if (UtileKit.validateObjectValues(UtileKit.getStringwithoutCurreny(currencyEditTxt.getEditText()))) {
                            String currencyValueTxt = UtileKit.getStringwithoutCurreny(currencyEditTxt.getEditText());
                            if (currencyValueTxt.length() != 0) {
                                try {
                                    ammount = BigInteger.valueOf(Long.parseLong((currencyValueTxt)));
//                                        //Log.e("amount ", "" + ammount);
                                } catch (NumberFormatException e) {
//                                        ammount = BigInteger.valueOf(0);
                                } catch (Exception e) {
//                                        ammount = BigInteger.valueOf(0);
                                }
                                mTotal = mTotal.add(ammount);
                            }
                        }
                    } else {
                        BigInteger mLev3Total = BigInteger.valueOf(0);
                        for (int k = 1; k < sizeArray; k++) {
                            BigInteger ammount = BigInteger.valueOf(0);
                            CurrencyGroupView currencyEditTxt = this.curencyListMap.get("" + i).get(j).get(k);
                            //Log.e("currencyEditTxt Lev3 ", "" + currencyEditTxt.getTag());
                            if (UtileKit.validateObjectValues(UtileKit.getStringwithoutCurreny(currencyEditTxt.getEditText()))) {
                                String currencyValueTxt = UtileKit.getStringwithoutCurreny(currencyEditTxt.getEditText());

                                if (currencyValueTxt.length() != 0) {
                                    try {
                                        mLev3Total = mLev3Total.add(BigInteger.valueOf(Long.parseLong((currencyValueTxt))));
                                        ammount = BigInteger.valueOf(Long.parseLong((currencyValueTxt)));
//                                        //Log.e("amount ", "" + ammount);
                                    } catch (NumberFormatException e) {
//                                        ammount = BigInteger.valueOf(0);
                                    } catch (Exception e) {
//                                        ammount = BigInteger.valueOf(0);
                                    }
                                    mTotal = mTotal.add(ammount);
                                }
                            }
                        }

                        this.curencyListMap.get("" + i).get(j).get(0).setText("" + UtileKit.currencyCunvertion(mLev3Total));
                    }
                }
                if (lev2Size > 1) {
                    this.curencyListMap.get("" + i).get(0).get(0).setText("" + UtileKit.currencyCunvertion(mTotal));
                    //Log.e("Currency Total", "" + UtileKit.currencyCunvertion(mTotal));
                }
            }
        }
    }
    @Override
    public void onChangeTextEditText(String s) {

    }


    @Override
    public void incomeSelectedLevOneList(ArrayList<Income_cat_lev1> selectedList) {
        selectedLev0List=new ArrayList<>();
        if(!selectedList.isEmpty() && selectedList!= null) {
            for (int i = 0; i < selectedList.size(); i++) {
                selectedLev0List.add(selectedList.get(i).getId().replaceAll("\\s+", ""));
            }

            android.support.v4.app.FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();
            android.support.v4.app.DialogFragment newFragment = LevelSecondSelectionDialog.newInstance(familyId, selectedList, incomeFilterHashMap, incLev2SelLisInterface, selectedLev1List, selectedLev2List);
            newFragment.show(fm, "dialog");
        }else{
            selectedLev1List.clear();
            selectedLev2List.clear();
            incomeSelectedIncomeCat();
        }

    }

    @Override
    public void selectionNextButtonClick() {
        addFragmenttoStack(ExpenseTabMainFragment.newInstance(0));

    }

    @Override
    public void expenseSelectedLevOneList(ArrayList<ExpensesLevelZeroData> selectedList) {

    }

    public void setIncomeLev1SelectLisaner(RetIncomeDynamicDetail incomeLev1SelectLisaner) {
        this.incomeLev1SelectLisaner = incomeLev1SelectLisaner;
    }

    @Override
    public void incomeSelectedLev2List(ArrayList<String> seleLev2List) {
        //Log.e("setIncomeLev2SelnLi","Works***_______*****"+seleLev2List.toString());
    }

    @Override
    public void incomeSelectedLev3List(ArrayList<String> seleLev3List) {
        //Log.e("setIncomeLev3SelnLi","Works***_______*****"+seleLev3List.toString());
    }

    public void setIncomeLev2SelectionLisaner(RetIncomeDynamicDetail incomeLev2SelectionLisaner) {
        this.incLev2SelLisInterface = incomeLev2SelectionLisaner;
        //Log.e("setIncomeLev2SelnLi","Works***_______*****");
    }

    @Override
    public void incomeSelectedIncomeCat() {

        //Log.e("Done","Works*****____________******");

//        if(!selectedLev0List.isEmpty())
        {

            WebServiceCalls webServiceObj;
            webServiceObj = ServiceGenerator
                    .createService(WebServiceCalls.class);
            String is_own_user;
            if(familyId.equalsIgnoreCase("0"))
            {
                is_own_user="Y";
            }else
            {
                is_own_user="N";
            }
            Call<IncomeInputModel> callIncome = webServiceObj.callAddIncomepostretSelectService(UtileKit.getPersistedPurplePathPref("user_id"),
                    selectedLev0List.toString().replace("[","").replace("]","").replaceAll("\\s+",""),
                    selectedLev1List.toString().replace("[","").replace("]","").replaceAll("\\s+",""),
                    selectedLev2List.toString().replace("[","").replace("]","").replaceAll("\\s+",""),
                    familyId,is_own_user
            );
            UtileKit.showSpinnerDialog(mContext,false);
            callIncome.enqueue(new Callback<IncomeInputModel>() {
                @Override
                public void onResponse(Call<IncomeInputModel> call, Response<IncomeInputModel> response) {
                    UtileKit.dismisssSpinnerDialog();
                    IncomeInputModel categoryModel = response.body();
                    if (categoryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        //  mIncomeInputModel = categoryModel;

//                    addViewto(fragmentview,mIncomeCategoryModel)
                        if(categoryModel!=null)
                        { if(userIncomeDetailModel==null)
                            userIncomeDetailModel=new User_incomes();
                            HashMap<String,Income_cat_lev1> incomeSelectedHashMap=new HashMap<>();
                            if(categoryModel.getData().getIncome_cat_lev1()!=null)
                            {
                                selLev1List.clear();
                                selLev2List.clear();
                                selLev3List.clear();
                                selLev1List.addAll(selectedLev0List);
                                selLev2List.addAll(selectedLev1List);
                                selLev3List.addAll(selectedLev2List);
                                int size = categoryModel.getData().getIncome_cat_lev1().size();
                                for (int i = 0; i < size; i++)
                                {
//                                            //Log.e("Lev1", "" + categoryModel.getData().getIncome_cat_lev1().get(i).getLev1_name());

                                    Income_cat_lev1 obj = categoryModel.getData().getIncome_cat_lev1().get(i);
//                                    userIncomeDetailModel.setIncome_cat_lev1(categoryModel.getData().getIncome_cat_lev1());
                                    obj.setCat_lev2List(getLev2CatgoryList(categoryModel.getData().getIncome_cat_lev1().get(i).getId(), categoryModel));
                                    incomeSelectedHashMap.put("" + i, obj);
                                }
                                viewParentLinerView.removeAllViews();
                                addParentView(incomeSelectedHashMap, viewParentLinerView);
                            }

                        }

                    } else {
                        UtileKit.intitializeAlertDialog(
                                categoryModel.getData().getMessage(),
                                mContext);

//                        UtileKit.alertDialog(
//                                categoryModel.getData().getMessage(),
//                                mContext);
                    }
                }

                @Override
                public void onFailure(Call<IncomeInputModel> call, Throwable t) {
                    UtileKit.alertRetrofitExceptionDialog( mContext,t);
                    UtileKit.dismisssSpinnerDialog();
                }
            });
        }
    }

    @Override
    public void dialogNextButtonClick() {
        selectionNextButtonClick();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CalculatorAct.REQUEST_RESULT_SUCCESSFUL) {
            String result = data.getStringExtra(CalculatorAct.RESULT);
            ((CurrencyGroupView) nameEditview).setText(result);
        }
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    /**
     * Generate a value suitable for use in {@link #(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}

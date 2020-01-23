package com.purplepath.purplepath.expenseEDCOC.PreRetirementExpence;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyEditText;
import com.purplepath.purplepath.customview.CurrencyGroupView;
import com.purplepath.purplepath.dialog.ExpectedIncrementDialogFragment;
import com.purplepath.purplepath.expenseEDCOC.selectiondialog.LevelOneSelectorDaialog;
import com.purplepath.purplepath.expenseEDCOC.selectiondialog.LevelSecondSelectionExpDialog;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesDetailModel;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesDetailsData;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelOneData;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelThreeData;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelTwoData;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelZeroData;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev0;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev1;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev2;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev3;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.ExpensesSelectionAddModel;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.GetExpensesDetailsModel;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.User_expense;
import com.purplepath.purplepath.expensesRedesign.updateexpensedetailsmodel.ExpensesUpdateModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncLev2SelLisInterface;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncSelecOnDismisInterf;
import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev1;
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

import static com.purplepath.purplepath.apputiles.UtileKit.getPersistedPurplePathPref;


/**
 * Created by dinesh on 30/05/17.
 */

public class ExpenseDynamicFragment extends BaseFragment implements View.OnClickListener, ExpectedIncrementDialogFragment.OnExpectedValueListener, CurrencyEditText.OnTextChangeEditText, IncSelecOnDismisInterf, IncLev2SelLisInterface {

    public static final String TITLE = "";
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public ArrayList<String> selLev0List = new ArrayList<>(),
            selLev1List = new ArrayList<>(),
            selLev2List = new ArrayList<>(),
            selLev3List = new ArrayList<>();
    public JSONArray jsonArray = new JSONArray();
    GetExpensesDetailsModel expenceModel;
    ExpensesDetailModel expenseCatMoel;
    ArrayList<String> lifeExpactancyList = new ArrayList<>();
    View nameEditview;
    TextView postRetirementbtn;
    private String PARENT_CLASS_SOURCE = "ExpenseDynamicFragment";
    private ArrayList<String> selectedLev0List,
            selectedLev1List,
            selectedLev2List;
    private HashMap<String, Expense_cat_lev0> expenseFilterHashMap = new HashMap<>();
    private HashMap<String, ExpensesLevelZeroData> expenseCatFilterHashMap = new HashMap<>();
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout, add_cartoon_layout;
    private LinearLayout viewParentLinerView;
    private HashMap<String, ArrayList<ArrayList<CurrencyGroupView>>> curencyListMap = new HashMap<>();
    private HashMap<String, ArrayList<ArrayList<ImageView>>> lifeExpactancyImgView = new HashMap<>();
    private Context mContext;
    private OnActivityBackPressedListener mCallBackListener;
    private IncSelecOnDismisInterf incomeLev1SelectLisaner;
    private IncLev2SelLisInterface incLev2SelLisInterface;

    public static ExpenseDynamicFragment newInstance(GetExpensesDetailsModel obj, ExpensesDetailModel expenceCatagoryModel, Boolean isFisttime) {

        Bundle args = new Bundle();

        ExpenseDynamicFragment fragment = new ExpenseDynamicFragment();
        args.putSerializable("GetExpensesDetailsModel", obj);
        args.putSerializable("catagory", expenceCatagoryModel);
//        ,String typeflag
//        args.putString("flag",typeflag);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Generate a value suitable for use in {@link #(int)}.
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selLev0List = new ArrayList<>();
        selLev1List = new ArrayList<>();
        selLev2List = new ArrayList<>();
        selLev3List = new ArrayList<>();
        selectedLev0List = new ArrayList<>();
        selectedLev1List = new ArrayList<>();
        selectedLev2List = new ArrayList<>();
        setIncomeLev1SelectLisaner(this);
        setIncomeLev2SelectionLisaner(this);

        mContext = getContext();
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        if (getArguments().containsKey("GetExpensesDetailsModel")) {
            expenceModel = (GetExpensesDetailsModel) getArguments().getSerializable("GetExpensesDetailsModel");

            setExpenseFilterdData(expenceModel);
        }

        if (expenceModel == null) {

//            //Log.e("Level1______", "" + selLev0List.size());
            FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
            DialogFragment newFragment = LevelOneSelectorDaialog.newInstance(expenseCatFilterHashMap,
                    incomeLev1SelectLisaner, selLev0List);
            newFragment.show(fm, "dialog");
        }
        if (getArguments().containsKey("catagory")) {
            expenseCatMoel = (ExpensesDetailModel) getArguments().getSerializable("catagory");
            try {
                if (expenseCatMoel != null) {
                    int size = expenseCatMoel.getData().getExpense_cat_lev0().size();
                    for (int i = 0; i < size; i++) {
                        //Log.e("FistLev1", "" + expenseCatMoel.getData().getExpense_cat_lev0().get(0).getLev0_name());
                        ExpensesLevelZeroData obj = expenseCatMoel.getData().getExpense_cat_lev0().get(i);
                        obj.setExpense_cat_lev1(getLev1CatgoryList(expenseCatMoel.getData().getExpense_cat_lev0().get(i).getId(), expenseCatMoel.getData()));
                        expenseCatFilterHashMap.put("" + i, obj);
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setExpenseFilterdData(GetExpensesDetailsModel expenceModel) {

        try {
            selectedLev0List = new ArrayList<>();
            selectedLev1List = new ArrayList<>();
            selectedLev2List = new ArrayList<>();
            if (expenceModel != null) {
                if (expenceModel.getData().getUser_expense() != null) {
                    try {
                        selLev0List = new ArrayList<String>(Arrays.asList(expenceModel.getData().getUser_expense().get(0).getLevel_0_ids().split(",")));
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    selLev1List = new ArrayList<String>(Arrays.asList(expenceModel.getData().getUser_expense().get(0).getLevel_1_ids().split(",")));
                    selLev2List = new ArrayList<String>(Arrays.asList(expenceModel.getData().getUser_expense().get(0).getLevel_2_ids().split(",")));
                    selLev3List = new ArrayList<String>(Arrays.asList(expenceModel.getData().getUser_expense().get(0).getLevel_3_ids().split(",")));

                    for (int i = 0; i < selLev0List.size(); i++) {
                        selLev0List.set(i, selLev0List.get(i).replaceAll("\\s+", "").replaceAll(" ", ""));
                        selectedLev0List.add(selLev0List.get(i));
                    }
                    for (int i = 0; i < selLev1List.size(); i++) {
                        selLev1List.set(i, selLev1List.get(i).replaceAll("\\s+", "").replaceAll(" ", ""));
                        selectedLev1List.add(selLev1List.get(i));
                    }
                    for (int i = 0; i < selLev2List.size(); i++) {
                        selLev2List.set(i, selLev2List.get(i).replaceAll("\\s+", "").replaceAll(" ", ""));
                        selectedLev2List.add(selLev2List.get(i));
                    }
                    for (int i = 0; i < selLev3List.size(); i++) {
                        selLev3List.set(i, selLev3List.get(i).replaceAll("\\s+", "").replaceAll(" ", ""));
                    }
                    int size = expenceModel.getData().getUser_expense().get(0).getExpense_cat_lev0().size();
                    for (int i = 0; i < size; i++) {
                        //Log.e("FistLev1", "" + expenceModel.getData().getUser_expense().get(0).getExpense_cat_lev0().get(i).getLev0_name());
                        Expense_cat_lev0 obj = expenceModel.getData().getUser_expense().get(0).getExpense_cat_lev0().get(i);
                        obj.setExpense_cat_lev1(getLev1CatgoryList(expenceModel.getData().getUser_expense().get(0).getExpense_cat_lev0().get(i).getId(), expenceModel.getData().getUser_expense().get(0)));

                        expenseFilterHashMap.put("" + i, obj);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View expenceDetailView = inflater.inflate(R.layout.fragment_inc_usr_dyn_detil, container, false);
        return expenceDetailView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        postRetirementbtn = view.findViewById(R.id.goal_headear_bg_TxtView);
        add_cartoon_layout = view.findViewById(R.id.add_cartoon_layout);
        viewParentLinerView = view.findViewById(R.id.addIncomeView);
        FloatingActionButton fab = view.findViewById(R.id.incomefabId);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIncomeDetail(false, "fab");

//                //Log.e("Level1______", "" + selLev0List.size());
//                selectedLev0List=new ArrayList<String>();
//                selectedLev0List.addAll(selLev0List);


            }
        });
        addParentView(expenseFilterHashMap, viewParentLinerView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CalculatorAct.REQUEST_RESULT_SUCCESSFUL) {
            String result = data.getStringExtra(CalculatorAct.RESULT);
            ((CurrencyGroupView) nameEditview).setText(result);
            ((CurrencyGroupView) nameEditview).getEditText().setBackgroundResource(R.drawable.edittextbackgrounggreen);
        }
    }

    private void addParentView(HashMap<String, Expense_cat_lev0> incomeCatHashList, LinearLayout parentView) {
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
                    totalTextview.setText("" + incomeCatHashList.get("" + i).getLev0_name());
                    CurrencyGroupView totalEditview = view.findViewById(R.id.incomeTotalEditId);
                    totalEditview.setId(ViewId++);
                    totalEditview.setTag(incomeCatHashList.get("" + i).getTb_field_name());
                    totalEditview.getEditText().setId(generateViewId());
                    currencyEditTexts.add(totalEditview);
                    currencyArrayEditTexts.add(currencyEditTexts);
//                    totalEditview.setOnTextChangeEditTextListerner(this);
                    totalEditview.setTextHint("Total");
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

                    if (incomeCatHashList.get("" + i).getExpense_cat_lev1() != null) {

                        int lev2Size = incomeCatHashList.get("" + i).getExpense_cat_lev1().size();

                        for (int j = 0; j < lev2Size; j++) {
                            ArrayList<CurrencyGroupView> currencyEditLEv2List = new ArrayList<>();
                            ArrayList<ImageView> lifeExpLEV2ImgViewList = new ArrayList<>();
                            int lev3Size = 0;
                            if (incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getExpense_cat_lev2() != null)
                                lev3Size = incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getExpense_cat_lev2().size();
                            if (lev3Size == 0) {
                                View lev2view = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
                                final CurrencyGroupView nameEditview = lev2view.findViewById(R.id.currencyEditTxt);
                                currencyEditLEv2List.add(nameEditview);
                                nameEditview.getEditText().setId(generateViewId());
                                nameEditview.getEditText().setOnTextChangeEditTextListerner(this, nameEditview.getAmountInWords());
                                TextInputLayout hintText = lev2view.findViewById(R.id.textInput);
                                ImageView lifeExpLev2View = lev2view.findViewById(R.id.lifeExptImgPopView);
                                ImageView lifeExpLev2CalView = lev2view.findViewById(R.id.calculaterImgView);
                                if (incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getInfo_value() != null) {
                                    lifeExpactancyList.add("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getInfo_value());
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
                                nameEditview.setTag("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getTb_field_name());
                                lifeExpLev2CalView.setTag("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getTb_field_name());
                                lifeExpLev2CalView.setId(ViewId++);
                                nameEditview.setId(ViewId++);

                                if (incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getValue() != null) {
                                    nameEditview.setText("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getValue());
                                    //Log.e("incomeCatHashList", "" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getTb_field_name() + "" + incomeCatHashList.get("" + i).getValue().toString());
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
                                 * dinesh
                                 *    hintText.setHint("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getLev1_name());
                                 */
                                nameEditview.setTextHint("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getLev1_name());

                                parentView.addView(lev2view);
                            } else {
                                View lev2Leadview = LayoutInflater.from(mContext).inflate(R.layout.add_income_total_header, null);
                                TextView lev2TotTextview = lev2Leadview.findViewById(R.id.incomeTotalTxtId);
                                lev2TotTextview.setText("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getLev1_name());
                                final CurrencyGroupView lev2Editview = lev2Leadview.findViewById(R.id.incomeTotalEditId);
                                ImageView lifeExpTitleLev2View = lev2Leadview.findViewById(R.id.lifeExptImgPopView);
                                ImageView lifeExpTitleLev2CalView = lev2Leadview.findViewById(R.id.calExpImgListView);
                                currencyEditLEv2List.add(lev2Editview);
//                            lev2Editview.setOnTextChangeEditTextListerner(this);
                                lev2Editview.setTextHint("Total");
                                lev2Editview.getEditText().setId(generateViewId());

                                if (incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getInfo_value() != null) {
                                    lifeExpactancyList.add("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getInfo_value());
                                } else
                                    lifeExpactancyList.add("");

                                lifeExpTitleLev2View.setTag(lifeExpactancyList.size());
                                lifeExpTitleLev2View.setId(lifeExpactancyList.size());
//                                lifeExpImgListView.add(lifeExpTitleLev2View);
                                lifeExpLEV2ImgViewList.add(lifeExpTitleLev2View);
                                lev2Editview.setTag("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getTb_field_name());
                                lifeExpTitleLev2CalView.setTag("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getTb_field_name());
                                lifeExpTitleLev2CalView.setId(ViewId++);
                                lev2Editview.setId(ViewId++);
                                if (incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getValue() != null) {
                                    lev2Editview.setText("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getValue());
                                    //Log.e("incomeCatHashList", "" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getTb_field_name() + "" + incomeCatHashList.get("" + i).getValue().toString());
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
                                    nameLev3Editview.getEditText().setOnTextChangeEditTextListerner(this, nameLev3Editview.getAmountInWords());
                                    TextInputLayout hintLev3Text = lev3view.findViewById(R.id.textInput);
                                    ImageView lifeExpLev3View = lev3view.findViewById(R.id.lifeExptImgPopView);
                                    ImageView lifeExpLev3CalView = lev3view.findViewById(R.id.calculaterImgView);
                                    if (incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getExpense_cat_lev2().get(k).getInfo_value() != null) {
                                        lifeExpactancyList.add("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getExpense_cat_lev2().get(k).getInfo_value());
                                    } else
                                        lifeExpactancyList.add("");

                                    lifeExpLev3View.setTag(lifeExpactancyList.size());
                                    lifeExpLev3View.setId(lifeExpactancyList.size());

//                                    lifeExpImgListView.add(lifeExpLev3View);
                                    lifeExpLEV2ImgViewList.add(lifeExpLev3View);
                                    nameLev3Editview.getEditText().setId(generateViewId());
                                    nameLev3Editview.setTag("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getExpense_cat_lev2().get(k).getTb_field_name());
                                    lifeExpLev3CalView.setTag("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getExpense_cat_lev2().get(k).getTb_field_name());
                                    nameLev3Editview.setId(ViewId++);
                                    lifeExpLev3CalView.setId(ViewId++);
                                    if (incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getExpense_cat_lev2().get(k).getValue() != null) {
                                        nameLev3Editview.setText("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getExpense_cat_lev2().get(k).getValue());
                                        //Log.e("incomeCatHashList", "" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getTb_field_name() + "" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getExpense_cat_lev2().get(k).getValue());
                                    }
                                    lifeExpLev3CalView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            showCalDialog(nameLev3Editview);
                                        }
                                    });
//                        nameEditview.setDefaultHintEnabled(true);

                                    /**
                                     * dinesh
                                     * hintLev3Text.setHint("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getExpense_cat_lev2().get(k).getLev2_name());
                                     */

                                    nameLev3Editview.setTextHint("" + incomeCatHashList.get("" + i).getExpense_cat_lev1().get(j).getExpense_cat_lev2().get(k).getLev2_name());
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
                        for (int l = 0; l < lifeExpactancyImgView.get("" + m).get(n).size(); l++) {

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
            onChangeTextEditText("", null);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Expense_cat_lev1> getLev1CatgoryList(String lev1headerTagId, User_expense mCategoryModel) {

        ArrayList<Expense_cat_lev1> incomeCatLev1List = new ArrayList<>();
        try {
            for (int j = 0; j < mCategoryModel.getExpense_cat_lev1().size(); j++) {
                if (mCategoryModel.getExpense_cat_lev1().get(j).getLev0_id().equals(lev1headerTagId)) {
                    Expense_cat_lev1 lev1obj = mCategoryModel.getExpense_cat_lev1().get(j);
                    String lev1Id = lev1obj.getId();
                    //Log.e("Lev1", "" + lev1obj.getLev1_name() + "Lev1" + lev1obj.getId());
                    ArrayList<Expense_cat_lev2> lev2Obj = new ArrayList<>();
                    for (int k = 0; k < mCategoryModel.getExpense_cat_lev2().size(); k++) {
                        if (lev1Id.equalsIgnoreCase(mCategoryModel.getExpense_cat_lev2().get(k).getLev1_id())) {

                            String lev2Id = mCategoryModel.getExpense_cat_lev2().get(k).getId();
                            //Log.e("Lev2", "" + lev2Id + "Lev2" + mCategoryModel.getExpense_cat_lev2().get(k).getLev2_name());
                            ArrayList<Expense_cat_lev3> lev3Obj = new ArrayList<>();
                            for (int l = 0; l < mCategoryModel.getExpense_cat_lev3().size(); l++) {
                                if (lev2Id.equalsIgnoreCase(mCategoryModel.getExpense_cat_lev3().get(l).getLev2_id())) {
                                    lev3Obj.add(mCategoryModel.getExpense_cat_lev3().get(l));
                                    //Log.e("Lev3", "" + lev2Id + "Lev3" + mCategoryModel.getExpense_cat_lev3().get(l).getLev3_name());
                                }
                            }
                            Expense_cat_lev2 expLev2Obj = mCategoryModel.getExpense_cat_lev2().get(k);
                            expLev2Obj.setExpense_cat_lev3(lev3Obj);
                            lev2Obj.add(expLev2Obj);
//                        //Log.e("Lev3",""+ mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev3_name());

//                        //Log.e("Lev2",""+ob.getLev2_name()+"Lev1"+ob.getLev1_id()+"Lev3"+ mCategoryModel.getData().getIncome_cat_lev3().get(k).getId());
                        }
                    }

                    lev1obj.setExpense_cat_lev2(lev2Obj);
                    incomeCatLev1List.add(lev1obj);
                }


            }
        } catch (Exception e) {
            return null;
        }
        return incomeCatLev1List;
    }

    private ArrayList<ExpensesLevelOneData> getLev1CatgoryList(String lev1headerTagId, ExpensesDetailsData mCategoryModel) {

        ArrayList<ExpensesLevelOneData> incomeCatLev1List = new ArrayList<>();
        try {
            for (int j = 0; j < mCategoryModel.getExpense_cat_lev1().size(); j++) {
                if (mCategoryModel.getExpense_cat_lev1().get(j).getLev0_id().equals(lev1headerTagId)) {
                    ExpensesLevelOneData lev1obj = mCategoryModel.getExpense_cat_lev1().get(j);
                    String lev1Id = lev1obj.getId();
                    //Log.e("Lev1", "" + lev1obj.getLev1_name() + "Lev1" + lev1obj.getId());
                    ArrayList<ExpensesLevelTwoData> lev2Obj = new ArrayList<>();
                    for (int k = 0; k < mCategoryModel.getExpense_cat_lev2().size(); k++) {
                        if (lev1Id.equalsIgnoreCase(mCategoryModel.getExpense_cat_lev2().get(k).getLev1_id())) {

                            String lev2Id = mCategoryModel.getExpense_cat_lev2().get(k).getId();
                            //Log.e("Lev2", "" + lev2Id + "Lev2" + mCategoryModel.getExpense_cat_lev2().get(k).getLev2_name());
                            ArrayList<ExpensesLevelThreeData> lev3Obj = new ArrayList<>();
                            for (int l = 0; l < mCategoryModel.getExpense_cat_lev3().size(); l++) {
                                if (lev2Id.equalsIgnoreCase(mCategoryModel.getExpense_cat_lev3().get(l).getLev2_id())) {
                                    lev3Obj.add(mCategoryModel.getExpense_cat_lev3().get(l));
                                    //Log.e("Lev3", "" + lev2Id + "Lev3" + mCategoryModel.getExpense_cat_lev3().get(l).getLev3_name());
                                }
                            }
                            ExpensesLevelTwoData expLev2Obj = mCategoryModel.getExpense_cat_lev2().get(k);
                            expLev2Obj.setExpensesLevelThreeData(lev3Obj);
                            lev2Obj.add(expLev2Obj);
//                        //Log.e("Lev3",""+ mCategoryModel.getData().getIncome_cat_lev3().get(k).getLev3_name());

//                        //Log.e("Lev2",""+ob.getLev2_name()+"Lev1"+ob.getLev1_id()+"Lev3"+ mCategoryModel.getData().getIncome_cat_lev3().get(k).getId());
                        }
                    }

                    lev1obj.setExpensesLevelTwoData(lev2Obj);
                    incomeCatLev1List.add(lev1obj);
                }


            }
        } catch (Exception e) {
            return null;
        }
        return incomeCatLev1List;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow: {
                saveIncomeDetail(true, "back");
//                mCallBackListener.onActivityBackPressed();

            }
            break;
            case R.id.relative_center_home: {
                saveIncomeDetail(true, "home");

//                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow: {
                saveIncomeDetail(true, "back");
//                mCallBackListener.onActivityBackPressed();

            }
        }

    }

    private void showCalDialog(View view) {
        nameEditview = view;
//        nameEditview.setTag(view.getTag());
        String calculaterValue = ((CurrencyGroupView) nameEditview).getText().toString();
        Intent calculatorIntent = new Intent(getActivity(), CalculatorAct.class);
        calculatorIntent.putExtra(CalculatorAct.TITLE_ACTIVITY, TITLE);
        calculatorIntent.putExtra(CalculatorAct.PARENT_ACTIVITY, PARENT_CLASS_SOURCE);
        calculatorIntent.putExtra(CalculatorAct.VALUE, calculaterValue);
        startActivityForResult(calculatorIntent, CalculatorAct.REQUEST_RESULT_SUCCESSFUL);

    }

    private void showPopupDialog(View view, String s) {
        FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
        DialogFragment newFragment = ExpectedIncrementDialogFragment.newInstance(this, view, s);

        newFragment.show(fm, "dialog");
    }

    @Override
    public void incomeSelectedLev2List(ArrayList<String> seleLev2List) {
        //Log.e("setIncomeLev2SelnLi", "Works***_______*****" + seleLev2List.toString());
    }

    @Override
    public void incomeSelectedLev3List(ArrayList<String> seleLev3List) {
        //Log.e("setIncomeLev3SelnLi", "Works***_______*****" + seleLev3List.toString());
    }

    @Override
    public void expenseSelectedLevOneList(ArrayList<ExpensesLevelZeroData> selectedList) {
        selectedLev0List = new ArrayList<>();
        add_cartoon_layout.setVisibility(View.GONE);
        if (selectedList != null && !selectedList.isEmpty()) {
            viewParentLinerView.setVisibility(View.VISIBLE);
            for (int i = 0; i < selectedList.size(); i++) {
                selectedLev0List.add(selectedList.get(i).getId().replaceAll("\\s+", ""));
            }

            android.support.v4.app.FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();
            android.support.v4.app.DialogFragment newFragment = LevelSecondSelectionExpDialog.newInstance(selectedList, expenseCatFilterHashMap, incLev2SelLisInterface, selectedLev1List, selectedLev2List);
            newFragment.show(fm, "dialog");
        } else {
            selectedLev1List.clear();
            selectedLev2List.clear();
            incomeSelectedIncomeCat();
            postRetirementbtn.setText("Please add Expenses by clicking the + button");
            add_cartoon_layout.setVisibility(View.VISIBLE);
            viewParentLinerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void incomeSelectedLevOneList(ArrayList<Income_cat_lev1> selectedList) {


    }

    @Override
    public void incomeSelectedIncomeCat() {
        //Log.e("Done", "Works*****____________******");

//        if (!selectedLev0List.isEmpty()) {

//            HashSet hs = new HashSet();
//            hs.addAll(selectedLev0List);
//            selectedLev0List.clear();
//            selectedLev0List.addAll(hs);
//            hs.clear();
//            hs.addAll(selectedLev1List);
//            selectedLev1List.clear();
//            selectedLev1List.addAll(hs);
//            hs.clear();
//            hs.addAll(selectedLev2List);
//            selectedLev2List.clear();
//            selectedLev2List.addAll(hs);

        callAddExpensesService(getPersistedPurplePathPref("user_id"),
                selectedLev0List.toString().replace("[", "").replace("]", "").replaceAll("\\s+", ""),
                selectedLev1List.toString().replace("[", "").replace("]", "").replaceAll("\\s+", ""),
                selectedLev2List.toString().replace("[", "").replace("]", "").replaceAll("\\s+", ""));

//            UtileKit.showSpinnerDialog(mContext, false);

//        }
    }

    private void callAddExpensesService(String user_id, String mSelLev0List, String mSelLev1List, String mSelLev2List) {
        WebServiceCalls webServiceObj;

        //Log.e("TAG", "selLev0List" + selLev0List + "selLev1List" + selLev1List + "selLev2List" + selLev2List);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<ExpensesSelectionAddModel> call = webServiceObj.callGetExpensesAddModelService(
                user_id, "Y", mSelLev0List, mSelLev1List, mSelLev2List, "1", "0");
        call.enqueue(new Callback<ExpensesSelectionAddModel>() {
            @Override
            public void onResponse(Call<ExpensesSelectionAddModel> call, Response<ExpensesSelectionAddModel> response) {
                UtileKit.dismisssSpinnerDialog();
                ExpensesSelectionAddModel expenceModel = response.body();
                if (expenceModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    try {
                        selLev1List.clear();
                        selLev2List.clear();
                        selLev3List.clear();
                        selLev1List.addAll(selectedLev0List);
                        selLev2List.addAll(selectedLev1List);
                        selLev3List.addAll(selectedLev2List);
                        callGetExpensesService();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    UtileKit.dismisssSpinnerDialog();
                }
            }

            @Override
            public void onFailure(Call<ExpensesSelectionAddModel> call, Throwable t) {

                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }

    @Override
    public void selectionNextButtonClick() {
        if (expenceModel == null) {
            mCallBackListener.onActivityBackPressed();
        }

    }

    @Override
    public void dialogNextButtonClick() {

    }

    /**
     * LifeExpense Array List of saved data
     *
     * @param dialog
     * @param age
     * @param percentage
     * @param id
     * @throws JSONException
     */
    @Override
    public void onValueSet(ExpectedIncrementDialogFragment dialog, String age, String percentage, int id) throws JSONException {
        lifeExpactancyList.set(id - 1, "" + age + "," + percentage);
    }



    @Override
    public void onChangeTextEditText(String s, TextView spanText) {


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

    private void saveIncomeDetail(boolean isback, String type) {

        int total = 0;
        String mTotalSalery = "0", mOwnUser;
        JSONObject jsonIncomeObject = null;

        String UserId = getPersistedPurplePathPref("user_id");

        if (!curencyListMap.isEmpty()) {

            for (int i = 0; i < curencyListMap.size(); i++) {
                if (curencyListMap.get("" + i) != null) {
                    int mLev1Total = 0;
                    int lev2Size = curencyListMap.get("" + i).size();
                    CurrencyGroupView currencyTitleEditTxt = curencyListMap.get("" + i).get(0).get(0);
                    for (int k = 1; k < lev2Size; k++) {
                        if (lev2Size >= 2) {
                            int lev3size = curencyListMap.get("" + i).get(k).size();
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
                                                if (j == 0) {
                                                    /**
                                                     * Skip Lev 2 Header on Toatal
                                                     */
                                                    if (lev3size == 1) {
                                                        total = total + curentAmout;
                                                        mLev1Total = mLev1Total + curentAmout;
                                                    }
                                                } else {

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

                                            Log.i("", "addToJsonObject lifeExpactancyList" + "I " + mLifeExpVal + " ****" + lifeExpactancyList.get(mLifeExpVal - 1));
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
            callGetExpensesUpdateModelService(UserId,
                    overallTotal, isback, type);
        } else {
            if (isback) {
                if (type.equals("back"))
                    mCallBackListener.onActivityBackPressed();
            } else if (type.equals("home")) {
                startHomeActivity();
            } else if (type.equals("fab")) {
                FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
                DialogFragment newFragment = LevelOneSelectorDaialog.newInstance(expenseCatFilterHashMap, incomeLev1SelectLisaner, selectedLev0List);
                newFragment.show(fm, "dialog");
            }
        }
    }

    /**
     * Add Json to Expense
     *
     * @param field
     * @param value
     */
    private void addToJsonObject(String field, String value) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("field", field);
            jsonObject.put("value", value);

            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update User Expense
     *
     * @param userId
     * @param overallTotal
     * @param isback
     * @param type
     */
    public void callGetExpensesUpdateModelService(String userId, String overallTotal, final boolean isback, final String type) {
        WebServiceCalls webServiceObj;
        Log.i("DumAss", "" + jsonArray.toString());
        JSONObject jsonEssentialObject = new JSONObject();
        try {
            jsonEssentialObject.put("exp_det", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = jsonEssentialObject.toString();
//        String overallvalue = String.valueOf(moverallExpenses);
        Log.i("strjsonstring", "" + str);
        Log.i("level_one_ids", "" + selLev0List);
        Log.i("level_one_ids", "" + selLev1List);
        Log.i("level_one_ids", "" + selLev2List);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<ExpensesUpdateModel> call = webServiceObj.callGetExpensesUpdateModelService(
                userId, selLev0List.toString().replace("[", "").replace("]", "").replaceAll("\\s+", ""),
                selLev1List.toString().replace("[", "").replace("]", "").replaceAll("\\s+", ""),
                selLev2List.toString().replace("[", "").replace("]", "").replaceAll("\\s+", ""),
                selLev3List.toString().replace("[", "").replace("]", "").replaceAll("\\s+", ""),
                str, overallTotal, "Y", "0");
//                UtileKit.getPersistedPurplePathPref("user_id"), level_one_ids, level_two_ids, level_three_ids,exp_id, str, overallvalue);
        call.enqueue(new Callback<ExpensesUpdateModel>() {
            @Override
            public void onResponse(Call<ExpensesUpdateModel> call, Response<ExpensesUpdateModel> response) {
                ExpensesUpdateModel expenceModel = response.body();

                UtileKit.dismisssSpinnerDialog();
                if (isback)
                    mCallBackListener.onActivityBackPressed();
                else if (type.equals("home")) {
                    startHomeActivity();

                } else if (type.equals("fab")) {
                    FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
                    DialogFragment newFragment = LevelOneSelectorDaialog.newInstance(expenseCatFilterHashMap, incomeLev1SelectLisaner, selectedLev0List);
                    newFragment.show(fm, "dialog");
                }
            }

            @Override
            public void onFailure(Call<ExpensesUpdateModel> call, Throwable t) {

                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
        UtileKit.dismisssSpinnerDialog();
    }

    /**
     * interface initialisation onCreate
     *
     * @param mLev1SelectLisaner
     */
    public void setIncomeLev1SelectLisaner(ExpenseDynamicFragment mLev1SelectLisaner) {
        this.incomeLev1SelectLisaner = mLev1SelectLisaner;
    }

    /**
     * interface initialisation onCreate
     *
     * @param mLev2SelectionLisaner
     */
    public void setIncomeLev2SelectionLisaner(ExpenseDynamicFragment mLev2SelectionLisaner) {
        this.incLev2SelLisInterface = mLev2SelectionLisaner;
    }

    public void callGetExpensesService() {
        WebServiceCalls webServiceObj;

        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetExpensesDetailsModel> call = webServiceObj.callGetExpensesDetailsService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetExpensesDetailsModel>() {
            @Override
            public void onResponse(Call<GetExpensesDetailsModel> call, Response<GetExpensesDetailsModel> response) {
                GetExpensesDetailsModel getExpensesDetailsModel = response.body();


                if (getExpensesDetailsModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {


                    if (getExpensesDetailsModel.getData().getUser_expense() != null) {
                        if (!getExpensesDetailsModel.getData().getUser_expense().isEmpty()) {
                            expenceModel = getExpensesDetailsModel;
                            setExpenseFilterdData(expenceModel);
                            addParentView(expenseFilterHashMap, viewParentLinerView);
                        }
                    }
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<GetExpensesDetailsModel> call, Throwable t) {

                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });

    }

    @Override
    public void onChangeTextEditText(String s) {

    }
}

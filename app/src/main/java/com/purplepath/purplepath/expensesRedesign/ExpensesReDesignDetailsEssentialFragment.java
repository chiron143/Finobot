//package com.purplepath.purplepath.expensesRedesign;
//
//import android.app.DialogFragment;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.TextInputLayout;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.calculator.CalculatorAct;
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.assets.AssetsDetailsFragment;
//import com.purplepath.purplepath.customview.CurrencyDefaultEdt;
//import com.purplepath.purplepath.customview.CurrencyEditText;
//import com.purplepath.purplepath.dia//Log.expectedIncrementDialogFragment;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesDetailModel;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelTwoData;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev1;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev2;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev3;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.GetExpensesDetailsModel;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.User_expense;
//import com.purplepath.purplepath.expensesRedesign.expensesselection.ExpensesSelectionDetailsFragment;
//import com.purplepath.purplepath.expensesRedesign.updateexpensedetailsmodel.ExpensesUpdateModel;
//import com.purplepath.purplepath.fragments.BaseFragment;
//import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
//import com.purplepath.purplepath.myinterface.OnSelectedDoneClickListener;
//import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
//import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class ExpensesReDesignDetailsEssentialFragment extends BaseFragment implements View.OnClickListener, ExpectedIncrementDialogFragment.OnExpectedValueListener, CurrencyEditText.OnTextChangeEditText{
//    private final static String TAG = ExpensesReDesignDetailsEssentialFragment.class.getCanonicalName();
//
//    private static Context mContext;
//    private ExpensesDetailModel mExpensesDetailModel;
//    private OnActivityBackPressedListener mCallBackListener;
////    public ArrayList<ExpensesLevelOneData> expensesLevelOneData = new ArrayList<ExpensesLevelOneData>();
////    public ArrayList<ExpensesLevelTwoData> expensesLevelTwoData = new ArrayList<ExpensesLevelTwoData>();
////    public ArrayList<ExpensesLevelThreeData> expensesLevelThreeData = new ArrayList<ExpensesLevelThreeData>();
//
//    private static ArrayList<Expense_cat_lev1> expensesLevelOneData;
//    private static ArrayList<Expense_cat_lev2> expensesLevelTwoData;
//    private static ArrayList<Expense_cat_lev3> expensesLevelThreeData;
//
//    private static ArrayList<View> mLevelOneListView;
//    private static ArrayList<View> mLevelTwoListView;
//    private static HashMap<Integer ,ArrayList<String>> mHashLifeExpView=new HashMap<>();
//    private static HashMap<String ,ArrayList<View>> mHashEditTextListView=new HashMap<>();
//    private static HashMap<Integer ,ArrayList<View>> mHashImgListView=new HashMap<>();
//
//    private static JSONArray jsonArray;
//    //public ArrayList<ExpensesLevelOneData> myLeveloneData = new ArrayList<ExpensesLevelOneData>();
//    // public ExpensesLevelOneData mexpensesOverallData = new ExpensesLevelOneData();
//    public LinearLayout linearLayout;
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//    private  GetExpensesDetailsModel getExpensesDetailsModel;
//    private User_expense user_expense;
//    private CurrencyDefaultEdt essentialTotal;
//
//    FragmentTransaction fragmentTransaction;
//    FragmentManager fragmentManager;
//    private static String level_zero,level_one_ids, level_two_ids, level_three_ids, exp_id;
//    private static int moverallExpenses;
//    OnSelectedDoneClickListener onSelectedDoneClickListener;
//    private View cartoonView;
//    private ScrollView mainView;
//    private static ArrayList<String> lifeExpactancyList;
//    int exp_value_position;
//    private static int get_value_position;
//    private FloatingActionButton fab;
//    public static final String PARENT_CLASS_SOURCE = "com.gp89developers.example.MainActivity";
//    public static final String TITLE = "CalculatorInputView";
//    CurrencyEditText nameEditview ;
//    public ExpensesReDesignDetailsEssentialFragment(OnSelectedDoneClickListener onSelectedDoneClickListene){
//        onSelectedDoneClickListener = onSelectedDoneClickListene;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//        mContext = getActivity();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View expensesdetailView = inflater.inflate(R.layout.fragment_expenses_redesign_essential, container, false);
//        cartoonView= expensesdetailView.findViewById(R.id.cartoonView);
//        mainView=(ScrollView)expensesdetailView.findViewById(R.id.scrollViewId);
//
//        callGetExpensesService();
//        changeAddView(expensesdetailView);
//        exp_value_position =0;
//        get_value_position =0;
//        fragmentManager = getActivity().getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        expensesLevelOneData = new ArrayList<Expense_cat_lev1>();
//        expensesLevelTwoData = new ArrayList<Expense_cat_lev2>();
//        expensesLevelThreeData = new ArrayList<Expense_cat_lev3>();
//        lifeExpactancyList = new ArrayList<>();
//
//        mLevelOneListView = new ArrayList<View>();
//        mLevelTwoListView = new ArrayList<View>();
//
//        mleftRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_left_arrow);
//        mcenterRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_center_home);
//        mRightRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_right_arrow);
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//        mRightRelativeLayout.setOnClickListener(this);
//
//        essentialTotal = (CurrencyDefaultEdt)expensesdetailView.findViewById(R.id.currencyEditTxtTotal);
//
//        fab = (FloatingActionButton) expensesdetailView.findViewById(R.id.redesign_essential_fab);
//        linearLayout = (LinearLayout) expensesdetailView.findViewById(R.id.epenses_row_layout);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onclickFragment();
//            }
//        });
////        if(!ExpListViewAdapterWithCheckbox.mEssentListCheckedLevelTwoDataGroup.isEmpty()){
////            if(ExpListViewAdapterWithCheckbox.mEssentListCheckedLevelTwoDataGroup.size()>0){
////                //callExpensesCategoriesService();
////
//////                for (int i = 0; i < ExpListViewAdapterWithCheckbox.mDiscretListCheckedLevelTwoDataGroup.size(); i++) {
//////                    ExpListViewAdapterWithCheckbox.mDiscretListCheckedLevelTwoDataGroup.get(i).getLev2_name();
//////                    View view = LayoutInflater.from(mContext).inflate(R.layout.add_row_view, null);
//////                    container.addView(view);
//////                }
////
////            }
////        }
//        return expensesdetailView;
//    }
//
//    private void changeAddView(View view) {
//        TextView textView = (TextView) view.findViewById(R.id.goal_headear_bg_TxtView);
//        textView.setText("Please add Expenses by clicking the + button");
//        ImageView imageView = (ImageView) view.findViewById(R.id.goal_click_image);
//        imageView.setBackgroundResource(R.drawable.ic_add_icon_big);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                onclickFragment();
//
//            }
//        });
//    }
//
//    private void onclickFragment() {
//        getInputValues();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        fragmentManager.popBackStack();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        ExpensesSelectionDetailsFragment fragment =  ExpensesSelectionDetailsFragment.newInstance(0);
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
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
//                // getAsArrayList(mExpensesDetailModel);
//            }
//            @Override
//            public void onFailure(Call<ExpensesDetailModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
//                UtileKit.dismisssSpinnerDialog();
//                UtileKit.alertRetrofitExceptionDialog( mContext, t);
//            }
//        });
//        UtileKit.dismisssSpinnerDialog();
//    }
//
//
////    private void getAsArrayList(ExpensesDetailModel expensesDetailModel){
////        if (expensesDetailModel.getStatusCode().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
////            for(int a=0; a<expensesDetailModel.getData().getExpense_cat_lev1().size(); a++) {
////                if(expensesDetailModel.getData().getExpense_cat_lev1().get(a).getType().equalsIgnoreCase("Essential")) {
////                    expensesLevelOneData.add(expensesDetailModel.getData().getExpense_cat_lev1().get(a));
////                }
////            }
////            expensesLevelTwoData = expensesDetailModel.getData().getExpense_cat_lev2();
////            expensesLevelThreeData  = expensesDetailModel.getData().getExpense_cat_lev3();
////
////            if(expensesLevelOneData !=null) {
////                for (int i = 0; i < expensesLevelOneData.size(); i++) {
////                    String level1Id = expensesLevelOneData.get(i).getId();
////                    myLeveloneData.add(expensesLevelOneData.get(i));
////                    // //Log.e("myLevelOneData",""+expensesLevelOneData.get(i).getLev1_name());
////                    View levelOneview = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
////                    TextInputLayout textInputLayout = (TextInputLayout) levelOneview.findViewById(R.id.textInput);
////                    textInputLayout.setHint(expensesLevelOneData.get(i).getLev1_name());
////                    CurrencyEditText eduvalue = (CurrencyEditText) levelOneview.findViewById(R.id.liabi_loan_amount_edt);
////                    ImageView leveloneimageView = (ImageView) levelOneview.findViewById(R.id.exp_education_list_edt_popup);
////                    leveloneimageView.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View view) {
////                           // view.setTag();
////                        }
////                    });
////
////                    linearLayout.addView(levelOneview);
////                    ArrayList<ExpensesLevelTwoData> myLevelTwolist = new ArrayList<ExpensesLevelTwoData>();
////                    for (int j = 0; j < expensesLevelTwoData.size(); j++) {
////                        String level2Id = expensesLevelTwoData.get(j).getLev1_id();
////                        String level3Id = null;
////                        if (level1Id.equalsIgnoreCase(level2Id)) {
////                            myLevelTwolist.add(expensesLevelTwoData.get(j));
////                            ////Log.e("myLevelTwoData",""+expensesLevelTwoData.get(j).getLev2_name());
////                            level3Id = expensesLevelTwoData.get(j).getId();
////                            myLeveloneData.get(i).setExpensesLevelTwoData(myLevelTwolist);
////
////                            View levelTwoView = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
////                            TextInputLayout levelTwotextInputLayout = (TextInputLayout) levelTwoView.findViewById(R.id.textInput);
////                            levelTwotextInputLayout.setHint(expensesLevelTwoData.get(j).getLev2_name());
////                            CurrencyEditText levelTwoEdt = (CurrencyEditText) levelTwoView.findViewById(R.id.liabi_loan_amount_edt);
////                            ImageView levelTwoimageView = (ImageView) levelTwoView.findViewById(R.id.exp_education_list_edt_popup);
////                            levelTwoimageView.setOnClickListener(new View.OnClickListener() {
////                                @Override
////                                public void onClick(View view) {
////                                    // view.setTag();
////                                }
////                            });
////                            linearLayout.addView(levelTwoView);
////
////                        }
////
////                        ArrayList<ExpensesLevelThreeData> myLevelThreelist = new ArrayList<ExpensesLevelThreeData>();
////                        if(expensesLevelThreeData !=null) {
////                            for (int k = 0; k < expensesLevelThreeData.size(); k++) {
////                                String mylevel3Id = expensesLevelThreeData.get(k).getLev2_id();
////                                if(level3Id != null) {
////                                    if (level3Id.equalsIgnoreCase(mylevel3Id)) {
////                                        myLevelThreelist.add(expensesLevelThreeData.get(k));
////                                        // //Log.e("myLevelThreeData", "" + expensesLevelThreeData.get(k).getLev3_name());
////                                        ////Log.e("myLeveltwosizeData", "jjjj"+j+"myj" + myLeveloneData.get(i).getExpensesLevelTwoData());
////                                        myLeveloneData.get(i).getExpensesLevelTwoData().get(myLeveloneData.get(i).getExpensesLevelTwoData().size()-1).setExpensesLevelThreeData(myLevelThreelist);
////
////                                        View levelThreeView = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
////                                        TextInputLayout levelThreetextInputLayout = (TextInputLayout) levelThreeView.findViewById(R.id.textInput);
////                                        levelThreetextInputLayout.setHint(expensesLevelThreeData.get(k).getLev3_name());
////                                        ImageView levelThreeimageView = (ImageView) levelThreeView.findViewById(R.id.exp_education_list_edt_popup);
////                                        levelThreeimageView.setOnClickListener(new View.OnClickListener() {
////                                            @Override
////                                            public void onClick(View view) {
////                                                // view.setTag();
////                                            }
////                                        });
////                                        linearLayout.addView(levelThreeView);
////
////                                        //myLevelTwolist.get(k).setExpensesLevelThreeData(myLevelThreelist);
////                                    }
////                                }
////                            }
////                        }
////                    }
////                }
////                mexpensesOverallData.setExpensesLevelOneData(myLeveloneData);
////            }
////        }
////       // addEssentialExpensesData();
////    }
//
//
//
////    public void addEssentialExpensesData(){
////        for (int m = 0; m < mexpensesOverallData.getExpensesLevelOneData().size(); m++) {
////            // //Log.e("mygetLevelOneData", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getLev1_name());
////            //Log.e("mygetLevelOneData", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getType());
////            //Log.e("mygetLevelgetLev1_name", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getLev1_name());
////            if (mexpensesOverallData.getExpensesLevelOneData().get(m).getType().equalsIgnoreCase("Essential")) {
////                String levelOneName = mexpensesOverallData.getExpensesLevelOneData().get(m).getLev1_name();
////                ExpensesLevelOneData expensesLevelOneData = new ExpensesLevelOneData();
////                expensesLevelOneData = mexpensesOverallData.getExpensesLevelOneData().get(m);
////                mEssentialListDataGroup.add(expensesLevelOneData);
////                if (mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData() != null) {
////                    if (!mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().isEmpty()) {
////                        ArrayList<ExpensesLevelTwoData> expensesLevelTwoData = new ArrayList<ExpensesLevelTwoData>();
////                        for (int l = 0; l < mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().size(); l++) {
////                            // //Log.e("mygetLevelTwoData", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getLev2_name());
////                            String levelTwoName = mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getLev2_name();
////                            expensesLevelTwoData.add(mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l));
////                            mEssentialListDataChild.put(levelOneName, expensesLevelTwoData);
////                            if (mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getExpensesLevelThreeData() != null) {
////                                if (!mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getExpensesLevelThreeData().isEmpty()) {
////                                    for (int n = 0; n < mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getExpensesLevelThreeData().size(); n++) {
////                                        //  //Log.e("mygetLevelThreeData", "" + mexpensesOverallData.getExpensesLevelOneData().get(m).getExpensesLevelTwoData().get(l).getExpensesLevelThreeData().get(n).getLev3_name());
////                                    }
////                                }
////                            }
////                        }
////                    } else {
////                        ArrayList<ExpensesLevelTwoData> expensesLevelTwoData = new ArrayList<ExpensesLevelTwoData>();
////                        mEssentialListDataChild.put(levelOneName, expensesLevelTwoData);
////                    }
////                } else {
////                    ArrayList<ExpensesLevelTwoData> expensesLevelTwoData = new ArrayList<ExpensesLevelTwoData>();
////                    mEssentialListDataChild.put(levelOneName, expensesLevelTwoData);
////                }
////            }
////            navigationAdapter = new ExpListViewAdapterWithCheckbox(mContext, mEssentialListDataGroup, mEssentialListDataChild, "Essential");
////            navigationView.setAdapter(navigationAdapter);
////        }
////    }
//
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mContext=context;
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
//    public void onPause() {
//        super.onPause();
//        //  AddExpensesService();
//    }
//
//    public void callGetExpensesService(){
//        WebServiceCalls webServiceObj;
//        getExpensesDetailsModel = null;
//        user_expense = null;
//        UtileKit.showSpinnerDialog(mContext, false);
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<GetExpensesDetailsModel> call = webServiceObj.callGetExpensesDetailsService(UtileKit.getPersistedPurplePathPref("user_id"));
//        call.enqueue(new Callback<GetExpensesDetailsModel>() {
//            @Override
//            public void onResponse(Call<GetExpensesDetailsModel> call, Response<GetExpensesDetailsModel> response) {
//                getExpensesDetailsModel = response.body();
//
////                if(!getExpensesDetailsModel.getData().getUser_expense().isEmpty()) {
////                    exp_id = getExpensesDetailsModel.getData().getUser_expense().get(0).getId();
////                    user_expense = getExpensesDetailsModel.getData().getUser_expense().get(0);
////                    level_one_ids = getExpensesDetailsModel.getData().getUser_expense().get(0).getLevel_1_ids();
////                    level_two_ids  = getExpensesDetailsModel.getData().getUser_expense().get(0).getLevel_2_ids();
////                    level_three_ids = getExpensesDetailsModel.getData().getUser_expense().get(0).getLevel_3_ids();
////                    getAsExpensesArrayList(user_expense);
////                }
//                if(getExpensesDetailsModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//
//
//                if(getExpensesDetailsModel.getData().getUser_expense()!=null) {
//                    if (!getExpensesDetailsModel.getData().getUser_expense().isEmpty()) {
//                        mainView.setVisibility(View.VISIBLE);
//                        cartoonView.setVisibility(View.GONE);
//                        fab.setVisibility(View.VISIBLE);
//                        exp_id = getExpensesDetailsModel.getData().getUser_expense().get(0).getId();
//                        user_expense = getExpensesDetailsModel.getData().getUser_expense().get(0);
//                        level_one_ids = getExpensesDetailsModel.getData().getUser_expense().get(0).getLevel_1_ids();
//                        level_two_ids = getExpensesDetailsModel.getData().getUser_expense().get(0).getLevel_2_ids();
//                        level_three_ids = getExpensesDetailsModel.getData().getUser_expense().get(0).getLevel_3_ids();
//                        getAsExpensesArrayList(user_expense);
//                    }
//                }
//                }
//                else
//                {
//                    fab.setVisibility(View.GONE);
//                    mainView.setVisibility(View.GONE);
//                    cartoonView.setVisibility(View.VISIBLE);
//                }
//                UtileKit.dismisssSpinnerDialog();
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
//
//
//    private void getAsExpensesArrayList(User_expense user_expense) {
//        try {
//            for (int a = 0; a < user_expense.getExpense_cat_lev1().size(); a++) {
//                if (user_expense.getExpense_cat_lev1().get(a).getType().equalsIgnoreCase("Essential")) {
//                    expensesLevelOneData.add(user_expense.getExpense_cat_lev1().get(a));
//                }
//            }
//
//            for (int b = 0; b < user_expense.getExpense_cat_lev2().size(); b++) {
//                expensesLevelTwoData.add(user_expense.getExpense_cat_lev2().get(b));
//
//            }
//
//            for (int c = 0; c < user_expense.getExpense_cat_lev3().size(); c++) {
//                expensesLevelThreeData.add(user_expense.getExpense_cat_lev3().get(c));
//
//            }
//            mHashEditTextListView = new HashMap<>();
//            if (expensesLevelOneData != null) {
//
//                for (int i = 0; i < expensesLevelOneData.size(); i++) {
//                    ArrayList<View> mHashEditTextView = new ArrayList<>();
//                    if (i > 0) {
//                        exp_value_position = exp_value_position + 1;
//                    }
//                    String level1Id = expensesLevelOneData.get(i).getId();
//                    String exp_life_value = expensesLevelOneData.get(i).getInfo_value();
//                    View levelOneview = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
////                ArrayList<String> lifeExpactancyView=new ArrayList();
//
//                    TextInputLayout textInputLayout = (TextInputLayout) levelOneview.findViewById(R.id.textInput);
//                    textInputLayout.setHint(expensesLevelOneData.get(i).getLev1_name());
//                    final CurrencyEditText eduvalue = (CurrencyEditText) levelOneview.findViewById(R.id.currencyEditTxt);
//                    eduvalue.setOnTextChangeEditTextListerner(this);
//                    eduvalue.setText(expensesLevelOneData.get(i).getValue());
//                    eduvalue.setTag(expensesLevelOneData.get(i).getTb_field_name());
//                    lifeExpactancyList.add(exp_value_position, exp_life_value);
//                    mHashEditTextView.add(eduvalue);
//
//                    /*ImageView essential_calculaterImgView=(ImageView)levelOneview.findViewById(R.id.calculaterImgView);
//                    essential_calculaterImgView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent calculatorIntent = new Intent(getActivity(), CalculatorAct.class);
//                            calculatorIntent.putExtra(CalculatorAct.TITLE_ACTIVITY, TITLE);
//                            calculatorIntent.putExtra(CalculatorAct.PARENT_ACTIVITY, PARENT_CLASS_SOURCE);
//                            calculatorIntent.putExtra(CalculatorAct.VALUE, eduvalue.getText().toString());
//                            startActivityForResult(calculatorIntent, CalculatorAct.REQUEST_RESULT_SUCCESSFUL);
//                        }
//                    });
//*/
//
//                    ImageView leveloneimageView = (ImageView) levelOneview.findViewById(R.id.lifeExptImgPopView);
//                    leveloneimageView.setTag(exp_value_position);
//                    leveloneimageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            int exp_value = Integer.valueOf(view.getTag().toString());
//                            showPopupDialog(view, lifeExpactancyList.get(exp_value));
//                            // leveloneimageView
//                        }
//                    });
//                    mLevelOneListView.add(levelOneview);
////                lifeExpactancyView.add(exp_life_value);
//                    linearLayout.addView(levelOneview);
//                    ArrayList<ExpensesLevelTwoData> myLevelTwolist = new ArrayList<ExpensesLevelTwoData>();
//                    for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                        String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                        String level3Id = null;
//                        if (level1Id.equalsIgnoreCase(level2Id)) {
//                            exp_value_position = exp_value_position + 1;
//                            String exp_cat2_life_value = expensesLevelTwoData.get(j).getInfo_value();
//                            level3Id = expensesLevelTwoData.get(j).getId();
//                            View levelTwoView = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
//                            TextInputLayout levelTwotextInputLayout = (TextInputLayout) levelTwoView.findViewById(R.id.textInput);
//                            levelTwotextInputLayout.setHint(expensesLevelTwoData.get(j).getLev2_name());
//                            CurrencyEditText levelTwoEdt = (CurrencyEditText) levelTwoView.findViewById(R.id.currencyEditTxt);
//
//                            levelTwoEdt.setOnTextChangeEditTextListerner(this);
//                            levelTwoEdt.setText(expensesLevelTwoData.get(j).getValue());
//                            levelTwoEdt.setTag(expensesLevelTwoData.get(j).getTb_field_name());
//
////                        lifeExpactancyView.add(exp_cat2_life_value);
//                            ImageView levelTwoimageView = (ImageView) levelTwoView.findViewById(R.id.lifeExptImgPopView);
//                            levelTwoimageView.setTag(exp_value_position);
//                            levelTwoimageView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    // view.setTag();
//                                    int exp_value = Integer.valueOf(view.getTag().toString());
//                                    showPopupDialog(view, lifeExpactancyList.get(exp_value));
//                                }
//                            });
//                            mLevelTwoListView.add(levelTwoView);
//                            mHashEditTextView.add(levelTwoEdt);
//                            lifeExpactancyList.add(exp_value_position, exp_cat2_life_value);
////
////                        if(expensesLevelThreeData !=null) {
////                            for (int k = 0; k < expensesLevelThreeData.size(); k++) {
////                                String mylevel3Id = expensesLevelThreeData.get(k).getLev2_id();
////
////                                if(level3Id != null) {
////                                    if (level3Id.equalsIgnoreCase(mylevel3Id)) {
////
////                                        View levelThreeView = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
////                                        TextInputLayout levelThreetextInputLayout = (TextInputLayout) levelThreeView.findViewById(R.id.textInput);
////                                        levelThreetextInputLayout.setHint(expensesLevelThreeData.get(k).getLev3_name());
////                                        CurrencyEditText levelthreeEdt = (CurrencyEditText) levelThreeView.findViewById(R.id.currencyEditTxt);
////                                        levelthreeEdt.setOnTextChangeEditTextListerner(this);
////                                        exp_value_position = exp_value_position+1;
////                                        levelthreeEdt.setText(expensesLevelThreeData.get(k).getValue());
////                                        levelthreeEdt.setTag(expensesLevelThreeData.get(j).getTb_field_name());
////
////                                        lifeExpactancyList.add(exp_value_position, expensesLevelThreeData.get(k).getInfo_value());
////                                        ImageView levelThreeimageView = (ImageView) levelThreeView.findViewById(R.id.lifeExptImgPopView);
////                                        levelThreeimageView.setTag(exp_value_position);
//////                                        lifeExpactancyView.add(expensesLevelThreeData.get(k).getInfo_value());
////                                        levelThreeimageView.setOnClickListener(new View.OnClickListener() {
////                                            @Override
////                                            public void onClick(View view) {
////                                                int exp_value = Integer.valueOf(view.getTag().toString());
////                                                showPopupDialog(view, lifeExpactancyList.get(exp_value));
////
////                                            }
////                                        });
////                                        linearLayout.addView(levelThreeView);
////                                        mHashEditTextView.add(levelthreeEdt);
////                                        //myLevelTwolist.get(k).setExpensesLevelThreeData(myLevelThreelist);
////                                    }
////                                }
////                            }
////                        }
//                        linearLayout.addView(levelTwoView);
//
//                        }
//
//
////                    mHashLifeExpView.put(i,lifeExpactancyView);
//
//                    }
//                    mHashEditTextListView.put("" + i, mHashEditTextView);
//                }
//                // mexpensesOverallData.setExpensesLevelOneData(myLeveloneData);
//            }
//
//            // addEssentialExpensesData();
//            onChangeTextEditText("");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.relative_left_arrow:
//            {
//                mCallBackListener.onActivityBackPressed();
//                getInputValues();
//            }
//            break;
//            case R.id.relative_center_home:
//            {
//                getInputValues();
//                Intent i = new Intent(getActivity(), HomePageActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//
////                getActivity().finish();
////                Intent i = new Intent(activity, HomePageActivity.class);
////                startActivity(i);
//            }
//            break;
//            case R.id.relative_right_arrow:
//            {
//                getInputValues();
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
//    public void onValueSet(ExpectedIncrementDialogFragment dialog, String age, String percentage, int id) throws JSONException {
//
//        String contribution_info_value = null;
//        if (UtileKit.validateObjectValues(age) && UtileKit.validateObjectValues(percentage)) {
//            contribution_info_value = age.concat(",").concat(percentage);
//        } else if (UtileKit.validateObjectValues(percentage)) {
//            contribution_info_value = percentage;
//        } else if (UtileKit.validateObjectValues(age)) {
//            contribution_info_value = age;
//        }
//        lifeExpactancyList.set(id, contribution_info_value);
//    }
//
//    @Override
//    public void onChangeTextEditText(String s, TextView currencySpanTxtView) {
//
//    }
//
//    @Override
//    public void onChangeTextEditText(String s) {
//        int overallTotalValue=0;
//        if (mLevelOneListView != null) {
//            if (!mLevelOneListView.isEmpty()) {
//                for (int i = 0; i < mLevelOneListView.size(); i++) {
//                    CurrencyEditText levelOneEdt = (CurrencyEditText) mLevelOneListView.get(i).findViewById(R.id.currencyEditTxt);
//                    String value = UtileKit.getStringwithoutCurreny(levelOneEdt);
//                    if(value.length()>0) {
//                        int valueInInt = Integer.parseInt(value);
//                        overallTotalValue = overallTotalValue + valueInInt;
//                        //Log.e("Name",""+levelOneEdt.getTag());
//                    }
//                }
//                    if (mLevelTwoListView != null) {
//                        if (!mLevelTwoListView.isEmpty()) {
//                            for (int j = 0; j < mLevelTwoListView.size(); j++) {
//                                CurrencyEditText levelTwoEdt = (CurrencyEditText) mLevelTwoListView.get(j).findViewById(R.id.currencyEditTxt);
//                                String valuetwo = UtileKit.getStringwithoutCurreny(levelTwoEdt);
//                                if(valuetwo.length()>0) {
//                                    int valueInInttwo = Integer.parseInt(valuetwo);
//                                    overallTotalValue = overallTotalValue + valueInInttwo;
//                                    //Log.e("Total",""+overallTotalValue);
////                                    //Log.e("Name",""+levelOneEdt.getTag());
//                                }
//                            }
//                        }
//                    }
//
//            }
//        }
//        // overallTotalValue = Integer.parseInt(s)+overallTotalValue;
//        String total = String.valueOf(overallTotalValue);
//        essentialTotal.setText(total);
//    }
//
//    public static void getInputValues() {
//
//        Log.i(TAG,"getInputValues");
//        JSONArray discretionaryJsonArray = ExpensesReDesignDetailsDiscretionaryFragment.getDiscretionaryInputValues();
//        jsonArray = new JSONArray();
//        Log.i(TAG,"getInputValues discretionaryJsonArray" +discretionaryJsonArray.length());
//        if (UtileKit.validateObjectValues(discretionaryJsonArray) && discretionaryJsonArray.length() > 0) {
//            if (discretionaryJsonArray.length() > 0) {
//                for (int i = 0; i < discretionaryJsonArray.length(); i++) {
//                    try {
//                        jsonArray.put(discretionaryJsonArray.get(i));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                moverallExpenses = ExpensesReDesignDetailsDiscretionaryFragment.moverallExpenses;
//            }
//        }
//        if (mHashEditTextListView != null) {
//            Log.i(TAG,"getInputValues mHashEditTextListView" );
//            get_value_position=0;
//            if (!mHashEditTextListView.isEmpty())
//                for (int i = 0; i < mHashEditTextListView.size(); i++) {
////                    CurrencyEditText levelOneEdt = (CurrencyEditText) mLevelOneListView.get(i).findViewById(R.id.currencyEditTxt);
////                    String value = UtileKit.getStringwithoutCurreny(levelOneEdt);
////                    if(value.length()>0) {
////                        int valueInInt = Integer.parseInt(value);
////                        if(i>0) {
////                            get_value_position = get_value_position + 1;
////                        }
////                        moverallExpenses = moverallExpenses + valueInInt;
////                    }
////                    try {
////                        addToJsonObject(expensesLevelOneData.get(i).getTb_field_name(), value);
////                        addToJsonObject(expensesLevelOneData.get(i).getTb_field_name().concat("_info"), lifeExpactancyList.get(get_value_position));
////
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
//
//                    if (mHashEditTextListView.get(""+i)!= null) {
//                        if (!mHashEditTextListView.get(""+i).isEmpty()) {
//                            for (int j = 0; j < mHashEditTextListView.get(""+i).size(); j++) {
//                                CurrencyEditText levelTwoEdt = (CurrencyEditText) mHashEditTextListView.get(""+i).get(j).findViewById(R.id.currencyEditTxt);
//                                String valuetwo = UtileKit.getStringwithoutCurreny(levelTwoEdt);
//                                if(valuetwo.length()>0) {
//                                    int valueInInttwo = Integer.parseInt(valuetwo);
//                                    moverallExpenses = moverallExpenses + valueInInttwo;
//                                }
//
//                                try {
//                                    addToJsonObject(levelTwoEdt.getTag().toString(), valuetwo);
//                                    addToJsonObject(levelTwoEdt.getTag().toString().concat("_info"), lifeExpactancyList.get(get_value_position));
//                                    get_value_position = get_value_position + 1;
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                catch (Exception e)
//                                {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                        Log.i(TAG,"getInputValues mHashEditTextListView ends" );
//                    }
//                }
//            Log.i(TAG,"getInputValues mHashEditTextListView Before" );
//            /**
//             * remove
//             */
////           try {
////               addToJsonObject("esst_total", "23232");
////               addToJsonObject("esst_total_info", "23,32");
////               addToJsonObject("discre_total", "1000");
////               addToJsonObject("commit_total", "2000");
////           }catch (Exception e)
////           {
////               e.printStackTrace();
////           }
//
//            callGetExpensesUpdateModelService();
//        }
//    }
//
//
//
//    private static  void addToJsonObject(String field, String value) throws JSONException{
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("field", field);
//        jsonObject.put("value", value);
//        jsonArray.put(jsonObject);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.menu_empty_items, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.ic_clear_btn:
//                if (menuItem.getItemId() == R.id.ic_clear_btn)
//                    essentialTotal.setText("");
//                if (mLevelOneListView != null) {
//                    if (!mLevelOneListView.isEmpty()) {
//                        for (int i = 0; i < mLevelOneListView.size(); i++) {
//                            CurrencyEditText levelOneEdt = (CurrencyEditText) mLevelOneListView.get(i).findViewById(R.id.currencyEditTxt);
//                            levelOneEdt.setText("");
//                            if (mLevelTwoListView != null) {
//                                if (!mLevelTwoListView.isEmpty()) {
//                                    for (int j = 0; j < mLevelTwoListView.size(); j++) {
//                                        CurrencyEditText levelTwoEdt = (CurrencyEditText) mLevelTwoListView.get(j).findViewById(R.id.currencyEditTxt);
//                                        levelTwoEdt.setText("");
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                return true;
//            case R.id.ic_done_btn:
//                getInputValues();
//                if (menuItem.getItemId() == R.id.ic_done_btn)
//                    fragmentTransaction = fragmentManager.beginTransaction();
//                // ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
//                //ExpensesReDesignDetailsFragment fragment = new ExpensesReDesignDetailsFragment();
//                AssetsDetailsFragment fragment = new AssetsDetailsFragment();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();
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
//
//    public static  void callGetExpensesUpdateModelService(){
//        WebServiceCalls webServiceObj;
//        Log.i("DumAss",""+jsonArray.toString());
//        JSONObject jsonEssentialObject = new JSONObject();
//        try {
//            jsonEssentialObject.put("exp_det", jsonArray);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String   str = jsonEssentialObject.toString();
//        String overallvalue = String.valueOf(moverallExpenses);
//        Log.i("strjsonstring",""+str);
//        Log.i("level_one_ids",""+level_one_ids);
//        Log.i("level_one_ids",""+level_two_ids);
//        Log.i("level_one_ids",""+level_three_ids);
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<ExpensesUpdateModel> call = webServiceObj.callGetExpensesUpdateModelService(
//                UtileKit.getPersistedPurplePathPref("user_id"),level_zero, level_one_ids, level_two_ids, level_three_ids, str, overallvalue,"Y","0");
////                UtileKit.getPersistedPurplePathPref("user_id"), level_one_ids, level_two_ids, level_three_ids,exp_id, str, overallvalue);
//        call.enqueue(new Callback<ExpensesUpdateModel>() {
//            @Override
//            public void onResponse(Call<ExpensesUpdateModel> call, Response<ExpensesUpdateModel> response) {
//
//            }
//            @Override
//            public void onFailure(Call<ExpensesUpdateModel> call, Throwable t) {
//
//                UtileKit.dismisssSpinnerDialog();
//                UtileKit.alertRetrofitExceptionDialog( mContext, t);
//            }
//        });
//        UtileKit.dismisssSpinnerDialog();
//    }
//
//
//    private void showPopupDialog(View view, String s) {
//        android.app.FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
//        DialogFragment newFragment = ExpectedIncrementDialogFragment.newInstance(this,view,s);
//        newFragment.show(fm, "dialog");
//    }
//
//}
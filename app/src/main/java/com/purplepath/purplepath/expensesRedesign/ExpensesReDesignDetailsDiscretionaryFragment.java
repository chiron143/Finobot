//package com.purplepath.purplepath.expensesRedesign;
//
//import android.app.DialogFragment;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.textfield.TextInputLayout;
//import androidx.fragment.app.FragmentActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
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
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.assets.AssetsDetailsFragment;
//import com.purplepath.purplepath.customview.CurrencyDefaultEdt;
//import com.purplepath.purplepath.customview.CurrencyEditText;
//import com.purplepath.purplepath.dia//Log.expectedIncrementDialogFragment;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesDetailModel;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev1;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev2;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev3;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.GetExpensesDetailsModel;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.User_expense;
//import com.purplepath.purplepath.expensesRedesign.expensesselection.ExpensesSelectionDetailsFragment;
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
//import static com.purplepath.purplepath.expensesRedesign.ExpensesReDesignDetailsEssentialFragment.getInputValues;
//
///**
// * Created by Bert on 27-Jun-16.
// */
//public class ExpensesReDesignDetailsDiscretionaryFragment extends BaseFragment implements View.OnClickListener, ExpectedIncrementDialogFragment.OnExpectedValueListener, CurrencyEditText.OnTextChangeEditText {
//
//    public static ArrayList<View> mLevelTwoListView;
//    public static int moverallExpenses;
//    private static ArrayList<Expense_cat_lev1> expensesLevelOneData;
//    private static ArrayList<Expense_cat_lev2> expensesLevelTwoData;
//    private static ArrayList<Expense_cat_lev3> expensesLevelThreeData;
//    private static ArrayList<View> mLevelOneListView;
//    private static JSONArray jsonArray;
//    private static ArrayList<String> lifeExpactancyList;
//    private static HashMap<String, ArrayList<View>> mHashEditTxtDesView = new HashMap<>();
//    private static int get_value_position;
//    OnSelectedDoneClickListener onSelectedDoneClickListener;
//    FragmentTransaction fragmentTransaction;
//    FragmentManager fragmentManager;
//    int exp_value_position;
//    private Context mContext;
//    private ExpensesDetailModel mExpensesDetailModel;
//    private GetExpensesDetailsModel getExpensesDetailsModel;
//    private OnActivityBackPressedListener mCallBackListener;
//    private LinearLayout linearLayout;
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//    private User_expense user_expense;
//    private CurrencyDefaultEdt discretionaryTotal;
//    private View cartoonView;
//    private ScrollView mainView;
//    private FloatingActionButton fab;
//    public ExpensesReDesignDetailsDiscretionaryFragment(OnSelectedDoneClickListener onSelectedDoneClickListene) {
//        onSelectedDoneClickListener = onSelectedDoneClickListene;
//    }
//
//    public static JSONArray getDiscretionaryInputValues() {
//        jsonArray = new JSONArray();
//        if (mLevelOneListView != null) {
//            get_value_position = 0;
//            if (!mHashEditTxtDesView.isEmpty()) {
//                for (int i = 0; i < mHashEditTxtDesView.size(); i++) {
////                    CurrencyEditText levelOneEdt = (CurrencyEditText) mLevelOneListView.get(i).findViewById(R.id.currencyEditTxt);
////                    String value = UtileKit.getStringwithoutCurreny(levelOneEdt);
////
////                    if(value.length()>0) {
////                        int valueInInt = Integer.parseInt(value);
////                        moverallExpenses = moverallExpenses + valueInInt;
////                    }
////                    try {
////                        addToJsonObject(expensesLevelOneData.get(i).getTb_field_name(), value);
////                        addToJsonObject(expensesLevelOneData.get(i).getTb_field_name().concat("_info"), lifeExpactancyList.get(get_value_position));
////
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                    get_value_position = get_value_position + 1;
//                    if (mHashEditTxtDesView.get("" + i) != null) {
//                        if (!mHashEditTxtDesView.get("" + i).isEmpty()) {
//                            for (int j = 0; j < mHashEditTxtDesView.get("" + i).size(); j++) {
//                                CurrencyEditText levelTwoEdt = (CurrencyEditText) mHashEditTxtDesView.get("" + i).get(j).findViewById(R.id.currencyEditTxt);
//                                String valuetwo = UtileKit.getStringwithoutCurreny(levelTwoEdt);
//                                if (valuetwo.length() > 0) {
//                                    int valueInInttwo = Integer.parseInt(valuetwo);
//                                    moverallExpenses = moverallExpenses + valueInInttwo;
//                                }
//
//                                try {
//                                    addToJsonObject(mHashEditTxtDesView.get("" + i).get(j).getTag().toString(), valuetwo);
//                                    addToJsonObject(mHashEditTxtDesView.get("" + i).get(j).getTag().toString().concat("_info"), lifeExpactancyList.get(get_value_position));
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                catch (Exception e)
//                                {
//                                    e.printStackTrace();
//                                }
//                                get_value_position = get_value_position + 1;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return jsonArray;
//    }
//
//    private static void addToJsonObject(String field, String value) throws JSONException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("field", field);
//        jsonObject.put("value", value);
//        jsonArray.put(jsonObject);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View expensesdetailView = inflater.inflate(R.layout.fragment_expenses_redesign_discretionary, container, false);
//        cartoonView= expensesdetailView.findViewById(R.id.cartoonView);
//        mainView=(ScrollView)expensesdetailView.findViewById(R.id.scrollViewId);
//
//        callGetExpensesService();
//        changeAddView(expensesdetailView);
//
//        fragmentManager = getActivity().getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//
//        expensesLevelOneData = new ArrayList<Expense_cat_lev1>();
//        expensesLevelTwoData = new ArrayList<Expense_cat_lev2>();
//        expensesLevelThreeData = new ArrayList<Expense_cat_lev3>();
//        lifeExpactancyList = new ArrayList<>();
//        exp_value_position = 0;
//        get_value_position = 0;
//        mleftRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_left_arrow);
//        mcenterRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_center_home);
//        mRightRelativeLayout = (RelativeLayout) expensesdetailView.findViewById(R.id.relative_right_arrow);
//        discretionaryTotal = (CurrencyDefaultEdt)expensesdetailView.findViewById(R.id.currencyEditTxtTotal);
//
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//        mRightRelativeLayout.setOnClickListener(this);
//
//        mLevelOneListView = new ArrayList<View>();
//        mLevelTwoListView = new ArrayList<View>();
//
//        fab = (FloatingActionButton) expensesdetailView.findViewById(R.id.redesign_discretionary_fab);
//        linearLayout = (LinearLayout) expensesdetailView.findViewById(R.id.epenses_row_layout);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                onclickFragment();
//            }
//        });
//        return expensesdetailView;
//    }
//    private void changeAddView(View view){
//        TextView textView = (TextView) view.findViewById(R.id.goal_headear_bg_TxtView);
//        textView.setText("Please add Expenses by clicking the + button");
//        ImageView	imageView = (ImageView) view.findViewById(R.id.goal_click_image);
//        imageView.setBackgroundResource(R.drawable.ic_add_icon_big);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                onclickFragment();
//
//            }
//        });
//
//
//    }
//
//    private void onclickFragment() {
//        getInputValues();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        getActivity().getSupportFragmentManager().popBackStack();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        ExpensesSelectionDetailsFragment fragment =  ExpensesSelectionDetailsFragment.newInstance(1);
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
//    }
//
//    public void callGetExpensesService() {
//        WebServiceCalls webServiceObj;
//        getExpensesDetailsModel = null;
//        user_expense = new User_expense();
//        UtileKit.showSpinnerDialog(mContext, false);
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<GetExpensesDetailsModel> call = webServiceObj.callGetExpensesDetailsService(UtileKit.getPersistedPurplePathPref("user_id"));
//        call.enqueue(new Callback<GetExpensesDetailsModel>() {
//            @Override
//            public void onResponse(Call<GetExpensesDetailsModel> call, Response<GetExpensesDetailsModel> response) {
//                getExpensesDetailsModel = response.body();
//                if(getExpensesDetailsModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//                    mainView.setVisibility(View.VISIBLE);
//                    cartoonView.setVisibility(View.GONE);
//                    fab.setVisibility(View.VISIBLE);
//                    if (getExpensesDetailsModel.getData().getUser_expense() != null) {
//                        if (!getExpensesDetailsModel.getData().getUser_expense().isEmpty()) {
//                            user_expense = getExpensesDetailsModel.getData().getUser_expense().get(0);
//                            getAsExpensesArrayList(user_expense);
//                        }
//                    }
//                }
//                else {
//                    mainView.setVisibility(View.GONE);
//                    cartoonView.setVisibility(View.VISIBLE);
//                    fab.setVisibility(View.GONE);
//                }
//                UtileKit.dismisssSpinnerDialog();
//            }
//
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
//    private void getAsExpensesArrayList(User_expense user_expense) {
//        for (int a = 0; a < user_expense.getExpense_cat_lev1().size(); a++) {
//            if (user_expense.getExpense_cat_lev1().get(a).getType().equalsIgnoreCase("Discretionary")) {
//                expensesLevelOneData.add(user_expense.getExpense_cat_lev1().get(a));
//            }
//        }
//
//        for (int b = 0; b < user_expense.getExpense_cat_lev2().size(); b++) {
//            expensesLevelTwoData.add(user_expense.getExpense_cat_lev2().get(b));
//
//        }
//
//        for (int c = 0; c < user_expense.getExpense_cat_lev3().size(); c++) {
//            expensesLevelThreeData.add(user_expense.getExpense_cat_lev3().get(c));
//        }
//        mHashEditTxtDesView = new HashMap<>();
//        if (expensesLevelOneData != null) {
//            for (int i = 0; i < expensesLevelOneData.size(); i++) {
//                if (i > 0) {
//                    exp_value_position = exp_value_position + 1;
//                }
//                String level1Id = expensesLevelOneData.get(i).getId();
//                String exp_life_value = expensesLevelOneData.get(i).getInfo_value();
//                View levelOneview = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
//                ArrayList<View> mHashEditTextView = new ArrayList<>();
//                TextInputLayout textInputLayout = (TextInputLayout) levelOneview.findViewById(R.id.textInput);
//                textInputLayout.setHint(expensesLevelOneData.get(i).getLev1_name());
//                CurrencyEditText eduvalue = (CurrencyEditText) levelOneview.findViewById(R.id.currencyEditTxt);
//                mHashEditTextView.add(eduvalue);
//                eduvalue.setOnTextChangeEditTextListerner(this);
//                eduvalue.setText(expensesLevelOneData.get(i).getValue());
//                eduvalue.setTag(expensesLevelOneData.get(i).getTb_field_name());
//
//                ImageView leveloneimageView = (ImageView) levelOneview.findViewById(R.id.lifeExptImgPopView);
//                leveloneimageView.setTag(exp_value_position);
//                leveloneimageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int exp_value = Integer.valueOf(view.getTag().toString());
//                        showPopupDialog(view, lifeExpactancyList.get(exp_value));
//
//                        // view.setTag();
//                    }
//                });
//                lifeExpactancyList.add(exp_value_position, exp_life_value);
//                mLevelOneListView.add(levelOneview);
//                linearLayout.addView(levelOneview);
////                ArrayList<ExpensesLevelTwoData> myLevelTwolist = new ArrayList<ExpensesLevelTwoData>();
//                for (int j = 0; j < expensesLevelTwoData.size(); j++) {
//                    String level2Id = expensesLevelTwoData.get(j).getLev1_id();
//                    String level3Id = null;
//                    if (level1Id.equalsIgnoreCase(level2Id)) {
//                        level3Id = expensesLevelTwoData.get(j).getId();
//                        exp_value_position = exp_value_position + 1;
//                        String exp_cat2_life_value = expensesLevelTwoData.get(j).getInfo_value();
//                        View levelTwoView = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
//                        TextInputLayout levelTwotextInputLayout = (TextInputLayout) levelTwoView.findViewById(R.id.textInput);
//                        levelTwotextInputLayout.setHint(expensesLevelTwoData.get(j).getLev2_name());
//                        CurrencyEditText levelTwoEdt = (CurrencyEditText) levelTwoView.findViewById(R.id.currencyEditTxt);
//                        mHashEditTextView.add(levelTwoEdt);
//                        levelTwoEdt.setOnTextChangeEditTextListerner(this);
//                        levelTwoEdt.setText(expensesLevelTwoData.get(j).getValue());
//                        levelTwoEdt.setTag(expensesLevelTwoData.get(j).getTb_field_name());
//                        ImageView levelTwoimageView = (ImageView) levelTwoView.findViewById(R.id.lifeExptImgPopView);
//                        levelTwoimageView.setTag(exp_value_position);
//                        levelTwoimageView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                // view.setTag();
//                                int exp_value = Integer.valueOf(view.getTag().toString());
//                                showPopupDialog(view, lifeExpactancyList.get(exp_value));
//
//                            }
//                        });
//                        mLevelTwoListView.add(levelTwoView);
//                        lifeExpactancyList.add(exp_value_position, exp_cat2_life_value);
//                        linearLayout.addView(levelTwoView);
//                    }
////                    ArrayList<ExpensesLevelThreeData> myLevelThreelist = new ArrayList<ExpensesLevelThreeData>();
////                    if (expensesLevelThreeData != null) {
////                        for (int k = 0; k < expensesLevelThreeData.size(); k++) {
////                            String mylevel3Id = expensesLevelThreeData.get(k).getLev2_id();
////
////                            if (level3Id != null) {
////                                if (level3Id.equalsIgnoreCase(mylevel3Id)) {
////
////                                    View levelThreeView = LayoutInflater.from(mContext).inflate(R.layout.add_income_header_view, null);
////                                    TextInputLayout levelThreetextInputLayout = (TextInputLayout) levelThreeView.findViewById(R.id.textInput);
////                                    levelThreetextInputLayout.setHint(expensesLevelThreeData.get(k).getLev3_name());
////                                    CurrencyEditText levelthreeEdt = (CurrencyEditText) levelThreeView.findViewById(R.id.currencyEditTxt);
////                                    levelthreeEdt.setOnTextChangeEditTextListerner(this);
////                                    exp_value_position = exp_value_position + 1;
////                                    levelthreeEdt.setText(expensesLevelThreeData.get(k).getValue());
////                                    levelthreeEdt.setTag(expensesLevelThreeData.get(j).getTb_field_name());
////                                    ImageView levelThreeimageView = (ImageView) levelThreeView.findViewById(R.id.lifeExptImgPopView);
////                                    levelThreeimageView.setTag(exp_value_position);
//////                                        lifeExpactancyView.add(expensesLevelThreeData.get(k).getInfo_value());
////                                    levelThreeimageView.setOnClickListener(new View.OnClickListener() {
////                                        @Override
////                                        public void onClick(View view) {
////                                            int exp_value = Integer.valueOf(view.getTag().toString());
////                                            showPopupDialog(view, lifeExpactancyList.get(exp_value));
////
////                                        }
////                                    });
////                                    mHashEditTextView.add(levelthreeEdt);
////                                    lifeExpactancyList.add(exp_value_position, expensesLevelThreeData.get(k).getInfo_value());
////                                    linearLayout.addView(levelThreeView);
////
////                                    //myLevelTwolist.get(k).setExpensesLevelThreeData(myLevelThreelist);
////                                }
////                            }
////                        }
////                    }
//
//                }
//                mHashEditTxtDesView.put("" + i, mHashEditTextView);
//            }
//            // mexpensesOverallData.setExpensesLevelOneData(myLeveloneData);
//        }
////        discretionaryTotal.setText(user_expense.get);
//        // addEssentialExpensesData();
//        onChangeTextEditText("");
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mContext = context;
//        mContext = context;
//        try {
//            mCallBackListener = (OnActivityBackPressedListener) (context);
//        } catch (ClassCastException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.relative_left_arrow: {
//                getInputValues();
//                mCallBackListener.onActivityBackPressed();
//            }
//            break;
//            case R.id.relative_center_home: {
//                getInputValues();
//                Intent i = new Intent(getActivity(), HomePageActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
////                getActivity().finish();
////                Intent i = new Intent(activity, HomePageActivity.class);
////                startActivity(i);
//            }
//            break;
//            case R.id.relative_right_arrow: {
//                getInputValues();
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
//
//        int overallTotalValue=0;
//        if (mLevelOneListView != null) {
//            if (!mLevelOneListView.isEmpty()) {
//                for (int i = 0; i < mLevelOneListView.size(); i++) {
//                    CurrencyEditText levelOneEdt = (CurrencyEditText) mLevelOneListView.get(i).findViewById(R.id.currencyEditTxt);
//                    String value = UtileKit.getStringwithoutCurreny(levelOneEdt);
//                    if (value.length() > 0) {
//                        int valueInInt = Integer.parseInt(value);
//                        overallTotalValue = overallTotalValue + valueInInt;
//
//                    }
//                    //Log.e("Total", "" + overallTotalValue+"Name"+ levelOneEdt.getTag());
//
//                }
//                    if (mLevelTwoListView != null) {
//                        if (!mLevelTwoListView.isEmpty()) {
//                            for (int j = 0; j < mLevelTwoListView.size(); j++) {
//                                CurrencyEditText levelTwoEdt = (CurrencyEditText) mLevelTwoListView.get(j).findViewById(R.id.currencyEditTxt);
//                                String valuetwo = UtileKit.getStringwithoutCurreny(levelTwoEdt);
//
//
//                                if(valuetwo.length()>0) {
//                                    int valueInInttwo = Integer.parseInt(valuetwo);
//                                    overallTotalValue = overallTotalValue + valueInInttwo;
//                                                                }
//                                //Log.e("Total",""+overallTotalValue+"Name"+levelTwoEdt.getTag()+"valuetwo"+" "+valuetwo);
//
//                            }
//
//                        }
//                    }
//
//            }
//        }
//        // overallTotalValue = Integer.parseInt(s)+overallTotalValue;
//        String total = String.valueOf(overallTotalValue);
//        discretionaryTotal.setText(total);
//
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
//    public void onDestroyOptionsMenu() {
//        this.setMenuVisibility(false);
//        super.onDestroyOptionsMenu();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.ic_clear_btn:
//                if (menuItem.getItemId() == R.id.ic_clear_btn)
//                    if (mLevelOneListView != null) {
//                        if (!mLevelOneListView.isEmpty()) {
//                            for (int i = 0; i < mLevelOneListView.size(); i++) {
//                                CurrencyEditText levelOneEdt = (CurrencyEditText) mLevelOneListView.get(i).findViewById(R.id.currencyEditTxt);
//                                levelOneEdt.setText("");
//                                if (mLevelTwoListView != null) {
//                                    if (!mLevelTwoListView.isEmpty()) {
//                                        for (int j = 0; j < mLevelTwoListView.size(); j++) {
//                                            CurrencyEditText levelTwoEdt = (CurrencyEditText) mLevelTwoListView.get(j).findViewById(R.id.currencyEditTxt);
//                                            levelTwoEdt.setText("");
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
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
//    private void showPopupDialog(View view, String s) {
//        android.app.FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
//        DialogFragment newFragment = ExpectedIncrementDialogFragment.newInstance(this, view, s);
//        newFragment.show(fm, "dialog");
//    }
//
//}
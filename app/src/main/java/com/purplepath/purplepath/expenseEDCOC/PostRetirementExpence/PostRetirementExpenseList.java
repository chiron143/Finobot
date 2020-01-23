package com.purplepath.purplepath.expenseEDCOC.PostRetirementExpence;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.gson.Gson;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.expenseEDCOC.ExpenseTabMainFragment;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesDetailModel;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev0;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.GetExpensesDetailsModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.Notification.adapters.PromptsRecyclerViewAdapter.fragmentManager;
import static com.purplepath.purplepath.apputiles.UtileKit.EXPANCE_CATAGORY_PREF;
import static com.purplepath.purplepath.apputiles.UtileKit.SUCCESSCODE;
import static com.purplepath.purplepath.apputiles.UtileKit.getPersistedPurplePathPref;

/**
 * Created by dinesh on 19/05/17.
 */

public class PostRetirementExpenseList extends BaseFragment implements View.OnClickListener {


    @Bind(R.id.essentialCurTxtId)
    DefaultCurrencyTextView essentialCurTxt;

    @Bind(R.id.discretionaryCurTxtId)
    DefaultCurrencyTextView discretionaryCurTxt;

    @Bind(R.id.commitmentCurTxtId)
    DefaultCurrencyTextView commitmentCurTxt;

    @Bind(R.id.obligationCurTxtId)
    DefaultCurrencyTextView obligationCurTxt;

    @Bind(R.id.contributionCurTxtId)
    DefaultCurrencyTextView contributionCurTxt;

    @Bind(R.id.relative_left_arrow)
    RelativeLayout mleftRelativeLayout;

    @Bind(R.id.relative_center_home)
    RelativeLayout mcenterRelativeLayout;

    @Bind(R.id.relative_right_arrow)
    RelativeLayout mRightRelativeLayout;

    @Bind(R.id.add_floating_button_Id)
    FloatingActionButton addFloatingBtn;

    @Bind(R.id.main_layout)
    LinearLayout mainView;

    @Bind(R.id.add_cartoon_layout)
    RelativeLayout cartoonView;

    @Bind(R.id.essetLiabView)
    LinearLayout essetLiabView;

    @Bind(R.id.discreTotalLinView)
    LinearLayout discreTotalLinView;

    @Bind(R.id.committotLinView)
    LinearLayout committotLinView;

    @Bind(R.id.oblitotLinView)
    LinearLayout oblitotLinView;

    @Bind(R.id.contri_totalLinView)
    LinearLayout contri_totalLinView;

    @Bind(R.id.essential_btn)
    ImageView essentialBtn;

    @Bind(R.id.discretionary_btn)
    ImageView discretionaryBtn;

    @Bind(R.id.commitment_btn)
    ImageView commitmentBtn;

    @Bind(R.id.Obligation_btn)
    ImageView ObligationBtn;

    @Bind(R.id.contri_total_btn)
    ImageView contri_total_btn;

    @Bind(R.id.goal_headear_bg_TxtView)
    TextView postRetirementbtn;

    @Bind(R.id.goal_click_image)
    ImageView mAddImgButten;


    GetExpensesDetailsModel expensesDetailsModel;

    ArrayList<String> selLev1List = new ArrayList<>(), selLev2List = new ArrayList<>(), selLev3List = new ArrayList<>();

    private OnActivityBackPressedListener mCallBackListener;

    private Context mContext;

    private ExpensesDetailModel expenceCatagoryModel;

    public static PostRetirementExpenseList newInstance() {

        Bundle args = new Bundle();

        PostRetirementExpenseList fragment = new PostRetirementExpenseList();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (getContext());
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }

        String catgoryJsonString = getPersistedPurplePathPref(EXPANCE_CATAGORY_PREF);
        if (catgoryJsonString == null) {
            callExpensesCategoriesService();
        } else {
            try {
                Gson gson = new Gson();
                expenceCatagoryModel = gson.fromJson(catgoryJsonString, ExpensesDetailModel.class);
            } catch (Exception ex) {
                callExpensesCategoriesService();
            }
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expense_ecoc_pre_retirement, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        try {

            mleftRelativeLayout.setOnClickListener(this);

            mRightRelativeLayout.setOnClickListener(this);
            mcenterRelativeLayout.setOnClickListener(this);
            addFloatingBtn.setOnClickListener(this);

            essentialBtn.setOnClickListener(this);
            discretionaryBtn.setOnClickListener(this);
            commitmentBtn.setOnClickListener(this);
            ObligationBtn.setOnClickListener(this);
            contri_total_btn.setOnClickListener(this);
            mAddImgButten.setOnClickListener(this);
            postRetirementbtn.setText("Please add Expenses by clicking the + button");

            callGetExpensesService();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    public void callGetExpensesService() {
        WebServiceCalls webServiceObj;

        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetExpensesDetailsModel> call = webServiceObj.GetPostRetirementExpensesService(getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetExpensesDetailsModel>() {
            @Override
            public void onResponse(Call<GetExpensesDetailsModel> call, Response<GetExpensesDetailsModel> response) {
                GetExpensesDetailsModel getExpensesDetailsModel = response.body();


                if (getExpensesDetailsModel.getStatus_code().equalsIgnoreCase(SUCCESSCODE)) {


                    if (getExpensesDetailsModel.getData().getUser_expense() != null) {
                        if (!getExpensesDetailsModel.getData().getUser_expense().isEmpty()) {
                            expensesDetailsModel = getExpensesDetailsModel;
                            for (Expense_cat_lev0 obj : expensesDetailsModel.getData().getUser_expense().get(0).getExpense_cat_lev0()) {
                                setExpenseSetValue(obj.getId(), obj.getValue());
                            }
                            mainView.setVisibility(View.VISIBLE);
                            cartoonView.setVisibility(View.GONE);


                        }
                    }
                } else {

                    mainView.setVisibility(View.GONE);
                    cartoonView.setVisibility(View.VISIBLE);
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

    private void setExpenseSetValue(String id, String value) {
        switch (id) {

            case "1":
                essentialCurTxt.setText("" + value);
                essetLiabView.setVisibility(View.VISIBLE);
                break;
            case "2":
                discretionaryCurTxt.setText("" + value);
                discreTotalLinView.setVisibility(View.VISIBLE);
                break;
            case "3":
                commitmentCurTxt.setText("" + value);
                committotLinView.setVisibility(View.VISIBLE);
                break;
            case "4":
                obligationCurTxt.setText("" + value);
                oblitotLinView.setVisibility(View.VISIBLE);
                break;
            case "5":
                commitmentCurTxt.setText("" + value);
                contri_totalLinView.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void callExpensesCategoriesService() {
        //UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<ExpensesDetailModel> call = webServiceObj.callExpensesCategoriesService();
        call.enqueue(new Callback<ExpensesDetailModel>() {
            @Override
            public void onResponse(Call<ExpensesDetailModel> call, Response<ExpensesDetailModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                ExpensesDetailModel mExpensesDetailModel = response.body();
                if (mExpensesDetailModel != null)
                    if (mExpensesDetailModel.getStatus_code().equalsIgnoreCase(SUCCESSCODE)) {
                        expenceCatagoryModel = mExpensesDetailModel;

                        Gson gson = new Gson();
                        UtileKit.persistingPurplePathPref(EXPANCE_CATAGORY_PREF, gson.toJson(expenceCatagoryModel));
                    }
                UtileKit.dismisssSpinnerDialog();
//                getAsArrayList(mExpensesDetailModel);
            }

            @Override
            public void onFailure(Call<ExpensesDetailModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow: {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home: {
                Intent i = new Intent(mContext, HomePageActivity.class);
                startActivity(i);
            }
            break;
            case R.id.relative_right_arrow: {

                fragmentManager.popBackStack();

                addFragmenttoStack(new ExpenseTabMainFragment());


            }
            break;
            case R.id.add_floating_button_Id:
            case R.id.essential_btn:
            case R.id.discretionary_btn:
            case R.id.commitment_btn:
            case R.id.Obligation_btn:
            case R.id.contri_total_btn:
                //Log.e("Level1______", "" + selLev1List.size());
                addFragmenttoStack(RetirementExpenseFragment.newInstance(expensesDetailsModel, expenceCatagoryModel,false));

                break;
            case R.id.goal_click_image:
                addFragmenttoStack(RetirementExpenseFragment.newInstance(expensesDetailsModel,expenceCatagoryModel,true));

                break;
        }
    }
}
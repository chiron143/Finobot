package com.purplepath.purplepath.expensesanalysis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyTextView;
import com.purplepath.purplepath.expenseEDCOC.ExpenseTabMainFragment;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev0;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.GetExpensesDetailsModel;
import com.purplepath.purplepath.expensesanalysis.fragment.ExpensesAnalysisFragment;
import com.purplepath.purplepath.expensesanalysis.model.ExpensesAnalysisData;
import com.purplepath.purplepath.expensesanalysis.model.ExpensesAnalysisModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpanseanaysisMainPageFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;
    private ExpensesAnalysisModel mexpensesAnalysisModel;
    private ExpensesAnalysisData expensesAnalysisData;
    private Context mContext;

    private LinearLayout mlayout_Essential,mlayout_Discretionary,mlayout_Commitment,
            mlayout_Obligation,mlayout_Contribution;
    private TextView asset_total_value;
    String masset_total_value;
    private FloatingActionButton mEditExpenseFabBtn;
    GetExpensesDetailsModel expenseModel;



    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_expanseanaysis_main_page1, container, false);
        backPressedListener.setActionBarTitle("Expense Analysis");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);

        mlayout_Essential= view.findViewById(R.id.layout_Essential);
        mlayout_Discretionary= view.findViewById(R.id.layout_Discretionary);
        mlayout_Commitment= view.findViewById(R.id.layout_Commitment);
        mlayout_Obligation= view.findViewById(R.id.layout_Obligation);
        mlayout_Contribution= view.findViewById(R.id.layout_Contribution);

        mEditExpenseFabBtn= view.findViewById(R.id.expense_fab_id);
        mEditExpenseFabBtn.setOnClickListener(this);

        asset_total_value = view.findViewById(R.id.asset_total_value);

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        callExpensesAnalysisService();
        callExpensesAnalysisServiceNew();
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_detail);
        MenuItem items=menu.findItem(R.id.menu_chart);
        // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_detail:
                try{
                    addFragmenttoStack(new ExpanseanaysisDetailFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            case R.id.menu_chart:
                try{
                    addFragmenttoStack(new ExpensesAnalysisFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void callExpensesAnalysisService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<ExpensesAnalysisModel> call = webServiceObj.callExpensesAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"),"Y");
        call.enqueue(new Callback<ExpensesAnalysisModel>() {
            @Override
            public void onResponse(Call<ExpensesAnalysisModel> call, Response<ExpensesAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
                mexpensesAnalysisModel = response.body();

                try {
                if(mexpensesAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    masset_total_value=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getOverall_expense();
                    asset_total_value.setText("₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(masset_total_value))));
                }
                else{
                   // UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }
                }catch (Exception e) {
                        e.printStackTrace();
                    }
            }
            @Override
            public void onFailure(Call<ExpensesAnalysisModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }






    //Expense new
    public void callExpensesAnalysisServiceNew() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetExpensesDetailsModel> call = webServiceObj.callGetExpensesDetailsService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetExpensesDetailsModel>() {
            @Override
            public void onResponse(Call<GetExpensesDetailsModel> call, Response<GetExpensesDetailsModel> response) {
                UtileKit.dismisssSpinnerDialog();
                expenseModel = response.body();
                try {
                    if(expenseModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        if (expenseModel.getData().getUser_expense() != null) {
                            if (!expenseModel.getData().getUser_expense().isEmpty()) {

                                for (Expense_cat_lev0 obj:expenseModel.getData().getUser_expense().get(0).getExpense_cat_lev0()) {
                                    setExpenseSetValue(obj.getId());
                                }
                            }
                        }
                    }else{
                      //  UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetExpensesDetailsModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }


    private void setExpenseSetValue(String id) {
        switch (id)
        {
            case "1":
                mlayout_Essential.setVisibility(View.VISIBLE);
                break;
            case "2":
                mlayout_Discretionary.setVisibility(View.VISIBLE);
                break;
            case "3":
                mlayout_Commitment.setVisibility(View.VISIBLE);
                break;
            case "4" :
                mlayout_Obligation.setVisibility(View.VISIBLE);
                break;
            case "5":
                mlayout_Contribution.setVisibility(View.VISIBLE);
                break;
        }
    }


    void validate(String mstring,LinearLayout linearLayout){
        if (mstring==null||mstring.equalsIgnoreCase("0")) {
            linearLayout.setVisibility(View.GONE);
        }
        else {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.expense_fab_id:
                addFragmenttoStack(new ExpenseTabMainFragment());
                break;
        }

    }
}

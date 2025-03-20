package com.purplepath.purplepath.cashmanaganalysis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.clans.fab.FloatingActionMenu;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.cashflowmanagmentchart.model.GetcashflowinoutflowModel;
import com.purplepath.purplepath.cashmanaganalysis.model.CashManagentAnalyisModel;
import com.purplepath.purplepath.customview.CurrencyTextView;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.expenseEDCOC.ExpenseTabMainFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomedetails.fragment.IncomeDetail;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CashmanagementInOutFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private GetcashflowinoutflowModel incomeAnalysisModel;
    private CashManagentAnalyisModel mcashManagentAnalyisModel;

    private TextView errorTextview,txt_surplus;
    private DefaultCurrencyTextView overall_income,overall_experience;
    String mtxt_surplus,moverall_income,moverall_experience,mdeficit;
    private Context mContext;
    private TextView deficit;

    TextView in_flow_amount,out_flow_amount;

    FloatingActionMenu mcashmanagement_floating_action_menu;
    com.github.clans.fab.FloatingActionButton  mfab_income, mfab_expense;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        backPressedListener= (OnActivityBackPressedListener) mContext;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_cashmanagement_in_out, container, false);
        backPressedListener.setActionBarTitle("Cash Management");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        deficit = view.findViewById(R.id.deficit);
        txt_surplus = view.findViewById(R.id.txt_surplus);

        overall_income= view.findViewById(R.id.overall_income);
        overall_experience= view.findViewById(R.id.overall_experience);
        mcashmanagement_floating_action_menu = view.findViewById(R.id.cashmanagement_floating_action_menu);
        mfab_income = view.findViewById(R.id.fab_income);
        mfab_expense = view.findViewById(R.id.fab_expense);

        UtileKit.setSvgButtonDrawableFloatingButton(mfab_income,mContext,R.drawable.ic_incomefloat_icon);
        UtileKit.setSvgButtonDrawableFloatingButton(mfab_expense,mContext,R.drawable.ic_expensefloat_icon);

        mfab_income.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try{
                    addFragmenttoStack(new IncomeDetail());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        mfab_expense.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try{
                    addFragmenttoStack(new ExpenseTabMainFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        callCashManagmentService();
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
                addFragmenttoStack(new CashmanagementdetailFragment());
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;

            case R.id.menu_chart:

            try{
                addFragmenttoStack(CashManagemntAnalysis.newInstance(mcashManagentAnalyisModel.getData().getCash_mang_det()));
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }





    public void callCashManagmentService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<CashManagentAnalyisModel> call = webServiceObj.callCashMangChartService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<CashManagentAnalyisModel>()  {
            @Override
            public void onResponse(Call<CashManagentAnalyisModel> call, Response<CashManagentAnalyisModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success", "" + response.body());
                mcashManagentAnalyisModel = response.body();

                try {
                if(mcashManagentAnalyisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if (null != mcashManagentAnalyisModel.getData().getCash_mang_det()) {
                        /*overall_income.setText(mcashManagentAnalyisModel.getData().getCash_mang_det().getOver_all_inc());
                    overall_experience.setText(mcashManagentAnalyisModel.getData().getCash_mang_det().getOver_all_exp());*/

                     moverall_income=mcashManagentAnalyisModel.getData().getCash_mang_det().getOver_all_inc();
                     overall_income.setText(moverall_income);
                     moverall_experience=mcashManagentAnalyisModel.getData().getCash_mang_det().getTot_exp();
                     overall_experience.setText(moverall_experience);

                    if(Integer.parseInt(mcashManagentAnalyisModel.getData().getCash_mang_det().getDeflict())<0){
                        mdeficit=mcashManagentAnalyisModel.getData().getCash_mang_det().getDeflict();
                        deficit.setText("₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(mdeficit))));
                        deficit.setTextColor(Color.parseColor("#FF0000"));
                        txt_surplus.setText("Deficit For the next 1 year");
                    }
                    else if(Integer.parseInt(mcashManagentAnalyisModel.getData().getCash_mang_det().getDeflict())>0){
                        mdeficit=mcashManagentAnalyisModel.getData().getCash_mang_det().getDeflict();
                        deficit.setText("₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(mdeficit))));
                        deficit.setTextColor(Color.parseColor("#006400"));
                        txt_surplus.setText("Surplus For the next 1 year");
                     }
                    }
             }
                else
                {
                   // UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }

                } catch (Exception e){
                    e.printStackTrace();
                }

              }
            @Override
            public void onFailure(Call<CashManagentAnalyisModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
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

        }

    }
}

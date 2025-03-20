package com.purplepath.purplepath.cashmanaganalysis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CashmanagementdetailFragment extends BaseFragment implements View.OnClickListener {
    public static Fragment newInstance() {
        return new CashmanagementdetailFragment();
    }

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout,mRightRelativeLayout;
    private GetcashflowinoutflowModel incomeAnalysisModel;
    private CashManagentAnalyisModel mcashManagentAnalyisModel;

    private TextView errorTextview,txt_surplus,overall_income,overall_experience,msource_expanse;
    private Context mContext;
    private TextView total_currencyTextView;

    private DefaultCurrencyTextView  salary_income_value,income_from_property_value,income_from_business_property_value,
            income_from_other_sources_value,capital_gain_value,expanse_value,oblication_value,contribution_value,
            commitment_value;

    String  msalary_income_value,mincome_from_property_value,mincome_from_business_property_value,
            mincome_from_other_sources_value,mcapital_gain_value,mexpanse_value,moblication_value,mcontribution_value,
            mcommitment_value,mtotal_currencyTextView;

    FloatingActionMenu mcashmanagement_floating_action_menu;
    com.github.clans.fab.FloatingActionButton  mfab_income, mfab_expense;

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_cashmanagementdetail, container, false);
        backPressedListener.setActionBarTitle("Cash Management");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

//        mdeficit = (CurrencyTextView) view.findViewById(R.id.deficit);
        txt_surplus = view.findViewById(R.id.txt_surplus);

        overall_income= view.findViewById(R.id.overall_income);
        overall_experience= view.findViewById(R.id.overall_experience);

        salary_income_value= view.findViewById(R.id.salary_income_value);
        income_from_property_value= view.findViewById(R.id.income_from_property_value);
        income_from_business_property_value= view.findViewById(R.id.income_from_business_property_value);
        income_from_other_sources_value= view.findViewById(R.id.income_from_other_sources_value);
        capital_gain_value= view.findViewById(R.id.capital_gain_value);

        total_currencyTextView= view.findViewById(R.id.total_currencyTextView);

        msource_expanse= view.findViewById(R.id.source_expanse);


        expanse_value= view.findViewById(R.id.expanse_value);
        oblication_value = view.findViewById(R.id.oblication_value);
        contribution_value = view.findViewById(R.id.contribution_value);
        commitment_value = view.findViewById(R.id.commitment_value);

        //mRightRelativeLayout.setVisibility(View.GONE);
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
        inflater.inflate(R.menu.menu_summary_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_chart);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_chart:

            try{
                addFragmenttoStack(CashManagemntAnalysis.newInstance(mcashManagentAnalyisModel.getData().getCash_mang_det()));
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;

            case R.id.menu_summary:

                try{
                    addFragmenttoStack(new CashmanagementInOutFragment());
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
                if(mcashManagentAnalyisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    if (null != mcashManagentAnalyisModel.getData().getCash_mang_det()) {
                        try {
                            if(mcashManagentAnalyisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                                String sitotal=mcashManagentAnalyisModel.getData().getCash_mang_det().getSi_total();
                                String iptotal=mcashManagentAnalyisModel.getData().getCash_mang_det().getIp_total();
                                String ibtotal=mcashManagentAnalyisModel.getData().getCash_mang_det().getIb_total();
                                String ifstotal=mcashManagentAnalyisModel.getData().getCash_mang_det().getIfs_total();
                                String cgtotal=mcashManagentAnalyisModel.getData().getCash_mang_det().getCg_total();
                                String overallexp=mcashManagentAnalyisModel.getData().getCash_mang_det().getOver_all_exp();
                                String overobli=mcashManagentAnalyisModel.getData().getCash_mang_det().getOver_all_obli();
                                String overcont=mcashManagentAnalyisModel.getData().getCash_mang_det().getOver_all_contr();
                                String overcommi=mcashManagentAnalyisModel.getData().getCash_mang_det().getOver_all_commt();
                                String dificit=mcashManagentAnalyisModel.getData().getCash_mang_det().getDeflict();

                                validateDefault(sitotal,salary_income_value);
                                validateDefault(iptotal,income_from_property_value);
                                validateDefault(ibtotal,income_from_business_property_value);
                                validateDefault(ifstotal,income_from_other_sources_value);
                                validateDefault(cgtotal,capital_gain_value);
                                validateDefault(overallexp,expanse_value);
                                validateDefault(overobli,oblication_value);
                                validateDefault(overcont,contribution_value);
                                validateDefault(overcommi,commitment_value);
                              //  validate(dificit,total_currencyTextView);

                                total_currencyTextView.setText("₹ "+UtileKit.longvalueabsolute(Float.parseFloat(dificit)));

                                DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                                Date date=new Date();
                                String [] value=dateFormat.format(date).split("/");
                                msource_expanse.setText("As on "+value[2]+" "+UtileKit.getMonthString
                                        (Integer.parseInt(value[1])-1)+" "+value[0]);
                            }
                            else
                            {
                                UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                            }


                } catch (Exception e){
                            e.printStackTrace();
                        }}}

            }
            @Override
            public void onFailure(Call<CashManagentAnalyisModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }


    void validate(String mstring,CurrencyTextView currencyTextView){
        if (mstring==null|| mstring.equalsIgnoreCase("0")) {
            currencyTextView.setText("0");
        }
        else {
            currencyTextView.setText(mstring);
        }
    }

    void validateDefault(String mstring,DefaultCurrencyTextView currencyTextView){
        if (mstring==null|| mstring.equalsIgnoreCase("0")) {
            currencyTextView.setText("0");
        }
        else {
            currencyTextView.setText(mstring);
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

        }

    }
}

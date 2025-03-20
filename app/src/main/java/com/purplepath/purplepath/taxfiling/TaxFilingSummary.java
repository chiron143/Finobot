package com.purplepath.purplepath.taxfiling;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.Space;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.TaxFilingViewPager.TaxFilingChatViewPager;
import com.purplepath.purplepath.taxfiling.addsessionmodel.AddSessionModel;
import com.purplepath.purplepath.taxfiling.getsummary.TaxFileSummaryModel;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;


/**
 * Created by pravinr on 4/9/18.
 */

public class TaxFilingSummary extends BaseFragment implements View.OnClickListener {


    private TaxFileSummaryModel taxFileSummaryModel;

    private OnActivityBackPressedListener mCallBackListener;

    RelativeLayout relative_left_arrow,relative_center_home;

    private Context mContext;

    private TextView incomefrom_salary_value,income_property_value,income_other_value,
                     gross_income_value,deduction_value,rebates_value,total_taxable_income_value,
                     liability_value,income_after_tax_value,tax_prepaid,refund_payable_values;
    private TextView txt_tax_values;

    private BigInteger mIncomefrom_salary_value=BigInteger.ZERO;

    private BigInteger mIncome_property_value=BigInteger.ZERO;

    private BigInteger mIncome_other_value=BigInteger.ZERO;

    private BigInteger mGross_income_value=BigInteger.ZERO;

    private BigInteger mDeduction_value=BigInteger.ZERO;

    private BigInteger mRebates_value=BigInteger.ZERO;

    private BigInteger mTotal_taxable_income_value=BigInteger.ZERO;

    private BigInteger mLiability_value=BigInteger.ZERO;

    private BigInteger mIncome_after_tax_value=BigInteger.ZERO;

    private LinearLayout layout_refund_payable;

    private BigInteger mTax_prepaid=BigInteger.ZERO;

    private BigInteger mRefund_payable_values=BigInteger.ZERO;

    private FloatingActionButton fab_declaration;

    private BigInteger mbal_tax_payable_value=BigInteger.ZERO;



    public static TaxFilingSummary newInstance(String card_amount) {
        TaxFilingSummary taxFileDeclaration = new TaxFilingSummary();
        Bundle args = new Bundle();

        if (card_amount != null) {
            args.putSerializable("card_amount", card_amount);
        }
        taxFileDeclaration.setArguments(args);
        return taxFileDeclaration;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try{
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_tax_filing_summary, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        //    if(getArguments().containsKey("card_amount")) {
//            card_amount = getArguments().getString("card_amount");
//        }
        mCallBackListener.setActionBarTitle("Tax Filing Summary");
        relative_center_home= view.findViewById(R.id.relative_center_home);
        relative_left_arrow= view.findViewById(R.id.relative_left_arrow);

        relative_center_home.setOnClickListener(this);
        relative_left_arrow.setOnClickListener(this);



        incomefrom_salary_value= view.findViewById(R.id.incomefrom_salary_value);
        income_property_value= view.findViewById(R.id.income_property_value);
        income_other_value= view.findViewById(R.id.income_other_value);
        gross_income_value= view.findViewById(R.id.gross_income_value);
        deduction_value= view.findViewById(R.id.deduction_value);
        rebates_value= view.findViewById(R.id.rebates_value);
        total_taxable_income_value= view.findViewById(R.id.total_taxable_income_value);
        liability_value= view.findViewById(R.id.liability_value);
        income_after_tax_value= view.findViewById(R.id.income_after_tax_value);


        tax_prepaid= view.findViewById(R.id.tax_prepaid);
        txt_tax_values= view.findViewById(R.id.txt_tax_values);
        refund_payable_values= view.findViewById(R.id.refund_payable_values);

        layout_refund_payable= view.findViewById(R.id.layout_refund_payable);

        fab_declaration = (FloatingActionButton) view.findViewById(R.id.fab_declaration);
        fab_declaration.setOnClickListener(this);




        callTaxAnalysisService();


//        String s = "ABCDE1234F"; // get your editext value here
//        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
//
//        Matcher matcher = pattern.matcher(s);
//// Check if pattern matches
//        if (matcher.matches()) {
//            Log.i("Matching","Yes");
//        }


        return view;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mFirebaseAnalytics.setCurrentScreen(getActivity(), getString(R.string.analtics_taxsummary_screen), null /* class override */);
//    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_taxsummary_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "19");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    private void callTaxAnalysisService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxFileSummaryModel> call = webServiceObj.callGetTaxFileSummaryService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxFileSummaryModel>() {
            @Override
            public void onResponse(Call<TaxFileSummaryModel> call, Response<TaxFileSummaryModel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    taxFileSummaryModel = response.body();

                    if (taxFileSummaryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                     showSummaryValues(taxFileSummaryModel);
                    }
                    else{

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxFileSummaryModel> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void showSummaryValues(TaxFileSummaryModel taxFileSummaryModel) {


        if(taxFileSummaryModel.getData().getSummary_result().get(0).getInc_from_sal()!=null);{
            mIncomefrom_salary_value=new BigInteger(taxFileSummaryModel.getData().getSummary_result().get(0).getInc_from_sal());
            incomefrom_salary_value.setText("₹ "+UtileKit.formatedNumbers(mIncomefrom_salary_value));
        }
        if(taxFileSummaryModel.getData().getSummary_result().get(0).getTot_inc_of_hp()!=null);{
            mIncome_property_value=new BigInteger(taxFileSummaryModel.getData().getSummary_result().get(0).getTot_inc_of_hp());
            income_property_value.setText("₹ "+UtileKit.formatedNumbers(mIncome_property_value));
        }
        if(taxFileSummaryModel.getData().getSummary_result().get(0).getInc_oth_src()!=null);{
            mIncome_other_value=new BigInteger(taxFileSummaryModel.getData().getSummary_result().get(0).getInc_oth_src());
            income_other_value.setText("₹ "+UtileKit.formatedNumbers(mIncome_other_value));
        }
        if(taxFileSummaryModel.getData().getSummary_result().get(0).getGross_tot_inc()!=null);{
            mGross_income_value=new BigInteger(taxFileSummaryModel.getData().getSummary_result().get(0).getGross_tot_inc());
            gross_income_value.setText("₹ "+UtileKit.formatedNumbers(mGross_income_value));
        }
        if(taxFileSummaryModel.getData().getSummary_result().get(0).getTot_via_ded()!=null);{
            mDeduction_value=new BigInteger(taxFileSummaryModel.getData().getSummary_result().get(0).getTot_via_ded());
            deduction_value.setText("₹ "+UtileKit.formatedNumbers(mDeduction_value));
        }
        if(taxFileSummaryModel.getData().getSummary_result().get(0).getRebate_87a()!=null);{
            mRebates_value=new BigInteger(taxFileSummaryModel.getData().getSummary_result().get(0).getRebate_87a());
            rebates_value.setText("₹ "+UtileKit.formatedNumbers(mRebates_value));
        }

        //Gross income-Deduction=Total Taxable Income
        mTotal_taxable_income_value=mGross_income_value.subtract(mDeduction_value);
        total_taxable_income_value.setText("₹ "+UtileKit.formatedNumbers(mTotal_taxable_income_value));


        if(taxFileSummaryModel.getData().getSummary_result().get(0).getNet_tax_liab()!=null);{
            mLiability_value=new BigInteger(taxFileSummaryModel.getData().getSummary_result().get(0).getNet_tax_liab());
            liability_value.setText("₹ "+UtileKit.formatedNumbers(mLiability_value));
        }
        mIncome_after_tax_value=mTotal_taxable_income_value.subtract(mLiability_value);

        income_after_tax_value.setText("₹ "+UtileKit.formatedNumbers(mIncome_after_tax_value));

        //top bar values
        if(taxFileSummaryModel.getData().getSummary_result().get(0).getTot_tax_paid()!=null){
            mTax_prepaid=new BigInteger(taxFileSummaryModel.getData().getSummary_result().get(0).getTot_tax_paid());
            tax_prepaid.setText("₹ "+UtileKit.formatedNumbers(mTax_prepaid));
        }

        //TAX PAID & REFUNDABLE
        if(taxFileSummaryModel.getData().getSummary_result().get(0).getRefund_due()!=null){
            mRefund_payable_values=new BigInteger(taxFileSummaryModel.getData().getSummary_result().get(0).getRefund_due());
        }

        if(taxFileSummaryModel.getData().getSummary_result().get(0).getBal_tax_payable()!=null){
            mbal_tax_payable_value=new BigInteger(taxFileSummaryModel.getData().getSummary_result().get(0).getBal_tax_payable());
        }



        if(mRefund_payable_values.signum() == 1){

            txt_tax_values.setText("Tax Refund");
            layout_refund_payable.setBackgroundResource(R.drawable.tax_summary_green_back);
            refund_payable_values.setText("₹ " + UtileKit.formatedNumbers(mRefund_payable_values));
        }else {
            txt_tax_values.setText("Tax Payable");
            layout_refund_payable.setBackgroundResource(R.drawable.tax_amber_back);
            //First time "net_tax_liab" will set in the textview after will change to bal_tax_payable
            refund_payable_values.setText("₹ " + UtileKit.formatedNumbers(mbal_tax_payable_value));
        }




    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }


    void setValues(String values,BigInteger biginteger,TextView textview){
        if(values!=null){
            biginteger=new BigInteger(values);
            textview.setText("₹ "+UtileKit.formatedNumbers(biginteger));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

            case R.id.fab_declaration:

                String corresponding_table="users_tax_file_page_visited_status",
                        field_name="tax_summary",
                        field_value="Y";
                AddTaxfileSessionService(corresponding_table,field_name,field_value);
                break;

        }
    }
    private void AddTaxfileSessionService(String corresponding_table, String field_name, String field_value) {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddSessionModel> call = webServiceObj.AddTaxfileSessionService(UtileKit.getPersistedPurplePathPref("user_id")
                ,corresponding_table,field_name, field_value);
        call.enqueue(new Callback<AddSessionModel>() {
            @Override
            public void onResponse(Call<AddSessionModel> call, Response<AddSessionModel> response) {
                UtileKit.dismisssSpinnerDialog();
                AddSessionModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                      //  addFragmenttoStack(new TaxFileChartConversation());

                        addFragmenttoStack(new TaxFilingChatViewPager());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<AddSessionModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }
}

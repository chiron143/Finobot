package com.purplepath.purplepath.taxprompt;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.TaxPromptNewModel;

import java.math.BigInteger;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Murugesan on 1/22/18.
 */

public class TaxPromptFunnel_Chart extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private TaxPromptNewModel taxAnalysistModel;

    private TextView mtotal_taxable,mtax,mafter_tax,mincome,mexcemption,mgross_income,mdeduction,mrebates;

    private BigInteger total_tax_values=BigInteger.ZERO;

    private BigInteger tax_value=BigInteger.ZERO;

    private BigInteger after_tax=BigInteger.ZERO;


    private BigInteger income_value=BigInteger.ZERO;

    private BigInteger excemption_value=BigInteger.ZERO;

    private BigInteger gross_income_value=BigInteger.ZERO;

    private BigInteger deduction_value=BigInteger.ZERO;

    private BigInteger rebates_value=BigInteger.ZERO;

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_promt_funnel, container, false);
         backPressedListener.setActionBarTitle("Tax Analysis");

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        mtotal_taxable= view.findViewById(R.id.total_taxable);
        mtax= view.findViewById(R.id.tax);
        mafter_tax= view.findViewById(R.id.after_tax);

        mincome= view.findViewById(R.id.income);
        mexcemption= view.findViewById(R.id.excemption);
        mgross_income= view.findViewById(R.id.gross_income);
        mdeduction= view.findViewById(R.id.deduction);
        mrebates= view.findViewById(R.id.rebates);

        callTaxFunnelService();
        return view;
    }


    private void callTaxFunnelService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String finYr = String.valueOf(year-1);
        Call<TaxPromptNewModel> call = webServiceObj.callTaxPromptService_new(UtileKit.getPersistedPurplePathPref("user_id"),"FY"+finYr);
        call.enqueue(new Callback<TaxPromptNewModel>() {
            @Override
            public void onResponse(Call<TaxPromptNewModel> call, Response<TaxPromptNewModel> response) {
                UtileKit.dismisssSpinnerDialog();
                taxAnalysistModel= response.body();
                try {
                    if (taxAnalysistModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        showFunnelValues(taxAnalysistModel);

                    } else {

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<TaxPromptNewModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void showFunnelValues(TaxPromptNewModel taxAnalysistModel) {

        if(taxAnalysistModel.getData().getTax_calc().getTot_income_before_gti()!=null) {
            income_value = new BigInteger(taxAnalysistModel.getData().getTax_calc().getTot_income_before_gti());
        }

        if(taxAnalysistModel.getData().getTax_calc().getExemptions()!=null) {
            excemption_value = new BigInteger(taxAnalysistModel.getData().getTax_calc().getExemptions());
        }

        if(taxAnalysistModel.getData().getTax_calc().getGti_income()!=null) {
            gross_income_value = new BigInteger(taxAnalysistModel.getData().getTax_calc().getGti_income());
        }

        if(taxAnalysistModel.getData().getTax_calc().getRebates()!=null) {
            rebates_value = new BigInteger(taxAnalysistModel.getData().getTax_calc().getRebates());
        }

        if(taxAnalysistModel.getData().getTax_calc().getAllowed_deduction()!=null) {
            deduction_value = new BigInteger(taxAnalysistModel.getData().getTax_calc().getAllowed_deduction());
        }




        if(taxAnalysistModel.getData().getTax_calc().getTaxable_income()!=null) {
            total_tax_values = new BigInteger(taxAnalysistModel.getData().getTax_calc().getTaxable_income());
        }
        if(taxAnalysistModel.getData().getTax_calc().getTotal_tax_payable()!=null) {
            tax_value = new BigInteger(taxAnalysistModel.getData().getTax_calc().getTotal_tax_payable());
        }

        mincome.setText("l "+UtileKit.formatedNumbers(income_value));
        mexcemption.setText("₹ "+UtileKit.formatedNumbers(excemption_value));
        mgross_income.setText("₹ "+UtileKit.formatedNumbers(gross_income_value));
        mdeduction.setText("₹ "+UtileKit.formatedNumbers(deduction_value));
        mrebates.setText("₹ "+UtileKit.formatedNumbers(rebates_value));

        after_tax=total_tax_values.subtract(tax_value);

        mtotal_taxable.setText("₹ "+UtileKit.formatedNumbers(total_tax_values));
        mtax.setText("₹ "+UtileKit.formatedNumbers(tax_value));
        mafter_tax.setText("₹ "+UtileKit.formatedNumbers(after_tax));

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_detail,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_detail);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_detail:

                //addFragmenttoStack(new TaxClasificationDetail());
                addFragmenttoStack(new TaxClasificationDetail());

                break;
            case R.id.menu_summary:

                addFragmenttoStack(new TaxPromptSummary());

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                startSettingHomeActivity();
                break;

        }

    }
}

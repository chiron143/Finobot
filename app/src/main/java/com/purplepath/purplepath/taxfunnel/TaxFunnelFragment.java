package com.purplepath.purplepath.taxfunnel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
import java.math.BigInteger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Murugesan on 1/17/18.
 */

public class TaxFunnelFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private TaxCashFlowModel taxAnalysistModel;

    private TextView errorTextview;

    private DefaultCurrencyTextView mtotal_taxable,mtax,mafter_tax,mincome,mexcemption,mgross_income,mdeduction,mrebates;

    private BigInteger deduction=BigInteger.ZERO;

    private BigInteger gross_income=BigInteger.ZERO;

    private BigInteger taxable_income=BigInteger.ZERO;

    private BigInteger total_tax_payable=BigInteger.ZERO;

    private BigInteger after_tax=BigInteger.ZERO;

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_funnel, container, false);
       // backPressedListener.setActionBarTitle("Funnel");

        /*mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);*/

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
        Call<TaxCashFlowModel> call = webServiceObj.callinsurance_tax_Cash_Flow_Service(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxCashFlowModel>() {
            @Override
            public void onResponse(Call<TaxCashFlowModel> call, Response<TaxCashFlowModel> response) {
                UtileKit.dismisssSpinnerDialog();
                taxAnalysistModel= response.body();
                try {
                    if (taxAnalysistModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        showFunnelValues(taxAnalysistModel);

                        addDeductionValues(taxAnalysistModel);

                    } else {
                        errorTextview.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<TaxCashFlowModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void showFunnelValues(TaxCashFlowModel taxAnalysistModel) {

        String str_income=taxAnalysistModel.getData().getTax_calc().getTot_income();
        String str_excemption=taxAnalysistModel.getData().getTax_calc().getExemptions();
        String str_gross_income=taxAnalysistModel.getData().getTax_calc().getGti_income();
        String str_tax=taxAnalysistModel.getData().getTax_calc().getTotal_tax_payable();


        mincome.setText(str_income);
        mexcemption.setText(str_excemption);
        mgross_income.setText(str_gross_income);
        mtax.setText(str_tax);
    }
    private void addDeductionValues(TaxCashFlowModel taxAnalysistModel) {

        deduction=BigInteger.ZERO;

        int length=taxAnalysistModel.getData().getDed_by_prod().size();
          for (int i=0;i<length;i++){

              if(taxAnalysistModel.getData().getDed_by_prod().get(i).getContr_val()!=null) {
                  deduction = deduction.add(new BigInteger(taxAnalysistModel.getData().getDed_by_prod().get(i).getContr_val()));
              }

          }

        mdeduction.setText(String.valueOf(deduction));

        gross_income=new BigInteger(taxAnalysistModel.getData().getTax_calc().getGti_income());

        taxable_income=gross_income.subtract(deduction);

        mtotal_taxable.setText(String.valueOf(taxable_income));

        total_tax_payable=new BigInteger(taxAnalysistModel.getData().getTax_calc().getTotal_tax_payable());

        after_tax=taxable_income.subtract(total_tax_payable);

        mafter_tax.setText(String.valueOf(after_tax));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

           /* case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                startSettingHomeActivity();
                break;
*/
        }

    }
}

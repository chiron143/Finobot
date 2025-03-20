package com.purplepath.purplepath.taxanalysis;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;
import android.text.SpannableString;
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
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
import com.purplepath.purplepath.taxanalysis.modes.GetTaxPlanModels;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaxPlanSummary extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.savingsTopText)
    TextView savingsTopText;

    @BindView(R.id.savingsBottomText)
    TextView savingsBottomText;

    @BindView(R.id.availedTopText)
    TextView availedTopText;

    @BindView(R.id.availedBottomText)
    TextView availedBottomText;

    @BindView(R.id.pendingTopText)
    TextView pendingTopText;

    @BindView(R.id.pendingBottomText)
    TextView pendingBottomText;
    private FloatingActionButton fab_id;
    private String TAG="spcheck";
    private GetTaxPlanModels taxAnalysisChartModel;
    private TaxCashFlowModel taxAnalysisCashFlowChartModel;
    private OnActivityBackPressedListener mCallBackListener;
    RelativeLayout relative_left_arrow,relative_center_home;
  //  private int sum=0;
    private String totalLimtcal= null;
    private Context mContext;
    private  LinearLayout myour_entitle_layout,mtax_savingavalied_layout,
            myouhava_pendingamount_layout;
    private CardView mtax_savingavalied_cardlayout,myour_entitle_cardlayout,myouhava_pendingamount_cardlayout;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBackListener=(OnActivityBackPressedListener)context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.networth_summary_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_details:

                //TaxPlanDetails taxPlanDetails=TaxPlanDetails.newInstance(taxAnalysisCashFlowChartModel,taxAnalysisChartModel);
                //showFragment(taxPlanDetails);

                TaxPlanDetailViewPager taxPlanDetailViewPager=TaxPlanDetailViewPager.newInstance(taxAnalysisCashFlowChartModel,taxAnalysisChartModel);
                showFragment(taxPlanDetailViewPager);

                break;
            case R.id.menu_graph:
//                TaxCashFlowChart taxCashFlowChart=new TaxCashFlowChart();

                try {
                    //pradeep
                    //descriptiondatapass(taxAnalysisCashFlowChartModel);

                    TaxAnalysisViewPager taxAnalysisViewPager=TaxAnalysisViewPager.newInstance(taxAnalysisCashFlowChartModel);
                    showFragment(taxAnalysisViewPager);


                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void descriptiondatapass(TaxCashFlowModel user_tax) {
        try {

            Fragment fragment = new TaxAnalysis();
            Bundle bundle = new Bundle();
            if (user_tax != null) {
                bundle.putSerializable("user_tax_position", user_tax);
                Log.i("TaxCashFlowChart", "getTotal_savings pass" + user_tax);
            }
            fragment.setArguments(bundle);
            addFragmenttoStack(fragment);
//            fragmentTransaction.replace(R.id.fragment_container, fragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commitAllowingStateLoss();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment).addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_tax_plan_summary, container, false);
        mContext=getContext();
        ButterKnife.bind(this,view);

        mCallBackListener.setActionBarTitle("Tax Plan");
        relative_center_home= view.findViewById(R.id.relative_center_home);
        relative_left_arrow= view.findViewById(R.id.relative_left_arrow);
        fab_id = view.findViewById(R.id.fab_id);

        myour_entitle_layout= view.findViewById(R.id.your_entitle_layout);
        mtax_savingavalied_layout= view.findViewById(R.id.tax_savingavalied_layout);
        myouhava_pendingamount_layout= view.findViewById(R.id.youhava_pendingamount_layout);

        myour_entitle_cardlayout= view.findViewById(R.id.your_entitle_cardlayout);
        mtax_savingavalied_cardlayout= view.findViewById(R.id.tax_savingavalied_cardlayout);
        myouhava_pendingamount_cardlayout= view.findViewById(R.id.youhava_pendingamount_cardlayout);

        relative_center_home.setOnClickListener(this);
        relative_left_arrow.setOnClickListener(this);
        fab_id.setOnClickListener(this);
        setHasOptionsMenu(true);
        callTaxEntitlementService();
        callTaxAnalysisService();


        return view;
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
            case R.id.fab_id:
            {
//                addFragmenttoStack(new InsuranceDetailsFragment());
            }
            break;
        }
    }

    private void callTaxAnalysisService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxCashFlowModel> call = webServiceObj.callinsurance_tax_Cash_Flow_Service(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxCashFlowModel>() {
            @Override
            public void onResponse(Call<TaxCashFlowModel> call, Response<TaxCashFlowModel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    taxAnalysisCashFlowChartModel = response.body();

                    if (taxAnalysisCashFlowChartModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if (taxAnalysisCashFlowChartModel.getData().getUser_tax().size() > 0) {
                            String taxtotalsavings = taxAnalysisCashFlowChartModel.getData().getUser_tax().get(0).getTotal_savings();
                            if (taxtotalsavings != null && !taxtotalsavings.equalsIgnoreCase("0")) {
                                setAvailedTaxValue(taxtotalsavings);
                            } else {
                                // setAvailedTaxValue(String.valueOf(0));
                                // myour_entitle_layout.setVisibility(View.VISIBLE);
                                mtax_savingavalied_cardlayout.setVisibility(View.GONE);
                                myouhava_pendingamount_cardlayout.setVisibility(View.GONE);
                            }
                        }else {
                            UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                        }
                    }
                    else{
                        setAvailedTaxValue(String.valueOf(0));
                        mtax_savingavalied_layout.setVisibility(View.GONE);
                       // mtax_savingavalied_cardlayout.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxCashFlowModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void callTaxEntitlementService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetTaxPlanModels> call = webServiceObj.callinsurance_tax_Service(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetTaxPlanModels>() {
            @Override
            public void onResponse(Call<GetTaxPlanModels> call, Response<GetTaxPlanModels> response) {
                try {
                    Log.i("Tax Analysis","Tax Analysis user id"+UtileKit.getPersistedPurplePathPref("user_id"));
                    UtileKit.dismisssSpinnerDialog();

                    taxAnalysisChartModel = response.body();
                    if (taxAnalysisChartModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if(null!= taxAnalysisChartModel.getData().getTax_plan().get(0).getSection()) {
                            int size = taxAnalysisChartModel.getData().getTax_plan().size();

                            //Log.i("Tax Analysis","Tax Analysis  Session"+taxAnalysisChartModel.getData().getTax_plan().get(0).getSection());
                            try {
                                int sum=0;
                                for (int i = 0; i < size; i++) {
                                    Log.i(TAG, "i=" + i + " sum=" + sum + "");
                                    sum += Integer.parseInt(taxAnalysisChartModel.getData().getTax_plan().get(i).getTotal_limit());

                                }
                                setValueForEntitlement(sum);
                                Log.i(TAG, "Final Sum=" + sum + "");
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    else {
                        setValueForEntitlement(Integer.parseInt(String.valueOf(0)));
                       // myour_entitle_layout.setVisibility(View.GONE);
                        myour_entitle_cardlayout.setVisibility(View.GONE);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetTaxPlanModels> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }

    private void setValueForEntitlement(int sum) {
        savingsTopText.setText("Your Entitlement for Tax Savings");

        totalLimtcal = String.valueOf(sum);
        //String str="₹ "+formatedNumber(sum);
        String str = "₹ "+UtileKit.longvalueabsolute(sum);
        SpannableString styleText=new SpannableString(str);
       // styleText.setSpan(new RelativeSizeSpan(2F),0,str.length(),0);
      //  styleText.setSpan(new StyleSpan(Typeface.BOLD),0,str.length(),0);

        savingsBottomText.setText(styleText);
       // savingsBottomText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
             //   getResources().getDimension(R.dimen.text_size_mediam));


    }

    private void setAvailedTaxValue(String total_savings) {
        try {
            //String str = "₹ " + formatedNumber(Integer.parseInt(total_savings));
            String str = "₹ "+UtileKit.longvalueabsolute(Integer.parseInt(total_savings));
            availedTopText.setText("Tax Savings Availed");
            SpannableString styleText = new SpannableString(str);
           // styleText.setSpan(new RelativeSizeSpan(2f), 0, str.length(), 0);
          //  styleText.setSpan(new StyleSpan(Typeface.BOLD),0,str.length(),0);
            availedBottomText.setText(styleText);
            if(total_savings!=null&& !total_savings.equalsIgnoreCase("0"))  {
                setBalanceValue(total_savings , totalLimtcal);
            }
            else
            {
                mtax_savingavalied_cardlayout.setVisibility(View.GONE);
                myouhava_pendingamount_cardlayout.setVisibility(View.GONE);
            }

         }catch (Exception e){
        e.printStackTrace();
    }
    }

    private void setBalanceValue(String total_savings, String totalLimtcal) {
        pendingTopText.setText("You have un-availed pending amount");

       try {
           if(total_savings!= null && totalLimtcal != null) {
               int sum = Integer.parseInt(totalLimtcal);
               int pendingAmount = sum - Integer.parseInt(total_savings);
               //String str = "₹ " + formatedNumber(pendingAmount);
               String str = "₹ "+UtileKit.longvalueabsolute(pendingAmount);
               SpannableString styleText = new SpannableString(str);
               // styleText.setSpan(new RelativeSizeSpan(2f), 0, str.length(), 0);
               //  styleText.setSpan(new StyleSpan(Typeface.BOLD),0,str.length(),0);
               pendingBottomText.setText(styleText);


               String output = formatedNumber(10000000);
               Log.i(TAG, "setBalanceValue: " + output);
           }else{
               int pendingAmount = Integer.parseInt(total_savings);
               //String str = "₹ " + formatedNumber(pendingAmount);
               String str = "₹ "+UtileKit.longvalueabsolute(pendingAmount);
               SpannableString styleText = new SpannableString(str);
               // styleText.setSpan(new RelativeSizeSpan(2f), 0, str.length(), 0);
               //  styleText.setSpan(new StyleSpan(Typeface.BOLD),0,str.length(),0);
               pendingBottomText.setText(styleText);


               String output = formatedNumber(10000000);
               Log.i(TAG, "setBalanceValue: " + output);
           }
       }catch (Exception e){
           e.printStackTrace();
       }


    }

    private String formatedNumber(int value){
        DecimalFormat myFormatter = new DecimalFormat("#,##,###");
        String output = myFormatter.format(value);
        return output;
    }


}

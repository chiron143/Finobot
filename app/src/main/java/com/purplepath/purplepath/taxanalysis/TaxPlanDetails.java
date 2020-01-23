/*
package com.purplepath.purplepath.taxanalysis;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
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
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
import com.purplepath.purplepath.taxanalysis.modes.GetTaxPlanModels;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



*/
/**
 * A simple {@link Fragment} subclass.
 *//*

public class TaxPlanDetails extends BaseFragment implements View.OnClickListener {

     @Bind(R.id.layout_80C)
    RelativeLayout layout_80C;

     @Bind(R.id.value_80C)
    TextView value_80C;

     @Bind(R.id.layout_80CCD)
    RelativeLayout layout_80CCD;

     @Bind(R.id.value_80CCD)
    TextView value_80CCD;


     @Bind(R.id.layout_80CCG)
    RelativeLayout layout_80CCG;

     @Bind(R.id.value_80CCG)
    TextView value_80CCG;

     @Bind(R.id.layout_80D)
    RelativeLayout layout_80D;

     @Bind(R.id.value_80D)
    TextView value_80D;

     @Bind(R.id.layout_80GG)
    RelativeLayout layout_80GG;

     @Bind(R.id.value_80GG)
    TextView value_80GG;

     @Bind(R.id.layout_TRA)
    RelativeLayout layout_TRA;

     @Bind(R.id.value_TRA)
    TextView value_TRA;

     @Bind(R.id.availed_layout_80C)
    RelativeLayout availed_layout_80C;

     @Bind(R.id.availed_value_80C)
    TextView availed_value_80C;

     @Bind(R.id.availed_layout_80CCD)
    RelativeLayout availed_layout_80CCD;

     @Bind(R.id.availed_value_80CCD)
    DefaultCurrencyTextView availed_value_80CCD;

     @Bind(R.id.availed_layout_80CCG)
    RelativeLayout availed_layout_80CCG;

     @Bind(R.id.availed_value_80CCG)
    DefaultCurrencyTextView availed_value_80CCG;

     @Bind(R.id.availed_layout_80D)
    RelativeLayout availed_layout_80D;

     @Bind(R.id.availed_value_80D)
    DefaultCurrencyTextView availed_value_80D;

     @Bind(R.id.availed_layout_80GG)
    RelativeLayout availed_layout_80GG;

     @Bind(R.id.availed_value_80GG)
    DefaultCurrencyTextView availed_value_80GG;

     @Bind(R.id.availed_layout_TRA)
    RelativeLayout availed_layout_TRA;

     @Bind(R.id.availed_value_TRA)
    DefaultCurrencyTextView availed_value_TRA;


     @Bind(R.id.pending_layout_80C)
    RelativeLayout pending_layout_80C;

     @Bind(R.id.pending_value_80C)
     DefaultCurrencyTextView pending_value_80C;

     @Bind(R.id.pending_layout_80CCD)
    RelativeLayout pending_layout_80CCD;

     @Bind(R.id.pending_value_80CCD)
    DefaultCurrencyTextView pending_value_80CCD;

     @Bind(R.id.pending_layout_80CCG)
    RelativeLayout pending_layout_80CCG;

     @Bind(R.id.pending_value_80CCG)
    DefaultCurrencyTextView pending_value_80CCG;

     @Bind(R.id.pending_layout_80D)
    RelativeLayout pending_layout_80D;

     @Bind(R.id.pending_value_80D)
    DefaultCurrencyTextView pending_value_80D;

     @Bind(R.id.pending_layout_80GG)
    RelativeLayout pending_layout_80GG;

     @Bind(R.id.pending_value_80GG)
    DefaultCurrencyTextView pending_value_80GG;

     @Bind(R.id.pending_layout_TRA)
    RelativeLayout pending_layout_TRA;

     @Bind(R.id.pending_value_TRA)
    DefaultCurrencyTextView pending_value_TRA;

     @Bind(R.id.line1_Text)
    TextView line1_Text;

     @Bind(R.id.line2_Text)
    TextView line2_Text;

    private FloatingActionButton fab_id;
    private HashMap<String, String> map = new HashMap<String, String>();
    RelativeLayout relative_left_arrow, relative_center_home;
    private TaxCashFlowModel taxAnalysistModel;
    private GetTaxPlanModels entitlementInfoModel;//taxAnalysisChartModel
    private OnActivityBackPressedListener mCallBackListener;
    private Bundle args;

    private Context mContext;
    private CardView mlayout_entitlement,mlayout_availed,
            mlayout_pending,mlayout_saveing_total;


    public static TaxPlanDetails newInstance(TaxCashFlowModel taxAnalysistModel, GetTaxPlanModels taxAnalysisChartModel) {
        TaxPlanDetails fragment = new TaxPlanDetails();
        Bundle args = new Bundle();
        if (taxAnalysistModel != null) {
            args.putSerializable("TaxPlanDetails", taxAnalysistModel);
            args.putSerializable("entitlementInfo", taxAnalysisChartModel);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        mCallBackListener = (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext=getContext();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chart:
                descriptiondatapass(taxAnalysistModel);
                break;
            case R.id.menu_summary:
                TaxPlanSummary taxPlanSummary = new TaxPlanSummary();
                showFragment(taxPlanSummary);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }
    private void descriptiondatapass(TaxCashFlowModel user_tax) {
        try {

            Fragment fragment = new TaxAnalysis();
//            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tax_plan_details, container, false);
        ButterKnife.bind(this, view);
        mCallBackListener.setActionBarTitle("Tax Plan");
        setHasOptionsMenu(true);
        fab_id = (FloatingActionButton) view.findViewById(R.id.fab_id);
        relative_center_home = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        relative_left_arrow = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mlayout_entitlement=(CardView)view.findViewById(R.id.layout_entitlement);
        mlayout_availed=(CardView)view.findViewById(R.id.layout_availed);
        mlayout_pending=(CardView)view.findViewById(R.id.layout_pending);
        mlayout_saveing_total=(CardView)view.findViewById(R.id.layout_saveing_total);



        args = getArguments();
        if (args != null) {
            if (args.containsKey("entitlementInfo")) {
                entitlementInfoModel = (GetTaxPlanModels) args.getSerializable("entitlementInfo");
                try {
                    if (entitlementInfoModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        showEntitlementInfo();
                    } else {
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            if (args.containsKey("TaxPlanDetails")) {
                taxAnalysistModel = (TaxCashFlowModel) args.getSerializable("TaxPlanDetails");
                if (taxAnalysistModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    showAvailedValues();
                    showSavedAmount();
                }
                else{
                        mlayout_pending.setVisibility(View.GONE);
                       mlayout_availed.setVisibility(View.GONE);
                       mlayout_saveing_total.setVisibility(View.GONE);
                    }
            }
        }else {
            callTaxEntitlementService();
            callTaxAnalysisService();
        }
        relative_center_home.setOnClickListener(this);
        relative_left_arrow.setOnClickListener(this);
        fab_id.setOnClickListener(this);
        return view;
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
                    taxAnalysistModel = response.body();
                    if (taxAnalysistModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        showAvailedValuesValidationpart();
                        showSavedAmount();
                    }
                    else
                        {
                            mlayout_pending.setVisibility(View.GONE);
                        mlayout_availed.setVisibility(View.GONE);
                        mlayout_saveing_total.setVisibility(View.GONE);
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<TaxCashFlowModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
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
//                    Log.i("Tax Analysis","Tax Analysis user id"+UtileKit.getPersistedPurplePathPref("user_id"));
                    UtileKit.dismisssSpinnerDialog();

                    entitlementInfoModel = response.body();

                    if (entitlementInfoModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        showEntitlementInfo();
                    }
                    else {
                        mlayout_entitlement.setVisibility(View.GONE);
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







    private void showSavedAmount() {
        try {
            line1_Text.setText("You have saved");
            String str = taxAnalysistModel.getData().getUser_tax().get(0).getTotal_savings();
            if(str.equalsIgnoreCase("0")){
                mlayout_saveing_total.setVisibility(View.GONE);


            }else {
                str = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(str));
                SpannableString styledText = new SpannableString(str);
                styledText.setSpan(new RelativeSizeSpan(2f), 0, str.length(), 0);
                line2_Text.setText(styledText);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String mEightyCValue,mEightyCCDValue,mEightyCCGValue,mEightyGGValue,mEightyDValue,mTRAValue;
    private void showAvailedValuesValidationpart(){
        String value;
        int userTaxSize=taxAnalysistModel.getData().getUser_tax().size();
        if (userTaxSize != 0) {
            int size = taxAnalysistModel.getData().getUser_tax().get(0).getSections().size();
            for (int i = 0; i < size; i++) {
                try {
                    if (taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getName().equalsIgnoreCase("80C")) {
                        mEightyCValue = taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getVal();
                    } else if (taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getName().equalsIgnoreCase("80CCD")) {
                        mEightyCCDValue = taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getVal();
                    } else if (taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getName().equalsIgnoreCase("80CCG")) {
                        mEightyCCGValue = taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getVal();
                    } else if (taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getName().equalsIgnoreCase("80D")) {
                        mEightyDValue = taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getVal();
                    } else if (taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getName().equalsIgnoreCase("80GG")) {
                        mEightyGGValue = taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getVal();
                    } else if (taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getName().equalsIgnoreCase("TRA")) {
                        mTRAValue = taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getVal();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(UtileKit.validateObjectValuesAndCheckZero(mEightyCValue)&&UtileKit.validateObjectValuesAndCheckZero(mEightyCCDValue)
          && UtileKit.validateObjectValuesAndCheckZero(mEightyCCGValue)&& UtileKit.validateObjectValuesAndCheckZero(mEightyDValue)
         && UtileKit.validateObjectValuesAndCheckZero(mEightyGGValue) && UtileKit.validateObjectValuesAndCheckZero(mTRAValue)){
                showAvailedValues();
            }else{
                mlayout_availed.setVisibility(View.GONE);
                mlayout_pending.setVisibility(View.GONE);
            }
        }
    }

    private void showAvailedValues() {
        String value;
        int userTaxSize=taxAnalysistModel.getData().getUser_tax().size();
        if (userTaxSize != 0) {
            int size = taxAnalysistModel.getData().getUser_tax().get(0).getSections().size();
            for (int i = 0; i < size; i++) {

                if (taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getName().equalsIgnoreCase("80C")) {
                    try {
                        value = taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getVal();
                        setValuesAndShowLayout(availed_layout_80C, availed_value_80C, value);
                        setValuesAndShowLayout(pending_layout_80C, pending_value_80C, ((int)(Float.parseFloat(map.get("80C")) - Float.parseFloat(value))) + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getName().equalsIgnoreCase("80CCD")) {
                    try {
                        value = taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getVal();
                        setValuesAndShowLayout(availed_layout_80CCD, availed_value_80CCD, value);
                        setValuesAndShowLayout(pending_layout_80CCD, pending_value_80CCD, ((int)(Float.parseFloat(map.get("80CCD")) - Float.parseFloat(value))) + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getName().equalsIgnoreCase("80CCG")) {
                    try {
                        value = taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getVal();
                        setValuesAndShowLayout(availed_layout_80CCG, availed_value_80CCG, value);
                        setValuesAndShowLayout(pending_layout_80CCG, pending_value_80CCG,((int)( Float.parseFloat(map.get("80CCG")) - Float.parseFloat(value))) + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getName().equalsIgnoreCase("80D")) {
                    try {
                        value = taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getVal();
                        setValuesAndShowLayout(availed_layout_80D, availed_value_80D, value);
                        setValuesAndShowLayout(pending_layout_80D, pending_value_80D,((int)( Float.parseFloat(map.get("80D")) - Float.parseFloat(value))) + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getName().equalsIgnoreCase("80GG")) {
                    try {
                        value = taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getVal();
                        setValuesAndShowLayout(availed_layout_80GG, availed_value_80GG, value);
                        setValuesAndShowLayout(pending_layout_80GG, pending_value_80GG, ((int)(Float.parseFloat(map.get("80GG")) - Float.parseFloat(value))) + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getName().equalsIgnoreCase("TRA")) {
                    try {
                        value = taxAnalysistModel.getData().getUser_tax().get(0).getSections().get(i).getVal();
                        setValuesAndShowLayout(availed_layout_TRA, availed_value_TRA, value);
                        setValuesAndShowLayout(pending_layout_TRA, pending_value_TRA, ((int)(Float.parseFloat(map.get("TRA")) - Float.parseFloat(value))) + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }

    private void showEntitlementInfo() {
        String value;
        int size = entitlementInfoModel.getData().getTax_plan().size();
        for (int i = 0; i < size; i++) {
            if (entitlementInfoModel.getData().getTax_plan().get(i).getSection().equalsIgnoreCase("80C")) {
                value = entitlementInfoModel.getData().getTax_plan().get(i).getTotal_limit();
                map.put("80C", value);
                setValuesAndShowLayout(layout_80C, value_80C, value);
            } else if (entitlementInfoModel.getData().getTax_plan().get(i).getSection().equalsIgnoreCase("80CCD")) {
                value = entitlementInfoModel.getData().getTax_plan().get(i).getTotal_limit();
                map.put("80CCD", value);
                setValuesAndShowLayout(layout_80CCD, value_80CCD, value);
            } else if (entitlementInfoModel.getData().getTax_plan().get(i).getSection().equalsIgnoreCase("80CCG")) {
                value = entitlementInfoModel.getData().getTax_plan().get(i).getTotal_limit();
                map.put("80CCG", value);
                setValuesAndShowLayout(layout_80CCG, value_80CCG, value);
            } else if (entitlementInfoModel.getData().getTax_plan().get(i).getSection().equalsIgnoreCase("80D")) {
                value = entitlementInfoModel.getData().getTax_plan().get(i).getTotal_limit();
                map.put("80D", value);
                setValuesAndShowLayout(layout_80D, value_80D, value);
            } else if (entitlementInfoModel.getData().getTax_plan().get(i).getSection().equalsIgnoreCase("80GG")) {
                value = entitlementInfoModel.getData().getTax_plan().get(i).getTotal_limit();
                map.put("80GG", value);
                setValuesAndShowLayout(layout_80GG, value_80GG, value);
            } else if (entitlementInfoModel.getData().getTax_plan().get(i).getSection().equalsIgnoreCase("TRA")) {
                value = entitlementInfoModel.getData().getTax_plan().get(i).getTotal_limit();
                map.put("TRA", value);
                setValuesAndShowLayout(layout_TRA, value_TRA, value);
            }
        }

    }

    private void setValuesAndShowLayout(RelativeLayout layout, TextView textView, String value) {
        layout.setVisibility(View.VISIBLE);
        textView.setText(value);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;
            case R.id.fab_id:
            {
//                addFragmenttoStack(new InsuranceDetailsFragment());
            }
            break;
        }
    }
}
*/

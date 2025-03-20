package com.purplepath.purplepath.taxprompt;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.Space;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.purplepath.purplepath.taxfiling.TaxFilingViewPager.TaxFilingChatViewPager;
import com.purplepath.purplepath.taxfiling.addsessionmodel.AddSessionModel;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.Ded_by_prods;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.TaxPromptNewModel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Murugesan on 1/22/18.
 */

public class TaxPromptSummary extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.savingsTopText)
    TextView savingsTopText;

    @BindView(R.id.entitled_value)
    TextView entitled_value;

    @BindView(R.id.availedTopText)
    TextView availedTopText;

    @BindView(R.id.availed_value)
    TextView availed_value;

    @BindView(R.id.pendingTopText)
    TextView pendingTopText;

    @BindView(R.id.pending_value)
    TextView pending_value;

    private TaxPromptNewModel taxPromptNewModel;

    private OnActivityBackPressedListener mCallBackListener;

    RelativeLayout relative_left_arrow, relative_center_home;

    private Context mContext;

    HashSet<String> taxproduct_hashset = new HashSet<String>();

    private BigInteger availed = BigInteger.ZERO;

    private BigInteger str_availed = BigInteger.ZERO;

    private BigInteger total_sub_all = BigInteger.ZERO;

    BigInteger entitle = BigInteger.ZERO;

    BigInteger str_entitle = BigInteger.ZERO;

    HashMap<String, ArrayList<Ded_by_prods>> mfilterarrayEntitlement = new HashMap<>();

    HashMap<String, BigInteger> mEntitlementTotalValue = new HashMap<>();

    BigInteger sub_entitlement_availed = BigInteger.ZERO;

    private LinearLayout see_more_layout;

    private LinearLayout contact_layout;

    private int STORAGE_PERMISSION_CODE = 23;

    private String bangaloreno = "+918884400678";

    private TextView summary_text;

    BigInteger entitle_greater = BigInteger.ZERO;

    ArrayList<String> arrayListavail = new ArrayList<String>();

    private FloatingActionButton rating_floating_button;


    private TextView mtax_value, mincome_value, mexemption_value, mgross_income_value,
            mdeduction_value, mrebates_value, mtotal_taxable_income_value, mincome_after_tax_value;

    private BigInteger income_value = BigInteger.ZERO;

    private BigInteger excemption_value = BigInteger.ZERO;

    private BigInteger gross_income_value = BigInteger.ZERO;

    private BigInteger deduction_value = BigInteger.ZERO;

    private BigInteger rebates_value = BigInteger.ZERO;

    private BigInteger tax_value = BigInteger.ZERO;

    private BigInteger tax_refund = BigInteger.ZERO;

    private BigInteger total_tax_values = BigInteger.ZERO;

    private BigInteger after_tax = BigInteger.ZERO;

    private Space card_space;

    private TextView tax_prepaid;

    private TextView mLiability_value;

    private LinearLayout mlayout_you_need_pay, layout_refund_payable;

    private BigInteger tax_prepaids = BigInteger.ZERO;

    private TextView txt_tax_values;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBackListener = (OnActivityBackPressedListener) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tax_prompt_summary1, container, false);
        mContext = getContext();
        ButterKnife.bind(this, view);

        mCallBackListener.setActionBarTitle("Tax Plan Summary");
        relative_center_home = view.findViewById(R.id.relative_center_home);
        relative_left_arrow = view.findViewById(R.id.relative_left_arrow);

        relative_center_home.setOnClickListener(this);
        relative_left_arrow.setOnClickListener(this);

        see_more_layout = view.findViewById(R.id.see_more_layout);
        see_more_layout.setOnClickListener(this);

        contact_layout = view.findViewById(R.id.contact_layout);
        contact_layout.setOnClickListener(this);

        summary_text = view.findViewById(R.id.summary_text);


        mincome_value = view.findViewById(R.id.income_value);
        mexemption_value = view.findViewById(R.id.exemption_value);
        mgross_income_value = view.findViewById(R.id.gross_income_value);
        mdeduction_value = view.findViewById(R.id.deduction_value);
        mrebates_value = view.findViewById(R.id.rebates_value);

        mLiability_value = view.findViewById(R.id.liability_value);


        mtotal_taxable_income_value = view.findViewById(R.id.total_taxable_income_value);
        mincome_after_tax_value = view.findViewById(R.id.income_after_tax_value);

        rating_floating_button = view.findViewById(R.id.rating_floating_button);
        rating_floating_button.setOnClickListener(this);

        card_space = view.findViewById(R.id.card_space);

        tax_prepaid = view.findViewById(R.id.tax_prepaid);
        mtax_value = view.findViewById(R.id.tax_values);
        mlayout_you_need_pay = view.findViewById(R.id.layout_you_need_pay);

        txt_tax_values = view.findViewById(R.id.txt_tax_values);

        layout_refund_payable = view.findViewById(R.id.layout_refund_payable);


        setHasOptionsMenu(true);

        callTaxAnalysisService();


        return view;
    }


    private void callTaxAnalysisService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String finYr = String.valueOf(year-1);
        Call<TaxPromptNewModel> call = webServiceObj.callTaxPromptService_new(UtileKit.getPersistedPurplePathPref("user_id"), "FY"+finYr);
        call.enqueue(new Callback<TaxPromptNewModel>() {
            @Override
            public void onResponse(Call<TaxPromptNewModel> call, Response<TaxPromptNewModel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    taxPromptNewModel = response.body();

                    if (taxPromptNewModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {


                        showFunnelValues(taxPromptNewModel);

                        groupingSection(taxPromptNewModel);


                        if (taxPromptNewModel.getData().getSummary_text() != null) {

                            String summary_flag = taxPromptNewModel.getData().getSummary_flag();
                            String summary_txt = taxPromptNewModel.getData().getSummary_text();
                            summary_text.setText(summary_txt);

                            if (summary_flag.equalsIgnoreCase("Y")) {
                                summary_text.setVisibility(View.VISIBLE);
                                contact_layout.setVisibility(View.VISIBLE);

                                // card_space.setVisibility(View.VISIBLE);

                            } else {
                                summary_text.setVisibility(View.GONE);
                                contact_layout.setVisibility(View.GONE);

                                //  card_space.setVisibility(View.GONE);
                            }
                        }


                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxPromptNewModel> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void showFunnelValues(TaxPromptNewModel taxPromptNewModel) {


        if (taxPromptNewModel.getData().getTax_calc().getTotal_tax_payable() != null) {
            tax_value = new BigInteger(taxPromptNewModel.getData().getTax_calc().getTotal_tax_payable());
        }

        if (taxPromptNewModel.getData().getTax_calc().getTotal_tax_refund() != null) {
            tax_refund = new BigInteger(taxPromptNewModel.getData().getTax_calc().getTotal_tax_refund());
        }

        if (taxPromptNewModel.getData().getTax_calc().getPrepaid_tax() != null) {
            tax_prepaids = new BigInteger(taxPromptNewModel.getData().getTax_calc().getPrepaid_tax());
        }

        tax_prepaid.setText("₹ " + UtileKit.formatedNumbers(tax_prepaids));

        String tax_flag = taxPromptNewModel.getData().getTax_calc().getTax_flag();

        if (tax_flag.equalsIgnoreCase("Receive")) {

            //mtxt_you_need.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            // layout_refund_payable.setBackgroundColor(ContextCompat.getColor(mContext, R.color.tax_table_background));

            layout_refund_payable.setBackgroundResource(R.drawable.tax_summary_green_back);

            txt_tax_values.setText("Tax Refund");
            mtax_value.setText("₹ " + UtileKit.formatedNumbers(tax_refund));


        } else {

            layout_refund_payable.setBackgroundResource(R.drawable.tax_amber_back);
            // layout_refund_payable.setBackgroundColor(ContextCompat.getColor(mContext, R.color.amber_color));
            txt_tax_values.setText("Tax Payable");

            int val = tax_value.compareTo(BigInteger.ONE);
            Log.d("Compare Val:", val + "");
            if (val == 0) {
                mtax_value.setText("₹ " + UtileKit.formatedNumbers(BigInteger.ONE));

            } else {
                mtax_value.setText("₹ " + UtileKit.formatedNumbers(tax_value.subtract(tax_prepaids)));

            }


        }


        if (taxPromptNewModel.getData().getTax_calc().getTot_income_before_gti() != null) {
            income_value = new BigInteger(taxPromptNewModel.getData().getTax_calc().getTot_income_before_gti());
        }

        if (taxPromptNewModel.getData().getTax_calc().getExemptions() != null) {
            excemption_value = new BigInteger(taxPromptNewModel.getData().getTax_calc().getExemptions());
        }

        if (taxPromptNewModel.getData().getTax_calc().getGti_income() != null) {
            gross_income_value = new BigInteger(taxPromptNewModel.getData().getTax_calc().getGti_income());
        }
        if (taxPromptNewModel.getData().getTax_calc().getAllowed_deduction() != null) {
            deduction_value = new BigInteger(taxPromptNewModel.getData().getTax_calc().getAllowed_deduction());
        }

        if (taxPromptNewModel.getData().getTax_calc().getRebates() != null) {
            rebates_value = new BigInteger(taxPromptNewModel.getData().getTax_calc().getRebates());
        }


        mincome_value.setText("₹ " + UtileKit.formatedNumbers(income_value));
        mexemption_value.setText("₹ " + UtileKit.formatedNumbers(excemption_value));
        mgross_income_value.setText("₹ " + UtileKit.formatedNumbers(gross_income_value));
        mdeduction_value.setText("₹ " + UtileKit.formatedNumbers(deduction_value));
        mrebates_value.setText("₹ " + UtileKit.formatedNumbers(rebates_value));

        mLiability_value.setText("₹ " + UtileKit.formatedNumbers(tax_value));


        if (taxPromptNewModel.getData().getTax_calc().getTaxable_income() != null) {
            total_tax_values = new BigInteger(taxPromptNewModel.getData().getTax_calc().getTaxable_income());
        }

        mtotal_taxable_income_value.setText("₹ " + UtileKit.formatedNumbers(total_tax_values));

        after_tax = total_tax_values.subtract(tax_value);

        mincome_after_tax_value.setText("₹ " + UtileKit.formatedNumbers(after_tax));

    }


    private void groupingSection(TaxPromptNewModel taxCashFlowModel) {

        if (taxCashFlowModel.getData().getDed_by_prod() != null) {
            int length = taxCashFlowModel.getData().getDed_by_prod().size();

            String diability_flag = taxCashFlowModel.getData().getDisability_flag();

            for (int i = 0; i < length; i++) {
                taxproduct_hashset.add(taxCashFlowModel.getData().getDed_by_prod().get(i).getTax_section());
            }

            //  ArrayList<String> arrayList = new ArrayList<String>(taxproduct_hashset);

            arrayListavail = new ArrayList<String>(taxproduct_hashset);

            //  ArrayList< ArrayList<Ded_by_prods>> mfilterarray = new ArrayList<>();

            for (int j = 0; j < arrayListavail.size(); j++) {
                String str_obj = arrayListavail.get(j);
                if (str_obj != null) {
                    ArrayList<Ded_by_prods> taxsection_heading = new ArrayList<Ded_by_prods>();

                    for (int k = 0; k < length; k++) {
                        if (str_obj.equals(taxCashFlowModel.getData().getDed_by_prod().get(k).getTax_section())) {
                            // taxsection_heading.add(taxCashFlowModel.getData().getDed_by_prod().get(k));

                            String disability_inner_array = taxCashFlowModel.getData().getDed_by_prod().get(k).getDisability_flag();


                            try {
                                if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("Y"))) {
                                    taxsection_heading.add(taxCashFlowModel.getData().getDed_by_prod().get(k));
                                } else if ((disability_inner_array.equalsIgnoreCase("Y") && diability_flag.equalsIgnoreCase("Y"))) {
                                    taxsection_heading.add(taxCashFlowModel.getData().getDed_by_prod().get(k));
                                } else if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("N"))) {
                                    taxsection_heading.add(taxCashFlowModel.getData().getDed_by_prod().get(k));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    mfilterarrayEntitlement.put(str_obj, taxsection_heading);
                }
            }
            filterSection(mfilterarrayEntitlement);
     }
    }
    private void filterSection(HashMap<String, ArrayList<Ded_by_prods>> mfilterarray) {

        entitle_greater = BigInteger.ZERO;
        int length = mfilterarray.size();
        //for(String key:mfilterarray.keySet()){
        availed = BigInteger.ZERO;

        BigInteger mEntitle_greater = BigInteger.ZERO;

        for (String key : arrayListavail) {

            int innerlength = mfilterarray.get(key).size();

            entitle = BigInteger.ZERO;

            mEntitle_greater = BigInteger.ZERO;
            for (int j = 0; j < innerlength; j++) {

                //entitle
                if (mfilterarray.get(key).get(j).getEntitled() != null) {
                    entitle = (new BigInteger(mfilterarray.get(key).get(j).getEntitled()));
                }
                //availed
                if (mfilterarray.get(key).get(j).getAllowed_value() != null) {
                    availed = availed.add(new BigInteger(mfilterarray.get(key).get(j).getAllowed_value()));
                }
                //Entitle greater
                if (mfilterarray.get(key).get(j).getEntitled() != null) {
                    //entitle_greater = entitle_greater.add(new BigInteger(mfilterarray.get(key).get(j).getEntitled()));
                    mEntitle_greater = (new BigInteger(mfilterarray.get(key).get(j).getEntitled()));

                }

            }
            mEntitlementTotalValue.put(key, entitle);
            Log.d("mEntitlementTotalValsss", "mEntitlementTotalValuessss" + mEntitlementTotalValue);

            entitle_greater = entitle_greater.add(mEntitle_greater);

            Log.d("entitle_greatersss" + key, "" + key + entitle + "--->availed" + availed + "entitle_greater" + entitle_greater);


        }

        str_entitle = BigInteger.ZERO;
        sub_entitlement_availed = BigInteger.ZERO;
        for (String key1 : mEntitlementTotalValue.keySet()) {

            str_entitle = str_entitle.add(mEntitlementTotalValue.get(key1));
        }
        entitled_value.setText("₹ " + String.valueOf(UtileKit.formatedNumbers(str_entitle)));


        str_availed = new BigInteger(taxPromptNewModel.getData().getTax_calc().getAllowed_deduction());
        availed_value.setText("₹ " + String.valueOf(UtileKit.formatedNumbers(str_availed)));


        sub_entitlement_availed = str_entitle.subtract(str_availed);
        pending_value.setText("₹ " + String.valueOf(UtileKit.formatedNumbers(sub_entitlement_availed)));


      /*  int checkLess;
        checkLess = availed.compareTo(entitle_greater);

        if(checkLess == 1) {
            sub_entitlement_availed=str_entitle.subtract(entitle_greater);
            availed_value.setText("₹ "+String.valueOf(UtileKit.formatedNumbers(entitle_greater)));
            pending_value.setText("₹ "+String.valueOf(UtileKit.formatedNumbers(sub_entitlement_availed)));
        }
        else if(checkLess == 0){
            sub_entitlement_availed=str_entitle.subtract(entitle_greater);
            availed_value.setText("₹ "+String.valueOf(UtileKit.formatedNumbers(entitle_greater)));
            pending_value.setText("₹ "+String.valueOf(UtileKit.formatedNumbers(sub_entitlement_availed)));
        }else {
            sub_entitlement_availed=str_entitle.subtract(availed);
            availed_value.setText("₹ "+String.valueOf(UtileKit.formatedNumbers(availed)));
            pending_value.setText("₹ "+String.valueOf(UtileKit.formatedNumbers(sub_entitlement_availed)));
        }*/



      /*  str_entitle=BigInteger.ZERO;
        sub_entitlement_availed =BigInteger.ZERO;

        for(String key1:mEntitlementTotalValue.keySet()){
            str_entitle=str_entitle.add(mEntitlementTotalValue.get(key1));
        }

        sub_entitlement_availed=str_entitle.subtract(availed);

        entitled_value.setText("₹ "+String.valueOf(UtileKit.formatedNumbers(str_entitle)));
        availed_value.setText("₹ "+String.valueOf(UtileKit.formatedNumbers(availed)));
        pending_value.setText("₹ "+String.valueOf(UtileKit.formatedNumbers(sub_entitlement_availed)));*/
    }


    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.CALL_PHONE)) {
        }
        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

            case R.id.see_more_layout:

                addFragmenttoStack(new TaxPromptShowDetailView());

                break;

            case R.id.contact_layout:
                if (isReadStorageAllowed()) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + bangaloreno));
                    startActivity(callIntent);
                } else {
                    requestStoragePermission();
                }
                break;
            case R.id.rating_floating_button:

                String corresponding_table="users_tax_file_page_visited_status",
                        field_name="tax_summary",
                        field_value="Y";
                AddTaxfileSessionService(corresponding_table,field_name,field_value);

                /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.finobot.finobot&hl=en"));
                startActivity(intent);*/

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

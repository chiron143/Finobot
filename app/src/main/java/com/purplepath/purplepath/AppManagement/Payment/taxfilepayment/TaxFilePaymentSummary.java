package com.purplepath.purplepath.AppManagement.Payment.taxfilepayment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Payment.gettaxpaymentmodel.TaxPaymentModels;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.TaxFileAdditionalChartConversation;
import com.purplepath.purplepath.taxfiling.TaxFilePlanningChartConversation;
import com.purplepath.purplepath.taxfiling.TaxFilingHousePropertyConversation;
import com.purplepath.purplepath.taxfiling.TaxFilingSummary;
import com.purplepath.purplepath.taxfiling.TaxPlanningViewPager.TaxPlanningChatViewPager;
import com.purplepath.purplepath.taxfiling.getUserStatusModel.UserStatusModel;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.TaxPromptNewModel;
import com.purplepath.purplepath.taxprompt.model.TaxPromptModel;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;

/**
 * Created by pravinr on 4/20/18.
 */

public class TaxFilePaymentSummary extends BaseFragment implements View.OnClickListener{

    TaxPaymentModels mPaymentCheckModel;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    OnActivityBackPressedListener backPressedListener;

    private LinearLayout tax_filing_click;

    private String successMessage="";

    private String card_amount="";

    private LinearLayout layout_merchentid,layout_mspreference,layour_amount;

    private Context mContext;

    private String sessionAgreementScreen="",form16Session="",nonForm16Session="",
            planamountSession="",form16_uploadSession="";

    TaxPromptModel taxPromptModel;

    private String housePropertyFlag="";

    public static TaxFilePaymentSummary newInstance(TaxPaymentModels mPaymentCheckModel, String card_amount) {

        Bundle args = new Bundle();
        args.putSerializable("mPaymentCheckModel",mPaymentCheckModel);
        TaxFilePaymentSummary fragment = new TaxFilePaymentSummary();

        if (card_amount != null) {
            args.putSerializable("card_amount", card_amount);
        }

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        try{
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){
            e.printStackTrace();
        }

        if(getArguments().containsKey("mPaymentCheckModel"))
            mPaymentCheckModel=(TaxPaymentModels)getArguments().get("mPaymentCheckModel");
        backPressedListener= (OnActivityBackPressedListener) getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_paymeny_view, container, false);
        setHasOptionsMenu(true);

        if(getArguments().containsKey("card_amount")) {
            card_amount = getArguments().getString("card_amount");
        }

        layout_merchentid=(LinearLayout)view.findViewById(R.id.layout_merchentid);
        layout_mspreference=(LinearLayout)view.findViewById(R.id.layout_mspreference);
        layour_amount=(LinearLayout)view.findViewById(R.id.layour_amount);

        tax_filing_click = (LinearLayout) view.findViewById(R.id.tax_filing_click);
        tax_filing_click.setOnClickListener(this);

        callGetTaxfileUserStatus();

        return view;
    }
    private void callGetTaxfileUserStatus() {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<UserStatusModel> call = webServiceObj.callGetTaxfileUserStatus(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<UserStatusModel>() {
            @Override
            public void onResponse(Call<UserStatusModel> call, Response<UserStatusModel> response) {
                UtileKit.dismisssSpinnerDialog();
                UserStatusModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        sessionMaintanance(userStatusModel);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<UserStatusModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }

    private void sessionMaintanance(UserStatusModel userStatusModel) {


        if(!userStatusModel.getData().getPage_visited_array().isEmpty()) {

            nonForm16Session = userStatusModel.getData().getPage_visited_array().get(0).getNon_form16();
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        CustomTextView merchantOrderId= view.findViewById(R.id.merchantOrder_id);
        CustomTextView merchantReferceCode= view.findViewById(R.id.mspreference_id);
        CustomTextView resultmsg_id= view.findViewById(R.id.resultmsg_id);
        DefaultCurrencyTextView current_accout= view.findViewById(R.id.current_accout_id);
        merchantOrderId.setText(mPaymentCheckModel.getData().getUser_tax_file_payment_details().getMerchant_order_id());
        merchantReferceCode.setText(mPaymentCheckModel.getData().getUser_tax_file_payment_details().getMsp_ref_id());
        current_accout.setText(mPaymentCheckModel.getData().getUser_tax_file_payment_details().getAmount());

        ImageView success_image=view.findViewById(R.id.success_image);



        successMessage=mPaymentCheckModel.getData().getPayment_made();

        UtileKit.persistingPurplePathPref("taxFilePaymentSuccessMode", mPaymentCheckModel.getData().getPayment_made());

        if(mPaymentCheckModel.getData().getUser_tax_file_payment_details().getTransaction_status().equalsIgnoreCase("Success")){
            resultmsg_id.setText("Payment Sucessful");
            UtileKit.setSvgImageviewDrawable(success_image,mContext,R.drawable.ic_upload_success);
        }
        else if(mPaymentCheckModel.getData().getUser_tax_file_payment_details().getTransaction_status().equalsIgnoreCase("Failure")){
            resultmsg_id.setText("Your Payment is Failure Please try again");
            UtileKit.setSvgImageviewDrawable(success_image,mContext,R.drawable.ic_payment_failed);
            layout_merchentid.setVisibility(View.GONE);
            layout_mspreference.setVisibility(View.GONE);
            layour_amount.setVisibility(View.GONE);
        }
        else if(mPaymentCheckModel.getData().getUser_tax_file_payment_details().getTransaction_status().equalsIgnoreCase("Cancelled")){
            resultmsg_id.setText("Your Payment is Cancelled Please try again");

            layout_merchentid.setVisibility(View.GONE);
            layout_mspreference.setVisibility(View.GONE);
            layour_amount.setVisibility(View.GONE);
        }

        callGetTaxPromptHouseService();
    }
    public void callGetTaxPromptHouseService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxPromptModel> call = webServiceObj.callInitialGetHouseTaxFileService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxPromptModel>() {
            @Override
            public void onResponse(Call<TaxPromptModel> call, Response<TaxPromptModel> response) {
                UtileKit.dismisssSpinnerDialog();
                taxPromptModel = response.body();
                if (taxPromptModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    housePropertyFlag=taxPromptModel.getData().getProperty_status();

                }
            }

            @Override
            public void onFailure(Call<TaxPromptModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_taxsummary_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "26");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
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
            case R.id.tax_filing_click:


                if(mPaymentCheckModel.getData().getUser_tax_file_payment_details().getTransaction_status().equalsIgnoreCase("Success")){


                    if(nonForm16Session.equalsIgnoreCase("Y")){

                        // addFragmenttoStack(new TaxFilePlanningChartConversation());
                         addFragmenttoStack(new TaxPlanningChatViewPager());

                    }else if(card_amount.equalsIgnoreCase("199")){

                        if(housePropertyFlag.equalsIgnoreCase("Y")){
                            addFragmenttoStack(new TaxFilingHousePropertyConversation());
                        }else {

                            callTaxAnalysisService();
                            //addFragmenttoStack(new TaxFilingSummary());
                        }

                    }else {
                        addFragmenttoStack(new TaxFileAdditionalChartConversation());
                    }

                }
                else if(mPaymentCheckModel.getData().getUser_tax_file_payment_details().getTransaction_status().equalsIgnoreCase("Failure")){
                    addFragmenttoStack(TaxFilePayment.newInstance(card_amount));
                }
                else if(mPaymentCheckModel.getData().getUser_tax_file_payment_details().getTransaction_status().equalsIgnoreCase("Cancelled")){
                    addFragmenttoStack(TaxFilePayment.newInstance(card_amount));
                }



                break;

        }

    }
    //Once we got file upload success in this service has to use for prabhu calculation
    private void callTaxAnalysisService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String finYr = String.valueOf(year-1);
        Call<TaxPromptNewModel> call = webServiceObj.callTaxPromptService(UtileKit.getPersistedPurplePathPref("user_id"), "FY"+finYr);
        call.enqueue(new Callback<TaxPromptNewModel>() {
            @Override
            public void onResponse(Call<TaxPromptNewModel> call, Response<TaxPromptNewModel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    TaxPromptNewModel taxPromptNewModel = response.body();

                    if (taxPromptNewModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        addFragmenttoStack(new TaxFilingSummary());
                    }

                } catch (Exception e) {
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

}

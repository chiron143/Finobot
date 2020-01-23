package com.purplepath.purplepath.AppManagement.Payment.taxfilepayment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Payment.gettaxpaymentmodel.TaxPaymentModels;
import com.purplepath.purplepath.AppManagement.privacy.PrivacyPolicyFragment;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.TaxFilingInitialConversation;
import com.purplepath.purplepath.taxfiling.addsessionmodel.AddSessionModel;

import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;
import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.tax_payment_respone;

/**
 * Created by pravinr on 4/20/18.
 */

public class TaxFilePayment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private WebView webview;

    LinearLayout bottom_bar_layout;

    private String card_amount="";


    //TaxFiling Payment
    public static TaxFilePayment newInstance(String card_amount) {
        TaxFilePayment paymentPayUmoney = new TaxFilePayment();
        Bundle args = new Bundle();
        if (card_amount != null) {
            args.putSerializable("card_amount", card_amount);
        }
        paymentPayUmoney.setArguments(args);
        return paymentPayUmoney;
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
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
            backPressedListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        if(getArguments().containsKey("card_amount")) {
            card_amount = getArguments().getString("card_amount");
        }

        View view=inflater.inflate(R.layout.fragment_taxfilingpayment, container, false);
        backPressedListener.setActionBarTitle("Payment");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        bottom_bar_layout= view.findViewById(R.id.bottom_bar_layout);




        webview = view.findViewById(R.id.WebView);
        webview.setWebViewClient(new MyBrowser());
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        try {
            String urls = ServiceGenerator.tax_payment_request;
            String postDatas = "&user_id=" + URLEncoder.encode(UtileKit.getPersistedPurplePathPref("user_id").concat(""), "UTF-8")
                            + "&amt=" + URLEncoder.encode(card_amount, "UTF-8");
            webview.postUrl(urls, postDatas.getBytes());

        }catch (Exception e) {
            e.printStackTrace();
        }

        AddTaxfileSessionService("users_tax_file_page_visited_status","payment_page", "Y");

        return view;
    }


    private void AddTaxfileSessionService(String corresponding_table, String field_name, String field_value) {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddSessionModel> call = webServiceObj.AddTaxfileSessionService(UtileKit.getPersistedPurplePathPref("user_id")
                , corresponding_table, field_name, field_value);
        call.enqueue(new Callback<AddSessionModel>() {
            @Override
            public void onResponse(Call<AddSessionModel> call, Response<AddSessionModel> response) {
                UtileKit.dismisssSpinnerDialog();
                AddSessionModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

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

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_taxpayment_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "25");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Log.e("Test->onPageStarted","Url"+url);
            UtileKit.showSpinnerDialog(getContext(),false);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("Test->shouldUrlLoading","Url"+url);
            view.loadUrl(url);
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.e("Teston->PageFinished","Url"+url);
          //  view.scrollTo(0,0);
            UtileKit.dismisssSpinnerDialog();
            if(url.equalsIgnoreCase(tax_payment_respone))
            {
                callTaxProductService();
            }
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
    private void callTaxProductService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxPaymentModels> call = webServiceObj.get_tax_payment_details_by_user(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxPaymentModels>() {
            @Override
            public void onResponse(Call<TaxPaymentModels> call, Response<TaxPaymentModels> response) {
                UtileKit.dismisssSpinnerDialog();
                TaxPaymentModels paymentCheckModel= response.body();
                try {
                    if (paymentCheckModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

//                        Log.e("Sucess","Output getMerchant_order_id"+paymentCheckModel.getData().
//                                getUser_tax_file_payment_details().getMerchant_order_id());

                        addFragmenttoStack(TaxFilePaymentSummary.newInstance(paymentCheckModel,card_amount));


                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<TaxPaymentModels> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

}
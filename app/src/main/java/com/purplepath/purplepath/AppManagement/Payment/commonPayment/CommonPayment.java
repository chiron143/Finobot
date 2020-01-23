package com.purplepath.purplepath.AppManagement.Payment.commonPayment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.purplepath.purplepath.AppManagement.Payment.getcommonpaymentmodel.CommonPaymentModel;
import com.purplepath.purplepath.AppManagement.privacy.PrivacyPolicyFragment;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.common_payment_respone;

/**
 * Created by pravinr on 4/20/18.
 */

public class CommonPayment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private WebView webview;

    //Boolean isSignUp=false;

    LinearLayout bottom_bar_layout;

    private String final_amounts="",str_paid_type="",str_validity="";



    //Payment for all
    public static CommonPayment newInstance(String str_basic_type, String final_amounts, String validity) {
        CommonPayment paymentPayUmoney = new CommonPayment();
        Bundle args = new Bundle();
        if (final_amounts != null) {
            args.putSerializable("final_amounts", final_amounts);
        }
        if(str_basic_type!=null){
            args.putSerializable("paid_type",str_basic_type);
        }
        if (validity != null) {
            args.putSerializable("validity_year", validity);
        }

        paymentPayUmoney.setArguments(args);
        return paymentPayUmoney;
    }






    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
            backPressedListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Payment for all
        if(getArguments().containsKey("final_amounts")) {
            final_amounts = getArguments().getString("final_amounts");
        }
        if(getArguments().containsKey("paid_type")){
            str_paid_type=getArguments().getString("paid_type");
        }
        if(getArguments().containsKey("validity_year")){
            str_validity=getArguments().getString("validity_year");
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

        try{
         //   Bundle args=getArguments();

//            if(args!=null){
//                if(args.containsKey("Flag")) {
//                    isSignUp = getArguments().getBoolean("Flag");
//                }
//            }
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){
            e.printStackTrace();
        }
//        if(!isSignUp){
//            bottom_bar_layout.setVisibility(View.GONE);
//        }
//        else {
//            bottom_bar_layout.setVisibility(View.VISIBLE);
//        }


        webview = view.findViewById(R.id.WebView);
        webview.setWebViewClient(new MyBrowser());
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        try {

                try {
                    String url = ServiceGenerator.common_payment_request;
                    String postData =
//                    "QPayID=" + URLEncoder.encode("hyrateapi", "UTF-8")
//                    + "&QPayPWD=" + URLEncoder.encode("api#1234", "UTF-8")
//                    + "&TransactionType=" + URLEncoder.encode("PURCHASE", "UTF-8")
                            "&user_id=" + URLEncoder.encode(UtileKit.getPersistedPurplePathPref("user_id").concat(""), "UTF-8")
//                    + "&Currency=" + URLEncoder.encode("INR", "UTF-8")
//                    + "&Mode=" + URLEncoder.encode("TEST", "UTF-8")
//                    + "&PaymentPageRequired=" + URLEncoder.encode("Y", "UTF-8")
//                    + "&Paymentoption=" + URLEncoder.encode("C,D", "UTF-8")
//                    + "&name=" + URLEncoder.encode("test", "UTF-8")
//                    + "&address=" + URLEncoder.encode("test", "UTF-8")
//                    + "&city=" + URLEncoder.encode("test", "UTF-8")
//                    + "&state=" + URLEncoder.encode("test", "UTF-8")
//                    + "&country=" + URLEncoder.encode("test", "UTF-8")
//                    + "&postal_code=" + URLEncoder.encode("12345", "UTF-8")
//                    + "&phone=" + URLEncoder.encode("12345", "UTF-8")
//                    + "&email=" + URLEncoder.encode("test@test.com", "UTF-8")
                   // + "&amt=" + URLEncoder.encode("150", "UTF-8")

                                    //Payment for all
                                    + "&amt="+ URLEncoder.encode(final_amounts, "UTF-8")
                                    + "&user_paid_type=" + URLEncoder.encode(str_paid_type, "UTF-8")
                                    + "&validity_months=" + URLEncoder.encode(str_validity, "UTF-8");

                    webview.postUrl(url,postData.getBytes());
                }catch (Exception e){
                    e.printStackTrace();
                }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return view;
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

            UtileKit.dismisssSpinnerDialog();
            if(url.equalsIgnoreCase(common_payment_respone))
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
        Call<CommonPaymentModel> call = webServiceObj.get_payment_details_by_user(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<CommonPaymentModel>() {
            @Override
            public void onResponse(Call<CommonPaymentModel> call, Response<CommonPaymentModel> response) {
                UtileKit.dismisssSpinnerDialog();
                CommonPaymentModel paymentCheckModel= response.body();
                try {
                    if (paymentCheckModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        Log.e("Sucess","Output getMerchant_order_id"+paymentCheckModel.getData().getUser_payment_details().getMerchant_order_id());
                        addFragmenttoStack(CommonPaymentSummary.newInstance(paymentCheckModel,final_amounts,str_paid_type,str_validity));


                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<CommonPaymentModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }
    public static Fragment newInstance(boolean flag) {
        PrivacyPolicyFragment fragment = new PrivacyPolicyFragment();
        Bundle args = new Bundle();
        args.putBoolean("Flag", flag);
        fragment.setArguments(args);
        return fragment;
    }
}
package com.purplepath.purplepath.settings.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.finobot.finobot.R;
import com.purplepath.purplepath.fragments.BaseFragment;



/**
 * Created by bertrandrussellsakthees on 06/01/17.
 */

public class TermsAndCondition extends BaseFragment {
    private WebView mwebview;
    private Activity activity;
    private String url = "http://50.62.164.79/admin/testing_dnd/webview/termsandconditions.html";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View termsandconditionview =  inflater.inflate(R.layout.frag_termsandcondition_view, container, false);
        activity = getActivity();
        mwebview = termsandconditionview.findViewById(R.id.webView);


        mwebview.loadUrl(url);
//        startWebView(mwebview,url);

        return termsandconditionview;
    }

    private void startWebView(WebView webView,String url) {
        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            public void onLoadResource (WebView view, String url) {

                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }

            }
            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }

                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}

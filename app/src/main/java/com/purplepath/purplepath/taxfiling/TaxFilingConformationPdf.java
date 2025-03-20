package com.purplepath.purplepath.taxfiling;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.getconformationmodel.ConformationModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;
import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.download_file_url;

/**
 * Created by pravinr on 4/25/18.
 */

public class TaxFilingConformationPdf extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private WebView webview;

    private FloatingActionButton fab_declaration,download_pdf;

    private ConformationModel conformationModel;

    private String itr1_url="";

    private Uri uri;


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
            backPressedListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_taxfiling_conformation, container, false);
        backPressedListener.setActionBarTitle("Tax Filing Confirmation");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        fab_declaration = (FloatingActionButton) view.findViewById(R.id.fab_declaration);
        fab_declaration.setOnClickListener(this);
        download_pdf= (FloatingActionButton) view.findViewById(R.id.download_pdf);
        download_pdf.setOnClickListener(this);
        webview = view.findViewById(R.id.WebView);
        callConformationService();

        return view;
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        MyApplication.mFirebaseAnalytics.setCurrentScreen(getActivity(), getString(R.string.analtics_taxdpdf_screen), null /* class override */);
//        callConformationService();
//    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_taxdpdf_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "12");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }


    public void callConformationService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(getActivity(), false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<ConformationModel> call = webServiceObj.callGetTaxfileConformationService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<ConformationModel>() {
            @Override
            public void onResponse(Call<ConformationModel> call, Response<ConformationModel> response) {
                UtileKit.dismisssSpinnerDialog();
                conformationModel = response.body();
                try{
                    if (conformationModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        try {

                            if(conformationModel.getData().getItr1_url()!=null) {
                                itr1_url = download_file_url + conformationModel.getData().getItr1_url();
                            }

                            UtileKit.showSpinnerDialog(getActivity(), false);

                            webview.getSettings().setJavaScriptEnabled(true);
                            String filename =itr1_url;

                            Log.d("filename","filename"+filename);
                         //   webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + itr1_url);
                            webview.loadUrl("https://docs.google.com/viewer?url=" + itr1_url);

                            webview.setWebViewClient(new WebViewClient() {

                                public void onPageFinished(WebView view, String url){
                                    UtileKit.dismisssSpinnerDialog();
                                }
                            });



                        }catch (Exception e){
                            e.printStackTrace();
                            UtileKit.dismisssSpinnerDialog();
                        }



                    }else{
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ConformationModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( getActivity(),t);
            }
        });
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
            case R.id.fab_declaration:
                addFragmenttoStack(new TaxFileDeclaration());
                break;
            case R.id.download_pdf:

                uri = Uri.parse(download_file_url+conformationModel.getData().getItr1_url());

                Toast.makeText(getActivity(), "Downloading",Toast.LENGTH_LONG).show();

                downlondMethodFromWeb(uri,"Taxfile.pdf");
                break;

        }


    }

    //Download the file method
    private void downlondMethodFromWeb(Uri uri, String name) {
        DownloadManager.Request r = new DownloadManager.Request(uri);
        r.setDescription("Finobot").setTitle("Finobot");
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);
        r.allowScanningByMediaScanner();
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager dm = (DownloadManager) mContext.getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }


}

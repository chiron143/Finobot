package com.purplepath.purplepath.taxfiling;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenFile;
import com.kbeanie.imagechooser.api.FileChooserListener;
import com.kbeanie.imagechooser.api.FileChooserManager;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.document.model.DocumentModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.addsessionmodel.AddSessionModel;
import com.purplepath.purplepath.taxfiling.credencialmodel.CredencialModel;
import com.purplepath.purplepath.taxfiling.freeuserconfirmation.TaxFreeUserPdfModel;
import com.purplepath.purplepath.taxfiling.getUserStatusModel.UserStatusModel;
import com.purplepath.purplepath.taxfiling.uploadtaxfiles.TaxFileCheckListFragment;
import com.squareup.okhttp.OkHttpClient;
import java.io.File;
import butterknife.BindView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;
import retrofit2.Call;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;

/**
 * Created by pravinr on 6/8/18.
 */

public class TaxFileTwentySixForm extends BaseFragment implements View.OnClickListener ,FileChooserListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    //private int STORAGE_PERMISSION_CODE = 23;

    public String document = "document";

    public String typeofString="Form 26AS";// Government, etc...

    public String Card="Card";

    public static FileChooserManager filechooser;

    int RESULT_OK=-1;

    private String sub_digital_version="Form 26AS";

    private String xapiKey="fccd9a9b31a28f3d473f18a605bedcec";

    private String selected_item;

    private ScrollView scrollview;

    private String dcument_id="";

    private LinearLayout layout_form26;

    private String card_amount="";

    private TextView incometaxlink;

    private String planamountSession="",add_invest="",nonForm16Session="";

    private FloatingActionButton skib_fab;

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

        View view=inflater.inflate(R.layout.fragment_taxfiling_twentysix, container, false);
        backPressedListener.setActionBarTitle("Income Tax Form26AS");

        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        incometaxlink=(TextView)view.findViewById(R.id.incometaxlink);
        incometaxlink.setOnClickListener(this);


        layout_form26=(LinearLayout)view.findViewById(R.id.layout_form26);
        layout_form26.setOnClickListener(this);

        skib_fab=(FloatingActionButton)view.findViewById(R.id.skib_fab);
        skib_fab.setOnClickListener(this);

        callGetTaxfileUserStatus();

        return view;
    }

    private void callGetTaxfileUserStatus() {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<UserStatusModel> call = webServiceObj.callGetTaxfileUserStatus(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new retrofit2.Callback<UserStatusModel>() {
            @Override
            public void onResponse(Call<UserStatusModel> call, Response<UserStatusModel> response) {
                UtileKit.dismisssSpinnerDialog();
                UserStatusModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        add_invest=userStatusModel.getData().getPage_visited_array().get(0).getAdd_invest();
                        planamountSession=userStatusModel.getData().getPage_visited_array().get(0).getPlan_amount();
                        nonForm16Session = userStatusModel.getData().getPage_visited_array().get(0).getNon_form16();
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
    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_form26_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "9");
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

            //hide converasation
            case R.id.layout_form26:

                if(isReadStorageAllowed()){
                    fileGetFrom26AS();
                    return;
                }
                requestStoragePermission();

                break;

            case R.id.incometaxlink:
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.incometaxindiaefiling.gov.in"));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request."+ " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.skib_fab:

                AddTaxfileSessionService("users_tax_file_page_visited_status","form26as","Y");

                if(add_invest.equalsIgnoreCase("Y")||nonForm16Session.equalsIgnoreCase("Y")){
                    addFragmenttoStack(new TaxFileCheckListFragment());
                }
                else if(add_invest.equalsIgnoreCase("N")&&planamountSession.equalsIgnoreCase("199")){
                    addFragmenttoStack(new TaxFileCheckListFragment());
                }
                else if(planamountSession.equalsIgnoreCase("1")){
                    callGetFreeUserPdfService();
                }

                break;
        }

    }

    private void AddTaxfileSessionService(String corresponding_table, String field_name, String field_value) {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AddSessionModel> call = webServiceObj.AddTaxfileSessionService(UtileKit.getPersistedPurplePathPref("user_id")
                ,corresponding_table,field_name, field_value);
        call.enqueue(new retrofit2.Callback<AddSessionModel>() {
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

    //Free user PDF generate ITR1 files
    public void callGetFreeUserPdfService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);

        Call<TaxFreeUserPdfModel> call = webServiceObj.callGetFreeUserPdfService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new retrofit2.Callback<TaxFreeUserPdfModel>() {
            @Override
            public void onResponse(Call<TaxFreeUserPdfModel> call, Response<TaxFreeUserPdfModel> response) {
                UtileKit.dismisssSpinnerDialog();
                TaxFreeUserPdfModel getChecklistModel = response.body();
                try {
                    if (getChecklistModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        addFragmenttoStack(new TaxFilingConformationPdf());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxFreeUserPdfModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }

//    private boolean isReadStorageAllowed() {
//        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
//        if (result == PackageManager.PERMISSION_GRANTED)
//            return true;
//        return false;
//    }
//    private void requestStoragePermission(){
//        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
//        }
//        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
//    }


    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }
    private void requestStoragePermission(){

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},3);
    }

    public void fileGetFrom26AS() {
        try{
            //Library using upload file
            filechooser = new FileChooserManager(this);
            filechooser.setFileChooserListener(this);

            try {
                filechooser.choose();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Library using upload file

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{

            //Library using upload file
            if (requestCode == ChooserType.REQUEST_PICK_FILE && resultCode == RESULT_OK) {
                if (filechooser == null) {
                    filechooser = new FileChooserManager(this);
                    filechooser.setFileChooserListener(this);
                }
                filechooser.submit(requestCode, data);
            }
            //Library using upload file

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onFileChosen(final ChosenFile file) {

        getActivity().runOnUiThread(new Runnable() {
            public void run() {

                if (file != null) {

                    new DownloadAnonymousTask().execute(file.getFilePath());
                }

            }
        });
    }
    private class DownloadAnonymousTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            return  params[0];
        }
        protected void onPostExecute(String result) {
        //    Log.i("onPostExecute","onPostExecute"+ result);


            File image = new File(result);
            String OutPut = (image.getAbsolutePath());

            File image1 = new File(OutPut);
            uploadFileRetrofit(image1.getAbsolutePath(), document,typeofString,Card);
        }
    }
    @Override
    public void onError(final String reason) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getActivity(), "File does not exist", Toast.LENGTH_LONG).show();
            }
        });
    }





    //Retrofit 1.9
    private void uploadFileRetrofit(String stringUri,String document,String Government,String Card) {

        try {
            TypedFile typedFile = new TypedFile("image/jpg",new File(stringUri));
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceGenerator.service_base_url)
                    .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
            UtileKit.showSpinnerDialog(mContext, false);

            WebServiceCalls apiInterface = restAdapter.create(WebServiceCalls.class);

            apiInterface.callAddFileService(UtileKit.getPersistedPurplePathPref("user_id"),
                    document, Government, Card, typedFile,xapiKey,sub_digital_version ,selected_item,new Callback<DocumentModel>() {
                        @Override
                        public void success(DocumentModel documentModel, retrofit.client.Response response) {
                            try {
                              //  Log.d("loading","loading"+ response);
                                if (documentModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                                    dcument_id=documentModel.getData().getUploaded_data().get(0).getDoc_id();


                                    Toast.makeText(getActivity(), "File Successfully Uploaded",Toast.LENGTH_LONG).show();

                                    //Here Check Form26 Uploaded or empty check waiting for Parser Response
                                    addFragmenttoStack(TaxFilingFormParseTwentySix.newInstance(dcument_id));

                               //     Log.d("dcument_id","dcument_id"+dcument_id);

                                }
                                else {
                                    UtileKit.intitializeAlertDialog(getString(R.string.file_valid),mContext);
                                }
                                UtileKit.dismisssSpinnerDialog();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                        @Override
                        public void failure(RetrofitError retrofitError) {
                            UtileKit.dismisssSpinnerDialog();
                            UtileKit.alertRetrofitExceptionDialog( mContext,retrofitError);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
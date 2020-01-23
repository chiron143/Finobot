package com.purplepath.purplepath.taxfiling;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.purplepath.purplepath.taxfiling.dialoginstructionscreen.TaxFilingFormInstructionDialog;
import com.purplepath.purplepath.taxfiling.resettaxfiling.TaxFileResetModel;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;


import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;
import retrofit2.Call;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;


/**
 * Created by pravinr on 6/6/18.
 */

public class TaxFilingUploadFileNew extends BaseFragment implements View.OnClickListener ,FileChooserListener  {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    public static Context mContext;

   // private int STORAGE_PERMISSION_CODE = 23;

    public String document = "document";

    public String typeofString="Form 16";// Government, etc...

    public String Card="Card";

    private String card_amount="";

    public static FileChooserManager filechooser;

    int RESULT_OK=-1;

    private String sub_digital_version="";

    private String xapiKey="fccd9a9b31a28f3d473f18a605bedcec";

    private String selected_item;

    private ScrollView scrollview;

    private String dcument_id="";

    private LinearLayout layout_form16;

    private TextView form16_samplepdf_click;

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

        View view=inflater.inflate(R.layout.fragment_taxfiling_uploadfilenew, container, false);
        backPressedListener.setActionBarTitle("Income Tax Form16");

        scrollview = view.findViewById(R.id.scrollview);
        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        layout_form16=(LinearLayout)view.findViewById(R.id.layout_form16);
        layout_form16.setOnClickListener(this);

        form16_samplepdf_click=(TextView)view.findViewById(R.id.form16_samplepdf_click);
        form16_samplepdf_click.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_form16_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "22");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_taxfile_reset,menu);
        MenuItem item=menu.findItem(R.id.menu_taxfile_reset);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_taxfile_reset:
                try{
                    resetTaxFilingDialog(mContext);

                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
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

            case R.id.layout_form16:

                if(isReadStorageAllowed()){
                   // Toast.makeText(getActivity(),"You already have the permission",Toast.LENGTH_LONG).show();

                    fileGetFromStorage();

                    return;
                }else {
                    requestStoragePermission();
                }


                break;
            case R.id.form16_samplepdf_click:

                TaxFilingFormInstructionDialog dialog_view = new TaxFilingFormInstructionDialog();
                dialog_view.show(getFragmentManager(), "ViewPdf");

                break;


        }
    }


    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }
    private void requestStoragePermission(){

        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] PermissionsUploadFile, @NonNull int[] grantResults) {
//
//        if(requestCode == STORAGE_PERMISSION_CODE){
//
//            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//
//                Toast.makeText(getActivity(),"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
//
//                fileGetFromStorage();
//            }else{
//
//                Toast.makeText(getActivity(),"Oops you just denied the permission",Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    public void fileGetFromStorage() {
        try{
            //Library using upload file
            filechooser = new FileChooserManager(this);
            filechooser.setFileChooserListener(this);

            try {
                filechooser.choose();
            } catch (Exception e) {
                e.printStackTrace();
            }

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
            Log.i("onPostExecute","onPostExecute"+ result);


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
                //   pBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "File does not exist", Toast.LENGTH_LONG).show();
               // Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }





    //Retrofit 1.9
    private void uploadFileRetrofit(String stringUri,String document,String Government,String Card) {

        try {
            Government="Form 16";

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

                                if (documentModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                                    dcument_id=documentModel.getData().getUploaded_data().get(0).getDoc_id();

                                    Toast.makeText(getActivity(), "File Successfully Uploaded",Toast.LENGTH_LONG).show();


                                    addFragmenttoStack(TaxFilingFormParse.newInstance(dcument_id));

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

    private void resetTaxFilingDialog(Context mContext) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater= LayoutInflater.from(mContext);
        dialogView=inflater.inflate(R.layout.yes_no_reset,null);
        alertDialog=new android.support.v7.app.AlertDialog.Builder(mContext).create();
        alertDialog.setView(dialogView);
        final TextView txt_heading=dialogView.findViewById(R.id.txt_heading);
        final TextView textView=dialogView.findViewById(R.id.additional_yes);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getResetTaxfilingService();

                alertDialog.dismiss();

            }
        });
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void getResetTaxfilingService() {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxFileResetModel> call = webServiceObj.callGetResetTaxfileService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new retrofit2.Callback<TaxFileResetModel>() {
            @Override
            public void onResponse(Call<TaxFileResetModel> call, Response<TaxFileResetModel> response) {
                UtileKit.dismisssSpinnerDialog();
                TaxFileResetModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        startSettingHomeActivity();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxFileResetModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext,t);
            }
        });
    }



}

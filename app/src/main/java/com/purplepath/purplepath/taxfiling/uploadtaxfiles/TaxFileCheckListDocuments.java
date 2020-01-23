package com.purplepath.purplepath.taxfiling.uploadtaxfiles;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenFile;
import com.kbeanie.imagechooser.api.FileChooserListener;
import com.kbeanie.imagechooser.api.FileChooserManager;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;
import com.purplepath.purplepath.document.Interface.OnItemClickListenerInterface;
import com.purplepath.purplepath.document.deleteModels.DeleteModels;
import com.purplepath.purplepath.document.model.DocumentModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.TaxFilingUploadFileNew;
import com.purplepath.purplepath.taxfiling.adapter.TaxFileCheckListDocumentAdapter;
import com.purplepath.purplepath.taxfiling.getchecklist.ChecklistModel;
import com.purplepath.purplepath.taxfiling.getchecklist.Result;
import com.purplepath.purplepath.taxfiling.getchecklistdocument.GetChecklistModel;
import com.squareup.okhttp.OkHttpClient;
import java.io.File;
import java.util.ArrayList;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;
import retrofit2.Call;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;
import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.download_file_url;

/**
 * Created by pravinr on 3/28/18.
 */

public class TaxFileCheckListDocuments extends BaseFragment implements View.OnClickListener,FileChooserListener,
        AdapterView.OnItemSelectedListener, OnItemClickListenerInterface {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    public static Context mContext;

    private ListView listview;

    private TaxFileCheckListDocumentAdapter taxFileCheckListDocumentAdapter;

    private ChecklistModel checklistModel;

    private ArrayList<Result> arrayListResult=new ArrayList<>();

    private Spinner product_Spinner;

    private ArrayList<String> productCategoriesArray = new ArrayList<>();

    private FloatingActionButton fabAddfiles,fabDone;

    private int STORAGE_PERMISSION_CODE = 23;

    public static FileChooserManager filechooser;

    int RESULT_OK=-1;

    private String xapiKey="fccd9a9b31a28f3d473f18a605bedcec";

    public String document = "document";

    public String typeofString="Tax";// Government, etc...

    public String Card="Card";

    private String sub_digital_version="";

    private String selected_item;

    private ArrayList<com.purplepath.purplepath.taxfiling.getchecklistdocument.Document_details> document_details=new ArrayList<>();

    OnItemClickListenerInterface getpositionInterface;

    LayoutInflater inflater;

    View dialogView;

    android.support.v7.app.AlertDialog alertDialog;

    private TextView empty_value;

    public Uri uri;


    public static TaxFileCheckListDocuments newInstance(Result section) {
        TaxFileCheckListDocuments taxfile = new TaxFileCheckListDocuments();
        Bundle args = new Bundle();
        if (section!=null) {
            args.putSerializable("section", section);
        }
        taxfile.setArguments(args);
        return taxfile;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        getpositionInterface = this;
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
    public void setListener(OnItemClickListenerInterface callbackInterface){
        this.getpositionInterface=callbackInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        arrayListResult.clear();
        if (getArguments() != null) {
            if (getArguments().containsKey("section")) {
                Result  mReslt = (Result) getArguments().getSerializable("section");
                productCategoriesArray=mReslt.getProducts();
                sub_digital_version=mReslt.getSection();
            }
        }
        View view=inflater.inflate(R.layout.fragment_taxfiling_checklist_document, container, false);
        backPressedListener.setActionBarTitle("Income Tax Checklist Document");

        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        empty_value= (TextView)view.findViewById(R.id.empty_value);

        listview=(ListView)view.findViewById(R.id.listview);

        fabAddfiles=(FloatingActionButton)view.findViewById(R.id.fabAddfiles);
        fabAddfiles.setOnClickListener(this);
        fabDone=(FloatingActionButton)view.findViewById(R.id.fabDone);
        fabDone.setOnClickListener(this);


        if(productCategoriesArray.isEmpty()){
            fabAddfiles.setVisibility(View.GONE);
        }else {
            fabAddfiles.setVisibility(View.VISIBLE);
        }


//        setSpinnerAdapter(product_Spinner, productCategoriesArray, mContext);
//
//        product_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//
//                selected_item = adapterView.getItemAtPosition(position).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        callAddFileWebServiceupdates();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    uri = Uri.parse(download_file_url+document_details.get(i).getDoc_url());
                    File file = new File(String.valueOf(uri));

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    if(file.toString().contains(".jpg")|| file.toString().contains(".jpeg") || file.toString().contains(".png"))
                    {
                        intent.setDataAndType(uri, "image/jpeg");
                    }
                    else if (file.toString().contains(".pdf")) {
                        intent.setDataAndType(uri, "application/pdf");
                    }
                    else if (file.toString().contains(".ppt") || file.toString().contains(".pptx")) {
                        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                    }
                    else if (file.toString().contains(".xls") || file.toString().contains(".xlsx")) {
                        intent.setDataAndType(uri, "application/vnd.ms-excel");
                    }
                    else if (file.toString().contains(".gif")) {
                        intent.setDataAndType(uri, "image/gif");
                    }
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_taxdocumentchecklist_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "3");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }


    public void setSpinnerAdapter(Spinner mMyMartialSpinner, ArrayList<String> mystringList, Context mycontext) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("");
        for (String s : mystringList) {
            stringList.add(s);
        }
        CustomSpinerAdapter adapter_state = new CustomSpinerAdapter(mycontext, stringList);
        mMyMartialSpinner.setAdapter(adapter_state);

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
            case R.id.fabAddfiles:

//                if(!selected_item.isEmpty()){
//                    if(isReadStorageAllowed()){
//                        fileGetFromStorage();
//                        return;
//                    }
//                    requestStoragePermission();
//                }else {
//                    spinnerError(product_Spinner);
//                }
                    if(isReadStorageAllowed()){
                        spinnerChecklistDocument(productCategoriesArray,mContext);
                        return;
                    }
                    requestStoragePermission();




                break;
            case R.id.fabDone:
                addFragmenttoStack(new TaxFileCheckListFragment());
                break;
        }

    }

    private void spinnerChecklistDocument(ArrayList<String> productCategoriesArray, Context mContext) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialogs;
        final Spinner section_spinner,product_spinner;
        final LinearLayout layout_product_spinner,layout_section_spinner;


        try {
            inflater = LayoutInflater.from(mContext);
            dialogView = inflater.inflate(R.layout.taxfiledocument_dialog_spinner_view, null);
            alertDialogs = new AlertDialog.Builder(mContext).create();
            alertDialogs.setView(dialogView);
            TextView heading = (TextView) dialogView.findViewById(R.id.txt_heading);

            section_spinner = (Spinner) dialogView.findViewById(R.id.section_spinner);

            product_spinner = (Spinner) dialogView.findViewById(R.id.product_spinner);
            layout_product_spinner = (LinearLayout) dialogView.findViewById(R.id.layout_product_spinner);
            layout_section_spinner= (LinearLayout) dialogView.findViewById(R.id.layout_section_spinner);
            layout_section_spinner.setVisibility(View.GONE);

            setSpinnerAdapter(product_spinner, productCategoriesArray, mContext);

            product_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position!=0) {
                        selected_item = parent.getItemAtPosition(position).toString();

                    }else {
                        selected_item="";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(selected_item.equalsIgnoreCase("")){
                        spinnerError(product_spinner);
                    }else{
                            fileGetFromStorage();
                            alertDialogs.dismiss();
                        }

                }
            });

            dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialogs.dismiss();
                }
            });


            alertDialogs.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void spinnerError(Spinner spinner) {
        TextView errorText = (TextView) spinner.getSelectedView();
        errorText.setError("");
        errorText.setTextColor(Color.RED);
        errorText.setText("");
    }

    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == STORAGE_PERMISSION_CODE){

            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                spinnerChecklistDocument(productCategoriesArray,mContext);
            }
        }
    }

    private void fileGetFromStorage() {
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        switch (parent.getId()) {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view, int position) {
        alertButtonDialogYesNo(position);
    }

    @Override
    public void onClick(View view, int position, String name, String mvalue) {

    }
    private void alertButtonDialogYesNo( int position) {
        passalertButtonDialogYesNo("Are you sure want to delete?", mContext,position);
    }

    private void passalertButtonDialogYesNo(String message, Context context, final int position) {


        inflater= LayoutInflater.from(context);
        dialogView=inflater.inflate(R.layout.yes_no_dialog,null);
        alertDialog=new android.support.v7.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        TextView stringErrorMessage = dialogView.findViewById(R.id.textViewDilog);
        stringErrorMessage.setText(HomePageActivity.stringMessageError);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callChecklistDeleteFileServices(String.valueOf(position));
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

    private void callChecklistDeleteFileServices(final String documentId) {

        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<DeleteModels> call = webServiceObj.call_delete_documents_by_user(UtileKit.getPersistedPurplePathPref("user_id"),documentId);
        call.enqueue(new retrofit2.Callback<DeleteModels>() {
            @Override
            public void onResponse(Call<DeleteModels> call, Response<DeleteModels> response) {
                UtileKit.dismisssSpinnerDialog();
                DeleteModels getDeleteModels = response.body();
                try{
                    if (getDeleteModels.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        callAddFileWebServiceupdates();

                        UtileKit.intitializeAlertDialog(getString(R.string.filesuccessfullydeleted),mContext);
                        addFragmenttoStack(TaxFileCheckListDocuments.this);
                        taxFileCheckListDocumentAdapter.updateResults(documentId);
                        taxFileCheckListDocumentAdapter.notifyDataSetChanged();
                    }}catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<DeleteModels> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }

    private class DownloadAnonymousTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            return  params[0];
        }
        protected void onPostExecute(String result) {

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
                    document, Government, Card, typedFile,xapiKey,sub_digital_version,selected_item,new Callback<DocumentModel>() {
                        @Override
                        public void success(DocumentModel documentModel, retrofit.client.Response response) {
                            try {

                                if (documentModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                                    UtileKit.intitializeAlertDialog(getString(R.string.filesuccessfully),mContext);

                                    callAddFileWebServiceupdates();


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



//Add and update
    public void callAddFileWebServiceupdates() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        if(sub_digital_version!= null) {
            Call<GetChecklistModel> call = webServiceObj.getCheckListDocumentService(UtileKit.getPersistedPurplePathPref("user_id"),
                    sub_digital_version);
            call.enqueue(new retrofit2.Callback<GetChecklistModel>() {
                @Override
                public void onResponse(Call<GetChecklistModel> call, Response<GetChecklistModel> response) {
                    UtileKit.dismisssSpinnerDialog();
                    GetChecklistModel getChecklistModel = response.body();
                    try {
                        if (getChecklistModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                            if (null != getChecklistModel.getData().getDocument_details()) {

                                document_details =getChecklistModel.getData().getDocument_details();
                                taxFileCheckListDocumentAdapter = new TaxFileCheckListDocumentAdapter(mContext, document_details, getChecklistModel, getpositionInterface);
                                listview.setAdapter(taxFileCheckListDocumentAdapter);
                                taxFileCheckListDocumentAdapter.notifyDataSetChanged();
                                empty_value.setVisibility(View.GONE);
                            }
                        } else {
                            empty_value.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GetChecklistModel> call, Throwable t) {
                    UtileKit.dismisssSpinnerDialog();
                    UtileKit.alertRetrofitExceptionDialog(mContext, t);
                }
            });
        }
    }
}

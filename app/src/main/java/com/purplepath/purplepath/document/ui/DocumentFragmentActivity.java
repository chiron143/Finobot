package com.purplepath.purplepath.document.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.clans.fab.FloatingActionMenu;
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ChosenImages;
import com.kbeanie.imagechooser.api.ChosenVideo;
import com.kbeanie.imagechooser.api.ChosenVideos;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.kbeanie.imagechooser.api.MediaChooserListener;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;
import com.purplepath.purplepath.document.Interface.Constants;
import com.purplepath.purplepath.document.Interface.OnItemClickListenerInterface;
import com.purplepath.purplepath.document.adapter.DocumentListAdapter;
import com.purplepath.purplepath.document.deleteModels.DeleteModels;
import com.purplepath.purplepath.document.getfilemodels.Document_details;
import com.purplepath.purplepath.document.getfilemodels.GetDocumentModels;
import com.purplepath.purplepath.document.model.DocumentModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.mime.TypedFile;
import retrofit2.Call;
import retrofit2.Response;

import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.download_file_url;


/**
 * Created by pravinr on 6/23/17.
 */

public class DocumentFragmentActivity extends BaseFragment implements View.OnClickListener,MediaChooserListener,
        OnItemClickListenerInterface {
    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;
    DocumentListAdapter documentListAdapter;
    ListView listView;
    private Context mContext;
    FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton  fab_gallery, mfab_files;
    public String document = "document";
    public String typeofString;// Government, etc...
    public String Card="Card";
    private ArrayList<Document_details> document_details=new ArrayList<>();
    public Uri uri;
    private int SELECT_FILE=2;
    ImageChooserManager imageChooserManager;
    private String filePath;
    private int chooserType;
    int RESULT_OK=-1;
    OnItemClickListenerInterface getpositionInterface;
    private DeleteModels mdeleteModels;
    int REQUEST_STORAGE=1;
    EditText edittext_search;
    LayoutInflater inflater;
    View dialogView;
    android.support.v7.app.AlertDialog alertDialog;
    private int STORAGE_PERMISSION_CODE = 23;
    //String download_file_url = "https://s3-ap-southeast-1.amazonaws.com/fino-bucket/";

    private LinearLayout layoutFabMain,layoutGallery,layoutfile;
    private View layout_view;
    boolean flagToggleButton = false;
    private TextView empty_value;
    private FloatingActionButton fabMain;

    private String sub_digital_version="";

    private String xapiKey="fccd9a9b31a28f3d473f18a605bedcec";

    private String selected_item;

    private String selected_item_section,selected_item_product;

    private ArrayList<String> sectionCategoriesArray = new ArrayList<String>();

    private ArrayList<String> productCategoriesArray = new ArrayList<String>();

    private GetDocumentModels getDocumentModels;

    private String layoutClickfileorGallery;

//    public static DocumentFragmentActivity newInstance(String validatestring) {
//
//        DocumentFragmentActivity fragment=new DocumentFragmentActivity();
//        fragment.typeofString = validatestring;
//        Log.i("","typeofString"+fragment.typeofString);
//        return fragment;
//    }
    public static DocumentFragmentActivity newInstance(String validatestring) {
        DocumentFragmentActivity documentFragmentActivity = new DocumentFragmentActivity();
        Bundle args = new Bundle();

        if (validatestring != null) {
            args.putSerializable("validatestring", validatestring);
        }
        documentFragmentActivity.setArguments(args);
        return documentFragmentActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        getpositionInterface = this;
        backPressedListener = (OnActivityBackPressedListener) (mContext);
    }
    public void setListener(OnItemClickListenerInterface callbackInterface){
        this.getpositionInterface=callbackInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        if(getArguments().containsKey("validatestring")) {
            typeofString = getArguments().getString("validatestring");
        }

        View view=inflater.inflate(R.layout.fragment_documents, container, false);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        listView = view.findViewById(R.id.listview_document);
        empty_value= view.findViewById(R.id.empty_value);


        layout_view= view.findViewById(R.id.layout_view);

        fabMain= view.findViewById(R.id.fabMain);
        // layoutFabMain=(LinearLayout)view.findViewById(R.id.layoutFabMain);
        layoutGallery= view.findViewById(R.id.layoutGallery);
        layoutfile= view.findViewById(R.id.layoutfile);

        layoutGallery.setOnClickListener(this);
        layoutfile.setOnClickListener(this);

        if(typeofString.equalsIgnoreCase("Form 16")){
            fabMain.setVisibility(View.GONE);
        }
        if(typeofString.equalsIgnoreCase("Form 16-A")){
            fabMain.setVisibility(View.GONE);
        }
        if(typeofString.equalsIgnoreCase("Form 16-B")){
            fabMain.setVisibility(View.GONE);
        }

        edittext_search= view.findViewById(R.id.etSearch);
        edittext_search.setEnabled(true);
        edittext_search.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    documentListAdapter.getFilter().filter(s.toString());
                    documentListAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        callAddFileWebServiceupdates();



        layout_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout_view.setVisibility(View.GONE);
                layoutGallery.setVisibility(View.GONE);
                layoutfile.setVisibility(View.GONE);

            }
        });




        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(flagToggleButton)) {
                    layoutGallery.setVisibility(View.VISIBLE);
                    layoutfile.setVisibility(View.VISIBLE);
                    layout_view.setVisibility(View.VISIBLE);
                    flagToggleButton=true;
                }
                else
                {
                    layoutGallery.setVisibility(View.GONE);
                    layoutfile.setVisibility(View.GONE);
                    layout_view.setVisibility(View.GONE);
                    flagToggleButton = false;
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // String uri = download_file_url+document_details.get(i).getDoc_url();
                // File file = new File(uri);
                // openFile(file );
                Log.d("","Urlpath"+uri);
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



    private void spinnerChecklistDocument(final String layoutGallery, final GetDocumentModels getDocumentModels) {
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


                int length = getDocumentModels.getData().getSec_prod().size();

                for (int i = 0; i < length; i++) {
                    sectionCategoriesArray.add(getDocumentModels.getData().getSec_prod().get(i).getSection());
                }

                setSpinnerAdapter(section_spinner, sectionCategoriesArray, mContext);


            section_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position!=0){
                    selected_item_section = parent.getItemAtPosition(position).toString();

                    productCategoriesArray= getDocumentModels.getData().getSec_prod().get(position-1).getProducts();

                    setSpinnerAdapter(product_spinner, productCategoriesArray, mContext);
                    }else {
                        selected_item_section="";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            product_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if(position!=0) {
                        selected_item_product = parent.getItemAtPosition(position).toString();

                    }else {
                        selected_item_product="";
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    /*if(UtileKit.validateObjectValues(selected_item_section) &&UtileKit.validateObjectValues(selected_item_product)){
                        if (layoutGallery.equalsIgnoreCase("layoutGallery")) {
                            chooseImage();
                            alertDialogs.dismiss();
                        } else {
                            fileGetFromStorage();
                            alertDialogs.dismiss();
                        }
                    }else{
                        if (selected_item_section.equalsIgnoreCase("")) {
                            spinnerError(section_spinner);
                        }else if(selected_item_product.equalsIgnoreCase("")){
                            spinnerError(product_spinner);
                        }
                    }*/

                    if (selected_item_section.equalsIgnoreCase("")) {
                        spinnerError(section_spinner);
                     }else if(selected_item_product.equalsIgnoreCase("")){
                        spinnerError(product_spinner);
                     }else  {
                        if (layoutGallery.equalsIgnoreCase("layoutGallery")) {
                            chooseImage();
                            alertDialogs.dismiss();
                        } else {
                            fileGetFromStorage();
                            alertDialogs.dismiss();
                        }
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

    public void setSpinnerAdapter(Spinner mMyMartialSpinner, ArrayList<String> mystringList, Context mycontext) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("");
        for (String s : mystringList) {
            stringList.add(s);
        }
        CustomSpinerAdapter adapter_state = new CustomSpinerAdapter(mycontext, stringList);
        mMyMartialSpinner.setAdapter(adapter_state);
    }

    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
        }
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(),"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getActivity(),"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.document_menu,menu);
        MenuItem item=menu.findItem(R.id.menu_search);
        super.onCreateOptionsMenu(menu, inflater);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        search(searchView);

        AutoCompleteTextView searchTextView = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.seatchview_cusorcolor);
        } catch (Exception e) {
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    documentListAdapter.getFilter().filter(newText);
                    documentListAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            }
        });
    }


    private void chooseImage() {
        chooserType = ChooserType.REQUEST_PICK_PICTURE;
        imageChooserManager = new ImageChooserManager(this,ChooserType.REQUEST_PICK_PICTURE, true);
        imageChooserManager.setImageChooserListener(this);
        try {
            filePath = imageChooserManager.choose();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void fileGetFromStorage() {
        try{
            Intent intent = new Intent(mContext, FileChooserActivity.class);
            startActivityForResult(intent, SELECT_FILE);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        try{
            if (resultCode == RESULT_OK && (requestCode == ChooserType.REQUEST_PICK_PICTURE )) {
                if (imageChooserManager == null) {

                    String picturePath = null;
                    String[] proj = { MediaStore.Images.Media.DATA };
                    String result = null;
                    Uri selectedImageUri = data.getData();

                    Log.d("","selecturiii"+selectedImageUri);

                    CursorLoader cursorLoader = new CursorLoader(mContext,selectedImageUri, proj, null, null, null);
                    Cursor cursor = cursorLoader.loadInBackground();

                    if(cursor != null){
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        picturePath = cursor.getString(column_index);
                    }
                    File image = new File(String.valueOf(selectedImageUri));
                    uploadFileRetrofit(image.getAbsolutePath(),document, typeofString,Card);

                        /*String fileSelected = data.getStringExtra(Constants.KEY_FILE_SELECTED);
                        uploadFileRetrofit(fileSelected,document, typeofString,Card);*/
                }
                imageChooserManager.submit(requestCode, data);
            }
            else if (requestCode == SELECT_FILE) { //File Pde,Word,Doc all file selected this place
                String fileSelected = data.getStringExtra(Constants.KEY_FILE_SELECTED);
                uploadFileRetrofit(fileSelected,document, typeofString,Card);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onClick(View view, int position) {

        Log.i("", "FileimageChooserManager Path : " + position);

        alertButtonDialogYesNo(position);
//        callDeleteFileServices(String.valueOf(position));
    }

    @Override
    public void onClick(View view, int position, String name, String positio) {

    }

    private void alertButtonDialogYesNo( int position) {

        passalertButtonDialogYesNo("Are you sure want to delete?", mContext,position);
    }

    private void passalertButtonDialogYesNo(String message, Context context, final int position) {

//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setMessage(message);
//		builder.setPositiveButton(context.getString(R.string.dialog_no),
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//
//						// TODO Auto-generated method stub
//
//					}
//				});
//
//		builder.setNegativeButton(
//				context.getString(R.string.dialog_yes),
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//
//						// TODO Auto-generated method stub
//						deleteGoal(goalId, userId, position);
//
//					}
//				});
//		builder.setCancelable(true);
//		AlertDialog alert = builder.show();
//		Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
//		//nbutton.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//		Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//		//pbutton.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//		TextView messageText = (TextView) alert.findViewById(android.R.id.message);
//		messageText.setGravity(Gravity.CENTER);

        inflater= LayoutInflater.from(context);
        dialogView=inflater.inflate(R.layout.yes_no_dialog,null);
        alertDialog=new android.support.v7.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        TextView stringErrorMessage = dialogView.findViewById(R.id.textViewDilog);
        stringErrorMessage.setText(HomePageActivity.stringMessageError);
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();

                callDeleteFileServices(String.valueOf(position));
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

    private void callDeleteFileServices(final String documentId) {

        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<DeleteModels> call = webServiceObj.call_delete_documents_by_user(UtileKit.getPersistedPurplePathPref("user_id"),documentId);
        call.enqueue(new retrofit2.Callback<DeleteModels>() {
            @Override
            public void onResponse(Call<DeleteModels> call, Response<DeleteModels> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success", "" + response.body());
                DeleteModels getDeleteModels = response.body();
                Log.d("hi","addfile"+ response.body());
                try{
                    if (getDeleteModels.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        callAddFileWebServiceupdates();


                        UtileKit.intitializeAlertDialog(getString(R.string.filesuccessfullydeleted),mContext);
                        addFragmenttoStack(DocumentFragmentActivity.this);
                        documentListAdapter.updateResults(documentId);
                        documentListAdapter.notifyDataSetChanged();

                    }}catch (Exception e) {
                    e.printStackTrace();
                }
                //  UtileKit.dismisssSpinnerDialog();
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
            Log.i("onPostExecute","onPostExecute"+ result);

            String picturePath = result;
            File image = new File(result);
            String OutPut = compressImage(image.getAbsolutePath());
            String orientation = "";
            ExifInterface exif;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(OutPut, options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;
            if(imageHeight>=imageWidth){
                orientation = "P";
            }else{
                int height = imageHeight *40/100;
                int imgHeight =  imageHeight+height;
                if(imgHeight>=imageWidth){
                    orientation = "P";
                }else{
                    orientation = "L";
                }
            }
            File image1 = new File(OutPut);
            uploadFileRetrofit(image1.getAbsolutePath(), document,typeofString,Card);
        }
    }
    public String compressImage(String imageUri) {
        System.out.println("startcompression"+imageUri);
        String filePath = imageUri;
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        // max Height and width values of the compressed image is taken as
        // 816x612

        float maxHeight = 816.0f;
        // float maxWidth = 612.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        // width and height values are set maintaining the aspect ratio of the
        // image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }
        // setting inSampleSize value allows to load a scaled down version of
        // the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth,actualHeight);
        // inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
        // this options allow android to claim the bitmap memory if it runs low
        // on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inDensity = DisplayMetrics.DENSITY_MEDIUM;
        options.inTargetDensity = this.getResources().getDisplayMetrics().densityDpi;
        options.inScaled = true;
        options.inPreferQualityOverSpeed = true;
        options.inTempStorage = new byte[16 * 1024];
        try {
            // load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,
                    Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2,
                middleY - bmp.getHeight() / 2, new Paint(
                        Paint.FILTER_BITMAP_FLAG));

        FileOutputStream out = null;
        String filename = getFilename();
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);

            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,true);
            out = new FileOutputStream(filename);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    public String getFilename() {
        File uriFile;
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file1 = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));
        if (file.exists()) {

            if (!file1.exists()) {
                file1.mkdirs();
            }
            uriFile = new File(file1.getAbsolutePath() + "/"+ System.currentTimeMillis() + ".jpg");
        } else {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            File path1 = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)));
            if (path.exists()) {
                if (!path1.exists()) {
                    path1.mkdirs();
                }
            }
            uriFile = new File(path1.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        }

        String uriSting = uriFile.getAbsolutePath();
        //uploadFileRetrofit(uriSting,document, typeofString,Card);

        return uriSting;

    }
    //Retrofit 1.9
    private void uploadFileRetrofit(String stringUri,String document,String Government,String Card) {
        String str_section = null,str_product = null;
        if(Government.equalsIgnoreCase("Tax")){
             str_section=selected_item_section;
             str_product=selected_item_product;
        }else {
            str_section="";
            str_product="";
        }

        try {
            TypedFile typedFile = new TypedFile("image/jpg",new File(stringUri));
            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceGenerator.service_base_url)
             .setClient(new OkClient(new OkHttpClient())).setLogLevel(RestAdapter.LogLevel.FULL).build();
            UtileKit.showSpinnerDialog(mContext, false);

            WebServiceCalls apiInterface = restAdapter.create(WebServiceCalls.class);

            apiInterface.callAddFileService(UtileKit.getPersistedPurplePathPref("user_id"),
                    document, Government, Card, typedFile,xapiKey,str_section,str_product ,new Callback<DocumentModel>() {
                        @Override
                        public void success(DocumentModel documentModel, retrofit.client.Response response) {
                            try {
                                Log.d("hi","loadddddd"+ response);
                                if (documentModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                                    UtileKit.intitializeAlertDialog(getString(R.string.filesuccessfully),mContext);
                                    callAddFileWebServiceupdates();
                                }
                                else {
                                    UtileKit.intitializeAlertDialog(getString(R.string.file_valid),mContext);
                                    UtileKit.intitializeAlertDialog(documentModel.getData().getMessage(),mContext);
                                }
                                UtileKit.dismisssSpinnerDialog();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                        @Override
                        public void failure(RetrofitError retrofitError) {
                            //Log.e("CallBack", " failure is " + retrofitError);
                            UtileKit.dismisssSpinnerDialog();
                            UtileKit.alertRetrofitExceptionDialog( mContext,retrofitError);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void callAddFileWebServiceupdates() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        if(typeofString!= null) {
            Call<GetDocumentModels> call = webServiceObj.callget_documents_by_user(UtileKit.getPersistedPurplePathPref("user_id"),
                    typeofString);
            call.enqueue(new retrofit2.Callback<GetDocumentModels>() {
                @Override
                public void onResponse(Call<GetDocumentModels> call, Response<GetDocumentModels> response) {
                    UtileKit.dismisssSpinnerDialog();
                    //Log.e("success", "" + response.body());
                     getDocumentModels = response.body();
                    Log.d("hi", "addfile" + response.body());
                    try {
                        if (getDocumentModels.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                            if (null != getDocumentModels.getData().getDocument_details()) {

                                document_details = getDocumentModels.getData().getDocument_details();
                                documentListAdapter = new DocumentListAdapter(mContext, document_details, getDocumentModels, getpositionInterface);
                                LinearLayoutManager llm = new LinearLayoutManager(mContext);
                                llm.setOrientation(LinearLayoutManager.VERTICAL);
                                listView.setAdapter(documentListAdapter);
                                empty_value.setVisibility(View.GONE);
                                documentListAdapter.notifyDataSetChanged();

                            }
                        } else {
                            empty_value.setVisibility(View.VISIBLE);
                          //  documentListAdapter.notifyDataSetChanged();
                            //  UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //  UtileKit.dismisssSpinnerDialog();
                }

                @Override
                public void onFailure(Call<GetDocumentModels> call, Throwable t) {
                    //Log.e("CallBack", " failure is " + t);
                    UtileKit.dismisssSpinnerDialog();
                    UtileKit.alertRetrofitExceptionDialog(mContext, t);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.layoutGallery:
                layoutGallery.setVisibility(View.GONE);
                layoutfile.setVisibility(View.GONE);
                layout_view.setVisibility(View.GONE);

                if(isReadStorageAllowed()){

                    if(typeofString.equalsIgnoreCase("Tax")) {
                        if(getDocumentModels.getData().getSec_prod()!=null &&
                                getDocumentModels.getData().getSec_prod().size()!=0
                                &&!getDocumentModels.getData().getSec_prod().isEmpty()) {
                            spinnerChecklistDocument("layoutGallery",getDocumentModels);
                        }else {
                            UtileKit.intitializeAlertDialog("No details to retrieve", mContext);
                        }

                    }else {
                        chooseImage();
                    }


                    return;
                }
                requestStoragePermission();
                break;
            case R.id.layoutfile:
                layoutGallery.setVisibility(View.GONE);
                layoutfile.setVisibility(View.GONE);
                layout_view.setVisibility(View.GONE);

                if(isReadStorageAllowed()){
                    if(typeofString.equalsIgnoreCase("Tax")) {
                        if(getDocumentModels.getData().getSec_prod()!=null && getDocumentModels.getData().getSec_prod().size()!=0
                                &&!getDocumentModels.getData().getSec_prod().isEmpty()) {
                            spinnerChecklistDocument("layoutfile", getDocumentModels);
                        }else {
                            UtileKit.intitializeAlertDialog("No details to retrieve", mContext);
                        }
                    }else {
                        fileGetFromStorage();
                    }
                    return;
                }
                requestStoragePermission();
                break;
        }
    }

    @Override
    public void onImageChosen(ChosenImage chosenImage) {
        Log.i("ChosenImage","ChosenImage " + chosenImage);

        if (chosenImage != null) {

            new DownloadAnonymousTask().execute(chosenImage.getFilePathOriginal());
        }
    }

    @Override
    public void onVideoChosen(ChosenVideo chosenVideo) {

    }

    @Override
    public void onError(String reason) {
        Log.i("ChosenImage","ChosenImage reason" + reason);
    }

    @Override
    public void onVideosChosen(ChosenVideos chosenVideos) {

    }

    @Override
    public void onImagesChosen(ChosenImages chosenImages) {

    }


}



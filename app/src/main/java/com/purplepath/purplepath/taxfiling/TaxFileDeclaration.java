package com.purplepath.purplepath.taxfiling;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.ContactUs.ContactusFragment;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.dialog.AssetDatePickerDialogFragment;
import com.purplepath.purplepath.calenderNumberPicker.calenderUi.CalenderTabs;
import com.purplepath.purplepath.customview.CalendarEditText;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxfiling.addsessionmodel.AddSessionModel;
import com.purplepath.purplepath.taxfiling.credencialmodel.CredencialModel;
import com.purplepath.purplepath.taxfiling.getUserStatusModel.UserStatusModel;
import com.purplepath.purplepath.taxfiling.getdeclarationmodel.DeclarationModel;
import com.purplepath.purplepath.taxfiling.getplanamtmodel.PlanAmountModel;
import com.purplepath.purplepath.taxfiling.gpsLocation.SingleShotLocationProvider;
import com.purplepath.purplepath.taxfiling.uploadtaxfiles.TaxFileCheckListFragment;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;

/**
 * Created by pravinr on 3/22/18.
 */

public class TaxFileDeclaration extends BaseFragment implements View.OnClickListener ,DatePickerCallBackInterface {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private EditText mCalanderEdt;

    private CharacterEditText name_Edt,surname_Edt,place_Edt,pan_Edt;

    private CalendarEditText date_Edt;

    private FloatingActionButton fab_declaration;

    CalenderTabs mcalenderTabDialog;

    private String timeStamp;

    private TextView capacity_text;


    private Bundle args=new Bundle();

    private String planAmount="";

    private String add_invest="",nonForm16Session="",prepaid_tax_status="";


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
        View view=inflater.inflate(R.layout.fragment_taxfiling_declaration, container, false);
        backPressedListener.setActionBarTitle("Declaration");

        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        name_Edt= (CharacterEditText) view.findViewById(R.id.name_Edt);
        surname_Edt= (CharacterEditText) view.findViewById(R.id.surname_Edt);
        place_Edt= (CharacterEditText) view.findViewById(R.id.place_Edt);
        pan_Edt= (CharacterEditText) view.findViewById(R.id.pan_Edt);

        capacity_text= (TextView) view.findViewById(R.id.capacity_text);
        capacity_text.setText("Individual");


        date_Edt= (CalendarEditText) view.findViewById(R.id.date_Edt);
        editTextDrawableClick(date_Edt);

        fab_declaration = (FloatingActionButton) view.findViewById(R.id.fab_declaration);
        fab_declaration.setOnClickListener(this);
        try {
            timeStamp = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        }catch (Exception e){
            e.printStackTrace();
        }


        String dateStr = parseDate(timeStamp);
        date_Edt.setText(dateStr);

        setInputValues();
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

                        add_invest=userStatusModel.getData().getPage_visited_array().get(0).getAdd_invest();
                        nonForm16Session = userStatusModel.getData().getPage_visited_array().get(0).getNon_form16();
                        planAmount=userStatusModel.getData().getPage_visited_array().get(0).getPlan_amount();
                        prepaid_tax_status=userStatusModel.getData().getPrepaid_tax_status();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // callGetPlanAmountService();
    }


    private void callGetPlanAmountService() {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<PlanAmountModel> call = webServiceObj.callGetPlanAmountService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new retrofit2.Callback<PlanAmountModel>() {
            @Override
            public void onResponse(Call<PlanAmountModel> call, Response<PlanAmountModel> response) {
                UtileKit.dismisssSpinnerDialog();
                PlanAmountModel userStatusModel = response.body();
                try {
                    if (userStatusModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        if(userStatusModel.getData().getResponse_array().get(0).getPlan_amount()!=null){
                          //  planAmount=userStatusModel.getData().getResponse_array().get(0).getPlan_amount();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PlanAmountModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }


    public String parseDate(String time) {
        String str = null;
        DateFormat oldFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy ");
        Date oldDate = null;
        try {
            oldDate = (Date)oldFormatter .parse(time);
            str =formatter.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(formatter.format(oldDate));

        return str;
    }
    public String parseDateReverse(String time) {
        String str = null;
        DateFormat oldFormatter = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date oldDate = null;
        try {
            oldDate = (Date)oldFormatter .parse(time);
            str =formatter.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(formatter.format(oldDate));

        return str;
    }
    public void editTextDrawableClick(final CalendarEditText EdtText) {

        EdtText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    edtTextonClick(EdtText);
                    return true;
                }
                return false;
            }
        });
    }
    private void setEdtText(EditText edttext) {
        this.mCalanderEdt = edttext;
    }
    private EditText getEdtText() {
        return mCalanderEdt;
    }
    public void edtTextonClick(CalendarEditText EdtText) {
        try {
            if (EdtText == date_Edt) {
                setEdtText(date_Edt);
                String date=EdtText.getText().toString();
                if (UtileKit.validateObjectValues(date)) {
                    if (UtileKit.removeDefaultValue(date)) {
                        if (date.trim().length() != 0) {
                            CalenderTabs mcalenderTabs =  CalenderTabs.newInstance(this,
                                    "Date",Boolean.TRUE,Boolean.FALSE,Boolean.FALSE,"1", date);
                            if(UtileKit.validateObjectValues(date)) {
                                args.putString(AssetDatePickerDialogFragment.FULLDATE, date);
                                mcalenderTabs.setArguments(args);
                            }
                            mcalenderTabs.show(getFragmentManager(),"Date");

                        }
                    }
                }

            }
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        } catch (Exception e) {
        }
    }
    @Override
    public void onResume() {
        super.onResume();


        Bundle bundle = new Bundle();
        mFirebaseAnalytics.setCurrentScreen(getActivity(),getString(R.string.analtics_taxdeclaration_screen),getActivity().getClass().getSimpleName());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "7");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Tax Module");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Tax");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);



        if (Build.VERSION.SDK_INT >= 23) {
            int rcon = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

            if (rcon == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");

                if(isgpsonoroff()) {

                    firstloacation(getActivity());
                }else {
                    displayPromptForEnablingGPS(getActivity());
                }

            } else {

                Log.v("TAG","Permission is revoked");
                requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            if(isgpsonoroff()) {

                firstloacation(getActivity());
            }else {
                displayPromptForEnablingGPS(getActivity());
            }
        }
    }
    public void firstloacation(Context context) {
        // when you need location
        // if inside activity context = this;

        SingleShotLocationProvider.requestSingleUpdate(context,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        Log.d("Location", "my location is " + location.latitude+" "+location.longitude);
                        initializeall(location.longitude,location.latitude);
                    }
                });
    }
    private void initializeall(float lon,float lat) {

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // addresses.get(0).
        String cityName = addresses.get(0).getLocality();
//        String stateName = addresses.get(0).getAddressLine(1);
//        String countryName = addresses.get(0).getAddressLine(2);

        place_Edt.setText(cityName);

    }
    public static void displayPromptForEnablingGPS(final Context activity)
    {

        final AlertDialog.Builder builder =  new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Enable the GPS";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }

    boolean isgpsonoroff(){

        LocationManager manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE );
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return statusOfGPS;

    }
    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void updateEditTextValue(String value, String title) {

        try {
            if (UtileKit.validateObjectValues(value)) {

                timeStamp = parseDateReverse(value);

                date_Edt.setText(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void updateIndividualEditTextValue(String value, String title) {

    }


    //Get the value from web service
    private void setInputValues() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<DeclarationModel> call = webServiceObj.callGetTaxFileDeclarationService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<DeclarationModel>() {
            @Override
            public void onResponse(Call<DeclarationModel> call, Response<DeclarationModel> response) {
                UtileKit.dismisssSpinnerDialog();
                DeclarationModel declarationModel = response.body();
                try{
                    if (declarationModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        String capacity="";

                        String firstname = declarationModel.getData().getDec_details().get(0).getFirst_name();
                        String sur_name = declarationModel.getData().getDec_details().get(0).getSurname();
                        String pan_no = declarationModel.getData().getDec_details().get(0).getPan_no();

                        if(declarationModel.getData().getDec_details().get(0).getCapacity()!=null) {
                            capacity = declarationModel.getData().getDec_details().get(0).getCapacity();
                        }else if(declarationModel.getData().getDec_details().get(0).getCapacity()==null){
                            capacity="Individual";
                        }


                        name_Edt.setText(firstname);
                        surname_Edt.setText(sur_name);
                        pan_Edt.setText(pan_no);
                        capacity_text.setText(capacity);


                    }
                    else {
                        UtileKit.intitializeAlertDialog(declarationModel.getData().getMessage(), mContext);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<DeclarationModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
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

            case R.id.fab_declaration:

                String mFirstname = name_Edt.getText().toString();
                String mSurname = surname_Edt.getText().toString();
                String mPlace = place_Edt.getText().toString();
                String mDate = timeStamp;
                String mPan_no = pan_Edt.getText().toString();
                String mcapacity="Individual";


                validateUserCredentials(mFirstname,mSurname,mPlace,mDate,mPan_no,mcapacity);

                break;




        }

    }

    private void validateUserCredentials(String mFirstname, String mSurname, String mPlace, String mDate, String mPan_no,
                                         String mcapacity) {

        if(UtileKit.validateObjectValues(mFirstname)){
            if (UtileKit.validateObjectValues(mSurname)) {
                    if (UtileKit.validateObjectValues(mPlace)) {
                        if (UtileKit.validateObjectValues(mDate)) {
                            if (UtileKit.validateObjectValues(mPan_no)) {
                                getInputValues(mFirstname, mSurname, mPlace, mDate, mPan_no, mcapacity);
                            } else {
                                UtileKit.intitializeAlertDialog(getString(R.string.taxfilingpan), mContext);
                            }
                        } else {
                            UtileKit.intitializeAlertDialog(getString(R.string.taxfilingdate), mContext);
                        }
                    } else {
                        UtileKit.intitializeAlertDialog(getString(R.string.taxfilingplace), mContext);
                    }

            }
            else {
                UtileKit.intitializeAlertDialog(getString(R.string.taxfilingsurname),mContext);
            }
        } else{
            UtileKit.intitializeAlertDialog(getString(R.string.taxfilingfirst),mContext);
        }
    }





    //Get the value from user input
    private void getInputValues(String firstname,String surname,String place,String Date,String pans,String capacity) {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<DeclarationModel> call = webServiceObj.callAddTaxFileDeclarationService(UtileKit.getPersistedPurplePathPref("user_id"),
                firstname,surname,place,Date,pans,capacity);
        call.enqueue(new Callback<DeclarationModel>() {
            @Override
            public void onResponse(Call<DeclarationModel> call, Response<DeclarationModel> response) {
                UtileKit.dismisssSpinnerDialog();
                DeclarationModel declarationmodel = response.body();
                try {
                    if (declarationmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        //addFragmenttoStack(new TaxFilingCredential());

                        if(add_invest.equalsIgnoreCase("Y")){
                            addFragmenttoStack(new TaxFilingCredential());
                        }
                        else if(nonForm16Session.equalsIgnoreCase("Y")){

                            addFragmenttoStack(new TaxFilingCredential());
                        }
                        else if(add_invest.equalsIgnoreCase("N")&&planAmount.equalsIgnoreCase("199")){
                            addFragmenttoStack(new TaxFilingCredential());
                        }

                        else if(planAmount.equalsIgnoreCase("1")){
                             if(prepaid_tax_status.equalsIgnoreCase("Y")){
                                 callCredentialsService();
                             }else {
                                 prepaidTaxStatusDialog(mContext);
                             }
                        }


                    } else {
                        UtileKit.intitializeAlertDialog(declarationmodel.getData().getMessage(), mContext);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<DeclarationModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }


    private void prepaidTaxStatusDialog(Context context) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater= LayoutInflater.from(context);
        dialogView=inflater.inflate(R.layout.alert_message_layout,null);
        alertDialog=new androidx.appcompat.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        TextView stringErrorMessage = dialogView.findViewById(R.id.textViewDilog);
        stringErrorMessage.setText("We found you have not paid any prepaid tax. Please contact our support for more assistance.");
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddTaxfileSessionServiceTaxPrepaid("users_tax_file_page_visited_status","free_user_flow_completed","Y");

                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }

    private void AddTaxfileSessionServiceTaxPrepaid(String corresponding_table, String field_name, String field_value) {

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

                        addFragmenttoStack(new ContactusFragment());
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
    //Create XML IRT file
    private void  callCredentialsService() {

        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<CredencialModel> call = webServiceObj.callCreateXmlfileService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<CredencialModel>() {
            @Override
            public void onResponse(Call<CredencialModel> call, Response<CredencialModel> response) {
                UtileKit.dismisssSpinnerDialog();
                CredencialModel declarationmodel = response.body();
                try{
                if (declarationmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    //addFragmenttoStack(new TaxFilingThankyouFragment());
                    AddTaxfileSessionService("users_tax_file_page_visited_status","itr_xml_generated","Y");
                }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CredencialModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

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

                        addFragmenttoStack(new TaxFilingThankyouFragment());

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

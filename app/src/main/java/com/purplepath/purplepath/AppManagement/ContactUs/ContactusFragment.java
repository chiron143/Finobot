package com.purplepath.purplepath.AppManagement.ContactUs;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.net.URLEncoder;
import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.finobot.finobot.R.id.location_icon;


public class ContactusFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;

    private TextView mtxt_address,mtxt_callnow,mtxt_sendmail,mtxt_viewmap,
                     mtxt_address1,mtxt_callnow1,mtxt_sendmail1,mtxt_viewmap1;


    private String bangaloreno="+918884400678";
    private Context mContext;

    private Double chennai_lat=13.071108;
    private Double chennai_long=80.254247;
    private String chennai_markerText="Chennai location";

    private Double bangalore_lat=12.980504;
    private Double bangalore_long=77.641711;
    private String bangalore_markerText="Bangalore location";

    private int STORAGE_PERMISSION_CODE = 23;

    //static final Integer CALL = 0x2;

    private String whatsapp_no="8884400678";

    private TextView mtxt_whatsapp;

    private String statemets="";

    private TextView tax_statement;

//    public static ContactusFragment newInstance(String statemets) {
//        ContactusFragment contactusFragment = new ContactusFragment();
//        Bundle args = new Bundle();
//
//        if (statemets != null) {
//            args.putSerializable("statemets", statemets);
//        }
//        contactusFragment.setArguments(args);
//        return contactusFragment;
//    }

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        mContext=getContext();
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contactus, container, false);

//        if(getArguments().containsKey("statemets")) {
//            statemets = getArguments().getString("statemets");
//        }


        backPressedListener.setActionBarTitle("Contact Us");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);



        mtxt_address= view.findViewById(R.id.txt_address);
        mtxt_callnow= view.findViewById(R.id.txt_callnow);
        mtxt_sendmail= view.findViewById(R.id.txt_sendmail);
        mtxt_whatsapp= view.findViewById(R.id.txt_whatsapp);
        mtxt_viewmap= view.findViewById(R.id.txt_viewmap);
        mtxt_address.setText("#863, 5th Main, 5th Cross,\n"+"\n"+"1st Stage, Indiranagar,\n"+"\n"+"Bangalore - 560038\n"+"\n"+"Karnataka.");

        mtxt_address1= view.findViewById(R.id.txt_address1);
        mtxt_callnow1= view.findViewById(R.id.txt_callnow1);
        mtxt_sendmail1= view.findViewById(R.id.txt_sendmail1);
        mtxt_viewmap1= view.findViewById(R.id.txt_viewmap1);
        mtxt_address1.setText("1-E, Elcanso Complex,\n"+"\n"+"10, Casa Major Road,\n"+"\n"+"Egmore,\n"+"\n"+"Chennai – 600008.");
        mtxt_viewmap1= view.findViewById(R.id.txt_viewmap1);

        mtxt_callnow.setOnClickListener(this);
        mtxt_callnow1.setOnClickListener(this);
        mtxt_sendmail.setOnClickListener(this);
        mtxt_sendmail1.setOnClickListener(this);

        mtxt_whatsapp.setOnClickListener(this);

        mtxt_viewmap.setOnClickListener(this);
        mtxt_viewmap1.setOnClickListener(this);

        UtileKit.setSvgEdittextDrawableLeft(mtxt_callnow,mContext,R.drawable.ic_call_now);
        UtileKit.setSvgEdittextDrawableLeft(mtxt_sendmail,mContext,R.drawable.ic_email_svg);
        UtileKit.setSvgEdittextDrawableLeft(mtxt_whatsapp,mContext,R.drawable.ic_whatsapp_icon);
        UtileKit.setSvgEdittextDrawableLeft(mtxt_viewmap,mContext,R.drawable.ic_maps);

        UtileKit.setSvgEdittextDrawableLeft(mtxt_callnow1,mContext,R.drawable.ic_call_now);
        UtileKit.setSvgEdittextDrawableLeft(mtxt_sendmail1,mContext,R.drawable.ic_email_svg);
        UtileKit.setSvgEdittextDrawableLeft(mtxt_viewmap1,mContext,R.drawable.ic_maps);






        return view;
    }
    private void alreadyDoneAlertDialog(Context context) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;

        inflater= LayoutInflater.from(context);
        dialogView=inflater.inflate(R.layout.alert_message_layout,null);
        alertDialog=new androidx.appcompat.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        TextView stringErrorMessage = dialogView.findViewById(R.id.textViewDilog);
        stringErrorMessage.setText("If you were eligible to file Tax returns last Financial year, but did not file returns, we cannot file the returns for this Financial year. Please contact us to file last year's returns");
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.txt_callnow:
                if (isReadStorageAllowed()) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + bangaloreno));
                    startActivity(callIntent);
                }
               else {
                    requestStoragePermission();
                }

                break;
            case R.id.txt_sendmail:

                //String emails = "support@finobot.com";
                String emails = "engage@finobot.com";

                if (UtileKit.validateObjectValues(emails)) {
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setData(Uri.parse("mailto:" + emails));
                    try {
                        mContext.startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(mContext, "No email clients installed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mContext, "No Email ID given", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.txt_callnow1:

                if (isReadStorageAllowed()) {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:" + bangaloreno));
                        startActivity(callIntent);
                    }else {
                    requestStoragePermission();
                }
                break;

            case R.id.txt_viewmap:
                Fragment fragment= com.purplepath.purplepath.AppManagement.ContactUs.MapsFragment.newInstance(bangalore_lat,bangalore_long,bangalore_markerText);
                addFragmenttoStack(fragment);
                break;

            case R.id.txt_sendmail1:
               // sendEmail();



              //  String email = "support@finobot.com";
                String email = "engage@finobot.com";

                if (UtileKit.validateObjectValues(email)) {
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setData(Uri.parse("mailto:" + email));
                    try {
                        mContext.startActivity(i);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(mContext, "No email clients installed", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mContext, "No Email ID given", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;

            case R.id.relative_center_home:
                startSettingHomeActivity();
                break;

            case R.id.txt_viewmap1:
                //Toast.makeText(mContext,"onClick",Toast.LENGTH_SHORT).show();
                fragment= com.purplepath.purplepath.AppManagement.ContactUs.MapsFragment.newInstance(chennai_lat,chennai_long,chennai_markerText);
                addFragmenttoStack(fragment);
                break;
            case R.id.txt_whatsapp:

                if (isReadStorageAllowed1()) {
                    openWhatsApp(whatsapp_no);
                }else {
                    requestStoragePermission1();
                }
                break;

        }
    }



    private void openWhatsApp(String no) {

        boolean installed = appInstalledOrNot("com.whatsapp");
        if(installed) {
            System.out.println("App is already installed on your phone");

            String bl = getContactName(whatsapp_no,mContext);

            if(!bl.equals("")) {

                PackageManager packageManager = getApplicationContext().getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);

                try {
                    String url = "https://api.whatsapp.com/send?phone=+91"+ whatsapp_no +"&text=" + URLEncoder.encode("", "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (i.resolveActivity(packageManager) != null) {
                        mContext.startActivity(i);
                    }
                } catch (Exception e){
                    //Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                createcontact();
            }
        } else {
            Toast.makeText(getApplicationContext(),"WhatsApp is not currently installed on your phone",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = mContext.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public String getContactName(final String phoneNumber,Context context)
    {
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));

        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName="";
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName=cursor.getString(0);
            }
            cursor.close();
        }

        return contactName;
    }

    public void createcontact(){

        String DisplayName = "Finobot";
        String MobileNumber = whatsapp_no;

        ArrayList<ContentProviderOperation> ops = new ArrayList < ContentProviderOperation > ();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        //------------------------------------------------------ Names
        if (DisplayName != null) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            DisplayName).build());
        }

        //------------------------------------------------------ Mobile Number
        if (MobileNumber != null) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        try {
            mContext.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);

            new Handler().postDelayed(new Runnable() {


                @Override
                public void run() {

                    openWhatsApp(whatsapp_no);

                }
            }, 2500);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }





    private boolean isReadStorageAllowed() {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)mContext,Manifest.permission.CALL_PHONE)){
        }
        ActivityCompat.requestPermissions((Activity)mContext,new String[]{Manifest.permission.CALL_PHONE},STORAGE_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }else{
            }
        }
    }




    private boolean isReadStorageAllowed1() {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_CONTACTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestStoragePermission1(){

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)mContext,Manifest.permission.WRITE_CONTACTS)){
        }
        ActivityCompat.requestPermissions((Activity)mContext,new String[]{Manifest.permission.WRITE_CONTACTS},STORAGE_PERMISSION_CODE);
    }




}

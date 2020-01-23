package com.purplepath.purplepath.AppManagement.Vendors;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Vendors.Models.VendorDetailsModel;
import com.purplepath.purplepath.AppManagement.Vendors.adapters.VendorAdapter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.document.Interface.OnItemClickListenerInterface;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.getPersistedPurplePathPref;

/**
 * @ author: Pratheep.S
 */
public class VendorFragment extends BaseFragment implements View.OnClickListener, OnItemClickListenerInterface {

    Context mContext;
    private VendorAdapter vendorAdapter;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout,mRightRelativeLayout;
    private OnActivityBackPressedListener backPressedListener;
    OnItemClickListenerInterface getpositionInterface;

    VendorDetailsModel vendorDetailsModel;

    //Permision code that will be checked in the method onRequestPermissionsResult
    private int STORAGE_PERMISSION_CODE = 23;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        getpositionInterface = this;
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        backPressedListener= (OnActivityBackPressedListener) context;

    }

    public void setListener(OnItemClickListenerInterface callbackInterface){
        this.getpositionInterface=callbackInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_vendor, container, false);
        recyclerView= view.findViewById(R.id.vendorRecyclerView);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        layoutManager=new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        backPressedListener.setActionBarTitle("Vendors");

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        callVendorDetailsWebService();
        return view;
    }

    private void callVendorDetailsWebService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls obj=ServiceGenerator.createService(WebServiceCalls.class);
        Call<VendorDetailsModel> call=obj.getVendorDetailsService(getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<VendorDetailsModel>() {
            @Override
            public void onResponse(Call<VendorDetailsModel> call, Response<VendorDetailsModel> response) {
                UtileKit.dismisssSpinnerDialog();
                vendorDetailsModel=response.body();
                if(vendorDetailsModel.getStatus_code().equals(UtileKit.SUCCESSCODE)&& vendorDetailsModel.getData().getVendors().size()>0){

                    vendorAdapter=new VendorAdapter(vendorDetailsModel,mContext, getpositionInterface);

                    recyclerView.setAdapter(vendorAdapter);
                }

            }

            @Override
            public void onFailure(Call<VendorDetailsModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();

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
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }
    }

    @Override
    public void onClick(View view, int position) {

            if (isReadStorageAllowed()) {
                //If permission is already having then showing the toast
//                Toast.makeText(mContext, "You already have the permission", Toast.LENGTH_LONG).show();
                //Existing the method with return
                if(vendorDetailsModel.getData().getVendors().get(position).getPhone_number()!= null) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + vendorDetailsModel.getData().getVendors().get(position).getPhone_number()));
                    startActivity(callIntent);
                }
                return;
            }

            //If the app has not the permission then asking for the permission
            requestStoragePermission();
        }

    @Override
    public void onClick(View view, int position, String name, String positin) {

    }


    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }


    //Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)mContext,Manifest.permission.CALL_PHONE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions((Activity)mContext,new String[]{Manifest.permission.CALL_PHONE},STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
//                Toast.makeText(mContext,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();

            }else{
                //Displaying another toast if permission is not granted
//                Toast.makeText(mContext,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }
}

package com.purplepath.purplepath.insurance.adapter;

/**
 * Created by Bert on 05-Jul-16.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.AppConstants;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.goal.GoalFamilyDetailsModel;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.dialog.InsuranceDialogFragment;
import com.purplepath.purplepath.insurance.insuranceinterface.OnCheckListIsEmpty;
import com.purplepath.purplepath.insurance.insuranceinterface.OndeleteUpdateList;
import com.purplepath.purplepath.insurance.model.DeleteInsuranceModel;
import com.purplepath.purplepath.insurance.model.GetInsuranceInputData;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LifeInsuranceListAdapter extends ArrayAdapter<GetInsuranceInputData> {
    Context context;
    ArrayList<GetInsuranceInputData> getLifeInsuranceUserData;
    OnCheckListIsEmpty onCheckListIsEmpty;
    OndeleteUpdateList ondeleteUpdateList;
    GoalFamilyDetailsModel addFamilyDetailModel;
    LayoutInflater inflater;
    View dialogView;
    TextView stringMessageText;
    android.support.v7.app.AlertDialog alertDialog;
    FirstTimeDoneInterface firstTimeDoneInterface;
    public LifeInsuranceListAdapter(Context context, ArrayList<GetInsuranceInputData> getLifeInsUserData,
                                    OnCheckListIsEmpty onCheckListIsempty, OndeleteUpdateList ondeleteUpdate,
                                    FirstTimeDoneInterface firstTimeDoneInterface) {
        super(context,0, getLifeInsUserData);
        this.context = context;
        getLifeInsuranceUserData = getLifeInsUserData;
        onCheckListIsEmpty = onCheckListIsempty;
        ondeleteUpdateList = ondeleteUpdate;
        this.firstTimeDoneInterface=firstTimeDoneInterface;
    }

    @Override
    public int getCount() {
        return getLifeInsuranceUserData.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String goalNameStr, goalTypeNameStr, goalYearsStr;

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_asset_row_item, parent, false);
        TextView assetName = convertView.findViewById(R.id.assets_row_name_rowitem);
        TextView assetType = convertView.findViewById(R.id.assets_row_type_rowitem);
        TextView assetcurrentValue = convertView.findViewById(R.id.assets_row_currentvalue_rowitem);
        ImageView editImageView = convertView.findViewById(R.id.asset_edit_rowitem);
        ImageView deleteImageView = convertView.findViewById(R.id.asset_delete_rowitem);
        LinearLayout detilView= convertView.findViewById(R.id.assets_row_topic_layout);
        detilView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLifeInsuranceDialog(position);
            }
        });
        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLifeInsuranceDialog(position);
            }
        });

        deleteImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(UtileKit.validateObjectValues(getLifeInsuranceUserData.get(position).getId()) &&
                        UtileKit.validateObjectValues(getLifeInsuranceUserData.get(position).getUser_id())){
                    alertButtonDialogYesNo(getLifeInsuranceUserData.get(position).getId(),
                            getLifeInsuranceUserData.get(position).getUser_id(), position);
                }
            }
        });

        if(UtileKit.validateObjectValues(getLifeInsuranceUserData.get(position).getPolicy_name())){
            assetName.setText(getLifeInsuranceUserData.get(position).getPolicy_name());
        }


        if(UtileKit.validateObjectValues(getLifeInsuranceUserData.get(position).getAnnual_prem())){
            assetType.setText(getLifeInsuranceUserData.get(position).getAnnual_prem());
        }

        if(UtileKit.validateObjectValues(getLifeInsuranceUserData.get(position).getCoverage())){
            assetcurrentValue.setText(getLifeInsuranceUserData.get(position).getCoverage());
        }

        return convertView;
    }

    private void getLifeInsuranceDialog(int position) {
        if(UtileKit.validateObjectValues(getLifeInsuranceUserData.get(position).getId()) &&
                UtileKit.validateObjectValues(getLifeInsuranceUserData.get(position).getUser_id())) {
            Bundle args = new Bundle();
            args.putString("ins_id", getLifeInsuranceUserData.get(position).getId());
            args.putString("userid", getLifeInsuranceUserData.get(position).getUser_id());
            args.putString("type", getLifeInsuranceUserData.get(position).getIns_type());
            args.putInt("size", getLifeInsuranceUserData.size());
            args.putSerializable("Insuranceobject", getLifeInsuranceUserData.get(position));
            InsuranceDialogFragment insuranceDialogFragment = InsuranceDialogFragment.newInstance(onCheckListIsEmpty,
                    addFamilyDetailModel, firstTimeDoneInterface);
            insuranceDialogFragment.setArguments(args);
            insuranceDialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
        }
    }

    private void alertButtonDialogYesNo(String insuranceId,  String userId , int position) {

        passalertButtonDialogYesNo(HomePageActivity.stringMessageError, context, insuranceId, userId, position);
    }

    private void passalertButtonDialogYesNo(String message, Context context, final String insuranceId, final String userId, final int position) {

//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage(message);
//        builder.setPositiveButton(context.getString(R.string.dialog_no),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        // TODO Auto-generated method stub
//
//                    }
//                });
//
//        builder.setNegativeButton(
//                context.getString(R.string.dialog_yes),
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        // TODO Auto-generated method stub
//                        deleteInsurance(insuranceId, userId, position);
//
//                    }
//                });
//        builder.setCancelable(true);
//        AlertDialog alert = builder.show();
//        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
//        //nbutton.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//        //pbutton.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//        TextView messageText = (TextView) alert.findViewById(android.R.id.message);
//        messageText.setGravity(Gravity.CENTER);


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

                deleteInsurance(insuranceId, userId, position);
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


    private void deleteInsurance(String insuranceId, String userId, int position) {
        deleteInsuranceService(insuranceId, userId, position);
    }

    private void deleteInsuranceService(String insuranceId, String userId, final int position) {
        UtileKit.showSpinnerDialog(context, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<DeleteInsuranceModel> call = webServiceObj.callDeleteInsuranceListService(insuranceId, userId);
        call.enqueue(new Callback<DeleteInsuranceModel>() {
            @Override
            public void onResponse(Call<DeleteInsuranceModel> call, Response<DeleteInsuranceModel> response) {
                //Log.e("CallBack", " success is " + response.body());
              //  getLifeInsuranceUserData.remove(position)
                ondeleteUpdateList.checkListSize(getLifeInsuranceUserData.size());
                getLifeInsuranceUserData.remove(position);
                notifyDataSetChanged();
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<DeleteInsuranceModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( context,t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }

}


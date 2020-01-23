package com.purplepath.purplepath.assets.adapter;

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
import android.widget.TextView;

import com.google.gson.Gson;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.AppConstants;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.dialog.AssetDialogFragment;
import com.purplepath.purplepath.assets.model.AssetCategoriesLevelOne;
import com.purplepath.purplepath.assets.model.AssetCategoriesModel;
import com.purplepath.purplepath.assets.model.DeleteAssetModel;
import com.purplepath.purplepath.assets.model.GetAssetUserData;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.insuranceinterface.OnCheckListIsEmpty;
import com.purplepath.purplepath.insurance.insuranceinterface.OndeleteUpdateList;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhysicalAssetsListAdapter extends ArrayAdapter<GetAssetUserData> {
    Context context;
    ArrayList<GetAssetUserData> getassetsUserDataModel;
    OnCheckListIsEmpty onCheckListIsEmpty;
    OndeleteUpdateList ondeleteUpdateList;
    String assetCat;
    Gson gson = new Gson();
    AssetCategoriesModel assetCategoriesModel;
    private ArrayList<AssetCategoriesLevelOne> assetCategoriesLevelOneListData = new ArrayList<AssetCategoriesLevelOne>();
    LayoutInflater inflater;
    View dialogView;
    android.support.v7.app.AlertDialog alertDialog;
    Boolean isSignUp = false;
    ArrayList<String> formArray = new ArrayList<String>();
    FirstTimeDoneInterface firstTimeDoneInterface;

    public PhysicalAssetsListAdapter(Context context, ArrayList<GetAssetUserData> goalListModel,
                                     OnCheckListIsEmpty onCheckListIsempty, OndeleteUpdateList
                                             ondeleteUpdate, FirstTimeDoneInterface firstTimeDoneInterface) {
        super(context,0, goalListModel);
        this.context = context;
        getassetsUserDataModel = goalListModel;
        onCheckListIsEmpty = onCheckListIsempty;
        ondeleteUpdateList = ondeleteUpdate;
         this.firstTimeDoneInterface= firstTimeDoneInterface;

        assetCat=UtileKit.getPersistedPurplePathPref("AssetCat");
    }

    @Override
    public int getCount()
    {
        return getassetsUserDataModel.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String goalNameStr, goalTypeNameStr, goalYearsStr;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.assert_row_view_adapter, parent, false);
        }
        TextView assetName = convertView.findViewById(R.id.assets_row_name_rowitem);
        TextView assetType = convertView.findViewById(R.id.assets_row_type_rowitem);
        TextView assetcurrentValue = convertView.findViewById(R.id.assets_row_currentvalue_rowitem);

        ImageView editImageView = convertView.findViewById(R.id.asset_edit_rowitem);
        ImageView deleteImageView = convertView.findViewById(R.id.asset_delete_rowitem);

        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UtileKit.validateObjectValues(getassetsUserDataModel.get(position).getId()) &&
                        UtileKit.validateObjectValues(getassetsUserDataModel.get(position).getUser_id())) {

                    Bundle args = new Bundle();
                    args.putString("assetid", getassetsUserDataModel.get(position).getId());
                    args.putString("userid", getassetsUserDataModel.get(position).getUser_id());
                    args.putString("type", "Physical");
                    args.putSerializable("assetobject", getassetsUserDataModel.get(position));
                    AssetDialogFragment assetDialogFragment = AssetDialogFragment.newInstance(onCheckListIsEmpty,firstTimeDoneInterface);
                    assetDialogFragment.setArguments(args);
                    assetDialogFragment.show(((FragmentActivity) context).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);

                }
            }
        });

        deleteImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(UtileKit.validateObjectValues(getassetsUserDataModel.get(position).getId()) &&
                        UtileKit.validateObjectValues(getassetsUserDataModel.get(position).getUser_id())){
                    alertButtonDialogYesNo(getassetsUserDataModel.get(position).getId(), getassetsUserDataModel.get(position).getUser_id(), position);
                }
            }
        });

        if(UtileKit.validateObjectValues(getassetsUserDataModel.get(position).getAsset_name())){
            assetName.setText(getassetsUserDataModel.get(position).getAsset_name());
        }


        if(UtileKit.validateObjectValues(getassetsUserDataModel.get(position).getCat_lev1_id())) {
            try {

                assetCategoriesModel = gson.fromJson(assetCat, AssetCategoriesModel.class);
                assetCategoriesLevelOneListData = assetCategoriesModel.getData().getAsset_cat_lev1();
                for (int i = 0; i < assetCategoriesLevelOneListData.size(); i++) {
                    if (assetCategoriesLevelOneListData.get(i).getId().equals(getassetsUserDataModel.get(position).getCat_lev1_id())) {
                        assetType.setText(assetCategoriesLevelOneListData.get(i).getLev1_name());
                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(UtileKit.validateObjectValues(getassetsUserDataModel.get(position).getCurrent_value())){
            try {
                assetcurrentValue.setText(getassetsUserDataModel.get(position).getCurrent_value());
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return convertView;
    }

    private void alertButtonDialogYesNo(String goalId,  String userId , int position) {

        passalertButtonDialogYesNo("Are you sure want to delete?", context, goalId, userId, position);
    }

    private void passalertButtonDialogYesNo(String message, Context context, final String goalId, final String userId, final int position) {



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

                deleteGoal(goalId, userId, position);
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


    private void deleteGoal(String goalId, String userId, int position) {
        deleteGoalService(goalId, userId, position);
    }

    private void deleteGoalService(String goalId, String userId, final int position) {
        UtileKit.showSpinnerDialog(context, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<DeleteAssetModel> call = webServiceObj.callDeleteAssetsListService(goalId, userId);
        call.enqueue(new Callback<DeleteAssetModel>() {
            @Override
            public void onResponse(Call<DeleteAssetModel> call, Response<DeleteAssetModel> response) {
                ////Log.e("CallBack", " success is " + response.body());
                getassetsUserDataModel.remove(position);
                ondeleteUpdateList.checkListSize(getassetsUserDataModel.size());
                notifyDataSetChanged();
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<DeleteAssetModel> call, Throwable t) {
                ////Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( context, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }

}


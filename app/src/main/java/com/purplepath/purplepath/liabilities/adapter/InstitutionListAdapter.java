package com.purplepath.purplepath.liabilities.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.AppConstants;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.goal.DeleteGoalsModel;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.insuranceinterface.OnCheckListIsEmpty;
import com.purplepath.purplepath.insurance.insuranceinterface.OndeleteUpdateList;
import com.purplepath.purplepath.liabilities.dialog.LiabilityDialogFragment;
import com.purplepath.purplepath.liabilities.model.LiabCategoryModel;
import com.purplepath.purplepath.liabilities.model.UserLiabilityList;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dinesh on 13/07/16.
 */
public class InstitutionListAdapter extends ArrayAdapter<UserLiabilityList> {
    Context context;
    ArrayList<UserLiabilityList> liabbUserModel;
    LiabCategoryModel mliabCategoryModel;
    String mType;
    private OnCheckListIsEmpty onCheckListIsEmptyOrNot;
    private  OndeleteUpdateList ondeleteUpdateList;
    LayoutInflater inflater;
    View dialogView;
    android.support.v7.app.AlertDialog alertDialog;

    Boolean isSignUp = false;
    ArrayList<String> formArray = new ArrayList<String>();
    FirstTimeDoneInterface firstTimeDoneInterface;

    public InstitutionListAdapter(Context mContext, ArrayList<UserLiabilityList> mInstutionList,
                                  LiabCategoryModel mliabCategoryModel, String institutions,
                                  OnCheckListIsEmpty onCheckListIsEmptyOrNot, OndeleteUpdateList
                                          ondeleteUpdateList, FirstTimeDoneInterface firstTimeDoneInterface) {
        super(mContext,0, mInstutionList);
        this.context = mContext;
        liabbUserModel =mInstutionList;
        this.mliabCategoryModel=mliabCategoryModel;
        this.onCheckListIsEmptyOrNot=onCheckListIsEmptyOrNot;
        this.ondeleteUpdateList=ondeleteUpdateList;
        this.isSignUp=isSignUp;
        this.formArray=formArray;
        mType=institutions;
        this.firstTimeDoneInterface=firstTimeDoneInterface;
    }

    @Override
    public int getCount()
    {
        return liabbUserModel.size();
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
        EditText assetcurrentValue = convertView.findViewById(R.id.assets_row_currentvalue_rowitem);

        ImageView editImageView = convertView.findViewById(R.id.asset_edit_rowitem);
        ImageView deleteImageView = convertView.findViewById(R.id.asset_delete_rowitem);

        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(UtileKit.validateObjectValues(liabbUserModel.get(position).getId()) &&
                        UtileKit.validateObjectValues(liabbUserModel.get(position).getUser_id())) {
                    LiabilityDialogFragment.newInstance(liabbUserModel.get(position),mliabCategoryModel,
                            "Institutions", onCheckListIsEmptyOrNot, isSignUp, formArray, firstTimeDoneInterface).show(((FragmentActivity) context).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
                }
            }
        });

        deleteImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(UtileKit.validateObjectValues(liabbUserModel.get(position).getId()) &&
                        UtileKit.validateObjectValues(liabbUserModel.get(position).getUser_id())){
                    alertButtonDialogYesNo(liabbUserModel.get(position).getId(), liabbUserModel.get(position).getUser_id(), position);
                }
            }
        });

        if(UtileKit.validateObjectValues(liabbUserModel.get(position).getLiab_name())){
            assetName.setText(liabbUserModel.get(position).getLiab_name());
        }


        if(UtileKit.validateObjectValues(liabbUserModel.get(position).getCat_lev1_id())) {
            if (mliabCategoryModel.getData().getLiab_cat_lev1() != null) {
                int size = mliabCategoryModel.getData().getLiab_cat_lev1().size();
                for (int i = 0; i < size; i++) {
                    if (mliabCategoryModel.getData().getLiab_cat_lev1().get(i).getId().equalsIgnoreCase(liabbUserModel.get(position).getCat_lev1_id())) {
                        assetType.setText(mliabCategoryModel.getData().getLiab_cat_lev1().get(i).getLev1_name());
                        break;
                    }
                }
            }
        }
        if(UtileKit.validateObjectValues(liabbUserModel.get(position).getLoan_amt())){
            try {

//                String loanAmount = UtileKit.currencyCunvertions(liabbUserModel.get(position).getLoan_amt());
//                Log.i("loanAmount","loanAmount from services"+ liabbUserModel.get(position).getLoan_amt());
//                Log.i("loanAmount","loanAmount "+ loanAmount);
//                assetcurrentValue.setText(loanAmount);
                assetcurrentValue.setText(liabbUserModel.get(position).getLoan_amt());
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
//                        deleteGoal(goalId, userId, position);
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
        Call<DeleteGoalsModel> call = webServiceObj.callDeleteLibListService(goalId, userId);
        call.enqueue(new Callback<DeleteGoalsModel>() {
            @Override
            public void onResponse(Call<DeleteGoalsModel> call, Response<DeleteGoalsModel> response) {
                //Log.e("CallBack", " success is " + response.body());
                liabbUserModel.remove(position);
                notifyDataSetChanged();
                ondeleteUpdateList.checkListSize(liabbUserModel.size());
                UtileKit.dismisssSpinnerDialog();

            }

            @Override
            public void onFailure(Call<DeleteGoalsModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( context,t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }

}


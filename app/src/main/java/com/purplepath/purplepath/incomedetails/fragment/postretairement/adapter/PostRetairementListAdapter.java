package com.purplepath.purplepath.incomedetails.fragment.postretairement.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
import com.purplepath.purplepath.incomedetails.fragment.model.User_incomes;
import com.purplepath.purplepath.incomedetails.fragment.postretairement.interfacepost.OnPostRetirementClick;

import java.util.ArrayList;

/**
 * Created by dinesh on 24/01/17.
 */
public class PostRetairementListAdapter extends ArrayAdapter<User_incomes> {
    private Context context;
    private ArrayList<User_incomes> preRetuserUserModel;
    private AddFamilyDetailModel addFamilyDetailModel;

//    private OndeleteUpdateList ondeleteUpdateList;
    private OnPostRetirementClick onPostRetirementClick;
    public PostRetairementListAdapter(Context context, ArrayList<User_incomes> user_incomes, AddFamilyDetailModel addFamilyDetailModel, OnPostRetirementClick onPostRetirementClick) {
        super(context,0, user_incomes);
        this.context = context;
        preRetuserUserModel =user_incomes;
        this.addFamilyDetailModel=addFamilyDetailModel;
//        this.ondeleteUpdateList=ondeleteUpdateList;
        this.onPostRetirementClick=onPostRetirementClick;

    }

    @Override
    public int getCount()
    {
        if(null!=preRetuserUserModel)
            return preRetuserUserModel.size();
        else
            return 0;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String goalNameStr, goalTypeNameStr, goalYearsStr;
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_income_row_item, parent, false);
        TextView assetName = convertView.findViewById(R.id.assets_row_name_rowitem);
        EditText assetType = convertView.findViewById(R.id.assets_row_type_rowitem);
//        CurrencyDefaultEdt assetcurrentValue = (CurrencyDefaultEdt)convertView.findViewById(R.id.assets_row_currentvalue_rowitem);

        ImageView editImageView = convertView.findViewById(R.id.asset_edit_rowitem);
        ImageView deleteImageView = convertView.findViewById(R.id.asset_delete_rowitem);
        if(null!=preRetuserUserModel.get(position).getOver_all_total())
            assetType.setText(preRetuserUserModel.get(position).getOver_all_total());
        if(preRetuserUserModel.get(position).getFamily_id().equalsIgnoreCase("0"))
        {
            assetName.setText("Self");
            editImageView.setTag("0");

        }
        else {
            if (null != addFamilyDetailModel) {
                if (null != preRetuserUserModel.get(position).getFamily_id())
                    for( int i =0 ; i < addFamilyDetailModel.getData().getFamily_details().size() ;  i ++) {
                    if (addFamilyDetailModel.getData().getFamily_details().get(i ).getFid().equalsIgnoreCase(preRetuserUserModel.get(position).getFamily_id())) {
                        assetName.setText(addFamilyDetailModel.getData().getFamily_details().get(i).getName());
                        Log.i("Self not in list","PostRetairementListAdapter assetName" +addFamilyDetailModel.getData().getFamily_details().get(i ).getName());
                        Log.i("Self not in list","PostRetairementListAdapter family id" +preRetuserUserModel.get(position).getFamily_id());
                        editImageView.setTag(preRetuserUserModel.get(position).getFamily_id());
                    }
                }
            }
        }
//        assetType.setText(liabbUserModel.get(position));
        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPostRetirementClick.onPostRetirentClick(v.getTag().toString());
                Log.i("Self not in list","IncomeListAdapter family id" +v.getTag().toString());

//                if(UtileKit.validateObjectValues(liabbUserModel.get(position).getData().getUser_incomes()))) {
//
//                }
            }
        });
        deleteImageView.setVisibility(View.INVISIBLE);
//        assetcurrentValue.setVisibility(View.GONE);
//        deleteImageView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                onIncomeClickLisaner.onIncomeClick();
//                if(UtileKit.validateObjectValues(liabbUserModel.get(position).getData().getUser_incomes().get) &&
//                        UtileKit.validateObjectValues(liabbUserModel.get(position).getUser_id())){
//                    alertButtonDialogYesNo(liabbUserModel.get(position).getId(), liabbUserModel.get(position).getUser_id(), position);
//                }
//            }
//        });
//
//        if(UtileKit.validateObjectValues(liabbUserModel.get(position).getLiab_name())){
//            assetName.setText(liabbUserModel.get(position).getLiab_name());
//        }
//
//
//        if(UtileKit.validateObjectValues(liabbUserModel.get(position).getCat_lev1_id())){
//            assetType.setText(liabbUserModel.get(position).getCat_lev1_id());
//        }
//
//        if(UtileKit.validateObjectValues(liabbUserModel.get(position).getLoan_amt())){
//            assetcurrentValue.setText(liabbUserModel.get(position).getLoan_amt());
//
//        }

        return convertView;
    }

//    private void alertButtonDialogYesNo(String goalId,  String userId , int position) {
//
//        passalertButtonDialogYesNo("Are you sure want to delete?", context, goalId, userId, position);
//    }
//
//    private void passalertButtonDialogYesNo(String message, Context context, final String goalId, final String userId, final int position) {
//
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
//    }


//    private void deleteGoal(String goalId, String userId, int position) {
//        deleteGoalService(goalId, userId, position);
//    }
//
//    private void deleteGoalService(String goalId, String userId, final int position) {
//        UtileKit.showSpinnerDialog(context, false);
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<DeleteGoalsModel> call = webServiceObj.callDeleteLibListService(goalId, userId);
//        call.enqueue(new Callback<DeleteGoalsModel>() {
//            @Override
//            public void onResponse(Call<DeleteGoalsModel> call, Response<DeleteGoalsModel> response) {
//                //Log.e("CallBack", " success is " + response.body());
//                preRetuserUserModel.remove(position);
//                notifyDataSetChanged();
//                UtileKit.dismisssSpinnerDialog();
//                ondeleteUpdateList.checkListSize(preRetuserUserModel.size());
//            }
//
//            @Override
//            public void onFailure(Call<DeleteGoalsModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
//                UtileKit.dismisssSpinnerDialog();
//            }
//        });
//        UtileKit.dismisssSpinnerDialog();
//    }

}
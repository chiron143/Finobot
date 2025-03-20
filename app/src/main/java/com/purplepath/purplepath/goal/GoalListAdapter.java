package com.purplepath.purplepath.goal;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.AppConstants;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyDefaultEdt;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.insuranceinterface.OnCheckListIsEmpty;
import com.purplepath.purplepath.myinterface.OnGoalDoneSelectedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoalListAdapter extends ArrayAdapter<GetGoalsUserData>  { // SpeakerList
	Context context;
	private OnGoalDoneSelectedListener mOnGoalDoneSelectedListener;
	ArrayList<GetGoalsUserData> getGoalsUserDataModel;
	ArrayList<GetGoalsUserData> fiterableContactsData;
	OnCheckListIsEmpty checkGoalListAdapterIsEmptyOrNot;
	GoalFamilyDetailsModel addFamilyDetailModel;
	LayoutInflater inflater;
	View dialogView;
	androidx.appcompat.app.AlertDialog alertDialog;

	FirstTimeDoneInterface firstTimeDoneInterface;

	public GoalListAdapter(Context context,GoalFamilyDetailsModel addFamilyDetailModel, ArrayList<GetGoalsUserData> goalListModel,
						   OnCheckListIsEmpty checkGoalListIsEmptyOrNot, OnGoalDoneSelectedListener mGoalDoneSelectedListener,
						   FirstTimeDoneInterface firstTimeDoneInterface) {
		super(context,0, goalListModel);
		this.context = context;
		checkGoalListAdapterIsEmptyOrNot = checkGoalListIsEmptyOrNot;
		mOnGoalDoneSelectedListener = mGoalDoneSelectedListener;
		getGoalsUserDataModel = goalListModel;
		fiterableContactsData = goalListModel;
		firstTimeDoneInterface=firstTimeDoneInterface;
		this.addFamilyDetailModel=addFamilyDetailModel;
	}

	@Override
	public int getCount()
	{
		return getGoalsUserDataModel.size();
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
			convertView = inflater.inflate(R.layout.adapter_goal_row_item, parent, false);
		}
       	TextView goalName = convertView.findViewById(R.id.goal_name_rowitem);
		CurrencyDefaultEdt goalType = convertView.findViewById(R.id.goal_type_rowitem);
		TextView goalYear = convertView.findViewById(R.id.goal_year_rowitem);
		ImageView editImageView = convertView.findViewById(R.id.goal_edit_rowitem);
		ImageView deleteImageView = convertView.findViewById(R.id.goal_delete_rowitem);

		editImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(UtileKit.validateObjectValues(getGoalsUserDataModel.get(position).getId()) &&
						UtileKit.validateObjectValues(getGoalsUserDataModel.get(position).getUser_id())) {

					Bundle args = new Bundle();
					args.putString("goalid", getGoalsUserDataModel.get(position).getId());
					args.putString("userid", getGoalsUserDataModel.get(position).getUser_id());
					args.putSerializable("object", getGoalsUserDataModel.get(position));
					args.putSerializable("GoalFamilyDetails",addFamilyDetailModel);

					GoalDetailFragment goalDetailFragment = GoalDetailFragment.newInstance(mOnGoalDoneSelectedListener,firstTimeDoneInterface);

					goalDetailFragment.setArguments(args);

					goalDetailFragment.show(((FragmentActivity) context).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
				}
			}
		});

		deleteImageView.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if(UtileKit.validateObjectValues(getGoalsUserDataModel.get(position).getId()) &&
						UtileKit.validateObjectValues(getGoalsUserDataModel.get(position).getUser_id())){
					alertButtonDialogYesNo(getGoalsUserDataModel.get(position).getId(), getGoalsUserDataModel.get(position).getUser_id(), position);
				}
			}
		});

		if(UtileKit.validateObjectValues(getGoalsUserDataModel.get(position).getGoal_name())){
			goalName.setText(getGoalsUserDataModel.get(position).getGoal_name());
		}


		if(UtileKit.validateObjectValues(getGoalsUserDataModel.get(position).getGoal_years())){
			goalYear.setText(getGoalsUserDataModel.get(position).getGoal_years());
		}
		try {
			if (UtileKit.validateObjectValues(getGoalsUserDataModel.get(position).getCost_of_goal())) {
				goalType.setText(getGoalsUserDataModel.get(position).getCost_of_goal());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}

	private void alertButtonDialogYesNo(String goalId,  String userId , int position) {

		passalertButtonDialogYesNo("Are you sure want to delete?", context, goalId, userId, position);
	}

	private void passalertButtonDialogYesNo(String message, Context context, final String goalId, final String userId, final int position) {

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
		alertDialog=new androidx.appcompat.app.AlertDialog.Builder(context).create();
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
		Call<DeleteGoalsModel> call = webServiceObj.callDeleteGoalsListService(goalId, userId);
		call.enqueue(new Callback<DeleteGoalsModel>() {
			@Override
			public void onResponse(Call<DeleteGoalsModel> call, Response<DeleteGoalsModel> response) {
				getGoalsUserDataModel.remove(position);
				checkGoalListAdapterIsEmptyOrNot.checkListSize(getGoalsUserDataModel.size());
				Log.i("spcheck", "onResponse: goalsize after delete "+getGoalsUserDataModel.size());
				notifyDataSetChanged();
				UtileKit.dismisssSpinnerDialog();
			}

			@Override
			public void onFailure(Call<DeleteGoalsModel> call, Throwable t) {
				//Log.e("CallBack", " failure is " + t);
				UtileKit.dismisssSpinnerDialog();
				UtileKit.alertRetrofitExceptionDialog( context,t);
			}
		});
	}

}


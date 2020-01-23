package com.purplepath.purplepath.goal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.finobot.finobot.activity.LoginandSignUpActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.AppConstants;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.chatprompt.PromptChatFragment1;
import com.purplepath.purplepath.chatprompt.insertmodel.InsertModel;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.customview.ExpandableListView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.fragments.EmailLoginFragment;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.incomedetails.fragment.IncomeDetail;
import com.purplepath.purplepath.insurance.insuranceinterface.OnCheckListIsEmpty;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.myinterface.OnGoalDoneSelectedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoalsListFragment extends BaseFragment implements View.OnClickListener ,
		OnCheckListIsEmpty,OnGoalDoneSelectedListener,FirstTimeDoneInterface {
	private Context mContext;
    private ExpandableListView mgoalListView;
    private GoalListAdapter mgoalsListAdapter;
    public  ArrayList<GetGoalsUserData> getGoalsUserData = new ArrayList<GetGoalsUserData>();
	private GetGoalsListModel getGoalsListModel;
    private int height = 0;
	private CustomTextView goalSortEdt, goalFilterEdt;
	private float percent_height;
	public static int j ;
	private FloatingActionButton addGoalFloatingBtn;
	private TextView addTextView;
	private OnCheckListIsEmpty checkGoalListIsEmptyOrNot;
	private OnActivityBackPressedListener mCallBackListener;
	private OnGoalDoneSelectedListener mOnGoalDoneSelectedListener;
	private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
	private static GoalFamilyDetailsModel addFamilyDetailModel;
	private ArrayList<GoalFamilyDetails> familyDetails = new ArrayList<GoalFamilyDetails>();
	private ArrayList<String> mgoalBelongsToArrayList = new ArrayList<String>();
	private ArrayList<String> mgoalBelongsToArrayListId = new ArrayList<String>();
	private RelativeLayout mAddAndShowGoal,mPictureAddGoal;

	String user_visited_flag="";
	Boolean isSignUp = false;
	ArrayList<String> formArray = new ArrayList<String>();

	private LinearLayout bottom_bar_layout,bottom_bar_donelayout;
	private RelativeLayout relative_finish_later,relative_done_arrow;

	public static int goallistsize;

	InsertModel insertModel;

	FirstTimeDoneInterface firstTimeDoneInterface;

	public static GoalsListFragment newInstance() {
		GoalsListFragment goalsListFragment = new GoalsListFragment();
		return goalsListFragment;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		callFamilyDetailsService();
		mContext = getContext();
		firstTimeDoneInterface = this;

		try {


			if (getArguments() != null) {
				if (getArguments().containsKey("IsSignUp"))
					isSignUp = getArguments().getBoolean("IsSignUp");
			}

			if (getArguments() != null) {
				if (getArguments().containsKey("form_array")) {
					formArray = (ArrayList<String>) getArguments().getSerializable("form_array");
				}
			}
			setHasOptionsMenu(true);
			mCallBackListener = (OnActivityBackPressedListener) (mContext);
		}catch(ClassCastException e)
		{
			e.printStackTrace();
		}

		catch(Exception e)
		{}
        setRetainInstance(true);
		setonGoalListSizeChange(this);
		try{
			MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
		}catch (Exception e){}
	}


	public void setListener(FirstTimeDoneInterface firstTimeDoneInterface){
		this.firstTimeDoneInterface=firstTimeDoneInterface;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

	//	InsuranceDialogFragment goalDetailFragment = new InsuranceDialogFragment();
	//	goalDetailFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), "goalslistfragment");

		/*mgoalListView.setOnItemClickListener(new adapterview.OnItemClickListener() {
			@Override
			public void onItemClick(adapterview<?> parent, View view, int position, long id) {
				if(view.getId()==R.id.goal_edit_rowitem){
					Toast.makeText(mContext, "ckck", Toast.LENGTH_LONG).show();
				}else if(view.getId()==R.id.goal_delete_rowitem){
					Toast.makeText(mContext, "ckck", Toast.LENGTH_LONG).show();
				}

			}
		});*/

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View goalsView = inflater.inflate(R.layout.fragment_goal, container, false);
        // add image from addgoal with pic
//		imageView = (ImageView) goalsView.findViewById(R.id.goal_click_image);
//		imageView.setBackgroundResource(R.drawable.ic_add_icon_big);
//		imageView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {


//			}
//		});
        mAddAndShowGoal= goalsView.findViewById(R.id.addAndShowGoal);
		mPictureAddGoal= goalsView.findViewById(R.id.pictureAddGoal);



		mCallBackListener.setActionBarTitle("Goal Details");
		mleftRelativeLayout = goalsView.findViewById(R.id.relative_left_arrow);
		mcenterRelativeLayout = goalsView.findViewById(R.id.relative_center_home);
		mRightRelativeLayout = goalsView.findViewById(R.id.relative_right_arrow);
		mleftRelativeLayout.setOnClickListener(this);
		mcenterRelativeLayout.setOnClickListener(this);
		mRightRelativeLayout.setOnClickListener(this);


		relative_finish_later= goalsView.findViewById(R.id.relative_finish_later);
		relative_done_arrow= goalsView.findViewById(R.id.relative_done_arrow);
		relative_finish_later.setOnClickListener(this);
		relative_done_arrow.setOnClickListener(this);



		bottom_bar_layout= goalsView.findViewById(R.id.bottom_bar_layout);
		UtileKit.mandatoryFieldLinearLayout(isSignUp,bottom_bar_layout);

		bottom_bar_donelayout= goalsView.findViewById(R.id.bottom_bar_donelayout);
		UtileKit.mandatoryFieldDoneLayout(isSignUp,bottom_bar_donelayout);


//		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//			@Override
//			public boolean onMenuItemClick(MenuItem menuItem) {
//				switch (menuItem.getItemId()) {
//					case R.id.ic_clear_btn:
//						if (menuItem.getItemId() == R.id.ic_clear_btn)
//							//Log.e("debug","action clear has clicked");
//						return true;
//				}
//				return false;
//
//				}
//		});
		addGoalFloatingBtn = goalsView.findViewById(R.id.addTabViewId);
		addGoalFloatingBtn.setOnClickListener(this);

		addTextView = goalsView.findViewById(R.id.goal_add_row);
		addTextView.setText("Add goal");

		mgoalListView = goalsView.findViewById(R.id.goal_list);
		mgoalListView.setExpanded(true);
			goalSortEdt  = goalsView.findViewById(R.id.goal_sort_Edt);
			goalFilterEdt  = goalsView.findViewById(R.id.goal_filter_Edt);

		UtileKit.setSvgCustomTextviewDrawableLeft(goalSortEdt,mContext,R.drawable.ic_sortby_goals);
		UtileKit.setSvgCustomTextviewDrawableLeft(goalFilterEdt,mContext,R.drawable.ic_filtter_goals);

		/*FloatingActionButton fab = (FloatingActionButton) goalsView.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				IncomeDetail fragment = new IncomeDetail();
				fragmentTransaction.replace(R.id.fragment_container, fragment);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commitAllowingStateLoss();
			}
		});*/
			goalSortEdt.setOnClickListener(this);
			goalFilterEdt.setOnClickListener(this);
		setonGoalDoneSelectedChange(this);
		if(isSignUp==true) {
			firstTimeDoneButtonGone("N");
		}

		return goalsView;
	}


	private void firstTimeDoneButtonGone(String done) {
		if(done.equalsIgnoreCase("Y")){
			relative_done_arrow.setVisibility(View.VISIBLE);
		}else {
			relative_done_arrow.setVisibility(View.GONE);
		}
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		callGetGoalListService();
	}

	public static int getDeviceHeight(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}


	public void callGetGoalListService() {
		UtileKit.showSpinnerDialog(mContext, false);
		WebServiceCalls webServiceObj;
		webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
		//Call<GetGoalsListModel> call = webServiceObj.callGetGoalsListService(LoginandSignUpActivity.UserId);
		Call<GetGoalsListModel> call = webServiceObj.callGetGoalsListService(/*EmailLoginFragment.oAuth_key,*/UtileKit.getPersistedPurplePathPref("user_id"));
		call.enqueue(new Callback<GetGoalsListModel>() {
			@Override
			public void onResponse(Call<GetGoalsListModel> call, Response<GetGoalsListModel> response) {

				getGoalsListModel = response.body();
				Log.d("Viswaaaa_errorrr1", response.code()+"");
				String code = response.code()+"";
				/*if (code.equals("401")){
					clearPreferences();
					Log.d("LogOut", "Out");
					LogotTheApp();
					getActivity().finish();
					//LoginManager.getInstance().logOut();

				}else {*/


						if (getGoalsListModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
							goallistsize = getGoalsListModel.getData().getUser_goals().size();
							if (goallistsize == 0) {
								mPictureAddGoal.setVisibility(View.VISIBLE);
								mAddAndShowGoal.setVisibility(View.INVISIBLE);

							} else {
								mPictureAddGoal.setVisibility(View.INVISIBLE);
								mAddAndShowGoal.setVisibility(View.VISIBLE);
							}
							getGoalsUserData = getGoalsListModel.getData().getUser_goals();
							if (UtileKit.validateObjectValues(getGoalsUserData)) {
								mgoalsListAdapter = new GoalListAdapter(mContext, addFamilyDetailModel, getGoalsUserData,
										checkGoalListIsEmptyOrNot, mOnGoalDoneSelectedListener, firstTimeDoneInterface);
								mgoalListView.setAdapter(mgoalsListAdapter);
							} else {

							}
						}

				//}
				else
				{
					mPictureAddGoal.setVisibility(View.VISIBLE);
					mAddAndShowGoal.setVisibility(View.INVISIBLE);

				}
				UtileKit.dismisssSpinnerDialog();
			}

			@Override
			public void onFailure(Call<GetGoalsListModel> call, Throwable t) {
				//Log.e("CallBack", " failure is " + t);
				UtileKit.dismisssSpinnerDialog();
				Log.d("Error", t.getMessage());
				UtileKit.alertRetrofitExceptionDialog( mContext,t);
			}
		});
	}

	private void callFamilyDetailsService() {
		UtileKit.showSpinnerDialog(mContext, false);
		WebServiceCalls webServiceObj;
		webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
		Call<GoalFamilyDetailsModel> call = webServiceObj.callFamilyDetailsListService(UtileKit.getPersistedPurplePathPref("user_id"));
		call.enqueue(new Callback<GoalFamilyDetailsModel>() {
			@Override
			public void onResponse(Call<GoalFamilyDetailsModel> call, Response<GoalFamilyDetailsModel> response) {
				////Log.e("CallBack", " family is " + call.toString());
				addFamilyDetailModel = response.body();
				UtileKit.dismisssSpinnerDialog();
				if (addFamilyDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
					familyDetails = addFamilyDetailModel.getData().getFamily_details();
					if (UtileKit.validateObjectValues(familyDetails)) {
						if(!familyDetails.isEmpty()) {
							for (int i = 0; i < familyDetails.size(); i++) {
								mgoalBelongsToArrayList.add(familyDetails.get(i).getName());
								mgoalBelongsToArrayListId.add(familyDetails.get(i).getId());
							}
							//mgoal_family_layout.setVisibility(View.VISIBLE);
						}
						//      UtileKit.setArrayListSpinnerAdapter(mgoalBelongsToSpinner, mgoalBelongsToArrayList, activity);
						//setSpinnerAdapter(mgoalBelongsToSpinner, mgoalBelongsToArrayList, activity);
					}
				}
			}
			@Override
			public void onFailure(Call<GoalFamilyDetailsModel> call, Throwable t) {
				//Log.e("CallBack", " failure is " + t);
				UtileKit.dismisssSpinnerDialog();
				UtileKit.alertRetrofitExceptionDialog( mContext,t);
			}
		});

	}
	public static Comparator<GetGoalsUserData> GoalNameAscComparator = new Comparator<GetGoalsUserData>() {

		public int compare(GetGoalsUserData s1, GetGoalsUserData s2) {
			String StudentName1 = s1.getGoal_name().toUpperCase();
			String StudentName2 = s2.getGoal_name().toUpperCase();

			//ascending order
			return StudentName1.compareTo(StudentName2);

		}};


	public static Comparator<GetGoalsUserData> GoalNameDescComparator = new Comparator<GetGoalsUserData>() {

		public int compare(GetGoalsUserData s1, GetGoalsUserData s2) {
			String StudentName1 = s1.getGoal_name().toUpperCase();
			String StudentName2 = s2.getGoal_name().toUpperCase();

			//descending order
			return StudentName2.compareTo(StudentName1);
		}};



	public static Comparator<GetGoalsUserData> GoalYearComparator = new Comparator<GetGoalsUserData>() {

		public int compare(GetGoalsUserData s1, GetGoalsUserData s2) {

			int goalyear1 = Integer.valueOf(s1.getGoal_years());
			int  goalyear2 = Integer.valueOf(s2.getGoal_years());

	   /*For ascending order*/
			return goalyear1-goalyear2;

	   /*For descending order*/
			//rollno2-rollno1;
		}};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.goal_filter_Edt:
				PopupMenu popup = new PopupMenu(mContext, goalFilterEdt);
				//Inflating the Popup using xml file
				popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
				//registering popup with OnMenuItemClickListener
				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						//mgoalsListAdapter.getFilter().filter(cs);
						//Toast.makeText(mContext,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
						if(UtileKit.validateObjectValues(getGoalsUserData)) {
							Collections.sort(getGoalsUserData, GoalNameDescComparator);
						}
						mgoalsListAdapter = new GoalListAdapter(mContext,addFamilyDetailModel, getGoalsUserData,
								checkGoalListIsEmptyOrNot, mOnGoalDoneSelectedListener,firstTimeDoneInterface);
						mgoalListView.setAdapter(mgoalsListAdapter);
						return true;
					}
				});
				popup.show();
				break;
			case R.id.goal_sort_Edt:
				PopupMenu sortpopup = new PopupMenu(mContext, goalSortEdt);
				//Inflating the Popup using xml file
				sortpopup.getMenuInflater().inflate(R.menu.sort_popup_menu, sortpopup.getMenu());
				//registering popup with OnMenuItemClickListener
				sortpopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						switch (item.getItemId()){
							case R.id.sortbydate:
								if(UtileKit.validateObjectValues(getGoalsUserData)) {
									Collections.sort(getGoalsUserData, GoalYearComparator);
								}
								mgoalsListAdapter = new GoalListAdapter(mContext,addFamilyDetailModel ,getGoalsUserData,
										checkGoalListIsEmptyOrNot, mOnGoalDoneSelectedListener,firstTimeDoneInterface);
								mgoalListView.setAdapter(mgoalsListAdapter);
								break;
							case R.id.sortbyname:
								if(UtileKit.validateObjectValues(getGoalsUserData)) {
									Collections.sort(getGoalsUserData, GoalNameAscComparator);
								}
								mgoalsListAdapter = new GoalListAdapter(mContext,addFamilyDetailModel, getGoalsUserData,
										checkGoalListIsEmptyOrNot, mOnGoalDoneSelectedListener,firstTimeDoneInterface);
								mgoalListView.setAdapter(mgoalsListAdapter);
								break;
						}
						//Toast.makeText(mContext,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
						return true;
					}
				});
				sortpopup.show();
				break;
			case R.id.addTabViewId:{
				Bundle args = new Bundle();
				args.putString("goalid", "");
				args.putSerializable("GoalFamilyDetails",addFamilyDetailModel);

                args.putSerializable("isSignUp", isSignUp);
                args.putSerializable("formArray", formArray);

				GoalDetailFragment goalDetailFragment = new GoalDetailFragment(mOnGoalDoneSelectedListener,firstTimeDoneInterface);
				goalDetailFragment.setArguments(args);
				goalDetailFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(),
						AppConstants.SHOWGOALDETAILS_TAG);
			}
			break;
			case R.id.relative_left_arrow:
			{
				mCallBackListener.onActivityBackPressed();
			}
			break;
			case R.id.relative_center_home:
			{
				startHomeActivity();
//				getActivity().finish();
			}
			break;
			case R.id.relative_right_arrow:
			{
			nextFragment();

			}
			break;
			case R.id.relative_finish_later:{
				Intent i=new Intent(getActivity(), HomePageActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(i);
			}
			break;
			case R.id.relative_done_arrow:{
				callUpdateInsertFlagService();
				addFragmenttoStack(new PromptChatFragment1());
			}
			break;

		}
	}

	public void callUpdateInsertFlagService() {
		WebServiceCalls webServiceObj;
		UtileKit.showSpinnerDialog(mContext, false);
		webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
		Call<InsertModel> call = webServiceObj.callUpdateInsertFlagService(UtileKit.getPersistedPurplePathPref("user_id"),
				"users_general_goals","Y","Y");
		call.enqueue(new Callback<InsertModel>() {
			@Override
			public void onResponse(Call<InsertModel> call, Response<InsertModel> response) {
				UtileKit.dismisssSpinnerDialog();
				insertModel = response.body();
				if(insertModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

				}
			}
			@Override
			public void onFailure(Call<InsertModel> call, Throwable t) {
				UtileKit.alertRetrofitExceptionDialog( mContext, t);
				UtileKit.dismisssSpinnerDialog();
			}
		});
	}
	private void nextFragment() {
		addFragmenttoStack( new IncomeDetail());
//		fragmentTransaction.replace(R.id.fragment_container, fragment);
//		fragmentTransaction.addToBackStack(null);
//		fragmentTransaction.commitAllowingStateLoss();
	}


	public void setonGoalListSizeChange(OnCheckListIsEmpty onCheckListIsEmpty) {

		checkGoalListIsEmptyOrNot = onCheckListIsEmpty;
	}


	@Override
	public void checkListSize(int size) {
		if(size==0){
			mPictureAddGoal.setVisibility(View.VISIBLE);
			mAddAndShowGoal.setVisibility(View.INVISIBLE);
		}

	}

	@Override
	public void onHomeBackPresedLisaner() {
		startHomeActivity();
	}

	@Override
	public void onBackpressedLisaner() {
		mCallBackListener.onActivityBackPressed();
	}

	@Override
	public void onNextBAckpressed() {
		nextFragment();
	}

	public void setonGoalDoneSelectedChange(OnGoalDoneSelectedListener onGoalDoneSelectedListener) {

		mOnGoalDoneSelectedListener = onGoalDoneSelectedListener;
	}

	@Override
	public void OnGoalDoneSelected() {
		callGetGoalListService();
		}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.menu_empty_items, menu);
		super.onCreateOptionsMenu(menu, inflater);


	}
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {

			case R.id.ic_done_btn:

				try{

					mCallBackListener.onActivityBackPressed();
				}catch (Exception e){
					e.printStackTrace();
				}
				return true;
		}
		return super.onOptionsItemSelected(menuItem);
	}

	@Override
	public void onDestroyOptionsMenu() {
		super.onDestroyOptionsMenu();

	}

	@Override
	public void firstTimeDone(String done) {
		firstTimeDoneButtonGone(done);
	}


	private void LogotTheApp() {
		HomePageActivity.getExpensesDetailsModel = null;
		HomePageActivity.expensesDetailsSize = 0;
		//alertDialogLogout.dismiss();
		getActivity().finish();
		Intent loginActivity = new Intent(mContext, LoginandSignUpActivity.class);
		loginActivity.putExtra("Logout", true);
		loginActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(loginActivity);
		Log.d("LogOut_value", "Logout");
	}



	private void clearPreferences() {
		SharedPreferences settings = mContext.getSharedPreferences("useridPref", Context.MODE_PRIVATE);
		settings.edit().remove("name_services").commit();
		settings.edit().remove("email_service").commit();
		settings.edit().remove("user_id").commit();
		settings.edit().remove("scheduleModel").commit();
		settings.edit().remove("UserPrefDate").commit();
		settings.edit().remove("AssetCat").commit();
		settings.edit().remove("mobilenumbergot").commit();
		//  settings.edit().remove("oneTimeShowAgreement").commit();
		//  settings.edit().remove("terms_checkbox_select").commit();
		// settings.edit().remove("continueAgreement").commit();
		settings.edit().remove("dialogShown").commit();
		settings.edit().remove("cust_id").commit();

		settings.edit().remove("dialogShownInitial").commit();
		settings.edit().remove("dialogShownTaxfile").commit();
		settings.edit().remove("dialogShownTaxPlanningSection").commit();
		settings.edit().remove("dialogShownTaxFilingSection").commit();


	}


}

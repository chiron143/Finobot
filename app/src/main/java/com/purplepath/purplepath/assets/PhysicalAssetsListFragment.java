package com.purplepath.purplepath.assets;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.AppConstants;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assets.adapter.PhysicalAssetsListAdapter;
import com.purplepath.purplepath.assets.dialog.AssetDialogFragment;
import com.purplepath.purplepath.assets.model.AssetCategoriesLevelOne;
import com.purplepath.purplepath.assets.model.AssetCategoriesModel;
import com.purplepath.purplepath.assets.model.GetAssetModel;
import com.purplepath.purplepath.assets.model.GetAssetUserData;
import com.purplepath.purplepath.chatprompt.model.Prompt_statements;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.insuranceinterface.OnCheckListIsEmpty;
import com.purplepath.purplepath.insurance.insuranceinterface.OndeleteUpdateList;
import com.purplepath.purplepath.liabilities.LiabilitiesTabViewFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.R.id.fab;


public class PhysicalAssetsListFragment extends BaseFragment implements View.OnClickListener,OnCheckListIsEmpty, OndeleteUpdateList, AssetsDetailsFragment.OnCustomPhysicalTabChange {
	private Context mContext;
    private com.purplepath.purplepath.customview.ExpandableListView massetListView;
    private PhysicalAssetsListAdapter physicalAssetsListAdapter;
	private GetAssetModel getAssetModel;
    private ArrayList<GetAssetUserData> getAssetUserData;
	public static int j ;
	private LinearLayout linearLayout;
	View goalsView;
	private TextView addTextView;
	private RelativeLayout containerLayout, addbuttonContainerLayout;
	private OnCheckListIsEmpty onCheckListIsEmptyOrNot;
	private OndeleteUpdateList ondeleteUpdateList;

	private AssetCategoriesModel assetCategoriesModel;
	private ArrayList<AssetCategoriesLevelOne> assetCategoriesLevelOneListData = new ArrayList<AssetCategoriesLevelOne>();
	private String assetType;
	private ArrayList<AssetCategoriesLevelOne> assetCategoriesLevelOneList = new ArrayList<AssetCategoriesLevelOne>();

	Boolean isSignUp = false;
	ArrayList<String> formArray = new ArrayList<String>();

	FloatingActionButton signup_physical_assets_fab, fab;

	FirstTimeDoneInterface firstTimeDoneInterface;

	public static PhysicalAssetsListFragment newInstance(Boolean isSignUp,ArrayList<String> formArray,
														 FirstTimeDoneInterface firstTimeDoneInterface) {
		PhysicalAssetsListFragment fragment = new PhysicalAssetsListFragment();

		Bundle args = new Bundle();

		if (isSignUp != null) {
			args.putSerializable("isSignUp", isSignUp);
		}
		if(formArray!=null){
			args.putSerializable("formArray",formArray);
		}
		fragment.setArguments(args);
		fragment.firstTimeDoneInterface=firstTimeDoneInterface;
		return fragment;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setRetainInstance(true);
		setonCheckListSizeChange(this);
		setonDeleteListSizeChange(this);

		if (getArguments() != null) {
			if (getArguments().containsKey("isSignUp"))
				isSignUp = getArguments().getBoolean("isSignUp");
		}
		if (getArguments() != null) {
			if (getArguments().containsKey("formArray")) {
				formArray = (ArrayList<String>) getArguments().getSerializable("formArray");
			}
		}

		//callCommonModelService();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		assetDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		AssetsDetailsFragment.setCustomOnPhysicalTabChangeListener(this);
//		int i= HomePageFragment.physicalAssetsize;
		goalsView = inflater.inflate(R.layout.fragment_physical_assets_list, container, false);
		containerLayout = goalsView.findViewById(R.id.assets_container_layout);
		addbuttonContainerLayout = goalsView.findViewById(R.id.add_button_layout);

		showContentView(goalsView);
		changeAddView(goalsView);
//		if(i==0) {
//			containerLayout.setVisibility(View.GONE);
//			addbuttonContainerLayout.setVisibility(View.VISIBLE);
//		}else{
//			containerLayout.setVisibility(View.VISIBLE);
//			addbuttonContainerLayout.setVisibility(View.GONE);
			callGetAssetService();
//		}



		return goalsView;
	}








	private View showContentView(View view){

		massetListView = view.findViewById(R.id.physical_assets_list);
		massetListView.setExpanded(true);
//		addAssetImageView =(ImageView) goalsView.findViewById(R.id.assets_add_imageView);
//		addAssetImageView.setOnClickListener(this);

		linearLayout = view.findViewById(R.id.assets_add_layout);
		linearLayout.setOnClickListener(this);

		addTextView = view.findViewById(R.id.add_row);
		addTextView.setText("Add Physical Assets");

		 fab = view.findViewById(R.id.physical_assets_fab);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle args = new Bundle();
				args.putString("assetid", null);
				args.putString("userid", null);
				args.putString("type", "Physical");
				args.putSerializable("assetobject", null);
				args.putString("type", "Physical");
				args.putSerializable("assetobject", null);

				args.putSerializable("isSignUp", isSignUp);
				args.putSerializable("formArray", formArray);


				AssetDialogFragment assetDialogFragment = AssetDialogFragment.newInstance(onCheckListIsEmptyOrNot,firstTimeDoneInterface);
				assetDialogFragment.setArguments(args);
				assetDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);

			}
		});

		return view;
	}

	private View changeAddView(View view){
		ImageView	imageView = view.findViewById(R.id.goal_click_image);
		TextView textView = view.findViewById(R.id.goal_headear_bg_TxtView);
		textView.setText("Please add physical assets by clicking the + button");
		imageView.setBackgroundResource(R.drawable.ic_add_icon_big);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle args = new Bundle();
				args.putString("assetid", null);
				args.putString("userid", null);
				args.putString("type", "Physical");
				args.putSerializable("assetobject", null);
				AssetDialogFragment assetDialogFragment = AssetDialogFragment.newInstance(onCheckListIsEmptyOrNot,firstTimeDoneInterface);
				assetDialogFragment.setArguments(args);
				assetDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
			}
		});

		return view;
	}


	public static int getDeviceHeight(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}


	public void callGetAssetService() {
		UtileKit.showSpinnerDialog(mContext, false);
		WebServiceCalls webServiceObj;
		webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
		Call<GetAssetModel> call = webServiceObj.callGetAssetService(UtileKit.getPersistedPurplePathPref("user_id"));
		call.enqueue(new Callback<GetAssetModel>() {
			@Override
			public void onResponse(Call<GetAssetModel> call, Response<GetAssetModel> response) {
				UtileKit.dismisssSpinnerDialog();
				getAssetModel = response.body();
				if (getAssetModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
					getAssetUserData = 	getAssetModel.getData().getUser_assets();
					 ArrayList<GetAssetUserData> physicalAssetUserData = new ArrayList<GetAssetUserData>();
					for (int i = 0; i<getAssetUserData.size(); i++) {
						if (getAssetUserData.get(i).getType().equalsIgnoreCase("Physical")) {
							physicalAssetUserData.add(getAssetUserData.get(i));
						}
					}
							if (!physicalAssetUserData.isEmpty()) {
								containerLayout.setVisibility(View.VISIBLE);
								addbuttonContainerLayout.setVisibility(View.GONE);
								physicalAssetsListAdapter = new PhysicalAssetsListAdapter(mContext, physicalAssetUserData,
										onCheckListIsEmptyOrNot, ondeleteUpdateList,firstTimeDoneInterface);
								massetListView.setAdapter(physicalAssetsListAdapter);
								physicalAssetsListAdapter.notifyDataSetChanged();
							}
				        	else {
								containerLayout.setVisibility(View.GONE);
								addbuttonContainerLayout.setVisibility(View.VISIBLE);

							}

				}
				else {
					containerLayout.setVisibility(View.GONE);
					addbuttonContainerLayout.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void onFailure(Call<GetAssetModel> call, Throwable t) {
				//Log.e("CallBack", " failure is " + t);
				UtileKit.alertRetrofitExceptionDialog( mContext, t);
				UtileKit.dismisssSpinnerDialog();
			}
		});

	}


	@Override
	public void onClick(View v) {

		Bundle args = new Bundle();
		args.putString("assetid", null);
		args.putString("userid", null);
		args.putString("type", "Physical");
		args.putSerializable("assetobject", null);
		AssetDialogFragment assetDialogFragment = AssetDialogFragment.newInstance(onCheckListIsEmptyOrNot,firstTimeDoneInterface);
		assetDialogFragment.setArguments(args);
		assetDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);

	}

	@Override
	public void onPhysicalTabChangeCallService() {

	}

	public void setonCheckListSizeChange(OnCheckListIsEmpty onCheckListIsEmpty){

		onCheckListIsEmptyOrNot = onCheckListIsEmpty;
	}

	public void setonDeleteListSizeChange(OndeleteUpdateList updateList){

		ondeleteUpdateList = updateList;
	}

	@Override
	public void checkListSize(int size) {
		if(size>0) {
			//Log.e("afdsafasdfsdf","afdasfsda");
			addbuttonContainerLayout.setVisibility(View.GONE);
			containerLayout.setVisibility(View.VISIBLE);
			callGetAssetService();
		}else{
			containerLayout.setVisibility(View.GONE);
			addbuttonContainerLayout.setVisibility(View.VISIBLE);
		}

	}


	@Override
	public void onHomeBackPresedLisaner() {
		startHomeActivity();
	}

	@Override
	public void onBackpressedLisaner() {
		((OnActivityBackPressedListener)getActivity()).onActivityBackPressed();
	}

	@Override
	public void onNextBAckpressed() {
		 addFragmenttoStack(new LiabilitiesTabViewFragment());

	}

	private void callAssetCategoriesService() {
		final ArrayList<String> assetCategoriesLevelOneIds = new ArrayList<String>();
		UtileKit.showSpinnerDialog( mContext, false);
		WebServiceCalls webServiceObj;
		webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
		Call<AssetCategoriesModel> call = webServiceObj.callAssetsCategoriesService();
		call.enqueue(new Callback<AssetCategoriesModel>() {
			@Override
			public void onResponse(Call<AssetCategoriesModel> call, Response<AssetCategoriesModel> response) {
				//Log.e("CallBack", " assets is " + call.toString());
				assetCategoriesModel = response.body();
				UtileKit.dismisssSpinnerDialog();
				if (assetCategoriesModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
					assetCategoriesLevelOneListData = assetCategoriesModel.getData().getAsset_cat_lev1();
					if (assetType.equalsIgnoreCase("Financial")) {
						for (int i = 0; i < assetCategoriesLevelOneListData.size(); i++) {
							if (assetCategoriesLevelOneListData.get(i).getType().equalsIgnoreCase("Financial")) {
								assetCategoriesLevelOneList.add(assetCategoriesLevelOneListData.get(i));
							}
						}
					} else {
						for (int i = 0; i < assetCategoriesLevelOneListData.size(); i++) {
							if (assetCategoriesLevelOneListData.get(i).getType().equalsIgnoreCase("Physical")) {
								assetCategoriesLevelOneList.add(assetCategoriesLevelOneListData.get(i));
							}
						}

					}


				/*	assetCategoriesLevelTwoList = assetCategoriesModel.getData().getAsset_cat_lev2();
					assetCategoriesLevelThreeList = assetCategoriesModel.getData().getAsset_cat_lev3();
					if (assetCategoriesLevelOneList != null) {
						for (int i = 0; i < assetCategoriesLevelOneList.size(); i++) {
							assetCategoriesLevelOneNameList.add(assetCategoriesLevelOneList.get(i).getLev1_name());
							assetCategoriesLevelOneIds.add(assetCategoriesLevelOneList.get(i).getId());

						}
						//    UtileKit.setArrayListSpinnerAdapter(mAssetCategoriesOneSpinner, assetCategoriesLevelOneNameList, activity);
						setSpinnerAdapter(mAssetCategoriesOneSpinner, assetCategoriesLevelOneNameList, activity);
						if (UtileKit.validateObjectValues(checkAssetID)) {
							if (UtileKit.validateObjectValues(getAssetUserData.getCat_lev1_id())) {
								int cat1_pos = Integer.valueOf(getAssetUserData.getCat_lev1_id());
								mAssetCategoriesOneSpinner.setSelection(getSelectedSpinnerposition(getAssetUserData.getCat_lev1_id(), assetCategoriesLevelOneIds));
							}
//                            if(UtileKit.validateObjectValues(getAssetUserData.getCat_lev3_id())) {
//                                int cat3_pos = Integer.valueOf(getAssetUserData.getCat_lev3_id());
//                                mAssetCategoriesThreeSpinner.setSelection(getSpinnerposition(getAssetUserData.getCat_lev3_id(), assetCategoriesLevelOneNameList));
//                            }
						}
					}
					*/

//                   /* if(goalCategoriesLevelTwoList !=null){
//                        for(int i=0; i<goalCategoriesLevelTwoList.size(); i++){
//                            goalCategoriesLevelTwoNameList.add(goalCategoriesLevelTwoList.get(i).getLev2_name());
//                        }
//                    }
//
//                    if(goalCategoriesLevelThreeList !=null){
//                        for(int i=0; i<goalCategoriesLevelThreeList.size(); i++){
//                            goalCategoriesLevelThreenameList.add(goalCategoriesLevelThreeList.get(i).getLev3_name());
//                        }
//
//                    }

				}
			}

			@Override
			public void onFailure(Call<AssetCategoriesModel> call, Throwable t) {
				//Log.e("CallBack", " assets is " + t);
				UtileKit.alertRetrofitExceptionDialog( mContext, t);
				UtileKit.dismisssSpinnerDialog();
			}
		});

	}
}

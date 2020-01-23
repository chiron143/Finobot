package com.purplepath.purplepath.insurance.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.AppConstants;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.ExpandableListView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.adapter.LifeInsuranceListAdapter;
import com.purplepath.purplepath.insurance.dialog.InsuranceDialogFragment;
import com.purplepath.purplepath.insurance.insuranceinterface.OnCheckListIsEmpty;
import com.purplepath.purplepath.insurance.insuranceinterface.OndeleteUpdateList;
import com.purplepath.purplepath.insurance.model.GetInsuranceInputData;
import com.purplepath.purplepath.insurance.model.GetInsuranceModel;
import com.purplepath.purplepath.model.CommonModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retirementbenefits.RetirementBenefitsFragment;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.insurance.fragment.InsuranceDetailsFragment.addFamilyDetailModel;


public class LifeInsuranceListFragment extends BaseFragment implements View.OnClickListener,
        OnCheckListIsEmpty, OndeleteUpdateList, InsuranceDetailsFragment.OnCustomLifeInsurTabChange {
    private Context mContext;
    private ExpandableListView mListView;
    private LifeInsuranceListAdapter lifeInsuranceListAdapter;
    private GetInsuranceModel getInsuranceModel;
    private ArrayList<GetInsuranceInputData> getInsuranceUserData;
    private int height = 0;
	private TextView goalSortEdt, goalFilterEdt;
	private float percent_height;
	public static int j ;
	CommonModel commonModel;
	private ImageView addAssetImageView, addBtnId;
    private TextView addTextView, physical_assets_type_header, physical_assets_currentvalue;
    private LinearLayout linearLayout;
    private LinearLayout headerLayout;
    View goalsView;
    private RelativeLayout containerLayout, addbuttonContainerLayout,add_btn_parent_layout;
    private LayoutInflater layoutInflater;
    private ViewGroup viewGroupContainer;

    private OnCheckListIsEmpty onCheckListIsEmptyOrNot;
    private OndeleteUpdateList ondeleteUpdateList;
//    private ArrayList<String> mgoalBelongsToArrayList = new ArrayList<String>();
//    private ArrayList<String> mgoalBelongsToArrayListId = new ArrayList<String>();
//    private ArrayList<GoalFamilyDetails> familyDetails = new ArrayList<GoalFamilyDetails>();
//    private GoalFamilyDetailsModel addFamilyDetailModel;
    private OnActivityBackPressedListener mCallBackListener;
    Boolean isSignUp = false;
    ArrayList<String> formArray = new ArrayList<String>();
    FirstTimeDoneInterface firstTimeDoneInterface;




    public static LifeInsuranceListFragment newInstance(Boolean isSignUp, ArrayList<String> formArray,
                                                        FirstTimeDoneInterface firstTimeDoneInterface) {
        LifeInsuranceListFragment fragment = new LifeInsuranceListFragment();
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
        mContext=getContext();

        if (getArguments() != null) {
            if (getArguments().containsKey("isSignUp"))
                isSignUp = getArguments().getBoolean("isSignUp");
        }
        if (getArguments() != null) {
            if (getArguments().containsKey("formArray")) {
                formArray = (ArrayList<String>) getArguments().getSerializable("formArray");
            }
        }


        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }

        catch(Exception e)
        {}
        //callCommonModelService();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
//		InsuranceDialogFragment assetDialogFragment = new InsuranceDialogFragment();
//		assetDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        InsuranceDetailsFragment.setCustomOnLifeInsurTabChangeListener(this);
        goalsView = inflater.inflate(R.layout.fragment_physical_assets_list, container, false);
        containerLayout = goalsView.findViewById(R.id.assets_container_layout);
        physical_assets_type_header = goalsView.findViewById(R.id.physical_assets_type_header);
        physical_assets_currentvalue = goalsView.findViewById(R.id.physical_assets_currentvalue);
        addbuttonContainerLayout = goalsView.findViewById(R.id.add_button_layout);
        add_btn_parent_layout = goalsView.findViewById(R.id.add_btn_parent_layout);
        addBtnId = goalsView.findViewById(R.id.goal_click_image);
        physical_assets_type_header.setText(R.string.annualpremiumnostar);
        physical_assets_currentvalue.setText(R.string.sumassurednostar);
        showContentView(goalsView,mContext);
        changeAddView(goalsView);
        callGetInsuranceService();
        if(getInsuranceModel!= null){
            containerLayout.setVisibility(View.VISIBLE);
            addbuttonContainerLayout.setVisibility(View.GONE);

        }else{

            containerLayout.setVisibility(View.GONE);
            addbuttonContainerLayout.setVisibility(View.VISIBLE);
        }
//        callFamilyDetailsService();
        addBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFragment();
            }
        });
        return goalsView;
	}


    public static int getDeviceHeight(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.heightPixels;
	}

    public void callGetInsuranceService() {
		UtileKit.showSpinnerDialog(mContext, false);
		WebServiceCalls webServiceObj;
		webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
		Call<GetInsuranceModel> call = webServiceObj.callGetInsuranceService(UtileKit.getPersistedPurplePathPref("user_id"));
		call.enqueue(new Callback<GetInsuranceModel>() {
			@Override
			public void onResponse(Call<GetInsuranceModel> call, Response<GetInsuranceModel> response) {
				ArrayList<GetInsuranceInputData> getLifeInsuranceUserData = new ArrayList<GetInsuranceInputData>();
				UtileKit.dismisssSpinnerDialog();
				getInsuranceModel = response.body();
				if (getInsuranceModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
					getInsuranceUserData = 	getInsuranceModel.getData().getUser_insurance();
					for (int i = 0; i<getInsuranceUserData.size(); i++) {
						if (getInsuranceUserData.get(i).getIns_type().equalsIgnoreCase("Life")) {
							getLifeInsuranceUserData.add(getInsuranceUserData.get(i));
							if (UtileKit.validateObjectValues(getLifeInsuranceUserData)) {
								lifeInsuranceListAdapter = new LifeInsuranceListAdapter(mContext,
                                        getLifeInsuranceUserData, onCheckListIsEmptyOrNot,ondeleteUpdateList,
                                        firstTimeDoneInterface);
								mListView.setAdapter(lifeInsuranceListAdapter);
							}
						}
					}

				}
                if (getLifeInsuranceUserData.isEmpty()) {
                    containerLayout.setVisibility(View.GONE);
                    addbuttonContainerLayout.setVisibility(View.VISIBLE);
                }
                else {
                    containerLayout.setVisibility(View.VISIBLE);
                    addbuttonContainerLayout.setVisibility(View.GONE);
                }

			}

			@Override
            public void onFailure(Call<GetInsuranceModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
				UtileKit.dismisssSpinnerDialog();
			}
		});

	}


    @Override
    public void onClick(View v) {
        Bundle args = new Bundle();
		args.putString("ins_id", null);
		args.putString("userid", null);
        args.putString("type", "Life");
        args.putInt("size", 0);
        args.putSerializable("Insuranceobject", null);// Why position didt send getLifeInsuranceUserData.size()
        InsuranceDialogFragment insuranceDialogFragment = InsuranceDialogFragment.newInstance(onCheckListIsEmptyOrNot,
                addFamilyDetailModel, firstTimeDoneInterface);
        insuranceDialogFragment.setArguments(args);
        insuranceDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);

	}

    @Override
    public void onLifeInsurTabChangeCallService() {

    }

    private View showContentView(View view, final Context mContext) {

        mListView = view.findViewById(R.id.physical_assets_list);
        mListView.setExpanded(true);
        headerLayout = view.findViewById(R.id.physical_assets_layout);
        linearLayout = view.findViewById(R.id.assets_add_layout);
        linearLayout.setOnClickListener(this);

        addTextView = view.findViewById(R.id.add_row);
        addTextView.setText("Add Life Insurance");

        FloatingActionButton fab = view.findViewById(R.id.physical_assets_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("ins_id", null);
                args.putString("userid", null);
                args.putString("type", "Life");
                args.putInt("size", 0);
                args.putSerializable("Insuranceobject", null);
                //signup prompt
                args.putBoolean("isSignUp",isSignUp);

                InsuranceDialogFragment insuranceDialogFragment = InsuranceDialogFragment.newInstance(onCheckListIsEmptyOrNot,
                        addFamilyDetailModel,firstTimeDoneInterface);
                insuranceDialogFragment.setArguments(args);
                insuranceDialogFragment.show(((FragmentActivity) LifeInsuranceListFragment.this.mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);

            }
        });

        return view;
    }



    private View changeAddView(View view) {

        TextView textView = view.findViewById(R.id.goal_headear_bg_TxtView);
        textView.setText("Please add life insurance by clicking the + button");
        ImageView imageView = view.findViewById(R.id.goal_click_image);
        imageView.setBackgroundResource(R.drawable.ic_add_icon_big);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFragment();

            }
        });

        return view;
    }

    private void onClickFragment() {
        try {
            Bundle args = new Bundle();
            args.putString("ins_id", null);
            args.putString("userid", null);
            args.putString("type", "Life");
            args.putInt("size", 0);
            args.putSerializable("Insuranceobject", null);
            InsuranceDialogFragment insuranceDialogFragment = InsuranceDialogFragment.newInstance(onCheckListIsEmptyOrNot,
                    addFamilyDetailModel, firstTimeDoneInterface);
            insuranceDialogFragment.setArguments(args);
            insuranceDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setonCheckListSizeChange(OnCheckListIsEmpty onCheckListIsEmpty) {

        onCheckListIsEmptyOrNot = onCheckListIsEmpty;
    }


    public void setonDeleteListSizeChange(OndeleteUpdateList updateList) {

        ondeleteUpdateList = updateList;

    }


    @Override
    public void checkListSize(int size) {
        if (size > 0) {
            //Log.e("LifeInsuranceList", "afdasfsda");
//            addbuttonContainerLayout.setVisibility(View.GONE);
//            containerLayout.setVisibility(View.VISIBLE);
            callGetInsuranceService();
        }
        if (size==0){
            //Log.e("LifeInsuranceList", "afdasfsda in else");
//            linearLayout.setVisibility(View.GONE);
//            containerLayout.setVisibility(View.GONE);
//            addbuttonContainerLayout.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onHomeBackPresedLisaner() {
        Intent i = new Intent(getActivity(), HomePageActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onBackpressedLisaner() {
        mCallBackListener.onActivityBackPressed();
    }

    @Override
    public void onNextBAckpressed() {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        RetirementBenefitsFragment fragment =
                addFragmenttoStack(new RetirementBenefitsFragment());
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
    }

    /*private void callFamilyDetailsService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalFamilyDetailsModel> call = webServiceObj.callFamilyDetailsListService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GoalFamilyDetailsModel>() {
            @Override
            public void onResponse(Call<GoalFamilyDetailsModel> call, Response<GoalFamilyDetailsModel> response) {
                //Log.e("CallBack", " family is " + call.toString());
                addFamilyDetailModel = response.body();
                UtileKit.dismisssSpinnerDialog();
//                if (addFamilyDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
////                    familyDetails = addFamilyDetailModel.getData().getFamily_details();
////                    if (UtileKit.validateObjectValues(familyDetails)) {
//////                        if(!familyDetails.isEmpty()) {
//////                            for (int i = 0; i < familyDetails.size(); i++) {
//////                                mgoalBelongsToArrayList.add(familyDetails.get(i).getName());
//////                                mgoalBelongsToArrayListId.add(familyDetails.get(i).getId());
////                                //Log.e("CallBack", " family is " + familyDetails.get(i).getName());
//////                            }
////                            //minsurance_family_layout.setVisibility(View.VISIBLE);
//////                        }
////                        //  UtileKit.setArrayListSpinnerAdapter(mInsurBelongToSpinner, mgoalBelongsToArrayList, activity);
////                        //Log.e("CallBack", "InsuranceSpinnerbelongtoarraylist" + mgoalBelongsToArrayList);
////                        //setSpinnerAdapter(mInsurBelongToSpinner, mgoalBelongsToArrayList, activity);
////                        //Log.e("CallBack", "InsuranceSpinnerbelongtoarraylist2" + mgoalBelongsToArrayList);
////                    }
//                }
            }
            @Override
            public void onFailure(Call<GoalFamilyDetailsModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }*/
}

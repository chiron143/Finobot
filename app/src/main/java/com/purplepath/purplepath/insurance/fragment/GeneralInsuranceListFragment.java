package com.purplepath.purplepath.insurance.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
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
import com.purplepath.purplepath.goal.GoalFamilyDetails;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.adapter.GeneralInsuranceListAdapter;
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


public class GeneralInsuranceListFragment extends BaseFragment implements OnCheckListIsEmpty, OndeleteUpdateList, View.OnClickListener, InsuranceDetailsFragment.OnCustomGeneralInsurTabChange {
    private Context mContext;
    private ExpandableListView mListView;
    private GeneralInsuranceListAdapter insuranceListAdapter;
    private GetInsuranceModel getInsuranceModel;
    private ArrayList<GetInsuranceInputData> getInsuranceUserData;
    private int height = 0;
    private TextView goalSortEdt, goalFilterEdt, physical_assets_type_header, physical_assets_currentvalue;
    private float percent_height;
    public static int j ;
    CommonModel commonModel;
    private ImageView addAssetImageView , addBtnId;
    private TextView addTextView;
    private LinearLayout linearLayout;
    View generalInsuranceView;
    private RelativeLayout containerLayout, addbuttonContainerLayout, add_btn_parent_layout;
    private OnCheckListIsEmpty onCheckListIsEmptyOrNot;
    private OndeleteUpdateList ondeleteUpdateList;

    private ArrayList<GoalFamilyDetails> familyDetails = new ArrayList<GoalFamilyDetails>();

    private OnActivityBackPressedListener mCallBackListener;
    Boolean isSignUp = false;
    ArrayList<String> formArray = new ArrayList<String>();
    FirstTimeDoneInterface firstTimeDoneInterface;

    public static GeneralInsuranceListFragment newInstance(Boolean isSignUp, ArrayList<String> formArray,
                                                           FirstTimeDoneInterface firstTimeDoneInterface) {
        GeneralInsuranceListFragment fragment = new GeneralInsuranceListFragment();
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

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setRetainInstance(true);
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
        InsuranceDetailsFragment.setCustomOnGenInsurTabChangeListener(this);
        setonCheckListSizeChange(this);
        setonDeleteListSizeChange(this);
//        int i= HomePageFragment.generalInsurancesize;
        generalInsuranceView = inflater.inflate(R.layout.fragment_physical_assets_list, container, false);

       return generalInsuranceView;
    }

    @Override
    public void onViewCreated(View generalInsuranceView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(generalInsuranceView, savedInstanceState);
        containerLayout = generalInsuranceView.findViewById(R.id.assets_container_layout);
        physical_assets_type_header = generalInsuranceView.findViewById(R.id.physical_assets_type_header);
        physical_assets_currentvalue = generalInsuranceView.findViewById(R.id.physical_assets_currentvalue);
        addbuttonContainerLayout = generalInsuranceView.findViewById(R.id.add_button_layout);
        add_btn_parent_layout = generalInsuranceView.findViewById(R.id.add_btn_parent_layout);
        addBtnId = generalInsuranceView.findViewById(R.id.goal_click_image);
        physical_assets_type_header.setText(R.string.annualpremiumnostar);
        physical_assets_currentvalue.setText(R.string.sumassurednostar);

//        if(getInsuranceModel == null){
//            add_btn_parent_layout.setVisibility(View.VISIBLE);
//            containerLayout.setVisibility(View.GONE);
//        }

        showContentView(generalInsuranceView);
        changeAddView(generalInsuranceView);
        callGetInsuranceService();

//        if(i==0) {
////            containerLayout.setVisibility(View.GONE);
////            addbuttonContainerLayout.setVisibility(View.VISIBLE);
//        }else{
//            containerLayout.setVisibility(View.VISIBLE);
//            addbuttonContainerLayout.setVisibility(View.GONE);
//            callGetInsuranceService();
//        }
//        if (i == 0) {
//            containerLayout.setVisibility(View.GONE);
//            addbuttonContainerLayout.setVisibility(View.VISIBLE);
//        } else {
//            containerLayout.setVisibility(View.VISIBLE);
//            addbuttonContainerLayout.setVisibility(View.GONE);
//            callGetInsuranceService();
//        }
        addBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickFragment();
            }
        });
    }

    private View showContentView(View view){

        mListView = view.findViewById(R.id.physical_assets_list);
        mListView.setExpanded(true);

        linearLayout = view.findViewById(R.id.assets_add_layout);
        linearLayout.setOnClickListener(this);

        addTextView = view.findViewById(R.id.add_row);
        addTextView.setText("Add General Insurance");

        FloatingActionButton fab = view.findViewById(R.id.physical_assets_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("ins_id", null);
                args.putString("userid", null);
                args.putString("type", "General");
                args.putInt("size", 0);
                args.putSerializable("Insuranceobject", null);
                //signup prompt
                args.putBoolean("isSignUp",isSignUp);

                InsuranceDialogFragment insuranceDialogFragment = InsuranceDialogFragment.newInstance(onCheckListIsEmptyOrNot,
                        addFamilyDetailModel, firstTimeDoneInterface);
                insuranceDialogFragment.setArguments(args);
                insuranceDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
            }
        });

        return view;
    }


    private void changeAddView(View view){
        TextView textView = view.findViewById(R.id.goal_headear_bg_TxtView);
        textView.setText("Please add general insurance by clicking the + button");
        ImageView	imageView = view.findViewById(R.id.goal_click_image);
        imageView.setBackgroundResource(R.drawable.ic_add_icon_big);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onclickFragment();

            }
        });

    }

    private void onclickFragment() {
        try{
            Bundle args = new Bundle();
            args.putString("ins_id", null);
            args.putString("userid", null);
            args.putString("type", "General");
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


  /*  public static int getDeviceHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }*/


    public void callGetInsuranceService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetInsuranceModel> call = webServiceObj.callGetInsuranceService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetInsuranceModel>() {
            @Override
            public void onResponse(Call<GetInsuranceModel> call, Response<GetInsuranceModel> response) {
                UtileKit.dismisssSpinnerDialog();
                getInsuranceModel = response.body();
                ArrayList<GetInsuranceInputData> getGeneralInsuranceUserData = new ArrayList<GetInsuranceInputData>();
                if (getInsuranceModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        getInsuranceUserData     = 	getInsuranceModel.getData().getUser_insurance();
                    for (int i = 0; i<getInsuranceUserData.size(); i++) {
                        if (getInsuranceUserData.get(i).getIns_type().equalsIgnoreCase("General")) {
                            getGeneralInsuranceUserData.add(getInsuranceUserData.get(i));
                            if (UtileKit.validateObjectValues(getGeneralInsuranceUserData)) {
                                insuranceListAdapter = new GeneralInsuranceListAdapter(mContext,
                                        getGeneralInsuranceUserData, onCheckListIsEmptyOrNot, ondeleteUpdateList,
                                        addFamilyDetailModel,firstTimeDoneInterface);
                                mListView.setAdapter(insuranceListAdapter);
                               // insuranceListAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                }
                if (getGeneralInsuranceUserData.isEmpty()) {
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
        try {
            Bundle args = new Bundle();
            args.putString("ins_id", null);
            args.putString("userid", null);
            args.putString("type", "General");
            args.putSerializable("insuranceobject", null);
            InsuranceDialogFragment insuranceDialogFragment = InsuranceDialogFragment.newInstance(onCheckListIsEmptyOrNot, addFamilyDetailModel, firstTimeDoneInterface);
            insuranceDialogFragment.setArguments(args);
            insuranceDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onGeneralInsurTabChangeCallService() {


    }

    public void setonCheckListSizeChange(OnCheckListIsEmpty onCheckListIsEmpty){

        onCheckListIsEmptyOrNot = onCheckListIsEmpty;
    }

    public void setonDeleteListSizeChange(OndeleteUpdateList updateList){

        ondeleteUpdateList = updateList;
    }

    @Override
    public void checkListSize(int size) {
//		//Log.e("size",""+size);
//          if(size>0){
//			  viewGroupContainer.removeAllViews();
//			  View addView = layoutInflater.inflate(R.layout.fragment_physical_assets_list, viewGroupContainer, false);
//			  containerLayout = (RelativeLayout)addView.findViewById(R.id.assets_container_layout);
//			  viewGroupContainer.addView(addView);
//			  showContentView(addView);
//			  callGetInsuranceService();
//
//		  }else{
//			  viewGroupContainer.removeAllViews();
//			  goalsView=  layoutInflater.inflate(R.layout.fragment_add_goal, viewGroupContainer, false);
//			  viewGroupContainer.addView(goalsView);
//			  changeAddView(goalsView);
//		  }

        if(size>0) {
            //Log.e("GeneralInsuranceLis","size"+size);
            addbuttonContainerLayout.setVisibility(View.GONE);
            containerLayout.setVisibility(View.VISIBLE);
            callGetInsuranceService();
        }else{
            containerLayout.setVisibility(View.GONE);
            addbuttonContainerLayout.setVisibility(View.VISIBLE);
            //Log.e("GeneralInsuranceList","size"+size);
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


}

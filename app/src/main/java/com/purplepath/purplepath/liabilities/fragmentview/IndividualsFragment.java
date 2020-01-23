package com.purplepath.purplepath.liabilities.fragmentview;

import android.content.Context;
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
import android.widget.ScrollView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.AppConstants;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.ExpandableListView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goal.InterfaceDone.FirstTimeDoneInterface;
import com.purplepath.purplepath.insurance.fragment.InsuranceDetailsFragment;
import com.purplepath.purplepath.insurance.insuranceinterface.OnCheckListIsEmpty;
import com.purplepath.purplepath.insurance.insuranceinterface.OndeleteUpdateList;
import com.purplepath.purplepath.liabilities.adapter.LiabilityListAdapter;
import com.purplepath.purplepath.liabilities.dialog.LiabilityDialogFragment;
import com.purplepath.purplepath.liabilities.model.AddLiabilityModel;
import com.purplepath.purplepath.liabilities.model.LiabCategoryModel;
import com.purplepath.purplepath.liabilities.model.UserLiabilityList;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dinesh on 05/07/16.
 */
public class IndividualsFragment extends BaseFragment implements  OnCheckListIsEmpty, OndeleteUpdateList{
    private  static  String ARG_PARAM1="param";
    private Context mContext;
    private LiabCategoryModel mliabCategoryModel=null;
    private LiabilityListAdapter mLibilityListAdapter;
    private ExpandableListView mLiablityListView;
    private AddLiabilityModel mLiablityListArrayModel;
    private ArrayList<UserLiabilityList> mIndividualsList=new ArrayList<>();
    private OnCheckListIsEmpty onCheckListIsEmptyOrNot;
    private OndeleteUpdateList ondeleteUpdateList;
    private final String SUCCESSCODE = "200";
    private ScrollView containerLayout;
    private RelativeLayout addbuttonContainerLayout;
    LinearLayout linearLayout;
//    private Button institutionDetailsBtn;
    private ImageView institutionDetailsBtn;
    View preRetirementView;


    ArrayList<String> formArray = new ArrayList<String>();
    Boolean isSignUp = false;
    Bundle args = new Bundle();

    FirstTimeDoneInterface firstTimeDoneInterface;

    public static IndividualsFragment newInstance(LiabCategoryModel liabCategoryModel, AddLiabilityModel mLiablityList,
                                                  Boolean isSignUp, ArrayList<String> formArray,
                                                  FirstTimeDoneInterface firstTimeDoneInterface) {
        //Log.e("Check","count");
        Bundle args = new Bundle();
        args.putSerializable("param1",liabCategoryModel);
        args.putSerializable("param2",mLiablityList);


        if (isSignUp != null) {
            args.putSerializable("isSignUp", isSignUp);
        }
        if(formArray!=null){
            args.putSerializable("formArray",formArray);
        }

        IndividualsFragment fragment = new IndividualsFragment();
        fragment.setArguments(args);
        fragment.firstTimeDoneInterface=firstTimeDoneInterface;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        setonCheckListSizeChange(this);
        setonDeleteListSizeChange(this);

        if(null!=getArguments())
        {
            if(getArguments().containsKey("param1"))
                mliabCategoryModel = (LiabCategoryModel) getArguments().getSerializable("param1");
            if(getArguments().containsKey("param2"))
                mLiablityListArrayModel = (AddLiabilityModel) getArguments().getSerializable("param2");
        }

        if (getArguments() != null) {
            if (getArguments().containsKey("isSignUp"))
                isSignUp = getArguments().getBoolean("isSignUp");
        }
        if (getArguments() != null) {
            if (getArguments().containsKey("formArray")) {
                formArray = (ArrayList<String>) getArguments().getSerializable("formArray");
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View preRetirementView = inflater.inflate(R.layout.fragment_lib_individuals_listview, container, false);

        if(mliabCategoryModel == null) {
            addbuttonContainerLayout.setVisibility(View.VISIBLE);
        }

        return preRetirementView;
    }

    @Override
    public void onViewCreated(View preRetirementView, @Nullable Bundle savedInstanceState) {
        mLiablityListView = preRetirementView.findViewById(R.id.individuals_liability_list);
        mLiablityListView.setExpanded(true);
        FloatingActionButton fab = preRetirementView.findViewById(R.id.liability__fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                if (mliabCategoryModel != null)


           LiabilityDialogFragment.newInstance(null, mliabCategoryModel,"Individuals", onCheckListIsEmptyOrNot
                   ,isSignUp,formArray, firstTimeDoneInterface).
                   show(((FragmentActivity) mContext).getSupportFragmentManager(),
          AppConstants.SHOWGOALDETAILS_TAG);

//                args.putSerializable("isSignUp", isSignUp);
//                args.putSerializable("formArray", formArray);
//
//                LiabilityDialogFragment liabilityDialogFragment = LiabilityDialogFragment.newInstance(null,
//                        mliabCategoryModel,"Individuals", onCheckListIsEmptyOrNot, isSignUp, formArray);
//                liabilityDialogFragment.setArguments(args);
//                liabilityDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(),
//                        AppConstants.SHOWGOALDETAILS_TAG);


            }
        });
        institutionDetailsBtn = preRetirementView.findViewById(R.id.addBtnId);
        institutionDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mliabCategoryModel != null)
                    LiabilityDialogFragment.newInstance(null, mliabCategoryModel,"Individuals", onCheckListIsEmptyOrNot, isSignUp, formArray, firstTimeDoneInterface).show(((FragmentActivity) mContext).getSupportFragmentManager(),
                            AppConstants.SHOWGOALDETAILS_TAG);

//                    args = new Bundle();
//                args.putSerializable("isSignUp", isSignUp);
//                args.putSerializable("formArray", formArray);
//
//                LiabilityDialogFragment liabilityDialogFragment = LiabilityDialogFragment.newInstance(null,
//                        mliabCategoryModel,"Individuals",
//                        onCheckListIsEmptyOrNot);
//                liabilityDialogFragment.setArguments(args);
//                liabilityDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(),
//                        AppConstants.SHOWGOALDETAILS_TAG);

            }
        });
        containerLayout = preRetirementView.findViewById(R.id.libi_parent_listView);
        addbuttonContainerLayout = preRetirementView.findViewById(R.id.add_btn_parent_layout);

        //No need add button below using floating button
       /* linearLayout = (LinearLayout) preRetirementView.findViewById(R.id.liability_add_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mliabCategoryModel != null)
                    LiabilityDialogFragment.newInstance(null, mliabCategoryModel,"Individuals", onCheckListIsEmptyOrNot).show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
            }
        });*/


        if(null!=mLiablityListArrayModel)

        {
            mIndividualsList=new ArrayList<>();
            int size=mLiablityListArrayModel.getData().getUser_liabs().size();
            for(int i=0;i<size;i++)
            {
                if(mLiablityListArrayModel.getData().getUser_liabs().get(i).getType().equalsIgnoreCase("Individuals")) {
                    mIndividualsList.add(mLiablityListArrayModel.getData().getUser_liabs().get(i));
                    //Log.e("IndividualSucess","I>>>>"+i);
                }
            }
            if(!mIndividualsList.isEmpty()) {


                mLibilityListAdapter = new LiabilityListAdapter(mContext, mIndividualsList, mliabCategoryModel,
                        "Individuals",onCheckListIsEmptyOrNot,ondeleteUpdateList, firstTimeDoneInterface);
                mLiablityListView.setAdapter(mLibilityListAdapter);
                addbuttonContainerLayout.setVisibility(View.GONE);
                containerLayout.setVisibility(View.VISIBLE);
            }
            else {
                addbuttonContainerLayout.setVisibility(View.VISIBLE);
                containerLayout.setVisibility(View.GONE);
            }


        }
        else {
            addbuttonContainerLayout.setVisibility(View.VISIBLE);
            containerLayout.setVisibility(View.GONE);
        }
    }


    public void setonCheckListSizeChange(OnCheckListIsEmpty onCheckListIsEmpty){

        onCheckListIsEmptyOrNot = onCheckListIsEmpty;
    }


    public void setonDeleteListSizeChange(OndeleteUpdateList updateList){

        ondeleteUpdateList = updateList;
    }

    private void GetUserLiabList()
    {
        WebServiceCalls webServiceObj2;
        webServiceObj2 = ServiceGenerator.createService(WebServiceCalls.class);
        UtileKit.showSpinnerDialog(mContext,false);
        Call<AddLiabilityModel> call2 = webServiceObj2.callGetLiablityByUser(UtileKit.getPersistedPurplePathPref("user_id"));
        call2.enqueue(new Callback<AddLiabilityModel>() {
            @Override
            public void onResponse(Call<AddLiabilityModel> call, Response<AddLiabilityModel> response) {

                AddLiabilityModel getLiabModel;

                getLiabModel = response.body();
                if (getLiabModel.getStatus_code().equalsIgnoreCase(SUCCESSCODE)) {
                    UtileKit.dismisssSpinnerDialog();
                    int size=getLiabModel.getData().getUser_liabs().size();
                    mIndividualsList=new ArrayList<>();
                    for(int i=0;i<size;i++)
                    {
                        if(getLiabModel.getData().getUser_liabs().get(i).getType().equalsIgnoreCase("Individuals")) {
                            mIndividualsList.add(getLiabModel.getData().getUser_liabs().get(i));
                            //Log.e("Sucess","I>>>>"+i);
                        }
                    }
                    if(!mIndividualsList.isEmpty()) {
                        mLibilityListAdapter = new LiabilityListAdapter(mContext, mIndividualsList,
                                mliabCategoryModel, "Individuals", onCheckListIsEmptyOrNot, ondeleteUpdateList,firstTimeDoneInterface);
                        mLiablityListView.setAdapter(mLibilityListAdapter);
                        addbuttonContainerLayout.setVisibility(View.GONE);
                        containerLayout.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        addbuttonContainerLayout.setVisibility(View.VISIBLE);
                        containerLayout.setVisibility(View.GONE);
                    }
                }
                else
                {
                    addbuttonContainerLayout.setVisibility(View.VISIBLE);
                    containerLayout.setVisibility(View.GONE);

                }

                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<AddLiabilityModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }
    @Override
    public void checkListSize(int size) {
        GetUserLiabList();
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
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        InsuranceDetailsFragment fragment =
                addFragmenttoStack(new InsuranceDetailsFragment());
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
    }
}

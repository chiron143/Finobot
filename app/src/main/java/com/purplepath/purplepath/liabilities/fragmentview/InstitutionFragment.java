package com.purplepath.purplepath.liabilities.fragmentview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
import com.purplepath.purplepath.liabilities.adapter.InstitutionListAdapter;
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
public class InstitutionFragment extends BaseFragment implements OnCheckListIsEmpty, OndeleteUpdateList {

    private LiabCategoryModel mliabCategoryModel;
    private Context mContext;
    private AddLiabilityModel mLiablityListArrayModel;
    private ExpandableListView mLiablityListView;
    private InstitutionListAdapter mInstitutionListAdapter;
    private ArrayList<UserLiabilityList> mInstutionList=new ArrayList<>();
    private OnCheckListIsEmpty onCheckListIsEmptyOrNot;
    private OndeleteUpdateList ondeleteUpdateList;
    private ScrollView containerLayout;
    private RelativeLayout  addContainerLayout;
    private  LinearLayout   linearLayout;
//    Button institutionDetailsBtn;
    ImageView institutionDetailsBtn;
    private final String SUCCESSCODE = "200";

    ArrayList<String> formArray = new ArrayList<String>();
    Boolean isSignUp = false;
    Bundle args = new Bundle();

    FirstTimeDoneInterface firstTimeDoneInterface;

    public static InstitutionFragment newInstance(LiabCategoryModel liablityCategoryModel, AddLiabilityModel liablityList,
                                                  Boolean isSignUp, ArrayList<String> formArray,
                                                  FirstTimeDoneInterface firstTimeDoneInterface) {
        Bundle args = new Bundle();
        args.putSerializable("param1",liablityCategoryModel);
        args.putSerializable("param2",liablityList);

        if (isSignUp != null) {
            args.putSerializable("isSignUp", isSignUp);
        }
        if(formArray!=null){
            args.putSerializable("formArray",formArray);
        }
        InstitutionFragment fragment = new InstitutionFragment();
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mLiablityListArrayModel == null){
            addContainerLayout.setVisibility(View.VISIBLE);
            Log.i("Institution Fragment","onActivityCreated = null");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View institutionFragmentView = inflater.inflate(R.layout.fragment_liability_institution_list_view, container, false);
        Log.i("Institution Fragment","onCreateView");
        return institutionFragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mLiablityListView = view.findViewById(R.id.Institution_liability_list);
        mLiablityListView.setExpanded(true);
        FloatingActionButton fab = view.findViewById(R.id.liability__fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                if (mliabCategoryModel != null)

                                    LiabilityDialogFragment.newInstance(null, mliabCategoryModel,"Institutions",
                            onCheckListIsEmptyOrNot,isSignUp,formArray,firstTimeDoneInterface).show(((FragmentActivity) mContext).
                                getSupportFragmentManager(),
                            AppConstants.SHOWGOALDETAILS_TAG);

//                args.putSerializable("isSignUp", isSignUp);
//                args.putSerializable("formArray", formArray);
//
//                LiabilityDialogFragment liabilityDialogFragment = LiabilityDialogFragment.newInstance(null,
//                        mliabCategoryModel,"Institutions",
//                        onCheckListIsEmptyOrNot);
//                liabilityDialogFragment.setArguments(args);
//                liabilityDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(),
//                        AppConstants.SHOWGOALDETAILS_TAG);

            }
        });
        institutionDetailsBtn = view.findViewById(R.id.preretirementAddBtnId);
        institutionDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mliabCategoryModel != null)

                         LiabilityDialogFragment.newInstance(null, mliabCategoryModel,"Institutions",
                            onCheckListIsEmptyOrNot, isSignUp, formArray, firstTimeDoneInterface).show(((FragmentActivity) mContext).
                            getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);

//                    args = new Bundle();
//                args.putSerializable("isSignUp", isSignUp);
//                args.putSerializable("formArray", formArray);
//
//                LiabilityDialogFragment liabilityDialogFragment = LiabilityDialogFragment.newInstance(null,
//                        mliabCategoryModel,"Institutions",
//                        onCheckListIsEmptyOrNot);
//                liabilityDialogFragment.setArguments(args);
//                liabilityDialogFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(),
//                        AppConstants.SHOWGOALDETAILS_TAG);

            }
        });
        containerLayout = view.findViewById(R.id.libi_parent_listView);
        addContainerLayout = view.findViewById(R.id.add_button_parent_layout);

        //No need add button below using floating button
       /* linearLayout = (LinearLayout) view.findViewById(R.id.liability_add_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mliabCategoryModel != null)
                    LiabilityDialogFragment.newInstance(null, mliabCategoryModel,"Institutions", onCheckListIsEmptyOrNot).show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
            }
        });*/

        if(null!=mLiablityListArrayModel)
       {
           mInstutionList=new ArrayList<>();
           int size=mLiablityListArrayModel.getData().getUser_liabs().size();
           for(int i=0;i<size;i++)
           {
               if(mLiablityListArrayModel.getData().getUser_liabs().get(i).getType().equalsIgnoreCase("Institutions")) {
                   mInstutionList.add(mLiablityListArrayModel.getData().getUser_liabs().get(i));
                   //Log.e("Sucess","I>>>>"+i);
               }
           }
           if(!mInstutionList.isEmpty()) {


               mInstitutionListAdapter = new InstitutionListAdapter(mContext, mInstutionList, mliabCategoryModel,
                       "Institutions",onCheckListIsEmptyOrNot,ondeleteUpdateList, firstTimeDoneInterface);
               mLiablityListView.setAdapter(mInstitutionListAdapter);
               addContainerLayout.setVisibility(View.GONE);
               containerLayout.setVisibility(View.VISIBLE);

           }
           else {

               addContainerLayout.setVisibility(View.VISIBLE);
               containerLayout.setVisibility(View.GONE);
           }


       }
        else {
            addContainerLayout.setVisibility(View.VISIBLE);
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

    Call<AddLiabilityModel> call2 = webServiceObj2.callGetLiablityByUser(UtileKit.getPersistedPurplePathPref("user_id"));
    call2.enqueue(new Callback<AddLiabilityModel>() {
        @Override
        public void onResponse(Call<AddLiabilityModel> call, Response<AddLiabilityModel> response) {

            AddLiabilityModel getLiabModel;
            getLiabModel = response.body();
            if (getLiabModel.getStatus_code().equalsIgnoreCase(SUCCESSCODE)) {
                UtileKit.dismisssSpinnerDialog();
                int size=getLiabModel.getData().getUser_liabs().size();
                mInstutionList=new ArrayList<>();
                for(int i=0;i<size;i++)
                {
                    if(getLiabModel.getData().getUser_liabs().get(i).getType().equalsIgnoreCase("Institutions")) {
                        mInstutionList.add(getLiabModel.getData().getUser_liabs().get(i));
                        //Log.e("Sucess","I>>>>"+i);
                    }
                }
                if(!mInstutionList.isEmpty()) {
                    mInstitutionListAdapter = new InstitutionListAdapter(mContext, mInstutionList, mliabCategoryModel,
                            "Institutions", onCheckListIsEmptyOrNot, ondeleteUpdateList,firstTimeDoneInterface);
                    mLiablityListView.setAdapter(mInstitutionListAdapter);
                    addContainerLayout.setVisibility(View.GONE);
                    containerLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    addContainerLayout.setVisibility(View.VISIBLE);
                    containerLayout.setVisibility(View.GONE);

                }
            }
            else
            {
                addContainerLayout.setVisibility(View.VISIBLE);
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

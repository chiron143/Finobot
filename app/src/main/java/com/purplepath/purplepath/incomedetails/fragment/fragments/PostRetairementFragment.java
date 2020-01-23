package com.purplepath.purplepath.incomedetails.fragment.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomedetails.fragment.model.GetIncomeModel;
import com.purplepath.purplepath.incomedetails.fragment.model.IncomeCategoryModel;
import com.purplepath.purplepath.incomedetails.fragment.postretairement.RetIncFromFamily;
import com.purplepath.purplepath.incomedetails.fragment.postretairement.adapter.PostRetairementListAdapter;
import com.purplepath.purplepath.incomedetails.fragment.postretairement.interfacepost.OnPostRetirementClick;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.Notification.adapters.PromptsRecyclerViewAdapter.fragmentManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PostRetairementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostRetairementFragment  extends BaseFragment implements OnPostRetirementClick {
//    private ImageView addIncomeDetailsBtn;
    private Context mContext;
    private AddFamilyDetailModel mFamilyDetailModel;
    private IncomeCategoryModel mCategoryModel;
    private GetIncomeModel mIncomeDetailModel;
    private ListView mincomeListView;
    private RelativeLayout mlistcontainerLayout ,addbuttonContainerLayout;
//    private RelativeLayout addbuttonContainerLayout ;
    private LinearLayout linearLayout ;
    private OnPostRetirementClick onPostRetireClickLisaner;



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callGetFamilyDetail(UtileKit.getPersistedPurplePathPref("user_id"));
        callIncomeCategoryCode();
//        getIncomeDetail();
        mContext = getContext();
        onPostRetireClickLisaner=this;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View postRetirementView = inflater.inflate(R.layout.fragment_pre_retirement_adapter_view, container, false);
        TextView titleAddView= postRetirementView.findViewById(R.id.goal_headear_bg_TxtView);
        titleAddView.setText("Please click + button to fill post-retirement income ");
        if(mFamilyDetailModel!=null){
            try {
                getIncomeDetail(mFamilyDetailModel);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            try{
                callGetFamilyDetail(UtileKit.getPersistedPurplePathPref("user_id"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(mCategoryModel == null){
            callIncomeCategoryCode();
        }

        return postRetirementView;
    }

    @Override
    public void onViewCreated(View postRetirementView, @Nullable Bundle savedInstanceState) {
        //        add_buttonParent=(RelativeLayout)postRetirementView.findViewById(R.id.add_button_parent_layout);
        mincomeListView = postRetirementView.findViewById(R.id.Institution_liability_list);
        FloatingActionButton fab = postRetirementView.findViewById(R.id.addIncome_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String familyID = "0";
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                Fragment incomeFrag=
                        addFragmenttoStack(RetIncFromFamily.newInstance(mFamilyDetailModel,mCategoryModel,mIncomeDetailModel, fragmentManager, familyID));
                // LifeInsuranceListFragment fragment = new LifeInsuranceListFragment();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, incomeFrag);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();
            }
        });
//        ImageView institutionDetailsBtn = (ImageView) postRetirementView.findViewById(R.id.preretirementAddBtnId);
//        institutionDetailsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (mliabCategoryModel != null)
////                    LiabilityDialogFragment.newInstance(null, mliabCategoryModel,"Individuals", onCheckListIsEmptyOrNot).show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
//            }
//        });
        mlistcontainerLayout = postRetirementView.findViewById(R.id.libi_parent_listView);
        addbuttonContainerLayout = postRetirementView.findViewById(R.id.add_button_parent_layout);
        linearLayout = postRetirementView.findViewById(R.id.liability_add_layout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String familyID = "0";
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                Fragment incomeFrag=
                        addFragmenttoStack(RetIncFromFamily.newInstance(mFamilyDetailModel,mCategoryModel,mIncomeDetailModel, fragmentManager, familyID));
                // LifeInsuranceListFragment fragment = new LifeInsuranceListFragment();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, incomeFrag);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();
            }
        });
//        addIncomeDetailsBtn=(ImageView)postRetirementView.findViewById(R.id.preretirementAddBtnId);
//        addIncomeDetailsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String familyID = "0";
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                Fragment incomeFrag= RetIncFromFamily.newInstance(mFamilyDetailModel,mCategoryModel,mIncomeDetailModel, fragmentManager, familyID);
//                // LifeInsuranceListFragment fragment = new LifeInsuranceListFragment();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, incomeFrag);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();
//
//            }
//        });
    }

    public static PostRetairementFragment newInstance(int position) {
        PostRetairementFragment fragment = new PostRetairementFragment();
        Bundle args = new Bundle();
        args.putInt("Count", position);
        fragment.setArguments(args);
        return fragment;
    }

    private void callGetFamilyDetail(String userId) {
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<AddFamilyDetailModel> call = webServiceObj.callFamilyDetailsService(userId);

        UtileKit.showSpinnerDialog(mContext, false);
        call.enqueue(new Callback<AddFamilyDetailModel>() {
            @Override
            public void onResponse(Call<AddFamilyDetailModel> call, Response<AddFamilyDetailModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
                AddFamilyDetailModel categoryModel = response.body();
                if (categoryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    mFamilyDetailModel = categoryModel;
                    getIncomeDetail(mFamilyDetailModel);
//                    addViewto(fragmentview,mIncomeCategoryModel)
                } else {
//                    UtileKit.alertDialog(
//                            categoryModel.getData().getMessage(),
//                            mContext);
                    getIncomeDetail(mFamilyDetailModel);
//                    mlistcontainerLayout.setVisibility(View.GONE);
//                    addbuttonContainerLayout.setVisibility(View.VISIBLE);
//                    mincomeListView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AddFamilyDetailModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }

    private void callIncomeCategoryCode() {
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        UtileKit.showSpinnerDialogForExplore(mContext, false);
        Call<IncomeCategoryModel> call = webServiceObj.callIncomeCategories();
        call.enqueue(new Callback<IncomeCategoryModel>() {
            @Override
            public void onResponse(Call<IncomeCategoryModel> call, Response<IncomeCategoryModel> response) {
                UtileKit.dismisssExploreSpinnerDialog();
                IncomeCategoryModel incomeCategoryModel=response.body();
                if(incomeCategoryModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE))
                {

                    mCategoryModel=incomeCategoryModel;
                }
                else {
//                    UtileKit.alertDialog(
//                            incomeCategoryModel.getData().getMessage(),
//                            mContext);
                }
            }

            @Override
            public void onFailure(Call<IncomeCategoryModel> call, Throwable t) {
                UtileKit.dismisssExploreSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }

    private void getIncomeDetail(final AddFamilyDetailModel mFamilyDetail) {

        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<GetIncomeModel> call = webServiceObj.GetIncomepostretDetailService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetIncomeModel>() {
            @Override
            public void onResponse(Call<GetIncomeModel> call, Response<GetIncomeModel> response) {
             try {
                 //Log.e("CallBack", " response is " + call.toString());

                 GetIncomeModel incomeDetail = response.body();
                 if (incomeDetail.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                     GetIncomeModel incomeDetailModel = incomeDetail;
                     mIncomeDetailModel = incomeDetailModel;
//                     addIncomeDetailsBtn.setVisibility(View.GONE);
                     mlistcontainerLayout.setVisibility(View.VISIBLE);
                     PostRetairementListAdapter adapter = new PostRetairementListAdapter(mContext, mIncomeDetailModel.getData().getUser_incomes(), mFamilyDetail, onPostRetireClickLisaner);
                     mincomeListView.setAdapter(adapter);
//
//         addViewto(fragmentview,mIncomeCategoryModel)
                 } else {
                     mlistcontainerLayout.setVisibility(View.GONE);

                     mincomeListView.setVisibility(View.GONE);
//                     addIncomeDetailsBtn.setVisibility(View.VISIBLE);
                     addbuttonContainerLayout.setVisibility(View.VISIBLE);
//                    UtileKit.alertDialog(
//                            incomeDetail.getData().getMessage(),
//                            mContext);
                 }
             }catch (Exception e)
             {
                 e.printStackTrace();
             }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<GetIncomeModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }



    @Override
    public void onPostRetirentClick(String familyID) {
//        Log.i("Sucess","OnClick on More postion RetIncFromFamily" + familyID);
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//
//        Fragment incomeFrag=
          addFragmenttoStack(RetIncFromFamily.newInstance(mFamilyDetailModel,mCategoryModel,mIncomeDetailModel, fragmentManager, familyID));
        // LifeInsuranceListFragment fragment = new LifeInsuranceListFragment();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, incomeFrag);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onDeleteIncomeClick() {
        //Log.e("Sucess","OnClick on Delete");
    }

}

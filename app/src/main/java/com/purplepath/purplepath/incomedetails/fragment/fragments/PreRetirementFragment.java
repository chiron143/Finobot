package com.purplepath.purplepath.incomedetails.fragment.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.famlydetail.model.AddFamilyDetailModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomedetails.fragment.IncomefromFamilyDetails;
import com.purplepath.purplepath.incomedetails.fragment.InterfaceTab.OnIncomeClickLisaner;
import com.purplepath.purplepath.incomedetails.fragment.adapter.IncomeListAdapter;
import com.purplepath.purplepath.incomedetails.fragment.model.GetIncomeModel;
import com.purplepath.purplepath.incomedetails.fragment.model.IncomeCategoryModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.Notification.adapters.PromptsRecyclerViewAdapter.fragmentManager;

/**
 * Created by dinesh on 20/06/16.
 */
public class PreRetirementFragment extends BaseFragment implements OnIncomeClickLisaner {
//    private ImageView addIncomeDetailsBtn;
    private Context mContext;
    private AddFamilyDetailModel mFamilyDetailModel;
    private IncomeCategoryModel mCategoryModel;
    private GetIncomeModel mIncomeDetailModel;
    private ListView mincomeListView;
    private RelativeLayout mlistcontainerLayout ;
    private RelativeLayout addbuttonContainerLayout ;
    private FloatingActionButton addFloatingBtn ;
    private OnIncomeClickLisaner onIncomeClickLisaner;
    private IncomeCategoryModel incomeCategoryModel;


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
          onIncomeClickLisaner=this;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View preRetirementView = inflater.inflate(R.layout.fragment_pre_retirement_adapter_view, container, false);
//        add_buttonParent=(RelativeLayout)preRetirementView.findViewById(R.id.add_button_parent_layout);
        mincomeListView = preRetirementView.findViewById(R.id.Institution_liability_list);
        TextView titleAddView= preRetirementView.findViewById(R.id.goal_headear_bg_TxtView);
        titleAddView.setText("Please click + button to fill pre-retirement income ");

//        if(mFamilyDetailModel!=null){
//            try {
//                getIncomeDetail(mFamilyDetailModel);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }else{
            try{
               callGetFamilyDetail(UtileKit.getPersistedPurplePathPref("user_id"));
        }catch (Exception e){
            e.printStackTrace();
        }
//        }
        if(mCategoryModel == null){
            callIncomeCategoryCode();
        }

//        ImageView institutionDetailsBtn = (ImageView) preRetirementView.findViewById(R.id.preretirementAddBtnId);
//        institutionDetailsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (mliabCategoryModel != null)
////                    LiabilityDialogFragment.newInstance(null, mliabCategoryModel,"Individuals", onCheckListIsEmptyOrNot).show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
//            }
//        });
        mlistcontainerLayout = preRetirementView.findViewById(R.id.libi_parent_listView);
        addbuttonContainerLayout = preRetirementView.findViewById(R.id.add_button_parent_layout);
        addFloatingBtn = preRetirementView.findViewById(R.id.addIncome_btn);
        addFloatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         String familyID = "0";
                        addFragmenttoStack(IncomefromFamilyDetails.newInstance(mFamilyDetailModel,mCategoryModel,mIncomeDetailModel, fragmentManager, familyID));

            }
        });
//        addIncomeDetailsBtn=(FloatingActionButton)preRetirementView.findViewById(R.id.preretirementAddBtnId);
//        addIncomeDetailsBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String familyID = "0";
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                Fragment incomeFrag=IncomefromFamilyDetails.newInstance(mFamilyDetailModel,mCategoryModel,mIncomeDetailModel, fragmentManager, familyID);
//                // LifeInsuranceListFragment fragment = new LifeInsuranceListFragment();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, incomeFrag);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();
//
//            }
//        });
        return preRetirementView;
    }

    public static PreRetirementFragment newInstance(int position) {
        PreRetirementFragment fragment = new PreRetirementFragment();
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
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
                UtileKit.dismisssSpinnerDialog();

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
                 incomeCategoryModel=response.body();
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
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
                UtileKit.dismisssExploreSpinnerDialog();

            }
        });

    }

    private void getIncomeDetail(final AddFamilyDetailModel mFamilyDetail) {

        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<GetIncomeModel> call = webServiceObj.GetIncomeDetailService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetIncomeModel>() {
            @Override
            public void onResponse(Call<GetIncomeModel> call, Response<GetIncomeModel> response) {
                //Log.e("CallBack", " response is " + call.toString());

                GetIncomeModel incomeDetail = response.body();
                if (incomeDetail.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    GetIncomeModel incomeDetailModel = incomeDetail;
                    mIncomeDetailModel=incomeDetailModel;
//                    addIncomeDetailsBtn.setVisibility(View.GONE);
                    mlistcontainerLayout.setVisibility(View.VISIBLE);
                    addbuttonContainerLayout.setVisibility(View.GONE);
                    IncomeListAdapter   adapter=new IncomeListAdapter(mContext, mIncomeDetailModel.getData().getUser_incomes(),mFamilyDetail,onIncomeClickLisaner);
                    mincomeListView.setAdapter(adapter);
//
//         addViewto(fragmentview,mIncomeCategoryModel)
                } else {
//                    addIncomeDetailsBtn.setVisibility(View.VISIBLE);
                    mlistcontainerLayout.setVisibility(View.GONE);
                    addbuttonContainerLayout.setVisibility(View.VISIBLE);
                    mincomeListView.setVisibility(View.GONE);
//                    UtileKit.alertDialog(
//                            incomeDetail.getData().getMessage(),
//                            mContext);
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<GetIncomeModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    @Override
    public void onIncomeClick(String familyID) {
        addFragmenttoStack(IncomefromFamilyDetails.newInstance(mFamilyDetailModel,mCategoryModel,mIncomeDetailModel, fragmentManager, familyID));

    }

    @Override
    public void onDeleteIncomeClick() {
        //Log.e("Sucess","OnClick on Delete");
    }



}

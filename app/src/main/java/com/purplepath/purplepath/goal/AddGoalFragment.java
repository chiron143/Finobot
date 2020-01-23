//package com.purplepath.purplepath.goal;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.incomedetails.fragment.IncomeDetail;
//import com.purplepath.purplepath.incomedetails.fragment.IncomefromFamilyDetails;
//import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
//import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
//import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class AddGoalFragment extends Fragment implements View.OnClickListener{
//    private Context mContext;
//    private ImageView imageView;
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//    private OnActivityBackPressedListener mCallBackListener;
//    private static GoalFamilyDetailsModel addFamilyDetailModel;
//    public AddGoalFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mContext = getContext();
//        try {
//            mCallBackListener = (OnActivityBackPressedListener) (mContext);
//        }catch(ClassCastException e)
//        {
//            e.printStackTrace();
//        }
//
//        catch(Exception e)
//        {}
//        FragmentTransaction fragmentTransaction;
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//
//        if(HomePageActivity.goallistsize>0){
//            GoalsListFragment fragment = new GoalsListFragment();
//            getActivity().getSupportFragmentManager().popBackStack();
//            fragmentTransaction.replace(R.id.fragment_container, fragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commitAllowingStateLoss();
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//       View addgoalsView=  inflater.inflate(R.layout.fragment_add_goal, container, false);
//       // addgoalsView = inflater.inflate(R.layout.fragment_goal_imageview, container, false);
//
//        callFamilyDetailsService();
//
//        mCallBackListener.setActionBarTitle("Add Goal");
//        imageView = (ImageView) addgoalsView.findViewById(R.id.goal_click_image);
//        imageView.setBackgroundResource(R.drawable.ic_add_icon_big);
//        mleftRelativeLayout = (RelativeLayout) addgoalsView.findViewById(R.id.relative_left_arrow);
//        mcenterRelativeLayout = (RelativeLayout) addgoalsView.findViewById(R.id.relative_center_home);
//        mRightRelativeLayout = (RelativeLayout) addgoalsView.findViewById(R.id.relative_right_arrow);
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//        mRightRelativeLayout.setOnClickListener(this);
////		toolbar.setOnMenuItemClickListener(new Toolbar.On
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle args = new Bundle();
//                args.putString("goalid", "");
//                args.putSerializable("GoalFamilyDetails",addFamilyDetailModel);
////                GoalDetailFragment goalDetailFragment = new GoalDetailFragment(mOnGoalDoneSelectedListener);
////                goalDetailFragment.setArguments(args);
////                goalDetailFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
//
//            }
//        });
//
//        FloatingActionButton fab = (FloatingActionButton) addgoalsView.findViewById(R.id.add_goal_fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                IncomefromFamilyDetails fragment = new IncomefromFamilyDetails();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();
//            }
//        });
//
//
//        return  addgoalsView;
//
//    }
//
//    private void callFamilyDetailsService() {
//        UtileKit.showSpinnerDialog(mContext, false);
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<GoalFamilyDetailsModel> call = webServiceObj.callFamilyDetailsListService(UtileKit.getPersistedPurplePathPref("user_id"));
//        call.enqueue(new Callback<GoalFamilyDetailsModel>() {
//            @Override
//            public void onResponse(Call<GoalFamilyDetailsModel> call, Response<GoalFamilyDetailsModel> response) {
//                ////Log.e("CallBack", " family is " + call.toString());
//                addFamilyDetailModel = response.body();
//                UtileKit.dismisssSpinnerDialog();
//               /* if (addFamilyDetailModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
//                    familyDetails = addFamilyDetailModel.getData().getFamily_details();
//                    if (UtileKit.validateObjectValues(familyDetails)) {
//                        if(!familyDetails.isEmpty()) {
//                            for (int i = 0; i < familyDetails.size(); i++) {
//                                mgoalBelongsToArrayList.add(familyDetails.get(i).getName());
//                                mgoalBelongsToArrayListId.add(familyDetails.get(i).getId());
//                            }
//                            //mgoal_family_layout.setVisibility(View.VISIBLE);
//                        }
//                        //      UtileKit.setArrayListSpinnerAdapter(mgoalBelongsToSpinner, mgoalBelongsToArrayList, activity);
//                        //setSpinnerAdapter(mgoalBelongsToSpinner, mgoalBelongsToArrayList, activity);
//                    }
//                }*/
//            }
//            @Override
//            public void onFailure(Call<GoalFamilyDetailsModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
//                UtileKit.dismisssSpinnerDialog();
//                UtileKit.alertRetrofitExceptionDialog( mContext,t);
//            }
//        });
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//    }
//
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//
//    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//
//            case R.id.goal_add_layout:{
//                Bundle args = new Bundle();
//                args.putString("goalid", "");
////                GoalDetailFragment goalDetailFragment = new GoalDetailFragment(mOnGoalDoneSelectedListener);
////                goalDetailFragment.setArguments(args);
////                goalDetailFragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), AppConstants.SHOWGOALDETAILS_TAG);
//            }
//            break;
//            case R.id.relative_left_arrow:
//            {
//                mCallBackListener.onActivityBackPressed();
//            }
//            break;
//            case R.id.relative_center_home:
//            {
//                Intent i = new Intent(getActivity(), HomePageActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
////                getActivity().finish();
//            }
//            break;
//            case R.id.relative_right_arrow:
//            {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                IncomeDetail fragment = new IncomeDetail();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();
//
//            }
//        }
//    }
//
//}

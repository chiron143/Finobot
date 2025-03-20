package com.purplepath.purplepath.goaltimeline.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.goalanalysis.adapter.GoalsListViewAdapter;
import com.purplepath.purplepath.goalanalysis.model.Short_goal;
import com.purplepath.purplepath.goalanalysis.singlegoal.SingleGoalDonutview;
import com.purplepath.purplepath.goaltimeline.retirement_models.RetirementModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoalDemoFragment extends Fragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private  RetirementModel mRetirementModel;

     private ListView goalListView;

    private GoalsListViewAdapter mGoalAdapter;

    private Context mContext;

    public static GoalDemoFragment newInstance(String fh) {

        Bundle args = new Bundle();
        args.putString("key",fh);
        GoalDemoFragment fragment = new GoalDemoFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public GoalDemoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        try {
            setHasOptionsMenu(true);
            backPressedListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_goal_demo, container, false);
        String title=getArguments().getString("key");
        backPressedListener.setActionBarTitle(title);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        goalListView = view.findViewById(R.id.goalListViewId);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        callGetRetirmentService();

        goalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onclickPostionStartFragment(position);
            }
        });
        return view;
    }

    private void onclickPostionStartFragment(int position) {
        try {
            if (mRetirementModel.getData().getUser_goals() != null) {
                if (mRetirementModel.getData().getUser_goals().get(position).getId() != null) {
                    addFragmenttoStack(SingleGoalDonutview.newInstance(mRetirementModel.getData().getUser_goals().get(position).getId(),
                            mRetirementModel.getData().getUser_goals().get(position).getGoal_name(),"retirementview",
                            mRetirementModel.getData().getUser_goals().get(position).getGoal_years(),
                            mRetirementModel.getData().getUser_goals().get(position).getExpected_increment()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void callGetRetirmentService() {

        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(getActivity(), false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<RetirementModel> call = webServiceObj.callget_ret_goals_by_user(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<RetirementModel>() {
            @Override
            public void onResponse(Call<RetirementModel> call, Response<RetirementModel> response) {
                UtileKit.dismisssSpinnerDialog();
                Log.i("success", "callget_ret_goals_by_user in table view" + response.body());
                mRetirementModel = response.body();
                insertValueinListView(mRetirementModel);

            }
            @Override
            public void onFailure(Call<RetirementModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( getActivity(),t);
            }
        });
    }

    private void insertValueinListView(RetirementModel mRetirementModel) {
        if (mRetirementModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
            setAdapterListView(mRetirementModel.getData().getUser_goals());
        }
    }
    private void setAdapterListView(ArrayList<Short_goal> user_goals) {
        mGoalAdapter = new GoalsListViewAdapter(mContext, 0, user_goals);
        goalListView.setAdapter(mGoalAdapter);
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);

                startActivity(i);
                break;

        }

    }

    /**
     * Start fragment in R.id.fragment_container
     *
     * @param mfagment
     */
    public void addFragmenttoStack(Fragment mfagment) {
        try {


            if (!mfagment.isVisible()) {
                androidx.fragment.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, mfagment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
//                HomePageActivity.collapse(mDropdownLayout);

            }
            if((getActivity() instanceof  HomePageActivity))
            {
                ((HomePageActivity)getActivity()).closeDropDownTab();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_detail);
        super.onCreateOptionsMenu(menu, inflater);
    }

}

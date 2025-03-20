package com.purplepath.purplepath.goalplanning;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.cashmanaganalysis.CashmanagementInOutFragment;
import com.purplepath.purplepath.cashmanaganalysis.CashmanagementdetailFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goal.GoalsListFragment;
import com.purplepath.purplepath.goalanalysis.adapter.GoalsListViewAdapter;
import com.purplepath.purplepath.goalanalysis.model.GoalAnalysisModel;
import com.purplepath.purplepath.goalanalysis.model.Short_goal;
import com.purplepath.purplepath.goalanalysis.singlegoal.SingleGoalDonutview;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dinesh on 01/08/17.
 */

public class GoalPlanningFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.totalGoalid)
    TextView totalGoalBtn;

    @BindView(R.id.total_goal)
    LinearLayout totalGoal;

    @BindView(R.id.shortTermId)
    TextView shortTermBtn;

    @BindView(R.id.mediumTermId)
    TextView mediumTermBtn;

    @BindView(R.id.longTermId)
    TextView longTermBtn;

    @BindView(R.id.greenId)
    Button greenBtn;

    @BindView(R.id.amberId)
    Button amberBtn;

    @BindView(R.id.redId)
    Button redBtn;

    @BindView(R.id.goalListViewId)
    ListView goalListView;

    @BindView(R.id.title_viewId)
    AppCompatTextView titleViewTxt;


    @BindView(R.id.relative_right_arrow)
    RelativeLayout mRightRelativeLayout;


    @BindView(R.id.relative_center_home)
    RelativeLayout mcenterRelativeLayout;

    @BindView(R.id.relative_left_arrow)
    RelativeLayout mleftRelativeLayout;

    @BindView(R.id.linear_short_term_goal)
    LinearLayout linear_short_term_goal;


    @BindView(R.id.linear_medium_term_goal)
    LinearLayout linear_medium_term_goal;

    @BindView(R.id.linear_long_term_goal)
    LinearLayout linear_long_term_goal;

    @BindView(R.id.totalGoals)
    AppCompatButton totalGoals;


    Context mContext;

    GoalsListViewAdapter mGoalAdapter;
    GoalAnalysisModel goalAnalysisModel;

    private ArrayList<Short_goal> totalgoalObj;
    private OnActivityBackPressedListener mCallBackListener;
    FloatingActionButton fab_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_planning_frag_view, container, false);
        ButterKnife.bind(this, view);
        mCallBackListener.setActionBarTitle("Goal Plan");


        fab_id = view.findViewById(R.id.fab_id);
        fab_id.setOnClickListener(this);

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        shortTermBtn.setOnClickListener(this);
        mediumTermBtn.setOnClickListener(this);
        longTermBtn.setOnClickListener(this);
        totalGoalBtn.setOnClickListener(this);
        linear_short_term_goal.setOnClickListener(this);
        totalGoal.setOnClickListener(this);
        totalGoals.setOnClickListener(this);
        linear_medium_term_goal.setOnClickListener(this);
        linear_long_term_goal.setOnClickListener(this);
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
            if (totalgoalObj != null) {
                if (totalgoalObj.get(position).getId() != null) {
                    addFragmenttoStack(SingleGoalDonutview.newInstance(totalgoalObj.get(position).getId(),
                            totalgoalObj.get(position).getGoal_name(), "GoalView",
                            totalgoalObj.get(position).getGoal_years(), totalgoalObj.get(position).getExpected_increment()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callGoalAnalysisService();
    }

    @Override
    public void onClick(View v) {
        totalgoalObj = new ArrayList<Short_goal>();
        switch (v.getId()) {

            case R.id.totalGoalid:
                getTotalGoal(totalgoalObj, goalAnalysisModel);
                break;
            case R.id.totalGoals:
                getTotalGoal(totalgoalObj, goalAnalysisModel);
                break;
            case R.id.total_goal:
                getTotalGoal(totalgoalObj, goalAnalysisModel);
                break;
            case R.id.fab_id:

                addFragmenttoStack(new GoalsListFragment());

                break;

            case R.id.linear_short_term_goal:
                if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det()))
                    if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det().getShort_goal())) {
                        totalgoalObj.addAll(goalAnalysisModel.getData().getGoal_det().getShort_goal());
                        setAdapterListView(totalgoalObj);
                    }
                break;
            case R.id.linear_medium_term_goal:
                if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det()))
                    if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det().getMed_goal())) {
                        totalgoalObj.addAll(goalAnalysisModel.getData().getGoal_det().getMed_goal());
                        setAdapterListView(totalgoalObj);
                    }
                break;
            case R.id.linear_long_term_goal:
                if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det()))
                    if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det().getLong_goal())) {
                        totalgoalObj.addAll(goalAnalysisModel.getData().getGoal_det().getLong_goal());
                        setAdapterListView(totalgoalObj);
                    }
                break;
            case R.id.greenId:
                break;
            case R.id.amberId:
                break;
            case R.id.redId:
                break;

            case R.id.relative_left_arrow: {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home: {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
//                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow: {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                LiabilitiesTabViewFragment fragment = new LiabilitiesTabViewFragment();
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();

            }
            break;
            default:
                break;

        }
    }

    public void callGoalAnalysisService() {
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalAnalysisModel> call = webServiceObj.callGoalAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GoalAnalysisModel>() {
            @Override
            public void onResponse(Call<GoalAnalysisModel> call, Response<GoalAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success", "" + response.body());
                goalAnalysisModel = response.body();

                if (goalAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if (UtileKit.validateObjectValues(goalAnalysisModel)) {
                        titleViewTxt.setText(getString(R.string.totalgoal));
                        totalGoalBtn.setText(goalAnalysisModel.getData().getGoal_det().getTot_goals());
                        String shortSize = "";
                        if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det().getShort_goal()))
                            shortSize = "" + goalAnalysisModel.getData().getGoal_det().getShort_goal().size();
                        //shortTermBtn.setText(getString(R.string.shortgoal).concat(" ").concat(shortSize));
                        shortTermBtn.setText(shortSize);
                        String mediumSize = "";
                        if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det().getMed_goal()))
                            mediumSize = "" + goalAnalysisModel.getData().getGoal_det().getMed_goal().size();
                        mediumTermBtn.setText(mediumSize);
                        String longSize = "";
                        if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det().getLong_goal()))
                            longSize = "" + goalAnalysisModel.getData().getGoal_det().getLong_goal().size();
                        longTermBtn.setText(longSize);
                        totalgoalObj = new ArrayList<>();
                        getTotalGoal(totalgoalObj, goalAnalysisModel);
                        if (!totalgoalObj.isEmpty()) {
                            setAdapterListView(totalgoalObj);

                        }

                    }


                }
            }

            @Override
            public void onFailure(Call<GoalAnalysisModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });

    }

    private void setAdapterListView(ArrayList<Short_goal> totalgoalObj) {
        mGoalAdapter = new GoalsListViewAdapter(mContext, 0, totalgoalObj);
        goalListView.setAdapter(mGoalAdapter);
    }

    private void getTotalGoal(ArrayList<Short_goal> totalgoalObj, GoalAnalysisModel goalAnalysisModel) {
        if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det())) {
            if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det().getShort_goal()))
                totalgoalObj.addAll(goalAnalysisModel.getData().getGoal_det().getShort_goal());

            if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det().getMed_goal()))
                totalgoalObj.addAll(goalAnalysisModel.getData().getGoal_det().getMed_goal());
            if (UtileKit.validateObjectValues(goalAnalysisModel.getData().getGoal_det().getLong_goal()))
                totalgoalObj.addAll(goalAnalysisModel.getData().getGoal_det().getLong_goal());

            setAdapterListView(totalgoalObj);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        MenuItem item = menu.findItem(R.id.menu_summary);
        MenuItem items = menu.findItem(R.id.menu_detail);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_detail:

                try {
//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    CashmanagementdetailFragment fragment =
                    addFragmenttoStack(new CashmanagementdetailFragment());
//                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
//                    fragmentTransaction.commitAllowingStateLoss();
                    // mCallBackListener.onActivityBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:

                try {
//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    CashmanagementInOutFragment fragment =
                    addFragmenttoStack(new CashmanagementInOutFragment());
//                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
//                    fragmentTransaction.commitAllowingStateLoss();
                    //  mCallBackListener.onActivityBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }


}

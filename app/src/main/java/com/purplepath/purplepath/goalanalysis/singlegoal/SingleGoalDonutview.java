package com.purplepath.purplepath.goalanalysis.singlegoal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyTextView;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goaltimeline.GoalTableViewFragment;
import com.purplepath.purplepath.goaltimeline.GoalTimeLineCashFlow;
import com.purplepath.purplepath.goaltimeline.getGoalPlanModel.GoalPlanModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Suresh on 17/07/17.
 */

public class SingleGoalDonutview extends BaseFragment implements
        View.OnClickListener {
    OnActivityBackPressedListener mCallBackListener;

    ImageView link_image;

    LinearLayout maddviewLinearlayout, layout_retirement_need;

    private ArrayList<Integer> maddPercentagevaluecolor = new ArrayList<>();

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private GoalPlanModel mGoalPlanModel;

    private String goalId, goldName, mtypeofview, yearof_goals;

    private CustomTextView title, headingText, retirement_need_title,
            expected_amount_title, required_amount_title, goal_value;

    private CurrencyTextView retirement_need_value,
            expected_amount_value, required_amount_value;

    private String expected_increment;

    public static SingleGoalDonutview newInstance(String mGoalId, String mGoalname, String goalView,
                                                  String mGoalyears, String mExpectedincrement) {
        Bundle args = new Bundle();
        SingleGoalDonutview fragment = new SingleGoalDonutview();
        args.putString("mGoalId", mGoalId);
        args.putString("mGoalname", mGoalname);
        args.putString("mGoalview", goalView);
        args.putString("mGoalyears", mGoalyears);
        args.putString("mExpectedincrement", mExpectedincrement);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        if (getArguments().containsKey("mGoalId")) {
            goalId = getArguments().getString("mGoalId");
        }
        if (getArguments().containsKey("mGoalname")) {
            goldName = getArguments().getString("mGoalname");
        }
        if (getArguments().containsKey("mGoalview")) {
            mtypeofview = getArguments().getString("mGoalview");
        }
        if (getArguments().containsKey("mGoalyears")) {
            yearof_goals = getArguments().getString("mGoalyears");
        }
        if (getArguments().containsKey("mExpectedincrement"))
            expected_increment = getArguments().getString("mExpectedincrement");

        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.single_donut_view, container, false);
        mCallBackListener.setActionBarTitle("Goal Plan");
        initalizeView(view);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        if (UtileKit.validateObjectValues(goldName)) {
            title.setText(goldName);
        }

        if (expected_increment.equalsIgnoreCase("")) {
            UtileKit.intitializeAlertDialog(getString(R.string.expected_inc), mContext);
        }

        callGetGoalService(goalId);
        String[] colorsTxt = mContext.getResources().getStringArray(R.array.arrays_colors);
        for (int i = 0; i < colorsTxt.length; i++) {
            int newColor = Color.parseColor(colorsTxt[i]);
            maddPercentagevaluecolor.add(newColor);
        }
        return view;
    }


    public void callGetGoalService(String id) {

        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(getActivity(), false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalPlanModel> call = webServiceObj.callGetGoalService(UtileKit.getPersistedPurplePathPref("user_id"), id);
        call.enqueue(new Callback<GoalPlanModel>() {
            @Override
            public void onResponse(Call<GoalPlanModel> call, Response<GoalPlanModel> response) {
                UtileKit.dismisssSpinnerDialog();
                Log.i("success", "callGetGoalService...." + response.body());
                mGoalPlanModel = response.body();
                try {
                    singleGoalLine(mGoalPlanModel);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GoalPlanModel> call, Throwable t) {
                Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(getActivity(), t);
            }
        });
    }

    private void singleGoalLine(GoalPlanModel mGoalPlanModel) {

        int rowSize = mGoalPlanModel.getData().getGoal_plan().size();
        View view;
        for (int i = 0; i < rowSize; i++) {

            view = LayoutInflater.from(mContext).inflate(R.layout.single_goal_line_items, null);

            TextView textprogressview = view.findViewById(R.id.textprogressview);

            DonutProgress donutprogressview = view.findViewById(R.id.donutprogressview);

            View mimage = view.findViewById(R.id.lineImageview);

            try {
                if (mGoalPlanModel.getData().getGoal_plan().get(i).getCost() != null) {
                    donutprogressview.setDonut_progress(
                            String.valueOf(Math.round(Float.parseFloat(mGoalPlanModel.getData().getGoal_plan().get(i).getShade()))));
                }

                if (mGoalPlanModel.getData().getGoal_plan().get(i).getCost() != null) {


                    textprogressview.setText("Amount " + UtileKit.longvalueabsolute(Float.parseFloat(roundofftheEpowervalue(mGoalPlanModel.getData().getGoal_plan().get(i).getCost()))) + " |  Year " + mGoalPlanModel.getData().getGoal_plan().get(i).getYear());
                }

                donutprogressview.setFinishedStrokeColor(maddPercentagevaluecolor.get(i));

                if (i == rowSize - 1) {
                    mimage.setVisibility(View.GONE);
                }

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            maddviewLinearlayout.addView(view);
        }

        if (UtileKit.validateObjectValues(mtypeofview)) {
            if (mtypeofview.equalsIgnoreCase("GoalView")) {
                layout_retirement_need.setVisibility(View.GONE);
                headingText.setText("Years to Goal");
                goal_value.setText(yearof_goals);
                expected_amount_title.setText("Expected Amount");
                expected_amount_value.setText(UtileKit.currencyConvert(mGoalPlanModel.getData().getGoal_plan_cal().getPmt_yearly()));
                required_amount_title.setText("Required Amount");
                required_amount_value.setText(UtileKit.currencyConvert(mGoalPlanModel.getData().getGoal_plan_cal().getPmt_yearly()));
            } else {
                layout_retirement_need.setVisibility(View.VISIBLE);
                headingText.setText("Years to Retirement");
                goal_value.setText(yearof_goals);
                retirement_need_title.setText("Retirement Need");
                retirement_need_value.setText(UtileKit.currencyConvert(mGoalPlanModel.getData().getGoal_plan_cal().getFv()));
                expected_amount_title.setText("Expected Amount");
                expected_amount_value.setText(UtileKit.currencyConvert(mGoalPlanModel.getData().getGoal_plan_cal().getPmt_yearly()));
                required_amount_title.setText("Required Amount");
                required_amount_value.setText(UtileKit.currencyConvert(mGoalPlanModel.getData().getGoal_plan_cal().getPmt_yearly()));
            }
        }
    }

    private String roundofftheEpowervalue(String cost) {
        try {
            Double d = Double.valueOf(cost);
            System.out.println("exponential = " + d);

            String formatMask = "0.################################################";
            DecimalFormat df = new DecimalFormat(formatMask);

            System.out.println("normal number=" + df.format(d));
            return df.format(d);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return cost;
    }


    private void initalizeView(View view) {

        link_image = view.findViewById(R.id.link_image);

        title = view.findViewById(R.id.title);

        headingText = view.findViewById(R.id.headingText);

        goal_value = view.findViewById(R.id.goal_value);

        retirement_need_title = view.findViewById(R.id.retirement_need_title);

        retirement_need_value = view.findViewById(R.id.retirement_need_value);

        expected_amount_title = view.findViewById(R.id.expected_amount_title);

        expected_amount_value = view.findViewById(R.id.expected_amount_value);

        required_amount_title = view.findViewById(R.id.required_amount_title);

        required_amount_value = view.findViewById(R.id.required_amount_value);

        maddviewLinearlayout = view.findViewById(R.id.addViewsinglegoal);

        layout_retirement_need = view.findViewById(R.id.layout_retirement_need);

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);

        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);

        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relative_left_arrow: {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home: {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            break;

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_cashmanagement, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_graph:
                GoalTimeLineCashFlow mGoalTimeLineCashFlow = GoalTimeLineCashFlow.newInstance(mGoalPlanModel,
                        goalId, goldName, yearof_goals, expected_increment);
                showFragment(mGoalTimeLineCashFlow);
                break;
            case R.id.menu_details:
                GoalTableViewFragment mGoalTableLineCashFlow = GoalTableViewFragment.newInstance(goalId, goldName,
                        yearof_goals, expected_increment);
                showFragment(mGoalTableLineCashFlow);
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

}

package com.purplepath.purplepath.goaltimeline;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.cashflowmanagmentchart.CashflowChartFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goal.GoalsListFragment;
import com.purplepath.purplepath.goalanalysis.dailogFragment.MutipleGoalDialog;
import com.purplepath.purplepath.goalanalysis.singlegoal.SingleGoalDonutview;
import com.purplepath.purplepath.goaltimeline.model.GoalAgeTimeLineModel;
import com.purplepath.purplepath.goaltimeline.model.GoalTimeLineModel;
import com.purplepath.purplepath.goaltimeline.model.Goal_tmln;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.service_base_url;
import static com.squareup.picasso.Picasso.with;

/**
 * Created by dinesh on 13/11/16.
 */
public class GoalTimeLineFragment extends BaseFragment implements View.OnClickListener, OnItemClickListnerInterfaces {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    ImageView clickMeId;
    Button submitBtn;
    EditText submitText;
    LinearLayout rLayout;
    TextView errorTextview;
    Fragment fragment;
    OnItemClickListnerInterfaces getpositionInterface;
    private boolean isClicked = true;
    private LinearLayout checkboxLayout;
    private Context mContext;
    private ArrayList<CheckBox> userNameCheckBox = new ArrayList<>();
    private ArrayList<Goal_tmln> mGoalTimeLineModel;
    private OnActivityBackPressedListener mCallBackListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private FloatingActionButton mGoalTimeLieFabBtn;
    private HashMap<String, Boolean> checkBoxState = new HashMap<>();

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        getpositionInterface = this;
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    public void setListener(OnItemClickListnerInterfaces callbackInterface) {
        this.getpositionInterface = callbackInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View timeLineViewView;

        mContext = container.getContext();
        mCallBackListener.setActionBarTitle("My Timeline");
        timeLineViewView = inflater.inflate(R.layout.fragment_time_line,
                container, false);
        clickMeId = timeLineViewView.findViewById(R.id.clickmeId);
        submitBtn = timeLineViewView.findViewById(R.id.submitButtonId);
        rLayout = timeLineViewView.findViewById(R.id.addViewId);
        submitText = timeLineViewView.findViewById(R.id.getValueId);
        checkboxLayout = timeLineViewView.findViewById(R.id.checkboxLayout);
        errorTextview = timeLineViewView.findViewById(R.id.errorTextview);
        mleftRelativeLayout = timeLineViewView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = timeLineViewView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = timeLineViewView.findViewById(R.id.relative_right_arrow);
        mGoalTimeLieFabBtn = timeLineViewView.findViewById(R.id.timeline_fab_id);
        mGoalTimeLieFabBtn.setOnClickListener(this);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rLayout.removeAllViews();
                if (submitText.getText().toString().trim().length() != 0) {
                    int count = Integer.parseInt(submitText.getText().toString());
//                    setView(count, goalTimeLineModel.getData().getGoal_tmln());
                }
            }
        });


        callGoalTimeLineService();
        return timeLineViewView;
    }

    void setView(ArrayList<Goal_tmln> goal_tmln) {
        try {
            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;
            int mAge = 0;
            int mLifeExpatancy = 20;
            Calendar today = Calendar.getInstance();
            int curentYear = today.get(Calendar.YEAR);
            ArrayList<GoalAgeTimeLineModel> mGoalAgeTimeLine = new ArrayList<>();
            if (!goal_tmln.isEmpty()) {
                rLayout.removeAllViews();
                // if(null!=goal_tmln.get(0).getDob()){

                String[] dob = goal_tmln.get(0).getDob().split("-");


                if (null != dob) {
                    try {
                        int year = Integer.parseInt(dob[0]);
                        int month = Integer.parseInt(dob[1]);
                        int day = Integer.parseInt(dob[2]);
                        /**
                         * Get Current Age
                         */
                        mAge = UtileKit.getAge(year, month, day);
                        try {
                            mLifeExpatancy = Integer.parseInt(goal_tmln.get(0).getLife_expectancy_age());
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                            mLifeExpatancy=0;
                        }
                        /**
                         * get Total Expected Life
                         */
                        if (mLifeExpatancy < mAge)
                            mLifeExpatancy = mLifeExpatancy + mAge;

                        for (int age = mAge; age <= mLifeExpatancy; age++) {
//                        ArrayList<GoalAgeTimeLineModel> mGoalTimeArray = new ArrayList<>();
                            GoalAgeTimeLineModel mGoalTime = new GoalAgeTimeLineModel();
                            mGoalTime.setmAge(age);
                            mGoalTime.setmYear(curentYear);
                            mGoalTime.setmLifeExpectancy(mLifeExpatancy);
//                        mGoalTimeArray.add(mGoalTime);
                            mGoalAgeTimeLine.add(mGoalTime);
                            curentYear++;

                        }
                        /**
                         * Add All Goals to TimeLine till Life Expectancy
                         */
                        for (Goal_tmln goal : goal_tmln) {
                            String goalDate = goal.getGoal_end_date();
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            Date endDate = df.parse(goalDate);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(endDate);
                            int mMonth = cal.get(Calendar.MONTH);
                            int mdate = cal.get(Calendar.DATE);
                            int mYear = cal.get(Calendar.YEAR);
//                            int mGoalAge = UtileKit.getDurationAge(mYear, mMonth, mdate);
                            int mGAge = 0;
                            int nextGoal = 0;
                            int goalFrequency = 0;
                            int interval= 0;
                            int mFixIntial=0;
                            int durationRepet=0;
                            int durationIntial=0;


                            try {

                                /**
                                 * initialize goal frequency
                                 */
                                if (!StringUtils.isEmpty(goal.getGoal_frequency())) {

                                    goalFrequency = Integer.parseInt(goal.getGoal_frequency());
                                    if(goalFrequency==0)
                                    {
                                        goalFrequency=1;

                                    }
                                }


                                /**
                                 * initialize duration
                                 */
                                if(!StringUtils.isEmpty(goal.getGoal_duration()))
                                {
                                    durationIntial= Integer.parseInt(goal.getGoal_duration());
                                    durationRepet= Integer.parseInt(goal.getGoal_duration());
                                    if(durationIntial==0)
                                    {
                                        durationIntial=1;
                                        durationRepet=1;
                                    }


                                }
                                /**
                                 * intialize interval
                                 */
                                if(!StringUtils.isEmpty(goal.getGoal_interval()))
                                {
                                    interval= Integer.parseInt(goal.getGoal_interval());
                                    mFixIntial= Integer.parseInt(goal.getGoal_interval());
                                }


                                /**
                                 * Initial Year Of Goal
                                 */
                                mGAge = mAge + Integer.parseInt(goal.getGoal_years());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            for (int i = 0; i < mGoalAgeTimeLine.size(); i++) {

                                if ((mGoalAgeTimeLine.get(i).getmAge()) == mGAge) {


                                        if (goal.getGoal_recurrence().equalsIgnoreCase("Y")) {



                                            /**
                                             * Adding frequency with Curent Age  mAge+getGoal_frequency
                                             *
                                             */
//                                        mGoalAge = Math.abs(mGoalAge) + Integer.parseInt(goal.getGoal_frequency());

                                            if (mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine() != null) {
                                                if((durationIntial==0||durationRepet>0) &&goalFrequency>0){
                                                    ArrayList<Goal_tmln> goalTmln = mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine();
                                                    goalTmln.add(goal);
                                                    mGoalAgeTimeLine.get(i).setmGoalAgeTimeLine(goalTmln);
                                                }
                                            } else {
                                                if((durationIntial==0||durationRepet>0) && goalFrequency>0) {
                                                    ArrayList<Goal_tmln> goalTmln = new ArrayList<>();
                                                    goalTmln.add(goal);
                                                    mGoalAgeTimeLine.get(i).setmGoalAgeTimeLine(goalTmln);
                                                }

                                            }
                                            /**
                                             * ADD INTERVAL
                                             */
                                            if(interval==0) {
                                                if (goalFrequency != 0) {
                                                    try {

//                                                    mGAge = (mGoalAgeTimeLine.get(i).getmAge()) + Integer.parseInt(goal.getGoal_interval());
                                                        goalFrequency = goalFrequency - 1;
//                                                durationRepet=durationIntial;
//                                                interval = mFixIntial;
                                                        interval = interval - 1;
                                                        durationRepet=durationRepet-1;
                                                        if(interval==0) {
                                                            mGAge = mGoalAgeTimeLine.get(i).getmAge() + 1;
                                                            durationRepet=durationIntial;
                                                            interval = mFixIntial;
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                            else {
                                                if(interval==mFixIntial)
                                                {
                                                    mGAge = mGoalAgeTimeLine.get(i).getmAge() + 1;
                                                    durationRepet=durationIntial;
                                                    interval = interval - 1;
                                                    durationRepet=durationRepet-1;
//                                            if(interval==0&&goalFrequency != 0)
//                                                mGAge = mGoalAgeTimeLine.get(i).getmAge() + mFixIntial;
                                                }
                                                else {
                                                    interval = interval - 1;
                                                    durationRepet=durationRepet-1;
//                                            if(durationRepet!=0)
                                                    mGAge = mGoalAgeTimeLine.get(i).getmAge() + 1;
                                                    if(interval==0&&goalFrequency != 0) {
                                                        goalFrequency = goalFrequency - 1;
                                                        mGAge = mGoalAgeTimeLine.get(i).getmAge() + 1;
                                                        durationRepet=durationIntial;
                                                        interval = mFixIntial;
                                                    }
                                                }
                                            }
                                        } else {
                                            if (mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine() != null) {
                                                ArrayList<Goal_tmln> goalTmln =      mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine();
                                                goalTmln.add(goal);
                                                mGoalAgeTimeLine.get(i).setmGoalAgeTimeLine(goalTmln);
                                            }
                                            else {
                                                ArrayList<Goal_tmln> goalTmln = new ArrayList<>();
                                                goalTmln.add(goal);
                                                mGoalAgeTimeLine.get(i).setmGoalAgeTimeLine(goalTmln);
                                            }
                                            if((durationRepet>1) ) {
                                                mGAge = mGoalAgeTimeLine.get(i).getmAge() + 1;
                                                durationRepet=durationRepet-1;
                                            }
                                            else {
                                                break;
                                            }
                                        }



                                }
                            }

                        }
                    } catch (ArrayIndexOutOfBoundsException e1) {
                        e1.printStackTrace();
                    } catch (NumberFormatException e2) {
                        e2.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            int size = mGoalAgeTimeLine.size();
            View view;
            for (int i = 0; i < size; i++) {
                if (mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine() == null) {
                    view = LayoutInflater.from(mContext).inflate(R.layout.fragment_time_line_age_view, null);
                    TextView userAgeTxt = view.findViewById(R.id.userAgeId);
                    TextView ageTextView = view.findViewById(R.id.ageViewTxtId);
                    TextView currentYearTxt = view.findViewById(R.id.yearViewTxtId);
                    ageTextView.setText("" + mGoalAgeTimeLine.get(i).getmAge());
                    currentYearTxt.setText("" + mGoalAgeTimeLine.get(i).getmYear());
                    userAgeTxt.setText("" + i);
                    rLayout.addView(view);

                } else {
                    if (mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine().size() > 2)
                        view = LayoutInflater.from(mContext).inflate(R.layout.fragment_time_line_more_view, null);
                    else
                        view = LayoutInflater.from(mContext).inflate(R.layout.fragment_time_line_view, null);

                    LinearLayout leftView = view.findViewById(R.id.left);
                    LinearLayout button2 = view.findViewById(R.id.right);
                    LinearLayout rightTextView = view.findViewById(R.id.rightText);
                    LinearLayout leftTextView = view.findViewById(R.id.leftText);
                    ImageView goalleft1 = view.findViewById(R.id.goalleft1);
                    ImageView goalleft2 = view.findViewById(R.id.goalleft2);
                    ImageView longLineView = view.findViewById(R.id.longLineId);
                    TextView userAgeTxt = view.findViewById(R.id.userAgeId);
                    TextView leftTextYearLable = view.findViewById(R.id.line_Textid);
                    TextView rightTextYearLable = view.findViewById(R.id.line_rightid);
                    ImageView goalRight2 = view.findViewById(R.id.goalright2);
                    ImageView goalRight1 = view.findViewById(R.id.goalright1);
                    TextView agerightId = view.findViewById(R.id.ageleftId);
                    TextView ageleftId = view.findViewById(R.id.agerightId);
                    AppCompatTextView moreRightTxt = view.findViewById(R.id.goalrightmorebtn);
                    AppCompatTextView moreLeftTxt = view.findViewById(R.id.goalleftmorebtn);
                    goalleft1.getLayoutParams().height = width / 9;
                    goalleft2.getLayoutParams().height = width / 9;
                    longLineView.getLayoutParams().height = (int) (width / 5.5);
                    userAgeTxt.setText("" + i);
                    if (!mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine().get(0).getGoal_flexibility().equalsIgnoreCase("Flexible")) {
                        leftView.setVisibility(View.VISIBLE);
                        button2.setVisibility(View.GONE);
                        rightTextView.setVisibility(View.VISIBLE);

//                addNodeCircleView.setVisibility(View.GONE);
//                addChildNodeLeft1.setVisibility(View.GONE);
//                addChildNodeLeft2.setVisibility(View.GONE);
//                addNodeCircleView2.setVisibility(View.GONE);
//                addChildNodeRight1.setVisibility(View.GONE);
//                addChildNodeRight2.setVisibility(View.GONE);
                        setViewId(goalleft1);
                        setViewId(goalleft2);
                        setViewId(moreLeftTxt);
                        rightTextYearLable.setText("" + mGoalAgeTimeLine.get(i).getmYear());
                        agerightId.setText("" + mGoalAgeTimeLine.get(i).getmAge());
                        agerightId.setVisibility(View.VISIBLE);
                        goalleft1.setTag(mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine());
                        goalleft2.setTag(mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine());
                        moreLeftTxt.setTag(mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine());
                        goalleft1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    ArrayList<Goal_tmln> mGoalArray = (ArrayList<Goal_tmln>) v.getTag();
                                    String goalId = mGoalArray.get(0).getId();
                                    String mGoalname = mGoalArray.get(0).getGoal_name();
                                    String mGoalyears = mGoalArray.get(0).getGoal_years();
                                    String mExpectedincrement=mGoalArray.get(0).getExpected_increment();
                                    addFragmenttoStack(SingleGoalDonutview.newInstance(goalId, mGoalname, "GoalView", mGoalyears,mExpectedincrement));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        goalleft2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    ArrayList<Goal_tmln> mGoalArray = (ArrayList<Goal_tmln>) v.getTag();
                                    String goalId = mGoalArray.get(1).getId();
                                    String mGoalname = mGoalArray.get(0).getGoal_name();
                                    String mGoalyears = mGoalArray.get(0).getGoal_years();
                                    String mExpectedincrement=mGoalArray.get(0).getExpected_increment();
                                    addFragmenttoStack(SingleGoalDonutview.newInstance(goalId, mGoalname, "GoalView", mGoalyears,mExpectedincrement));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        moreLeftTxt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    ArrayList<Goal_tmln> mGoalArray = (ArrayList<Goal_tmln>) v.getTag();
//                                String goalId=mGoalArray.get(1).getId();
//                                addFragmenttoStack(MutipleGoalDialog.newInstance(mGoalArray, mContext));
                                    FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
                                    DialogFragment newFragment = MutipleGoalDialog.newInstance(mGoalArray, mContext, getpositionInterface);

                                    newFragment.show(fm, "dialog");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
//                if (goal_tmln.get(i).getImg_url() != null)
                        if (mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine().size() == 2) {
                            goalleft2.setVisibility(View.VISIBLE);
                            try {
                                with(mContext)
                                        .load(service_base_url + mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine().get(1).getImg_url())
                                        .placeholder(R.drawable.icon_4)
                                        .resize(getDeviceWidth() / 9, getDeviceWidth() / 9)
                                        .into(goalleft2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if (mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine().size() > 2) {
                            goalleft2.setVisibility(View.GONE);
                            moreLeftTxt.setVisibility(View.VISIBLE);
                            //Log.e("Goal L2v2", "Set Visible" + mGoalAgeTimeLine.get(i).getmAge());
                        } else {
                            goalleft2.setVisibility(View.GONE);


                        }
                        Picasso.with(mContext)
                                .load(service_base_url + mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine().get(0).getImg_url())
                                .placeholder(R.drawable.icon_4)
                                .resize(getDeviceWidth() / 9, getDeviceWidth() / 9)
                                .into(goalleft1);
                    } else {
                        leftView.setVisibility(View.GONE);
                        button2.setVisibility(View.VISIBLE);
                        leftTextView.setVisibility(View.VISIBLE);
                        leftTextYearLable.setText("" + mGoalAgeTimeLine.get(i).getmYear());
                        ageleftId.setText("" + mGoalAgeTimeLine.get(i).getmAge());
                        ageleftId.setVisibility(View.VISIBLE);
//                addNodeCircleView.setVisibility(View.GONE);
//                addChildNodeRight1.setVisibility(View.GONE);
//                addChildNodeRight2.setVisibility(View.GONE);
//                addNodeCircleView2.setVisibility(View.GONE);
//                addChildNodeLeft1.setVisibility(View.GONE);
//                addChildNodeLeft2.setVisibility(View.GONE);

                        setViewId(goalRight1);
                        setViewId(goalRight2);
                        setViewId(moreRightTxt);
                        goalRight1.setTag(mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine());
                        goalRight2.setTag(mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine());
                        moreRightTxt.setTag(mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine());
                        goalRight1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    ArrayList<Goal_tmln> mGoalArray = (ArrayList<Goal_tmln>) v.getTag();
                                    String goalId = mGoalArray.get(0).getId();
                                    String mGoalname = mGoalArray.get(0).getGoal_name();
                                    String mGoalyears = mGoalArray.get(0).getGoal_years();
                                    String mExpectedincrement=mGoalArray.get(0).getExpected_increment();
                                    addFragmenttoStack(SingleGoalDonutview.newInstance(goalId, mGoalname, "GoalView", mGoalyears,mExpectedincrement));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        goalRight2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    ArrayList<Goal_tmln> mGoalArray = (ArrayList<Goal_tmln>) v.getTag();
                                    String goalId = mGoalArray.get(0).getId();
                                    String mGoalname = mGoalArray.get(0).getGoal_name();
                                    String mGoalyears = mGoalArray.get(0).getGoal_years();
                                    String mExpectedincrement=mGoalArray.get(0).getExpected_increment();
                                    addFragmenttoStack(SingleGoalDonutview.newInstance(goalId, mGoalname, "GoalView", mGoalyears,mExpectedincrement));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        moreRightTxt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    ArrayList<Goal_tmln> mGoalArray = (ArrayList<Goal_tmln>) v.getTag();
//                                String goalId=mGoalArray.get(1).getId();
//                                   addFragmenttoStack(MutipleGoalDialog.newInstance(mGoalArray, mContext));

                                    FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
                                    DialogFragment newFragment = MutipleGoalDialog.newInstance(mGoalArray, mContext, getpositionInterface);

                                    newFragment.show(fm, "dialog");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        if (mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine().size() == 2) {
                            goalRight2.setVisibility(View.VISIBLE);
                            try {
                                Picasso.with(mContext)
                                        .load(service_base_url + mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine().get(1).getImg_url())
                                        .placeholder(R.drawable.icon_4)
                                        .resize(getDeviceWidth() / 9, getDeviceWidth() / 9)
                                        .into(goalRight2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if (mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine().size() > 2) {
                            goalRight2.setVisibility(View.GONE);
                            moreRightTxt.setVisibility(View.VISIBLE);
                        } else {
                            goalRight2.setVisibility(View.GONE);

                        }
                        Picasso.with(mContext)
                                .load(service_base_url + mGoalAgeTimeLine.get(i).getmGoalAgeTimeLine().get(0).getImg_url())
                                .placeholder(R.drawable.icon_2)
                                .resize(getDeviceWidth() / 9, getDeviceWidth() / 9)
                                .into(goalRight1);

                    }
                    rLayout.addView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void expand(final View v) {

        v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? WindowManager.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
        isClicked = false;
    }

    public void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
        isClicked = true;
    }

    public void setViewId(View myView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {

            myView.setId(generateViewId());

        } else {

            myView.setId(View.generateViewId());

        }
    }

    public void callGoalTimeLineService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalTimeLineModel> call = webServiceObj.callGoalTimeService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GoalTimeLineModel>() {
            @Override
            public void onResponse(Call<GoalTimeLineModel> call, Response<GoalTimeLineModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success", "" + response.body());
                GoalTimeLineModel goalTimeLineModel = response.body();
                ArrayList<String> familyId = new ArrayList<String>();
                ArrayList<String> familyName = new ArrayList<String>();

                if (goalTimeLineModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if (null != goalTimeLineModel.getData().getGoal_tmln()) {
                        mGoalTimeLineModel = goalTimeLineModel.getData().getGoal_tmln();
                        int size = goalTimeLineModel.getData().getGoal_tmln().size();

                        for (int i = 0; i < size; i++) {
                            if (!arrayCompare(goalTimeLineModel.getData().getGoal_tmln().get(i).getBelongs_to_id(), familyId)) {
                                familyId.add(goalTimeLineModel.getData().getGoal_tmln().get(i).getBelongs_to_id());
                                familyName.add(goalTimeLineModel.getData().getGoal_tmln().get(i).getName());
                            }
                        }
                        setView(goalTimeLineModel.getData().getGoal_tmln());
                        if (!familyId.isEmpty()) {
                            AddCheckBoxView(familyId, familyName);
                        }
                    }
                } else {
                    errorTextview.setVisibility(View.VISIBLE);
                    errorTextview.setText(HomePageActivity.errorMessageInChart);
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<GoalTimeLineModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }

    public boolean arrayCompare(String compStr, ArrayList<String> mList) {
        for (String i : mList) {
            if (compStr.equalsIgnoreCase(i)) {
                return true;
            }

        }
        return false;
    }

    private void AddCheckBoxView(ArrayList<String> familId, final ArrayList<String> familyName) {
        try {
            checkboxLayout.removeAllViews();
            userNameCheckBox.clear();

            for (int i = 0; i < familId.size(); i++) {
                LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout parent_layout = new LinearLayout(mContext);
                parent_layout.setWeightSum(2);
                parent_layout.setOrientation(LinearLayout.HORIZONTAL);
                parent_param_layout.setMargins(10, 0, 0, 10);
                parent_layout.setLayoutParams(parent_param_layout);

                LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parms_left_layout.weight = 1F;
                LinearLayout left_layout = new LinearLayout(mContext);
                left_layout.setOrientation(LinearLayout.HORIZONTAL);
                left_layout.setGravity(Gravity.LEFT);
                left_layout.setLayoutParams(parms_left_layout);

                //Log.e("Possition To Add L", "" + i + familyName.get(i));
                userNameCheckBox.add(new CheckBox(mContext));
                userNameCheckBox.get(i).setId(i);
                UtileKit.setTextAppearance(mContext, android.R.style.TextAppearance_Small, userNameCheckBox.get(i));
                userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        int position = compoundButton.getId();
//                    if(!(position == userNameCheckBox.size() - 1))
                        {
                            checkBoxState.put(familyName.get(position), b);
                        }
//                    else if((position == userNameCheckBox.size() - 1)&b)
//                    {
////                        checkBoxState.clear();
//                    }
                        checkBoxOnClick(position, b);
                        //Log.e("Possition To Add L", "" + position);

                    }
                });
                userNameCheckBox.get(i).setText(familyName.get(i));
                userNameCheckBox.get(i).setTag(familId.get(i));
                userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.app_text_color_gray));
                left_layout.addView(userNameCheckBox.get(i));
                i++;
                if (familyName.size() == i) {
                    parent_layout.addView(left_layout);
                    checkboxLayout.addView(parent_layout);
                    break;
                }

                LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parms_right_layout.weight = 1F;
                LinearLayout right_layout = new LinearLayout(mContext);
                right_layout.setOrientation(LinearLayout.HORIZONTAL);
                right_layout.setGravity(Gravity.LEFT);
                parent_param_layout.setMargins(10, 0, 0, 10);
                right_layout.setLayoutParams(parms_right_layout);

                //Log.e("Possition To Add R", "" + i + familyName.get(i));
                userNameCheckBox.add(new CheckBox(mContext));
                userNameCheckBox.get(i).setId(i);

                UtileKit.setTextAppearance(mContext, android.R.style.TextAppearance_Small, userNameCheckBox.get(i));
                userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        int position = compoundButton.getId();
                        checkBoxState.put(familyName.get(position), b);
                        checkBoxOnClick(position, b);
                        //Log.e("Possition To Add R", "" + position);
                    }
                });
                userNameCheckBox.get(i).setText(familyName.get(i));
                userNameCheckBox.get(i).setTag(familId.get(i));
                userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.app_text_color_gray));
                right_layout.addView(userNameCheckBox.get(i));

                parent_layout.addView(right_layout);
                parent_layout.addView(left_layout);
                checkboxLayout.addView(parent_layout);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (!userNameCheckBox.isEmpty())
//            userNameCheckBox.get(userNameCheckBox.size() - 1).setChecked(true);
        if (checkBoxState.isEmpty()) {
            if (!userNameCheckBox.isEmpty())
                userNameCheckBox.get(userNameCheckBox.size() - 1).setChecked(true);
        } else if (!userNameCheckBox.isEmpty()) {
            for (String key : checkBoxState.keySet()) {

                for (CheckBox mCheckbox : userNameCheckBox) {
                    if (mCheckbox.getText().toString().equalsIgnoreCase(key)) {
                        mCheckbox.setChecked(checkBoxState.get(key));
                    }
                }
            }

        }
    }

    private void checkBoxOnClick(int position, boolean b) {

        ArrayList<Goal_tmln> goal_tmln = new ArrayList<>();
        if (mGoalTimeLineModel != null)
            for (int person = 0; person < userNameCheckBox.size(); person++) {
                if (userNameCheckBox.get(person).isChecked()) {
                    for (int i = 0; i < mGoalTimeLineModel.size(); i++) {
                        if (userNameCheckBox.get(person).getTag().equals(mGoalTimeLineModel.get(i).getBelongs_to_id())) {
                            goal_tmln.add(mGoalTimeLineModel.get(i));
                        }
                    }
                }
            }
        if (!goal_tmln.isEmpty()) {
            setView(goal_tmln);
        } else {
            rLayout.removeAllViews();
        }
//            if (mGoalTimeLineModel != null) {
//                for (int i = 0; i < userNameCheckBox.size(); i++) {
//                    if (userNameCheckBox.get(position).isChecked()) {
//                        goal_tmln.add(mGoalTimeLineModel.get(i));
//                    }
//                }
//
//            }

//        for (int i = 0; i < userNameCheckBox.size(); i++) {
//            if (i != position)
//                userNameCheckBox.get(i).setChecked(false);
//
//        }

         /*  addSingleUserChart(position);*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relative_left_arrow: {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home: {
                startHomeActivity();
            }
            break;
            case R.id.relative_right_arrow: {
                addFragmenttoStack(new CashflowChartFragment());
            }
            break;
            case R.id.timeline_fab_id:
                addFragmenttoStack(new GoalsListFragment());
                break;

        }
    }

    @Override
    public void onClick(View view, int position) {
        Log.i("Possition To Add L", "setOnItemSelectedListener onClick" + position);
//        addFragmenttoStack(SingleGoalDonutview.newInstance(String.valueOf(position), name, "GoalView", mvalue,expectedincrement));
    }

    @Override
    public void onClick(String  Id, String name, String mvalue,String expectedincrement) {
//        Log.i("Possition To Add L", "setOnItemSelectedListener onClick" + position);
        addFragmenttoStack(SingleGoalDonutview.newInstance(Id, name, "GoalView", mvalue,expectedincrement));
    }
}


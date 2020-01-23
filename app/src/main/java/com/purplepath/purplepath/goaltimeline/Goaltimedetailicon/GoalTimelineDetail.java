/*

package com.purplepath.purplepath.goaltimeline.Goaltimedetailicon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goaltimeline.GoalTimeLineFragment;
import com.purplepath.purplepath.goaltimeline.GoalTimelineFragmentMainPage;
import com.purplepath.purplepath.goaltimeline.model.GoalAgeTimeLineModel;
import com.purplepath.purplepath.goaltimeline.model.GoalTimeLineModel;
import com.purplepath.purplepath.goaltimeline.model.Goal_tmln;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoalTimelineDetail extends BaseFragment implements View.OnClickListener {
    private GoaTimelineDetailadapter mGoaTimelineDetailadapter;
   // private ArrayList<Goal_tmln> mGoalTimeLineModel=new ArrayList<>();
   //ArrayList<GoalAgeTimeLineModel> mGoalAgeTimeLine = new ArrayList<>();

    private ArrayList<GoalAgeTimeLineModel> mGoalAgeTimegroup ;
    private ArrayList<Goal_tmln> mListDataChild;


    private static final String ARG_COLUMN_COUNT = "column-count";
    private ImageView mtimeline_chart,mtimeline_detail_icon;
    private Context mContext;
    private int mColumnCount = 1;
    RecyclerView  recyclerView;
    TextView errorTextview;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;
    private  OnActivityBackPressedListener mCallBackListener;
    Fragment fragment;
    private ExpandableListView mexpandableListView;
    public GoalTimelineDetail() {
    }

    public static GoalTimelineFragmentMainPage newInstance(int columnCount) {
        GoalTimelineFragmentMainPage fragment = new GoalTimelineFragmentMainPage();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {

        }
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        callGoalTimeLineService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = container.getContext();
        mCallBackListener.setActionBarTitle("Goals");
        View view = inflater.inflate(R.layout.fragment_item_list_goaltimeline_mainpage, container, false);
        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mtimeline_chart=(ImageView) view.findViewById(R.id.timeline_chart);
        mtimeline_detail_icon=(ImageView) view.findViewById(R.id.timeline_detail_icon);



        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mtimeline_chart.setOnClickListener(this);
        mtimeline_detail_icon.setOnClickListener(this);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // recyclerView = (RecyclerView) view.findViewById(R.id.list);
        mexpandableListView = (ExpandableListView) view.findViewById(R.id.expenses_redesign_essential_view);



    }
    public void callGoalTimeLineService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(getActivity(), false);
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
                        mListDataChild = goalTimeLineModel.getData().getGoal_tmln();
                        int size = goalTimeLineModel.getData().getGoal_tmln().size();

                        for (int i = 0; i < size; i++) {
                            if (!arrayCompare(goalTimeLineModel.getData().getGoal_tmln().get(i).getBelongs_to_id(), familyId)) {
                                familyId.add(goalTimeLineModel.getData().getGoal_tmln().get(i).getBelongs_to_id());
                                familyName.add(goalTimeLineModel.getData().getGoal_tmln().get(i).getName());
                            }
                        }
                        mGoaTimelineDetailadapter = new GoaTimelineDetailadapter(getActivity(), mGoalAgeTimegroup,mListDataChild, goalTimeLineModel);
                        mexpandableListView.setAdapter(mGoaTimelineDetailadapter);
                        mGoaTimelineDetailadapter.notifyDataSetChanged();

                    } else {
                        errorTextview.setText(HomePageActivity.errorMessageInChart);
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                    }
                }
                UtileKit.dismisssSpinnerDialog();
            }


            public boolean arrayCompare(String compStr, ArrayList<String> mList) {
                for (String i : mList) {
                    if (compStr.equalsIgnoreCase(i)) {
                        return true;
                    }

                }
                return false;
            }

            @Override
            public void onFailure(Call<GoalTimeLineModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( getActivity(),t);
            }
        });
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relative_left_arrow:
            {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
//                getActivity().finish();
            }
            break;
            case R.id.timeline_chart:
            {
                fragment=new GoalTimeLineFragment();
                addFragmentToActivity(fragment);
            }
            break;
            //Muruga pending works//

*/
/*  case R.id.timeline_detail_icon:
            {
                fragment=new GoalTimeLineFragment();
                addFragmentToActivity(fragment);
            }
            break;*//*


        }
    }
    private void addFragmentToActivity(Fragment fragment) {
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
}

*/

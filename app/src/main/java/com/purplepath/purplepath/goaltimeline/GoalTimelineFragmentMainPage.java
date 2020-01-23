package com.purplepath.purplepath.goaltimeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goaltimeline.model.GoalTimeLineModel;
import com.purplepath.purplepath.goaltimeline.model.Goal_tmln;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoalTimelineFragmentMainPage extends BaseFragment implements View.OnClickListener {
private GoalTimeLineFragmentMainpageAdapter myItemRecyclerViewAdapter;
    private ArrayList<Goal_tmln> mGoalTimeLineModel=new ArrayList<>();
    private static final String ARG_COLUMN_COUNT = "column-count";
    //private SVGImageView mtimeline_chart;
    private Context mContext;
    private int mColumnCount = 1;
    RecyclerView  recyclerView;
    TextView errorTextview;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;
    private  OnActivityBackPressedListener mCallBackListener;
    Fragment fragment;
    AlertDialog alertDialog;
    LayoutInflater inflater;
    View dialogView;
    public GoalTimelineFragmentMainPage() {
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mContext = container.getContext();
        mCallBackListener.setActionBarTitle("Goal Plan");
        View view = inflater.inflate(R.layout.fragment_item_list_goaltimeline_mainpage, container, false);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        errorTextview = view.findViewById(R.id.errorTextview);


        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        callGoalTimeLineService();
        return view;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_detail);
        MenuItem items=menu.findItem(R.id.menu_chart);
        // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_detail:
                try{
//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    GoalTableViewFragment fragment =
                            new GoalTableViewFragment();
//                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
//                    fragmentTransaction.commitAllowingStateLoss();
                }catch (Exception e){
                    e.printStackTrace();
                }
//                UtileKit.intitializeAlertDialog(getString(R.string.goalconsr),mContext);
                return true;

            case R.id.menu_chart:

                try{
//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    SingleGoalDonutview fragment = new SingleGoalDonutview();
////                    GoalTimeLineFragment fragment = new GoalTimeLineFragment();
//                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
//                    fragmentTransaction.commitAllowingStateLoss();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.list);
//listview=(ListView) view.findViewById(R.id.list);

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

                if (goalTimeLineModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if (null != goalTimeLineModel.getData().getGoal_tmln()) {
                        mGoalTimeLineModel=goalTimeLineModel.getData().getGoal_tmln();
                      /*  int size = goalTimeLineModel.getData().getGoal_tmln().size();*/

                        myItemRecyclerViewAdapter = new GoalTimeLineFragmentMainpageAdapter(getActivity(),mGoalTimeLineModel,goalTimeLineModel);
                        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(myItemRecyclerViewAdapter);
                        myItemRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }else{
                    recyclerView.setVisibility(View.GONE);
                    errorTextview.setVisibility(View.VISIBLE);
                    errorTextview.setText(HomePageActivity.errorMessageInChart);
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }
                UtileKit.dismisssSpinnerDialog();
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
            }
            break;
           /* case R.id.timeline_chart:
            {
                fragment=new GoalTimeLineFragment();
                addFragmentToActivity(fragment);
            }
            break;*/

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

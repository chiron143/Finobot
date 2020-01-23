package com.purplepath.purplepath.goaltimeline;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.evrencoskun.tableview.TableView;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goalanalysis.singlegoal.SingleGoalDonutview;
import com.purplepath.purplepath.goaltimeline.getGoalPlanModel.GoalPlanModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.recommendation.adapter.RecomendationTableAdapter;
import com.purplepath.purplepath.recommendation.getgoalmodel.Goalmodel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxproduct.model.Cell;
import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
import com.purplepath.purplepath.taxproduct.model.RowHeader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.R.id.tableParentView;

/**
 * Created by Suresh on 27/07/17.
 */

public class GoalTableViewFragment extends BaseFragment implements
        View.OnClickListener {

    OnActivityBackPressedListener mCallBackListener;


    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private GoalPlanModel mGoalPlanModel;

    private WebView webViewgoal;

    private String goalId,goalName,yearof_goals;

    private String expected_increment;

    private String rs="&#x20B9";

    RelativeLayout tableParentView;

    private RecomendationTableAdapter mTableViewAdapter;

    private TableView mTableView;

    private ArrayList<String> mColoumNameList=new ArrayList<String>( Arrays.asList("Age", "Time", "Year","Beg Value",
            "Savings","Earnings","Cost","Grant","Need","Deficit","End Value"));


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
            if(getArguments().containsKey("mGoalId"))
                goalId=getArguments().getString("mGoalId");

            if(getArguments().containsKey("mGoalName"))
                goalName = getArguments().getString("mGoalName");
            if(getArguments().containsKey("mGoalyears"))
                yearof_goals = getArguments().getString("mGoalyears");

            if(getArguments().containsKey("mExpectedincrement"))
                expected_increment=getArguments().getString("mExpectedincrement");

        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mContext = container.getContext();
        mCallBackListener.setActionBarTitle("Goal Plan");
        View view = inflater.inflate(R.layout.goal_table_view, container, false);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        webViewgoal = view.findViewById(R.id.webViewgoal);

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        mTableView = createTableView();
        tableParentView= view.findViewById(R.id.tableParentView);
        tableParentView.addView(mTableView);

        if(goalId!= null){
            callGetGoalService(goalId);
        }

        return view;

    }
    private TableView createTableView() {
        TableView tableView = new TableView(getContext());
        // Set adapter
        mTableViewAdapter = new RecomendationTableAdapter(mContext);
        tableView.setAdapter(mTableViewAdapter);
        // Set layout params
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tableView.setLayoutParams(tlp);
        return tableView;
    }

    public void callGetGoalService(String goalId) {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(getActivity(), false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalPlanModel> call = webServiceObj.callGetGoalService(UtileKit.getPersistedPurplePathPref("user_id"),goalId);
        call.enqueue(new Callback<GoalPlanModel>() {
            @Override
            public void onResponse(Call<GoalPlanModel> call, Response<GoalPlanModel> response) {
                UtileKit.dismisssSpinnerDialog();
                Log.i("success", "callGetGoalService in table view" + response.body());
                mGoalPlanModel = response.body();
                insertValueinTableView(mGoalPlanModel);

            }
            @Override
            public void onFailure(Call<GoalPlanModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( getActivity(),t);
            }
        });
    }

    private void insertValueinTableView(GoalPlanModel mGoalPlanModel) {

        if (mGoalPlanModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
            if (mGoalPlanModel.getData().getGoal_plan().size() > 0) {
                showWebView(mGoalPlanModel);
            }
        }

    }

    private void showWebView(GoalPlanModel mGoalPlanModel) {

      /*  String htmlContent = "<!DOCTYPE html>\n" +

                "<html>\n" +
                "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" >" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<table border=\"1\" width=\"device-width\" height = \"150px\" cellpadding=\"10px\" cellspacing=\"0\" style=\"border-collapse:collapse;\" >\n" +
                "  <tr id=\"header-row\" align = \"center\">\n" +
                "    <th width = \"150px\"> Age</th>\n" +
                "    <th width = \"150px\"> Time </th> \n" +
                "    <th width = \"150px\">Year</th>\n" +
                "    <th width = \"150px\"> Beg Value </th>\n" +
                "    <th width = \"150px\"> Savings </th>\n" +
                "    <th width = \"150px\"> Earnings </th>\n" +
                "    <th width = \"150px\"> Cost </th> \n" +
                "    <th width = \"150px\"> Grant </th> \n" +
                "    <th width = \"150px\"> Need </th>\n" +
                "    <th width = \"150px\"> Deficit </th>\n" +
                "    <th width = \"150px\"> End Value </th>\n" +
                "  </tr>\n" +
                addTableRows(mGoalPlanModel)
                +
                "</table>\n" +
                "</body>\n" +
                "</html>\n";

        webViewgoal.setBackgroundColor(Color.parseColor("#e5e5e5"));
        webViewgoal.loadData(htmlContent, "text/html", "UTF-8");*/


        addTableRows(mGoalPlanModel);



    }

    private void addTableRows(GoalPlanModel mGoalPlanModel) {
        List<RowHeader> rowHeaders = getRowHeaderList(mGoalPlanModel);
        List<List<Cell>> cellList = getCellListForSortingTest(mGoalPlanModel);
        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameList);
        mTableViewAdapter.setAllItems(columnHeaders, rowHeaders, cellList);
    }

    private List<List<Cell>> getCellListForSortingTest(GoalPlanModel mGoalPlanModel) {

        List<List<Cell>> list = new ArrayList<>();

        int length = mGoalPlanModel.getData().getGoal_plan().size();
        if (null!=mGoalPlanModel.getData().getGoal_plan()) {

            for (int i = 0; i < length; i++) {
                List<Cell> cellList = new ArrayList<>();

                String goalage="",goaltime="",goal_year="",bag_value="",goal_saving="",
                        goal_erning="",goal_cost="",goal_grand="",goal_need="",goal_deficit="",goal_end_value="";

                if(mGoalPlanModel.getData().getGoal_plan().get(i).getAge()!= null){
                    goalage=mGoalPlanModel.getData().getGoal_plan().get(i).getAge();
                }else{
                    goalage= "0";
                }
                if(mGoalPlanModel.getData().getGoal_plan().get(i).getTime()!= null)
                {
                    goaltime=mGoalPlanModel.getData().getGoal_plan().get(i).getTime();
                }else{
                    goaltime= "0";
                }
                if(mGoalPlanModel.getData().getGoal_plan().get(i).getYear()!= null){
                    goal_year=mGoalPlanModel.getData().getGoal_plan().get(i).getYear();
                }else{
                    goal_year= "0";
                }
                if(mGoalPlanModel.getData().getGoal_plan().get(i).getBeg_val()!= null){
                    bag_value=(UtileKit.currToCharConversion(mGoalPlanModel.getData().getGoal_plan().get(i).getBeg_val()));
                }else{
                    bag_value= "0";
                }
                if(mGoalPlanModel.getData().getGoal_plan().get(i).getSavings()!= null){
                    goal_saving=(UtileKit.currToCharConversion(mGoalPlanModel.getData().getGoal_plan().get(i).getSavings()));
                }else{
                    goal_saving= "0";
                }
                if(mGoalPlanModel.getData().getGoal_plan().get(i).getEarnings()!= null){
                    goal_erning=(UtileKit.currToCharConversion(mGoalPlanModel.getData().getGoal_plan().get(i).getEarnings()));
                }else{
                    goal_erning= "0";
                }
                if(mGoalPlanModel.getData().getGoal_plan().get(i).getCost()!= null){
                    goal_cost=(UtileKit.currToCharConversion(mGoalPlanModel.getData().getGoal_plan().get(i).getCost()));
                }else{
                    goal_cost= "0";
                }
                if(mGoalPlanModel.getData().getGoal_plan().get(i).getGrant()!= null){
                    goal_grand=(UtileKit.currToCharConversion(mGoalPlanModel.getData().getGoal_plan().get(i).getGrant()));
                }else{
                    goal_grand= "0";
                }
                if(mGoalPlanModel.getData().getGoal_plan().get(i).getNeed()!= null){
                    goal_need=(UtileKit.currToCharConversion(mGoalPlanModel.getData().getGoal_plan().get(i).getNeed()));
                }else{
                    goal_need= "0";
                }

                if(mGoalPlanModel.getData().getGoal_plan().get(i).getDeficit()!= null){
                    goal_deficit=(UtileKit.currToCharConversion( mGoalPlanModel.getData().getGoal_plan().get(i).getDeficit()));
                }else{
                    goal_deficit= "0";
                }

                if(mGoalPlanModel.getData().getGoal_plan().get(i).getEnd_val()!= null){
                    goal_end_value=(UtileKit.currToCharConversion(mGoalPlanModel.getData().getGoal_plan().get(i).getEnd_val()));
                }else{
                    goal_end_value= "0";
                }




                cellList.add(new Cell(i+"00", goalage));
                cellList.add(new Cell(i+"01", goaltime));
                cellList.add(new Cell(i+"02", goal_year));
                cellList.add(new Cell(i+"03", bag_value));
                cellList.add(new Cell(i+"03", goal_saving));
                cellList.add(new Cell(i+"03", goal_erning));
                cellList.add(new Cell(i+"03", goal_cost));
                cellList.add(new Cell(i+"03", goal_grand));
                cellList.add(new Cell(i+"03", goal_need));
                cellList.add(new Cell(i+"03", goal_deficit));
                cellList.add(new Cell(i+"03", goal_end_value));



                list.add(cellList);
            }
        }
        return list;
    }

    private List<ColumnHeader> getColumnHeaderListCash(ArrayList<String> mColoumNameList) {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < mColoumNameList.size(); i++) {
            ColumnHeader header = new ColumnHeader(""+i, mColoumNameList.get(i));
            list.add(header);
        }
        return list;
    }

    private List<RowHeader> getRowHeaderList(GoalPlanModel mGoalPlanModel) {
        List<RowHeader> list = new ArrayList<>();

        int n=mGoalPlanModel.getData().getGoal_plan().size();
        for (int i = 0; i <n ; i++) {
            RowHeader header = new RowHeader("row " + i,""+(i+1));
            list.add(header);
        }
        return list;
    }

    /*private String addTableRows(GoalPlanModel mGoalPlanModel) {

        String row ="";
        int rowSize = mGoalPlanModel.getData().getGoal_plan().size();
        for (int i = 0; i < rowSize; i++) {
            row =row  + "  <tr align = \"center\">\n" +
                    "    <td>" +UtileKit.validateObjectValuesreturnZero(mGoalPlanModel.getData().getGoal_plan().get(i).getAge()) +"</td>\n" +
                    "    <td>" +(UtileKit.validateObjectValuesreturnZero(mGoalPlanModel.getData().getGoal_plan().get(i).getTime())) +"</td>\n" +
                    "    <td>" +(UtileKit.validateObjectValuesreturnZero(mGoalPlanModel.getData().getGoal_plan().get(i).getYear()))+"</td>\n" +
                    "    <td>"+rs+" " +UtileKit.longvalueabsolute(Float.parseFloat(UtileKit.validateObjectValuesreturnZero(mGoalPlanModel.getData().getGoal_plan().get(i).getBeg_val())))+"</td>\n" +
                    "    <td>"+rs+" " +UtileKit.longvalueabsolute(Float.parseFloat(UtileKit.validateObjectValuesreturnZero(mGoalPlanModel.getData().getGoal_plan().get(i).getSavings()))) +"</td>\n" +
                    "    <td>"+rs+" " +UtileKit.longvalueabsolute(Float.parseFloat(UtileKit.validateObjectValuesreturnZero(mGoalPlanModel.getData().getGoal_plan().get(i).getEarnings()))) +"</td>\n" +
                    "    <td>"+rs+" " +UtileKit.longvalueabsolute(Float.parseFloat(UtileKit.validateObjectValuesreturnZero(mGoalPlanModel.getData().getGoal_plan().get(i).getCost()))) +"</td>\n" +
                    "    <td>"+rs+" " +UtileKit.longvalueabsolute(Float.parseFloat(UtileKit.validateObjectValuesreturnZero(mGoalPlanModel.getData().getGoal_plan().get(i).getGrant()))) +"</td>\n" +
                    "    <td>"+rs+" " +UtileKit.longvalueabsolute(Float.parseFloat(UtileKit.validateObjectValuesreturnZero(mGoalPlanModel.getData().getGoal_plan().get(i).getNeed()))) +"</td>\n" +
                    "    <td>"+rs+" " +UtileKit.longvalueabsolute(Float.parseFloat(UtileKit.validateObjectValuesreturnZero(mGoalPlanModel.getData().getGoal_plan().get(i).getDeficit()))) +"</td>\n" +
                    "    <td>"+rs+" "+UtileKit.longvalueabsolute(Float.parseFloat(UtileKit.validateObjectValuesreturnZero(mGoalPlanModel.getData().getGoal_plan().get(i).getEnd_val()))) +"</td>\n" +
                    "  </tr>\n" ;
        }


        return row;
    }*/

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

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_chart);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chart:
                GoalTimeLineCashFlow mGoalTimeLineCashFlow = GoalTimeLineCashFlow.newInstance(mGoalPlanModel,
                        goalId,goalName,yearof_goals,expected_increment);
                showFragment(mGoalTimeLineCashFlow);
                break;
            case R.id.menu_summary:
                SingleGoalDonutview mSingleGoalDonutview = SingleGoalDonutview.newInstance( goalId,goalName,
                        "GoalView", yearof_goals,expected_increment);
                showFragment(mSingleGoalDonutview);
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

    public static GoalTableViewFragment newInstance(String goalId, String goldName, String years,String mExpectedincrement) {
        Bundle args = new Bundle();
        GoalTableViewFragment fragment = new GoalTableViewFragment();
        args.putString("mGoalId",goalId);
        args.putString("mGoalName",goldName);
        args.putString("mGoalyears",years);
        args.putString("mExpectedincrement",mExpectedincrement);
        fragment.setArguments(args);
        return fragment;
    }
}

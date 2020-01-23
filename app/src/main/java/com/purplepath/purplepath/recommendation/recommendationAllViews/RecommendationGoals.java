package com.purplepath.purplepath.recommendation.recommendationAllViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.TableView;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.recommendation.adapter.RecomendationSavingTableAdapter;
import com.purplepath.purplepath.recommendation.getgoalmodel.Goalmodel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxproduct.model.Cell;
import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
import com.purplepath.purplepath.taxproduct.model.RowHeader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.R.id.empty_values;


/**
 * Created by pravinr on 3/7/18.
 */

public class RecommendationGoals extends BaseFragment implements View.OnClickListener {

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    private Goalmodel goalmodel;

    private TextView surplus_needed, goal_permonths, goal_pvalues;

    private RecomendationSavingTableAdapter recomendationSavingTableAdapter;

    RelativeLayout tableParentView;

    private TableView mTableView;

    private BigDecimal per1year;
    private BigDecimal per1month;
    private BigDecimal pvals;

    String per1year2 = "", per1month2 = "", pvals2 = "";

    private ArrayList<String> mColoumNameList = new ArrayList<String>(Arrays.asList("Goal", "Present cost", "Current fund", "Target future cost", "Time to active", "Resource  used", "Investment needed"));

    TextView empty_values;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_popup_goals, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallBackListener.setActionBarTitle("Goals");


        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);


        mTableView = createTableViewSavings();
        tableParentView = view.findViewById(R.id.tableParentView);
        tableParentView.addView(mTableView);

        surplus_needed = view.findViewById(R.id.surplus_needed);

        goal_permonths = view.findViewById(R.id.goal_permonths);

        goal_pvalues = view.findViewById(R.id.goal_pvalues);
        empty_values = view.findViewById(R.id.empty_values);

        callAllInsuranceService();

    }

    private TableView createTableViewSavings() {
        TableView tableView = new TableView(getContext());
        // Set adapter
        recomendationSavingTableAdapter = new RecomendationSavingTableAdapter(mContext);
        tableView.setAdapter(recomendationSavingTableAdapter);
        // Set layout params
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tableView.setLayoutParams(tlp);
        return tableView;
    }

    private void callAllInsuranceService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Goalmodel> call = webServiceObj.getGoalRecommendation(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Goalmodel>() {
            @Override
            public void onResponse(Call<Goalmodel> call, Response<Goalmodel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    goalmodel = response.body();

                    if (goalmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if (goalmodel.getData().getUser_goals() != null) {
                            applyGoals(goalmodel);
                        }
                    } else {
                        empty_values.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Goalmodel> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void applyGoals(Goalmodel goalmodel) {
        if (goalmodel.getData().getUser_goals() != null) {
            int length = goalmodel.getData().getUser_goals().size();
            per1year = new BigDecimal(length);
            per1month = new BigDecimal(length);
            pvals = new BigDecimal(length);
            try {
                for (int k = 0; k < length; k++) {
                    per1year = per1year.add(new BigDecimal(UtileKit.rounddecimalNumber(goalmodel.getData().
                            getUser_goals().get(k).getPmt_1_year())));
                    per1month = per1month.add(new BigDecimal(UtileKit.rounddecimalNumber(goalmodel.getData().
                            getUser_goals().get(k).getPmt_1_mon())));
                    pvals = pvals.add(new BigDecimal(UtileKit.rounddecimalNumber(goalmodel.getData().
                            getUser_goals().get(k).getPval())));

                    per1year2 = (UtileKit.longvalueabsolute(Float.parseFloat(String.valueOf(per1year))));
                    per1month2 = (UtileKit.longvalueabsolute(Float.parseFloat(String.valueOf(per1month))));
                    pvals2 = (UtileKit.longvalueabsolute(Float.parseFloat(String.valueOf(pvals))));


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            showWebViewGoal(goalmodel);

        }
    }

    private void showWebViewGoal(Goalmodel goalmodel) {
        surplus_needed.setText(" Your investment surplus needed" + UtileKit.fromHtml("₹ " + "<b>" + "<u>" + per1year2 + "</u>" +
                "</b>") + " " + "p.a.");

        goal_permonths.setText(UtileKit.fromHtml("₹ " + "<b>" + "<u>" + per1month2 + "</u>" + "</b>" + " " + "p.a."));

        goal_pvalues.setText(UtileKit.fromHtml("₹ " + "<b>" + "<u>" + pvals2 + "</u>" + "</b>" + " " + "lumpsum"));
        loadGoalData(goalmodel);
    }

    private void loadGoalData(Goalmodel goalmodel) {


        List<RowHeader> rowHeaders = getRowHeaderList(goalmodel);
        List<List<Cell>> cellList = getCellListForSortingTest(goalmodel);
        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameList);

        recomendationSavingTableAdapter.setAllItems(columnHeaders, rowHeaders, cellList);
    }

    private List<ColumnHeader> getColumnHeaderListCash(ArrayList<String> mColoumNameList) {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < mColoumNameList.size(); i++) {
            ColumnHeader header = new ColumnHeader("" + i, mColoumNameList.get(i));
            list.add(header);
        }
        return list;
    }

    private List<RowHeader> getRowHeaderList(Goalmodel goalmodel) {
        List<RowHeader> list = new ArrayList<>();

        int n = goalmodel.getData().getUser_goals().size();
        for (int i = 0; i < n; i++) {
            RowHeader header = new RowHeader("row " + i, "" + (i + 1));
            list.add(header);
        }
        return list;
    }

    private List<List<Cell>> getCellListForSortingTest(Goalmodel goalmodel) {
        List<List<Cell>> list = new ArrayList<>();
        String rowsadd = "";
        int length = goalmodel.getData().getUser_goals().size();
        if (null != goalmodel.getData().getUser_goals()) {
            for (int i = 0; i < length; i++) {
                List<Cell> cellList = new ArrayList<>();
                Cell cell;
                String goalname = "", costofgoal = "", fundvalue = "",
                        fv_1_year = "", goal_year = "", pmt_1year = "",
                        pmt_1_mon = "", pval = "", equity = "", debt = "", liquid = "";

                if (goalmodel.getData().getUser_goals().get(i).getGoal_name() != null) {
                    goalname = goalmodel.getData().getUser_goals().get(i).getGoal_name();
                } else {
                    goalname = "0";
                }
                if (goalmodel.getData().getUser_goals().get(i).getCost_of_goal() != null && !
                        goalmodel.getData().getUser_goals().get(i).getCost_of_goal().isEmpty()) {
                    costofgoal = (UtileKit.currToCharConversion(goalmodel.getData().
                            getUser_goals().get(i).getCost_of_goal()));
                } else {
                    costofgoal = "0";
                }
                if (goalmodel.getData().getUser_goals().get(i).getFund_val() != null) {
                    fundvalue = (UtileKit.currToCharConversion(goalmodel.getData().
                            getUser_goals().get(i).getFund_val()));
                } else {
                    fundvalue = "0";
                }
                if (goalmodel.getData().getUser_goals().get(i).getFv_1_year() != null) {
                    fv_1_year = (UtileKit.currToCharConversion(goalmodel.getData().
                            getUser_goals().get(i).getFv_1_year()));
                } else {
                    fv_1_year = "0";
                }
                if (goalmodel.getData().getUser_goals().get(i).getGoal_years() != null) {
                    goal_year = goalmodel.getData().getUser_goals().get(i).getGoal_years();
                } else {
                    goal_year = "0";
                }
                if (goalmodel.getData().getUser_goals().get(i).getPmt_1_year() != null) {
                    pmt_1year = (UtileKit.currToCharConversion(goalmodel.getData().
                            getUser_goals().get(i).getPmt_1_year()));
                } else {
                    pmt_1year = "0";
                }
                if (goalmodel.getData().getUser_goals().get(i).getPmt_1_mon() != null) {
                    pmt_1_mon = (UtileKit.currToCharConversion(goalmodel.getData().
                            getUser_goals().get(i).getPmt_1_mon()));
                } else {
                    pmt_1_mon = "0";
                }
                if (goalmodel.getData().getUser_goals().get(i).getPval() != null) {
                    pval = (UtileKit.currToCharConversion(goalmodel.getData().
                            getUser_goals().get(i).getPval()));
                } else {
                    pval = "0";
                }

                if (goalmodel.getData().getUser_goals().get(i).getResources_used().getEquity() != null) {
                    equity = String.valueOf((Float.parseFloat(goalmodel.getData().getUser_goals().get(i).getResources_used().
                            getEquity()) * 100));
                } else {
                    equity = "0";
                }
                if (goalmodel.getData().getUser_goals().get(i).getResources_used().getDebt() != null) {
                    debt = String.valueOf((Float.parseFloat(goalmodel.getData().getUser_goals().get(i).getResources_used().
                            getDebt()) * 100));
                } else {
                    debt = "0";
                }
                if (goalmodel.getData().getUser_goals().get(i).getResources_used().getLiquid() != null) {
                    liquid = String.valueOf((Float.parseFloat(goalmodel.getData().getUser_goals().get(i).getResources_used().
                            getLiquid()) * 100));
                } else {
                    liquid = "0";
                }
                cellList.add(new Cell(i + "00", goalname));
                cellList.add(new Cell(i + "01", costofgoal));
                cellList.add(new Cell(i + "02", fundvalue));
                cellList.add(new Cell(i + "03", fv_1_year));
                cellList.add(new Cell(i + "03", goal_year));
                cellList.add(new Cell(i + "04", "Equity - " + equity + " % " + "\n" + "Debt - " + debt + " % " + "\n" + "Liquid - " + liquid + " % "));
                cellList.add(new Cell(i + "05", "₹ " + pmt_1year + " p.a." + "\n" + "₹ " + pmt_1_mon + " p.m." + "\n" + "₹ " + pval + " lumpsum"));

                list.add(cellList);
            }
        }
        return list;
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

}

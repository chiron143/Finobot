package com.purplepath.purplepath.investmentPlan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.purplepath.purplepath.investmentPlan.Adapter.InverstmentPlanningnew;
import com.purplepath.purplepath.investmentPlan.models.IPS_Bank;
import com.purplepath.purplepath.investmentPlan.models.IPS_Mutual_found;
import com.purplepath.purplepath.investmentPlan.models.IPS_Postoffice;
import com.purplepath.purplepath.investmentPlan.models.IPS_Results;
import com.purplepath.purplepath.investmentPlan.models.InvestmentPlan;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.recommendation.getgoalmodel.Goalmodel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxproduct.model.Cell;
import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
import com.purplepath.purplepath.taxproduct.model.RowHeader;
import com.purplepath.purplepath.taxprompt.TaxAnaylsisBarChart;
import com.purplepath.purplepath.taxprompt.TaxPromptSummary;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by rajasekar b on 27/02/2019.
 */
public class InverstmentPalanChartview extends BaseFragment implements View.OnClickListener {

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    private Goalmodel goalmodel;

    private InvestmentPlan investmentPlan;

    private TextView surplus_needed, goal_permonths, goal_pvalues;

    private InverstmentPlanningnew recomendationSavingTableAdapter;

    RelativeLayout tableParentView;

    private TableView mTableView;

    private BigDecimal per1year;

    private BigDecimal per1month;

    private BigDecimal pvals;

    String per1year2 = "", per1month2 = "", pvals2 = "";

    private ArrayList<String> mColoumNameList = new ArrayList<String>
            (Arrays.asList("Assets Class", "Goal Name", "Product level 1", "Product level 2", "Cost of Goal", "Inflation Rate", "Return Rate", "Time to Attain", "Duration", "Lumpsum", "Monthly", "Annual"));

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
        return inflater.inflate(R.layout.fragment_inverstment_planing, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallBackListener.setActionBarTitle("Investment Planning");


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

        //callAllInsuranceService();
        callInvestmentService();

    }

    private TableView createTableViewSavings() {
        TableView tableView = new TableView(getContext());
        // Set adapter
        recomendationSavingTableAdapter = new InverstmentPlanningnew(mContext);
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

    private void callInvestmentService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        //    Call<InvestmentPlan> call = webServiceObj.getInvesmentPlaning(UtileKit.getPersistedPurplePathPref("user_id"));
        Call<InvestmentPlan> call = webServiceObj.getInvesmentPlaning("247");
        call.enqueue(new Callback<InvestmentPlan>() {
            @Override
            public void onResponse(Call<InvestmentPlan> call, Response<InvestmentPlan> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    investmentPlan = response.body();
                    System.out.println("Rajasekar :" + investmentPlan.getStatus_code() + "");
                    System.out.println("Rajasekar :" + UtileKit.SUCCESSCODE + "");

                    if (investmentPlan.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        System.out.println("Rajasekar :" + investmentPlan.getData().getResults().get(0).getId() + "");
                        loadPlanData(investmentPlan);
                        loadData(investmentPlan);
                    } else {
                        System.out.println("Rajasekar else:" + investmentPlan.getData().getResults().get(0).getId() + "");
                        empty_values.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<InvestmentPlan> call, Throwable t) {
                t.printStackTrace();
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

    private void loadData(InvestmentPlan investmentPlan) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPlanData(InvestmentPlan investmentPlan) {

        // list_overall_question = overall_questions.get(key_Array);

        List<RowHeader> rowHeaders = getRowHeaderList1(investmentPlan);
        List<List<Cell>> cellList = getCellListForSorting(investmentPlan);
        List<ColumnHeader> columnHeaders = getColumnHeaderListCash1(mColoumNameList);


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

    private List<ColumnHeader> getColumnHeaderListCash1(ArrayList<String> mColoumNameList) {
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

    private List<RowHeader> getRowHeaderList1(InvestmentPlan investmentPlan) {
        List<RowHeader> list = new ArrayList<>();

        int n = investmentPlan.getData().getResults().size();
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

    private List<List<Cell>> getCellListForSorting(InvestmentPlan investmentPlan) {
        List<List<Cell>> list = new ArrayList<>();
        String rowsadd = "";
        int length = investmentPlan.getData().getAsset_classes().size();
        ArrayList<String> list_asset_class = investmentPlan.getData().getAsset_classes();
        //int length = 5;
        for (int j = 0; j < length; j++) {
            String array_key = list_asset_class.get(j);
            ArrayList<IPS_Results> arrayList_data = investmentPlan.getData().getGoals_with_asset_class().get(array_key);
            List<Cell> cellList = new ArrayList<>();
            Cell cell;
            StringBuilder assets = new StringBuilder();
            StringBuilder val = new StringBuilder();
            //"Assets Class", "Goals", "Cost of Goal","Inflation Rate","Return Rate", "Time to Attain","Duration","Lumpsum","Monthly","Annual"
            StringBuilder product = new StringBuilder();

            StringBuilder anual = new StringBuilder();

            StringBuilder inflation = new StringBuilder();

            StringBuilder returns = new StringBuilder();

            StringBuilder monthly = new StringBuilder();

            StringBuilder Lumpsum = new StringBuilder();

            StringBuilder Time_to_Attain = new StringBuilder();

            StringBuilder current_value = new StringBuilder();
            StringBuilder target_value = new StringBuilder();

            StringBuilder time_to_achive = new StringBuilder();
            StringBuilder resources = new StringBuilder();

            StringBuilder inversment = new StringBuilder();
            StringBuilder leval1 = new StringBuilder();
            StringBuilder leval2 = new StringBuilder();

            String start_date = "";
            String end_date = "";
            assets = new StringBuilder();

            int length_as = arrayList_data.size();
            int length_add = length_as / 2;
            for (int i = 0; i < length_as; i++) {

                StringBuilder leval1_child = new StringBuilder();
                StringBuilder leval2_child = new StringBuilder();
                IPS_Results ipsResults = arrayList_data.get(i);

                float equity = Float.parseFloat(ipsResults.getEquity_fv() + "");
                float depth = Float.parseFloat(ipsResults.getDebt_fv());
                float liquid = Float.parseFloat(ipsResults.getLiquid_fv());
                float common = Float.parseFloat(ipsResults.getComm_fv());
                float real_estate = Float.parseFloat(ipsResults.getReal_estate_fv());
                Log.d("I am ROBOT 3.0 >>>>:", length_add + ":" + length_as + "");

                if (length_add == i) {
                    assets.append("\n").append("\n").append(array_key);

                } else {
                    assets.append("\n");
                }


                if (ipsResults.getGoal_name() != null) {
                    product.append("\n" + "\n").append(ipsResults.getGoal_name());
                } else {
                    product = new StringBuilder("0");
                }
                if (ipsResults.getCost_of_goal() != null || !ipsResults.getCost_of_goal().equals("")) {
                    current_value.append("\n" + "\n").append(ipsResults.getCost_of_goal());
                } else {
                    current_value = new StringBuilder("0");
                }
                if (ipsResults.getEquity_fv() != null) {
                    inflation.append("\n" + "\n").append(ipsResults.getGoal_name());
                } else {
                    inflation = new StringBuilder("0");
                }
                if (ipsResults.getGoal_start_datetime() != null) {
                    String s_date = ipsResults.getGoal_start_datetime();
                    String e_date = ipsResults.getGoal_end_datetime();
                    start_date = (String) DateFormat.format("yyyy", get_date_format(s_date));
                    end_date = (String) DateFormat.format("yyyy", get_date_format(e_date));
                    time_to_achive.append("\n" + "\n").append(start_date).append("-").append(end_date);

                } else {
                    time_to_achive = new StringBuilder("0");
                }
                if (ipsResults.getGoal_name() != null) {
                    returns.append("\n" + "\n").append(ipsResults.getGoal_name());
                } else {
                    returns = new StringBuilder("0");
                }
                if (ipsResults.getGoal_years() != null) {
                    Time_to_Attain.append("\n" + "\n").append(ipsResults.getGoal_years());
                } else {
                    Time_to_Attain = new StringBuilder("0");
                }
                if (ipsResults.getEquity_pmt_lumpsum() != null) {
                    Lumpsum.append("\n" + "\n").append(ipsResults.getEquity_pmt_lumpsum());
                } else {
                    Lumpsum = new StringBuilder("0");
                }
                if (ipsResults.getComm_pmt_monthly() != null) {
                    monthly.append("\n" + "\n").append(ipsResults.getComm_pmt_monthly());
                } else {
                    monthly = new StringBuilder("0");
                }
                if (ipsResults.getDebt_annu_due_pv() != null) {
                    anual.append("\n" + "\n").append(ipsResults.getDebt_annu_due_pv());
                } else {
                    anual = new StringBuilder("0");
                }
                if (array_key.equals("equity_goals")) {
                    if (ipsResults.getEquity_recommended_products().getBank().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Bank");
                        int len = ipsResults.getEquity_recommended_products().getBank().size();
                        ArrayList<IPS_Bank> bank = ipsResults.getEquity_recommended_products().getBank();
                        for (int k = 0; k < len; k++) {
                            IPS_Bank ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getAsset_class());
                        }
                    }
                    if (ipsResults.getEquity_recommended_products().getPost_office().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Post Office");
                        int len = ipsResults.getEquity_recommended_products().getPost_office().size();
                        ArrayList<IPS_Postoffice> bank = ipsResults.getEquity_recommended_products().getPost_office();
                        for (int k = 0; k < len; k++) {
                            IPS_Postoffice ips_ps = bank.get(k);
                            leval2_child.append("\n").append(ips_ps.getAsset_class());
                        }
                    }
                    if (ipsResults.getEquity_recommended_products().getMutual_funds().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Mutual Found");
                        int len = ipsResults.getEquity_recommended_products().getMutual_funds().size();
                        ArrayList<IPS_Mutual_found> bank = ipsResults.getEquity_recommended_products().getMutual_funds();
                        for (int k = 0; k < len; k++) {
                            IPS_Mutual_found ips_ps = bank.get(k);
                            leval2_child.append("\n").append(ips_ps.getFund_house());
                        }
                    }
                }
                if (array_key.equals("debt_goals")) {
                    if (ipsResults.getDebt_recommended_products().getBank().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Bank");
                        int len = ipsResults.getDebt_recommended_products().getBank().size();
                        ArrayList<IPS_Bank> bank = ipsResults.getDebt_recommended_products().getBank();
                        for (int k = 0; k < len; k++) {
                            IPS_Bank ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getAsset_class());
                        }
                    }
                    if (ipsResults.getDebt_recommended_products().getPost_office().size() != 0) {
                        leval1_child.append("\n" + "\n").append("post office");
                        int len = ipsResults.getDebt_recommended_products().getPost_office().size();
                        ArrayList<IPS_Postoffice> bank = ipsResults.getDebt_recommended_products().getPost_office();
                        for (int k = 0; k < len; k++) {
                            IPS_Postoffice ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getAsset_class());
                        }
                    }
                    if (ipsResults.getDebt_recommended_products().getMutual_funds().size() != 0) {
                        leval1_child.append("\n" + "\n").append("post office");
                        int len = ipsResults.getDebt_recommended_products().getMutual_funds().size();
                        ArrayList<IPS_Mutual_found> bank = ipsResults.getDebt_recommended_products().getMutual_funds();
                        for (int k = 0; k < len; k++) {
                            IPS_Mutual_found ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getFund_house());
                        }
                    }
                }
                if (array_key.equals("liquid_goals")) {
                    if (ipsResults.getLiquid_recommended_products().getBank().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Bank");
                        int len = ipsResults.getLiquid_recommended_products().getBank().size();
                        ArrayList<IPS_Bank> bank = ipsResults.getLiquid_recommended_products().getBank();
                        for (int k = 0; k < len; k++) {
                            IPS_Bank ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getAsset_class());
                        }


                    }
                    if (ipsResults.getLiquid_recommended_products().getPost_office().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Post Office");
                        int len = ipsResults.getLiquid_recommended_products().getPost_office().size();
                        ArrayList<IPS_Postoffice> bank = ipsResults.getLiquid_recommended_products().getPost_office();
                        for (int k = 0; k < len; k++) {
                            IPS_Postoffice ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getAsset_class());
                        }
                    }
                    if (ipsResults.getLiquid_recommended_products().getMutual_funds().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Mutual Found");
                        int len = ipsResults.getLiquid_recommended_products().getMutual_funds().size();
                        ArrayList<IPS_Mutual_found> bank = ipsResults.getLiquid_recommended_products().getMutual_funds();
                        for (int k = 0; k < len; k++) {
                            IPS_Mutual_found ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getFund_house());
                        }
                    }
                }
                if (array_key.equals("comm_goals")) {
                    if (ipsResults.getComm_recommended_products().getBank().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Bank");
                        int len = ipsResults.getComm_recommended_products().getBank().size();
                        ArrayList<IPS_Bank> bank = ipsResults.getComm_recommended_products().getBank();
                        for (int k = 0; k < len; k++) {
                            IPS_Bank ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getAsset_class());
                        }
                    }
                    if (ipsResults.getComm_recommended_products().getPost_office().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Post Office");
                        int len = ipsResults.getComm_recommended_products().getPost_office().size();
                        ArrayList<IPS_Postoffice> bank = ipsResults.getComm_recommended_products().getPost_office();
                        for (int k = 0; k < len; k++) {
                            IPS_Postoffice ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getAsset_class());
                        }
                    }
                    if (ipsResults.getComm_recommended_products().getMutual_funds().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Post Office");
                        int len = ipsResults.getComm_recommended_products().getMutual_funds().size();
                        ArrayList<IPS_Mutual_found> bank = ipsResults.getComm_recommended_products().getMutual_funds();
                        for (int k = 0; k < len; k++) {
                            IPS_Mutual_found ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getFund_house());
                        }
                    }
                }
                if (array_key.equals("real_estate_goals")) {
                    if (ipsResults.getReal_estate_recommended_products().getBank().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Bank");
                        int len = ipsResults.getReal_estate_recommended_products().getBank().size();
                        ArrayList<IPS_Bank> bank = ipsResults.getReal_estate_recommended_products().getBank();
                        for (int k = 0; k < len; k++) {
                            IPS_Bank ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getAsset_class());

                        }
                    }
                    if (ipsResults.getReal_estate_recommended_products().getPost_office().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Post Office");
                        int len = ipsResults.getReal_estate_recommended_products().getPost_office().size();
                        ArrayList<IPS_Postoffice> bank = ipsResults.getReal_estate_recommended_products().getPost_office();
                        for (int k = 0; k < len; k++) {
                            IPS_Postoffice ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getAsset_class());

                        }
                    }
                    if (ipsResults.getReal_estate_recommended_products().getMutual_funds().size() != 0) {
                        leval1_child.append("\n" + "\n").append("Post Office");
                        int len = ipsResults.getReal_estate_recommended_products().getMutual_funds().size();
                        ArrayList<IPS_Mutual_found> bank = ipsResults.getReal_estate_recommended_products().getMutual_funds();
                        for (int k = 0; k < len; k++) {
                            IPS_Mutual_found ips_bank = bank.get(k);
                            leval2_child.append("\n").append(ips_bank.getFund_house());

                        }
                    }
                }
                Log.e("leval1_child:", leval1_child + "");
                Log.e("leval2_child:", leval2_child + "");
                leval1.append(leval1_child);
                leval2.append(leval2_child);
            }

            //  Log.d("assets.....>>>>", assets + "");
            // Log.d("product.....>>>>", product + "");
            // Log.d("current_value.....>>>>", current_value + "");
            // Log.d("Time_to_Attain.....>>>>", Time_to_Attain + "");
            // Log.d("time_to_achive.....>>>>", time_to_achive + "");

            cellList.add(new Cell(j + "00", assets + ""));
            cellList.add(new Cell(j + "01", product + ""));
            cellList.add(new Cell(j + "01", leval1 + ""));
            cellList.add(new Cell(j + "01", leval2 + ""));
            cellList.add(new Cell(j + "02", current_value + ""));
            cellList.add(new Cell(j + "03", inflation + ""));//inflation
            cellList.add(new Cell(j + "04", returns + ""));//return
            cellList.add(new Cell(j + "05", Time_to_Attain + ""));
            cellList.add(new Cell(j + "06", time_to_achive.toString()));
            cellList.add(new Cell(j + "07", Lumpsum + ""));
            cellList.add(new Cell(j + "08", monthly + ""));
            cellList.add(new Cell(j + "09", anual + ""));
            //cellList.add(new Cell(i + "04", "Equity - " + equity + " % " + "\n" + "Debt - " + debt + " % " + "\n" + "Liquid - " + liquid + " % "));
            //cellList.add(new Cell(i + "05", "₹ " + pmt_1year + " p.a." + "\n" + "₹ " + pmt_1_mon + " p.m." + "\n" + "₹ " + pval + " lumpsum"));
            list.add(cellList);
        }

        return list;
    }

    private Date get_date_format(String date_val) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.parse(date_val);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;

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
        inflater.inflate(R.menu.menu_summary_and_chart, menu);
        MenuItem item = menu.findItem(R.id.menu_summary);
        MenuItem items = menu.findItem(R.id.menu_chart);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_chart:
                try {

                    //addFragmenttoStack(new TaxAnaylsisBarChart());
                    addFragmenttoStack(new TaxAnaylsisBarChart());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:
                try {
                    addFragmenttoStack(new TaxPromptSummary());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

}

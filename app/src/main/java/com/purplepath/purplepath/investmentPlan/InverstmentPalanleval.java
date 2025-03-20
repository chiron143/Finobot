package com.purplepath.purplepath.investmentPlan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.TableView;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.investmentPlan.models.IPS_Income_details;
import com.purplepath.purplepath.investmentPlan.models.IPS_Results;
import com.purplepath.purplepath.investmentPlan.models.IPS_Sum;
import com.purplepath.purplepath.investmentPlan.models.InvestmentPlan;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by rajasekar b on 27/02/2019.
 */
public class InverstmentPalanleval extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    private InvestmentPlan investmentPlans;

    private ArrayList<IPS_Results> arrayList_data;

    private TableView mTableView;

    private BigDecimal per1year;

    private BigDecimal per1month;

    private BigDecimal pvals;
    private RecyclerView recyclerView;
    InvestmentPlanningAdapterleval adapter;
    private TextView text_header;
    String per1year2 = "", per1month2 = "", pvals2 = "", key_name_sub = "", key_name = "", indicate = "";

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
        return inflater.inflate(R.layout.fragment_inverstment_planings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallBackListener.setActionBarTitle("Investment Planning");


        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        recyclerView = view.findViewById(R.id.recyclerView);
        text_header = view.findViewById(R.id.text_header);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        mTableView = createTableViewSavings();
        //callAllInsuranceService();
        //callInvestmentService();
        load_data();


    }

    private TableView createTableViewSavings() {
        TableView tableView = new TableView(getContext());
        // Set adapter
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tableView.setLayoutParams(tlp);
        return tableView;
    }

    private void load_data() {
        try {
            text_header.setText("Sub Asset Class");
            if (null != getArguments()) {
                Log.d("getArguments:", getArguments() + "");
                if (getArguments().containsKey("InvestmentPlan")) {
                    investmentPlans = (InvestmentPlan) getArguments().getSerializable("InvestmentPlan");
                    key_name = getArguments().getString("key_name");
                    arrayList_data = investmentPlans.getData().getGoals_with_asset_class().get(key_name);
                    Log.d("load data 1:", investmentPlans.getData().getMessage());
                } else {
                    Log.d("load data 2:", investmentPlans.getData().getMessage());
                }


                if (key_name.equalsIgnoreCase("equity_goals")) {

                    indicate = "Equity->";
                } else if (key_name.equalsIgnoreCase("debt_goals")) {
                    indicate = "Debt->";

                } else if (key_name.equalsIgnoreCase("liquid_goals")) {
                    indicate = "Liquid->";

                } else if (key_name.equalsIgnoreCase("comm_goals")) {
                    indicate = "Commodity->";

                } else if (key_name.equalsIgnoreCase("real_estate_goals")) {
                    indicate = "Real Estate->";

                }

                text_header.setText(indicate + "Sub Asset Class");


            }
            Log.d("load data:", investmentPlans.getData().getMessage());
            int array_size = arrayList_data.size();

            ArrayList<Integer> start_date = new ArrayList<>();
            ArrayList<Integer> end_date = new ArrayList<>();
            ArrayList<String> mutual_fund = new ArrayList<>();
            ArrayList<String> mutual_fund_av = new ArrayList<>();
            ArrayList<String> bank_fund_av = new ArrayList<>();
            ArrayList<String> bank_fund = new ArrayList<>();
            ArrayList<String> post_fund_av = new ArrayList<>();
            ArrayList<String> post_fund = new ArrayList<>();

            ArrayList<Double> mutual_fund_cv = new ArrayList<>();
            ArrayList<Double> mutual_fund_cv_av = new ArrayList<>();
            ArrayList<Double> mutual_fund_fv = new ArrayList<>();
            ArrayList<Double> mutual_fund_fv_av = new ArrayList<>();
            ArrayList<Double> mutual_fund_ac = new ArrayList<>();
            ArrayList<Double> mutual_fund_ac_av = new ArrayList<>();
            ArrayList<Integer> mutual_fund_div = new ArrayList<>();
            ArrayList<Integer> mutual_fund_div_av = new ArrayList<>();
            ArrayList<Double> bank_fund_cv = new ArrayList<>();
            ArrayList<Double> bank_fund_cv_av = new ArrayList<>();
            ArrayList<Double> bank_fund_fv = new ArrayList<>();
            ArrayList<Double> bank_fund_fv_av = new ArrayList<>();
            ArrayList<Integer> bank_fund_div = new ArrayList<>();
            ArrayList<Integer> bank_fund_div_av = new ArrayList<>();
            ArrayList<Double> bank_fund_ac = new ArrayList<>();
            ArrayList<Double> bank_fund_ac_av = new ArrayList<>();
            ArrayList<Double> post_fund_cv = new ArrayList<>();
            ArrayList<Double> post_fund_cv_av = new ArrayList<>();
            ArrayList<Double> post_fund_fv = new ArrayList<>();
            ArrayList<Double> post_fund_fv_av = new ArrayList<>();
            ArrayList<Integer> post_fund_div = new ArrayList<>();
            ArrayList<Integer> post_fund_div_av = new ArrayList<>();
            ArrayList<Double> post_fund_ac = new ArrayList<>();
            ArrayList<Double> post_fund_ac_av = new ArrayList<>();

            double mutual_ctotal = 0.00;
            double mutual_ftotal = 0.00;
            double mutual_ac = 0.00;
            double bank_ac = 0.00;
            double post_ac = 0.00;
            int mutual_div = 0;
            double bank_ctotal = 0.00;
            double bank_ftotal = 0.00;
            int bank_div = 0;
            double post_ctotal = 0.00;
            double post_ftotal = 0.00;
            int post_div = 0;

            IPS_Income_details income_details = investmentPlans.getData().getIncome_details();

            double si_total = income_details.getSi_total();
            double ip_total = income_details.getIp_total();
            double ib_total = income_details.getIb_total();
            double cg_total = income_details.getSi_total();
            double ifs_total = income_details.getSi_total();
            double current_val = 0, annual_con = 0, target_val = 0, target_div = 0;
            for (int i = 0; i < array_size; i++) {
                IPS_Results results = arrayList_data.get(i);

                Log.d("Check 1", "1");
                Log.d("Check 1", key_name + "");
                if (key_name.equalsIgnoreCase("equity_goals")) {
                    Log.d("Check 1", "2");

                    if (results.getEquity_recommended_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund.add("Mutual Fund");
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                        Log.d("Mutual_Fund_calculation", Double.parseDouble(results.getEquity_fv())+"*"+Double.parseDouble(results.getEquity_per()));
                        mutual_fund_fv.add(target_val);
                        mutual_fund_cv.add(current_val);
                        mutual_fund_div.add(1);
                        mutual_fund_ac.add(Double.parseDouble(results.getEquity_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));
                    }

                    if (results.getEquity_recommended_products().getBank().size() > 0) {
                        //mutual_fund.add("Mutual Fund");
                    }
                } else if (key_name.equalsIgnoreCase("debt_goals")) {
                    if (results.getDebt_recommended_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund.add("Mutual Fund");
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));
                        mutual_fund_cv.add(current_val);
                        mutual_fund_fv.add(target_val);
                        mutual_fund_div.add(1);
                        mutual_fund_ac.add(Double.parseDouble(results.getFixed_inc_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));
                    }
                    if (results.getDebt_recommended_products().getBank().size() != 0) {
                        bank_fund.add("Bank");
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));

                        bank_fund_fv.add(target_val);
                        bank_fund_cv.add(current_val);
                        bank_fund_div.add(1);
                        bank_fund_ac.add(Double.parseDouble(results.getFixed_inc_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));


                    }
                    if (results.getDebt_recommended_products().getPost_office().size() != 0) {
                        post_fund.add("Post Office");
                        target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_cv.add(current_val);
                        post_fund_fv.add(target_val);
                        post_fund_div.add(1);
                        post_fund_ac.add(Double.parseDouble(results.getFixed_inc_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));


                    }

                } else if (key_name.equalsIgnoreCase("liquid_goals")) {
                    System.out.println("LQ=>" + results.getLiquid_recommended_products().getMutual_funds().size());
                    System.out.println("LQ=>" + results.getLiquid_recommended_products().getBank().size());
                    System.out.println("LQ=>" + results.getLiquid_recommended_products().getPost_office().size());
                    if (results.getLiquid_recommended_products().getMutual_funds().size() != 0) {
                        Log.d("target_val r ->", results.getLiquid_fv() + ":" + results.getLiquid_per());
                        mutual_fund.add("Mutual Fund");
                        target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        mutual_fund_cv.add(current_val);
                        mutual_fund_fv.add(target_val);
                        mutual_fund_div.add(1);
                        mutual_fund_ac.add(Double.parseDouble(results.getLiquid_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));


                    }
                    if (results.getLiquid_recommended_products().getBank().size() != 0) {
                        bank_fund.add("Bank");
                        Log.d("target_val r ->", results.getLiquid_fv() + ":" + results.getLiquid_per());

                        target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        bank_fund_cv.add(current_val);
                        bank_fund_fv.add(target_val);
                        bank_fund_div.add(1);
                        bank_fund_ac.add(Double.parseDouble(results.getLiquid_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }
                    if (results.getLiquid_recommended_products().getPost_office().size() != 0) {
                        Log.d("target_val r ->", results.getLiquid_fv() + ":" + results.getLiquid_per());

                        post_fund.add("Post Office");
                        target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_fv.add(target_val);
                        post_fund_cv.add(current_val);
                        post_fund_div.add(1);
                        post_fund_ac.add(Double.parseDouble(results.getLiquid_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));


                    }
                } else if (key_name.equalsIgnoreCase("comm_goals")) {
                    if (results.getComm_recommended_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund.add("Mutual Fund");
                        target_val = (Double.parseDouble(results.getComm_fv()) * Double.parseDouble(results.getComm_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        mutual_fund_fv.add(target_val);
                        mutual_fund_cv.add(current_val);
                        mutual_fund_div.add(1);
                        mutual_fund_ac.add(Double.parseDouble(results.getComm_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));


                    }
                    if (results.getComm_recommended_products().getBank().size() != 0) {
                        bank_fund.add("Bank");
                        target_val = (Double.parseDouble(results.getComm_fv()) * Double.parseDouble(results.getComm_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        bank_fund_cv.add(current_val);
                        bank_fund_fv.add(target_val);
                        bank_fund_div.add(1);
                        bank_fund_ac.add(Double.parseDouble(results.getComm_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }
                    if (results.getComm_recommended_products().getPost_office().size() != 0) {
                        post_fund.add("Post Office");
                        target_val = (Double.parseDouble(results.getComm_fv()) * Double.parseDouble(results.getComm_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_fv.add(target_val);
                        post_fund_cv.add(current_val);
                        post_fund_div.add(1);
                        post_fund_ac.add(Double.parseDouble(results.getComm_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }

                } else if (key_name.equalsIgnoreCase("real_estate_goals")) {
                    if (results.getReal_estate_recommended_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund.add("Mutual Fund");
                        target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        mutual_fund_fv.add(target_val);
                        mutual_fund_cv.add(current_val);
                        mutual_fund_div.add(1);
                        mutual_fund_ac.add(Double.parseDouble(results.getReal_estate_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));


                    }
                    if (results.getReal_estate_recommended_products().getBank().size() != 0) {
                        bank_fund.add("Bank");
                        target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        bank_fund_cv.add(current_val);
                        bank_fund_fv.add(target_val);
                        bank_fund_div.add(1);
                        bank_fund_ac.add(Double.parseDouble(results.getReal_estate_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));


                    }
                    if (results.getReal_estate_recommended_products().getPost_office().size() != 0) {
                        post_fund.add("Post Office");
                        target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_cv.add(current_val);
                        post_fund_fv.add(target_val);
                        post_fund_div.add(1);
                        post_fund_ac.add(Double.parseDouble(results.getReal_estate_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }

                }
            }
            for (int j = 0; j < array_size; j++) {
                IPS_Results results = arrayList_data.get(j);

                Log.d("Check 1", "1");
                Log.d("Check 1", key_name + "");
                if (key_name.equalsIgnoreCase("equity_goals")) {
                    Log.d("Check 1", "2");

                    if (results.getEquity_available_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund_av.add("Mutual Fund");
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                        mutual_fund_fv_av.add(target_val);
                        mutual_fund_cv_av.add(current_val);
                        mutual_fund_div_av.add(1);
                        mutual_fund_ac_av.add(Double.parseDouble(results.getEquity_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));


                    }
                    if (results.getEquity_available_products().getBank().size() > 0) {
                        //mutual_fund.add("Mutual Fund");
                    }

                } else if (key_name.equalsIgnoreCase("debt_goals")) {
                    if (results.getDebt_available_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund_av.add("Mutual Fund");
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));
                        mutual_fund_cv_av.add(current_val);
                        mutual_fund_fv_av.add(target_val);
                        mutual_fund_div_av.add(1);
                        mutual_fund_ac_av.add(Double.parseDouble(results.getFixed_inc_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }
                    if (results.getDebt_available_products().getBank().size() != 0) {
                        bank_fund_av.add("Bank");
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));

                        bank_fund_fv_av.add(target_val);
                        bank_fund_cv_av.add(current_val);
                        bank_fund_div_av.add(1);
                        bank_fund_ac_av.add(Double.parseDouble(results.getFixed_inc_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }
                    if (results.getDebt_available_products().getPost_office().size() != 0) {
                        post_fund_av.add("Post Office");

                        target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_cv_av.add(current_val);
                        post_fund_fv_av.add(target_val);
                        post_fund_div_av.add(1);
                        post_fund_ac_av.add(Double.parseDouble(results.getFixed_inc_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }

                } else if (key_name.equalsIgnoreCase("liquid_goals")) {
                    if (results.getLiquid_available_products().getMutual_funds().size() != 0) {
                        Log.d("target_val a ->", results.getLiquid_fv() + ":" + results.getLiquid_per());
                        mutual_fund_av.add("Mutual Fund");
                        target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        mutual_fund_cv_av.add(current_val);
                        mutual_fund_fv_av.add(target_val);
                        mutual_fund_div_av.add(1);
                        mutual_fund_ac_av.add(Double.parseDouble(results.getLiquid_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }
                    if (results.getLiquid_available_products().getBank().size() != 0) {
                        bank_fund_av.add("Bank");
                        Log.d("target_val a ->", results.getLiquid_fv() + ":" + results.getLiquid_per());

                        target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));

                        bank_fund_cv_av.add(current_val);
                        bank_fund_fv_av.add(target_val);
                        bank_fund_div_av.add(1);
                        bank_fund_ac_av.add(Double.parseDouble(results.getLiquid_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }
                    if (results.getLiquid_available_products().getPost_office().size() != 0) {
                        Log.d("target_val a ->", results.getLiquid_fv() + ":" + results.getLiquid_per());

                        post_fund_av.add("Post Office");
                        target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_fv_av.add(target_val);
                        post_fund_cv_av.add(current_val);
                        post_fund_div_av.add(1);
                        post_fund_ac_av.add(Double.parseDouble(results.getLiquid_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }

                } else if (key_name.equalsIgnoreCase("comm_goals")) {
                    if (results.getComm_available_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund_av.add("Mutual Fund");
                        target_val = (Double.parseDouble(results.getComm_fv()) * Double.parseDouble(results.getComm_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        mutual_fund_fv_av.add(target_val);
                        mutual_fund_cv_av.add(current_val);
                        mutual_fund_div_av.add(1);
                        mutual_fund_ac_av.add(Double.parseDouble(results.getComm_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }
                    if (results.getComm_available_products().getBank().size() != 0) {
                        bank_fund_av.add("Bank");
                        target_val = (Double.parseDouble(results.getComm_fv()) * Double.parseDouble(results.getComm_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        bank_fund_cv_av.add(current_val);
                        bank_fund_fv_av.add(target_val);
                        bank_fund_div_av.add(1);
                        bank_fund_ac_av.add(Double.parseDouble(results.getComm_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }
                    if (results.getComm_available_products().getPost_office().size() != 0) {
                        post_fund_av.add("Post Office");
                        target_val = (Double.parseDouble(results.getComm_fv()) * Double.parseDouble(results.getComm_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_fv_av.add(target_val);
                        post_fund_cv_av.add(current_val);
                        post_fund_div_av.add(1);
                        post_fund_ac_av.add(Double.parseDouble(results.getComm_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }

                } else if (key_name.equalsIgnoreCase("real_estate_goals")) {
                    if (results.getReal_estate_available_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund_av.add("Mutual Fund");
                        target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        mutual_fund_fv_av.add(target_val);
                        mutual_fund_cv_av.add(current_val);
                        mutual_fund_div_av.add(1);
                        mutual_fund_ac_av.add(Double.parseDouble(results.getReal_estate_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }
                    if (results.getReal_estate_available_products().getBank().size() != 0) {
                        bank_fund_av.add("Bank");
                        target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        bank_fund_cv_av.add(current_val);
                        bank_fund_fv_av.add(target_val);
                        bank_fund_div_av.add(1);
                        bank_fund_ac_av.add(Double.parseDouble(results.getReal_estate_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }
                    if (results.getReal_estate_available_products().getPost_office().size() != 0) {
                        post_fund_av.add("Post Office");
                        target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_cv_av.add(current_val);
                        post_fund_fv_av.add(target_val);
                        post_fund_div_av.add(1);
                        post_fund_ac_av.add(Double.parseDouble(results.getReal_estate_annual_contr()));
                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                    }

                }
            }


            ArrayList<IPS_Sum> list_ips_sum = new ArrayList<>();


            mutual_ctotal = calculate_val(mutual_fund_cv);
            mutual_ftotal = calculate_val(mutual_fund_fv);
            mutual_div = calculate_div(mutual_fund_div);
            mutual_ac = calculate_val(mutual_fund_ac);
            bank_ctotal = calculate_val(bank_fund_cv);
            bank_ftotal = calculate_val(bank_fund_fv);
            bank_div = calculate_div(bank_fund_div);
            bank_ac = calculate_val(bank_fund_ac);
            post_ctotal = calculate_val(post_fund_cv);
            post_ftotal = calculate_val(post_fund_fv);
            post_div = calculate_div(post_fund_div);
            post_ac = calculate_val(post_fund_ac);


            IPS_Sum ips_sum = new IPS_Sum();

            if (!merge_string(mutual_fund).equals("")) {
                ips_sum.setTitle(merge_string(mutual_fund));
                ips_sum.setTarget_value(mutual_ftotal);
                Log.e("mutual_fund", mutual_ftotal+"");
                ips_sum.setCurrent_value(mutual_ctotal);
                ips_sum.setStart_date_s(start_date);
                ips_sum.setEnd_date_s(end_date);

                if (mutual_ac != 0) {
                    ips_sum.setSi_total((si_total / mutual_ac) * 100);
                    ips_sum.setIp_total((ip_total / mutual_ac) * 100);
                    ips_sum.setIb_total((ib_total / mutual_ac) * 100);
                    ips_sum.setCg_total((cg_total / mutual_ac) * 100);
                    ips_sum.setIfs_total((ifs_total / mutual_ac) * 100);

                } else {
                    ips_sum.setSi_total(0);
                    ips_sum.setIp_total(0);
                    ips_sum.setIb_total(0);
                    ips_sum.setCg_total(0);
                    ips_sum.setIfs_total(0);
                }
                    ips_sum.setDiv(mutual_div);

                Log.e("mutual_div", arrayList_data.size()+"");
                list_ips_sum.add(ips_sum);

            }


            ///
            if (!merge_string(bank_fund).equals("")) {

                ips_sum = new IPS_Sum();
                ips_sum.setTitle(merge_string(bank_fund));
                ips_sum.setTarget_value(bank_ftotal);
                Log.d("mutual_fund_bank", bank_ftotal+"");
                ips_sum.setCurrent_value(bank_ctotal);

                    ips_sum.setDiv(bank_div);
                ips_sum.setStart_date_s(start_date);
                ips_sum.setEnd_date_s(end_date);
                if (bank_ac != 0) {
                    ips_sum.setSi_total((si_total / bank_ac) * 100);
                    ips_sum.setIp_total((ip_total / bank_ac) * 100);
                    ips_sum.setIb_total((ib_total / bank_ac) * 100);
                    ips_sum.setCg_total((cg_total / bank_ac) * 100);
                    ips_sum.setIfs_total((ifs_total / bank_ac) * 100);
                } else {
                    ips_sum.setSi_total(0);
                    ips_sum.setIp_total(0);
                    ips_sum.setIb_total(0);
                    ips_sum.setCg_total(0);
                    ips_sum.setIfs_total(0);
                }
                list_ips_sum.add(ips_sum);
            }

            if (!merge_string(post_fund).equals("")) {

                ips_sum = new IPS_Sum();
                ips_sum.setTitle(merge_string(post_fund));
                ips_sum.setTarget_value(post_ftotal);
                Log.d("mutual_fund_post", post_ftotal+"");
                ips_sum.setCurrent_value(post_ctotal);

                    ips_sum.setDiv(post_div);

                ips_sum.setStart_date_s(start_date);
                ips_sum.setEnd_date_s(end_date);
                if (bank_ac != 0) {
                    ips_sum.setSi_total((post_ac / bank_ac) * 100);
                    ips_sum.setIp_total((post_ac / bank_ac) * 100);
                    ips_sum.setIb_total((post_ac / bank_ac) * 100);
                    ips_sum.setCg_total((post_ac / bank_ac) * 100);
                    ips_sum.setIfs_total((post_ac / bank_ac) * 100);
                } else {
                    ips_sum.setSi_total(0);
                    ips_sum.setIp_total(0);
                    ips_sum.setIb_total(0);
                    ips_sum.setCg_total(0);
                    ips_sum.setIfs_total(0);
                }

                list_ips_sum.add(ips_sum);
            }


            mutual_ctotal = calculate_val(mutual_fund_cv_av);
            mutual_ftotal = calculate_val(mutual_fund_fv_av);
            mutual_div = calculate_div(mutual_fund_div_av);
            mutual_ac = calculate_val(mutual_fund_ac_av);
            bank_ctotal = calculate_val(bank_fund_cv_av);
            bank_ftotal = calculate_val(bank_fund_fv_av);
            bank_div = calculate_div(bank_fund_div_av);
            bank_ac = calculate_val(bank_fund_ac_av);
            post_ctotal = calculate_val(post_fund_cv_av);
            post_ftotal = calculate_val(post_fund_fv_av);
            post_div = calculate_div(post_fund_div_av);
            post_ac = calculate_val(post_fund_ac_av);


            ips_sum = new IPS_Sum();

            if (!merge_string(mutual_fund_av).equals("")) {
                ips_sum.setTitle(merge_string(mutual_fund_av));
                ips_sum.setType("AV");
                ips_sum.setTarget_value(mutual_ftotal);
                Log.d("mutual_fund_av", mutual_ftotal+"");
                ips_sum.setCurrent_value(mutual_ctotal);
                ips_sum.setStart_date_s(start_date);
                ips_sum.setEnd_date_s(end_date);
                if (mutual_ac != 0) {
                    ips_sum.setSi_total((si_total / mutual_ac) * 100);
                    ips_sum.setIp_total((ip_total / mutual_ac) * 100);
                    ips_sum.setIb_total((ib_total / mutual_ac) * 100);
                    ips_sum.setCg_total((cg_total / mutual_ac) * 100);
                    ips_sum.setIfs_total((ifs_total / mutual_ac) * 100);

                } else {
                    ips_sum.setSi_total(0);
                    ips_sum.setIp_total(0);
                    ips_sum.setIb_total(0);
                    ips_sum.setCg_total(0);
                    ips_sum.setIfs_total(0);
                }

                    ips_sum.setDiv(mutual_div);
                list_ips_sum.add(ips_sum);
            }


            ///
            if (!merge_string(bank_fund_av).equals("")) {

                ips_sum = new IPS_Sum();
                ips_sum.setTitle(merge_string(bank_fund_av));
                ips_sum.setType("AV");

                ips_sum.setTarget_value(bank_ftotal);
                Log.d("mutual_fund_bank_av", bank_ftotal+"");
                ips_sum.setCurrent_value(bank_ctotal);

                    ips_sum.setDiv(bank_div);
                ips_sum.setStart_date_s(start_date);
                ips_sum.setEnd_date_s(end_date);
                if (bank_ac != 0) {
                    ips_sum.setSi_total((si_total / bank_ac) * 100);
                    ips_sum.setIp_total((ip_total / bank_ac) * 100);
                    ips_sum.setIb_total((ib_total / bank_ac) * 100);
                    ips_sum.setCg_total((cg_total / bank_ac) * 100);
                    ips_sum.setIfs_total((ifs_total / bank_ac) * 100);
                } else {
                    ips_sum.setSi_total(0);
                    ips_sum.setIp_total(0);
                    ips_sum.setIb_total(0);
                    ips_sum.setCg_total(0);
                    ips_sum.setIfs_total(0);
                }
                list_ips_sum.add(ips_sum);
            }

            if (!merge_string(post_fund_av).equals("")) {

                ips_sum = new IPS_Sum();
                ips_sum.setTitle(merge_string(post_fund_av));
                ips_sum.setType("AV");
                ips_sum.setTarget_value(post_ftotal);
                Log.e("mutual_fund_post_av", post_ftotal+"");
                ips_sum.setCurrent_value(post_ctotal);

                    ips_sum.setDiv(post_div);
                ips_sum.setStart_date_s(start_date);
                ips_sum.setEnd_date_s(end_date);
                if (bank_ac != 0) {
                    ips_sum.setSi_total((post_ac / bank_ac) * 100);
                    ips_sum.setIp_total((post_ac / bank_ac) * 100);
                    ips_sum.setIb_total((post_ac / bank_ac) * 100);
                    ips_sum.setCg_total((post_ac / bank_ac) * 100);
                    ips_sum.setIfs_total((post_ac / bank_ac) * 100);
                } else {
                    ips_sum.setSi_total(0);
                    ips_sum.setIp_total(0);
                    ips_sum.setIb_total(0);
                    ips_sum.setCg_total(0);
                    ips_sum.setIfs_total(0);
                }

                list_ips_sum.add(ips_sum);
            }


            // Set<String> set = new HashSet<>(mutual_fund);
            // mutual_fund.clear();
            // mutual_fund.addAll(set);

            adapter = new InvestmentPlanningAdapterleval(list_ips_sum);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);


           /* if (!merge_string(mutual_fund).equals("")) {

                ips_sum.setDiv(adapter.getItemCount());
                Log.e("mutual_div", mutual_div+"");
                list_ips_sum.add(ips_sum);
            }


            ///
            if (!merge_string(bank_fund).equals("")) {

                ips_sum.setDiv(adapter.getItemCount());

                list_ips_sum.add(ips_sum);
            }

            if (!merge_string(post_fund).equals("")) {


                ips_sum.setDiv(adapter.getItemCount());


                list_ips_sum.add(ips_sum);
            }


            if (!merge_string(mutual_fund_av).equals("")) {

                ips_sum.setDiv(adapter.getItemCount());
                list_ips_sum.add(ips_sum);
            }


            ///
            if (!merge_string(bank_fund_av).equals("")) {


                ips_sum.setDiv(adapter.getItemCount());

                list_ips_sum.add(ips_sum);
            }

            if (!merge_string(post_fund_av).equals("")) {


                ips_sum.setDiv(adapter.getItemCount());
                list_ips_sum.add(ips_sum);
            }*/



            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private double calculate_val(ArrayList<Double> list) {
        double final_result = 0.00;
        try {
            int len = list.size();

            for (int i = 0; i < len; i++) {

                final_result += list.get(i);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return final_result;
    }

    private int calculate_div(ArrayList<Integer> list) {

        int final_result = 0;
        try {
            int len = list.size();

            for (int i = 0; i < len; i++) {

                final_result += list.get(i);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return final_result;
    }

    private String merge_string(ArrayList<String> list) {

        String final_result = "";
        try {
            int len = list.size();

            for (int i = 0; i < len; i++) {

                final_result = list.get(i);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return final_result;
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
                    InvestmentPlanningGraf fragment = InvestmentPlanningGraf.newInstance(investmentPlans);

                    addFragmenttoStack(fragment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.menu_summary:
                try {

                    Bundle args = new Bundle();
                    args.putSerializable("InvestmentPlan", investmentPlans);
                    args.putString("Level", "Level1");
                    args.putString("key_name", key_name);

                    InverstmentPalanStatementView fragment = new InverstmentPalanStatementView();
                    fragment.setArguments(args);


                    addFragmenttoStack(fragment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private class InvestmentPlanningAdapterleval extends RecyclerView.Adapter<InvestmentPlanningAdapterleval.ViewHolder> {
        private ArrayList<IPS_Sum> arrayList_data;
        public View view;
        private Context context;
        double tar_val;

        private InvestmentPlanningAdapterleval(ArrayList<IPS_Sum> ivp) {
            this.arrayList_data = ivp;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView text_annual_value, text_group_name, text_current_value, text_target_value, text_start_date,
                    text_end_date, text_sp, text_hp, text_rp, text_cg, text_os;

            LinearLayout linear_goal_name;

            public ViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                text_group_name = view.findViewById(R.id.text_group_name);
                text_annual_value = view.findViewById(R.id.text_anual_value);
                text_current_value = view.findViewById(R.id.text_current_value);
                text_target_value = view.findViewById(R.id.text_target_value);
                text_start_date = view.findViewById(R.id.text_start_date);
                text_end_date = view.findViewById(R.id.text_end_date);
                linear_goal_name = view.findViewById(R.id.linear_goal_name);
                text_sp = view.findViewById(R.id.text_sp);
                text_hp = view.findViewById(R.id.text_hp);
                text_rp = view.findViewById(R.id.text_rp);
                text_cg = view.findViewById(R.id.text_cg);
                text_os = view.findViewById(R.id.text_os);

            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.investment_plan_adapterleval, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.text_group_name.setText(arrayList_data.get(position).getTitle());

            if (arrayList_data.get(position).getType().equalsIgnoreCase("AV")) {
                holder.text_group_name.setTextColor(Color.GREEN);

            }
            BigDecimal target_value = BigDecimal.ONE;
            BigDecimal current_value = BigDecimal.ONE;
            BigDecimal annual_value = BigDecimal.ONE;
            Log.d("Viswa_txt", arrayList_data.get(position).getType());
            annual_value = new BigDecimal(arrayList_data.get(position).getAnnual_con());
            current_value = new BigDecimal(arrayList_data.get(position).getCurrent_value());
            target_value = new BigDecimal((arrayList_data.get(position).getTarget_value() / /*arrayList_data.get(position).getDiv()*/adapter.getItemCount()));
            holder.text_target_value.setText(UtileKit.formatedNumbers_decimal(target_value));
            tar_val = (arrayList_data.get(position).getTarget_value() / /*arrayList_data.get(position).getDiv()*/adapter.getItemCount());
            Log.d("Target_value", arrayList_data.get(position).getTarget_value()+"/"+adapter.getItemCount());
            holder.text_current_value.setText(UtileKit.formatedNumbers_decimal(current_value));
            holder.text_sp.setText(arrayList_data.get(position).getSi_total() + "");
            holder.text_hp.setText(arrayList_data.get(position).getIb_total() + "");
            holder.text_rp.setText(arrayList_data.get(position).getIp_total() + "");
            holder.text_cg.setText(arrayList_data.get(position).getIfs_total() + "");
            holder.text_os.setText(arrayList_data.get(position).getCg_total() + "");
            holder.text_annual_value.setText(UtileKit.formatedNumbers_decimal(annual_value));
            holder.text_start_date.setText(UtileKit.get_start_date(arrayList_data.get(position).getStart_date_s()));
            holder.text_end_date.setText(UtileKit.get_end_date(arrayList_data.get(position).getStart_date_s()));
            UtileKit.get_start_date(arrayList_data.get(position).getStart_date_s());
            UtileKit.get_start_date(arrayList_data.get(position).getEnd_date_s());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    key_name_sub = arrayList_data.get(position).getTitle();
                    Bundle args = new Bundle();
                    args.putSerializable("InvestmentPlan", investmentPlans);
                    Log.e("InvestmentPlan", investmentPlans.toString());
                    //key_name = getArguments().getString("key_name");
                    args.putString("key_name", key_name);
                    args.putDouble("target_val", tar_val);
                    Log.d("target_val", tar_val+"");
                    Log.e("key_name", key_name);
                    args.putString("key_name_sub", key_name_sub);
                    Log.e("key_name_sub", key_name);
                    args.putString("indicate", indicate + key_name_sub);
                    Log.e("indicate", indicate + key_name_sub);
                    InverstmentPalanleval1 fragment = new InverstmentPalanleval1();
                    fragment.setArguments(args);
                    addFragmenttoStack(fragment);


                }
            });
        }

        @Override
        public int getItemCount() {
            if (arrayList_data.size() == 0) {
                return 1;
            }
            return arrayList_data.size();

        }
    }

}

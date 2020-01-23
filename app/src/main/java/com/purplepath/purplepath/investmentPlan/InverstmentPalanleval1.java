package com.purplepath.purplepath.investmentPlan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.purplepath.purplepath.investmentPlan.models.IPS_Bank;
import com.purplepath.purplepath.investmentPlan.models.IPS_Mutual_found;
import com.purplepath.purplepath.investmentPlan.models.IPS_Postoffice;
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
public class InverstmentPalanleval1 extends BaseFragment implements View.OnClickListener {

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;


    private InvestmentPlan investmentPlans;
    private ArrayList<IPS_Results> arrayList_data;


    private TableView mTableView;

    private BigDecimal per1year;

    private BigDecimal per1month;
    ArrayList<IPS_Sum> list_ips_sum = new ArrayList<>();
    ArrayList<InvestmentPlan> list_ips_sum1 = new ArrayList<>();
    private BigDecimal pvals;
    private RecyclerView recyclerView;
    InvestmentPlanningAdapterleval adapter;
    private TextView text_header;
    String per1year2 = "", per1month2 = "", pvals2 = "", indicate = "", key_name_sub = "", key_name = "";
    double values;

    private ArrayList<String> mColoumNameList = new ArrayList<String>
            (Arrays.asList("Assets Class", "Goal Name", "Product level 1", "Product level 2", "Cost of Goal", "Inflation Rate", "Return Rate", "Time to Attain", "Duration", "Lumpsum", "Monthly", "Annual"));

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
            text_header.setText("Products");
            if (null != getArguments()) {
                Log.d("getArguments:", getArguments() + "");
                if (getArguments().containsKey("InvestmentPlan")) {
                    investmentPlans = (InvestmentPlan) getArguments().getSerializable("InvestmentPlan");
                    key_name = getArguments().getString("key_name");
                    key_name_sub = getArguments().getString("key_name_sub");
                    values = getArguments().getDouble("target_val");
                    Log.d("Check_double", values+"");
                    indicate = getArguments().getString("indicate");
                    arrayList_data = investmentPlans.getData().getGoals_with_asset_class().get(key_name);
                    Log.d("load data 1:", investmentPlans.getData().getMessage());
                } else {
                    Log.d("load data 2:", investmentPlans.getData().getMessage());
                }
                text_header.setText(indicate + "->" + "Products");

            }
            Log.d("load data:", investmentPlans.getData().getMessage());
            int array_size = arrayList_data.size();

            ArrayList<Integer> end_date = new ArrayList<>();
            ArrayList<Integer> start_date = new ArrayList<>();
            ArrayList<String> mutual_fund = new ArrayList<>();
            ArrayList<String> mutual_fund_code = new ArrayList<>();
            ArrayList<String> bank_fund = new ArrayList<>();
            ArrayList<String> post_fund = new ArrayList<>();

            ArrayList<Double> mutual_fund_cv = new ArrayList<>();
            ArrayList<Double> mutual_fund_fv = new ArrayList<>();
            ArrayList<Double> bank_fund_cv = new ArrayList<>();
            ArrayList<Double> bank_fund_fv = new ArrayList<>();
            ArrayList<Double> post_fund_cv = new ArrayList<>();
            ArrayList<Double> post_fund_fv = new ArrayList<>();

            double mutual_ctotal = 0.00;
            double mutual_ftotal = 0.00;
            double bank_ctotal = 0.00;
            double bank_ftotal = 0.00;
            double post_ctotal = 0.00;
            double post_ftotal = 0.00;
            double current_val = 0, target_val = 0;
            for (int i = 0; i < array_size; i++) {
                list_ips_sum = new ArrayList<>();
                list_ips_sum1 = new ArrayList<>();
                IPS_Results results = arrayList_data.get(i);
                Log.d("Check 1", "1");
                Log.d("Check 1", key_name + "");
                if (key_name.equalsIgnoreCase("equity_goals")) {
                    Log.d("Check 1", "2");
                    if (results.getEquity_recommended_products().getMutual_funds().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {

                            ArrayList<IPS_Mutual_found> lis_mutual_fund = results.getEquity_recommended_products().getMutual_funds();
                            current_val = (Double.parseDouble(results.getComm_current_value()));
                            target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                            Log.d("2", Double.parseDouble(results.getEquity_fv())+"*"+Double.parseDouble(results.getEquity_per()));

/*
                            if (lis_mutual_fund.size() != 0) {
                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Mutual_found ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1:", j + "");
                                    mutual_fund.add(ips_mutual_found.getScheme_name());
                                    mutual_fund_code.add(ips_mutual_found.getScheme_code());
                                    //current_val = (Double.parseDouble(ips_mutual_found.getMin_invst_amt()));
                                    // target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val);
                                }
                            }
*/

                            if (lis_mutual_fund.size() != 0) {
                                ArrayList<IPS_Sum> list_ips_sum_temp = new ArrayList<>();
                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Mutual_found ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1:", j + "");
                                    mutual_fund.add(ips_mutual_found.getScheme_name());
                                    mutual_fund_code.add(ips_mutual_found.getScheme_code());
                                    start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                                    //current_val = (Double.parseDouble(ips_mutual_found.getMin_invst_amt()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val);
                                    IPS_Sum ips_sum = new IPS_Sum();
                                    ips_sum.setTitle(lis_mutual_fund.get(j).getScheme_name());
                                    Log.d("SCEEM_NAME", lis_mutual_fund.get(j).getScheme_type());
                                    ips_sum.setCode(lis_mutual_fund.get(j).getScheme_code());
                                    ips_sum.setTarget_value(target_val);
                                    Log.d("TorVal", target_val+"");
                                    ips_sum.setCurrent_value(current_val);
                                    ips_sum.setStart_date_s(start_date);
                                    ips_sum.setEnd_date_s(end_date);
                                    list_ips_sum.add(ips_sum);

                                }
                            }
                        }
                    }

                } else if (key_name.equalsIgnoreCase("debt_goals")) {
                    IPS_Sum ips_sum = new IPS_Sum();
                    if (results.getDebt_recommended_products().getMutual_funds().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {
                            ArrayList<IPS_Mutual_found> lis_mutual_fund = results.getDebt_recommended_products().getMutual_funds();
                            current_val = (Double.parseDouble(results.getComm_current_value()));
                            target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));
                            Log.d("Mutual_tarVal", target_val + "");

                            if (lis_mutual_fund.size() != 0) {
                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Mutual_found ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getScheme_name());
                                    //current_val = (Double.parseDouble(ips_mutual_found.getMin_invst_amt()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val);
                                    start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                                    ips_sum.setTitle(lis_mutual_fund.get(j).getScheme_name());
                                    //list_ips_sum.add(ips_sum);
                                }
                            }
                        }

                    }

                        if (results.getDebt_available_products().getMutual_funds().size() != 0) {
                            if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {
                                ArrayList<IPS_Mutual_found> lis_mutual_fund = results.getDebt_recommended_products().getMutual_funds();
                                current_val = (Double.parseDouble(results.getComm_current_value()));
                                target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));
                                Log.d("Mutual_tarVal", target_val + "");

                                if (lis_mutual_fund.size() != 0) {
                                    for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                        IPS_Mutual_found ips_mutual_found = lis_mutual_fund.get(j);
                                        Log.d("Check 1", "2");
                                        mutual_fund.add(ips_mutual_found.getScheme_name());
                                        //current_val = (Double.parseDouble(ips_mutual_found.getMin_invst_amt()));
                                        //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                        mutual_fund_fv.add(target_val);
                                        mutual_fund_cv.add(current_val);
                                        start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                        //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                        end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                        //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                                        ips_sum.setTitle("Financial");

                                    }
                                }
                            }

                        }


                    Log.d("getBank().size()-->", results.getDebt_recommended_products().getBank().size() + "");

                    if (results.getDebt_recommended_products().getBank().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Bank")) {
                            ArrayList<IPS_Bank> lis_mutual_fund = results.getDebt_recommended_products().getBank();
                            if (lis_mutual_fund.size() != 0) {

                                current_val = (Double.parseDouble(results.getComm_current_value()));
                                target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));

                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Bank ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getProduct_name());
                                    // current_val = (Double.parseDouble(ips_mutual_found.getEffective_interest()));
                                    // target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val);
                                    start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                                }
                            }


                        }

                    }
                    if (results.getDebt_recommended_products().getPost_office().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Post Office")) {
                            ArrayList<IPS_Postoffice> lis_mutual_fund = results.getDebt_recommended_products().getPost_office();
                            current_val = (Double.parseDouble(results.getComm_current_value()));
                            target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));

                            if (lis_mutual_fund.size() != 0) {
                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Postoffice ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getAsset_class());
                                    //current_val = (Double.parseDouble(ips_mutual_found.getEffective_interest()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val); start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                                }
                            }
                        }

                    }
                    list_ips_sum.add(ips_sum);

                } else if (key_name.equalsIgnoreCase("liquid_goals")) {
                    if (results.getLiquid_recommended_products().getMutual_funds().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {

                            ArrayList<IPS_Mutual_found> lis_mutual_fund = results.getLiquid_recommended_products().getMutual_funds();

                            if (lis_mutual_fund.size() != 0) {
                                current_val = (Double.parseDouble(results.getComm_current_value()));
                                target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));

                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Mutual_found ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getScheme_name());
                                    // current_val = (Double.parseDouble(ips_mutual_found.getMin_invst_amt()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val); start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                                }
                            }
                        }
                    }
                    Log.d("getBank().size()-->", results.getLiquid_recommended_products().getBank().size() + "");

                    if (results.getLiquid_recommended_products().getBank().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Bank")) {
                            ArrayList<IPS_Bank> lis_mutual_fund = results.getLiquid_recommended_products().getBank();
                            if (lis_mutual_fund.size() != 0) {
                                current_val = (Double.parseDouble(results.getComm_current_value()));
                                target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));

                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Bank ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getProduct_name());
                                    //current_val = (Double.parseDouble(ips_mutual_found.getEffective_interest()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val); start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));


                                }
                            }


                        }

                    }
                    if (results.getDebt_available_products().getBank().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Bank")) {
                            ArrayList<IPS_Bank> lis_mutual_fund = results.getLiquid_recommended_products().getBank();
                            if (lis_mutual_fund.size() != 0) {
                                current_val = (Double.parseDouble(results.getComm_current_value()));
                                target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));

                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Bank ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getProduct_name());
                                    //current_val = (Double.parseDouble(ips_mutual_found.getEffective_interest()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val); start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));


                                }
                            }


                        }

                    }
                    if (results.getLiquid_recommended_products().getPost_office().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Post Office")) {

                            ArrayList<IPS_Postoffice> lis_mutual_fund = results.getLiquid_recommended_products().getPost_office();

                            if (lis_mutual_fund.size() != 0) {
                                current_val = (Double.parseDouble(results.getComm_current_value()));
                                target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));

                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Postoffice ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getAsset_class());
                                    // current_val = (Double.parseDouble(ips_mutual_found.getEffective_interest()));
                                    // target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val); start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                                }
                            }
                        }


                    }

                } else if (key_name.equalsIgnoreCase("comm_goals")) {
                    if (results.getComm_recommended_products().getMutual_funds().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {

                            ArrayList<IPS_Mutual_found> lis_mutual_fund = results.getComm_recommended_products().getMutual_funds();

                            if (lis_mutual_fund.size() != 0) {
                                current_val = (Double.parseDouble(results.getComm_current_value()));
                                target_val = (Double.parseDouble(results.getComm_fv()) * Double.parseDouble(results.getComm_per()));

                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Mutual_found ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getScheme_name());
                                    // current_val = (Double.parseDouble(ips_mutual_found.getMin_invst_amt()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val); start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                                }
                            }
                        }
                    }

                    if (results.getComm_recommended_products().getBank().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Bank")) {
                            ArrayList<IPS_Bank> lis_mutual_fund = results.getComm_recommended_products().getBank();
                            if (lis_mutual_fund.size() != 0) {
                                current_val = (Double.parseDouble(results.getComm_current_value()));
                                target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));

                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Bank ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getProduct_name());
                                    //current_val = (Double.parseDouble(ips_mutual_found.getEffective_interest()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val); start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));


                                }
                            }


                        }

                    }
                    if (results.getComm_recommended_products().getPost_office().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Post Office")) {

                            ArrayList<IPS_Postoffice> lis_mutual_fund = results.getComm_recommended_products().getPost_office();

                            if (lis_mutual_fund.size() != 0) {
                                current_val = (Double.parseDouble(results.getComm_current_value()));
                                target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));

                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Postoffice ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getAsset_class());
                                    //current_val = (Double.parseDouble(ips_mutual_found.getEffective_interest()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val); start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                                }
                            }
                        }

                    }

                } else if (key_name.equalsIgnoreCase("real_estate_goals")) {
                    if (results.getReal_estate_recommended_products().getMutual_funds().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {

                            ArrayList<IPS_Mutual_found> lis_mutual_fund = results.getReal_estate_recommended_products().getMutual_funds();

                            if (lis_mutual_fund.size() != 0) {
                                current_val = (Double.parseDouble(results.getComm_current_value()));
                                target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));

                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Mutual_found ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getScheme_name());
                                    //current_val = (Double.parseDouble(ips_mutual_found.getMin_invst_amt()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val); start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                                }
                            }
                        }
                    }
                    if (results.getReal_estate_recommended_products().getBank().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Bank")) {
                            ArrayList<IPS_Bank> lis_mutual_fund = results.getReal_estate_recommended_products().getBank();
                            if (lis_mutual_fund.size() != 0) {
                                current_val = (Double.parseDouble(results.getComm_current_value()));
                                target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));

                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Bank ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getProduct_name());
                                    //current_val = (Double.parseDouble(ips_mutual_found.getEffective_interest()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val); start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));


                                }
                            }


                        }

                    }
                    if (results.getReal_estate_recommended_products().getPost_office().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Post Office")) {
                            ArrayList<IPS_Postoffice> lis_mutual_fund = results.getReal_estate_recommended_products().getPost_office();
                            if (lis_mutual_fund.size() != 0) {
                                current_val = (Double.parseDouble(results.getComm_current_value()));
                                target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));

                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Postoffice ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getAsset_class());
                                    current_val = (Double.parseDouble(ips_mutual_found.getEffective_interest()));
                                    target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val); start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_start_datetime()))));
                                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(results.getGoal_end_datetime()))));
                                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));

                                }
                            }
                        }

                    }

                }
            }


            mutual_ctotal = calculate_val(mutual_fund_cv);
            mutual_ftotal = calculate_val(mutual_fund_fv);
            Log.d("vkss", mutual_fund_fv+"");
            Log.e("vks", calculate_val(mutual_fund_fv)+"");
            bank_ctotal = calculate_val(bank_fund_cv);
            bank_ftotal = calculate_val(bank_fund_fv);
            post_ctotal = calculate_val(post_fund_cv);
            post_ftotal = calculate_val(post_fund_fv);

            IPS_Sum ips_sum = new IPS_Sum();
            System.out.println("0" + mutual_fund_code.size() + "");
            if (!merge_string(mutual_fund).equals("")) {
                for (int i = 0; i < mutual_fund_code.size(); i++) {
                    //merge_string_code(mutual_fund_code.get(i), mutual_ctotal, mutual_ftotal, mutual_fund_code);
                }
                ips_sum.setTitle(merge_string(mutual_fund));
                // ips_sum.setTitle(merge_string_code(mutual_fund_code));
                ips_sum.setTarget_value(mutual_ftotal);
                Log.d("Viswa_check_total", mutual_ftotal+"");
                ips_sum.setCurrent_value(mutual_ctotal);
                ips_sum.setStart_date_s(start_date);
                ips_sum.setEnd_date_s(end_date);
                list_ips_sum.add(ips_sum);
                System.out.println("SIZE 3:" + list_ips_sum.size());

            }
            ///

            System.out.println("CODE SIZE bank_fund:" + bank_fund.size() + "");

            if (!merge_string(bank_fund).equals("")) {
                ips_sum = new IPS_Sum();
                ips_sum.setTitle(merge_string(bank_fund));
                ips_sum.setTarget_value(bank_ftotal);
                ips_sum.setCurrent_value(bank_ctotal); ips_sum.setStart_date_s(start_date);
                ips_sum.setEnd_date_s(end_date);
                list_ips_sum.add(ips_sum);
                System.out.println("SIZE 4:" + list_ips_sum.size());

            }
            if (!merge_string(post_fund).equals("")) {

                ips_sum = new IPS_Sum();
                ips_sum.setTitle(merge_string(post_fund));
                ips_sum.setTarget_value(post_ftotal);
                ips_sum.setCurrent_value(post_ctotal); ips_sum.setStart_date_s(start_date);
                ips_sum.setEnd_date_s(end_date);
                list_ips_sum.add(ips_sum);
                System.out.println("SIZE 5:" + list_ips_sum.size());

            }

            // Set<String> set = new HashSet<>(mutual_fund);
            // mutual_fund.clear();
            // mutual_fund.addAll(set);
            System.out.println("SIZE 6:" + list_ips_sum.size());


            for (int i = 0; i < list_ips_sum.size(); i++) {

                for (int j = i + 1; j < list_ips_sum.size(); j++) {
                    if (list_ips_sum.get(i).getCode().equals(list_ips_sum.get(j).getCode())) {
                        list_ips_sum.remove(j);
                        j--;
                    }
                }
            }
            Log.e("Rajasekar:-->>", "Hello");
            adapter = new InvestmentPlanningAdapterleval(list_ips_sum);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
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
        Log.d("final", final_result+"");

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
        System.out.println("merge_string" + final_result);
        return final_result.toString();
    }

    private ArrayList<IPS_Sum> merge_string_code(ArrayList<IPS_Sum> list) {
        try {
            int len = list.size();
            for (int i = 0; i < list.size(); i++) {

                for (int j = i + 1; j < list.size(); j++) {
                    System.out.println(list.size() + "if->" + list.get(i).getTitle() + ":" + list.get(j).getTitle());
                    if (list.get(i).getTitle().equals(list.get(j).getTitle())) {
                        list.remove(j);
                        j--;
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
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
                    args.putString("Level", "Level2");
                    args.putString("key_name", key_name);
                    args.putString("key_name_sub", key_name_sub);
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

        private InvestmentPlanningAdapterleval(ArrayList<IPS_Sum> ivp) {
            this.arrayList_data = ivp;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView text_group_name, text_anual_value, text_current_value, text_target_value, text_start_date,
                    text_end_date;
            LinearLayout linear_goal_name;

            public ViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                text_group_name = view.findViewById(R.id.text_group_name);
                text_current_value = view.findViewById(R.id.text_current_value);
                text_target_value = view.findViewById(R.id.text_target_value);
                text_anual_value = view.findViewById(R.id.text_anual_value);
                text_start_date = view.findViewById(R.id.text_start_date);
                text_end_date = view.findViewById(R.id.text_end_date);
                linear_goal_name = view.findViewById(R.id.linear_goal_name);

            }
        }

        @Override
        public InvestmentPlanningAdapterleval.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.investment_plan_adapterleval1, parent, false);
            InvestmentPlanningAdapterleval.ViewHolder viewHolder = new InvestmentPlanningAdapterleval.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(InvestmentPlanningAdapterleval.ViewHolder holder, int position) {

            IPS_Sum data = arrayList_data.get(position);
            holder.text_group_name.setText(data.getTitle());
            //Log.d("Title_check", plan.getData().getResults().get(position).getDebt_available_products().getMutual_funds().get(position).getScheme_type());

            BigDecimal target_value = BigDecimal.ONE;
            BigDecimal current_value = BigDecimal.ONE;
            BigDecimal annual_value = BigDecimal.ONE;

            annual_value = new BigDecimal(arrayList_data.get(position).getAnnual_con());
            current_value = new BigDecimal(arrayList_data.get(position).getCurrent_value());
           // target_value = new BigDecimal((arrayList_data.get(position).getTarget_value() / arrayList_data.get(position).getDiv()));
            target_value = new BigDecimal((values / adapter.getItemCount()));
            Log.e("calculation", arrayList_data.get(position).getTarget_value()+":/"+arrayList_data.get(position).getDiv());


            holder.text_target_value.setText(UtileKit.formatedNumbers_decimal(target_value));
            Log.e("value", UtileKit.formatedNumbers_decimal(target_value));
            holder.text_current_value.setText(UtileKit.formatedNumbers_decimal(current_value));
            Log.e("value1", UtileKit.formatedNumbers_decimal(annual_value));
            holder.text_anual_value.setText(UtileKit.formatedNumbers_decimal(annual_value));
            holder.text_start_date.setText(UtileKit.get_start_date(arrayList_data.get(position).getStart_date_s()));
            Log.e("value2", UtileKit.get_start_date(arrayList_data.get(position).getStart_date_s()));
            holder.text_end_date.setText(UtileKit.get_end_date(arrayList_data.get(position).getStart_date_s()));

            //holder.text_target_value.setText(arrayList_data.get(position).getTarget_value());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putSerializable("InvestmentPlan", investmentPlans);
                    //key_name = getArguments().getString("key_name");
                    args.putString("key_name", key_name);
                    args.putString("sub_key_name", key_name_sub);
                    args.putString("indicate", indicate);
                    Log.d("sub_key_name:", key_name_sub + "");
                    InverstmentDetails fragment = new InverstmentDetails();
                    fragment.setArguments(args);
                    addFragmenttoStack(fragment);
                }
            });
        }

        @Override
        public int getItemCount() {

            return arrayList_data.size();

        }
    }

}

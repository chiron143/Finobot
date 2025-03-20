package com.purplepath.purplepath.investmentPlan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.investmentPlan.models.IPS_Bank;
import com.purplepath.purplepath.investmentPlan.models.IPS_Income_details;
import com.purplepath.purplepath.investmentPlan.models.IPS_Mutual_found;
import com.purplepath.purplepath.investmentPlan.models.IPS_Postoffice;
import com.purplepath.purplepath.investmentPlan.models.IPS_Results;
import com.purplepath.purplepath.investmentPlan.models.IPS_Sum;
import com.purplepath.purplepath.investmentPlan.models.InvestmentPlan;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.recommendation.getgoalmodel.Goalmodel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rajasekar b on 27/02/2019.
 */
public class InverstmentPalanStatementView extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private RecyclerView recyclerView;
    private OnActivityBackPressedListener mCallBackListener;
    private InvestmentPlanningAdapter adapter;
    private CustomSpinerAdapters adapter_state;
    private Goalmodel goalmodel;
    private LinearLayout linear_parent;
    private InvestmentPlan investmentPlan;
    private String indicate = "", key_name_sub = "", key_name = "";
    private String Level = "";
    private ArrayList<IPS_Results> arrayList_data;
    ArrayList<IPS_Sum> list_ips_sum = new ArrayList<>();
    public boolean SPINNER_CHECK = false;

    private BigDecimal per1year;
    private BigDecimal per1month;
    private BigDecimal pvals;


    String per1year2 = "", per1month2 = "", pvals2 = "";


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


        return inflater.inflate(R.layout.fragment_inverstment_planing_statement, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallBackListener.setActionBarTitle("Investment Planing");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        recyclerView = view.findViewById(R.id.recyclerView);
        linear_parent = view.findViewById(R.id.linear_parent);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        try {
            if (null != getArguments()) {
                Log.d("getArguments:", getArguments() + "");
                if (getArguments().containsKey("InvestmentPlan")) {

                    investmentPlan = (InvestmentPlan) getArguments().getSerializable("InvestmentPlan");
                    Level = getArguments().getString("Level");
                    key_name = getArguments().getString("key_name");
                    key_name_sub = getArguments().getString("key_name_sub");
                    indicate = getArguments().getString("indicate");


                    Log.d("load data 1:", investmentPlan.getData().getMessage());
                } else {
                    Log.d("load data 2:", investmentPlan.getData().getMessage());
                }

                if (Level.equalsIgnoreCase("Level")) {
                    load_data();

                } else if (Level.equalsIgnoreCase("Level1")) {
                    load_data_leval();

                } else if (Level.equalsIgnoreCase("Level2")) {
                    load_data_leval2();

                }
            } else {
                Log.d("getArguments else:", getArguments() + "");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //callAllInsuranceService();
        //callInvestmentService();

    }

    private void load_data_leval() {
        try {
            Log.d("load data:", investmentPlan.getData().getMessage());
            arrayList_data = investmentPlan.getData().getGoals_with_asset_class().get(key_name);

            int array_size = arrayList_data.size();

            ArrayList<String> mutual_fund = new ArrayList<>();
            ArrayList<String> bank_fund = new ArrayList<>();
            ArrayList<String> post_fund = new ArrayList<>();
            ArrayList<Double> mutual_fund_cv = new ArrayList<>();
            ArrayList<Double> mutual_fund_fv = new ArrayList<>();
            ArrayList<Double> mutual_fund_ac = new ArrayList<>();
            ArrayList<Integer> mutual_fund_div = new ArrayList<>();
            ArrayList<Double> bank_fund_cv = new ArrayList<>();
            ArrayList<Double> bank_fund_fv = new ArrayList<>();
            ArrayList<Integer> bank_fund_div = new ArrayList<>();
            ArrayList<Double> bank_fund_ac = new ArrayList<>();
            ArrayList<Double> post_fund_cv = new ArrayList<>();
            ArrayList<Double> post_fund_fv = new ArrayList<>();
            ArrayList<Integer> post_fund_div = new ArrayList<>();
            ArrayList<Double> post_fund_ac = new ArrayList<>();

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

            IPS_Income_details income_details = investmentPlan.getData().getIncome_details();

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
                        mutual_fund_fv.add(target_val);
                        mutual_fund_cv.add(current_val);
                        mutual_fund_div.add(1);
                        mutual_fund_ac.add(Double.parseDouble(results.getEquity_annual_contr()));

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

                    }
                    if (results.getDebt_recommended_products().getBank().size() != 0) {
                        bank_fund.add("Bank");
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));

                        bank_fund_fv.add(target_val);
                        bank_fund_cv.add(current_val);
                        bank_fund_div.add(1);
                        bank_fund_ac.add(Double.parseDouble(results.getFixed_inc_annual_contr()));

                    }
                    if (results.getDebt_recommended_products().getPost_office().size() != 0) {
                        post_fund.add("Post Office");
                        target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_cv.add(current_val);
                        post_fund_fv.add(target_val);
                        post_fund_div.add(1);
                        post_fund_ac.add(Double.parseDouble(results.getFixed_inc_annual_contr()));

                    }

                } else if (key_name.equalsIgnoreCase("liquid_goals")) {
                    if (results.getLiquid_recommended_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund.add("Mutual Fund");
                        target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        mutual_fund_cv.add(current_val);
                        mutual_fund_fv.add(target_val);
                        mutual_fund_div.add(1);
                        mutual_fund_ac.add(Double.parseDouble(results.getLiquid_annual_contr()));

                    }
                    if (results.getLiquid_recommended_products().getBank().size() != 0) {
                        bank_fund.add("Bank");
                        target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));

                        bank_fund_cv.add(current_val);
                        bank_fund_fv.add(target_val);
                        bank_fund_div.add(1);
                        bank_fund_ac.add(Double.parseDouble(results.getLiquid_annual_contr()));

                    }
                    if (results.getLiquid_recommended_products().getPost_office().size() != 0) {
                        post_fund.add("Post Office");
                        target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_fv.add(target_val);
                        post_fund_cv.add(current_val);
                        post_fund_div.add(1);
                        post_fund_ac.add(Double.parseDouble(results.getLiquid_annual_contr()));

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

                    }
                    if (results.getComm_recommended_products().getBank().size() != 0) {
                        bank_fund.add("Bank");
                        target_val = (Double.parseDouble(results.getComm_fv()) * Double.parseDouble(results.getComm_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        bank_fund_cv.add(current_val);
                        bank_fund_fv.add(target_val);
                        bank_fund_div.add(1);
                        bank_fund_ac.add(Double.parseDouble(results.getComm_annual_contr()));

                    }
                    if (results.getComm_recommended_products().getPost_office().size() != 0) {
                        post_fund.add("Post Office");
                        target_val = (Double.parseDouble(results.getComm_fv()) * Double.parseDouble(results.getComm_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_fv.add(target_val);
                        post_fund_cv.add(current_val);
                        post_fund_div.add(1);
                        post_fund_ac.add(Double.parseDouble(results.getComm_annual_contr()));

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

                    }
                    if (results.getReal_estate_recommended_products().getBank().size() != 0) {
                        bank_fund.add("Bank");
                        target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        bank_fund_cv.add(current_val);
                        bank_fund_fv.add(target_val);
                        bank_fund_div.add(1);
                        bank_fund_ac.add(Double.parseDouble(results.getReal_estate_annual_contr()));

                    }
                    if (results.getReal_estate_recommended_products().getPost_office().size() != 0) {
                        post_fund.add("Post Office");
                        target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_cv.add(current_val);
                        post_fund_fv.add(target_val);
                        post_fund_div.add(1);
                        post_fund_ac.add(Double.parseDouble(results.getReal_estate_annual_contr()));

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
                        mutual_fund.add("Mutual Fund");
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                        mutual_fund_fv.add(target_val);
                        mutual_fund_cv.add(current_val);
                        mutual_fund_div.add(1);
                        mutual_fund_ac.add(Double.parseDouble(results.getEquity_annual_contr()));

                    }
                    if (results.getEquity_available_products().getBank().size() > 0) {
                        //mutual_fund.add("Mutual Fund");
                    }

                } else if (key_name.equalsIgnoreCase("debt_goals")) {
                    if (results.getDebt_available_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund.add("Mutual Fund");
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));
                        mutual_fund_cv.add(current_val);
                        mutual_fund_fv.add(target_val);
                        mutual_fund_div.add(1);
                        mutual_fund_ac.add(Double.parseDouble(results.getFixed_inc_annual_contr()));

                    }
                    if (results.getDebt_available_products().getBank().size() != 0) {
                        bank_fund.add("Bank");
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));

                        bank_fund_fv.add(target_val);
                        bank_fund_cv.add(current_val);
                        bank_fund_div.add(1);
                        bank_fund_ac.add(Double.parseDouble(results.getFixed_inc_annual_contr()));

                    }
                    if (results.getDebt_available_products().getPost_office().size() != 0) {
                        post_fund.add("Post Office");
                        target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_cv.add(current_val);
                        post_fund_fv.add(target_val);
                        post_fund_div.add(1);
                        post_fund_ac.add(Double.parseDouble(results.getFixed_inc_annual_contr()));

                    }

                } else if (key_name.equalsIgnoreCase("liquid_goals")) {
                    if (results.getLiquid_available_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund.add("Mutual Fund");
                        target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        mutual_fund_cv.add(current_val);
                        mutual_fund_fv.add(target_val);
                        mutual_fund_div.add(1);
                        mutual_fund_ac.add(Double.parseDouble(results.getLiquid_annual_contr()));

                    }
                    if (results.getLiquid_available_products().getBank().size() != 0) {
                        bank_fund.add("Bank");
                        target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));

                        bank_fund_cv.add(current_val);
                        bank_fund_fv.add(target_val);
                        bank_fund_div.add(1);
                        bank_fund_ac.add(Double.parseDouble(results.getLiquid_annual_contr()));

                    }
                    if (results.getLiquid_available_products().getPost_office().size() != 0) {
                        post_fund.add("Post Office");
                        target_val = (Double.parseDouble(results.getLiquid_fv()) * Double.parseDouble(results.getLiquid_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_fv.add(target_val);
                        post_fund_cv.add(current_val);
                        post_fund_div.add(1);
                        post_fund_ac.add(Double.parseDouble(results.getLiquid_annual_contr()));

                    }

                } else if (key_name.equalsIgnoreCase("comm_goals")) {
                    if (results.getComm_available_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund.add("Mutual Fund");
                        target_val = (Double.parseDouble(results.getComm_fv()) * Double.parseDouble(results.getComm_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        mutual_fund_fv.add(target_val);
                        mutual_fund_cv.add(current_val);
                        mutual_fund_div.add(1);
                        mutual_fund_ac.add(Double.parseDouble(results.getComm_annual_contr()));

                    }
                    if (results.getComm_available_products().getBank().size() != 0) {
                        bank_fund.add("Bank");
                        target_val = (Double.parseDouble(results.getComm_fv()) * Double.parseDouble(results.getComm_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        bank_fund_cv.add(current_val);
                        bank_fund_fv.add(target_val);
                        bank_fund_div.add(1);
                        bank_fund_ac.add(Double.parseDouble(results.getComm_annual_contr()));

                    }
                    if (results.getComm_available_products().getPost_office().size() != 0) {
                        post_fund.add("Post Office");
                        target_val = (Double.parseDouble(results.getComm_fv()) * Double.parseDouble(results.getComm_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_fv.add(target_val);
                        post_fund_cv.add(current_val);
                        post_fund_div.add(1);
                        post_fund_ac.add(Double.parseDouble(results.getComm_annual_contr()));

                    }

                } else if (key_name.equalsIgnoreCase("real_estate_goals")) {
                    if (results.getReal_estate_available_products().getMutual_funds().size() != 0) {
                        Log.d("Check 1", "2");
                        mutual_fund.add("Mutual Fund");
                        target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        mutual_fund_fv.add(target_val);
                        mutual_fund_cv.add(current_val);
                        mutual_fund_div.add(1);
                        mutual_fund_ac.add(Double.parseDouble(results.getReal_estate_annual_contr()));

                    }
                    if (results.getReal_estate_available_products().getBank().size() != 0) {
                        bank_fund.add("Bank");
                        target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        bank_fund_cv.add(current_val);
                        bank_fund_fv.add(target_val);
                        bank_fund_div.add(1);
                        bank_fund_ac.add(Double.parseDouble(results.getReal_estate_annual_contr()));

                    }
                    if (results.getReal_estate_available_products().getPost_office().size() != 0) {
                        post_fund.add("Post Office");
                        target_val = (Double.parseDouble(results.getReal_estate_fv()) * Double.parseDouble(results.getReal_estate_per()));
                        current_val = (Double.parseDouble(results.getComm_current_value()));
                        post_fund_cv.add(current_val);
                        post_fund_fv.add(target_val);
                        post_fund_div.add(1);
                        post_fund_ac.add(Double.parseDouble(results.getReal_estate_annual_contr()));

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
                ips_sum.setCurrent_value(mutual_ctotal);

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
            if (!merge_string(bank_fund).equals("")) {

                ips_sum = new IPS_Sum();
                ips_sum.setTitle(merge_string(bank_fund));
                ips_sum.setTarget_value(bank_ftotal);
                ips_sum.setCurrent_value(bank_ctotal);
                ips_sum.setDiv(bank_div);

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
                ips_sum.setCurrent_value(post_ctotal);
                ips_sum.setDiv(post_div);
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

            adapter = new InvestmentPlanningAdapter(list_ips_sum);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void redirect() {
        try {
            System.gc();
            System.out.println("redirect key_name :" + key_name);
            if (Level.equalsIgnoreCase("Level")) {
                Level = "Level1";

                load_data_leval();
            } else if (Level.equalsIgnoreCase("Level1")) {
                Level = "Level2";

                load_data_leval2();

            } else if (Level.equalsIgnoreCase("Level2")) {
                Level = "Level3";

                // load_data_leval2();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load_data() {
        try {
            ArrayList<String> list_mainGroup = investmentPlan.getData().getAsset_classes();
            int len = list_mainGroup.size();
            ArrayList<IPS_Sum> list_sum_of = new ArrayList<>();
            for (int i = 0; i < len; i++) {
                String key = list_mainGroup.get(i);
                ArrayList<IPS_Results> arrayList_data = investmentPlan.getData().getGoals_with_asset_class().get(key);
                int len_sub = arrayList_data.size();
                IPS_Sum ips_sum = new IPS_Sum();
                double current_value = 0;
                double target_value = 0;
                double annual_con = 0;
                ArrayList<Integer> start_date = new ArrayList<>();
                ArrayList<String> list_start_date = new ArrayList<>();
                ArrayList<Integer> end_date = new ArrayList<>();
                ArrayList<String> list_end_date = new ArrayList<>();
                for (int j = 0; j < len_sub; j++) {
                    IPS_Results ipsResults = arrayList_data.get(j);
                    current_value += Double.parseDouble(ipsResults.getComm_current_value());

                    if (key.equalsIgnoreCase("equity_goals")) {
                        target_value += (Double.parseDouble(ipsResults.getEquity_fv()) * Double.parseDouble(ipsResults.getEquity_per()));
                        System.out.println(key + "-> REF:" + (Double.parseDouble(ipsResults.getEquity_fv()) * Double.parseDouble(ipsResults.getEquity_per())));
                        annual_con += Double.parseDouble(ipsResults.getEquity_annual_contr());

                    } else if (key.equalsIgnoreCase("debt_goals")) {
                        annual_con += Double.parseDouble(ipsResults.getDebt_annu_due_pv());
                        target_value += (Double.parseDouble(ipsResults.getDebt_fv()) * Double.parseDouble(ipsResults.getDebt_per()));
                        System.out.println(key + "-> REF:" + ((Double.parseDouble(ipsResults.getDebt_fv()) * Double.parseDouble(ipsResults.getDebt_per()))));

                    } else if (key.equalsIgnoreCase("liquid_goals")) {
                        target_value += (Double.parseDouble(ipsResults.getLiquid_fv()) * Double.parseDouble(ipsResults.getLiquid_per()));
                        System.out.println(key + "-> REF:" + ((Double.parseDouble(ipsResults.getLiquid_fv()) * Double.parseDouble(ipsResults.getLiquid_per()))));
                        annual_con += Double.parseDouble(ipsResults.getLiquid_annual_contr());

                    } else if (key.equalsIgnoreCase("comm_goals")) {
                        target_value += (Double.parseDouble(ipsResults.getComm_fv()) * Double.parseDouble(ipsResults.getComm_per()));
                        System.out.println(key + "-> REF:" + (Double.parseDouble(ipsResults.getComm_fv()) * Double.parseDouble(ipsResults.getComm_per())));
                        annual_con += Double.parseDouble(ipsResults.getComm_annual_contr());

                    } else if (key.equalsIgnoreCase("real_estate_goals")) {
                        annual_con += Double.parseDouble(ipsResults.getReal_estate_annual_contr());
                        target_value += (Double.parseDouble(ipsResults.getReal_estate_fv()) * Double.parseDouble(ipsResults.getReal_estate_per()));
                        System.out.println(key + "-> REF:" + (Double.parseDouble(ipsResults.getReal_estate_fv()) * Double.parseDouble(ipsResults.getReal_estate_per())));
                    }

                    System.out.println(key + "-> T:" + target_value);
                    System.out.println(key + "-> C:" + current_value);

                    start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                    start_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                    list_start_date.add((String) DateFormat.format("yyyy-MM-dd", get_date_format(ipsResults.getGoal_end_datetime())));
                    list_end_date.add((String) DateFormat.format("yyyy-MM-dd", get_date_format(ipsResults.getGoal_end_datetime())));
                    //  list_end_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));
                }
                //System.out.println(Collections.max(list_start_date));
                //System.out.println(Collections.min(list_start_date));
                //Collections.sort(list_start_date);

                ips_sum.setTitle(key + "");
                ips_sum.setKey(key + "");
                ips_sum.setCurrent_value(current_value);
                ips_sum.setAnnual_con(annual_con);
                ips_sum.setTarget_value(target_value);
                ips_sum.setStart_date_s(start_date);
                ips_sum.setEnd_date_s(end_date);
                ips_sum.setDate_start(list_start_date);
                ips_sum.setDate_end(list_end_date);


                list_sum_of.add(ips_sum);
            }

            Log.d("load data:", investmentPlan.getData().getMessage());
            adapter = new InvestmentPlanningAdapter(list_sum_of);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
            linear_parent.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load_data_leval2() {
        try {

            Log.d("load data:", investmentPlan.getData().getMessage());
            int array_size = arrayList_data.size();

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
                                    //current_val = (Double.parseDouble(ips_mutual_found.getMin_invst_amt()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val);
                                    IPS_Sum ips_sum = new IPS_Sum();
                                    ips_sum.setTitle(lis_mutual_fund.get(j).getScheme_name());
                                    ips_sum.setCode(lis_mutual_fund.get(j).getScheme_code());
                                    ips_sum.setTarget_value(target_val);
                                    ips_sum.setCurrent_value(current_val);
                                    list_ips_sum.add(ips_sum);

                                }
                            }
                        }
                    }

                } else if (key_name.equalsIgnoreCase("debt_goals")) {
                    if (results.getDebt_recommended_products().getMutual_funds().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {
                            ArrayList<IPS_Mutual_found> lis_mutual_fund = results.getDebt_recommended_products().getMutual_funds();
                            current_val = (Double.parseDouble(results.getComm_current_value()));
                            target_val = (Double.parseDouble(results.getDebt_fv()) * Double.parseDouble(results.getDebt_per()));

                            if (lis_mutual_fund.size() != 0) {
                                for (int j = 0; j < lis_mutual_fund.size(); j++) {
                                    IPS_Mutual_found ips_mutual_found = lis_mutual_fund.get(j);
                                    Log.d("Check 1", "2");
                                    mutual_fund.add(ips_mutual_found.getScheme_name());
                                    //current_val = (Double.parseDouble(ips_mutual_found.getMin_invst_amt()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val);
                                }
                            }
                        }

                    }


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
                                    mutual_fund_cv.add(current_val);
                                }
                            }
                        }

                    }


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
                                    mutual_fund_cv.add(current_val);
                                }
                            }
                        }
                    }
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
                                    //current_val = (Double.parseDouble(ips_mutual_found.getEffective_interest()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val);

                                }
                            }


                        }

                    }
                    if (results.getDebt_recommended_products().getPost_office().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Post Office")) {

                            ArrayList<IPS_Postoffice> lis_mutual_fund = results.getDebt_recommended_products().getPost_office();

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
                                    mutual_fund_cv.add(current_val);
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
                                    mutual_fund_cv.add(current_val);
                                }
                            }
                        }
                    }

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
                                    //current_val = (Double.parseDouble(ips_mutual_found.getEffective_interest()));
                                    //target_val = (Double.parseDouble(results.getEquity_fv()) * Double.parseDouble(results.getEquity_per()));
                                    mutual_fund_fv.add(target_val);
                                    mutual_fund_cv.add(current_val);

                                }
                            }


                        }

                    }
                    if (results.getDebt_recommended_products().getPost_office().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Post Office")) {

                            ArrayList<IPS_Postoffice> lis_mutual_fund = results.getDebt_recommended_products().getPost_office();

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
                                    mutual_fund_cv.add(current_val);
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
                                    mutual_fund_cv.add(current_val);
                                }
                            }
                        }
                    }
                    if (results.getDebt_recommended_products().getBank().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Bank")) {
                            ArrayList<IPS_Bank> lis_mutual_fund = results.getDebt_recommended_products().getBank();
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
                                    mutual_fund_cv.add(current_val);

                                }
                            }


                        }

                    }
                    if (results.getDebt_recommended_products().getPost_office().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Post Office")) {
                            ArrayList<IPS_Postoffice> lis_mutual_fund = results.getDebt_recommended_products().getPost_office();
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
                                    mutual_fund_cv.add(current_val);
                                }
                            }
                        }

                    }

                }
            }


            mutual_ctotal = calculate_val(mutual_fund_cv);
            mutual_ftotal = calculate_val(mutual_fund_fv);
            bank_ctotal = calculate_val(bank_fund_cv);
            bank_ftotal = calculate_val(bank_fund_fv);
            post_ctotal = calculate_val(post_fund_cv);
            post_ftotal = calculate_val(post_fund_fv);

            IPS_Sum ips_sum = new IPS_Sum();
            System.out.println("CODE SIZE:" + mutual_fund_code.size() + "");
            if (!merge_string(mutual_fund).equals("")) {
                for (int i = 0; i < mutual_fund_code.size(); i++) {
                    //merge_string_code(mutual_fund_code.get(i), mutual_ctotal, mutual_ftotal, mutual_fund_code);
                }
                ips_sum.setTitle(merge_string(mutual_fund));
                // ips_sum.setTitle(merge_string_code(mutual_fund_code));
                ips_sum.setTarget_value(mutual_ftotal);
                ips_sum.setCurrent_value(mutual_ctotal);
                list_ips_sum.add(ips_sum);
                System.out.println("SIZE 3:" + list_ips_sum.size());

            }
            ///
            if (!merge_string(bank_fund).equals("")) {
                ips_sum = new IPS_Sum();
                ips_sum.setTitle(merge_string(bank_fund));
                ips_sum.setTarget_value(bank_ftotal);
                ips_sum.setCurrent_value(bank_ctotal);
                list_ips_sum.add(ips_sum);
                System.out.println("SIZE 4:" + list_ips_sum.size());

            }
            if (!merge_string(post_fund).equals("")) {

                ips_sum = new IPS_Sum();
                ips_sum.setTitle(merge_string(post_fund));
                ips_sum.setTarget_value(post_ftotal);
                ips_sum.setCurrent_value(post_ctotal);
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
            adapter = new InvestmentPlanningAdapter(list_ips_sum);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    addFragmenttoStack(new InverstmentPalans());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:
                try {
                    addFragmenttoStack(new InverstmentPalans());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public class InvestmentPlanningAdapter extends RecyclerView.Adapter<InvestmentPlanningAdapter.ViewHolder> {
        private ArrayList<IPS_Sum> ips_sum;
        public View view;
        private Context context;

        public InvestmentPlanningAdapter(ArrayList<IPS_Sum> ivp) {
            this.ips_sum = ivp;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView text_group_name, text_contribution, text_accumulation, text_duration, text_next;
            public Spinner spinner_data;

            public ViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                text_group_name = view.findViewById(R.id.text_group_name);
                text_contribution = view.findViewById(R.id.text_contribution);
                text_accumulation = view.findViewById(R.id.text_accumulation);
                text_duration = view.findViewById(R.id.text_duration);
                spinner_data = view.findViewById(R.id.spinner_data);
                text_next = view.findViewById(R.id.text_next);

            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.investment_planning_statement_view, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            try {
                if (Level.equalsIgnoreCase("Level")) {
                    if (ips_sum.get(position).getTitle().equalsIgnoreCase("equity_goals")) {
                        holder.text_group_name.setText("Equity");
                    } else if (ips_sum.get(position).getTitle().equalsIgnoreCase("debt_goals")) {
                        holder.text_group_name.setText("Debt");
                    } else if (ips_sum.get(position).getTitle().equalsIgnoreCase("liquid_goals")) {
                        holder.text_group_name.setText("Liquid");
                    } else if (ips_sum.get(position).getTitle().equalsIgnoreCase("comm_goals")) {
                        holder.text_group_name.setText("Commodity");
                    } else if (ips_sum.get(position).getTitle().equalsIgnoreCase("real_estate_goals")) {
                        holder.text_group_name.setText("Real Estate");
                    }
                } else {
                    holder.text_group_name.setText(ips_sum.get(position).getTitle());
                }

                BigDecimal target_value = BigDecimal.ZERO;
                BigDecimal current_value = BigDecimal.ZERO;
                BigDecimal anual_value = BigDecimal.ZERO;
                current_value = new BigDecimal(ips_sum.get(position).getCurrent_value() + "");
                target_value = new BigDecimal(ips_sum.get(position).getTarget_value() + "");
                anual_value = new BigDecimal(ips_sum.get(position).getAnnual_con() + "");
                holder.text_accumulation.setText(UtileKit.formatedNumbers_decimal(target_value));
                holder.text_contribution.setText(UtileKit.formatedNumbers_decimal(anual_value));
                setSpinnerAdapter(holder.spinner_data, mContext);
                //holder.text_duration.setText(ips_sum.get(position).getEnd_date_s().get(position).toString());

                System.out.println("Date Start" + ips_sum.get(position).getDate_start());
                System.out.println("Date End" + ips_sum.get(position).getDate_end());
                holder.spinner_data.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        SPINNER_CHECK = true;

                        return false;
                    }
                });
                holder.spinner_data.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (SPINNER_CHECK) {
                            holder.text_contribution.setText("154454545");
                            SPINNER_CHECK = false;
                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                if (Level.equalsIgnoreCase("Level2")) {
                    holder.text_next.setVisibility(View.INVISIBLE);
                } else {
                    holder.text_next.setVisibility(View.VISIBLE);

                }
                holder.text_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        key_name = ips_sum.get(position).getTitle();
                        key_name_sub = ips_sum.get(position).getTitle();

                        redirect();


                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {

            return ips_sum.size();

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


    public void setSpinnerAdapter(Spinner mMyMartialSpinner, Context mycontext) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("Lumpsum");
        stringList.add("Annually");
        stringList.add("Monthly");
        // for (String s : mystringList) {
        //    stringList.add(s);
        //}
        adapter_state = new CustomSpinerAdapters(mycontext, stringList);
        mMyMartialSpinner.setAdapter(adapter_state);
        mMyMartialSpinner.setGravity(Gravity.BOTTOM);
    }

    public class CustomSpinerAdapters extends ArrayAdapter<String> {

        LayoutInflater inflater;
        List<String> objects;

        public CustomSpinerAdapters(Context context, List<String> objects) {
            super(context, 0, objects);
            // TODO Auto-generated constructor stub
            this.objects = objects;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            View v;
            //  if (position == 0) {
            TextView tv = new TextView(getContext());
            //   tv.setHeight(0);
            //   tv.setVisibility(View.VISIBLE);
            //    v = tv;
            //}
            //  else {
            v = getCustomView(position, convertView, parent);
            // }
            return v;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            View mySpinner = inflater.inflate(R.layout.spinner_invs, parent,
                    false);

            TextView spinnerTxtView = mySpinner.findViewById(android.R.id.text1);
            try {
                spinnerTxtView.setText(objects.get(position));
//            Log.i("CustomSpinerAdapter", "CustomSpinerAdapter" + objects.get(position));
//        DisplayMetrics metrics = parent.getResources().getDisplayMetrics();
//        float dp = 5f;
//        float fpixels = metrics.density * dp;
//        int pixels = (int) (fpixels + 0.5f);
//
//        spinnerTxtView.setHeight(pixels);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return mySpinner;
        }


    }

    private void spinner_data() {
        try {


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

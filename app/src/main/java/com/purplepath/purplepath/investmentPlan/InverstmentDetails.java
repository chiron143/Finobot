package com.purplepath.purplepath.investmentPlan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by rajasekar b on 27/02/2019.
 */
public class InverstmentDetails extends BaseFragment implements View.OnClickListener {

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
    InvestmentPlanning_Adapterleval_bank adapter_bank;
    InvestmentPlanning_Adapterleval_post_office adapter_post_office;
    private TextView text_header;
    String per1year2 = "", per1month2 = "", pvals2 = "", indicate = "", key_name_sub = "", key_name = "";

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

            if (null != getArguments()) {
                Log.d("getArguments:", getArguments() + "");
                if (getArguments().containsKey("InvestmentPlan")) {
                    investmentPlans = (InvestmentPlan) getArguments().getSerializable("InvestmentPlan");
                    key_name = getArguments().getString("key_name");
                    key_name_sub = getArguments().getString("sub_key_name");
                    indicate = getArguments().getString("indicate");
                    Log.d("sub_key_name 2: ", key_name_sub + "");
                    arrayList_data = investmentPlans.getData().getGoals_with_asset_class().get(key_name);
                    Log.d("load data 1:", investmentPlans.getData().getMessage());
                } else {
                    Log.d("load data 2:", investmentPlans.getData().getMessage());
                }

                text_header.setText(indicate + "->" + key_name_sub);
            }
            Log.d("load data:", investmentPlans.getData().getMessage());
            int array_size = arrayList_data.size();
            ArrayList<IPS_Mutual_found> list_mutual_fund = new ArrayList<>();
            ArrayList<IPS_Bank> list_bank_fund = new ArrayList<>();
            ArrayList<IPS_Postoffice> list_post_fund = new ArrayList<>();

            for (int i = 0; i < array_size; i++) {
                IPS_Results results = arrayList_data.get(i);
                Log.d("Check 1", "1");
                Log.d("Check 1", key_name + "");
                if (key_name.equalsIgnoreCase("equity_goals")) {
                    Log.d("Check 1", "2");
                    if (results.getEquity_recommended_products().getMutual_funds().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {
                            list_mutual_fund = results.getEquity_recommended_products().getMutual_funds();

                        }
                    }

                } else if (key_name.equalsIgnoreCase("debt_goals")) {
                    if (results.getDebt_recommended_products().getMutual_funds().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {

                            list_mutual_fund = results.getDebt_recommended_products().getMutual_funds();

                        }

                    }


                    if (results.getDebt_recommended_products().getBank().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Bank")) {
                            list_bank_fund = results.getDebt_recommended_products().getBank();


                        }

                    }
                    if (results.getDebt_recommended_products().getPost_office().size() != 0) {
                        list_post_fund = results.getDebt_recommended_products().getPost_office();

                    }


                } else if (key_name.equalsIgnoreCase("liquid_goals")) {
                    if (results.getLiquid_recommended_products().getMutual_funds().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {

                            list_mutual_fund = results.getLiquid_recommended_products().getMutual_funds();

                        }
                    }
                    if (results.getLiquid_recommended_products().getBank().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Bank")) {
                            list_bank_fund = results.getLiquid_recommended_products().getBank();
                        }
                    }
                    if (results.getLiquid_recommended_products().getPost_office().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Post Office")) {
                            list_post_fund = results.getLiquid_recommended_products().getPost_office();
                            if (list_post_fund.size() != 0) {
                            }
                        }
                    }
                } else if (key_name.equalsIgnoreCase("comm_goals")) {
                    if (results.getComm_recommended_products().getMutual_funds().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {
                            list_mutual_fund = results.getComm_recommended_products().getMutual_funds();
                            if (list_mutual_fund.size() != 0) {
                            }
                        }
                    }
                    if (results.getComm_recommended_products().getBank().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Bank")) {
                            list_bank_fund = results.getComm_recommended_products().getBank();
                            if (list_bank_fund.size() != 0) {
                            }
                        }
                    }
                    if (results.getComm_recommended_products().getPost_office().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Post Office")) {

                            list_post_fund = results.getComm_recommended_products().getPost_office();

                            if (list_post_fund.size() != 0) {
                            }
                        }
                    }

                } else if (key_name.equalsIgnoreCase("real_estate_goals")) {
                    if (results.getReal_estate_recommended_products().getMutual_funds().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {
                            list_mutual_fund = results.getReal_estate_recommended_products().getMutual_funds();
                            if (list_mutual_fund.size() != 0) {
                            }
                        }
                    }
                    if (results.getReal_estate_recommended_products().getBank().size() != 0) {
                        if (key_name_sub.equalsIgnoreCase("Bank")) {
                            list_bank_fund = results.getReal_estate_recommended_products().getBank();
                            if (list_bank_fund.size() != 0) {
                            }


                        }

                    }
                    if (results.getReal_estate_recommended_products().getPost_office().size() != 0) {

                        if (key_name_sub.equalsIgnoreCase("Post Office")) {

                            list_post_fund = results.getReal_estate_recommended_products().getPost_office();

                            if (list_mutual_fund.size() != 0) {
                            }
                        }

                    }

                }
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            if (key_name_sub.equalsIgnoreCase("Mutual Fund")) {

                adapter = new InvestmentPlanningAdapterleval(list_mutual_fund);
                recyclerView.setAdapter(adapter);

            }
            if (key_name_sub.equalsIgnoreCase("Bank")) {

                adapter_bank = new InvestmentPlanning_Adapterleval_bank(list_bank_fund);
                recyclerView.setAdapter(adapter_bank);

            }
            if (key_name_sub.equalsIgnoreCase("Post Office")) {

                adapter_post_office = new InvestmentPlanning_Adapterleval_post_office(list_post_fund);
                recyclerView.setAdapter(adapter_post_office);

            }

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
                    addFragmenttoStack(new InverstmentPalanChartview());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.menu_summary:
                try {

                    Bundle args = new Bundle();
                    args.putSerializable("InvestmentPlan", investmentPlans);
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
        private ArrayList<IPS_Mutual_found> arrayList_data;
        public View view;
        private Context context;

        private InvestmentPlanningAdapterleval(ArrayList<IPS_Mutual_found> ivp) {
            this.arrayList_data = ivp;
            this.context = getActivity();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView text_group_name, text_code, text_name, text_type, text_category, text_sub_category, text_plan_type,
                    text_nav_name, text_launch_date, text_closer_date, text_inv_amount, text_created_date;

            public ViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                text_group_name = view.findViewById(R.id.text_group_name);
                text_code = view.findViewById(R.id.text_code);
                text_name = view.findViewById(R.id.text_name);
                text_type = view.findViewById(R.id.text_type);
                text_category = view.findViewById(R.id.text_category);
                text_sub_category = view.findViewById(R.id.text_sub_category);
                text_plan_type = view.findViewById(R.id.text_plan_type);
                text_nav_name = view.findViewById(R.id.text_nav_name);
                text_launch_date = view.findViewById(R.id.text_launch_date);
                text_closer_date = view.findViewById(R.id.text_closer_date);
                text_inv_amount = view.findViewById(R.id.text_inv_amount);
                text_created_date = view.findViewById(R.id.text_created_date);
            }
        }

        @Override
        public InvestmentPlanningAdapterleval.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.investment_details_adapter, parent, false);
            InvestmentPlanningAdapterleval.ViewHolder viewHolder = new InvestmentPlanningAdapterleval.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(InvestmentPlanningAdapterleval.ViewHolder holder, int position) {
            IPS_Mutual_found data = arrayList_data.get(position);
            holder.text_group_name.setText(data.getFund_house());
            holder.text_code.setText(data.getScheme_code());
            holder.text_name.setText(data.getScheme_name());
            holder.text_type.setText(data.getScheme_type());
            holder.text_category.setText(data.getScheme_category());
            holder.text_sub_category.setText(data.getScheme_sub_category());
            holder.text_plan_type.setText(data.getPlan_type());
            holder.text_nav_name.setText(data.getScheme_nav_name());
            holder.text_launch_date.setText(data.getLaunch_date());
            holder.text_closer_date.setText(data.getClosure_date());
            holder.text_inv_amount.setText(data.getMin_invst_amt());
            holder.text_created_date.setText(data.getCreated_datetime());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        @Override
        public int getItemCount() {

            return arrayList_data.size();

        }
    }

    private class InvestmentPlanning_Adapterleval_bank extends RecyclerView.Adapter<InvestmentPlanning_Adapterleval_bank.ViewHolder> {
        private ArrayList<IPS_Bank> arrayList_data;
        public View view;
        private Context context;

        private InvestmentPlanning_Adapterleval_bank(ArrayList<IPS_Bank> ivp) {
            this.arrayList_data = ivp;
            this.context = getActivity();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView text_asset_class, text_asset_sub_class, text_product_name, text_investment_period, text_stated_interest;
            TextView text_effective_date, text_compounding_period, text_effective_interest, text_tax_benefit, text_age;

            public ViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                text_asset_class = view.findViewById(R.id.text_asset_class);
                text_asset_sub_class = view.findViewById(R.id.text_asset_sub_class);
                text_product_name = view.findViewById(R.id.text_product_name);
                text_investment_period = view.findViewById(R.id.text_investment_period);
                text_stated_interest = view.findViewById(R.id.text_stated_interest);
                text_effective_date = view.findViewById(R.id.text_effective_date);
                text_compounding_period = view.findViewById(R.id.text_compounding_period);
                text_effective_interest = view.findViewById(R.id.text_effective_interest);
                text_tax_benefit = view.findViewById(R.id.text_tax_benefit);
                text_age = view.findViewById(R.id.text_age);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.investment_details_bank_adapter, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            IPS_Bank data = arrayList_data.get(position);
            holder.text_asset_class.setText(data.getAsset_class());
            holder.text_asset_sub_class.setText(data.getAsset_sub_class());
            holder.text_product_name.setText(data.getProduct_name());
            holder.text_investment_period.setText(data.getInvestment_period());
            holder.text_stated_interest.setText(data.getStated_interest());
            holder.text_effective_date.setText(data.getEffective_date());
            holder.text_compounding_period.setText(data.getCompounding_period());
            holder.text_effective_interest.setText(data.getEffective_interest());
            holder.text_tax_benefit.setText(data.getTax_benefit());
            holder.text_age.setText(data.getAge());
            //holder.text_target_value.setText(arrayList_data.get(position).getTarget_value());
            //holder.text_current_value.setText(arrayList_data.get(position).getCurrent_value());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        @Override
        public int getItemCount() {

            return arrayList_data.size();

        }
    }

    private class InvestmentPlanning_Adapterleval_post_office extends RecyclerView.Adapter<InvestmentPlanning_Adapterleval_post_office.ViewHolder> {
        private ArrayList<IPS_Postoffice> arrayList_data;
        public View view;
        private Context context;

        private InvestmentPlanning_Adapterleval_post_office(ArrayList<IPS_Postoffice> ivp) {
            this.arrayList_data = ivp;
            this.context = getActivity();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView text_asset_class, text_asset_sub_class, text_product_name, text_investment_period, text_stated_interest;
            private TextView text_effective_date, text_compounding_period, text_effective_interest, text_tax_benefit, text_age;

            public ViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                text_asset_class = view.findViewById(R.id.text_asset_class);
                text_asset_sub_class = view.findViewById(R.id.text_asset_sub_class);
                text_product_name = view.findViewById(R.id.text_product_name);
                text_investment_period = view.findViewById(R.id.text_investment_period);
                text_stated_interest = view.findViewById(R.id.text_stated_interest);
                text_effective_date = view.findViewById(R.id.text_effective_date);
                text_compounding_period = view.findViewById(R.id.text_compounding_period);
                text_effective_interest = view.findViewById(R.id.text_effective_interest);
                text_tax_benefit = view.findViewById(R.id.text_tax_benefit);
                text_age = view.findViewById(R.id.text_age);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.investment_details_post_office_adapter, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            IPS_Postoffice data = arrayList_data.get(position);
            holder.text_asset_class.setText(data.getAsset_class());
            holder.text_asset_sub_class.setText(data.getAsset_sub_class());
            holder.text_product_name.setText(data.getProduct_name());
            holder.text_investment_period.setText(data.getInvestment_period());
            holder.text_stated_interest.setText(data.getStated_interest());
            holder.text_effective_date.setText(data.getEffective_date());
            holder.text_compounding_period.setText(data.getCompounding_period());
            holder.text_effective_interest.setText(data.getEffective_interest());
            holder.text_tax_benefit.setText(data.getTax_benefit());
            holder.text_age.setText(data.getAge());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        @Override
        public int getItemCount() {

            return arrayList_data.size();

        }
    }

}

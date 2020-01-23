package com.purplepath.purplepath.investmentPlan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
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
import com.purplepath.purplepath.goalanalysis.singlegoal.SingleGoalDonutview;
import com.purplepath.purplepath.investmentPlan.models.IPS_Results;
import com.purplepath.purplepath.investmentPlan.models.IPS_Sum;
import com.purplepath.purplepath.investmentPlan.models.InvestmentPlan;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by rajasekar b on 27/02/2019.
 */
public class InverstmentPalans extends BaseFragment implements View.OnClickListener {
    String key_name = "";
    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private LinearLayout linear_parent;

    private OnActivityBackPressedListener mCallBackListener;


    private InvestmentPlan investmentPlan;

    TextView text_header, empty_values;
    private TableView mTableView;

    private BigDecimal per1year;

    private BigDecimal per1month;

    private BigDecimal pvals;
    private RecyclerView recyclerView;
    InvestmentPlanningAdapter adapter;
    GridViewInvers gridAdapter;
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
        empty_values = view.findViewById(R.id.empty_values);
        linear_parent = view.findViewById(R.id.linear_parent);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        mTableView = createTableViewSavings();
        text_header.setText("Asset Class");
        //callAllInsuranceService();
        callInvestmentService();
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
                ArrayList<Date> list_start_date = new ArrayList<>();
                ArrayList<Integer> end_date = new ArrayList<>();
                ArrayList<Date> list_end_date = new ArrayList<>();
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
                        System.out.println(key + "-> REF ->:" + (Double.parseDouble(ipsResults.getLiquid_fv()) + ":" + Double.parseDouble(ipsResults.getLiquid_per())));
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
                    //  list_start_date.add(get_date_format_((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_start_datetime()))));
                    end_date.add(Integer.parseInt((String) DateFormat.format("yyyy", get_date_format(ipsResults.getGoal_end_datetime()))));
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
                list_sum_of.add(ips_sum);
                System.out.println("Set Data-->:" + current_value + ":" + annual_con + ":" + target_value);
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

    private void callInvestmentService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<InvestmentPlan> call = webServiceObj.getInvesmentPlaning(UtileKit.getPersistedPurplePathPref("user_id"));
        // Call<InvestmentPlan> call = webServiceObj.getInvesmentPlaning("247");
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
                        load_data();
                    } else {
                        linear_parent.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);

                        // System.out.println("Rajasekar else:" + investmentPlan.getData().getResults().get(0).getId() + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    linear_parent.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<InvestmentPlan> call, Throwable t) {

                t.printStackTrace();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
                linear_parent.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });
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

                    InvestmentPlanningGraf fragment = InvestmentPlanningGraf.newInstance(investmentPlan);
                    addFragmenttoStack(fragment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.menu_summary:
                try {

                    Bundle args = new Bundle();
                    args.putSerializable("InvestmentPlan", investmentPlan);
                    args.putString("Level", "Level");
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

    public class InvestmentPlanningAdapter extends RecyclerView.Adapter<InvestmentPlanningAdapter.ViewHolder> {
        private ArrayList<IPS_Sum> ips_sum;
        public View view;
        private Context context;

        public InvestmentPlanningAdapter(ArrayList<IPS_Sum> ivp) {
            this.ips_sum = ivp;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView text_group_name, text_current_value, text_anual_value, text_target_value, text_start_date,
                    text_end_date, text_next;
            RecyclerView recyclerview;

            public ViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                text_group_name = view.findViewById(R.id.text_group_name);
                text_current_value = view.findViewById(R.id.text_current_value);
                text_target_value = view.findViewById(R.id.text_target_value);
                text_start_date = view.findViewById(R.id.text_start_date);
                text_end_date = view.findViewById(R.id.text_end_date);
                recyclerview = view.findViewById(R.id.recyclerview);
                text_next = view.findViewById(R.id.text_next);
                text_anual_value = view.findViewById(R.id.text_anual_value);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.investment_plan_adapter, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            try {
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
                BigDecimal target_value = BigDecimal.ZERO;
                BigDecimal current_value = BigDecimal.ZERO;
                BigDecimal anual_value = BigDecimal.ZERO;
                current_value = new BigDecimal(ips_sum.get(position).getCurrent_value() + "");
                target_value = new BigDecimal(ips_sum.get(position).getTarget_value() + "");

                anual_value = new BigDecimal(ips_sum.get(position).getAnnual_con() + "");

                holder.text_current_value.setText(UtileKit.formatedNumbers_decimal(current_value));
                holder.text_target_value.setText(UtileKit.formatedNumbers_decimal(target_value));
                Log.w("viswaTargetvalue", UtileKit.formatedNumbers_decimal(target_value)+"");
                holder.text_anual_value.setText(UtileKit.formatedNumbers_decimal(anual_value));


                holder.text_start_date.setText(UtileKit.get_start_date(ips_sum.get(position).getStart_date_s()));
                holder.text_end_date.setText(UtileKit.get_end_date(ips_sum.get(position).getStart_date_s()));
                UtileKit.get_start_date(ips_sum.get(position).getStart_date_s());
                UtileKit.get_start_date(ips_sum.get(position).getEnd_date_s());
                key_name = investmentPlan.getData().getAsset_classes().get(position);
                ArrayList<IPS_Results> arrayList_data = investmentPlan.getData().getGoals_with_asset_class().get(key_name);
                int lenght = arrayList_data.size();
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.WRAP_CONTENT);
                lparams.setMargins(2, 2, 2, 2);
                gridAdapter = new GridViewInvers(arrayList_data, getActivity());
                LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                holder.recyclerview.setLayoutManager(horizontalLayoutManager);
                holder.recyclerview.setAdapter(gridAdapter);
                holder.text_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        key_name = investmentPlan.getData().getAsset_classes().get(position);
                        Bundle args = new Bundle();
                        args.putSerializable("InvestmentPlan", investmentPlan);
                        args.putString("key_name", key_name);
                        Log.e("Value_count", investmentPlan.getData().getResults().size()+"");
                        InverstmentPalanleval fragment = new InverstmentPalanleval();
                        fragment.setArguments(args);
                        addFragmenttoStack(fragment);
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


    public class GridViewInvers extends RecyclerView.Adapter<GridViewInvers.ViewHolder> {
        private ArrayList<IPS_Results> ips_results;
        public View view;
        private Activity context;

        public GridViewInvers(ArrayList<IPS_Results> ivp, Activity activity) {
            this.ips_results = ivp;
            this.context = activity;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView text_goal_name;

            public ViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                text_goal_name = view.findViewById(R.id.text_goal_name);


            }
        }

        @Override
        public GridViewInvers.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_name_adapter, parent, false);
            GridViewInvers.ViewHolder viewHolder = new GridViewInvers.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(GridViewInvers.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            String key_name = ips_results.get(position).getGoal_name();
            String other = ips_results.get(position).getGoal_name();
            holder.text_goal_name.setText(key_name);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addFragmenttoStack(SingleGoalDonutview.newInstance(ips_results.get(position).getId(),
                            ips_results.get(position).getGoal_name(), "GoalView",
                            ips_results.get(position).getGoal_years(), ips_results.get(position).getExpected_increment()));
                    /*
                    addFragmenttoStack(SingleGoalDonutview.newInstance("55",
                            "BMW Car", "GoalView",
                            "22", "35"));*/

                }
            });


        }

        @Override
        public int getItemCount() {

            return ips_results.size();

        }
    }

    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }


}

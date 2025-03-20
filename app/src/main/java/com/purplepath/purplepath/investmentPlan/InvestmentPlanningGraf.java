package com.purplepath.purplepath.investmentPlan;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.mikephil.charting.charts2.LineChart;
import com.github.mikephil.charting.components2.Legend;
import com.github.mikephil.charting.components2.XAxis;
import com.github.mikephil.charting.data2.Entry;
import com.github.mikephil.charting.data2.LineData;
import com.github.mikephil.charting.data2.LineDataSet;
import com.github.mikephil.charting.formatter2.IValueFormatter;
import com.github.mikephil.charting.interfaces2.datasets.ILineDataSet;
import com.github.mikephil.charting.utils2.ColorTemplate;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.investmentPlan.models.IPS_Results;
import com.purplepath.purplepath.investmentPlan.models.IPS_Sum;
import com.purplepath.purplepath.investmentPlan.models.InvestmentPlan;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Pratheep.S on 01-06-2017.
 */
public class InvestmentPlanningGraf extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.lineChart)
    LineChart mChart;

    @BindView(R.id.noChartData)
    TextView noChartData_txt;

    @BindView(R.id.legendLayout1)
    LinearLayout legendLayout1;

    Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private int[] mColors = new int[]{
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };

    private InvestmentPlan investmentPlan;
    private OnActivityBackPressedListener mCallBackListener;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.networth_summary_menu, menu);
        menu.findItem(R.id.menu_graph).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_details:
                ((OnActivityBackPressedListener) mContext).onActivityBackPressed();
                break;
            case android.R.id.home:
                // ((OnActivityBackPressedListener)mContext).onActivityBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public static InvestmentPlanningGraf newInstance(InvestmentPlan investmentPlan) {
        Bundle args = new Bundle();
        args.putSerializable("InvestmentPlan", investmentPlan);
        InvestmentPlanningGraf fragment = new InvestmentPlanningGraf();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            setHasOptionsMenu(true);
            mContext = getContext();
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_investment_plan_graph, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        mCallBackListener.setActionBarTitle("InvestmentPlanning");

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey("InvestmentPlan")) {
                try {
                    investmentPlan = (InvestmentPlan) args.getSerializable("InvestmentPlan");
                    if (investmentPlan.getStatus_code().equals(UtileKit.SUCCESSCODE) && investmentPlan.getData().getResults().size() > 0) {
                        load_data();
                    } else {
                        showNoChartData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showNoChartData();
            }
        }
        return view;
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
                ArrayList<Integer> end_date = new ArrayList<>();
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

            }
            initializeGraph_1(list_sum_of);


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

    private void showNoChartData() {
        noChartData_txt.setVisibility(View.VISIBLE);
        mChart.setVisibility(View.GONE);
    }

    private void initializeGraph_1(ArrayList<IPS_Sum> list_data) {

        mChart.setDrawGridBackground(false);
        //mChart.getDescription().setEnabled(false);
        mChart.setDrawBorders(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisRight().setDrawAxisLine(false);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getAxisLeft().setDrawGridLines(false);
        //mChart.getXAxis().setDrawAxisLine(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        ArrayList<Entry> values = new ArrayList<Entry>();
        int size = list_data.size();
         for (int i = 0; i < size; i++) {
            /// String tenusrevalue = UtileKit.currToCharConversionabsolute(Float.parseFloat(investmentPlan.getData().getAmtz_sch().get(i).getTenure()));
            ///String loan_interest = UtileKit.currToCharConversionabsolute(Float.parseFloat(investmentPlan.getData().getAmtz_sch().get(i).getLoan_interest()));
            //Log.i("Amortization Graph", " initializeGraph_1 tenusrevalue " + tenusrevalue);
            // Log.i("Amortization Graph", " initializeGraph_1 loan_interest " + loan_interest);
            float  val_anual=+Float.parseFloat((list_data.get(i).getAnnual_con()+i)+"");
            values.add(new Entry(list_data.get(i).getEnd_date_s().get(i), val_anual));
        }
        LineDataSet d = new LineDataSet(values, "Planning");
        d.setLineWidth(2.5f);
        d.setCircleRadius(.5f);
        //int color = mColors[1 % mColors.length];
        int color = ColorTemplate.rgb("#07ff66");
        d.setDrawValues(false);
        d.setColor(color);
        d.setCircleColor(color);
        d.setValueFormatter(new MyValueFormatter());
        dataSets.add(d);
         ArrayList<Entry> values2 = new ArrayList<Entry>();
        for (int i = 0; i < size; i++) {
            float   val_target=+Float.parseFloat((list_data.get(i).getTarget_value()+i)+"");

            values2.add(new Entry(list_data.get(i).getEnd_date_s().get(i), val_target));
        }
        LineDataSet d2 = new LineDataSet(values2, "Actual");
        d2.setLineWidth(2.5f);
        d2.setCircleRadius(.5f);
        d2.setDrawValues(false);
        //int color = mColors[1 % mColors.length];
        int color2 = ColorTemplate.rgb("#ff07e4");
        d2.setColor(color2);
        d2.setCircleColor(color2);
        d2.setValueFormatter(new MyValueFormatter());
        dataSets.add(d2);
        int colors[] = {color, color2};
        ArrayList<String> elements = new ArrayList<>(Arrays.asList("Planning", "Actual"));
        showLegend(elements, colors, legendLayout1);

        // make the first DataSet dashed
        //  ((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);
        // ((LineDataSet) dataSets.get(0)).setColors(ColorTemplate.VORDIPLOM_COLORS);
        //((LineDataSet) dataSets.get(0)).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);
        Legend legend = mChart.getLegend();
        legend.setEnabled(false);

        LineData data = new LineData(dataSets);
        mChart.setDescription(null);
        mChart.setData(data);
        mChart.invalidate();
    }


    private void showLegend(ArrayList<String> elements, int[] colors, LinearLayout legendLayout) {
        for (int i = 0; i < elements.size(); i++) {

            LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout parent_layout = new LinearLayout(mContext);
            parent_layout.setWeightSum(2);
            if (colors.length == 3) {
                parent_layout.setOrientation(LinearLayout.VERTICAL);
            } else {
                parent_layout.setOrientation(LinearLayout.HORIZONTAL);
            }
            parent_param_layout.setMargins(10, 0, 0, 10);
            parent_layout.setLayoutParams(parent_param_layout);

            LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_left_layout.weight = 1F;
            LinearLayout left_layout = new LinearLayout(mContext);
            left_layout.setOrientation(LinearLayout.HORIZONTAL);
            left_layout.setGravity(Gravity.LEFT);
            left_layout.setLayoutParams(parms_left_layout);

            LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(45, 45);
            parms_legen_layout.setMargins(20, 20, 20, 20);
            LinearLayout legend_layout = new LinearLayout(mContext);
            legend_layout.setLayoutParams(parms_legen_layout);
            legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            legend_layout.setBackgroundColor(colors[i]);
            left_layout.addView(legend_layout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            TextView txt_unit = new TextView(mContext);
            txt_unit.setMaxLines(2);
            // params.setMargins(0, 0, 20, 0);

            txt_unit.setLayoutParams(params);
            txt_unit.setGravity(Gravity.LEFT | Gravity.CENTER);
            txt_unit.setText(elements.get(i));
            left_layout.addView(txt_unit);
            i++;
            if ((elements.size()) == i) {
                parent_layout.addView(left_layout);
                legendLayout.addView(parent_layout);

                break;
            }

            LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_right_layout.weight = 1F;
            LinearLayout right_layout = new LinearLayout(mContext);
            right_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_layout.setGravity(Gravity.LEFT);
            parent_param_layout.setMargins(10, 0, 0, 10);
            right_layout.setLayoutParams(parms_right_layout);


            LinearLayout.LayoutParams parms_rightlegend_layout = new LinearLayout.LayoutParams(45, 45);
            parms_rightlegend_layout.setMargins(20, 20, 20, 20);
            LinearLayout right_legend_layout = new LinearLayout(mContext);
            right_legend_layout.setLayoutParams(parms_rightlegend_layout);
            right_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_legend_layout.setBackgroundColor(colors[i]);
            right_layout.addView(right_legend_layout);
            LinearLayout.LayoutParams right_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            TextView right_txt_unit = new TextView(mContext);
            right_txt_unit.setMaxLines(2);
            //right_params.setMargins(0, 0, 20, 0);
            right_txt_unit.setLayoutParams(right_params);
            right_txt_unit.setGravity(Gravity.LEFT | Gravity.CENTER);
            right_txt_unit.setText(elements.get(i));
            right_layout.addView(right_txt_unit);

            parent_layout.addView(right_layout);
            parent_layout.addView(left_layout);
            legendLayout.addView(parent_layout);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;

            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                startActivity(i);
                break;
        }

    }

    private class MyValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("#,##,##0.0"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, com.github.mikephil.charting.utils2.ViewPortHandler viewPortHandler) {
            //Log.e("ChartValueFormatter", "getFormattedValue" + value);
            String decimalvalueintext = UtileKit.currToCharConversionabsolute(value);
            //Log.e("ChartValueFormatter", "getFormattedValue" + decimalvalueintext);
//            return mFormat.format(Math.abs(value))+ "cr" ;
            return decimalvalueintext;
        }

    }
}

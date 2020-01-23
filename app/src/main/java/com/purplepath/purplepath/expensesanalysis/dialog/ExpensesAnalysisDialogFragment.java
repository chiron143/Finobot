package com.purplepath.purplepath.expensesanalysis.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.finobot.finobot.R;
import com.purplepath.purplepath.expensesanalysis.ExpanseanaysisDetailFragment;
import com.purplepath.purplepath.expensesanalysis.ExpanseanaysisMainPageFragment;
import com.purplepath.purplepath.expensesanalysis.model.Edu_det;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;

public class ExpensesAnalysisDialogFragment extends BaseFragment implements View.OnClickListener {

    private Edu_det edu_det;

    private RelativeLayout mainLayout;
    private LinearLayout titleLayout;
    private PieChart mChart;
    // we're going to display pie chart for smartphones martket shares
    private Float[] yData;
    private String[] xData;
    private Context mContext;
    DisplayMetrics displaymetrics = new DisplayMetrics();
    private Legend l;

    private OnActivityBackPressedListener mCallBackListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private int width, chartsize, marginsize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    public static ExpensesAnalysisDialogFragment newInstance(ArrayList<String> xDataLevel, ArrayList<Float> yDataLevel) {
        Bundle args = new Bundle();
        args.putStringArrayList("xdata", xDataLevel);
        args.putSerializable("ydata", yDataLevel);
        ExpensesAnalysisDialogFragment fragment = new ExpensesAnalysisDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View expensedialogView = inflater.inflate(R.layout.dialog_expenses_analysis, container, false);
        mainLayout = expensedialogView.findViewById(R.id.dialog_expense_mainLayout);
        titleLayout = expensedialogView.findViewById(R.id.dialog_expense_titleLayout);

        mleftRelativeLayout = expensedialogView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = expensedialogView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = expensedialogView.findViewById(R.id.relative_right_arrow);

        width = getDeviceWidth();
        double d = width * 0.25;
        chartsize = (int) (d);

        double margindouble = width * 0.03;
        marginsize = (int) (margindouble);

        width = width - chartsize;
        mCallBackListener.setActionBarTitle("Expense Analysis");
        if (getArguments() != null) {
            ArrayList<Float> yDataArray = (ArrayList<Float>) getArguments().getSerializable("ydata");
            ArrayList<String> xDataArray = getArguments().getStringArrayList("xdata");
            yData = new Float[yDataArray.size()];
            xData = new String[xDataArray.size()];
            yData = yDataArray.toArray(yData);
            xData = xDataArray.toArray(xData);
        }

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        mChart = new PieChart(getActivity());
        // add pie chart to main layout

        mChart.setLayoutParams(new FrameLayout.LayoutParams(width, width));
        mainLayout.setBackgroundResource(R.drawable.rectangle_border_bg);
        mainLayout.addView(mChart);
//        mainLayout.setBackgroundColor(Color.parseColor("#ffffff"));

        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("");

        // enable hole and configure
        mChart.setDrawHoleEnabled(false);
//        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(false);

        mChart.setDrawSliceText(false);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null) {
                    return;
                } else {

                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data

        // customize legends
        l = mChart.getLegend();
        l.setEnabled(false);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(10);
        l.setYEntrySpace(10);
        addData();
        return expensedialogView;
    }


    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Market Share");
        dataSet.setSliceSpace(0);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        //    int colorcodes[] = l.getColors();

        for (int i = 0; i < xData.length; i++) {
            LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout parent_layout = new LinearLayout(mContext);
            parent_layout.setWeightSum(2);
            parent_layout.setOrientation(LinearLayout.HORIZONTAL);
            parent_param_layout.setMargins(10, 0, 0, 10);
            parent_layout.setLayoutParams(parent_param_layout);

            LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_left_layout.weight = 1F;
            LinearLayout left_layout = new LinearLayout(mContext);
            left_layout.setOrientation(LinearLayout.HORIZONTAL);
            left_layout.setGravity(Gravity.LEFT);
            left_layout.setLayoutParams(parms_left_layout);

            LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(45, 45);
            parms_legen_layout.setMargins(20, 10, 20, 10);
            LinearLayout legend_layout = new LinearLayout(mContext);
            legend_layout.setLayoutParams(parms_legen_layout);
            legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            legend_layout.setBackgroundColor(colors.get(i));
            left_layout.addView(legend_layout);

            TextView txt_unit = new TextView(mContext);
            LinearLayout.LayoutParams txtleft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            txt_unit.setLayoutParams(txtleft_params);
            txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
            txt_unit.setText(xData[i]);
            left_layout.addView(txt_unit);

            i++;
            if (i >= xData.length) {
                parent_layout.addView(left_layout);
                titleLayout.addView(parent_layout);
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
            parms_rightlegend_layout.setMargins(20, 10, 20, 10);
            LinearLayout right_legend_layout = new LinearLayout(mContext);
            right_legend_layout.setLayoutParams(parms_rightlegend_layout);
            right_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_legend_layout.setBackgroundColor(colors.get(i));
            right_layout.addView(right_legend_layout);

            TextView right_txt_unit = new TextView(mContext);
            LinearLayout.LayoutParams right_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            right_txt_unit.setLayoutParams(right_params);
            right_txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
            right_txt_unit.setText(xData[i]);
            right_layout.addView(right_txt_unit);

            parent_layout.addView(right_layout);
            parent_layout.addView(left_layout);
            titleLayout.addView(parent_layout);

        }
        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_detail, menu);
        MenuItem item = menu.findItem(R.id.menu_summary);
        MenuItem items = menu.findItem(R.id.menu_detail);
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_detail:

                try {
                    addFragmenttoStack(new ExpanseanaysisDetailFragment());
                    // mCallBackListener.onActivityBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:

                try {
                    addFragmenttoStack(new ExpanseanaysisMainPageFragment());

                    //  mCallBackListener.onActivityBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;


        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relative_left_arrow: {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home: {
                startHomeActivity();
//                getActivity().finish();
            }
            break;

        }
    }
}
package com.purplepath.purplepath.incomechartdetail;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.expensesanalysis.fragment.ExpensesAnalysisFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomedetails.fragment.IncomeDetail;
import com.purplepath.purplepath.incomedetails.fragment.model.GetIncomeModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dinesh on 20/07/17.
 */

public class IncomePieLev2ChartFragment extends BaseFragment implements View.OnClickListener {

    DisplayMetrics displaymetrics = new DisplayMetrics();
    private RelativeLayout mainLayout;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private PieChart mChart;
    private ScrollView scrolllinearlayout;
    // we're going to display pie chart for smartphones martket shares
    private Float[] yData;
    private ArrayList<String> xData;
    private Context mContext;
    private LinearLayout titleLayout, checkboxLayout;
    private ArrayList<CheckBox> userNameCheckBox = new ArrayList<>();
    private GetIncomeModel incomeAnalysisModel;
    private int chartsize, marginsize;
    private TextView errorTextview;
    private OnActivityBackPressedListener mCallBackListener;
    private FloatingActionButton mEditIncomeFloatBtn;
    private String mSelectedCatagor;
    private HashMap<String, Boolean> checkBoxState = new HashMap<>();

    public static IncomePieLev2ChartFragment newInstance(GetIncomeModel mIncomeAnalysisModel, String selectedCatagory, HashMap<String, Boolean> checkBoxState) {
        IncomePieLev2ChartFragment fragment = new IncomePieLev2ChartFragment();
        Bundle obj = new Bundle();
        obj.putSerializable("IncomeAnalysisModel", mIncomeAnalysisModel);
        obj.putString("selectedCatagory", selectedCatagory);
        obj.putSerializable("checkBoxState", checkBoxState);
        fragment.setArguments(obj);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getContext();
        try {
            // setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
            if (getArguments().containsKey("IncomeAnalysisModel"))
                incomeAnalysisModel = (GetIncomeModel) getArguments().get("IncomeAnalysisModel");
            mSelectedCatagor = (String) getArguments().get("selectedCatagory");
            checkBoxState.clear();
            checkBoxState.putAll((HashMap<String, Boolean>) getArguments().get("checkBoxState"));

        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
//        getIncomeDetail();
        View incomeView = inflater.inflate(R.layout.fragment_income_piechart_view, container, false);
        mainLayout = incomeView.findViewById(R.id.mainLayout);
        titleLayout = incomeView.findViewById(R.id.titleLayout);
        scrolllinearlayout = incomeView.findViewById(R.id.layout_Scroll);
        errorTextview = incomeView.findViewById(R.id.empty_chart_display);
        checkboxLayout = incomeView.findViewById(R.id.checkboxLayout);
        mleftRelativeLayout = incomeView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = incomeView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = incomeView.findViewById(R.id.relative_right_arrow);
        mEditIncomeFloatBtn = incomeView.findViewById(R.id.incom_fab_id);
        mRightRelativeLayout.setVisibility(View.GONE);
        mCallBackListener.setActionBarTitle("Income Analysis");
        mEditIncomeFloatBtn.setOnClickListener(this);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        int width = getDeviceWidth();
        double d = width * 0.25;
        chartsize = (int) (d);
        width = width - chartsize;

        double margindouble = width * 0.03;
        marginsize = (int) (margindouble);

        mChart = new PieChart(getActivity());
        // add pie chart to main layout
        mChart.setLayoutParams(new FrameLayout.LayoutParams(width, width));

        RelativeLayout.LayoutParams title_params = (RelativeLayout.LayoutParams) titleLayout.getLayoutParams();
        title_params.setMargins(0, marginsize, 0, marginsize);
        titleLayout.setLayoutParams(title_params);


        mainLayout.addView(mChart);
        //mainLayout.setBackgroundColor(Color.parseColor("#ffffff"));

        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setDrawSliceText(false);

        // enable hole and configure
        mChart.setDrawHoleEnabled(false);
//        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);
        mChart.setDrawSliceText(false);
        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e != null) {
                    //Log.e("mChart", "onValueSelected--getVal" + e.getVal());
                    //Log.e("mChart", "onValueSelected--getVal" + e.getData());
                    try {
                        //Log.e("mChart", "onValueSelected--getVal"+xData.get(e.getXIndex()));
                        if (getLel2Id(xData.get(e.getXIndex())))
                            addFragmenttoStack(IncomePieLev3ChartFragment.newInstance(incomeAnalysisModel, xData.get(e.getXIndex()), checkBoxState));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    return;
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // add data

        // customize legends
        Legend l = mChart.getLegend();
        l.setEnabled(false);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(10);
        l.setYEntrySpace(10);
        if (incomeAnalysisModel != null) {
            scrolllinearlayout.setVisibility(View.VISIBLE);
            xData = new ArrayList<>();
            int size = incomeAnalysisModel.getData().getUser_incomes().size();
            for (int i = 0; i < size; i++) {
                if (!incomeAnalysisModel.getData().getUser_incomes().get(i).getFamily_id().equalsIgnoreCase("0")) {
                    xData.add(incomeAnalysisModel.getData().getUser_incomes().get(i).getFamily_name());
                } else {
                    String getprefuserName = UtileKit.getPersistedPurplePathPref("name_services", null);
                    xData.add(getprefuserName);
                }


            }
            AddCheckBoxView(xData, yData);

        }
        return incomeView;
    }

    private boolean getLel2Id(String level2Name) {
        Boolean isDrildownavailable = false;
        if (incomeAnalysisModel != null) {
            for (CheckBox obj : userNameCheckBox)
                if (obj.isChecked()) {
                    int size = incomeAnalysisModel.getData().getUser_incomes().get(obj.getId()).getIncome_cat_lev2().size();
                    for (int i = 0; i < size; i++) {
                        if (incomeAnalysisModel.getData().getUser_incomes().get(obj.getId()).getIncome_cat_lev2().get(i).getLev2_name().equalsIgnoreCase(level2Name)) {
                            String mLev2Id = incomeAnalysisModel.getData().getUser_incomes().get(obj.getId()).getIncome_cat_lev2().get(i).getId();
                            int msize = incomeAnalysisModel.getData().getUser_incomes().get(obj.getId()).getIncome_cat_lev3().size();
                            for (int j = 0; j < msize; j++) {
                                if (incomeAnalysisModel.getData().getUser_incomes().get(obj.getId()).getIncome_cat_lev3().get(j).getLev2_id().equalsIgnoreCase(mLev2Id)) {
                                    isDrildownavailable = true;
                                    return isDrildownavailable;

                                }
                            }
                        }

                    }
                }
        }
        return isDrildownavailable;
    }

    private void addData(ArrayList<String> xData, Float[] yData) {
        mChart.clear();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        try {
            for (int i = 0; i < xData.size(); i++) {
                if (yData[i] != null)
                    yVals1.add(new Entry(yData[i], i));
                else
                    yVals1.add(new Entry(0, i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> xVals = new ArrayList<String>();
        try {
            for (int i = 0; i < xData.size(); i++)
                xVals.add(xData.get(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
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

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        for (int i = 0; i < xData.size(); i++) {

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

            LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(40, 40);
            parms_legen_layout.setMargins(20, 20, 20, 20);
            LinearLayout legend_layout = new LinearLayout(mContext);
            legend_layout.setLayoutParams(parms_legen_layout);
            legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            legend_layout.setBackgroundColor(colors.get(i));
            left_layout.addView(legend_layout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            TextView txt_unit = new TextView(mContext);
            // params.setMargins(0, 0, 20, 0);
            LinearLayout.LayoutParams txtLeft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            txt_unit.setLayoutParams(txtLeft_params);
            txt_unit.setLayoutParams(params);
            txt_unit.setGravity(Gravity.LEFT | Gravity.CENTER);
            txt_unit.setText(xData.get(i));
            left_layout.addView(txt_unit);
            i++;
            if ((xData.size()) == i) {
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
            LinearLayout.LayoutParams right_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            TextView right_txt_unit = new TextView(mContext);
            //right_params.setMargins(0, 0, 20, 0);
            LinearLayout.LayoutParams txtRight_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            right_txt_unit.setLayoutParams(txtRight_params);
            right_txt_unit.setLayoutParams(right_params);
            right_txt_unit.setGravity(Gravity.LEFT | Gravity.CENTER);
            right_txt_unit.setText(xData.get(i));
            right_layout.addView(right_txt_unit);

            parent_layout.addView(right_layout);
            parent_layout.addView(left_layout);
            titleLayout.addView(parent_layout);
        }
//          AddCheckBoxView( xData,  yData);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();

    }

    private void AddCheckBoxView(final ArrayList<String> xData, Float[] yData) {
        checkboxLayout.removeAllViews();
        userNameCheckBox.clear();
        for (int i = 0; i < xData.size(); i++) {
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

            //Log.e("Possition To Add L", "" + i);
            userNameCheckBox.add(new CheckBox(mContext));
            userNameCheckBox.get(i).setId(i);
            UtileKit.setTextAppearance(mContext, android.R.style.TextAppearance_Small, userNameCheckBox.get(i));
            userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    try {
                        int position = compoundButton.getId();
                        if (!(position == userNameCheckBox.size() - 1)) {
                            checkBoxState.put(xData.get(position), b);
                        } else if ((position == userNameCheckBox.size() - 1) & b) {
                            checkBoxState.clear();
                        }
                        checkBoxOnClick(position, b);
                        //Log.e("Possition To Add L", "" + position);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            userNameCheckBox.get(i).setText(xData.get(i));
            userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.app_text_color_gray));
            left_layout.addView(userNameCheckBox.get(i));
            i++;
            if (xData.size() == i) {
                if (xData.remove("Total")) {
                    parent_layout.addView(left_layout);
                    checkboxLayout.addView(parent_layout);
                }
                break;
            }

            LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_right_layout.weight = 1F;
            LinearLayout right_layout = new LinearLayout(mContext);
            right_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_layout.setGravity(Gravity.LEFT);
            parent_param_layout.setMargins(10, 0, 0, 10);
            right_layout.setLayoutParams(parms_right_layout);

            //Log.e("Possition To Add R", "" + i);
            userNameCheckBox.add(new CheckBox(mContext));
            userNameCheckBox.get(i).setId(i);
            UtileKit.setTextAppearance(mContext, android.R.style.TextAppearance_Small, userNameCheckBox.get(i));
            userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    try {
                        int position = compoundButton.getId();
                        if (!(position == userNameCheckBox.size() - 1)) {
                            checkBoxState.put(xData.get(position), b);
                        } else if ((position == userNameCheckBox.size() - 1) & b) {
                            checkBoxState.clear();
                        }
                        checkBoxOnClick(position, b);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            userNameCheckBox.get(i).setText(xData.get(i));
            userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.app_text_color_gray));
            right_layout.addView(userNameCheckBox.get(i));

            parent_layout.addView(right_layout);
            parent_layout.addView(left_layout);
            checkboxLayout.addView(parent_layout);


        }
        if (checkBoxState.isEmpty()) {
            if (!userNameCheckBox.isEmpty())
                userNameCheckBox.get(userNameCheckBox.size() - 1).setChecked(true);
        } else if (!userNameCheckBox.isEmpty()) {
            for (String key : checkBoxState.keySet()) {

                for (CheckBox mCheckbox : userNameCheckBox) {
                    if (mCheckbox.getText().toString().equalsIgnoreCase(key)) {
                        mCheckbox.setChecked(checkBoxState.get(key));
                    }
                }
            }

        }

    }

    private void checkBoxOnClick(int position, boolean b) {
//        if (position == userNameCheckBox.size() - 1 & b) {
//            for (int i = 0; i < userNameCheckBox.size() - 1; i++) {
//                userNameCheckBox.get(i).setChecked(false);
//            }
//            titleLayout.removeAllViews();
//            addData(xData, yData);
//        } else {

        userNameCheckBox.get(position).setChecked(b);
//            if (b)
//                userNameCheckBox.get(userNameCheckBox.size() - 1).setChecked(false);
        int k = 0, mPosition = 0;
        for (int i = 0; i < userNameCheckBox.size(); i++) {
            if (userNameCheckBox.get(i).isChecked()) {
                k++;
                mPosition = userNameCheckBox.get(i).getId();
            }
        }
        if (k > 1) {
            addSingleUserChartCombine(position);
        } else if (k == 1) {
            addSingleUserChart(mPosition);
        } else {
            titleLayout.removeAllViews();
            ArrayList<String> xDataName = new ArrayList<>();
            Float yDataPercent[] = new Float[7];
            addData(xDataName, yDataPercent);
        }

//        }

    }

    private void addSingleUserChartCombine(int position) {
        Float yDataPercent[];
//        Arrays.fill(yDataPercent, 0.0);
        ArrayList<String> xDataName = new ArrayList<>();
        HashMap<String, String> objMap = new HashMap();
        HashMap<String, String> mCombinedIncomeMap = new HashMap();
//        for (int i = 0; i < 7; i++) {
//            yDataPercent[i] = 0.0f;
//        }
        int size;
        for (int i = 0; i < userNameCheckBox.size(); i++) {
            if (userNameCheckBox.get(i).isChecked()) {
                try {
                    if (incomeAnalysisModel.getData().getUser_incomes().get(i) != null) {
                        if (incomeAnalysisModel.getData().getUser_incomes().get(i).getIncome_cat_lev2() != null) {
                            int mSize = incomeAnalysisModel.getData().getUser_incomes().get(i).getIncome_cat_lev2().size();
                            for (int j = 0; j < mSize; j++) {
                                if (incomeAnalysisModel.getData().getUser_incomes().get(position).getIncome_cat_lev2().get(j).getLev1_id().equalsIgnoreCase(mSelectedCatagor)) {
                                    if (!incomeAnalysisModel.getData().getUser_incomes().get(i).getIncome_cat_lev2().get(j).getValue().equalsIgnoreCase("0")) {
                                        Float ydata = 0.0f;
                                        if (mCombinedIncomeMap.containsKey(incomeAnalysisModel.getData().getUser_incomes().get(i).getIncome_cat_lev2().get(j).getLev2_name())) {
                                            try {
                                                ydata = Float.parseFloat(mCombinedIncomeMap.get(incomeAnalysisModel.getData().getUser_incomes().get(i).getIncome_cat_lev2().get(j).getLev2_name()));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        try {
                                            ydata = ydata + Float.parseFloat(incomeAnalysisModel.getData().getUser_incomes().get(i).getIncome_cat_lev2().get(j).getValue());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
//                                yDataPercent[i] =yDataPercent[i]+ Float.parseFloat(incomeAnalysisModel.getData().getUser_incomes().get(i).getOver_all_total());
                                        objMap.put("" + incomeAnalysisModel.getData().getUser_incomes().get(i).getIncome_cat_lev2().get(j).getLev2_name(), "" + incomeAnalysisModel.getData().getUser_incomes().get(i).getIncome_cat_lev2().get(j).getLev2_name());
                                        mCombinedIncomeMap.put("" + incomeAnalysisModel.getData().getUser_incomes().get(i).getIncome_cat_lev2().get(j).getLev2_name(), "" + ydata);
                                    }
                                }
                            }
                        }

                        titleLayout.removeAllViews();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        int i = 0;
        yDataPercent = new Float[mCombinedIncomeMap.size()];
        xDataName = new ArrayList<>();
        for (String key : objMap.values()) {
            try {
                yDataPercent[i] = Float.parseFloat(mCombinedIncomeMap.get(key));
                i++;
            } catch (Exception e) {
                yDataPercent[i] = 0.0f;
            }
            xDataName.add(key);
        }
        xData.clear();
        xData.addAll(xDataName);
        addData(xDataName, yDataPercent);
    }

    private void addSingleUserChart(int position) {
        try {
            if (incomeAnalysisModel.getData().getUser_incomes().get(position) != null) {

                ArrayList<String> xDataName = new ArrayList<>();
                int i = -1;
                int size = incomeAnalysisModel.getData().getUser_incomes().get(position).getIncome_cat_lev2().size();
                Float yDataPercent[] = new Float[size];
                for (int j = 0; j < size; j++) {
                    if (incomeAnalysisModel.getData().getUser_incomes().get(position).getIncome_cat_lev2().get(j) != null) {
                        if (incomeAnalysisModel.getData().getUser_incomes().get(position).getIncome_cat_lev2().get(j).getLev1_id().equalsIgnoreCase(mSelectedCatagor)) {
                            if (!incomeAnalysisModel.getData().getUser_incomes().get(position).getIncome_cat_lev2().get(j).getValue().equalsIgnoreCase("0")) {
                                i++;
                                try {
                                    if (!StringUtils.isEmpty(incomeAnalysisModel.getData().getUser_incomes().get(position).getIncome_cat_lev2().get(j).getValue())) {
                                        yDataPercent[i] = Float.parseFloat(incomeAnalysisModel.getData().getUser_incomes().get(position).getIncome_cat_lev2().get(j).getValue());
                                    } else {
                                        yDataPercent[i] = 0.0f;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                xDataName.add("" + incomeAnalysisModel.getData().getUser_incomes().get(position).getIncome_cat_lev2().get(j).getLev2_name());
                            }
                        }
                    }
                }
                titleLayout.removeAllViews();
                xData.clear();
                xData.addAll(xDataName);
                addData(xDataName, yDataPercent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * public void callIncomeAnalysisService() {
     * WebServiceCalls webServiceObj;
     * UtileKit.showSpinnerDialog(mContext, false);
     * webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
     * Call<IncomeAnalysisModel> call = webServiceObj.callIncomeAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"));
     * call.enqueue(new Callback<IncomeAnalysisModel>() {
     *
     * @Override public void onResponse(Call<IncomeAnalysisModel> call, Response<IncomeAnalysisModel> response) {
     * UtileKit.dismisssSpinnerDialog();
     * //Log.e("success", "" + response.body());
     * incomeAnalysisModel = response.body();
     * //                ArrayList<Float> chartdatalist = new ArrayList<Float>();
     * //                ArrayList<String> chartitledatalist = new ArrayList<String>();
     * <p>
     * if (incomeAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
     * scrolllinearlayout.setVisibility(View.VISIBLE);
     * if (null != incomeAnalysisModel.getData().getUser_inc_analysis()) {
     * int size = incomeAnalysisModel.getData().getUser_inc_analysis().size();
     * yData = new Float[size + 1];
     * xData = new ArrayList<String>();
     * for (int i = 0; i < size; i++) {
     * if (incomeAnalysisModel.getData().getUser_inc_analysis().get(i).getIncome_percent() != null) {
     * if (!incomeAnalysisModel.getData().getUser_inc_analysis().get(i).getIncome_percent().equalsIgnoreCase("0")) {
     * Log.i("incomeChart", "incomeChart for loop" + incomeAnalysisModel.getData().getUser_inc_analysis().get(i).getIncome_percent());
     * yData[i] = Float.parseFloat(incomeAnalysisModel.getData().getUser_inc_analysis().get(i).getOverall_income());
     * }
     * } else {
     * yData[i] = Float.valueOf(0);
     * }
     * if (!incomeAnalysisModel.getData().getUser_inc_analysis().get(i).getFamilyname().equalsIgnoreCase("null"))
     * xData.add(incomeAnalysisModel.getData().getUser_inc_analysis().get(i).getFamilyname());
     * else
     * xData.add("User");
     * //Log.e("Sucess", "Error" + yData[i]);
     * }
     * //                        yData[size]=Float.valueOf(0);
     * xData.add("Total");
     * <p>
     * } else {
     * scrolllinearlayout.setVisibility(View.GONE);
     * errorTextview.setVisibility(View.VISIBLE);
     * errorTextview.setText(HomePageActivity.errorMessageInChart);
     * UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
     * }
     * }
     * if (yData != null) {
     * AddCheckBoxView(xData, yData);
     * //addData(xData,yData);
     * } else {
     * scrolllinearlayout.setVisibility(View.GONE);
     * errorTextview.setVisibility(View.VISIBLE);
     * errorTextview.setText(HomePageActivity.errorMessageInChart);
     * UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
     * }
     * UtileKit.dismisssSpinnerDialog();
     * }
     * @Override public void onFailure(Call<IncomeAnalysisModel> call, Throwable t) {
     * //Log.e("CallBack", " failure is " + t);
     * UtileKit.dismisssSpinnerDialog();
     * UtileKit.alertRetrofitExceptionDialog(mContext, t);
     * }
     * });
     * <p>
     * }
     */
    /*
    private void getIncomeDetail() {

        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<GetIncomeModel> call = webServiceObj.GetIncomeDetailService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetIncomeModel>() {
            @Override
            public void onResponse(Call<GetIncomeModel> call, Response<GetIncomeModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
                incomeAnalysisModel = response.body();
                yData = null;
                if (incomeAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    scrolllinearlayout.setVisibility(View.VISIBLE);
                    if (null != incomeAnalysisModel.getData().getUser_incomes()) {
                        int size = incomeAnalysisModel.getData().getUser_incomes().size();
                        yData = new Float[size + 1];
                        xData = new ArrayList<String>();
                        for (int i = 0; i < size; i++) {
                            if (incomeAnalysisModel.getData().getUser_incomes().get(i).getOver_all_total() != null) {
                                if (!incomeAnalysisModel.getData().getUser_incomes().get(i).getOver_all_total().equalsIgnoreCase("0")) {
                                    Log.i("incomeChart", "incomeChart for loop" + incomeAnalysisModel.getData().getUser_incomes().get(i).getOver_all_total());
                                    yData[i] = Float.parseFloat(incomeAnalysisModel.getData().getUser_incomes().get(i).getOver_all_total());
                                }
                            } else {
                                yData[i] = Float.valueOf(0);
                            }
                            if (!incomeAnalysisModel.getData().getUser_incomes().get(i).getFamily_id().equalsIgnoreCase("null"))
                                xData.add(incomeAnalysisModel.getData().getUser_incomes().get(i).getFamily_id());
                            else
                                xData.add("User");
                            //Log.e("Sucess", "Error" + yData[i]);
                        }
//                        yData[size]=Float.valueOf(0);
                        xData.add("Total");

                    }

                }
                if (yData != null) {
                    AddCheckBoxView(xData, yData);
                } else {
                    scrolllinearlayout.setVisibility(View.GONE);
                    errorTextview.setVisibility(View.VISIBLE);
                    errorTextview.setText(HomePageActivity.errorMessageInChart);
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                }
            }

            @Override
            public void onFailure(Call<GetIncomeModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }
*/
    /*
    void SetLevel2Chart(GetIncomeModel incomeAnalysisModel, String key) {
        String lev1Id = getIncomeLev1Id(incomeAnalysisModel, key);
        yData = null;
        if (incomeAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
            scrolllinearlayout.setVisibility(View.VISIBLE);
            if (null != incomeAnalysisModel.getData().getUser_incomes()) {
                int size = incomeAnalysisModel.getData().getUser_incomes().size();
                yData = new Float[size + 1];
                xData = new ArrayList<String>();
                for (int i = 0; i < size; i++) {
                    if (incomeAnalysisModel.getData().getUser_incomes().get(i).getOver_all_total() != null) {
                        if (!incomeAnalysisModel.getData().getUser_incomes().get(i).getOver_all_total().equalsIgnoreCase("0")) {
                            Log.i("incomeChart", "incomeChart for loop" + incomeAnalysisModel.getData().getUser_incomes().get(i).getOver_all_total());
                            yData[i] = Float.parseFloat(incomeAnalysisModel.getData().getUser_incomes().get(i).getOver_all_total());
                        }
                    } else {
                        yData[i] = Float.valueOf(0);
                    }
                    if (!incomeAnalysisModel.getData().getUser_incomes().get(i).getFamily_id().equalsIgnoreCase("null"))
                        xData.add(incomeAnalysisModel.getData().getUser_incomes().get(i).getFamily_id());
                    else
                        xData.add("User");
                    //Log.e("Sucess", "Error" + yData[i]);
                }
//                        yData[size]=Float.valueOf(0);
                xData.add("Total");

            }

        }
        if (yData != null) {
            AddCheckBoxView(xData, yData);
        } else {
            scrolllinearlayout.setVisibility(View.GONE);
            errorTextview.setVisibility(View.VISIBLE);
            errorTextview.setText(HomePageActivity.errorMessageInChart);
            UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
        }
    }
*/
    /*
    private String getIncomeLev1Id(GetIncomeModel incomeAnalysisModel, String key) {
        int size = incomeAnalysisModel.getData().getUser_incomes().size();
        for (int i = 0; i < size; i++) {
//            int size=incomeAnalysisModel.getData().getUser_incomes().get(i).getIncome_cat_lev1().get

        }

        return "";
    }
    */
/*
    private void setChartDataAsList(ArrayList<Float> chartdatalist, ArrayList<String> chartitledatalist, String value) {

        if (UtileKit.validateObjectValues(value) && !value.equalsIgnoreCase("0")) {
            Float valuepervalue = Float.parseFloat(value);
            chartdatalist.add(valuepervalue);
            chartitledatalist.add(value);
        }

    }
*/
    public int getDeviceWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return width;
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
                    addFragmenttoStack(new IncomeanaysisDetailpageFragment());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:

                try {
                    addFragmenttoStack(new IncomeanaysisMainPageFragment());
                    //  mCallBackListener.onActivityBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;


            case R.id.ic_done_btn:

                try {

                    mCallBackListener.onActivityBackPressed();
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
            }
            break;
            case R.id.relative_right_arrow: {
                addFragmenttoStack(new ExpensesAnalysisFragment());
            }
            break;
            case R.id.incom_fab_id:
                addFragmenttoStack(new IncomeDetail());
                break;


        }
    }
}

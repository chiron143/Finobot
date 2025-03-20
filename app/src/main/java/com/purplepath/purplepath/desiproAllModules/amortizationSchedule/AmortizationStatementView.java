package com.purplepath.purplepath.desiproAllModules.amortizationSchedule;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.mikephil.charting.charts2.PieChart;
import com.github.mikephil.charting.components2.Legend;
import com.github.mikephil.charting.data2.PieData;
import com.github.mikephil.charting.data2.PieDataSet;
import com.github.mikephil.charting.data2.PieEntry;
import com.github.mikephil.charting.formatter2.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models.AmortizationScheduleModel;
import com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models.Amtz_sch;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;

import butterknife.ButterKnife;


/**
 * Created by pravinr on 2/14/18.
 */

public class AmortizationStatementView extends BaseFragment implements View.OnClickListener {

    private Bundle args;
    private AmortizationScheduleModel amort_model;
    private PieChart chart;

    private Context mContext;
    private TextView text_emi, text_interest, text_principal;
    private OnActivityBackPressedListener mCallBackListener;

    private AbstractTableAdapter mTableViewAdapter;


    RelativeLayout fragment_container;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    public static AmortizationStatementView newInstance(AmortizationScheduleModel amort_model) {

        Bundle args = new Bundle();
        args.putSerializable("amort_model", amort_model);
        AmortizationStatementView fragment = new AmortizationStatementView();
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
        View view = inflater.inflate(R.layout.fragment_amortization_statement_view, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);


        mCallBackListener.setActionBarTitle("DeciPro - Amortization Schedule");


        mContext = getContext();
        args = getArguments();
        if (args != null) {
            if (args.containsKey("amort_model")) {
                amort_model = (AmortizationScheduleModel) args.getSerializable("amort_model");
            }

        }

        fragment_container = view.findViewById(R.id.container);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        text_emi = view.findViewById(R.id.text_emi);
        text_interest = view.findViewById(R.id.text_interest);
        text_principal = view.findViewById(R.id.text_principal);
        chart = view.findViewById(R.id.chart);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        load_data();
        return view;
    }

    private void load_data() {
        try {

            int size = amort_model.getData().getAmtz_sch().size();
            Amtz_sch data = amort_model.getData().getAmtz_sch().get(size - 1);

            text_emi.setText(UtileKit.formatedNumber(Float.parseFloat(data.getEmi())));
            text_interest.setText(UtileKit.formatedNumber(Float.parseFloat(data.getCum_interest())));
            text_principal.setText(UtileKit.formatedNumber(Float.parseFloat(data.getCum_principal())));

            float prciple = (Float.parseFloat(data.getCum_principal()) / Float.parseFloat(data.getCum_payment())) * 100;
            float interest = (Float.parseFloat(data.getCum_interest()) / Float.parseFloat(data.getCum_payment())) * 100;
            load_data_chart(prciple, interest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load_data_chart(float prciple, float interest) {
        try {

            chart.setUsePercentValues(true);
            chart.getDescription().setEnabled(true);
            chart.setContentDescription("Hello");
            chart.setExtraOffsets(5, 10, 5, 5);

            chart.setDragDecelerationFrictionCoef(0.95f);

            //tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

            // chart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
            //chart.setCenterText(generateCenterSpannableText());
            chart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

            chart.setDrawHoleEnabled(true);
            chart.setHoleColor(Color.WHITE);

            chart.setTransparentCircleColor(Color.WHITE);
            chart.setTransparentCircleAlpha(0);

            chart.setHoleRadius(5f);
            chart.setTransparentCircleRadius(61f);
            chart.setDrawCenterText(true);
            chart.setRotationAngle(0);
            // enable rotation of the chart by touch
            chart.setRotationEnabled(true);
            chart.setHighlightPerTapEnabled(true);

            // chart.setUnit(" €");
            // chart.setDrawUnitsInChart(true);

            // add a selection listener
            //  chart.setOnChartValueSelectedListener(getActivity());


            chart.animateY(1000);
            // chart.spin(2000, 0, 360);
            setData(prciple, interest);

            Legend l = chart.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(false);
            l.setEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.networth_summary_menu, menu);
        menu.findItem(R.id.menu_details).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_graph:
                AmortizationGraph fragment = AmortizationGraph.newInstance(amort_model);
                addFragment(fragment);
                break;

            case android.R.id.home:
                // ((OnActivityBackPressedListener)mContext).onActivityBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private void addFragment(AmortizationGraph fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();

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

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.5f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.65f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    private void setData(float prciple, float interest) {


        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        //for (int i = 0; i < 2; i++) {
        // entries.add(new PieEntry((float) (Math.random() * range) + range / 5, parties[i % parties.length]));
        entries.add(new PieEntry(prciple, "Principle"));
        entries.add(new PieEntry(interest, "Interest"));
        //}

        PieDataSet dataSet = new PieDataSet(entries, "Emi Result");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setUsingSliceColorAsValueLineColor(true);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);
        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }

}

package com.purplepath.purplepath.expensesanalysis.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.expenseEDCOC.ExpenseTabMainFragment;
import com.purplepath.purplepath.expensesanalysis.ExpanseanaysisDetailFragment;
import com.purplepath.purplepath.expensesanalysis.ExpanseanaysisMainPageFragment;
import com.purplepath.purplepath.expensesanalysis.dialog.ExpensesAnalysisDialogFragment;
import com.purplepath.purplepath.expensesanalysis.model.Edu_det;
import com.purplepath.purplepath.expensesanalysis.model.ExpensesAnalysisData;
import com.purplepath.purplepath.expensesanalysis.model.ExpensesAnalysisModel;
import com.purplepath.purplepath.expensesanalysis.model.Fam_det;
import com.purplepath.purplepath.expensesanalysis.model.Fd_det;
import com.purplepath.purplepath.expensesanalysis.model.He_det;
import com.purplepath.purplepath.expensesanalysis.model.Purch_det;
import com.purplepath.purplepath.expensesanalysis.model.Sh_det;
import com.purplepath.purplepath.expensesanalysis.model.Sk_det;
import com.purplepath.purplepath.expensesanalysis.model.Tax_det;
import com.purplepath.purplepath.expensesanalysis.model.Trans_det;
import com.purplepath.purplepath.expensesanalysis.model.User_exp_analysis;
import com.purplepath.purplepath.expensesanalysis.model.Ut_det;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.networthanalysis.BarChartActivitySinus;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExpensesAnalysisFragment extends BaseFragment implements View.OnClickListener {

    private ExpensesAnalysisModel expensesAnalysisModel;
    private ExpensesAnalysisData expensesAnalysisData;
    private Fam_det fam_det;
    private ArrayList<User_exp_analysis> userExpAnalysis;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private String char_contri_per, ch_care_suppt_per, edu_per,
            fines_penalty_per, food_per, gifts_per, health_exp_per, hol_vac_per, parent_support_per,
            personal_care_per, pet_care_per, purchases_per, shelter_per,
            skill_dev_per, tax_per, transport_per, utility_per;

    private RelativeLayout mainLayout;
    private LinearLayout titleLayout;
    private PieChart mChart;
    // we're going to display pie chart for smartphones martket shares
    private Float[] yData;
    private String[] xData;
    private Context mContext;
    private ScrollView scrolllinearlayout;
    private TextView errorTextview;
    private Legend l;
    private ArrayList<String> xDataLevel;
    private ArrayList<Float> yDataLevel;
    private int width, chartsize, marginsize;
    private OnActivityBackPressedListener mCallBackListener;
    private FloatingActionButton mEditExpenseFabBtn;
    private LinearLayout linear_layout_view;

    public static ExpensesAnalysisFragment newInstance() {
        ExpensesAnalysisFragment fragment = new ExpensesAnalysisFragment();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        callExpensesAnalysisService();
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View expanalyView = inflater.inflate(R.layout.activity_piechart, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        mCallBackListener.setActionBarTitle("Expense Analysis");
        mleftRelativeLayout = expanalyView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = expanalyView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = expanalyView.findViewById(R.id.relative_right_arrow);
        mainLayout = expanalyView.findViewById(R.id.mainLayout);
        titleLayout = expanalyView.findViewById(R.id.titleLayout);
        scrolllinearlayout = expanalyView.findViewById(R.id.layout_Scroll);
        errorTextview = expanalyView.findViewById(R.id.empty_chart_display);
        mEditExpenseFabBtn= expanalyView.findViewById(R.id.expense_fab_id);
        mEditExpenseFabBtn.setOnClickListener(this);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        linear_layout_view= expanalyView.findViewById(R.id.linear_layout_view);

        if(expensesAnalysisModel!= null){
            callExpensesAnalysisService();
        }
        width = getDeviceWidth();
        double d = width * 0.25;
        chartsize = (int) (d);

        double margindouble = width * 0.03;
        marginsize = (int) (margindouble);

        width = width - chartsize;

        mChart = new PieChart(getActivity());
        // add pie chart to main layout

        FrameLayout.LayoutParams frameLayout = new FrameLayout.LayoutParams(width, width);
        frameLayout.setMargins(0, 0, 0, 0);
        mChart.setLayoutParams(frameLayout);
        mainLayout.addView(mChart);


        RelativeLayout.LayoutParams title_params = (RelativeLayout.LayoutParams) titleLayout.getLayoutParams();
        title_params.setMargins(0, marginsize, 0, marginsize);
        titleLayout.setLayoutParams(title_params);

        //   mainLayout.setBackgroundColor(Color.parseColor("#ffffff"));
       //chart background programatically fixing with tab
        ShapeDrawable sd = new ShapeDrawable();
        sd.setShape(new RectShape());
        sd.getPaint().setColor(Color.GRAY);
        sd.getPaint().setStrokeWidth(5f);
        sd.getPaint().setStyle(Paint.Style.STROKE);
        linear_layout_view.setBackground(sd);


        // configure pie chart
        mChart.setUsePercentValues(false);
        mChart.setDescription("");

        // enable hole and configure
        mChart.setDrawHoleEnabled(false);
//        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        mChart.setDrawSliceText(false);

        mChart.setHighlightPerTapEnabled(true);

        // set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                xDataLevel = new ArrayList<String>();
                yDataLevel = new ArrayList<Float>();
                if (xData[e.getXIndex()].equalsIgnoreCase("Education")) {
                    Edu_det edu_det = fam_det.getEdu_det();
                    setLevelData(edu_det.getEdu_books_per(), 0, "Books");
                    setLevelData(edu_det.getEdu_comp_chgs_per(), 1, "Computer Charges");
                    setLevelData(edu_det.getEdu_ext_tuit_per(), 2, "External Tuition");
                    setLevelData(edu_det.getEdu_tuit_fee_per(), 3, "Tuition Fees");
                    setLevelData(edu_det.getEdu_stat_per(), 4, "Stationaries");
                    if (UtileKit.validateObjectValues(xDataLevel) && !xDataLevel.isEmpty()) {
                        ExpensesAnalysisDialogFragment fragment = new ExpensesAnalysisDialogFragment();
                        addFragmentToCall(fragment,xDataLevel, yDataLevel);
                    }
                } else if (xData[e.getXIndex()].equalsIgnoreCase("Food")) {
                    Fd_det fd_det = fam_det.getFd_det();
                    setLevelData(fd_det.getFd_eat_out_per(), 0, "Eating Out");
                    setLevelData(fd_det.getFd_groc_per(), 1,"Groceries");
                    if (UtileKit.validateObjectValues(xDataLevel) && !xDataLevel.isEmpty()) {
//                        ExpensesAnalysisDialogFragment fragment = ExpensesAnalysisDialogFragment.newInstance(xDataLevel, yDataLevel);
//                        fragment.show(getActivity().getSupportFragmentManager(), "FragmentDialog");
                        ExpensesAnalysisDialogFragment fragment = new ExpensesAnalysisDialogFragment();
                        addFragmentToCall(fragment,xDataLevel, yDataLevel);
                    }

                } else if (xData[e.getXIndex()].equalsIgnoreCase("Healthcare Expenses")) {
                    He_det he_det = fam_det.getHe_det();
                    setLevelData(he_det.getHe_dental_per(), 0, "Dental");
                    setLevelData(he_det.getHe_med_per(), 1, "Medical");
                    setLevelData(he_det.getHe_vision_per(), 2, "Vision");
                    if (UtileKit.validateObjectValues(xDataLevel) && !xDataLevel.isEmpty()) {
//                        ExpensesAnalysisDialogFragment fragment = ExpensesAnalysisDialogFragment.newInstance(xDataLevel, yDataLevel);
//                        fragment.show(getActivity().getSupportFragmentManager(), "FragmentDialog");
                        ExpensesAnalysisDialogFragment fragment = new ExpensesAnalysisDialogFragment();
                        addFragmentToCall(fragment,xDataLevel, yDataLevel);
                    }
                } else if (xData[e.getXIndex()].equalsIgnoreCase("Purchases")) {
                    Purch_det purch_det = fam_det.getPurch_det();
                    setLevelData(purch_det.getPurch_appar_cloth_per(), 0, "Apparels / Clothing");
                    setLevelData(purch_det.getPurch_houhold_items_per(), 1, "Household Items");
                    if (UtileKit.validateObjectValues(xDataLevel) && !xDataLevel.isEmpty()) {
//                        ExpensesAnalysisDialogFragment fragment = ExpensesAnalysisDialogFragment.newInstance(xDataLevel, yDataLevel);
//                        fragment.show(getActivity().getSupportFragmentManager(), "FragmentDialog");
                        ExpensesAnalysisDialogFragment fragment = new ExpensesAnalysisDialogFragment();
                        addFragmentToCall(fragment,xDataLevel, yDataLevel);
                    }
                } else if (xData[e.getXIndex()].equalsIgnoreCase("Shelter")) {
                    Sh_det sh_det = fam_det.getSh_det();
                    setLevelData(sh_det.getSh_hm_maint_per(), 0, "Home Maintenance");
                    setLevelData(sh_det.getSh_rent_per(), 1, "Rent");
                    if (UtileKit.validateObjectValues(xDataLevel) && !xDataLevel.isEmpty()) {
//                        ExpensesAnalysisDialogFragment fragment = ExpensesAnalysisDialogFragment.newInstance(xDataLevel, yDataLevel);
//                        fragment.show(getActivity().getSupportFragmentManager(), "FragmentDialog");
                        ExpensesAnalysisDialogFragment fragment = new ExpensesAnalysisDialogFragment();
                        addFragmentToCall(fragment,xDataLevel, yDataLevel);
                    }
                } else if (xData[e.getXIndex()].equalsIgnoreCase("Skill Development")) {
                    Sk_det sk_det = fam_det.getSk_det();
                    setLevelData(sk_det.getSk_books_per(), 0,"Books");
                    setLevelData(sk_det.getSk_class_per(), 1, "Class");
                    setLevelData(sk_det.getSk_course_per(), 2, "Course");
                    setLevelData(sk_det.getSk_equip_mat_per(), 3, "Equipments / Materials");
                    if (UtileKit.validateObjectValues(xDataLevel) && !xDataLevel.isEmpty()) {
//                        ExpensesAnalysisDialogFragment fragment = ExpensesAnalysisDialogFragment.newInstance(xDataLevel, yDataLevel);
//                        fragment.show(getActivity().getSupportFragmentManager(), "FragmentDialog");
                        ExpensesAnalysisDialogFragment fragment = new ExpensesAnalysisDialogFragment();
                        addFragmentToCall(fragment,xDataLevel, yDataLevel);
                    }
                } else if (xData[e.getXIndex()].equalsIgnoreCase("Tax")) {
                    Tax_det tax_det = fam_det.getTax_det();
                    setLevelData(tax_det.getTax_in_tax_per(), 0, "Income Tax");
                    setLevelData(tax_det.getTax_prop_tax_per(), 1, "Property Tax");
                    if (UtileKit.validateObjectValues(xDataLevel) && !xDataLevel.isEmpty()) {
//                        ExpensesAnalysisDialogFragment fragment = ExpensesAnalysisDialogFragment.newInstance(xDataLevel, yDataLevel);
//                        fragment.show(getActivity().getSupportFragmentManager(), "FragmentDialog");
                        ExpensesAnalysisDialogFragment fragment = new ExpensesAnalysisDialogFragment();
                        addFragmentToCall(fragment,xDataLevel, yDataLevel);
                    }
                } else if (xData[e.getXIndex()].equalsIgnoreCase("Transportation")) {
                    Trans_det trans_det = fam_det.getTrans_det();
                    setLevelData(trans_det.getTrans_commute_per(), 0,"Commute");
                    setLevelData(trans_det.getTrans_park_misce_per(), 1,"Parking / Miscellaneous");
                    setLevelData(trans_det.getTrans_pet_diesel_per(), 2, "Petrol / Diesel / Gas");
                    setLevelData(trans_det.getTrans_trav_per(), 3, "Travel");
                    if (UtileKit.validateObjectValues(xDataLevel) && !xDataLevel.isEmpty()) {
//                        ExpensesAnalysisDialogFragment fragment = ExpensesAnalysisDialogFragment.newInstance(xDataLevel, yDataLevel);
//                        fragment.show(getActivity().getSupportFragmentManager(), "FragmentDialog");
                        ExpensesAnalysisDialogFragment fragment = new ExpensesAnalysisDialogFragment();
                        addFragmentToCall(fragment,xDataLevel, yDataLevel);
                    }
                } else if (xData[e.getXIndex()].equalsIgnoreCase("Utility")) {
                    Ut_det ut_det = fam_det.getUt_det();
                    setLevelData(ut_det.getUt_cab_sat_per(), 0,"Cable / Satellite TV");
                    setLevelData(ut_det.getUt_elec_per(), 1, "Electricity");
                    setLevelData(ut_det.getUt_gas_per(), 2, "Gas");
                    setLevelData(ut_det.getUt_int_board_per(), 3,"Internet / Broadband");
                    setLevelData(ut_det.getUt_mb_ph_per(), 4, "Mobile Phone");
                    setLevelData(ut_det.getUt_tel_per(), 5, "Telephone");
                    setLevelData(ut_det.getUt_water_per(), 6, "Water");
                    if (UtileKit.validateObjectValues(xDataLevel) && !xDataLevel.isEmpty()) {
//                        ExpensesAnalysisDialogFragment fragment = ExpensesAnalysisDialogFragment.newInstance(xDataLevel, yDataLevel);
//                        fragment.show(getActivity().getSupportFragmentManager(), "FragmentDialog");
                        ExpensesAnalysisDialogFragment fragment = new ExpensesAnalysisDialogFragment();
                        addFragmentToCall(fragment,xDataLevel, yDataLevel);
                    }
                }

//                else if (xData[e.getXIndex()].equalsIgnoreCase("Charitable Contributions")) {
//                    Cc_det mCCdet = fam_det.getCc_det();
//                    setLevelData(mCCdet.getCc_char_don(), 0);
//                    setLevelData(mCCdet.getCc_char_don_per(), 1);
//
//                    if (UtileKit.validateObjectValues(xDataLevel) && !xDataLevel.isEmpty()) {
//                        ExpensesAnalysisDialogFragment fragment = new ExpensesAnalysisDialogFragment();
//                        addFragmentToCall(fragment,xDataLevel, yDataLevel);
//                    }
//                }
//                else if (xData[e.getXIndex()].equalsIgnoreCase("Fines and Penalties")) {
//
//                }
//                else if (xData[e.getXIndex()].equalsIgnoreCase("Gifts")) {
//
//                }
//                else if (xData[e.getXIndex()].equalsIgnoreCase("Holiday / Vacation")) {
//
//                }
//                else if (xData[e.getXIndex()].equalsIgnoreCase("Parents Support")) {
//
//                }
//                else if (xData[e.getXIndex()].equalsIgnoreCase("Pet Care")) {
//
//                }
//                else if (xData[e.getXIndex()].equalsIgnoreCase("Personal Care")) {
//
//                }

//                }
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
        return expanalyView;
    }

    private void addFragmentToCall(ExpensesAnalysisDialogFragment fragment, ArrayList<String> xDataLevel, ArrayList<Float> yDataLevel) {

         addFragmenttoStack(ExpensesAnalysisDialogFragment.newInstance(xDataLevel, yDataLevel));

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
            left_layout.setGravity(Gravity.CENTER_VERTICAL);
            left_layout.setLayoutParams(parms_left_layout);



            LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(45, 45);
            parms_legen_layout.setMargins(20, 10, 20, 10);
            LinearLayout legend_layout = new LinearLayout(mContext);
            legend_layout.setLayoutParams(parms_legen_layout);
            legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            legend_layout.setBackgroundColor(colors.get(i));
            left_layout.addView(legend_layout);


            TextView txt_unit = new TextView(mContext);
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
            right_layout.setGravity(Gravity.CENTER_VERTICAL);
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
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void callExpensesAnalysisService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<ExpensesAnalysisModel> call = webServiceObj.callExpensesAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"), "Y");
        call.enqueue(new Callback<ExpensesAnalysisModel>() {
            @Override
            public void onResponse(Call<ExpensesAnalysisModel> call, Response<ExpensesAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success", "success" + response.body());
                expensesAnalysisModel = response.body();
                ArrayList<Float> chartdatalist = new ArrayList<Float>();
                ArrayList<String> chartitledatalist = new ArrayList<String>();
                if (expensesAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    scrolllinearlayout.setVisibility(View.VISIBLE);
                    expensesAnalysisData = expensesAnalysisModel.getData();
                    userExpAnalysis = expensesAnalysisData.getUser_exp_analysis();
                    fam_det = userExpAnalysis.get(0).getFam_det();
                    char_contri_per = fam_det.getChar_contri_per();
                    ch_care_suppt_per = fam_det.getCh_care_suppt_per();
                    edu_per = fam_det.getEdu_per();
                    fines_penalty_per = fam_det.getFines_penalty_per();
                    food_per = fam_det.getFood_per();
                    gifts_per = fam_det.getGifts_per();
                    health_exp_per = fam_det.getHealth_exp_per();
                    hol_vac_per = fam_det.getHol_vac_per();
                    parent_support_per = fam_det.getParent_support_per();
                    personal_care_per = fam_det.getPersonal_care_per();
                    pet_care_per = fam_det.getPet_care_per();
                    purchases_per = fam_det.getPurchases_per();
                    shelter_per = fam_det.getShelter_per();
                    skill_dev_per = fam_det.getSkill_dev_per();
                    tax_per = fam_det.getTax_per();
                    transport_per = fam_det.getTransport_per();
                    utility_per = fam_det.getUtility_per();

                    setChartDataAsList(chartdatalist, chartitledatalist, ch_care_suppt_per, mContext.getResources().getString(R.string.exp_childcare_support));
                    setChartDataAsList(chartdatalist, chartitledatalist, char_contri_per, mContext.getResources().getString(R.string.exp_charitable_contribution));
                    setChartDataAsList(chartdatalist, chartitledatalist, edu_per, mContext.getResources().getString(R.string.exp_education));
                    setChartDataAsList(chartdatalist, chartitledatalist, fines_penalty_per, mContext.getResources().getString(R.string.exp_finesandpenalties));
                    setChartDataAsList(chartdatalist, chartitledatalist, food_per, mContext.getResources().getString(R.string.exp_food));
                    setChartDataAsList(chartdatalist, chartitledatalist, gifts_per, mContext.getResources().getString(R.string.exp_gifts));
                    setChartDataAsList(chartdatalist, chartitledatalist, health_exp_per, mContext.getResources().getString(R.string.exp_healthcareexpenses));
                    setChartDataAsList(chartdatalist, chartitledatalist, hol_vac_per, mContext.getResources().getString(R.string.exp_holidayvacation));
                    setChartDataAsList(chartdatalist, chartitledatalist, parent_support_per, mContext.getResources().getString(R.string.exp_parentssuport));
                    setChartDataAsList(chartdatalist, chartitledatalist, personal_care_per, mContext.getResources().getString(R.string.exp_personalcare));

                    setChartDataAsList(chartdatalist, chartitledatalist, pet_care_per, mContext.getResources().getString(R.string.exp_petcare));
                    setChartDataAsList(chartdatalist, chartitledatalist, purchases_per, mContext.getResources().getString(R.string.exp_purchases));
                    setChartDataAsList(chartdatalist, chartitledatalist, shelter_per, mContext.getResources().getString(R.string.exp_shelter));
                    setChartDataAsList(chartdatalist, chartitledatalist, skill_dev_per, mContext.getResources().getString(R.string.exp_skill_development));

                    setChartDataAsList(chartdatalist, chartitledatalist, tax_per, mContext.getResources().getString(R.string.exp_tax));
                    setChartDataAsList(chartdatalist, chartitledatalist, transport_per, mContext.getResources().getString(R.string.exp_transportation));
                    setChartDataAsList(chartdatalist, chartitledatalist, utility_per, mContext.getResources().getString(R.string.exp_utility));

                }
                if (UtileKit.validateObjectValues(chartdatalist)
                        && UtileKit.validateObjectValues(chartitledatalist) &&
                        !chartdatalist.isEmpty() && !chartitledatalist.isEmpty()) {
                    scrolllinearlayout.setVisibility(View.VISIBLE);
                    yData = new Float[chartdatalist.size()];
                    yData = chartdatalist.toArray(yData);

                    xData = new String[chartitledatalist.size()];
                    xData = chartitledatalist.toArray(xData);
                    addData();
                } else {
                    scrolllinearlayout.setVisibility(View.GONE);
                    errorTextview.setVisibility(View.VISIBLE);
                    errorTextview.setText(HomePageActivity.errorMessageInChart);
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                }

            }

            @Override
            public void onFailure(Call<ExpensesAnalysisModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });

    }


    private void setChartDataAsList(ArrayList<Float> chartdatalist, ArrayList<String> chartitledatalist,
                                    String value, String title) {

        if (UtileKit.validateObjectValues(value) && !value.equalsIgnoreCase("0")) {
            Float valuepervalue = Float.parseFloat(value);
            chartdatalist.add(valuepervalue);
            chartitledatalist.add(title);
        }
    }

    private void setLevelData(String leveldata, int i, String exp_cable) {
        if (UtileKit.validateObjectValues(leveldata) && Float.parseFloat(leveldata) > 0) {
            yDataLevel.add(Float.parseFloat(leveldata));
            xDataLevel.add(exp_cable);
        }
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
            case R.id.relative_right_arrow: {
               addFragmenttoStack(new BarChartActivitySinus());
            }
            break;
            case R.id.expense_fab_id:
                addFragmenttoStack(new ExpenseTabMainFragment());
                break;

        }
    }
}

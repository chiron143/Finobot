package com.purplepath.purplepath.healthinsurance;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.purplepath.purplepath.apputiles.ChartValueFormatter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.propertyinsurance.model.Motor_Proper_Health_Model;
import java.math.BigInteger;
import java.util.ArrayList;



/**
 * Created by pravinr on 11/7/17.
 */

public class FamilyHealthPlan extends BaseFragment implements View.OnClickListener {

    Context mContext;
    TextView errorTextview;
    private LinearLayout parentView;
    private HorizontalBarChart family_chart;
    Motor_Proper_Health_Model motor_Proper_Health_Model;

    private BigInteger family_suggessted;
    private ArrayList<BarEntry> BARENTRYHORIZONTAL ;
    private ArrayList<Float> unitCount ;
    private Typeface tf;
    private BarDataSet BardatasetHorizontal;

    private ScrollView layout_Scroll;
    private BigInteger total_coverage = BigInteger.ZERO;
    private BigInteger total_rider=BigInteger.ZERO;
    private BigInteger total_topup=BigInteger.ZERO;
    private BigInteger total_super_topup=BigInteger.ZERO;
    private BigInteger total_basic_plan=BigInteger.ZERO;

    private BigInteger total_add_all;
    private BigInteger total_sub_all;
    private BigInteger total_coverage_subract;

    private BigInteger employer_total_coverage=BigInteger.ZERO;
    private BigInteger employer_total_rider=BigInteger.ZERO;
    private BigInteger employer_total_topup=BigInteger.ZERO;
    private BigInteger employer_total_super_topup=BigInteger.ZERO;
    private BigInteger employer_total_basic_plan=BigInteger.ZERO;

    private BigInteger employer_total_add_all;
    private BigInteger employer_total_sub_all;

    private BigInteger personally_total_coverage=BigInteger.ZERO;
    private BigInteger personally_total_rider=BigInteger.ZERO;
    private BigInteger personally_total_topup=BigInteger.ZERO;
    private BigInteger personally_total_super_topup=BigInteger.ZERO;
    private BigInteger personally_total_basic_plan=BigInteger.ZERO;

    private BigInteger personally_total_add_all;
    private BigInteger personally_total_sub_all;

    ArrayList<String> xDatadetailscolor = new ArrayList<String>();
    ArrayList<String> xDatadetailsData = new ArrayList<String>();


    private BigInteger recommend=BigInteger.ZERO;



    public static FamilyHealthPlan newInstance(Motor_Proper_Health_Model motor_proper_health_model) {
        Bundle args = new Bundle();
        args.putSerializable("motor_Proper_Health_Model",motor_proper_health_model);
        FamilyHealthPlan fragment = new FamilyHealthPlan();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View familyview=inflater.inflate(R.layout.fragment_family_healthplan, container, false);
        errorTextview = familyview.findViewById(R.id.empty_chart_display);
        family_chart = familyview.findViewById(R.id.family_chart);
        parentView= familyview.findViewById(R.id.parentViewId);
        layout_Scroll= familyview.findViewById(R.id.layout_Scroll);

        BARENTRYHORIZONTAL = new ArrayList<>();
        unitCount = new ArrayList<>();
        tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        XAxis xl = family_chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);


        YAxis yl = family_chart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawLabels(false);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.1f);
        yl.setStartAtZero(false);
        YAxis yr = family_chart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setStartAtZero(false);
        yr.setValueFormatter(new ChartValueFormatter());

        Legend l = family_chart.getLegend();
        l.setEnabled(false);
        l.setTextSize(8 * getResources().getDisplayMetrics().density);
        l.setFormSize(15f);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(15f);

        if(null!=getArguments())
        {
            if(getArguments().containsKey("motor_Proper_Health_Model"))
                motor_Proper_Health_Model = (Motor_Proper_Health_Model) getArguments().getSerializable("motor_Proper_Health_Model");

            float[]  xBARENTRYPERSONALLY=null;
            float[]  xBARENTRYEMPLOYER=null;
            float[]  xBARENTRYCOVERAGE=null;
            float[]  xBARENTRYSUGGESSTED=null;

            int [] colour=new int[17];


            if (motor_Proper_Health_Model.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                try{
                    if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan()!=null){

                    family_suggessted= (new BigInteger(motor_Proper_Health_Model.
                            getData().getHealth_ins_plan().getFamily_floater_plan().getSuggested()));

                    //Total coverage
                    int length = motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                 getAvailable().size();

                        recommend=BigInteger.ZERO;

                    for (int i = 0; i < length; i++) {

                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().
                                getFamily_floater_plan().getAvailable().get(i).getCoverage()!=null)
                        total_coverage=total_coverage.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().
                                getFamily_floater_plan().getAvailable().get(i).getCoverage()));
                        Log.d("total_coverage","total_coverage"+total_coverage);

                        //Sub Insurance find ins_product_categories
                        int sub_length=motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                getAvailable().get(i).getSub_insur().size();
                        for (int j=0;j<sub_length;j++){

                            if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                    getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().isEmpty()){
                            if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                    getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().equals("Rider")){
                         total_rider=total_rider.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                        getAvailable().get(i).getSub_insur().get(j).getSum_assured()));
                                Log.d("","total_rider"+total_rider);
                            }
                            }

                            if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                    getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().isEmpty()){
                            if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                    getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().equals("Top Up")){
                         total_topup=total_topup.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                       getAvailable().get(i).getSub_insur().get(j).getSum_assured()));
                                Log.d("","total_topup"+total_topup);
                            }
                            }

                            if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                    getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().isEmpty()) {
                                if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                        getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().equals("Super Top Up")) {
                                    total_super_topup = total_super_topup.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(i).getSub_insur().get(j).getSum_assured()));
                                    Log.d("", "total_super_topup" + total_super_topup);
                                }
                            }

                            if(!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                    getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().isEmpty()){
                            if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                    getAvailable().get(i).getSub_insur().get(j).getIns_prod_cat().equals("Basic Plan")){
                         total_basic_plan=total_basic_plan.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                       getAvailable().get(i).getSub_insur().get(j).getSum_assured()));
                                Log.d("","total_basic_plan"+total_basic_plan);
                            }
                            }

                            total_add_all=total_rider.add(total_topup).add(total_super_topup).add(total_basic_plan);
                            total_sub_all=family_suggessted.subtract(total_add_all);


                            //If less than zero value come below condition apply
                            if(total_sub_all.signum() == 1){
                                recommend=total_sub_all;
                            }else {
                                recommend=BigInteger.ZERO;
                            }

                        }

                    }
                    if(total_coverage!=null&&total_add_all!=null)

                    total_coverage_subract=total_coverage.subtract(total_add_all);




                    /**
                     * employer provided
                     */
                    int emp_length = motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                            getAvailable().size();

                    for (int k = 0; k < emp_length; k++) {
                        if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().
                                get(k).getPlan_type()!=null) {

                            if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().
                                    get(k).getPlan_type().equals("Employer Provided")) {
                                employer_total_coverage = employer_total_coverage.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().
                                        getFamily_floater_plan().getAvailable().get(k).getCoverage()));
                                Log.d("", "employer_total_coverage" + employer_total_coverage);

                                //Sub Insurance find ins_product_categories employer provided
                                int emp_provided_sub_length = motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                        getAvailable().get(k).getSub_insur().size();
                                for (int m = 0; m < emp_provided_sub_length; m++) {

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().equals("Rider")) {
                                            employer_total_rider = employer_total_rider.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(k).getSub_insur().get(m).getSum_assured()));
                                            Log.d("", "employer_total_rider" + employer_total_rider);
                                        }
                                    }

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().equals("Top Up")) {
                                            employer_total_topup = employer_total_topup.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(k).getSub_insur().get(m).getSum_assured()));
                                            Log.d("", "employer_total_topup" + employer_total_topup);
                                        }
                                    }

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().equals("Super Top Up")) {
                                            employer_total_super_topup = employer_total_super_topup.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(k).getSub_insur().get(m).getSum_assured()));
                                            Log.d("", "employer_total_super_topup" + employer_total_super_topup);
                                        }
                                    }

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(k).getSub_insur().get(m).getIns_prod_cat().equals("Basic Plan")) {
                                            employer_total_basic_plan = employer_total_basic_plan.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(k).getSub_insur().get(m).getSum_assured()));
                                            Log.d("", "employer_total_basic_plan" + employer_total_basic_plan);
                                        }
                                    }


                                    employer_total_add_all = employer_total_rider.add(employer_total_topup).add(employer_total_super_topup).add(employer_total_basic_plan);
                                    employer_total_sub_all = family_suggessted.subtract(employer_total_add_all);

                                }
                            }
                        }


                    }

                    /**
                     * personally subscribed
                     */
                    int per_length = motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                            getAvailable().size();
                    for(int n=0;n<per_length;n++) {
                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().get(n).
                                getPlan_type() != null){
                            if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().getAvailable().get(n).
                                    getPlan_type().equals("Personally Subscribed")) {
                                personally_total_coverage = personally_total_coverage.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().
                                        getFamily_floater_plan().getAvailable().get(n).getCoverage()));
                                Log.d("", "personally_total_coverage" + personally_total_coverage);
                                //Sub Insurance find ins_product_categories personally provided
                                int emp_provided_sub_length = motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                        getAvailable().get(n).getSub_insur().size();
                                for (int o = 0; o < emp_provided_sub_length; o++) {

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().equals("Rider")) {
                                            personally_total_rider = personally_total_rider.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(n).getSub_insur().get(o).getSum_assured()));
                                            Log.d("", "personally_total_rider" + personally_total_rider);
                                        }
                                    }

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().equals("Top Up")) {
                                            personally_total_topup = employer_total_topup.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(n).getSub_insur().get(o).getSum_assured()));
                                            Log.d("", "personally_total_topup" + personally_total_topup);
                                        }
                                    }

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().equals("Super Top Up")) {
                                            personally_total_super_topup = personally_total_super_topup.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(n).getSub_insur().get(o).getSum_assured()));
                                            Log.d("", "personally_total_super_topup" + personally_total_super_topup);
                                        }
                                    }

                                    if (!motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                            getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().isEmpty()) {
                                        if (motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                getAvailable().get(n).getSub_insur().get(o).getIns_prod_cat().equals("Basic Plan")) {
                                            personally_total_basic_plan = personally_total_basic_plan.add(new BigInteger(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                                                    getAvailable().get(n).getSub_insur().get(o).getSum_assured()));
                                            Log.d("", "personally_total_basic_plan" + personally_total_basic_plan);
                                        }
                                    }

                                    personally_total_add_all = personally_total_rider.add(personally_total_topup).add(personally_total_super_topup).add(personally_total_basic_plan);
                                    personally_total_sub_all = family_suggessted.subtract(personally_total_add_all);
                                }

                            }

                    }

                    }


                    if(motor_Proper_Health_Model.getData().getHealth_ins_plan().getFamily_floater_plan().
                            equals("0")){
                        errorTextview.setVisibility(View.VISIBLE);
                        layout_Scroll.setVisibility(View.GONE);
                    }else {

                        /**
                         * 0th suggested bar
                         */
                        xBARENTRYSUGGESSTED = new float[1];
                        if (family_suggessted != null) {
                            xBARENTRYSUGGESSTED[0] = Float.parseFloat(String.valueOf(family_suggessted));
                            colour[0] = ColorTemplate.rgb("#53adfc");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#53adfc")));
                            xDatadetailsData.add("Suggessted : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(family_suggessted)))));
                        }

                        /**
                         * 1st Barchart topup,supertopup,basic,rider
                         */
                        xBARENTRYCOVERAGE=new float[6];
                        if (total_coverage_subract != null) {
                            xBARENTRYCOVERAGE[0] = Float.parseFloat(String.valueOf(total_coverage_subract));
                            colour[1] = ColorTemplate.rgb("#FFC107");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FFC107")));
                            xDatadetailsData.add("Total Coverage : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(total_coverage_subract)))));
                        }
                        if (total_basic_plan != null) {
                            xBARENTRYCOVERAGE[1] = Float.parseFloat(String.valueOf(total_basic_plan));
                            colour[2] = ColorTemplate.rgb("#008000");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#008000")));
                            xDatadetailsData.add("Basic Plan : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(total_basic_plan)))));
                        }
                        if (total_topup != null) {
                            xBARENTRYCOVERAGE[2] = Float.parseFloat(String.valueOf(total_topup));
                            colour[3] = ColorTemplate.rgb("#00FFFF");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#00FFFF")));
                            xDatadetailsData.add("Topup : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(total_topup)))));
                        }
                        if (total_super_topup != null) {
                            xBARENTRYCOVERAGE[3] = Float.parseFloat(String.valueOf(total_super_topup));
                            colour[4] = ColorTemplate.rgb("#FF00FF");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF00FF")));
                            xDatadetailsData.add("Super Topup : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(total_super_topup)))));
                        }

                        if (total_rider != null) {
                            xBARENTRYCOVERAGE[4] = Float.parseFloat(String.valueOf(total_rider));
                            colour[5] = ColorTemplate.rgb("#0000FF");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#0000FF")));
                            xDatadetailsData.add("Rider : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(total_rider)))));
                        }

                        if (recommend != null) {
                            xBARENTRYCOVERAGE[5] = Float.parseFloat(String.valueOf(recommend));
                            colour[6] = ColorTemplate.rgb("#FF0000");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF0000")));
                            xDatadetailsData.add("Recommended : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(recommend)))));
                        }

                        /**
                         * 2nd bar employer bar
                         */
                        xBARENTRYEMPLOYER=new float[5];
                        if (employer_total_basic_plan != null) {
                            xBARENTRYEMPLOYER[0] = Float.parseFloat(String.valueOf(employer_total_basic_plan));
                            colour[7] = ColorTemplate.rgb("#2E64FE");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#2E64FE")));
                            xDatadetailsData.add("Employer Basic Plan : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(employer_total_basic_plan)))));
                        }
                        if (employer_total_topup != null) {
                            xBARENTRYEMPLOYER[1] = Float.parseFloat(String.valueOf(employer_total_topup));
                            colour[8] = ColorTemplate.rgb("#DF3A01");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#DF3A01")));
                            xDatadetailsData.add("Employer Topup : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(employer_total_topup)))));
                        }
                        if (employer_total_super_topup != null) {
                            xBARENTRYEMPLOYER[2] = Float.parseFloat(String.valueOf(employer_total_super_topup));
                            colour[9] = ColorTemplate.rgb("#610B5E");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#610B5E")));
                            xDatadetailsData.add("Employer Super Topup : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(employer_total_super_topup)))));
                        }

                        if (employer_total_rider != null) {
                            xBARENTRYEMPLOYER[3] = Float.parseFloat(String.valueOf(employer_total_rider));
                            colour[10] = ColorTemplate.rgb("#3ADF00");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#3ADF00")));
                            xDatadetailsData.add("Employer Rider : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(employer_total_rider)))));
                        }

                        //Removing Employer recommended for  client change
                       /* if (employer_total_sub_all != null) {
                            xBARENTRYEMPLOYER[4] = Float.parseFloat(String.valueOf(employer_total_sub_all));
                            colour[11] = ColorTemplate.rgb("#FF0000");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF0000")));
                            xDatadetailsData.add("Employer Recommended : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(employer_total_sub_all)))));
                        }*/


                        /**
                         * 3rd bar Personally provider
                         */
                        xBARENTRYPERSONALLY=new float[5];
                        if (personally_total_basic_plan != null) {
                            xBARENTRYPERSONALLY[0] = Float.parseFloat(String.valueOf(personally_total_basic_plan));
                            colour[12] = ColorTemplate.rgb("#F5A9F2");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#F5A9F2")));
                            xDatadetailsData.add("Personally Basic Plan : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(personally_total_basic_plan)))));
                        }
                        if (personally_total_topup != null) {
                            xBARENTRYPERSONALLY[1] = Float.parseFloat(String.valueOf(personally_total_topup));
                            colour[13] = ColorTemplate.rgb("#424242");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#424242")));
                            xDatadetailsData.add("Personally Topup : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(personally_total_topup)))));
                        }
                        if (personally_total_super_topup != null) {
                            xBARENTRYPERSONALLY[2] = Float.parseFloat(String.valueOf(personally_total_super_topup));
                            colour[14] = ColorTemplate.rgb("#2EFE64");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#2EFE64")));
                            xDatadetailsData.add("Personally Super Topup : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(personally_total_super_topup)))));
                        }

                        if (personally_total_rider != null) {
                            xBARENTRYPERSONALLY[3] = Float.parseFloat(String.valueOf(personally_total_rider));
                            colour[15] = ColorTemplate.rgb("#F78181");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#F78181")));
                            xDatadetailsData.add("Personally Rider : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(personally_total_rider)))));
                        }

                        //Removing Personally recommended for  client change

                      /*  if (personally_total_sub_all != null) {
                            xBARENTRYPERSONALLY[4] = Float.parseFloat(String.valueOf(personally_total_sub_all));
                            colour[16] = ColorTemplate.rgb("#FF0000");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF0000")));
                            xDatadetailsData.add("Personally Recommended : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(personally_total_sub_all)))));
                        }*/






                    }




                    BARENTRYHORIZONTAL.add(new BarEntry(xBARENTRYSUGGESSTED, 0));
                    BARENTRYHORIZONTAL.add(new BarEntry(xBARENTRYCOVERAGE,1));
                    BARENTRYHORIZONTAL.add(new BarEntry(xBARENTRYEMPLOYER,2));
                    BARENTRYHORIZONTAL.add(new BarEntry(xBARENTRYPERSONALLY,3));
                    BardatasetHorizontal = new BarDataSet(BARENTRYHORIZONTAL, "");

                    BardatasetHorizontal.setColors(colour);
                    BardatasetHorizontal.setValueFormatter(new ChartValueFormatter());
                    BarData data;
                    data = new BarData(getXAxisValues(), BardatasetHorizontal);
                    family_chart.setData(data);
                    family_chart.setDescription(" ");
                    data.setDrawValues(false);
                    Legend horizontalline = family_chart.getLegend();
                    horizontalline.setEnabled(false);
                    horizontalline.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                    family_chart.setPinchZoom(false);
                    family_chart.setDoubleTapToZoomEnabled(false);
                    family_chart.setTouchEnabled(false);
                    family_chart.invalidate();
                    setDataForCheckbox(xDatadetailsData, xDatadetailscolor);
                }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {

            }
        }
        return familyview;
    }






    private void setDataForCheckbox(ArrayList<String> xDataCheckbox, ArrayList<String> colour) {
        try {

            for (int i = 0; i <  xDataCheckbox.size(); i++) {
                LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout parent_layout = new LinearLayout(mContext);
                parent_layout.setWeightSum(2);
                parent_layout.setOrientation(LinearLayout.HORIZONTAL);
                parent_param_layout.setMargins(10, 0, 0, 10);
                parent_layout.setLayoutParams(parent_param_layout);

                LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
                legend_layout.setBackgroundColor(Integer.parseInt(colour.get(i)));
                left_layout.addView(legend_layout);

                TextView txt_unit = new TextView(mContext);
                txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                LinearLayout.LayoutParams txtLeft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txt_unit.setLayoutParams(txtLeft_params);

                txt_unit.setText(xDataCheckbox.get(i));
                left_layout.addView(txt_unit);

                parent_layout.addView(left_layout);
                parentView.addView(parent_layout);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Suggested");
        xAxis.add("Total Coverage");
        xAxis.add("Employer Provided");
        xAxis.add("Personally Subscribed");
        return xAxis;
    }


    private float floatConvertion(String val) {
        float convVal = 0;
        try {
            convVal = Float.parseFloat(val);
        } catch (NumberFormatException e) {
        }
        return convVal;
    }

    @Override
    public void onClick(View v) {

    }
}

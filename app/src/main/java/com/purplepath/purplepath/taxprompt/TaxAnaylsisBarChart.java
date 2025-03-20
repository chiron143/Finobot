package com.purplepath.purplepath.taxprompt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.purplepath.purplepath.apputiles.ChartValueFormatter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxproduct.TaxPlanProductDetailFragment;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.Ded_by_prods;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.TaxPromptNewModel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.key;
import static java.lang.Float.parseFloat;

/**
 * Created by pravinr on 2/6/18.
 */

public class TaxAnaylsisBarChart extends BaseFragment implements View.OnClickListener {

    ArrayList<Float> xDataEntitlementBar = null;

    ArrayList<Float> xDataAvailedBar = null;

    ArrayList<Float> xDataPendingBar = null;

    private ArrayList<Integer> colorarray = new ArrayList<>(),
            entitle_colors = new ArrayList<>(),
            availed_colors = new ArrayList<>(),
            pending_colors = new ArrayList<>();

    private ArrayList<String> sectionArray = new ArrayList<>(),
            entitle_section = new ArrayList<>(),
            availed_section = new ArrayList<>(),
            pending_section = new ArrayList<>();

    ArrayList<BarEntry> BARENTRY;

    private CombinedChart barChart;

    private Typeface tf;

    private LinearLayout parentView, parentViewAvailed_layout, parentViewPending_layout;

    private RelativeLayout mainLayout;

    private Context mContext;

    private OnActivityBackPressedListener mCallBackListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private TaxPromptNewModel taxAnalysistModel;

    //Entitlement
    HashSet<String> tax_entitlement_hashset = new HashSet<String>();

    HashMap<String, ArrayList<Ded_by_prods>> mfilterarrayEntitlement = new HashMap<>();

    HashMap<String, BigInteger> mEntitlementTotalValue = new HashMap<>();

    BigInteger entitle = BigInteger.ZERO;

    //Availed
    HashSet<String> taxavailed_hashset = new HashSet<String>();

    HashMap<String, ArrayList<Ded_by_prods>> mfilterarrayAvailed = new HashMap<>();

    BigInteger availed = BigInteger.ZERO;

    BigInteger entitle_greater = BigInteger.ZERO;

    HashMap<String, BigInteger> mAvailedTotalValue = new HashMap<>();

    //Pending
    BigInteger sub_entitlement_availed = BigInteger.ZERO;

    //Bottom Legends
    private LinearLayout entitlement_title_card, availed_title_card, pending_title_card;

    boolean flagToggleButton = false;

    private ImageView entitle_plus, availed_plus, pending_plus;


    ArrayList<String> arrayListavail = new ArrayList<String>();
    ArrayList<String> arrayListentil = new ArrayList<String>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
            setHasOptionsMenu(true);

        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View taxAnalysisView = inflater.inflate(R.layout.frag_taxanalysis_bar_chart, container, false);
        mCallBackListener.setActionBarTitle("Tax Plan Details");

        entitle_colors.clear();
        availed_colors.clear();
        pending_colors.clear();

        entitle_section.clear();
        availed_section.clear();
        pending_section.clear();

        sectionArray.clear();
        colorarray.clear();

        barChart = taxAnalysisView.findViewById(R.id.chart);

        parentView = taxAnalysisView.findViewById(R.id.parentViewId);
        parentViewAvailed_layout = taxAnalysisView.findViewById(R.id.parentViewAvailed_layout);
        parentViewPending_layout = taxAnalysisView.findViewById(R.id.parentViewPending_layout);

        entitlement_title_card = taxAnalysisView.findViewById(R.id.entitlement_title_card);
        entitlement_title_card.setOnClickListener(this);

        availed_title_card = taxAnalysisView.findViewById(R.id.availed_title_card);
        availed_title_card.setOnClickListener(this);

        pending_title_card = taxAnalysisView.findViewById(R.id.pending_title_card);
        pending_title_card.setOnClickListener(this);

        entitle_plus = taxAnalysisView.findViewById(R.id.entitle_plus);
        availed_plus = taxAnalysisView.findViewById(R.id.availed_plus);
        pending_plus = taxAnalysisView.findViewById(R.id.pending_plus);
        entitle_plus.setOnClickListener(this);
        availed_plus.setOnClickListener(this);
        pending_plus.setOnClickListener(this);

        mainLayout = taxAnalysisView.findViewById(R.id.mainLayout);
        mleftRelativeLayout = taxAnalysisView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = taxAnalysisView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = taxAnalysisView.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setVisibility(View.GONE);


        LargeValueFormatter custom = new LargeValueFormatter();
        tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);
        ShapeDrawable sd = new ShapeDrawable();
        sd.setShape(new RectShape());
        sd.getPaint().setColor(Color.GRAY);
        sd.getPaint().setStrokeWidth(5f);
        sd.getPaint().setStyle(Paint.Style.STROKE);
        mainLayout.setBackground(sd);
        YAxis yl = barChart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawLabels(true);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.1f);
        yl.setValueFormatter(new ChartValueFormatter());
        yl.setStartAtZero(false);
        YAxis yr = barChart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawLabels(true);
        yr.setDrawGridLines(false);
        yr.setStartAtZero(false);
        yr.setValueFormatter(new ChartValueFormatter());
        Legend l = barChart.getLegend();
        l.setEnabled(false);
        l.setTextSize(8 * getResources().getDisplayMetrics().density);
        l.setFormSize(15f);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(15f);

        callTaxAnalysisService();

        return taxAnalysisView;
    }


    private void callTaxAnalysisService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String finYr = String.valueOf(year-1);
        Call<TaxPromptNewModel> call = webServiceObj.callTaxPromptService_new(UtileKit.getPersistedPurplePathPref("user_id"), "FY"+finYr);
        call.enqueue(new Callback<TaxPromptNewModel>() {
            @Override
            public void onResponse(Call<TaxPromptNewModel> call, Response<TaxPromptNewModel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    taxAnalysistModel = response.body();
                    if (taxAnalysistModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        groupingEntitlementProducts(taxAnalysistModel);

                        groupingAvailedDedbyProd(taxAnalysistModel);

                        pending();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<TaxPromptNewModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    //Entitlement funtion starting
    private void groupingEntitlementProducts(TaxPromptNewModel taxAnalysistModel) {

        if (taxAnalysistModel.getData().getDed_by_prod() != null) {
            int length = taxAnalysistModel.getData().getDed_by_prod().size();

            String diability_flag = taxAnalysistModel.getData().getDisability_flag();

            for (int i = 0; i < length; i++) {
                tax_entitlement_hashset.add(taxAnalysistModel.getData().getDed_by_prod().get(i).getTax_section());
            }


            // ArrayList<String> arrayList = new ArrayList<String>(tax_entitlement_hashset);

            arrayListentil = new ArrayList<String>(tax_entitlement_hashset);

            Collections.sort(arrayListentil);

            for (int j = 0; j < arrayListentil.size(); j++) {
                String str_obj = arrayListentil.get(j);
                if (str_obj != null) {
                    ArrayList<Ded_by_prods> taxsection_heading = new ArrayList<Ded_by_prods>();

                    for (int k = 0; k < length; k++) {
                        if (str_obj.equals(taxAnalysistModel.getData().getDed_by_prod().get(k).getTax_section())) {

                            String disability_inner_array = taxAnalysistModel.getData().getDed_by_prod().get(k).
                                    getDisability_flag();

                            try {
                                if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("Y"))) {
                                    taxsection_heading.add(taxAnalysistModel.getData().getDed_by_prod().get(k));
                                } else if ((disability_inner_array.equalsIgnoreCase("Y") && diability_flag.equalsIgnoreCase("Y"))) {
                                    taxsection_heading.add(taxAnalysistModel.getData().getDed_by_prod().get(k));
                                } else if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("N"))) {
                                    taxsection_heading.add(taxAnalysistModel.getData().getDed_by_prod().get(k));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    mfilterarrayEntitlement.put(str_obj, taxsection_heading);
                }
            }
            filterSection(mfilterarrayEntitlement);
        }
    }

    private void filterSection(HashMap<String, ArrayList<Ded_by_prods>> mfilterarrayEntitlement) {
        int[] xDataEntitlementcolour = new int[]{
                ColorTemplate.rgb("#53adfc"),
                ColorTemplate.rgb("#53fcf3"),
                ColorTemplate.rgb("#fc8553"),
                ColorTemplate.rgb("#8BC34A"),
                ColorTemplate.rgb("#FFC107"),
                ColorTemplate.rgb("#7d53fc"),
                ColorTemplate.rgb("#869c9b"),
                ColorTemplate.rgb("#53adfc"),
                ColorTemplate.rgb("#da53fc"),
                ColorTemplate.rgb("#c18911"),
                ColorTemplate.rgb("#5386fc"),
                ColorTemplate.rgb("#FFC107"),
                ColorTemplate.rgb("#8BC34A"),
                ColorTemplate.rgb("#E91E63"),
                ColorTemplate.rgb("#B71C1C"),
                ColorTemplate.rgb("#4A148C"),
                ColorTemplate.rgb("#006064"),
                ColorTemplate.rgb("#827717"),
                ColorTemplate.rgb("#FF3D00"),
                ColorTemplate.rgb("#00FF00"),
                ColorTemplate.rgb("#008080"),
                ColorTemplate.rgb("#800000"),
                ColorTemplate.rgb("#333300"),
                ColorTemplate.rgb("#c67a00"),
                ColorTemplate.rgb("#ffbe60"),
                ColorTemplate.rgb("#ffd99b"),
                ColorTemplate.rgb("#e9bb71"),
                ColorTemplate.rgb("#e8ac4a"),
                ColorTemplate.rgb("#af7922"),
                ColorTemplate.rgb("#c77f09"),
                ColorTemplate.rgb("#7e5718"),
                ColorTemplate.rgb("#cc0000"),
                ColorTemplate.rgb("#ff0000"),
                ColorTemplate.rgb("#fe5a5a"),
                ColorTemplate.rgb("#ff2020"),
                ColorTemplate.rgb("#ff9090"),
                ColorTemplate.rgb("#c51414"),
                ColorTemplate.rgb("#8e0202"),
                ColorTemplate.rgb("#921717"),
                ColorTemplate.rgb("#d35e5e"),
                ColorTemplate.rgb("#970b0b"),
                ColorTemplate.rgb("#869c9b"),
                ColorTemplate.rgb("#7eaeac"),
                ColorTemplate.rgb("#598482"),
                ColorTemplate.rgb("#58b1ad"),
                ColorTemplate.rgb("#37aba6"),
                ColorTemplate.rgb("#1f827d"),
                ColorTemplate.rgb("#35d5ce"),
                ColorTemplate.rgb("#05c2b9"),
                ColorTemplate.rgb("#2ed8d0"),
                ColorTemplate.rgb("#62fff8"),
                ColorTemplate.rgb("#237975"),
                ColorTemplate.rgb("#13726e"),
                Color.rgb(181, 205, 26)};

        ArrayList<String> xDataEntitlementSection = new ArrayList<>();
        int i = 0;
        xDataEntitlementBar = new ArrayList<>();
        for (String key : arrayListentil) {
            //  for(String key:mfilterarrayEntitlement.keySet()){
            String section = "";

            int innerlength = mfilterarrayEntitlement.get(key).size();

            if (innerlength != 0) {

                entitle = BigInteger.ZERO;
                xDataEntitlementSection = new ArrayList<>();
                for (int j = 0; j < innerlength; j++) {
                    if (mfilterarrayEntitlement.get(key).get(j).getTax_section() != null) {
                        section = mfilterarrayEntitlement.get(key).get(j).getTax_section();
                    }
                    if (mfilterarrayEntitlement.get(key).get(j).getEntitled() != null) {
                        entitle = new BigInteger(mfilterarrayEntitlement.get(key).get(j).getEntitled());
                    }


                }
                //this hashmap used for entitlement-availed = pending value calculation
                mEntitlementTotalValue.put(key, entitle);

                //Entitlement Bar
                xDataEntitlementSection.add(section + " : " + "₹ " + UtileKit.formatedNumbers(entitle));
                xDataEntitlementBar.add(Float.parseFloat(String.valueOf(entitle)));
                entitle_colors.add(xDataEntitlementcolour[i]);

                System.out.println("COLOR :" + xDataEntitlementcolour[i] + " : SECTION :" + xDataEntitlementSection);
                entitle_section.addAll(xDataEntitlementSection);
                i = i + 1;

            }
        }
    }
    //Entitlement funtion ending


    //Availed card starting
    private void groupingAvailedDedbyProd(TaxPromptNewModel taxAnalysistModel) {

        if (taxAnalysistModel.getData().getDed_by_prod() != null) {

            int length = taxAnalysistModel.getData().getDed_by_prod().size();

            String diability_flag = taxAnalysistModel.getData().getDisability_flag();

            for (int i = 0; i < length; i++) {
                taxavailed_hashset.add(taxAnalysistModel.getData().getDed_by_prod().get(i).getTax_section());
            }

            arrayListavail = new ArrayList<String>(taxavailed_hashset);

            Collections.sort(arrayListavail);

            for (int j = 0; j < arrayListavail.size(); j++) {

                String str_obj = arrayListavail.get(j);
                if (str_obj != null) {
                    ArrayList<Ded_by_prods> taxsection_heading = new ArrayList<Ded_by_prods>();

                    for (int k = 0; k < length; k++) {
                        if (str_obj.equals(taxAnalysistModel.getData().getDed_by_prod().get(k).getTax_section())) {

                            String disability_inner_array = taxAnalysistModel.getData().getDed_by_prod().get(k).
                                    getDisability_flag();

                            try {
                                if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("Y"))) {
                                    taxsection_heading.add(taxAnalysistModel.getData().getDed_by_prod().get(k));
                                } else if ((disability_inner_array.equalsIgnoreCase("Y") && diability_flag.equalsIgnoreCase("Y"))) {
                                    taxsection_heading.add(taxAnalysistModel.getData().getDed_by_prod().get(k));
                                } else if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("N"))) {
                                    taxsection_heading.add(taxAnalysistModel.getData().getDed_by_prod().get(k));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }
                    mfilterarrayAvailed.put(str_obj, taxsection_heading);
                }
            }
            filterAvailedSection(mfilterarrayAvailed);
        }
    }

    private void filterAvailedSection(HashMap<String, ArrayList<Ded_by_prods>> mfilterarrayAvailed) {

        xDataAvailedBar = new ArrayList<>();

        int[] xDataAvailedcolour = new int[]{
                ColorTemplate.rgb("#53adfc"),
                ColorTemplate.rgb("#53fcf3"),
                ColorTemplate.rgb("#fc8553"),
                ColorTemplate.rgb("#8BC34A"),
                ColorTemplate.rgb("#FFC107"),
                ColorTemplate.rgb("#7d53fc"),
                ColorTemplate.rgb("#869c9b"),
                ColorTemplate.rgb("#53adfc"),
                ColorTemplate.rgb("#da53fc"),
                ColorTemplate.rgb("#c18911"),
                ColorTemplate.rgb("#5386fc"),
                ColorTemplate.rgb("#FFC107"),
                ColorTemplate.rgb("#8BC34A"),

                ColorTemplate.rgb("#E91E63"),
                ColorTemplate.rgb("#B71C1C"),
                ColorTemplate.rgb("#4A148C"),
                ColorTemplate.rgb("#006064"),
                ColorTemplate.rgb("#827717"),
                ColorTemplate.rgb("#FF3D00"),
                ColorTemplate.rgb("#00FF00"),
                ColorTemplate.rgb("#008080"),
                ColorTemplate.rgb("#800000"),
                ColorTemplate.rgb("#333300"),

                ColorTemplate.rgb("#c67a00"),
                ColorTemplate.rgb("#ffbe60"),
                ColorTemplate.rgb("#ffd99b"),
                ColorTemplate.rgb("#e9bb71"),
                ColorTemplate.rgb("#e8ac4a"),
                ColorTemplate.rgb("#af7922"),
                ColorTemplate.rgb("#c77f09"),
                ColorTemplate.rgb("#7e5718"),
                ColorTemplate.rgb("#cc0000"),
                ColorTemplate.rgb("#ff0000"),
                ColorTemplate.rgb("#fe5a5a"),
                ColorTemplate.rgb("#ff2020"),
                ColorTemplate.rgb("#ff9090"),
                ColorTemplate.rgb("#c51414"),
                ColorTemplate.rgb("#8e0202"),
                ColorTemplate.rgb("#921717"),
                ColorTemplate.rgb("#d35e5e"),
                ColorTemplate.rgb("#970b0b"),
                ColorTemplate.rgb("#869c9b"),
                ColorTemplate.rgb("#7eaeac"),
                ColorTemplate.rgb("#598482"),
                ColorTemplate.rgb("#58b1ad"),
                ColorTemplate.rgb("#37aba6"),
                ColorTemplate.rgb("#1f827d"),
                ColorTemplate.rgb("#35d5ce"),
                ColorTemplate.rgb("#05c2b9"),
                ColorTemplate.rgb("#2ed8d0"),
                ColorTemplate.rgb("#62fff8"),
                ColorTemplate.rgb("#237975"),
                ColorTemplate.rgb("#13726e"),
                Color.rgb(181, 205, 26)};
        ArrayList<String> xDataAvailedSection = new ArrayList<>();
        int i = 0;
        int checkLess;

        for (String key : arrayListavail) {

            // for(String key:mfilterarrayAvailed.keySet()){
            String section = "";

            int innerlength = mfilterarrayAvailed.get(key).size();

            if (innerlength != 0) {

                availed = BigInteger.ZERO;
                entitle_greater = BigInteger.ZERO;

                xDataAvailedSection = new ArrayList<>();
                for (int j = 0; j < innerlength; j++) {

                    if (mfilterarrayAvailed.get(key).get(j).getTax_section() != null) {
                        section = mfilterarrayAvailed.get(key).get(j).getTax_section();
                    }

                    if (mfilterarrayAvailed.get(key).get(j).getAllowed_value() != null) {
                        availed = availed.add(new BigInteger(mfilterarrayAvailed.get(key).get(j).getAllowed_value()));
                    }
                    if (mfilterarrayAvailed.get(key).get(j).getEntitled() != null) {
                        entitle_greater = new BigInteger(mfilterarrayAvailed.get(key).get(j).getEntitled());
                    }


                }
                //---------if in this array availed value greater means take entitle value
                checkLess = availed.compareTo(entitle_greater);
                if (checkLess == 1) {
                    //this hashmap used for entitlement-availed = pending value calculation
                    mAvailedTotalValue.put(key, entitle_greater);

                    //Availed bar
                    xDataAvailedSection.add(section + " : " + "₹ " + UtileKit.formatedNumbers(entitle_greater));
                    xDataAvailedBar.add(parseFloat(String.valueOf(entitle_greater)));
                    availed_colors.add(xDataAvailedcolour[i]);
                    availed_section.addAll(xDataAvailedSection);
                    i = i + 1;
                } else if (checkLess == 0) {
                    //this hashmap used for entitlement-availed = pending value calculation
                    mAvailedTotalValue.put(key, entitle_greater);

                    //Availed bar
                    xDataAvailedSection.add(section + " : " + "₹ " + UtileKit.formatedNumbers(entitle_greater));
                    xDataAvailedBar.add(parseFloat(String.valueOf(entitle_greater)));
                    availed_colors.add(xDataAvailedcolour[i]);
                    availed_section.addAll(xDataAvailedSection);
                    i = i + 1;
                } else {
                    //this hashmap used for entitlement-availed = pending value calculation
                    mAvailedTotalValue.put(key, availed);

                    //Availed bar
                    xDataAvailedSection.add(section + " : " + "₹ " + UtileKit.formatedNumbers(availed));
                    xDataAvailedBar.add(parseFloat(String.valueOf(availed)));
                    availed_colors.add(xDataAvailedcolour[i]);
                    availed_section.addAll(xDataAvailedSection);
                    i = i + 1;
                }
                //--------


            }
        }
    }
    //Availed card ending


    //pending function starting
    private void pending() {

        xDataPendingBar = new ArrayList<>();
        int[] xDataPendingcolour = new int[]{
                ColorTemplate.rgb("#53adfc"),
                ColorTemplate.rgb("#53fcf3"),
                ColorTemplate.rgb("#fc8553"),
                ColorTemplate.rgb("#8BC34A"),
                ColorTemplate.rgb("#FFC107"),
                ColorTemplate.rgb("#7d53fc"),
                ColorTemplate.rgb("#869c9b"),
                ColorTemplate.rgb("#53adfc"),
                ColorTemplate.rgb("#da53fc"),
                ColorTemplate.rgb("#c18911"),
                ColorTemplate.rgb("#5386fc"),
                ColorTemplate.rgb("#FFC107"),
                ColorTemplate.rgb("#8BC34A"),

                ColorTemplate.rgb("#E91E63"),
                ColorTemplate.rgb("#B71C1C"),
                ColorTemplate.rgb("#4A148C"),
                ColorTemplate.rgb("#006064"),
                ColorTemplate.rgb("#827717"),
                ColorTemplate.rgb("#FF3D00"),
                ColorTemplate.rgb("#00FF00"),
                ColorTemplate.rgb("#008080"),
                ColorTemplate.rgb("#800000"),
                ColorTemplate.rgb("#333300"),

                ColorTemplate.rgb("#c67a00"),
                ColorTemplate.rgb("#ffbe60"),
                ColorTemplate.rgb("#ffd99b"),
                ColorTemplate.rgb("#e9bb71"),
                ColorTemplate.rgb("#e8ac4a"),
                ColorTemplate.rgb("#af7922"),
                ColorTemplate.rgb("#c77f09"),
                ColorTemplate.rgb("#7e5718"),
                ColorTemplate.rgb("#cc0000"),
                ColorTemplate.rgb("#ff0000"),
                ColorTemplate.rgb("#fe5a5a"),
                ColorTemplate.rgb("#ff2020"),
                ColorTemplate.rgb("#ff9090"),
                ColorTemplate.rgb("#c51414"),
                ColorTemplate.rgb("#8e0202"),
                ColorTemplate.rgb("#921717"),
                ColorTemplate.rgb("#d35e5e"),
                ColorTemplate.rgb("#970b0b"),
                ColorTemplate.rgb("#869c9b"),
                ColorTemplate.rgb("#7eaeac"),
                ColorTemplate.rgb("#598482"),
                ColorTemplate.rgb("#58b1ad"),
                ColorTemplate.rgb("#37aba6"),
                ColorTemplate.rgb("#1f827d"),
                ColorTemplate.rgb("#35d5ce"),
                ColorTemplate.rgb("#05c2b9"),
                ColorTemplate.rgb("#2ed8d0"),
                ColorTemplate.rgb("#62fff8"),
                ColorTemplate.rgb("#237975"),
                ColorTemplate.rgb("#13726e"),
                Color.rgb(181, 205, 26)};

        ArrayList<String> xDataPendingSection = new ArrayList<>();
        int i = 0;

        sub_entitlement_availed = BigInteger.ZERO;

        xDataPendingSection = new ArrayList<>();

        //for(String key:taxavailed_hashset){

        for (String key : arrayListentil) {

            if (mAvailedTotalValue.containsKey(key)) {

                sub_entitlement_availed = mEntitlementTotalValue.get(key).subtract(mAvailedTotalValue.get(key));

                //Pending Bar
                xDataPendingSection.add(key + " : " + "₹ " + UtileKit.formatedNumbers(sub_entitlement_availed));
                xDataPendingBar.add(parseFloat(String.valueOf(sub_entitlement_availed)));
                pending_colors.add(xDataPendingcolour[i]);
                i = i + 1;
            }
        }
        pending_section.addAll(xDataPendingSection);

        float[] xDataEntitlementFlotBar = new float[xDataEntitlementBar.size()];
        for (int m = 0; m < xDataEntitlementBar.size(); m++) {
            xDataEntitlementFlotBar[m] = xDataEntitlementBar.get(m);
        }
        float[] xDataAvailedBarFlotBar = new float[xDataAvailedBar.size()];
        for (int m = 0; m < xDataAvailedBar.size(); m++) {
            xDataAvailedBarFlotBar[m] = xDataAvailedBar.get(m);
        }
        float[] xDataPendingBarFlotBar = new float[xDataPendingBar.size()];
        for (int m = 0; m < xDataPendingBar.size(); m++) {
            xDataPendingBarFlotBar[m] = xDataPendingBar.get(m);
        }

        BarData d = new BarData();
        BARENTRY = new ArrayList<>();
        BARENTRY.add(new BarEntry(xDataEntitlementFlotBar, 0));
        BARENTRY.add(new BarEntry(xDataAvailedBarFlotBar, 1));
        BARENTRY.add(new BarEntry(xDataPendingBarFlotBar, 2));
        BarDataSet set = new BarDataSet(BARENTRY, "");


        set.setDrawValues(false);

        colorarray.addAll(entitle_colors);
        colorarray.addAll(availed_colors);
        colorarray.addAll(pending_colors);

        sectionArray.addAll(entitle_section);

        sectionArray.addAll(availed_section);

        sectionArray.addAll(pending_section);


        set.setColors(colorarray);
        //set.setColors(colour);
        set.setBarSpacePercent(20f);
        set.setValueFormatter(new ChartValueFormatter());
        d.addDataSet(set);


        setDataForCheckbox(entitle_section, entitle_colors, parentView);
        setDataForCheckbox(availed_section, availed_colors, parentViewAvailed_layout);
        setDataForCheckbox(pending_section, pending_colors, parentViewPending_layout);
        CombinedData data = new CombinedData(getXAxisValues());
        data.setData(d);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setDrawValueAboveBar(false);
        barChart.setTouchEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setData(data);
        barChart.setDescription("");
        barChart.setExtraBottomOffset(5f);
        barChart.invalidate();

    }
    //pending function ending


    private ArrayList<String> getXAxisValues() {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Entitlement");
        labels.add("Availed");
        labels.add("Pending");
        return labels;
    }

    private void setDataForCheckbox(ArrayList<String> xDataCheckbox, ArrayList<Integer> colour,
                                    LinearLayout parentViewAvailedLayout) {
        try {
            for (int i = 0; i < xDataCheckbox.size(); i++) {

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
                TextView txt_unit = new TextView(mContext);
                if (i >= 1) {
                    parms_legen_layout.setMargins(20, 10, 20, 10);
                    //txt_unit.setPadding(20,0,0,0);
                    txt_unit.setText(xDataCheckbox.get(i));
                } else {
                    txt_unit.setText(xDataCheckbox.get(i));
                }
                legend_layout.setBackgroundColor(colour.get(i));
                left_layout.addView(legend_layout);
                txt_unit.setText(xDataCheckbox.get(i));
                left_layout.addView(txt_unit);

                parent_layout.addView(left_layout);
                parentViewAvailedLayout.addView(parent_layout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

            case R.id.entitlement_title_card:
                if (!(flagToggleButton)) {
                    parentView.setVisibility(View.VISIBLE);
                    entitle_plus.setImageResource(R.drawable.ic_mine_icon);
                    flagToggleButton = true;
                } else {
                    parentView.setVisibility(View.GONE);
                    entitle_plus.setImageResource(R.drawable.ic_add_icon);
                    flagToggleButton = false;
                }
                break;
            case R.id.availed_title_card:
                if (!(flagToggleButton)) {
                    parentViewAvailed_layout.setVisibility(View.VISIBLE);
                    availed_plus.setImageResource(R.drawable.ic_mine_icon);
                    flagToggleButton = true;
                } else {
                    parentViewAvailed_layout.setVisibility(View.GONE);
                    availed_plus.setImageResource(R.drawable.ic_add_icon);
                    flagToggleButton = false;
                }
                break;

            case R.id.pending_title_card:
                if (!(flagToggleButton)) {
                    parentViewPending_layout.setVisibility(View.VISIBLE);
                    pending_plus.setImageResource(R.drawable.ic_mine_icon);
                    flagToggleButton = true;
                } else {
                    parentViewPending_layout.setVisibility(View.GONE);
                    pending_plus.setImageResource(R.drawable.ic_add_icon);
                    flagToggleButton = false;
                }
                break;


            case R.id.entitle_plus:

                if (!(flagToggleButton)) {
                    parentView.setVisibility(View.VISIBLE);
                    entitle_plus.setImageResource(R.drawable.ic_mine_icon);
                    flagToggleButton = true;
                } else {
                    parentView.setVisibility(View.GONE);
                    entitle_plus.setImageResource(R.drawable.ic_add_icon);
                    flagToggleButton = false;
                }

                break;
            case R.id.availed_plus:
                if (!(flagToggleButton)) {
                    parentViewAvailed_layout.setVisibility(View.VISIBLE);
                    availed_plus.setImageResource(R.drawable.ic_mine_icon);
                    flagToggleButton = true;
                } else {
                    parentViewAvailed_layout.setVisibility(View.GONE);
                    availed_plus.setImageResource(R.drawable.ic_add_icon);
                    flagToggleButton = false;
                }
                break;
            case R.id.pending_plus:
                if (!(flagToggleButton)) {
                    parentViewPending_layout.setVisibility(View.VISIBLE);
                    pending_plus.setImageResource(R.drawable.ic_mine_icon);
                    flagToggleButton = true;
                } else {
                    parentViewPending_layout.setVisibility(View.GONE);
                    pending_plus.setImageResource(R.drawable.ic_add_icon);
                    flagToggleButton = false;
                }
                break;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_detail:

                //addFragmenttoStack(new TaxPromptDetailViewPager());

                addFragmenttoStack(new TaxPlanProductDetailFragment());

                break;
            case R.id.menu_summary:

                addFragmenttoStack(new TaxPromptSummary());

                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

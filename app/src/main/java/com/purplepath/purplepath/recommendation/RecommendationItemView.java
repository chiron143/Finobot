//package com.purplepath.purplepath.recommendation;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import androidx.annotation.LayoutRes;
//import androidx.annotation.NonNull;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.evrencoskun.tableview.TableView;
//import com.finobot.finobot.R;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.recommendation.adapter.RecomendationLifeInsuranceTableAdapter;
//import com.purplepath.purplepath.recommendation.adapter.RecomendationSavingTableAdapter;
//import com.purplepath.purplepath.recommendation.adapter.RecomendationTableAdapter;
//import com.purplepath.purplepath.recommendation.getcashmodel.Cash_mang_det;
//import com.purplepath.purplepath.recommendation.getcashmodel.Cashmodel;
//import com.purplepath.purplepath.recommendation.getgoalmodel.Goalmodel;
//import com.purplepath.purplepath.recommendation.getgoalmodel.User_goals;
//import com.purplepath.purplepath.recommendation.getnetworthmodel.Assets;
//import com.purplepath.purplepath.recommendation.getnetworthmodel.Liab;
//import com.purplepath.purplepath.recommendation.getnetworthmodel.Networthmodel;
//import com.purplepath.purplepath.recommendation.getsaveinvestmodel.Saveinvestmodel;
//import com.purplepath.purplepath.recommendation.getsaveinvestmodel.User_invst_goals;
//import com.purplepath.purplepath.recommendation.model.Data;
//import com.purplepath.purplepath.recommendation.model.Emg_fund;
//import com.purplepath.purplepath.recommendation.model.Health_ins_plan;
//import com.purplepath.purplepath.recommendation.model.Ins_plan;
//import com.purplepath.purplepath.recommendation.model.Motor_ins_plan;
//import com.purplepath.purplepath.recommendation.model.Prop_ins_plan;
//import com.purplepath.purplepath.recommendation.model.RecommendData;
//import com.purplepath.purplepath.recommendation.model.RecommendationInfo;
//import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.Ded_by_prod;
//import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
//import com.purplepath.purplepath.taxproduct.model.Cell;
//import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
//import com.purplepath.purplepath.taxproduct.model.RowHeader;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//
//
///**
// * @author Murugesan on 02/02/17.
// */
//
//public class RecommendationItemView extends FrameLayout implements View.OnClickListener {
//
//    private ImageView itemIconView, itemIndicatorView;
//    private TextView itemNameView;
//    private ViewType viewType = ViewType.EMERGENCY_FUND_PLAN; // Default value
//    private RecommendData recommendData;
//    private Networthmodel networthmodel;
//    private Goalmodel goalmodel;
//    private Cashmodel cashmodel;
//    private Saveinvestmodel saveinvestmodel;
//    private TaxCashFlowModel taxCashFlowModel;
//    private boolean isPopUpShowing;
//    private PopupWindow popupWindow;
//    String hlvValue;
//    String networth_value;
//    String goal_name = "", cost_of_goal = "", goal_years = "", expected_increment = "",
//            fund_val = "", growth_rate = "", pmt_1_year = "", pmt_1_mon = "", pval = "";
//    private WebView webViewSavingInvesment,webViewIns_plan,webView_HealthInsurance,
//            webView_AutoInsurance,webView_PropertyInsurance,webViewCashManagement,
//            webViewnetworth,webView_goals,webView_Taxation;
//    private TextView errorTextview;
//    private Context mContext;
//
//    private String deflict="";
//    private BigDecimal per1year;
//    private BigDecimal per1month;
//    private BigDecimal pvals;
//    String per1year2="";
//    String per1month2="";
//    String pvals2="";
//    private String rs="&#x20B9";
//    private String deficit_color="";
//    HashSet<String> asset_hash = new HashSet<String>();
//    HashSet<String> taxproduct_hashset = new HashSet<String>();
//    private BigInteger availed=BigInteger.ZERO;
//    BigInteger entitle=BigInteger.ZERO;
//    private BigInteger total_sub_all=BigInteger.ZERO;
//    String total_tax_payable="";
//    String avg_tax_rate="";
//    String marg_tax_rate="";
//
//    private TableView mTableView;
//    private TableView mTableViewInsPlan;
//    private RecomendationTableAdapter mTableViewAdapter;
//
//    private RecomendationSavingTableAdapter recomendationSavingTableAdapter;
//
//    private RecomendationLifeInsuranceTableAdapter recomendationLifeInsuranceTableAdapter;
//    RelativeLayout tableParentView;
//
//    RelativeLayout tableParentView_Ins_Plan;
//
//    private ArrayList<String> mColoumNameList=new ArrayList<String>( Arrays.asList("Goal", "Present cost", "Current fund","Target future cost","Time to active","Resource  used","Investment needed"));
//
//    private ArrayList<String> mColoumNameListHealth=new ArrayList<String>( Arrays.asList("Insurance Product Type", "Cover Required", "Cover Availed","Cover Recommendation","Anual Premium"));
//
//    private ArrayList<String> mColoumNameListTaxation=new ArrayList<String>( Arrays.asList("Section", "Entitled", "Cover Availed","Pending"));
//
//    private ArrayList<String> mColoumNameListSavingInvest=new ArrayList<String>( Arrays.asList("Assets", "Current Values", "Target future cost","Time to active","Resource used","Investment needed"));
//
//    private ArrayList<String> mColoumNameListIns_Plan_Res=new ArrayList<String>( Arrays.asList("Insurance", "Existing Cover", "Existing Premium"));
//
//    private ArrayList<String> mColoumNameListIns_Plan=new ArrayList<String>( Arrays.asList("   Title   ", "Suggested Cover", "Suggested Premium"));
//
//    private ArrayList<String> mRownameInsurancePlan=new ArrayList<String>( Arrays.asList("Minimum Required Cover", "Minimum Required Cover to Purchage", "Maximum Required Cover","Maxmum Required Cover to Purchage"));
//
//    private TextView human_life_value;
//
//    String min_Req_Cover="",min_Req_Cover_Purchase="",
//            max_Req_Cover="",max_Req_Cover_Purchase="";
//
//    private TextView surplus_needed,goal_permonths,goal_pvalues;
//
//    private TextView mamount_pay,maverage_taxs,mmar_tax;
//
//    public RecommendationItemView(Context context) {
//        this(context, null);
//    }
//
//    public RecommendationItemView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public RecommendationItemView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        renderItemView(context, R.layout.view_recommendation_single_item);
//        setOnClickListener(this);
//    }
//
//    private void renderItemView(@NonNull Context context, @LayoutRes int viewRecommendationSingleItem) {
//        inflate(context, viewRecommendationSingleItem, this);
//        itemIconView = (ImageView) findViewById(R.id.item_icon);
//        itemIndicatorView = (ImageView) findViewById(R.id.item_indicator);
//        itemNameView = (TextView) findViewById(R.id.item_name);
//    }
//
//    public void setViewType(ViewType viewType) {
//        this.viewType = viewType;
//    }
//
//    public void updateRecommendationView(RecommendationInfo recommedationInfo) {
//        itemIconView.setImageResource(recommedationInfo.iconRes);
//        itemIndicatorView.setImageResource(recommedationInfo.indicatorRes);
//        itemNameView.setText(recommedationInfo.nameRes);
//    }
//
//    public void setRecommendedData(RecommendData recommendData) {
//        this.recommendData = recommendData;
//    }
//
//    public void setNetworthmodel(Networthmodel networthmodel) {
//        this.networthmodel = networthmodel;
//    }
//
//    public void setCashManagement(Cashmodel cashmodel) {
//        this.cashmodel = cashmodel;
//    }
//    public void setGoal(Goalmodel goalmodel) {
//        this.goalmodel = goalmodel;
//    }
//
//    public void setSavingInvest(Saveinvestmodel saveinvestmodel){
//        this.saveinvestmodel=saveinvestmodel;
//    }
//
//    public void setTaxation(TaxCashFlowModel taxCashFlowModel){
//        this.taxCashFlowModel=taxCashFlowModel;
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        if (isPopUpShowing() && null != popupWindow) {
//            setPopUpShowing(false);
//            popupWindow.dismiss();
//        } else {
//            setPopUpShowing(true);
//            View popUpContainer = getContainerView(v, viewType);
//            popupWindow = initPopupWindow(popUpContainer);
//            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    setPopUpShowing(true);
//                    itemIndicatorView.setImageResource(R.drawable.ic_add_icon);
//                }
//            });
//            if(viewType == ViewType.EMERGENCY_FUND_PLAN || viewType == ViewType.INSURANCE_PLAN ||
//                    viewType == ViewType.HEALTH_INSURANCE || viewType == ViewType.PROPERTY_INSURANCE ||
//                    viewType == ViewType.AUTO_INSURANCE)
//                RecommendationFragment.scrollview.post(new Runnable() {
//                    @Override
//                    public void run() {
//                       // RecommendationFragment.scrollview.scrollTo(0,
//                        //        RecommendationFragment.auto_insurance_plan_id.getBottom());
//                    }
//                });
//            popupWindow.showAsDropDown(this);
//            itemIndicatorView.setImageResource(R.drawable.ic_mine_icon);
//        }
//    }
//
//    private View getContainerView(View v, ViewType viewType) {
//        mContext=getContext();
//        if (null == viewType)
//            throw new NullPointerException("ViewType can't be null for RecommendedItemView!");
//         View view = null;
//        try {
//
//            /**
//             * Insurance plan call function
//             */
//            if (null != recommendData) {
//                view = getLifeInsuranceView(view, v);
//            }
//            /**
//             *Emergency call function
//             */
//            if (null != recommendData) {
//                view = getEmergencyView(view, v);
//            }
//            /**
//             *HealthInsurance call function
//             */
//            if (null != recommendData) {
//                view = getHealthInsuranceView(view, v);
//            }
//            /**
//             *AutoInsurance call function
//             */
//            if (null != recommendData) {
//                view = getAutoInsuranceView(view, v);
//            }
//            /**
//             *ProprtyInsurance call function
//             */
//            if (null != recommendData) {
//                view = getPropertyInsuranceView(view, v);
//            }
//            /**
//             *Networth call function
//             */
//            if (null != networthmodel) {
//                view = getNetworthView(view, v);
//            }
//            /**
//             *CashManagement call function
//             */
//            if (null != cashmodel) {
//                view = getCashManagementView(view, v);
//             }
//            /**
//             *Goals call function
//             */
//            if (null != goalmodel) {
//                view = getGoalView(view, v);
//            }
//            /**
//             *Saving and Invest call function
//             */
//            if (null != saveinvestmodel) {
//                    view = getSavingInvestmentView(view, v);
//            }
//            /**
//             *Taxation
//             */
//            if (null != taxCashFlowModel) {
//                view = getTaxationView(view, v);
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return view;
//    }
//
//    private View getCashManagementView(View view, View v) {
//        if (null != cashmodel) {
//        try{
//            com.purplepath.purplepath.recommendation.getcashmodel.Data data =
//                    (null != cashmodel.getData() ? cashmodel.getData() : null);
//            if(viewType == ViewType.CASH_MANAGEMENT) {
//                Cash_mang_det cash_mang_det = data.getCash_mang_det();
//                view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_popup_cashmanagement, null);
//                if (null != cash_mang_det){
//
//                    if(UtileKit.getPersistedPurplePathBoolPref("Cash Management")) {
//                        applyCashManagement(view, cash_mang_det);
//                    }else {
//                        UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
//                    }
//                }
//                else{
//                    view.findViewById(R.id.inner_progress_cashmang).setVisibility(View.GONE);
//                    view.findViewById(R.id.empty_value_cashmang).setVisibility(View.VISIBLE);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }}
//        return view;
//    }
//
//    private View getNetworthView(View view, View v) {
//
//        if (null != networthmodel) {
//            try{
//                com.purplepath.purplepath.recommendation.getnetworthmodel.Data dataNetworth =
//                        (null != networthmodel.getData() ? networthmodel.getData() : null);
//                if (viewType == ViewType.NETWORTH_PLAN) {
//                    view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_popup_networth_plan, null);
//                    Assets asset = dataNetworth.getAssets();
//                    Liab liab = dataNetworth.getLiab();
//                    if (null != asset || null != liab){
//
//                        if(UtileKit.getPersistedPurplePathBoolPref("Networth")) {
//                            applyNetworthDetails(view, asset, liab);
//                        }
//                        else {
//                            UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
//                        }
//                    }
//                    else{
//                        view.findViewById(R.id.inner_progress_netwoth).setVisibility(View.GONE);
//                    }
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return view;
//    }
//
//    private View getEmergencyView(View view, View v) {
//
//        if (viewType == ViewType.EMERGENCY_FUND_PLAN) {
//            if (null != recommendData) {
//                try{
//                    Data data = (null != recommendData.getData() ? recommendData.getData() : null);
//
//                    Emg_fund emgfund = data.getEmg_fund();
//                    view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_popup_emergency_plan, null);
//                    //  view.findViewById(R.id.inner_progress).setVisibility(View.GONE);
//                    if (null != emgfund) {
//
//                        if(UtileKit.getPersistedPurplePathBoolPref("Emergency Fund Plan")) {
//                            applyEmergencyInformation(view, emgfund);
//                        }
//                        else {
//                            UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
//                        }
//
//                    }
//                    else {
//                        //   view.findViewById(R.id.inner_progress).setVisibility(View.GONE);
//                        view.findViewById(R.id.empty_value_emerplan).setVisibility(VISIBLE);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//        }
//        return view;
//    }
//
//    private View getLifeInsuranceView(View view, View v) {
//
//
//        if (viewType == ViewType.INSURANCE_PLAN) {
//            if (null != recommendData) {
//
//                Data data = (null != recommendData.getData() ? recommendData.getData() : null);
//                Ins_plan insPlan = data.getIns_plan();
//                view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_popup_insurance_plan, null);
//                view.findViewById(R.id.inner_progress).setVisibility(View.GONE);
//                if (null != insPlan) {
//
//                    if(UtileKit.getPersistedPurplePathBoolPref("Life Insurance Plan")) {
//
//                        /*mTableView = createTableView();
//                        tableParentView= ((RelativeLayout)view.findViewById(R.id.insurance_plan_res));
//                        tableParentView.addView(mTableView);*/
//                        applyInsurancePlanDetails(view, insPlan);
//
//
//                       /* mTableViewInsPlan=createTableViewInsPlan();
//                        tableParentView_Ins_Plan= ((RelativeLayout)view.findViewById(R.id.insurance_plan));
//                        tableParentView_Ins_Plan.addView(mTableViewInsPlan);
//                        human_life_value= ((TextView)view.findViewById(R.id.human_life_value));
//                        applyInsurancePlanDetailss(view, insPlan);*/
//                    }else {
//                        UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
//                    }
//
//                }
//                else {
//                    view.findViewById(R.id.inner_progress).setVisibility(View.GONE);
//                    view.findViewById(R.id.empty_value_insplan).setVisibility(View.VISIBLE);
//                }
//            }
//        }
//        return view;
//    }
//
//    private View getSavingInvestmentView(View view, View v) {
//
//        if (null != saveinvestmodel) {
//        try{
//            com.purplepath.purplepath.recommendation.getsaveinvestmodel.Data data =
//                    (null != saveinvestmodel.getData() ? saveinvestmodel.getData() : null);
//            if(viewType == ViewType.SAVING_AND_INVESTMENT) {
//                ArrayList<User_invst_goals> user_invst_goals = data.getUser_invst_goals();
//                view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_popup_savinginvestment, null);
//                if (null != user_invst_goals&&user_invst_goals.size()>0){
//                    try {
//                        if(UtileKit.getPersistedPurplePathBoolPref("Savings and Investments")) {
//                            mTableView = createTableViewSavings();
//                            tableParentView= ((RelativeLayout)view.findViewById(R.id.tableParentView_saving_investment));
//                            tableParentView.addView(mTableView);
//                            applySavingandInvestDetails(view, user_invst_goals);
//                        }
//                        else {
//                            UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//                else{
//                    view.findViewById(R.id.inner_progress_saving).setVisibility(View.GONE);
//                    view.findViewById(R.id.empty_values_saving).setVisibility(View.VISIBLE);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }}
//        return view;
//    }
//
//    private TableView createTableViewSavings() {
//        TableView tableView = new TableView(getContext());
//        // Set adapter
//        recomendationSavingTableAdapter = new RecomendationSavingTableAdapter(mContext);
//        tableView.setAdapter(recomendationSavingTableAdapter);
//        // Set layout params
//        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
//                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        tableView.setLayoutParams(tlp);
//        return tableView;
//    }
//
//    private View getTaxationView(View view, View v) {
//
//        if(null!=taxCashFlowModel){
//            com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.Data data=
//                    (null != taxCashFlowModel.getData() ? taxCashFlowModel.getData() : null);
//            if(viewType == ViewType.TAXATION_PLAN) {
//
//                ArrayList<Ded_by_prod> ded_by_prods = data.getDed_by_prod();
//
//                view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_popup_taxation, null);
//                if (null != ded_by_prods&&ded_by_prods.size()>0){
//                    try {
//                        mTableView = createTableView();
//                        tableParentView= ((RelativeLayout)view.findViewById(R.id.tableParentView_taxation));
//                        tableParentView.addView(mTableView);
//
//
//                        mamount_pay= ((TextView)view.findViewById(R.id.amount_pay));
//                        maverage_taxs= ((TextView)view.findViewById(R.id.average_tax));
//                        mmar_tax= ((TextView)view.findViewById(R.id.mar_tax));
//                        applyTaxationDetails(view, ded_by_prods);
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//                else{
//                    view.findViewById(R.id.inner_progress_taxation).setVisibility(View.GONE);
//                    view.findViewById(R.id.empty_values_taxation).setVisibility(View.VISIBLE);
//                }
//            }
//        }
//        return view;
//    }
//
//    private View getPropertyInsuranceView(View view, View v) {
//
//        if (null != recommendData) {
//            try{
//                Data data = (null != recommendData.getData() ? recommendData.getData() : null);
//                if(viewType == ViewType.PROPERTY_INSURANCE) {
//                    ArrayList<Prop_ins_plan> prop_ins_plen = data.getProp_ins_plan();
//                    view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_popup_property_insurance, null);
//                    if (null != prop_ins_plen&&prop_ins_plen.size()>0){
//
//                        if(UtileKit.getPersistedPurplePathBoolPref("Property Insurance Plan")) {
//                            mTableView = createTableView();
//                            tableParentView= ((RelativeLayout)view.findViewById(R.id.tableParentView_property));
//                            tableParentView.addView(mTableView);
//                            applyPropertyInsurance(view, prop_ins_plen);
//                        }
//                        else {
//                            UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
//                        }
//                    }
//                    else{
//                        view.findViewById(R.id.inner_progress_property).setVisibility(View.GONE);
//                        view.findViewById(R.id.empty_value_proprty).setVisibility(View.VISIBLE);
//                    }
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return view;
//    }
//
//    private View getAutoInsuranceView(View view, View v) {
//
//        if (null != recommendData) {
//            try{
//                Data data = (null != recommendData.getData() ? recommendData.getData() : null);
//                if(viewType == ViewType.AUTO_INSURANCE) {
//                    ArrayList<Motor_ins_plan> motor_ins_plen = data.getMotor_ins_plan();
//                    view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_popup_auto_insurance, null);
//                    if (null != motor_ins_plen&&motor_ins_plen.size()>0) {
//
//                        if(UtileKit.getPersistedPurplePathBoolPref("Motor Insurance Plan")) {
//
//                            mTableView = createTableView();
//                            tableParentView= ((RelativeLayout)view.findViewById(R.id.tableParentView_auto));
//                            tableParentView.addView(mTableView);
//                            applyAutoInsurance(view, motor_ins_plen);
//                        }
//                        else {
//                            UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
//                        }
//
//                    } else {
//                        view.findViewById(R.id.inner_progress_auto).setVisibility(View.GONE);
//                        view.findViewById(R.id.empty_value_auto).setVisibility(View.VISIBLE);
//                    }
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return view;
//    }
//
//    private View getHealthInsuranceView(View view, View v) {
//
//        if (null != recommendData) {
//            try{
//                Data data = (null != recommendData.getData() ? recommendData.getData() : null);
//                if(viewType == ViewType.HEALTH_INSURANCE) {
//                    ArrayList<Health_ins_plan> health_ins_plan = data.getHealth_ins_plan();
//                    view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_popup_health_insurance, null);
//                    view.findViewById(R.id.inner_progress_health).setVisibility(View.GONE);
//                    if (null != health_ins_plan&&health_ins_plan.size()>0) {
//
//                        if(UtileKit.getPersistedPurplePathBoolPref("Health Insurance Plan")) {
//                            mTableView = createTableView();
//                            tableParentView= ((RelativeLayout)view.findViewById(R.id.tableParentView_health));
//                            tableParentView.addView(mTableView);
//                            applyHealthInsurance(view, health_ins_plan);
//                        }
//                        else {
//                            UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
//                        }
//                    }
//                    else{
//                        view.findViewById(R.id.inner_progress_health).setVisibility(View.GONE);
//                        view.findViewById(R.id.empty_value_health).setVisibility(View.VISIBLE);
//                    }
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return view;
//    }
//
//    private View getGoalView(View view, View v) {
//
//            try{
//                com.purplepath.purplepath.recommendation.getgoalmodel.Data data =
//                        (null != goalmodel.getData() ? goalmodel.getData() : null);
//                if(viewType == ViewType.GOALS) {
//                    ArrayList<User_goals> user_goals = data.getUser_goals();
//                    view = LayoutInflater.from(v.getContext()).inflate(R.layout.view_popup_goals, null);
//                    if (null != user_goals){
//
//                        if(UtileKit.getPersistedPurplePathBoolPref("Goals")) {
//                            mTableView = createTableViewSavings();
//                           tableParentView= ((RelativeLayout)view.findViewById(R.id.tableParentView));
//                           tableParentView.addView(mTableView);
//
//                            surplus_needed=((TextView)view.findViewById(R.id.surplus_needed));
//
//                            goal_permonths=((TextView)view.findViewById(R.id.goal_permonths));
//
//                            goal_pvalues=((TextView)view.findViewById(R.id.goal_pvalues));
//
//                            applyGoals(view, user_goals);
//                        }
//                        else {
//                            UtileKit.intitializeAlertDialog("please upgrade your pack",mContext);
//                        }
//                    }
//                    else{
//                        view.findViewById(R.id.inner_progress_goals).setVisibility(View.GONE);
//                        view.findViewById(R.id.empty_values_goals).setVisibility(View.VISIBLE);
//                    }
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//
//        return view;
//    }
//
//    private TableView createTableView() {
//        TableView tableView = new TableView(getContext());
//        // Set adapter
//        mTableViewAdapter = new RecomendationTableAdapter(mContext);
//        tableView.setAdapter(mTableViewAdapter);
//        // Set layout params
//        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
//                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        tableView.setLayoutParams(tlp);
//        return tableView;
//    }
//    private TableView createTableViewInsPlan() {
//        TableView tableView = new TableView(getContext());
//        // Set adapter
//        recomendationLifeInsuranceTableAdapter = new RecomendationLifeInsuranceTableAdapter(mContext);
//        tableView.setAdapter(recomendationLifeInsuranceTableAdapter);
//        // Set layout params
//        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
//                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        tableView.setLayoutParams(tlp);
//        return tableView;
//    }
//
//    /**
//     * Taxation
//     */
//    private void applyTaxationDetails(View view, ArrayList<Ded_by_prod> ded_by_prods) {
//        webView_Taxation = (WebView) view.findViewById(R.id.webview_taxation);
//        final View loaderView = view.findViewById(R.id.inner_progress_taxation);
//
//        webView_Taxation.setVisibility(GONE);
//        loaderView.setVisibility(GONE);
//    /*    TextView errorTextview = (TextView) view.findViewById(R.id.empty_values_taxation);
//        WebSettings settings = webView_Taxation.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(false);
//        settings.setDomStorageEnabled(true);
//        webView_Taxation.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                loaderView.setVisibility(VISIBLE);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                loaderView.setVisibility(GONE);
//            }
//        });*/
//
//        //tax calculation
//        if(taxCashFlowModel.getData().getTax_calc()!=null){
//            if(taxCashFlowModel.getData().getTax_calc().getTotal_tax_payable()!=null) {
//                total_tax_payable = taxCashFlowModel.getData().getTax_calc().getTotal_tax_payable();
//            }
//            if(taxCashFlowModel.getData().getTax_calc().getAvg_tax_rate()!=null) {
//                avg_tax_rate = taxCashFlowModel.getData().getTax_calc().getAvg_tax_rate();
//            }
//            if(taxCashFlowModel.getData().getTax_calc().getMarg_tax_rate()!=null) {
//                marg_tax_rate = taxCashFlowModel.getData().getTax_calc().getMarg_tax_rate();
//            }
//        }
//
//        if(taxCashFlowModel.getData().getDed_by_prod()!=null){
//            int length=taxCashFlowModel.getData().getDed_by_prod().size();
//            for(int i=0;i<length;i++){
//                taxproduct_hashset.add(taxCashFlowModel.getData().getDed_by_prod().get(i).getTax_section());
//            }
//            ArrayList<String> arrayList = new ArrayList<String>(taxproduct_hashset);
//            ArrayList< ArrayList<Ded_by_prod>> mfilterarray = new ArrayList<>();
//            for(int j=0;j<arrayList.size();j++){
//                String str_obj=arrayList.get(j);
//                if (str_obj!= null) {
//                    ArrayList<Ded_by_prod>taxsection_heading=new ArrayList<Ded_by_prod>();
//
//                    for (int k = 0; k < length; k++) {
//                        if(str_obj.equals(taxCashFlowModel.getData().getDed_by_prod().get(k).getTax_section())){
//                            taxsection_heading.add(taxCashFlowModel.getData().getDed_by_prod().get(k));
//                        }
//                    }
//                    mfilterarray.add(taxsection_heading);
//                }
//            }
//            showWebViewTaxation(mfilterarray);
//        }
//
//
//
//    }
//
//    private void showWebViewTaxation(ArrayList<ArrayList<Ded_by_prod>> mfilterarray) {
//       /* String htmlContents = "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"150px\", user-scalable=yes\" />" +
//                "<body>\n" +
//                "\n" +
//                "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
//                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
//
//                "  <tr align = \"center\">\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Section</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Entitled</th> \n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Availed</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Pending</th>\n" +
//                "  </tr>\n" +
//                addingTaxationTableRows(mfilterarray)+
//                "</table>\n" +
//                "\n"+
//
//
//
//                " Based on the inputs provided, <font color=\"#6699FF\"><b>"
//                +rs+" "+total_tax_payable + "</b></font>" + "is the amount pay." + "<br/>" +
//
//                " Average Tax Rate <font color=\"#6699FF\"><b>"
//                + avg_tax_rate + "</b></font>" + "<font color=\"#6699FF\">% </font>" + "<br/>" +
//
//                " Marginal Tax Rate <font color=\"#6699FF\"><b>"
//                + marg_tax_rate + "</b></font>" + "<font color=\"#6699FF\">% </font>" + "<br/>" +
//
//
//                "  <font color=\"#0b0d0f\"><b>"+"Average tax rate : "+
//                 "</b></font>" + " is a kind of flat rate to view how much tax was paid overall." + "<br/>" +
//
//                "<font color=\"#0b0d0f\"><b>"+" Marginal tax rate : "+
//                "</b></font>" + "is a rate at which to be paid for the next rupee in earnings." + "<br/>" +
//
//
//                "<br>"+
//                "</body>\n" +
//                "</html>\n";
//        webView_Taxation.loadData(htmlContents, "text/html", "UTF-8")*/;
//
//        mamount_pay.setText(UtileKit.fromHtml("Based on the input provided, "+"₹ "+"<b>"+"<u>"+total_tax_payable+"</u>"+"</b>"
//                +"is the amount pay."));
//        maverage_taxs.setText(UtileKit.fromHtml("Average Tax Rate : "+"<b>"+"<u>"+avg_tax_rate+"</u>"+"</b>"+"%"));
//        mmar_tax.setText(UtileKit.fromHtml("Marginal Tax Rate : "+"<b>"+"<u>"+marg_tax_rate+"</u>"+"</b>"+"%"));
//
//        loadTaxationInsuranceData(mfilterarray);
//    }
//
//    private void loadTaxationInsuranceData(ArrayList<ArrayList<Ded_by_prod>> mfilterarray) {
//
//        List<RowHeader> rowHeaders = getRowHeaderListTaxation(mfilterarray);
//        List<List<Cell>> cellList = getCellListForSortingTestTaxation(mfilterarray);
//        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameListTaxation);
//        mTableViewAdapter.setAllItems(columnHeaders, rowHeaders, cellList);
//
//    }
//
//    private List<RowHeader> getRowHeaderListTaxation(ArrayList<ArrayList<Ded_by_prod>> mfilterarray) {
//        List<RowHeader> list = new ArrayList<>();
//        int k=0;
//        int n=mfilterarray.size();
//        for (int i = 0; i <n ; i++) {
//
//            int inner_length=mfilterarray.get(i).size();
//
//            for (int j = 0; j <inner_length ; j++) {
//                RowHeader header = new RowHeader("row " + k, "" + (k ++));
//                list.add(header);
//            }
//        }
//        return list;
//    }
//
//    private List<List<Cell>> getCellListForSortingTestTaxation(ArrayList<ArrayList<Ded_by_prod>> mfilterarray) {
//
//        List<List<Cell>> list = new ArrayList<>();
//
//        int length = mfilterarray.size();
//
//        for (int i=0;i<length;i++) {
//            int inner_length=mfilterarray.get(i).size();
//
//            String  section = "",str_entitle = "",str_availed="",
//                    str_pending = "";
//            availed=BigInteger.ZERO;
//            entitle=BigInteger.ZERO;
//            total_sub_all=BigInteger.ZERO;
//            for (int j = 0; j < inner_length; j++) {
//                List<Cell> cellList = new ArrayList<>();
//
//                if(i==0) {
//                    section = mfilterarray.get(i).get(j).getTax_section();
//                }
//                //entitle
//                if (mfilterarray.get(i).get(j).getEntitled() != null) {
//                    entitle=new BigInteger(mfilterarray.get(i).get(j).getEntitled());
//                    str_entitle="₹ "+String.valueOf(UtileKit.formatedNumbers(entitle));
//                }
//                //availed
//                if (mfilterarray.get(i).get(j).getContr_val() != null) {
//                    availed = availed.add(new BigInteger(mfilterarray.get(i).get(j).getContr_val()));
//                    str_availed="₹ "+String.valueOf(UtileKit.formatedNumbers(availed));
//                }
//                //pending
//                total_sub_all=entitle.subtract(availed);
//                str_pending="₹ "+String.valueOf(UtileKit.formatedNumbers(total_sub_all));
//
//                cellList.add(new Cell(i+"00", section));
//                cellList.add(new Cell(i+"01", str_entitle));
//                cellList.add(new Cell(i+"02", str_availed));
//                cellList.add(new Cell(i+"03", str_pending));
//                list.add(cellList);
//            }
//
//
//        }
//        return list;
//
//    }
//
//    private String addingTaxationTableRows(ArrayList<ArrayList<Ded_by_prod>> mfilterarray) {
//        String rowsadd = "";
//        for (ArrayList<Ded_by_prod> ded_by_prod:mfilterarray) {
//            int length=ded_by_prod.size();
//            String  section = "",str_entitle = "",str_availed="",
//                    str_pending = "";
//            availed=BigInteger.ZERO;
//            entitle=BigInteger.ZERO;
//            total_sub_all=BigInteger.ZERO;
//            for (int i = 0; i < length; i++) {
//
//                if(i==0) {
//                    section = ded_by_prod.get(i).getTax_section();
//                }
//                //entitle
//                if (ded_by_prod.get(i).getEntitled() != null) {
//                    entitle=new BigInteger(ded_by_prod.get(i).getEntitled());
//                    str_entitle=String.valueOf(UtileKit.formatedNumbers(entitle));
//                }
//                //availed
//                if (ded_by_prod.get(i).getContr_val() != null) {
//                    availed = availed.add(new BigInteger(ded_by_prod.get(i).getContr_val()));
//                    str_availed=String.valueOf(UtileKit.formatedNumbers(availed));
//                }
//                //pending
//                total_sub_all=entitle.subtract(availed);
//                str_pending=String.valueOf(UtileKit.formatedNumbers(total_sub_all));
//            }
//            rowsadd = rowsadd + "  <tr align = \"center\">\n" +
//                    "    <td>" + section + "</td>\n" +
//                    "    <td>" + rs + " " + str_entitle + "</td>\n" +
//                    "    <td>" + rs + " " + str_availed + "</td>\n" +
//                    "    <td>" + rs + " " +  str_pending + "</td>\n" +
//                    "  </tr>\n";
//        }
//        return rowsadd;
//    }
//
//
//    /**
//     * Saving and Investment start
//     * @param v
//     * @param user_invst_goals
//     */
//    private void applySavingandInvestDetails(View v, ArrayList<User_invst_goals> user_invst_goals) {
//        webViewSavingInvesment = (WebView) v.findViewById(R.id.webview_saving);
//        final View loaderView = v.findViewById(R.id.inner_progress_saving);
//        webViewSavingInvesment.setVisibility(GONE);
//        loaderView.setVisibility(GONE);
//
//       /* errorTextview = (TextView) v.findViewById(R.id.empty_values_saving);
//        WebSettings settings = webViewSavingInvesment.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(false);
//        settings.setDomStorageEnabled(true);
//        webViewSavingInvesment.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                loaderView.setVisibility(VISIBLE);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                loaderView.setVisibility(GONE);
//            }
//        });*/
//
//
//        if (saveinvestmodel.getData().getUser_invst_goals()!= null) {
//
//
//            int length=saveinvestmodel.getData().getUser_invst_goals().size();
//
//            for(int i=0;i<length;i++) {
//                asset_hash.add(saveinvestmodel.getData().getUser_invst_goals().get(i).getAsset_class());
//            }
//            ArrayList<String> arrayList = new ArrayList<String>(asset_hash);
//
//
//         ArrayList< ArrayList<User_invst_goals>> mfilterarray = new ArrayList<>();
//
//            for(int j=0;j<arrayList.size();j++){
//
//                String str_obj=arrayList.get(j);
//                {
//                    if (str_obj!= null) {
//                        ArrayList<User_invst_goals>asset_heading=new ArrayList<User_invst_goals>();
//                        for (int k = 0; k < length; k++) {
//                            if (str_obj.equalsIgnoreCase(saveinvestmodel.getData().getUser_invst_goals().get(k).getAsset_class())) {
//                                Log.i("Recomendation", "getLev1_name" + saveinvestmodel.getData().getUser_invst_goals().get(k).getLev1_name().toString());
//                                asset_heading.add(saveinvestmodel.getData().getUser_invst_goals().get(k));
//                                Log.i("Recomendation", "asset_heading size" + asset_heading.size());
//                            }
//                        }
//                        mfilterarray.add(asset_heading);
//                    }
//                }
//
//            }
//
//
//            showWebViewSavingInvest(mfilterarray);
//
//        }
//
//
//    }
//    private void showWebViewSavingInvest(ArrayList<ArrayList<User_invst_goals>> saveinvestmodel) {
//     /*   String htmlContents = "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"150px\", user-scalable=yes\" />" +
//                "<body>\n" +
//                "\n" +
//                "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
//                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
//
//                "  <tr align = \"center\">\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Assets</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Current Values</th> \n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Target future cost</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Time to active</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Resource  used</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Investment needed</th>\n" +
//                "  </tr>\n" +
//                addingSaveInvestTableRows(saveinvestmodel)+
//                "</table>\n" +
//                "\n"+
//                "<br>"+
//
//
//                "</body>\n" +
//                "</html>\n";
//        webViewSavingInvesment.loadData(htmlContents, "text/html", "UTF-8");*/
//
//        loadSaving_InvestmentData(saveinvestmodel);
//    }
//
//    private void loadSaving_InvestmentData(ArrayList<ArrayList<User_invst_goals>> saveinvestmodel) {
//        List<RowHeader> rowHeaders = getRowHeaderListSavingInvest(saveinvestmodel);
//        List<List<Cell>> cellList = getCellListForSortingTestSavingInvest(saveinvestmodel);
//        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameListSavingInvest);
//        recomendationSavingTableAdapter.setAllItems(columnHeaders, rowHeaders, cellList);
//    }
//
//    private List<List<Cell>> getCellListForSortingTestSavingInvest(ArrayList<ArrayList<User_invst_goals>> saveinvestmodel) {
//
//        List<List<Cell>> list = new ArrayList<>();
//
//        int length = saveinvestmodel.size();
//        for (int i=0;i<length;i++) {
//
//
//            int inner_length = saveinvestmodel.get(i).size();
//            if (null != saveinvestmodel) {
//
//                for (int j = 0; j < inner_length; j++) {
//                    List<Cell> cellList = new ArrayList<>();
//                    String asset_class = "", lev1_name = "", current_value = "",
//                            target_fv = "", goal_years = "", salary = "",
//                            property = "", business = "", pmt_1_year = "", pmt_1_mon = "", pval = "";
//                  //  if(i ==0){
//                        if(saveinvestmodel.get(i).get(0).getAsset_class()!= null
//                                &&!saveinvestmodel.get(i).get(0).getAsset_class().isEmpty()){
//                            asset_class=saveinvestmodel.get(i).get(0).getAsset_class();
//                        }
//                //    }
//                     if(saveinvestmodel.get(i).get(j).getLev1_name()!= null){
//                        lev1_name=saveinvestmodel.get(i).get(j).getLev1_name();
//                    }
//
//                     if (saveinvestmodel.get(i).get(j).getCurrent_value() != null) {
//                        current_value = (UtileKit.currToCharConversion(saveinvestmodel.get(i).get(j).
//                                getCurrent_value()));
//                    } else {
//                        current_value = "0";
//                    }
//
//                    if (saveinvestmodel.get(i).get(j).getTarget_fv() != null) {
//                        target_fv = (UtileKit.currToCharConversion(saveinvestmodel.get(i).get(j).getTarget_fv()));
//                    } else {
//                        target_fv = "0";
//                    }
//
//                    if (saveinvestmodel.get(i).get(j).getGoal_years() != null) {
//                        goal_years = saveinvestmodel.get(i).get(j).getGoal_years();
//                    } else {
//                        goal_years = "0";
//                    }
//
//                    if (saveinvestmodel.get(i).get(j).getResources_used().getSalary() != null) {
//                        salary = String.valueOf((Float.parseFloat(saveinvestmodel.get(i).get(j).
//                                getResources_used().getSalary()) * 100));
//                    } else {
//                        salary = "0";
//                    }
//
//                    if (saveinvestmodel.get(i).get(j).getResources_used().getProperty() != null) {
//                        property = String.valueOf((Float.parseFloat(saveinvestmodel.get(i).get(j).
//                                getResources_used().getProperty()) * 100));
//                    } else {
//                        property = "0";
//                    }
//
//                    if (saveinvestmodel.get(i).get(j).getResources_used().getBusiness() != null) {
//                        business = String.valueOf((Float.parseFloat(saveinvestmodel.get(i).get(j).
//                                getResources_used().getBusiness()) * 100));
//                    } else {
//                        business = "0";
//                    }
//
//                    if (saveinvestmodel.get(i).get(j).getPmt_1_year() != null) {
//                        pmt_1_year = (UtileKit.currToCharConversion(saveinvestmodel.get(i).get(j).getPmt_1_year()));
//                    } else {
//                        pmt_1_year = "0";
//                    }
//
//                    if (saveinvestmodel.get(i).get(j).getPmt_1_mon() != null) {
//                        pmt_1_mon = (UtileKit.currToCharConversion(saveinvestmodel.get(i).get(j).getPmt_1_mon()));
//                    } else {
//                        pmt_1_mon = "0";
//                    }
//
//
//                    if (saveinvestmodel.get(i).get(j).getPval() != null) {
//                        pval = (UtileKit.currToCharConversion(saveinvestmodel.get(i).get(j).getPval()));
//                    } else {
//                        pval = "0";
//                    }
//
//
//                    cellList.add(new Cell(i+"04", asset_class+"\n"+lev1_name));
//                    cellList.add(new Cell(i+"00", current_value));
//                    cellList.add(new Cell(i+"01", target_fv));
//                    cellList.add(new Cell(i+"02", goal_years));
//                    cellList.add(new Cell(i+"03", "Salary - "+salary+" % "+"\n"+"Property - "+property+" % "+"\n"+"Business - "+business+" % " ));
//                    cellList.add(new Cell(i+"04",  ""+pmt_1_year+" p.a."+"\n"+"₹ "+pmt_1_mon+" p.m."+"\n"+ "₹ "+pval+" lumpsum"));
//
//
//
//
//
//                    list.add(cellList);
//                }
//
//            }
//        }
//        return list;
//    }
//
//    private List<RowHeader> getRowHeaderListSavingInvest(ArrayList<ArrayList<User_invst_goals>> saveinvestmodel) {
//        List<RowHeader> list = new ArrayList<>();
//
//         int k=0;
//        int n=saveinvestmodel.size();
//        for (int i = 0; i <n ; i++) {
//
//            int inner_length=saveinvestmodel.get(i).size();
//
//            for (int j = 0; j <inner_length ; j++) {
//                RowHeader header = new RowHeader("row " + k, "" + (k ++));
//                list.add(header);
//            }
//        }
//        return list;
//    }
//
//    private String addingSaveInvestTableRows(ArrayList<ArrayList<User_invst_goals>> saveinvestmodelArray) {
//        String rowsadd = "";
//        for (ArrayList<User_invst_goals> saveinvestmodel:saveinvestmodelArray) {
//
//            int length = saveinvestmodel.size();
//            if (null != saveinvestmodel) {
//                for (int i = 0; i < length; i++) {
//                    String asset_class = "", lev1_name = "", current_value = "",
//                            target_fv = "", goal_years = "", resources_used = "", salary = "",
//                            property = "", business = "", pmt_1_year = "", pmt_1_mon = "", pval = "";
//                    if(i ==0){
//                    if(saveinvestmodel.get(i).getAsset_class()!= null){
//                    asset_class=saveinvestmodel.get(i).getAsset_class();
//                }else{
//                    asset_class= "Asset name";
//                }
//               } if(saveinvestmodel.get(i).getLev1_name()!= null){
//                        lev1_name=saveinvestmodel.get(i).getLev1_name();
//                    }else{
//                        lev1_name= "Level name";
//                    }
//                    if (saveinvestmodel.get(i).getCurrent_value() != null) {
//                        current_value = (UtileKit.longvalueabsolute(Float.parseFloat(saveinvestmodel.get(i).
//                                getCurrent_value())));
//                    } else {
//                        current_value = "0";
//                    }
//                    if (saveinvestmodel.get(i).getTarget_fv() != null) {
//                        target_fv = (UtileKit.longvalueabsolute(Float.parseFloat(saveinvestmodel.get(i).getTarget_fv())));
//                    } else {
//                        target_fv = "0";
//                    }
//                    if (saveinvestmodel.get(i).getGoal_years() != null) {
//                        goal_years = saveinvestmodel.get(i).getGoal_years();
//                    } else {
//                        goal_years = "0";
//                    }
//                    if (saveinvestmodel.get(i).getResources_used().getSalary() != null) {
//                        salary = String.valueOf((Float.parseFloat(saveinvestmodel.get(i).
//                                getResources_used().getSalary()) * 100));
//                    } else {
//                        salary = "0";
//                    }
//                    if (saveinvestmodel.get(i).getResources_used().getProperty() != null) {
//                        property = String.valueOf((Float.parseFloat(saveinvestmodel.get(i).
//                                getResources_used().getProperty()) * 100));
//                    } else {
//                        property = "0";
//                    }
//
//                    if (saveinvestmodel.get(i).getResources_used().getBusiness() != null) {
//                        business = String.valueOf((Float.parseFloat(saveinvestmodel.get(i).
//                                getResources_used().getBusiness()) * 100));
//                    } else {
//                        business = "0";
//                    }
//                    if (saveinvestmodel.get(i).getPmt_1_year() != null) {
//                        pmt_1_year = (UtileKit.longvalueabsolute(Float.parseFloat(saveinvestmodel.get(i).getPmt_1_year())));
//                    } else {
//                        pmt_1_year = "0";
//                    }
//                    if (saveinvestmodel.get(i).getPmt_1_mon() != null) {
//                        pmt_1_mon = (UtileKit.longvalueabsolute(Float.parseFloat(saveinvestmodel.get(i).getPmt_1_mon())));
//                    } else {
//                        pmt_1_mon = "0";
//                    }
//
//                    if (saveinvestmodel.get(i).getPval() != null) {
//                        pval = (UtileKit.longvalueabsolute(Float.parseFloat(saveinvestmodel.get(i).getPval())));
//                    } else {
//                        pval = "0";
//                    }
//
//
//                    rowsadd = rowsadd + "  <tr align = \"center\">\n" +
//                            "    <td>" + "<font color=\"#6699FF\">" + asset_class + "</font>" + "<br>" + lev1_name + "</td>\n" +
//                            "    <td>" + rs + " " + current_value + "</td>\n" +
//                            "    <td>" + rs + " " + target_fv + "</td>\n" +
//                            "    <td>" + goal_years + "</td>\n" +
//                            "    <td>" + "Salary - " + salary + " % " + "<br>" + "Property - " + property + " % " + "<br>" + "Business - " + business + " % " + "</td>\n" +
//                            "    <td>" + rs + " " + pmt_1_year + " p.a." + "<br>" + rs + " " + pmt_1_mon + " p.m." + "<br>" + rs + " " + pval + " lumpsum" + "</td>\n" +
//                            "  </tr>\n";
//                }
//            }
//        }
//        return rowsadd;
//    }
//    //Saving and Investment end
//
//    //Goal start
//    private void applyGoals(@NonNull View v, @NonNull ArrayList<User_goals> usergoals) {
//        webView_goals = (WebView) v.findViewById(R.id.webview_goals);
//
//        final View loaderView = v.findViewById(R.id.inner_progress_goals);
//        webView_goals.setVisibility(View.GONE);
////        WebSettings settings = webView_goals.getSettings();
////        settings.setJavaScriptEnabled(true);
////        settings.setSupportZoom(true);
////        settings.setBuiltInZoomControls(true);
////        settings.setDisplayZoomControls(false);
////        settings.setLoadWithOverviewMode(true);
////        settings.setUseWideViewPort(false);
////        settings.setDomStorageEnabled(true);
////        webView_goals.setWebViewClient(new WebViewClient() {
////            @Override
////            public void onPageStarted(WebView view, String url, Bitmap favicon) {
////                super.onPageStarted(view, url, favicon);
////                loaderView.setVisibility(VISIBLE);
////            }
////
////            @Override
////            public void onPageFinished(WebView view, String url) {
////                super.onPageFinished(view, url);
////                loaderView.setVisibility(GONE);
////            }
////        });
//
//
//        if (goalmodel.getData().getUser_goals()!= null) {
//            int length = goalmodel.getData().getUser_goals().size();
//            per1year= new BigDecimal(length);
//            per1month = new BigDecimal(length);
//            pvals =new BigDecimal(length);
//            try {
//                for (int k = 0; k < length; k++) {
//                    per1year = per1year.add(new BigDecimal(UtileKit.rounddecimalNumber(goalmodel.getData().
//                            getUser_goals().get(k).getPmt_1_year())));
//                    per1month =per1month.add(new BigDecimal(UtileKit.rounddecimalNumber(goalmodel.getData().
//                            getUser_goals().get(k).getPmt_1_mon())));
//                    pvals =pvals.add( new BigDecimal(UtileKit.rounddecimalNumber(goalmodel.getData().
//                            getUser_goals().get(k).getPval())));
//
//                     per1year2=(UtileKit.longvalueabsolute(Float.parseFloat(String.valueOf(per1year))));
//                     per1month2=(UtileKit.longvalueabsolute(Float.parseFloat(String.valueOf(per1month))));
//                     pvals2=(UtileKit.longvalueabsolute(Float.parseFloat(String.valueOf(pvals))));
//
//
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            showWebViewGoal(goalmodel);
//            loaderView.setVisibility(GONE);
//
//        }
//        else {
//            loaderView.setVisibility(GONE);
//        }
//    }
//    private void showWebViewGoal(Goalmodel goalmodel) {
////        String htmlContents = "<!DOCTYPE html>\n" +
////                "<html>\n" +
////                "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"150px\", user-scalable=yes\" />" +
////                "<body>\n" +
////                "\n" +
////                "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
////                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
////
////
////                "  <tr align = \"center\">\n" +
////                "    <th style=\"padding-left:10px;padding-right:10px;\">Goal</th>\n" +
////                "    <th style=\"padding-left:10px;padding-right:10px;\">Present cost</th> \n" +
////                "    <th style=\"padding-left:10px;padding-right:10px;\">Current fund</th>\n" +
////                "    <th style=\"padding-left:10px;padding-right:10px;\">Target future cost</th>\n" +
////                "    <th style=\"padding-left:10px;padding-right:10px;\">Time to active</th>\n" +
////                "    <th style=\"padding-left:10px;padding-right:10px;\">Resource  used</th>\n" +
////                "    <th style=\"padding-left:10px;padding-right:10px;\">Investment needed</th>\n" +
////                "  </tr>\n" +
////                addingTableRows(goalmodel)+
////                "</table>\n" +
////                "\n"+
////                "<br>"+
////
////                " Your investment surplus needed <font color=\"#6699FF\"><b>"
////                +rs+" "+per1year2 + "</b></font>" + "<font color=\"#6699FF\"> p.a. </font>" +
////                " | <font color=\"#6699FF\"><b>"
////                +rs+" "+per1month2 + "</b></font>" + "<font color=\"#6699FF\"> p.m. </font>" +
////                " | <font color=\"#6699FF\"><b>"
////                +rs+" "+pvals2 + "</b></font>" + "<font color=\"#6699FF\">  lumpsum</font>" +
////                "</body>\n" +
////                "</html>\n";
////        webView_goals.loadData(htmlContents, "text/html", "UTF-8");
//
//        surplus_needed.setText(" Your investment surplus needed"+UtileKit.fromHtml("₹ "+"<b>" +"<u>"+per1year2+"</u>"+
//                "</b>")+" "+"p.a.");
//
//        goal_permonths.setText(UtileKit.fromHtml("₹ "+"<b>" +"<u>"+per1month2+"</u>"+"</b>"+" "+"p.a."));
//
//        goal_pvalues.setText(UtileKit.fromHtml("₹ "+"<b>" +"<u>"+pvals2+"</u>"+"</b>"+" "+"lumpsum"));
//        loadGoalData(goalmodel);
//    }
//    private void loadGoalData(Goalmodel goalmodel) {
//        List<RowHeader> rowHeaders = getRowHeaderList(goalmodel);
//        List<List<Cell>> cellList = getCellListForSortingTest(goalmodel);
//        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameList);
//        recomendationSavingTableAdapter.setAllItems(columnHeaders, rowHeaders, cellList);
//    }
//    private List<ColumnHeader> getColumnHeaderListCash(ArrayList<String> mColoumNameList) {
//        List<ColumnHeader> list = new ArrayList<>();
//        for (int i = 0; i < mColoumNameList.size(); i++) {
//            ColumnHeader header = new ColumnHeader(""+i, mColoumNameList.get(i));
//            list.add(header);
//        }
//        return list;
//    }
//    private List<RowHeader> getRowHeaderList(Goalmodel goalmodel) {
//        List<RowHeader> list = new ArrayList<>();
//
//        int n=goalmodel.getData().getUser_goals().size();
//        for (int i = 0; i <n ; i++) {
//            RowHeader header = new RowHeader("row " + i,""+(i+1));
//            list.add(header);
//        }
//        return list;
//    }
//    private  List<List<Cell>> getCellListForSortingTest(Goalmodel goalmodel) {
//        List<List<Cell>> list = new ArrayList<>();
//        String rowsadd ="";
//        int length = goalmodel.getData().getUser_goals().size();
//        if (null!=goalmodel.getData().getUser_goals()) {
//            for (int i = 0; i < length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//                Cell cell;
//                String goalname="",costofgoal="",fundvalue="",
//                        fv_1_year="",goal_year="",growth_rate="",pmt_1year="",
//                        pmt_1_mon="",pval="",equity="",debt="",liquid="";
//
//                if(goalmodel.getData().getUser_goals().get(i).getGoal_name()!= null){
//                    goalname=goalmodel.getData().getUser_goals().get(i).getGoal_name();
//                }else{
//                    goalname= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getCost_of_goal()!= null&&!
//                        goalmodel.getData().getUser_goals().get(i).getCost_of_goal().isEmpty())
//                {
//                    costofgoal=(UtileKit.currToCharConversion(goalmodel.getData().
//                            getUser_goals().get(i).getCost_of_goal()));
//                }else{
//                    costofgoal= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getFund_val()!= null){
//                    fundvalue=(UtileKit.currToCharConversion(goalmodel.getData().
//                            getUser_goals().get(i).getFund_val()));
//                }else{
//                    fundvalue= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getFv_1_year()!= null){
//                    fv_1_year=(UtileKit.currToCharConversion(goalmodel.getData().
//                            getUser_goals().get(i).getFv_1_year()));
//                }else{
//                    fv_1_year= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getGoal_years()!= null){
//                    goal_year=goalmodel.getData().getUser_goals().get(i).getGoal_years();
//                }else{
//                    goal_year= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getPmt_1_year()!= null){
//                    pmt_1year=(UtileKit.currToCharConversion(goalmodel.getData().
//                            getUser_goals().get(i).getPmt_1_year()));
//                }else{
//                    pmt_1year= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getPmt_1_mon()!= null){
//                    pmt_1_mon=(UtileKit.currToCharConversion(goalmodel.getData().
//                            getUser_goals().get(i).getPmt_1_mon()));
//                }else{
//                    pmt_1_mon= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getPval()!= null){
//                    pval=(UtileKit.currToCharConversion(goalmodel.getData().
//                            getUser_goals().get(i).getPval()));
//                }else{
//                    pval= "0";
//                }
//
//                if(goalmodel.getData().getUser_goals().get(i).getResources_used().getEquity()!= null){
//                    equity=String.valueOf((Float.parseFloat(goalmodel.getData().getUser_goals().get(i).getResources_used().
//                            getEquity())*100));
//                }else{
//                    equity= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getResources_used().getDebt()!= null){
//                    debt=String.valueOf((Float.parseFloat(goalmodel.getData().getUser_goals().get(i).getResources_used().
//                            getDebt())*100));
//                }else{
//                    debt= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getResources_used().getLiquid()!= null){
//                    liquid= String.valueOf((Float.parseFloat(goalmodel.getData().getUser_goals().get(i).getResources_used().
//                            getLiquid())*100));
//                }else{
//                    liquid= "0";
//                }
//                cellList.add(new Cell(i+"00", goalname));
//                cellList.add(new Cell(i+"01", costofgoal));
//                cellList.add(new Cell(i+"02", fundvalue));
//                cellList.add(new Cell(i+"03", fv_1_year));
//                cellList.add(new Cell(i+"03", goal_year));
//                cellList.add(new Cell(i+"04", "Equity - "+equity+" % "+"\n"+"Debt - "+debt+" % "+"\n"+"Liquid - "+liquid+" % " ));
//                cellList.add(new Cell(i+"05",  "₹ "+pmt_1year+" p.a."+"\n"+"₹ "+pmt_1_mon+" p.m."+"\n"+ "₹ "+pval+" lumpsum"));
//
////                rowsadd =rowsadd  + "  <tr align = \"center\">\n" +
////                        "    <td>" +goalname+"</td>\n" +
////                        "    <td>" +rs+" "+costofgoal+"</td>\n" +
////                        "    <td>" +rs+" "+fundvalue+"</td>\n" +
////                        "    <td>" +rs+" "+fv_1_year+"</td>\n" +
////                        "    <td>" +goal_year +"</td>\n" +
////                        "    <td>" +"Equity - "+equity+" % "+"<br>"+"Debt - "+debt+" % "+"<br>"+"Liquid - "+liquid+" % "+"</td>\n" +
////                        "    <td>" +rs+" "+pmt_1year+" p.a."+"<br>"+rs+" "+pmt_1_mon+" p.m."+"<br>"+rs+" "+pval+" lumpsum"+"</td>\n" +
////                        "  </tr>\n" ;
//
//                list.add(cellList);
//            }
//        }
//        return list;
//    }
//    //Goal end
//    /*private String addingTableRows(Goalmodel goalmodel) {
//
//        String rowsadd ="";
//        int length = goalmodel.getData().getUser_goals().size();
//        if (null!=goalmodel.getData().getUser_goals()) {
//            for (int i = 0; i < length; i++) {
//                String goalname="",costofgoal="",fundvalue="",
//                        fv_1_year="",goal_year="",growth_rate="",pmt_1year="",
//                        pmt_1_mon="",pval="",equity="",debt="",liquid="";
//
//                if(goalmodel.getData().getUser_goals().get(i).getGoal_name()!= null){
//                    goalname=goalmodel.getData().getUser_goals().get(i).getGoal_name();
//                }else{
//                    goalname= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getCost_of_goal()!= null&&!
//                        goalmodel.getData().getUser_goals().get(i).getCost_of_goal().isEmpty())
//                {
//                    costofgoal=(UtileKit.longvalueabsolute(Float.parseFloat(goalmodel.getData().
//                            getUser_goals().get(i).getCost_of_goal())));
//                }else{
//                    costofgoal= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getFund_val()!= null){
//                    fundvalue=(UtileKit.longvalueabsolute(Float.parseFloat(goalmodel.getData().
//                            getUser_goals().get(i).getFund_val())));
//                }else{
//                    fundvalue= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getFv_1_year()!= null){
//                    fv_1_year=(UtileKit.longvalueabsolute(Float.parseFloat(goalmodel.getData().
//                            getUser_goals().get(i).getFv_1_year())));
//                }else{
//                    fv_1_year= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getGoal_years()!= null){
//                    goal_year=goalmodel.getData().getUser_goals().get(i).getGoal_years();
//                }else{
//                    goal_year= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getPmt_1_year()!= null){
//                    pmt_1year=(UtileKit.longvalueabsolute(Float.parseFloat(goalmodel.getData().
//                            getUser_goals().get(i).getPmt_1_year())));
//                }else{
//                    pmt_1year= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getPmt_1_mon()!= null){
//                    pmt_1_mon=(UtileKit.longvalueabsolute(Float.parseFloat(goalmodel.getData().
//                            getUser_goals().get(i).getPmt_1_mon())));
//                }else{
//                    pmt_1_mon= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getPval()!= null){
//                    pval=(UtileKit.longvalueabsolute(Float.parseFloat(goalmodel.getData().
//                            getUser_goals().get(i).getPval())));
//                }else{
//                    pval= "0";
//                }
//
//                if(goalmodel.getData().getUser_goals().get(i).getResources_used().getEquity()!= null){
//                    equity=String.valueOf((Float.parseFloat(goalmodel.getData().getUser_goals().get(i).getResources_used().
//                            getEquity())*100));
//                }else{
//                    equity= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getResources_used().getDebt()!= null){
//                    debt=String.valueOf((Float.parseFloat(goalmodel.getData().getUser_goals().get(i).getResources_used().
//                            getDebt())*100));
//                }else{
//                    debt= "0";
//                }
//                if(goalmodel.getData().getUser_goals().get(i).getResources_used().getLiquid()!= null){
//                    liquid= String.valueOf((Float.parseFloat(goalmodel.getData().getUser_goals().get(i).getResources_used().
//                            getLiquid())*100));
//                }else{
//                    liquid= "0";
//                }
//
//                rowsadd =rowsadd  + "  <tr align = \"center\">\n" +
//                        "    <td>" +goalname+"</td>\n" +
//                        "    <td>" +rs+" "+costofgoal+"</td>\n" +
//                        "    <td>" +rs+" "+fundvalue+"</td>\n" +
//                        "    <td>" +rs+" "+fv_1_year+"</td>\n" +
//                        "    <td>" +goal_year +"</td>\n" +
//                        "    <td>" +"Equity - "+equity+" % "+"<br>"+"Debt - "+debt+" % "+"<br>"+"Liquid - "+liquid+" % "+"</td>\n" +
//                        "    <td>" +rs+" "+pmt_1year+" p.a."+"<br>"+rs+" "+pmt_1_mon+" p.m."+"<br>"+rs+" "+pval+" lumpsum"+"</td>\n" +
//                        "  </tr>\n" ;
//            }
//        }
//        return rowsadd;
//    }*/
//    //Goal end
//
//
//    //Cash Management start
//    private void applyCashManagement(@NonNull View v, @NonNull Cash_mang_det cash_mang_det) {
//        webViewCashManagement = (WebView) v.findViewById(R.id.webview_cashmang);
//        final View loaderView = v.findViewById(R.id.inner_progress_cashmang);
//        WebSettings settings = webViewCashManagement.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(false);
//        settings.setDomStorageEnabled(true);
//        webViewCashManagement.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                loaderView.setVisibility(VISIBLE);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                loaderView.setVisibility(GONE);
//            }
//        });
//
//
//        if(cashmodel.getData().getCash_mang_det()!= null){
//
//            String si_total_exist="",ip_total_exist="",ib_total_exist="",
//                    cg_total_exist="",ifs_total_exist="";
//            if(cashmodel.getData().getCash_mang_det().getSi_total_exist()!= null){
//                si_total_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getSi_total_exist())));
//            }else{
//                si_total_exist= "0";
//            }
//
//            if(cashmodel.getData().getCash_mang_det().getIp_total_exist()!= null){
//                ip_total_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getIp_total_exist())));
//            }else{
//                ip_total_exist= "0";
//            }
//            if(cashmodel.getData().getCash_mang_det().getIb_total_exist()!= null){
//                ib_total_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getIb_total_exist())));
//            }else{
//                ib_total_exist= "0";
//            }
//            if(cashmodel.getData().getCash_mang_det().getCg_total_exist()!= null){
//                cg_total_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getCg_total_exist())));
//            }else{
//                cg_total_exist= "0";
//            }
//            if(cashmodel.getData().getCash_mang_det().getIfs_total_exist()!= null){
//                ifs_total_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getIfs_total_exist())));
//            }else{
//                ifs_total_exist= "0";
//            }
//
//
//            String si_total_sugg="",ip_total_sugg="",ib_total_sugg="",
//                    cg_total_sugg="",ifs_total_sugg="";
//
//            if(cashmodel.getData().getCash_mang_det().getSi_total_sugg()!= null){
//                si_total_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getSi_total_sugg())));
//            }else{
//                si_total_sugg= "0";
//            }
//
//            if(cashmodel.getData().getCash_mang_det().getIp_total_sugg()!= null){
//                ip_total_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getIp_total_sugg())));
//            }else{
//                ip_total_sugg= "0";
//            }
//            if(cashmodel.getData().getCash_mang_det().getIb_total_sugg()!= null){
//                ib_total_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getIb_total_sugg())));
//            }else{
//                ib_total_sugg= "0";
//            }
//            if(cashmodel.getData().getCash_mang_det().getCg_total_sugg()!= null){
//                cg_total_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getCg_total_sugg())));
//            }else{
//                cg_total_sugg= "0";
//            }
//            if(cashmodel.getData().getCash_mang_det().getIfs_total_sugg()!= null){
//                ifs_total_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getIfs_total_sugg())));
//            }else{
//                ifs_total_sugg= "0";
//            }
//
//            String over_all_exp_exist="",over_all_commt_exist="",over_all_obli_exist="",
//                    over_all_contr_exist="";
//
//
//            if(cashmodel.getData().getCash_mang_det().getOver_all_exp_exist()!= null){
//                over_all_exp_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getOver_all_exp_exist())));
//            }else{
//                over_all_exp_exist= "0";
//            }
//            if(cashmodel.getData().getCash_mang_det().getOver_all_commt_exist()!= null){
//                over_all_commt_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getOver_all_commt_exist())));
//            }else{
//                over_all_commt_exist= "0";
//            }
//            if(cashmodel.getData().getCash_mang_det().getOver_all_obli_exist()!= null){
//                over_all_obli_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getOver_all_obli_exist())));
//            }else{
//                over_all_obli_exist= "0";
//            }
//            if(cashmodel.getData().getCash_mang_det().getOver_all_contr_exist()!= null){
//                over_all_contr_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getOver_all_contr_exist())));
//            }else{
//                over_all_contr_exist= "0";
//            }
//
//            String over_all_exp_sugg="",over_all_commt_sugg="",over_all_obli_sugg="",
//                    over_all_contr_sugg="";
//
//            if(cashmodel.getData().getCash_mang_det().getOver_all_exp_sugg()!= null){
//                over_all_exp_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getOver_all_exp_sugg())));
//            }else{
//                over_all_exp_sugg= "0";
//            }
//            if(cashmodel.getData().getCash_mang_det().getOver_all_commt_sugg()!= null){
//                over_all_commt_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getOver_all_commt_sugg())));
//            }else{
//                over_all_commt_sugg= "0";
//            }
//            if(cashmodel.getData().getCash_mang_det().getOver_all_obli_sugg()!= null){
//                over_all_obli_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getOver_all_obli_sugg())));
//            }else{
//                over_all_obli_sugg= "0";
//            }
//            if(cashmodel.getData().getCash_mang_det().getOver_all_contr_sugg()!= null){
//                over_all_contr_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
//                        getCash_mang_det().getOver_all_contr_sugg())));
//            }else{
//                over_all_contr_sugg= "0";
//            }
//
//
//            if(cashmodel.getData().getCash_mang_det().getDeflict()!= null){
//                if(Integer.parseInt(cashmodel.getData().getCash_mang_det().getDeflict())<0) {
//                    deflict = (UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().getCash_mang_det().
//                            getDeflict())))+" Deficit";
//                    deficit_color="<font color='#FF0000'>" +rs+" "+ deflict + "</font>";
//                }
//                else if(Integer.parseInt(cashmodel.getData().getCash_mang_det().getDeflict())>0){
//                    deflict = (UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().getCash_mang_det().
//                            getDeflict())))+" Surplus";
//                    deficit_color="<font color='#006400'>" +rs+" "+ deflict + "</font>";
//                }
//            }else{
//                deflict= "0";
//            }
//
//
//
//            String htmlContents = "<!DOCTYPE html>\n" +
//                    "<html>\n" +
//                    "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"150px\", user-scalable=yes\" />" +
//                    "<body>\n" +
//                    "\n" +
//
//                    "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
//                    "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
//
//                    "  <tr align = \"center\">\n" +
//                    "    <th width = \"150px\">Earning</th>\n" +
//                    "    <th width = \"150px\">Existings</th> \n" +
//                    "    <th width = \"150px\">Suggested</th>\n" +
//                    "  </tr>\n" +
//                    "  <tr align = \"center\">\n" +
//                    "    <td>Salary</td>\n" +
//                    "    <td>" +rs+" "+ si_total_exist + "</td>\n" +
//                    "    <td>" +rs+" "+ si_total_sugg  + "</td>\n" +
//                    "  </tr>\n" +
//                    "  <tr align = \"center\">\n" +
//                    "    <td>Property</td>\n" +
//                    "    <td>" +rs+" "+ ip_total_exist + "</td>\n" +
//                    "    <td>" +rs+" "+ ip_total_sugg + "</td>\n" +
//                    "  </tr>\n" +
//                    "  <tr align = \"center\">\n" +
//                    "    <td>Business</td>\n" +
//                    "    <td>" +rs+" "+ ib_total_exist + "</td>\n" +
//                    "    <td>" +rs+" "+ ib_total_sugg + "</td>\n" +
//                    "  </tr>\n" +
//                    "  <tr align = \"center\">\n" +
//                    "    <td>Capital Gains</td>\n" +
//                    "    <td>" +rs+" "+ cg_total_exist + "</td>\n" +
//                    "    <td>" +rs+" "+ cg_total_sugg + "</td>\n" +
//                    "  </tr>\n" +
//                    "  <tr align = \"center\">\n" +
//                    "    <td>Other Source</td>\n" +
//                    "    <td>" +rs+" "+ ifs_total_exist + "</td>\n" +
//                    "    <td>" +rs+" "+ ifs_total_sugg +  "</td>\n" +
//                    "  </tr>\n" +
//                    "</table>\n" +
//
//                    "</p>" +
//                    "\n" +
//
//
//                    "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"150px\", user-scalable=yes\" />" +
//                    "<body>\n" +
//                    "\n" +
//
//
//                    "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
//                    "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
//
//                    "  <tr align = \"center\">\n" +
//                    "    <th width = \"150px\">Expenses</th>\n" +
//                    "    <th width = \"150px\">Existings</th> \n" +
//                    "    <th width = \"150px\">Suggested</th>\n" +
//                    "  </tr>\n" +
//                    "  <tr align = \"center\">\n" +
//                    "    <td>Expense</td>\n" +
//                    "    <td>" +rs+" "+ over_all_exp_exist + "</td>\n" +
//                    "    <td>" +rs+" "+ over_all_exp_sugg + "</td>\n" +
//                    "  </tr>\n" +
//                    "  <tr align = \"center\">\n" +
//                    "    <td>Commitment</td>\n" +
//                    "    <td>" +rs+" "+ over_all_commt_exist + "</td>\n" +
//                    "    <td>" +rs+" "+ over_all_commt_sugg + "</td>\n" +
//                    "  </tr>\n" +
//                    "  <tr align = \"center\">\n" +
//                    "    <td>Obligation</td>\n" +
//                    "    <td>" +rs+" "+ over_all_obli_exist+ "</td>\n" +
//                    "    <td>" +rs+" "+ over_all_obli_sugg + "</td>\n" +
//                    "  </tr>\n" +
//                    "  <tr align = \"center\">\n" +
//                    "    <td>Contribution</td>\n" +
//                    "    <td>" +rs+" "+ over_all_contr_exist+ "</td>\n" +
//                    "    <td>" +rs+" "+ over_all_contr_sugg + "</td>\n" +
//                    "  </tr>\n" +
//                    "</table>\n" +
//
//                    "<br> <p align = \"center\"> Your existing cashflow for the next 1 year is <font color=\"\"><b>"
//                    + deficit_color + "</b></font><br>" +
//
//                    " <p align = \"center\"> By adjusting your cashflow to the suggestion your cashflow for the next 1 year will be <font color=\"#7a0098;\"><b>"
//                    +rs+" "+ 0 + "</b></font><br>" +
//
//
//                    "</body>\n" +
//                    "</html>\n";
//            webViewCashManagement.loadData(htmlContents, "text/html", "UTF-8");
//        }}
//    //Cash Management end
//
//
//
//    //New Insurance plan start
//
//  /*  private void applyInsurancePlanDetails(@NonNull View v, @NonNull Ins_plan insPlan) {
//        webViewIns_plan = (WebView) v.findViewById(R.id.webview);
//        final View loaderView = v.findViewById(R.id.inner_progress);
//        webViewIns_plan.setVisibility(GONE);
//
//
//
//            loanPlan_Res_Insurance(recommendData);
//    }
//
//    private void loanPlan_Res_Insurance(RecommendData recommendData) {
//        List<RowHeader> rowHeaders = getRowHeaderListInsPlanRes(recommendData);
//        List<List<Cell>> cellList = getCellListForSortingTestInsPlanRes(recommendData);
//        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameListIns_Plan_Res);
//        mTableViewAdapter.setAllItems(columnHeaders, rowHeaders, cellList);
//    }
//
//    private List<List<Cell>> getCellListForSortingTestInsPlanRes(RecommendData recommendData) {
//
//        List<List<Cell>> list = new ArrayList<>();
//
//        int length = recommendData.getData().getIns_plan().getPlan_res().size();
//
//        if (null!=recommendData.getData().getIns_plan()) {
//
//            for (int i = 0; i < length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//
//                String coverage="",annual_prem="",ins_prod_type="";
//
//                if(recommendData.getData().getIns_plan().getPlan_res().get(i).getIns_type()!= null){
//                    ins_prod_type=recommendData.getData().getIns_plan().getPlan_res().get(i).getIns_type();
//                }else{
//                    ins_prod_type= "0";
//                }
//                if(recommendData.getData().getIns_plan().getPlan_res().get(i).getCoverage()!= null&&
//                        Float.parseFloat(recommendData.getData().getIns_plan().getPlan_res().get(i).getCoverage())>0){
//                    coverage="₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(recommendData.getData().
//                            getIns_plan().getPlan_res().get(i).getCoverage())));
//                }else{
//                    coverage= "₹ "+"0";
//                }
//                if(recommendData.getData().getIns_plan().getPlan_res().get(i).getAnnual_prem()!= null&&
//                        Float.parseFloat(recommendData.getData().getIns_plan().getPlan_res().get(i).getAnnual_prem())>0){
//                    annual_prem="₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(recommendData.getData().
//                            getIns_plan().getPlan_res().get(i).getAnnual_prem())));
//                }else{
//                    annual_prem= "₹ "+"0";
//                }
//
//
//
//                cellList.add(new Cell(i+"00", ins_prod_type));
//                cellList.add(new Cell(i+"01", coverage));
//                cellList.add(new Cell(i+"02", annual_prem));
//
//                list.add(cellList);
//
//            }
//        }
//        return list;
//    }
//
//    private List<RowHeader> getRowHeaderListInsPlanRes(RecommendData recommendData) {
//        List<RowHeader> list = new ArrayList<>();
//        int n=recommendData.getData().getIns_plan().getPlan_res().size();
//        for (int i = 0; i <n ; i++) {
//            RowHeader header = new RowHeader("row " + i,""+(i+1));
//            list.add(header);
//        }
//        return list;
//    }
//
//    private void applyInsurancePlanDetailss(View view, Ins_plan insPlan) {
//
//        if (recommendData.getData().getIns_plan().getHlv()!= null) {
//            hlvValue=recommendData.getData().getIns_plan().getHlv();
//        } else {
//            hlvValue = "0";
//        }
//        try {
//            hlvValue = (UtileKit.longvalueabsolute(Float.parseFloat(hlvValue)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        human_life_value.setText("Your estimated human life value"+"₹ "+hlvValue);
//        loadInsurance_Plan(recommendData);
//    }
//
//    private void loadInsurance_Plan(RecommendData recommendData) {
//        List<RowHeader> rowHeaders = getRowHeaderListInsPlan(mRownameInsurancePlan);
//        List<List<Cell>> cellList = getCellListForSortingTestInsPlans(recommendData);
//        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameListIns_Plan);
//        recomendationLifeInsuranceTableAdapter.setAllItems(columnHeaders, rowHeaders, cellList);
//    }
//
//    private List<List<Cell>> getCellListForSortingTestInsPlans(RecommendData recommendData) {
//        List<List<Cell>> list = new ArrayList<>();
//
//        if (recommendData.getData().getIns_plan()!= null) {
//
//
//
//
//            String min_Req_Cover="",min_Req_Cover_Purchase="",
//                    max_Req_Cover="",max_Req_Cover_Purchase="";
//
//            String sugges_min_Req_Cover="₹ "+"0",sugges_min_Req_Cover_Purchase="₹ "+"0",
//                    sugges_max_Req_Cover="₹ "+"0",sugges_max_Req_Cover_Purchase="₹ "+"0";
//
//            if (recommendData.getData().getIns_plan().getMin_cov_need()!= null&&
//                    (Float.parseFloat(recommendData.getData().getIns_plan().getMin_cov_need())>0)) {
//
//                min_Req_Cover="₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(recommendData.getData().
//                        getIns_plan().getMin_cov_need())));
//            }
//
//            else {
//                min_Req_Cover = "₹ "+"0";
//            }
//            if (recommendData.getData().getIns_plan().getMin_cur_ins_cov()!= null&&
//                    (Float.parseFloat(recommendData.getData().getIns_plan().getMin_cur_ins_cov())>0)){
//
//                min_Req_Cover_Purchase="₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(recommendData.getData().
//                        getIns_plan().getMin_cur_ins_cov())));
//            } else {
//                min_Req_Cover_Purchase ="₹ "+ "0";
//            }
//            if (recommendData.getData().getIns_plan().getMax_cov_need()!= null&&
//                    (Float.parseFloat(recommendData.getData().getIns_plan().getMax_cov_need())>0)) {
//
//                max_Req_Cover="₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(recommendData.getData().getIns_plan().
//                        getMax_cov_need())));
//            } else {
//                max_Req_Cover = "₹ "+"0";
//            }
//            if (recommendData.getData().getIns_plan().getMax_cur_ins_cov()!= null&&
//                    (Float.parseFloat(recommendData.getData().getIns_plan().getMax_cur_ins_cov())>0)) {
//
//                max_Req_Cover_Purchase="₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(recommendData.getData().getIns_plan().
//                        getMax_cur_ins_cov())));
//            } else {
//                max_Req_Cover_Purchase = "₹ "+"0";
//            }
//            List<Cell> cellList = new ArrayList<>();
//            cellList.add(new Cell("", "Minimum Required Cover"));
//            cellList.add(new Cell("", min_Req_Cover));
//            cellList.add(new Cell("", sugges_min_Req_Cover));
//            list.add(cellList);
//
//            List<Cell> cellList2 = new ArrayList<>();
//            cellList2.add(new Cell("", "Minimum Required Cover to Purchage"));
//            cellList2.add(new Cell("", min_Req_Cover_Purchase));
//            cellList2.add(new Cell("", sugges_min_Req_Cover_Purchase));
//            list.add(cellList2);
//
//            List<Cell> cellList3 = new ArrayList<>();
//            cellList3.add(new Cell("", "Maximum Required Cover"));
//            cellList3.add(new Cell("", max_Req_Cover));
//            cellList3.add(new Cell("", sugges_max_Req_Cover));
//            list.add(cellList3);
//
//            List<Cell> cellList4 = new ArrayList<>();
//            cellList4.add(new Cell("", "Maxmum Required Cover to Purchage"));
//            cellList4.add(new Cell("", max_Req_Cover_Purchase));
//            cellList4.add(new Cell("", sugges_max_Req_Cover_Purchase));
//            list.add(cellList4);
//        }
//
//
//        return list;
//    }
//
//    private List<RowHeader> getRowHeaderListInsPlan(ArrayList<String> mRownameInsurancePlan) {
//
//        List<RowHeader> list = new ArrayList<>();
//        int n= mRownameInsurancePlan.size();
//        for (int i = 0; i <n ; i++) {
//            RowHeader header = new RowHeader("row " + i,""+(i+1));
//            list.add(header);
//        }
//        return list;
//
//    }*/
//
//    private void applyInsurancePlanDetails(@NonNull View v, @NonNull Ins_plan insPlan) {
//        webViewIns_plan = (WebView) v.findViewById(R.id.webview);
//        final View loaderView = v.findViewById(R.id.inner_progress);
//        WebSettings settings = webViewIns_plan.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(false);
//        settings.setDomStorageEnabled(true);
//        webViewIns_plan.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                loaderView.setVisibility(VISIBLE);
//            }
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                loaderView.setVisibility(GONE);
//            }
//        });
//        if (recommendData.getData().getIns_plan()!= null) {
//
//            if (recommendData.getData().getIns_plan().getMin_cov_need()!= null&&
//                    (Float.parseFloat(recommendData.getData().getIns_plan().getMin_cov_need())>0)) {
//
//                min_Req_Cover=(UtileKit.currToCharConversion(recommendData.getData().
//                        getIns_plan().getMin_cov_need()));
//            }
//
//            else {
//                min_Req_Cover = "0";
//            }
//            if (recommendData.getData().getIns_plan().getMin_cur_ins_cov()!= null&&
//                    (Float.parseFloat(recommendData.getData().getIns_plan().getMin_cur_ins_cov())>0)){
//
//                min_Req_Cover_Purchase=(UtileKit.currToCharConversion(recommendData.getData().
//                        getIns_plan().getMin_cur_ins_cov()));
//            } else {
//                min_Req_Cover_Purchase = "0";
//            }
//            if (recommendData.getData().getIns_plan().getMax_cov_need()!= null&&
//                    (Float.parseFloat(recommendData.getData().getIns_plan().getMax_cov_need())>0)) {
//
//                max_Req_Cover=(UtileKit.currToCharConversion(recommendData.getData().getIns_plan().
//                        getMax_cov_need()));
//            } else {
//                max_Req_Cover = "0";
//            }
//            if (recommendData.getData().getIns_plan().getMax_cur_ins_cov()!= null&&
//                    (Float.parseFloat(recommendData.getData().getIns_plan().getMax_cur_ins_cov())>0)) {
//
//                max_Req_Cover_Purchase=(UtileKit.currToCharConversion(recommendData.getData().getIns_plan().
//                        getMax_cur_ins_cov()));
//            } else {
//                max_Req_Cover_Purchase = "0";
//            }
//
//
//
//            if (recommendData.getData().getIns_plan().getHlv()!= null) {
//                hlvValue=recommendData.getData().getIns_plan().getHlv();
//            } else {
//                hlvValue = "0";
//            }
//            try {
//                hlvValue = (UtileKit.currToCharConversion(hlvValue));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            showWebViewInsurance(recommendData);
//        }
//        else {
//            loaderView.setVisibility(GONE);
//            errorTextview.setVisibility(VISIBLE);
//        }
//
//    }
//    private void showWebViewInsurance(RecommendData recommendData) {
//        String htmlContents = "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"100px\", user-scalable=yes\" />" +
//                "<body>\n" +
//                "\n" +
//                "<table border=\"1\" width=\"device-width\"height = \"100px\"table bordercolor=\"#A9A9A9\" " +
//                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
//                "  <tr align = \"center\">\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Insurance</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Existing Cover</th> \n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Existing Premium</th>\n" +
//                "  </tr>\n" +
//                addingTableRowsInsurance(recommendData)+
//                "</table>\n" +
//
//
//                "</p>" +
//
//                "\n" +
//                "<table border=\"1\" width=\"device-width\"height = \"100px\"table bordercolor=\"#A9A9A9\" " +
//                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
//                "  <tr align = \"center\">\n" +
//                "    <th width = \"150px\">Title</th>\n" +
//                "    <th width = \"150px\">Suggested Cover</th> \n" +
//                "    <th width = \"150px\">Suggested Premium</th>\n" +
//                "  </tr>\n" +
//                "  <tr align = \"center\">\n" +
//                "    <td>Minimum Required Cover</td>\n" +
//                "    <td>" +rs+" "+ min_Req_Cover + "</td>\n" +
//                "    <td>" +rs+" "+ 0 + "</td>\n" +
//                "  </tr>\n" +
//                "  <tr align = \"center\">\n" +
//                "    <td>Minimum Required Cover to Purchase</td>\n" +
//                "    <td>" +rs+" "+ min_Req_Cover_Purchase + "</td>\n" +
//                "    <td>" +rs+" "+ 0 + "</td>\n" +
//                "  </tr>\n" +
//                "  <tr align = \"center\">\n" +
//                "    <td>Maximum Required Cover</td>\n" +
//                "    <td>" +rs+" "+ max_Req_Cover+ "</td>\n" +
//                "    <td>" +rs+" "+ 0 + "</td>\n" +
//                "  </tr>\n" +
//                "  <tr align = \"center\">\n" +
//                "    <td>Maximum Required Cover to Purchase</td>\n" +
//                "    <td>" +rs+" "+ max_Req_Cover_Purchase+ "</td>\n" +
//                "    <td>" +rs+" "+ 0 + "</td>\n" +
//                "  </tr>\n" +
//                "</table>\n" +
//
//                "\n <br><br> <p align = \"center\"> Your estimated human life value <font color=\"#7a0098;\"><b>"
//                +rs+" "+ hlvValue + "</b></font></p><br>" +
//
//                "</body>\n" +
//                "</html>\n";
//        webViewIns_plan.loadData(htmlContents, "text/html", "UTF-8");
//    }
//    private String addingTableRowsInsurance(RecommendData recommendData) {
//        String rowsadd ="";
//        int length = recommendData.getData().getIns_plan().getPlan_res().size();
//        if (null!=recommendData.getData().getIns_plan()) {
//            for (int i = 0; i < length; i++) {
//                String coverage="",annual_prem="",ins_prod_type="";
//                if(recommendData.getData().getIns_plan().getPlan_res().get(i).getCoverage()!= null&&
//                        Float.parseFloat(recommendData.getData().getIns_plan().getPlan_res().get(i).getCoverage())>0){
//                    coverage=(UtileKit.currToCharConversion(recommendData.getData().
//                            getIns_plan().getPlan_res().get(i).getCoverage()));
//                }else{
//                    coverage= "0";
//                }
//                if(recommendData.getData().getIns_plan().getPlan_res().get(i).getAnnual_prem()!= null&&
//                        Float.parseFloat(recommendData.getData().getIns_plan().getPlan_res().get(i).getAnnual_prem())>0){
//                    annual_prem=(UtileKit.currToCharConversion(recommendData.getData().
//                            getIns_plan().getPlan_res().get(i).getAnnual_prem()));
//                }else{
//                    annual_prem= "0";
//                }
//
//                if(recommendData.getData().getIns_plan().getPlan_res().get(i).getIns_type()!= null){
//                    ins_prod_type=recommendData.getData().getIns_plan().getPlan_res().get(i).getIns_type();
//                }else{
//                    ins_prod_type= "0";
//                }
//                rowsadd =rowsadd  + "  <tr align = \"center\">\n" +
//                        "    <td>" +ins_prod_type+"</td>\n" +
//                        "    <td>"  +rs+" "+coverage+"</td>\n" +
//                        "    <td>"  +rs+" "+annual_prem+"</td>\n" +
//                        "  </tr>\n" ;
//            }
//        }
//        return rowsadd;
//    }
//    //New Insurance plan end
//
//    //New HealthInsurance Start
//    private void applyHealthInsurance(@NonNull View v, @NonNull ArrayList<Health_ins_plan> health_ins_plan) {
//        webView_HealthInsurance = (WebView) v.findViewById(R.id.webview_health);
//        final View loaderView = v.findViewById(R.id.inner_progress_health);
//        loaderView.setVisibility(GONE);
//        webView_HealthInsurance.setVisibility(View.GONE);
//     /*   TextView empty_value_health=(TextView) v.findViewById(R.id.empty_value_health);
//        WebSettings settings = webView_HealthInsurance.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(false);
//        settings.setDomStorageEnabled(true);
//        webView_HealthInsurance.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                loaderView.setVisibility(VISIBLE);
//            }
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                loaderView.setVisibility(GONE);
//            }
//        });*/
//        showWebViewHealthInsurance(recommendData);
//
//
//    }
//    private void showWebViewHealthInsurance(RecommendData recommendData) {
//       /* String htmlContents = "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"100px\", user-scalable=yes\" />" +
//                "<body>\n" +
//                "\n" +
//
//                "<table border=\"1\" width=\"device-width\"height = \"100px\"table bordercolor=\"#A9A9A9\" " +
//                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
//                "  <tr align = \"center\">\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Insurance Product Type</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Anual Premium</th> \n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Cover Availed</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Cover Required</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Cover Recommendation</th>\n" +
//                "  </tr>\n" +
//                addingTableRowsHealthInsurance(recommendData)+
//                "</table>\n" +
//
//                "</body>\n" +
//                "</html>\n";
//        webView_HealthInsurance.loadData(htmlContents, "text/html", "UTF-8");*/
//
//        loadHealthInsuranceData(recommendData);
//    }
//
//    private void loadHealthInsuranceData(RecommendData recommendData) {
//        List<RowHeader> rowHeaders = getRowHeaderListHealth(recommendData);
//        List<List<Cell>> cellList = getCellListForSortingTestHealthInsurance(recommendData);
//        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameListHealth);
//        mTableViewAdapter.setAllItems(columnHeaders, rowHeaders, cellList);
//    }
//
//    private List<RowHeader> getRowHeaderListHealth(RecommendData recommendData) {
//        List<RowHeader> list = new ArrayList<>();
//        int n=recommendData.getData().getHealth_ins_plan().size();
//        for (int i = 0; i <n ; i++) {
//            RowHeader header = new RowHeader("row " + i,""+(i+1));
//            list.add(header);
//        }
//        return list;
//    }
//
//    private List<List<Cell>> getCellListForSortingTestHealthInsurance(RecommendData recommendData) {
//        List<List<Cell>> list = new ArrayList<>();
//        String rowsadd ="";
//        int length = recommendData.getData().getHealth_ins_plan().size();
//
//        if (null!=recommendData.getData().getHealth_ins_plan()) {
//            for (int i = 0; i < length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//
//                String ins_prod_type="",annual_prem="",cover_availed="",cover_required="",cover_recommended="";
//                if(recommendData.getData().getHealth_ins_plan().get(i).getIns_prod_type()!= null){
//                    ins_prod_type=recommendData.getData().getHealth_ins_plan().get(i).getIns_prod_type();
//                }else{
//                    ins_prod_type= "0";
//                }
//
//                if(recommendData.getData().getHealth_ins_plan().get(i).getCover_required()!= null){
//                    cover_required="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
//                            getHealth_ins_plan().get(i).getCover_required()));
//                }else{
//                    cover_required="₹ "+ "0";
//                }
//
//                if(recommendData.getData().getHealth_ins_plan().get(i).getCover_availed()!= null){
//                    cover_availed="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
//                            getHealth_ins_plan().get(i).getCover_availed()));
//                }else{
//                    cover_availed= "₹ "+"0";
//                }
//                if(recommendData.getData().getHealth_ins_plan().get(i).getCover_recommended()!= null){
//                    cover_recommended="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
//                            getHealth_ins_plan().get(i).getCover_recommended()));
//                }else {
//                    cover_recommended = "0";
//                }
//                if(recommendData.getData().getHealth_ins_plan().get(i).getAnnual_prem()!= null){
//                    annual_prem="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
//                            getHealth_ins_plan().get(i).getAnnual_prem()));
//                }else{
//                    annual_prem= "₹ "+"0";
//                }
//                cellList.add(new Cell(i+"00", ins_prod_type));
//                cellList.add(new Cell(i+"01", cover_required));
//                cellList.add(new Cell(i+"02", cover_availed));
//                cellList.add(new Cell(i+"03", cover_recommended));
//                cellList.add(new Cell(i+"04", annual_prem));
//
//
//                list.add(cellList);
//            }
//        }
//        return list;
//    }
//
//    //New HealthInsurance End
//
//
//    //New AutoInsurance Start
//    private void applyAutoInsurance(@NonNull View v, @NonNull ArrayList<Motor_ins_plan> motor_ins_plen) {
//        webView_AutoInsurance = (WebView) v.findViewById(R.id.webview_auto);
//        final View loaderView = v.findViewById(R.id.inner_progress_auto);
//        webView_AutoInsurance.setVisibility(GONE);
//        loaderView.setVisibility(GONE);
//       /* WebSettings settings = webView_AutoInsurance.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(false);
//        settings.setDomStorageEnabled(true);
//        webView_AutoInsurance.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                loaderView.setVisibility(VISIBLE);
//            }
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                loaderView.setVisibility(GONE);
//            }
//        });*/
//        showWebViewAutoInsurance(recommendData);
//    }
//    private void showWebViewAutoInsurance(RecommendData recommendData) {
//       /* String htmlContents = "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"100px\", user-scalable=yes\" />" +
//                "<body>\n" +
//                "\n" +
//
//                "<table border=\"1\" width=\"device-width\"height = \"100px\"table bordercolor=\"#A9A9A9\" " +
//                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
//                "  <tr align = \"center\">\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Insurance Product Type</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Anual Premium</th> \n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Cover Availed</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Cover Required</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Cover Recommendation</th>\n" +
//                "  </tr>\n" +
//                addingTableRowsAutoInsurance(recommendData)+
//                "</table>\n" +
//
//                "</body>\n" +
//                "</html>\n";
//        webView_AutoInsurance.loadData(htmlContents, "text/html", "UTF-8");*/
//
//        loadAutoInsuranceData(recommendData);
//    }
//
//    private void loadAutoInsuranceData(RecommendData recommendData) {
//        List<RowHeader> rowHeaders = getRowHeaderListAuto(recommendData);
//        List<List<Cell>> cellList = getCellListForSortingTestAutoInsurance(recommendData);
//        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameListHealth);
//        mTableViewAdapter.setAllItems(columnHeaders, rowHeaders, cellList);
//    }
//
//    private List<RowHeader> getRowHeaderListAuto(RecommendData recommendData) {
//        List<RowHeader> list = new ArrayList<>();
//        int n=recommendData.getData().getMotor_ins_plan().size();
//        for (int i = 0; i <n ; i++) {
//            RowHeader header = new RowHeader("row " + i,""+(i+1));
//            list.add(header);
//        }
//        return list;
//    }
//
//    private List<List<Cell>> getCellListForSortingTestAutoInsurance(RecommendData recommendData) {
//
//        List<List<Cell>> list = new ArrayList<>();
//        String rowsadd ="";
//        int length = recommendData.getData().getMotor_ins_plan().size();
//
//        if (null!=recommendData.getData().getMotor_ins_plan()) {
//            for (int i = 0; i < length; i++) {
//
//                List<Cell> cellList = new ArrayList<>();
//
//                String ins_prod_type="",annual_prem="",cover_availed="",cover_required="",cover_recommended="";
//                if(recommendData.getData().getMotor_ins_plan().get(i).getIns_prod_type()!= null){
//                    ins_prod_type=recommendData.getData().getMotor_ins_plan().get(i).getIns_prod_type();
//                }else{
//                    ins_prod_type= "0";
//                }
//                if(recommendData.getData().getMotor_ins_plan().get(i).getCover_required()!= null){
//                    cover_required="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
//                            getMotor_ins_plan().get(i).getCover_required()));
//                }else{
//                    cover_required="₹ "+ "0";
//                }
//                if(recommendData.getData().getMotor_ins_plan().get(i).getCover_availed()!= null){
//                    cover_availed=(UtileKit.currToCharConversion(recommendData.getData().
//                            getMotor_ins_plan().get(i).getCover_availed()));
//                }else{
//                    cover_availed= "₹ "+"0";
//                }
//
//                if(recommendData.getData().getMotor_ins_plan().get(i).getCover_recommended()!= null){
//                    cover_recommended="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
//                            getMotor_ins_plan().get(i).getCover_recommended()));
//                }else {
//                    cover_recommended = "₹ "+"0";
//                }
//                if(recommendData.getData().getMotor_ins_plan().get(i).getAnnual_prem()!= null){
//                    annual_prem="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
//                            getMotor_ins_plan().get(i).getAnnual_prem()));
//                }else{
//                    annual_prem="₹ "+ "0";
//                }
//                cellList.add(new Cell(i+"00", ins_prod_type));
//                cellList.add(new Cell(i+"01", cover_required));
//                cellList.add(new Cell(i+"02", cover_availed));
//                cellList.add(new Cell(i+"03", cover_recommended));
//                cellList.add(new Cell(i+"03", annual_prem));
//
//
//                list.add(cellList);
//            }
//        }
//        return list;
//    }
//
//    //New AutoInsurance End
//
//
//    //New PropertyInsurance Start
//    private void applyPropertyInsurance(@NonNull View v, @NonNull ArrayList<Prop_ins_plan> prop_ins_plen) {
//        webView_PropertyInsurance = (WebView) v.findViewById(R.id.webview_property);
//        final View loaderView = v.findViewById(R.id.inner_progress_property);
//        webView_PropertyInsurance.setVisibility(GONE);
//        loaderView.setVisibility(GONE);
//
//    /*    WebSettings settings = webView_PropertyInsurance.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(false);
//        settings.setDomStorageEnabled(true);
//        webView_PropertyInsurance.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                loaderView.setVisibility(VISIBLE);
//            }
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                loaderView.setVisibility(GONE);
//            }
//        });*/
//        showWebViewPropertyInsurance(recommendData);
//    }
//    private void showWebViewPropertyInsurance(RecommendData recommendData) {
//       /* String htmlContents = "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"100px\", user-scalable=yes\" />" +
//                "<body>\n" +
//                "\n" +
//
//                "<table border=\"1\" width=\"device-width\"height = \"100px\"table bordercolor=\"#A9A9A9\" " +
//                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
//                "  <tr align = \"center\">\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Insurance Product Type</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Anual Premium</th> \n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Cover Availed</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Cover Required</th>\n" +
//                "    <th style=\"padding-left:10px;padding-right:10px;\">Cover Recommendation</th>\n" +
//                "  </tr>\n" +
//                addingTableRowsPropertyInsurance(recommendData)+
//                "</table>\n" +
//
//                "</body>\n" +
//                "</html>\n";
//        webView_PropertyInsurance.loadData(htmlContents, "text/html", "UTF-8");*/
//
//        loadPropertyInsuranceData(recommendData);
//    }
//
//    private void loadPropertyInsuranceData(RecommendData recommendData) {
//
//        List<RowHeader> rowHeaders = getRowHeaderListProperty(recommendData);
//        List<List<Cell>> cellList = getCellListForSortingTestPropertyInsurance(recommendData);
//        List<ColumnHeader> columnHeaders = getColumnHeaderListCash(mColoumNameListHealth);
//        mTableViewAdapter.setAllItems(columnHeaders, rowHeaders, cellList);
//
//    }
//
//    private List<List<Cell>> getCellListForSortingTestPropertyInsurance(RecommendData recommendData) {
//
//        List<List<Cell>> list = new ArrayList<>();
//        String rowsadd ="";
//        int length = recommendData.getData().getProp_ins_plan().size();
//
//        if (null!=recommendData.getData().getProp_ins_plan()) {
//            for (int i = 0; i < length; i++) {
//
//                List<Cell> cellList = new ArrayList<>();
//
//                String ins_prod_type="",annual_prem="",cover_availed="",cover_required="",cover_recommended="";
//                if(recommendData.getData().getProp_ins_plan().get(i).getIns_prod_type()!= null){
//                    ins_prod_type=recommendData.getData().getProp_ins_plan().get(i).getIns_prod_type();
//                }else{
//                    ins_prod_type= "0";
//                }
//
//                if(recommendData.getData().getProp_ins_plan().get(i).getCover_required()!= null){
//                    cover_required="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
//                            getProp_ins_plan().get(i).getCover_required()));
//                }else{
//                    cover_required="₹ "+ "0";
//                }
//
//                if(recommendData.getData().getProp_ins_plan().get(i).getCover_availed()!= null){
//                    cover_availed="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
//                            getProp_ins_plan().get(i).getCover_availed()));
//                }else{
//                    cover_availed= "₹ "+"0";
//                }
//                if(recommendData.getData().getProp_ins_plan().get(i).getCover_recommended()!= null){
//                    cover_recommended="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
//                            getProp_ins_plan().get(i).getCover_recommended()));
//                }else {
//                    cover_recommended ="₹ "+ "0";
//                }
//                if(recommendData.getData().getProp_ins_plan().get(i).getAnnual_prem()!= null){
//                    annual_prem="₹ "+(UtileKit.currToCharConversion(recommendData.getData().
//                            getProp_ins_plan().get(i).getAnnual_prem()));
//                }else{
//                    annual_prem="₹ "+ "0";
//                }
//                cellList.add(new Cell(i+"00", ins_prod_type));
//                cellList.add(new Cell(i+"01", cover_required));
//                cellList.add(new Cell(i+"02", cover_availed));
//                cellList.add(new Cell(i+"03", cover_recommended));
//                cellList.add(new Cell(i+"03", annual_prem));
//
//
//                list.add(cellList);
//            }
//        }
//        return list;
//
//    }
//
//    private List<RowHeader> getRowHeaderListProperty(RecommendData recommendData) {
//        List<RowHeader> list = new ArrayList<>();
//        int n=recommendData.getData().getProp_ins_plan().size();
//        for (int i = 0; i <n ; i++) {
//            RowHeader header = new RowHeader("row " + i,""+(i+1));
//            list.add(header);
//        }
//        return list;
//    }
//
//    //New PropertyInsurance End
//
//    //New EmergencyInsurance Start
//    private void applyEmergencyInformation(@NonNull View v, @NonNull Emg_fund emgfund) {
//        TextView noOfMonthsView, amountRequiredView, amountAccumulatedView, differenceView, statusView;
//        noOfMonthsView = (TextView) v.findViewById(R.id.answer_number_of_months);
//        amountRequiredView = (TextView) v.findViewById(R.id.answer_amount_required);
//        amountAccumulatedView = (TextView) v.findViewById(R.id.answer_amount_accumulated);
//        differenceView = (TextView) v.findViewById(R.id.answer_amount_difference);
//        statusView = (TextView) v.findViewById(R.id.answer_footer_view);
//
//        noOfMonthsView.setText(TextUtils.isEmpty(emgfund.getNo_of_months()) ? "N/A" : emgfund.getNo_of_months());
//        amountRequiredView.setText("₹ "+(UtileKit.formatedNumber(Float.valueOf(TextUtils.isEmpty(emgfund.getAmt_req()) ? "N/A" : emgfund.getAmt_req()))));
//        amountAccumulatedView.setText("₹ "+(UtileKit.formatedNumber(Float.valueOf(TextUtils.isEmpty(emgfund.getAmt_accum()) ? "N/A" : emgfund.getAmt_accum()))));
//        differenceView.setText("₹ "+(UtileKit.formatedNumber(Float.valueOf(TextUtils.isEmpty(emgfund.getDiff()) ? "N/A" : emgfund.getDiff()))));
//        statusView.setText(TextUtils.isEmpty(emgfund.getStatus()) ? "N/A" : emgfund.getStatus());
//    }
//    //New EmergencyInsurance end
//
//    //New Networh Start
//    private void applyNetworthDetails(@NonNull View v, @NonNull Assets assets, @NonNull Liab liab) {
//        webViewnetworth = (WebView) v.findViewById(R.id.webview_networth);
//        final View loaderView = v.findViewById(R.id.inner_progress_netwoth);
//        WebSettings settings = webViewnetworth.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(false);
//        settings.setDomStorageEnabled(true);
//        webViewnetworth.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                loaderView.setVisibility(VISIBLE);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                loaderView.setVisibility(GONE);
//            }
//        });
//
//        String asset_value = (UtileKit.longvalueabsolute(Float.parseFloat(networthmodel.getData().getOver_all_asset_val())));
//        String asset_return = (UtileKit.rounddecimalNumber(networthmodel.getData().getAssets().getOver_all_weigh_avg()));
//
//        String liability_value = (UtileKit.longvalueabsolute(Float.parseFloat(networthmodel.getData().getOver_all_liab_val())));
//        String liability_return = (UtileKit.rounddecimalNumber(networthmodel.getData().getLiab().getOver_loan_amt_weigh_avg()));
//
//        networth_value = (networthmodel.getData().getNetworth_val());
//        String empty = "";
//
//        String floatconver_networth_value = "";
//        try {
//            floatconver_networth_value = (UtileKit.longvalueabsolute(Float.parseFloat(networth_value)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String htmlContents = "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" />" +
//                "<body>\n" +
//                "\n" +
////                "<table border=\"1\" width=\"device-width\" height = \"150px\" cellpadding=\"0\" " +
////                "cellspacing=\"0\" style=\"border-collapse:collapse;\" >\n" +
//
//                "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
//                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
//
//
//                "  <tr align = \"center\">\n" +
//                "    <th width = \"150px\"> </th>\n" +
//                "    <th width = \"150px\">Value</th> \n" +
//                "    <th width = \"150px\">Return</th>\n" +
//                "  </tr>\n" +
//                "  <tr align = \"center\">\n" +
//                "    <td>Asset</td>\n" +
//                "    <td>" +rs+" "+ asset_value + "</td>\n" +
//                "    <td>" + asset_return + "% " + " p.a. " + "</td>\n" +
//                "  </tr>\n" +
//                "  <tr align = \"center\">\n" +
//                "    <td>Liability</td>\n" +
//                "    <td>" +rs+" "+ liability_value + "</td>\n" +
//                "    <td>" + liability_return + "% " + " p.a. " + "</td>\n" +
//                "  </tr>\n" +
//                "  <tr align = \"center\">\n" +
//                "    <td>Networth</td>\n" +
//                "    <td>" +rs+" "+ floatconver_networth_value + "</td>\n" +
//                "    <td>" + empty + "</td>\n" +
//                "  </tr>\n" +
//                "</table>\n" +
//
//                " Your liability payout is at a lower rate <font color=\"#6699FF\"><b>"
//
//
//                + liability_return + "</b></font>" + "<font color=\"#6699FF\">% </font>" + " p.a. " + "\n" +
//                " than your asset growth rate, <font color=\"#6699FF\"><b>"
//                + asset_return + "</b></font>" + "<font color=\"#6699FF\">% </font>" + " p.a. " + "</p>" +
//
//
//               // "\n<br/>Your Netwoth is <font color=\"#6699FF\"><b>"
//              //  + "eroding" + "</b></font>" + " at a rate of" + "</br>" +
//
//              //  "\nAverage Inflance Risk is " + "</br>" +
//              //  "\nMarginal Tax Risk is " + "</br>" +
//              //  "\nYour real rate of growth is " +
//
//
//                "</body>\n" +
//                "</html>\n";
//        webViewnetworth.loadData(htmlContents, "text/html", "UTF-8");
//    }
//    //New Networh Start
//
//
//    private String getRoundedMoneyValue(String unFormattedVaue) {
//        String formattedCurrency = null;
//        try {
//            formattedCurrency = UtileKit.currToCharConversion(unFormattedVaue);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return formattedCurrency;
//    }
//    /**
//     * To initialize popup window
//     */
//    public PopupWindow initPopupWindow(View mPopUpRootView) {
//        final PopupWindow popUpWindow = new PopupWindow(mPopUpRootView, ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        popUpWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        popUpWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        popUpWindow.setTouchable(true);
//        popUpWindow.setOutsideTouchable(true);
//        popUpWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        popUpWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                    popUpWindow.dismiss();
//                }
//                return false;
//            }
//        });
//        return popUpWindow;
//    }
//
//    public boolean isPopUpShowing() {
//        return isPopUpShowing;
//    }
//
//    public void setPopUpShowing(boolean popUpShowing) {
//        isPopUpShowing = popUpShowing;
//    }
//
//
//
//    enum ViewType {
//        INSURANCE_PLAN, EMERGENCY_FUND_PLAN,NETWORTH_PLAN,GOALS,
//        HEALTH_INSURANCE,AUTO_INSURANCE,PROPERTY_INSURANCE,CASH_MANAGEMENT,SAVING_AND_INVESTMENT,TAXATION_PLAN
//    }
//}

//package com.purplepath.purplepath.cashflowmanagmentchart;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebView;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.cashflowmanagmentchart.model.GetcashflowinoutflowModel;
//import com.purplepath.purplepath.cashflowmanagmentchart.model.IncomeExpenseCashFlowModel;
//import com.purplepath.purplepath.fragments.BaseFragment;
//import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
//import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
//
///**
// * Created by pravinr on 9/11/17.
// */
//
//public class CashflowDetails extends BaseFragment implements View.OnClickListener{
//
//
//    private  WebView webViewInOutCashflow,webViewIncomeExpense,
//             webViewDefli_surplus,webViewTax;
//    OnActivityBackPressedListener backPressedListener;
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//    private Context mContext;
//    private GetcashflowinoutflowModel getcashflowinoutflowModel;
//    private IncomeExpenseCashFlowModel incomeExpenseCashFlowModel;
//    private TaxCashFlowModel taxAnalysisCashFlowChartModel;
//    private CheckBox CheckBox_suplus_deficit,CheckBox_Overallcashflow,
//            CheckBox_taxflow,CheckBox_IncomeExpenseCashFlow;
//    private String deflictminus="",deflictplus="";
//    private TextView empty_value;
//    private String rs="&#x20B9";
//    private RelativeLayout linearLayout_checkbox;
//
//    @Override
//    public void onAttach(Context context) {
//        backPressedListener= (OnActivityBackPressedListener) context;
//        super.onAttach(context);
//        mContext = context;
//    }
//
//    public static CashflowDetails newInstance(GetcashflowinoutflowModel getcashflowinoutflowModel,
//                                              IncomeExpenseCashFlowModel incomeExpenseCashFlowModel,
//                                              TaxCashFlowModel taxAnalysisCashFlowChartModel) {
//        CashflowDetails cashflowDetails = new CashflowDetails();
//        Bundle args = new Bundle();
//        if (getcashflowinoutflowModel != null) {
//            args.putSerializable("cashflowinoutflowModel", getcashflowinoutflowModel);
//        }
//        if (incomeExpenseCashFlowModel != null) {
//            args.putSerializable("incomeExpenseCashFlowModel", incomeExpenseCashFlowModel);
//        }
//
//        if (taxAnalysisCashFlowChartModel != null) {
//            args.putSerializable("taxAnalysisCashFlowChartModel", taxAnalysisCashFlowChartModel);
//        }
//        cashflowDetails.setArguments(args);
//        return cashflowDetails;
//    }
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.fragment_cashflow_details, container, false);
//        setHasOptionsMenu(true);
//        backPressedListener.setActionBarTitle("My Cash Flow");
//        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
//        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
//        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
//
//        linearLayout_checkbox= view.findViewById(R.id.linearLayout_checkbox);
//
//        webViewDefli_surplus= view.findViewById(R.id.webViewDefli_surplus);
//        webViewInOutCashflow= view.findViewById(R.id.webViewInOutCashflow);
//        webViewTax= view.findViewById(R.id.webViewTax);
//        webViewIncomeExpense= view.findViewById(R.id.webViewIncomeExpense);
//        empty_value= view.findViewById(R.id.empty_value);
//
//        CheckBox_suplus_deficit= view.findViewById(R.id.CheckBox_suplus_deficit);
//        CheckBox_Overallcashflow= view.findViewById(R.id.CheckBox_Overallcashflow);
//        CheckBox_taxflow= view.findViewById(R.id.CheckBox_taxflow);
//        CheckBox_IncomeExpenseCashFlow= view.findViewById(R.id.CheckBox_IncomeExpenseCashFlow);
//        CheckBox_suplus_deficit.setOnClickListener(this);
//        CheckBox_Overallcashflow.setOnClickListener(this);
//        CheckBox_taxflow.setOnClickListener(this);
//        CheckBox_IncomeExpenseCashFlow.setOnClickListener(this);
//
//        mRightRelativeLayout.setVisibility(View.GONE);
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//
//
//        CheckBox_suplus_deficit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    webViewDefli_surplus.setVisibility(View.VISIBLE);
//                    webViewIncomeExpense.setVisibility(View.GONE);
//                    webViewInOutCashflow.setVisibility(View.GONE);
//                    webViewTax.setVisibility(View.GONE);
//                    CheckBox_taxflow.setChecked(false);
//                    CheckBox_Overallcashflow.setChecked(false);
//                    CheckBox_IncomeExpenseCashFlow.setChecked(false);
//                }}
//        });
//        CheckBox_Overallcashflow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    webViewDefli_surplus.setVisibility(View.GONE);
//                    webViewIncomeExpense.setVisibility(View.GONE);
//                    webViewInOutCashflow.setVisibility(View.VISIBLE);
//                    webViewTax.setVisibility(View.GONE);
//                    CheckBox_suplus_deficit.setChecked(false);
//                    CheckBox_taxflow.setChecked(false);
//                    CheckBox_IncomeExpenseCashFlow.setChecked(false);
//                }}
//        });
//        CheckBox_taxflow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    webViewDefli_surplus.setVisibility(View.GONE);
//                    webViewIncomeExpense.setVisibility(View.GONE);
//                    webViewInOutCashflow.setVisibility(View.GONE);
//                    webViewTax.setVisibility(View.VISIBLE);
//                    CheckBox_suplus_deficit.setChecked(false);
//                    CheckBox_Overallcashflow.setChecked(false);
//                    CheckBox_IncomeExpenseCashFlow.setChecked(false);
//                }}
//        });
//        CheckBox_IncomeExpenseCashFlow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    webViewDefli_surplus.setVisibility(View.GONE);
//                    webViewIncomeExpense.setVisibility(View.VISIBLE);
//                    webViewInOutCashflow.setVisibility(View.GONE);
//                    webViewTax.setVisibility(View.GONE);
//                    CheckBox_suplus_deficit.setChecked(false);
//                    CheckBox_Overallcashflow.setChecked(false);
//                    CheckBox_taxflow.setChecked(false);
//                }}
//        });
//
//
//
//        // Cash InOut cashflow
//        Bundle args = getArguments();
//        if (args != null) {
//            if (args.containsKey("cashflowinoutflowModel")) {
//                getcashflowinoutflowModel = (GetcashflowinoutflowModel) args.getSerializable("cashflowinoutflowModel");
//                Log.i("spcheck", "output cashflowinoutflowModel" + getcashflowinoutflowModel);
//                if (getcashflowinoutflowModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
//                    try{
//                    // int lengths=getcashflowinoutflowModel.getData().getFin_cash_flow().size();
//                    //if(lengths!=0){
//                        if(UtileKit.validateObjectValues(getcashflowinoutflowModel.getData().getFin_cash_flow())){
//                        showCashflowInOutFlowDetails(getcashflowinoutflowModel);
//                        showDeflicitSurplusDetails(getcashflowinoutflowModel);
//                        linearLayout_checkbox.setVisibility(View.VISIBLE);
//                    }
//                    else {
//                        linearLayout_checkbox.setVisibility(View.GONE);
//                        empty_value.setVisibility(View.VISIBLE);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//
//                }else {
//                    linearLayout_checkbox.setVisibility(View.GONE);
//                    empty_value.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//
//        if (args != null) {
//            if (args.containsKey("incomeExpenseCashFlowModel")) {
//                incomeExpenseCashFlowModel = (IncomeExpenseCashFlowModel) args.getSerializable("incomeExpenseCashFlowModel");
//                Log.i("spcheck", "output incomeExpenseCashFlowModel " + incomeExpenseCashFlowModel);
//                if ( incomeExpenseCashFlowModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
//                    try{
//                    //  int length=incomeExpenseCashFlowModel.getData().getCash_mang_det().size();
//                    // if(length!=0){
//                    if(UtileKit.validateObjectValues(incomeExpenseCashFlowModel.getData().getCash_mang_det())&&
//                            incomeExpenseCashFlowModel.getData().getCash_mang_det().size()!=0){
//                        showCashflowIncomeExpenseDetails(incomeExpenseCashFlowModel);
//                        linearLayout_checkbox.setVisibility(View.VISIBLE);
//                    }
//                    else {
//                        linearLayout_checkbox.setVisibility(View.GONE);
//                        empty_value.setVisibility(View.VISIBLE);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                } else {
//                }
//            }
//        }
//
//        if (args != null) {
//            if (args.containsKey("taxAnalysisCashFlowChartModel")) {
//                taxAnalysisCashFlowChartModel = (TaxCashFlowModel) args.getSerializable("taxAnalysisCashFlowChartModel");
//                Log.i("spcheck", "output taxAnalysisCashFlowChartModel " + taxAnalysisCashFlowChartModel);
//                if (taxAnalysisCashFlowChartModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
//                    try{
//                    //int length=taxAnalysisCashFlowChartModel.getData().getUser_tax().size();
//                    // if(length!=0){
//                    if(UtileKit.validateObjectValues(taxAnalysisCashFlowChartModel.getData().getUser_tax())){
//                        showTaxDetails(taxAnalysisCashFlowChartModel);
//                        linearLayout_checkbox.setVisibility(View.VISIBLE);
//                    }
//                    else {
//                        linearLayout_checkbox.setVisibility(View.GONE);
//                        empty_value.setVisibility(View.VISIBLE);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                } else {
//                }
//            }
//        }
//
//        try {
//            if(UtileKit.validateObjectValues(incomeExpenseCashFlowModel.getData().getCash_mang_det() )&&
//                    incomeExpenseCashFlowModel.getData().getCash_mang_det().size()!=0){
//                CheckBox_taxflow.setChecked(false);
//                CheckBox_Overallcashflow.setChecked(false);
//                CheckBox_IncomeExpenseCashFlow.setChecked(false);
//                CheckBox_suplus_deficit.setChecked(true);
//                showDeflicitSurplusDetails(getcashflowinoutflowModel);
//                linearLayout_checkbox.setVisibility(View.VISIBLE);
//            } else {
//                linearLayout_checkbox.setVisibility(View.GONE);
//                empty_value.setVisibility(View.VISIBLE);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        return view;
//    }
//
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }
//
//    private void showTaxDetails(TaxCashFlowModel taxAnalysisCashFlowChartModel) {
//        String htmlContent = "<!DOCTYPE html>\n" +
//
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" >" +
//                "</head>\n" +
//                "<body>\n" +
//                "\n" +
//                "<table border=\"1\" width=\"device-width\" height = \"150px\" cellpadding=\"10px\" cellspacing=\"0\" style=\"border-collapse:collapse;\" >\n" +
//                "  <tr id=\"header-row\" align = \"center\">\n" +
//                "    <th width = \"150px\">Total Savings</th>\n" +
//                "    <th width = \"150px\">Total Tax Payable</th>\n" +
//                "    <th width = \"150px\">Actual Tax</th>\n" +
//                "    <th width = \"150px\">Cess</th>\n" +
//                "    <th width = \"150px\">Surage</th>\n" +
//                "    <th width = \"150px\">Tax Age</th>\n" +
//                "    <th width = \"150px\">Years Pass</th>\n" +
//                "    <th width = \"150px\">Years Remain</th>\n" +
//                "  </tr>\n" +
//                addTableTaxRows(taxAnalysisCashFlowChartModel)
//                +
//                "</table>\n" +
//                "</body>\n" +
//                "</html>\n";
//        webViewTax.loadData(htmlContent, "text/html", "UTF-8");
//    }
//    private String addTableTaxRows(TaxCashFlowModel taxAnalysisCashFlowChartModel) {
//        String row ="";
//        int rowSize = taxAnalysisCashFlowChartModel.getData().getUser_tax().size();
//        for (int i = 0; i < rowSize; i++) {
//            row =row  + "  <tr align = \"center\">\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(taxAnalysisCashFlowChartModel.getData().getUser_tax().
//                    get(i).getTotal_savings())))+"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(taxAnalysisCashFlowChartModel.getData().getUser_tax().
//                    get(i).getTotal_tax_payable()))) +"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(taxAnalysisCashFlowChartModel.getData().getUser_tax().
//                    get(i).getActual_tax())))+"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(taxAnalysisCashFlowChartModel.getData().getUser_tax().
//                    get(i).getCess())))+"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(taxAnalysisCashFlowChartModel.getData().getUser_tax().
//                    get(i).getSurage())))+"</td>\n" +
//                    "    <td>" +(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getTax_age()) +"</td>\n" +
//                    "    <td>" +(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getYears_pass())+"</td>\n" +
//                    "    <td>" +(taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getYears_remain())+"</td>\n" +
//                    "  </tr>\n";
//        }
//        return row;
//    }
//
//    private void showDeflicitSurplusDetails(GetcashflowinoutflowModel mGetcashflowinoutflowModel) {
//        String htmlContent = "<!DOCTYPE html>\n" +
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" >" +
//                "</head>\n" +
//                "<body>\n" +
//                "\n" +
//                "<table border=\"1\" width=\"device-width\" height = \"150px\" cellpadding=\"10px\" cellspacing=\"0\" style=\"border-collapse:collapse;\" >\n" +
//                "  <tr id=\"header-row\" align = \"center\">\n" +
//                "    <th width = \"150px\">Surplus</th>\n" +
//                "    <th width = \"150px\">Deficit</th>\n" +
//                "  </tr>\n" +
//                addDeflicitSurplusRows(mGetcashflowinoutflowModel)
//                +
//                "</table>\n" +
//                "</body>\n" +
//                "</html>\n";
//        webViewDefli_surplus.loadData(htmlContent, "text/html", "UTF-8");
//    }
//    private String addDeflicitSurplusRows(GetcashflowinoutflowModel mGetcashflowinoutflowModel) {
//        String row ="";
//        int rowSize = mGetcashflowinoutflowModel.getData().getFin_cash_flow().size();
//        for (int i = 0; i < rowSize; i++) {
//
//            if(mGetcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getCash_flow()!= null){
//                if(Float.parseFloat(mGetcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getCash_flow())<0) {
//                    deflictminus = (UtileKit.longvalueabsolute(Float.parseFloat(mGetcashflowinoutflowModel.getData().getFin_cash_flow().
//                            get(i).getCash_flow())));
//                }
//                else {
//                    deflictminus= "0";
//                }
//              if(Float.parseFloat(mGetcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getCash_flow())>0){
//                    deflictplus = (UtileKit.longvalueabsolute(Float.parseFloat(mGetcashflowinoutflowModel.getData().getFin_cash_flow().
//                            get(i).getCash_flow())));
//                }else {
//                  deflictplus="0";
//              }
//            }
//            row =row  + "  <tr align = \"center\">\n" +
//                    "    <td>" +rs+" "+deflictplus+"</td>\n" +
//                    "    <td>" +rs+" "+deflictminus+"</td>\n" +
//                    "  </tr>\n" ;
//        }
//        return row;
//    }
//
//    private void showCashflowIncomeExpenseDetails(IncomeExpenseCashFlowModel incomeExpenseCashFlowModel) {
//        String htmlContent = "<!DOCTYPE html>\n" +
//
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" >" +
//                "</head>\n" +
//                "<body>\n" +
//                "\n" +
//                "<table border=\"1\" width=\"device-width\" height = \"150px\" cellpadding=\"10px\" cellspacing=\"0\" style=\"border-collapse:collapse;\" >\n" +
//                "  <tr id=\"header-row\" align = \"center\">\n" +
//                "    <th width = \"150px\">Cash Age</th>\n" +
//                "    <th width = \"150px\">Income from salary</th>\n" +
//                "    <th width = \"150px\">Income from Property</th>\n" +
//                "    <th width = \"150px\">Income from Business</th>\n" +
//                "    <th width = \"150px\">Capital Gains</th>\n" +
//                "    <th width = \"150px\">Income from Others</th>\n" +
//                "    <th width = \"150px\">Expenses</th>\n" +
//                "    <th width = \"150px\">Oblications</th>\n" +
//                "    <th width = \"150px\">Contributions</th>\n" +
//                "    <th width = \"150px\">Commitiments</th>\n" +
//                "  </tr>\n" +
//                addTableIncomeExpenseRows(incomeExpenseCashFlowModel)
//                +
//                "</table>\n" +
//                "</body>\n" +
//                "</html>\n";
//        webViewIncomeExpense.loadData(htmlContent, "text/html", "UTF-8");
//    }
//    private String addTableIncomeExpenseRows(IncomeExpenseCashFlowModel incomeExpenseCashFlowModel) {
//        String row ="";
//        int rowSize = incomeExpenseCashFlowModel.getData().getCash_mang_det().size();
//        for (int i = 0; i < rowSize; i++) {
//            row =row  + "  <tr align = \"center\">\n" +
//                    "    <td>" +(incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getCash_age())+"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
//                                get(i).getSi_total()))) +"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
//                                get(i).getIp_total())))+"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
//                                get(i).getIb_total())))+"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
//                                get(i).getCg_total())))+"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
//                                get(i).getIfs_total()))) +"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
//                                get(i).getOver_all_exp())))+"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
//                                get(i).getOver_all_obli())))+"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
//                                get(i).getOver_all_contr()))) +"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
//                                get(i).getOver_all_contr())))+"</td>\n" +
//
//                    "  </tr>\n";
//        }
//        return row;
//    }
//
//    private void showCashflowInOutFlowDetails(GetcashflowinoutflowModel getcashflowinoutflowModel) {
//        String htmlContent = "<!DOCTYPE html>\n" +
//
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" >" +
//                "</head>\n" +
//                "<body>\n" +
//                "\n" +
//                "<table border=\"1\" width=\"device-width\" height = \"150px\" cellpadding=\"10px\" cellspacing=\"0\" style=\"border-collapse:collapse;\" >\n" +
//                "  <tr id=\"header-row\" align = \"center\">\n" +
//                "    <th width = \"150px\">Cash Age</th>\n" +
//                "    <th width = \"150px\">In Flow</th>\n" +
//                "    <th width = \"150px\">Out Flow</th> \n" +
//                "    <th width = \"150px\">Cash Flow</th>\n" +
//                "    <th width = \"150px\">Years Pass</th>\n" +
//                "    <th width = \"150px\">Years Remain</th>\n" +
//                "  </tr>\n" +
//                addTableInOutFlowRows(getcashflowinoutflowModel)
//                +
//                "</table>\n" +
//                "</body>\n" +
//                "</html>\n";
//        webViewInOutCashflow.loadData(htmlContent, "text/html", "UTF-8");
//    }
//    private String addTableInOutFlowRows(GetcashflowinoutflowModel getcashflowinoutflowModel) {
//        String row ="";
//        int rowSize = getcashflowinoutflowModel.getData().getFin_cash_flow().size();
//        for (int i = 0; i < rowSize; i++) {
//            row =row  + "  <tr align = \"center\">\n" +
//                    "    <td>" +(getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getCash_age())+"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(getcashflowinoutflowModel.getData().getFin_cash_flow().
//                                get(i).getIn_flow())))+"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(getcashflowinoutflowModel.getData().getFin_cash_flow().
//                                get(i).getOut_flow())))+"</td>\n" +
//                    "    <td>" +rs+" "+(UtileKit.longvalueabsolute(Float.parseFloat(getcashflowinoutflowModel.getData().getFin_cash_flow().
//                                get(i).getCash_flow())))+"</td>\n" +
//                    "    <td>" +(getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getYears_pass()) +"</td>\n" +
//                    "    <td>" +(getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getYears_remain()) +"</td>\n" +
//                    "  </tr>\n" ;
//        }
//        return row;
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        inflater.inflate(R.menu.menu_cash_chart,menu);
//        MenuItem items=menu.findItem(R.id.menu_chart);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.menu_chart:
//                try{
//                    addFragmenttoStack(new CashflowChartFragment());
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                return true;
//        }
//        return super.onOptionsItemSelected(menuItem);
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.relative_left_arrow:
//                backPressedListener.onActivityBackPressed();
//                break;
//            case R.id.relative_center_home:
//                Intent i=new Intent(getActivity(), HomePageActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);
//                break;
//        }
//
//    }
//
//}

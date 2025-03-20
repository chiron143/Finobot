package com.purplepath.purplepath.cashflowmanagmentchart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.cashflowmanagmentchart.model.GetcashflowinoutflowModel;
import com.purplepath.purplepath.cashflowmanagmentchart.model.IncomeExpenseCashFlowModel;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.adapter.CarBuyVsLeaseInnerTableAdapter;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
import com.purplepath.purplepath.taxproduct.model.Cell;
import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
import com.purplepath.purplepath.taxproduct.model.RowHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pravinr on 2/16/18.
 */

public class CashflowDetails1 extends BaseFragment implements View.OnClickListener {


    private RelativeLayout webViewInOutCashflow, webViewIncomeExpense, webViewDefli_surplus, webViewTax;

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private GetcashflowinoutflowModel getcashflowinoutflowModel;

    private IncomeExpenseCashFlowModel incomeExpenseCashFlowModel;

    private TaxCashFlowModel taxAnalysisCashFlowChartModel;

    private CheckBox CheckBox_suplus_deficit, CheckBox_Overallcashflow, CheckBox_taxflow, CheckBox_IncomeExpenseCashFlow;

    private TextView empty_value;

    private RelativeLayout linearLayout_checkbox;

    private AbstractTableAdapter mTableViewAdapter;

    private TableView mTableView;

    private ArrayList<String> dificitColoumName = new ArrayList<String>();

    private ArrayList<String> taxCashColoumName = new ArrayList<String>();

    private ArrayList<String> expenseColoumName = new ArrayList<String>();

    private ArrayList<String> overallCashColoumName = new ArrayList<String>();

    private List<RowHeader> mRowHeaderList;

    private List<ColumnHeader> mColumnHeaderList;

    private List<List<Cell>> mCellList;

    private int checkbox_position = 0;

    Bundle args = getArguments();


    @Override
    public void onAttach(Context context) {
        backPressedListener = (OnActivityBackPressedListener) context;
        super.onAttach(context);
    }

    public static CashflowDetails1 newInstance(GetcashflowinoutflowModel getcashflowinoutflowModel,
                                               IncomeExpenseCashFlowModel incomeExpenseCashFlowModel,
                                               TaxCashFlowModel taxAnalysisCashFlowChartModel, int checkbox_position) {
        CashflowDetails1 cashflowDetails = new CashflowDetails1();
        Bundle args = new Bundle();
        if (getcashflowinoutflowModel != null) {
            args.putSerializable("cashflowinoutflowModel", getcashflowinoutflowModel);
        }
        if (incomeExpenseCashFlowModel != null) {
            args.putSerializable("incomeExpenseCashFlowModel", incomeExpenseCashFlowModel);
        }

        if (taxAnalysisCashFlowChartModel != null) {
            args.putSerializable("taxAnalysisCashFlowChartModel", taxAnalysisCashFlowChartModel);
        }

        if (checkbox_position != 0) {
            args.putSerializable("checkbox_position", checkbox_position);
        }

        cashflowDetails.setArguments(args);
        return cashflowDetails;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        dificitColoumName.add("Surplus");
        dificitColoumName.add("Deficit");

        taxCashColoumName.add("Total Savings");
        taxCashColoumName.add("Total Tax Payable");
        taxCashColoumName.add("Actual Tax");
        taxCashColoumName.add("Cess");
        taxCashColoumName.add("Surage");
        taxCashColoumName.add("Tax Age");
        taxCashColoumName.add("Years Pass");
        taxCashColoumName.add("Years Remain");

        expenseColoumName.add("Cash Age");
        expenseColoumName.add("Income from salary");
        expenseColoumName.add("Income from Property");
        expenseColoumName.add("Income from Business");
        expenseColoumName.add("Capital Gains");
        expenseColoumName.add("Income from Others");
        expenseColoumName.add("Expenses");
        expenseColoumName.add("Oblications");
        expenseColoumName.add("Contributions");
        expenseColoumName.add("Commitiments");

        overallCashColoumName.add("Cash Age");
        overallCashColoumName.add("In Flow");
        overallCashColoumName.add("Out Flow");
        overallCashColoumName.add("Cash Flow");
        overallCashColoumName.add("Years Pass");
        overallCashColoumName.add("Years Remain");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cashflow_details_new, container, false);
        setHasOptionsMenu(true);
        backPressedListener.setActionBarTitle("My Cash Flow");



        System.out.println("CHK ....."+" 123456789");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        linearLayout_checkbox = view.findViewById(R.id.linearLayout_checkbox);

        webViewDefli_surplus = view.findViewById(R.id.webViewDefli_surplus);
        webViewInOutCashflow = view.findViewById(R.id.webViewInOutCashflow);
        webViewTax = view.findViewById(R.id.webViewTax);
        webViewIncomeExpense = view.findViewById(R.id.webViewIncomeExpense);
        empty_value = view.findViewById(R.id.empty_value);

        CheckBox_suplus_deficit = view.findViewById(R.id.CheckBox_suplus_deficit);
        CheckBox_Overallcashflow = view.findViewById(R.id.CheckBox_Overallcashflow);
        CheckBox_taxflow = view.findViewById(R.id.CheckBox_taxflow);
        CheckBox_IncomeExpenseCashFlow = view.findViewById(R.id.CheckBox_IncomeExpenseCashFlow);
        CheckBox_suplus_deficit.setOnClickListener(this);
        CheckBox_Overallcashflow.setOnClickListener(this);
        CheckBox_taxflow.setOnClickListener(this);
        CheckBox_IncomeExpenseCashFlow.setOnClickListener(this);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        CheckBox_suplus_deficit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    webViewDefli_surplus.setVisibility(View.VISIBLE);
                    webViewIncomeExpense.setVisibility(View.GONE);
                    webViewInOutCashflow.setVisibility(View.GONE);
                    webViewTax.setVisibility(View.GONE);
                    CheckBox_taxflow.setChecked(false);
                    CheckBox_Overallcashflow.setChecked(false);
                    CheckBox_IncomeExpenseCashFlow.setChecked(false);
                    checkbox_position = 1;
                }
            }
        });
        CheckBox_Overallcashflow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    webViewDefli_surplus.setVisibility(View.GONE);
                    webViewIncomeExpense.setVisibility(View.GONE);
                    webViewInOutCashflow.setVisibility(View.VISIBLE);
                    webViewTax.setVisibility(View.GONE);
                    CheckBox_suplus_deficit.setChecked(false);
                    CheckBox_taxflow.setChecked(false);
                    CheckBox_IncomeExpenseCashFlow.setChecked(false);
                    checkbox_position = 0;
                }
            }
        });
        CheckBox_taxflow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    webViewDefli_surplus.setVisibility(View.GONE);
                    webViewIncomeExpense.setVisibility(View.GONE);
                    webViewInOutCashflow.setVisibility(View.GONE);
                    webViewTax.setVisibility(View.VISIBLE);
                    CheckBox_suplus_deficit.setChecked(false);
                    CheckBox_Overallcashflow.setChecked(false);
                    CheckBox_IncomeExpenseCashFlow.setChecked(false);
                    checkbox_position = 3;
                }
            }
        });
        CheckBox_IncomeExpenseCashFlow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    webViewDefli_surplus.setVisibility(View.GONE);
                    webViewIncomeExpense.setVisibility(View.VISIBLE);
                    webViewInOutCashflow.setVisibility(View.GONE);
                    webViewTax.setVisibility(View.GONE);
                    CheckBox_suplus_deficit.setChecked(false);
                    CheckBox_Overallcashflow.setChecked(false);
                    CheckBox_taxflow.setChecked(false);
                    checkbox_position = 2;
                }
            }
        });


        // Cash InOut cashflow
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey("cashflowinoutflowModel")) {
                getcashflowinoutflowModel = (GetcashflowinoutflowModel) args.getSerializable("cashflowinoutflowModel");
                Log.i("spcheck", "output cashflowinoutflowModel" + getcashflowinoutflowModel);
                if (getcashflowinoutflowModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    try {
                        // int lengths=getcashflowinoutflowModel.getData().getFin_cash_flow().size();
                        //if(lengths!=0){
                        if (UtileKit.validateObjectValues(getcashflowinoutflowModel.getData().getFin_cash_flow())) {

                            //Overall-cash
                            initDataOverallCash();
                            mTableView = createTableView();
                            webViewInOutCashflow.addView(mTableView);
                            loadDataOverallCash();

                            //Deficit-surplus
                            initDataDefict();
                            mTableView = createTableView();
                            webViewDefli_surplus.addView(mTableView);
                            loadDataDefict();

                            linearLayout_checkbox.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout_checkbox.setVisibility(View.GONE);
                            empty_value.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    linearLayout_checkbox.setVisibility(View.GONE);
                    empty_value.setVisibility(View.VISIBLE);
                }
            }
        }

        if (args != null) {
            if (args.containsKey("incomeExpenseCashFlowModel")) {
                incomeExpenseCashFlowModel = (IncomeExpenseCashFlowModel) args.getSerializable("incomeExpenseCashFlowModel");
                Log.i("spcheck", "output incomeExpenseCashFlowModel " + incomeExpenseCashFlowModel);
                if (incomeExpenseCashFlowModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    try {
                        //  int length=incomeExpenseCashFlowModel.getData().getCash_mang_det().size();
                        // if(length!=0){
                        if (UtileKit.validateObjectValues(incomeExpenseCashFlowModel.getData().getCash_mang_det()) &&
                                incomeExpenseCashFlowModel.getData().getCash_mang_det().size() != 0) {

                            //Expense
                            initDataExpense();
                            mTableView = createTableView();
                            webViewIncomeExpense.addView(mTableView);
                            loadDataExpense();
                            linearLayout_checkbox.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout_checkbox.setVisibility(View.GONE);
                            empty_value.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }
        }

        if (args != null) {
            if (args.containsKey("taxAnalysisCashFlowChartModel")) {
                taxAnalysisCashFlowChartModel = (TaxCashFlowModel) args.getSerializable("taxAnalysisCashFlowChartModel");
                Log.i("spcheck", "output taxAnalysisCashFlowChartModel " + taxAnalysisCashFlowChartModel);
                if (taxAnalysisCashFlowChartModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    try {
                        //int length=taxAnalysisCashFlowChartModel.getData().getUser_tax().size();
                        // if(length!=0){
                        if (UtileKit.validateObjectValues(taxAnalysisCashFlowChartModel.getData().getUser_tax())) {

                            //Tax-cash-flow function
                            initDataTaxCash();
                            mTableView = createTableView();
                            webViewTax.addView(mTableView);
                            loadDataTaxCash();


                            linearLayout_checkbox.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout_checkbox.setVisibility(View.GONE);
                            empty_value.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                }
            }
        }

        //checkbox selection getting from cashflowchart fragment
        try {
            if (args != null) {
                if (args.containsKey("checkbox_position")) {
                    checkbox_position = args.getInt("checkbox_position");

                    Log.d("checkbox_positionss", "checkbox_positionss" + checkbox_position);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            if (UtileKit.validateObjectValues(incomeExpenseCashFlowModel.getData().getCash_mang_det()) &&
                    incomeExpenseCashFlowModel.getData().getCash_mang_det().size() != 0) {

                if (checkbox_position == 0) {
                    CheckBox_Overallcashflow.setChecked(true);


                    //Overall-cash
                    initDataOverallCash();
                    mTableView = createTableView();
                    webViewInOutCashflow.addView(mTableView);
                    loadDataOverallCash();


                } else if (checkbox_position == 1) {
                    CheckBox_suplus_deficit.setChecked(true);

                    //Deficit-surplus
                    initDataDefict();
                    mTableView = createTableView();
                    webViewDefli_surplus.addView(mTableView);
                    loadDataDefict();

                } else if (checkbox_position == 2) {
                    CheckBox_IncomeExpenseCashFlow.setChecked(true);

                    //Expense
                    initDataExpense();
                    mTableView = createTableView();
                    webViewIncomeExpense.addView(mTableView);
                    loadDataExpense();


                } else if (checkbox_position == 3) {
                    CheckBox_taxflow.setChecked(true);

                    //Tax-cash-flow function
                    initDataTaxCash();
                    mTableView = createTableView();
                    webViewTax.addView(mTableView);
                    loadDataTaxCash();

                }

//                CheckBox_taxflow.setChecked(false);
//                CheckBox_Overallcashflow.setChecked(false);
//                CheckBox_IncomeExpenseCashFlow.setChecked(false);
//                CheckBox_suplus_deficit.setChecked(true);


                linearLayout_checkbox.setVisibility(View.VISIBLE);
            } else {
                linearLayout_checkbox.setVisibility(View.GONE);
                empty_value.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }


    //Deficit-Surplus
    private void initDataDefict() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();

        int n = getcashflowinoutflowModel.getData().getFin_cash_flow().size();

        for (int i = 0; i < n; i++) {
            mCellList.add(new ArrayList<Cell>());
        }
    }

    private void loadDataDefict() {
        List<RowHeader> rowHeaders = getRowHeaderListDefict();
        List<List<Cell>> cellList = getCellListForSortingTestDefict();
        List<ColumnHeader> columnHeaders = getColumnHeaderListDefict();

        mRowHeaderList.addAll(rowHeaders);
        for (int i = 0; i < cellList.size(); i++) {
            mCellList.get(i).addAll(cellList.get(i));
        }
        mColumnHeaderList.addAll(columnHeaders);
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

    }

    private TableView createTableView() {
        TableView tableView = new TableView(getContext());
        mTableViewAdapter = new CarBuyVsLeaseInnerTableAdapter(mContext);
        tableView.setAdapter(mTableViewAdapter);
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tableView.setLayoutParams(tlp);
        return tableView;
    }

    private List<List<Cell>> getCellListForSortingTestDefict() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < getcashflowinoutflowModel.getData().getFin_cash_flow().size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {

                String text = "" + 0;

                String id = j + "-" + i;

                Cell cell;
                switch (j) {

                    case 0:
                        try {
                            if (getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getCash_flow() != null) {
                                if (Float.parseFloat(getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getCash_flow()) < 0) {
                                    text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(getcashflowinoutflowModel.getData().getFin_cash_flow().
                                            get(i).getCash_flow()));
                                } else {
                                    text = "₹ " + "0";
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            if (getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getCash_flow() != null) {
                                if (Float.parseFloat(getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getCash_flow()) > 0) {
                                    text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(getcashflowinoutflowModel.getData().getFin_cash_flow().
                                            get(i).getCash_flow()));
                                } else {
                                    text = "₹ " + "0";
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                }
                cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    private List<ColumnHeader> getColumnHeaderListDefict() {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < dificitColoumName.size(); i++) {
            ColumnHeader header = new ColumnHeader("" + i, dificitColoumName.get(i));
            list.add(header);
        }
        return list;
    }

    private List<RowHeader> getRowHeaderListDefict() {
        List<RowHeader> list = new ArrayList<>();
        int n = getcashflowinoutflowModel.getData().getFin_cash_flow().size();
        for (int i = 0; i < n; i++) {
            RowHeader header = new RowHeader("row " + i, "" + i);
            list.add(header);
        }
        return list;
    }


    //Tax-Cash-flow
    private void initDataTaxCash() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();

        int n = taxAnalysisCashFlowChartModel.getData().getUser_tax().size();

        for (int i = 0; i < n; i++) {
            mCellList.add(new ArrayList<Cell>());
        }
    }

    private void loadDataTaxCash() {
        List<RowHeader> rowHeaders = getRowHeaderListTaxCash();
        List<List<Cell>> cellList = getCellListForSortingTestTaxCash();
        List<ColumnHeader> columnHeaders = getColumnHeaderListTaxCash();

        mRowHeaderList.addAll(rowHeaders);
        for (int i = 0; i < cellList.size(); i++) {
            mCellList.get(i).addAll(cellList.get(i));
        }
        mColumnHeaderList.addAll(columnHeaders);
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

    }

    private List<List<Cell>> getCellListForSortingTestTaxCash() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < taxAnalysisCashFlowChartModel.getData().getUser_tax().size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 8; j++) {

                String text = "" + 0;

                String id = j + "-" + i;

                Cell cell;
                switch (j) {
                    case 0:
                        try {
                            if (taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getTotal_savings() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(taxAnalysisCashFlowChartModel.getData().getUser_tax().
                                        get(i).getTotal_savings()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            if (taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getTotal_tax_payable() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(taxAnalysisCashFlowChartModel.getData().getUser_tax().
                                        get(i).getTotal_tax_payable()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            if (taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getActual_tax() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(taxAnalysisCashFlowChartModel.getData().getUser_tax().
                                        get(i).getActual_tax()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            if (taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getCess() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(taxAnalysisCashFlowChartModel.getData().getUser_tax().
                                        get(i).getCess()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            if (taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getSurage() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(taxAnalysisCashFlowChartModel.getData().getUser_tax().
                                        get(i).getSurage()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                            if (taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getTax_age() != null) {
                                text = taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getTax_age();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        try {
                            if (taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getYears_pass() != null) {
                                text = taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getYears_pass();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 7:
                        try {
                            if (taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getYears_remain() != null) {
                                text = taxAnalysisCashFlowChartModel.getData().getUser_tax().get(i).getYears_remain();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                }
                cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    private List<ColumnHeader> getColumnHeaderListTaxCash() {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < taxCashColoumName.size(); i++) {
            ColumnHeader header = new ColumnHeader("" + i, taxCashColoumName.get(i));
            list.add(header);
        }
        return list;
    }

    private List<RowHeader> getRowHeaderListTaxCash() {
        List<RowHeader> list = new ArrayList<>();
        int n = taxAnalysisCashFlowChartModel.getData().getUser_tax().size();
        for (int i = 0; i < n; i++) {
            RowHeader header = new RowHeader("row " + i, "" + i);
            list.add(header);
        }
        return list;
    }

    //Expense
    private void initDataExpense() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();

        int n = incomeExpenseCashFlowModel.getData().getCash_mang_det().size();

        for (int i = 0; i < n; i++) {
            mCellList.add(new ArrayList<Cell>());
        }
    }

    private void loadDataExpense() {
        List<RowHeader> rowHeaders = getRowHeaderListExpense();
        List<List<Cell>> cellList = getCellListForSortingTestExpense();
        List<ColumnHeader> columnHeaders = getColumnHeaderListExpense();

        mRowHeaderList.addAll(rowHeaders);
        for (int i = 0; i < cellList.size(); i++) {
            mCellList.get(i).addAll(cellList.get(i));
        }
        mColumnHeaderList.addAll(columnHeaders);
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

    }

    private List<List<Cell>> getCellListForSortingTestExpense() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < incomeExpenseCashFlowModel.getData().getCash_mang_det().size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {

                String text = "" + 0;

                String id = j + "-" + i;

                Cell cell;
                switch (j) {
                    case 0:
                        try {
                            if (incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getCash_age() != null) {
                                text = incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getCash_age();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            if (incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getSi_total() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
                                        get(i).getSi_total()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            if (incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getIp_total() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
                                        get(i).getIp_total()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            if (incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getIb_total() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
                                        get(i).getIb_total()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            if (incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getCg_total() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
                                        get(i).getCg_total()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                            if (incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getIfs_total() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
                                        get(i).getIfs_total()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        try {
                            if (incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getOver_all_exp() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
                                        get(i).getOver_all_exp()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 7:
                        try {
                            if (incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getOver_all_obli() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
                                        get(i).getOver_all_obli()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 8:
                        try {
                            if (incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getOver_all_contr() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
                                        get(i).getOver_all_contr()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 9:
                        try {
                            if (incomeExpenseCashFlowModel.getData().getCash_mang_det().get(i).getOver_all_contr() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(incomeExpenseCashFlowModel.getData().getCash_mang_det().
                                        get(i).getOver_all_contr()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                }
                cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    private List<ColumnHeader> getColumnHeaderListExpense() {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < expenseColoumName.size(); i++) {
            ColumnHeader header = new ColumnHeader("" + i, expenseColoumName.get(i));
            list.add(header);
        }
        return list;
    }

    private List<RowHeader> getRowHeaderListExpense() {
        List<RowHeader> list = new ArrayList<>();
        int n = incomeExpenseCashFlowModel.getData().getCash_mang_det().size();
        for (int i = 0; i < n; i++) {
            RowHeader header = new RowHeader("row " + i, "" + i);
            list.add(header);
        }
        return list;
    }


    //Overall-Cash-flow
    private void initDataOverallCash() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();

        int n = getcashflowinoutflowModel.getData().getFin_cash_flow().size();

        for (int i = 0; i < n; i++) {
            mCellList.add(new ArrayList<Cell>());
        }
    }

    private void loadDataOverallCash() {
        List<RowHeader> rowHeaders = getRowHeaderListOverallCash();
        List<List<Cell>> cellList = getCellListForSortingTestOverallCash();
        List<ColumnHeader> columnHeaders = getColumnHeaderListOverallCash();

        mRowHeaderList.addAll(rowHeaders);
        for (int i = 0; i < cellList.size(); i++) {
            mCellList.get(i).addAll(cellList.get(i));
        }
        mColumnHeaderList.addAll(columnHeaders);
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

    }

    private List<List<Cell>> getCellListForSortingTestOverallCash() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < getcashflowinoutflowModel.getData().getFin_cash_flow().size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 6; j++) {

                String text = "" + 0;

                String id = j + "-" + i;

                Cell cell;
                switch (j) {
                    case 0:
                        try {
                            if (getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getCash_age() != null) {
                                text = getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getCash_age();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        try {
                            if (getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getIn_flow() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(getcashflowinoutflowModel.getData().getFin_cash_flow().
                                        get(i).getIn_flow()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            if (getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getOut_flow() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(getcashflowinoutflowModel.getData().getFin_cash_flow().
                                        get(i).getOut_flow()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            if (getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getCash_flow() != null) {
                                text = "₹ " + UtileKit.longvalueabsolute(Float.parseFloat(getcashflowinoutflowModel.getData().getFin_cash_flow().
                                        get(i).getCash_flow()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            if (getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getYears_pass() != null) {
                                text = getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getYears_pass();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                            if (getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getYears_remain() != null) {
                                text = getcashflowinoutflowModel.getData().getFin_cash_flow().get(i).getYears_remain();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                }
                cell = new Cell(id, text);
                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    private List<ColumnHeader> getColumnHeaderListOverallCash() {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < overallCashColoumName.size(); i++) {
            ColumnHeader header = new ColumnHeader("" + i, overallCashColoumName.get(i));
            list.add(header);
        }
        return list;
    }

    private List<RowHeader> getRowHeaderListOverallCash() {
        List<RowHeader> list = new ArrayList<>();
        int n = getcashflowinoutflowModel.getData().getFin_cash_flow().size();
        for (int i = 0; i < n; i++) {
            RowHeader header = new RowHeader("row " + i, "" + i);
            list.add(header);
        }
        return list;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_cash_chart, menu);
        MenuItem items = menu.findItem(R.id.menu_chart);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_chart:
                try {
                    addFragmenttoStack(CashflowChartFragment.newInstance(checkbox_position));
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
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
        }

    }

}

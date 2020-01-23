package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.adapter.CarBuyVsLeaseInnerTableAdapter;
import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models.CarVsLeaseModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.taxproduct.model.Cell;
import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
import com.purplepath.purplepath.taxproduct.model.RowHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pravinr on 2/15/18.
 */

public class CarVsLeaseInnerTableFragment1 extends BaseFragment implements View.OnClickListener{

    private Bundle args;
    private CarVsLeaseModel carVsLeaseModel;
    private int code;


    private ArrayList<String> leaseColoumName=new ArrayList<String>();
    private ArrayList<String> cashColoumName=new ArrayList<String>();
    private ArrayList<String> loanColoumName=new ArrayList<String>();

    private TableView mTableView;

    private List<RowHeader> mRowHeaderList;
    private List<ColumnHeader> mColumnHeaderList;
    private List<List<Cell>> mCellList;

    private AbstractTableAdapter mTableViewAdapter;

    private Context mContext;

    RelativeLayout fragment_container;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;


    public static CarVsLeaseInnerTableFragment1 newInstance(CarVsLeaseModel carVsLeaseModel, int code) {

        Bundle args = new Bundle();
        args.putSerializable("carVsLeaseModel", carVsLeaseModel);
        args.putInt("code", code);
        CarVsLeaseInnerTableFragment1 fragment = new CarVsLeaseInnerTableFragment1();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        mCallBackListener= (OnActivityBackPressedListener) mContext;
        try {
            leaseColoumName.add("Tenure");
            leaseColoumName.add("Yearly Lease Amount");
            leaseColoumName.add("Upfront Expense");
            leaseColoumName.add("Lease Fuel Run Expenses");
            leaseColoumName.add("Lease Insurance");
            leaseColoumName.add("Lease Tax");
            leaseColoumName.add("Lease ForeGone Intrest");
            leaseColoumName.add("Lease termination Expenses");
            leaseColoumName.add("Total Lease Expenses");


            cashColoumName.add("Tenure");
            cashColoumName.add("Cash Payment");
            cashColoumName.add("Cash Main Repair");
            cashColoumName.add("Cash Fuel Run Expenses");
            cashColoumName.add("Cash Insurance");
            cashColoumName.add("Cash Foregone Intrest");
            cashColoumName.add("Total Cost Buying Cash");
            cashColoumName.add("Lease Cash Difference");
            cashColoumName.add("Loan Cash Difference");

            loanColoumName.add("Tenure");
            loanColoumName.add("Down Payment");
            loanColoumName.add("Loan Payment");
            loanColoumName.add("Loan Interest");
            loanColoumName.add("Loan Principal");
            loanColoumName.add("Loan Out Balance");
            loanColoumName.add("Loan Main Repair");
            loanColoumName.add("Loan Fuel Run Expenses");
            loanColoumName.add("Loan Insurance");
            loanColoumName.add("Loan Foregone Intrest");
            loanColoumName.add("Total Cost Buying Loan");
            loanColoumName.add("Lease Loan Difference");



        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inner_table_lease, container, false);

         fragment_container = view.findViewById(R.id.containers);

        mleftRelativeLayout = view. findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view. findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view. findViewById(R.id.relative_right_arrow);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);


        args = getArguments();
        if (null != args) {
            if (args.containsKey("carVsLeaseModel")) {
                carVsLeaseModel = (CarVsLeaseModel) args.getSerializable("carVsLeaseModel");
                //setValuesFromModel();
            }

            if (args.containsKey("code")) {
                code = args.getInt("code");
            }
        }

        if (carVsLeaseModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
            if (null != carVsLeaseModel.getData()) {
                switch (code) {
                    case 1:
                        if (carVsLeaseModel.getData().getCar_lease() != null) {
                            initData();
                            mTableView = createTableView();
                            fragment_container.addView(mTableView);
                            loadData();
                        }
                        break;
                    case 2:
                        if (carVsLeaseModel.getData().getCar_cash() != null) {
                            initDataCash();
                            mTableView = createTableView();
                            fragment_container.addView(mTableView);
                            loadDataCash();
                        }
                        break;
                    case 3:
                        if (carVsLeaseModel.getData().getCar_loan() != null) {
                            initDataLoan();
                            mTableView = createTableView();
                            fragment_container.addView(mTableView);
                            loadDataLoan();
                        }
                        break;
                }

            } else {
                Toast.makeText(getContext(), "No Details to show", Toast.LENGTH_LONG).show();
            }
        }


        return view;
    }


    //Lease
    private void initData() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();

       int n=carVsLeaseModel.getData().getCar_lease().getCashflow().size();

        for (int i = 0; i < n; i++) {
            mCellList.add(new ArrayList<Cell>());
        }
    }

    private void loadData() {
        List<RowHeader> rowHeaders = getRowHeaderList();
        List<List<Cell>> cellList = getCellListForSortingTest();
        List<ColumnHeader> columnHeaders = getColumnHeaderList();
        mRowHeaderList.addAll(rowHeaders);
        for (int i = 0; i < cellList.size(); i++) {
            mCellList.get(i).addAll(cellList.get(i));
        }
        // Load all data
        mColumnHeaderList.addAll(columnHeaders);
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
    }

    private TableView createTableView() {
        TableView tableView = new TableView(getContext());
        // Set adapter
        mTableViewAdapter = new CarBuyVsLeaseInnerTableAdapter(mContext);
        tableView.setAdapter(mTableViewAdapter);
        // Set layout params
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tableView.setLayoutParams(tlp);
        return tableView;
    }

    private List<RowHeader> getRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();
        int n=carVsLeaseModel.getData().getCar_lease().getCashflow().size();
        for (int i = 0; i <n ; i++) {
            RowHeader header = new RowHeader("row " + i,""+i);
            list.add(header);
        }
        return list;
    }

    private List<List<Cell>> getCellListForSortingTest() {
        List<List<Cell>> list = new ArrayList<>();

        //here for loop start 1st position because web service start "0" remove (Murugesan&prabhu)
        for (int i = 0; i < carVsLeaseModel.getData().getCar_lease().getCashflow().size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 9; j++) {

               // Object text = "cell " + j + " " + i;

                String text = ""+ 0;

                // Create dummy id.
                String id = j + "-" + i;

                Cell cell;
                switch (j) {
                   /* case 0:
                        text = "" + (i + 1);
                        break;*/
                    case 0:
                        text = carVsLeaseModel.getData().getCar_lease().getCashflow().get(i).getTenure();
                        break;
                    case 1:
                        text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().
                                getCashflow().get(i).getYearly_lease_amt()));
                        break;

                    case 2:
                        text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().
                                getCashflow().get(i).getUpfront_expense()));
                        break;
                    case 3:
                        text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().
                                getCashflow().get(i).getLease_fuel_run_exp()));
                        break;
                    case 4:
                        text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().
                                getCashflow().get(i).getLease_insurance()));
                        break;
                    case 5:
                        text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().
                                getCashflow().get(i).getLease_tax()));
                        break;
                    case 6:
                        text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().
                                getCashflow().get(i).getLease_foregone_int()));
                        break;
                    case 7:
                        text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().
                                getCashflow().get(i).getLease_ter_exp()));
                        break;
                    case 8:
                        text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().
                                getCashflow().get(i).getTot_lease_exp()));
                        break;
                }

                    cell = new Cell(id, text);
                    cellList.add(cell);
                }
                list.add(cellList);
            }

            return list;
        }

    private List<ColumnHeader> getColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < leaseColoumName.size(); i++) {
            ColumnHeader header = new ColumnHeader(""+i, leaseColoumName.get(i));
            list.add(header);
        }
        return list;
    }


    //Cash
    private void initDataCash() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();

        int n=carVsLeaseModel.getData().getCar_cash().getCashflow().size();

        for (int i = 0; i < n; i++) {
            mCellList.add(new ArrayList<Cell>());
        }
    }

    private void loadDataCash() {
        List<RowHeader> rowHeaders = getRowHeaderListCash();
        List<List<Cell>> cellList = getCellListForSortingTestCash();
        List<ColumnHeader> columnHeaders = getColumnHeaderListCash();
        mRowHeaderList.addAll(rowHeaders);
        for (int i = 0; i < cellList.size(); i++) {
            mCellList.get(i).addAll(cellList.get(i));
        }
        // Load all data
        mColumnHeaderList.addAll(columnHeaders);
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
    }

    private List<RowHeader> getRowHeaderListCash() {
        List<RowHeader> list = new ArrayList<>();
        int n=carVsLeaseModel.getData().getCar_cash().getCashflow().size();
        for (int i = 0; i <n ; i++) {
            RowHeader header = new RowHeader("row " + i,""+i);
            list.add(header);
        }
        return list;
    }

    private List<List<Cell>> getCellListForSortingTestCash() {
        List<List<Cell>> list = new ArrayList<>();

        //here for loop start 1st position because web service start "0" remove (Murugesan&prabhu)
        for (int i = 0; i < carVsLeaseModel.getData().getCar_cash().getCashflow().size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 9; j++) {

                //Object text = "cell " + j + " " + i;

                String text = ""+ 0;

                // Create dummy id.
                String id = j + "-" + i;

                Cell cell;
                switch (j) {
                   /* case 0:
                        text = "" + (i + 1);
                        break;*/

                    case 0:
                        text = carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getTenure();
                        break;
                    case 1:
                        text =UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_cash()
                                .getCashflow().get(i).getCash_payment()));
                        break;

                    case 2:
                        text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_cash().
                                getCashflow().get(i).getCash_main_repair()));
                        break;
                    case 3:
                        try {
                            if(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getCash_fuel_run_exp()!=null) {
                        text = formatNegativeNumber(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).
                                getCash_fuel_run_exp());
                            }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                        if(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getCash_insurance()!=null) {
                            text = formatNegativeNumber(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).
                                    getCash_insurance());
                        }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        text =UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_cash().
                                getCashflow().get(i).getCash_foregone_int()));
                        break;
                    case 6:
                        text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_cash().
                                getCashflow().get(i).getTot_cost_buying_cash()));
                        break;
                    case 7:
                        try {
                            if(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getLease_cash_difference()!=null){
                        text = formatNegativeNumber(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).
                                getLease_cash_difference());
                        }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 8:
                        try {
                            if(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getLoan_cash_difference()!=null){
                        text = formatNegativeNumber(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).
                                getLoan_cash_difference());
                            }}catch (Exception e){
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

    private List<ColumnHeader> getColumnHeaderListCash() {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < cashColoumName.size(); i++) {
            ColumnHeader header = new ColumnHeader(""+i, cashColoumName.get(i));
            list.add(header);
        }
        return list;
    }


    //Loan
    private void initDataLoan() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();

        int n=carVsLeaseModel.getData().getCar_loan().getCashflow().size();

        for (int i = 0; i < n; i++) {
            mCellList.add(new ArrayList<Cell>());
        }
    }

    private void loadDataLoan() {
        List<RowHeader> rowHeaders = getRowHeaderListLoan();
        List<List<Cell>> cellList = getCellListForSortingTestLoan();
        List<ColumnHeader> columnHeaders = getColumnHeaderListLoan();
        mRowHeaderList.addAll(rowHeaders);
        for (int i = 0; i < cellList.size(); i++) {
            mCellList.get(i).addAll(cellList.get(i));
        }
        // Load all data
        mColumnHeaderList.addAll(columnHeaders);
        mTableViewAdapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);
    }

    private List<RowHeader> getRowHeaderListLoan() {
        List<RowHeader> list = new ArrayList<>();
        int n=carVsLeaseModel.getData().getCar_loan().getCashflow().size();
        for (int i = 0; i <n ; i++) {
            RowHeader header = new RowHeader("row " + i,""+i);
            list.add(header);
        }
        return list;
    }

    private List<List<Cell>> getCellListForSortingTestLoan() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i <carVsLeaseModel.getData().getCar_loan().getCashflow().size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 12; j++) {

               // Object text = "cell " + j + " " + i;

                String text = ""+ 0;
                // Create dummy id.
                String id = j + "-" + i;

                Cell cell;
                switch (j) {
                   /* case 0:
                        text = "" + (i + 1);
                        break;*/

                    case 0:
                        text = carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getTenure();
                        break;
                    case 1:
                        if(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getDown_pay()!=null){
                        text =UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().
                                getCashflow().get(i).getDown_pay()));
                        }
                        break;

                    case 2:
                        if(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_payment()!=null) {
                            text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().
                                    getCashflow().get(i).getLoan_payment()));
                        }
                        break;
                    case 3:
                        try {
                            if(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_interest()!=null) {
                                text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().
                                        get(i).getLoan_interest()));
                            }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            if(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_principal()!=null) {
                                text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().
                                        getCashflow().get(i).getLoan_principal()));
                            }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                        if(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_out_bal()!=null){
                        text =UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().
                                get(i).getLoan_out_bal()));
                        }
                }catch (Exception e){
                    e.printStackTrace();
                }
                        break;
                    case 6:
                        try{
                        if(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_main_repair()!=null) {
                            text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().
                                    get(i).getLoan_main_repair()));
                        }}catch (Exception e){
                    e.printStackTrace();
                }
                        break;
                    case 7:
                        try {
                            if(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_fuel_run_exp()!=null){
                                text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().
                                        getCashflow().get(i).getLoan_fuel_run_exp()));
                            }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 8:
                        try {
                            if(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_interest()!=null){
                                text = UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().
                                        getCashflow().get(i).getLoan_interest()));
                            }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 9:
                        try {
                        if(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_foregone_int()!=null){
                        text=UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().
                                getCashflow().get(i).getLoan_foregone_int()));}
                }catch (Exception e){
                    e.printStackTrace();
                }
                        break;
                    case 10:
                        try {
                        if(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getTot_cost_buying_loan()!=null){
                        text=UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().
                                getCashflow().get(i).getTot_cost_buying_loan()));}
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 11:
                        try {
                        if(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLease_loan_difference()!=null){
                        text=UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().
                                get(i).getLease_loan_difference()));
                        }}catch (Exception e){
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

    private List<ColumnHeader> getColumnHeaderListLoan() {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < loanColoumName.size(); i++) {
            ColumnHeader header = new ColumnHeader(""+i, loanColoumName.get(i));
            list.add(header);
        }
        return list;
    }


    private String formatNegativeNumber(String value){
        String result=null;
        try {
            if(value.contains("-")){
                String[] arr=value.split("-");
                result="-"+UtileKit.formatedNumber(Float.parseFloat(arr[1]));
                Log.i("spcheck", "formatNegativeNumber: value="+value+ "result="+result);
            }else {
                result=UtileKit.formatedNumber(Float.parseFloat(value));
            }

        }catch (NumberFormatException e){e.printStackTrace();}

        return result;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }
    }

}

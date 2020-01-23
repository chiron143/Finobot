package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models.BuyVsRentModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.taxproduct.model.Cell;
import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
import com.purplepath.purplepath.taxproduct.model.RowHeader;
import com.purplepath.purplepath.taxprompt.TaxPromptShowDetailView;

import java.util.ArrayList;
import java.util.List;

import static com.finobot.finobot.R.id.relative_center_home;
import static com.finobot.finobot.R.id.relative_left_arrow;

/**
 * Created by pravinr on 2/15/18.
 */

public class InnerTableFragment1 extends BaseFragment implements View.OnClickListener{

    private Bundle args;
    private BuyVsRentModel buyVsRentModel;
    private int code;

    private ArrayList<String> rentingColoumName=new ArrayList<String>();
    private ArrayList<String> cashColoumName=new ArrayList<String>();
    private ArrayList<String> loanColoumName=new ArrayList<String>();

    private TableView mTableView;

    private List<RowHeader> mRowHeaderList;
    private List<ColumnHeader> mColumnHeaderList;
    private List<List<Cell>> mCellList;

    private AbstractTableAdapter mTableViewAdapter;

    private Context mContext;

    RelativeLayout fragment_container;



    public static InnerTableFragment1 newInstance(BuyVsRentModel buyVsRentModel, int code) {

        Bundle args = new Bundle();
        args.putSerializable("buyVsRentModel", buyVsRentModel);
        args.putInt("code", code);
        InnerTableFragment1 fragment = new InnerTableFragment1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();

        try {
            rentingColoumName.add("Tenure");
            rentingColoumName.add("Foregone Interest");
            rentingColoumName.add("Total Annual cost of Rent");

            cashColoumName.add("Tenure");
            cashColoumName.add("Foregone Interest");
            cashColoumName.add("Gross cost of Buying");
            cashColoumName.add("Tax Savings Deductions");
            cashColoumName.add("Tax Savings");
            cashColoumName.add("Net Cost of Buying");
            cashColoumName.add("Rent Cash Difference");
            cashColoumName.add("Loan Cash Difference");

            loanColoumName.add("Tenure");
            loanColoumName.add("Property Price");
            loanColoumName.add("Property Value");
            loanColoumName.add("Mortgage Payment");
            loanColoumName.add("Mortgage Interest");
            loanColoumName.add("Tax -Section 24");
            loanColoumName.add("Mortgage Principal");
            loanColoumName.add("Tax -Section 80c");
            loanColoumName.add("Total interest on Mortgage");
            loanColoumName.add("Tax Savings");
            loanColoumName.add("Net Annual Cost of Buying");
            loanColoumName.add("Rent Loan Difference");



        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inner_table_rating, container, false);
        fragment_container = view.findViewById(R.id.house_containers);


        args = getArguments();
        if (null != args) {
            if (args.containsKey("buyVsRentModel")) {
                buyVsRentModel = (BuyVsRentModel) args.getSerializable("buyVsRentModel");
                //setValuesFromModel();
            }

            if (args.containsKey("code")) {
                code = args.getInt("code");
            }
        }

        if (buyVsRentModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
            if (null != buyVsRentModel.getData()) {
                switch (code) {
                    case 1:
                        if (buyVsRentModel.getData().getHome_rent() != null) {

                            initData();
                            mTableView = createTableView();
                            fragment_container.addView(mTableView);
                            loadData();
                        }
                        break;
                    case 2:
                        if (buyVsRentModel.getData().getHome_cash() != null) {
                            initDataCash();
                            mTableView = createTableView();
                            fragment_container.addView(mTableView);
                            loadDataCash();
                        }
                        break;
                    case 3:
                        if (buyVsRentModel.getData().getHome_loan() != null) {
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

    //Rent
    private void initData() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();

        int n=buyVsRentModel.getData().getHome_rent().getCashflow().size();

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
        int n=buyVsRentModel.getData().getHome_rent().getCashflow().size();
        for (int i = 0; i <n ; i++) {
            RowHeader header = new RowHeader("row " + i,""+i);
            list.add(header);
        }
        return list;
    }

    private List<List<Cell>> getCellListForSortingTest() {
        List<List<Cell>> list = new ArrayList<>();

        //here for loop start 1st position because web service start "0" remove (Murugesan&prabhu)
        for (int i = 0; i < buyVsRentModel.getData().getHome_rent().getCashflow().size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {

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
                        text = buyVsRentModel.getData().getHome_rent().getCashflow().get(i).getTenure();
                        break;
                    case 1:
                        try {
                        if(buyVsRentModel.getData().getHome_rent().getCashflow().get(i).getForegone_interest()!=null) {
                            text = UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_rent().
                                    getCashflow().get(i).getForegone_interest()));
                        }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;

                    case 2:
                        try {
                        if(buyVsRentModel.getData().getHome_rent().getCashflow().get(i).getTot_annu_cost_rent()!=null) {
                            text = UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_rent().
                                    getCashflow().get(i).getTot_annu_cost_rent()));
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

    private List<ColumnHeader> getColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();
        for (int i = 0; i < rentingColoumName.size(); i++) {
            ColumnHeader header = new ColumnHeader(""+i, rentingColoumName.get(i));
            list.add(header);
        }
        return list;
    }


    //Cash
    private void initDataCash() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();

        int n=buyVsRentModel.getData().getHome_cash().getCashflow().size();

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
        int n=buyVsRentModel.getData().getHome_cash().getCashflow().size();
        for (int i = 0; i <n ; i++) {
            RowHeader header = new RowHeader("row " + i,""+i);
            list.add(header);
        }
        return list;
    }

    private List<List<Cell>> getCellListForSortingTestCash() {
        List<List<Cell>> list = new ArrayList<>();

        //here for loop start 1st position because web service start "0" remove (Murugesan&prabhu)
        for (int i = 0; i < buyVsRentModel.getData().getHome_cash().getCashflow().size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
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
                        if(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getTenure()!=null) {
                            text = buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getTenure();
                        }
                        break;
                    case 1:
                        try {
                        if(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getForegone_inerest()!=null) {
                            text = UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_cash().
                                    getCashflow().get(i).getForegone_inerest()));
                        }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;

                    case 2:
                        try {
                        if(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getGross_cost_of_buying()!=null) {
                            text = UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_cash().
                                    getCashflow().get(i).getGross_cost_of_buying()));
                        }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            if(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getTax_savings_deductions()!=null) {
                                text = formatNegativeNumber(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).
                                        getTax_savings_deductions());
                            }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            if(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getTax_savings()!=null) {
                                text = formatNegativeNumber(buyVsRentModel.getData().getHome_cash().getCashflow().
                                        get(i).getTax_savings());
                            }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                        if(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getNet_cost_of_buying()!=null) {
                            text = UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_cash().
                                    getCashflow().get(i).getNet_cost_of_buying()));
                        }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        try{
                        if(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getRent_cash_difference()!=null) {
                            text = formatNegativeNumber(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).
                                    getRent_cash_difference());
                        }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 7:
                        try {
                            if(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getLoan_cash_difference()!=null){
                                text = formatNegativeNumber(buyVsRentModel.getData().getHome_cash().getCashflow().
                                        get(i).getLoan_cash_difference());
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

        int n=buyVsRentModel.getData().getHome_loan().getCashflow().size();

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
        int n=buyVsRentModel.getData().getHome_loan().getCashflow().size();
        for (int i = 0; i <n ; i++) {
            RowHeader header = new RowHeader("row " + i,""+i);
            list.add(header);
        }
        return list;
    }

    private List<List<Cell>> getCellListForSortingTestLoan() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i <buyVsRentModel.getData().getHome_loan().getCashflow().size(); i++) {
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
                        if(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTenure()!=null) {
                            text = buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTenure();
                        }
                        break;
                    case 1:
                        try {
                        if(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getPropertyPrice()!=null){
                            text =UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).
                                    getPropertyPrice()));
                        }}catch (Exception e){
                    e.printStackTrace();
                }
                        break;

                    case 2:
                        try{
                        if(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getPropertyValue()!=null) {
                            text = UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).
                                    getPropertyValue()));
                        }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            if(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getMortagePayment()!=null) {
                                text = UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).
                                        getMortagePayment()));
                            }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        try {
                            if(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getMortageInterest()!=null) {
                                text = UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).
                                        getMortageInterest()));
                            }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 5:
                        try {
                            if(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTax24()!=null){
                                text =checkForOptionalValueFormat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTax24());
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 6:
                        try{
                            if(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getMortagePrincipal()!=null) {
                                text =UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).
                                        getMortagePrincipal()));
                            }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 7:
                        try {
                            if(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTax80c()!=null){
                                text = checkForOptionalValueFormat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTax80c());
                            }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 8:
                        try {
                            if(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTotIntOnMortgage()!=null){
                                text = UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).
                                        getTotIntOnMortgage()));
                            }}catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 9:
                        try {
                            if(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTaxSavings()!=null){
                                text=UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).
                                        getTaxSavings()));
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 10:
                        try {
                            if(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getNetAnnuCostOfBuying()!=null){
                                text=UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).
                                        getNetAnnuCostOfBuying()));}
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        break;
                    case 11:
                        try {
                            if(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getRentLoanDifference()!=null){
                                text=formatNegativeNumber(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).
                                        getRentLoanDifference());
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



    private String checkForOptionalValueFormat(String value) {
        String result = null;
        if (value.matches("[0-9]+"))
            result = UtileKit.formatedNumber(Float.parseFloat(value));
        else
            result = value;

        return result;
    }

    private String formatNegativeNumber(String value){
        String result=null;
        try {
            if(value.contains("-")){
                String[] arr=value.split("-");
                result="-"+ UtileKit.formatedNumber(Float.parseFloat(arr[1]));
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

        }
    }
}

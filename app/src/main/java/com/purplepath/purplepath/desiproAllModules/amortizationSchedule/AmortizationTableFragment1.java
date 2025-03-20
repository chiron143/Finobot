package com.purplepath.purplepath.desiproAllModules.amortizationSchedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.amortizationSchedule.adapter.AmortizationTableAdapter;
import com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models.AmortizationScheduleModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.taxproduct.model.Cell;
import com.purplepath.purplepath.taxproduct.model.ColumnHeader;
import com.purplepath.purplepath.taxproduct.model.RowHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;

/**
 * Created by pravinr on 2/14/18.
 */

public class AmortizationTableFragment1 extends BaseFragment implements View.OnClickListener {


    private Bundle args;
    private AmortizationScheduleModel amort_model;

    private Context mContext;

    private OnActivityBackPressedListener mCallBackListener;

    private AbstractTableAdapter mTableViewAdapter;

    private TableView mTableView;

    private ArrayList<String> coloumName=new ArrayList<String>();

    private List<RowHeader> mRowHeaderList;
    private List<ColumnHeader> mColumnHeaderList;
    private List<List<Cell>> mCellList;

    RelativeLayout fragment_container;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    public static AmortizationTableFragment1 newInstance(AmortizationScheduleModel amort_model) {

        Bundle args = new Bundle();
        args.putSerializable("amort_model", amort_model);
        AmortizationTableFragment1 fragment = new AmortizationTableFragment1();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            setHasOptionsMenu(true);
            mContext=getContext();

            coloumName.add("Month");
            coloumName.add("Outstanding Balance start");
            coloumName.add("EMI");
            coloumName.add("Interest");
            coloumName.add("Principal");
            coloumName.add("Service Tax");
            coloumName.add("Outstanding Balance end");
            coloumName.add("Total Payment");
            coloumName.add("Cummulative Payment");
            coloumName.add("Cummulative Principal");
            coloumName.add("Cummulative Interest");
            coloumName.add("Cummulative Tax");


            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amortization_table1, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);


        mCallBackListener.setActionBarTitle("DeciPro - Amortization Schedule");


        mContext=getContext();
        args = getArguments();
        if (args != null) {
            if (args.containsKey("amort_model")) {
                amort_model = (AmortizationScheduleModel) args.getSerializable("amort_model");
            }

        }

        initData();
        fragment_container = view.findViewById(R.id.container);
        // Create Table view
        mTableView = createTableView();
        fragment_container.addView(mTableView);
        loadData();
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);


        return view;
    }


    private void initData() {
        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();

        int n=amort_model.getData().getAmtz_sch().size();

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
        mTableViewAdapter = new AmortizationTableAdapter(mContext,amort_model);
        tableView.setAdapter(mTableViewAdapter);


        // Set layout params
        FrameLayout.LayoutParams tlp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams
                .MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tableView.setLayoutParams(tlp);

        return tableView;
    }

    private List<List<Cell>> getCellListForSortingTest() {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < amort_model.getData().getAmtz_sch().size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 12; j++) {
                String text = "cell " + j + " " + i;

                final int random = new Random().nextInt();

                // Create dummy id.
                String id = j + "-" + i;

                Cell cell;
                switch(j)
                {
                    case 0:
                        text=""+(i+1);
                        break;
                    case 1:
                        text= UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i)
                                .getOut_bal_start()));
                        break;
                    case 2:
                        text=UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).getEmi()));
                        break;

                    case 3:
                        text=UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).
                                getLoan_interest()));
                        break;
                    case 4:
                        text=UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).
                                getLoan_principal()));
                        break;
                    case 5:
                        text=UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).
                                getService_tax()));
                        break;
                    case 6:
                        text=UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).
                                getOut_bal_end()));
                        break;
                    case 7:
                        text=UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).
                                getTot_payment()));
                        break;
                    case 8:
                        text=UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).
                                getCum_payment()));
                        break;
                    case 9:
                        text=UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).
                                getCum_principal()));
                        break;
                    case 10:
                        text=UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).
                                getCum_interest()));
                        break;
                    case 11:
                        text=UtileKit.formatedNumber(Float.parseFloat(amort_model.getData().getAmtz_sch().get(i).
                                getCum_tax()));
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
        String title ="";
        for (int i = 0; i < coloumName.size(); i++) {



            ColumnHeader header = new ColumnHeader(""+i, coloumName.get(i));
            list.add(header);
        }

        return list;
    }

    private List<RowHeader> getRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();

        int n=amort_model.getData().getAmtz_sch().size();
        for (int i = 0; i <n ; i++) {

            RowHeader header = new RowHeader("row " + i,""+i);
            list.add(header);
        }

        return list;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.networth_summary_menu,menu);
        menu.findItem(R.id.menu_details).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_graph:
                AmortizationGraph fragment=AmortizationGraph.newInstance(amort_model);
                addFragment(fragment);
                break;

            case android.R.id.home:
                // ((OnActivityBackPressedListener)mContext).onActivityBackPressed();
                break;

        }
        return super.onOptionsItemSelected(item);

    }

    private void addFragment(AmortizationGraph fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.fragment_container,fragment);
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

}

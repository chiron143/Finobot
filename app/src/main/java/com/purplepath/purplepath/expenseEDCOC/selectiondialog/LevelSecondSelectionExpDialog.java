package com.purplepath.purplepath.expenseEDCOC.selectiondialog;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.expenseEDCOC.selectiondialog.adapter.Lev2IncTabAdapterExp;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelZeroData;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncLev2SelLisInterface;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dinesh on 01/06/17.
 */

public class LevelSecondSelectionExpDialog extends DialogFragment {
    String familyId;
    ArrayList<ExpensesLevelZeroData> selectedList;
    HashMap<String, ExpensesLevelZeroData> incomeFilterHashMap;
    IncLev2SelLisInterface incLev2SelLisInterface;
    private static  String ARG_PARAM2="incomeCatagoryList";
    private static  String ARG_PARAM3="selectedLev1List";
    private static  String ARG_PARM4="Selected2List";
    private static String ARG_PARM5="selected3List";
    private TabLayout mTabLayoutIncome;
    private Lev2IncTabAdapterExp adapter;
    private ViewPager mViewPager;
    ArrayList<String> selLev1List,selLev2List;
    private TextView dialogTitle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(androidx.fragment.app.DialogFragment.STYLE_NO_FRAME, R.style.MY_DIALOG);
        if (getArguments() != null) {

            if (getArguments().containsKey(ARG_PARAM2)) {
                incomeFilterHashMap = (HashMap<String,ExpensesLevelZeroData>)getArguments().getSerializable(ARG_PARAM2);

            }
            if(getArguments().containsKey(ARG_PARAM3))
            {
                selectedList=(ArrayList<ExpensesLevelZeroData>)getArguments().getSerializable(ARG_PARAM3);
            }
            if(getArguments().containsKey(ARG_PARM4))
            {
                selLev1List=(ArrayList<String>) getArguments().getSerializable(ARG_PARM4);
            }
            if(getArguments().containsKey(ARG_PARM5))
            {
                selLev2List=(ArrayList<String>) getArguments().getSerializable(ARG_PARM5);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View incomeLev1SelectView = inflater.inflate(R.layout.frag_inc_lev2_sel_view, container, false);
        mTabLayoutIncome = (TabLayout) incomeLev1SelectView.findViewById(R.id.tab_layout_id);
        ImageView backBtn=(ImageView)incomeLev1SelectView.findViewById(R.id.backButtonId);
        FloatingActionButton  doneBtn=(FloatingActionButton)incomeLev1SelectView.findViewById(R.id.incomedetails_tick_button_level2);   RelativeLayout mleftRelativeLayout = (RelativeLayout) incomeLev1SelectView.findViewById(R.id.relative_left_arrow);
        RelativeLayout mcenterRelativeLayout = (RelativeLayout) incomeLev1SelectView.findViewById(R.id.relative_center_home);
      //  RelativeLayout mRightRelativeLayout = (RelativeLayout) incomeLev1SelectView.findViewById(R.id.relative_right_arrow);
        dialogTitle=(TextView)incomeLev1SelectView.findViewById(R.id.dialogTitleId);
        dialogTitle.setText("Expense Details");

        if(selectedList!=null)
            for (int i = 0; i < selectedList.size(); i++) {

                try {
                    mTabLayoutIncome.addTab(mTabLayoutIncome.newTab().setText("" + selectedList.get(i).getLev0_name()));
                    mTabLayoutIncome.setTabGravity(TabLayout.GRAVITY_CENTER);
                   // mTabLayoutIncome.setTabTextColors(ColorStateList.valueOf(Color.GRAY));
//            tabTitles.add("PreRetirement ");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

        mViewPager = incomeLev1SelectView.findViewById(R.id.pager);
        adapter = new Lev2IncTabAdapterExp(getChildFragmentManager(), selectedList,incomeFilterHashMap,incLev2SelLisInterface,selLev1List,selLev2List);
        mViewPager.setAdapter(adapter);
        mTabLayoutIncome.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayoutIncome));
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incLev2SelLisInterface.incomeSelectedIncomeCat();
                dismiss();

            }
        });
        mleftRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mcenterRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(i);
            }
        });
//        mRightRelativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//
//                incLev2SelLisInterface.dialogNextButtonClick();
//            }
//        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return incomeLev1SelectView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static DialogFragment newInstance(ArrayList<ExpensesLevelZeroData> selectedList, HashMap<String, ExpensesLevelZeroData> incomeFilterHashMap, IncLev2SelLisInterface incomeLev2SelectLisaner , ArrayList<String> selLev2List, ArrayList<String>selLev3List)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARM4,selLev2List);
        args.putSerializable(ARG_PARM5,selLev3List);
        args.putSerializable(ARG_PARAM2,incomeFilterHashMap);
        args.putSerializable(ARG_PARAM3,selectedList);
        LevelSecondSelectionExpDialog fragment = new LevelSecondSelectionExpDialog();
        fragment.setArguments(args);
        fragment.incLev2SelLisInterface=incomeLev2SelectLisaner;
        return fragment;
    }
}

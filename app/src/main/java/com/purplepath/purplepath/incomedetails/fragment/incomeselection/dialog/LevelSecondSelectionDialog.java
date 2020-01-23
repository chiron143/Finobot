package com.purplepath.purplepath.incomedetails.fragment.incomeselection.dialog;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.adapter.Lev2IncTabAdapter;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncLev2SelLisInterface;
import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dinesh on 26/08/16.
 */
public class LevelSecondSelectionDialog extends DialogFragment {
    String familyId;
    ArrayList<Income_cat_lev1> selectedList;
    HashMap<String, Income_cat_lev1> incomeFilterHashMap;
    IncLev2SelLisInterface incLev2SelLisInterface;
    private static String ARG_PARAM1="FamilyId";
    private static  String ARG_PARAM2="incomeCatagoryList";
    private static  String ARG_PARAM3="selectedLev1List";
    private static  String ARG_PARM4="Selected2List";
    private static String ARG_PARM5="selected3List";
    private TabLayout mTabLayoutIncome;
    private Lev2IncTabAdapter adapter;
    private ViewPager mViewPager;
    ArrayList<String> selLev2List,selLev3List;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_FRAME, R.style.MY_DIALOG);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_PARAM1)) {
                familyId = (getArguments().getString(ARG_PARAM1));
            }
            if (getArguments().containsKey(ARG_PARAM2)) {
                incomeFilterHashMap = (HashMap<String,Income_cat_lev1>)getArguments().getSerializable(ARG_PARAM2);

            }
            if(getArguments().containsKey(ARG_PARAM3))
            {
                selectedList=(ArrayList<Income_cat_lev1>)getArguments().getSerializable(ARG_PARAM3);
            }
            if(getArguments().containsKey(ARG_PARM4))
            {
                selLev2List=(ArrayList<String>) getArguments().getSerializable(ARG_PARM4);
            }
            if(getArguments().containsKey(ARG_PARM5))
            {
                selLev3List=(ArrayList<String>) getArguments().getSerializable(ARG_PARM5);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View incomeLev1SelectView = inflater.inflate(R.layout.frag_inc_lev2_sel_view, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mTabLayoutIncome = (TabLayout) incomeLev1SelectView.findViewById(R.id.tab_layout_id);
        ImageView backBtn=(ImageView)incomeLev1SelectView.findViewById(R.id.backButtonId);
        ImageView  doneBtn=(ImageView)incomeLev1SelectView.findViewById(R.id.doneButtonId);
        FloatingActionButton fab = (FloatingActionButton) incomeLev1SelectView.findViewById(R.id.incomedetails_tick_button_level2);
        RelativeLayout mleftRelativeLayout = (RelativeLayout) incomeLev1SelectView.findViewById(R.id.relative_left_arrow);
        RelativeLayout mcenterRelativeLayout = (RelativeLayout) incomeLev1SelectView.findViewById(R.id.relative_center_home);
       // RelativeLayout mRightRelativeLayout = (RelativeLayout) incomeLev1SelectView.findViewById(R.id.relative_right_arrow);


        if(selectedList!=null)
        for (int i = 0; i < selectedList.size(); i++) {

                try {
                    mTabLayoutIncome.addTab(mTabLayoutIncome.newTab().setText("" + selectedList.get(i).getLev1_name()));
                    mTabLayoutIncome.setTabGravity(TabLayout.GRAVITY_CENTER);
                  //  mTabLayoutIncome.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
//            tabTitles.add("PreRetirement ");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

        mViewPager = incomeLev1SelectView.findViewById(R.id.pager);
        adapter = new Lev2IncTabAdapter(getChildFragmentManager(), selectedList,incomeFilterHashMap,familyId,incLev2SelLisInterface,selLev2List,selLev3List);
        mViewPager.setAdapter(adapter);
        mTabLayoutIncome.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayoutIncome));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incLev2SelLisInterface.incomeSelectedIncomeCat();
                dismiss();
            }
        });
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
    public static DialogFragment newInstance(String familyId, ArrayList<Income_cat_lev1> selectedList, HashMap<String, Income_cat_lev1> incomeFilterHashMap, IncLev2SelLisInterface incomeLev2SelectLisaner ,ArrayList<String> selLev2List,ArrayList<String>selLev3List)
        {
        Bundle args = new Bundle();
            args.putSerializable(ARG_PARM4,selLev2List);
            args.putSerializable(ARG_PARM5,selLev3List);
            args.putString(ARG_PARAM1,familyId);
        args.putSerializable(ARG_PARAM2,incomeFilterHashMap);
        args.putSerializable(ARG_PARAM3,selectedList);
            LevelSecondSelectionDialog fragment = new LevelSecondSelectionDialog();
        fragment.setArguments(args);
        fragment.incLev2SelLisInterface=incomeLev2SelectLisaner;
        return fragment;
    }
}

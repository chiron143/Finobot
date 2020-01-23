package com.purplepath.purplepath.incomedetails.fragment.incomeselection.dialog;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.customview.NonExpandableListView;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.adapter.Lev2SelAdapter;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncLev2SelLisInterface;
import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev1;
import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dinesh on 29/08/16.
 */
public class LevelSecondSelectionFragment extends Fragment {
    Context mContext;
    ArrayList< Income_cat_lev2 > listDataGroup;
    NonExpandableListView navigationView;
    HashMap<String, Income_cat_lev1> incomeCatHashMap;
    Income_cat_lev1 selectedList;
    ArrayList<String> selLev2List,selLev3List;
    private static String ARG_PARAM1="FamilyId";
    private static  String ARG_PARAM2="incomeCatagoryList";
    private static  String ARG_PARAM3="selectedLev1List";
    private static  String ARG_PARM4="Selected2List";
    private static String ARG_PARM5="selected3List";
    private  IncLev2SelLisInterface incLev2SelLisInterface;
    String familyId;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_PARAM1)) {
                familyId = (getArguments().getString(ARG_PARAM1));
            }
            if (getArguments().containsKey(ARG_PARAM2)) {
                incomeCatHashMap = (HashMap<String,Income_cat_lev1>)getArguments().getSerializable(ARG_PARAM2);
            }
            if(getArguments().containsKey(ARG_PARAM3))
            {
                selectedList=(Income_cat_lev1)getArguments().getSerializable(ARG_PARAM3);
            }
            if(getArguments().containsKey(ARG_PARM4))
            {
                selLev2List=(ArrayList<String>) getArguments().getSerializable(ARG_PARM4);
            }
            if(getArguments().containsKey(ARG_PARM5))
            {
                selLev3List=(ArrayList<String>) getArguments().getSerializable(ARG_PARM5);
            }
            if(incomeCatHashMap!=null)
            {
                listDataGroup=new ArrayList<>();
                for (int i = 0; i < incomeCatHashMap.size(); i++)
                {
                    if(selectedList.getId().equalsIgnoreCase(incomeCatHashMap.get(""+i).getId()))
                    {
                        if(incomeCatHashMap.get(""+i).getCat_lev2List()!=null)
                        listDataGroup.addAll(incomeCatHashMap.get(""+i).getCat_lev2List());
                    }
                }

            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View incomeLev1SelectView = inflater.inflate(R.layout.frag_lev2_selec_tab_list_view, container, false);
        navigationView = incomeLev1SelectView.findViewById(R.id.lev2_list_view);
        navigationView.setExpanded(true);
        Lev2SelAdapter  navigationAdapter = new Lev2SelAdapter(mContext,listDataGroup,incLev2SelLisInterface,selLev2List,selLev3List);

        navigationView.setAdapter(navigationAdapter);


//        FloatingActionButton fab = (FloatingActionButton) incomeLev1SelectView.findViewById(R.id.redesign_essential_fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                //ExpensesReDesignDetailsFragment fragment = new ExpensesReDesignDetailsFragment();
////                AssetsDetailsFragment fragment = new AssetsDetailsFragment();
////                fragmentTransaction.replace(R.id.fragment_container, fragment);
////                fragmentTransaction.addToBackStack(null);
////                fragmentTransaction.commitAllowingStateLoss();
//            }
//        });


        return incomeLev1SelectView;
    }


    public static LevelSecondSelectionFragment newInstance(ArrayList< Income_cat_lev2 > listDataGroup) {
        
        Bundle args = new Bundle();
        
        LevelSecondSelectionFragment fragment = new LevelSecondSelectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(HashMap<String, Income_cat_lev1> incomeCatHashMap,
                                       Income_cat_lev1 income_cat_lev1, String familyId,
                                       IncLev2SelLisInterface incLev2SelLisInterface,
                                       ArrayList<String> selLev2List,ArrayList<String> selLev3List) {
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,familyId);
        args.putSerializable(ARG_PARAM2,incomeCatHashMap);
        args.putSerializable(ARG_PARAM3,income_cat_lev1);
        args.putSerializable(ARG_PARM4,selLev2List);
        args.putSerializable(ARG_PARM5,selLev3List);
        LevelSecondSelectionFragment fragment = new LevelSecondSelectionFragment();
        fragment.setArguments(args);
        fragment.incLev2SelLisInterface=incLev2SelLisInterface;
        return fragment;
    }
}

package com.purplepath.purplepath.incomedetails.fragment.incomeselection.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.adapter.Level1SelectionAdapter;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncSelecOnDismisInterf;
import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dinesh on 26/08/16.
 */
public class LevelOneSelectorDaialog extends DialogFragment {

    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private Context mContext;
    private String[] tabTitles = new String[]{"Essential", "Discretionary"};
    private Toolbar toolbar;
    static OnCustomEssentialTabChange onCustomTabChange;
    static OnCustomDiscretionaryTabChange onDisCustomTabChange;
    private static String ARG_PARAM1="FamilyId";
    private static  String ARG_PARAM2="incomeCatagoryList";
    private static String ARGS_PARAM3="selectedLev1";
    private String familyId;
    private ListView incSelLev1ListView;
    private HashMap<String,Income_cat_lev1> incomeFilterHashMap=new HashMap<>();
    private ArrayList<Income_cat_lev1> selectedList;
    private IncSelecOnDismisInterf incomeLev1SelectLisaner;
    private ArrayList<Income_cat_lev1> incomeLev1List;
    ArrayList<String> mSelLev1List;
    public static DialogFragment newInstance(String familyId, HashMap<String, Income_cat_lev1> incomeCatogoryHashMap, IncSelecOnDismisInterf incomeLev1SelectLisaner, ArrayList<String> selLev1List) {

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,familyId);
        args.putSerializable(ARG_PARAM2,incomeCatogoryHashMap);
        args.putStringArrayList(ARGS_PARAM3,selLev1List);
        LevelOneSelectorDaialog fragment = new LevelOneSelectorDaialog();
        fragment.setArguments(args);
        fragment.incomeLev1SelectLisaner=incomeLev1SelectLisaner;
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_FRAME, R.style.MY_DIALOG);
        selectedList =new ArrayList<>();
        mSelLev1List=new ArrayList<>();
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_PARAM1)) {
                familyId = (getArguments().getString(ARG_PARAM1));
            }
            if (getArguments().containsKey(ARG_PARAM2)) {
                incomeFilterHashMap = (HashMap<String,Income_cat_lev1>)getArguments().getSerializable(ARG_PARAM2);

            }
            if(getArguments().containsKey(ARGS_PARAM3))
            {
                mSelLev1List.addAll(getArguments().getStringArrayList(ARGS_PARAM3));
                //Log.e("Got Lev1",""+mSelLev1List.size());
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View incomeLev1SelectView = inflater.inflate(R.layout.frag_inc_lev1_sel_view, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        incSelLev1ListView=(ListView)incomeLev1SelectView.findViewById(R.id.inc_select_lev1_list_id);
        ImageView  backBtn=(ImageView)incomeLev1SelectView.findViewById(R.id.backButtonId);
        ImageView  doneBtn=(ImageView)incomeLev1SelectView.findViewById(R.id.doneButtonId);
        RelativeLayout mleftRelativeLayout = (RelativeLayout) incomeLev1SelectView.findViewById(R.id.relative_left_arrow);
        RelativeLayout mcenterRelativeLayout = (RelativeLayout) incomeLev1SelectView.findViewById(R.id.relative_center_home);
        //RelativeLayout mRightRelativeLayout = (RelativeLayout) incomeLev1SelectView.findViewById(R.id.relative_right_arrow);
        FloatingActionButton doneFab = (FloatingActionButton) incomeLev1SelectView.findViewById(R.id.incomedetails_tick_button);

        if(incomeFilterHashMap!=null)
         {
           incomeLev1List=new ArrayList<>();
           for (int i = 0; i < incomeFilterHashMap.size(); i++)
            {
               incomeLev1List.add(incomeFilterHashMap.get("" + i));
           }
             Level1SelectionAdapter adapter=new Level1SelectionAdapter(mContext, incomeLev1List);
             adapter.update(incomeLev1List,selectedList);
             incSelLev1ListView.setAdapter(adapter);
             incSelLev1ListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

             for (int j = 0; j < incomeFilterHashMap.size(); j++) {
                 if(mSelLev1List!=null)
                 for (int k = 0; k < mSelLev1List.size(); k++) {
                     if(incomeFilterHashMap.get(""+j).getId().equalsIgnoreCase(mSelLev1List.get(k)))
                     {
                         selectedList.add(incomeFilterHashMap.get("" + j));
                         incSelLev1ListView.setItemChecked(j,true);
                         //Log.e("Got Lev1",""+j+"incomeFilterHashMap"+incomeFilterHashMap.get("" + j));
                     }
                 }

             }
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
//             mRightRelativeLayout.setOnClickListener(new View.OnClickListener() {
//                 @Override
//                 public void onClick(View v) {
//                     dismiss();
//
//                     incomeLev1SelectLisaner.selectionNextButtonClick();
//                 }
//             });



               incSelLev1ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                       //Log.e("incSelLev1ListView", "Position+++++" + i);
                       //Log.e("before", "" + selectedList.toString());
                       if (selectedList.contains(incomeFilterHashMap.get("" + i))) {
                           selectedList.remove(incomeFilterHashMap.get("" + i));

                       } else {
                           selectedList.add(incomeFilterHashMap.get("" + i));

                       }
                       //Log.e("Sucess", "" + selectedList.toString());
                   }
               });



        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            dismiss();
            }
        });

        doneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    incomeLev1SelectLisaner.incomeSelectedLevOneList(selectedList);
                    dismiss();


            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!selectedList.isEmpty())
                {
                    incomeLev1SelectLisaner.incomeSelectedLevOneList(selectedList);
                    dismiss();
                }
                else
                {
                    dismiss();
                }
            }
        });



        return incomeLev1SelectView;
    }
    public static void setCustomOnEssentialTabChangeListener(OnCustomEssentialTabChange onTabChange){
        onCustomTabChange = onTabChange;
    }

    public static void setCustomOnDiscretionaryTabChangeListener(OnCustomDiscretionaryTabChange onDisTabChange){
        onDisCustomTabChange = onDisTabChange;
    }




    public interface OnCustomEssentialTabChange{
        void onEssentialTabChangeCallService();
    }

    public interface OnCustomDiscretionaryTabChange{
        void onDiscretionaryTabChangeCallService();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }
}

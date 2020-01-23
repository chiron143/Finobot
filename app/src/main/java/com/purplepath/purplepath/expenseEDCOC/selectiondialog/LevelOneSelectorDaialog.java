package com.purplepath.purplepath.expenseEDCOC.selectiondialog;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelZeroData;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncSelecOnDismisInterf;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dinesh on 01/06/17.
 */

public class LevelOneSelectorDaialog extends DialogFragment {

    private Context mContext;

    static OnCustomEssentialTabChange onCustomTabChange;
    static OnCustomDiscretionaryTabChange onDisCustomTabChange;
    private static  String ARG_PARAM2="incomeCatagoryList";
    private static String ARGS_PARAM3="selectedLev1";
    private ListView incSelLev1ListView;
    private HashMap<String,ExpensesLevelZeroData> incomeFilterHashMap=new HashMap<>();
    private ArrayList<ExpensesLevelZeroData> selectedList;
    private IncSelecOnDismisInterf incomeLev1SelectLisaner;
    private ArrayList<ExpensesLevelZeroData> incomeLev1List;
    ArrayList<String> mSelLev0List;
    private TextView dialogTitle;
    private FloatingActionButton doneBtn;
    public static DialogFragment newInstance(HashMap<String, ExpensesLevelZeroData> incomeCatogoryHashMap, IncSelecOnDismisInterf incomeLev1SelectLisaner, ArrayList<String> selLev1List) {

        Bundle args = new Bundle();

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

        mSelLev0List=new ArrayList<>();
        if (getArguments() != null) {

            if (getArguments().containsKey(ARG_PARAM2)) {
                incomeFilterHashMap = (HashMap<String,ExpensesLevelZeroData>)getArguments().getSerializable(ARG_PARAM2);

            }
            if(getArguments().containsKey(ARGS_PARAM3))
            {
                mSelLev0List.addAll(getArguments().getStringArrayList(ARGS_PARAM3));
                //Log.e("Got Lev1",""+mSelLev0List.size());
            }
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        selectedList =new ArrayList<>();
        View incomeLev1SelectView = inflater.inflate(R.layout.frag_inc_lev1_sel_view, container, false);
        incSelLev1ListView=(ListView)incomeLev1SelectView.findViewById(R.id.inc_select_lev1_list_id);
        ImageView backBtn=(ImageView)incomeLev1SelectView.findViewById(R.id.backButtonId);
         doneBtn=(FloatingActionButton)incomeLev1SelectView.findViewById(R.id.incomedetails_tick_button);
         dialogTitle=(TextView)incomeLev1SelectView.findViewById(R.id.dialogTitleId);
        dialogTitle.setText("Expense Details");
        RelativeLayout mleftRelativeLayout = (RelativeLayout) incomeLev1SelectView.findViewById(R.id.relative_left_arrow);
        RelativeLayout mcenterRelativeLayout = (RelativeLayout) incomeLev1SelectView.findViewById(R.id.relative_center_home);
       // RelativeLayout mRightRelativeLayout = (RelativeLayout) incomeLev1SelectView.findViewById(R.id.relative_right_arrow);

        if(incomeFilterHashMap!=null)
        {
            incomeLev1List=new ArrayList<>();
            for (int i = 0; i < incomeFilterHashMap.size(); i++)
            {
                incomeLev1List.add(incomeFilterHashMap.get("" + i));
            }
            com.purplepath.purplepath.expenseEDCOC.selectiondialog.adapter.Level1SelectionAdapter adapter=new com.purplepath.purplepath.expenseEDCOC.selectiondialog.adapter.Level1SelectionAdapter(mContext, incomeLev1List);
            adapter.update(incomeLev1List,mSelLev0List);
            incSelLev1ListView.setAdapter(adapter);
            incSelLev1ListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            for (int j = 0; j < incomeFilterHashMap.size(); j++) {
                if(mSelLev0List!=null)
                    for (int k = 0; k < mSelLev0List.size(); k++) {
                        if(incomeFilterHashMap.get(""+j).getId().equalsIgnoreCase(mSelLev0List.get(k).replace(" ","")))
                        {
                            selectedList.add(incomeFilterHashMap.get("" + j));
                            incSelLev1ListView.setItemChecked(j,true);
                            //Log.e("Got Lev1 Selected",""+j+"incomeFilterHashMap"+incomeFilterHashMap.get("" + j));
                        }
                    }

            }
            mleftRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incomeLev1SelectLisaner.selectionNextButtonClick();
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
//            mRightRelativeLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    incomeLev1SelectLisaner.selectionNextButtonClick();
//                    dismiss();
//
//
//                }
//            });



            incSelLev1ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Log.e("incSelLev1ListView", "Position+++++" + i);
                    //Log.e("before", "" + selectedList.toString());
                    if (mSelLev0List.contains(incomeFilterHashMap.get("" + i).getId())) {
                        selectedList.remove(incomeFilterHashMap.get("" + i));
                        mSelLev0List.remove(incomeFilterHashMap.get("" + i).getId());

                    } else {
                        selectedList.add(incomeFilterHashMap.get("" + i));
                        mSelLev0List.add(incomeFilterHashMap.get("" + i).getId());

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
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    incomeLev1SelectLisaner.expenseSelectedLevOneList(selectedList);
                    dismiss();
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

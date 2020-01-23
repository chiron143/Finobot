package com.purplepath.purplepath.expenseEDCOC.selectiondialog.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelZeroData;

import java.util.ArrayList;

/**
 * Created by dinesh on 01/06/17.
 */

public class Level1SelectionAdapter extends ArrayAdapter<ExpensesLevelZeroData> {
    private final Context context;
    private  ArrayList<ExpensesLevelZeroData> settingsList;
    private ArrayList<String>selLev1List;
    private int settingsItemsCount = 0;

    public Level1SelectionAdapter(Context context, ArrayList<ExpensesLevelZeroData> incomeLev1List) {
        super(context, 0);
        this.context = context;
        this.settingsList = new ArrayList<>();
        this.selLev1List=new ArrayList<>();

    }

    public void update(ArrayList<ExpensesLevelZeroData> incomeLev1List, ArrayList<String> selLev1List)
    {
        this.settingsList =new ArrayList<>();
        this.settingsList.addAll(incomeLev1List);
        this.selLev1List=selLev1List;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(settingsList!=null)
            return  settingsList.size();
        else
            return 0;
    }

    @Override
    public ExpensesLevelZeroData getItem(int position) {
        // TODO Auto-generated method stub
        return settingsList.get(position);
    }

    @Override
    public View getView(final int position, View rowView, ViewGroup parent) {
        ViewHolder holder = null;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(android.R.layout.simple_list_item_multiple_choice, null);
            holder = new ViewHolder();
            holder.setTxtTitle(  (CheckedTextView) rowView
                    .findViewById(android.R.id.text1));

//            holder.getTxtTitle().setSelected(false);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.getTxtTitle().setText(settingsList.get(position).getLev0_name());
        if(!selLev1List.isEmpty())
            if(selLev1List.contains(settingsList.get(position).getId())) {
                holder.getTxtTitle().setChecked(true);
//                holder.getTxtTitle().setSelected(true);
                //Log.e("Sucess","Truee"+position);
            }


        return rowView;
    }


    static class ViewHolder {
        CheckedTextView txtTitle;

        public CheckedTextView getTxtTitle() {
            return txtTitle;
        }

        public void setTxtTitle(CheckedTextView txtTitle) {
            this.txtTitle = txtTitle;
        }
    }


}

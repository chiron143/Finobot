package com.purplepath.purplepath.expenseEDCOC.selectiondialog.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelOneData;
import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelTwoData;
import com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface.IncLev2SelLisInterface;
import com.purplepath.purplepath.myinterface.OnCategoriesSelectedItemListener;

import java.util.ArrayList;

/**
 * Created by dinesh on 01/06/17.
 */

public class Lev2ExpSelAdapter extends BaseExpandableListAdapter {


    private Context mContext;

    /*
     * Here we have a Hashmap containing a String key
     * (can be Integer or other type but I was testing
     * with contacts so I used contact name as the key)
    */
//    private HashMap<String, ArrayList<Income_cat_lev2>> mListDataChild;

    // ArrayList that is what each key in the above
    // hashmap points to
    private ArrayList<ExpensesLevelOneData> mListDataGroup;
    private ArrayList<String> mSelectLev2List;
    private ArrayList<String> mSelectLev3List;

    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;

    /*
          *  For the purpose of this document, I'm only using a single
     *	textview in the group (parent) and child, but you're limited only
     *	by your XML view for each group item :)
    */
    private String groupText;
    private String childText;

    /*  Here's the constructor we'll use to pass in our calling
     *  activity's context, group items, and child items
    */

//    private ArrayList<ExpensesLevelOneData> mEssentListCheckedLevelOneDataGroup = new ArrayList<ExpensesLevelOneData>();
//    public static ArrayList<Expense_cat_lev1> mEssentListCheckedLevelTwoDataGroup = new ArrayList<Expense_cat_lev1>();

    //    private ArrayList<ExpensesLevelOneData> mDiscretListCheckedLevelOneDataGroup = new ArrayList<ExpensesLevelOneData>();
//    public static  ArrayList<Expense_cat_lev1> mDiscretListCheckedLevelTwoDataGroup = new ArrayList<Expense_cat_lev1>();
//    public ArrayList<String> essentialmIds = new ArrayList<String>();
//    public ArrayList<String> discretionarymIds = new ArrayList<String>();
//    private ArrayList<String> mselectedData = new ArrayList<String>();
//    private String mExpensestype;
    private OnCategoriesSelectedItemListener onCategoriesSelectedItemListener;
    /*
     *    Interface after adding
     * */
    private IncLev2SelLisInterface incLev2SelLisInterface;

    public Lev2ExpSelAdapter(Context context,
                             ArrayList<ExpensesLevelOneData> listDataGroup, IncLev2SelLisInterface incLev2SelLisInterface, ArrayList<String> mSelectLev2List, ArrayList<String> mSelectLev3List) {

        mContext = context;
        mListDataGroup = listDataGroup;
        this.incLev2SelLisInterface = incLev2SelLisInterface;
        this.mSelectLev2List = mSelectLev2List;
        this.mSelectLev3List = mSelectLev3List;
//        mListDataChild = listDataChild;
//        mExpensestype = type;
//        onCategoriesSelectedItemListener = onCategoriesSelectedItem;
        // Initialize our hashmap containing our check states here
//        mChildCheckStates = new HashMap<Integer, boolean[]>();
    }

    @Override
    public int getGroupCount() {
        return mListDataGroup.size();
    }

    /*
     * This defaults to "public object getGroup" if you auto import the methods
     * I've always make a point to change it from "object" to whatever item
     * I passed through the constructor
    */
    @Override
    public ExpensesLevelOneData getGroup(int groupPosition) {
        return mListDataGroup.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        //  I passed a text string into an activity holding a getter/setter
        //  which I passed in through "ExpListGroupItems".
        //  Here is where I call the getter to get that text
        groupText = getGroup(groupPosition).getLev1_name();

//        if (convertView == null) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.frag_inc_sel_group_view, null);

        // Initialize the GroupViewHolder defined at the bottom of this document
        groupViewHolder = new GroupViewHolder();

        groupViewHolder.mGroupText = convertView.findViewById(R.id.childTextView);
        groupViewHolder.mCheckBox = convertView.findViewById(R.id.checkBox);
        groupViewHolder.mGroupImgView = convertView.findViewById(R.id.groupImgViewId);
        groupViewHolder.mCheckBox.setTag(groupPosition);
//            convertView.setTag(groupViewHolder);
//        } else {
//
//            groupViewHolder = (GroupViewHolder) convertView.getTag();
//        }
        if (getChildrenCount(groupPosition) == 0) {
            groupViewHolder.mCheckBox.setVisibility(View.VISIBLE);
            groupViewHolder.mGroupImgView.setVisibility(View.INVISIBLE);
        } else {
            groupViewHolder.mCheckBox.setVisibility(View.INVISIBLE);
            groupViewHolder.mGroupImgView.setVisibility(View.VISIBLE);

        }

        groupViewHolder.mGroupText.setText(groupText);
        if (isExpanded)
        {
            groupViewHolder.mGroupImgView.setImageResource(R.drawable.ic_down_arrow);

        }
        else
        {
            groupViewHolder.mGroupImgView.setImageResource(R.drawable.ic_up_arrow);
        }
        if (mSelectLev2List.contains(getGroup(groupPosition).getId()))
        {
            if( groupViewHolder.mCheckBox.getTag().equals(groupPosition)&& !groupViewHolder.mCheckBox.isChecked())
                groupViewHolder.mCheckBox.setChecked(true);
        }
        groupViewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                if (!mSelectLev2List.contains(getGroup(groupPosition).getId()))
                {
                    mSelectLev2List.add(getGroup(groupPosition).getId());
                    //Log.e("setIncomeLev2SelnLi", "add***_______*****" + mSelectLev2List.toString());
                    // sending data to income Tab view via Listener
                    incLev2SelLisInterface.incomeSelectedLev3List(mSelectLev3List);
                } else {
                    mSelectLev2List.remove(getGroup(groupPosition).getId());
                    //Log.e("setIncomeLev2SelnLi", "remove***_______*****" + mSelectLev2List.toString());
                    incLev2SelLisInterface.incomeSelectedLev3List(mSelectLev3List);
                }
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mListDataGroup.get(groupPosition).getExpensesLevelTwoData() != null)
            return mListDataGroup.get(groupPosition).getExpensesLevelTwoData().size();
        else
            return 0;

    }

    /*
     * This defaults to "public object getChild" if you auto import the methods
     * I've always make a point to change it from "object" to whatever item
     * I passed through the constructor
    */
    @Override
    public ExpensesLevelTwoData getChild(int groupPosition, int childPosition) {
        if (mListDataGroup.get(groupPosition).getExpensesLevelTwoData().get(childPosition) != null)
            return mListDataGroup.get(groupPosition).getExpensesLevelTwoData().get(childPosition);
        else
            return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        childText = getChild(groupPosition, childPosition).getLev2_name();

//        if (convertView == null) {
        childViewHolder = new ChildViewHolder();
        LayoutInflater inflater = (LayoutInflater) this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.inc_sel_child_itm, null);


        childViewHolder.setmChildText((CustomTextView) convertView
                .findViewById(R.id.childTextView));
        childViewHolder.setmCheckBox((CheckBox) convertView
                .findViewById(R.id.checkBox));
        childViewHolder.getmCheckBox().setTag(childPosition);
//            convertView.setTag(R.layout.child_item, childViewHolder);


//        } else {
//
//            childViewHolder = (ChildViewHolder) convertView
//                    .getTag(R.layout.child_item);
//        }

        childViewHolder.getmChildText().setText(childText);
        /**
         *  set check box true for previusly selected catagorys
         */

        if (mSelectLev3List.contains(getChild(groupPosition, childPosition).getId())) {
            if (childViewHolder.getmCheckBox().getTag().equals(childPosition) && !childViewHolder.getmCheckBox().isChecked())
                childViewHolder.getmCheckBox().setChecked(true);
        }

        /**
         *  add or remove  selected item in array via incLev2SelLisInterface to IncomeDynamicDetail
         */
        childViewHolder.getmCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    if (!mSelectLev3List.contains(getChild(groupPosition, childPosition).getId())) {
                        mSelectLev3List.add(getChild(groupPosition, childPosition).getId());
                        //Log.e("setIncomeLev3SelnLi", "add***_______*****" + mSelectLev3List.toString() + getChild(groupPosition, childPosition).getLev2_name());
                        incLev2SelLisInterface.incomeSelectedLev3List(mSelectLev3List);
                        if(!mSelectLev2List.contains(getChild(groupPosition, childPosition).getLev1_id()))
                        {
                            mSelectLev2List.add(getChild(groupPosition, childPosition).getLev1_id());
                            incLev2SelLisInterface.incomeSelectedLev2List(mSelectLev2List);
                        }
                    }

                } else {
                    mSelectLev3List.remove(getChild(groupPosition, childPosition).getId());
                    //Log.e("setIncomeLev3SelnLi", "Remove***_______*****" + mSelectLev3List.toString());
                    incLev2SelLisInterface.incomeSelectedLev3List(mSelectLev3List);
                }
//
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public final class GroupViewHolder {

        CustomTextView mGroupText;
        CheckBox mCheckBox;
        ImageView mGroupImgView;
    }

    public final class ChildViewHolder {

        CustomTextView mChildText;

        public CustomTextView getmChildText() {
            return mChildText;
        }

        public void setmChildText(CustomTextView mChildText) {
            this.mChildText = mChildText;
        }

        CheckBox mCheckBox;

        public CheckBox getmCheckBox() {
            return mCheckBox;
        }

        public void setmCheckBox(CheckBox mCheckBox) {
            this.mCheckBox = mCheckBox;
        }
    }


}
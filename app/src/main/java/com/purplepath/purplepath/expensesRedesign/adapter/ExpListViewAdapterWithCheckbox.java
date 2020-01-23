//package com.purplepath.purplepath.expensesRedesign.adapter;
//
///**
// * Created by Bert on 19-Aug-16.
// */
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelOneData;
//import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelTwoData;
//import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.User_expense;
//import com.purplepath.purplepath.myinterface.OnCategoriesSelectedItemListener;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.HashSet;
//
//// Eclipse wanted me to use a sparse array instead of my hashmaps, I just suppressed that suggestion
//@SuppressLint("UseSparseArrays")
//public class ExpListViewAdapterWithCheckbox extends BaseExpandableListAdapter {
//
//    // Define activity context
//    private Context mContext;
//
//
//    private HashMap<String, ArrayList<ExpensesLevelTwoData>> mListDataChild;
//
//    // ArrayList that is what each key in the above
//    // hashmap points to
//    private ArrayList<ExpensesLevelOneData> mListDataGroup;
//
//    private HashMap<Integer, boolean[]> mChildCheckStates;
//    private HashMap<Integer, Boolean> mGroupCheckStates;
//    // Our getChildView & getGroupView use the viewholder patter
//    // Here are the viewholders defined, the inner classes are
//    // at the bottom
//    private ChildViewHolder childViewHolder;
//    private GroupViewHolder groupViewHolder;
//
//    /*
//          *  For the purpose of this document, I'm only using a single
//     *	textview in the group (parent) and child, but you're limited only
//     *	by your XML view for each group item :)
//    */
//    private String groupText;
//    private String childText;
//
//    /*  Here's the constructor we'll use to pass in our calling
//     *  activity's context, group items, and child items
//    */
//
//    private ArrayList<ExpensesLevelOneData> mEssentListCheckedLevelOneDataGroup = new ArrayList<ExpensesLevelOneData>();
//    public  ArrayList<ExpensesLevelTwoData> mEssentListCheckedLevelTwoDataGroup = new ArrayList<ExpensesLevelTwoData>();
//
//    private ArrayList<ExpensesLevelOneData> mDiscretListCheckedLevelOneDataGroup = new ArrayList<ExpensesLevelOneData>();
//    public  ArrayList<ExpensesLevelTwoData> mDiscretListCheckedLevelTwoDataGroup = new ArrayList<ExpensesLevelTwoData>();
//    public ArrayList<String> essentialmIds = new ArrayList<String>();
//    public ArrayList<String> discretionarymIds = new ArrayList<String>();
//    private ArrayList<String> mselectedData = new ArrayList<String>();
//    private String mExpensestype;
//    private OnCategoriesSelectedItemListener onCategoriesSelectedItemListener;
//    private ArrayList<User_expense> user_expenses;
//    private HashSet<String> mSelectLev2List;
//    public ArrayList<String> mChooseLev1 = new ArrayList<String>();
//    private ArrayList<String> mChooseLev2 = new ArrayList<String>();
//    public ExpListViewAdapterWithCheckbox(Context context,
//                                          ArrayList<ExpensesLevelOneData> listDataGroup, HashMap<String,
//            ArrayList<ExpensesLevelTwoData>> listDataChild, String type,
//                                          OnCategoriesSelectedItemListener onCategoriesSelectedItem) {
//
//        mContext = context;
//        mListDataGroup = listDataGroup;
//        mListDataChild = listDataChild;
//        mExpensestype = type;
//        mSelectLev2List=new HashSet<>();
//        mChooseLev1=new ArrayList<>();
//        mChooseLev2=new ArrayList<>();
//        onCategoriesSelectedItemListener = onCategoriesSelectedItem;
//        if(HomePageActivity.getExpensesDetailsModel != null) {
//            if (HomePageActivity.getExpensesDetailsModel.getData() != null) {
//                if (HomePageActivity.getExpensesDetailsModel.getData().getUser_expense() != null) {
//                    if (!HomePageActivity.getExpensesDetailsModel.getData().getUser_expense().isEmpty()) {
//                        if(HomePageActivity.getExpensesDetailsModel.getData().getUser_expense().get(0).getLevel_1_ids()!=null)
//                        mChooseLev1=  new ArrayList<>(Arrays.asList(HomePageActivity.getExpensesDetailsModel.getData().getUser_expense().get(0).getLevel_1_ids().split(",")));
//                        if(HomePageActivity.getExpensesDetailsModel.getData().getUser_expense().get(0).getLevel_2_ids()!=null)
//                        mChooseLev2= new ArrayList<>(Arrays.asList(HomePageActivity.getExpensesDetailsModel.getData().getUser_expense().get(0).getLevel_2_ids().split(",")));
//                        user_expenses = HomePageActivity.getExpensesDetailsModel.getData().getUser_expense();
//                    }
//                }
//            }
//        }
//
//        for (ExpensesLevelOneData parentObj : mListDataGroup) {
//
//            if (mChooseLev1.size() > 0) {
//                if (mChooseLev1.contains(parentObj.getId())) {
//                    onCategoriesSelectedItemListener.onSelectedItem(parentObj.getId(), "add", "levelone", parentObj.getId(), user_expenses);
//                    try {
//                        for (int i = 0; i < listDataChild.get(parentObj.getLev1_name()).size(); i++) {
//                            if (mChooseLev2.contains(listDataChild.get(parentObj.getLev1_name()).get(i).getId()))
//                                onCategoriesSelectedItemListener.onSelectedItem(listDataChild.get(parentObj.getLev1_name()).get(i).getId(), "add", "leveltwo", listDataChild.get(parentObj.getLev1_name()).get(i).getLev1_id(), user_expenses);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//
//
//        }
////        // Initialize our hashmap containing our check states here
////        if(mChooseLev1.size()>0)
////        {
////            for(String id : mChooseLev1) {
////                onCategoriesSelectedItemListener.onSelectedItem(id, "add", "levelone", id, user_expenses);
////            }
////        }
////        if(mChooseLev2.size()>0)
////        {
////            for(String id : mChooseLev2)
////
////                onCategoriesSelectedItemListener.onSelectedItem(id, "add", "levelone",id, user_expenses);
////        }
//        mChildCheckStates = new HashMap<Integer, boolean[]>();
//        mGroupCheckStates = new HashMap<Integer, Boolean>();
//    }
//
//    @Override
//    public int getGroupCount() {
//        return mListDataGroup.size();
//    }
//
//    /*
//     * This defaults to "public object getGroup" if you auto import the methods
//     * I've always make a point to change it from "object" to whatever item
//     * I passed through the constructor
//    */
//    @Override
//    public ExpensesLevelOneData getGroup(int groupPosition) {
//        return mListDataGroup.get(groupPosition);
//    }
//
//    @Override
//    public long getGroupId(int groupPosition) {
//        return groupPosition;
//    }
//
//    @Override
//    public View getGroupView(int groupPosition, boolean isExpanded,
//                             View convertView, ViewGroup parent) {
//        final int mGroupPos= groupPosition;
//        //Log.e("mGroupPos",""+mGroupPos);
//        //  I passed a text string into an activity holding a getter/setter
//        //  which I passed in through "ExpListGroupItems".
//        //  Here is where I call the getter to get that text
//        groupText = getGroup(groupPosition).getLev1_name();
//
////        if (convertView == null) {
//
//            LayoutInflater inflater = (LayoutInflater) mContext
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.group_item, null);
//
//            // Initialize the GroupViewHolder defined at the bottom of this document
//            groupViewHolder = new GroupViewHolder();
//
//            groupViewHolder.mGroupText = (TextView) convertView.findViewById(R.id.groupTextView);
//            groupViewHolder.mGroupcheckBox = (CheckBox)  convertView.findViewById(R.id.groupcheckBox);
//            groupViewHolder.mGroupImgView = (ImageView) convertView.findViewById(R.id.groupImgViewId);
//            groupViewHolder.mGroupcheckBox.setTag(groupPosition);
//            convertView.setTag(groupViewHolder);
////        } else {
////
////            groupViewHolder = (GroupViewHolder) convertView.getTag();
////        }
//        if (getChildrenCount(groupPosition) == 0) {
//            groupViewHolder.mGroupcheckBox.setVisibility(View.VISIBLE);
//            groupViewHolder.mGroupImgView.setVisibility(View.INVISIBLE);
//        } else {
//            groupViewHolder.mGroupcheckBox.setVisibility(View.INVISIBLE);
//            groupViewHolder.mGroupImgView.setVisibility(View.VISIBLE);
//
//        }
//        if (isExpanded)
//        {
//            groupViewHolder.mGroupImgView.setImageResource(R.drawable.ic_down_arrow);
//
//
//        }
//        else
//        {
//            groupViewHolder.mGroupImgView.setImageResource(R.drawable.ic_up_arrow);
//        }
//        groupViewHolder.mGroupcheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                ExpensesLevelOneData expensesLevelOneData = getGroup(mGroupPos);
//                if (isChecked) {
//
//                    mChooseLev1.add(expensesLevelOneData.getId());
//                    if(mExpensestype.equalsIgnoreCase("Essential")) {
//                        onCategoriesSelectedItemListener.onSelectedItem(expensesLevelOneData.getId(), "add", "levelone",expensesLevelOneData.getId(), user_expenses);
//                        //Log.e("Essentialadd",""+expensesLevelOneData.getId());
//                    }else if(mExpensestype.equalsIgnoreCase("Discretionary")){
//                        //Log.e("Discretionaryadd",""+expensesLevelOneData.getId());
//                        onCategoriesSelectedItemListener.onSelectedItem(expensesLevelOneData.getId(), "add", "levelone",expensesLevelOneData.getId(), user_expenses);
//
//                    }
//                } else {
//
////                    mGroupCheckStates.put(mGroupPos, isChecked);
//                    mChooseLev1.remove(expensesLevelOneData.getId());
//                    if(mExpensestype.equalsIgnoreCase("Essential")) {
//                        onCategoriesSelectedItemListener.onSelectedItem(expensesLevelOneData.getId(), "remove", "levelone",expensesLevelOneData.getId(), user_expenses);
//                        //Log.e("Essentialremove",""+expensesLevelOneData.getId());
//                    }else if(mExpensestype.equalsIgnoreCase("Discretionary")){
//                        //Log.e("Discretionaryremove",""+expensesLevelOneData.getId());
//                        onCategoriesSelectedItemListener.onSelectedItem(expensesLevelOneData.getId(), "remove", "levelone",expensesLevelOneData.getId(), user_expenses);
//
//                    }
//                }
//            }
//        });
//
//
//
////        if (user_expenses != null) {
////            if (!user_expenses.get(0).getExpense_cat_lev1().isEmpty()) {
////                for (int i = 0; i < user_expenses.get(0).getExpense_cat_lev1().size(); i++) {
////                    int id = Integer.parseInt(user_expenses.get(0).getExpense_cat_lev1().get(i).getId());
////                 ExpensesLevelOneData expensesLevelOneData =    getGroup(groupPosition);
////                   int levelid = Integer.parseInt(expensesLevelOneData.getId());
////                    if (id == levelid) {
////
////
////                    }
////                }
////            }
////        }
//        ExpensesLevelOneData expensesLevelOneData =    getGroup(groupPosition);
//
//        if(mChooseLev1.contains(expensesLevelOneData.getId()))
//        {
////            groupViewHolder.mGroupcheckBox.setChecked(true);
////            //Log.e("Groupaddingeachtime",""+expensesLevelOneData.getLev1_name());
//            if( groupViewHolder.mGroupcheckBox.getTag().equals(groupPosition)&& !groupViewHolder.mGroupcheckBox.isChecked())
//                groupViewHolder.mGroupcheckBox.setChecked(true);
////            android.widget.ExpandableListView mExpandableListView = (android.widget.ExpandableListView) parent;
////            mExpandableListView.expandGroup(groupPosition);
//        }
//
//       /* if (mGroupCheckStates.containsKey(mGroupPos)) {
//			*//*
//			 * if the hashmap mChildCheckStates<Integer, Boolean[]> contains
//			 * the value of the parent view (group) of this child (aka, the key),
//			 * then retrive the boolean array getChecked[]
//			*//*
//            Boolean getChecked = mGroupCheckStates.get(mGroupPos);
//
//            // set the check state of this position's checkbox based on the
//            // boolean value of getChecked[position]
//            groupViewHolder.mGroupcheckBox.setChecked(getChecked);
////            android.widget.ExpandableListView mExpandableListView = (android.widget.ExpandableListView) parent;
////            mExpandableListView.expandGroup(groupPosition);
//        } else {
//
//			*//*
//			 * if the hashmap mChildCheckStates<Integer, Boolean[]> does not
//			 * contain the value of the parent view (group) of this child (aka, the key),
//			 * (aka, the key), then initialize getChecked[] as a new boolean array
//			 *  and set it's size to the total number of children associated with
//			 *  the parent group
//			*//*
//            Boolean getChecked = false;
//
//            // add getChecked[] to the mChildCheckStates hashmap using mGroupPosition as the key
//            mGroupCheckStates.put(mGroupPos, getChecked);
//
//            // set the check state of this position's checkbox based on the
//            // boolean value of getChecked[position]
//            groupViewHolder.mGroupcheckBox.setChecked(false);
//        }*/
//
//        groupViewHolder.mGroupText.setText(groupText);
//
//        return convertView;
//    }
//
//    @Override
//    public int getChildrenCount(int groupPosition) {
//        return mListDataChild.get(mListDataGroup.get(groupPosition).getLev1_name()).size();
//    }
//
//    /*
//     * This defaults to "public object getChild" if you auto import the methods
//     * I've always make a point to change it from "object" to whatever item
//     * I passed through the constructor
//    */
//    @Override
//    public ExpensesLevelTwoData getChild(int groupPosition, int childPosition) {
//        return mListDataChild.get(mListDataGroup.get(groupPosition).getLev1_name()).get(childPosition);
//    }
//
//    @Override
//    public long getChildId(int groupPosition, int childPosition) {
//        return childPosition;
//    }
//
//    @Override
//    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//
//        final int mGroupPosition = groupPosition;
//        final int mChildPosition = childPosition;
//
//        //  I passed a text string into an activity holding a getter/setter
//        //  which I passed in through "ExpListChildItems".
//        //  Here is where I call the getter to get that text
//        childText = getChild(mGroupPosition, mChildPosition).getLev2_name();
//
////        if (convertView == null) {
//
//            LayoutInflater inflater = (LayoutInflater) this.mContext
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.expenses_child_item, null);
//
//            childViewHolder = new ChildViewHolder();
//
//            childViewHolder.mChildText = (TextView) convertView
//                    .findViewById(R.id.childTextView);
//            childViewHolder.mCheckBox = (CheckBox) convertView
//                    .findViewById(R.id.checkBox);
//
//            convertView.setTag(childViewHolder);
//        childViewHolder.mCheckBox.setTag(""+childPosition);
//
////        } else {
////
////            childViewHolder = (ChildViewHolder) convertView.getTag();
////        }
//
//        childViewHolder.mChildText.setText(childText);
//
//		/*
//		 * You have to set the onCheckChangedListener to null
//		 * before restoring check states because each call to
//		 * "setChecked" is accompanied by a call to the
//		 * onCheckChangedListener
//		*/
//        childViewHolder.mCheckBox.setOnCheckedChangeListener(null);
//try {
//    if (user_expenses != null) {
//        if (!user_expenses.get(0).getExpense_cat_lev2().isEmpty()) {
//            for (int i = 0; i < user_expenses.get(0).getExpense_cat_lev2().size(); i++) {
//                String id = user_expenses.get(0).getExpense_cat_lev2().get(i).getId();
//                if (id.equalsIgnoreCase(getChild(mGroupPosition, mChildPosition).getId())) {
////                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
////                    getChecked[mChildPosition] = true;
////                    mChildCheckStates.put(mGroupPosition, getChecked);
//                    mSelectLev2List.add(user_expenses.get(0).getExpense_cat_lev2().get(i).getId());
//                    childViewHolder.mCheckBox.setChecked(true);
//                    onCategoriesSelectedItemListener.onSelectedItem(user_expenses.get(0).getExpense_cat_lev2().get(i).getId(), "add", "leveltwo",user_expenses.get(0).getExpense_cat_lev2().get(i).getLev1_id(), user_expenses);
//                }
//            }
//        }
//    }
//}catch (ArrayIndexOutOfBoundsException e) {
//    e.printStackTrace();
//}
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    /*    if (mChildCheckStates.containsKey(mGroupPosition)) {
//			*//*
//			 * if the hashmap mChildCheckStates<Integer, Boolean[]> contains
//			 * the value of the parent view (group) of this child (aka, the key),
//			 * then retrive the boolean array getChecked[]
//			*//*
//            boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
//
//            // set the check state of this position's checkbox based on the
//            // boolean value of getChecked[position]
////            childViewHolder.mCheckBox.setChecked(getChecked[mChildPosition]);
//
//        } else {
//
//			*//*
//			 * if the hashmap mChildCheckStates<Integer, Boolean[]> does not
//			 * contain the value of the parent view (group) of this child (aka, the key),
//			 * (aka, the key), then initialize getChecked[] as a new boolean array
//			 *  and set it's size to the total number of children associated with
//			 *  the parent group
//			*//*
//            boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];
//
//            // add getChecked[] to the mChildCheckStates hashmap using mGroupPosition as the key
//            mChildCheckStates.put(mGroupPosition, getChecked);
//
//            // set the check state of this position's checkbox based on the
//            // boolean value of getChecked[position]
////            childViewHolder.mCheckBox.setChecked(false);
//        }*/
//
//        childViewHolder.mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                ExpensesLevelOneData expensesLevelOneData = getGroup(mGroupPosition);
//                if (isChecked) {
////                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
////                    getChecked[mChildPosition] = isChecked;
////                    mChildCheckStates.put(mGroupPosition, getChecked);
////
////                    String name = getChild(mGroupPosition, mChildPosition).getLev2_name();
////                    mGroupCheckStates.put(mGroupPosition, isChecked);
//
//                    childViewHolder.mCheckBox.setTag("true");
//                    ExpensesLevelTwoData expensesLevelTwoData = getChild(mGroupPosition, mChildPosition);
//                    mselectedData.add(expensesLevelTwoData.getId());
//                    mSelectLev2List.add(expensesLevelTwoData.getId());
//                    if(mExpensestype.equalsIgnoreCase("Essential")) {
//                        mEssentListCheckedLevelTwoDataGroup.add(expensesLevelTwoData);
//                        essentialmIds.add(expensesLevelTwoData.getId());
//                        onCategoriesSelectedItemListener.onSelectedItem(expensesLevelTwoData.getLev1_id(), "add", "levelone",expensesLevelTwoData.getLev1_id(), user_expenses);
//                        //Log.e("Essentialadd LevelOne ",""+expensesLevelOneData.getLev1_name());
//                        //Log.e("Essentialadd LevelOne ",""+expensesLevelTwoData.getLev2_name());
//                        onCategoriesSelectedItemListener.onSelectedItem(expensesLevelTwoData.getId(), "add", "leveltwo",expensesLevelTwoData.getLev1_id(), user_expenses);
//
//                    }else if(mExpensestype.equalsIgnoreCase("Discretionary")){
//                        mDiscretListCheckedLevelTwoDataGroup.add(expensesLevelTwoData);
//                        discretionarymIds.add(expensesLevelTwoData.getId());
//                        //Log.e("DiscrearyaddLevelOne",""+expensesLevelOneData.getLev1_name());
//                        //Log.e("Essentialadd LevelOne ",""+expensesLevelTwoData.getLev2_name());
//                        onCategoriesSelectedItemListener.onSelectedItem(expensesLevelOneData.getId(), "add", "levelone",expensesLevelTwoData.getLev1_id(), user_expenses);
//                        onCategoriesSelectedItemListener.onSelectedItem(expensesLevelTwoData.getId(), "add", "leveltwo",expensesLevelTwoData.getLev1_id(), user_expenses);
//
//                    }
//
//
//
////                    Gson gson = new Gson();
////                    String json = gson.toJson(expensesLevelTwoData);
////                    ExpensesSelectionDetailsEssentialFragment.persistingAnonymePref(name, );
//
//                } else {
////                    boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
////                    getChecked[mChildPosition] = isChecked;
////                    mGroupCheckStates.put(mGroupPosition, isChecked);
////                    mChildCheckStates.put(mGroupPosition, getChecked);
//                    String name = getChild(mGroupPosition, mChildPosition).getLev2_name();
//                    ExpensesLevelTwoData expensesLevelTwoData = getChild(mGroupPosition, mChildPosition);
//                    mSelectLev2List.remove(expensesLevelTwoData.getId());
//                    childViewHolder.mCheckBox.setTag("false");
//                    if(mExpensestype.equalsIgnoreCase("Essential")) {
////                        if (mEssentListCheckedLevelTwoDataGroup.size() > 0) {
////                            for (int i = 0; i < mEssentListCheckedLevelTwoDataGroup.size(); i++) {
////                                if (mEssentListCheckedLevelTwoDataGroup.get(i) == expensesLevelTwoData) {
////                                    essentialmIds.remove(i);
////                                    mselectedData.remove(i);
//
//                                    onCategoriesSelectedItemListener.onSelectedItem(expensesLevelTwoData.getId(), "remove", "leveltwo",expensesLevelTwoData.getLev1_id(),user_expenses);
////                                    onCategoriesSelectedItemListener.onSelectedItem(expensesLevelOneData.getId(), "remove", "levelone");
//                                    //Log.e("Essentialremove",""+expensesLevelOneData.getId());
////                                }
////                            }
////                        }
//                    }else if(mExpensestype.equalsIgnoreCase("Discretionary")){
////                        if (mDiscretListCheckedLevelTwoDataGroup.size() > 0) {
////                            for (int i = 0; i < mDiscretListCheckedLevelTwoDataGroup.size(); i++) {
////                                if (mDiscretListCheckedLevelTwoDataGroup.get(i) == expensesLevelTwoData) {
////                                    discretionarymIds.remove(i);
////                                    mselectedData.remove(i);
//                                    onCategoriesSelectedItemListener.onSelectedItem(expensesLevelTwoData.getId(), "remove", "leveltwo",expensesLevelTwoData.getLev1_id(), user_expenses);
//                                    //Log.e("Discretionaryremove",""+expensesLevelOneData.getId());
//                                    onCategoriesSelectedItemListener.onSelectedItem(expensesLevelOneData.getId(), "remove", "levelone",expensesLevelTwoData.getLev1_id(), user_expenses);
////                                }
////                            }
////                        }
//                    }
//
//
//                }
//                    //mListCheckedLevelTwoDataGroup =
//                    //ExpensesSelectionDetailsEssentialFragment.removePersistedAnonymePref(name);
//
//            }
//        });
//        if (mSelectLev2List.contains(getChild(groupPosition, childPosition).getId())) {
//            if (childViewHolder.mCheckBox.getTag().equals(""+childPosition) && !childViewHolder.mCheckBox.isChecked())
//                childViewHolder.mCheckBox.setChecked(true);
//        }
////        if(childViewHolder.mCheckBox.getTag()!=null)
////        {
////            if(childViewHolder.mCheckBox.getTag().toString().equalsIgnoreCase("true"))
////            {
////                childViewHolder.mCheckBox.setChecked(true);
////                //Log.e("TagLog","sucesssssss*********");
////            }
////        }
//
//        return convertView;
//    }
//
//    @Override
//    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        return false;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//    public final class GroupViewHolder {
//
//        TextView mGroupText;
//        CheckBox mGroupcheckBox;
//        ImageView mGroupImgView;
//    }
//
//    public final class ChildViewHolder {
//
//        TextView mChildText;
//        CheckBox mCheckBox;
//    }
//
//
//
//}
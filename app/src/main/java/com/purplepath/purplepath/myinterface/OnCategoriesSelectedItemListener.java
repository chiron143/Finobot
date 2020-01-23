package com.purplepath.purplepath.myinterface;

import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.User_expense;

import java.util.ArrayList;

/**
 * Created by Bert on 25-Aug-16.
 */
public interface OnCategoriesSelectedItemListener {
     void onSelectedItem(String id, String key, String leveltype, String groupId, ArrayList<User_expense> user_expenses);
}

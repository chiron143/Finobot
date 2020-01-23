package com.purplepath.purplepath.incomedetails.fragment.incomeselection.dailoginterface;

import com.purplepath.purplepath.expenses.expensesmodel.ExpensesLevelZeroData;
import com.purplepath.purplepath.incomedetails.fragment.model.Income_cat_lev1;

import java.util.ArrayList;


/**
 * Created by dinesh on 29/08/16.
 */
public interface IncSelecOnDismisInterf {
    void incomeSelectedLevOneList(ArrayList<Income_cat_lev1> selectedList);
    void selectionNextButtonClick();
    void expenseSelectedLevOneList(ArrayList<ExpensesLevelZeroData> selectedList);
}

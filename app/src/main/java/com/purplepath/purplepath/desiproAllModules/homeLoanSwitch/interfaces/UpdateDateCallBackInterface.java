package com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.interfaces;

/**
 * Created by Pratheep.S on 30-03-2017.
 */

public interface UpdateDateCallBackInterface {
    void updateAllDates(String loanStartDate,String firstEmiPaidDate,String lastEmiPaidDateEdt,String nextEmiDueDateEdt,String finalEmiDateEdt);
    void updateOutStandingBalance(String balanceAmount);
}

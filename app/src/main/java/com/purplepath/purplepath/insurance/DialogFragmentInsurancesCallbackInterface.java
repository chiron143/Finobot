package com.purplepath.purplepath.insurance;

/**
 * Created by Suresh on 06/02/17.
 */

public interface DialogFragmentInsurancesCallbackInterface {
    void upadateInsurancesYears(String dob);
    void upadatepolicy_issue_date(String policy_issue_date);
    void upadatelast_Premium_Paid_Date(String last_Premium_Paid_Date);
    void upadatenext_Premium_Due_Date(String next_Premium_Due_Date);
    void upadatePremium_Due_Till_Date(String premium_Due_Till_Date);
    void upadatepolicy_end_date(String policy_end_date);
    void upadatematurity_Date(String maturity_Date);

}

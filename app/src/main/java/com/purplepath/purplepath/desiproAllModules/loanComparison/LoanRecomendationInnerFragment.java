package com.purplepath.purplepath.desiproAllModules.loanComparison;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.loanComparison.models.Loan1;
import com.purplepath.purplepath.desiproAllModules.loanComparison.models.Loan2;
import com.purplepath.purplepath.desiproAllModules.loanComparison.models.Loan3;
import com.purplepath.purplepath.desiproAllModules.loanComparison.models.LoanComparisonModel;
import com.purplepath.purplepath.fragments.BaseFragment;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author: Pratheep.S
 */
public class LoanRecomendationInnerFragment extends BaseFragment {

    private static String TAG = "spcheck";
    @Bind(R.id.total_payement_edt)
    EditText total_payement_edt;

    @Bind(R.id.total_payement_assoc_charge_edt)
    EditText total_payement_assoc_charge_edt;

    @Bind(R.id.total_principal_payement_edt)
    EditText total_principal_payement_edt;

    @Bind(R.id.total_interest_payement_edt)
    EditText total_interest_payement_edt;
    @Bind(R.id.statedInterestRate_edt)
    EditText statedInterestRate_edt;
    @Bind(R.id.effectiveInterestRate_edt)
    EditText effectiveInterestRate_edt;
    @Bind(R.id.internalRateOfReturn_edt)
    EditText internalRateOfReturn_edt;
    @Bind(R.id.classification_txt)
    TextView classification_txt;
    @Bind(R.id.star1)
    ImageView star1;
    @Bind(R.id.star2)
    ImageView star2;
    @Bind(R.id.star3)
    ImageView star3;
    private LoanComparisonModel loanComparisonModel;
    private int fragmentID;
    private HashMap<Integer, String> classificationMap = new HashMap<Integer, String>() {{
        put(0, "Best Preffered Deal");
        put(1, "Next Preffered Deal");
        put(2, "Least Preffered Deal");
        put(3, "");
    }};

    public static LoanRecomendationInnerFragment newInstance(LoanComparisonModel loanComparisonModel, int fragmentID) {

        Bundle args = new Bundle();
        args.putSerializable("LoanComparisonModel", loanComparisonModel);
        args.putInt("fragmentID", fragmentID);
        LoanRecomendationInnerFragment fragment = new LoanRecomendationInnerFragment();
        fragment.setArguments(args);
        Log.i(TAG, "newInstance: Dialog inner fragmentID" + fragmentID);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loan_recomendation_inner, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            Bundle args = getArguments();
            if (args != null) {
                if (args.containsKey("LoanComparisonModel")) {
                    loanComparisonModel = (LoanComparisonModel) args.getSerializable("LoanComparisonModel");
                    if (loanComparisonModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        if (args.containsKey("fragmentID")) {
                            fragmentID = args.getInt("fragmentID");
                            int size = loanComparisonModel.getData().getBest_ln_array().size();
                            int rank = 3;
                            switch (fragmentID) {
                                case 1:
                                    Loan1 loan1 = loanComparisonModel.getData().getLoan1();
                                    for (int i = 0; i < size; i++) {
                                        if (loanComparisonModel.getData().getBest_ln_array().get(i).equals("loan1")) {
                                            rank = i;
                                        }
                                    }
                                    setValues(loan1.getTot_pay(),loan1.getTot_pay_with_assoc_charge() ,loan1.getTot_prin_pay(), loan1.getTot_int_pay(), loan1.getStat_rate(),
                                            loan1.getEff_rate(), loan1.getIrr_rate(), rank);
                                    break;
                                case 2:
                                    Loan2 loan2 = loanComparisonModel.getData().getLoan2();
                                    for (int i = 0; i < size; i++) {
                                        if (loanComparisonModel.getData().getBest_ln_array().get(i).equals("loan2")) {
                                            rank = i;
                                        }
                                    }
                                    setValues(loan2.getTot_pay(),loan2.getTot_pay_with_assoc_charge(), loan2.getTot_prin_pay(), loan2.getTot_int_pay(), loan2.getStat_rate(),
                                            loan2.getEff_rate(), loan2.getIrr_rate(), rank);

                                    break;
                                case 3:
                                    Loan3 loan3 = loanComparisonModel.getData().getLoan3();
                                    for (int i = 0; i < size; i++) {
                                        if (loanComparisonModel.getData().getBest_ln_array().get(i).equals("loan3")) {
                                            rank = i;
                                        }
                                    }

                                    setValues(loan3.getTot_pay(),loan3.getTot_pay_with_assoc_charge(), loan3.getTot_prin_pay(), loan3.getTot_int_pay(), loan3.getStat_rate(),
                                            loan3.getEff_rate(), loan3.getIrr_rate(), rank);

                                    break;
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Network Error Please try again later..", Toast.LENGTH_LONG).show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValues(String total_pay,String total_pay_with_assoc_charge, String total_principal_pay, String tot_interest_pay, String stated_rate,
                           String effective_rate, String irr_rate, int rank) {

        total_payement_edt.setText(total_pay == null ? "" : "" + Math.round(Double.parseDouble(total_pay)));
        total_payement_assoc_charge_edt.setText(total_pay_with_assoc_charge == null ? "" : "" + Math.round(Double.parseDouble(total_pay_with_assoc_charge)));
        total_principal_payement_edt.setText(total_principal_pay == null ? "" : "" + Math.round(Double.parseDouble(total_principal_pay)));
        total_interest_payement_edt.setText(tot_interest_pay == null ? "" : "" + Math.round(Double.parseDouble(tot_interest_pay)));
        statedInterestRate_edt.setText(stated_rate== null?"":""+ Math.round(Double.parseDouble(stated_rate)));
        effectiveInterestRate_edt.setText(effective_rate== null?"":""+ Math.round(Double.parseDouble(effective_rate)));
        internalRateOfReturn_edt.setText(irr_rate== null?"":""+ Math.round(Double.parseDouble(irr_rate)));
        classification_txt.setText(classificationMap.get(rank));
        switch (rank) {
            case 0:
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.VISIBLE);
                star3.setVisibility(View.VISIBLE);
                break;
            case 1:
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.VISIBLE);
                star3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                star1.setVisibility(View.VISIBLE);
                star2.setVisibility(View.INVISIBLE);
                star3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                break;
        }
    }

}

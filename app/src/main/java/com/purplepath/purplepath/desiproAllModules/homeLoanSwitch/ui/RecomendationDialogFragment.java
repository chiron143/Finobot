package com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.ui;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.models.LoanRecomendationModel;
import com.purplepath.purplepath.fragments.BaseFragment;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Pratheep.S on 05-04-2017.
 */

public class RecomendationDialogFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_amountSaved)
    TextView tv_amountSaved;

    @Bind(R.id.amountSavedInEmi_edt)
    com.blackcat.currencyedittext.CurrencyEditText amountSavedInEmi;

    @Bind(R.id.tv_additionalExpense_edt)
    com.blackcat.currencyedittext.CurrencyEditText additionalExpense;

    @Bind(R.id.presentValueSavings_edt)
    com.blackcat.currencyedittext.CurrencyEditText presentValueSavings;

    @Bind(R.id.overallSavings_edt)
    com.blackcat.currencyedittext.CurrencyEditText overallSavings;

    @Bind(R.id.realSavings_edt)
    com.blackcat.currencyedittext.CurrencyEditText realSavings;

    @Bind(R.id.realSavingsPercent_edt)
    EditText realSavingsPercent;

    @Bind(R.id.amountSavedInTenure_edt)
    EditText amountSavedInTenure_edt;

    @Bind(R.id.closebtnId)
    ImageView closeButton;

    @Bind(R.id.textview_recomended)
    TextView textview_recomended;

    @Bind(R.id.img_thumb)
    ImageView img_thumb;

    Locale indianlocal = new Locale("en", "IN");

    Bundle arguments;
    LoanRecomendationModel loanRecomendationModel;

    public static RecomendationDialogFragment newInstance(LoanRecomendationModel loanRecomendationModel) {
        Bundle args = new Bundle();
        args.putSerializable("LoanRecomendationModel", loanRecomendationModel);
        RecomendationDialogFragment fragment = new RecomendationDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
//        int height = getResources().getDisplayMetrics().heightPixels;
//        int width = getResources().getDisplayMetrics().widthPixels;
//        getDialog().getWindow().setLayout((int) (width * .90), (int) (height * .85));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_recomendation_home_loan, container, false);
        ButterKnife.bind(this, view);

        arguments = getArguments();
        if ((arguments != null) && arguments.containsKey("LoanRecomendationModel")) {
            loanRecomendationModel = (LoanRecomendationModel) arguments.getSerializable("LoanRecomendationModel");
            loadValuesInPopup(loanRecomendationModel);
        }

        closeButton.setOnClickListener(this);


        amountSavedInEmi.setLocale(indianlocal);
        additionalExpense.setLocale(indianlocal);
        overallSavings.setLocale(indianlocal);
        presentValueSavings.setLocale(indianlocal);
        realSavings.setLocale(indianlocal);

        return view;
    }

    private void loadValuesInPopup(LoanRecomendationModel loanRecomendationModel) {

        amountSavedInEmi.setText(getRoundedValues(loanRecomendationModel.getData().getRecommend().getOption1().getAmt_saved_in_emi()));
        if (Float.parseFloat(getRoundedValues(loanRecomendationModel.getData().getRecommend().getOption1().getAmt_saved_in_emi())) > 0) {
            tv_amountSaved.setText("Amount paid less in EMI is");
        } else if (Float.parseFloat(getRoundedValues(loanRecomendationModel.getData().getRecommend().getOption1().getAmt_saved_in_emi())) < 0) {
            tv_amountSaved.setText("Amount paid more in EMI is");
        } else if (Float.parseFloat(getRoundedValues(loanRecomendationModel.getData().getRecommend().getOption1().getAmt_saved_in_emi())) == 0) {
            tv_amountSaved.setText("No change in EMI paid ");
            amountSavedInEmi.setVisibility(View.INVISIBLE);
        }
        amountSavedInTenure_edt.setText(getRoundedValues(loanRecomendationModel.getData().getRecommend().getOption1().getAmt_saved_in_tenure()));
        additionalExpense.setText(getRoundedValues(loanRecomendationModel.getData().getRecommend().getOption1().getAdd_exp()));
        presentValueSavings.setText(getRoundedValues(loanRecomendationModel.getData().getRecommend().getOption1().getPre_val_savings()));
        overallSavings.setText(getRoundedValues(loanRecomendationModel.getData().getRecommend().getOption1().getOverall_savings()));
        realSavings.setText(getRoundedValues(loanRecomendationModel.getData().getRecommend().getOption1().getReal_savings()));
        realSavingsPercent.setText((loanRecomendationModel.getData().getRecommend().getOption1().getReal_savings_percent()));
        Log.i("spcheck", loanRecomendationModel.getData().getRecommend().getOption1().getHome_loan_switch());
        String recomendation = loanRecomendationModel.getData().getRecommend().getOption1().getHome_loan_switch();
        if (recomendation.equalsIgnoreCase("Recommended")) {
            textview_recomended.setText("Recommended");
            textview_recomended.setTextColor(Color.parseColor("#64dd17"));
            img_thumb.setImageResource(R.drawable.ic_recommened_hand);
        } else if (recomendation.equalsIgnoreCase("Not Recommended")) {
            textview_recomended.setText("Not Recommended");
            textview_recomended.setTextColor(Color.parseColor("#f44336"));
            img_thumb.setImageResource(R.drawable.ic_not_recommened_hand);
        } else if (recomendation.equalsIgnoreCase("Neutral")) {
            textview_recomended.setText("Neutral");
            textview_recomended.setTextColor(Color.parseColor("#4799E8"));
            img_thumb.setImageResource(R.drawable.ic_neutral_hand);
        } else if (recomendation.equalsIgnoreCase("Invalid")) {
            textview_recomended.setText("Invalid");
            //textview_recomended.setBackgroundResource(R.drawable.textview_border_red);
        }

    }

    private String getRoundedValues(String value) {
        String result = "";
        if (UtileKit.validateObjectValues(value)) {
            int amount = Math.round(Float.parseFloat(value));
            result = amount + "";
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.closebtnId:
//                getDialog().dismiss();
                break;

        }
    }
}

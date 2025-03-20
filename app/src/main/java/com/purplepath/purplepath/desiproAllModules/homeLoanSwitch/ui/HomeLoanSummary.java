package com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.models.LoanRecomendationModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by pravinr on 11/7/17.
 */

public class HomeLoanSummary extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private Context mContext;



    @BindView(R.id.tv_amountSaved)
    TextView tv_amountSaved;

    @BindView(R.id.amountSavedInEmi_edt)
    com.blackcat.currencyedittext.CurrencyEditText amountSavedInEmi;

    @BindView(R.id.tv_additionalExpense_edt)
    com.blackcat.currencyedittext.CurrencyEditText additionalExpense;

    @BindView(R.id.presentValueSavings_edt)
    com.blackcat.currencyedittext.CurrencyEditText presentValueSavings;

    @BindView(R.id.overallSavings_edt)
    com.blackcat.currencyedittext.CurrencyEditText overallSavings;

    @BindView(R.id.realSavings_edt)
    com.blackcat.currencyedittext.CurrencyEditText realSavings;

    @BindView(R.id.realSavingsPercent_edt)
    EditText realSavingsPercent;

    @BindView(R.id.amountSavedInTenure_edt)
    EditText amountSavedInTenure_edt;

    @BindView(R.id.closebtnId)
    ImageView closeButton;

    @BindView(R.id.textview_recomended)
    TextView textview_recomended;

    @BindView(R.id.img_thumb)
    ImageView img_thumb;

    Locale indianlocal = new Locale("en", "IN");

    Bundle arguments;
    LoanRecomendationModel loanRecomendationModel;

    public static HomeLoanSummary newInstance(LoanRecomendationModel loanRecomendationModel) {
        Bundle args = new Bundle();
        args.putSerializable("LoanRecomendationModel", loanRecomendationModel);
        HomeLoanSummary fragment = new HomeLoanSummary();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // setHasOptionsMenu(true);
        mContext=getContext();
        View view=inflater.inflate(R.layout.fragment_homeloan_summary, container, false);
        ButterKnife.bind(this, view);
        backPressedListener.setActionBarTitle("Home Loan Switch");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mContext = getContext();

        arguments = getArguments();
        if ((arguments != null) && arguments.containsKey("LoanRecomendationModel")) {
            loanRecomendationModel = (LoanRecomendationModel) arguments.getSerializable("LoanRecomendationModel");
            loadValuesInPopup(loanRecomendationModel);
        }

        amountSavedInEmi.setLocale(indianlocal);
        additionalExpense.setLocale(indianlocal);
        overallSavings.setLocale(indianlocal);
        presentValueSavings.setLocale(indianlocal);
        realSavings.setLocale(indianlocal);

        return view;
    }



    private void loadValuesInPopup(LoanRecomendationModel loanRecomendationModel) {

        amountSavedInEmi.setText(""+getRoundedValues(loanRecomendationModel.getData().getRecommend().getOption1().getAmt_saved_in_emi()));
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
        realSavingsPercent.setText((""+loanRecomendationModel.getData().getRecommend().getOption1().getReal_savings_percent()));
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

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_detail);
        MenuItem items=menu.findItem(R.id.menu_chart);
        // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_detail:

                try{
                    addFragmenttoStack(new HomeLoanDetails());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_chart:

                try{

                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }*/
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }

    }
}

package com.purplepath.purplepath.desiproAllModules.loanEligibility.view;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.desiproAllModules.loanEligibility.view.models.LoanEligibilityModel;

/**
 * Created by bertrandrussellsakthees on 23/08/17.
 */

public class LoanEligibilityDialog extends DialogFragment implements View.OnClickListener {

    private final static String TAG = LoanEligibilityDialog.class.getCanonicalName();


    private Context context;
    private LoanEligibilityModel mLoanEligibilityModel;
    int height;

    int width;

    private ImageView mdoneimageview, closebtnId;

    private CustomTextView mtype_of_Affordability, tv_timeframe,tv_firstview,tv_secondview,tv_thirdview,tv_fourthview;

    private Button txt_done;
    private RelativeLayout layout_tv_firstview,layout_tv_secondview,layout_tv_thirdview,layout_tv_fourthview;


    public static DialogFragment newInstance(LoanEligibilityModel mLoanEligibilityModel, Context mContext) {
        Bundle args = new Bundle();
        args.putSerializable("gaolAffordability", mLoanEligibilityModel);
        LoanEligibilityDialog fragment = new LoanEligibilityDialog();
        fragment.context = mContext;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.APNA_DIALOG);

        try {
            if (getArguments().containsKey("gaolAffordability")) {
                mLoanEligibilityModel = (LoanEligibilityModel) getArguments().getSerializable("gaolAffordability");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.widthPixels - (int) Math.abs(metrics.widthPixels * .20);
        width = metrics.widthPixels - (int) Math.abs(metrics.widthPixels * .40);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loan_eligibility_dailog, container, false);
        closebtnId = view.findViewById(R.id.closebtnId);
        txt_done = view.findViewById(R.id.txt_done);
        mdoneimageview = view.findViewById(R.id.img_thumb);
        mtype_of_Affordability = view.findViewById(R.id.textview_recomended);
        tv_timeframe = view.findViewById(R.id.tv_timeframe);
        tv_firstview = view.findViewById(R.id.tv_firstview);
        tv_secondview = view.findViewById(R.id.tv_secondview);
        tv_thirdview = view.findViewById(R.id.tv_thirdview);
        tv_fourthview = view.findViewById(R.id.tv_fourthview);


        if (mLoanEligibilityModel != null) {
            validationpartOfGoalAffordability(mLoanEligibilityModel);
        }
        txt_done.setOnClickListener(this);
        closebtnId.setOnClickListener(this);
        return view;
    }

    private void validationpartOfGoalAffordability(LoanEligibilityModel mLoanEligibilityModel) {

        try {
            if (mLoanEligibilityModel.getData().getResult() != null) {
                mtype_of_Affordability.setText(mLoanEligibilityModel.getData().getResult());
                if (mLoanEligibilityModel.getData().getResult().equalsIgnoreCase("Eligible")) {
                    mdoneimageview.setBackgroundResource(R.drawable.ic_recommened_hand);
                    tv_timeframe.setText("Based on your input(s), you could be eligible for availing the loan.");

                } else {
                    mdoneimageview.setBackgroundResource(R.drawable.ic_not_recommened_hand);
                    tv_timeframe.setText("Based on your input(s), you may not be eligible for availing the loan, due to the following reasons");


                    if (forloopconditioncheck(mLoanEligibilityModel.getData().getNon_eligible_array(),"Payment")) {
                           tv_firstview.setVisibility(View.VISIBLE);
                           tv_firstview.setText("your repayment capacity is not meeting the requirement");
                       } else {
                           tv_firstview.setVisibility(View.GONE);
                       }
                       if (forloopconditioncheck(mLoanEligibilityModel.getData().getNon_eligible_array(),"Debt Ratio")) {
                           tv_secondview.setVisibility(View.VISIBLE);
                           tv_secondview.setText("your current debt ratio is higher than the expected rate");
                       } else {
                           tv_secondview.setVisibility(View.GONE);
                       }
                       if (forloopconditioncheck(mLoanEligibilityModel.getData().getNon_eligible_array(),"Credit Score")) {
                           tv_fourthview.setVisibility(View.VISIBLE);
                           tv_fourthview.setText("your credit score is below the expected rank");
                       } else {
                           tv_fourthview.setVisibility(View.GONE);
                       }
                       if (forloopconditioncheck(mLoanEligibilityModel.getData().getNon_eligible_array(),"LTV")) {
                           tv_thirdview.setVisibility(View.VISIBLE);
                           tv_thirdview.setText("your loan to value is not meeting the requirement");
                       } else {
                           tv_thirdview.setVisibility(View.GONE);
                       }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean forloopconditioncheck(String[] non_eligible_array, String checkvalue) {
        try {
            for (String noneli : non_eligible_array) {
                if (noneli.equals(checkvalue)) {
                    return true;
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_done: {
                dismiss();
            }
            break;
            case R.id.closebtnId: {
                dismiss();
            }
            break;

        }
    }
}

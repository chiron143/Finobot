package com.purplepath.purplepath.desiproAllModules.goalaffordability.ui.DialogFragment;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.desiproAllModules.goalaffordability.ui.models.GoalAffordabilityModels;

/**
 * Created by Suresh on 17/08/17.
 */

public class GoalAffordabilityDialog extends DialogFragment implements View.OnClickListener , RadioGroup.OnCheckedChangeListener {

    private final static String TAG = GoalAffordabilityDialog.class.getCanonicalName();


    private Context mContext;

    private GoalAffordabilityModels mGoalAffordabilityModels;
    int height;

    int width;

    private ImageView mdoneimageview, closebtnId;

    private CustomTextView mtype_of_Affordability, tv_timeframe,flexibile_time,annual_savings;

    private RadioButton timeframe_yes_RadioBtn, timeframe_no_RadioBtn, annualincome_yes_RadioBtn, annualincome_no_RadioBtn;

    private LinearLayout flexilibilitylayout, annuallayout;

    private Button txt_done;

    RadioGroup processingFee_rg, annualincomeRadioRg;

    private boolean isTimeframe= true, isAnnualIncome= true;

    public static DialogFragment newInstance(GoalAffordabilityModels mGoalAffordabilityModels, Context mContext) {
        Bundle args = new Bundle();
        args.putSerializable("gaolAffordability", mGoalAffordabilityModels);
        GoalAffordabilityDialog fragment = new GoalAffordabilityDialog();
        fragment.mContext = mContext;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.APNA_DIALOG);

        try {
            if (getArguments().containsKey("gaolAffordability")) {
                mGoalAffordabilityModels = (GoalAffordabilityModels) getArguments().getSerializable("gaolAffordability");
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
        View view = inflater.inflate(R.layout.goal_affordability_dialog, container, false);
        closebtnId = view.findViewById(R.id.closebtnId);
        txt_done = view.findViewById(R.id.txt_done);
        mdoneimageview = view.findViewById(R.id.img_thumb);
        mtype_of_Affordability = view.findViewById(R.id.textview_recomended);
        tv_timeframe = view.findViewById(R.id.tv_timeframe);
        timeframe_yes_RadioBtn = view.findViewById(R.id.timeframe_yes_RadioBtn);
        timeframe_no_RadioBtn = view.findViewById(R.id.timeframe_no_RadioBtn);
        annualincome_yes_RadioBtn = view.findViewById(R.id.annualincome_yes_RadioBtn);
        annualincome_no_RadioBtn = view.findViewById(R.id.annualincome_no_RadioBtn);
        annualincomeRadioRg = view.findViewById(R.id.annualincomeRadioRg);
        processingFee_rg = view.findViewById(R.id.timeframe_rg);
        flexilibilitylayout = view.findViewById(R.id.flexilibilitylayout);
        annuallayout = view.findViewById(R.id.annuallayout);
        flexibile_time= view.findViewById(R.id.flexibile_time);
        annual_savings= view.findViewById(R.id.annual_savings);
        if (mGoalAffordabilityModels != null) {
            validationpartOfGoalAffordability(mGoalAffordabilityModels);
        }
//        setAllRadioButtonsToCheckedState();
        txt_done.setOnClickListener(this);
        closebtnId.setOnClickListener(this);
        processingFee_rg.setOnCheckedChangeListener(this);
        annualincomeRadioRg.setOnCheckedChangeListener(this);
        return view;
    }
    private void setAllRadioButtonsToCheckedState() {
        UtileKit.getSwitchYesBtnView(annualincome_yes_RadioBtn ,annualincome_no_RadioBtn, mContext);
        UtileKit.getSwitchYesBtnView(timeframe_yes_RadioBtn,timeframe_no_RadioBtn, mContext);

    }
    private void validationpartOfGoalAffordability(GoalAffordabilityModels mGoalAffordabilityModels) {

        try {
            if (mGoalAffordabilityModels.getData().getResult() != null) {
                mtype_of_Affordability.setText(mGoalAffordabilityModels.getData().getResult());
                if (mGoalAffordabilityModels.getData().getResult().equalsIgnoreCase("Affordable")) {
                    mdoneimageview.setBackgroundResource(R.drawable.ic_recommened_hand);
                    tv_timeframe.setText("Based on your input(s), achieving your goal is possible within the timeframe desired.");
                    annuallayout.setVisibility(View.GONE);
                    flexilibilitylayout.setVisibility(View.GONE);
                    flexibile_time.setVisibility(View.GONE);
                    annual_savings.setVisibility(View.GONE);
                } else {
                    mdoneimageview.setBackgroundResource(R.drawable.ic_not_recommened_hand);
                    tv_timeframe.setText("Based on your input(s), achieving your goal is possible within the timeframe desired.");
                    annuallayout.setVisibility(View.VISIBLE);
                    flexilibilitylayout.setVisibility(View.VISIBLE);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()) {
            case R.id.timeframe_rg:
                try {
                    if (checkedId == R.id.timeframe_yes_RadioBtn) {
                        UtileKit.getSwitchYesBtnView(timeframe_yes_RadioBtn, timeframe_no_RadioBtn, mContext);
                        flexibile_time.setVisibility(View.VISIBLE);
                        flexibile_time.setText("Based on your input(s), achieving your goal is possible within the timeframe desired," +
                                " provided you are able to" + "save and invest Rs. " + mGoalAffordabilityModels.getData()
                                .getPmt() + ", annually");
                        isTimeframe = true;
                    } else if (checkedId == R.id.timeframe_no_RadioBtn) {
                        UtileKit.getSwitchNoBtnView(timeframe_yes_RadioBtn, timeframe_no_RadioBtn, mContext);
                        flexibile_time.setVisibility(View.GONE);
                        isTimeframe = false;
                    }
                    if (!isAnnualIncome && !isTimeframe) {
                        annual_savings.setVisibility(View.VISIBLE);
                        annual_savings.setText("Based on your input(s), achieving your goal is not possible ");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.annualincomeRadioRg:
                try{
                if (checkedId == R.id.annualincome_yes_RadioBtn) {
                    UtileKit.getSwitchYesBtnView(annualincome_yes_RadioBtn,annualincome_no_RadioBtn, mContext);
                    annual_savings.setVisibility(View.VISIBLE);
                    annual_savings.setText("Alternatively, based on your annual investment capacity, your will be able to fulfill your goal" +
                            "in +" + mGoalAffordabilityModels.getData()
                                    .getTenure() +  " years");
                isAnnualIncome = true;
                } else if (checkedId == R.id.annualincome_no_RadioBtn) {
                    UtileKit.getSwitchNoBtnView(annualincome_yes_RadioBtn,annualincome_no_RadioBtn, mContext);
                    annual_savings.setVisibility(View.GONE);
                    isAnnualIncome = false;
                }

                if(!isAnnualIncome && !isTimeframe){
                    annual_savings.setVisibility(View.VISIBLE);
                    annual_savings.setText("Based on your input(s), achieving your goal is not possible ");
                }
                  }catch (Exception e){
                    e.printStackTrace();
                 }
                break;
        }
    }
}

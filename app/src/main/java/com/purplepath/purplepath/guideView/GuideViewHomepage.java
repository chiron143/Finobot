package com.purplepath.purplepath.guideView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.purplepath.purplepath.AppManagement.Quiz.nextpageinterpage.NextInterface;
import com.finobot.finobot.R;
import com.purplepath.purplepath.customview.CustomTextView;

/**
 * Created by Suresh on 16/06/17.
 */

public class GuideViewHomepage extends DialogFragment {

    TextView mtoolbarTitle;


    private CustomTextView mHeading;
    private ImageView mIncomeDetails, mExpenseDetails, mLiabilities, mAssets,mNetworth,mCashMgmt;
    private RelativeLayout relativeLayout, drawer_layout;
    final DisplayMetrics dm= new DisplayMetrics();
    private LinearLayout linearLayout;
    private int height,width;

    private NextInterface dismissDialog;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.WindowTitleBackground);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View guideview = inflater.inflate(R.layout.guide_homepage, container, false);

        mIncomeDetails = guideview.findViewById(R.id.iv_income_details);
        mExpenseDetails = guideview.findViewById(R.id.iv_expenseDetails);
        mLiabilities = guideview.findViewById(R.id.iv_liabilities);
        mAssets = guideview.findViewById(R.id.iv_assets);
        mNetworth= guideview.findViewById(R.id.iv_netWorth);
        mCashMgmt= guideview.findViewById(R.id.iv_cashManagement);

        linearLayout= guideview.findViewById(R.id.iv_center_image);

        relativeLayout = guideview.findViewById(R.id.parent_RelativeLayout);

        drawer_layout = guideview.findViewById(R.id.drawer_layout);


        drawer_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });

        return guideview;
    }

}

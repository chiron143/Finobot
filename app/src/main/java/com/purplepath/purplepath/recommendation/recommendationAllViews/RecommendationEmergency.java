package com.purplepath.purplepath.recommendation.recommendationAllViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.recommendation.model.RecommendData;
/**
 * Created by pravinr on 3/7/18.
 */

public class RecommendationEmergency extends BaseFragment implements View.OnClickListener  {

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    TextView noOfMonthsView, amountRequiredView, amountAccumulatedView, differenceView, statusView;

    RecommendData recommendData;


    public static RecommendationEmergency newInstance(RecommendData recommendData) {
        RecommendationEmergency recommend = new RecommendationEmergency();
        Bundle args = new Bundle();
        if (recommendData != null) {
            args.putSerializable("recommendData", recommendData);
        }
        recommend.setArguments(args);
        return recommend;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_popup_emergency_plan, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallBackListener.setActionBarTitle("Emergency Fund Plan");

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        noOfMonthsView = view.findViewById(R.id.answer_number_of_months);
        amountRequiredView = view.findViewById(R.id.answer_amount_required);
        amountAccumulatedView = view.findViewById(R.id.answer_amount_accumulated);
        differenceView = view.findViewById(R.id.answer_amount_difference);
        statusView = view.findViewById(R.id.answer_footer_view);

        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey("recommendData")) {
                recommendData = (RecommendData) args.getSerializable("recommendData");
                try {
                    if (recommendData.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        if(recommendData.getData().getEmg_fund()!=null) {

                            applyEmergencyInformation(recommendData);
                        }
                    }else {
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }

    private void applyEmergencyInformation(RecommendData recommendData) {
        noOfMonthsView.setText(TextUtils.isEmpty(recommendData.getData().getEmg_fund().getNo_of_months()) ? "N/A" : recommendData.getData().getEmg_fund().getNo_of_months());
        amountRequiredView.setText("₹ "+(UtileKit.formatedNumber(Float.valueOf(TextUtils.isEmpty(recommendData.getData().getEmg_fund().getAmt_req()) ? "N/A" : recommendData.getData().getEmg_fund().getAmt_req()))));
        amountAccumulatedView.setText("₹ "+(UtileKit.formatedNumber(Float.valueOf(TextUtils.isEmpty(recommendData.getData().getEmg_fund().getAmt_accum()) ? "N/A" : recommendData.getData().getEmg_fund().getAmt_accum()))));
        differenceView.setText("₹ "+(UtileKit.formatedNumber(Float.valueOf(TextUtils.isEmpty(recommendData.getData().getEmg_fund().getDiff()) ? "N/A" : recommendData.getData().getEmg_fund().getDiff()))));
        statusView.setText(TextUtils.isEmpty(recommendData.getData().getEmg_fund().getStatus()) ? "N/A" : recommendData.getData().getEmg_fund().getStatus());
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow:
            {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            break;


        }
    }

}

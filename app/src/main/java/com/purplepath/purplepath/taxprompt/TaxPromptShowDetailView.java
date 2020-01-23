package com.purplepath.purplepath.taxprompt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.taxproduct.TaxPlanProductDetailFragment;

import butterknife.ButterKnife;

/**
 * Created by Murugesan on 1/25/18.
 */

public class TaxPromptShowDetailView extends BaseFragment implements View.OnClickListener {

    private OnActivityBackPressedListener mCallBackListener;

    RelativeLayout relative_left_arrow,relative_center_home;

    private Context mContext;

    private TextView mtax_plan,mtax_clasification,mtax_recommendation;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBackListener=(OnActivityBackPressedListener)context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view =inflater.inflate(R.layout.fragment_tax_prompt_show_detail, container, false);
        mContext=getContext();
        ButterKnife.bind(this,view);

        mCallBackListener.setActionBarTitle("Tax Details");
        relative_center_home= view.findViewById(R.id.relative_center_home);
        relative_left_arrow= view.findViewById(R.id.relative_left_arrow);

        relative_center_home.setOnClickListener(this);
        relative_left_arrow.setOnClickListener(this);

        mtax_plan= view.findViewById(R.id.tax_plan);
        mtax_clasification= view.findViewById(R.id.tax_clasification);
        mtax_recommendation= view.findViewById(R.id.tax_recommendation);

        mtax_plan.setOnClickListener(this);
        mtax_clasification.setOnClickListener(this);
        mtax_recommendation.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

            case R.id.tax_plan:

               // addFragmenttoStack(new TaxPromptDetailViewPager());

                addFragmenttoStack(new TaxPlanProductDetailFragment());

                break;
            case R.id.tax_clasification:

               // addFragmenttoStack(new TaxClasificationDetail());
                addFragmenttoStack(new TaxClasificationDetail());

                break;
            case R.id.tax_recommendation:

               addFragmenttoStack(new TaxRecommendationTable());

               // addFragmenttoStack(new TaxRecommendation());

                break;

        }
    }

}


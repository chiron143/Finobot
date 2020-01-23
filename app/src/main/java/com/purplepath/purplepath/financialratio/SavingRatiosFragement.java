package com.purplepath.purplepath.financialratio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dinesh on 28/04/17.
 */

public class SavingRatiosFragement extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    public static SavingRatiosFragement newInstance(double value,String title) {

        Bundle args = new Bundle();
        args.putDouble("key",value);
        args.putString("title",title);
        SavingRatiosFragement fragment = new SavingRatiosFragement();
        fragment.setArguments(args);
        return fragment;
    }
     @Bind(R.id.donut_progress)
    DonutProgress saveProgress;
    @Bind(R.id.ratioTitle)
    TextView ratioTitleTxt;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backPressedListener= (OnActivityBackPressedListener) getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_save_ratio_view1, container, false);

        ButterKnife.bind(this,view);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        try {
            double obj = 0;
            String title = "";
            if (getArguments().containsKey("key"))
                obj = getArguments().getDouble("key");
            if (getArguments().containsKey("title"))
                title = getArguments().getString("title");
            backPressedListener.setActionBarTitle(title);
            saveProgress.setSuffixText("");
            ratioTitleTxt.setText(""+title);
//            saveProgress.setText(""+(float) Math.round(obj));
            saveProgress.setProgress((float) Math.round(obj));
        }catch (Exception e)
        {

        }
        return view;
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);

                startActivity(i);
                break;

        }

    }
}

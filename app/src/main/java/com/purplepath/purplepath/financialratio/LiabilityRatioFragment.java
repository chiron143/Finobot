package com.purplepath.purplepath.financialratio;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pravinr on 9/21/17.
 */

public class LiabilityRatioFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    public static LiabilityRatioFragment newInstance(double value,String title) {

        Bundle args = new Bundle();
        args.putDouble("key",value);
        args.putString("title",title);
        LiabilityRatioFragment fragment = new LiabilityRatioFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @BindView(R.id.donut_progress)
    DonutProgress saveProgress;
    @BindView(R.id.ratioTitle)
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
        View view = inflater.inflate(R.layout.fragment_liability_ratio_view, container, false);

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

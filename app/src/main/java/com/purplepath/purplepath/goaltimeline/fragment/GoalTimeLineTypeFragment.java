package com.purplepath.purplepath.goaltimeline.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.fragments.GetMobileNumberFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

/**
 * Created by Suresh on 28/07/17.
 */

public class GoalTimeLineTypeFragment  extends BaseFragment implements
        View.OnClickListener{
    String type_of_goals;

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    public static Fragment newInstance(String type) {
        GoalTimeLineTypeFragment fragment = new GoalTimeLineTypeFragment();
        fragment.type_of_goals = type;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setHasOptionsMenu(true);
        mCallBackListener = (OnActivityBackPressedListener) (mContext);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.type_of_goal, container, false);
        mCallBackListener.setActionBarTitle("Goal Plan");

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        Log.i("GoalAnalysisFragment","onCreateView type_of_goals"+ type_of_goals );
        return view;
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

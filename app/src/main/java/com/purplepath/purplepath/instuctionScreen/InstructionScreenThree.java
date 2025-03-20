package com.purplepath.purplepath.instuctionScreen;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Suresh on 18/09/17.
 */

public class InstructionScreenThree extends BaseFragment {



    @BindView(R.id. text_first)
    TextView text_first;
    @BindView(R.id. text_second)
    TextView text_second;
    @BindView(R.id. text_third)
    TextView text_third;
    @BindView(R.id. text_four)
    TextView text_four;
    @BindView(R.id. text_five)
    TextView text_five;

    @BindView(R.id. layout_one)
    LinearLayout layout_one;
    @BindView(R.id. layout_second)
    LinearLayout layout_second;
    @BindView(R.id. layout_third)
    LinearLayout layout_third;
    @BindView(R.id. layout_four)
    LinearLayout layout_four;
    @BindView(R.id. layout_five)
    LinearLayout layout_five;




    private Context mContext;

    private OnActivityBackPressedListener mCallBackListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mContext=getActivity();
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
            setHasOptionsMenu(true);

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
        View viewthree =  inflater.inflate(R.layout.instruction_screen_three, container, false);
        ButterKnife.bind(this,viewthree);



        return viewthree;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        layout_one.setVisibility(View.VISIBLE);
        layout_second.setVisibility(View.VISIBLE);
        layout_third.setVisibility(View.VISIBLE);
        layout_four.setVisibility(View.VISIBLE);
        layout_five.setVisibility(View.VISIBLE);

        text_first.setText("Getting to know about yourself is the first step to get started.");
        text_second.setText("We would like to know about you in complete details so as to provide a comprehensive financial plan that is actionable by you.");
        text_third.setText("It really helps to make a real good meaning out of your plan, if the data that you provide is very accurate. This is the most important requisite of the system. ");
        text_four.setText("GIGO stands for ‘Gold In Gold Out’ or ‘Garbage In Garbage Out’. The validity of the recommendation depends purely on the data that is being supplied into the system. Greater the accuracy of the input, greater the credibility of the outcome. ");
        text_five.setText("Hence, you are expected to enter the real data – demographic details and financial details – for the system to reflect reality such that the financial plan generated and the financial recommendations made are useful to you and enables you to move closer to achieving the goals.");

    }
}
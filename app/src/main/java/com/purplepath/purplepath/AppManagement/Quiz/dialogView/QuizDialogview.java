package com.purplepath.purplepath.AppManagement.Quiz.dialogView;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.AppManagement.Quiz.nextpageinterpage.NextInterface;

import static com.purplepath.purplepath.AppManagement.Quiz.Ui.QuizFragmentListview.scorevariable;


/**
 * Created by Suresh on 05/09/17.
 */

public class QuizDialogview  extends DialogFragment implements View.OnClickListener  {

    private Button playbutton;
    private TextView mfirsttextview, msecondtextview,mthirdtextview,mheadingtextview , headingtextScore, scoreText;

    private String typeofview;

    private int mScoretype, mlength;

    private LinearLayout startplayLayout, scorelayout;

    private Context mContext;

    public NextInterface nextInterface;

    private boolean handledClick = false;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view  =inflater.inflate(R.layout.quiz_dialog,container,false);
        initializeViews(view);
        playbutton.setOnClickListener(this);

        if(typeofview!= null) {
            if (typeofview.equalsIgnoreCase("ScoreView")) {
                startplayLayout.setVisibility(View.GONE);
                scorelayout.setVisibility(View.VISIBLE);
                headingtextScore.setText("Your Score ");
                scoreText.setText("Your Score is  " + scorevariable + "/" + mlength);
                playbutton.setText("Submit");

            } else {
                startplayLayout.setVisibility(View.VISIBLE);
                scorelayout.setVisibility(View.GONE);
            }
        }else{
            startplayLayout.setVisibility(View.VISIBLE);
            scorelayout.setVisibility(View.GONE);
        }


        return view;
    }

    private void initializeViews(View view) {

        playbutton = view.findViewById(R.id.playbutton);
        mfirsttextview = view.findViewById(R.id.firsttext);
        msecondtextview = view.findViewById(R.id.secondtext);
        mthirdtextview  = view.findViewById(R.id.thirdtext);
        mheadingtextview= view.findViewById(R.id.headingtext);
        startplayLayout = view.findViewById(R.id.startplayLayout);
        scorelayout = view.findViewById(R.id.score);
        scoreText = view.findViewById(R.id.scoreText);
        headingtextScore = view.findViewById(R.id.headingtextScore);

    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.playbutton:
                Fragment fragment;

                if(typeofview!= null) {
                    if (typeofview.equalsIgnoreCase("ScoreView")) {
                        if (!handledClick){
                            handledClick = true;
                            nextInterface.onTabAddpostion(0);
                        }

                    }else{
                        dismiss();
                    }
                }
               dismiss();
                break;
       }

    }



    public static DialogFragment newInstance(String scoreView, int score_length, Context mContext, NextInterface nextInterface) {
        QuizDialogview fragment=new QuizDialogview();
        fragment.typeofview = scoreView;
//        fragment.mScoretype = scorevariable;
        fragment.mlength = score_length;
        fragment.mContext = mContext;
        fragment.nextInterface = nextInterface;
        Log.i("","Score to be display : "+fragment.mScoretype + "length :"+ score_length );
        return fragment;
    }
}

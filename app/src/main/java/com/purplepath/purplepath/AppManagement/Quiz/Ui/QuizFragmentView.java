package com.purplepath.purplepath.AppManagement.Quiz.Ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.AppManagement.Quiz.model.Quiz;
import com.purplepath.purplepath.AppManagement.Quiz.nextpageinterpage.NextInterface;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;

import static com.purplepath.purplepath.AppManagement.Quiz.Ui.QuizFragmentListview.checktheposition;
import static com.purplepath.purplepath.AppManagement.Quiz.Ui.QuizFragmentListview.scorevariable;
import static com.purplepath.purplepath.AppManagement.Quiz.Ui.QuizFragmentListview.totalSize;
import static com.purplepath.purplepath.AppManagement.Quiz.Ui.QuizFragmentListview.viewPagerPosition;


/**
 * Created by pravinr on 6/14/17.
 */

public class QuizFragmentView extends BaseFragment implements NextInterface, View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private Context mContext;
    private  Quiz quizmodel;
    private CheckBox mcheckbox1,mcheckbox2,mcheckbox3,mcheckbox4;
    private TextView moption1,moption2,moption3,moption4,manswer,mquestion,mquestion_no,mcorrect_answer_toast;
    private  LinearLayout mlayout_wrong_answer;
    private ImageView mwrong_right;
    private FloatingActionButton mdone, mright_arrow_nextpage;
    private LinearLayout mlayout_correct_answer;
    public NextInterface nextInterface;

    public DatePickerCallBackInterface mDatePickerCallBackInterface;
    private ImageView mdummy;

    private int current_postion,viewpagersize;

    private boolean ischecked = false,isbuttonisChecked = false;

    private String checkstatus = "show next quiz";

    String qid,question,op1,op2,op3,op4,answer;



    private OnActivityBackPressedListener mCallBackListener;

    private int mPosition;

    RadioGroup radioGroup;
    RadioButton optionA,optionB,optionC,optionD;
    private ArrayList<Quiz> quizs=new ArrayList<>();

    int size_length;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        backPressedListener= (OnActivityBackPressedListener) mContext;
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }

        catch(Exception e)
        {}
        setRetainInstance(true);
        if (getArguments() != null) {
            if(getArguments().containsKey("quizmodelDetails")) {
                quizmodel = (Quiz) getArguments().getSerializable("quizmodelDetails");

                Log.i("success", "QuizFragmentView" + quizmodel.getQuiz_op1());

            }
            if(getArguments().containsKey("Count"))
                mPosition =  getArguments().getInt("Count");
            Log.i("success", "QuizFragmentView mPosition" + mPosition);
        }

        closeDialogFragment(this);

    }

    public void closeDialogFragment(NextInterface nextInterface) {
        this.nextInterface = nextInterface;
    }

    public void setListener(DatePickerCallBackInterface callbackInterface){
        this.mDatePickerCallBackInterface=callbackInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_quiz_view, container, false);
        backPressedListener.setActionBarTitle("Quiz");


        mquestion_no= view.findViewById(R.id.question_no);

        mlayout_wrong_answer= view.findViewById(R.id.layout_wrong_answer);
        manswer= view.findViewById(R.id.answer);
        mquestion= view.findViewById(R.id.questions);
        mdone= view.findViewById(R.id.done);
        mwrong_right= view.findViewById(R.id.wrong_right);
        mright_arrow_nextpage= view.findViewById(R.id.right_arrow_nextpage);
        mcorrect_answer_toast= view.findViewById(R.id.correct_answer_toast);
        mlayout_correct_answer= view.findViewById(R.id.layout_correct_answer);



        radioGroup= view.findViewById(R.id.radioGroup1);
        optionA= view.findViewById(R.id.optionA);
        optionB= view.findViewById(R.id.optionB);
        optionC = view.findViewById(R.id.optionC);
        optionD= view.findViewById(R.id.optionD);

        mdummy= view.findViewById(R.id.dummy);
        mright_arrow_nextpage.setOnClickListener(this);
        mdone.setOnClickListener(this);
        callQuizService(quizmodel);

        return view;
    }

    public void callQuizService(Quiz quizmodel) {
                try {
                    {
                         qid=quizmodel.getQuiz_id();
                         question=quizmodel.getQuiz_que();
                        op1=quizmodel.getQuiz_op1();
                         op2=quizmodel.getQuiz_op2();
                         op3=quizmodel.getQuiz_op3();
                         op4=quizmodel.getQuiz_op4();
                         answer=quizmodel.getQuiz_ans();
//                        mquestion_no.setText(qid);
                        mquestion_no.setText("Question");
                        mquestion.setText(question);
                        setvalueinoption(quizmodel.getQuiz_op1(),optionA);
                        setvalueinoption(quizmodel.getQuiz_op2(),optionB);
                        setvalueinoption(quizmodel.getQuiz_op3(),optionC);
                        setvalueinoption(quizmodel.getQuiz_op4(),optionD);

                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

    private void setoptioninForWrongAnswer(RadioButton option1, RadioButton option2, RadioButton option3,
                                           TextView manswer, String typeofAnswer,
                                           ImageView mwrong_right, ImageView mdummy,
                                           LinearLayout mlayout_wrong_answer, LinearLayout mlayout_correct_answer,
                                           FloatingActionButton mdone, FloatingActionButton mright_arrow_nextpage, int ic_hand_right, TextView mcorrect_answer_toast,
                                           String answer) {

        try {
            option1.setEnabled(false);
            option2.setEnabled(false);
            option3.setEnabled(false);
            manswer.setText(typeofAnswer);
            manswer.setTextColor(Color.parseColor("#006400"));
            mwrong_right.setImageResource(ic_hand_right);
            mdummy.setImageResource(R.drawable.finobot_loging);
            mlayout_wrong_answer.setVisibility(View.VISIBLE);
            mlayout_correct_answer.setVisibility(View.VISIBLE);
            ischecked = true;
            automaticcheckConditon(ischecked);
            mdone.setVisibility(View.GONE);
            mright_arrow_nextpage.setVisibility(View.VISIBLE);
            mcorrect_answer_toast.setTextColor(Color.parseColor("#006400"));
            mcorrect_answer_toast.setText("Correct answer is : "+answer);
            isbuttonisChecked = true;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setoptioninForCorrectAnswer(RadioButton option1, RadioButton option2, RadioButton option3,
                                             TextView manswer, String typeofAnswer,
                                             ImageView mwrong_right, ImageView mdummy,
                                             LinearLayout mlayout_wrong_answer, LinearLayout mlayout_correct_answer,
                                             FloatingActionButton mdone, FloatingActionButton mright_arrow_nextpage, int ic_hand_right) {
        try {
            option1.setEnabled(false);
            option2.setEnabled(false);
            option3.setEnabled(false);
            manswer.setText(typeofAnswer);
            manswer.setTextColor(Color.parseColor("#006400"));
            mwrong_right.setImageResource(ic_hand_right);
            mdummy.setImageResource(R.drawable.finobot_loging);
            mlayout_wrong_answer.setVisibility(View.VISIBLE);
            mlayout_correct_answer.setVisibility(View.VISIBLE);
            ischecked = true;
            automaticcheckConditon(ischecked);
            mdone.setVisibility(View.GONE);
            mright_arrow_nextpage.setVisibility(View.VISIBLE);
            isbuttonisChecked = true;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setvalueinoption(String quiz_op, RadioButton options) {
        if(UtileKit.validateObjectValuesAndCheckZero(quiz_op)){

            options.setVisibility(View.VISIBLE);
            options.setText(quiz_op);
        }else {
            options.setVisibility(View.GONE);
        }
    }

    private void automaticcheckConditon(boolean ischecked) {

//        if(ischecked){
//            final Handler mTimerHandler = new Handler();
//            final Handler threadHandler = new Handler();
//            new Thread() {
//                @Override
//                public void run() {
//                    threadHandler.postDelayed(new Runnable() {
//                        public void run() {
//                            nextInterface.onTabAddpostion(mPosition+1);
//                        }
//                    }, 2000);
//                }
//            }.start();
//
//        }
    }


    public static Fragment newInstance(int position, Quiz quiz, NextInterface nextInterface,
                                       DatePickerCallBackInterface mDatePickerCallBackInterface,
                                       int update_postion, int size_length) {

        QuizFragmentView fragment = new QuizFragmentView();
        Bundle args = new Bundle();
        args.putInt("Count", position);
        args.putSerializable("quizmodelDetails",quiz);
        fragment.nextInterface=nextInterface;
        fragment.mDatePickerCallBackInterface = mDatePickerCallBackInterface;
        fragment.current_postion = update_postion;

        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public void onTabAddpostion(int count) {

        if(count==0){
            addFragmenttoStack(new QuizFragmentListview());
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.done: {
                isbuttonisChecked = false;

                if (optionA.isChecked()) {
                    if (op1.equals(answer)) {

                        setoptioninForCorrectAnswer(optionB, optionC, optionD, manswer, "Answer is Correct", mwrong_right
                                , mdummy, mlayout_wrong_answer, mlayout_correct_answer, mdone, mright_arrow_nextpage, R.drawable.ic_hand_right);
                        scorevariable = scorevariable + 1;

                    } else {

                        setoptioninForWrongAnswer(optionB, optionC, optionD, manswer, "Answer is Wrong", mwrong_right
                                , mdummy, mlayout_wrong_answer, mlayout_correct_answer, mdone, mright_arrow_nextpage, R.drawable.ic_hand_rong,
                                mcorrect_answer_toast, answer);

                    }
                }


                if (optionB.isChecked()) {
                    if (op2.equals(answer)) {
                        setoptioninForCorrectAnswer(optionA, optionC, optionD, manswer, "Answer is Correct", mwrong_right
                                , mdummy, mlayout_wrong_answer, mlayout_correct_answer, mdone, mright_arrow_nextpage, R.drawable.ic_hand_right);
                        scorevariable = scorevariable + 1;

                    } else {
                        setoptioninForWrongAnswer(optionA, optionC, optionD, manswer, "Answer is Wrong", mwrong_right
                                , mdummy, mlayout_wrong_answer, mlayout_correct_answer, mdone, mright_arrow_nextpage, R.drawable.ic_hand_rong,
                                mcorrect_answer_toast, answer);
                    }
                }
                if (optionC.isChecked()) {
                    if (op3.equals(answer)) {
                        setoptioninForCorrectAnswer(optionA, optionB, optionD, manswer, "Answer is Correct", mwrong_right
                                , mdummy, mlayout_wrong_answer, mlayout_correct_answer, mdone, mright_arrow_nextpage, R.drawable.ic_hand_right);
                        scorevariable = scorevariable + 1;
                    } else {
                        mright_arrow_nextpage.setVisibility(View.VISIBLE);
                        isbuttonisChecked = true;
                        setoptioninForWrongAnswer(optionA, optionB, optionD, manswer, "Answer is Wrong", mwrong_right
                                , mdummy, mlayout_wrong_answer, mlayout_correct_answer, mdone, mright_arrow_nextpage, R.drawable.ic_hand_rong,
                                mcorrect_answer_toast, answer);
                    }
                }

                if (optionD.isChecked()) {
                    if (op4.equals(answer)) {
                        setoptioninForCorrectAnswer(optionA, optionB, optionC, manswer, "Answer is Correct", mwrong_right
                                , mdummy, mlayout_wrong_answer, mlayout_correct_answer, mdone, mright_arrow_nextpage, R.drawable.ic_hand_right);
                        scorevariable = scorevariable + 1;
                    } else {
                        setoptioninForWrongAnswer(optionA, optionB, optionC, manswer, "Answer is Wrong", mwrong_right
                                , mdummy, mlayout_wrong_answer, mlayout_correct_answer, mdone, mright_arrow_nextpage, R.drawable.ic_hand_rong,
                                mcorrect_answer_toast, answer);
                    }
                }


                if (!isbuttonisChecked) {
                    UtileKit.intitializeAlertDialog("Please select any one option", mContext);

                }else{
                    if(totalSize-1 == viewPagerPosition){
                        mright_arrow_nextpage.setVisibility(View.VISIBLE);
                        mdone.setVisibility(View.GONE);
                        mright_arrow_nextpage.setImageResource(R.drawable.ic_done_menu);
                        checkstatus = "displayScore";
                    }else {
                        mDatePickerCallBackInterface.updateEditTextValue(String.valueOf(current_postion + 1), "Scoreview");

                        if (checktheposition == mPosition) {
                            mright_arrow_nextpage.setVisibility(View.VISIBLE);
                            mdone.setVisibility(View.GONE);
                            mright_arrow_nextpage.setImageResource(R.drawable.ic_done_menu);
                            checkstatus = "displayScore";

                        }
                    }
                }

            }
            break;
        case R.id.right_arrow_nextpage:{
                if(checkstatus.equalsIgnoreCase("displayScore")){
                    mDatePickerCallBackInterface.updateEditTextValue(String.valueOf(current_postion + 1), "displayScore");
                }else{
                    nextInterface.onTabAddpostion(current_postion + 1);
                    mDatePickerCallBackInterface.updateEditTextValue(String.valueOf(0), "doneview");
                }
            }
        }



    }
}

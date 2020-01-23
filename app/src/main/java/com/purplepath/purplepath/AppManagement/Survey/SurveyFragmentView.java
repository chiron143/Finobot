package com.purplepath.purplepath.AppManagement.Survey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.finobot.finobot.activity.LoginandSignUpActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Survey.model.Survey;
import com.purplepath.purplepath.AppManagement.Survey.model.Surveymodel;
import com.purplepath.purplepath.AppManagement.Survey.model.Surveytomodel;
import com.purplepath.purplepath.AppManagement.Survey.nextpageinterpage.NextInterface;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.SUCCESSCODE;

/**
 * Created by pravinr on 6/14/17.
 */

public class SurveyFragmentView extends Fragment {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private Context mContext;
    private  Survey surveymodel;
    private CheckBox mcheckbox1,mcheckbox2,mcheckbox3,mcheckbox4;
    private TextView moption1,moption2,moption3,moption4,manswer,mquestion,mquestion_no,mcorrect_answer_toast;
    private  LinearLayout mlayout_wrong_answer;
    private ImageView mwrong_right;
    private FloatingActionButton mdone, mright_arrow_nextpage;
    private LinearLayout mlayout_correct_answer;
    public NextInterface nextInterface;
    private ImageView mdummy;

    private boolean ischecked = false;

    private OnActivityBackPressedListener mCallBackListener;

    private int mPosition;

    RadioGroup radioGroup;
    RadioButton optionA,optionB,optionC,optionD;
    private ArrayList<Survey> quizs=new ArrayList<>();
    public static int silakidum;

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

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
                surveymodel = (Survey) getArguments().getSerializable("quizmodelDetails");

                Log.i("success", "QuizFragmentView" + surveymodel.getOption_1());

            }
            if(getArguments().containsKey("Count"))
                mPosition =  getArguments().getInt("Count");
            Log.i("success", "QuizFragmentView mPosition" + mPosition);
        }
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_survey_viewy, container, false);
        backPressedListener.setActionBarTitle("SURVEY");


        mquestion_no= view.findViewById(R.id.question_no);

        mlayout_wrong_answer= view.findViewById(R.id.layout_wrong_answer);
        manswer= view.findViewById(R.id.answer);
        mquestion= view.findViewById(R.id.questions);
        mdone= view.findViewById(R.id.done);
        mwrong_right= view.findViewById(R.id.wrong_right);
        // mright_arrow_nextpage=(FloatingActionButton) view.findViewById(R.id.right_arrow_nextpage);
        mcorrect_answer_toast= view.findViewById(R.id.correct_answer_toast);
        mlayout_correct_answer= view.findViewById(R.id.layout_correct_answer);



        radioGroup= view.findViewById(R.id.radioGroup1);
        optionA= view.findViewById(R.id.optionA);
        optionB= view.findViewById(R.id.optionB);
        optionC = view.findViewById(R.id.optionC);


        mdummy= view.findViewById(R.id.dummy);


        callQuizService();
        return view;
    }

    public void callQuizService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Surveymodel> call = webServiceObj.getSurveyService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Surveymodel>() {
            @Override
            public void onResponse(Call<Surveymodel> call, Response<Surveymodel> response) {
                UtileKit.dismisssSpinnerDialog();
                Log.e("success", "" + response.body());
                final Surveymodel surveymodel = response.body();

                try {
                    if(surveymodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        final String qid=surveymodel.getData().getQuiz().get(mPosition).getSurvey_ques_id();
                        String question=surveymodel.getData().getQuiz().get(mPosition).getQuestion();
                        final String op1=surveymodel.getData().getQuiz().get(mPosition).getOption_1();
                        final String op2=surveymodel.getData().getQuiz().get(mPosition).getOption_2();
                        final String op3=surveymodel.getData().getQuiz().get(mPosition).getOption_3();




                        String array[] = new String[surveymodel.getData().getQuiz().size()];
                        for(int j =0;j<surveymodel.getData().getQuiz().size();j++){
                            array[j] = String.valueOf(surveymodel.getData().getQuiz().get(j));
                        }
                        for(String k: array)
                        {
                            Log.d("hi","arraytostring"+k);
                        }
                        mquestion_no.setText(qid);
                        mquestion.setText(question);
                        optionA.setText(op1);
                        optionB.setText(op2);
                        optionC.setText(op3);


                        for(int i=1; i < mPosition; i++) {
                            if(i == mPosition - 1) {
                                Log.d("hi","lastposition"+i);
                                //  mright_arrow_nextpage.setVisibility(View.GONE);
                            }
                        }

                      /* mright_arrow_nextpage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                nextInterface.onTabAddpostion(mPosition+1);

                            }
                        });*/

                        mdone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if(optionA.isChecked()){
                                    //   mdone.setVisibility(View.GONE);

//                                    if(op1.equals(answer)){
//
//                                        optionB.setEnabled(false);
//                                        optionC.setEnabled(false);
//                                        optionD.setEnabled(false);
//
//                                        manswer.setText("Answer is Correct");
//                                        manswer.setTextColor(Color.parseColor("#006400"));
//                                        mwrong_right.setImageResource(R.drawable.ic_hand_right);
//                                        mdummy.setImageResource(R.drawable.ic_finobot_icon);
//                                        mlayout_wrong_answer.setVisibility(View.VISIBLE);
//                                        mlayout_correct_answer.setVisibility(View.VISIBLE);
//                                        ischecked = true;
//                                        automaticcheckConditon(ischecked);
//
//                                    }
//                                    else {
//
//                                        optionB.setEnabled(false);
//                                        optionC.setEnabled(false);
//                                        optionD.setEnabled(false);
//
//                                        manswer.setText("Answer is Wrong");
//                                        manswer.setTextColor(Color.parseColor("#FF0000"));
//                                        mwrong_right.setImageResource(R.drawable.ic_hand_rong);
//
//                                        mcorrect_answer_toast.setTextColor(Color.parseColor("#006400"));
//                                        mcorrect_answer_toast.setText("Correct answer is : "+answer);
//                                        mlayout_correct_answer.setVisibility(View.VISIBLE);
//                                        mdummy.setImageResource(R.drawable.ic_finobot_icon);
//                                        mlayout_wrong_answer.setVisibility(View.VISIBLE);
//                                        ischecked = true;
//                                        automaticcheckConditon(ischecked);
//
//                                    }

                                    String opt = (String) optionA.getText();
                                    sendPost(qid,opt);

                                }


                                if(optionB.isChecked()){

                                    //   mdone.setVisibility(View.GONE);
//                                    if(op2.equals(answer)){
//                                        optionA.setEnabled(false);
//                                        optionC.setEnabled(false);
//                                        optionD.setEnabled(false);
//                                        manswer.setTextColor(Color.parseColor("#006400"));
//                                        manswer.setText("Answer is Correct");
//                                        mwrong_right.setImageResource(R.drawable.ic_hand_right);
//                                        mdummy.setImageResource(R.drawable.ic_finobot_icon);
//                                        mlayout_wrong_answer.setVisibility(View.VISIBLE);
//                                        mlayout_correct_answer.setVisibility(View.VISIBLE);
//                                        ischecked = true;
//                                        automaticcheckConditon(ischecked);
//
//
//                                    }
//                                    else {
//                                        optionA.setEnabled(false);
//                                        optionC.setEnabled(false);
//                                        optionD.setEnabled(false);
//                                        manswer.setText("Answer is Wrong");
//                                        manswer.setTextColor(Color.parseColor("#FF0000"));
//                                        mwrong_right.setImageResource(R.drawable.ic_hand_rong);
//                                        mcorrect_answer_toast.setTextColor(Color.parseColor("#006400"));
//                                        mcorrect_answer_toast.setText("Correct answer is : "+answer);
//                                        mlayout_correct_answer.setVisibility(View.VISIBLE);
//                                        mdummy.setImageResource(R.drawable.ic_finobot_icon);
//                                        mlayout_wrong_answer.setVisibility(View.VISIBLE);
//                                        ischecked = true;
//                                        automaticcheckConditon(ischecked);
//
//                                    }


                                    String opt = (String) optionB.getText();
                                    sendPost(qid,opt);

                                }
                                if(optionC.isChecked()){
                                    // mdone.setVisibility(View.GONE);

//                                    if(op3.equals(answer)){
//                                        optionA.setEnabled(false);
//                                        optionB.setEnabled(false);
//                                        optionD.setEnabled(false);
//                                        manswer.setText("Answer is Correct");
//                                        manswer.setTextColor(Color.parseColor("#006400"));
//                                        mwrong_right.setImageResource(R.drawable.ic_hand_right);
//                                        mdummy.setImageResource(R.drawable.ic_finobot_icon);
//                                        mlayout_wrong_answer.setVisibility(View.VISIBLE);
//                                        mlayout_correct_answer.setVisibility(View.VISIBLE);
//                                        ischecked = true;
//                                        automaticcheckConditon(ischecked);
//                                    }
//                                    else {
//
//                                        optionA.setEnabled(false);
//                                        optionB.setEnabled(false);
//                                        optionD.setEnabled(false);
//                                        manswer.setText("Answer is Wrong");
//                                        manswer.setTextColor(Color.parseColor("#FF0000"));
//                                        mwrong_right.setImageResource(R.drawable.ic_hand_rong);
//                                        mcorrect_answer_toast.setTextColor(Color.parseColor("#006400"));
//                                        mcorrect_answer_toast.setText("Correct answer is : "+answer);
//                                        mlayout_correct_answer.setVisibility(View.VISIBLE);
//                                        mdummy.setImageResource(R.drawable.ic_finobot_icon);
//                                        mlayout_wrong_answer.setVisibility(View.VISIBLE);
//                                        ischecked = true;
//                                        automaticcheckConditon(ischecked);
//
//                                    }


                                    String opt = (String) optionC.getText();

                                    sendPost(qid,opt);

                                }


                            }
                        });
                    }
                    else{
                        intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Surveymodel> call, Throwable t) {

                Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    public void sendPost(String sid, final String opt) {

        WebServiceCalls webServiceObj2;
        webServiceObj2 = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Surveytomodel> call = webServiceObj2.getSurveytoService(UtileKit.getPersistedPurplePathPref("user_id"),sid,opt);
        call.enqueue(new Callback<Surveytomodel>() {
            @Override
            public void onResponse(Call<Surveytomodel> call, Response<Surveytomodel> response) {

                try {
                    Surveytomodel surtomodel = response.body();

                    if (surtomodel.getStatus_code().equalsIgnoreCase(SUCCESSCODE)) {
                        Log.e("Hello","Done");
                        intitializeAlertDialog(getString(R.string.successfullysur),mContext);
                    }
                    else {
                        intitializeAlertDialog(surtomodel.getData().getMessage(),mContext);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Surveytomodel> call, Throwable t) {

                // Log.e(TAG, "Unable to submit post to API.");
                Log.e("CallBack", " failure is " + t);


                intitializeAlertDialog(getString(R.string.successfullysur),mContext);
            }
        });
    }

    private void intitializeAlertDialog(String string, Context mContext) {
        LayoutInflater inflater;
        View dialogView;
        final AlertDialog alertDialogs;
        try {
            inflater = LayoutInflater.from(mContext);
            dialogView = inflater.inflate(R.layout.alert_message_layout, null);
            alertDialogs = new AlertDialog.Builder(mContext).create();
            alertDialogs.setView(dialogView);
            TextView erroreMessage = dialogView.findViewById(R.id.textViewDilog);

            erroreMessage.setText(string);
            dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //                    SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
//                    SharedPreferences.Editor edt = pref.edit();
//                    edt.putString("off", "dontcheck");
//                    edt.commit();
                    silakidum = 1;
                    LoginandSignUpActivity.checkoff = 1;
                    alertDialogs.dismiss();
                    Intent i=new Intent(getActivity(), HomePageActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            });
//            dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    alertDialog.dismiss();
//                }
//            });
            alertDialogs.show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void automaticcheckConditon(boolean ischecked) {

        if(ischecked){
            final Handler mTimerHandler = new Handler();
            final Handler threadHandler = new Handler();
            new Thread() {
                @Override
                public void run() {
                    threadHandler.postDelayed(new Runnable() {
                        public void run() {
                            nextInterface.onTabAddpostion(mPosition+1);
                        }
                    }, 2000);
                }
            }.start();

        }
    }
    public static Fragment newInstance(int position, Survey quiz, NextInterface nextInterface) {

        SurveyFragmentView fragment = new SurveyFragmentView();
        Bundle args = new Bundle();
        args.putInt("Count", position);
        args.putSerializable("quizmodelDetails",quiz);
        fragment.nextInterface=nextInterface;
        fragment.setArguments(args);
        return fragment;


    }
}

package com.purplepath.purplepath.AppManagement.Quiz.Ui;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Quiz.adapter.PagerAdapters;
import com.purplepath.purplepath.AppManagement.Quiz.dialogView.QuizDialogview;
import com.purplepath.purplepath.AppManagement.Quiz.model.Quizmodel;
import com.purplepath.purplepath.AppManagement.Quiz.nextpageinterpage.NextInterface;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.DatePickerCallBackInterface;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.AppManagement.Quiz.Ui.QuizFragmentListview.checktheposition;
import static com.purplepath.purplepath.AppManagement.Quiz.Ui.QuizFragmentListview.totalSize;
import static com.purplepath.purplepath.AppManagement.Quiz.Ui.QuizFragmentListview.viewPagerPosition;



public class QuizFragment extends BaseFragment implements NextInterface,View.OnClickListener ,DatePickerCallBackInterface{

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    public PagerAdapters adapter=null;
    public  ViewPager mViewPager=null;
    Context mContext;
    public  Quizmodel quizmodels;
    public NextInterface nextInterface;

    public DatePickerCallBackInterface mDatePickerCallBackInterface;

    private String typeofQuiz;

    private boolean isdialogshow;

    int size_length, current_position=0;


    public static ImageView  mright_arrow_nextpage;

    public void setNextInterface(NextInterface nextInterface) {
        this.nextInterface = nextInterface;
    }

    public void setScorePosition(DatePickerCallBackInterface mDatePickerCallBackInterface) {
        this.mDatePickerCallBackInterface = mDatePickerCallBackInterface;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setNextInterface(this);
        setScorePosition(this);
        backPressedListener= (OnActivityBackPressedListener) mContext;
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_quiz, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backPressedListener.setActionBarTitle("QUIZ");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mViewPager = view.findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("hi","arraytostring onPageScrolled"+position );
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("hi","arraytostring onPageSelected"+position );
                current_position = position;
                viewPagerPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        if (isdialogshow) {

            FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
            DialogFragment newFragment = new QuizDialogview();

            newFragment.show(fm, "dialog");
        }


        if(typeofQuiz!= null) {
            callQuizServiceByCallingTopic(typeofQuiz);
        }
    }

    public void callQuizServiceByCallingTopic(final String typeofQuiz) {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Quizmodel> call = webServiceObj.getquizbytopic(typeofQuiz);
        call.enqueue(new Callback<Quizmodel>() {
            @Override
            public void onResponse(Call<Quizmodel> call, Response<Quizmodel> response) {
                UtileKit.dismisssSpinnerDialog();
                 quizmodels = response.body();
                try {
                    if(quizmodels.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if(quizmodels!= null) {
                            try {
                                size_length = quizmodels.getData().getQuiz().size();
                                totalSize =size_length;
                                adapter = new PagerAdapters(getChildFragmentManager(), quizmodels, nextInterface,
                                        mDatePickerCallBackInterface,current_position, size_length);
                                mViewPager.setAdapter(adapter);
                                mViewPager.setOffscreenPageLimit(quizmodels.getData().getQuiz().size());
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    else{
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Quizmodel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }


    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }

    }

    @Override
    public void onTabAddpostion(int count) {
        backPressedListener.onActivityBackPressed();
    }

    public static QuizFragment newInstance(String topicstring, boolean isshowdialog) {

        QuizFragment fragment=new QuizFragment();
        fragment.typeofQuiz = topicstring;
        fragment.isdialogshow = isshowdialog;
        Log.i("","typeofString"+fragment.typeofQuiz);
        return fragment;
    }
    /*
     * current_position is used to viewpager current position
     * size_length is used to get Full length in quiz
     * QuizDialogview is used to display score pop up
     * checktheposition is used to display pop
     * */
    @Override
    public void updateEditTextValue(String value, String title) {

        if(title.equalsIgnoreCase("displayScore")) {
            if (current_position == size_length - 1) {
                FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
                DialogFragment newFragment = QuizDialogview.newInstance("ScoreView",
                        size_length, mContext, nextInterface);
                newFragment.show(fm, "dialog");
            }
        }else if(title.equalsIgnoreCase("doneview")){
            mViewPager.setCurrentItem(current_position + 1, true);
        }
        else {
            checktheposition = checktheposition + Integer.parseInt(value);
        }

    }

    @Override
    public void updateIndividualEditTextValue(String value, String title) {

    }
}

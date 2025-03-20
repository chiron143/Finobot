package com.purplepath.purplepath.AppManagement.Quiz.Ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.AppManagement.Quiz.adapter.CustomQuizListTopicAdapter;
import com.purplepath.purplepath.AppManagement.Quiz.topicModels.GetQuizTopics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Suresh on 04/09/17.
 */

public class QuizFragmentListview  extends BaseFragment implements View.OnClickListener{

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    Context mContext;

    private  GetQuizTopics mGetQuizTopics;

    private CustomQuizListTopicAdapter mCustomQuizListTopicAdapter;

    private ListView mlistview;

    public  boolean isshowdialog = false;

    public static String typeofTopic;
    public  static  int scorevariable = 0 ,checktheposition  = 0, totalSize =  0, viewPagerPosition =0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext= getActivity();
        mCallBackListener = (OnActivityBackPressedListener) (mContext);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quizlistfragment, container, false);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        // mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        mlistview = view.findViewById(R.id.quiz_listview);

        try {
            mCallBackListener.setActionBarTitle("Quiz");
        } catch (Exception e) {
            e.printStackTrace();
        }
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        // mRightRelativeLayout.setOnClickListener(this);

        callWebservicesListview();


        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onclickPostionStartFragment(position,mGetQuizTopics);
            }
        });


        return view;
    }

    private void onclickPostionStartFragment(int position, GetQuizTopics mGetQuizTopics) {
        scorevariable =0;
        checktheposition = 0;
        totalSize =0;viewPagerPosition =0;
        isshowdialog = true;
        String titlestring = mGetQuizTopics.getData().getQuiz_topics().get(position).getQuiz_topic();
        Log.i("Document grid fragment"," validatestring  " + titlestring);
        typeofTopic = titlestring;
        // Strat next Fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        QuizFragment fragment = QuizFragment.newInstance(titlestring, isshowdialog);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void callWebservicesListview() {

        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);

        Call<GetQuizTopics> call = webServiceObj.getQuizService();
        call.enqueue(new retrofit2.Callback<GetQuizTopics>() {
            @Override
            public void onResponse(Call<GetQuizTopics> call, Response<GetQuizTopics> response) {
                UtileKit.dismisssSpinnerDialog();
                mGetQuizTopics = response.body();

                setvalueinAdapter(mGetQuizTopics);
            }

            @Override
            public void onFailure(Call<GetQuizTopics> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }

    private void setvalueinAdapter(GetQuizTopics mGetQuizTopics) {

        if(mGetQuizTopics!= null){
            mCustomQuizListTopicAdapter = new CustomQuizListTopicAdapter(mGetQuizTopics, mContext);
            mlistview.setAdapter(mCustomQuizListTopicAdapter);

        }
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
//                getActivity().finish();
            }
            break;
        }
    }
}

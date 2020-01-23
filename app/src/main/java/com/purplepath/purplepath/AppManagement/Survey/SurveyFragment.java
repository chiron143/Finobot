package com.purplepath.purplepath.AppManagement.Survey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.purplepath.purplepath.AppManagement.Survey.adapter.PagerAdapters;
import com.purplepath.purplepath.AppManagement.Survey.model.Surveymodel;
import com.purplepath.purplepath.AppManagement.Survey.nextpageinterpage.NextInterface;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SurveyFragment extends BaseFragment implements NextInterface,View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    public PagerAdapters adapter=null;
    public  ViewPager mViewPager=null;
    Context mContext;
    public static Surveymodel quizmodel=null;
    public NextInterface nextInterface;




    public static ImageView  mright_arrow_nextpage;

    public void setNextInterface(NextInterface nextInterface) {
        this.nextInterface = nextInterface;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setNextInterface(this);
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_survey, container, false);
        backPressedListener= (OnActivityBackPressedListener) getContext();
        backPressedListener.setActionBarTitle("SURVEY");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mViewPager = view.findViewById(R.id.viewpager_survey);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

       // mright_arrow_nextpage=(ImageView)view.findViewById(R.id.right_arrow_nextpage);

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
                Surveymodel quizmodels = response.body();

                try {
                    if(quizmodels.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if(quizmodels!= null) {
                            try {
                                Log.e("success", "quizmodel.getData().getQuiz()" + quizmodels.getData().getQuiz().size());
                                quizmodel = quizmodels;
                                adapter = new PagerAdapters(getChildFragmentManager(), quizmodel, nextInterface);
                                mViewPager.setAdapter(adapter);
                                mViewPager.setOffscreenPageLimit(quizmodels.getData().getQuiz().size());
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                            /*mright_arrow_nextpage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mViewPager.setCurrentItem(getItem(+1), true);
                                }
                            });*/
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
            public void onFailure(Call<Surveymodel> call, Throwable t) {
                Log.e("CallBack", " failure is " + t);

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
        Log.e("CallBack", " onTabAddpostion count" + count);
        mViewPager.setCurrentItem(count, true);
    }


}

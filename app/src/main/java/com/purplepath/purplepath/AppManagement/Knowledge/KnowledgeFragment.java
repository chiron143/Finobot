package com.purplepath.purplepath.AppManagement.Knowledge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Knowledge.adapter.KnowledgeExpandableAdapter;
import com.purplepath.purplepath.AppManagement.Knowledge.model.Knowledge;
import com.purplepath.purplepath.AppManagement.Knowledge.model.Knowledgemodel;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KnowledgeFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    //RecyclerView recyclerView;
    private Context mContext;
   // KnowledgeAdapter knowledgeAdapter;
    private ArrayList<Knowledge> mKnowledge=new ArrayList<>();

    Knowledgemodel knowledgemodel;

    ExpandableListView knowledge_exp_lv;
    KnowledgeExpandableAdapter knowledgeExpandableAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*recyclerView = (RecyclerView) view.findViewById(R.id.listviews);
        recyclerView.setNestedScrollingEnabled(false);*/
        knowledge_exp_lv= view.findViewById(R.id.know_exp_lv);
//        knowledge_exp_lv.setNestedScrollingEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_knowledge, container, false);
        backPressedListener.setActionBarTitle("Knowledge");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        callKnowledgeService();
        return view;
    }





    public void callKnowledgeService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(getActivity(), false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Knowledgemodel> call = webServiceObj.getKnowledgeService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Knowledgemodel>() {
            @Override
            public void onResponse(Call<Knowledgemodel> call, Response<Knowledgemodel> response) {
                UtileKit.dismisssSpinnerDialog();
//                //Log.e("success", "" + response.body());

                 knowledgemodel=response.body();
                try{
                    if (knowledgemodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if (null != knowledgemodel.getData().getKnowledge()) {

                           // mKnowledge=knowledgemodel.getData().getKnowledge();


                            knowledgeExpandableAdapter=new KnowledgeExpandableAdapter(mContext,knowledgemodel);
                            knowledge_exp_lv.setAdapter(knowledgeExpandableAdapter);

                            /*knowledgeAdapter = new KnowledgeAdapter(getActivity(),mKnowledge,knowledgemodel);
                            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(llm);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(knowledgeAdapter);*/
                        }
                    }else{
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }}catch (Exception e) {
                    e.printStackTrace();
                }
                //  UtileKit.dismisssSpinnerDialog();
            }
            @Override
            public void onFailure(Call<Knowledgemodel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( getActivity(),t);
            }
        });
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
}

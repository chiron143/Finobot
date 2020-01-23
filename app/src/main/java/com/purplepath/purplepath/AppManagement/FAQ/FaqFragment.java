package com.purplepath.purplepath.AppManagement.FAQ;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.FAQ.adapter.Faqexpandableadapter;
import com.purplepath.purplepath.AppManagement.FAQ.model.FAQ;
import com.purplepath.purplepath.AppManagement.FAQ.model.Faqdata;
import com.purplepath.purplepath.AppManagement.FAQ.model.Faqmodel;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FaqFragment extends Fragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    RecyclerView recyclerView;
    private Faqdata mfaqdata;
    private Context mContext;
    private ArrayList<FAQ> mFAQ=new ArrayList<>();

    ArrayList<String> listDataHeader;

    ExpandableListView faq_exp_lv;
    Faqexpandableadapter faqexpandableadapter;
    //Faqadapter faqadapter;


    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext = getContext();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_faq, container, false);
            backPressedListener.setActionBarTitle("FAQs");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);



        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        callFaqService();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // recyclerView = (RecyclerView) view.findViewById(R.id.list);

        faq_exp_lv= view.findViewById(R.id.faq_exp_lv);

       // explvlist = (ExpandableListView)view.findViewById(R.id.ParentLevel);
        //explvlist.setAdapter(new Parentadapter());
    }


    public void callFaqService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(getActivity(), false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Faqmodel> call = webServiceObj.getFAQService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Faqmodel>() {
            @Override
            public void onResponse(Call<Faqmodel> call, Response<Faqmodel> response) {
                UtileKit.dismisssSpinnerDialog();
//                //Log.e("success", "" + response.body());
                Faqmodel faqmodel = response.body();
                try {


                if (faqmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if (null != faqmodel.getData().getFaq()) {
                        mFAQ=faqmodel.getData().getFaq();


                        faqexpandableadapter=new Faqexpandableadapter(mContext,faqmodel);
                        faq_exp_lv.setAdapter(faqexpandableadapter);

                        /*faqadapter = new Faqadapter(getActivity(),mFAQ,faqmodel);
                        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(llm);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(faqadapter);
                        faqadapter.notifyDataSetChanged();*/

                    }
                }else{
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                UtileKit.dismisssSpinnerDialog();
            }
            @Override
            public void onFailure(Call<Faqmodel> call, Throwable t) {
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
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;

        }

    }
}

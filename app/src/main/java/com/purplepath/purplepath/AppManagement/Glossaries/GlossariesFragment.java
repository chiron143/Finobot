package com.purplepath.purplepath.AppManagement.Glossaries;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Glossaries.Adapter.GlossariesAdapter;
import com.purplepath.purplepath.AppManagement.Glossaries.Models.GlossariesModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.getPersistedPurplePathPref;


public class GlossariesFragment extends Fragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private Context mContext;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private android.widget.ExpandableListView glosarries_exp_lv;
    GlossariesAdapter glossariesAdapter;
    public GlossariesFragment() {

        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        mContext=context;
        super.onAttach(context);
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
        View view=inflater.inflate(R.layout.fragment_glossaries, container, false);
        backPressedListener.setActionBarTitle("Glossary");
        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);
        glosarries_exp_lv= (android.widget.ExpandableListView) view.findViewById(R.id.glosarries_exp_lv);


        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        callGlossariesWebService();


        return view;
    }

    private void callGlossariesWebService() {
        WebServiceCalls obj= ServiceGenerator.createService(WebServiceCalls.class);
        Call<GlossariesModel> call= obj.getGlossariesService(getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GlossariesModel>() {
            @Override
            public void onResponse(Call<GlossariesModel> call, Response<GlossariesModel> response) {
                GlossariesModel glossariesModel=response.body();
                glossariesAdapter=new GlossariesAdapter(mContext,glossariesModel);
                glosarries_exp_lv.setAdapter(glossariesAdapter);

            }

            @Override
            public void onFailure(Call<GlossariesModel> call, Throwable t) {

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

package com.purplepath.purplepath.Notification.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.purplepath.purplepath.Notification.Models.PromptsModel;
import com.purplepath.purplepath.Notification.adapters.PromptsRecyclerViewAdapter;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pratheep.S on 19-01-2017.
 */

public class PromptsFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private PromptsRecyclerViewAdapter mPromptsRecyclerViewAdapter;
    private ArrayList<String> mMissingDetails=new ArrayList<>();
    private static Context mContext;
    private TextView mNoAlertsTextView;
    private FragmentManager mFragmentManager;
    String alldetails []={"Personal Details","Family Details"};
    String personalDetails []={"Personal Details"};
    String familyDetails []={"Family Details"};

    ArrayList<String> promptDetails=new ArrayList<>();
    private PromptsModel promptsModel;
    //"Income Details","Expense Details"
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_prompts,container,false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView= view.findViewById(R.id.recylerView_Prompts);
        mContext=getContext();
        mNoAlertsTextView = view.findViewById(R.id.tv_noContent);
        mLinearLayoutManager=new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFragmentManager=getFragmentManager();
        Log.i("spcheck","user id "+UtileKit.getPersistedPurplePathPref("user_id"));

        callPromptsWebService();
    }

    private void callPromptsWebService() {
        UtileKit.showSpinnerDialog(mContext,false);
        Call<PromptsModel> call=ServiceGenerator.createService(WebServiceCalls.class).callPromptsService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<PromptsModel>() {
            @Override
            public void onResponse(Call<PromptsModel> call, Response<PromptsModel> response) {
                UtileKit.dismisssSpinnerDialog();
                promptsModel=response.body();
                if(promptsModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                    if (promptsModel.getData().getPersonal_fields() != null
                            &&promptsModel.getData().getFamily_fields() != null ) {
                        mPromptsRecyclerViewAdapter = new PromptsRecyclerViewAdapter(mContext, promptsModel, alldetails, mFragmentManager);
                        mRecyclerView.setAdapter(mPromptsRecyclerViewAdapter);
                        Log.i("spcheck", "Personal & family details not empty");
                    }else if(promptsModel.getData().getPersonal_fields() != null){
                        mPromptsRecyclerViewAdapter = new PromptsRecyclerViewAdapter(mContext, promptsModel,personalDetails, mFragmentManager);
                        mRecyclerView.setAdapter(mPromptsRecyclerViewAdapter);
                        Log.i("spcheck", "Personal details alone is not empty");
                    }else if(promptsModel.getData().getFamily_fields() != null){
                        mPromptsRecyclerViewAdapter = new PromptsRecyclerViewAdapter(mContext, promptsModel,familyDetails, mFragmentManager);
                        mRecyclerView.setAdapter(mPromptsRecyclerViewAdapter);
                        Log.i("spcheck", "Personal details alone is not empty");
                    }
                }else if(response.body().getStatus_code().equals("100")) {
                    mNoAlertsTextView.setText("You have entered the required details");
                    mNoAlertsTextView.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
//                    UtileKit.showToastShort(mContext, mContext.getString(R.string.noInfoMsg));
                }
            }

            @Override
            public void onFailure(Call<PromptsModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }
}

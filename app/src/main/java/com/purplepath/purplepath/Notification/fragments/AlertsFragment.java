package com.purplepath.purplepath.Notification.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.purplepath.purplepath.Notification.Models.AlertsModel;
import com.purplepath.purplepath.Notification.adapters.AlertsRecyclerViewAdapter;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pratheep.S on 19-01-2017.
 */

public class AlertsFragment extends Fragment {
    private RecyclerView mRecclerView;
    private AlertsRecyclerViewAdapter mAlertsRecyclerViewAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private AlertsModel mAlertsModel;
    private TextView mNoAlertsTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alerts, container, false);
        mRecclerView = view.findViewById(R.id.rv_alert);
        mNoAlertsTextView = view.findViewById(R.id.tv_noContent);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecclerView.setLayoutManager(mLinearLayoutManager);
        callAlertWebService();
        return view;
    }

    private void callAlertWebService() {
        //UtileKit.getPersistedPurplePathPref("user_id")
        Call<AlertsModel> call = (ServiceGenerator.createService(WebServiceCalls.class)).callAlertsService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<AlertsModel>() {
            @Override
            public void onResponse(Call<AlertsModel> call, Response<AlertsModel> response) {
                mAlertsModel = response.body();
                if (mAlertsModel.getStatus().equalsIgnoreCase("error")) {
                    mNoAlertsTextView.setVisibility(View.VISIBLE);

                } else {
                    Log.i("spcheck", "onResponse: Success");
                    mNoAlertsTextView.setVisibility(View.GONE);
                    mAlertsRecyclerViewAdapter = new AlertsRecyclerViewAdapter(mAlertsModel);
                    mRecclerView.setAdapter(mAlertsRecyclerViewAdapter);
                }

            }

            @Override
            public void onFailure(Call<AlertsModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( getActivity(),t);
            }
        });

    }
}

package com.purplepath.purplepath.Notification.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.Notification.Models.NotificationsModel;
import com.purplepath.purplepath.Notification.adapters.NewAnnouncementAdapter;
import com.purplepath.purplepath.Notification.adapters.NotificationRecyclerViewAdapter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.model.homeCardModel.HomeCardsModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pratheep.S on 17-01-2017.
 */

public class AnnouncementFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private NotificationRecyclerViewAdapter mNotificationRecyclerViewAdapter;
    private NotificationsModel mNotificationsModel;
    private TextView mNoAnnouncementTextView;
    private Context mContext;
    private HomeCardsModel homeCardsModel;
    private NewAnnouncementAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_notification, container, false);
        mContext = getContext();
        mRecyclerView = view.findViewById(R.id.rv_announcements);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mNoAnnouncementTextView = view.findViewById(R.id.tv_noContent);

        callHomeAllCardsService();
        //callAnnouncementsWebService();
        return view;
    }

    private void callHomeAllCardsService() {
        UtileKit.showSpinnerDialog(mContext, false);
        final String userName = UtileKit.getPersistedPurplePathPref("name_services", null);
        Call<HomeCardsModel> call = ServiceGenerator.createService(WebServiceCalls.class)
                .getHomeAllCardsService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<HomeCardsModel>() {
            @Override
            public void onResponse(Call<HomeCardsModel> call, Response<HomeCardsModel> response) {
                UtileKit.dismisssSpinnerDialog();
                homeCardsModel = response.body();
                if (homeCardsModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if (null != homeCardsModel.getData()) {
                        if (null != homeCardsModel.getData().getCard2()) {//Announcements
                            mNoAnnouncementTextView.setVisibility(View.GONE);
                            adapter = new NewAnnouncementAdapter(homeCardsModel);
                            mRecyclerView.setAdapter(adapter);
                        }

                    }

                } else {
                    mNoAnnouncementTextView.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onFailure(Call<HomeCardsModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(getActivity(), t);
            }
        });

    }

    private void callAnnouncementsWebService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls callObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<NotificationsModel> call = callObj.callNotificationService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<NotificationsModel>() {
            @Override
            public void onResponse(Call<NotificationsModel> call, Response<NotificationsModel> response) {
                UtileKit.dismisssSpinnerDialog();
                mNotificationsModel = response.body();
                if (mNotificationsModel.getData().getOther_notifications() == null) {
                    mNoAnnouncementTextView.setVisibility(View.VISIBLE);
                } else {
                    mNoAnnouncementTextView.setVisibility(View.GONE);
                    mNotificationRecyclerViewAdapter = new NotificationRecyclerViewAdapter(mNotificationsModel, 2);
                    mRecyclerView.setAdapter(mNotificationRecyclerViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<NotificationsModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(getActivity(), t);
            }
        });
    }
}

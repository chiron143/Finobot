package com.purplepath.purplepath.Notification.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.Notification.Models.NotificationsModel;
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

public class NotificationFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private NotificationRecyclerViewAdapter mNotificationRecyclerViewAdapter;
    private NotificationsModel mNotificationsModel;
    private TextView mNoNotificationsTextView;
    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user_notification,container,false);
        mContext=getContext();
        mNoNotificationsTextView= view.findViewById(R.id.tv_noContent);
        callNotificationWebService();


        mRecyclerView= view.findViewById(R.id.rv_notification);

        mLinearLayoutManager=new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

       /* mNotificationRecyclerViewAdapter=new NotificationRecyclerViewAdapter(mNotificationsModel);
        mRecyclerView.setAdapter(mNotificationRecyclerViewAdapter);*/

        return view;
    }




    private void callNotificationWebService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls callObj= ServiceGenerator.createService(WebServiceCalls.class);
        Call<NotificationsModel> call =callObj.callNotificationService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<NotificationsModel>() {
            @Override
            public void onResponse(Call<NotificationsModel> call, Response<NotificationsModel> response) {
                UtileKit.dismisssSpinnerDialog();
                mNotificationsModel=response.body();
                if(mNotificationsModel.getData().getUser_notifications()==null){
                    mNoNotificationsTextView.setVisibility(View.VISIBLE);
                }
                else {
                    mNoNotificationsTextView.setVisibility(View.GONE);
                    mNotificationRecyclerViewAdapter = new NotificationRecyclerViewAdapter(mNotificationsModel, 1);
                    mRecyclerView.setAdapter(mNotificationRecyclerViewAdapter);
                    Log.i("spcheck", "onResponse: ");
                }
            }

            @Override
            public void onFailure(Call<NotificationsModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( getActivity(),t);
            }
        });
    }

}

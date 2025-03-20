package com.purplepath.purplepath.AppManagement.Links;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Links.adapter.LinksAdapter;
import com.purplepath.purplepath.AppManagement.Links.model.Links;
import com.purplepath.purplepath.AppManagement.Links.model.Linksmodel;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LinksFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    ListView recyclerView;
    private Context mContext;
    LinksAdapter linksAdapter;
    private ArrayList<Links> mLinks=new ArrayList<>();

    public LinksFragment() {
    }

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.list);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_links, container, false);
        backPressedListener.setActionBarTitle("Links");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        callLinksService();


        return view;
    }


    public void callLinksService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(getActivity(), false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Linksmodel> call = webServiceObj.getLinksService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Linksmodel>() {
            @Override
            public void onResponse(Call<Linksmodel> call, Response<Linksmodel> response) {
                UtileKit.dismisssSpinnerDialog();
//                //Log.e("success", "" + response.body());
                Linksmodel linksmodel = response.body();
                try{
                if (linksmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if (null != linksmodel.getData().getLinks()) {

                        mLinks=linksmodel.getData().getLinks();
                        linksAdapter = new LinksAdapter(getActivity(),mLinks,linksmodel);
//                        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//                        llm.setOrientation(LinearLayoutManager.VERTICAL);
//                        recyclerView.setLayoutManager(llm);
//                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(linksAdapter);

                        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String uri = mLinks.get(i).getUrl();
                                Intent starturl = new Intent(Intent.ACTION_VIEW,Uri.parse(uri));
                                startActivity(starturl);

                            }
                        });
                    }
                }else{
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }}catch (Exception e) {
                e.printStackTrace();
                }
              //  UtileKit.dismisssSpinnerDialog();
            }
            @Override
            public void onFailure(Call<Linksmodel> call, Throwable t) {
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

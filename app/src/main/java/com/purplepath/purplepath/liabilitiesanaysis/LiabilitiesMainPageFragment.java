package com.purplepath.purplepath.liabilitiesanaysis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assetsanalysis.model.AssestAnalysisModel;
import com.purplepath.purplepath.customview.CurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.liabilities.LiabilitiesTabViewFragment;
import com.purplepath.purplepath.liabilitiesanaysis.model.Liab_Anaysis_Model;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiabilitiesMainPageFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private AssestAnalysisModel mAssetsAnalysisModel;
    private String mmliabilities_total_value;
    //private long mmliabilities_total_value =0;
    private Context mContext;

    private TextView errorTextview;
    private TextView mliabilities_total_value;
    private Liab_Anaysis_Model mliab_anaysis_model;
    private FloatingActionButton mEditFloatingBtn;


    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_liabilities_main_page, container, false);
        backPressedListener.setActionBarTitle("Liability Analysis");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mliabilities_total_value = view.findViewById(R.id.liabilities_total_value);
        errorTextview = view.findViewById(R.id.empty_chart_display);
        mEditFloatingBtn= view.findViewById(R.id.liab_fab_id);
        mEditFloatingBtn.setOnClickListener(this);

        //mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        callLiabilityanaysisService();
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_detail);
        MenuItem items=menu.findItem(R.id.menu_chart);
        // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_detail:

                try{
                    addFragmenttoStack(new LiabilitiesDetailPageFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_chart:

                try{
                    addFragmenttoStack(new LiabilitiesAnaysisFragments());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void callLiabilityanaysisService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext,false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Liab_Anaysis_Model> call = webServiceObj.callLiabilityanaysisService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Liab_Anaysis_Model>() {
            @Override
            public void onResponse(Call<Liab_Anaysis_Model> call, Response<Liab_Anaysis_Model> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success",""+response.body());
                mliab_anaysis_model = response.body();
            try {

                if (mliab_anaysis_model.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if(null!=mliab_anaysis_model.getData().getLiab_analysis())
                    {
                        String overallloanamt=mliab_anaysis_model.getData().getLiab_analysis().getOver_loan_amt();

                         if (overallloanamt==null||overallloanamt.equalsIgnoreCase("0")) {
                             mliabilities_total_value.setText("₹ "+"0.00");
                        }
                        else {
                             mliabilities_total_value.setText("₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(overallloanamt))));
                        }
                    }
                }
                else{
                   // UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }}
            @Override
            public void onFailure(Call<Liab_Anaysis_Model> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
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
            case R.id.liab_fab_id:
                addFragmenttoStack(new LiabilitiesTabViewFragment());
                break;
        }

    }
}

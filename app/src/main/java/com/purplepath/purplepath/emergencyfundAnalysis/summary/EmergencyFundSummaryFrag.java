package com.purplepath.purplepath.emergencyfundAnalysis.summary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.emergencyfundAnalysis.EmergencyFundParent;
import com.purplepath.purplepath.emergencyfundAnalysis.model.EmergencyFundModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.insurance.fragment.InsuranceDetailsFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.recommendation.model.RecommendData;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dinesh on 26/04/17.
 */

public class EmergencyFundSummaryFrag  extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    Context mContext;
    OnActivityBackPressedListener backPressedListener;

    EmergencyFundModel mEmergencyFundModel;
    private RecommendData recommendData;


    @BindView(R.id.statusText)
    TextView statusText;

    @BindView(R.id.no_of_month_value)
    TextView no_of_month_value;

    @BindView(R.id.amt_required_value)
    TextView amt_required_value;

    @BindView(R.id.amt_accumulated_value)
    TextView amt_accumulated_value;

    @BindView(R.id.difference_value)
    TextView difference_value;

    private FloatingActionButton fab_id;

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;

        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getContext();
        View view = inflater.inflate(R.layout.frag_emerge_summary_anay, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        backPressedListener.setActionBarTitle("Emergency Fund");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        fab_id = view.findViewById(R.id.fab_id);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        fab_id.setOnClickListener(this);
        callEmergFundAnalyService();
        callEmergencyFundPlanService();
        return view;
    }

    public void callEmergencyFundPlanService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext,false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<RecommendData> call = webServiceObj.triggerRecommendationService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<RecommendData>() {
            @Override
            public void onResponse(Call<RecommendData> call, Response<RecommendData> response) {
                UtileKit.dismisssSpinnerDialog();
                RecommendData recommendData;
                recommendData = response.body();
                try {
                    if (recommendData.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        String str_no_of_month_value=recommendData.getData().getEmg_fund().getNo_of_months();
                        String str_amt_required_value=recommendData.getData().getEmg_fund().getAmt_req();
                        String str_amt_accumulated_value=recommendData.getData().getEmg_fund().getAmt_accum();
                        String str_amt_difference_value=recommendData.getData().getEmg_fund().getDiff();
                        String str_fund_status=recommendData.getData().getEmg_fund().getStatus();

                        if (str_no_of_month_value==null|| str_no_of_month_value.equalsIgnoreCase("0")) {
                            no_of_month_value.setText("0");
                        }else {
                            no_of_month_value.setText(str_no_of_month_value);
                        }
//                        validate(str_amt_required_value,amt_required_value);
//                        validate(str_amt_accumulated_value,amt_accumulated_value);
//                        validate(str_amt_difference_value,difference_value);


                        amt_required_value.setText("₹ "+(UtileKit.formatedNumber(Float.valueOf(TextUtils.isEmpty(recommendData.getData().getEmg_fund().getAmt_req()) ? "N/A" : recommendData.getData().getEmg_fund().getAmt_req()))));
                        amt_accumulated_value.setText("₹ "+(UtileKit.formatedNumber(Float.valueOf(TextUtils.isEmpty(recommendData.getData().getEmg_fund().getAmt_accum()) ? "N/A" : recommendData.getData().getEmg_fund().getAmt_accum()))));
                        difference_value.setText("₹ "+(UtileKit.formatedNumber(Float.valueOf(TextUtils.isEmpty(recommendData.getData().getEmg_fund().getDiff()) ? "N/A" : recommendData.getData().getEmg_fund().getDiff()))));

                        statusText.setText(str_fund_status);
                    }
                    else{
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }}
            @Override
            public void onFailure(Call<RecommendData> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }
//    void validate(String mstring,DefaultCurrencyTextView defaultCurrencyTextView){
//        if (mstring==null|| mstring.equalsIgnoreCase("0")) {
//            defaultCurrencyTextView.setText("₹ "+"0");
//        }
//        else {
//            defaultCurrencyTextView.setText("₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(mstring))));
//        }
//    }


    void validate(String mstring,DefaultCurrencyTextView mcurrencyTextView){
        if (mstring==null|| mstring.equalsIgnoreCase("0")) {
            mcurrencyTextView.setText("0");
        }
        else {
            mcurrencyTextView.setText("₹ "+String.valueOf((UtileKit.formatedNumber(Float.valueOf((mstring))))));
        }
    }


    public void callEmergFundAnalyService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<EmergencyFundModel> call = webServiceObj.callEmergencyFundChartService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<EmergencyFundModel>() {
            @Override
            public void onResponse(Call<EmergencyFundModel> call, Response<EmergencyFundModel> response) {
                UtileKit.dismisssSpinnerDialog();
                EmergencyFundModel   emergencyFundModel = response.body();
                ArrayList<Float> chartdatalist = new ArrayList<Float>();
                ArrayList<String> chartitledatalist = new ArrayList<String>();
                if (emergencyFundModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    mEmergencyFundModel=emergencyFundModel;
                    if(mEmergencyFundModel.getData().getEf_result().getEm_fund_stat()!= null) {
                     //   statusText.setText(mEmergencyFundModel.getData().getEf_result().getEm_fund_stat());
                    }
                }
                else {
                    statusText.setText("");
                }
            }@Override
            public void onFailure(Call<EmergencyFundModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_emergency, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.menu_graphId:
                fragment = new EmergencyFundParent();
                addToActivity(fragment);
                break;
            case R.id.menu_detailsId:
                fragment =  EmergencyFundDetailSumary.newInstance(mEmergencyFundModel);
                addToActivity(fragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addToActivity(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        androidx.fragment.app.FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
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

//            case R.id.fab_id:
//            {
//                addFragmenttoStack(new InsuranceDetailsFragment());
//            }
//            break;
        }
    }
}
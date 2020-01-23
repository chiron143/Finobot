package com.purplepath.purplepath.incomechartdetail;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomechartdetail.model.IncomeAnalysisModel;
import com.purplepath.purplepath.incomedetails.fragment.IncomeDetail;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class IncomeanaysisMainPageFragment extends BaseFragment implements View.OnClickListener {
    private Context mContext;
    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private  IncomeAnalysisModel incomeAnalysisModel;
    private TextView errorTextview;
    private TextView income_total_value;
    private TextView salary_income_value,income_from_property_value,
            income_from_business_property_value,income_from_other_sources_value,capital_gain_value;
    String  mincome_total_value,msalary_income_value,mincome_from_property_value,
            mincome_from_business_property_value,mincome_from_other_sources_value,mcapital_gain_value;
    LinearLayout layout_si,layout_ip,layout_ib,layout_ifs,layout_cg;
    TextView list;
    int resVector=0;
    private  FloatingActionButton mEditIncomeFloatBtn;



    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mContext = getContext();
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_incomeanaysis_main_page, container, false);
        backPressedListener.setActionBarTitle("Income Analysis");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        income_total_value= view.findViewById(R.id.income_total_value);
        salary_income_value= view.findViewById(R.id.salary_income_value);
        income_from_property_value= view.findViewById(R.id.income_from_property_value);
        income_from_business_property_value= view.findViewById(R.id.income_from_business_property_value);
        income_from_other_sources_value= view.findViewById(R.id.income_from_other_sources_value);
        capital_gain_value= view.findViewById(R.id.capital_gain_value);
        mEditIncomeFloatBtn= view.findViewById(R.id.incom_fab_id);
        mEditIncomeFloatBtn.setOnClickListener(this);

        //list=(TextView)view.findViewById(R.id.list);

        layout_si= view.findViewById(R.id.layout_si);
        layout_ip= view.findViewById(R.id.layout_ip);
        layout_ib= view.findViewById(R.id.layout_ib);
        layout_ifs= view.findViewById(R.id.layout_ifs);
        layout_cg= view.findViewById(R.id.layout_cg);


        errorTextview = view.findViewById(R.id.empty_chart_display);

        //mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        callIncomeAnalysisService();
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
                    addFragmenttoStack(new IncomeanaysisDetailpageFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;




            case R.id.menu_chart:

                try{
                    addFragmenttoStack(new IncomePieChartFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }




    public void callIncomeAnalysisService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext,false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<IncomeAnalysisModel> call = webServiceObj.callIncomeAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<IncomeAnalysisModel>() {
            @Override
            public void onResponse(Call<IncomeAnalysisModel> call, Response<IncomeAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success",""+response.body());
                incomeAnalysisModel = response.body();
                try {
                    if (incomeAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if (null != incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getFam_det()) {

                            String Si = incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getFam_det().getSi_total();
                            String Ips = incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getFam_det().getIp_total();
                            String Ibs = incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getFam_det().getIb_total();
                            String Ifs = incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getFam_det().getIfs_total();
                            String Cg = incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getFam_det().getCg_total();

                            mincome_total_value = incomeAnalysisModel.getData().getUser_inc_analysis().get(0).getOverall_income();
                            income_total_value.setText("₹ " + (UtileKit.longvalueabsolute(Float.parseFloat(mincome_total_value))));


                            if (Si == null || Si.equals("0")) {
                                layout_si.setVisibility(View.GONE);
                                salary_income_value.setText("");
                            } else {
                                layout_si.setVisibility(View.VISIBLE);
                            }

                            if (Ips == null || Ips.equals("0")) {
                                layout_ip.setVisibility(View.GONE);
                                income_from_property_value.setText("");
                            } else {
                                layout_ip.setVisibility(View.VISIBLE);
                            }

                            if (Ibs == null || Ibs.equals("0")) {
                                layout_ib.setVisibility(View.GONE);
                                income_from_business_property_value.setText("");
                            } else {
                                layout_ib.setVisibility(View.VISIBLE);
                            }


                            if (Ifs == null || Ifs.equals("0")) {
                                layout_ifs.setVisibility(View.GONE);
                                income_from_other_sources_value.setText("");
                            } else {
                                layout_ifs.setVisibility(View.VISIBLE);
                            }

                            if (Cg == null || Cg.equals("0")) {
                                layout_cg.setVisibility(View.GONE);
                                // capital_gain_value.setText("");
                            } else {
                                layout_cg.setVisibility(View.VISIBLE);
                            }


                        } else {
                            //errorTextview.setVisibility(View.VISIBLE);
                            //errorTextview.setText(HomePageActivity.errorMessageInChart);
                          //  UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                        }
                    }
                    UtileKit.dismisssSpinnerDialog();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<IncomeAnalysisModel> call, Throwable t) {
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
            case R.id.incom_fab_id:
                addFragmenttoStack(new IncomeDetail());
                break;

        }

    }
}

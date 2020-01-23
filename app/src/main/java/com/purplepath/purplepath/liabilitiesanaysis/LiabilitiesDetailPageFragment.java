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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyTextView;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.liabilities.LiabilitiesTabViewFragment;
import com.purplepath.purplepath.liabilitiesanaysis.model.Liab_Anaysis_Model;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiabilitiesDetailPageFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Liab_Anaysis_Model mliab_anaysis_model;
    private Context mContext;
    private TextView mtotal_currencyTextView;
    private TextView msource_expanse;
    private DefaultCurrencyTextView mcreditcard_value,mloanoffer_value,mLoan_value,
            mrefundabledeposit_value,munpalatablebill_value,motherliabilities_value;
    private LinearLayout mlayout_creditcard,mlayout_loanoffer,mlayout_Loan,
            mlayout_refundabledeposit,mlayout_unpalatablebill,mlayout_otherliabilities,
            mlayout_total_currencyTextView;
    private FloatingActionButton mEditFloatingBtn;


    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_liabilities_detail_page, container, false);
        backPressedListener.setActionBarTitle("Liability Analysis");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        mcreditcard_value = view.findViewById(R.id.creditcard_value);
        mloanoffer_value = view.findViewById(R.id.loanoffer_value);
        mLoan_value = view.findViewById(R.id.Loan_value);
        mrefundabledeposit_value = view.findViewById(R.id.refundabledeposit_value);
        munpalatablebill_value = view.findViewById(R.id.unpalatablebill_value);
        motherliabilities_value = view.findViewById(R.id.otherliabilities_value);

        msource_expanse= view.findViewById(R.id.source_expanse);
        mtotal_currencyTextView= view.findViewById(R.id.total_currencyTextView);

        mlayout_creditcard= view.findViewById(R.id.layout_creditcard);
        mlayout_loanoffer= view.findViewById(R.id.layout_loanoffer);
        mlayout_Loan= view.findViewById(R.id.layout_Loan);
        mlayout_refundabledeposit= view.findViewById(R.id.layout_refundabledeposit);
        mlayout_unpalatablebill= view.findViewById(R.id.layout_unpalatablebill);
        mlayout_otherliabilities= view.findViewById(R.id.layout_otherliabilities);
        mlayout_total_currencyTextView= view.findViewById(R.id.layout_total_currencyTextView);
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
        inflater.inflate(R.menu.menu_summary_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_chart);
        // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_chart:
                try{
                    addFragmenttoStack(new LiabilitiesAnaysisFragments());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:

                try{
                    addFragmenttoStack(new LiabilitiesMainPageFragment());
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

                        String creditcard=mliab_anaysis_model.getData().getLiab_analysis().getCcard();
                        String loanoffer=mliab_anaysis_model.getData().getLiab_analysis().getLn_off();
                        String loan=mliab_anaysis_model.getData().getLiab_analysis().getLn();
                        String refundable=mliab_anaysis_model.getData().getLiab_analysis().getRf_dep();
                        String unpaidable=mliab_anaysis_model.getData().getLiab_analysis().getUnpd_bills();
                        String otherliab=mliab_anaysis_model.getData().getLiab_analysis().getOth_liab();

                      //  validate(overallloanamt,mtotal_currencyTextView,mlayout_total_currencyTextView);
                        mtotal_currencyTextView.setText("₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(overallloanamt))));

                        validate(creditcard,mcreditcard_value,mlayout_creditcard);
                        validate(loanoffer,mloanoffer_value,mlayout_loanoffer);
                        validate(loan,mLoan_value,mlayout_Loan);
                        validate(refundable,mrefundabledeposit_value,mlayout_refundabledeposit);
                        validate(unpaidable,munpalatablebill_value,mlayout_unpalatablebill);
                        validate(otherliab,motherliabilities_value,mlayout_otherliabilities);

                        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                        Date date=new Date();
                        String [] value=dateFormat.format(date).split("/");
                        msource_expanse.setText("As on "+value[2]+" "+UtileKit.getMonthString(Integer.parseInt(value[1])-1)+" "+value[0]);
                    }
                }else{
                       // UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Liab_Anaysis_Model> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }


    void validate(String mstring,DefaultCurrencyTextView defaultCurrencyTextView, LinearLayout mlinearLayout){
        if (mstring==null||mstring.equalsIgnoreCase("0")) {
            defaultCurrencyTextView.setText("0");
            mlinearLayout.setVisibility(View.GONE);
        }
        else {
            defaultCurrencyTextView.setText(mstring);
            mlinearLayout.setVisibility(View.VISIBLE);
        }
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

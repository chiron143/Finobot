package com.purplepath.purplepath.AppManagement.Payment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Payment.upgrademodel.UpgradePaymentModel;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pravinr on 4/23/18.
 */

public class PaymentUpgradeSummary extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private LinearLayout layout_amount,layout_lite_type,layout_pro_type,layout_prime_type,
                         layout_upgrade;

    private TextView one_year,amount,validity,best_pricing;

    private String is_paid,user_paid_type,expiry_date;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            mContext =getContext();
            backPressedListener = (OnActivityBackPressedListener) (mContext);
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_upgrade_summary, container, false);
        MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        backPressedListener.setActionBarTitle("Upgrade");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);


        layout_amount=(LinearLayout)view.findViewById(R.id.layout_amount);
        layout_lite_type=(LinearLayout)view.findViewById(R.id.layout_lite_type);
        layout_pro_type=(LinearLayout)view.findViewById(R.id.layout_pro_type);
        layout_prime_type=(LinearLayout)view.findViewById(R.id.layout_prime_type);
        layout_upgrade=(LinearLayout)view.findViewById(R.id.layout_upgrade);
        layout_upgrade.setOnClickListener(this);

        one_year=(TextView)view.findViewById(R.id.one_year);
        amount=(TextView)view.findViewById(R.id.amount);
        validity=(TextView)view.findViewById(R.id.validity);
        best_pricing=(TextView)view.findViewById(R.id.best_pricing);

        callCommonPaymentService();

        return view;
    }


    private void callCommonPaymentService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<UpgradePaymentModel> call = webServiceObj.get_common_payment_summary(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<UpgradePaymentModel>() {
            @Override
            public void onResponse(Call<UpgradePaymentModel> call, Response<UpgradePaymentModel> response) {
                UtileKit.dismisssSpinnerDialog();
                UpgradePaymentModel upgradePaymentModel= response.body();

                try {
                    if (upgradePaymentModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        if(upgradePaymentModel.getData().getPayment_details().get(0).getIs_paid()!=null){
                            is_paid=upgradePaymentModel.getData().getPayment_details().get(0).getIs_paid();
                        }else {
                            is_paid="";
                        }

                        if(upgradePaymentModel.getData().getPayment_details().get(0).getUser_paid_type()!=null) {
                            user_paid_type = upgradePaymentModel.getData().getPayment_details().get(0).getUser_paid_type();
                        }else {
                            user_paid_type="";
                        }
                        if(upgradePaymentModel.getData().getPayment_details().get(0).getExpiry_date()!=null) {
                            expiry_date = upgradePaymentModel.getData().getPayment_details().get(0).getExpiry_date();
                        }else {
                            expiry_date="";
                        }



                        if(is_paid.equalsIgnoreCase("N")){
                            layout_lite_type.setVisibility(View.VISIBLE);
                            layout_upgrade.setVisibility(View.GONE);
                            one_year.setText("Free");
                            best_pricing.setText("LITE");
                            validity.setVisibility(View.INVISIBLE);
                            layout_upgrade.setVisibility(View.VISIBLE);
                        }else {

                            layout_upgrade.setVisibility(View.GONE);
                            if(user_paid_type.equalsIgnoreCase("1")){
                                layout_lite_type.setVisibility(View.VISIBLE);
                                one_year.setText("Free");
                                best_pricing.setText("LITE");
                                validity.setVisibility(View.INVISIBLE);
                            }
                            else if(user_paid_type.equalsIgnoreCase("2")){
                                layout_pro_type.setVisibility(View.VISIBLE);
                                one_year.setText("One Year");
                                layout_amount.setVisibility(View.VISIBLE);
                                amount.setText("₹ "+"1,188");
                                best_pricing.setText("PRO");
                                validity.setVisibility(View.VISIBLE);
                                validity.setText("Validity :"+" "+parseDate(expiry_date));


                            }else if(user_paid_type.equalsIgnoreCase("3")){
                                layout_prime_type.setVisibility(View.VISIBLE);
                                one_year.setText("One Year");
                                layout_amount.setVisibility(View.VISIBLE);
                                amount.setText("₹ "+"7,788");
                                best_pricing.setText("PRIME");
                                validity.setVisibility(View.VISIBLE);
                                validity.setText("Validity :"+" "+parseDate(expiry_date));

                            }
                        }


                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<UpgradePaymentModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
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
                startSettingHomeActivity();
                break;
            case R.id.layout_upgrade:
                addFragmenttoStack(new PaymentViewPager());
                break;

        }
    }

    public String parseDate(String time) {
        String str = null;
        DateFormat oldFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date oldDate = null;
        try {
            oldDate = oldFormatter .parse(time);
            str =formatter.format(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(formatter.format(oldDate));

        return str;
    }
}

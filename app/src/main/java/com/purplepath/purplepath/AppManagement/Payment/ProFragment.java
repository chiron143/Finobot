package com.purplepath.purplepath.AppManagement.Payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.purplepath.purplepath.AppManagement.Payment.commonPayment.CommonPayment;
import com.purplepath.purplepath.AppManagement.Payment.getcommonpaymentmodel.CommonPaymentModel;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.purplepath.purplepath.apputiles.UtileKit.pro_paid_type;
import static com.purplepath.purplepath.apputiles.UtileKit.validity_one_year;

/**
 * Created by pravinr on 12/5/17.
 */

public class ProFragment extends BaseFragment implements View.OnClickListener {

    private TextView buy_now1, buy_now2, buy_now3;

    private Context mContext;

    private String total_amount = "1188", str_buy_now2 = "200", str_buy_now3 = "300";

    private LinearLayout layout_buynow;

    Fragment fragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pro_payment, container, false);

        buy_now1 = view.findViewById(R.id.buy_now1);
        buy_now2 = view.findViewById(R.id.buy_now2);
        buy_now3 = view.findViewById(R.id.buy_now3);
        buy_now1.setOnClickListener(this);
        buy_now2.setOnClickListener(this);
        buy_now3.setOnClickListener(this);


        layout_buynow = (LinearLayout) view.findViewById(R.id.layout_buynow);
        layout_buynow.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buy_now1:
                callTaxPaymentService();
                break;
//            case R.id.buy_now2:
//                fragment=PaymentWebView.newInstance(pro_paid_type,str_buy_now2,validity_two_year );
//                addFragmenttoStack(fragment);
//                break;
//            case R.id.buy_now3:
//                fragment=PaymentWebView.newInstance(pro_paid_type,str_buy_now3,validity_three_year);
//                addFragmenttoStack(fragment);
//                break;

        }

    }

    private void callTaxPaymentService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<CommonPaymentModel> call = webServiceObj.get_payment_details_by_user(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<CommonPaymentModel>() {
            @Override
            public void onResponse(Call<CommonPaymentModel> call, Response<CommonPaymentModel> response) {
                UtileKit.dismisssSpinnerDialog();
                CommonPaymentModel paymentCheckModel = response.body();
                try {
                    if (paymentCheckModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        String commonPaymentSuccessMode = paymentCheckModel.getData().getPayment_made();

                        if (commonPaymentSuccessMode.equalsIgnoreCase("true")) {
                            successDialog(mContext);
                        } else {
//                            fragment = CommonPayment.newInstance(pro_paid_type, str_buy_now1, validity_one_year);
//                            addFragmenttoStack(fragment);
                            fragment = PromotionScreen.newInstance(pro_paid_type, total_amount, validity_one_year);
                            addFragmenttoStack(fragment);
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CommonPaymentModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void successDialog(Context context) {
        LayoutInflater inflater;
        final View dialogView;
        final AlertDialog alertDialog;
        inflater = LayoutInflater.from(context);
        dialogView = inflater.inflate(R.layout.yes_no_dialogs, null);
        alertDialog = new androidx.appcompat.app.AlertDialog.Builder(context).create();
        alertDialog.setView(dialogView);
        TextView stringErrorMessage = dialogView.findViewById(R.id.textViewDilog);
        stringErrorMessage.setText("Already Paid");
        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }



}

package com.purplepath.purplepath.AppManagement.Payment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Payment.commonPayment.CommonPayment;
import com.purplepath.purplepath.AppManagement.Payment.getdiscountmodel.DiscountModel;
import com.purplepath.purplepath.AppManagement.Payment.promocodemodel.PromoCodeModel;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.finobot.finobot.MyApplication.mFirebaseAnalytics;
import static com.purplepath.purplepath.apputiles.UtileKit.pro_paid_type;
import static com.purplepath.purplepath.apputiles.UtileKit.validity_one_year;

public class PromotionScreen extends BaseFragment implements View.OnClickListener {


    private OnActivityBackPressedListener mCallBackListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    private ImageView promo_tick;

    private TextView company_percentage, promo_text, both_com_promo, both_com_promo_text, both_com_promo_pay;

    private EditText promocode_edittext;

    private double company_discount = 0.0f;

    double company_percentages = 0.0f, promocode_percentages = 0.0f, com_pro = 0.0f;

    private LinearLayout layout_checkout, layout_applicable, layout_apply, layout_company;

    private String total_amount = "", str_paid_type = "", str_validity = "";

    Fragment fragment;

    float final_amounts = 0.0f;

    //Payment for all
    public static PromotionScreen newInstance(String str_basic_type, String total_amount, String validity) {
        PromotionScreen paymentPayUmoney = new PromotionScreen();
        Bundle args = new Bundle();
        if (total_amount != null) {
            args.putSerializable("total_amount", total_amount);
        }
        if (str_basic_type != null) {
            args.putSerializable("paid_type", str_basic_type);
        }
        if (validity != null) {
            args.putSerializable("validity_year", validity);
        }

        paymentPayUmoney.setArguments(args);
        return paymentPayUmoney;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Payment for all
        if (getArguments().containsKey("total_amount")) {
            total_amount = getArguments().getString("total_amount");
        }
        if (getArguments().containsKey("paid_type")) {
            str_paid_type = getArguments().getString("paid_type");
        }
        if (getArguments().containsKey("validity_year")) {
            str_validity = getArguments().getString("validity_year");
        }

        View view = inflater.inflate(R.layout.fragment_promotion_page, container, false);
        setHasOptionsMenu(true);
        MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        mCallBackListener.setActionBarTitle("Promotion");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        company_percentage = view.findViewById(R.id.company_percentage);
        promo_tick = view.findViewById(R.id.promo_tick);
        promo_text = view.findViewById(R.id.promo_text);
        promocode_edittext = view.findViewById(R.id.promocode_edittext);


        layout_checkout = view.findViewById(R.id.layout_checkout);
        layout_checkout.setOnClickListener(this);

        both_com_promo = view.findViewById(R.id.both_com_promo);
        both_com_promo_text = view.findViewById(R.id.both_com_promo_text);
        both_com_promo_pay = view.findViewById(R.id.both_com_promo_pay);

        layout_applicable = view.findViewById(R.id.layout_applicable);
        layout_apply = view.findViewById(R.id.layout_apply);
        layout_apply.setOnClickListener(this);

        layout_company = view.findViewById(R.id.layout_company);
        calLDiscountService();
        return view;
    }

    //company
    private void calLDiscountService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<DiscountModel> call = webServiceObj.getDiscountService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<DiscountModel>() {
            @Override
            public void onResponse(Call<DiscountModel> call, Response<DiscountModel> response) {
                UtileKit.dismisssSpinnerDialog();
                DiscountModel discountModel = response.body();
                try {
                    if (discountModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        layout_company.setVisibility(View.VISIBLE);
                        if (Double.valueOf(discountModel.getData().getDiscount_per()) != null&&
                                Double.valueOf(discountModel.getData().getDiscount_per()) != 0)
                            company_percentages = Double.valueOf(discountModel.getData().getDiscount_per());
                            company_percentage.setText(String.valueOf(company_percentages * 100) .concat ("%"));
                            company_percentage.setTextColor(Color.parseColor("#006400"));

                            com_pro = company_percentages + promocode_percentages;
                            float convert = Float.valueOf(total_amount);
                            if (convert != 0.0f)
                                company_discount = (convert) * (com_pro);
                            both_com_promo.setText(String.valueOf(company_discount));

                            float converts = Float.valueOf(total_amount);
                            final_amounts = (float) (converts - company_discount);
                            both_com_promo_pay.setText(" "+String.valueOf(final_amounts));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<DiscountModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.relative_left_arrow:

                mCallBackListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                startSettingHomeActivity();
                break;

            case R.id.layout_apply:

               // layout_applicable.setVisibility(View.VISIBLE);
                String promocode = promocode_edittext.getText().toString();

                callPromoCodeService(promocode);
                break;
            case R.id.layout_checkout:

                float convert = Float.valueOf(total_amount);
                final_amounts = (float) (convert - company_discount);
                fragment = CommonPayment.newInstance(pro_paid_type, String.valueOf(final_amounts), validity_one_year);
                addFragmenttoStack(fragment);
                Log.d("final_amounts","final_amounts"+final_amounts);

                break;

        }

    }

    //promocode
    private void callPromoCodeService(String promocode) {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<PromoCodeModel> call = webServiceObj.getPromoCodeService(UtileKit.getPersistedPurplePathPref("user_id"), promocode);
        call.enqueue(new Callback<PromoCodeModel>() {
            @Override
            public void onResponse(Call<PromoCodeModel> call, Response<PromoCodeModel> response) {
                UtileKit.dismisssSpinnerDialog();
                PromoCodeModel promoCodeModel = response.body();
                try {
                    if (promoCodeModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        layout_applicable.setVisibility(View.VISIBLE);
                        UtileKit.setSvgImageviewDrawable(promo_tick, mContext, R.drawable.ic_promo_tick);
                        promo_text.setText("Promo Code applied Successfully");
                        promo_text.setTextColor(Color.parseColor("#006400"));

                        if (Double.valueOf(promoCodeModel.getData().getPromo_details().get(0).getDiscount()) != null&&
                                Double.valueOf(promoCodeModel.getData().getPromo_details().get(0).getDiscount()) !=0)
                            promocode_percentages = Double.valueOf(promoCodeModel.getData().getPromo_details().get(0).getDiscount());

                        com_pro = company_percentages + promocode_percentages;
                        float convert = Float.valueOf(total_amount);

                        if (convert != 0.0f)
                            company_discount = (convert) * (com_pro);

                        both_com_promo_text.setText("Overall discount Applied  ");
                        both_com_promo.setText(String.valueOf(company_discount));

                        float converts = Float.valueOf(total_amount);
                        final_amounts = (float) (converts - company_discount);
                        both_com_promo_pay.setText(" "+String.valueOf(final_amounts));


                    }else if(promoCodeModel.getStatus_code().equals(UtileKit.SUCCESS_OVERRIDE_CODE)){
                        layout_applicable.setVisibility(View.VISIBLE);
                        UtileKit.setSvgImageviewDrawable(promo_tick, mContext, R.drawable.ic_promo_untick);
                        promo_text.setText("Promo Code are not valid or expired");
                        promo_text.setTextColor(Color.parseColor("#FF0000"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PromoCodeModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

}

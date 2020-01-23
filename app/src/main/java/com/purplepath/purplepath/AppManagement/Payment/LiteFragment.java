package com.purplepath.purplepath.AppManagement.Payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.fragments.BaseFragment;

import static com.purplepath.purplepath.apputiles.UtileKit.lite_paid_type;
import static com.purplepath.purplepath.apputiles.UtileKit.validity_one_year;
import static com.purplepath.purplepath.apputiles.UtileKit.validity_three_year;
import static com.purplepath.purplepath.apputiles.UtileKit.validity_two_year;


/**
 * Created by pravinr on 12/5/17.
 */

public class LiteFragment extends BaseFragment implements View.OnClickListener {

    private TextView buy_now1,buy_now2,buy_now3;

    private Context mContext;

    private String str_buy_now1="0",str_buy_now2="100",str_buy_now3="150";

    private LinearLayout layout_buynow;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_lite_payment, container, false);

        buy_now1= view.findViewById(R.id.buy_now1);
        buy_now2= view.findViewById(R.id.buy_now2);
        buy_now3= view.findViewById(R.id.buy_now3);
        buy_now1.setOnClickListener(this);
        buy_now2.setOnClickListener(this);
        buy_now3.setOnClickListener(this);

        layout_buynow=(LinearLayout) view.findViewById(R.id.layout_buynow);
        layout_buynow.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        switch (v.getId()){

            case R.id.buy_now1:
//                fragment=PaymentWebView.newInstance(lite_paid_type,str_buy_now1,validity_one_year);
//                addFragmenttoStack(fragment);


                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

                break;
//            case R.id.buy_now2:
//                fragment=PaymentWebView.newInstance(lite_paid_type,str_buy_now2, validity_two_year);
//                addFragmenttoStack(fragment);
//                break;
//            case R.id.buy_now3:
//                fragment=PaymentWebView.newInstance(lite_paid_type,str_buy_now3, validity_three_year);
//                addFragmenttoStack(fragment);
//                break;

        }

    }
}

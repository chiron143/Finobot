//package com.purplepath.purplepath.AppManagement.Payment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.purplepath.purplepath.AppManagement.Payment.model.PaymentCheckModel;
//import com.purplepath.purplepath.customview.CustomTextView;
//import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
//import com.purplepath.purplepath.fragments.BaseFragment;
//import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
//import com.purplepath.purplepath.taxfiling.TaxFilingSummary;
//
///**
// * Created by dinesh on 16/02/18.
// */
//
//public class PaymentSummery extends BaseFragment implements View.OnClickListener{
//
//    PaymentCheckModel mPaymentCheckModel;
//
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//
//    OnActivityBackPressedListener backPressedListener;
//
//    private String commonPayment="";
//
//    private LinearLayout tax_filing_click;
//
//    public static PaymentSummery newInstance(PaymentCheckModel mPaymentCheckModel) {
//
//        Bundle args = new Bundle();
//        args.putSerializable("mPaymentCheckModel",mPaymentCheckModel);
//
//
//        PaymentSummery fragment = new PaymentSummery();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if(getArguments().containsKey("mPaymentCheckModel"))
//        mPaymentCheckModel=(PaymentCheckModel)getArguments().get("mPaymentCheckModel");
//        backPressedListener= (OnActivityBackPressedListener) getContext();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view =inflater.inflate(R.layout.fragment_paymeny_view, container, false);
//
//        tax_filing_click = (LinearLayout) view.findViewById(R.id.tax_filing_click);
//        tax_filing_click.setOnClickListener(this);
//
//
//        return view;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
//        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
//        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
//        mRightRelativeLayout.setVisibility(View.GONE);
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//        CustomTextView merchantOrderId= view.findViewById(R.id.merchantOrder_id);
//        CustomTextView merchantReferceCode= view.findViewById(R.id.mspreference_id);
//        CustomTextView resultmsg_id= view.findViewById(R.id.resultmsg_id);
//        DefaultCurrencyTextView current_accout= view.findViewById(R.id.current_accout_id);
//        merchantOrderId.setText(mPaymentCheckModel.getData().getUser_payment_details().getMerchant_order_id());
//        merchantReferceCode.setText(mPaymentCheckModel.getData().getUser_payment_details().getMsp_ref_id());
//        current_accout.setText(mPaymentCheckModel.getData().getUser_payment_details().getAmount());
//
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()){
//            case R.id.relative_left_arrow:
//                backPressedListener.onActivityBackPressed();
//                break;
//            case R.id.relative_center_home:
//                startSettingHomeActivity();
//                break;
//            case R.id.tax_filing_click:
//
//                if(commonPayment.equalsIgnoreCase("commonPayment")){
//                    Intent i=new Intent(getActivity(), HomePageActivity.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(i);
//
//
//                }else {
//                    addFragmenttoStack(new TaxFilingSummary());
//                }
//                break;
//
//        }
//
//    }
//}

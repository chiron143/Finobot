package com.purplepath.purplepath.AppManagement.Payment.commonPayment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.AppManagement.Payment.getcommonpaymentmodel.CommonPaymentModel;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CustomTextView;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
/**
 * Created by pravinr on 4/20/18.
 */

public class CommonPaymentSummary extends BaseFragment implements View.OnClickListener{

    CommonPaymentModel mPaymentCheckModel;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    OnActivityBackPressedListener backPressedListener;

    private LinearLayout tax_filing_click;

    private String successMessage="";

    private Context mContext;

    private String final_amounts="",str_paid_type="",str_validity="";

    private LinearLayout layout_merchentid,layout_mspreference,layour_amount;

    public static CommonPaymentSummary newInstance(CommonPaymentModel mPaymentCheckModel,
                                                   String str_basic_type, String final_amounts, String validity) {

        Bundle args = new Bundle();
        args.putSerializable("mPaymentCheckModel",mPaymentCheckModel);
        CommonPaymentSummary fragment = new CommonPaymentSummary();

        if (final_amounts != null) {
            args.putSerializable("final_amounts", final_amounts);
        }
        if(str_basic_type!=null){
            args.putSerializable("paid_type",str_basic_type);
        }
        if (validity != null) {
            args.putSerializable("validity_year", validity);
        }

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        if(getArguments().containsKey("mPaymentCheckModel"))
            mPaymentCheckModel=(CommonPaymentModel)getArguments().get("mPaymentCheckModel");
        backPressedListener= (OnActivityBackPressedListener) getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_paymeny_view, container, false);
        //Payment for all
        if(getArguments().containsKey("final_amounts")) {
            final_amounts = getArguments().getString("final_amounts");
        }
        if(getArguments().containsKey("paid_type")){
            str_paid_type=getArguments().getString("paid_type");
        }
        if(getArguments().containsKey("validity_year")){
            str_validity=getArguments().getString("validity_year");
        }

        layout_merchentid=(LinearLayout)view.findViewById(R.id.layout_merchentid);
        layout_mspreference=(LinearLayout)view.findViewById(R.id.layout_mspreference);
        layour_amount=(LinearLayout)view.findViewById(R.id.layour_amount);

        tax_filing_click = (LinearLayout) view.findViewById(R.id.tax_filing_click);
        tax_filing_click.setOnClickListener(this);



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        CustomTextView merchantOrderId= view.findViewById(R.id.merchantOrder_id);
        CustomTextView merchantReferceCode= view.findViewById(R.id.mspreference_id);
        CustomTextView resultmsg_id= view.findViewById(R.id.resultmsg_id);
        DefaultCurrencyTextView current_accout= view.findViewById(R.id.current_accout_id);

        merchantOrderId.setText(mPaymentCheckModel.getData().getUser_payment_details().getMerchant_order_id());
        merchantReferceCode.setText(mPaymentCheckModel.getData().getUser_payment_details().getMsp_ref_id());
        current_accout.setText(mPaymentCheckModel.getData().getUser_payment_details().getAmount());

        successMessage=mPaymentCheckModel.getData().getPayment_made();
        ImageView success_image=view.findViewById(R.id.success_image);

        UtileKit.persistingPurplePathPref("commonPaymentSuccessMode", mPaymentCheckModel.getData().getPayment_made());

//        if(successMessage.equalsIgnoreCase("true")){
//            resultmsg_id.setText("Payment done sucessfully");
//        }
        if(mPaymentCheckModel.getData().getUser_payment_details().getTransaction_status().equalsIgnoreCase("Success")){
            resultmsg_id.setText("Payment Sucessful");
            UtileKit.setSvgImageviewDrawable(success_image,mContext,R.drawable.ic_upload_success);
        }
        else if(mPaymentCheckModel.getData().getUser_payment_details().getTransaction_status().equalsIgnoreCase("Failure")){
            resultmsg_id.setText("Your Payment is Failure Please try again");
            UtileKit.setSvgImageviewDrawable(success_image,mContext,R.drawable.ic_payment_failed);
            layout_merchentid.setVisibility(View.GONE);
            layout_mspreference.setVisibility(View.GONE);
            layour_amount.setVisibility(View.GONE);
        }
        else if(mPaymentCheckModel.getData().getUser_payment_details().getTransaction_status().equalsIgnoreCase("Cancelled")){
            resultmsg_id.setText("Your Payment is Cancelled Please try again");

            layout_merchentid.setVisibility(View.GONE);
            layout_mspreference.setVisibility(View.GONE);
            layour_amount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
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
            case R.id.tax_filing_click:

                if(mPaymentCheckModel.getData().getUser_payment_details().getTransaction_status().equalsIgnoreCase("Success")){
                    Intent i=new Intent(getActivity(), HomePageActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                else if(mPaymentCheckModel.getData().getUser_payment_details().getTransaction_status().equalsIgnoreCase("Failure")){
                    addFragmenttoStack(CommonPayment.newInstance(final_amounts,str_paid_type,str_validity));
                }
                else if(mPaymentCheckModel.getData().getUser_payment_details().getTransaction_status().equalsIgnoreCase("Cancelled")){
                    addFragmenttoStack(CommonPayment.newInstance(final_amounts,str_paid_type,str_validity));
                }

                break;

        }

    }
}

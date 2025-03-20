package com.purplepath.purplepath.taxprepaid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.chatprompt.PromptChatFragment1;
import com.purplepath.purplepath.chatprompt.insertmodel.InsertModel;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.fragments.PersonalDetailsFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxprepaid.gettaxprepaidmodel.TaxPrepaidmodel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by pravinr on 1/10/18.
 */

public class TaxPrepaidFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    Context mContext;

    private CurrencyGhostView mAdvance_tax_paid,mAssessment_tax_paid,mTds_deducted,
            mTcs_deducted,mMat_credit,mAmt_credit;

    private CharacterEditText mNote;

    private String msetAdvance_tax_paid,msetAssessment_tax_paid,msetTds_deducted,
                   msetTcs_deducted,msetMat_credit,msetAmt_credit,msetNote;

    InsertModel insertModel;



    Boolean isSignUp = false;
    ArrayList<String> formArray = new ArrayList<String>();
    private LinearLayout bottom_bar_layout,bottom_bar_donelayout;
    private RelativeLayout relative_finish_later,relative_done_arrow;
    private LinearLayout advanced_tax_paid_layout,assessment_tax_paid_layout,
            tds_deducted_layout,tcs_deducted_layout,mat_credit_layout,
            amount_deducted_layout,note_layout;


    public static TaxPrepaidFragment newInstance() {
        TaxPrepaidFragment personal = new TaxPrepaidFragment();

        return personal;
    }

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{

            if (getArguments() != null) {
                if (getArguments().containsKey("IsSignUp"))
                    isSignUp = getArguments().getBoolean("IsSignUp");
            }

            if (getArguments() != null) {
                if (getArguments().containsKey("form_array")) {
                    formArray = (ArrayList<String>) getArguments().getSerializable("form_array");
                }
            }


            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        mContext = getContext();
        setHasOptionsMenu(true);



        View view=inflater.inflate(R.layout.fragment_tax_prepaid, container, false);
        backPressedListener.setActionBarTitle("Tax Prepaid");
        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_right_arrow);

        mAdvance_tax_paid=(CurrencyGhostView)view.findViewById(R.id.total_advance_tax_paid_Edt);
        mAdvance_tax_paid.setTextHint("Advance tax paid");
        mAssessment_tax_paid=(CurrencyGhostView)view.findViewById(R.id.total_self_assessment_tax_paid_Edt);
        mAssessment_tax_paid.setTextHint("Assessment tax paid");
        mTds_deducted=(CurrencyGhostView)view.findViewById(R.id.total_tds_deducted_Edt);
        mTds_deducted.setTextHint("TDS deducted");
        mTcs_deducted=(CurrencyGhostView)view.findViewById(R.id.total_tcs_deducted_Edt);
        mTcs_deducted.setTextHint("TCS deducted");
        mMat_credit=(CurrencyGhostView)view.findViewById(R.id.mat_credit_Edt);
        mMat_credit.setTextHint("MAT credit");
        mAmt_credit=(CurrencyGhostView)view.findViewById(R.id.amt_credit_Edt);
        mAmt_credit.setTextHint("Amount credit");
        mNote = (CharacterEditText) view.findViewById(R.id.note_Edt);


        advanced_tax_paid_layout=(LinearLayout)view.findViewById(R.id.advanced_tax_paid_layout);
        assessment_tax_paid_layout=(LinearLayout)view.findViewById(R.id.assessment_tax_paid_layout);
        tds_deducted_layout=(LinearLayout)view.findViewById(R.id.tds_deducted_layout);
        tcs_deducted_layout=(LinearLayout)view.findViewById(R.id.tcs_deducted_layout);
        mat_credit_layout=(LinearLayout)view.findViewById(R.id.mat_credit_layout);
        amount_deducted_layout=(LinearLayout)view.findViewById(R.id.amount_deducted_layout);
        note_layout=(LinearLayout)view.findViewById(R.id.note_layout);



        relative_finish_later= view.findViewById(R.id.relative_finish_later);
        relative_done_arrow= view.findViewById(R.id.relative_done_arrow);
        relative_finish_later.setOnClickListener(this);
        relative_done_arrow.setOnClickListener(this);


        bottom_bar_layout= view.findViewById(R.id.bottom_bar_layout);
        UtileKit.mandatoryFieldLinearLayout(isSignUp,bottom_bar_layout);

        bottom_bar_donelayout= view.findViewById(R.id.bottom_bar_donelayout);
        UtileKit.mandatoryFieldDoneLayout(isSignUp,bottom_bar_donelayout);


        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);




        setInputValues();
        return view;
    }


        private void getInputValues() {
                UtileKit.showSpinnerDialog(mContext,false);
                String str_Advance_tax_paid,str_mAssessment_tax_paid,str_mTds_deducted,str_mTcs_deducted,str_mMat_credit,str_mAmt_credit,str_mNote ;
                str_Advance_tax_paid=UtileKit.getStringwithoutDefaultCurreny(mAdvance_tax_paid.getEditText());
                str_mAssessment_tax_paid=UtileKit.getStringwithoutDefaultCurreny(mAssessment_tax_paid.getEditText());
                str_mTds_deducted=UtileKit.getStringwithoutDefaultCurreny(mTds_deducted.getEditText());
                str_mTcs_deducted=UtileKit.getStringwithoutDefaultCurreny(mTcs_deducted.getEditText());
                str_mMat_credit=UtileKit.getStringwithoutDefaultCurreny(mMat_credit.getEditText());
                str_mAmt_credit=UtileKit.getStringwithoutDefaultCurreny(mAmt_credit.getEditText());
                str_mNote = mNote.getText().toString();

                WebServiceCalls webServiceObj;
                webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
                Call<TaxPrepaidmodel> call = webServiceObj.callAddTaxPrepaidService(UtileKit.getPersistedPurplePathPref("user_id"),
                    str_Advance_tax_paid,str_mAssessment_tax_paid,str_mTds_deducted,str_mTcs_deducted,str_mMat_credit,str_mAmt_credit,str_mNote);
                call.enqueue(new Callback<TaxPrepaidmodel>() {
                @Override
                public void onResponse(Call<TaxPrepaidmodel> call, Response<TaxPrepaidmodel> response) {
                    UtileKit.dismisssSpinnerDialog();
                    TaxPrepaidmodel taxPrepaidmodel = response.body();
                        if (taxPrepaidmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        }
                }

                @Override
                public void onFailure(Call<TaxPrepaidmodel> call, Throwable t) {
                    UtileKit.dismisssSpinnerDialog();
                    UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }

       private void setInputValues() {
            UtileKit.showSpinnerDialog(mContext,false);
            WebServiceCalls webServiceObj;
            webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
            Call<TaxPrepaidmodel> call = webServiceObj.callGetTaxPrepaidService(UtileKit.getPersistedPurplePathPref("user_id"));
            call.enqueue(new Callback<TaxPrepaidmodel>() {
                @Override
                public void onResponse(Call<TaxPrepaidmodel> call, Response<TaxPrepaidmodel> response) {
                    UtileKit.dismisssSpinnerDialog();
                    TaxPrepaidmodel taxPrepaidmodel = response.body();
                        try{
                            if (taxPrepaidmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                                msetAdvance_tax_paid = taxPrepaidmodel.getData().getPre_tax().get(0).getTotal_advance_tax_paid();
                                msetAssessment_tax_paid = taxPrepaidmodel.getData().getPre_tax().get(0).getTotal_self_assessment_tax_paid();
                                msetTds_deducted = taxPrepaidmodel.getData().getPre_tax().get(0).getTotal_tds_deducted();
                                msetTcs_deducted = taxPrepaidmodel.getData().getPre_tax().get(0).getTotal_tcs_deducted();
                                msetMat_credit = taxPrepaidmodel.getData().getPre_tax().get(0).getMat_credit();
                                msetAmt_credit = taxPrepaidmodel.getData().getPre_tax().get(0).getAmt_credit();
                                msetNote = taxPrepaidmodel.getData().getPre_tax().get(0).getNotes();

                                validate(msetAdvance_tax_paid,mAdvance_tax_paid);
                                validate(msetAssessment_tax_paid,mAssessment_tax_paid);
                                validate(msetTds_deducted,mTds_deducted);
                                validate(msetTcs_deducted,mTcs_deducted);
                                validate(msetMat_credit,mMat_credit);
                                validate(msetAmt_credit,mAmt_credit);
                                validateCharacterEdit(msetNote,mNote);
                    }
                    else {
                    //   UtileKit.intitializeAlertDialog(taxPrepaidmodel.getData().getMessage(), mContext);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<TaxPrepaidmodel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }


    void validate(String string,CurrencyGhostView currencyGhostView){
        if(!string.equalsIgnoreCase("0")){
            currencyGhostView.setText(string);
        }
    }
    void validateCharacterEdit(String string,CharacterEditText characterEditText){
        if(!string.equalsIgnoreCase("0")){
            characterEditText.setText(string);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                getInputValues();
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                getInputValues();
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;


            case R.id.relative_finish_later:{
                getInputValues();
                Intent intent=new Intent(getActivity(), HomePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            break;
            case R.id.relative_done_arrow:{
                getInputValues();
                callUpdateInsertFlagService();
                addFragmenttoStack(new PromptChatFragment1());

            }
            break;

        }

    }


    public void callUpdateInsertFlagService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<InsertModel> call = webServiceObj.callUpdateInsertFlagService(UtileKit.getPersistedPurplePathPref("user_id"),
                "users_prepaid_tax","Y","Y");
        call.enqueue(new Callback<InsertModel>() {
            @Override
            public void onResponse(Call<InsertModel> call, Response<InsertModel> response) {
                UtileKit.dismisssSpinnerDialog();
                insertModel = response.body();
                if(insertModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                }
            }
            @Override
            public void onFailure(Call<InsertModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }
}

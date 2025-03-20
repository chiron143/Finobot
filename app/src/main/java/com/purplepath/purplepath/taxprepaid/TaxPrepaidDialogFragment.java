package com.purplepath.purplepath.taxprepaid;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxprepaid.gettaxprepaidmodel.TaxPrepaidmodel;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pravinr on 1/15/18.
 */

public class TaxPrepaidDialogFragment extends DialogFragment implements View.OnClickListener{

    Context mContext;

    private CurrencyGhostView mAdvance_tax_paid,mAssessment_tax_paid,mTds_deducted,
            mTcs_deducted,mMat_credit,mAmt_credit;

    private CharacterEditText mNote;

    private String msetAdvance_tax_paid,msetAssessment_tax_paid,msetTds_deducted,
            msetTcs_deducted,msetMat_credit,msetAmt_credit,msetNote;

    private FloatingActionButton mfab_tax_prepaid;

    private ImageView backBtn;

    private TextView titleNameTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MY_DIALOG);
    }

    /*@Override
    public void onResume() {
        super.onResume();
        int height=getResources().getDisplayMetrics().heightPixels;
        int width=getResources().getDisplayMetrics().widthPixels;
        getDialog().getWindow().setLayout((int)(width*.90),(int)(height*.95));
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view=inflater.inflate(R.layout.dialog_tax_prepaid,container,false);
        ButterKnife.bind(this,view);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.white);


        mAdvance_tax_paid= view.findViewById(R.id.total_advance_tax_paid_Edt);
        mAdvance_tax_paid.setTextHint("Advance tax paid");
        mAssessment_tax_paid= view.findViewById(R.id.total_self_assessment_tax_paid_Edt);
        mAssessment_tax_paid.setTextHint("Assessment tax paid");
        mTds_deducted= view.findViewById(R.id.total_tds_deducted_Edt);
        mTds_deducted.setTextHint("Tds deducted");
        mTcs_deducted= view.findViewById(R.id.total_tcs_deducted_Edt);
        mTcs_deducted.setTextHint("Tcs deducted");
        mMat_credit= view.findViewById(R.id.mat_credit_Edt);
        mMat_credit.setTextHint("Mat credit");
        mAmt_credit= view.findViewById(R.id.amt_credit_Edt);
        mAmt_credit.setTextHint("Amount credit");
        mNote = view.findViewById(R.id.note_Edt);


        titleNameTxt= view.findViewById(R.id.dialogTitleId);
        titleNameTxt.setText("Tax Prepaid");

        backBtn= view.findViewById(R.id.backButtonId);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mfab_tax_prepaid= view.findViewById(R.id.fab_tax_prepaid);
        mfab_tax_prepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputValues();
            }
        });

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
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}

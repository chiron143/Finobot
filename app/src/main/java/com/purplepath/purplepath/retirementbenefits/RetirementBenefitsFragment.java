package com.purplepath.purplepath.retirementbenefits;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.calculator.CalculatorAct;
import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.chatprompt.PromptChatFragment1;
import com.purplepath.purplepath.customview.CharacterEditText;
import com.purplepath.purplepath.customview.CurrencyGhostView;
import com.purplepath.purplepath.customview.CustomCalenderImageView;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retirementbenefits.model.RetirementBenifitModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxprepaid.TaxPrepaidFragment;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.incomedetails.IncomeDynamicDetail.PARENT_CLASS_SOURCE;


/**
 * Created by dinesh on 13/07/16.
 */
public class RetirementBenefitsFragment extends BaseFragment implements View.OnClickListener{

    private CurrencyGhostView mGratuity,
             mAnnuityPension,
             mSuperannuationFund,
             mLeaveEncashment,
             mRetrenchmentcompensation,
            mVoluntaryretirement,
            mRetiotherEdt;
    CharacterEditText   mRetiNoteEdt;
    String msetGratuityString,
            msetAnnuityPensionString,
            msetSuperannuationFundString,
            msetLeaveEncashmentString,
            msetRetrenchmentcompensationString,
            msetVoluntaryretirementString,
            mRetiNoteEdtString,
            mRetiotherEdtString;
    ScrollView mScrollView;
    private CustomCalenderImageView mreti_Gratuity_calculaterImgView,mreti_Annuity_pension_calculaterImgView,
            mreti_supperannuation_calculaterImgView,mreti_leave_encashment_calculaterImgView,
            mreti_retrenchment_calculaterImgView,mreti_voluntary_retirement_calculaterImgView,
            mreti_other_retirement_calculaterImgView;
    View nameEditview;
    public static final String TITLE = "";

    Context mContext;
    private Toolbar toolbar;
    private OnActivityBackPressedListener mCallBackListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private RetirementBenifitModel mRetirementBenifitModel;
    public HashMap<String,View> errorMapView=new HashMap<>();


    Boolean isSignUp = false;
    ArrayList<String> formArray = new ArrayList<String>();
    private LinearLayout bottom_bar_layout,bottom_bar_donelayout;
    private RelativeLayout relative_finish_later,relative_done_arrow;
    private LinearLayout gratuity_layout,annuity_pension_layout,superannuantion_layout,
            leave_enhashment_layout,retension_layout,valuntary_retirement_layout,others_layout,notes_layout;



    public static RetirementBenefitsFragment newInstance() {
        RetirementBenefitsFragment personal = new RetirementBenefitsFragment();

        return personal;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }

        catch(Exception e)
        {}
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
        }catch (Exception e){}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retirementBenifitView = inflater.inflate(R.layout.fragment_retirement_benefits_view, container, false);
        mCallBackListener.setActionBarTitle("Retirement Benefits");
        mScrollView= retirementBenifitView.findViewById(R.id.scrollView);
        mleftRelativeLayout = retirementBenifitView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = retirementBenifitView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = retirementBenifitView.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        mGratuity= retirementBenifitView.findViewById(R.id.reti_Gratuity_Edt);
        errorMapView.put("gratuity",mGratuity);
        mGratuity.setTextHint("Gratuity*");
        mGratuity.setfullHintTxt(getString(R.string.hint_retirement_gratuity));

        mAnnuityPension= retirementBenifitView.findViewById(R.id.reti_Annuity_Pension_Edt);
        errorMapView.put("annuity_pension",mAnnuityPension);
        mAnnuityPension.setTextHint("Annuity / Pension*");
        mAnnuityPension.setfullHintTxt(getString(R.string.hint_retirement_annuity_pension));

        mSuperannuationFund= retirementBenifitView.findViewById(R.id.reti_superannuation_fund_Edt);
        errorMapView.put("saf",mSuperannuationFund);
        mSuperannuationFund.setTextHint("Superannuation Fund (SAF)");
        mSuperannuationFund.setfullHintTxt(getString(R.string.hint_retirement_superannuationfund));

        mLeaveEncashment= retirementBenifitView.findViewById(R.id.reti_leave_encashment_Edt);
        errorMapView.put("leave_encash",mLeaveEncashment);
        mLeaveEncashment.setTextHint("Leave Encashment");
        mLeaveEncashment.setfullHintTxt(getString(R.string.hint_retirement_leaveencashment));



        mRetrenchmentcompensation= retirementBenifitView.findViewById(R.id.reti_retrenchment_compens_Edt);
        errorMapView.put("retren_comp",mRetrenchmentcompensation);
        mRetrenchmentcompensation.setTextHint("Retrenchment compensation");
        mRetrenchmentcompensation.setfullHintTxt(getString(R.string.hint_retirement_retrenchmentcompensation));


        mVoluntaryretirement= retirementBenifitView.findViewById(R.id.reti_voluntary_retirement_Edt);
        errorMapView.put("vr_comp",mVoluntaryretirement);
        mVoluntaryretirement.setTextHint("Voluntary Retirement (VR) Compensation");
        mVoluntaryretirement.setfullHintTxt(getString(R.string.hint_retirement_voluntaryretirementcompensation));


        mRetiNoteEdt = retirementBenifitView.findViewById(R.id.reti_note_Edt);
        errorMapView.put("notes",mRetiNoteEdt);
        mRetiNoteEdt.setHintText(getString(R.string.hint_retirement_notes), ((TextInputLayout)(mRetiNoteEdt.getParent()).getParent()));

        mRetiotherEdt = retirementBenifitView.findViewById(R.id.reti_other_Edt);
        errorMapView.put("others",mRetiotherEdt);
        mRetiotherEdt.setTextHint("Others");
        mRetiotherEdt.setfullHintTxt(getString(R.string.hint_retirement_others));

        mreti_Gratuity_calculaterImgView= retirementBenifitView.findViewById(R.id.reti_Gratuity_calculaterImgView);
        mreti_Annuity_pension_calculaterImgView= retirementBenifitView.findViewById(R.id.reti_Annuity_pension_calculaterImgView);
        mreti_supperannuation_calculaterImgView= retirementBenifitView.findViewById(R.id.reti_supperannuation_calculaterImgView);
        mreti_leave_encashment_calculaterImgView= retirementBenifitView.findViewById(R.id.reti_leave_encashment_calculaterImgView);
        mreti_retrenchment_calculaterImgView= retirementBenifitView.findViewById(R.id.reti_retrenchment_calculaterImgView);
        mreti_voluntary_retirement_calculaterImgView= retirementBenifitView.findViewById(R.id.reti_voluntary_retirement_calculaterImgView);
        mreti_other_retirement_calculaterImgView= retirementBenifitView.findViewById(R.id.reti_other_retirement_calculaterImgView);

        mreti_Gratuity_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mGratuity);
            }
        });
        mreti_Annuity_pension_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mAnnuityPension);
            }
        });
        mreti_supperannuation_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mSuperannuationFund);
            }
        });
        mreti_leave_encashment_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mLeaveEncashment);
            }
        });
        mreti_retrenchment_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mRetrenchmentcompensation);
            }
        });
        mreti_voluntary_retirement_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mVoluntaryretirement);
            }
        });
        mreti_other_retirement_calculaterImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalDialog(mRetiotherEdt);
            }
        });

        mRetiNoteEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(view.isSelected())
                {
                    mScrollView.post(new Runnable() {
                        public void run() {
                            mScrollView.fullScroll(View.FOCUS_DOWN);
                            mRetiNoteEdt.requestFocus();
                        }
                    });
                }
            }
        });
        FloatingActionButton fab = retirementBenifitView.findViewById(R.id.reti_benifit_details_fab);
        setInputValues();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                getInputValues();

                Intent activity = new Intent(getActivity(), HomePageActivity.class);
                startActivity(activity);


            }
        });


        gratuity_layout=(LinearLayout)retirementBenifitView.findViewById(R.id.gratuity_layout);
        annuity_pension_layout=(LinearLayout)retirementBenifitView.findViewById(R.id.annuity_pension_layout);
        superannuantion_layout=(LinearLayout)retirementBenifitView.findViewById(R.id.superannuantion_layout);
        leave_enhashment_layout=(LinearLayout)retirementBenifitView.findViewById(R.id.leave_enhashment_layout);
        retension_layout=(LinearLayout)retirementBenifitView.findViewById(R.id.retension_layout);
        valuntary_retirement_layout=(LinearLayout)retirementBenifitView.findViewById(R.id.valuntary_retirement_layout);
        others_layout=(LinearLayout)retirementBenifitView.findViewById(R.id.others_layout);
        notes_layout=(LinearLayout)retirementBenifitView.findViewById(R.id.notes_layout);


        relative_finish_later= retirementBenifitView.findViewById(R.id.relative_finish_later);
        relative_done_arrow= retirementBenifitView.findViewById(R.id.relative_done_arrow);
        relative_finish_later.setOnClickListener(this);
        relative_done_arrow.setOnClickListener(this);

        bottom_bar_layout= retirementBenifitView.findViewById(R.id.bottom_bar_layout);
        UtileKit.mandatoryFieldLinearLayout(isSignUp,bottom_bar_layout);

        bottom_bar_donelayout= retirementBenifitView.findViewById(R.id.bottom_bar_donelayout);
        UtileKit.mandatoryFieldDoneLayout(isSignUp,bottom_bar_donelayout);


        return retirementBenifitView;
    }
    private void showCalDialog(View view) {
        nameEditview = view;
//        nameEditview.setTag(view.getTag());
        String calculaterValue = ((CurrencyGhostView) nameEditview).getText().toString();
        Intent calculatorIntent = new Intent(getActivity(), CalculatorAct.class);
        calculatorIntent.putExtra(CalculatorAct.TITLE_ACTIVITY, TITLE);
        calculatorIntent.putExtra(CalculatorAct.PARENT_ACTIVITY, PARENT_CLASS_SOURCE);
        calculatorIntent.putExtra(CalculatorAct.VALUE, calculaterValue);
        startActivityForResult(calculatorIntent, CalculatorAct.REQUEST_RESULT_SUCCESSFUL);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == CalculatorAct.REQUEST_RESULT_SUCCESSFUL) {
            String result = data.getStringExtra(CalculatorAct.RESULT);
            ((CurrencyGhostView) nameEditview).setText(result);
            ((CurrencyGhostView) nameEditview).getEditText().setBackgroundResource(R.drawable.edittextbackgrounggreen);
        }
    }
    private void getInputValues() {
        UtileKit.showSpinnerDialog(mContext,false);
        String mGratuityString,
                mAnnuityPensionString,
                mSuperannuationFundString,
                mLeaveEncashmentString,
                mRetrenchmentcompensationString,
                mVoluntaryretirementString,
                mRetiNoteEdtString,
                mRetiotherEdtString;
        mGratuityString=UtileKit.getStringwithoutDefaultCurreny(mGratuity.getEditText());
        mAnnuityPensionString=UtileKit.getStringwithoutDefaultCurreny(mAnnuityPension.getEditText());
        mSuperannuationFundString=UtileKit.getStringwithoutDefaultCurreny(mSuperannuationFund.getEditText());
        mLeaveEncashmentString=UtileKit.getStringwithoutDefaultCurreny(mLeaveEncashment.getEditText());
        mRetrenchmentcompensationString=UtileKit.getStringwithoutDefaultCurreny(mRetrenchmentcompensation.getEditText());
        mVoluntaryretirementString=UtileKit.getStringwithoutDefaultCurreny(mVoluntaryretirement.getEditText());
        mRetiNoteEdtString = mRetiNoteEdt.getText().toString();
        mRetiotherEdtString = UtileKit.getStringwithoutDefaultCurreny(mRetiotherEdt.getEditText());
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<RetirementBenifitModel> call = webServiceObj.callAddRetirementBenifitService(UtileKit.getPersistedPurplePathPref("user_id"),
                mGratuityString,
                mAnnuityPensionString,
                mSuperannuationFundString,
                mLeaveEncashmentString,
                mRetrenchmentcompensationString,
                mVoluntaryretirementString,
                mRetiotherEdtString,
                mRetiNoteEdtString);
        call.enqueue(new Callback<RetirementBenifitModel>() {
            @Override
            public void onResponse(Call<RetirementBenifitModel> call, Response<RetirementBenifitModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
                RetirementBenifitModel retirementBenifitModel = response.body();
                if (retirementBenifitModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {


//                    addViewto(fragmentview,mIncomeCategoryModel)
                } else {
//                    UtileKit.alertDialog(
//                            retirementBenifitModel.getData().getMessage(),
//                            mContext);
                }
            }

            @Override
            public void onFailure(Call<RetirementBenifitModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }


    private void emptyErrorValidation(ArrayList<String> emptyArrayList) {
        for(String obj:emptyArrayList)
        {
            View view=errorMapView.get(obj);
            UtileKit.emptyErrorViewList(view);
        }
    }

    private void setInputValues() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<RetirementBenifitModel> call = webServiceObj.callGetRetirementBenefitDetailService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<RetirementBenifitModel>() {
            @Override
            public void onResponse(Call<RetirementBenifitModel> call, Response<RetirementBenifitModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
                RetirementBenifitModel retirementBenifitModel = response.body();
                try{

                if (retirementBenifitModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    mRetirementBenifitModel = retirementBenifitModel;
                    msetGratuityString = retirementBenifitModel.getData().getRet_ben().get(0).getGratuity();
                    msetAnnuityPensionString = retirementBenifitModel.getData().getRet_ben().get(0).getAnnuity_pension();
                    msetSuperannuationFundString = retirementBenifitModel.getData().getRet_ben().get(0).getSaf();
                    msetLeaveEncashmentString = retirementBenifitModel.getData().getRet_ben().get(0).getLeave_encash();
                    msetRetrenchmentcompensationString = retirementBenifitModel.getData().getRet_ben().get(0).getRetren_comp();
                    msetVoluntaryretirementString = retirementBenifitModel.getData().getRet_ben().get(0).getVr_comp();
                    mRetiNoteEdtString = retirementBenifitModel.getData().getRet_ben().get(0).getNotes();
                    mRetiotherEdtString = retirementBenifitModel.getData().getRet_ben().get(0).getOthers();

                    if(UtileKit.validateObjectValues(retirementBenifitModel.getData().getRet_ben().get(0).getEmpty_flds()))
                        emptyErrorValidation(retirementBenifitModel.getData().getRet_ben().get(0).getEmpty_flds());


                    if(!msetGratuityString.equalsIgnoreCase("0")){
                        mGratuity.setText(msetGratuityString);
                    }
                    else
                    {
                      //  mGratuity.setHintTextEmptyError();
                    }
                    if(!msetAnnuityPensionString.equalsIgnoreCase("0")){
                        mAnnuityPension.setText(msetAnnuityPensionString);
                    }
                    else
                    {
                        //mAnnuityPension.setHintTextEmptyError();
                    }
                    if(!msetSuperannuationFundString.equalsIgnoreCase("0")){
                        mSuperannuationFund.setText(msetSuperannuationFundString);
                    }
                    else
                    {
                       // mSuperannuationFund.setHintTextEmptyError();
                    }
                    if(!msetLeaveEncashmentString.equalsIgnoreCase("0")){
                        mLeaveEncashment.setText(msetLeaveEncashmentString);
                    }
                    else
                    {
                       // mLeaveEncashment.setHintTextEmptyError();
                    }
                    if(!msetRetrenchmentcompensationString.equalsIgnoreCase("0")){
                        mRetrenchmentcompensation.setText(msetRetrenchmentcompensationString);
                    }
                    else
                    {
                       // mRetrenchmentcompensation.setHintTextEmptyError();
                    }
                    if(!msetVoluntaryretirementString.equalsIgnoreCase("0")){
                        mVoluntaryretirement.setText(msetVoluntaryretirementString);
                    }
                    else
                    {
                        //mVoluntaryretirement.setHintTextEmptyError();
                    }
                    if(!mRetiNoteEdtString.equalsIgnoreCase("0")){
                        mRetiNoteEdt.setText(mRetiNoteEdtString);
                    }
                    else
                    {
                       // mRetiNoteEdt.setHintTextEmptyError();
                    }
                    if(!mRetiotherEdtString.equalsIgnoreCase("0")){
                        mRetiotherEdt.setText(mRetiotherEdtString);
                    }
                    else
                    {
                        //mRetiotherEdt.setHintTextEmptyError();
                    }
                }
                else {
//                    UtileKit.alertDialog(retirementBenifitModel.getData().getMessage(), mContext);
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<RetirementBenifitModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_left_arrow: {
                getInputValues();
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home: {
                getInputValues();
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
//                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow: {
                getInputValues();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    TaxPrepaidFragment fragment = new TaxPrepaidFragment();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
            }
            break;
            case R.id.relative_finish_later:{

                Intent intent=new Intent(getActivity(), HomePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            break;
            case R.id.relative_done_arrow:{

                getInputValues();
                addFragmenttoStack(new PromptChatFragment1());

            }
            break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.ic_done_btn:

                try{

                    mCallBackListener.onActivityBackPressed();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();

    }
}

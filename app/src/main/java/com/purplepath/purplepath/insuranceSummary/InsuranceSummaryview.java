package com.purplepath.purplepath.insuranceSummary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.insurance.fragment.InsuranceDetailsFragment;
import com.purplepath.purplepath.insuranceAnalysis.InsuranceAnalysis;
import com.purplepath.purplepath.insuranceAnalysis.models.InsuranceChartModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bertrandrussellsakthees on 14/06/17.
 */

public class InsuranceSummaryview extends BaseFragment implements  View.OnClickListener  {
    private Context mContext;

    private OnActivityBackPressedListener mCallBackListener;

    OnActivityBackPressedListener backPressedListener;

    @BindView(R.id.comprehensive_life_insurance_cover)
    TextView comprehensive_life_insurance_cover;

    @BindView(R.id.finacial_requirement)
    TextView finacial_requirement;

    @BindView(R.id.liquidating_your_asset)
    TextView liquidating_your_asset;

    @BindView(R.id.currentInsurances_cover)
    TextView currentInsurances_cover;

    @BindView(R.id.purchase_add)
    TextView purchase_add;

    @BindView(R.id.meet_your_expenses)
    TextView meet_your_expenses;

    @BindView(R.id.meet_your_expenses1)
    TextView meet_your_expenses1;

    @BindView(R.id.relign_your_life_objectives)
    TextView relign_your_life_objectives;

    @BindView(R.id.relign_your_life_objectives1)
    TextView relign_your_life_objectives1;

    @BindView(R.id.pay_your_obligation)
    TextView pay_your_obligation;

    @BindView(R.id.pay_your_obligation1)
    TextView pay_your_obligation1;



    @BindView(R.id.relative_left_arrow)
    RelativeLayout mleftRelativeLayout;

    @BindView(R.id.rela_layout)
    RelativeLayout rela_layout;

    @BindView(R.id.relative_center_home)
    RelativeLayout mcenterRelativeLayout;

    @BindView(R.id.relative_right_arrow)
    RelativeLayout mRightRelativeLayout;

    private Float current_Insurance, current_saving,min_Surrival_cover,min_requried_cover,max_recommended_cover;

    private String current_expenses, outstandingdept, future_obligation;

    private InsuranceChartModel insuranceChartModel;

    private TextView errorTextview;
    private  ScrollView scrollview;
    private FloatingActionButton fab_id;

    String mystring;
    String curr_Min_sur_cov_req="";
    String mReq_cov_req="";
    String mRecom_cov_req="";

    String str_meet_your_expenses_positive="just to meet your expense";
    String str_meet_your_expenses_negative="Your covered to meet your expense";
    String str_pay_your_obligation_positive="to meet your expense and pay off your obligation";
    String str_pay_your_obligation_negative="Your covered to meet your expense and pay off your obligation";
    String str_relign_your_life_objectives_pos="to meet your expense and pay off your obligation and also to realign your life objective";
    String str_relign_your_life_objectives_neg="Your covered to meet your expense and pay off your obligation and also to realign your life objective";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);

            backPressedListener= (OnActivityBackPressedListener) mContext;
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View insuranceView =  inflater.inflate(R.layout.fragment_insurance_summary, container, false);
        ButterKnife.bind(this,insuranceView);
        mCallBackListener.setActionBarTitle("Life Insurance ");
        errorTextview = insuranceView.findViewById( R.id.empty_chart_display);
        scrollview = insuranceView.findViewById( R.id.scrollview);
        fab_id = insuranceView.findViewById(R.id.fab_id);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        fab_id.setOnClickListener(this);
        callInsuranceAnalysisService();// Services call here ;


        return  insuranceView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_detail_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_detail);
        MenuItem items=menu.findItem(R.id.menu_chart);
        // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_detail:

                try{
//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    InsuranceTableView fragment =
                            addFragmenttoStack(new InsuranceTableView());
//                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
//                    fragmentTransaction.commitAllowingStateLoss();
//                    mCallBackListener.onActivityBackPressed();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;



            case R.id.menu_chart:

                try{
//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    InsuranceAnalysis fragment
                      addFragmenttoStack(new InsuranceAnalysis());
//                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
//                    fragmentTransaction.commitAllowingStateLoss();
                   // mCallBackListener.onActivityBackPressed();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

        }
        return super.onOptionsItemSelected(menuItem);
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
            case R.id.fab_id:
            {
                addFragmenttoStack(new InsuranceDetailsFragment());
            }
            break;
        }

    }

    public  void callInsuranceAnalysisService(){
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<InsuranceChartModel> call = webServiceObj.callinsurance_plan_Service(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<InsuranceChartModel>() {
            @Override
            public void onResponse(Call<InsuranceChartModel> call, Response<InsuranceChartModel> response) {
                try {
                    Log.i("InsuranceTableView","InsuranceTableView user id"+UtileKit.getPersistedPurplePathPref("user_id"));
                    UtileKit.dismisssSpinnerDialog();

                    insuranceChartModel = response.body();
                    if (insuranceChartModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        if(null!= insuranceChartModel.getData().getIns_plan()) {
                            scrollview.setVisibility(View.VISIBLE);
                            current_Insurance = floatConvertion(insuranceChartModel.getData().getIns_plan().getCurr_ins());
                            current_saving = floatConvertion(insuranceChartModel.getData().getIns_plan().getTot_asset());
                            min_Surrival_cover = floatConvertion(insuranceChartModel.getData().getIns_plan().getMin_sur_cov_req());
                            min_requried_cover = floatConvertion(insuranceChartModel.getData().getIns_plan().getReq_cov_req());
                            max_recommended_cover = floatConvertion(insuranceChartModel.getData().getIns_plan().getRecom_cov_req());
                            current_expenses = insuranceChartModel.getData().getIns_plan().getCurr_exp();
                            outstandingdept = insuranceChartModel.getData().getIns_plan().getTot_liab();
                            future_obligation = insuranceChartModel.getData().getIns_plan().getFut_req();
                            Log.i("InsuranceTableView", " InsuranceTableView current_Insurance services is " + current_Insurance
                                    + " current_saving " + current_saving + " min_Surrival_cover " + min_Surrival_cover + " min_requried_cover "
                                    + min_requried_cover + " max_recommended_cover " + Math.abs(max_recommended_cover) + " current_expenses " + current_expenses
                                    + " outstandingdept " + outstandingdept + " future_obligation " + future_obligation);



                            if (insuranceChartModel.getData().getIns_plan().getCurr_ins().equalsIgnoreCase("0") &&
                                    insuranceChartModel.getData().getIns_plan().getCurr_contr().equalsIgnoreCase("0") &&
                                    insuranceChartModel.getData().getIns_plan().getMin_sur_cov_req().equalsIgnoreCase("0")
                                    &&insuranceChartModel.getData().getIns_plan().getReq_cov_req().equalsIgnoreCase("0")
                                    && insuranceChartModel.getData().getIns_plan().getRecom_cov_req().equalsIgnoreCase("0")) {
                                errorTextview.setVisibility(View.VISIBLE);
                                errorTextview.setText(HomePageActivity.errorMessageInChart);
                                UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                                scrollview.setVisibility(View.GONE);
                            }
                            else {
                                try {
                                    //Underline string value --one
                                    mystring = String.valueOf(Integer.valueOf(current_expenses)+Integer.valueOf(outstandingdept)+Integer.valueOf(future_obligation));

                                    SpannableStringBuilder builder = new SpannableStringBuilder();
                                    UnderlineSpan underlineSpan = new UnderlineSpan();


                                    String red = "Your Comprehensive life insurance cover requirement is ₹ ";
                                    SpannableString redSpannable= new SpannableString(red);
                                    redSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, red.length(), 0);
                                    builder.append(redSpannable);

                                    SpannableString content = new SpannableString(UtileKit.formatedNumber(Float.valueOf(String.valueOf(new BigInteger(mystring)))));

                                    if (mystring.compareTo(String.valueOf(BigDecimal.ZERO)) < 0){
                                        content.setSpan(new ForegroundColorSpan(Color.RED),0,content.length(),0);
                                        UtileKit.intitializeAlertDialog(getString(R.string.overinsured),mContext);
                                    }
                                    else if(mystring.compareTo(String.valueOf(BigDecimal.ZERO)) > 0){
                                        content.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.DarkGreen)),0,content.length(),0);
                                    }

                                    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
                                    content.setSpan(boldSpan, 0,content.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    content.setSpan(underlineSpan, 0, content.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    builder.append(content);

                                    long date = System.currentTimeMillis();

                                    SimpleDateFormat sdf = new SimpleDateFormat("  MMM-dd-yyyy");
                                    String dateString = sdf.format(date);

                                    SpannableString whiteSpannable= new SpannableString(dateString);
                                    whiteSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, dateString.length(), 0);
                                    builder.append(whiteSpannable);

                                    comprehensive_life_insurance_cover.setText(builder, TextView.BufferType.SPANNABLE);
                                    //Underline string value --one

                                    finacial_requirement.setText("This take care of your family's finacial requirement, in an unfortunate, event that may occur");

                                    //Underline string value --two
                                    String curr_ins = insuranceChartModel.getData().getIns_plan().getTot_asset().toString();
                                    SpannableStringBuilder builder1 = new SpannableStringBuilder();
                                    UnderlineSpan underlineSpan1 = new UnderlineSpan();

                                    String red1 = "You may arrange a cash value up to ₹ ";
                                    SpannableString redSpannable1= new SpannableString(red1);
                                    redSpannable1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, red1.length(), 0);
                                    builder1.append(redSpannable1);

                                    SpannableString content1 = new SpannableString(UtileKit.formatedNumber(Float.valueOf(curr_ins)));
                                    StyleSpan boldSpan1 = new StyleSpan(Typeface.BOLD);
                                    content1.setSpan(boldSpan1, 0,content1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    content1.setSpan(underlineSpan1, 0, content1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    builder1.append(content1);

                                    String green1 = " by liquidating your assets";
                                    SpannableString whiteSpannable1= new SpannableString(green1);
                                    whiteSpannable1.setSpan(new ForegroundColorSpan(Color.BLACK), 0, green1.length(), 0);
                                    builder1.append(whiteSpannable1);

                                    liquidating_your_asset.setText(builder1, TextView.BufferType.SPANNABLE);
                                    //Underline string value --two


                                    //Underline string value --three
                                    String total_assert = insuranceChartModel.getData().getIns_plan().getCurr_ins().toString();
                                    SpannableStringBuilder builder2 = new SpannableStringBuilder();
                                    UnderlineSpan underlineSpan2 = new UnderlineSpan();

                                    String red2 = "Your current insurance cover sms up to ₹ ";
                                    SpannableString redSpannable2= new SpannableString(red2);
                                    redSpannable2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, red2.length(), 0);
                                    builder2.append(redSpannable2);

                                    SpannableString content2 = new SpannableString(UtileKit.formatedNumber(Float.valueOf(total_assert)));
                                    StyleSpan boldSpan2 = new StyleSpan(Typeface.BOLD);
                                    content2.setSpan(boldSpan2, 0,content2.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    content2.setSpan(underlineSpan2, 0, content2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    builder2.append(content2);

                                    String green2 = " which is adequate/inadequate";
                                    SpannableString whiteSpannable2= new SpannableString(green2);
                                    whiteSpannable2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, green2.length(), 0);
                                    builder2.append(whiteSpannable2);
                                    currentInsurances_cover.setText(builder2, TextView.BufferType.SPANNABLE);
                                    //Underline string value --three

                                    purchase_add.setText("You may have to purchase additional insurance cover for ");

                                    //Underline string value --four
                                     String curr_Min_sur_cov_req1 = insuranceChartModel.getData().getIns_plan().getMin_sur_cov_req().toString();
                                     validate(curr_Min_sur_cov_req1,meet_your_expenses,meet_your_expenses1,
                                            str_meet_your_expenses_positive,str_meet_your_expenses_negative);
                                    //Underline string value --four

                                    //Underline string value --five
                                    String mReq_cov_req1 = insuranceChartModel.getData().getIns_plan().getReq_cov_req().toString();
                                    validate(mReq_cov_req1,pay_your_obligation,pay_your_obligation1,
                                            str_pay_your_obligation_positive,str_pay_your_obligation_negative);
                                    //Underline string value --five

                                    //Underline string value --six
                                    String mRecom_cov_req1 = insuranceChartModel.getData().getIns_plan().getRecom_cov_req().toString();
                                    validate(mRecom_cov_req1,relign_your_life_objectives,relign_your_life_objectives1,
                                            str_relign_your_life_objectives_pos,str_relign_your_life_objectives_neg);
                                    //Underline string value --six


                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        }else{
//                            scrolllinearlayout.setVisibility(View.GONE);
//                            errorTextview.setVisibility(View.VISIBLE);
//                            errorTextview.setText(HomePageActivity.errorMessageInChart);
//                            UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                        }
                    }else{
                        errorTextview.setVisibility(View.VISIBLE);
                        errorTextview.setText(HomePageActivity.errorMessageInChart);
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                        scrollview.setVisibility(View.GONE);


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<InsuranceChartModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }







    void validate(String mstring,TextView setvaluefromservices,TextView setconstantvalue, String mPositivevalue, String mNegative){
        if (mstring.compareTo(String.valueOf(BigDecimal.ZERO)) > 0){
            UtileKit.setHtml(setvaluefromservices,"₹ "+"<b>" +"<u>"+ (UtileKit.formatedNumber(Float.valueOf(mstring)))+"</u>" + "</b>" + "<br />");
            setconstantvalue.setText(mPositivevalue);
        }
        else {
            UtileKit.setHtml(setvaluefromservices,"₹ "+"<b>" +"<u>"+ ("0")+"</u>" + "</b>" + "<br />");
            setconstantvalue.setText(mNegative);
        }
    }




    private float floatConvertion(String val) {
        float convVal = 0;
        try {
            convVal = Float.parseFloat(val);
        } catch (NumberFormatException e) {

        }
        return convVal;
    }
}

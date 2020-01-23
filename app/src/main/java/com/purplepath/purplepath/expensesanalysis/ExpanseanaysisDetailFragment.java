package com.purplepath.purplepath.expensesanalysis;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CurrencyTextView;
import com.purplepath.purplepath.customview.DefaultCurrencyTextView;
import com.purplepath.purplepath.expenseEDCOC.ExpenseTabMainFragment;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev0;
import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.GetExpensesDetailsModel;
import com.purplepath.purplepath.expensesanalysis.fragment.ExpensesAnalysisFragment;
import com.purplepath.purplepath.expensesanalysis.model.ExpensesAnalysisModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExpanseanaysisDetailFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private Context mContext;
    private ExpensesAnalysisModel mexpensesAnalysisModel;


    private TextView errorTextview,msource_expanse;
    private TextView mcharactercontribution_value,mcharactercare_value,meducation_value,
            mentertainmentrecreation_value,mfinespenalty_value,mfood,mgifts_value,mhealthexpense_value,
            mholidayvocation_value,mpurchase_value,mpetcare_value,mshelter_value,mSkilldevelopment_value,
            mtax_value,mtransport_value,mutility_value,mtotal_currencyTextView;
    private DefaultCurrencyTextView mEssential_value,mDiscretionary_value,mCommitment_value,
            mObligation_value,mContribution_value;

    private LinearLayout mlayout_charitycon,mlayout_childcare,mlayout_education,mlayout_entrecre,
            mlayout_finespenalty,mlayout_food,mlayout_gifts,mlayout_healthexp,mlayout_holivoca,
            mlayout_purchase,mlayout_petcare,mlayout_shelter,mlayout_skilldev,mlayout_tax,mlayout_transport,
            mlayout_utility;
    private LinearLayout mlayout_Essential,mlayout_Discretionary,mlayout_Commitment,
            mlayout_Obligation,mlayout_Contribution;

    private FloatingActionButton mEditExpenseFabBtn;
    GetExpensesDetailsModel expenseModel;

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_expanseanaysis_detail1, container, false);
        backPressedListener.setActionBarTitle("Expense Analysis");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        mEssential_value= view.findViewById(R.id.Essential_value);
        mDiscretionary_value= view.findViewById(R.id.Discretionary_value);
        mCommitment_value= view.findViewById(R.id.Commitment_value);
        mObligation_value= view.findViewById(R.id.Obligation_value);
        mContribution_value= view.findViewById(R.id.Contribution_value);

        /*mcharactercontribution_value = (CurrencyTextView) view.findViewById(R.id.charactercontribution_value);
        mcharactercare_value = (CurrencyTextView) view.findViewById(R.id.charactercare_value);
        meducation_value = (CurrencyTextView) view.findViewById(R.id.education_value);
        mentertainmentrecreation_value= (CurrencyTextView) view.findViewById(R.id.entertainmentrecreation_value);
        mfinespenalty_value= (CurrencyTextView) view.findViewById(R.id.finespenalty_value);
        mfood= (CurrencyTextView) view.findViewById(R.id.food_value);
        mgifts_value= (CurrencyTextView) view.findViewById(R.id.gifts_value);
        mhealthexpense_value= (CurrencyTextView) view.findViewById(R.id.healthexpense_value);
        mholidayvocation_value= (CurrencyTextView) view.findViewById(R.id.holidayvocation_value);
        mpurchase_value= (CurrencyTextView) view.findViewById(R.id.purchase_value);
        mpetcare_value= (CurrencyTextView) view.findViewById(R.id.petcare_value);
        mshelter_value= (CurrencyTextView) view.findViewById(R.id.shelter_value);
        mSkilldevelopment_value= (CurrencyTextView) view.findViewById(R.id.Skilldevelopment_value);
        mtax_value= (CurrencyTextView) view.findViewById(R.id.tax_value);
        mtransport_value= (CurrencyTextView) view.findViewById(R.id.transport_value);
        mutility_value= (CurrencyTextView) view.findViewById(R.id.utility_value);*/


        mtotal_currencyTextView= view.findViewById(R.id.total_currencyTextView);
        msource_expanse= view.findViewById(R.id.source_expanse);

        mlayout_Essential= view.findViewById(R.id.layout_Essential);
        mlayout_Discretionary= view.findViewById(R.id.layout_Discretionary);
        mlayout_Commitment= view.findViewById(R.id.layout_Commitment);
        mlayout_Obligation= view.findViewById(R.id.layout_Obligation);
        mlayout_Contribution= view.findViewById(R.id.layout_Contribution);


        /*mlayout_charitycon=(LinearLayout)view.findViewById(R.id.layout_charitycon);
        mlayout_childcare=(LinearLayout)view.findViewById(R.id.layout_childcare);
        mlayout_education=(LinearLayout)view.findViewById(R.id.layout_education);
        mlayout_entrecre=(LinearLayout)view.findViewById(R.id.layout_entrecre);
        mlayout_finespenalty=(LinearLayout)view.findViewById(R.id.layout_finespenalty);
        mlayout_food=(LinearLayout)view.findViewById(R.id.layout_food);
        mlayout_gifts=(LinearLayout)view.findViewById(R.id.layout_gifts);
        mlayout_healthexp=(LinearLayout)view.findViewById(R.id.layout_healthexp);
        mlayout_holivoca=(LinearLayout)view.findViewById(R.id.layout_holivoca);
        mlayout_purchase=(LinearLayout)view.findViewById(R.id.layout_purchase);
        mlayout_petcare=(LinearLayout)view.findViewById(R.id.layout_petcare);
        mlayout_shelter=(LinearLayout)view.findViewById(R.id.layout_shelter);
        mlayout_skilldev=(LinearLayout)view.findViewById(R.id.layout_skilldev);
        mlayout_tax=(LinearLayout)view.findViewById(R.id.layout_tax);
        mlayout_transport=(LinearLayout)view.findViewById(R.id.layout_transport);
        mlayout_utility=(LinearLayout)view.findViewById(R.id.layout_utility);*/

        mEditExpenseFabBtn= view.findViewById(R.id.expense_fab_id);
        mEditExpenseFabBtn.setOnClickListener(this);

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        callExpensesAnalysisService();
        callExpensesAnalysisServiceNew();
        return view;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_chart);
        // item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_chart:

                try{
                    addFragmenttoStack(new ExpensesAnalysisFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            case R.id.menu_summary:

                try{
                    addFragmenttoStack(new ExpanseanaysisMainPageFragment());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void callExpensesAnalysisService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<ExpensesAnalysisModel> call = webServiceObj.callExpensesAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"),"Y");
        call.enqueue(new Callback<ExpensesAnalysisModel>() {
            @Override
            public void onResponse(Call<ExpensesAnalysisModel> call, Response<ExpensesAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success", "" + response.body());
                mexpensesAnalysisModel = response.body();
                try {
                if(mexpensesAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    //if(null != mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det()) {
                        /*String char_contri=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getChar_contri();
                        String char_care=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getCh_care_suppt();
                        String education=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getEdu();
                        String ent_recre=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getEnt_recre();
                        String fines_penalty=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getFines_penalty();
                        String foods=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getFood();
                        String gifts=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getGifts();
                        String health_exp=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getHealth_exp();
                        String hol_voc=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getHol_vac();
                        String purchase=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getPurchases();
                        String pet_care=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getPet_care();
                        String shelter=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getShelter();
                        String skill_dev=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getSkill_dev();
                        String tax=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getTax();
                        String transport=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getTransport();
                        String utility=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getFam_det().getUtility();*/

                        String overall_expense=mexpensesAnalysisModel.getData().getUser_exp_analysis().get(0).getOverall_expense();

                        /*validate(char_contri,mlayout_charitycon,mcharactercontribution_value);
                        validate(char_care,mlayout_childcare,mcharactercare_value);
                        validate(education,mlayout_education,meducation_value);
                        validate(ent_recre,mlayout_entrecre,mentertainmentrecreation_value);
                        validate(fines_penalty,mlayout_finespenalty,mfinespenalty_value);
                        validate(foods,mlayout_food,mfood);
                        validate(gifts,mlayout_gifts,mgifts_value);
                        validate(health_exp,mlayout_healthexp,mhealthexpense_value);
                        validate(hol_voc,mlayout_holivoca,mholidayvocation_value);
                        validate(purchase,mlayout_purchase,mpurchase_value);
                        validate(pet_care,mlayout_petcare,mpetcare_value);
                        validate(shelter,mlayout_shelter,mshelter_value);
                        validate(skill_dev,mlayout_skilldev,mSkilldevelopment_value);
                        validate(tax,mlayout_tax,mtax_value);
                        validate(transport,mlayout_transport,mtransport_value);
                        validate(utility,mlayout_utility,mutility_value);*/

                        DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                        Date date=new Date();
                        String [] value=dateFormat.format(date).split("/");
                        msource_expanse.setText("As on "+value[2]+" "+UtileKit.getMonthString(Integer.parseInt(value[1])-1)+" "+value[0]);

                        mtotal_currencyTextView.setText("₹ "+(UtileKit.longvalueabsolute(Float.parseFloat(overall_expense))));
                }else{
                  //  UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                            }
            @Override
            public void onFailure(Call<ExpensesAnalysisModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }


    //Expense new
    public void callExpensesAnalysisServiceNew() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetExpensesDetailsModel> call = webServiceObj.callGetExpensesDetailsService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetExpensesDetailsModel>() {
            @Override
            public void onResponse(Call<GetExpensesDetailsModel> call, Response<GetExpensesDetailsModel> response) {
                UtileKit.dismisssSpinnerDialog();
                expenseModel = response.body();
                try {
                    if(expenseModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        if (expenseModel.getData().getUser_expense() != null) {
                            if (!expenseModel.getData().getUser_expense().isEmpty()) {

                                for (Expense_cat_lev0 obj:expenseModel.getData().getUser_expense().get(0).getExpense_cat_lev0()) {
                                    setExpenseSetValue(obj.getId(),obj.getValue());
                                }
                            }
                        }
                    }else{
                       // UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetExpensesDetailsModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void setExpenseSetValue(String id, String value) {
        switch (id)
        {
            case "1":
                mEssential_value.setText(""+value);
                mlayout_Essential.setVisibility(View.VISIBLE);
                break;
            case "2":
                mDiscretionary_value.setText(""+value);
                mlayout_Discretionary.setVisibility(View.VISIBLE);
                break;
            case "3":
                mCommitment_value.setText(""+value);
                mlayout_Commitment.setVisibility(View.VISIBLE);
                break;
            case "4" :
                mObligation_value.setText(""+value);
                mlayout_Obligation.setVisibility(View.VISIBLE);
                break;
            case "5":
                mContribution_value.setText(""+value);
                mlayout_Contribution.setVisibility(View.VISIBLE);
                break;
        }
    }

        void validate(String mstring,LinearLayout linearLayout,CurrencyTextView currencyTextView){
        if (mstring==null||mstring.equalsIgnoreCase("0")) {
            //linearLayout.setText("0");
            linearLayout.setVisibility(View.GONE);
        }
        else {
            currencyTextView.setText(mstring);
            linearLayout.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                startHomeActivity();
                break;
            case R.id.expense_fab_id:
                addFragmenttoStack(new ExpenseTabMainFragment());
                break;

        }

    }
}

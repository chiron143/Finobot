package com.purplepath.purplepath.settings.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.settings.Interfaces.UpdateOnNavigation;
import com.purplepath.purplepath.settings.UpdateModels.AsssumptionsUpdateModel;
import com.purplepath.purplepath.settings.models.AssumptionsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Pratheep.S on 04-01-2017.
 */

public class AssumptionsFragment extends BaseFragment implements View.OnClickListener,UpdateOnNavigation {
    private EditText etMF, etEquity, etBonds, etLiquid, etGold, etRealEstate, etBankPO, etPPF, etInflation, etRiskFreeInst;
    private EditText etITeffective, etITMariginal, etServiceTax, etIncomeRate, etExpenseRate, etAssetRate, etLiabRate, etGoalRate;
    private ImageView ivEquity, ivMF;
    private int height, width;
    private AssumptionsModel assumptionsModel;

    private int editTextIDs[]={R.id.et_equity,R.id.et_mf,R.id.et_bonds,R.id.et_liquid_mf,R.id.et_gold,R.id.et_real_estate,
    R.id.et_Bank_Post_Office,R.id.et_ppf,R.id.et_Inflation,R.id.et_RiskFreeInstruments,R.id.et_IncomeTaxRateEffective,R.id.et_IncomeTaxRateMarginal,
    R.id.et_ServiceTax,R.id.et_IncomeRate,R.id.et_ExpenseRate,R.id.et_AssetRate,R.id.et_LiabRate,R.id.et_GoalRate};

    private HashMap<Integer,String> map=new HashMap<>();
    public View view;
    //private boolean isDataLoaded=false;

    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assumptions, container, false);
        mContext = getContext();
        initiallizeAllViews(view);
        callAssumptionsWebservice();
        calculateDisplayMetrics();
        setOnClickListenerForImageView(view);
        return view;
    }

    private void setOnClickListenerForImageView(View view) {
        ViewGroup viewGroup = view.findViewById(R.id.root_assumptions);
        ViewGroup subviewGroup;
        View genericView;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            subviewGroup = (ViewGroup) viewGroup.getChildAt(i);
            genericView = subviewGroup.getChildAt(0);

            if (genericView instanceof ImageView) {
                genericView.setOnClickListener(this);
            }

        }

    }


    private void calculateDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

    }

    private void initiallizeAllViews(View view) {
        etEquity = view.findViewById(R.id.et_equity);
        etMF = view.findViewById(R.id.et_mf);
        etBonds = view.findViewById(R.id.et_bonds);
        etLiquid = view.findViewById(R.id.et_liquid_mf);
        etGold = view.findViewById(R.id.et_gold);
        etRealEstate = view.findViewById(R.id.et_real_estate);
        etBankPO = view.findViewById(R.id.et_Bank_Post_Office);
        etPPF = view.findViewById(R.id.et_ppf);
        etInflation = view.findViewById(R.id.et_Inflation);
        etRiskFreeInst = view.findViewById(R.id.et_RiskFreeInstruments);
        etITeffective = view.findViewById(R.id.et_IncomeTaxRateEffective);
        etITMariginal = view.findViewById(R.id.et_IncomeTaxRateMarginal);
        etServiceTax = view.findViewById(R.id.et_ServiceTax);
        etIncomeRate = view.findViewById(R.id.et_IncomeRate);
        etExpenseRate = view.findViewById(R.id.et_ExpenseRate);
        etAssetRate = view.findViewById(R.id.et_AssetRate);
        etLiabRate = view.findViewById(R.id.et_LiabRate);
        etGoalRate = view.findViewById(R.id.et_GoalRate);
        ivMF = view.findViewById(R.id.mfPopView);
        ivEquity = view.findViewById(R.id.equity_PopView);

    }

    public void callAssumptionsWebservice() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls callObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<AssumptionsModel> call= callObj.callAssumptionsService(UtileKit.getPersistedPurplePathPref("user_id"));
        Log.i("spcheck", "callAssumptionsWebservice: get user id"+UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<AssumptionsModel>() {
            @Override
            public void onResponse(Call<AssumptionsModel> call, Response<AssumptionsModel> response) {
                UtileKit.dismisssSpinnerDialog();
                assumptionsModel = response.body();
                 if(assumptionsModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)){
                    updateAllfields(response.body());
                }else{
                     UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                 }
            }

            @Override
            public void onFailure(Call<AssumptionsModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext,t);
            }
        });

    }

    private void updateAllfields(AssumptionsModel response) {
       // Log.i("spcheck", response.getData().getSas_assumptions().get(0).getAssumed_value());
        try {
            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(0).getAssumed_value())) {
                etEquity.append(response.getData().getSas_assumptions().get(0).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(1).getAssumed_value())) {
                etMF.append(response.getData().getSas_assumptions().get(1).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(2).getAssumed_value())) {
                etBonds.append(response.getData().getSas_assumptions().get(2).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(3).getAssumed_value())) {
                etLiquid.append(response.getData().getSas_assumptions().get(3).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(4).getAssumed_value())) {
                etGold.append(response.getData().getSas_assumptions().get(4).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(5).getAssumed_value())) {
                etRealEstate.append(response.getData().getSas_assumptions().get(5).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(6).getAssumed_value())) {
                etBankPO.append(response.getData().getSas_assumptions().get(6).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(7).getAssumed_value())) {
                etPPF.append(response.getData().getSas_assumptions().get(7).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(8).getAssumed_value())) {
                etInflation.append(response.getData().getSas_assumptions().get(8).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(9).getAssumed_value())) {
                etRiskFreeInst.append(response.getData().getSas_assumptions().get(9).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(10).getAssumed_value())) {
                etITeffective.append(response.getData().getSas_assumptions().get(10).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(11).getAssumed_value())) {
                etITMariginal.append(response.getData().getSas_assumptions().get(11).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(12).getAssumed_value())) {
                etServiceTax.append(response.getData().getSas_assumptions().get(12).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(13).getAssumed_value())) {
                etIncomeRate.append(response.getData().getSas_assumptions().get(13).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(14).getAssumed_value())) {
                etExpenseRate.append(response.getData().getSas_assumptions().get(14).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(15).getAssumed_value())) {
                etAssetRate.append(response.getData().getSas_assumptions().get(15).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(16).getAssumed_value())) {
                etLiabRate.append(response.getData().getSas_assumptions().get(16).getAssumed_value());
            }

            if (UtileKit.validateObjectValuesAndCheckZero(response.getData().getSas_assumptions().get(17).getAssumed_value())) {
                etGoalRate.append(response.getData().getSas_assumptions().get(17).getAssumed_value());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("spcheck", "onResume: Assumptions");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("spcheck", "onPause: Assumptions ");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.equity_PopView:
                showPopUpWindow(view, 0);
                break;

            case R.id.mfPopView:
                showPopUpWindow(view, 1);
                break;

            case R.id.bonds_PopView:
                showPopUpWindow(view, 2);
                break;

            case R.id.liquidmf_PopView:
                showPopUpWindow(view, 3);
                break;

            case R.id.goldPopView:
                showPopUpWindow(view, 4);
                break;

            case R.id.realestate_PopView:
                    showPopUpWindow(view, 5);
                break;

            case R.id.bank_postoffice_PopView:
                    showPopUpWindow(view, 6);
                break;

            case R.id.ppfPopView:
                    showPopUpWindow(view, 7);
                break;

            case R.id.inflation_PopView:
                    showPopUpWindow(view, 8);
                break;

            case R.id.Riskfree_PopView:
                    showPopUpWindow(view, 9);
                break;

            case R.id.it_effective_PopView:
                    showPopUpWindow(view, 10);
                break;

            case R.id.it_marginalPopView:
                    showPopUpWindow(view, 11);
                break;

            case R.id.servicePopView:
                    showPopUpWindow(view, 12);
                break;

            case R.id.incomePopView:
                    showPopUpWindow(view, 13);
                break;

            case R.id.expensePopView:
                    showPopUpWindow(view, 14);
                break;

            case R.id.assetratePopView:
                    showPopUpWindow(view, 15);
                break;

            case R.id.liabratePopView:
                    showPopUpWindow(view, 16);
                break;

            case R.id.goalratePopView:
                    showPopUpWindow(view, 17);
                break;

        }
    }


    private void updateAssumptionsWebservice() {
        UtileKit.showSpinnerDialog(mContext, false);
        String val;
        int noOfElements=editTextIDs.length;
        for(int i=0;i<noOfElements;i++){
            val=((EditText)view.findViewById(editTextIDs[i])).getText().toString();
            Log.i("spcheck", "Edit Text val updateAssumptionsWebservice: " + val);
            map.put(i,val);
        }
        JSONObject finalObj= makeJsonObject();
        WebServiceCalls callObj = ServiceGenerator.createService(WebServiceCalls.class);
        //Call<AsssumptionsUpdateModel> call=callObj.updateAssumptionsService("1",finalObj.toString());
        Log.i("spcheck", "updateAssumptionsWebservice: json value "+finalObj.toString());
        Log.i("spcheck", "updateAssumptionsWebservice: update userid "+UtileKit.getPersistedPurplePathPref("user_id"));
        Call<AsssumptionsUpdateModel> call=callObj.updateAssumptionsService(UtileKit.getPersistedPurplePathPref("user_id"),
                finalObj.toString());
        call.enqueue(new Callback<AsssumptionsUpdateModel>() {
            @Override
            public void onResponse(Call<AsssumptionsUpdateModel> call, Response<AsssumptionsUpdateModel> response) {
                Log.i("spcheck", "onResponse: success");
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<AsssumptionsUpdateModel> call, Throwable t) {
                Log.i("spcheck", "onResponse: failure");
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( getActivity(),t);

            }
        });

    }

    private  JSONObject makeJsonObject() {
        JSONObject obj = null;
        JSONArray array = new JSONArray();
        for (int i = 0; i < map.size(); i++) {
            obj = new JSONObject();
            try {
                obj.put("class_id", (i+1)+"");
                obj.put("assumed_value", map.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
        }
        JSONObject finalObj = new JSONObject();
        try {
            finalObj.put("sas_det", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
      return finalObj;
    }

    private void showPopUpWindow(View view, int i) {
       // if(isDataLoaded) {
        try {
            if (assumptionsModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.assumptions_popup, null);
                final PopupWindow popupWindow = new PopupWindow(popupView);
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setWidth((int) (width * .4));

                EditText guidlineValue = popupView.findViewById(R.id.guidlineValuePopup);
                EditText publishedValue = popupView.findViewById(R.id.publishedValuePopup);
                if (assumptionsModel.getData().getSas_assumptions() != null) {
                    guidlineValue.setText(assumptionsModel.getData().getSas_assumptions().get(i).getGuideline_value());
                    publishedValue.setText(assumptionsModel.getData().getSas_assumptions().get(i).getPublished_value());
                }

                guidlineValue.setKeyListener(null);
                publishedValue.setKeyListener(null);

                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button ok = popupView.findViewById(R.id.expected_inc_cancel);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAsDropDown(view, 50, -30);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void updateAllFields() {
        //if(isDataLoaded) {
        try {
//            if (assumptionsModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                Log.i("spcheck", "updateAllFields: Assumptions ");
                updateAssumptionsWebservice();
//            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

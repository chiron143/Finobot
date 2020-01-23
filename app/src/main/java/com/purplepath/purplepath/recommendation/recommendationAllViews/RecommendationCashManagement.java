package com.purplepath.purplepath.recommendation.recommendationAllViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.recommendation.getcashmodel.Cashmodel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pravinr on 3/7/18.
 */

public class RecommendationCashManagement extends BaseFragment implements View.OnClickListener  {

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    private Cashmodel cashmodel;

    private String rs="&#x20B9";

    private WebView webViewCashManagement;

    private String deficit_color="";

    private String deflict="";

    TextView empty_values,title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
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
        return inflater.inflate(R.layout.view_popup_cashmanagement, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallBackListener.setActionBarTitle("Cash Management");

        webViewCashManagement = view.findViewById(R.id.webview_cashmang);

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        empty_values= view.findViewById(R.id.empty_values);
        title= view.findViewById(R.id.title);
        callAllInsuranceService();

    }
    private void callAllInsuranceService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Cashmodel> call = webServiceObj.getCashManagementRecommendation(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Cashmodel>() {
            @Override
            public void onResponse(Call<Cashmodel> call, Response<Cashmodel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    cashmodel = response.body();

                    if (cashmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if (null != cashmodel.getData().getCash_mang_det()) {
                            title.setText("Your Cash Management");
                            webViewCashManagement.setVisibility(View.VISIBLE);
                            applyCashManagement(cashmodel);
                        }
                    }
                    else{
                        webViewCashManagement.setVisibility(View.GONE);
                        empty_values.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Cashmodel> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void applyCashManagement(Cashmodel cashmodel) {
        if(cashmodel.getData().getCash_mang_det()!= null){

            String si_total_exist="",ip_total_exist="",ib_total_exist="",
                    cg_total_exist="",ifs_total_exist="";
            if(cashmodel.getData().getCash_mang_det().getSi_total_exist()!= null){
                si_total_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getSi_total_exist())));
            }else{
                si_total_exist= "0";
            }

            if(cashmodel.getData().getCash_mang_det().getIp_total_exist()!= null){
                ip_total_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getIp_total_exist())));
            }else{
                ip_total_exist= "0";
            }
            if(cashmodel.getData().getCash_mang_det().getIb_total_exist()!= null){
                ib_total_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getIb_total_exist())));
            }else{
                ib_total_exist= "0";
            }
            if(cashmodel.getData().getCash_mang_det().getCg_total_exist()!= null){
                cg_total_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getCg_total_exist())));
            }else{
                cg_total_exist= "0";
            }
            if(cashmodel.getData().getCash_mang_det().getIfs_total_exist()!= null){
                ifs_total_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getIfs_total_exist())));
            }else{
                ifs_total_exist= "0";
            }


            String si_total_sugg="",ip_total_sugg="",ib_total_sugg="",
                    cg_total_sugg="",ifs_total_sugg="";

            if(cashmodel.getData().getCash_mang_det().getSi_total_sugg()!= null){
                si_total_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getSi_total_sugg())));
            }else{
                si_total_sugg= "0";
            }

            if(cashmodel.getData().getCash_mang_det().getIp_total_sugg()!= null){
                ip_total_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getIp_total_sugg())));
            }else{
                ip_total_sugg= "0";
            }
            if(cashmodel.getData().getCash_mang_det().getIb_total_sugg()!= null){
                ib_total_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getIb_total_sugg())));
            }else{
                ib_total_sugg= "0";
            }
            if(cashmodel.getData().getCash_mang_det().getCg_total_sugg()!= null){
                cg_total_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getCg_total_sugg())));
            }else{
                cg_total_sugg= "0";
            }
            if(cashmodel.getData().getCash_mang_det().getIfs_total_sugg()!= null){
                ifs_total_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getIfs_total_sugg())));
            }else{
                ifs_total_sugg= "0";
            }

            String over_all_exp_exist="",over_all_commt_exist="",over_all_obli_exist="",
                    over_all_contr_exist="";


            if(cashmodel.getData().getCash_mang_det().getOver_all_exp_exist()!= null){
                over_all_exp_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getOver_all_exp_exist())));
            }else{
                over_all_exp_exist= "0";
            }
            if(cashmodel.getData().getCash_mang_det().getOver_all_commt_exist()!= null){
                over_all_commt_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getOver_all_commt_exist())));
            }else{
                over_all_commt_exist= "0";
            }
            if(cashmodel.getData().getCash_mang_det().getOver_all_obli_exist()!= null){
                over_all_obli_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getOver_all_obli_exist())));
            }else{
                over_all_obli_exist= "0";
            }
            if(cashmodel.getData().getCash_mang_det().getOver_all_contr_exist()!= null){
                over_all_contr_exist=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getOver_all_contr_exist())));
            }else{
                over_all_contr_exist= "0";
            }

            String over_all_exp_sugg="",over_all_commt_sugg="",over_all_obli_sugg="",
                    over_all_contr_sugg="";

            if(cashmodel.getData().getCash_mang_det().getOver_all_exp_sugg()!= null){
                over_all_exp_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getOver_all_exp_sugg())));
            }else{
                over_all_exp_sugg= "0";
            }
            if(cashmodel.getData().getCash_mang_det().getOver_all_commt_sugg()!= null){
                over_all_commt_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getOver_all_commt_sugg())));
            }else{
                over_all_commt_sugg= "0";
            }
            if(cashmodel.getData().getCash_mang_det().getOver_all_obli_sugg()!= null){
                over_all_obli_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getOver_all_obli_sugg())));
            }else{
                over_all_obli_sugg= "0";
            }
            if(cashmodel.getData().getCash_mang_det().getOver_all_contr_sugg()!= null){
                over_all_contr_sugg=(UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().
                        getCash_mang_det().getOver_all_contr_sugg())));
            }else{
                over_all_contr_sugg= "0";
            }


            if(cashmodel.getData().getCash_mang_det().getDeflict()!= null){
                if(Integer.parseInt(cashmodel.getData().getCash_mang_det().getDeflict())<0) {
                    deflict = (UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().getCash_mang_det().
                            getDeflict())))+" Deficit";
                    deficit_color="<font color='#FF0000'>" +rs+" "+ deflict + "</font>";
                }
                else if(Integer.parseInt(cashmodel.getData().getCash_mang_det().getDeflict())>0){
                    deflict = (UtileKit.longvalueabsolute(Float.parseFloat(cashmodel.getData().getCash_mang_det().
                            getDeflict())))+" Surplus";
                    deficit_color="<font color='#006400'>" +rs+" "+ deflict + "</font>";
                }
            }else{
                deflict= "0";
            }



            String htmlContents = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"150px\", user-scalable=yes\" />" +
                    "<body>\n" +
                    "\n" +

                    "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
                    "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+

                    "  <tr align = \"center\">\n" +
                    "    <th width = \"150px\">Earning</th>\n" +
                    "    <th width = \"150px\">Existings</th> \n" +
                    "    <th width = \"150px\">Suggested</th>\n" +
                    "  </tr>\n" +
                    "  <tr align = \"center\">\n" +
                    "    <td>Salary</td>\n" +
                    "    <td>" +rs+" "+ si_total_exist + "</td>\n" +
                    "    <td>" +rs+" "+ si_total_sugg  + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr align = \"center\">\n" +
                    "    <td>Property</td>\n" +
                    "    <td>" +rs+" "+ ip_total_exist + "</td>\n" +
                    "    <td>" +rs+" "+ ip_total_sugg + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr align = \"center\">\n" +
                    "    <td>Business</td>\n" +
                    "    <td>" +rs+" "+ ib_total_exist + "</td>\n" +
                    "    <td>" +rs+" "+ ib_total_sugg + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr align = \"center\">\n" +
                    "    <td>Capital Gains</td>\n" +
                    "    <td>" +rs+" "+ cg_total_exist + "</td>\n" +
                    "    <td>" +rs+" "+ cg_total_sugg + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr align = \"center\">\n" +
                    "    <td>Other Source</td>\n" +
                    "    <td>" +rs+" "+ ifs_total_exist + "</td>\n" +
                    "    <td>" +rs+" "+ ifs_total_sugg +  "</td>\n" +
                    "  </tr>\n" +
                    "</table>\n" +

                    "</p>" +
                    "\n" +


                    "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"150px\", user-scalable=yes\" />" +
                    "<body>\n" +
                    "\n" +


                    "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
                    "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+

                    "  <tr align = \"center\">\n" +
                    "    <th width = \"150px\">Expenses</th>\n" +
                    "    <th width = \"150px\">Existings</th> \n" +
                    "    <th width = \"150px\">Suggested</th>\n" +
                    "  </tr>\n" +
                    "  <tr align = \"center\">\n" +
                    "    <td>Expense</td>\n" +
                    "    <td>" +rs+" "+ over_all_exp_exist + "</td>\n" +
                    "    <td>" +rs+" "+ over_all_exp_sugg + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr align = \"center\">\n" +
                    "    <td>Commitment</td>\n" +
                    "    <td>" +rs+" "+ over_all_commt_exist + "</td>\n" +
                    "    <td>" +rs+" "+ over_all_commt_sugg + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr align = \"center\">\n" +
                    "    <td>Obligation</td>\n" +
                    "    <td>" +rs+" "+ over_all_obli_exist+ "</td>\n" +
                    "    <td>" +rs+" "+ over_all_obli_sugg + "</td>\n" +
                    "  </tr>\n" +
                    "  <tr align = \"center\">\n" +
                    "    <td>Contribution</td>\n" +
                    "    <td>" +rs+" "+ over_all_contr_exist+ "</td>\n" +
                    "    <td>" +rs+" "+ over_all_contr_sugg + "</td>\n" +
                    "  </tr>\n" +
                    "</table>\n" +

                    "<br> <p align = \"center\"> Your existing cashflow for the next 1 year is <font color=\"\"><b>"
                    + deficit_color + "</b></font><br>" +

                    " <p align = \"center\"> By adjusting your cashflow to the suggestion your cashflow for the next 1 year will be <font color=\"#7a0098;\"><b>"
                    +rs+" "+ 0 + "</b></font><br>" +


                    "</body>\n" +
                    "</html>\n";
            webViewCashManagement.loadData(htmlContents, "text/html", "UTF-8");
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow:
            {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
            break;


        }
    }

}

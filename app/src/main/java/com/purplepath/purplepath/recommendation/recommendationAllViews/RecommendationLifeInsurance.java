package com.purplepath.purplepath.recommendation.recommendationAllViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.recommendation.adapter.RecomendationLifeInsuranceTableAdapter;
import com.purplepath.purplepath.recommendation.model.RecommendData;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by pravinr on 3/7/18.
 */

public class RecommendationLifeInsurance  extends BaseFragment implements View.OnClickListener  {

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    RecommendData recommendData;

    private AbstractTableAdapter mTableViewAdapter;

    private TableView mTableView;

    private ArrayList<String> mRownameInsurancePlan=new ArrayList<String>( Arrays.asList("Minimum Required Cover", "Minimum Required Cover to Purchage", "Maximum Required Cover","Maxmum Required Cover to Purchage"));

    RelativeLayout life_insurance_container;

    private RecomendationLifeInsuranceTableAdapter recomendationLifeInsuranceTableAdapter;

    private ArrayList<String> mColoumNameListIns_Plan=new ArrayList<String>( Arrays.asList("   Title   ", "Suggested Cover", "Suggested Premium"));

    private WebView webview;

    String  min_Req_Cover="",min_Req_Cover_Purchase="",
            max_Req_Cover="",max_Req_Cover_Purchase="",hlvValue="";

    private String rs="&#x20B9";

    public static RecommendationLifeInsurance newInstance(RecommendData recommendData) {
        RecommendationLifeInsurance recommend = new RecommendationLifeInsurance();
        Bundle args = new Bundle();
        if (recommendData != null) {
            args.putSerializable("recommendData", recommendData);
        }
        recommend.setArguments(args);
        return recommend;
    }

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
        return inflater.inflate(R.layout.fragment_recommendation_life, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallBackListener.setActionBarTitle("Life Insurance Plan");

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        webview = view.findViewById(R.id.webview);


        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey("recommendData")) {
                recommendData = (RecommendData) args.getSerializable("recommendData");
                try {
                    if (recommendData.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
                        if (recommendData.getData().getIns_plan() != null){
                            applylifeInsuranceInformation(recommendData);
                    }
                    }else {
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }


    }

    private void applylifeInsuranceInformation(RecommendData recommendData) {
        if (recommendData.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

            if (recommendData.getData().getIns_plan()!= null) {

                if (recommendData.getData().getIns_plan().getMin_cov_need()!= null&&
                        (Float.parseFloat(recommendData.getData().getIns_plan().getMin_cov_need())>0)) {

                    min_Req_Cover=(UtileKit.currToCharConversion(recommendData.getData().
                            getIns_plan().getMin_cov_need()));
                }

                else {
                    min_Req_Cover = "0";
                }
                if (recommendData.getData().getIns_plan().getMin_cur_ins_cov()!= null&&
                        (Float.parseFloat(recommendData.getData().getIns_plan().getMin_cur_ins_cov())>0)){

                    min_Req_Cover_Purchase=(UtileKit.currToCharConversion(recommendData.getData().
                            getIns_plan().getMin_cur_ins_cov()));
                } else {
                    min_Req_Cover_Purchase = "0";
                }
                if (recommendData.getData().getIns_plan().getMax_cov_need()!= null&&
                        (Float.parseFloat(recommendData.getData().getIns_plan().getMax_cov_need())>0)) {

                    max_Req_Cover=(UtileKit.currToCharConversion(recommendData.getData().getIns_plan().
                            getMax_cov_need()));
                } else {
                    max_Req_Cover = "0";
                }
                if (recommendData.getData().getIns_plan().getMax_cur_ins_cov()!= null&&
                        (Float.parseFloat(recommendData.getData().getIns_plan().getMax_cur_ins_cov())>0)) {

                    max_Req_Cover_Purchase=(UtileKit.currToCharConversion(recommendData.getData().getIns_plan().
                            getMax_cur_ins_cov()));
                } else {
                    max_Req_Cover_Purchase = "0";
                }



                if (recommendData.getData().getIns_plan().getHlv()!= null) {
                    hlvValue=recommendData.getData().getIns_plan().getHlv();
                } else {
                    hlvValue = "0";
                }
                try {
                    hlvValue = (UtileKit.currToCharConversion(hlvValue));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                showWebViewInsurance(recommendData);
            }
        }
    }


    private void showWebViewInsurance(RecommendData recommendData) {
        String htmlContents = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"100px\", user-scalable=yes\" />" +
                "<body>\n" +
                "\n" +
                "<table border=\"1\" width=\"device-width\"height = \"100px\"table bordercolor=\"#A9A9A9\" " +
                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
                "  <tr align = \"center\">\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Insurance</th>\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Existing Cover</th> \n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Existing Premium</th>\n" +
                "  </tr>\n" +
                addingTableRowsInsurance(recommendData)+
                "</table>\n" +


                "</p>" +

                "\n" +
                "<table border=\"1\" width=\"device-width\"height = \"100px\"table bordercolor=\"#A9A9A9\" " +
                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+
                "  <tr align = \"center\">\n" +
                "    <th width = \"150px\">Title</th>\n" +
                "    <th width = \"150px\">Suggested Cover</th> \n" +
                "    <th width = \"150px\">Suggested Premium</th>\n" +
                "  </tr>\n" +
                "  <tr align = \"center\">\n" +
                "    <td>Minimum Required Cover</td>\n" +
                "    <td>" +rs+" "+ min_Req_Cover + "</td>\n" +
                "    <td>" +rs+" "+ 0 + "</td>\n" +
                "  </tr>\n" +
                "  <tr align = \"center\">\n" +
                "    <td>Minimum Required Cover to Purchase</td>\n" +
                "    <td>" +rs+" "+ min_Req_Cover_Purchase + "</td>\n" +
                "    <td>" +rs+" "+ 0 + "</td>\n" +
                "  </tr>\n" +
                "  <tr align = \"center\">\n" +
                "    <td>Maximum Required Cover</td>\n" +
                "    <td>" +rs+" "+ max_Req_Cover+ "</td>\n" +
                "    <td>" +rs+" "+ 0 + "</td>\n" +
                "  </tr>\n" +
                "  <tr align = \"center\">\n" +
                "    <td>Maximum Required Cover to Purchase</td>\n" +
                "    <td>" +rs+" "+ max_Req_Cover_Purchase+ "</td>\n" +
                "    <td>" +rs+" "+ 0 + "</td>\n" +
                "  </tr>\n" +
                "</table>\n" +

                "\n <br><br> <p align = \"center\"> Your estimated human life value <font color=\"#7a0098;\"><b>"
                +rs+" "+ hlvValue + "</b></font></p><br>" +

                "</body>\n" +
                "</html>\n";
        webview.loadData(htmlContents, "text/html", "UTF-8");
    }
    private String addingTableRowsInsurance(RecommendData recommendData) {
        String rowsadd ="";
        int length = recommendData.getData().getIns_plan().getPlan_res().size();
        if (null!=recommendData.getData().getIns_plan()) {
            for (int i = 0; i < length; i++) {
                String coverage="",annual_prem="",ins_prod_type="";
                if(recommendData.getData().getIns_plan().getPlan_res().get(i).getCoverage()!= null&&
                        Float.parseFloat(recommendData.getData().getIns_plan().getPlan_res().get(i).getCoverage())>0){
                    coverage=(UtileKit.currToCharConversion(recommendData.getData().
                            getIns_plan().getPlan_res().get(i).getCoverage()));
                }else{
                    coverage= "0";
                }
                if(recommendData.getData().getIns_plan().getPlan_res().get(i).getAnnual_prem()!= null&&
                        Float.parseFloat(recommendData.getData().getIns_plan().getPlan_res().get(i).getAnnual_prem())>0){
                    annual_prem=(UtileKit.currToCharConversion(recommendData.getData().
                            getIns_plan().getPlan_res().get(i).getAnnual_prem()));
                }else{
                    annual_prem= "0";
                }

                if(recommendData.getData().getIns_plan().getPlan_res().get(i).getIns_type()!= null){
                    ins_prod_type=recommendData.getData().getIns_plan().getPlan_res().get(i).getIns_type();
                }else{
                    ins_prod_type= "0";
                }
                rowsadd =rowsadd  + "  <tr align = \"center\">\n" +
                        "    <td>" +ins_prod_type+"</td>\n" +
                        "    <td>"  +rs+" "+coverage+"</td>\n" +
                        "    <td>"  +rs+" "+annual_prem+"</td>\n" +
                        "  </tr>\n" ;
            }
        }
        return rowsadd;
    }



    private void callAllInsuranceService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<RecommendData> call = webServiceObj.triggerRecommendationService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<RecommendData>() {
            @Override
            public void onResponse(Call<RecommendData> call, Response<RecommendData> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    recommendData = response.body();

                    if (recommendData.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        if (recommendData.getData().getIns_plan()!= null) {

                            if (recommendData.getData().getIns_plan().getMin_cov_need()!= null&&
                                    (Float.parseFloat(recommendData.getData().getIns_plan().getMin_cov_need())>0)) {

                                min_Req_Cover=(UtileKit.currToCharConversion(recommendData.getData().
                                        getIns_plan().getMin_cov_need()));
                            }

                            else {
                                min_Req_Cover = "0";
                            }
                            if (recommendData.getData().getIns_plan().getMin_cur_ins_cov()!= null&&
                                    (Float.parseFloat(recommendData.getData().getIns_plan().getMin_cur_ins_cov())>0)){

                                min_Req_Cover_Purchase=(UtileKit.currToCharConversion(recommendData.getData().
                                        getIns_plan().getMin_cur_ins_cov()));
                            } else {
                                min_Req_Cover_Purchase = "0";
                            }
                            if (recommendData.getData().getIns_plan().getMax_cov_need()!= null&&
                                    (Float.parseFloat(recommendData.getData().getIns_plan().getMax_cov_need())>0)) {

                                max_Req_Cover=(UtileKit.currToCharConversion(recommendData.getData().getIns_plan().
                                        getMax_cov_need()));
                            } else {
                                max_Req_Cover = "0";
                            }
                            if (recommendData.getData().getIns_plan().getMax_cur_ins_cov()!= null&&
                                    (Float.parseFloat(recommendData.getData().getIns_plan().getMax_cur_ins_cov())>0)) {

                                max_Req_Cover_Purchase=(UtileKit.currToCharConversion(recommendData.getData().getIns_plan().
                                        getMax_cur_ins_cov()));
                            } else {
                                max_Req_Cover_Purchase = "0";
                            }



                            if (recommendData.getData().getIns_plan().getHlv()!= null) {
                                hlvValue=recommendData.getData().getIns_plan().getHlv();
                            } else {
                                hlvValue = "0";
                            }
                            try {
                                hlvValue = (UtileKit.currToCharConversion(hlvValue));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            showWebViewInsurance(recommendData);
                        }
                    }
                    else{

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RecommendData> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
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

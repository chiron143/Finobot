package com.purplepath.purplepath.recommendation.recommendationAllViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.purplepath.purplepath.recommendation.getnetworthmodel.Networthmodel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by pravinr on 3/7/18.
 */

public class RecommendationNetworth extends BaseFragment implements View.OnClickListener  {

    private Context mContext;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private OnActivityBackPressedListener mCallBackListener;

    private Networthmodel networthmodel;

    String networth_value;

    private String rs="&#x20B9";

    private WebView  webViewnetworth;

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
        return inflater.inflate(R.layout.view_popup_networth_plan, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCallBackListener.setActionBarTitle("Networth");

        webViewnetworth = view.findViewById(R.id.webview_networth);

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        callAllInsuranceService();

    }
    private void callAllInsuranceService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Networthmodel> call = webServiceObj.getNetworthRecommendation(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Networthmodel>() {
            @Override
            public void onResponse(Call<Networthmodel> call, Response<Networthmodel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    networthmodel = response.body();

                    if (networthmodel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                        if(networthmodel.getData().getNetworth_val()!=null) {
                            applyNetworthDetails(networthmodel);
                        }
                    }
                    else{
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Networthmodel> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void applyNetworthDetails(Networthmodel networthmodel) {

        String asset_value = (UtileKit.longvalueabsolute(Float.parseFloat(networthmodel.getData().getOver_all_asset_val())));
        String asset_return = (UtileKit.rounddecimalNumber(networthmodel.getData().getAssets().getOver_all_weigh_avg()));

        String liability_value = (UtileKit.longvalueabsolute(Float.parseFloat(networthmodel.getData().getOver_all_liab_val())));
        String liability_return = (UtileKit.rounddecimalNumber(networthmodel.getData().getLiab().getOver_loan_amt_weigh_avg()));

        networth_value = (networthmodel.getData().getNetworth_val());
        String empty = "";

        String floatconver_networth_value = "";
        try {
            floatconver_networth_value = (UtileKit.longvalueabsolute(Float.parseFloat(networth_value)));
        } catch (Exception e) {
            e.printStackTrace();
        }


        String htmlContents = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" />" +
                "<body>\n" +
                "\n" +

                "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+


                "  <tr align = \"center\">\n" +
                "    <th width = \"150px\"> </th>\n" +
                "    <th width = \"150px\">Value</th> \n" +
                "    <th width = \"150px\">Return</th>\n" +
                "  </tr>\n" +
                "  <tr align = \"center\">\n" +
                "    <td>Asset</td>\n" +
                "    <td>" +rs+" "+ asset_value + "</td>\n" +
                "    <td>" + asset_return + "% " + " p.a. " + "</td>\n" +
                "  </tr>\n" +
                "  <tr align = \"center\">\n" +
                "    <td>Liability</td>\n" +
                "    <td>" +rs+" "+ liability_value + "</td>\n" +
                "    <td>" + liability_return + "% " + " p.a. " + "</td>\n" +
                "  </tr>\n" +
                "  <tr align = \"center\">\n" +
                "    <td>Networth</td>\n" +
                "    <td>" +rs+" "+ floatconver_networth_value + "</td>\n" +
                "    <td>" + empty + "</td>\n" +
                "  </tr>\n" +
                "</table>\n" +

                " Your liability payout is at a lower rate <font color=\"#6699FF\"><b>"


                + liability_return + "</b></font>" + "<font color=\"#6699FF\">% </font>" + " p.a. " + "\n" +
                " than your asset growth rate, <font color=\"#6699FF\"><b>"
                + asset_return + "</b></font>" + "<font color=\"#6699FF\">% </font>" + " p.a. " + "</p>" +

                "</body>\n" +
                "</html>\n";
        webViewnetworth.loadData(htmlContents, "text/html", "UTF-8");
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

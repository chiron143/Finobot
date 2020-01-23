package com.purplepath.purplepath.taxprompt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.Ded_by_prods;
import com.purplepath.purplepath.taxprompt.gettaxpromptmodel.TaxPromptNewModel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Murugesan on 1/25/18.
 */

public class TaxRecommendationTable extends BaseFragment implements View.OnClickListener {


    private TaxPromptNewModel taxCashFlowModel;

    private OnActivityBackPressedListener mCallBackListener;

    RelativeLayout relative_left_arrow, relative_center_home;

    private Context mContext;

    WebView webView_recommendation, webView_recommendation_sub_table, webView_recommendation_other_product;

    private String rs = "&#x20B9";

    HashSet<String> taxproduct_hashset = new HashSet<String>();

    private BigInteger availed = BigInteger.ZERO;

    BigInteger entitle = BigInteger.ZERO;

    private BigInteger total_sub_all = BigInteger.ZERO;

    String avg_tax_rate = "", marg_tax_rate = "";

    String str_total_tax_payable = "";

    BigInteger total_tax_payable = BigInteger.ZERO;

    BigInteger total_tax_refund = BigInteger.ZERO;

    BigInteger entitle_greater = BigInteger.ZERO;

    ArrayList<Ded_by_prods> arrayListsecondTable = new ArrayList<>();

    ArrayList<Ded_by_prods> arrayListsecondTableNew = new ArrayList<>();

    ArrayList<ArrayList<Ded_by_prods>> mfilterarray = new ArrayList<>();

    ArrayList<String> arrayListavail = new ArrayList<String>();


    //otherproduct
    private BigInteger entitle_other = BigInteger.ZERO;
    private BigInteger availed_other = BigInteger.ZERO;
    private BigInteger pending_other = BigInteger.ZERO;
    private BigInteger total_sub_all_pending_other = BigInteger.ZERO;


    private TextView empty_text;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBackListener = (OnActivityBackPressedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_tax_recommendation, container, false);
        mContext = getContext();
        ButterKnife.bind(this, view);

        mCallBackListener.setActionBarTitle("Tax Recommendation");
        relative_center_home = view.findViewById(R.id.relative_center_home);
        relative_left_arrow = view.findViewById(R.id.relative_left_arrow);

        relative_center_home.setOnClickListener(this);
        relative_left_arrow.setOnClickListener(this);

        webView_recommendation = view.findViewById(R.id.webView_recommendation);
        webView_recommendation_sub_table = view.findViewById(R.id.webView_recommendation_sub_table);
        webView_recommendation_other_product = view.findViewById(R.id.webView_recommendation_other_product);


        empty_text = view.findViewById(R.id.empty_text);

        callTaxAnalysisService();


        return view;
    }


    private void callTaxAnalysisService() {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String finYr = String.valueOf(year-1);
        Call<TaxPromptNewModel> call = webServiceObj.callTaxPromptService_new(UtileKit.getPersistedPurplePathPref("user_id"), "FY"+finYr);
        call.enqueue(new Callback<TaxPromptNewModel>() {
            @Override
            public void onResponse(Call<TaxPromptNewModel> call, Response<TaxPromptNewModel> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    taxCashFlowModel = response.body();
                    if (taxCashFlowModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        //Table bottom text function
                        tableBottomText(taxCashFlowModel);
                        //Grouping
                        if (taxCashFlowModel.getData().getDed_by_prod() != null) {
                            int length = taxCashFlowModel.getData().getDed_by_prod().size();
                            for (int i = 0; i < length; i++) {
                                taxproduct_hashset.add(taxCashFlowModel.getData().getDed_by_prod().get(i).getTax_section());
                            }
                            arrayListavail = new ArrayList<String>(taxproduct_hashset);
                            //Sorting arraylist
                            Collections.sort(arrayListavail);
                            for (int j = 0; j < arrayListavail.size(); j++) {
                                String str_obj = arrayListavail.get(j);
                                if (str_obj != null) {
                                    ArrayList<Ded_by_prods> taxsection_heading = new ArrayList<>();

                                    for (int k = 0; k < length; k++) {
                                        if (str_obj.equals(taxCashFlowModel.getData().getDed_by_prod().get(k).getTax_section())) {
                                            taxsection_heading.add(taxCashFlowModel.getData().getDed_by_prod().get(k));
                                        }
                                    }
                                    mfilterarray.add(taxsection_heading);
                                }
                            }


                            showWebViewTaxation(mfilterarray);
                        }

                        //OtherProductTable
                        // showWebViewOtherProduct(taxCashFlowModel);
                    } else {
                        empty_text.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



            @Override
            public void onFailure(Call<TaxPromptNewModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }


    private void tableBottomText(TaxPromptNewModel taxCashFlowModel) {
        //tax calculation
        if (taxCashFlowModel.getData().getTax_calc() != null) {
            if (taxCashFlowModel.getData().getTax_calc().getTotal_tax_payable() != null) {
                total_tax_payable = new BigInteger(taxCashFlowModel.getData().getTax_calc().
                        getTotal_tax_payable());

                total_tax_refund = new BigInteger(taxCashFlowModel.getData().getTax_calc().getTotal_tax_refund());
                String tax_flag = taxCashFlowModel.getData().getTax_calc().getTax_flag();

                if (tax_flag.equalsIgnoreCase("Pay")) {
                    str_total_tax_payable = String.valueOf(UtileKit.formatedNumbers(total_tax_payable) + "  "
                            + "is the amount you have to pay in tax");

                } else if (tax_flag.equalsIgnoreCase("Receive")) {
                    str_total_tax_payable = String.valueOf(UtileKit.formatedNumbers(total_tax_refund) + "  "
                            + "is the amount you will receive refund from tax department");
                } else if (tax_flag.equalsIgnoreCase("Moderate")) {
                    str_total_tax_payable = String.valueOf(UtileKit.formatedNumbers(total_tax_payable) + "  "
                            + "you neither have to pay tax nor receive any refund");
                }

            }
            if (taxCashFlowModel.getData().getTax_calc().getAvg_tax_rate() != null) {
                avg_tax_rate = taxCashFlowModel.getData().getTax_calc().getAvg_tax_rate();
            }
            if (taxCashFlowModel.getData().getTax_calc().getMarg_tax_rate() != null) {
                marg_tax_rate = taxCashFlowModel.getData().getTax_calc().getMarg_tax_rate();
            }
        }

    }

    private void showWebViewTaxation(ArrayList<ArrayList<Ded_by_prods>> mfilterarray) {
        String htmlContents = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"150px\", user-scalable=yes\" />" +
                "<body>\n" +


                "<center><h4>My Tax Savings u/s Chapter VI</h4></center>" +


                "\n" +
                "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n" +

                "  <tr align = \"center\">\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Section</th>\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Entitled</th> \n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Availed</th>\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Pending</th>\n" +
                "  </tr>\n" +
                addingTaxationTableRows(mfilterarray) +
                "</table>\n" +
                "\n" +


                "<br/>" +

                "<center><h4>My Tax Savings u/s Other Options</h4></center>" +


                "\n" +
                "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n" +

                "  <tr align = \"center\">\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Product Name</th>\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Entitled</th> \n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Availed</th>\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Pending</th>\n" +
                "  </tr>\n" +
                addingOtherProductTableRows(taxCashFlowModel) +
                "</table>\n" +
                "\n" +


                "<br/>" +


                " Based on the inputs provided, <font color=\"#6699FF\"><b>"
                + rs + " " + str_total_tax_payable + "    " + "</b></font>" + "" + "<br/>" +

                " Average Tax Rate : <font color=\"#6699FF\"><b>"
                + avg_tax_rate + "</b></font>" + "<font color=\"#6699FF\">% </font>" + "<br/>" +

                " Marginal Tax Rate : <font color=\"#6699FF\"><b>"
                + marg_tax_rate + "</b></font>" + "<font color=\"#6699FF\">% </font>" + "<br/>" +

                "<br/>" +

                "  <font color=\"#0b0d0f\"><b>" + "Average tax rate - " +
                "</b></font>" + " is a kind of flat rate to view how much tax was paid overall." + "<br/>" +

                "<font color=\"#0b0d0f\"><b>" + " Marginal tax rate - " +
                "</b></font>" + "is a rate at which to be paid for the next rupee in earnings." + "<br/>" +


                "<br>" +
                "</body>\n" +
                "</html>\n";
        webView_recommendation.loadData(htmlContents, "text/html", "UTF-8");
    }


    private String addingTaxationTableRows(ArrayList<ArrayList<Ded_by_prods>> mfilterarray) {
        String rowsadd = "";

        arrayListsecondTable = new ArrayList<>();

        for (ArrayList<Ded_by_prods> ded_by_prod : mfilterarray) {

            int length = ded_by_prod.size();

            String section = "", str_entitle = "", str_availed = "0", str_pending = "0";
            String str_availed_greater = "0";
            availed = BigInteger.ZERO;
            entitle = BigInteger.ZERO;
            total_sub_all = BigInteger.ZERO;
            entitle_greater = BigInteger.ZERO;

            int checkLess;

            arrayListsecondTableNew = new ArrayList<>();

            for (int i = 0; i < length; i++) {

                try {

                    if (i == 0) {
                        section = ded_by_prod.get(i).getTax_section();
                    }
                    //entitle
                    if (ded_by_prod.get(i).getEntitled() != null) {
                        entitle = new BigInteger(ded_by_prod.get(i).getEntitled());
                        str_entitle = String.valueOf(UtileKit.formatedNumbers(entitle));
                    }

                    if (ded_by_prod.get(i).getAllowed_value() != null || ded_by_prod.get(i).getEntitled() != null) {
                        availed = availed.add(new BigInteger(ded_by_prod.get(i).getAllowed_value()));
                        str_availed = String.valueOf(UtileKit.formatedNumbers(availed));
                        entitle_greater = new BigInteger(ded_by_prod.get(i).getEntitled());
                        str_availed_greater = String.valueOf(UtileKit.formatedNumbers(entitle_greater));
                    }
                    //Disablity
                    String diability_flag = taxCashFlowModel.getData().getDisability_flag();
                    String disability_inner_array = ded_by_prod.get(i).getDisability_flag();
                    //availed
                    if (ded_by_prod.get(i).getAllowed_value() != null) {

                        if (ded_by_prod.get(i).getAllowed_value().equalsIgnoreCase("0")) {
                            //need to work
                            try {
                                if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("Y"))) {
                                    arrayListsecondTableNew.add(ded_by_prod.get(i));
                                } else if ((disability_inner_array.equalsIgnoreCase("Y") && diability_flag.equalsIgnoreCase("Y"))) {
                                    arrayListsecondTableNew.add(ded_by_prod.get(i));
                                } else if ((disability_inner_array.equalsIgnoreCase("A") && diability_flag.equalsIgnoreCase("N"))) {
                                    arrayListsecondTableNew.add(ded_by_prod.get(i));
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            checkLess = availed.compareTo(entitle_greater);

            if (checkLess == 1) {
                //pending
                total_sub_all = entitle.subtract(entitle_greater);
                if (total_sub_all != null) {
                    str_pending = String.valueOf(UtileKit.formatedNumbers(total_sub_all));
                }
                rowsadd = rowsadd + "  <tr align = \"center\">\n" +
                        "    <td>" + section + "</td>\n" +
                        "    <td>" + rs + " " + str_entitle + "</td>\n" +
                        "    <td>" + rs + " " + str_availed_greater + "</td>\n" +
                        "    <td>" + rs + " " + str_pending + "</td>\n" +
                        "  </tr>\n";
            } else if (checkLess == 0) {
                //pending
                total_sub_all = entitle.subtract(entitle_greater);
                if (total_sub_all != null) {
                    str_pending = String.valueOf(UtileKit.formatedNumbers(total_sub_all));
                }

                rowsadd = rowsadd + "  <tr align = \"center\">\n" +
                        "    <td>" + section + "</td>\n" +
                        "    <td>" + rs + " " + str_entitle + "</td>\n" +
                        "    <td>" + rs + " " + str_availed_greater + "</td>\n" +
                        "    <td>" + rs + " " + str_pending + "</td>\n" +
                        "  </tr>\n";
            } else {
                //pending
                total_sub_all = entitle.subtract(availed);
                if (total_sub_all != null) {
                    str_pending = String.valueOf(UtileKit.formatedNumbers(total_sub_all));
                }
                rowsadd = rowsadd + "  <tr align = \"center\">\n" +
                        "    <td>" + section + "</td>\n" +
                        "    <td>" + rs + " " + str_entitle + "</td>\n" +
                        "    <td>" + rs + " " + str_availed + "</td>\n" +
                        "    <td>" + rs + " " + str_pending + "</td>\n" +
                        "  </tr>\n";
            }


            if (section.equals("80C")) {

                if (!str_pending.equals("0")) {
                    arrayListsecondTable.addAll(arrayListsecondTableNew);
                }
            } else {
                arrayListsecondTable.addAll(arrayListsecondTableNew);
            }
            if (arrayListsecondTable.isEmpty()) {
                showWebViewTaxationSubTable(mfilterarray, arrayListsecondTable);
            } else {
                showWebViewTaxationSubTableWithText(mfilterarray, arrayListsecondTable);
            }


        }
        return rowsadd;
    }









   /* private void showWebViewOtherProduct(TaxPromptNewModel taxCashFlowModel) {
        String htmlContents = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"150px\", user-scalable=yes\" />" +
                "<body>\n" +
                "\n" +
                "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+

                "  <tr align = \"center\">\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Product Name</th>\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Entitled</th> \n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Availed</th>\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Pending</th>\n" +
                "  </tr>\n" +
                addingOtherProductTableRows(taxCashFlowModel)+
                "</table>\n" +
                "\n"+


                "<br>"+
                "</body>\n" +
                "</html>\n";
        webView_recommendation_other_product.loadData(htmlContents, "text/html", "UTF-8");

    }*/

    private String addingOtherProductTableRows(TaxPromptNewModel taxCashFlowModel) {
        String rowsadd = "";

        String product_name = "", entitle = "", availed = "", pending = "";

        int length = taxCashFlowModel.getData().getDed_by_other_prod().size();

        availed_other = BigInteger.ZERO;
        entitle_other = BigInteger.ZERO;
        total_sub_all_pending_other = BigInteger.ZERO;

        for (int i = 0; i < length; i++) {

            if (taxCashFlowModel.getData().getDed_by_other_prod().get(i).getProd_name() != null) ;
            {
                product_name = taxCashFlowModel.getData().getDed_by_other_prod().get(i).getProd_name();

            }
            if (taxCashFlowModel.getData().getDed_by_other_prod().get(i).getEntitled() != null) {
                entitle_other = new BigInteger(taxCashFlowModel.getData().getDed_by_other_prod().get(i).getEntitled());
                entitle = String.valueOf(UtileKit.formatedNumbers(entitle_other));
            }
            if (taxCashFlowModel.getData().getDed_by_other_prod().get(i).getAllowed_value() != null) {
                availed_other = new BigInteger(taxCashFlowModel.getData().getDed_by_other_prod().get(i).getAllowed_value());
                availed = String.valueOf(UtileKit.formatedNumbers(availed_other));
            }

            total_sub_all_pending_other = entitle_other.subtract(availed_other);
            pending = String.valueOf(UtileKit.formatedNumbers(total_sub_all_pending_other));

            rowsadd = rowsadd + "  <tr align = \"center\">\n" +
                    "    <td>" + product_name + "</td>\n" +
                    "    <td>" + rs + " " + entitle + "</td>\n" +
                    "    <td>" + rs + " " + availed + "</td>\n" +
                    "    <td>" + rs + " " + pending + "</td>\n" +
                    "  </tr>\n";

        }


        return rowsadd;
    }


    //Arraylist not empty if u have value means add extra text table below
    private void showWebViewTaxationSubTableWithText(ArrayList<ArrayList<Ded_by_prods>> mfilterarray,
                                                     ArrayList<Ded_by_prods> ded_by_prods) {
        String htmlContents = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"150px\", user-scalable=yes\" />" +
                "<body>\n" +
                "\n" +

                " By looking at your current tax savings and investments, you still have opportunity to invest as below <br>" +


                "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n" +

                "  <tr align = \"center\">\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Section</th>\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Product Name</th> \n" +
                "  </tr>\n" +
                addingTaxationTableRowsSubTableRowsWithText(mfilterarray, ded_by_prods) +
                "</table>\n" +
                "\n" +
                "<br>" +


                "</body>\n" +
                "</html>\n";
        webView_recommendation_sub_table.loadData(htmlContents, "text/html", "UTF-8");
    }

    private String addingTaxationTableRowsSubTableRowsWithText(ArrayList<ArrayList<Ded_by_prods>> mfilterarray,
                                                               ArrayList<Ded_by_prods> ded_by_prods) {
        String rowsadd = "";

        int length = ded_by_prods.size();

        String section = "", product_name = "";

        for (int i = 0; i < length; i++) {

            int innerlength = mfilterarray.size();

            for (int j = 0; j < innerlength; j++) {

                int inner_innerlength = mfilterarray.get(j).size();

                for (int k = 0; k < inner_innerlength; k++) {

                    if (ded_by_prods.get(i).getTax_section().equalsIgnoreCase(mfilterarray.get(j).get(k).getTax_section())) {

                        //section
                        section = ded_by_prods.get(i).getTax_section();
                        Log.d("section", "sectionsss" + section);

                        //Product Name
                        if (ded_by_prods.get(i).getProd_name() != null) {
                            product_name = ded_by_prods.get(i).getProd_name();
                            Log.d("product_name", "product_namesss" + product_name);
                        }


                    }
                }
            }
            rowsadd = rowsadd + "  <tr align = \"center\">\n" +
                    "    <td>" + section + "</td>\n" +
                    "    <td>" + product_name + "</td>\n" +
                    "  </tr>\n";
        }
        return rowsadd;
    }


    //Arraylist is empty---without text
    private void showWebViewTaxationSubTable(ArrayList<ArrayList<Ded_by_prods>> mfilterarray,
                                             ArrayList<Ded_by_prods> ded_by_prods) {
        String htmlContents = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head> <meta name=\"viewport\" content=\"width=device-width\"height=\"150px\", user-scalable=yes\" />" +
                "<body>\n" +
                "\n" +

                /*" By looking at your current tax savings investments, you still have opportunity to invest as below <br>" +


                "<table border=\"1\" width=\"device-width\"height = \"150px\"table bordercolor=\"#A9A9A9\" " +
                "cellpadding=\"0\" cellspacing=\"0\"style=\"border-collapse:collapse;\">\n"+

                "  <tr align = \"center\">\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Section</th>\n" +
                "    <th style=\"padding-left:10px;padding-right:10px;\">Product Name</th> \n" +
                "  </tr>\n" +
                addingTaxationTableRowsSubTableRows(mfilterarray,ded_by_prods)+
                "</table>\n" +
                "\n"+
                "<br>"+*/
                "<b> You are already tax-efficient. You have no scope for further investments. <br>" + "<b>" +

                "</body>\n" +
                "</html>\n";
        webView_recommendation_sub_table.loadData(htmlContents, "text/html", "UTF-8");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_health_summary, menu);
        MenuItem item = menu.findItem(R.id.menu_health_summary);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {


            case R.id.menu_health_summary:
                try {
                    addFragmenttoStack(new TaxPromptSummary());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }
    }

}

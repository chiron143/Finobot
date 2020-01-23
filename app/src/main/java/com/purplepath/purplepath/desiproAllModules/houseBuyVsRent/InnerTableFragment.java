//package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent;
//
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebView;
//import android.widget.Toast;
//
//import com.finobot.finobot.R;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models.BuyVsRentModel;
//
///**
// * @author Pratheep.S
// */
//public class InnerTableFragment extends Fragment {
//
//    private Bundle args;
//    private BuyVsRentModel buyVsRentModel;
//    private int code;
//    WebView webView;
//
//    public InnerTableFragment() {
//        // Required empty public constructor
//    }
//
//    public static InnerTableFragment newInstance(BuyVsRentModel buyVsRentModel, int code) {
//
//        Bundle args = new Bundle();
//        args.putSerializable("buyVsRentModel", buyVsRentModel);
//        args.putInt("code", code);
//        InnerTableFragment fragment = new InnerTableFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_inner_table, container, false);
//        webView = view.findViewById(R.id.webViewDeciPro);
//
//        args = getArguments();
//        if (null != args) {
//            if (args.containsKey("buyVsRentModel")) {
//                buyVsRentModel = (BuyVsRentModel) args.getSerializable("buyVsRentModel");
//                //setValuesFromModel();
//            }
//
//            if (args.containsKey("code")) {
//                code = args.getInt("code");
//            }
//        }
//
//        if (buyVsRentModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
//            if (null != buyVsRentModel.getData()) {
//                switch (code) {
//                    case 1:
//                        if (buyVsRentModel.getData().getHome_rent() != null) {
//                            showWebViewRent(buyVsRentModel);
//                        }
//                        break;
//                    case 2:
//                        if (buyVsRentModel.getData().getHome_cash() != null) {
//                            showWebViewHomeCash(buyVsRentModel);
//                        }
//                        break;
//                    case 3:
//                        if (buyVsRentModel.getData().getHome_loan() != null) {
//                            showWebViewHomeLoan(buyVsRentModel);
//                        }
//                        break;
//                }
//
//            } else {
//                Toast.makeText(getContext(), "No Details to show", Toast.LENGTH_LONG).show();
//            }
//        }
//
//
//        return view;
//    }
//
//    private void showWebViewHomeLoan(BuyVsRentModel buyVsRentModel) {
//        String htmlContent = "<!DOCTYPE html>\n" +
//
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" >" +
//                "</head>\n" +
//                "<body>\n" +
//                "\n" +
//                "<table border=\"1\" width=\"device-width\" height = \"150px\" cellpadding=\"10px\" cellspacing=\"0\" style=\"border-collapse:collapse;\" >\n" +
//                "  <tr id=\"header-row\" align = \"center\">\n" +
//                "    <th width = \"150px\">Tenure</th>\n" +
//                "    <th width = \"150px\">Property Price</th> \n" +
//                "    <th width = \"150px\">Property Value</th>\n" +
//                "    <th width = \"150px\">Mortgage Payment</th>\n" +
//                "    <th width = \"150px\">Mortgage Interest</th> \n" +
//                "    <th width = \"150px\">Tax -Section 24</th>\n" +
//                "    <th width = \"150px\">Mortgage Principal</th>\n" +
//                "    <th width = \"150px\">Tax -Section 80c</th> \n" +
//                "    <th width = \"150px\">Total interest on Mortgage</th>\n" +
//                "    <th width = \"150px\">Tax Savings</th>\n" +
//                "    <th width = \"150px\">Net Annual Cost of Buying</th> \n" +
//                "    <th width = \"150px\">Rent Loan Difference</th>\n" +
//                "  </tr>\n" +
//                addTableRowsHomeLoan(buyVsRentModel)
//                +
//                "</table>\n" +
//                "</body>\n" +
//                "</html>\n";
//
//        //webView.setBackgroundColor(Color.parseColor("#e5e5e5"));
//        webView.setBackgroundColor(Color.parseColor("#fafafa"));
//        webView.loadData(htmlContent, "text/html", "UTF-8");
//        addTableRowsHomeLoan(buyVsRentModel);
//    }
//
//    private String addTableRowsHomeLoan(BuyVsRentModel buyVsRentModel) {
//        String row = "";
//        if (buyVsRentModel.getData().getHome_loan().getCashflow() != null) {
//            int rowSize = buyVsRentModel.getData().getHome_loan().getCashflow().size();
//            for (int i = 1; i < rowSize; i++) {
//                row = row + "  <tr align = \"center\">\n" +
//                        "    <td>" + buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTenure() + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getPropertyPrice())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getPropertyValue())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getMortagePayment())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getMortageInterest())) + "</td>\n" +
//                        "    <td>" + checkForOptionalValueFormat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTax24()) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getMortagePrincipal())) + "</td>\n" +
//                        "    <td>" + checkForOptionalValueFormat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTax80c()) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTotIntOnMortgage())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getTaxSavings())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getNetAnnuCostOfBuying())) + "</td>\n" +
//                        "    <td>" + formatNegativeNumber(buyVsRentModel.getData().getHome_loan().getCashflow().get(i).getRentLoanDifference()) + "</td>\n" +
//                        "  </tr>\n";
//
//            }
//
//        }
//
//
//        return row;
//    }
//
//    private void showWebViewHomeCash(BuyVsRentModel buyVsRentModel) {
//        String htmlContent = "<!DOCTYPE html>\n" +
//
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" >" +
//                "</head>\n" +
//                "<body>\n" +
//                "\n" +
//                "<table border=\"1\" width=\"device-width\" height = \"150px\" cellpadding=\"10px\" cellspacing=\"0\" style=\"border-collapse:collapse;\" >\n" +
//                "  <tr id=\"header-row\" align = \"center\">\n" +
//                "    <th width = \"150px\">Tenure</th>\n" +
//                "    <th width = \"150px\">Foregone Interest</th> \n" +
//                "    <th width = \"150px\">Gross cost of Buying</th>\n" +
//                "    <th width = \"150px\">Tax Savings Deductions</th>\n" +
//                "    <th width = \"150px\">Tax Savings</th> \n" +
//                "    <th width = \"150px\">Net Cost of Buying</th>\n" +
//                "    <th width = \"150px\">Rent Cash Difference</th>\n" +
//                "    <th width = \"150px\">Loan Cash Difference</th> \n" +
//                "  </tr>\n" +
//                addTableRowsHomeCash(buyVsRentModel)
//                +
//                "</table>\n" +
//                "</body>\n" +
//                "</html>\n";
//
//        //webView.setBackgroundColor(Color.parseColor("#e5e5e5"));
//        webView.setBackgroundColor(Color.parseColor("#fafafa"));
//        webView.loadData(htmlContent, "text/html", "UTF-8");
//        addTableRowsHomeCash(buyVsRentModel);
//    }
//
//
//    private String addTableRowsHomeCash(BuyVsRentModel buyVsRentModel) {
//        String row = "";
//        if (buyVsRentModel.getData().getHome_cash().getCashflow() != null) {
//            int rowSize = buyVsRentModel.getData().getHome_cash().getCashflow().size();
//            for (int i = 1; i < rowSize; i++) {
//                row = row + "  <tr align = \"center\">\n" +
//                        "    <td>" + buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getTenure() + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getForegone_inerest())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getGross_cost_of_buying())) + "</td>\n" +
//                        "    <td>" + formatNegativeNumber(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getTax_savings_deductions()) + "</td>\n" +
//                        "    <td>" + formatNegativeNumber(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getTax_savings()) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getNet_cost_of_buying())) + "</td>\n" +
//                        "    <td>" + formatNegativeNumber(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getRent_cash_difference()) + "</td>\n" +
//                        "    <td>" + formatNegativeNumber(buyVsRentModel.getData().getHome_cash().getCashflow().get(i).getLoan_cash_difference()) + "</td>\n" +
//                        "  </tr>\n";
//
//            }
//
//        }
//
//
//        return row;
//    }
//
//    private void showWebViewRent(BuyVsRentModel buyVsRentModel) {
//
//        String htmlContent = "<!DOCTYPE html>\n" +
//
//                "<html>\n" +
//                "<head> <meta name=\"viewport\" content=\"width=device-width, user-scalable=yes\" >" +
//                "</head>\n" +
//                "<body>\n" +
//                "\n" +
//                "<table border=\"1\" width=\"device-width\" height = \"150px\" cellpadding=\"10px\" cellspacing=\"0\" style=\"border-collapse:collapse;\" >\n" +
//                "  <tr id=\"header-row\" align = \"center\">\n" +
//                "    <th width = \"150px\">Tenure</th>\n" +
//                "    <th width = \"150px\">Foregone Interest</th> \n" +
//                "    <th width = \"150px\">Total Annual cost of Rent</th>\n" +
//                "  </tr>\n" +
//                addTableRowsRent(buyVsRentModel)
//                +
//                "</table>\n" +
//                "</body>\n" +
//                "</html>\n";
//
//        //webView.setBackgroundColor(Color.parseColor("#e5e5e5"));
//        webView.setBackgroundColor(Color.parseColor("#fafafa"));
//        webView.loadData(htmlContent, "text/html", "UTF-8");
//        addTableRowsRent(buyVsRentModel);
//
//    }
//
//    private String addTableRowsRent(BuyVsRentModel buyVsRentModel) {
//        String row = "";
//        if (buyVsRentModel.getData().getHome_rent().getCashflow() != null) {
//            int rowSize = buyVsRentModel.getData().getHome_rent().getCashflow().size();
//            for (int i = 0; i < rowSize; i++) {
//                row = row + "  <tr align = \"center\">\n" +
//                        "    <td>" + buyVsRentModel.getData().getHome_rent().getCashflow().get(i).getTenure().toString() + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_rent().getCashflow().get(i).getForegone_interest())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(buyVsRentModel.getData().getHome_rent().getCashflow().get(i).getTot_annu_cost_rent())) + "</td>\n" +
//                        "  </tr>\n";
//            }
//
//        }
//
//
//        return row;
//    }
//
//    private String checkForOptionalValueFormat(String value) {
//        String result = null;
//        if (value.matches("[0-9]+"))
//            result = UtileKit.formatedNumber(Float.parseFloat(value));
//        else
//            result = value;
//
//        return result;
//    }
//
//
//    private String formatNegativeNumber(String value){
//        String result=null;
//        try {
//            if(value.contains("-")){
//                String[] arr=value.split("-");
//                result="-"+UtileKit.formatedNumber(Float.parseFloat(arr[1]));
//                Log.i("spcheck", "formatNegativeNumber: value="+value+ "result="+result);
//            }else {
//                result=UtileKit.formatedNumber(Float.parseFloat(value));
//            }
//
//        }catch (NumberFormatException e){e.printStackTrace();}
//
//        return result;
//    }
//
//}

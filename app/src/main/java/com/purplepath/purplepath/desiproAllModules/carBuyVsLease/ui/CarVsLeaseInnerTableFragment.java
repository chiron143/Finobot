//package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.ui;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import androidx.fragment.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebView;
//import android.widget.Toast;
//
//import com.finobot.finobot.R;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models.CarVsLeaseModel;
//
///**
// * Created by Suresh on 23/01/18.
// */
//
//public class CarVsLeaseInnerTableFragment extends Fragment {
//
//    private Bundle args;
//    private CarVsLeaseModel carVsLeaseModel;
//    private int code;
//    WebView webView;
//
//    public CarVsLeaseInnerTableFragment() {
//        // Required empty public constructor
//    }
//
//    public static CarVsLeaseInnerTableFragment newInstance(CarVsLeaseModel carVsLeaseModel, int code) {
//
//        Bundle args = new Bundle();
//        args.putSerializable("carVsLeaseModel", carVsLeaseModel);
//        args.putInt("code", code);
//        CarVsLeaseInnerTableFragment fragment = new CarVsLeaseInnerTableFragment();
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
//            if (args.containsKey("carVsLeaseModel")) {
//                carVsLeaseModel = (CarVsLeaseModel) args.getSerializable("carVsLeaseModel");
//                //setValuesFromModel();
//            }
//
//            if (args.containsKey("code")) {
//                code = args.getInt("code");
//            }
//        }
//
//        if (carVsLeaseModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
//            if (null != carVsLeaseModel.getData()) {
//                switch (code) {
//                    case 1:
//                        if (carVsLeaseModel.getData().getCar_lease() != null) {
//                            showWebViewRent(carVsLeaseModel);
//                        }
//                        break;
//                    case 2:
//                        if (carVsLeaseModel.getData().getCar_cash() != null) {
//                            showWebViewHomeCash(carVsLeaseModel);
//                        }
//                        break;
//                    case 3:
//                        if (carVsLeaseModel.getData().getCar_loan() != null) {
//                            showWebViewHomeLoan(carVsLeaseModel);
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
//// "tenure": 1,
////         "down_pay": 2000,
////         "loan_payment": 8454.6,
////         "loan_interest": 1613.93,
////         "loan_principal": 6840.67,
////         "loan_out_bal": 23159.33,
////         "loan_main_repair": 0,
////         "loan_fuel_run_exp": 0,
////         "loan_insurance": 0,
////         "loan_foregone_int": 42,
////         "tot_cost_buying_loan": 10496.6,
////         "lease_loan_difference": -1866.6
//    private void showWebViewHomeLoan(CarVsLeaseModel carVsLeaseModel) {
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
//        "    <th width = \"150px\">Down Payment</th> \n" +
//        "    <th width = \"150px\">Loan Payment</th>\n" +
//        "    <th width = \"150px\">Loan Interest</th>\n" +
//        "    <th width = \"150px\">Loan Principal</th> \n" +
//        "    <th width = \"150px\">Loan Out Balance</th>\n" +
//        "    <th width = \"150px\">Loan Main Repair</th>\n" +
//        "    <th width = \"150px\">Loan Fuel Run Expenses</th> \n" +
//        "    <th width = \"150px\">Loan Insurance</th> \n" +
//        "    <th width = \"150px\">Loan Foregone Intrest</th> \n" +
//        "    <th width = \"150px\">Total Cost Buying Loan</th> \n" +
//        "    <th width = \"150px\">Lease Loan Difference</th> \n" +
//                "  </tr>\n" +
//                addTableRowsHomeLoan(carVsLeaseModel)
//                +
//                "</table>\n" +
//                "</body>\n" +
//                "</html>\n";
//
//        //webView.setBackgroundColor(Color.parseColor("#e5e5e5"));
//        webView.setBackgroundColor(Color.parseColor("#fafafa"));
//        webView.loadData(htmlContent, "text/html", "UTF-8");
//        addTableRowsHomeLoan(carVsLeaseModel);
//    }
//
//    private String addTableRowsHomeLoan(CarVsLeaseModel carVsLeaseModel) {
//        String row = "";
//        if (carVsLeaseModel.getData().getCar_loan().getCashflow() != null) {
//            int rowSize = carVsLeaseModel.getData().getCar_loan().getCashflow().size();
//            for (int i = 1; i < rowSize; i++) {
//                row = row + "  <tr align = \"center\">\n" +
//                        "    <td>" + carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getTenure() + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getDown_pay())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_payment())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_interest())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_principal())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_out_bal())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_main_repair())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_fuel_run_exp())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_interest())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLoan_foregone_int())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getTot_cost_buying_loan())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_loan().getCashflow().get(i).getLease_loan_difference())) + "</td>\n" +
//                        "  </tr>\n";
//
//            }
//
//        }
//
//
//        return row;
//    }
//    //                "tenure": 1,
////                "cash_payment": 32000,
////                "cash_main_repair": 0,
////                "cash_fuel_run_exp": 0,
////                "cash_insurance": 0,
////                "cash_foregone_int": 672,
////                "tot_cost_buying_cash": 32672,
////                "lease_cash_difference": -24042,
////                "loan_cash_difference": -22175.4
//    private void showWebViewHomeCash(CarVsLeaseModel carVsLeaseModel) {
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
//                "    <th width = \"150px\">Cash Payment</th> \n" +
//                "    <th width = \"150px\">Cash Main Repair</th>\n" +
//                "    <th width = \"150px\">Cash Fuel Run Expenses</th>\n" +
//                "    <th width = \"150px\">Cash Insurance</th> \n" +
//                "    <th width = \"150px\">Cash Foregone Intrest</th>\n" +
//                "    <th width = \"150px\">Total Cost Buying Cash</th> \n" +
//                "    <th width = \"150px\">Lease Cash Difference</th> \n" +
//                "    <th width = \"150px\">Loan Cash Difference</th> \n" +
//                "  </tr>\n" +
//                addTableRowsHomeCash(carVsLeaseModel)
//                +
//                "</table>\n" +
//                "</body>\n" +
//                "</html>\n";
//
//        //webView.setBackgroundColor(Color.parseColor("#e5e5e5"));
//        webView.setBackgroundColor(Color.parseColor("#fafafa"));
//        webView.loadData(htmlContent, "text/html", "UTF-8");
//        addTableRowsHomeCash(carVsLeaseModel);
//    }
//
//
//    private String addTableRowsHomeCash(CarVsLeaseModel carVsLeaseModel) {
//        String row = "";
//        if (carVsLeaseModel.getData().getCar_cash().getCashflow() != null) {
//            int rowSize = carVsLeaseModel.getData().getCar_cash().getCashflow().size();
//            for (int i = 1; i < rowSize; i++) {
//                row = row + "  <tr align = \"center\">\n" +
//                        "    <td>" + carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getTenure() + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getCash_payment())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getCash_main_repair())) + "</td>\n" +
//                        "    <td>" + formatNegativeNumber(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getCash_fuel_run_exp()) + "</td>\n" +
//                        "    <td>" + formatNegativeNumber(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getCash_insurance()) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getCash_foregone_int())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getTot_cost_buying_cash())) + "</td>\n" +
//                        "    <td>" + formatNegativeNumber(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getLease_cash_difference()) + "</td>\n" +
//                        "    <td>" + formatNegativeNumber(carVsLeaseModel.getData().getCar_cash().getCashflow().get(i).getLoan_cash_difference()) + "</td>\n" +
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
//    private void showWebViewRent(CarVsLeaseModel carVsLeaseModel) {
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
//                "    <th width = \"150px\">Yearly Lease Amount</th> \n" +
//                "    <th width = \"150px\">Upfront Expense</th>\n" +
//                "    <th width = \"150px\">Lease Fuel Run Expenses</th>\n" +
//                "    <th width = \"150px\">Lease Insurance</th> \n" +
//                "    <th width = \"150px\">Lease Tax</th>\n" +
//                "    <th width = \"150px\">Lease ForeGone Intrest</th>\n" +
//                "    <th width = \"150px\">Lease termination Expenses</th> \n" +
//                "    <th width = \"150px\">Total Lease Expenses</th>\n" +
//                "  </tr>\n" +
//                addTableRowsRent(carVsLeaseModel)
//                +
//                "</table>\n" +
//                "</body>\n" +
//                "</html>\n";
////
//        //webView.setBackgroundColor(Color.parseColor("#e5e5e5"));
//        webView.setBackgroundColor(Color.parseColor("#fafafa"));
//        webView.loadData(htmlContent, "text/html", "UTF-8");
//        addTableRowsRent(carVsLeaseModel);
//
//    }
//
//    private String addTableRowsRent(CarVsLeaseModel carVsLeaseModel) {
//        String row = "";
//        if (carVsLeaseModel.getData().getCar_lease().getCashflow() != null) {
//            int rowSize = carVsLeaseModel.getData().getCar_lease().getCashflow().size();
//            for (int i = 0; i < rowSize; i++) {
//                row = row + "  <tr align = \"center\">\n" +
//                        "    <td>" + carVsLeaseModel.getData().getCar_lease().getCashflow().get(i).getTenure().toString() + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().getCashflow().get(i).getYearly_lease_amt())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().getCashflow().get(i).getUpfront_expense())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().getCashflow().get(i).getLease_fuel_run_exp())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().getCashflow().get(i).getLease_insurance())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().getCashflow().get(i).getLease_tax())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().getCashflow().get(i).getLease_foregone_int())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().getCashflow().get(i).getLease_ter_exp())) + "</td>\n" +
//                        "    <td>" + UtileKit.formatedNumber(Float.parseFloat(carVsLeaseModel.getData().getCar_lease().getCashflow().get(i).getTot_lease_exp())) + "</td>\n" +
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

//package com.purplepath.purplepath.AppManagement.Payment;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.finobot.finobot.R;
//import com.finobot.finobot.activity.HomePageActivity;
//import com.payu.india.Model.PaymentParams;
//import com.payu.india.Model.PayuConfig;
//import com.payu.india.Model.PayuHashes;
//import com.payu.india.Payu.Payu;
//import com.payu.india.Payu.PayuConstants;
//import com.payu.payuui.Activity.PayUBaseActivity;
//import com.purplepath.purplepath.AppManagement.Payment.model.PaymentModel;
//import com.purplepath.purplepath.apputiles.UtileKit;
//import com.purplepath.purplepath.fragments.BaseFragment;
//import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
//import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
//import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.util.Iterator;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static com.purplepath.purplepath.apputiles.UtileKit.SUCCESSCODE;
//import static com.purplepath.purplepath.apputiles.UtileKit.intitializeAlertDialog;
//
///**
// * Created by pravinr on 11/23/17.
// */
//
//public class PaymentPayUmoney extends BaseFragment implements View.OnClickListener {
//
//    OnActivityBackPressedListener backPressedListener;
//    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
//
//    private String merchantKey, userCredentials;
//
//    private PaymentParams mPaymentParams;
//
//    private PayuConfig payuConfig;
//
//    private Spinner environmentSpinner;
//
//    private Context mContext;
//
//    private TextView buy_now;
//
//    private EditText merchantKey_edt,amount_edt;
//
//    private String str_amount="",str_paid_type="",str_validity="";
//
//    private ProgressBar mProgressbar;
//
//    public static PaymentPayUmoney newInstance(String str_basic_type,String str_amount,String validity) {
//        PaymentPayUmoney paymentPayUmoney = new PaymentPayUmoney();
//        Bundle args = new Bundle();
//        if (str_amount != null) {
//            args.putSerializable("str_buy_now", str_amount);
//        }
//
//
//        if(str_basic_type!=null){
//            args.putSerializable("paid_type",str_basic_type);
//        }
//
//        if (validity != null) {
//            args.putSerializable("validity_year", validity);
//        }
//
//        paymentPayUmoney.setArguments(args);
//        return paymentPayUmoney;
//    }
//    @Override
//    public void onAttach(Context context) {
//        backPressedListener= (OnActivityBackPressedListener) context;
//        super.onAttach(context);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        mContext=getContext();
//
//        if(getArguments().containsKey("str_buy_now")) {
//            str_amount = getArguments().getString("str_buy_now");
//        }
//
//
//
//        if(getArguments().containsKey("paid_type")){
//            str_paid_type=getArguments().getString("paid_type");
//        }
//
//
//        if(getArguments().containsKey("validity_year")){
//            str_validity=getArguments().getString("validity_year");
//        }
//
//
//
//        View view=inflater.inflate(R.layout.fragment_payment_payumoney, container, false);
//        backPressedListener.setActionBarTitle("Payment ");
//        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
//        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
//        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
//        mRightRelativeLayout.setVisibility(View.GONE);
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//
//        mProgressbar= view.findViewById(R.id.ProgressBar);
//
//        buy_now= view.findViewById(R.id.buy_now);
//        buy_now.setOnClickListener(this);
//
//        merchantKey_edt = view.findViewById(R.id.editTextMerchantKey);
//        amount_edt = view. findViewById(R.id.editTextAmount);
//
//        amount_edt.setEnabled(false);
//        amount_edt.setText(str_amount);
//
//        Payu.setInstance(getContext());
//        //Lets setup the environment spinner
//        environmentSpinner = view.findViewById(R.id.spinner_environment);
//        String[] environmentArray = getResources().getStringArray(R.array.environment_array);
///*        list.add("Test");
//        list.add("Production");*/
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, environmentArray);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        environmentSpinner.setAdapter(dataAdapter);
//        environmentSpinner.setSelection(0);
//
//        environmentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        buy_now.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // merchantKey="";
//                /*merchantKey = ((EditText)view.findViewById(R.id.editTextMerchantKey)).getText().toString();
//                String amount = ((EditText)view. findViewById(R.id.editTextAmount)).getText().toString();
//                String email = ((EditText)view. findViewById(R.id.editTextEmail)).getText().toString();*/
//
//                merchantKey=merchantKey_edt.getText().toString();
//
//                String value = environmentSpinner.getSelectedItem().toString();
//                int environment;
//                String TEST_ENVIRONMENT = getResources().getString(R.string.test);
//                if (value.equals(TEST_ENVIRONMENT))
//                    environment = PayuConstants.STAGING_ENV;
//                else
//                    environment = PayuConstants.PRODUCTION_ENV;
//
//                //userCredentials = merchantKey + ":" + str_email;
//                userCredentials = merchantKey + ":" ;
//
//                mPaymentParams = new PaymentParams();
//
//                mPaymentParams.setKey(merchantKey);
//                mPaymentParams.setAmount(str_amount);
//                mPaymentParams.setProductInfo("product_info");
//                mPaymentParams.setFirstName("firstname");
//                mPaymentParams.setEmail("test@gmail.com");
//                mPaymentParams.setPhone("");
//
//                mPaymentParams.setTxnId("" + System.currentTimeMillis());
//
//                mPaymentParams.setSurl("https://payu.herokuapp.com/success");
//                mPaymentParams.setFurl("https://payu.herokuapp.com/failure");
//                mPaymentParams.setNotifyURL(mPaymentParams.getSurl());  //for lazy pay
//
//                mPaymentParams.setUdf1("udf1");
//                mPaymentParams.setUdf2("udf2");
//                mPaymentParams.setUdf3("udf3");
//                mPaymentParams.setUdf4("udf4");
//                mPaymentParams.setUdf5("udf5");
//
//                mPaymentParams.setUserCredentials(userCredentials);
//                payuConfig = new PayuConfig();
//                payuConfig.setEnvironment(environment);
//                generateHashFromServer(mPaymentParams);
//            }
//        });
//
//
//
//
//
//        return view;
//    }
//
//
//
//
//
//
//    public void callPaymentService(String user_paid_type,String last_paid_amount,String validity_months,String trans_id) {
//
//        try {
//            WebServiceCalls webServiceObj;
//            webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//            Call<PaymentModel> call = webServiceObj.callPaymentService(UtileKit.getPersistedPurplePathPref("user_id"),
//                    user_paid_type,last_paid_amount,validity_months,trans_id);
//            call.enqueue(new Callback<PaymentModel>() {
//                @Override
//                public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
//                    try {
//                        PaymentModel paymentModel = response.body();
//                        if (paymentModel.getStatus_code().equalsIgnoreCase(SUCCESSCODE)) {
//
//                            intitializeAlertDialog(getString(R.string.successpayment),mContext);
//                        }
//                        else {
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                @Override
//                public void onFailure(Call<PaymentModel> call, Throwable t) {
//                    UtileKit.alertRetrofitExceptionDialog( mContext, t);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void generateHashFromServer(PaymentParams mPaymentParams) {
//        StringBuffer postParamsBuffer = new StringBuffer();
//        postParamsBuffer.append(concatParams(PayuConstants.KEY, mPaymentParams.getKey()));
//        postParamsBuffer.append(concatParams(PayuConstants.AMOUNT, mPaymentParams.getAmount()));
//        postParamsBuffer.append(concatParams(PayuConstants.TXNID, mPaymentParams.getTxnId()));
//        postParamsBuffer.append(concatParams(PayuConstants.EMAIL, null == mPaymentParams.getEmail() ? "" : mPaymentParams.getEmail()));
//        postParamsBuffer.append(concatParams(PayuConstants.PRODUCT_INFO, mPaymentParams.getProductInfo()));
//        postParamsBuffer.append(concatParams(PayuConstants.FIRST_NAME, null == mPaymentParams.getFirstName() ? "" : mPaymentParams.getFirstName()));
//        postParamsBuffer.append(concatParams(PayuConstants.UDF1, mPaymentParams.getUdf1() == null ? "" : mPaymentParams.getUdf1()));
//        postParamsBuffer.append(concatParams(PayuConstants.UDF2, mPaymentParams.getUdf2() == null ? "" : mPaymentParams.getUdf2()));
//        postParamsBuffer.append(concatParams(PayuConstants.UDF3, mPaymentParams.getUdf3() == null ? "" : mPaymentParams.getUdf3()));
//        postParamsBuffer.append(concatParams(PayuConstants.UDF4, mPaymentParams.getUdf4() == null ? "" : mPaymentParams.getUdf4()));
//        postParamsBuffer.append(concatParams(PayuConstants.UDF5, mPaymentParams.getUdf5() == null ? "" : mPaymentParams.getUdf5()));
//        postParamsBuffer.append(concatParams(PayuConstants.USER_CREDENTIALS, mPaymentParams.getUserCredentials() == null ? PayuConstants.DEFAULT : mPaymentParams.getUserCredentials()));
//
//        if (null != mPaymentParams.getOfferKey())
//            postParamsBuffer.append(concatParams(PayuConstants.OFFER_KEY, mPaymentParams.getOfferKey()));
//
//        String postParams = postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&' ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();
//
//        GetHashesFromServerTask getHashesFromServerTask = new GetHashesFromServerTask();
//        getHashesFromServerTask.execute(postParams);
//    }
//
//    protected String concatParams(String key, String value) {
//        return key + "=" + value + "&";
//    }
//
//    private class GetHashesFromServerTask extends AsyncTask<String, String, PayuHashes> {
//       // private ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//           /* progressDialog = new ProgressDialog(getActivity());
//            progressDialog.setMessage("Please wait...");
//            progressDialog.show();*/
//
//            mProgressbar.setVisibility(View.VISIBLE);
//
//        }
//
//        @Override
//        protected PayuHashes doInBackground(String... postParams) {
//            PayuHashes payuHashes = new PayuHashes();
//            try {
//                URL url = new URL("https://payu.herokuapp.com/get_hash");
//
//                String postParam = postParams[0];
//
//                byte[] postParamsByte = postParam.getBytes("UTF-8");
//
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
//                conn.setDoOutput(true);
//                conn.getOutputStream().write(postParamsByte);
//
//                InputStream responseInputStream = conn.getInputStream();
//                StringBuffer responseStringBuffer = new StringBuffer();
//                byte[] byteContainer = new byte[1024];
//                for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
//                    responseStringBuffer.append(new String(byteContainer, 0, i));
//                }
//
//                JSONObject response = new JSONObject(responseStringBuffer.toString());
//
//                Iterator<String> payuHashIterator = response.keys();
//                while (payuHashIterator.hasNext()) {
//                    String key = payuHashIterator.next();
//                    switch (key) {
//
//                        case "payment_hash":
//                            payuHashes.setPaymentHash(response.getString(key));
//                            break;
//
//                        case "vas_for_mobile_sdk_hash":
//                            payuHashes.setVasForMobileSdkHash(response.getString(key));
//                            break;
//
//                        case "payment_related_details_for_mobile_sdk_hash":
//                            payuHashes.setPaymentRelatedDetailsForMobileSdkHash(response.getString(key));
//                            break;
//
//                        case "delete_user_card_hash":
//                            payuHashes.setDeleteCardHash(response.getString(key));
//                            break;
//
//                        case "get_user_cards_hash":
//                            payuHashes.setStoredCardsHash(response.getString(key));
//                            break;
//
//                        case "edit_user_card_hash":
//                            payuHashes.setEditCardHash(response.getString(key));
//                            break;
//
//                        case "save_user_card_hash":
//                            payuHashes.setSaveCardHash(response.getString(key));
//                            break;
//
//                        case "check_offer_status_hash":
//                            payuHashes.setCheckOfferStatusHash(response.getString(key));
//                            break;
//                        default:
//                            break;
//                    }
//                }
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return payuHashes;
//        }
//
//        @Override
//        protected void onPostExecute(PayuHashes payuHashes) {
//            super.onPostExecute(payuHashes);
//
//           // progressDialog.dismiss();
//            mProgressbar.setVisibility(View.GONE);
//            launchSdkUI(payuHashes);
//        }
//    }
//    public void launchSdkUI(PayuHashes payuHashes) {
//
//        Intent intent = new Intent(getActivity(), PayUBaseActivity.class);
//        intent.putExtra(PayuConstants.PAYU_CONFIG, payuConfig);
//        intent.putExtra(PayuConstants.PAYMENT_PARAMS, mPaymentParams);
//        intent.putExtra(PayuConstants.PAYU_HASHES, payuHashes);
//        startActivityForResult(intent,PayuConstants.PAYU_REQUEST_CODE);
//    }
//
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
//            if (data != null) {
//                if(data.getStringExtra("payu_response").contains("success")) {
//
//                    //web service call here
//                    callPaymentService(str_paid_type, str_amount, str_validity, mPaymentParams.getTxnId().toString());
//
//                    new AlertDialog.Builder(mContext)
//                            .setCancelable(false)
//                            .setMessage("Payu's Data : " + data.getStringExtra("payu_response") + "\n\n\n Merchant's Data: " +
//                                    data.getStringExtra("result"))
//                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int whichButton) {
//                                    dialog.dismiss();
//                                }
//                            }).show();
//
//                }
//
//
//            } else {
//                Toast.makeText(getActivity(), getString(R.string.could_not_receive_data), Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()){
//
//            case R.id.relative_left_arrow:
//                backPressedListener.onActivityBackPressed();
//                break;
//            case R.id.relative_center_home:
//                Intent i=new Intent(getActivity(), HomePageActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//                break;
//            case R.id.buy_now:
//               // navigateToBaseActivity(v);
//                break;
//
//
//        }
//
//    }
//}

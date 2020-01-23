package com.purplepath.purplepath.retrofitservice;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.purplepath.purplepath.model.AccessToken;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {

    final private static int REQ_TIME_OUT = 2;
//   public static String service_base_url ="http://52.66.64.150/pp_dev2/";
//   public static String service_base_url = "http://35.154.3.101/purple_path/";
//   public static String service_base_url = "http://50.62.164.79/admin/testing_dnd/";

    //prabhu
    //public static String service_base_url = "http://50.62.164.79/admin/pp_test/";

//prabhu new base url has been implemented

    // * Live url

    //    public static String service_base_url = "https://www.purplepathwms.com/purple_path/index.php/" ;
//    public static final String API_KEY="7e8f920f0b65439ff86a87a542044a48";
//    public static final String service_base_url_sub="https://www.purplepathwms.com/purple_path/";
//    public static String download_file_url = "https://s3-ap-southeast-1.amazonaws.com/fino-bucket/";
    /*
     * Live version 1.1 url
     *
     *
    // */

   /* public static String service_base_url = "https://purplepathwms.com/purple_path/";
    public static final String API_KEY = "7e8f920f0b65439ff86a87a542044a48";
    public static final String service_base_url_sub = "https://purplepathwms.com/purple_path/";
    public static String download_file_url = "https://s3-ap-southeast-1.amazonaws.com/fino-bucket/";
    public static String paymentservice_base_url = "http://purplepathwms.in/";
    //commonpayment
    public static String common_payment_request = "https://purplepathwms.com/payment/qpay.php";
    public static String common_payment_respone = "https://purplepathwms.com/payment/status.php";
    //taxpayment
    public static String tax_payment_request = "https://purplepathwms.com/payment/qpay_tax_file.php";
    public static String tax_payment_respone = "https://purplepathwms.com/payment/status_tax_file.php";
    public static String IDENTIFY = "L";*/


   // Dev url
    public static final String service_base_url_sub="http://purplepathwms.in/pp_dev2/";
    public static final String API_KEY="74292922692a484a167ef4c035fb3dc1";
    public static String service_base_url = "http://purplepathwms.in/pp_dev2/";
    //public static String service_base_url = "http://purplepathwms.in/pp_dev1/";
    public static String paymentservice_base_url = "http://purplepathwms.in/";
    public static String download_file_url = "http://s3-ap-southeast-1.amazonaws.com/finobot-dev/";
    //commonpayment
    public static String common_payment_request = "http://purplepathwms.in/payment/qpay.php";
    public static String common_payment_respone="http://purplepathwms.in/payment/status.php";
    //taxpayment
    public static String tax_payment_request = "http://purplepathwms.in/payment/qpay_tax_file.php";
    public static String tax_payment_respone="http://purplepathwms.in/payment/status_tax_file.php";
    public static String IDENTIFY ="D";

    //webview url
    public static final String API_OAUTH_REDIRECT = "nl.jpelgrm.retrofit2oauthrefresh://oauth";
    public static String about_us = service_base_url_sub + "webview/about_us.html";
    public static String terms_conditions = service_base_url_sub + "webview/termsandconditions.html";
    public static String privacy_policy = service_base_url_sub + "webview/privacy_policy.html";
    public static String terms_service = service_base_url_sub + "webview/termsofservice.html";

    public static AccessToken mToken;
    public static AccessToken token;

    private ServiceGenerator() {
    }


    public static <S> S createService(Class<S> serviceClass) {

        Retrofit retrofit;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(service_base_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUnsafeOkHttpClient())
                .build();


        return retrofit.create(serviceClass);
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


            OkHttpClient okHttpClient = new OkHttpClient();

            okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslSocketFactory)
                    .addInterceptor(interceptor)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request.Builder requestBuilder = original.newBuilder()
//                                    .header("Content-type", "application/json")
//                                    .header("Accept", "application/json")
                                    .header("X-API-KEY",
                                            API_KEY)
                                    .method(original.method(), original.body());

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    })
                    .connectTimeout(REQ_TIME_OUT, TimeUnit.MINUTES)
                    .readTimeout(REQ_TIME_OUT, TimeUnit.MINUTES)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                    .build();

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static <S> S createService(Class<S> serviceClass, AccessToken accessToken, Context c) {
        try {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            Retrofit.Builder builder;
            builder = new Retrofit.Builder()
                    .baseUrl(service_base_url)
                    .addConverterFactory(GsonConverterFactory.create());

            if (accessToken != null) {
                final Context mContext = c;
                mToken = accessToken;
                token = accessToken;
                httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Accept", "application/json")
                                .header("Content-type", "application/json")
                                .header("Authorization",
                                        token.getTokenType() + " " + token.getAccessToken())
                                .method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] chain,
                            String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] chain,
                            String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[0];
                    }
                }};

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("TLS");
                try {
                    sslContext.init(null, trustAllCerts,
                            new java.security.SecureRandom());
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext
                        .getSocketFactory();
                httpClient.sslSocketFactory(sslSocketFactory);
                httpClient.authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        if (responseCount(response) >= 2) {
                            // If both the original call and the call with refreshed token failed,
                            // it will probably keep failing, so don't try again.
                            return null;
                        }

                        // We need a new client, since we don't want to make another call using our client with access token
                        WebServiceCalls tokenClient = createService(WebServiceCalls.class);
                        Call<AccessToken> call = tokenClient.getRefreshAccessToken(mToken.getRefreshToken(),
                                mToken.getClientID(), mToken.getClientSecret(), API_OAUTH_REDIRECT,
                                "refresh_token");
                        try {
                            retrofit2.Response<AccessToken> tokenResponse = call.execute();
                            if (tokenResponse.code() == 200) {
                                AccessToken newToken = tokenResponse.body();
                                mToken = newToken;
                                SharedPreferences prefs = mContext.getSharedPreferences("", Context.MODE_PRIVATE);
                                prefs.edit().putBoolean("oauth.loggedin", true).apply();
                                prefs.edit().putString("oauth.accesstoken", newToken.getAccessToken()).apply();
                                prefs.edit().putString("oauth.refreshtoken", newToken.getRefreshToken()).apply();
                                prefs.edit().putString("oauth.tokentype", newToken.getTokenType()).apply();

                                return response.request().newBuilder()
                                        .header("Authorization", newToken.getTokenType() + " " + newToken.getAccessToken())
                                        .build();
                            } else {
                                return null;
                            }
                        } catch (IOException e) {
                            return null;
                        }
                    }
                });
            }

            OkHttpClient client = httpClient.build();
            Retrofit retrofit = builder.client(client).build();
            return retrofit.create(serviceClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}
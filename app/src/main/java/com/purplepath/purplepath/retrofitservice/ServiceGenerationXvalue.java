//package com.purplepath.purplepath.retrofitservice;
//
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by bertrandrussellsakthees on 12/01/17.
// */
//
//public class ServiceGenerationXvalue {
//
//    final private static int REQ_TIME_OUT = 2;
//    public static String service_base_url = "http://xvaluetech.in/bpl_meditech/bpl_meditech/";
//
//    private ServiceGenerationXvalue() {
//    }
//
//
//    public static <S> S createService(Class<S> serviceClass) {
//
//        Retrofit retrofit;
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .connectTimeout(REQ_TIME_OUT, TimeUnit.MINUTES)
//                .readTimeout(REQ_TIME_OUT, TimeUnit.MINUTES).build();
//
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(service_base_url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//
//
//        return retrofit.create(serviceClass);
//    }
//
//}

package com.projects.vkotov.todotestapp.model.api;

import android.content.Context;
import android.util.Log;

import com.projects.vkotov.todotestapp.BuildConfig;
import com.projects.vkotov.todotestapp.Constants;
import com.projects.vkotov.todotestapp.Prefs;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by skill on 28.02.2018.
 */

public class ApiModule {

//    private static final boolean ENABLE_AUTH = false;
//    private static final String AUTH_64 = "***"; //your code here


    public static ApiInterface getApiInterface(Context context) {

        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS);

//        if (Prefs.getToken(context)!=null) {
//            httpClient.addInterceptor(chain -> {
//                Request original = chain.request();
//
//                Request request = original.newBuilder()
//                        .header("Token", Prefs.getToken(context))
//                        .method(original.method(), original.body())
//                        .build();
//
//                return chain.proceed(request);
//            });
//        }

//         Trace serve response
        if (BuildConfig.DEBUG) {
            httpClient.interceptors().add(chain -> {
                Request request = chain.request();
                Response response = chain.proceed(request);
                Log.i("ServerResponse: ", response.body().string());
                return chain.proceed(request);
//                return response;
            });
        }


        if (BuildConfig.DEBUG) {
            httpClient.interceptors().add(chain -> {
                Request request = chain.request();
                Log.i("Retrofit", request.method() + ' ' + request.url());
                if (request.body() != null) {
                    Log.i("Retrofit", request.body().toString());
                }
                return chain.proceed(request);
            });
        }


        Retrofit.Builder builder = new Retrofit.Builder()
//                baseUrl("https://api.github.com/")
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build());
//        if (ENABLE_AUTH) builder.client(httpClient);

        ApiInterface apiInterface = builder.build().create(ApiInterface.class);
        return apiInterface;
    }
}
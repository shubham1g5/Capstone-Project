package com.example.shubham.sixfourfantasy.network;

import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

class RetrofitServiceGenerator {

    private static final String BASE_URL = "https://dev132-cricket-live-scores-v1.p.mashape.com/";

    @NonNull
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request request = chain.request().newBuilder()
                        .addHeader("X-Mashape-Key", BuildConfig.CRICKET_SCORE_API_KEY).build();
                return chain.proceed(request);
            }).build();

    @NonNull
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build();

    public static <T> T createService(@NonNull Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }
}

package com.saladevs.changelogclone.network;

import com.google.gson.Gson;
import com.saladevs.changelogclone.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("x-api-key", BuildConfig.API_KEY);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }).build();

    private static final Retrofit RETROFIT =
            new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .client(CLIENT)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .build();

    private static final PlayStoreService PLAY_STORE_SERVICE =
            RETROFIT.create(PlayStoreService.class);

    public static PlayStoreService getPlayStoreService() {
        return PLAY_STORE_SERVICE;
    }


}

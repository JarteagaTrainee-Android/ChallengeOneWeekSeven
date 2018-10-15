package com.applaudostudio.weeksevenchallengeone.apiclient;

import com.applaudostudio.weeksevenchallengeone.util.ApplicationCon;
import com.applaudostudio.weeksevenchallengeone.util.ConnectionManager;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient  {
    private static Retrofit retrofit = null;

    /***
     * Client for the retrofit
     * @param url bae url
     * @return return a Retrofit object
     */
    public static Retrofit getClient(String url) {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache;
        cache = new Cache(new File(ApplicationCon.getContext().getCacheDir(), "APIData"), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new offLineInterceptor())
                .addNetworkInterceptor(new ApiInterceptor())
                .cache(cache)
                .build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();
        return retrofit;
    }


    /***
     * Interceptor for the retrofit client on offline data
     */
    public static class offLineInterceptor implements Interceptor {
        ConnectionManager networkUtils = new ConnectionManager(ApplicationCon.getContext());

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!networkUtils.isNetworkAvailable()) {
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + 3600)
                        .build();
            }
            return chain.proceed(request);
        }
    }

    /***
     * Interceptor for the api requests
     */
    public static class ApiInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response apiResponse = chain.proceed(chain.request());
            return apiResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 60)
                    .build();
        }
    }


}

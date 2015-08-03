package cn.nekocode.baseframework.rest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.nekocode.baseframework.Config;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by nekocode on 2015/7/17.
 */
public class APIFactory {
    private static final String API_HOST_URL = "http://www.weather.com.cn";
    private static API api;
    private static Gson gson;
    private static OkHttpClient okHttpClient;

    public static API getInstance(Context context) {
        if(api != null) {
            return api;
        } else {
            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("User-Agent", Config.USER_AGENT);
                }
            };

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(API_HOST_URL)
                    .setConverter(new GsonConverter(getGson()))
                    .setClient(new OkClient(getOkHttpClient(context)))
                    .setRequestInterceptor(requestInterceptor)
                    .build();

            api = restAdapter.create(API.class);

            return api;
        }
    }

    public static Gson getGson() {
        if(gson == null) {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                    .create();
        }
        return gson;
    }

    public static OkHttpClient getOkHttpClient(Context context) {
        if(okHttpClient == null) {
            File cacheDir = new File(context.getCacheDir(), Config.RESPONSE_CACHE_FILE);

            okHttpClient = new OkHttpClient();
            okHttpClient.setCache(new Cache(cacheDir, Config.RESPONSE_CACHE_SIZE));

            okHttpClient.setConnectTimeout(Config.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
            okHttpClient.setWriteTimeout(Config.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(Config.HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        }

        return okHttpClient;
    }
}

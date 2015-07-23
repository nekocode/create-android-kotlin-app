package cn.nekocode.baseframework.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by nekocode on 2015/7/17.
 */
public class APIFactory {
    private static final String API_HOST_URL = "http://suggest.taobao.com";
    public static API api;

    public static API getInstance() {
        if(api != null) {
            return api;
        } else {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                    .create();

            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("User-Agent", "Retrofit-Sample-App");
                }
            };

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(API_HOST_URL)
                    .setConverter(new GsonConverter(gson))
                    .setRequestInterceptor(requestInterceptor)
                    .build();

            api = restAdapter.create(API.class);

            return api;
        }
    }
}

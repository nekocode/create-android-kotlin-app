package cn.nekocode.baseframework.network;

import java.util.List;
import java.util.Map;

import cn.nekocode.baseframework.beans.TestBean;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

/**
 * Created by nekocode on 2015/7/17.
 */
public class APIFactory {
    public static API api;
    public static API api2;

    public static API getInstance() {
        if(api != null) {
            return api;
        } else {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("https://api.github.com")
                    .build();

            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("User-Agent", "Retrofit-Sample-App");
                }
            };

            RestAdapter restAdapter2 = new RestAdapter.Builder()
                    .setEndpoint("https://api.github.com")
                    .setRequestInterceptor(requestInterceptor)
                    .build();

            api = restAdapter.create(API.class);

            return api;
        }
    }

    public static API getInstanceWithAgent() {
        if(api2 != null) {
            return api2;
        } else {
            RequestInterceptor requestInterceptor = new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("User-Agent", "Retrofit-Sample-App");
                }
            };

            RestAdapter restAdapter2 = new RestAdapter.Builder()
                    .setEndpoint("https://api.github.com")
                    .setRequestInterceptor(requestInterceptor)
                    .build();

            api2 = restAdapter2.create(API.class);

            return api2;
        }
    }
}

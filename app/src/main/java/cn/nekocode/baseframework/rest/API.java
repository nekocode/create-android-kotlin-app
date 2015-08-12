package cn.nekocode.baseframework.rest;

import java.util.List;
import java.util.Map;

import cn.nekocode.baseframework.model.Weather;
import retrofit.Callback;
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
import rx.Observable;

/**
 * Created by nekocode on 2015/7/22.
 */
public interface API {
    @GET("/users/{user}/repos")
    List<Weather> listTest(@Path("user") String user);

    @GET("/group/{id}/users")
    List<Weather> groupList(@Path("id") int groupId, @Query("sort") String sort);

    @GET("/group/{id}/users")
    List<Weather> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST("/user/edit")
    Weather updateUser(@Field("first_name") String first, @Field("last_name") String last);

    @Multipart
    @PUT("/user/photo")
    Weather updateUser(@Part("photo") TypedFile photo, @Part("description") TypedString description);

    @Headers("Cache-Control: max-age=640000")
    @GET("/widget/list")
    List<Weather> widgetList();

    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("/users/{username}")
    Weather getUser(@Path("username") String username);

    @GET("/user")
    void getUser(@Header("Authorization") String authorization, Callback<Weather> callback);

    // Asynchronous execution requires the last parameter of the method be a Callback.
    @GET("/user/{id}/photo")
    void getUserPhoto(@Path("id") int id, Callback<Weather> cb);

//    @GET("/sug")
//    Observable<Weather> sugList(@Query("code") String code, @Query("q") String q);

    @GET("/sug")
    void sugList(@Query("code") String code, @Query("q") String q, Callback<Weather> callback);

    @GET("/adat/sk/{cityId}.html") //101010100
    Observable<Weather> getWeather(@Path("cityId") String cityId);

    @GET("/adat/sk/{cityId}.html") //101010100
    void getWeather(@Path("cityId") String cityId, Callback<Weather> callback);
}

package cn.nekocode.baseframework.rest

import cn.nekocode.baseframework.AppContext
import cn.nekocode.baseframework.Config
import cn.nekocode.baseframework.model.Weather
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.okhttp.Cache
import com.squareup.okhttp.OkHttpClient
import retrofit.Callback
import retrofit.RequestInterceptor
import retrofit.RestAdapter
import retrofit.client.OkClient
import retrofit.converter.GsonConverter
import retrofit.http.*
import retrofit.mime.TypedFile
import retrofit.mime.TypedString
import rx.Observable
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.platform.platformStatic

/**
 * Created by nekocode on 2015/8/13 0013.
 */

public class REST {
    companion object {
        public platformStatic val API_HOST_URL: String = "http://www.weather.com.cn"

        val okHttpClient: OkHttpClient
        val gson: Gson
        val api: APIs

        init {
            val cacheDir = File(AppContext.get().getCacheDir(), Config.RESPONSE_CACHE_FILE)
            okHttpClient = OkHttpClient()
            okHttpClient.setCache(Cache(cacheDir, Config.RESPONSE_CACHE_SIZE.toLong()))
            okHttpClient.setConnectTimeout(Config.HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            okHttpClient.setWriteTimeout(Config.HTTP_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            okHttpClient.setReadTimeout(Config.HTTP_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)

            gson = GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create()

            val requestInterceptor = object : RequestInterceptor {
                override fun intercept(request: RequestInterceptor.RequestFacade) {
                    request.addHeader("User-Agent", Config.USER_AGENT)
                }
            }

            val restAdapter = RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setEndpoint(REST.API_HOST_URL)
                    .setConverter(GsonConverter(gson))
                    .setClient(OkClient(okHttpClient))
                    .setRequestInterceptor(requestInterceptor).build()

            api = restAdapter.create(javaClass<cn.nekocode.baseframework.rest.REST.APIs>())
        }
    }

    interface APIs {
        GET("/users/{user}/repos")
        fun listTest(Path("user") user: String): List<Weather>

        GET("/group/{id}/users")
        fun groupList(Path("id") groupId: Int, Query("sort") sort: String): List<Weather>

        GET("/group/{id}/users")
        fun groupList(Path("id") groupId: Int, QueryMap options: Map<String, String>): List<Weather>

        FormUrlEncoded
        POST("/user/edit")
        fun updateUser(Field("first_name") first: String, Field("last_name") last: String): Weather

        Multipart
        PUT("/user/photo")
        fun updateUser(Part("photo") photo: TypedFile, Part("description") description: TypedString): Weather

        Headers("Cache-Control: max-age=640000")
        GET("/widget/list")
        fun widgetList(): List<Weather>

        Headers("Accept: application/vnd.github.v3.full+json", "User-Agent: Retrofit-Sample-App")
        GET("/users/{username}")
        fun getUser(Path("username") username: String): Weather

        GET("/user")
        fun getUser(Header("Authorization") authorization: String, callback: Callback<Weather>)

        // Asynchronous execution requires the last parameter of the method be a Callback.
        GET("/user/{id}/photo")
        fun getUserPhoto(Path("id") id: Int, cb: Callback<Weather>)

        GET("/sug")
        fun sugList(Query("code") code: String, Query("q") q: String, callback: Callback<Weather>)

        GET("/adat/sk/{cityId}.html") //101010100
        fun getWeather(Path("cityId") cityId: String): Observable<Weather>
    }
}

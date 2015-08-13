package cn.nekocode.baseframework.rest

import cn.nekocode.baseframework.AppContext
import cn.nekocode.baseframework.Config
import cn.nekocode.baseframework.model.Weather
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.okhttp.Cache
import com.squareup.okhttp.OkHttpClient
import retrofit.RequestInterceptor
import retrofit.RestAdapter
import retrofit.client.OkClient
import retrofit.converter.GsonConverter
import retrofit.http.GET
import retrofit.http.Path
import rx.Observable
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.platform.platformStatic

/**
 * Created by nekocode on 2015/8/13 0013.
 */

public class APIs {
    companion object APIs {
        private platformStatic val API_HOST_URL: String = "http://www.weather.com.cn"

        val okHttpClient: OkHttpClient
        val gson: Gson
        val api: API

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
                    .setEndpoint(API_HOST_URL)
                    .setConverter(GsonConverter(gson))
                    .setClient(OkClient(okHttpClient))
                    .setRequestInterceptor(requestInterceptor).build()

            api = restAdapter.create(javaClass<cn.nekocode.baseframework.rest.APIs.API>())
        }
    }

    interface API {
        GET("/adat/sk/{cityId}.html") //101010100
        public fun getWeather(Path("cityId") cityId: String): Observable<Weather>
    }
}

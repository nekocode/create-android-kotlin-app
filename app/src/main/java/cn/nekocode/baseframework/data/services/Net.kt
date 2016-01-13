package cn.nekocode.baseframework.data.services

import cn.nekocode.baseframework.App
import cn.nekocode.baseframework.Config
import cn.nekocode.baseframework.data.modules.WeatherModule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.RxJavaCallAdapterFactory
import retrofit2.http.*
import rx.Observable
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by nekocode on 2015/8/13 0013.
 */

public class Net {
    companion object {
        val API_HOST_URL: String = "http://www.weather.com.cn/"

        val okHttpClient: OkHttpClient
        val gson: Gson
        val api: APIs

        init {
            val cacheDir = File(App.instance.cacheDir, Config.Companion.RESPONSE_CACHE_FILE)
            okHttpClient = OkHttpClient.Builder()
                    .cache(Cache(cacheDir, Config.Companion.RESPONSE_CACHE_SIZE.toLong()))
                    .connectTimeout(Config.HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(Config.HTTP_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .readTimeout(Config.HTTP_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .build()

            gson = GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create()

            val restAdapter = Retrofit.Builder()
                    .baseUrl(API_HOST_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()

            api = restAdapter.create(APIs::class.java)
        }
    }

    interface APIs {
        @GET("adat/sk/{cityId}.html") //101010100
        fun getWeather(@Path("cityId") cityId: String): Observable<WeatherModule.WeatherWrapper>
    }
}

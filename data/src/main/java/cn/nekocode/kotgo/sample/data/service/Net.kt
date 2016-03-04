package cn.nekocode.kotgo.sample.data.service

import cn.nekocode.kotgo.sample.data.DataLayer
import cn.nekocode.kotgo.sample.data.dto.Meizi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by nekocode on 2015/8/13 0013.
 */

class Net {
    companion object {
        // Host
        val API_HOST_URL: String = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/"

        // OkHttp Config
        val RESPONSE_CACHE_FILE: String = "reponse_cache"
        val RESPONSE_CACHE_SIZE: Int = 10 * 1024 * 1024     // 10 MB
        val HTTP_CONNECT_TIMEOUT: Int = 10
        val HTTP_READ_TIMEOUT: Int = 30
        val HTTP_WRITE_TIMEOUT: Int = 10

        val okHttpClient: OkHttpClient
        val gson: Gson = GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create()
        val api: APIs

        init {
            val cacheDir = File(DataLayer.app.cacheDir, RESPONSE_CACHE_FILE)
            okHttpClient = OkHttpClient.Builder()
                    .cache(Cache(cacheDir, RESPONSE_CACHE_SIZE.toLong()))
                    .connectTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(HTTP_WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .readTimeout(HTTP_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .build()

            val restAdapter = Retrofit.Builder()
                    .baseUrl(API_HOST_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()

            api = restAdapter.create(APIs::class.java)
        }
    }

    data class ResponseWrapper<T>(val error: Boolean, val results: List<T>)

    interface APIs {
        @GET("{count}/{pageNum}")
        fun getMeizi(@Path("count") count: Int, @Path("pageNum") pageNum: Int): Observable<ResponseWrapper<Meizi>>
    }
}

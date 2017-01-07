package cn.nekocode.kotgo.sample.data.service

import cn.nekocode.kotgo.sample.data.DataLayer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
internal object GankService {
    // OkHttp Config
    const val RESPONSE_CACHE_FILE: String = "GANK_SERVICE_CACHE"
    const val RESPONSE_CACHE_SIZE = 10 * 1024 * 1024L
    const val HTTP_CONNECT_TIMEOUT = 10L
    const val HTTP_READ_TIMEOUT = 30L
    const val HTTP_WRITE_TIMEOUT = 10L

    // Host
    const val API_HOST_URL: String = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/"

    val HTTP_CLIENT: OkHttpClient = OkHttpClient.Builder()
            .apply {
                DataLayer.APP?.let {
                    cache(Cache(File(it.cacheDir, RESPONSE_CACHE_FILE),
                            RESPONSE_CACHE_SIZE))
                }
            }
            .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .build()

    val GSON: Gson = GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create()

    val REST_ADAPTER: Retrofit = Retrofit.Builder()
            .baseUrl(API_HOST_URL)
            .addConverterFactory(GsonConverterFactory.create(GSON))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(HTTP_CLIENT)
            .build()

    class ResponseWrapper<out T>(val error: Boolean, val results: List<T>)
}

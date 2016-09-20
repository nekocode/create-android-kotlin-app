package cn.nekocode.kotgo.sample.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.hawk.Hawk
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by nekocode on 2016/1/15.
 */
object DataLayer {
    // OkHttp Config
    const val RESPONSE_CACHE_FILE: String = "reponse_cache"
    const val RESPONSE_CACHE_SIZE = 10 * 1024 * 1024L
    const val HTTP_CONNECT_TIMEOUT = 10L
    const val HTTP_READ_TIMEOUT = 30L
    const val HTTP_WRITE_TIMEOUT = 10L

    lateinit var app: Context
    lateinit var okHttpClient: OkHttpClient
    val gson: Gson = GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create()

    fun init(app: Context) {
        DataLayer.app = app

        Hawk.init(app).build()

        okHttpClient = OkHttpClient.Builder().apply {
            val cacheDir = File(app.cacheDir, RESPONSE_CACHE_FILE)
            cache(Cache(cacheDir, RESPONSE_CACHE_SIZE))
            connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
        }.build()
    }
}
package cn.nekocode.template.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.paperdb.Paper
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
object DataLayer {
    const val HOST_GANK: String = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/"
    var RETROFIT_GANK: Retrofit? = null
    var CONTEXT: Context? = null
    var CLIENT: OkHttpClient? = null
    var GSON: Gson? = null


    fun init(context: Context) {
        DataLayer.CONTEXT = context.applicationContext

        CLIENT = OkHttpClient.Builder()
                .cache(Cache(File(context.cacheDir, "okhttp"), 10 * 1024 * 1024L))
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()

        GSON = GsonBuilder().setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'").create()

        RETROFIT_GANK = Retrofit.Builder()
                .baseUrl(HOST_GANK)
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(CLIENT)
                .build()

        Paper.init(context)
    }
}
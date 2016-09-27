package cn.nekocode.kotgo.sample.data.service

import cn.nekocode.kotgo.sample.data.DataLayer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
internal object GankService {
    // Host
    const val API_HOST_URL: String = "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/"

    val REST_ADAPTER: Retrofit = Retrofit.Builder()
            .baseUrl(API_HOST_URL)
            .addConverterFactory(GsonConverterFactory.create(DataLayer.gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(DataLayer.okHttpClient)
            .build()

    class ResponseWrapper<out T>(val error: Boolean, val results: List<T>)
}

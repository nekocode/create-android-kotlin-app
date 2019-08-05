/*
 * Copyright 2018. nekocode (nekocode.cn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.nekocode.gank.backend

import cn.nekocode.gank.backend.api.PicApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class Apis(
    httpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder(),
    gsonBuilder: GsonBuilder = GsonBuilder(),
    apiEnv: ApiEnv = ApiEnv.PRODUCT
) {

    enum class ApiEnv(val baseUrl: String) {
        PRODUCT("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/"),
    }

    /**
     * OkHttp Client
     */
    val client: OkHttpClient = httpClientBuilder
        .addInterceptor { chain ->
            val oldReq = chain.request()
            val newReqBuilder = oldReq.newBuilder()

            // Add headers
            newReqBuilder.addHeader("x-device-platform", "android")

            chain.proceed(newReqBuilder.build())
        }
        .build()

    /**
     * Gson
     */
    val gson: Gson = gsonBuilder.create()

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(apiEnv.baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    /**
     * All apis
     */
    val pic: PicApi = retrofit.create(PicApi::class.java)
}

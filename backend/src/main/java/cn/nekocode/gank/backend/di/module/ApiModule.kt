/*
 * Copyright 2019. nekocode (nekocode.cn@gmail.com)
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

package cn.nekocode.gank.backend.di.module

import cn.nekocode.gank.backend.api.PicApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@Module
class ApiModule(
    private val httpClientBuilder: OkHttpClient.Builder,
    private val gsonBuilder: GsonBuilder,
    private val env: Env = Env.PRODUCT
) {

    enum class Env(val baseUrl: String) {
        PRODUCT("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/"),
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = httpClientBuilder
        .addInterceptor { chain ->
            val oldReq = chain.request()
            val newReqBuilder = oldReq.newBuilder()

            // Add headers
            newReqBuilder.addHeader("x-device-platform", "android")

            chain.proceed(newReqBuilder.build())
        }
        .build()

    @Provides
    @Singleton
    fun provideGson(): Gson = gsonBuilder.create()

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(env.baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providePicApi(retrofit: Retrofit): PicApi = retrofit.create(PicApi::class.java)
}
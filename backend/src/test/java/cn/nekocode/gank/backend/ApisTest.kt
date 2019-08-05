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

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Ignore
import org.junit.Test

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class ApisTest {
    private val apis = Apis(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor(
            logger = object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        ).apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
    )
    private val client = apis.client
    private val gson = apis.gson

    @Test
    @Ignore("Please run this method manually.")
    fun getMeiziPics() {
        apis.pic.getMeiziPics(10, 0)
            .test()
            .assertNoErrors()
            .assertValue { response ->
                !response.error && response.results.size == 10
            }
    }
}

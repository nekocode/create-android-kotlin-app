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

package cn.nekocode.gank

import android.app.Application
import cn.nekocode.gank.backend.Apis
import cn.nekocode.gank.broadcast.BroadcastCallAdapter
import cn.nekocode.gank.broadcast.BroadcastConfig
import cn.nekocode.gank.broadcast.BroadcastRouter
import cn.nekocode.meepo.Meepo
import cn.nekocode.meepo.config.UriConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class GankApplication : Application() {
    lateinit var activityRouter: ActivityRouter
    lateinit var broadcastRouter: BroadcastRouter
    lateinit var apis: Apis

    override fun onCreate() {
        super.onCreate()

        activityRouter = Meepo.Builder()
            .config(UriConfig().scheme(BuildConfig.SCHEME).host(BuildConfig.APPLICATION_ID))
            .build().create(ActivityRouter::class.java)
        broadcastRouter = Meepo.Builder()
            .config(BroadcastConfig()).adapter(BroadcastCallAdapter())
            .build().create(BroadcastRouter::class.java)
        apis = Apis(OkHttpClient.Builder(), GsonBuilder())
    }
}

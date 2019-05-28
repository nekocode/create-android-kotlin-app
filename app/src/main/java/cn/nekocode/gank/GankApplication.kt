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
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager
import cn.nekocode.gank.backend.GankIoService
import cn.nekocode.gank.broadcast.BroadcastCallAdapter
import cn.nekocode.gank.broadcast.BroadcastConfig
import cn.nekocode.gank.broadcast.BroadcastRouter
import cn.nekocode.meepo.Meepo
import cn.nekocode.meepo.config.UriConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class GankApplication : Application() {
    lateinit var activityRouter: ActivityRouter
    lateinit var broadcastRouter: BroadcastRouter
    lateinit var gankIoService: GankIoService

    override fun onCreate() {
        super.onCreate()

        activityRouter = Meepo.Builder()
            .config(UriConfig().scheme(BuildConfig.SCHEME).host(BuildConfig.APPLICATION_ID))
            .build().create(ActivityRouter::class.java)
        broadcastRouter = Meepo.Builder()
            .config(BroadcastConfig()).adapter(BroadcastCallAdapter())
            .build().create(BroadcastRouter::class.java)
        gankIoService = GankIoService(OkHttpClient(), Gson())
    }
}

val Context.activityRouter get() = (this.applicationContext as GankApplication).activityRouter
val Context.broadcastRouter get() = (this.applicationContext as GankApplication).broadcastRouter
val Context.gankIoService get() = (this.applicationContext as GankApplication).gankIoService

fun Context.registerLocalReceiver(
    receiver: BroadcastReceiver.(Context?, Intent?) -> Unit,
    intentFilter: IntentFilter
) {
    LocalBroadcastManager.getInstance(this)
        .registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                receiver.invoke(this, context, intent)
            }
        }, intentFilter)
}

fun Context.registerLocalReceiver(
    receiver: BroadcastReceiver.(Context?, Intent?) -> Unit,
    vararg actions: String
) {
    val intentFilter = IntentFilter()
    actions.forEach {
        intentFilter.addAction(it)
    }
    registerLocalReceiver(receiver, intentFilter)
}

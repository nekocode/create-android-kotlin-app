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

package cn.nekocode.caka

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import cn.nekocode.caka.backend.di.module.ApiModule
import cn.nekocode.caka.di.component.AppComponent
import cn.nekocode.caka.di.component.DaggerAppComponent
import cn.nekocode.caka.di.module.AppModule
import cn.nekocode.caka.di.module.FlipperModule
import com.facebook.flipper.core.FlipperClient
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import timber.log.Timber
import javax.inject.Inject

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
open class MyApplication : Application() {
    lateinit var component: AppComponent
    @Inject
    lateinit var flipperClient: FlipperClient
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate() {
        super.onCreate()
        component = createComponent()
        component.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        flipperClient.start()
    }

    protected open fun createComponent(): AppComponent {
        val httpClientBuilder = OkHttpClient.Builder()
        val gsonBuilder = GsonBuilder()

        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .flipperModule(FlipperModule(this, httpClientBuilder))
            .apiModule(ApiModule(httpClientBuilder, gsonBuilder))
            .build()
    }
}

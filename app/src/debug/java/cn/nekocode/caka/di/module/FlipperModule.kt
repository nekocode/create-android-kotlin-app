package cn.nekocode.caka.di.module

import android.app.Application
import cn.nekocode.caka.di.AppScope
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.core.FlipperPlugin
import com.facebook.flipper.core.FlipperStateUpdateListener
import com.facebook.flipper.core.StateSummary
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@Module
class FlipperModule(
    private val app: Application,
    private val httpClientBuilder: OkHttpClient.Builder
) {

    @Provides
    @AppScope
    fun provideClient(): FlipperClient {
        return if (FlipperUtils.shouldEnableFlipper(app)) {
            SoLoader.init(app, false)

            AndroidFlipperClient.getInstance(app).apply {
                // Layout inspecting
                addPlugin(
                    InspectorFlipperPlugin(app, DescriptorMapping.withDefaults())
                )

                // Network inspecting
                NetworkFlipperPlugin().let {
                    addPlugin(it)
                    httpClientBuilder.addNetworkInterceptor(FlipperOkhttpInterceptor(it))
                }
            }

        } else {
            NO_OP_CLIENT
        }
    }

    companion object {
        private val NO_OP_CLIENT = object : FlipperClient {
            override fun getState(): String? = null
            override fun getStateSummary(): StateSummary? = null
            override fun addPlugin(plugin: FlipperPlugin?) {}
            override fun <T : FlipperPlugin?> getPlugin(id: String?): T? = null
            override fun <T : FlipperPlugin?> getPluginByClass(cls: Class<T>?): T? = null
            override fun removePlugin(plugin: FlipperPlugin?) {}
            override fun start() {}
            override fun stop() {}
            override fun unsubscribe() {}
            override fun subscribeForUpdates(stateListener: FlipperStateUpdateListener?) {}
        }
    }
}
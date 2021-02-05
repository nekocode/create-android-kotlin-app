package cn.nekocode.caka.di.module

import android.app.Application
import cn.nekocode.caka.di.AppScope
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.core.FlipperClient
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
    fun provideClient(): FlipperClient = AndroidFlipperClient.getInstance(app)
}
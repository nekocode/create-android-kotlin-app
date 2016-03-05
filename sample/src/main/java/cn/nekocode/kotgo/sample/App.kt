package cn.nekocode.kotgo.sample

import android.app.Application
import cn.nekocode.kotgo.component.util.FileUtils
import cn.nekocode.kotgo.component.util.MainThreadBus
import cn.nekocode.kotgo.sample.data.DataLayer
import kotlin.properties.Delegates

/**
 * Created by nekocode on 2015/7/22.
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        FileUtils.createAppDirs(this)
        DataLayer.hook(this)
    }

    companion object {
        var instance by Delegates.notNull<App>()
        val bus = MainThreadBus()
    }

}

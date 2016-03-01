package cn.nekocode.baseframework.sample

import android.app.Application
import cn.nekocode.baseframework.sample.data.DataLayer
import cn.nekocode.baseframework.sample.util.FileUtils
import kotlin.properties.Delegates

/**
 * Created by nekocode on 2015/7/22.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        FileUtils.createAppDirs()
        DataLayer.hook(this)
    }

    companion object {
        var instance by Delegates.notNull<App>()
    }

}

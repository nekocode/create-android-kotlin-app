package cn.nekocode.baseframework

import android.app.Application
import cn.nekocode.baseframework.data.DataLayer
import cn.nekocode.baseframework.utils.FileUtils
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

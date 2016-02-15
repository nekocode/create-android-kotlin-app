package cn.nekocode.baseframework

import android.app.Application
import cn.nekocode.baseframework.data.DataLayer
import cn.nekocode.baseframework.utils.FileUtils

/**
 * Created by nekocode on 2015/7/22.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instanceTmp = this

        FileUtils.createAppDirs()
        DataLayer.hook(this)
//        LeakCanary.install(this)
    }

    companion object {
        private var instanceTmp: App? = null

        val instance: App by lazy {
            instanceTmp!!
        }
    }

}

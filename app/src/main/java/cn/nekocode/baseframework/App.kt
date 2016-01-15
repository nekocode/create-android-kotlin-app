package cn.nekocode.baseframework

import android.app.Application
import cn.nekocode.baseframework.data.DataLayer
import cn.nekocode.baseframework.utils.FileUtils

/**
 * Created by nekocode on 2015/7/22.
 */
public class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instanceTmp = this

        FileUtils.createAppDirs()
//        LeakCanary.install(this)

        DataLayer.hook(this)
    }

    companion object {
        private var instanceTmp: App? = null

        public val instance: App by lazy {
            instanceTmp!!
        }
    }

}

package cn.nekocode.baseframework

import android.app.Application
import cn.nekocode.baseframework.data.local.Storage
import cn.nekocode.toast.utils.FileUtils
import com.squareup.leakcanary.LeakCanary

/**
 * Created by nekocode on 2015/7/22.
 */
public class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instanceTmp = this

        FileUtils.createAppRootDirs()
        Storage.init(this)
        LeakCanary.install(this)
    }

    companion object {
        private var instanceTmp: App? = null

        public val instance: App by lazy {
            instanceTmp!!
        }
    }

}

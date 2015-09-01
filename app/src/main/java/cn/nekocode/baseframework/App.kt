package cn.nekocode.baseframework

import android.app.Application
import android.content.Context
import cn.nekocode.toast.utils.FileUtils
import java.io.File
import kotlin.properties.Delegates

/**
 * Created by nekocode on 2015/7/22.
 */
public class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instanceTmp = this

        FileUtils.createAppRootDirs()
    }

    companion object {
        private var instanceTmp: App? = null

        public val instance: App by Delegates.lazy {
            instanceTmp!!
        }
    }

}

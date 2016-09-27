package cn.nekocode.kotgo.sample

import android.app.Application
import cn.nekocode.kotgo.sample.data.DataLayer

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        DataLayer.init(this)
    }

    companion object {
        lateinit var instance: App
    }

}

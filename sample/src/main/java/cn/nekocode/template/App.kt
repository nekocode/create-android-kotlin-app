package cn.nekocode.template

import android.app.Application
import cn.nekocode.template.data.DataLayer

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DataLayer.init(this)
    }

}

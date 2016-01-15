package cn.nekocode.baseframework.data

import android.app.Application
import cn.nekocode.baseframework.data.service.Local

/**
 * Created by nekocode on 2016/1/15.
 */
class DataLayer {
    companion object {
        var appTmp: Application? = null
        val app: Application by lazy {
            appTmp!!
        }

        fun hook(app: Application) {
            appTmp = app
            Local.init(app)
        }
    }
}
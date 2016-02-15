package cn.nekocode.baseframework.data

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel

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

            Hawk.init(app)
                    .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                    .setStorage(HawkBuilder.newSqliteStorage(app))
                    .setLogLevel(LogLevel.FULL)
                    .build();
        }
    }
}
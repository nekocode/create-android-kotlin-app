package cn.nekocode.baseframework.data

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel

/**
 * Created by nekocode on 2016/1/15.
 */
class DataLayer(val app: Application) {
    companion object {
        var instanceTmp: DataLayer? = null
        val instance: DataLayer by lazy {
            instanceTmp!!
        }
    }

    init {
        DataLayer.instanceTmp = this

        Hawk.init(app)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSqliteStorage(app))
                .setLogLevel(LogLevel.FULL)
                .build();
    }
}
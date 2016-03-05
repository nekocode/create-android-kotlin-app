package cn.nekocode.kotgo.sample.data

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel
import kotlin.properties.Delegates

/**
 * Created by nekocode on 2016/1/15.
 */
object DataLayer {
    var app by Delegates.notNull<Application>()

    fun hook(app: Application) {
        DataLayer.app = app

        Hawk.init(app)
                .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                .setStorage(HawkBuilder.newSqliteStorage(app))
                .setLogLevel(LogLevel.FULL)
                .build()
    }
}
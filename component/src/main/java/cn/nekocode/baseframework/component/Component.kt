package cn.nekocode.baseframework.component

import android.app.Application
import kotlin.properties.Delegates

/**
 * Created by nekocode on 16/2/29.
 */
object Component {
    var app by Delegates.notNull<Application>()

    fun inject(app: Application) {
        Component.app = app
    }
}
package cn.nekocode.baseframework.component.util

import android.os.Handler
import android.os.Looper
import com.squareup.otto.Bus

/**
 * Created by nekocode on 16/3/3.
 * Inspired by: http://www.jianshu.com/p/80fbb545828d
 */
class MainThreadBus: Bus() {
    private val handler = Handler(Looper.getMainLooper())

    override fun post(event: Any?) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event)
        } else {
            handler.post {
                super.post(event)
            }
        }
    }
}
package cn.nekocode.kotgo.sample.data

import android.content.Context
import io.paperdb.Paper

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
object DataLayer {
    internal var APP: Context? = null

    fun init(context: Context) {
        DataLayer.APP = context.applicationContext

        Paper.init(APP)
    }
}
package cn.nekocode.baseframework.sample

import android.content.Context
import android.content.Intent
import cn.nekocode.baseframework.sample.presentation.main.MainActivity

/**
 * Created by nekocode on 16/2/29.
 */
object Navigator {
    fun toMainPage(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}
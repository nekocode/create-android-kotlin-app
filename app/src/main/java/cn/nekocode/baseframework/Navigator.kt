package cn.nekocode.baseframework

import android.content.Context
import android.content.Intent
import cn.nekocode.baseframework.presentation.main.MainActivity

/**
 * Created by nekocode on 16/2/29.
 */
object Navigator {
    fun toMainPage(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}
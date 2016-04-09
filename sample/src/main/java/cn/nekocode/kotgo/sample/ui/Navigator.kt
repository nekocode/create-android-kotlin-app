package cn.nekocode.kotgo.sample.ui

import android.app.Activity
import cn.nekocode.kotgo.sample.data.dto.Meizi
import cn.nekocode.kotgo.sample.ui.main.MainActivity
import cn.nekocode.kotgo.sample.ui.page2.Page2Activity
import org.jetbrains.anko.intentFor

/**
 * Created by nekocode on 16/2/29.
 */
object Navigator {
    fun gotoMainPage(act: Activity) {
        act.apply {
            startActivity(act.intentFor<MainActivity>())
        }
    }

    fun gotoPage2(act: Activity, meizi: Meizi) {
        act.apply {
            startActivity(intentFor<Page2Activity>("meizi" to meizi))
        }
    }
}
package cn.nekocode.baseframework.sample.presentation

import android.content.Context
import cn.nekocode.baseframework.sample.presentation.main.MainActivity
import cn.nekocode.baseframework.sample.presentation.page2.Page2Activity
import org.jetbrains.anko.intentFor

/**
 * Created by nekocode on 16/2/29.
 */
fun Context.navigateToMainPage() {
    startActivity(intentFor<MainActivity>())
}

fun Context.navigateToPage2(title: String) {
    startActivity(intentFor<Page2Activity>(Pair("title", title)))
}



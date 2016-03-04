package cn.nekocode.kotgo.sample.presentation

import android.content.Context
import cn.nekocode.kotgo.sample.data.dto.Meizi
import cn.nekocode.kotgo.sample.presentation.main.MainActivity
import cn.nekocode.kotgo.sample.presentation.page2.Page2Activity
import org.jetbrains.anko.intentFor

/**
 * Created by nekocode on 16/2/29.
 */
fun Context.navigateToMainPage() {
    startActivity(intentFor<MainActivity>())
}

fun Context.navigateToPage2(meizi: Meizi) {
    startActivity(intentFor<Page2Activity>(Pair("meizi", meizi)))
}



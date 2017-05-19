package cn.nekocode.template.page

import android.content.Context
import cn.nekocode.meepo.annotation.Bundle
import cn.nekocode.meepo.annotation.TargetClass
import cn.nekocode.meepo.Meepo
import cn.nekocode.template.data.DO.Meizi
import cn.nekocode.template.page.page2.Page2Activity
import cn.nekocode.template.page.page2.Page2Presenter

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface UIRouter {

    companion object {
        val IMPL = Meepo.Builder().build().create(UIRouter::class.java)
    }

    @TargetClass(Page2Activity::class)
    fun gotoPage2(context: Context?, @Bundle(Page2Presenter.ARG_MEIZI) meizi: Meizi) {
        IMPL.gotoPage2(context, meizi)
    }
}
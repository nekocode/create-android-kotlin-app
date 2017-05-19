package cn.nekocode.template.base

import cn.nekocode.template.page.UIRouter
import org.jetbrains.anko.toast

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface IPresenter : IContextProvider, UIRouter {

    fun onError(err: Throwable) {
        context()?.toast(err.message ?: "Unknown Error")
    }
}
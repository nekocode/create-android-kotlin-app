package cn.nekocode.template.base

import org.jetbrains.anko.toast

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface IView : IContextProvider {

    fun toast(msg: String) {
        context()?.toast(msg)
    }
}
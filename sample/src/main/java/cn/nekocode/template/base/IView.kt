package cn.nekocode.template.base

import org.jetbrains.anko.toast

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface IView : IContext {

    fun toast(msg: String) {
        getContext().toast(msg)
    }
}
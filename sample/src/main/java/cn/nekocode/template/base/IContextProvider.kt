package cn.nekocode.template.base

import android.content.Context

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface IContextProvider {

    fun context(): Context?
}
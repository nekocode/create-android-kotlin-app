package cn.nekocode.template.base

import android.widget.Toast

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface IView : IContextProvider {

    fun toast(msg: String) {
        Toast.makeText(getContext() ?: return, msg, Toast.LENGTH_SHORT).show()
    }
}
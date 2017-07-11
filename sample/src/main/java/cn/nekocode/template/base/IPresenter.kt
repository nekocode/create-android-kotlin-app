package cn.nekocode.template.base

import android.widget.Toast
import cn.nekocode.template.screen.UIRouter

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface IPresenter : IContextProvider, UIRouter {

    fun onError(err: Throwable) {
        Toast.makeText(getContext() ?: return, err.message ?: "Unknown Error", Toast.LENGTH_SHORT).show()
    }
}
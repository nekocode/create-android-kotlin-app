package cn.nekocode.kotgo.component.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class BasePresenter() : WithLifecycleFragment() {

    final override fun onCreateView(inflater: LayoutInflater?,
                                    container: ViewGroup?, savedInstanceState: Bundle?): View? {

        onViewCreated(parentFragment ?: activity, savedInstanceState)
        return null
    }

    abstract fun onViewCreated(viewOfContract: Any, savedInstanceState: Bundle?)

    final override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    }
}
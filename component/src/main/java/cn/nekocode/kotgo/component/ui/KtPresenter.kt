package cn.nekocode.kotgo.component.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class KtPresenter<in V>() : WithLifecycleFragment() {

    final override fun onCreateView(inflater: LayoutInflater?,
                                    container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        onViewCreated((parentFragment ?: activity) as V?, savedInstanceState)
        return null
    }

    abstract fun onViewCreated(view: V?, savedInstanceState: Bundle?)

    final override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    }
}
package cn.nekocode.kotgo.component.presentation

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nekocode.kotgo.component.rx.RxLifecycle

/**
 * Created by nekocode on 16/3/3.
 */
abstract class BaseFragment: Fragment(), RxLifecycle.Getter {
    override val lifecycle = RxLifecycle()
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        lifecycle.onDestory()
    }
}
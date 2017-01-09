package cn.nekocode.kotgo.component.rx

import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
open class WithLifecycleDialogFragment() : DialogFragment(), RxLifecycle.Impl {
    override val lifecycle = RxLifecycle()

    @CallSuper
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        lifecycle.onAttach()
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.onCreate()
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lifecycle.onCreateView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycle.onStart()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycle.onResume()
    }

    @CallSuper
    override fun onPause() {
        lifecycle.onPause()
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycle.onStop()
        super.onStop()
    }

    @CallSuper
    override fun onDestroyView() {
        lifecycle.onDestroyView()
        super.onDestroyView()
    }

    @CallSuper
    override fun onDestroy() {
        lifecycle.onDestroy()
        super.onDestroy()
    }

    override fun onDetach() {
        lifecycle.onDetach()
        super.onDetach()
    }
}
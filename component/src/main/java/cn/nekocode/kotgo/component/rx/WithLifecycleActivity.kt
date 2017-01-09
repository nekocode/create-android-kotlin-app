package cn.nekocode.kotgo.component.rx

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
open class WithLifecycleActivity() : AppCompatActivity(), RxLifecycle.Impl {
    override val lifecycle = RxLifecycle()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.onCreate()
    }

    @CallSuper
    override fun onRestart() {
        super.onRestart()
        lifecycle.onRestart()
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
    override fun onDestroy() {
        lifecycle.onDestroy()
        super.onDestroy()
    }
}
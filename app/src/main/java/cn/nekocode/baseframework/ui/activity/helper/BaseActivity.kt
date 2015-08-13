package cn.nekocode.baseframework.ui.activity.helper

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import java.lang.ref.WeakReference
import java.util.ArrayList
import kotlin.properties.Delegates

open abstract class BaseActivity : AppCompatActivity() {
    companion object {
        private val handlers = ArrayList<MyHandler>()

        fun addHandler(handler: MyHandler) {
            handlers.add(handler)
        }

        fun deleteHandler(handler: MyHandler) {
            handlers.remove(handler)
        }

        fun removeAll() {
            handlers.clear()
        }

        fun broadcast(message: Message) {
            for (handler in handlers) {
                val msg = Message()
                msg.copyFrom(message)
                handler.sendMessage(msg)
            }
        }
    }

    protected val handler: MyHandler by Delegates.lazy {
        MyHandler(this)
    }

    public val _this: BaseActivity by Delegates.lazy {
        this!!
    }

    public fun sendMsg(message: Message) {
        val msg = Message()
        msg.copyFrom(message)
        handler.sendMessage(msg)
    }

    public fun sendMsgDelayed(message: Message, delayMillis: Int) {
        val msg = Message()
        msg.copyFrom(message)
        handler.sendMessageDelayed(msg, delayMillis.toLong())
    }

    public fun runDelayed(runnable: ()->Unit, delayMillis: Int) {
        val msg = Message()
        msg.what = -101
        msg.arg1 = -102
        msg.arg2 = -103
        msg.obj = runnable
        handler.sendMessageDelayed(msg, delayMillis.toLong())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addHandler(handler)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> this.finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun finish() {
        deleteHandler(handler)
        super.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    inner class MyHandler : Handler {
        private val mOuter: WeakReference<BaseActivity>

        constructor(activity: BaseActivity) {
            mOuter = WeakReference(activity)
        }

        override fun handleMessage(msg: Message) {
            val outer = mOuter.get() ?: return

            if (msg.what == -101 && msg.arg1 == -102 && msg.arg2 == -103) {
                (msg.obj as (()->Unit)).invoke()
                return
            }

            outer.handler(msg)
        }
    }

    public abstract fun handler(msg: Message)
}

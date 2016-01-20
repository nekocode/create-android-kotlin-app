package cn.nekocode.baseframework.ui.activity.component

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

import java.lang.ref.WeakReference

abstract class BaseActivity : AppCompatActivity() {
    companion object {
        private val handlers = arrayListOf<MyHandler>()

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

        class MyHandler : Handler {
            private val mOuter: WeakReference<BaseActivity>

            constructor(activity: BaseActivity) {
                mOuter = WeakReference(activity)
            }

            override fun handleMessage(msg: Message) {
                if(mOuter.get() == null) {
                    BaseActivity.deleteHandler(this)
                    return
                } else {

                    if (msg.what == -101 && msg.arg1 == -102 && msg.arg2 == -103) {
                        val runnable = (msg.obj as WeakReference<()->Unit>).get()
                        runnable?.invoke()
                        return
                    }

                    mOuter.get().handler(msg)
                }
            }
        }
    }

    protected val handler: MyHandler by lazy {
        MyHandler(this)
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
        msg.obj = WeakReference<()->Unit>(runnable)
        handler.sendMessageDelayed(msg, delayMillis.toLong())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addHandler(handler)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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

    public abstract fun handler(msg: Message)
}

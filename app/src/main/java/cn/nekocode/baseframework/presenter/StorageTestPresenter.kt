package cn.nekocode.baseframework.presenter

import cn.nekocode.baseframework.data.Model
import cn.nekocode.baseframework.presenter.helper.Presenter
import cn.nekocode.baseframework.data.local.Storage

/**
 * Created by nekocode on 2015/11/20.
 */
class StorageTestPresenter: Presenter {

    fun testStorage() {
        // storage testing
        Storage["test"] = Model.ParcelableTest(5, true)
        val model: Model.ParcelableTest? = Storage["test"]
    }

    override fun resume() {
    }

    override fun pause() {
    }

    override fun destory() {
        // clear storaged data
        Storage["test"] = null
    }
}
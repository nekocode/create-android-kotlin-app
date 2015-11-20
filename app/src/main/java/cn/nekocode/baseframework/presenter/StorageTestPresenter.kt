package cn.nekocode.baseframework.presenter

import cn.nekocode.baseframework.data.Model
import cn.nekocode.baseframework.presenter.helper.Presenter
import cn.nekocode.baseframework.utils.Storage

/**
 * Created by nekocode on 2015/11/20.
 */
class StorageTestPresenter: Presenter {

    fun testStorage() {
        // storage testing
        Storage["test"] = Model(5, 1)
        val model: Model? = Storage["test"]
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
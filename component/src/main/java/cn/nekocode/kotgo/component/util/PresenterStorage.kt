package cn.nekocode.kotgo.component.util

import cn.nekocode.kotgo.component.ui.Presenter
import java.util.*

/**
 * Created by nekocode on 2016/3/27 0027.
 */
enum class PresenterStorage {
    STORAGE;

    private val idToPresenter = HashMap<String, Presenter>()
    private val presenterToId = HashMap<Presenter, String>()
    operator fun contains(presenter: Presenter): Boolean = presenter in presenterToId

    fun add(presenter: Presenter): String {
        val id = "${presenter.javaClass.simpleName}_${UUID.randomUUID()}"
        idToPresenter[id] = presenter
        presenterToId[presenter] = id

        return id
    }

    fun get(id: String): Presenter? = idToPresenter[id]

    fun remove(presenter: Presenter) {
        idToPresenter.remove(presenterToId.remove(presenter))
    }

    fun getID(presenter: Presenter): String? = presenterToId[presenter]
}
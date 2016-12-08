package cn.nekocode.kotgo.sample.ui.main

import android.os.Bundle
import android.os.Parcelable
import cn.nekocode.kotgo.component.rx.RxBus
import cn.nekocode.kotgo.component.rx.bindLifecycle
import cn.nekocode.kotgo.component.rx.onUI
import cn.nekocode.kotgo.component.ui.KtPresenter
import cn.nekocode.kotgo.component.ui.KtFragmentActivity
import cn.nekocode.kotgo.sample.data.DO.Meizi
import cn.nekocode.kotgo.sample.data.DO.MeiziParcel
import cn.nekocode.kotgo.sample.data.repo.MeiziRepo
import cn.nekocode.kotgo.sample.event.LoadFinishedEvent
import cn.nekocode.kotgo.sample.ui.page2.Page2Fragment
import cn.nekocode.kotgo.sample.ui.page2.Page2Presenter
import rx.Observable
import java.util.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MainPresenter() : KtPresenter<Contract.View>(), Contract.Presenter {
    companion object {
        const val KEY_SAVED_MEIZIS = "KEY_SAVED_MEIZIS"

        fun push(act: KtFragmentActivity,
                 tag: String = MainFragment::class.java.canonicalName) {

            act.push(tag, MainFragment::class.java)
        }
    }

    val meiziList = ArrayList<Meizi>()
    val adapter = MeiziListAdapter(meiziList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.just(savedInstanceState).bindLifecycle(this)
                .flatMap {
                    if (it == null) {
                        MeiziRepo.getMeizis(50, 1)

                    } else {
                        Observable.just(
                                it.getParcelableArrayList<MeiziParcel>(
                                        KEY_SAVED_MEIZIS).map { it.data }
                        )
                    }
                }
                .flatMap {
                    Observable.fromCallable {
                        meiziList.clear()
                        meiziList.addAll(it)
                        RxBus.send(LoadFinishedEvent())
                    }
                }
                .onUI {
                    adapter.notifyDataSetChanged()
                }
    }

    override fun onViewCreated(view: Contract.View?, savedInstanceState: Bundle?) {
        with(adapter) {
            view?.setupAdapter(this)

            onMeiziItemClickListener = {
                Page2Presenter.push(activity as KtFragmentActivity, it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(
                KEY_SAVED_MEIZIS,
                meiziList.map(::MeiziParcel) as ArrayList<out Parcelable>)
    }
}
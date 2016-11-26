package cn.nekocode.kotgo.sample.ui.main

import android.os.Bundle
import android.os.Parcelable
import cn.nekocode.kotgo.component.rx.RxBus
import cn.nekocode.kotgo.component.rx.bindLifecycle
import cn.nekocode.kotgo.component.rx.onUI
import cn.nekocode.kotgo.component.ui.BasePresenter
import cn.nekocode.kotgo.component.ui.FragmentActivity
import cn.nekocode.kotgo.sample.data.DO.Meizi
import cn.nekocode.kotgo.sample.data.DO.MeiziParcel
import cn.nekocode.kotgo.sample.data.repo.MeiziRepo
import cn.nekocode.kotgo.sample.event.LoadFinishedEvent
import cn.nekocode.kotgo.sample.ui.page2.Page2Fragment
import rx.Observable
import java.util.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MeiziPresenter() : BasePresenter<Contract.View>(), Contract.Presenter {
    companion object {
        const val KEY_SAVED_MEIZIS = "KEY_SAVED_MEIZIS"
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
                Page2Fragment.push(activity as FragmentActivity, it)
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
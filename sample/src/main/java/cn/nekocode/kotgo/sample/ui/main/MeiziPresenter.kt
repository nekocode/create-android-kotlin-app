package cn.nekocode.kotgo.sample.ui.main

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import cn.nekocode.kotgo.component.rx.RxBus
import cn.nekocode.kotgo.component.rx.bindLifecycle
import cn.nekocode.kotgo.component.rx.onUI
import cn.nekocode.kotgo.component.ui.BasePresenter
import cn.nekocode.kotgo.sample.data.DO.Meizi
import cn.nekocode.kotgo.sample.data.DO.MeiziParcel
import cn.nekocode.kotgo.sample.data.repo.MeiziRepo
import cn.nekocode.kotgo.sample.event.LoadFinishedEvent
import cn.nekocode.kotgo.sample.ui.page2.Page2Fragment
import rx.Observable
import java.util.*

/**
 * Created by nekocode on 2015/11/20.
 */
class MeiziPresenter() : BasePresenter(), Contract.Presenter {
    companion object {
        const val KEY_PARCEL_MEIZIS = "meizis"
    }

    var view: Contract.View? = null
    val meiziList = ArrayList<Meizi>()
    val adapter = MeiziListAdapter(meiziList)

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        view = getParent() as Contract.View
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.just(savedInstanceState).bindLifecycle(this)
                .flatMap {
                    if (it == null) MeiziRepo.getMeizis(50, 1)
                    else Observable.just(
                            it.getParcelableArrayList<MeiziParcel>(KEY_PARCEL_MEIZIS).map { it.data }
                    )
                }
                .flatMap {
                    RxBus.send(LoadFinishedEvent())

                    meiziList.clear()
                    meiziList.addAll(it)
                    Observable.empty<Unit>()
                }
                .onUI {
                    adapter.notifyDataSetChanged()
                }
    }

    // You should not access the view on presenter's onCreate() because
    // when the screen rotates the presenter recreates more quickly than
    // the view. You should access the view on onVewCreated()
    override fun onVewCreated(savedInstanceState: Bundle?) {
        with(adapter) {
            view?.setupAdapter(this)

            onMeiziItemClickListener = {
                Page2Fragment.push(fragAct!!, it)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(
                KEY_PARCEL_MEIZIS,
                meiziList.map { MeiziParcel(it) } as ArrayList<out Parcelable>)
    }
}
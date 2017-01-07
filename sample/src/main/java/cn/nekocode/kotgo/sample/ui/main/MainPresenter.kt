package cn.nekocode.kotgo.sample.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import cn.nekocode.kotgo.component.rx.RxBus
import cn.nekocode.kotgo.component.ui.KtPresenter
import cn.nekocode.kotgo.sample.data.DO.Meizi
import cn.nekocode.kotgo.sample.data.DO.MeiziParcel
import cn.nekocode.kotgo.sample.data.repo.MeiziRepo
import cn.nekocode.kotgo.sample.event.LoadFinishedEvent
import cn.nekocode.kotgo.sample.ui.page2.Page2Presenter
import rx.Observable
import java.util.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MainPresenter() : KtPresenter<Contract.View>(), Contract.Presenter {
    companion object {
        const val KEY_SAVED_MEIZIS = "KEY_SAVED_MEIZIS"
        const val REQUEST_CODE_PAGE2 = 1
    }

    var view: Contract.View? = null
    val meiziList = ArrayList<Meizi>()
    val adapter = MeiziListAdapter(meiziList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.just(savedInstanceState)
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
                .safetySubscribe({
                    adapter.notifyDataSetChanged()
                }, {})
    }

    override fun onViewCreated(view: Contract.View?, savedInstanceState: Bundle?) {
        this.view = view

        adapter.let {
            view?.setupAdapter(it)

            it.onMeiziItemClickListener = { meizi ->
                Page2Presenter.pushForResult(this, REQUEST_CODE_PAGE2, meizi)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(
                KEY_SAVED_MEIZIS,
                meiziList.map(::MeiziParcel) as ArrayList<out Parcelable>)
    }

    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_PAGE2 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val rltMeizi = data.getParcelableExtra<MeiziParcel>(Page2Presenter.KEY_RLT_MEIZI).data
                    view?.toast("You clicked the photo: ${rltMeizi.id}")
                }
            }
        }
    }
}
package cn.nekocode.kotgo.sample.ui.main

import android.app.Activity
import android.os.Bundle
import cn.nekocode.kotgo.component.rx.RxBus
import cn.nekocode.kotgo.component.rx.bindLifecycle
import cn.nekocode.kotgo.component.rx.onUI
import cn.nekocode.kotgo.component.ui.BasePresenter
import cn.nekocode.kotgo.sample.data.dto.Meizi
import cn.nekocode.kotgo.sample.data.model.MeiziModel
import cn.nekocode.kotgo.sample.event.LoadFinishedEvent
import cn.nekocode.kotgo.sample.ui.page2.Page2Fragment
import java.util.*

/**
 * Created by nekocode on 2015/11/20.
 */
class MeiziPresenter(): BasePresenter(), Contract.Presenter {
    var view: Contract.View? = null
    val meiziList = ArrayList<Meizi>()
    val meiziListadapter = MeiziListAdapter(meiziList)

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        view = getParent() as Contract.View
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MeiziModel.getMeizis(50, 1).onUI().bindLifecycle(this).subscribe {
            meiziList.addAll(it)
            meiziListadapter.notifyDataSetChanged()

            RxBus.send(LoadFinishedEvent())
        }

        meiziListadapter.onMeiziItemClickListener = {
            Page2Fragment.push(fragAct!!, it)
        }
    }

    override fun getAdapter(): MeiziListAdapter = meiziListadapter
}
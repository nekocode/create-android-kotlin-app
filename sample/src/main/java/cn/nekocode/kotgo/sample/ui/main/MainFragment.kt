package cn.nekocode.kotgo.sample.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import butterknife.bindView
import cn.nekocode.kotgo.component.rx.RxBus
import cn.nekocode.kotgo.component.rx.bus
import cn.nekocode.kotgo.component.ui.BaseFragment
import cn.nekocode.kotgo.component.util.args
import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.data.dto.Meizi
import cn.nekocode.kotgo.sample.data.dto.MeiziParcel
import cn.nekocode.kotgo.sample.event.LoadFinishedEvent
import cn.nekocode.kotgo.sample.ui.page2.Page2Fragment
import org.jetbrains.anko.toast

class MainFragment: BaseFragment(), Contract.View {
    companion object {
        const val TAG = "MainFragment"
    }

    override val layoutId: Int = R.layout.fragment_main
    val toolbar: Toolbar by bindView(R.id.toolbar)
    val recyclerView: RecyclerView by bindView(R.id.recyclerView)
    val list: MutableList<Meizi> = arrayListOf()
    val adapter = MeiziListAdapter(list)

    val meiziPresenter: Contract.Presenter by lazy {
        bindPresenter<MeiziPresenter>()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = "Meizi List - Loading"

        bus {
            subscribe(LoadFinishedEvent::class.java) {
                toolbar.title = "Meizi List - Load finished"
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        adapter.onMeiziItemClickListener = {
            fragAct?.push(
                    Page2Fragment.TAG,
                    Page2Fragment::class.java,
                    args(Pair("meizi", MeiziParcel(it)))
            )
        }

        meiziPresenter
    }

    override fun refreshMeizis(meizis: List<Meizi>) {
        RxBus.send(LoadFinishedEvent())

        list.addAll(meizis)
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed(): Boolean {
        toast("Finished")
        return false
    }
}

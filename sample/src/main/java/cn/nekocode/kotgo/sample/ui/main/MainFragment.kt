package cn.nekocode.kotgo.sample.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import butterknife.bindView
import cn.nekocode.kotgo.component.rx.RxBus
import cn.nekocode.kotgo.component.ui.BaseFragment
import cn.nekocode.kotgo.component.ui.FragmentActivity
import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.event.LoadFinishedEvent
import org.jetbrains.anko.toast

class MainFragment: BaseFragment(), Contract.View {
    companion object {
        const val TAG = "MainFragment"

        fun push(fragmentActivity: FragmentActivity) {
            fragmentActivity.push(TAG, MainFragment::class.java)
        }
    }

    override val layoutId: Int = R.layout.fragment_main
    val toolbar: Toolbar by bindView(R.id.toolbar)
    val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    lateinit var meiziPresenter: Contract.Presenter

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 绑定 Presenter
        meiziPresenter = bindPresenter<MeiziPresenter>()

        toolbar.title = "Meizi List - Loading"
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = meiziPresenter.getAdapter()

        RxBus.subscribe(LoadFinishedEvent::class.java) {
            toolbar.title = "Meizi List - Load finished"
        }
    }

    override fun onBackPressed(): Boolean {
        toast("Finished")
        return false
    }
}

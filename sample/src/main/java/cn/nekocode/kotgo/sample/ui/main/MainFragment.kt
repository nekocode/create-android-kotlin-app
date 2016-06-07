package cn.nekocode.kotgo.sample.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import cn.nekocode.kotgo.component.rx.RxBus
import cn.nekocode.kotgo.component.ui.BaseFragment
import cn.nekocode.kotgo.component.ui.FragmentActivity
import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.event.LoadFinishedEvent
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.toast

class MainFragment : BaseFragment(), Contract.View {
    companion object {
        const val TAG = "MainFragment"

        fun push(fragmentActivity: FragmentActivity) {
            fragmentActivity.push(TAG, MainFragment::class.java)
        }
    }

    override val layoutId: Int = R.layout.fragment_main
    lateinit var meiziPresenter: Contract.Presenter

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // You should bind presenter on view's onViewCreated() for the
        // fragment manager can correctly find the presenter in cache
        meiziPresenter = bindPresenter<MeiziPresenter>()

        toolbar.title = "Meizi List - Loading"
        recyclerView.layoutManager = LinearLayoutManager(activity)

        RxBus.subscribe(LoadFinishedEvent::class.java) {
            toolbar.title = "Meizi List - Load finished"
        }
    }

    override fun setupAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

    override fun onBackPressed(): Boolean {
        toast("Finished")
        return false
    }
}

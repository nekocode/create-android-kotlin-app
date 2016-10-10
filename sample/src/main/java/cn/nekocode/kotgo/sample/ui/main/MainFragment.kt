package cn.nekocode.kotgo.sample.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nekocode.kotgo.component.rx.RxBus
import cn.nekocode.kotgo.component.ui.BaseFragment
import cn.nekocode.kotgo.component.ui.FragmentActivity
import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.event.LoadFinishedEvent
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.anko.toast

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MainFragment : BaseFragment(), Contract.View {
    companion object {
        fun push(act: FragmentActivity,
                 tag: String = MainFragment::class.java.canonicalName) {

            act.push(tag, MainFragment::class.java)
        }
    }

    lateinit var meiziPresenter: Contract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onCreatePresenter(presenterFactory: PresenterFactory) {
        meiziPresenter = presenterFactory.createOrGet(MeiziPresenter::class.java)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

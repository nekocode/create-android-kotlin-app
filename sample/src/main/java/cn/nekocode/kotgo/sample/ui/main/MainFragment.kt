package cn.nekocode.kotgo.sample.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nekocode.kotgo.component.rx.RxBus
import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.base.BaseFragment
import cn.nekocode.kotgo.sample.event.LoadFinishedEvent
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MainFragment : BaseFragment(), Contract.View {
    var presenter: Contract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RxBus.safetySubscribe(LoadFinishedEvent::class.java, {
            toolbar.title = "Meizi List - Load finished"
        }, {})
    }

    override fun onCreatePresenter(presenterFactory: PresenterFactory) {
        presenter = presenterFactory.createOrGet(MainPresenter::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = "Meizi List"
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun setupAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }

    override fun onBackPressed(): Boolean {
        toast("Finished")
        return false
    }
}

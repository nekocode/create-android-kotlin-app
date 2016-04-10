package cn.nekocode.kotgo.sample.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.bindView
import cn.nekocode.kotgo.component.rx.RxBus
import cn.nekocode.kotgo.component.ui.BaseFragment
import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.data.dto.Meizi
import cn.nekocode.kotgo.sample.ui.Navigator

class MainFragment: BaseFragment(), Contract.View {
    override val layoutId: Int = R.layout.fragment_main
    val recyclerView: RecyclerView by bindView(R.id.recyclerView)
    val list: MutableList<Meizi> = arrayListOf()
    val adapter = MeiziListAdapter(list)
    val meiziPresenter: Contract.Presenter by lazy {
        bindPresenter<MeiziPresenter>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        meiziPresenter
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        adapter.onMeiziItemClickListener = {
            Navigator.gotoPage2(activity, it)
        }
    }

    override fun refreshMeizis(meizis: List<Meizi>) {
        RxBus.send("Load finished.")

        list.addAll(meizis)
        adapter.notifyDataSetChanged()
    }
}

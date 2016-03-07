package cn.nekocode.kotgo.sample.presentation.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.bindView
import cn.nekocode.kotgo.component.presentation.BaseFragment
import cn.nekocode.kotgo.component.util.RxBus

import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.data.dto.Meizi
import cn.nekocode.kotgo.sample.presentation.gotoPage2

class MainFragment: BaseFragment(), MeiziPresenter.ViewInterface {
    override val layoutId: Int = R.layout.fragment_main
    val meiziPresenter = MeiziPresenter(this)

    val recyclerView: RecyclerView by bindView(R.id.recyclerView)
    val list: MutableList<Meizi> = arrayListOf()
    val adapter = MeiziListAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        meiziPresenter.getMeizis()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        adapter.onMeiziItemClickListener = {
            activity.gotoPage2(it)
        }
    }

    override fun refreshMeizis(meizis: List<Meizi>) {
        RxBus.send("Load finished.")

        list.addAll(meizis)
        adapter.notifyDataSetChanged()
    }
}

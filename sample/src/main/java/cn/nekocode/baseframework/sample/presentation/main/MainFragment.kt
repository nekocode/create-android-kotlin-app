package cn.nekocode.baseframework.sample.presentation.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.bindView
import cn.nekocode.baseframework.component.presentation.BaseFragment
import cn.nekocode.baseframework.component.presentation.Presenter
import cn.nekocode.baseframework.sample.App

import cn.nekocode.baseframework.sample.R
import cn.nekocode.baseframework.sample.data.dto.Meizi
import cn.nekocode.baseframework.sample.presentation.navigateToPage2

class MainFragment : BaseFragment(), MeiziPresenter.ViewInterface {
    override val layoutId: Int = R.layout.fragment_main
    val weatherPresenter = MeiziPresenter(this)
    override val presenters = arrayOf<Presenter>(weatherPresenter)

    val recyclerView: RecyclerView by bindView(R.id.recyclerView)
    val list: MutableList<Meizi> = arrayListOf()
    val adapter = MeiziListAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.bus.register(this)
        weatherPresenter.getMeizis()
    }

    override fun onDetach() {
        super.onDetach()
        App.bus.unregister(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        adapter.onMeiziItemClickListener = {
            activity.navigateToPage2(it)
        }
    }

    override fun refreshMeizis(meizis: List<Meizi>) {
        App.bus.post("Load finished.")

        list.addAll(meizis)
        adapter.notifyDataSetChanged()
    }
}

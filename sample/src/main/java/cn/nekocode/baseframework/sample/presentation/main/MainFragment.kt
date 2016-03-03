package cn.nekocode.baseframework.sample.presentation.main

import android.os.Bundle
import android.app.Fragment
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.bindView
import cn.nekocode.baseframework.component.util.showToast

import cn.nekocode.baseframework.sample.R
import cn.nekocode.baseframework.sample.data.dto.Meizi
import cn.nekocode.baseframework.sample.presentation.navigateToPage2

class MainFragment : Fragment(), MeiziPresenter.ViewInterface {
    val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    val list: MutableList<Meizi> = arrayListOf()
    val adapter = MeiziListAdapter(list)

    val weatherPresenter = MeiziPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherPresenter.onCreate(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        adapter.onMeiziItemClickListener = {
            activity.showToast("Clicked the ${it.id}.")
            activity.navigateToPage2("${it.id}")
        }
    }

    override fun refreshMeiziList(meizis: List<Meizi>) {
        list.addAll(meizis)
        adapter.notifyDataSetChanged()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        weatherPresenter.onAttach()
    }

    override fun onDetach() {
        super.onDetach()
        weatherPresenter.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

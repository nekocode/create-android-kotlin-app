package cn.nekocode.baseframework.ui.fragment

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import butterknife.bindView

import cn.nekocode.baseframework.R
import cn.nekocode.baseframework.model.Weather
import cn.nekocode.baseframework.rest.REST
import cn.nekocode.baseframework.ui.adapter.ResultAdapter
import cn.nekocode.baseframework.utils.ui
import org.jetbrains.anko.find
import org.jetbrains.anko.text
import kotlin.properties.Delegates

public class TestFragment : Fragment() {
    val list: MutableList<Weather> = linkedListOf()
    val adapter: ResultAdapter = ResultAdapter(list)

    var textView: TextView? = null
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = inflater.inflate(R.layout.fragment_test, container, false)

        textView = views.find(R.id.textView)
        recyclerView = views.find(R.id.recyclerView)

        setupViews()

        return views
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
    }

    fun setupViews() {
        textView?.text = ""

        REST.api.getWeather("101010100").ui().subscribe({
            textView?.text = it.getWeatherInfo().getCity()
        })

        for(i in 0..10) {
            val weather = Weather()
            list.add(weather)
        }

        adapter.onWeatherItemClickListener = {
            Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show()
        }

        recyclerView?.setLayoutManager(LinearLayoutManager(getActivity()))
        recyclerView?.setAdapter(adapter)
    }

    override fun onDetach() {
        super.onDetach()
    }
}

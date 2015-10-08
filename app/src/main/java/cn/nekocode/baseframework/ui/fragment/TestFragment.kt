package cn.nekocode.baseframework.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView

import cn.nekocode.baseframework.R
import cn.nekocode.baseframework.model.Weather
import cn.nekocode.baseframework.rest.REST
import cn.nekocode.baseframework.ui.adapter.ResultAdapter
import cn.nekocode.baseframework.utils.Cache
import cn.nekocode.baseframework.utils.onUI
import org.jetbrains.anko.find

public class TestFragment : Fragment() {
    val textView: TextView by bindView(R.id.textView)
    val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    val list: MutableList<Weather> = linkedListOf()
    val adapter: ResultAdapter = ResultAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView.text = ""

        REST.api.getWeather("101010100").onUI().subscribe({
            textView.text = it.weatherInfo.city
        })

        for(i in 0..10) {
            val weather = Weather()
            list.add(weather)
        }

        // cache test：can not use kotlin data class...
        Cache["hehe"] = String("heheheh".toByteArray())
        val strtest = Cache["hehe", String::class.java]

        adapter.onWeatherItemClickListener = {
            Toast.makeText(activity, "click", Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
    }
}

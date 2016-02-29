package cn.nekocode.baseframework.sample.presentation.main

import android.os.Bundle
import android.app.Fragment
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import cn.nekocode.baseframework.component.util.showToast

import cn.nekocode.baseframework.sample.R
import cn.nekocode.baseframework.sample.data.dto.Weather
import cn.nekocode.baseframework.sample.presentation.navigateToPage2

class TestFragment : Fragment(), WeatherPresenter.ViewInterface {
    val textView: TextView by bindView(R.id.textView)
    val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    val list: MutableList<Weather> = arrayListOf()
    val adapter: ResultAdapter = ResultAdapter(list)

    val weatherPresenter = WeatherPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherPresenter.onCreate(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for(i in 0..10) {
            val weather = Weather("城市")
            list.add(weather)
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        adapter.onWeatherItemClickListener = {
            activity.showToast("Clicked Item.")
            activity.navigateToPage2("Page 2")
        }
    }

    override fun setWeatherInfo(weather: Weather) {
        textView.text = weather.city
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

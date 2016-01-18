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
import butterknife.bindView

import cn.nekocode.baseframework.R
import cn.nekocode.baseframework.data.dto.Weather
import cn.nekocode.baseframework.presenter.WeatherPresenter
import cn.nekocode.baseframework.ui.adapter.ResultAdapter
import cn.nekocode.baseframework.utils.showToast

public class TestFragment : Fragment(), WeatherPresenter.ViewInterface {
    val textView: TextView by bindView(R.id.textView)
    val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    val list: MutableList<Weather> = linkedListOf()
    val adapter: ResultAdapter = ResultAdapter(list)

    val weatherPresenter = WeatherPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weatherPresenter.created()
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
            showToast("Clicked Item.")
        }
    }

    override fun setWeatherInfo(weather: Weather) {
        textView.text = weather.city
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
        weatherPresenter.detach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

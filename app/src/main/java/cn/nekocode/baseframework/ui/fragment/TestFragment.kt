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
import cn.nekocode.baseframework.data.Model
import cn.nekocode.baseframework.data.Weather
import cn.nekocode.baseframework.data.net.REST
import cn.nekocode.baseframework.presenter.StorageTestPresenter
import cn.nekocode.baseframework.presenter.WeatherPresenter
import cn.nekocode.baseframework.ui.adapter.ResultAdapter
import cn.nekocode.baseframework.data.local.Storage
import cn.nekocode.baseframework.utils.onUI
import cn.nekocode.baseframework.utils.showToast

public class TestFragment : Fragment(), WeatherPresenter.ViewInterface {
    val textView: TextView by bindView(R.id.textView)
    val recyclerView: RecyclerView by bindView(R.id.recyclerView)

    val list: MutableList<Weather> = linkedListOf()
    val adapter: ResultAdapter = ResultAdapter(list)

    val weatherPresenter = WeatherPresenter(this)
    val storageTestPresenter = StorageTestPresenter()

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
            val weather = Weather()
            list.add(weather)
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        adapter.onWeatherItemClickListener = {
            storageTestPresenter.testStorage()
            showToast("storage test")
        }
    }

    override fun setWeather(weather: Weather) {
        textView.text = weather.weatherInfo.city
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        storageTestPresenter.destory()
    }
}

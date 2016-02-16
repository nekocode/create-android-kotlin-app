package cn.nekocode.baseframework.presentation.main

import cn.nekocode.baseframework.data.dto.Weather
import cn.nekocode.baseframework.data.model.WeatherModel
import cn.nekocode.baseframework.presentation.Presenter

/**
 * Created by nekocode on 2015/11/20.
 */
class WeatherPresenter(val view: WeatherPresenter.ViewInterface): Presenter() {
    interface ViewInterface {
        fun setWeatherInfo(weather: Weather)
    }

    override fun onCreate() {
        super.onCreate()
        WeatherModel.getWeather("101010100").on(this).subscribe {
            view.setWeatherInfo(it)
        }
    }
}
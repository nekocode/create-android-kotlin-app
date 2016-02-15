package cn.nekocode.baseframework.presenter

import cn.nekocode.baseframework.data.dto.Weather
import cn.nekocode.baseframework.data.model.WeatherModel
import cn.nekocode.baseframework.presenter.component.Presenter
import cn.nekocode.baseframework.presenter.component.on

/**
 * Created by nekocode on 2015/11/20.
 */
class WeatherPresenter(val view: WeatherPresenter.ViewInterface): Presenter() {
    interface ViewInterface {
        fun setWeatherInfo(weather: Weather)
    }

    fun created() {
        WeatherModel.getWeather("101010100").on(this).subscribe {
            view.setWeatherInfo(it)
        }
    }
}
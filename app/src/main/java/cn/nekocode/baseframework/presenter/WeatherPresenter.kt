package cn.nekocode.baseframework.presenter

import cn.nekocode.baseframework.data.Weather
import cn.nekocode.baseframework.data.net.REST
import cn.nekocode.baseframework.presenter.helper.Presenter
import cn.nekocode.baseframework.utils.onUI

/**
 * Created by nekocode on 2015/11/20.
 */
class WeatherPresenter(val itf: WeatherPresenter.ViewInterface): Presenter {
    interface ViewInterface {
        fun setWeather(weather: Weather)
    }

    fun created() {
        REST.api.getWeather("101010100").onUI().subscribe({
            itf.setWeather(it)
        })
    }

    override fun resume() {
    }

    override fun pause() {
    }

    override fun destory() {
    }


}
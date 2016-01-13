package cn.nekocode.baseframework.data.modules

import cn.nekocode.baseframework.data.dto.Weather
import cn.nekocode.baseframework.data.services.Local
import cn.nekocode.baseframework.data.services.Net
import com.google.gson.annotations.SerializedName
import rx.Observable
import rx.schedulers.Schedulers

/**
 * Created by nekocode on 2016/1/13.
 */
object WeatherModule {
    data class WeatherWrapper(@SerializedName("weatherinfo") val weather: Weather)

    fun getWeather(cityId: String): Observable<Weather> {
        // Business Logic
        // TODO: Cache to local

        return Net.api.getWeather(cityId).subscribeOn(Schedulers.io()).map { it.weather }
    }
}
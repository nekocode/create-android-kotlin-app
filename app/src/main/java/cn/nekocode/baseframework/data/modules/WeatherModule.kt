package cn.nekocode.baseframework.data.modules

import cn.nekocode.baseframework.data.dto.Weather
import cn.nekocode.baseframework.data.services.Local
import cn.nekocode.baseframework.data.services.Net
import com.google.gson.annotations.SerializedName
import rx.Notification
import rx.Observable
import rx.schedulers.Schedulers

/**
 * Created by nekocode on 2016/1/13.
 */
object WeatherModule {
    data class WeatherWrapper(@SerializedName("weatherinfo") val weather: Weather)

    // Bussines Logic
    fun getWeather(cityId: String): Observable<Weather> =
            Net.api.getWeather(cityId).subscribeOn(Schedulers.io())
            .map { it.weather }
            .doOnEach {
                if(it.kind == Notification.Kind.OnNext) {
                    // Cache weather to local cache
                    Local["weather"] = it.value
                }
            }
            .onErrorResumeNext {
                // Fetech weather from local cache
                val weather: Weather? = Local["weather"]
                Observable.just(weather)
            }
}
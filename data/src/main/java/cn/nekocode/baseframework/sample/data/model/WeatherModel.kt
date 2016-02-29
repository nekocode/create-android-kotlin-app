package cn.nekocode.baseframework.sample.data.model

import cn.nekocode.baseframework.sample.data.dto.Weather
import cn.nekocode.baseframework.sample.data.service.Net
import com.google.gson.annotations.SerializedName
import com.orhanobut.hawk.Hawk
import rx.Notification
import rx.Observable
import rx.schedulers.Schedulers

/**
 * Created by nekocode on 2016/1/13.
 */
object WeatherModel {
    data class WeatherWrapper(@SerializedName("weatherinfo") val weather: Weather)

    // Bussines Logic
    fun getWeather(cityId: String): Observable<Weather> =
            Net.api.getWeather(cityId).subscribeOn(Schedulers.io())
            .map { it.weather }
            .doOnEach {
                if(it.kind == Notification.Kind.OnNext) {
                    // Cache weather to local cache
                    Hawk.put("weather", it.value)
                }
            }
            .onErrorResumeNext {
                // Fetech weather from local cache
                val weather: Weather? = Hawk.get("weather")
                Observable.just(weather)
            }
}
package cn.nekocode.baseframework.ui.activity

import android.os.Bundle
import android.os.Message
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast

import com.google.gson.Gson

import java.util.Objects

import cn.nekocode.baseframework.R
import cn.nekocode.baseframework.model.Weather
import cn.nekocode.baseframework.rest.API
import cn.nekocode.baseframework.rest.APIFactory
import cn.nekocode.baseframework.ui.activity.helper.BaseActivity
import rx.Observable
import rx.Observer
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.subjects.BehaviorSubject

import kotlinx.android.synthetic.activity_main.*;
import kotlin.properties.Delegates

public class MainActivity : BaseActivity<MainActivity>() {
    private val api: API
    private val gson: Gson

    init {
        api = APIFactory.getInstance()
        gson = APIFactory.getGson()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        refresh()
    }

    override fun handler(msg: Message) {
    }

    fun refresh(vararg objects: Any) {
        if (objects.size() == 0) {
            val observable = api.getWeather("101010100")
            observable.observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Weather> {
                override fun onCompleted() {
                }

                override fun onError(e: Throwable) {
                }

                override fun onNext(weather: Weather) {
                    refresh(weather)
                }
            })

        } else {
            val weather = objects[0] as Weather
            textView.setText(weather.getWeatherInfo().getCity())
        }
    }
}

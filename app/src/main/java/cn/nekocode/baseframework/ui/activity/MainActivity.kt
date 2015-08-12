package cn.nekocode.baseframework.ui.activity

import android.os.Bundle
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast

import com.google.gson.Gson

import cn.nekocode.baseframework.R
import cn.nekocode.baseframework.model.Weather
import cn.nekocode.baseframework.rest.API
import cn.nekocode.baseframework.rest.APIFactory
import cn.nekocode.baseframework.rest.APIs.APIs
import cn.nekocode.baseframework.ui.activity.helper.BaseActivity
import cn.nekocode.baseframework.ui.adapter.ResultAdapter
import rx.Observable
import rx.Observer
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.subjects.BehaviorSubject

import kotlinx.android.synthetic.activity_main.*;
import org.jetbrains.anko.text
import java.util.*
import kotlin.properties.Delegates

public class MainActivity : BaseActivity<MainActivity>() {

    val api: API = APIFactory.getInstance()
    val list: ArrayList<Weather> = ArrayList()
    val adapter: ResultAdapter = ResultAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        APIs.api.getWeather("101010100")
        setupViews()
    }

    fun setupViews() {
        val observable = api.getWeather("101010100")
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Weather> {
            override fun onCompleted() {
            }

            override fun onError(e: Throwable) {
            }

            override fun onNext(weather: Weather) {
                textView.text = weather.getWeatherInfo().getCity()
            }
        })

        for(i in 0..10) {
            val weather = Weather()
            list.add(weather)
        }

        adapter.onWeatherItemClickListener = {
            Toast.makeText(_this, "click", Toast.LENGTH_SHORT).show()
        }

        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.setAdapter(adapter)
    }

    override fun handler(msg: Message) {
    }
}

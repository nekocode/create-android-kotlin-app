package cn.nekocode.baseframework.ui.activity

import android.os.Bundle
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import butterknife.bindView

import com.google.gson.Gson

import cn.nekocode.baseframework.R
import cn.nekocode.baseframework.model.Weather
import cn.nekocode.baseframework.rest.REST
import cn.nekocode.baseframework.ui.activity.helper.BaseActivity
import cn.nekocode.baseframework.ui.adapter.ResultAdapter
import cn.nekocode.baseframework.utils.Events
import cn.nekocode.baseframework.utils.K
import cn.nekocode.baseframework.utils.ui
import rx.Observable
import rx.Observer
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.subjects.BehaviorSubject

import kotlinx.android.synthetic.activity_main.*;
import org.jetbrains.anko.find
import org.jetbrains.anko.text
import retrofit.RetrofitError
import retrofit.client.Response
import java.util.*
import kotlin.properties.Delegates

public class MainActivity : BaseActivity() {

    val list: MutableList<Weather> = linkedListOf()
    val adapter: ResultAdapter = ResultAdapter(list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
    }

    fun setupViews() {
        textView.text = ""

        REST.api.getWeather("101010100").ui().subscribe({
            textView.text = it.getWeatherInfo().getCity()
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



//public var rx.Observable<T>.inandroid: rx.Observable<T>
//    get() = observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
//    set(v) {}

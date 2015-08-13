package cn.nekocode.baseframework.ui.activity

import android.os.Bundle
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast

import cn.nekocode.baseframework.R
import cn.nekocode.baseframework.model.Weather
import cn.nekocode.baseframework.rest.REST
import cn.nekocode.baseframework.ui.activity.helper.BaseActivity
import cn.nekocode.baseframework.ui.adapter.ResultAdapter
import cn.nekocode.baseframework.utils.showToast
import cn.nekocode.baseframework.utils.ui
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import rx.subjects.BehaviorSubject

import kotlinx.android.synthetic.activity_main.*;
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

        runDelayed({
            showToast("呵呵哒")
        }, 3000)
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

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
        get() {
            return APIFactory.getInstance(this)
        }

    private val gson: Gson
        get() {
            return APIFactory.getGson()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BaseActivity>.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //        Config.Setting setting = Config.loadSetting();
        //        Config.saveSetting();

//        refresh()


        //        Observable.from(new String[]{"101010100"}).flatMap(new Func1<String, Observable<Weather>>() {
        //            @Override
        //            public Observable<Weather> call(String s) {
        //                return api.getWeather(s);
        //            }
        //        }).filter(new Func1<Weather, Boolean>() {
        //            @Override
        //            public Boolean call(Weather weather) {
        //                return weather.getWeatherInfo().getCity().equals("北京");
        //            }
        //        }).subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe(new Observer<Weather>() {
        //                    @Override
        //                    public void onCompleted() {
        //                    }
        //
        //                    @Override
        //                    public void onError(Throwable e) {
        //                    }
        //
        //                    @Override
        //                    public void onNext(Weather weather) {
        //                        textView.setText(weather.getWeatherInfo().getCity());
        //                    }
        //                });
    }

    override fun handler(msg: Message) {
    }

//    override fun refresh(vararg objects: Any) {
//        if (objects.size() == 0) {
//            val observable = api.getWeather("101010100")
//            observable.observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<Weather> {
//                override fun onCompleted() {
//                }
//
//                override fun onError(e: Throwable) {
//                }
//
//                override fun onNext(weather: Weather) {
//                    refresh(weather)
//                }
//            })
//
//        } else {
//            val weather = objects[0] as Weather
//            textView.setText(weather.getWeatherInfo().getCity())
//        }
//    }
}

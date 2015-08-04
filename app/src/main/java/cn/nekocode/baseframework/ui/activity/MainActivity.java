package cn.nekocode.baseframework.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.nekocode.baseframework.R;
import cn.nekocode.baseframework.model.Weather;
import cn.nekocode.baseframework.rest.API;
import cn.nekocode.baseframework.rest.APIFactory;
import cn.nekocode.baseframework.ui.activity.helper.BaseActivity;
import cn.nekocode.baseframework.ui.listener.Refreshable;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

public class MainActivity extends BaseActivity<MainActivity> implements Refreshable {
    API api;
    Gson gson;

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

//        Config.Setting setting = Config.loadSetting();
//        Config.saveSetting();

        api = APIFactory.getInstance(this);
        gson = APIFactory.getGson();

        refresh();

//        runDelayed(new Runnable() {
//            @Override
//            public void run() {
//                behaviorSubject.onNext("hehe");
//            }
//        }, 1000);


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

    @Override
    public void handler(Message msg) {
    }

    @Override
    public void refresh(Object... objects) {
        if(objects.length == 0) {
            Observable<Weather> observable = api.getWeather("101010100");
            observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Weather>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(Weather weather) {
                    refresh(weather);
                }
            });

        } else {
            Weather weather = (Weather) objects[0];
            textView.setText(weather.getWeatherInfo().getCity());
        }
    }
}

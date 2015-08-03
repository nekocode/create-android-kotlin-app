package cn.nekocode.baseframework.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.nekocode.baseframework.R;
import cn.nekocode.baseframework.model.Weather;
import cn.nekocode.baseframework.rest.API;
import cn.nekocode.baseframework.rest.APIFactory;
import cn.nekocode.baseframework.ui.activity.helper.BaseActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity<MainActivity> {
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

        Observable<Weather> observable = api.get();
        observable.observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Weather>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Weather result) {
                textView.setText(result.getWeatherInfo().getCity());
            }
        });

//        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                //订阅者回调 onNext 和 onCompleted
//                subscriber.onNext("one");
//                subscriber.onNext("two");
//                subscriber.onNext("three");
//                subscriber.onCompleted();
//            }
//        }).subscribeOn(Schedulers.io());
//
//        observable.observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Toast.makeText(_this, s, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        observable.observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Toast.makeText(_this, "2:" + s, Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handler(Message msg) {
    }
}

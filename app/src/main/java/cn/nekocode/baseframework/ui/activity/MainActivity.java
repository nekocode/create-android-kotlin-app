package cn.nekocode.baseframework.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.nekocode.baseframework.R;
import cn.nekocode.baseframework.bean.ResultBean;
import cn.nekocode.baseframework.rest.API;
import cn.nekocode.baseframework.rest.APIFactory;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends BaseActivity<MainActivity> {
    @InjectView(R.id.recyclerView)
    android.support.v7.widget.RecyclerView recyclerView;

    API api;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

//        Config.Setting setting = Config.loadSetting();
//        Config.saveSetting();

        api = APIFactory.getInstance(this);
        gson = APIFactory.getGson();
        api.sugList("utf-8", "电动", new Callback<ResultBean>() {
            @Override
            public void success(ResultBean resultBean, Response response) {
                String json = gson.toJson(resultBean);
                Toast.makeText(_this, json, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(_this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

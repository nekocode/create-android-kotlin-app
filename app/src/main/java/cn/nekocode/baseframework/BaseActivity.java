package cn.nekocode.baseframework;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity {
    private static ArrayList<Handler> handlers = new ArrayList<Handler>();
    protected MyHandler handler = new MyHandler(this);

    public BaseActivity _this;

    public static void addHandler(Handler handler) {
        handlers.add(handler);
    }

    public static void deleteHandler(Handler handler) {
        handlers.remove(handler);
    }

    public static void removeAll() {
        handlers.clear();
    }

    public static void broadcast(Message message) {
        for (Handler handler : handlers) {
            Message msg = new Message();
            msg.copyFrom(message);
            handler.sendMessage(msg);
        }
    }

    public void sendMsg(Message message) {
        Message msg = new Message();
        msg.copyFrom(message);
        handler.sendMessage(msg);
    }

    public void sendMsgDelayed(Message message, int delayMillis) {
        Message msg = new Message();
        msg.copyFrom(message);
        handler.sendMessageDelayed(msg, delayMillis);
    }

    public void runDelayed(Runnable runnable, int delayMillis) {
        Message msg = new Message();
        msg.what = -101;
        msg.arg1 = -102;
        msg.arg2 = -103;
        msg.obj = runnable;
        handler.sendMessageDelayed(msg, delayMillis);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _this = this;
        addHandler(handler);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        deleteHandler(handler);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    static class MyHandler extends Handler {
        private WeakReference<BaseActivity> mOuter;

        public MyHandler(BaseActivity activity) {
            mOuter = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final BaseActivity outer = mOuter.get();
            if (outer == null) {
                return;
            }

            if(msg.what==-101 && msg.arg1==-102 && msg.arg2==-103) {
                ((Runnable) msg.obj).run();
                return;
            }

            outer.handler(msg);
        }
    }

    public void handler(Message msg) {

    }
}

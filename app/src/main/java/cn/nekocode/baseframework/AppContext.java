package cn.nekocode.baseframework;

import android.app.Application;

/**
 * Created by nekocode on 2015/7/22.
 */
public class AppContext extends Application {
    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AppContext get() {
        return instance;
    }
}

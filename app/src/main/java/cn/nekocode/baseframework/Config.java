package cn.nekocode.baseframework;

import android.content.Context;

import cn.nekocode.baseframework.utils.K;

/**
 * Created by nekocode on 2015/7/23.
 */
public class Config {
    public static final String USER_AGENT = "TestApp";

    // OkHttp
    public static final String RESPONSE_CACHE_FILE = "reponse_cache";
    public static final int RESPONSE_CACHE_SIZE = 10 * 1024 * 1024;     // 10 MB
    public static final int HTTP_CONNECT_TIMEOUT = 10;
    public static final int HTTP_READ_TIMEOUT = 30;
    public static final int HTTP_WRITE_TIMEOUT = 10;

    // File Path
    public static final String FILE_SETTING = "setting.ser";


    // Setting
    private static Setting setting;
    static class Setting {
        boolean test = true;
    }

    public static Setting loadSetting() {
        if(setting == null)
            setting = K.load(FILE_SETTING, Setting.class);

        if(setting == null)
            setting = new Setting();

        return setting;
    }

    public static void saveSetting() {
        if(setting != null)
            K.save(FILE_SETTING, setting);
    }
}

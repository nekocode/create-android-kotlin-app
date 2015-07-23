package cn.nekocode.baseframework;

import cn.nekocode.baseframework.utils.C;

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
    public static class Setting {
        boolean test = true;
    }

    public static Setting loadSetting() {
        if(setting == null)
            setting = C.load(FILE_SETTING, Setting.class);

        if(setting == null)
            setting = new Setting();

        return setting;
    }

    public static void saveSetting() {
        if(setting != null)
            C.save(FILE_SETTING, setting);
    }

    public static void resetSetting() {
        C.delete(FILE_SETTING);
        setting = new Setting();
        saveSetting();
    }
}

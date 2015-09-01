package cn.nekocode.baseframework

import kotlin.platform.platformStatic

/**
 * Created by nekocode on 2015/7/23.
 */
public class Config {
    companion object {
        // app
        public platformStatic val APP_NAME: String = "nekoo"

        public platformStatic val USER_AGENT: String = "TestApp"

        // OkHttp
        public platformStatic val RESPONSE_CACHE_FILE: String = "reponse_cache"
        public platformStatic val RESPONSE_CACHE_SIZE: Int = 10 * 1024 * 1024     // 10 MB
        public platformStatic val HTTP_CONNECT_TIMEOUT: Int = 10
        public platformStatic val HTTP_READ_TIMEOUT: Int = 30
        public platformStatic val HTTP_WRITE_TIMEOUT: Int = 10
    }
}

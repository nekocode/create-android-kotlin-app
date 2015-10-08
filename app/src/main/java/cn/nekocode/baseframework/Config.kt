package cn.nekocode.baseframework

/**
 * Created by nekocode on 2015/7/23.
 */
public class Config {
    companion object {
        // app
        public @JvmStatic val APP_NAME: String = "nekoo"

        public @JvmStatic val USER_AGENT: String = "TestApp"

        // OkHttp
        public @JvmStatic val RESPONSE_CACHE_FILE: String = "reponse_cache"
        public @JvmStatic val RESPONSE_CACHE_SIZE: Int = 10 * 1024 * 1024     // 10 MB
        public @JvmStatic val HTTP_CONNECT_TIMEOUT: Int = 10
        public @JvmStatic val HTTP_READ_TIMEOUT: Int = 30
        public @JvmStatic val HTTP_WRITE_TIMEOUT: Int = 10
    }
}

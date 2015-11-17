package cn.nekocode.baseframework

/**
 * Created by nekocode on 2015/7/23.
 */
public class Config {
    companion object {
        // app
        val APP_NAME: String = "nekoo"

        val USER_AGENT: String = "TestApp"

        // OkHttp
        val RESPONSE_CACHE_FILE: String = "reponse_cache"
        val RESPONSE_CACHE_SIZE: Int = 10 * 1024 * 1024     // 10 MB
        val HTTP_CONNECT_TIMEOUT: Int = 10
        val HTTP_READ_TIMEOUT: Int = 30
        val HTTP_WRITE_TIMEOUT: Int = 10
    }
}

package cn.nekocode.kotgo.sample.data

import cn.nekocode.kotgo.sample.data.DO.Meizi
import cn.nekocode.kotgo.sample.data.service.GankService
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import rx.observers.TestSubscriber

/**
 * Created by nekocode on 16/6/14.
 */
class GankServiceTest {
    @Before
    fun setUp() {
        val client = OkHttpClient.Builder()
                .build()

        DataLayer.okHttpClient = client
    }

    @Test
    fun testGetMeizis() {
        val testSubscriber = TestSubscriber<List<Meizi>>()
        GankService.api.getMeizi(10, 1).map { it.results }.toBlocking().subscribe(testSubscriber)

        val meizis = testSubscriber.onNextEvents[0]
        Assert.assertTrue(meizis.size > 0)
    }
}
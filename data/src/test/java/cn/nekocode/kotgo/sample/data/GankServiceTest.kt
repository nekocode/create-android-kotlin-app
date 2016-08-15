package cn.nekocode.kotgo.sample.data

import cn.nekocode.kotgo.sample.data.DO.Meizi
import cn.nekocode.kotgo.sample.data.repo.MeiziRepo
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

import rx.observers.TestSubscriber

/**
 * Created by nekocode on 16/6/14.
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class GankServiceTest {

    @Before
    fun setUp() {
        DataLayer.init(RuntimeEnvironment.application, false)
    }

    @Test
    fun testGetMeizis() {
        val testSubscriber = TestSubscriber<List<Meizi>>()
        MeiziRepo.getMeizis(10, 1).toBlocking().subscribe(testSubscriber)

        val meizis = testSubscriber.onNextEvents[0]
        Assert.assertTrue(meizis.size > 0)
    }
}
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
 * @author nekocode (nekocode.cn@gmail.com)
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(23))
class GankServiceTest {

    @Before
    fun setUp() {
        DataLayer.init(RuntimeEnvironment.application)
    }

    @Test
    fun testGetMeizis() {
        val testSubscriber = TestSubscriber<List<Meizi>>()
        MeiziRepo.getMeizis(10, 1).toBlocking().subscribe(testSubscriber)

        val meizis = testSubscriber.onNextEvents[0]
        Assert.assertTrue(meizis.size > 0)
    }
}
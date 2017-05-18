package cn.nekocode.template.data

import cn.nekocode.template.data.service.GankService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class GankServiceTest {

    @Before
    fun setUp() {
        DataLayer.init(RuntimeEnvironment.application)
    }

    @Test
    fun testGetMeizis() {
        val meizis = GankService.getMeizis(10, 1).blockingFirst()
        print(meizis)
        Assert.assertTrue(meizis.isNotEmpty())
    }
}
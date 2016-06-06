package cn.nekocode.kotgo.sample

import android.app.Application
import cn.nekocode.kotgo.component.util.inject
import cn.nekocode.kotgo.sample.data.DataLayer
import cn.nekocode.kotgo.sample.injection.TestDep
import org.jetbrains.anko.toast

/**
 * Created by nekocode on 2015/7/22.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        DataLayer.hook(this)

        // Dependency Injection Test
        // Singleton
        val oldInt = TestDep.inject<Int>(1, 1)
        TestDep.int -= 2
        val newInt = TestDep.inject<Int>()
        toast("$oldInt -> $newInt")

        // Normal
        val oldStr = TestDep.inject<String>()
        TestDep.str = "new"
        val newStr = TestDep.inject<String>()
        toast("$oldStr -> $newStr")
    }

    companion object {
        lateinit var instance: App
    }

}

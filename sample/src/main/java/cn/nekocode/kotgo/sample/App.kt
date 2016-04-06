package cn.nekocode.kotgo.sample

import android.app.Application
import cn.nekocode.kotgo.component.util.Injector
import cn.nekocode.kotgo.component.util.inject
import cn.nekocode.kotgo.sample.data.DataLayer
import cn.nekocode.kotgo.sample.injection.TestDep
import org.jetbrains.anko.toast
import kotlin.properties.Delegates

/**
 * Created by nekocode on 2015/7/22.
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        DataLayer.hook(this)

        val dep = TestDep()
        Injector.bind(dep)

        // Singleton
        val oldInt = inject<Int>()
        dep.int ++
        val newInt = inject<Int>()
        toast("$oldInt -> $newInt")

        val oldStr = inject<String>()
        dep.str = "new"
        val newStr = inject<String>()
        toast("$oldStr -> $newStr")
    }

    companion object {
        var instance by Delegates.notNull<App>()
    }

}

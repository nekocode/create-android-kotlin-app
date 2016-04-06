package cn.nekocode.kotgo.sample.injection

import cn.nekocode.kotgo.component.util.Dependency

/**
 * Created by nekocode on 16/4/6.
 */
class TestDep : Dependency() {
    var int = 0
    var str = "old"

    override fun createDependencies() {
        bindSingleton<Int> {
            int
        }

        bind<String> {
            str
        }
    }
}
package cn.nekocode.kotgo.component.util

import java.util.*

/**
 * Created by nekocode on 16/4/6.
 */
open class Provider<T>(val creator: (args: Array<Any>) -> T) {
    open fun get(vararg args: Any): T {
        return creator(args as Array<Any>)
    }
}

class SingletonProvider<T>(creator: (args: Array<Any>) -> T) : Provider<T>(creator) {
    var instance: T? = null
    val lock = Any()

    override fun get(vararg args: Any): T {
        if (instance != null) {
            return instance!!

        } else {
            synchronized(lock) {
                if (instance == null)
                    instance = creator(args as Array<Any>)

                return instance!!
            }
        }
    }
}


abstract class Dependency {
    protected val dependencies = HashMap<Class<*>, Provider<Any>>()

    constructor() {
        createDependencies()
    }

    protected inline fun <reified T : Any> bind(noinline creator: (args: Array<Any>) -> T) {
        dependencies[T::class.java] = Provider(creator) as Provider<Any>
    }

    protected inline fun <reified T : Any> bindSingleton(noinline creator: (args: Array<Any>) -> T) {
        dependencies[T::class.java] = SingletonProvider(creator) as SingletonProvider<Any>
    }

    fun <T> provide(classType: Class<T>, vararg args: Any): T? {
        return dependencies[classType]?.get(*args) as T?
    }

    abstract fun createDependencies()
}

inline fun <reified T : Any> Dependency.injectNotNull(vararg args: Any): T = provide(T::class.java, *args)!!
inline fun <reified T : Any> Dependency.inject(vararg args: Any): T? = provide(T::class.java, *args)
package cn.nekocode.kotgo.component.util

import java.util.*

/**
 * Created by nekocode on 16/4/6.
 */
object Injector {
    var dependency: Dependency? = null

    fun bind(dependency: Dependency) {
        this.dependency = dependency
    }
}


open class Provider<T>(val creator: () -> T) {
    open fun get(): T {
        return creator()
    }
}

class SingletonProvider<T>(creator: () -> T): Provider<T>(creator) {
    var instance: T? = null
    val lock = Any()

    override fun get(): T {
        if(instance != null) {
            return instance!!

        } else {
            synchronized(lock) {
                if (instance == null)
                    instance = creator()

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

    protected inline fun <reified T: Any> bind(noinline creator: ()->T) {
        dependencies[T::class.java] = Provider(creator) as Provider<Any>
    }

    protected inline fun <reified T: Any> bindSingleton(noinline creator: ()->T) {
        dependencies[T::class.java] = SingletonProvider(creator) as SingletonProvider<Any>
    }

    fun <T> provide(classType: Class<T>): T? {
        return dependencies[classType]?.get() as T?
    }

    abstract fun createDependencies()
}

inline fun <reified T: Any> inject(): T? = Injector.dependency?.provide(T::class.java)
inline fun <reified T: Any> injectNotNull(): T = Injector.dependency!!.provide(T::class.java)!!
package cn.nekocode.gank.base

import androidx.fragment.app.Fragment
import com.uber.autodispose.*
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.*
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.parallel.ParallelFlowable

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
open class BaseFragment : Fragment() {
    private val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }

    /**
     * Modified from https://github.com/uber/AutoDispose
     */
    @CheckReturnValue
    fun <T> Flowable<T>.autoDisposable(): FlowableSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(scopeProvider))

    @CheckReturnValue
    fun <T> Observable<T>.autoDisposable(): ObservableSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(scopeProvider))

    @CheckReturnValue
    fun <T> Single<T>.autoDisposable(): SingleSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(scopeProvider))

    @CheckReturnValue
    fun <T> Maybe<T>.autoDisposable(): MaybeSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(scopeProvider))

    @CheckReturnValue
    fun Completable.autoDisposable(): CompletableSubscribeProxy =
        this.`as`(AutoDispose.autoDisposable<Any>(scopeProvider))

    @CheckReturnValue
    fun <T> ParallelFlowable<T>.autoDisposable(): ParallelFlowableSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(scopeProvider))
}
/*
 * Copyright 2019. nekocode (nekocode.cn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.nekocode.gank.base

import androidx.lifecycle.ViewModel
import com.uber.autodispose.*
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.*
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.parallel.ParallelFlowable
import io.reactivex.subjects.BehaviorSubject

/**
 * Copied and modified from: https://github.com/uber/AutoDispose
 *
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class BaseViewModel : ViewModel(), LifecycleScopeProvider<BaseViewModel.ViewModelEvent> {
    private val lifecycleEventsDelegate = lazy { BehaviorSubject.createDefault(ViewModelEvent.CREATED) }
    private val lifecycleEvents by lifecycleEventsDelegate

    enum class ViewModelEvent {
        CREATED, CLEARED
    }

    companion object {
        private val CORRESPONDING_EVENTS = CorrespondingEventsFunction<ViewModelEvent> { event ->
            when (event) {
                ViewModelEvent.CREATED -> ViewModelEvent.CLEARED
                else -> throw LifecycleEndedException(
                    "Cannot bind to ViewModel lifecycle after onCleared."
                )
            }
        }
    }

    override fun lifecycle(): Observable<ViewModelEvent> {
        return lifecycleEvents.hide()
    }

    override fun correspondingEvents(): CorrespondingEventsFunction<ViewModelEvent>? {
        return CORRESPONDING_EVENTS
    }

    override fun peekLifecycle(): ViewModelEvent? {
        return lifecycleEvents.value
    }

    override fun onCleared() {
        // Small optimization
        if (lifecycleEventsDelegate.isInitialized()) {
            lifecycleEvents.onNext(ViewModelEvent.CLEARED)
        }

        super.onCleared()
    }

    @CheckReturnValue
    fun <T> Flowable<T>.autoDisposable(): FlowableSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(this@BaseViewModel))

    @CheckReturnValue
    fun <T> Observable<T>.autoDisposable(): ObservableSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(this@BaseViewModel))

    @CheckReturnValue
    fun <T> Single<T>.autoDisposable(): SingleSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(this@BaseViewModel))

    @CheckReturnValue
    fun <T> Maybe<T>.autoDisposable(): MaybeSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(this@BaseViewModel))

    @CheckReturnValue
    fun Completable.autoDisposable(): CompletableSubscribeProxy =
        this.`as`(AutoDispose.autoDisposable<Any>(this@BaseViewModel))

    @CheckReturnValue
    fun <T> ParallelFlowable<T>.autoDisposable(): ParallelFlowableSubscribeProxy<T> =
        this.`as`(AutoDispose.autoDisposable(this@BaseViewModel))
}
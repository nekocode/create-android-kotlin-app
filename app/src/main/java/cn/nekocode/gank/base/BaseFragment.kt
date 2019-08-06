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

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import cn.nekocode.gank.GankApplication
import com.evernote.android.state.StateSaver
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StateSaver.restoreInstanceState(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        StateSaver.saveInstanceState(this, outState)
    }

    fun <T : ViewModel> getViewModel(key: String, modelClass: Class<T>) =
        getViewModelProvider(this).get(key, modelClass)

    fun <T : ViewModel> getViewModel(modelClass: Class<T>) =
        getViewModelProvider(this).get(modelClass)

    fun getViewModelProvider(fragment: Fragment) =
        ViewModelProviders.of(fragment, (requireActivity().application as GankApplication).viewModelFactory)

    fun getViewModelProvider(activity: FragmentActivity) =
        ViewModelProviders.of(activity, (requireActivity().application as GankApplication).viewModelFactory)

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
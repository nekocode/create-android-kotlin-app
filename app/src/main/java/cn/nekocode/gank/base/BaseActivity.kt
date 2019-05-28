/*
 * Copyright 2018. nekocode (nekocode.cn@gmail.com)
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

import android.annotation.SuppressLint
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.uber.autodispose.*
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.*
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.parallel.ParallelFlowable

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (NavUtils.getParentActivityName(this) != null) {
                    NavUtils.navigateUpFromSameTask(this)
                } else {
                    finish()
                }
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

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

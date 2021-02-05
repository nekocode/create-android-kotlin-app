/*
 * Copyright 2021. nekocode (nekocode.cn@gmail.com)
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

package cn.nekocode.caka.ui.home

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import autodispose2.autoDispose
import cn.nekocode.caka.backend.api.RepoApi
import cn.nekocode.caka.backend.model.Repository
import cn.nekocode.caka.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class HomeViewModel @Inject constructor(
    private val app: Application,
    private val repoApi: RepoApi
) : BaseViewModel() {
    val reposLiveData: LiveData<List<Repository>> = MutableLiveData()

    init {
        fetchRepos()
    }

    private fun fetchRepos() {
        reposLiveData as MutableLiveData
        repoApi.listRepos("nekocode")
            .retry()
            .map { repos ->
                repos.sortedByDescending { it.stargazersCount }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(this)
            .subscribe({ repos ->
                reposLiveData.postValue(repos)
            }, { err ->
                Toast.makeText(app, err.message, Toast.LENGTH_SHORT).show()
            })
    }
}
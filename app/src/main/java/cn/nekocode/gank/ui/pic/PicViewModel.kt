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

package cn.nekocode.gank.ui.pic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.nekocode.gank.backend.api.PicApi
import cn.nekocode.gank.backend.model.MeiziPic
import cn.nekocode.gank.base.BaseViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class PicViewModel @Inject constructor(
    private val picApi: PicApi
) : BaseViewModel() {
    val liveDataPic: LiveData<MeiziPic> = MutableLiveData()

    init {
        fetchOnePic()
    }

    fun fetchOnePic() {
        liveDataPic as MutableLiveData
        picApi.getMeiziPics(1, (1..10).random())
            .subscribeOn(Schedulers.io())
            .autoDisposable()
            .subscribe { response ->
                liveDataPic.postValue(response.results[0])
            }
    }
}
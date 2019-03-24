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

package cn.nekocode.gank.backend.api

import cn.nekocode.gank.backend.model.MeiziPic
import cn.nekocode.gank.backend.model.wrapper.GankIoResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface PicApi {

    @GET("{count}/{pageNum}")
    fun getMeiziPics(
        @Path("count") count: Int,
        @Path("pageNum") pageNum: Int
    ): Observable<GankIoResponse<MeiziPic>>
}

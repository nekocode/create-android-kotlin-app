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

package cn.nekocode.caka.backend.api

import cn.nekocode.caka.backend.model.Repository
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
interface RepoApi {

    @GET("/users/{username}/repos")
    fun listRepos(
        @Path("username") username: String,
        @Query("per_page") perPage: Int = 100
    ): Observable<ArrayList<Repository>>
}

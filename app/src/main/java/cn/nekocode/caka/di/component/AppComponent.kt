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

package cn.nekocode.caka.di.component

import cn.nekocode.caka.MyApplication
import cn.nekocode.caka.backend.di.module.ApiModule
import cn.nekocode.caka.di.AppScope
import cn.nekocode.caka.di.module.AppModule
import cn.nekocode.caka.di.module.FlipperModule
import cn.nekocode.caka.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@AppScope
@Singleton
@Component(
    modules = [
        AppModule::class,
        FlipperModule::class,
        ApiModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(app: MyApplication)

    fun newActivityComponentBuilder(): ActivityComponent.Builder

    fun newFragmentComponentBuilder(): FragmentComponent.Builder
}
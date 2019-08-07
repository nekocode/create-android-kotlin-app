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

package cn.nekocode.gank.di.component

import androidx.fragment.app.Fragment
import cn.nekocode.gank.di.FragmentScope
import cn.nekocode.gank.di.module.FragmentModule
import cn.nekocode.gank.ui.home.HomeFragment
import cn.nekocode.gank.ui.pic.PicFragment
import dagger.Subcomponent

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@FragmentScope
@Subcomponent(
    modules = [
        FragmentModule::class
    ]
)
interface FragmentComponent {

    @Subcomponent.Builder
    interface Builder {
        fun fragmentModule(module: FragmentModule): Builder
        fun build(): FragmentComponent
    }

    fun inject(fragment: HomeFragment)
    fun inject(fragment: PicFragment)
}

fun FragmentComponent.Builder.buildAndInject(fragment: Fragment) {
    val component = fragmentModule(FragmentModule(fragment)).build()

    when (fragment) {
        is HomeFragment -> component.inject(fragment)
        is PicFragment -> component.inject(fragment)
    }
}
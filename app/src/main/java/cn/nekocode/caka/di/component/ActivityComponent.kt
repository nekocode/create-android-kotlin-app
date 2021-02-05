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

import android.app.Activity
import cn.nekocode.caka.di.ActivityScope
import cn.nekocode.caka.di.module.ActivityModule
import cn.nekocode.caka.ui.MainActivity
import dagger.Subcomponent

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@ActivityScope
@Subcomponent(
    modules = [
        ActivityModule::class
    ]
)
interface ActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        fun activityModule(module: ActivityModule): Builder
        fun build(): ActivityComponent
    }

    fun inject(activity: MainActivity)
}

fun ActivityComponent.Builder.buildAndInject(activity: Activity) {
    val component = activityModule(ActivityModule(activity)).build()

    when (activity) {
        is MainActivity -> component.inject(activity)
    }
}
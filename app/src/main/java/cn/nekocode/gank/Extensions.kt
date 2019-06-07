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

package cn.nekocode.gank

import android.content.Context
import android.content.res.Resources
import androidx.fragment.app.Fragment

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
val Context.activityRouter get() = (this.applicationContext as GankApplication).activityRouter
val Fragment.activityRouter get() = this.requireActivity().activityRouter
val Context.apis get() = (this.applicationContext as GankApplication).apis
val Fragment.apis get() = this.requireActivity().apis

val Number.dp2px get() = (toInt() * Resources.getSystem().displayMetrics.density).toInt()
val Number.px2dp get() = (toInt() / Resources.getSystem().displayMetrics.density).toInt()

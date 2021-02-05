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

package cn.nekocode.caka.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.nekocode.caka.MyApplication
import cn.nekocode.caka.di.component.buildAndInject
import com.evernote.android.state.StateSaver

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).component
            .newActivityComponentBuilder().buildAndInject(this)
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
        ViewModelProvider(fragment, (application as MyApplication).viewModelFactory)

    fun getViewModelProvider(activity: FragmentActivity) =
        ViewModelProvider(activity, (application as MyApplication).viewModelFactory)
}

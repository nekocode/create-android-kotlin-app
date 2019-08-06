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

package cn.nekocode.gank.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.nekocode.gank.di.DaggerViewModelFactory
import cn.nekocode.gank.di.ViewModelKey
import cn.nekocode.gank.ui.pic.PicViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PicViewModel::class)
    abstract fun bindPicViewModel(viewModel: PicViewModel): ViewModel
}
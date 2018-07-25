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

package cn.nekocode.gank.broadcast

import android.content.Intent
import android.support.v4.content.LocalBroadcastManager

import cn.nekocode.meepo.CallMethod
import cn.nekocode.meepo.MeepoUtils
import cn.nekocode.meepo.adapter.CallAdapter
import cn.nekocode.meepo.config.Config

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class BroadcastCallAdapter : CallAdapter<Void> {

    override fun call(config: Config, method: CallMethod, args: Array<Any>): Void? {
        val context = MeepoUtils.getContextFromFirstParameter(args) ?: return null

        val intent = Intent()
        intent.action = method.action
        intent.putExtras(method.getBundle(args))
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)

        return null
    }
}

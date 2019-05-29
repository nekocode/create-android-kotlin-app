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

package cn.nekocode.gank.ui.home

import android.os.Bundle
import android.text.Html
import cn.nekocode.gank.R
import cn.nekocode.gank.activityRouter
import cn.nekocode.gank.base.BaseActivity
import cn.nekocode.gank.broadcast.BroadcastRouter
import cn.nekocode.gank.registerLocalReceiver
import com.evernote.android.state.StateSaver
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StateSaver.restoreInstanceState(this, savedInstanceState)

        setContentView(R.layout.activity_main)
        fetchBtn.text = Html.fromHtml(getString(R.string.fetch_pic))
        fetchBtn.setOnClickListener {
            activityRouter.gotoPic(this)
        }

        // Register local broadcast receiver
        registerLocalReceiver({ _, intent ->
            val action = (intent ?: return@registerLocalReceiver).action
                ?: return@registerLocalReceiver
            when (action) {
                BroadcastRouter.FETCH_SUC -> {
                    fetchBtn.text = getString(R.string.fetch_suc)
                    fetchBtn.isEnabled = false
                }
            }

        }, BroadcastRouter.FETCH_SUC)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        StateSaver.saveInstanceState(this, outState)
    }
}

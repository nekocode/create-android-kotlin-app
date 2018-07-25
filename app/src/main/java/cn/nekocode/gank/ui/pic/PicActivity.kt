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

package cn.nekocode.gank.ui.pic

import android.os.Bundle
import android.widget.Toast
import cn.nekocode.gank.R
import cn.nekocode.gank.backend.model.MeiziPic
import cn.nekocode.gank.base.BaseActivity
import cn.nekocode.gank.broadcastRouter
import cn.nekocode.gank.gankIoService
import com.evernote.android.state.State
import com.evernote.android.state.StateSaver
import com.squareup.picasso.Picasso
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pic.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class PicActivity : BaseActivity() {
    @State
    var pic: MeiziPic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StateSaver.restoreInstanceState(this, savedInstanceState)

        setContentView(R.layout.activity_pic)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.fetching_data)
        showPic()
    }

    private fun showPic() {
        if (pic != null) {
            Single.just(pic!!)
        } else {
            gankIoService().picApi.getMeiziPics(1, 0)
                    .subscribeOn(Schedulers.io())
                    .firstOrError()
                    .map { response ->
                        pic = response.results[0]
                        pic
                    }
        }
                .observeOn(AndroidSchedulers.mainThread())
                .to(AutoDispose.with(AndroidLifecycleScopeProvider.from(this)).forSingle())
                .subscribe({ pic ->
                    title = pic.id
                    Picasso.with(this).load(pic.url).centerCrop().fit().into(imageView)
                    broadcastRouter().tellFetchSuc(this)

                }, { _ ->
                    Toast.makeText(this, R.string.sth_went_wrong, Toast.LENGTH_SHORT).show()
                })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let { StateSaver.saveInstanceState(this, it) }
    }
}
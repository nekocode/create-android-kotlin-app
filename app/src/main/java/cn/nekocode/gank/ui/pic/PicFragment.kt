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

package cn.nekocode.gank.ui.pic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.nekocode.gank.R
import cn.nekocode.gank.apis
import cn.nekocode.gank.backend.model.MeiziPic
import cn.nekocode.gank.base.BaseFragment
import com.evernote.android.state.State
import com.squareup.picasso.Picasso
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pic.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class PicFragment : BaseFragment() {
    @State
    var pic: MeiziPic? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showPic()
    }

    private fun showPic() {
        if (pic != null) {
            Single.just(pic!!)
        } else {
            apis.pic.getMeiziPics(1, 0)
                .subscribeOn(Schedulers.io())
                .firstOrError()
                .map { response ->
                    pic = response.results[0]
                    pic
                }
        }
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable()
            .subscribe({ pic ->
                Picasso.get().load(pic.url).centerCrop().fit().into(imageView)

            }, {
                Toast.makeText(requireActivity(), R.string.sth_went_wrong, Toast.LENGTH_SHORT).show()
            })
    }
}

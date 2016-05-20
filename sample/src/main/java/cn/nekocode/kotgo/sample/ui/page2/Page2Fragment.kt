package cn.nekocode.kotgo.sample.ui.page2

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import butterknife.bindView
import cn.nekocode.kotgo.component.ui.BaseFragment
import cn.nekocode.kotgo.component.ui.FragmentActivity
import cn.nekocode.kotgo.component.util.args
import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.data.dto.Meizi
import cn.nekocode.kotgo.sample.data.dto.MeiziParcel
import com.squareup.picasso.Picasso

/**
 * Created by nekocode on 16/3/3.
 */
class Page2Fragment: BaseFragment(), Contract.View {
    companion object {
        const val TAG = "Page2Fragment"

        fun push(fragmentActivity: FragmentActivity, meizi: Meizi) {
            fragmentActivity.push(
                    TAG, Page2Fragment::class.java,
                    args(Pair("meizi", MeiziParcel(meizi)))
            )
        }
    }

    override val layoutId: Int = R.layout.fragment_page2
    val toolbar: Toolbar by bindView(R.id.toolbar)
    val imageView by bindView<ImageView>(R.id.imageView)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val meizi: Meizi = arguments.getParcelable<MeiziParcel>("meizi").data

        toolbar.title = "${meizi.id}"
        Picasso.with(activity).load(meizi.url).centerCrop().fit().into(imageView)
    }
}
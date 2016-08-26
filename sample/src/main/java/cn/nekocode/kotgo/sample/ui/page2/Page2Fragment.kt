package cn.nekocode.kotgo.sample.ui.page2

import android.os.Bundle
import android.view.View
import cn.nekocode.kotgo.component.ui.BaseFragment
import cn.nekocode.kotgo.component.ui.FragmentActivity
import cn.nekocode.kotgo.component.util.args
import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.data.DO.Meizi
import cn.nekocode.kotgo.sample.data.DO.MeiziParcel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_page2.*

/**
 * Created by nekocode on 16/3/3.
 */
class Page2Fragment : BaseFragment(), Contract.View {
    companion object {
        fun push(fragment: BaseFragment, meizi: Meizi,
                 tag: String = Page2Fragment::class.java.canonicalName) {

            fragment.push(tag, Page2Fragment::class.java, args(Pair("meizi", MeiziParcel(meizi))))
        }
    }

    override val layoutId: Int = R.layout.fragment_page2

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val meizi: Meizi = arguments.getParcelable<MeiziParcel>("meizi").data

        toolbar.title = "${meizi.id}"
        Picasso.with(activity).load(meizi.url).centerCrop().fit().into(imageView)
    }
}
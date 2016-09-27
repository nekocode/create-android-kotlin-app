package cn.nekocode.kotgo.sample.ui.page2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nekocode.kotgo.component.ui.BaseFragment
import cn.nekocode.kotgo.component.util.argsToBundle
import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.data.DO.Meizi
import cn.nekocode.kotgo.sample.data.DO.MeiziParcel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_page2.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class Page2Fragment : BaseFragment(), Contract.View {
    companion object {
        fun push(fragment: BaseFragment, meizi: Meizi,
                 tag: String = Page2Fragment::class.java.canonicalName) {

            fragment.push(tag, Page2Fragment::class.java, argsToBundle(Pair("meizi", MeiziParcel(meizi))))
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_page2, container, false)
    }

    override fun onCreatePresenter(presenterFactory: PresenterFactory) {
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val meizi = arguments.getParcelable<MeiziParcel>("meizi").data

        toolbar.title = meizi.id
        Picasso.with(activity).load(meizi.url).centerCrop().fit().into(imageView)
    }
}
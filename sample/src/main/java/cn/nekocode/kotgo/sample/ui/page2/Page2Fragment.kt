package cn.nekocode.kotgo.sample.ui.page2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nekocode.kotgo.component.ui.BaseFragment
import cn.nekocode.kotgo.component.ui.FragmentActivity
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
        const val KEY_ARG_MEIZI = "KEY_ARG_MEIZI"

        fun push(act: FragmentActivity, meizi: Meizi,
                 tag: String = Page2Fragment::class.java.canonicalName) {

            val args = Bundle()
            args.putParcelable(KEY_ARG_MEIZI, MeiziParcel(meizi))
            act.push(tag, Page2Fragment::class.java, args)
        }
    }

    override fun onCreatePresenter(presenterFactory: PresenterFactory) {
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_page2, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val meizi = arguments.getParcelable<MeiziParcel>(KEY_ARG_MEIZI).data

        toolbar.title = meizi.id
        Picasso.with(activity).load(meizi.url).centerCrop().fit().into(imageView)
    }
}
package cn.nekocode.kotgo.sample.ui.page2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.nekocode.kotgo.component.ui.KtFragment
import cn.nekocode.kotgo.sample.R
import cn.nekocode.kotgo.sample.base.BaseFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_page2.*
import org.jetbrains.anko.onClick

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class Page2Fragment : BaseFragment(), Contract.View {
    var presenter: Contract.Presenter? = null

    override fun onCreatePresenter(presenterFactory: PresenterFactory) {
        presenter = presenterFactory.createOrGet(Page2Presenter::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_page2, container, false)
    }

    override fun showMeizi(meiziVO: MeiziVO) {
        toolbar.title = meiziVO.title
        Picasso.with(activity).load(meiziVO.picUrl).centerCrop().fit().into(imageView)

        imageView.onClick {
            presenter?.onImageClick(meiziVO)
        }
    }
}
package cn.nekocode.template.screen.page2

import android.os.Bundle
import cn.nekocode.template.R
import cn.nekocode.template.base.BaseActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_page2.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class Page2Activity : BaseActivity(), Contract.View {
    var presenter: Contract.Presenter? = null


    override fun onCreatePresenter(presenterFactory: PresenterFactory) {
        presenter = presenterFactory.createOrGet(Page2Presenter::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page2)

        imageView.setOnClickListener {
            presenter?.onImageClick()
        }
    }

    override fun setMeizi(vo: MeiziVO) {
        title = vo.title
        Picasso.with(this).load(vo.url).centerCrop().fit().into(imageView)
    }
}
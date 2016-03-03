package cn.nekocode.baseframework.sample.presentation.page2

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import butterknife.bindView
import cn.nekocode.baseframework.component.presentation.BaseFragment
import cn.nekocode.baseframework.sample.R
import cn.nekocode.baseframework.sample.data.dto.Meizi
import com.squareup.picasso.Picasso

/**
 * Created by nekocode on 16/3/3.
 */
class Page2Fragment: BaseFragment() {
    override val layoutId: Int = R.layout.fragment_page2
    val imageView by bindView<ImageView>(R.id.imageView)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val meizi: Meizi = arguments.getParcelable("meizi")
        Picasso.with(activity).load(meizi.url).centerCrop().fit().into(imageView)
    }
}
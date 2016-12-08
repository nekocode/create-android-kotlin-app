package cn.nekocode.kotgo.sample.base

import android.widget.Toast
import cn.nekocode.kotgo.component.ui.KtFragment

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class BaseFragment : KtFragment(), BaseView {

    override fun toast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

}
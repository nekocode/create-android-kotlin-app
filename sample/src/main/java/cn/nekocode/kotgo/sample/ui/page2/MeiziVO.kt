package cn.nekocode.kotgo.sample.ui.page2

import cn.nekocode.kotgo.sample.base.VO
import cn.nekocode.kotgo.sample.data.base.DO

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MeiziVO(
        val title: String,
        val picUrl: String,

        override val data: DO? = null
) : VO
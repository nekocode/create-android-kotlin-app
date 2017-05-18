package cn.nekocode.template.page.page2

import cn.nekocode.template.data.DO.Meizi

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class MeiziVO(
        val url: String,
        val title: String,
        val DO: Any
) {
    companion object {
        fun fromMeizi(meizi: Meizi): MeiziVO {
            return MeiziVO(meizi.url, meizi.id, meizi)
        }
    }
}
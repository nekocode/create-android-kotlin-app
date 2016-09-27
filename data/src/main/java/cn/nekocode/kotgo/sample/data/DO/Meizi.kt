package cn.nekocode.kotgo.sample.data.DO

import com.google.gson.annotations.SerializedName
import nz.bradcampbell.paperparcel.PaperParcel

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@PaperParcel
class Meizi(
        @SerializedName("_id") val id: String,
        val type: String,
        val url: String,
        val who: String
)
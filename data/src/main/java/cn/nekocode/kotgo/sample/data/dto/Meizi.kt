package cn.nekocode.kotgo.sample.data.dto

import com.google.gson.annotations.SerializedName
import nz.bradcampbell.paperparcel.PaperParcel

/**
 * Created by nekocode on 16/3/3.
 */
@PaperParcel
data class Meizi(
        @SerializedName("_id") val id: String,
        val type: String,
        val url: String,
        val who: String
)
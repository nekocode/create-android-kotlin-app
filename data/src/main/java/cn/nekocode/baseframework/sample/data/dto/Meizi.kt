package cn.nekocode.baseframework.sample.data.dto

import java.util.*
import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

/**
 * Created by nekocode on 16/3/3.
 */
data class Meizi(
        @SerializedName("_id") val id: String,
        val type: String,
        val url: String,
        val who: String)
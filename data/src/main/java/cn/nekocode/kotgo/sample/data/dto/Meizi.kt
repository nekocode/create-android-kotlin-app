package cn.nekocode.kotgo.sample.data.dto

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
        val who: String
): Parcelable {
    // You can automatically grenerate the following code by using this plugin:
    // https://github.com/nekocode/android-parcelable-intellij-plugin-kotlin

    constructor(source: Parcel): this(source.readString(), source.readString(), source.readString(), source.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(type)
        dest?.writeString(url)
        dest?.writeString(who)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Meizi> = object : Parcelable.Creator<Meizi> {
            override fun createFromParcel(source: Parcel): Meizi {
                return Meizi(source)
            }

            override fun newArray(size: Int): Array<Meizi?> {
                return arrayOfNulls(size)
            }
        }
    }
}
package cn.nekocode.template.data.DO

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import paperparcel.PaperParcel

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
@PaperParcel
data class Meizi(
        @SerializedName("_id")
        override val id: String,
        val type: String,
        val url: String,
        val who: String
) : WithId, Parcelable {

    companion object {
        @JvmField val CREATOR = PaperParcelMeizi.CREATOR
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        PaperParcelMeizi.writeToParcel(this, dest, flags)
    }
}
package cn.nekocode.kotgo.component.ui

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class RequestInfo(var requestCode: Int, var resultCode: Int = 0, var resultData: Intent? = null) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.readParcelable<Intent?>(Intent::class.java.classLoader)
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(requestCode)
        dest?.writeInt(resultCode)
        dest?.writeParcelable(resultData, 0)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<RequestInfo> = object : Parcelable.Creator<RequestInfo> {
            override fun createFromParcel(source: Parcel): RequestInfo {
                return RequestInfo(source)
            }

            override fun newArray(size: Int): Array<RequestInfo?> {
                return arrayOfNulls(size)
            }
        }
    }
}
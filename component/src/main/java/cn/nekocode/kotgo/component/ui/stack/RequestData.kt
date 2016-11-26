package cn.nekocode.kotgo.component.ui.stack

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
class RequestData(var requestCode: Int, var resultCode: Int = 0, var resultData: Intent? = null) : Parcelable {
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
        @JvmField final val CREATOR: Parcelable.Creator<RequestData> = object : Parcelable.Creator<RequestData> {
            override fun createFromParcel(source: Parcel): RequestData {
                return RequestData(source)
            }

            override fun newArray(size: Int): Array<RequestData?> {
                return arrayOfNulls(size)
            }
        }
    }
}
package cn.nekocode.kotgo.component.ui

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
data class RequestFragmentsRecord(var reqCount: Int, val tags: ArrayList<String>, var requestCode: Int) : Parcelable {
    constructor(source: Parcel) : this(source.readInt(), source.createStringArrayList(), source.readInt())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(reqCount)
        dest?.writeStringList(tags)
        dest?.writeInt(requestCode)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<RequestFragmentsRecord> = object : Parcelable.Creator<RequestFragmentsRecord> {
            override fun createFromParcel(source: Parcel): RequestFragmentsRecord {
                return RequestFragmentsRecord(source)
            }

            override fun newArray(size: Int): Array<RequestFragmentsRecord?> {
                return arrayOfNulls(size)
            }
        }
    }
}
package cn.nekocode.baseframework.data.dto

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by nekocode on 2016/1/13.
 */
data class Weather(val city: String) : Parcelable {
    constructor(source: Parcel): this(source.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(this.city)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Weather> = object : Parcelable.Creator<Weather> {
            override fun createFromParcel(source: Parcel): Weather {
                return Weather(source)
            }

            override fun newArray(size: Int): Array<Weather?> {
                return arrayOfNulls(size)
            }
        }
    }
}
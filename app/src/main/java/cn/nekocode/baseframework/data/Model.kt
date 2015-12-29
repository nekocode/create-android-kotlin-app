package cn.nekocode.baseframework.data

import com.google.gson.annotations.SerializedName
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by nekocode on 2015/11/2.
 */

object Model {
    data class WeatherInfo(val city: String)
    data class Weather(@SerializedName("weatherinfo") val weatherInfo: WeatherInfo)

    data class ParcelableTest(var test1: Int, var test2: Boolean) : Parcelable {
        constructor(source: Parcel): this(source.readInt(), 1.toByte().equals(source.readByte()))

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeInt(this.test1)
            dest?.writeByte((if (test2) 1 else 0).toByte())
        }

        companion object {
            @JvmField final val CREATOR: Parcelable.Creator<ParcelableTest> = object : Parcelable.Creator<ParcelableTest> {
                override fun createFromParcel(source: Parcel): ParcelableTest {
                    return ParcelableTest(source)
                }

                override fun newArray(size: Int): Array<ParcelableTest?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}


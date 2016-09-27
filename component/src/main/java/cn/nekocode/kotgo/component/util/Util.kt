package cn.nekocode.kotgo.component.util

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
fun argsToBundle(vararg params: Pair<String, Any>): Bundle {
    val arguments = Bundle()
    params.forEach {
        val value = it.second
        when (value) {
            is Int -> arguments.putInt(it.first, value)
            is Long -> arguments.putLong(it.first, value)
            is CharSequence -> arguments.putCharSequence(it.first, value)
            is String -> arguments.putString(it.first, value)
            is Float -> arguments.putFloat(it.first, value)
            is Double -> arguments.putDouble(it.first, value)
            is Char -> arguments.putChar(it.first, value)
            is Short -> arguments.putShort(it.first, value)
            is Boolean -> arguments.putBoolean(it.first, value)
            is Serializable -> arguments.putSerializable(it.first, value)
            is Bundle -> arguments.putBundle(it.first, value)
            is Parcelable -> arguments.putParcelable(it.first, value)
            is Array<*> -> when {
                value.isArrayOf<CharSequence>() -> arguments.putCharSequenceArray(it.first, value as Array<out CharSequence>)
                value.isArrayOf<String>() -> arguments.putStringArray(it.first, value as Array<out String>)
                value.isArrayOf<Parcelable>() -> arguments.putParcelableArray(it.first, value as Array<out Parcelable>)
                else -> throw Throwable("arguments extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            is IntArray -> arguments.putIntArray(it.first, value)
            is LongArray -> arguments.putLongArray(it.first, value)
            is FloatArray -> arguments.putFloatArray(it.first, value)
            is DoubleArray -> arguments.putDoubleArray(it.first, value)
            is CharArray -> arguments.putCharArray(it.first, value)
            is ShortArray -> arguments.putShortArray(it.first, value)
            is BooleanArray -> arguments.putBooleanArray(it.first, value)
            else -> throw Throwable("arguments extra ${it.first} has wrong type ${value.javaClass.name}")
        }
    }

    return arguments
}
package cn.nekocode.baseframework.utils

import android.content.Context
import cn.nekocode.baseframework.App
import cn.nekocode.baseframework.model.Weather
import cn.nekocode.baseframework.rest.REST
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import rx.lang.kotlin.toSingletonObservable
import java.io.File
import kotlin.properties.Delegates

/**
 * Created by nekocode on 2015/8/13.
 */
public class K {
    companion object {
        private val kryo: Kryo = Kryo()
        val map: MutableMap<Any, Any> by Delegates.lazy {
            hashMapOf<Any, Any>()
        }

        fun write(fileName: String, obj: Any) {
            try {
                val fo = App.instance.openFileOutput(fileName, Context.MODE_PRIVATE)
                val output = Output(fo)

                kryo.writeObject(output, obj)
                output.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun <E> read(fileName: String, c: Class<E>): E? {
            var rlt: E? = null
            try {
                val fi = App.instance.openFileInput(fileName)
                val input = Input(fi)
                rlt = kryo.readObject(input, c)
                input.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return rlt
        }

        fun rm(fileName: String) {
            App.instance.deleteFile(fileName)
        }

        fun rmAll() {
            val toDelete = File("/data/data/" + App.instance.getPackageName() + "/files")

            if (toDelete != null && toDelete.exists() && toDelete.isDirectory()) {
                for (item in toDelete.listFiles()!!) {
                    item.delete()
                }
            }
        }
    }
}

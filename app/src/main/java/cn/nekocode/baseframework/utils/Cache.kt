package cn.nekocode.baseframework.utils

import android.content.Context
import cn.nekocode.baseframework.App
import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import java.io.File

/**
 * Created by nekocode on 2015/8/13.
 */
public class Cache {
    companion object {
        private val kryo: Kryo = Kryo()

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

        fun <T> read(fileName: String, c: Class<T>): T? {
            var rlt: T? = null
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

        fun clear() {
            val toDelete = File("/data/data/" + App.instance.packageName + "/files")

            if (toDelete.exists() && toDelete.isDirectory) {
                for (item in toDelete.listFiles()!!) {
                    item.delete()
                }
            }
        }

        operator fun set(fileName: String, obj: Any?){
            if(obj != null) {
                write(fileName, obj)
            } else {
                rm(fileName)
            }
        }

        operator fun <T> get(fileName: String, c: Class<T>): T? = read(fileName, c)
    }
}
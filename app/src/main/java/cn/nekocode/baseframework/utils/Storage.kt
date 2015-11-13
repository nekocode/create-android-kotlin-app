package cn.nekocode.baseframework.utils

import android.content.Context
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import com.orhanobut.hawk.LogLevel

/**
 * Created by nekocode on 2015/11/13.
 * Simple file-object storage
 */
public class Storage {
    companion object {
        fun init(context: Context) {
            Hawk.init(context)
                    .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                    .setStorage(HawkBuilder.newSqliteStorage(context))
                    .setLogLevel(LogLevel.FULL)
                    .build();
        }

        operator fun set(key: String, obj: Any?){
            if(obj != null) {
                Hawk.put(key, obj)
            } else {
                Hawk.remove(key)
            }
        }

        operator fun <T> get(key: String): T? = Hawk.get(key)
    }
}
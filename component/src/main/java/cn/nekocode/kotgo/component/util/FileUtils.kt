package cn.nekocode.kotgo.component.util

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import android.util.Log

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by nekocode on 2015/4/23 0023.
 */
object FileUtils {
    fun isExternalStorageMounted(): Boolean {
        val canRead = Environment.getExternalStorageDirectory().canRead()
        val onlyRead = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY
        val unMounted = Environment.getExternalStorageState() == Environment.MEDIA_UNMOUNTED

        return canRead && !onlyRead && !unMounted
    }

    fun getExternalAppDataPath(context: Context): String {
        return Environment.getExternalStorageDirectory().absolutePath + "/Android/data/" +
                context.packageName + File.separator
    }

    fun getExternalAppCachePath(context: Context): String {
        return getExternalAppDataPath(context) + "cache" + File.separator
    }

    fun createAppDirs(context: Context) {
        if (!isExternalStorageMounted()) {
            Log.e("createAppRootDirs", "sdcard unavailiable")
            return
        }

        var dir = File(getExternalAppDataPath(context))
        if (!dir.exists()) {
            dir.mkdirs()
        }

        dir = File(getExternalAppCachePath(context))
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }

    fun createNewFileInSDCard(absolutePath: String): File? {
        if (!isExternalStorageMounted()) return null
        if (TextUtils.isEmpty(absolutePath)) return null

        val file = File(absolutePath)
        if (file.exists()) {
            return file
        } else {
            val dir = file.parentFile
            if (!dir.exists()) {
                dir.mkdirs()
            }

            try {
                if (file.createNewFile()) {
                    return file
                }
            } catch (e: IOException) {
                Log.e("createNewFileInSDCard", e.message)
                return null
            }
        }
        return null

    }

    fun rmDirectory(path: File): Boolean {
        if (path.exists()) {
            val files = path.listFiles() ?: return true
            for (i in files.indices) {
                if (files[i].isDirectory) {
                    rmDirectory(files[i])
                } else {
                    files[i].delete()
                }
            }
        }
        return (path.delete())
    }

    private fun copyFile(sourceFile: File, targetFile: File) {
        var inBuff: BufferedInputStream? = null
        var outBuff: BufferedOutputStream? = null

        try {
            inBuff = BufferedInputStream(FileInputStream(sourceFile))
            outBuff = BufferedOutputStream(FileOutputStream(targetFile))

            val b = ByteArray(1024 * 5)
            var len: Int

            while (true) {
                len = inBuff.read(b).toInt()
                if (len == -1)
                    break

                outBuff.write(b, 0, len)
            }

            outBuff.flush()
        } finally {
            inBuff?.close()
            outBuff?.close()
        }
    }

}

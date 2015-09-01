package cn.nekocode.toast.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import cn.nekocode.baseframework.Config

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by nekocode on 2015/4/23 0023.
 */
public class FileUtils {
    companion object {
        private val APP_ROOT = Config.APP_NAME

        public fun isExternalStorageMounted(): Boolean {
            val canRead = Environment.getExternalStorageDirectory().canRead()
            val onlyRead = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY
            val unMounted = Environment.getExternalStorageState() == Environment.MEDIA_UNMOUNTED

            return canRead && !onlyRead && !unMounted
        }

        public fun getAppRootPath(): String {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_ROOT + File.separator
        }

        public fun getAppCachePath(): String {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_ROOT + File.separator + "cache" + File.separator
        }

        public fun createAppRootDirs() {
            if (!isExternalStorageMounted()) {
                Log.e("createAppRootDirs", "sdcard unavailiable")
            }

            var dir = File(getAppRootPath())
            if (!dir.exists()) {
                dir.mkdirs()
            }

            dir = File(getAppCachePath())
            if (!dir.exists()) {
                dir.mkdirs()
            }
        }

        public fun saveToAppDir(context: Context, pathOfFileToSave: String): Boolean {
            if (!isExternalStorageMounted()) {
                return false
            }

            val file = File(pathOfFileToSave)
            val name = file.getName()
            val newPath = getAppRootPath() + name
            try {
                createNewFileInSDCard(context, newPath)
                copyFile(file, File(newPath))
                return true
            } catch (e: IOException) {
                return false
            }


        }

        public fun createNewFileInSDCard(context: Context, absolutePath: String): File? {
            isExternalStorageMounted() ?: return null
            !TextUtils.isEmpty(absolutePath) ?: return null

            val file = File(absolutePath)
            if (file.exists()) {
                return file
            } else {
                val dir = file.getParentFile()
                if (!dir.exists()) {
                    dir.mkdirs()
                }

                try {
                    if (file.createNewFile()) {
                        return file
                    }
                } catch (e: IOException) {
                    Log.e("createNewFileInSDCard", e.getMessage())
                    return null
                }
            }
            return null

        }

        public fun rmDirectory(path: File): Boolean {
            if (path.exists()) {
                val files = path.listFiles() ?: return true
                for (i in files.indices) {
                    if (files[i].isDirectory()) {
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
                val len: Int

                while(true) {
                    len = inBuff.read(b).toInt()
                    if(len == -1)
                        break;

                    outBuff.write(b, 0, len)
                }

                outBuff.flush()
            } finally {
                inBuff?.close()
                outBuff?.close()
            }
        }
    }
}

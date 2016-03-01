package cn.nekocode.baseframework.sample.util

import android.content.Context
import android.media.MediaScannerConnection
import android.provider.MediaStore
import android.text.TextUtils
import android.webkit.MimeTypeMap
import java.util.*

/**
 * Created by nekocode on 2015/9/1.
 */
object MediaUtils {
    fun scanFiles(context: Context, paths: Array<String>, mimeTypes: Array<String>?, callback: MediaScannerConnection.OnScanCompletedListener?) {
        if (paths.size != 0) {
            MediaScannerConnection.scanFile(context, paths, mimeTypes, callback)
        }
    }

    fun scanFiles(context: Context, paths: Array<String>) {
        if (paths.size != 0) {
            MediaScannerConnection.scanFile(context, paths, null, null)
        }
    }

    // TODO: "MediaStore.MediaColumns.DATA" may be not right
    fun removeImageFromMediaLib(context: Context, filePath: String): Int {
        val resolver = context.contentResolver
        return resolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                MediaStore.MediaColumns.DATA + "=?", arrayOf(filePath))
    }

    // TODO
    fun removeAudioFromMediaLib(context: Context, filePath: String): Int {
        val resolver = context.contentResolver
        return resolver.delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                MediaStore.MediaColumns.DATA + "=?", arrayOf(filePath))
    }

    // TODO
    fun removeVideoFromMediaLib(context: Context, filePath: String): Int {
        val resolver = context.contentResolver
        return resolver.delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                MediaStore.MediaColumns.DATA + "=?", arrayOf(filePath))
    }

    fun removeMediaFromMediaLib(context: Context, filePath: String): Int {
        val mimeType = getFileMimeType(filePath)?.toLowerCase(Locale.US)
        var affectedRows = 0
        if (mimeType != null) {
            if (isImage(mimeType)) {
                affectedRows = removeImageFromMediaLib(context, filePath)
            } else if (isAudio(mimeType)) {
                affectedRows = removeAudioFromMediaLib(context, filePath);
            } else if (isVideo(mimeType)) {
                affectedRows = removeVideoFromMediaLib(context, filePath);
            }
        }
        return affectedRows
    }

    fun isAudio(mimeType: String): Boolean {
        return mimeType.startsWith("audio")
    }

    fun isImage(mimeType: String): Boolean {
        return mimeType.startsWith("image")
    }

    fun isVideo(mimeType: String): Boolean {
        return mimeType.startsWith("video")
    }

    fun isMediaType(mimeType: String): Boolean {
        var isMedia = false
        if (!TextUtils.isEmpty(mimeType)) {
            val mimeTypeLower = mimeType.toLowerCase(Locale.US)
            isMedia = isImage(mimeTypeLower) || isAudio(mimeTypeLower) || isVideo(mimeTypeLower)
        }
        return isMedia
    }

    fun getFileMimeType(filename: String): String? {
        if (TextUtils.isEmpty(filename)) {
            return null;
        }

        val lastDotIndex = filename.lastIndexOf('.');
        val mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                filename.substring(lastDotIndex + 1).toLowerCase());
        return mimetype;
    }
}

package cn.nekocode.baseframework.utils

import android.content.Context
import android.media.MediaScannerConnection
import android.provider.MediaStore
import android.text.TextUtils
import android.webkit.MimeTypeMap
import java.util.*

/**
 * Created by nekocode on 2015/9/1.
 */
public class MediaUtils {
    companion object {
        public fun scanFiles(context: Context, paths: Array<String>, mimeTypes: Array<String>?, callback: MediaScannerConnection.OnScanCompletedListener?) {
            if (paths.size() != 0) {
                MediaScannerConnection.scanFile(context, paths, mimeTypes, callback)
            }
        }

        public fun scanFiles(context: Context, paths: Array<String>) {
            if (paths.size() != 0) {
                MediaScannerConnection.scanFile(context, paths, null, null)
            }
        }

        // TODO: "MediaStore.MediaColumns.DATA" may be not right
        public fun removeImageFromMediaLib(context: Context, filePath: String): Int {
            val resolver = context.contentResolver
            return resolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    MediaStore.MediaColumns.DATA + "=?", arrayOf(filePath))
        }

        // TODO
        public fun removeAudioFromMediaLib(context: Context, filePath: String): Int {
            val resolver = context.contentResolver
            return resolver.delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    MediaStore.MediaColumns.DATA + "=?", arrayOf(filePath))
        }

        // TODO
        public fun removeVideoFromMediaLib(context: Context, filePath: String): Int {
            val resolver = context.contentResolver
            return resolver.delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    MediaStore.MediaColumns.DATA + "=?", arrayOf(filePath))
        }

        public fun removeMediaFromMediaLib(context: Context, filePath: String): Int {
            val mimeType = getFileMimeType(filePath)?.toLowerCase(Locale.US)
            var affectedRows = 0
            if(mimeType != null) {
                if(isImage(mimeType)) {
                    affectedRows = removeImageFromMediaLib(context, filePath)
                } else if(isAudio(mimeType)) {
                    affectedRows = removeAudioFromMediaLib(context ,filePath);
                } else if(isVideo(mimeType)) {
                    affectedRows = removeVideoFromMediaLib(context, filePath);
                }
            }
            return affectedRows
        }

        public fun isAudio(mimeType: String): Boolean {
            return mimeType.startsWith("audio")
        }

        public fun isImage(mimeType: String): Boolean {
            return mimeType.startsWith("image")
        }

        public fun isVideo(mimeType: String): Boolean {
            return mimeType.startsWith("video")
        }

        public fun isMediaType(mimeType: String): Boolean {
            var isMedia = false
            if(!TextUtils.isEmpty(mimeType)) {
                val mimeTypeLower = mimeType.toLowerCase(Locale.US)
                isMedia = isImage(mimeTypeLower) || isAudio(mimeTypeLower) || isVideo(mimeTypeLower)
            }
            return isMedia
        }

        public fun getFileMimeType(filename: String): String? {
            if (TextUtils.isEmpty(filename)) {
                return null;
            }

            val lastDotIndex = filename.lastIndexOf('.');
            val mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    filename.substring(lastDotIndex + 1).toLowerCase());
            return mimetype;
        }
    }
}

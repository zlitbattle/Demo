package com.example.demo

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log

/**
 *  @author : ling.zhang
 *  date : 2022/1/13 11:05 上午
 *  description :
 */
class MyApp : Application() {
    companion object {
        fun getRealPathFromURI(context: Context, contentUri: Uri?): String {
            Log.d("ling.zhang", "权限：uri==$contentUri")
            var path: String? = null
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            var cursor: Cursor? = null
            try {
                if (null != contentUri) {
                    if (contentUri.toString().startsWith("content://")) {
                        cursor = context.contentResolver.query(contentUri, proj, null, null, null)
                        if (null != cursor && cursor.moveToFirst()) {
                            val columnIndex: Int =
                                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                            path = cursor.getString(columnIndex)
                            Log.d("ling.zhang", "权限正常：$path")
                        }
                    } else if (contentUri.toString().startsWith("file://")) {
                        path = contentUri.path
                    }
                }
            } catch (e: java.lang.Exception) {
                Log.e("ling.zhang", "权限异常$e")
            } finally {
                cursor?.close()
            }
            return path!!
        }

        var myApp: MyApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        myApp = this
    }

    fun start(uri: Uri) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                getRealPathFromURI(this@MyApp, uri)
                handler.postDelayed(this, 1000)
            }
        }, 8000)

    }
}
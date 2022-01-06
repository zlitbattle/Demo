package com.example.send

import android.Manifest
import android.content.ClipData
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 10000)
        }
    }

    fun onClick(view: android.view.View) {
        // 分享一张图片
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_test)
        shareImage(saveBitmap(bitmap, "test"))
    }

    private fun shareImage(uri: Uri?) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(shareIntent)
    }

    private fun saveBitmap(bm: Bitmap, picName: String): Uri? {
        try {
            val dir: String = getExternalFilesDir(null)?.absolutePath
                .toString() + "/test/" + picName + ".jpg"
            val f = File(dir)
            if (!f.exists()) {
                f.parentFile.mkdirs()
                f.createNewFile()
            }
            val out = FileOutputStream(f)
            bm.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.flush()
            out.close()
            return FileProvider.getUriForFile(this, "$packageName.fileProvider", f)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}
package com.example.demo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity

/**
 *  @author : ling.zhang
 *  date : 2022/1/6 7:25 下午
 *  description :
 */
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (intent.getParcelableExtra<Parcelable>(Intent.EXTRA_STREAM) as? Uri)?.let {
            val handler = Handler(Looper.getMainLooper())
            MyApp.myApp?.start(it)
            handler.postDelayed({ finish() }, 3000)
        }
    }
}
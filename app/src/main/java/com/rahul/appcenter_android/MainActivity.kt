package com.rahul.appcenter_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rahul.library.callbacks.AppUpdateCallback
import com.rahul.library.managers.AppUpdateCenter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppUpdateCenter.getInstance()
            .setApiKey(this@MainActivity, "64a27c4d5f91b851c3f609e9")
            .listen(object : AppUpdateCallback {
                override fun onFinishCheckForUpdate() {

                }
            })
    }
}

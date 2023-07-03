package com.rahul.appcenter_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rahul.library.callbacks.AppUpdateCallback
import com.rahul.library.managers.InAppUpdate

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InAppUpdate.getInstance()
            .setApiKey(this@MainActivity, "64896ee2fb005c9f89fefca5")
            .listen(object : AppUpdateCallback {
                override fun onFinishCheckForUpdate() {

                }
            })
    }
}

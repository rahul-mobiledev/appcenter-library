package com.rahul.library.ui.immediate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rahul.library.R
import com.rahul.library.managers.InAppUpdate
import com.rahul.library.network.Response
import com.rahul.library.utils.getArgumentedText

class UpdateAvailableActivity : AppCompatActivity() {
    private lateinit var data: Response
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_immediate_update)
        setData()
        setupViews()
    }

    private fun setData() {
        data = intent.getParcelableExtra(InAppUpdate.APP)!!
    }

    private fun setupViews() {
        findViewById<TextView>(R.id.app_update_info).text =
            getArgumentedText(R.string.need_update, data.name)
        findViewById<ImageView>(R.id.exit).setOnClickListener {
            exitApp()
        }
        findViewById<Button>(R.id.update).setOnClickListener {
            startActivity(
                Intent(
                    this@UpdateAvailableActivity,
                    AppInstallActivity::class.java
                ).apply {
                    putExtra(InAppUpdate.APP, this@UpdateAvailableActivity.data)
                }
            )
            finish()
        }
    }

    private fun exitApp() {
        moveTaskToBack(true)
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(1)
    }

    override fun onBackPressed() {}
}

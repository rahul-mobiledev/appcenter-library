package com.rahul.library.ui.immediate

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rahul.library.R
import com.rahul.library.managers.AppUpdateCenter
import com.rahul.library.network.UpdateResponse
import com.rahul.library.services.InAppUpdateService
import com.rahul.library.utils.installApp

class AppInstallActivity : AppCompatActivity() {
    private lateinit var data: UpdateResponse
    private val downloadManager: DownloadManager by lazy {
        getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    private val onCompleteReceiverImmediate by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) ?: -1
                val file = downloadManager.getUriForDownloadedFile(id)
                val i = installApp(file)
                startActivity(i)
                context?.unregisterReceiver(this)

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_install)
        setData()
        setupViews()
    }

    private fun setData() {
        data = intent.getParcelableExtra(AppUpdateCenter.APP)!!
        startService(Intent(this, InAppUpdateService::class.java).apply {
            putExtra(AppUpdateCenter.APP, this@AppInstallActivity.data)
        })
        registerReceiver(
            onCompleteReceiverImmediate,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    private fun setupViews() {
        Glide.with(this@AppInstallActivity)
            .load(R.drawable.app_update)
            .into(findViewById(R.id.loading))

    }

    override fun onBackPressed() {}
}
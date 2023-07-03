package com.rahul.library.services

import android.app.DownloadManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.IBinder
import com.rahul.library.managers.AppUpdateCenter
import com.rahul.library.managers.NotificationManager
import com.rahul.library.network.UpdateResponse
import com.rahul.library.utils.installApp

class InAppUpdateService : Service() {

    private var downloadId: Long = 0
    private val downloadManager by lazy {
        getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }
    private lateinit var data: UpdateResponse

    private val onCompleteReceiverFlexible by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) ?: -1
                if (id == downloadId) {
                    val file = downloadManager.getUriForDownloadedFile(id)
                    val i = installApp(file)
                    NotificationManager.getInstance().sendNotification(i)
                    context?.unregisterReceiver(this)
                }
            }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            startProcess(it)
        }
        return START_REDELIVER_INTENT
    }

    private fun startProcess(intent: Intent) {
        data = intent.getParcelableExtra(AppUpdateCenter.APP)!!
        startForeground(
            FOREGROUND_ID,
            NotificationManager.getInstance().getForegroundNotification()
        )
        clearData()
        download()
        registerReceiver(
            onCompleteReceiverFlexible,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    private fun clearData() {
        val folder = getExternalFilesDir("apps")
        folder?.deleteRecursively()
    }

    private fun download() {
        data.activeRelease?.apkUrl?.let { url ->
            downloadId = downloadManager.enqueue(
                DownloadManager.Request(Uri.parse(url))
                    .setDestinationInExternalFilesDir(
                        this, "apps", "app.apk"
                    )
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            )
        }
    }


    companion object {
        private const val FOREGROUND_ID = 7
    }
}
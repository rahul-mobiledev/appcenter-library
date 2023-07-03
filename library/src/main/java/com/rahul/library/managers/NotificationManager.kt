package com.rahul.library.managers

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rahul.library.R

class NotificationManager() {

    private lateinit var context: Context
    fun init(context: Context) {
        this.context = context
        createNotificationChannel()
    }

    fun getForegroundNotification() = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(context.getString(R.string.foreground_title))
        .setContentText(context.getString(R.string.foreground_description))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    fun sendNotification(intent: Intent) {
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.n_title))
            .setContentText(context.getString(R.string.n_message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
        builder.notification.flags = builder.notification.flags or Notification.FLAG_AUTO_CANCEL

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val descriptionText = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: android.app.NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "rahul.appupdate.manager"
        private const val CHANNEL_NAME = "In App Updates"
        private const val CHANNEL_DESCRIPTION =
            "Update your app to its latest version with App Update Center Features"
        private const val NOTIFICATION_ID = 7

        private lateinit var instance: com.rahul.library.managers.NotificationManager
        fun getInstance() = if (this::instance.isInitialized) {
            instance
        } else {
            instance = NotificationManager()
            instance
        }
    }
}

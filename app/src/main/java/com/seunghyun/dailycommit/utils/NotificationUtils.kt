package com.seunghyun.dailycommit.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.seunghyun.dailycommit.MainActivity
import com.seunghyun.dailycommit.R
import java.util.concurrent.atomic.AtomicInteger

class NotificationUtils(private val context: Context) {
    private val notificationId = AtomicInteger(0)
    private val generalChannelName = context.getString(R.string.notification_channel_general)

    fun pushNotification(title: String, content: String) {
        createChannel()
        val intent = Intent(context, MainActivity::class.java)
        val notification = NotificationCompat.Builder(context, generalChannelName)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, 0))
                .setContentTitle(title)
                .setContentText(content)
                .build()
        NotificationManagerCompat.from(context).notify(notificationId.getAndIncrement(), notification)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(generalChannelName, generalChannelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
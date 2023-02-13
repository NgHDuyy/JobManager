package com.example.jobmanager.broadcastReceiver

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.jobmanager.R

const val notificationID = "notificationID"
const val channelID = "channel1"
const val title = "title"
const val message = "message"

class Notification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notification: Notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle(intent.getStringExtra(title))
            .setContentText(intent.getStringExtra(message))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(intent.getIntExtra(notificationID, 0), notification)
    }
}
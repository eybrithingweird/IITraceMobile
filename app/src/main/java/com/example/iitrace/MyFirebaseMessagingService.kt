package com.example.iitrace

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object{
        const val TAG = "Android Notifs"
    }

    override fun onNewToken(refreshedToken: String) {
        super.onNewToken(refreshedToken)
        // Get updated InstanceID token.
        val token = FirebaseMessaging.getInstance().token
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val title = remoteMessage.notification!!.title
        val text = remoteMessage.notification!!.body
        val CHANNEL_ID = "ALERT_NOTIFICATION"

        if(Build.VERSION.SDK_INT >= 26) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Heads Up Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel);
        }

        val notify: Notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(com.example.iitrace.R.mipmap.ic_launcher)
            .build()

        notify.flags = notify.flags or Notification.FLAG_AUTO_CANCEL
        notificationManager.notify(0, notify)

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}
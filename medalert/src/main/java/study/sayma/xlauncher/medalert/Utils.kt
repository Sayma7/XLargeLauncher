package study.sayma.xlauncher.medalert

import android.app.NotificationManager
import android.content.Context
import android.support.v4.app.NotificationCompat

/**
 * Created by Touhid on 11/20/2017.
 *
 */
object Utils {

    fun showNotification(context: Context, title: String, body: String) {
        val builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_alarm_on_white_24dp)
                .setContentTitle(title)
                .setContentText(body)

        /*val notificationIntent = Intent(this, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(contentIntent)*/

        // Add as notification
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())
    }
}
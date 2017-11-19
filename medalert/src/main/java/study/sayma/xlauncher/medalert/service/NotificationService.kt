package study.sayma.xlauncher.medalert.service

import android.app.IntentService
import android.content.Intent
import study.sayma.xlauncher.medalert.Utils


/**
 * Created by Touhid on 11/19/2017.
 *
 */
class NotificationService : IntentService("Notification") {
    override fun onHandleIntent(intent: Intent?) {
        val title = intent?.getStringExtra("title") + ""
        val body = intent?.getStringExtra("body") + ""
        Utils.showNotification(this, title, body)
    }
}
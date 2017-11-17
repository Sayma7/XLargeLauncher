package study.sayma.xlargelauncher.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_med_alert.*
import study.sayma.xlargelauncher.R
import android.app.AlarmManager
import android.content.Context.ALARM_SERVICE
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*


class MedAlertActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_alert)

        switch_med_ok.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                setAlarm()
        }
    }

    private fun setAlarm() {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 13) // For 1 PM or 2 PM
        calendar.set(Calendar.DAY_OF_YEAR, 1)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val activity = this
        val pi = PendingIntent.getService(activity, 0,
                Intent(activity, MedAlertActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
        val am = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pi)
    }
}

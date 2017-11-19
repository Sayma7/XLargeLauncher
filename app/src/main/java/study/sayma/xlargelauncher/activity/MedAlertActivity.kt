package study.sayma.xlargelauncher.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.activity_med_alert.*
import study.sayma.xlargelauncher.R
import java.util.*


class MedAlertActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        this.hour = hourOfDay
        this.minute = minute
        this.second = second
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_alert)

        tvTimeEntry.setOnClickListener({ _ -> showTimePicker() })

        switchMedAlert.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                setAlarm()
        }
    }

    private var hour = 0
    private var minute = 0
    private var second = 0

    private fun showTimePicker() {
        val now = Calendar.getInstance()
        val tpd = TimePickerDialog.newInstance(
                this@MedAlertActivity,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                false
        )
        tpd.show(fragmentManager, "Medicine Time")
    }

    private fun setAlarm() {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.DAY_OF_YEAR, 1)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, second)
        val activity = this
        val pi = PendingIntent.getService(activity, 0,
                Intent(activity, MedAlertActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
        val am = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pi)
    }
}

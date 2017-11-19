package study.sayma.xlauncher.medalert

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import study.sayma.xlauncher.medalert.db.MedAlertTable
import study.sayma.xlauncher.medalert.service.NotificationService
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val titles = arrayOf("Title 1", "Title 2", "Title 3", "Title 4", "Title 5")
        private val bodies = arrayOf("Body 1 Body  Body  Body  Body  Body  Body 1",
                "Body 2 Body  Body  Body  Body  Body  Body  Body 2",
                "Body 3 Body  Body  Body  Body  Body  Body  Body 3",
                "Body 4 Body  Body  Body  Body  Body  Body  Body 4",
                "Body 5 Body  Body  Body  Body  Body  Body  Body 5")
    }

    private lateinit var adapter: MedAlertAdapter
    private lateinit var medAlertDb: MedAlertTable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setHealthNotifications()

        rvMedAlerts.layoutManager = LinearLayoutManager(this)
        medAlertDb = MedAlertTable(this)
        adapter = MedAlertAdapter(this, medAlertDb.list())
        rvMedAlerts.adapter = adapter

        tvNoAlert.visibility = if (adapter.itemCount > 0) View.GONE else View.VISIBLE

        btnAddMoreMedAlerts.setOnClickListener({ _ ->
            startActivity(Intent(this@MainActivity, MedAlertAdd::class.java))
        })
    }

    override fun onResume() {
        super.onResume()
        adapter.addUniquely(medAlertDb.list())
        tvNoAlert.visibility = if (adapter.itemCount > 0) View.GONE else View.VISIBLE
    }

    private fun setHealthNotifications() {
        setHealthAlarm(1, 7, 0, 0)
        setHealthAlarm(2, 12, 0, 0)
        setHealthAlarm(3, 17, 0, 0)
        setHealthAlarm(4, 20, 0, 0)
        setHealthAlarm(5, 0, 10, 0)
    }

    private fun setHealthAlarm(no: Int, h: Int, m: Int, s: Int) {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.DAY_OF_YEAR, 1)
        calendar.set(Calendar.HOUR_OF_DAY, h)
        calendar.set(Calendar.MINUTE, m)
        calendar.set(Calendar.SECOND, s)
        val activity = this
        val intent = Intent(activity, NotificationService::class.java)
        intent.putExtra("title", titles[no % titles.size])
        intent.putExtra("body", bodies[no % bodies.size])
        val pi = PendingIntent.getService(activity, no * h,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val am = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pi)
    }
}

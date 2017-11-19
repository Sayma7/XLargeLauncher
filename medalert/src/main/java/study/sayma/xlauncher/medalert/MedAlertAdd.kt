package study.sayma.xlauncher.medalert

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.CheckBox
import android.widget.Toast
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.activity_med_alert.*
import study.sayma.xlauncher.medalert.db.MedAlertTable
import study.sayma.xlauncher.medalert.service.NotificationService
import java.util.*

/**
 * Created by Touhid on 11/19/2017.
 *
 */
class MedAlertAdd : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    private var hour = 0
    private var minute = 0
    private var second = 0
    private lateinit var db: MedAlertTable
    private val dayOn = BooleanArray(7, { false })

    private lateinit var cbxs: Array<CheckBox>

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        this.hour = hourOfDay
        this.minute = minute
        this.second = second
        tvTimeEntry.text = String.format("%d : %d", hour, minute)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_alert)

        cbxs = arrayOf(checkbox_saturday, checkbox_sunday, checkbox_monday, checkbox_tuesday,
                checkbox_wednesday, checkbox_thursday, checkbox_friday)

        tvTimeEntry.text = "0 : 0"
        tvTimeEntry.setOnClickListener({ _ -> showTimePicker() })

        /*switchMedAlert.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                setAlarm()
        }*/

        db = MedAlertTable(this)
        fabSaveMedAlert.setOnClickListener({ _ ->
            val medAlert = extractMedAlert() ?: return@setOnClickListener
            if (db.isFieldExist(medAlert))
                toast("Medicine-alert exists at the set time.")
            else if (db.insert(medAlert) > -1) {
                addAlert(medAlert)
                toast("The Medicine-alert is successfully set.")
                finish()
            }
        })

        setCheckboxTogglers()
    }

    private fun addAlert(medAlert: MedAlert) {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.DAY_OF_YEAR, 1)
        calendar.set(Calendar.HOUR_OF_DAY, medAlert.hour)
        calendar.set(Calendar.MINUTE, medAlert.minute)
        calendar.set(Calendar.SECOND, medAlert.second)
        val activity = this
        val intent = Intent(activity, NotificationService::class.java)
        intent.putExtra("title", medAlert.medicineName)
        intent.putExtra("body", "Time to take medicine")
        val pi = PendingIntent.getService(activity, medAlert.medicineName.length,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val am = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pi)
    }

    private fun setCheckboxTogglers() {
        checkbox_everyday.setOnCheckedChangeListener { _, b ->
            toggleCbxs(b)
        }
        (0 until cbxs.size).forEach { i ->
            cbxs[i].setOnCheckedChangeListener { _, isChecked ->
                dayOn[i] = isChecked
            }
        }
    }

    private fun toggleCbxs(isEveryday: Boolean) {
        (0 until cbxs.size).forEach { i ->
            dayOn[i] = isEveryday
            cbxs[i].isChecked = isEveryday
            cbxs[i].isEnabled = !isEveryday
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun extractMedAlert(): MedAlert? {
        val medName = etMedNameEntry.text.toString()
        if (medName.isEmpty()) {
            etMedNameEntry.error = "Medicine Name can't be empty."
            return null
        }
        val b = (0 until dayOn.size).any { dayOn[it] }
        if (!b) {
            toast("No days are selected.")
            return null
        }
        return MedAlert(medName, hour, minute, second, switchMedAlert.isChecked, dayOn)
    }

    private fun showTimePicker() {
        val now = Calendar.getInstance()
        val tpd = TimePickerDialog.newInstance(
                this@MedAlertAdd,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                now.get(Calendar.SECOND),
                true
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
                Intent(activity, MedAlertAdd::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
        val am = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pi)
    }
}
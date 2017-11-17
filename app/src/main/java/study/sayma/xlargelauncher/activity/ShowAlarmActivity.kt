package study.sayma.xlargelauncher.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_show_alarm.*
import study.sayma.xlargelauncher.R
import study.sayma.xlargelauncher.utils.C

class ShowAlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_alarm)

        val msg = intent.getStringExtra(C.KEY_ALARM_DATA)
        tvAlarmData.text = msg
    }
}

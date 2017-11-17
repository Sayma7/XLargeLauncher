package study.sayma.xlargelauncher.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import study.sayma.xlargelauncher.R
import study.sayma.xlargelauncher.utils.C
import study.sayma.xlargelauncher.utils.P
import study.sayma.xlargelauncher.utils.U

class EmergencySendSmsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_send_sms)

        if (intent.action == C.KEY_EMERGENCY)
            sendSms()
    }

    private fun sendSms() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.SEND_SMS),
                        PERMISSIONS_REQUEST_SEND_SMS)
            }
            return
        }
        val numberList = P.getEmergencyNumberList(this)
        if (numberList.size < 1) {
            toast("No numbers are added to your emergency-list")
            return
        }
        toast("Sending emergency SMS to your contacts ...")
        val msgSms = "I am in danger!! Send immediate HELP please."
        for (s in numberList)
            if (U.sendSMS(s, msgSms))
                toast("SMS sent to " + s)
            else
                toast("SMS failed to send to " + s)
        finish()
    }

    private val PERMISSIONS_REQUEST_SEND_SMS = 128

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) =
            when (requestCode) {
                PERMISSIONS_REQUEST_SEND_SMS ->
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        sendSms()
                    else
                        toast("SMS can't be sent in lack of permission")
                else -> {
                }
            }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}

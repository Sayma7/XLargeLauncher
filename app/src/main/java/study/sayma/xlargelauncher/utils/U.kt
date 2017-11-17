package study.sayma.xlargelauncher.utils

import android.telephony.SmsManager


/**
 * Created by Touhid on 11/17/2017.
 */
object U {
    fun isPhoneNumberValid(phoneNo: String): Boolean {
        return ((phoneNo.startsWith("880") && phoneNo.length == 13)
                ||
                (!phoneNo.startsWith("880") && phoneNo.length == 11) && phoneNo.matches("\\d+".toRegex()))
    }

    fun sendSMS(phoneNo: String, msg: String): Boolean {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNo, null, msg, null, null)
            return true
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return false
    }
}
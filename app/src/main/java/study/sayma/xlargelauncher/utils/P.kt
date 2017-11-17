package study.sayma.xlargelauncher.utils

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray

/**
 * Created by Touhid's Guest on 11/11/2017.
 */

object P {
    private val KEY_EMERGENCY_NUMBERS: String = "emergency_numbers"

    private var prefEditor: SharedPreferences.Editor? = null
    private var pref: SharedPreferences? = null

    private fun assurePref(ctx: Context) {
        if (pref == null)
            pref = ctx.getSharedPreferences(ctx.packageName, Context.MODE_PRIVATE)
        if (prefEditor == null)
            prefEditor = pref!!.edit()
    }

    public fun addEmergencyNumber(ctx: Context, number: String): Boolean {
        var phone: String = number
        if (number.length == 14 && number.startsWith("+880"))
            phone = number.substring(0, 3)
        if (number.length == 13 && number.startsWith("880"))
            phone = number.substring(0, 2)
        val ja: JSONArray = getEmergencyNumbers(ctx)
        val l = ja.length()
        (0 until l)
                .filter { ja.getString(it) == phone }
                .forEach { return false }
        ja.put(phone)
        saveEmergencyNumbers(ctx, ja)
        return true
    }

    public fun removeNumber(ctx: Context, number: String): Boolean {
        val ja: JSONArray = getEmergencyNumbers(ctx)
        val l = ja.length()

        val jaNew = JSONArray()
        for (i in 0 until l) {
            val num = ja.get(i) as String
            if (num != number)
                jaNew.put(number)
        }
        saveEmergencyNumbers(ctx, jaNew)
        return ja.length() > jaNew.length()
    }

    private fun saveEmergencyNumbers(ctx: Context, ja: JSONArray) {
        assurePref(ctx)
        prefEditor!!.putString(KEY_EMERGENCY_NUMBERS, ja.toString())
        prefEditor!!.apply()
    }

    fun getEmergencyNumbers(ctx: Context): JSONArray {
        assurePref(ctx)
        return JSONArray(pref!!.getString(KEY_EMERGENCY_NUMBERS, "[]"))
    }

    fun getEmergencyNumberList(ctx: Context): ArrayList<String> {
        val ja = getEmergencyNumbers(ctx)
        val l = ja.length()
        val list = ArrayList<String>()
        if (l < 1)
            return list
        (0 until l).mapTo(list) { ja.getString(it) }
        return list
    }
}

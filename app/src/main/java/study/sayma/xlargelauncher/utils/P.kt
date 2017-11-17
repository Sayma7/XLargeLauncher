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

    public fun addEmergencyNumber(ctx: Context, number: String) {
        val ja: JSONArray = getEmergencyNumbers(ctx)
        ja.put(number)
        saveEmergencyNumbers(ctx, ja)
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
        assurePref(ctx)
        val ja = JSONArray(pref!!.getString(KEY_EMERGENCY_NUMBERS, "[]"))
        val l = ja.length()
        val list = ArrayList<String>()
        if (l < 1)
            return list
        (0 until l).mapTo(list) { ja.getString(it) }
        return list
    }
}

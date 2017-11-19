package study.sayma.xlauncher.medalert.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import study.sayma.xlauncher.medalert.MedAlert


/**
 * Created by Touhid on 11/20/2017.
 *
 */
class MedAlertTable(private val context: Context) {

    companion object {
        private val TABLE_NAME = DatabaseHelper.TABLE_MED_ALERT
        private val KEY_ID = "_alert_id"                // 0 -integer (AI)
        private val KEY_MED_NAME = "_career_name_bn"    // 1 - text
        private val KEY_HOUR = "_career_name_en"        // 2 - text
        private val KEY_MINUTE = "_career_sur_names_bn" // 3 - text
        private val KEY_IS_ON = "_career_sur_names_en"  // 4 - text
        private val KEY_SATURDAY = "_career_brief_bn"   // 5 - text
        private val KEY_SUNDAY = "_career_brief_en"     // 6 - text
        private val KEY_MONDAY = "_jobs_bn"             // 7 - text
        private val KEY_TUESDAY = "_jobs_en"            // 8 - text
        private val KEY_WEDNESDAY = "_suc_stories_bn"   // 9 - text
        private val KEY_THURSDAY = "_suc_stories_en"    // 10 - text
        private val KEY_FRIDAY = "_req_bn"              // 11 - text
        private val TAG = this::class.java.simpleName
    }

    init {
        createTable()
    }

    private fun openDB(): SQLiteDatabase? = DatabaseManager.getInstance(context).openDatabase()

    private fun closeDB() {
        DatabaseManager.getInstance(context).closeDatabase()
    }

    private fun createTable() {

        val db = openDB()
        val CREATE_TABLE_SQL = ("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + "( " //
                + KEY_ID + " INTEGER PRIMARY KEY, " //0
                + KEY_MED_NAME + " TEXT, "          //1
                + KEY_HOUR + " INTEGER, "           //2
                + KEY_MINUTE + " INTEGER, "         //3
                //
                + KEY_IS_ON + " INTEGER, "          //4
                + KEY_SATURDAY + " INTEGER, "       //5
                + KEY_SUNDAY + " INTEGER, "         //6
                + KEY_MONDAY + " INTEGER, "         //7
                + KEY_TUESDAY + " INTEGER, "        //8
                + KEY_WEDNESDAY + " INTEGER, "      //9
                + KEY_THURSDAY + " INTEGER, "       //10
                + KEY_FRIDAY + " INTEGER "          //11
                + ")")
        db?.execSQL(CREATE_TABLE_SQL)
        closeDB()
    }

    fun isFieldExist(medAlert: MedAlert): Boolean {
        val db = openDB()
        val cursor = db?.rawQuery("SELECT " + KEY_MED_NAME + " FROM "
                + TABLE_NAME + " WHERE " + KEY_MED_NAME + " = ? AND " + KEY_HOUR + " = ? ",
                arrayOf(medAlert.medicineName, medAlert.hour.toString()))
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                cursor.close()
                closeDB()
                return true
            }
            cursor.close()
        }
        closeDB()
        return false
    }

    fun insert(medAlert: MedAlert): Long {
        if (isFieldExist(medAlert)) {
            Log.d(TAG, "MedAlert existed: updating")
            return update(medAlert)
        }
        val db = openDB()
        val ret = db?.insert(TABLE_NAME, null, getRowValue(medAlert))
        closeDB()
        Log.d(TAG, "Insert Returned, ret=" + ret)
        return ret ?: 0
    }

    private fun getRowValue(medAlert: MedAlert): ContentValues {
        val rv = ContentValues()
        rv.put(KEY_MED_NAME, medAlert.medicineName)
        rv.put(KEY_HOUR, medAlert.hour)
        rv.put(KEY_MINUTE, medAlert.minute)
        rv.put(KEY_IS_ON, if (medAlert.on) 1 else 0)
        rv.put(KEY_SATURDAY, if (medAlert.dayOn.isNotEmpty() && medAlert.dayOn[0]) 1 else 0)
        rv.put(KEY_SUNDAY, if (medAlert.dayOn.size > 1 && medAlert.dayOn[1]) 1 else 0)
        rv.put(KEY_MONDAY, if (medAlert.dayOn.size > 2 && medAlert.dayOn[2]) 1 else 0)
        rv.put(KEY_TUESDAY, if (medAlert.dayOn.size > 3 && medAlert.dayOn[3]) 1 else 0)
        rv.put(KEY_WEDNESDAY, if (medAlert.dayOn.size > 4 && medAlert.dayOn[4]) 1 else 0)
        rv.put(KEY_THURSDAY, if (medAlert.dayOn.size > 5 && medAlert.dayOn[5]) 1 else 0)
        rv.put(KEY_FRIDAY, if (medAlert.dayOn.size > 6 && medAlert.dayOn[6]) 1 else 0)
        return rv
    }

    fun update(medAlert: MedAlert): Long {
        val db = openDB()
        val ret = db?.update(TABLE_NAME, getRowValue(medAlert),
                "$KEY_MED_NAME = ? AND $KEY_HOUR = ? ",
                arrayOf(medAlert.medicineName, medAlert.hour.toString()))
        closeDB()
        Log.d(TAG, "Update Returned, ret=" + ret)
        return ret?.toLong() ?: 0
    }

    fun list(): ArrayList<MedAlert> {
        val db = openDB()
        val cursor = db?.rawQuery("SELECT * FROM " + TABLE_NAME, null)
        val medAlertList = ArrayList<MedAlert>()

        if (cursor != null) {
            if (cursor.moveToFirst())
                do {
                    medAlertList.add(parseCursor(cursor))
                } while (cursor.moveToNext())
            cursor.close()
        }
        db?.close()
        return medAlertList
    }

    private fun parseCursor(cursor: Cursor): MedAlert {
        val dayOn = BooleanArray(7, { false })
        for (i in 0..6)
            dayOn[i] = cursor.getInt(5 + i) == 1
        return MedAlert(cursor.getString(1),
                cursor.getInt(2), cursor.getInt(3), 0,
                cursor.getInt(4) == 1, dayOn)
    }

    fun delete(medAlert: MedAlert): Boolean {
        val db = openDB()
        val b = db?.delete(TABLE_NAME, "$KEY_MED_NAME = ? AND $KEY_HOUR = ? ",
                arrayOf(medAlert.medicineName, medAlert.hour.toString())) ?: -1
        db?.close()
        return b > 0
    }

}
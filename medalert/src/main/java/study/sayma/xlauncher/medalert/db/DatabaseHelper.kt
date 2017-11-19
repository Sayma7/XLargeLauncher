package study.sayma.xlauncher.medalert.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by Touhid on 11/20/2017.
 *
 */
class DatabaseHelper(private val context: Context,
                     private val DB_NAME: String = "med_alert.db", private val DB_VERSION: Int = 1) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        // Table names
        val TABLE_MED_ALERT = "med_alert"
    }

    override fun onCreate(db: SQLiteDatabase) {}

    fun getDbVersion(): Int = DB_VERSION

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MED_ALERT)
        onCreate(db)
    }
}

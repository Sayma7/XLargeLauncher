package study.sayma.xlauncher.medalert.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.util.concurrent.atomic.AtomicInteger


/**
 * Created by Touhid on 11/20/2017.
 *
 */
class DatabaseManager() {
    companion object {
        private var mInstance: DatabaseManager? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseManager {
            if (mInstance == null) {
                mInstance = DatabaseManager(context)
            }
            return mInstance as DatabaseManager
        }
    }

    private var mDBHelper: DatabaseHelper? = null
    private var mDatabase: SQLiteDatabase? = null

    private var mOpenCounter: AtomicInteger? = null

    private constructor(context: Context) : this() {
        this.mDatabase = null
        this.mOpenCounter = AtomicInteger()
        this.mDBHelper = DatabaseHelper(context.applicationContext)
    }

    @Synchronized
    fun openDatabase(): SQLiteDatabase? {
        if (mOpenCounter?.incrementAndGet() == 1) {
            mDatabase = mDBHelper?.writableDatabase
        }
        if (mDatabase == null || !mDatabase!!.isOpen) {
            mDatabase = mDBHelper?.writableDatabase
        }
        return mDatabase
    }

    @Synchronized
    fun closeDatabase() {
        if (mOpenCounter?.decrementAndGet() == 0) {
            mDatabase!!.close()
        }
    }

}
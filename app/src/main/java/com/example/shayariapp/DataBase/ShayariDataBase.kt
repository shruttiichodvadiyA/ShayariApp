package com.example.shayariapp

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.Error
import kotlin.Throws
import kotlin.jvm.Synchronized

class MyDatabase(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private val mDataBase: SQLiteDatabase? = null
    private var mNeedUpdate = false
    private val mContext: Context

    init {
        if (Build.VERSION.SDK_INT >= 17) DB_PATH =
            context.applicationInfo.dataDir + "/databases/" else DB_PATH =
            "/data/data/" + context.packageName + "/databases/"
        mContext = context
        copyDataBase()
        this.readableDatabase
    }

    private fun copyDataBase() {
        if (!checkDataBase()) {
            this.readableDatabase
            close()
            try {
                copyDBFile()
            } catch (mIOException: IOException) {
                throw Error("ErrorCopyingDataBase")
            }
        }
    }

    private fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DB_NAME)
        return dbFile.exists()
    }

    //    TODO copy file
    @Throws(IOException::class)
    private fun copyDBFile() {
        val mInput = mContext.assets.open(DB_NAME)
        val mOutput: OutputStream = FileOutputStream(DB_PATH + DB_NAME)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) mOutput.write(mBuffer, 0, mLength)
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) mNeedUpdate = true
    }

    //    TODO update database
    @Throws(IOException::class)
    fun updateDataBase() {
        if (mNeedUpdate) {
            val dbFile = File(DB_PATH + DB_NAME)
            if (dbFile.exists()) dbFile.delete()
            copyDataBase()
            mNeedUpdate = false
        }
    }

    @Synchronized
    override fun close() {
        mDataBase?.close()
        super.close()
    }

    fun readData(): ArrayList<ShayariModelClass> {
        var shayarilist = ArrayList<ShayariModelClass>()
        val db = readableDatabase
        val sql = "select * from CategoryTb"
        val c: Cursor = db.rawQuery(sql, null)
        if (c.moveToFirst()) {
            do {
                val id = c.getInt(0)
                val name = c.getString(1)
                Log.e(TAG, "readData:==> $id   $name ")

                var model = ShayariModelClass(id, name)
                shayarilist.add(model)

            } while (c.moveToNext())
        }
        return shayarilist
    }

    companion object {
        private const val TAG = "MyDatabase"
        private const val DB_NAME = "ShayariDataBase.db"
        private var DB_PATH = ""
        private const val DB_VERSION = 1
    }

    fun displayShayari(getid: Int): ArrayList<ShayariDisplay> {
        var shayariDisplaylist = ArrayList<ShayariDisplay>()
        val db = readableDatabase
        val sql = "select * from ShayariTable where Category_id ='$getid'"
        val c: Cursor = db.rawQuery(sql, null)
        if (c.moveToFirst()) {

            do {
                val id = c.getInt(0)
                val name = c.getString(1)
                val sid = c.getInt(2)
                val fav = c.getInt(3)
//                Log.e(TAG, "readData:==> $id   $name $sid ")

                var model = ShayariDisplay(id, name, sid, fav)
                shayariDisplaylist.add(model)

            } while (c.moveToNext())
        }
        return shayariDisplaylist
    }

    fun displayfavoutite(fav: Int, s_id: Int) {
        val update = writableDatabase
        val updatesql = "update ShayariTable set favourite='$fav'where Shayari_id='$s_id'"
        update.execSQL(updatesql)
    }

    fun DisplayFavouriteShayari(): ArrayList<FavouriteShayariclass> {
        var favouritelist = ArrayList<FavouriteShayariclass>()
        val db = readableDatabase
        val sql = "select * from ShayariTable where favourite=1"
        val c: Cursor = db.rawQuery(sql, null)
        if (c.moveToFirst()) {
            do {
                val id = c.getInt(0)
                val name = c.getString(1)
                val fav = c.getInt(2)

                var model = FavouriteShayariclass(id, name, fav)
                favouritelist.add(model)
            } while (c.moveToNext())
        }
        return favouritelist
    }

}

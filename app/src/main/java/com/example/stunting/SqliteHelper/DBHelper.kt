package com.example.stunting.SqliteHelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite. SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Calendar
import com.example.stunting.response.Stunting

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        // here we have created table with fields
        val createTable = ("CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USER_ID + " INTEGER,"
                + USERNAME + " TEXT,"
                + UMUR + " INTEGER,"
                + TINGGI_BADAN + " FLOAT,"
                + BERAT_BADAN + " FLOAT,"
                + STUNTING + " TEXT,"
                + CREATE_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + UPDATE_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP)")

        db?.execSQL(createTable)

        val createTable2 = ("CREATE TABLE " + TABLE_NAME2 + "("
                + ID + " INTEGER,"
                + USER_ID + " INTEGER,"
                + USERNAME + " TEXT,"
                + UMUR + " INTEGER,"
                + TINGGI_BADAN + " FLOAT,"
                + BERAT_BADAN + " FLOAT,"
                + STUNTING + " TEXT,"
                + CREATE_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + UPDATE_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP)")
        db?.execSQL(createTable2)

        val createTable3 = ("CREATE TABLE " + TABLE_NAME3 + "("
                + ID + " INTEGER)")
        db?.execSQL(createTable3)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // here we have upgraded the database
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME2")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME3")
        onCreate(db)
    }

    fun addData(user_id: Int, username: String, umur: Int, tinggi_badan: Float, berat_badan: Float, stunting: String): Boolean {
        // here we have created values for each column
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_ID, user_id)
        contentValues.put(USERNAME, username)
        contentValues.put(UMUR, umur)
        contentValues.put(TINGGI_BADAN, tinggi_badan)
        contentValues.put(BERAT_BADAN, berat_badan)
        contentValues.put(STUNTING, stunting)
        contentValues.put(CREATE_AT, Calendar.getInstance().time.toString())
        contentValues.put(UPDATE_AT, Calendar.getInstance().time.toString())

        // here we have used insert method
        val result = db.insert(TABLE_NAME, null, contentValues)

        // now we are checking if the data is inserted or not.
        return result != -1L
    }

    fun addData2(id:Int, user_id: Int, username: String, umur: Int, tinggi_badan: Float, berat_badan: Float, stunting: String): Boolean {
        // here we have created values for each column
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, id)
        contentValues.put(USER_ID, user_id)
        contentValues.put(USERNAME, username)
        contentValues.put(UMUR, umur)
        contentValues.put(TINGGI_BADAN, tinggi_badan)
        contentValues.put(BERAT_BADAN, berat_badan)
        contentValues.put(STUNTING, stunting)
        contentValues.put(CREATE_AT, Calendar.getInstance().time.toString())
        contentValues.put(UPDATE_AT,  Calendar.getInstance().time.toString())

        // here we have used insert method
        val result = db.insert(TABLE_NAME2, null, contentValues)

        // now we are checking if the data is inserted or not.
        return result != -1L
    }

    fun addData3(stunting_id: Int): Boolean {
        // here we have created values for each column
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, stunting_id)

        // here we have used insert method
        val result = db.insert(TABLE_NAME3, null, contentValues)

        // DELETE DATA FROM TABLE2
        db.delete(TABLE_NAME2, "$ID = $stunting_id", null)

        // now we are checking if the data is inserted or not.
        return result != -1L
    }

    @SuppressLint("Range")
    fun getData(): List<Stunting> {
        val datalIst = ArrayList<Stunting>()
        // here we have used rawQuery to fetch the data from database.
        val db = this.readableDatabase
        val cursor: Cursor =  db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $ID DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID))
                val user_id = cursor.getInt(cursor.getColumnIndex(USER_ID))
                val username = cursor.getString(cursor.getColumnIndex(USERNAME))
                val umur = cursor.getInt(cursor.getColumnIndex(UMUR))
                val tinggi_badan = cursor.getFloat(cursor.getColumnIndex(TINGGI_BADAN))
                val berat_badan = cursor.getFloat(cursor.getColumnIndex(BERAT_BADAN))
                val stunting = cursor.getString(cursor.getColumnIndex(STUNTING))
                val create_at = cursor.getString(cursor.getColumnIndex(CREATE_AT))
                val update_at = cursor.getString(cursor.getColumnIndex(UPDATE_AT))

                val data = Stunting(id, user_id, username, umur, tinggi_badan, berat_badan, stunting, create_at, update_at)
                datalIst.add(data)
            }while (cursor.moveToNext())
        }
        return datalIst
    }

    @SuppressLint("Range")
    fun getData2(user_id: Int, limit: Int, search: String): List<Stunting> {
        val datalIst = ArrayList<Stunting>()
        // here we have used rawQuery to fetch the data from database.
        val db = this.readableDatabase
        val cursor: Cursor =  db.rawQuery("SELECT * FROM $TABLE_NAME2 WHERE $USER_ID=$user_id AND $USERNAME LIKE '%$search%' ORDER BY $ID DESC LIMIT $limit", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID))
                val user_id = cursor.getInt(cursor.getColumnIndex(USER_ID))
                val username = cursor.getString(cursor.getColumnIndex(USERNAME))
                val umur = cursor.getInt(cursor.getColumnIndex(UMUR))
                val tinggi_badan = cursor.getFloat(cursor.getColumnIndex(TINGGI_BADAN))
                val berat_badan = cursor.getFloat(cursor.getColumnIndex(BERAT_BADAN))
                val stunting = cursor.getString(cursor.getColumnIndex(STUNTING))
                val create_at = cursor.getString(cursor.getColumnIndex(CREATE_AT))
                val update_at = cursor.getString(cursor.getColumnIndex(UPDATE_AT))

                val data = Stunting(id, user_id, username, umur, tinggi_badan, berat_badan, stunting, create_at, update_at)
                datalIst.add(data)
            }while (cursor.moveToNext())
        }

        return datalIst
    }

    @SuppressLint("Range")
    fun getData3(): List<Int> {
        val datalIst = ArrayList<Int>()
        // here we have used rawQuery to fetch the data from database.
        val db = this.readableDatabase
        val cursor: Cursor =  db.rawQuery("SELECT * FROM $TABLE_NAME3", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(ID))

                datalIst.add(id)
            }while (cursor.moveToNext())
        }

        return datalIst
    }

    fun deleteAllData() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }

    fun deleteAllData2() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME2, null, null)
    }

    fun deleteAllData3() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME3, null, null)
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "Stunting_DB"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "stunting"

        val TABLE_NAME2 = "stunting_get"

        val TABLE_NAME3 = "stunting_del"

        // below is the variable for id column
        val ID = "id"

        val USER_ID = "user_id"

        val USERNAME = "username"

        val UMUR = "umur"

        val TINGGI_BADAN = "tinggi_badan"

        val BERAT_BADAN = "berat_badan"

        val STUNTING = "stunting"

        val CREATE_AT = "created_at"

        val UPDATE_AT = "updated_at"
    }
}
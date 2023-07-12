package com.example.stunting.SqliteHelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite. SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Calendar
import com.example.stunting.response.Stunting
import com.example.stunting.helper.naivebayes.Data2

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

        val createTable2 = ("CREATE TABLE " + TABLE_NAME2 + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USERNAME + " TEXT,"
                + PASSWORD + " TEXT,"
                + CREATE_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + UPDATE_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP)")

        db?.execSQL(createTable)
        db?.execSQL(createTable2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // here we have upgraded the database
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME2")
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

    @SuppressLint("Range")
    fun getData(user_id: Int, limit: Int, search: String): List<Stunting> {
        val datalIst = ArrayList<Stunting>()
        // here we have used rawQuery to fetch the data from database.
        val db = this.readableDatabase
        val cursor: Cursor =  db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $USER_ID=$user_id AND $USERNAME LIKE '%$search%' ORDER BY $ID DESC LIMIT $limit", null)

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
    fun getData2(): ArrayList<Data2> {
        val datalIst = ArrayList<Data2>()
        // here we have used rawQuery to fetch the data from database.
        val db = this.readableDatabase
        val cursor: Cursor =  db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val umur = cursor.getDouble(cursor.getColumnIndex(UMUR))
                val tinggi = cursor.getDouble(cursor.getColumnIndex(TINGGI_BADAN))
                val berat = cursor.getDouble(cursor.getColumnIndex(BERAT_BADAN))
                val stunting = cursor.getString(cursor.getColumnIndex(STUNTING))

                val data = Data2(umur, tinggi, berat, stunting)
                datalIst.add(data)
            }while (cursor.moveToNext())
        }

        return datalIst
    }

    fun deleteData(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$ID=$id", null)
        return result != -1
    }


    fun addDataProfile(username: String, password: String): Boolean {
        // here we have created values for each column
        val db = this.writableDatabase
        val read = this.readableDatabase

        val cursor: Cursor =  read.rawQuery("SELECT * FROM ${TABLE_NAME2} WHERE ${USERNAME} = '$username'", null)
        cursor.moveToFirst()
        if (cursor.count > 0) {
            return false
        }

        val contentValues = ContentValues()
        contentValues.put(USERNAME, username)
        contentValues.put(PASSWORD, password)
        contentValues.put(CREATE_AT, Calendar.getInstance().time.toString())
        contentValues.put(UPDATE_AT, Calendar.getInstance().time.toString())

        // here we have used insert method
        val result = db.insert(TABLE_NAME2, null, contentValues)

        // now we are checking if the data is inserted or not.
        return result != -1L
    }

    @SuppressLint("Range")
    fun findDataProfile(username: String, password: String): UserObj{
        // here we have used rawQuery to fetch the data from database.
        val db = this.readableDatabase
        val cursor: Cursor =  db.rawQuery("SELECT * FROM ${TABLE_NAME2} WHERE ${USERNAME} = '$username' AND $PASSWORD = '$password'", null)
        cursor.moveToFirst()

        val user : UserObj = UserObj

        if (cursor.count > 0) {
            user.id = cursor.getInt(cursor.getColumnIndex(ID))
            user.username = cursor.getString(cursor.getColumnIndex(USERNAME))
            user.created_at = cursor.getString(cursor.getColumnIndex(CREATE_AT))
            user.updated_at = cursor.getString(cursor.getColumnIndex(UPDATE_AT))
        }

        return user
    }

    @SuppressLint("Range")
    fun updateDataProfile(username: String, password: String): UserObj{
        // here we have used rawQuery to fetch the data from database.
        val db = this.readableDatabase
        val result =  db.rawQuery("UPDATE ${TABLE_NAME2} SET ${USERNAME} = '$username', $PASSWORD = '$password', ${UPDATE_AT} = CURRENT_TIMESTAMP WHERE ${USERNAME} = '$username'", null)

        return findDataProfile(username, password)
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "Stunting_DB"

        // below is the variable for database version
        private val DATABASE_VERSION = 3

        // below is the variable for table name
        val TABLE_NAME = "stunting"

        val TABLE_NAME2 = "user"

        // below is the variable for id column
        val ID = "id"

        val USER_ID = "user_id"

        val USERNAME = "username"

        val PASSWORD = "password"

        val UMUR = "umur"

        val TINGGI_BADAN = "tinggi_badan"

        val BERAT_BADAN = "berat_badan"

        val STUNTING = "stunting"

        val CREATE_AT = "created_at"

        val UPDATE_AT = "updated_at"
    }

    object UserObj {
        var id: Int = 0
        var username: String = ""
        var created_at: String = ""
        var updated_at: String = ""
    }
}
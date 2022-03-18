package com.example.memberadmin

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context?, name:String?, factory: SQLiteDatabase.CursorFactory?, version: Int)
    : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) { // 자동 호출
        var sql : String = " CREATE TABLE IF NOT EXISTS MYTABLE( " +
                "    SEQ INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "    TXT TEXT) "  //<추후 컬럼명 변경 : NICKNAME, PROFLMSG>
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var sql : String = " DROP TABLE IF EXISTS MYTABLE "
        db?.execSQL(sql)
        onCreate(db)
    }

    fun insert(db: SQLiteDatabase, txt:String){
        var sql = " INSERT INTO MYTABLE(TXT) " +
                "   VALUES('${txt}') " // <추후 컬럼명 변경 : NICKNAME, PROFLMSG>

        db.execSQL(sql)
    }



}







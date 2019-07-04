package com.samsad.notes

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DBManager {
    val dbName = "MyNotes"
    val dbtable = "Notes"
    var colId = "ID"
    var colTitle = "Title"
    var colDescription = "Description"
    val dbVersion = 1

    val sqlCrateTable = "CREATE TABLE IF NOT EXISTS "+dbtable+"("+colId+" INTEGER PRIMARY KEY,"+
            colTitle+" TEXT,"+colDescription+" TEXT);"
    var sqlDB:SQLiteDatabase?=null

    constructor(context: Context){
        val db = DatabaseHelperNotes(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperNotes:SQLiteOpenHelper{

        var context:Context?=null

        constructor(context: Context):super(context,dbName,null,dbVersion){
            this.context=context
        }
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCrateTable)
            Toast.makeText(context,"Database is created",Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("DROP TABLE IF EXISTS "+dbtable)
        }

    }

    fun Insert(value:ContentValues): Long {
        val Id = sqlDB!!.insert(dbtable,"", value)
        return Id;
    }

    fun Query(projection:Array<String>,selection:String,selectionArgs:Array<String>,order:String):Cursor {
        val db = SQLiteQueryBuilder()
        db.tables=dbtable
        val cursor:Cursor = db.query(sqlDB,projection,selection,selectionArgs,null,null,order)
        return cursor

    }

    fun Delete(selection:String,selectionArgs:Array<String>):Int {
        val args = selectionArgs
        val count = sqlDB!!.delete(dbtable,selection,selectionArgs)
        return count
    }

    fun Update(contentValues: ContentValues,selection: String, selectionArgs: Array<String>):Int{
        val count = sqlDB!!.update(dbtable,contentValues,selection,selectionArgs)
        return  count
    }
}
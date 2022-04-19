package com.example.quick_tick

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.Month

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                DATE_YEAR_COL + " TEXT," +
                DATE_MONTH_COL + " TEXT," +
                DATE_DAY_COL + " TEXT," +
                START_COL + " TEXT," +
                END_COL + " TEXT," +
                REPEAT_COL + " TEXT," +
                DESCR_COL + " TEXT," +
                TAGS_COL + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addData(name : String, date_year : String, date_month : String, date_day : String, start : String, end : String, repeat : String, descr : String, tags : String){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(NAME_COl, name)
        values.put(DATE_YEAR_COL, date_year)
        values.put(DATE_MONTH_COL, date_month)
        values.put(DATE_DAY_COL, date_day)
        values.put(START_COL, start)
        values.put(END_COL, end)
        values.put(REPEAT_COL, repeat)
        values.put(DESCR_COL, descr)
        values.put(TAGS_COL, tags)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getData(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $DATE_YEAR_COL ASC, $DATE_MONTH_COL ASC, $DATE_DAY_COL ASC;", null)

    }

    fun getSpecData(year: String, month: String, day: String): Cursor?{
        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $DATE_YEAR_COL = $year AND $DATE_MONTH_COL = $month AND $DATE_DAY_COL = $day ", null)
    }

    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "Quick_Tick"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "tasks_table"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val NAME_COl = "name"

        // below is the variable for date column
        val DATE_YEAR_COL = "year"
        val DATE_MONTH_COL = "month"
        val DATE_DAY_COL = "day"

        // below is the variable for start column
        val START_COL = "start"

        // below is the variable for end column
        val END_COL = "end"

        // below is the variable for repeat column
        val REPEAT_COL = "repeat"

        // below is the variable for descr column
        val DESCR_COL = "desct"

        // below is the variable for tags column
        val TAGS_COL = "tags"


    }
}
package me.nikantchaudhary.taxifare;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nikant20 on 24-10-2016.
 */

public class DBHandler extends SQLiteOpenHelper {
    SQLiteDatabase db;
    public static String DBNAME = "FareDetails";
    public static int DBVER = 1;



    public DBHandler(Context context) {
        super(context,DBNAME, null, DBVER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS ratecalculator(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,baseFare INTEGER NOT NULL,fare INTEGER );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public long insertFareDetails(String baseFare, String fare){
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BaseFare",baseFare);
        contentValues.put("Fare",fare);


        long id = db.update("ratecalculator",contentValues,null,null);
        return id;
    }
/**
    public long updateFareDetails(String baseFare,String fare){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("BaseFare",baseFare);
        cv.put("Fare",fare);


        long id = db.update("ratecalculator",cv,null,null);
        return  id;
    }
 **/


}

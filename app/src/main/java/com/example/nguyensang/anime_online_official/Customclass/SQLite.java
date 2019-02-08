package com.example.nguyensang.anime_online_official.Customclass;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLite extends SQLiteOpenHelper {
    public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    // truy vấn không trả kết quả
    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public void Insert(String ten, String link, String date, String linkAnh){
        SQLiteDatabase database = getWritableDatabase();
        String sql="INSERT INTO DuLieu VALUES(null,?,?,?,?)";
        SQLiteStatement statement =  database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, ten);
        statement.bindString(2, link);
        statement.bindString(3, date);
        statement.bindString(4, linkAnh);
        statement.executeInsert();
    }

    public void Insert2(String TapPhim){
        SQLiteDatabase database = getWritableDatabase();
        String sql="INSERT INTO DuLieuTapPhim VALUES(null,?)";
        SQLiteStatement statement =  database.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, TapPhim);
        statement.executeInsert();
    }

    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.example.listview_database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.listview_database.MyDatabaseHelper;

public class ContactInfoDao {
    private MyDatabaseHelper helper;

    public ContactInfoDao(Context context, String name, String tableName, SQLiteDatabase.CursorFactory factory, int version) {
        helper = new MyDatabaseHelper(context, name, tableName, factory, version);
    }

    public long add(String name, String phone) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        long rowId = db.insert(helper.getTableName(), null, values);
        db.close();
        return rowId;
    }

    public int delete(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowId = db.delete(helper.getTableName(), "name = ?", new String[]{name});
        db.close();
        return rowId;
    }

    public int update(String name, String phone) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        int rowId = db.update(helper.getTableName(), values, "name = ?", new String[]{name});
        db.close();
        return rowId;
    }

    public Cursor query(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(helper.getTableName(), null, "name = ?", new String[]{name}, null, null, null);
        return cursor;
    }
}

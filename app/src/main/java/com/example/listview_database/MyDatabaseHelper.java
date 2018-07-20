package com.example.listview_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;
    private String tableName;
    private String TAG = "MyDatabaseHelper";
    public final String CREATE_TABLE;

    public MyDatabaseHelper(Context context, String name, String tableName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        this.tableName = tableName;
        CREATE_TABLE = "create table " + tableName + "(" +
                "id integer primary key autoincrement," +
                "name char(20), " +
                "phone varchar(20));";
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
        db.execSQL(CREATE_TABLE);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");
        db.execSQL("drop table if exits " + tableName);
        onCreate(db);
    }
}

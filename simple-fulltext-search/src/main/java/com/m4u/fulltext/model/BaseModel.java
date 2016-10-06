package com.m4u.fulltext.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.m4u.fulltext.helper.StringHelper;

public abstract class BaseModel extends SQLiteOpenHelper {

    private Context context;
    private SQLiteDatabase db;
    private String dbName;
    private String vTableName;
    private int dbVersion;
    private String[] tableCols;

    public BaseModel(Context context, String dbName, int dbVersion, String vTableName, String[] tableCols) {
        super(context, dbName, null, dbVersion);
        this.context = context;
        this.dbName = dbName;
        this.vTableName = vTableName;
        this.dbVersion = dbVersion;
        this.tableCols = tableCols;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String vTableCreate = StringHelper.makeCreateVirtualTableSQL(this.vTableName, this.tableCols);
        this.db.execSQL(vTableCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + this.vTableName);
        onCreate(db);
    }

    public int getDbVersion() {
        return dbVersion;
    }

    public String getvTableName() {
        return vTableName;
    }

    public String getDbName() {
        return dbName;
    }

    public String[] getTableCols() {
        return tableCols;
    }

}
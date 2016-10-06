package com.doberman.fulltext.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenericSearchModel extends BaseModel {

    public GenericSearchModel(Context context, String dbName, int dbVersion, String vTableName, String[] tableCols) {
        super(context, dbName, dbVersion, vTableName, tableCols);
    }

    public void insert(ContentValues contentValues) {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        writableDatabase.insert(getvTableName(), null, contentValues);
    }

    public List<HashMap<String, String>> searchByKeyword(String keyword, String[] projectionIn) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(getvTableName());
        Cursor cursor = builder.query(
                readableDatabase,
                projectionIn,
                getvTableName() + " MATCH ? ",
                new String[]{prepareKeyword(keyword)},
                null,
                null,
                null);
        return cursorToMap(cursor);
    }

    public List<HashMap<String, String>> searchByKeyword(String keyword) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(getvTableName());
        Cursor cursor = builder.query(
                readableDatabase,
                getTableCols(),
                getvTableName() + " MATCH ? ",
                new String[]{prepareKeyword(keyword)},
                null,
                null,
                null);

        return cursorToMap(cursor);
    }

    private List<HashMap<String, String>> cursorToMap(Cursor cursor) {
        List<HashMap<String, String>> listMapSearchItems = new ArrayList<>();
        while (cursor.moveToNext()) {
            HashMap<String, String> mapSearchItems = new HashMap();
            for (String column : cursor.getColumnNames()) {
                int columnIndex = cursor.getColumnIndex(column);
                String value = cursor.getString(columnIndex);
                mapSearchItems.put(column, value);
            }
            listMapSearchItems.add(mapSearchItems);
        }
        return listMapSearchItems;
    }

    private String prepareKeyword(String keyword) {
        String[] newStr = keyword.split(" ");
        for (int i = 0; i < newStr.length; i++) {
            newStr[i] = newStr[i] + "*";
        }
        return TextUtils.join(" ", newStr);
    }

}

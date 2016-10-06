package com.m4u.fulltext.helper;

import android.text.TextUtils;

public class StringHelper {

    public static String makeCreateVirtualTableSQL(String vTableName, String[] tableCols) {
        return buildTableCols(vTableName, tableCols);
    }

    private static String buildTableCols(String vTableName, String[] tableCols) {
        String createSql = "CREATE VIRTUAL TABLE " + vTableName + " USING fts4(%s)";
        String cols = TextUtils.join(",", tableCols);
        return String.format(createSql, cols);
    }


}

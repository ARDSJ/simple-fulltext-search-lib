package com.doberman.fulltext;


import android.app.Application;
import android.content.ContentValues;
import android.os.Build;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class SimpleFullTextSearchTest {

    private SimpleFullTextSearch simpleFullTextSearch;
    private Application context;
    private static final String DB_NAME = "db_test";
    private static final String V_TABLE_NAME = "vtb_search";
    private static final String[] V_TABLE_COLS = new String[]{"product", "description", "price"};
    private static final int DB_VERSION = 1;

    @Before
    public void setup() {
        this.context = RuntimeEnvironment.application;
        this.simpleFullTextSearch = new SimpleFullTextSearch(context, DB_NAME, DB_VERSION, V_TABLE_NAME, V_TABLE_COLS);
    }

    @Test
    public void testInsertItemsWithSuccess() {

        ContentValues contentValues = new ContentValues();
        contentValues.put("product", "Product A");
        contentValues.put("description", "Description Product A");
        contentValues.put("price", "1221,10");

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("product", "Product B");
        contentValues1.put("description", "Description Product B");
        contentValues1.put("price", "21,10");

        this.simpleFullTextSearch.insert(contentValues);
        this.simpleFullTextSearch.insert(contentValues1);

        testIfItemsInserted();

    }

    private void testIfItemsInserted() {

        List<HashMap<String, String>> hashMaps = this.simpleFullTextSearch.searchByKeyword("Product");

        Assert.assertEquals(hashMaps.size(),2);

        HashMap<String, String> item1 = hashMaps.get(0);
        HashMap<String, String> item2 = hashMaps.get(1);

        Assert.assertTrue(item1.get("product").equals("Product A"));
        Assert.assertTrue(item1.get("description").equals("Description Product A"));
        Assert.assertTrue(item1.get("price").equals("1221,10"));

        Assert.assertTrue(item2.get("product").equals("Product B"));
        Assert.assertTrue(item2.get("description").equals("Description Product B"));
        Assert.assertTrue(item2.get("price").equals("21,10"));

    }




}
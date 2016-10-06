package com.m4u.fulltext;

import android.content.ContentValues;
import android.content.Context;

import com.m4u.fulltext.model.GenericSearchModel;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class SimpleFullTextSearch {

    private final GenericSearchModel searchModel;

    public SimpleFullTextSearch(Context context, String dbName, int dbVersion, String vTableName, String[] tableCols) {
        this.searchModel = new GenericSearchModel(context, dbName, dbVersion, vTableName, tableCols);
    }

    public void insert(ContentValues contentValues) {
        this.searchModel.insert(contentValues);
    }

    public List<HashMap<String, String>> searchByKeyword(String keyword, String[] projectionIn) {
        return this.searchModel.searchByKeyword(keyword, projectionIn);
    }

    public List<HashMap<String, String>> searchByKeyword(String keyword) {
        return this.searchModel.searchByKeyword(keyword);
    }

    public Observable<List<HashMap<String, String>>> searchByKeywordAsync(final String keyword) {
        return Observable.create(new Observable.OnSubscribe<List<HashMap<String, String>>>() {
            @Override
            public void call(Subscriber<? super List<HashMap<String, String>>> subscriber) {
                try {
                    List<HashMap<String, String>> hashMaps = searchModel.searchByKeyword(keyword);
                    subscriber.onNext(hashMaps);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}

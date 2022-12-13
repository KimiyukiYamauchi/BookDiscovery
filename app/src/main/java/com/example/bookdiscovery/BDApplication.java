package com.example.bookdiscovery;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BDApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Realmをインスタンス化
        Realm.init(this);
        // Realm データベースを設定
        RealmConfiguration conf = new RealmConfiguration.Builder().name("BookDiscovery.realm").build();
        Realm.setDefaultConfiguration(conf);
    }
}

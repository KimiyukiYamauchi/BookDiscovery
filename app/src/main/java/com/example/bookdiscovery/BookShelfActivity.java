package com.example.bookdiscovery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class BookShelfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_shelf);


        // FragmentContainerにResultListFragmentを表示させる処理
        BookShelfFragment bookShelfFragment = BookShelfFragment.newInstance();
        // Activity内で表示するFragmentを管理するクラスをインスタンス化
        FragmentManager fm = getSupportFragmentManager();
        // Fragmentを表示、または別のFragmentに遷移するためのクラスをインスタンス化
        FragmentTransaction ft = fm.beginTransaction();
        // FragmentManagerに新しいFragmentを追加
        // FragmentContainerにResultListFragmentを表示するよう設定
        ft.add(R.id.FragmentContainer2, bookShelfFragment);
        // 上記の設定でFragmentManagerを更新
        ft.commit();


    }
}
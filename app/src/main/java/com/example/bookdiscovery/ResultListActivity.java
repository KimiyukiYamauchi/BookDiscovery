package com.example.bookdiscovery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class ResultListActivity extends AppCompatActivity {

    private String term;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);

        // 画面遷移時のデータが空でない場合
        if (getIntent().hasExtra("terms")) {
            // Key:termsにデータがあればValueを代入
            String term = getIntent().getStringExtra("terms");
            if (term.length() == 0) {
                term = "Android";
            }
        } else {
            // 画面遷移時のデータがからの場合は "Android"と文字列を代入
            term = "Android";
        }

        // FragmentContainerにResultListFragmentを表示させる処理
        ResultListFragment resultListFragment = ResultListFragment.getInstance(term);
        // Activity内で表示するFragmentを管理するクラスをインスタンス化
        FragmentManager fm = getSupportFragmentManager();
        // Fragmentを表示、または別のFragmentに遷移するためのクラスをインスタンス化
        FragmentTransaction ft = fm.beginTransaction();
        // FragmentManagerに新しいFragmentを追加
        // FragmentContainerにResultListFragmentを表示するよう設定
        ft.add(R.id.FragmentContainer, resultListFragment);
        // 上記の設定でFragmentManagerを更新
        ft.commit();
    }
}

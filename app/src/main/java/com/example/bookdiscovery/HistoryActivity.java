package com.example.bookdiscovery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // 画面コンポーネント関連付け
        TextView emptyRecyclerText = findViewById(R.id.EmptyRecyclerText);
        RecyclerView historyRecycler = findViewById(R.id.HistoryRecycler);
        // Realmクラスをインスタンス化
        Realm realm = Realm.getDefaultInstance();
        // 検索履歴テーブルのデータを全て取得
        RealmResults<SearchHistoryModel> resultData = realm.where(SearchHistoryModel.class).findAll();
        // 検索履歴の件数が１件以上なら繰り返し処理でTextViewに表示する
        if (!resultData.isEmpty() && resultData.size() > 0) {
            // adapterクラスをインスタンス化
            HistoryRecyclerAdapter historyAdapter = new HistoryRecyclerAdapter(this, resultData);
            // RecyclerViewの表示形式を決める
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            // RecyclerViewの初期設定
            historyRecycler.setAdapter(historyAdapter);
            historyRecycler.setLayoutManager(layoutManager);
        } else {
            // 検索履歴の件数が１件もない場合、履歴0件のメッセージを表示する
            // 検索履歴一覧を非表示に設定
            historyRecycler.setVisibility(View.GONE);
            emptyRecyclerText.setVisibility(View.VISIBLE);
        }
    }
}
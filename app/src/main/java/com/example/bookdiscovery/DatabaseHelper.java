package com.example.bookdiscovery;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    // データベースファイル名の定義フィールド
    private static final String DATABASE_NAME = "bookshelf.db";
    // バージョン情報の定義フィールド
    private static final int DATABASE_VERSION = 1;

    // コンストラクタ
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブル作成用SQL文字列の作成。
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE books (");
        sb.append("title TEXT PRIMARY KEY,");
        sb.append("subTitle TEXT,");
        sb.append("authors TEXT,");
        sb.append("publishedDate TEXT,");
        sb.append("description TEXT,");
        sb.append("pageCount INTEGER,");
        sb.append("smallThumbnail TEXT,");
        sb.append("thumbnail TEXT,");
        sb.append("medium TEXT");
        sb.append(");");
        String sql = sb.toString();

        // SQLの実行
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

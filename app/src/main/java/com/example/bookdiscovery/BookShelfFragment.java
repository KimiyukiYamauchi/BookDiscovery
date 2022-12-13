package com.example.bookdiscovery;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookShelfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookShelfFragment extends Fragment implements AdapterView.OnItemClickListener {

    // 検索結果一覧データ
    private List<ResultListModel> resultList;

    // xmlファイルのコンポーネントと関連付ける要素
    private ListView resultListView;
    // ListViewの表示内容を管理するクラス
    private BookShelfAdapter adapter;

    // データベースヘルパーオブジェクト
    private DatabaseHelper _helper;

    public BookShelfFragment() {
        // Required empty public constructor
    }

    public static BookShelfFragment newInstance() {
        // ResultListFragmentインスタンスを生成
        BookShelfFragment fragment = new BookShelfFragment();
        // 生成したResultListFragmentを返却
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DBヘルパーオブジェクトを生成
        _helper = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_shelf, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // xmlファイルのコンポーネントと関連付け
        resultListView =  getView().findViewById(R.id.FragmentBookShelfListView);

        // データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
        SQLiteDatabase db = _helper.getWritableDatabase();
        // SQL文字列の用意。
        String sql = "SELECT * FROM books";
        // SQLの実行
        Cursor cursor = db.rawQuery(sql, null);

        // 蔵書モデルクラスリスト
        resultList = new ArrayList<>();
        // SQL実行の戻り値であるカーソルオブジェクトをスールさせてデータベース内のデータを取得
        while (cursor.moveToNext()) {
            // 蔵書データクラスをインスタンス化
            ResultListModel resultData = new ResultListModel();

            // カラムのインデックス値を取得。
            int idxTitle = cursor.getColumnIndex("title");
            // タイトルをモデルクラスに代入
            resultData.title = cursor.getString(idxTitle);
            // カラムのインデックス値を取得。
            int idxsummary = cursor.getColumnIndex("description");
            // サマリーをモデルクラスに代入
            resultData.summary = cursor.getString(idxsummary);

            // 蔵書情報をリストに登録
            resultList.add(resultData);
        }

        // ListViewに表示する情報をまとめるAdapterをインスタンス化
        adapter = new BookShelfAdapter(getContext(), resultList);
        // ListViewに表示情報をまとめたAdapterをセット
        resultListView.setAdapter(adapter);
        // ListViewに行をクリックした時のイベントを登録
        resultListView.setOnItemClickListener(BookShelfFragment.this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onDestroy() {
        // DBヘルパーオブジェクトを解放。
        _helper.close();
        super.onDestroy();
    }
}
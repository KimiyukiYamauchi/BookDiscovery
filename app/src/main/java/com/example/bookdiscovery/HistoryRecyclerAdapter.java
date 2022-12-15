package com.example.bookdiscovery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.RealmResults;

public class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryHolder> {
    // 表示に必要なクラスを宣言
    private final Context context;
    private final LayoutInflater inflater;
    private final RealmResults<SearchHistoryModel> historyData;

    // コンストラクタ
    public HistoryRecyclerAdapter(Context context, RealmResults<SearchHistoryModel> historyData) {
        // レイアウトのインスタンス化に必要なクラス
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        // 表示するデータリスト
        this.historyData = historyData;
    }

    // 表示するレイアウトを指定するメソッド
    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // row_history_recycleのレイアウトをインスタンス化
        View view = inflater.inflate(R.layout.row_history_recycle, parent, false);
        // HistoryHolderクラスをインスタンス化して返却する
        return new HistoryHolder(view);
    }

    // 表示するレイアウトにデータを設定するメソッド
    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {
        // 検索履歴一覧から一つの履歴データを抽出
        SearchHistoryModel history = historyData.get(position);
        // ViewHolderにデータをセット
        holder.historyDate.setText(history.getSearchDate());
        holder.historyTerm.setText(history.getSearchTerm());
    }

    // 表示するリストの件数を指定するメソッド
    @Override
    public int getItemCount() {
        // RealmDBから取得した検索履歴全件分、行の表示処理を繰り返す
        return historyData.size();
    }

    // ViewHolderパターンクラス
    class HistoryHolder extends RecyclerView.ViewHolder {
        // Rowレイアウトと関連づけるコンポーネントを宣言
        public TextView historyDate;
        public TextView historyTerm;

        // コンストラクタ
        public HistoryHolder(View itemView) {
            super(itemView);
            // xmlファイルと関連付け
            historyDate = itemView.findViewById(R.id.RowHistoryDate);
            historyTerm = itemView.findViewById(R.id.RowHistoryTerm);
        }
    }
}

package com.example.bookdiscovery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ResultListAdapter extends android.widget.BaseAdapter {

    private List<ResultListModel> resultList;
    private LayoutInflater layoutInflater;

    // コンストラクタ
    public ResultListAdapter(Context context, List<ResultListModel> resultList) {
        this.resultList = resultList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // 一覧表示する要素数を返却する
        return resultList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.row_result_list, viewGroup, false);
        }

        TextView titleView = view.findViewById(R.id.RowListTitle);
        TextView summaryView = view.findViewById(R.id.RowListSummary);

        titleView.setText(resultList.get(i).title);
        summaryView.setText(resultList.get(i).summary);

        return view;
    }
}

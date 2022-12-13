package com.example.bookdiscovery;

import io.realm.RealmObject;

public class SearchHistoryModel extends RealmObject {
    // 検索日時カラム
    private String searchDate;
    // 検索文字列カラム
    private String searchTerm;

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }
}

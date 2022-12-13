package com.example.bookdiscovery;

import java.util.List;

public class DetailDataModel {
    // 蔵書単体リンク
    public String selfLink;
    // 蔵書概要データ
    public VolumeInfo volumeInfo;

    // 蔵書概要クラス
    public class VolumeInfo {
        // 蔵書タイトル
        public String title;
        // 蔵書サブタイトル
        public String subTitle;
        // 蔵書著者リスト
        public List<String> authors;
        // 蔵書発売日
        public String publishedDate;
        // 蔵書概要
        public String description;
        // 蔵書ページ数
        public int pageCount;
        // 蔵書サムネイル画像URL
        public ImageLinks imageLinks;
        // Google BooksへのリンクURL
        public String previewLink;
        // Play StoreへのリンクURL
        public String infoLink;
    }

    // 蔵書サムネイルクラス
    public class ImageLinks {
        // 蔵書小サイズサムネイル
        public String smallThumbnail;
        // 蔵書サムネイル
        public String thumbnail;
        // 中サイズ表示画像
        public String medium;
    }
}

package com.example.bookdiscovery;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DetailFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // データ渡しのキー情報
    private final static String BUNDLE_KEY = "BUNDLE_SELFLINK";

    // xmlファイルのコンポーネントと関連付ける要素
    private TextView titleText;
    private TextView subTitleText;
    private TextView authorText;
    private TextView descriptText;
    private TextView pageText;
    private TextView publishDateText;
    private ImageView detailImage;
    // Play Store リンクURL
    private String infoLink;
    // 個体リンクのURL
    private String selfLink;

    // メインスレッドに戻ってくるためのHandler
    private Handler handler;

    // データベースヘルパーオブジェクト
    private DatabaseHelper _helper;
    private DetailDataModel detailData;

    // スタティックコンストラクタ
    public static DetailFragment getInstance(String selfLink) {
        // DetailFragmentインスタンスを生成
        DetailFragment fragment = new DetailFragment();
        // DetailFragmentに渡すデータ格納クラスを生成
        Bundle args = new Bundle();
        // 検索文字列データを連携データにセット
        args.putString(BUNDLE_KEY, selfLink);
        // データ格納クラスをDetailFragmentインスタンスにセット
        fragment.setArguments(args);
        // 生成したDetailFragmentを返却
        return fragment;
    }

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DBヘルパーオブジェクトを生成
        _helper = new DatabaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.v("BookDiscovery", "onViewCreated");

        // Handlerをインスタンス化
        handler = new Handler();

        // 連携データが存在するか確認
        if (getArguments() != null) {
            // 連携データ内から"BUNDLE_SELFLINK"キーのデータを代入、なければ"Android"と文字列を代入
            selfLink = getArguments().getString(BUNDLE_KEY, "");
        }

        // selfLinkが空の場合は検索結果一覧画面に強制バック
        if (TextUtils.isEmpty(selfLink)) {
            getParentFragmentManager().popBackStack();
        }

        // xmlファイルのコンポーネントと関連付け
        titleText = getView().findViewById(R.id.DetailTitle);
        subTitleText = getView().findViewById(R.id.DetailSubTitle);
        authorText = getView().findViewById(R.id.DetailAuthor);
        descriptText = getView().findViewById(R.id.DetailDescription);
        pageText = getView().findViewById(R.id.DetailPageText);
        publishDateText = getView().findViewById(R.id.DetailPublishDateText);
        detailImage = getView().findViewById(R.id.DetailImage);
        Button transWebviewBtn = getView().findViewById(R.id.TransitionWebView);
        Button transitionBrowserBtn = getView().findViewById(R.id.TransitionBrouser);
        Button registdbBtn = getView().findViewById(R.id.RegistDB);

        // クリック時にブラウザアプリでURLを表示する処理を実装
        transitionBrowserBtn.setOnClickListener(this);

        // BTWebViewFragmentへの遷移処理を実装
        transWebviewBtn.setOnClickListener(this);

        // 詳細画面の書籍情報をデータベースに登録
        registdbBtn.setOnClickListener(this);


        // OkHttp通信クライアントをインスタンス化
        OkHttpClient okHttpClient = new OkHttpClient();
        // 通信するための情報
        // ResultListFragmentから取得したselfLinkURLにREST API通信を行う
        Request request = new Request.Builder().url(selfLink).build();
        // データの取得後の命令を実装
        Callback callBack = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                // 通信に失敗した原因をログに出力
                Log.e("failure API Response", e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, Response response) throws IOException {
                // JsonパースライブラリGsonのインスタンス化
                Gson gson = new Gson();
                // 返却されたJson文字列を一旦変数に代入
                String jsonString = response.body().string();
                // DetailDataModelクラスに代入
                detailData = gson.fromJson(jsonString, DetailDataModel.class);
                // Play Store へのリンクを代入
                infoLink = detailData.volumeInfo.infoLink;
                // パースが正常に行えたかLogcatに出力して確認。
                Log.d("BookDiscovery", detailData.volumeInfo.title);
                // メインスレッドで実行する処理をインスタンス化
                DetailFragment.ReflectDetail reflectResult = new DetailFragment.ReflectDetail(detailData);
                // Handlerにてメインスレッドに処理を戻し、ReflectResultのrunメソッドを実行する
                handler.post(reflectResult);
            }
        };
        // 非同期処理でREST API通信を実行
        okHttpClient.newCall(request).enqueue(callBack);
    }

    // ボタンクリック時のイベントを実装
    @Override
    public void onClick(View view) {
        // クリックされたボタンをIDで判定
        if (view.getId() == R.id.TransitionWebView) {
            // "WebViewで確認"ボタンをクリックした場合
            // BTWebViewFragmentをインスタンス化
            BTWebViewFragment fragment = BTWebViewFragment.getInstance(infoLink);
            // 別のFragmentに遷移するためのクラスをインスタンス化
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            // 現在、DetailFragmentを表示しているR.id.FragmentContainerをBTWebViewFragmentに置き換え
            ft.replace(R.id.FragmentContainer, fragment);
            // 表示していたFragmentをバックスタックに追加
            ft.addToBackStack(null);
            // 変更を反映
            ft.commit();
        } else if (view.getId() == R.id.TransitionBrouser) {
            // "ブラウザアプリで確認"ボタンをクリックした場合
            // ブラウザアプリで表示するURLをUriクラスにキャスト
            Uri uri = Uri.parse(infoLink);
            // ブラウザアプリで開くためのIntentをインスタンス化
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            // ブラウザアプリで指定URLを表示する
            startActivity(intent);
        } else if (view.getId() == R.id.RegistDB) {
//            Log.d("DetailFragment", "データベースに登録する処理");

            // データベースヘルパーオブジェクトからデータベース接続オブジェクトを取得
            SQLiteDatabase db = _helper.getWritableDatabase();

            // まず、リストで選択された書籍のデータを削除。その後インサートを行う
            // 削除用SQL文字列を用意
            String sqlDelete = "DELETE FROM books WHERE title = ?";
            // SQL文字列を元にプリペアードステートメントを取得
            SQLiteStatement stmt = db.compileStatement(sqlDelete);
            // 変数のバインド
            stmt.bindString(1, detailData.volumeInfo.title);
            // 削除SQLの実行
            stmt.executeUpdateDelete();

            // インサート用のSQL文字列の用意
            String sqlInsert = "INSERT INTO books " +
                    "(title, subTitle, authors, publishedDate, description, pageCount, smallThumbnail, " +
                    "thumbnail, medium) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            // SQL文字列を元にプリペアードステートメントを取得
            stmt = db.compileStatement(sqlInsert);
            // 変数のバインド
            stmt.bindString(1, detailData.volumeInfo.title);
//            Log.d("DetailFragment", detailData.volumeInfo.title);
            if (detailData.volumeInfo.subTitle != null) {
                stmt.bindString(2, detailData.volumeInfo.subTitle);
            } else {
                stmt.bindString(2, "");
            }
            String authorString = "";
            if (detailData.volumeInfo.authors != null) {
                // 著作者名が複数設定されていう場合があるので繰り返し処理で全て表示する
                for (String author : detailData.volumeInfo.authors) {
                    authorString += author + ",";
                }
                authorString = authorString.substring(0, authorString.length()-1);
            }
            stmt.bindString(3, authorString);
            stmt.bindString(4, detailData.volumeInfo.publishedDate);
            if (detailData.volumeInfo.description != null) {
                stmt.bindString(5, detailData.volumeInfo.description);
            } else {
                stmt.bindString(5, "");
            }
            stmt.bindLong(6, detailData.volumeInfo.pageCount);
            stmt.bindString(7, detailData.volumeInfo.imageLinks.smallThumbnail);
            stmt.bindString(8, detailData.volumeInfo.imageLinks.thumbnail);
            if (detailData.volumeInfo.imageLinks.medium != null) {
                stmt.bindString(9, detailData.volumeInfo.imageLinks.medium);
            } else {
                stmt.bindString(9, "");
            }
            // インサートSQLの実行
            stmt.executeInsert();

        }
    }

    // REST APIで取得したデータを画面に反映するためのクラス
    private class ReflectDetail implements Runnable {
        // 蔵書詳細データ
        DetailDataModel detailData;

        // コンストラクタ
        public ReflectDetail(DetailDataModel detailData) {
            this.detailData = detailData;
        }

        // Handlerから実行されるメソッド
        @Override
        public void run() {
            // タイトルを反映
            titleText.setText(detailData.volumeInfo.title);
            // サブタイトルが取得できていたら反映
            if (!TextUtils.isEmpty(detailData.volumeInfo.subTitle)) {
                subTitleText.setText(detailData.volumeInfo.subTitle);
            }
            // 概要が取得できていたら反映
            if (!TextUtils.isEmpty(detailData.volumeInfo.description)) {
                descriptText.setText(detailData.volumeInfo.description);
            }
            // 著作者名が取得できていたら反映
            if (detailData.volumeInfo.authors != null && detailData.volumeInfo.authors.size() > 0) {
                String authorString = new String();
                // 著作者名が複数設定されていう場合があるので繰り返し処理で全て表示する
                for (String author : detailData.volumeInfo.authors) {
                    authorString += author + ",";
                }
                authorString = authorString.substring(0, authorString.length()-1);
                authorText.setText(authorString);
            }
            // ページ数を反映
            pageText.setText(String.valueOf(detailData.volumeInfo.pageCount));
            // 発売日が取得できていたら反映
            if (!TextUtils.isEmpty(detailData.volumeInfo.publishedDate)) {
                publishDateText.setText(detailData.volumeInfo.publishedDate);
            }

            // Glideを使ってWeb上の画像をImageViewに表示させる
            if (detailData.volumeInfo.imageLinks != null) {
//                detailImage.loadUrl(detailData.volumeInfo.imageLinks.smallThumbnail);
                Log.v("BookDiscovery", "Glide " + detailData.volumeInfo.imageLinks.medium);
//
                RequestManager rm = Glide.with(DetailFragment.this);
                Log.v("BookDiscovery", "RequestManager " + rm);
                rm
                        .applyDefaultRequestOptions(RequestOptions.fitCenterTransform())
                        .load(detailData.volumeInfo.imageLinks.thumbnail)
//                        .load(detailData.volumeInfo.imageLinks.medium)
                        .listener(createLoggerListener())
                        .into(detailImage);
            }
        }
    }

    private RequestListener<Drawable> createLoggerListener() {
        return new RequestListener<Drawable>(){
            @Override
            public boolean onLoadFailed(
                    @Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                return false;
            }
            @Override
            public boolean onResourceReady(
                    Drawable resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                if (resource instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                    Log.d("BookDiscovery",
                            String.format("bitmap %,d bytes, size: %d x %d",
                                    bitmap.getByteCount(),
                                    bitmap.getWidth(),
                                    bitmap.getHeight()));
                }
                return false;
            }
        };
    }

    @Override
    public void onDestroy() {
        // DBヘルパーオブジェクトの解放
        _helper.close();
        super.onDestroy();
    }
}
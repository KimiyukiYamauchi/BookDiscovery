package com.example.bookdiscovery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgressDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgressDialogFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProgressDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgressDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgressDialogFragment newInstance(String param1, String param2) {
        ProgressDialogFragment fragment = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // ソフトバックボタンを無効化
        Objects.requireNonNull(getDialog()).setCancelable(false);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログ表示するレイアウトを生成
        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_progress_dialog, null, false);

        // アラートダイアログビルダーを使ってボタン付きのダイアログを生成
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("キャンセル", (dialogInterface, i) -> getActivity().finish())
                .setCancelable(false);
        // 表示するダイアログを生成して返却
        return builder.create();
    }
}
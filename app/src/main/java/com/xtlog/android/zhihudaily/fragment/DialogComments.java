package com.xtlog.android.zhihudaily.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.base.MyApplication;

/**
 * Created by admin on 2016/12/26.
 */

public class DialogComments extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        View dialogView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.dialog_comments, null);
        AlertDialog dialog = new AlertDialog.Builder(MyApplication.getContext())
                .setView(dialogView)
                .create();
        return dialog;
    }
}

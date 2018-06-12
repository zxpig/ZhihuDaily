package com.xtlog.android.zhihudaily.activity;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.base.MyApplication;
import com.xtlog.android.zhihudaily.models.ShortComment;

public class CommentsDialogActivity extends AppCompatActivity implements View.OnClickListener{

    private String contentText;
    private static final String TAG = "CommentsDialogActivity";
    private Button goodButton;
    private Button reportButton;
    private Button copyButton;
    private Button replyButton;

    private static final String EXTRA_CONTENT_TEXT = "extra_content_text";

    public static Intent newIntent(Context context, String contentText){
        Intent intent = new Intent(context, CommentsDialogActivity.class);
        intent.putExtra(EXTRA_CONTENT_TEXT, contentText);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comments);
        contentText = this.getIntent().getStringExtra(EXTRA_CONTENT_TEXT);

        goodButton = (Button)findViewById(R.id.dialog_comments_good_button);
        reportButton = (Button)findViewById(R.id.dialog_comments_report);
        copyButton = (Button)findViewById(R.id.dialog_comments_copy);
        replyButton = (Button)findViewById(R.id.dialog_comments_reply);

        goodButton.setOnClickListener(this);
        reportButton.setOnClickListener(this);
        copyButton.setOnClickListener(this);
        replyButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.dialog_comments_good_button:
                Toast.makeText(this, "你没有登录，无法使用此功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dialog_comments_report:
                Toast.makeText(this, "你没有登录，无法使用此功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dialog_comments_copy:

                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(contentText);
                Toast.makeText(this, "评论内容已经复制到剪贴板", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.dialog_comments_reply:
                Toast.makeText(this, "你没有登录，无法使用此功能", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}

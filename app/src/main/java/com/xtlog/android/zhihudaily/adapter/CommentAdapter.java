package com.xtlog.android.zhihudaily.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtlog.android.zhihudaily.R;
import com.xtlog.android.zhihudaily.activity.CommentsDialogActivity;
import com.xtlog.android.zhihudaily.base.MyApplication;
import com.xtlog.android.zhihudaily.models.ShortComment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by admin on 2016/12/21.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<ShortComment.Short> mShorts;
    private static final String TAG = "CommentAdapter";


    static class CommentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ShortComment.Short mShort;
        ImageView avatar;
        TextView nick;
        TextView content;
        TextView date;

        public CommentViewHolder (View view){
            super(view);
            avatar = (ImageView) view.findViewById(R.id.item_comments_tou_xiang);
            nick = (TextView)view.findViewById(R.id.item_comments_nick_text);
            content = (TextView)view.findViewById(R.id.item_comments_content);
            date = (TextView)view.findViewById(R.id.item_comments_date_text);
            view.setOnClickListener(this);

        }
        private void bindComment(ShortComment.Short ashort){
            mShort = ashort;
            nick.setText(ashort.getAuthor());
            content.setText(ashort.getContent());
            SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
            String str = format.format(ashort.getTime());
            date.setText(str);
            Picasso.with(MyApplication.getContext()).load(ashort.getAvatar()).into(avatar);
        }

        @Override
        public void onClick(View view) {
            MyApplication.getContext().startActivity(CommentsDialogActivity.newIntent(MyApplication.getContext(), mShort.getContent()));
        }
    }

    public CommentAdapter(List<ShortComment.Short> shortList){
        mShorts = shortList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments_list, parent, false);
        CommentViewHolder holder = new CommentViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        ShortComment.Short ashort = mShorts.get(position);
        holder.bindComment(ashort);

    }

    @Override
    public int getItemCount() {
        return mShorts.size();
    }
}

package com.xtlog.android.zhihudaily.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2016/12/25.
 */

public class ShortComment {
    private List<Short> comments;

    public List<Short> getComments() {
        return comments;
    }

    public void setComments(List<Short> comments) {
        this.comments = comments;
    }

    public static class Short {
        private String author;
        private int id;
        private String content;
        private int likes;
        private String avatar;
        private long time;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}

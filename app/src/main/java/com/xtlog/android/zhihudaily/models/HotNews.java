package com.xtlog.android.zhihudaily.models;

import java.util.List;

/**
 * Created by admin on 2016/12/23.
 */

public class HotNews {
    private List<RecentItem> recent;

    public HotNews(){}

    public List<RecentItem> getRecent() {
        return recent;
    }

    public void setRecent(List<RecentItem> recent) {
        this.recent = recent;
    }

    public static class RecentItem{
        private String news_id;
        private String url;
        private String thumbnail;
        private String title;

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}

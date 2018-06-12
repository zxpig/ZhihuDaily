package com.xtlog.android.zhihudaily.models;

import java.util.List;

/**
 * Created by admin on 2016/12/26.
 */

public class InSection {
    private long timestamp;
    private String name;
    private List<StoryInSection> stories;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StoryInSection> getStories() {
        return stories;
    }

    public void setStories(List<StoryInSection> stories) {
        this.stories = stories;
    }

    public static class StoryInSection{
        private List<String> images;
        private String date;
        private String display_date;
        private int id;
        private String title;

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDisplay_date() {
            return display_date;
        }

        public void setDisplay_date(String display_date) {
            this.display_date = display_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}

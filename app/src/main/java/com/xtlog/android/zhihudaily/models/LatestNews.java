package com.xtlog.android.zhihudaily.models;

import java.util.List;

/**
 * Created by admin on 2016/12/21.
 */

public class LatestNews {

    private String date;
    private List<Story> stories;
    private List<TopStory> top_stories;

    public LatestNews(){}



    public static class Story {

        private String title;
        private String ga_prefix;
        private List<String> images;
        private int type;
        private int id;

        public Story() {
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class TopStory {
        private String title;
        private String image;
        private String ga_prefix;
        private int type;
        private int id;

        public TopStory(){}

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<TopStory> getTopStories() {
        return top_stories;
    }

    public void setTopStories(List<TopStory> topStories) {
        this.top_stories = topStories;
    }

}

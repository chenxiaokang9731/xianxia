package com.bright.readapp.bean.zhihu;

import java.util.List;

/**
 * Created by chenxiaokang on 2016/10/18.
 */
public class Zhihu {

    private String date;

    private List<Stories> stories;

    private List<TopStories> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Stories> getStories() {
        return stories;
    }

    public void setStories(List<Stories> stories) {
        this.stories = stories;
    }

    public List<TopStories> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStories> top_stories) {
        this.top_stories = top_stories;
    }
}

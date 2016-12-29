package com.others.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class Recommend {

    /**
     * icon : http://img5.cache.netease.com/3g/2015/6/19/20150619145138c133a.png
     * title : 金币商城
     * description :
     * disappear :
     * url : http://c.3g.163.com/CreditMarket/default.html?source=discovery
     */

    private List<RecommendBean> recommend;

    public List<RecommendBean> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<RecommendBean> recommend) {
        this.recommend = recommend;
    }

    public static class RecommendBean {
        private String icon;
        private String title;
        private String description;
        private String disappear;
        private String url;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDisappear() {
            return disappear;
        }

        public void setDisappear(String disappear) {
            this.disappear = disappear;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

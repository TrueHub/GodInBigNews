package com.others.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class Banner {

    /**
     * title : 新年定制红包 31日下
     * image : http://img3.cache.netease.com/3g/2015/12/16/20151216102753b7798.png
     * url : http://c.3g.163.com/CreditMarket/default.html#/prize/p20151222fahongbao
     */

    private List<BannerBean> banner;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public static class BannerBean {
        private String title;
        private String image;
        private String url;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}

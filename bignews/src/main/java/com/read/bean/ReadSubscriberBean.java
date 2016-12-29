package com.read.bean;

import java.util.List;

/**
 * Created by Dell on 2016-11-18.
 */
public class ReadSubscriberBean {

    private List<BannerlistBean> bannerlist;
    private List<RecommendlistBean> recommendlist;

    public List<BannerlistBean> getBannerlist() {
        return bannerlist;
    }

    public void setBannerlist(List<BannerlistBean> bannerlist) {
        this.bannerlist = bannerlist;
    }

    public List<RecommendlistBean> getRecommendlist() {
        return recommendlist;
    }

    public void setRecommendlist(List<RecommendlistBean> recommendlist) {
        this.recommendlist = recommendlist;
    }

    public static class BannerlistBean {
        private String imgsrc;
        private String tname;
        private String tid;

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }

    public static class RecommendlistBean {
        private String docid;
        private String title;
        private String subnum;
        private String alias;
        private String tname;
        private String ename;
        private String digest;
        private String tid;

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubnum() {
            return subnum;
        }

        public void setSubnum(String subnum) {
            this.subnum = subnum;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }
}

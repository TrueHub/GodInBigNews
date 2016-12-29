package com.read.bean;

import java.util.List;

/**
 * Created by Dell on 2016-11-11.
 */
public class ReadCommandBean {

    private List<推荐Bean> 推荐;

    public List<推荐Bean> get推荐() {
        return 推荐;
    }

    public void set推荐(List<推荐Bean> 推荐) {
        this.推荐 = 推荐;
    }

    public static class 推荐Bean {
        private String docid;
        private String img;
        private String imgsrc;
        private String title;
        private String source;
        private List<String> unlikeReason;

        @Override
        public String toString() {
            return "推荐Bean{" +
                    "unlikeReasonsSize=" + unlikeReason.size() +
                    ", title='" + title + '\'' +
                    ", docid='" + docid + '\'' +
                    '}';
        }

        public void setUnlikeReason(List<String> unlikeReason) {
            this.unlikeReason = unlikeReason;
        }

        public List<String> getUnlikeReason() {
            return unlikeReason;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSource() {
            return source;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

    }
}
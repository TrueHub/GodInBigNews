package com.bignews9527.vidio.beans;

import java.util.List;

/**
 * Created by 1945374040 on 2016/11/15.
 */
public class JingPinBean {
    /**
     * topicImg : http://img1.cache.netease.com/m/newsapp/video/default.jpg
     * videosource : 新媒体
     * mp4Hd_url : http://flv2.bn.netease.com/videolib3/1511/16/UnQDK3902/HD/UnQDK3902-mobile.mp4
     * topicDesc : speed season是一档网易汽车强势打造的高清汽车类视频节目。
     * topicSid : VBA17KCVJ
     * cover : http://vimg1.ws.126.net/image/snapshot/2015/11/U/B/VB7K2EBUB.jpg
     * title : 【速度季】越野机器大冒险 决战老掌沟（上）
     * playCount : 600
     * replyBoard : video_bbs
     * sectiontitle : 速度季
     * replyid : B7K1SD49008535RB
     * description : 节目组准备的都是价格跌爆眼球的越野车
     * mp4_url : http://flv2.bn.netease.com/videolib3/1511/16/UnQDK3902/SD/UnQDK3902-mobile.mp4
     * length : 1382
     * playersize : 1
     * m3u8Hd_url : http://flv2.bn.netease.com/videolib3/1511/16/UnQDK3902/HD/movie_index.m3u8
     * vid : VB7K1SD49
     * m3u8_url : http://flv2.bn.netease.com/videolib3/1511/16/UnQDK3902/SD/movie_index.m3u8
     * ptime : 2015-11-16 11:18:25
     * topicName : 速度季
     */

    private List<VAP4BGTVDBean> VAP4BGTVD;

    public List<VAP4BGTVDBean> getVAP4BGTVD() {
        return VAP4BGTVD;
    }

    public void setVAP4BGTVD(List<VAP4BGTVDBean> VAP4BGTVD) {
        this.VAP4BGTVD = VAP4BGTVD;
    }

    public static class VAP4BGTVDBean {
        private String topicImg;
        private String videosource;
        private String mp4Hd_url;
        private String topicDesc;
        private String topicSid;
        private String cover;
        private String title;
        private int playCount;
        private String replyBoard;
        private String sectiontitle;
        private String replyid;
        private String description;
        private String mp4_url;
        private int length;
        private int playersize;
        private String m3u8Hd_url;
        private String vid;
        private String m3u8_url;
        private String ptime;
        private String topicName;

        public String getTopicImg() {
            return topicImg;
        }

        public void setTopicImg(String topicImg) {
            this.topicImg = topicImg;
        }

        public String getVideosource() {
            return videosource;
        }

        public void setVideosource(String videosource) {
            this.videosource = videosource;
        }

        public String getMp4Hd_url() {
            return mp4Hd_url;
        }

        public void setMp4Hd_url(String mp4Hd_url) {
            this.mp4Hd_url = mp4Hd_url;
        }

        public String getTopicDesc() {
            return topicDesc;
        }

        public void setTopicDesc(String topicDesc) {
            this.topicDesc = topicDesc;
        }

        public String getTopicSid() {
            return topicSid;
        }

        public void setTopicSid(String topicSid) {
            this.topicSid = topicSid;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPlayCount() {
            return playCount;
        }

        public void setPlayCount(int playCount) {
            this.playCount = playCount;
        }

        public String getReplyBoard() {
            return replyBoard;
        }

        public void setReplyBoard(String replyBoard) {
            this.replyBoard = replyBoard;
        }

        public String getSectiontitle() {
            return sectiontitle;
        }

        public void setSectiontitle(String sectiontitle) {
            this.sectiontitle = sectiontitle;
        }

        public String getReplyid() {
            return replyid;
        }

        public void setReplyid(String replyid) {
            this.replyid = replyid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMp4_url() {
            return mp4_url;
        }

        public void setMp4_url(String mp4_url) {
            this.mp4_url = mp4_url;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getPlayersize() {
            return playersize;
        }

        public void setPlayersize(int playersize) {
            this.playersize = playersize;
        }

        public String getM3u8Hd_url() {
            return m3u8Hd_url;
        }

        public void setM3u8Hd_url(String m3u8Hd_url) {
            this.m3u8Hd_url = m3u8Hd_url;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getM3u8_url() {
            return m3u8_url;
        }

        public void setM3u8_url(String m3u8_url) {
            this.m3u8_url = m3u8_url;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }
    }
}

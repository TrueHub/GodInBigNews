package com.bignews9527.vidio.beans;

import java.util.List;

/**
 * Created by 1945374040 on 2016/11/15.
 */
public class MengWuBean {
    /**
     * topicImg : http://vimg2.ws.126.net/image/snapshot/2016/5/J/Q/VBLSE3PJQ.jpg
     * videosource : 新媒体
     * mp4Hd_url : null
     * topicDesc : 分享各种狗狗、猫咪、萌宠搞笑视频。
     * topicSid : VBLSE3PJN
     * cover : http://vimg1.ws.126.net/image/snapshot/2016/11/R/R/VC50730RR.jpg
     * title : 厉害了我的猴哥！小猴子玩手机上瘾了
     * playCount : 0
     * replyBoard : video_bbs
     * videoTopic : {"alias":"分享各种狗狗、猫咪、萌宠搞笑视频。","tname":"萌宠当家","ename":"T1463108876861","tid":"T1463108876861"}
     * sectiontitle :
     * replyid : C506OF1T008535RB
     * description : 玩手机都上瘾了，厉害了我的猴哥
     * mp4_url : http://flv2.bn.netease.com/tvmrepo/2016/11/V/G/EC506N9VG/SD/EC506N9VG-mobile.mp4
     * length : 15
     * playersize : 0
     * m3u8Hd_url : null
     * vid : VC506OF1T
     * m3u8_url : http://flv2.bn.netease.com/tvmrepo/2016/11/V/G/EC506N9VG/SD/movie_index.m3u8
     * ptime : 2016-11-15 14:59:31
     * topicName : 萌宠当家
     */

    private List<VAP4BFR16Bean> VAP4BFR16;

    public List<VAP4BFR16Bean> getVAP4BFR16() {
        return VAP4BFR16;
    }

    public void setVAP4BFR16(List<VAP4BFR16Bean> VAP4BFR16) {
        this.VAP4BFR16 = VAP4BFR16;
    }

    public static class VAP4BFR16Bean {
        private String topicImg;
        private String videosource;
        private Object mp4Hd_url;
        private String topicDesc;
        private String topicSid;
        private String cover;
        private String title;
        private int playCount;
        private String replyBoard;
        /**
         * alias : 分享各种狗狗、猫咪、萌宠搞笑视频。
         * tname : 萌宠当家
         * ename : T1463108876861
         * tid : T1463108876861
         */

        private VideoTopicBean videoTopic;
        private String sectiontitle;
        private String replyid;
        private String description;
        private String mp4_url;
        private int length;
        private int playersize;
        private Object m3u8Hd_url;
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

        public Object getMp4Hd_url() {
            return mp4Hd_url;
        }

        public void setMp4Hd_url(Object mp4Hd_url) {
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

        public VideoTopicBean getVideoTopic() {
            return videoTopic;
        }

        public void setVideoTopic(VideoTopicBean videoTopic) {
            this.videoTopic = videoTopic;
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

        public Object getM3u8Hd_url() {
            return m3u8Hd_url;
        }

        public void setM3u8Hd_url(Object m3u8Hd_url) {
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

        public static class VideoTopicBean {
            private String alias;
            private String tname;
            private String ename;
            private String tid;

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

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }
        }
    }
}

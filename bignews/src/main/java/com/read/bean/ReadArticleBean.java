package com.read.bean;

import java.util.List;

/**
 * Created by Dell on 2016-11-16.
 */
public class ReadArticleBean {

    private ArticleBean C6F5LEE9001687H3;

    public void setArticleBean(ArticleBean c6F5LEE9001687H3) {
        C6F5LEE9001687H3 = c6F5LEE9001687H3;
    }

    public ArticleBean getArticleBean() {

        return C6F5LEE9001687H3;
    }

    public static class ArticleBean {
        private String body;
        private int replyCount;
        private String shareLink;
        private String digest;
        private String dkeys;
        private String ec;
        private String docid;
        private boolean picnews;
        private String title;
        private String tid;
        private String template;
        private int threadVote;
        private int threadAgainst;
        private String replyBoard;
        private String source;
        private boolean hasNext;
        private String voicecomment;
        private String ptime;
        private List<?> users;
        private List<?> ydbaike;
        private List<?> link;
        private List<ImgBean> img;
        private List<?> votes;
        private List<TopiclistNewsBean> topiclist_news;
        private List<?> topiclist;
        private List<?> boboList;
        private List<RelativeSysBean> relative_sys;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public String getShareLink() {
            return shareLink;
        }

        public void setShareLink(String shareLink) {
            this.shareLink = shareLink;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getDkeys() {
            return dkeys;
        }

        public void setDkeys(String dkeys) {
            this.dkeys = dkeys;
        }

        public String getEc() {
            return ec;
        }

        public void setEc(String ec) {
            this.ec = ec;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public boolean isPicnews() {
            return picnews;
        }

        public void setPicnews(boolean picnews) {
            this.picnews = picnews;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public int getThreadVote() {
            return threadVote;
        }

        public void setThreadVote(int threadVote) {
            this.threadVote = threadVote;
        }

        public int getThreadAgainst() {
            return threadAgainst;
        }

        public void setThreadAgainst(int threadAgainst) {
            this.threadAgainst = threadAgainst;
        }

        public String getReplyBoard() {
            return replyBoard;
        }

        public void setReplyBoard(String replyBoard) {
            this.replyBoard = replyBoard;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public String getVoicecomment() {
            return voicecomment;
        }

        public void setVoicecomment(String voicecomment) {
            this.voicecomment = voicecomment;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<?> getUsers() {
            return users;
        }

        public void setUsers(List<?> users) {
            this.users = users;
        }

        public List<?> getYdbaike() {
            return ydbaike;
        }

        public void setYdbaike(List<?> ydbaike) {
            this.ydbaike = ydbaike;
        }

        public List<?> getLink() {
            return link;
        }

        public void setLink(List<?> link) {
            this.link = link;
        }

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public List<?> getVotes() {
            return votes;
        }

        public void setVotes(List<?> votes) {
            this.votes = votes;
        }

        public List<TopiclistNewsBean> getTopiclist_news() {
            return topiclist_news;
        }

        public void setTopiclist_news(List<TopiclistNewsBean> topiclist_news) {
            this.topiclist_news = topiclist_news;
        }

        public List<?> getTopiclist() {
            return topiclist;
        }

        public void setTopiclist(List<?> topiclist) {
            this.topiclist = topiclist;
        }

        public List<?> getBoboList() {
            return boboList;
        }

        public void setBoboList(List<?> boboList) {
            this.boboList = boboList;
        }

        public List<RelativeSysBean> getRelative_sys() {
            return relative_sys;
        }

        public void setRelative_sys(List<RelativeSysBean> relative_sys) {
            this.relative_sys = relative_sys;
        }

        public static class ImgBean {
            private String ref;
            private String pixel;
            private String alt;
            private String src;

            public String getRef() {
                return ref;
            }

            public void setRef(String ref) {
                this.ref = ref;
            }

            public String getPixel() {
                return pixel;
            }

            public void setPixel(String pixel) {
                this.pixel = pixel;
            }

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }
        }

        public static class TopiclistNewsBean {
            private boolean hasCover;
            private String subnum;
            private String alias;
            private String tname;
            private String ename;
            private String tid;
            private String cid;

            public boolean isHasCover() {
                return hasCover;
            }

            public void setHasCover(boolean hasCover) {
                this.hasCover = hasCover;
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

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }
        }

        public static class RelativeSysBean {
            private String id;
            private String title;
            private String source;
            private String imgsrc;
            private String docID;
            private String from;
            private String type;
            private String ptime;
            private String href;

            @Override
            public String toString() {
                return "RelativeSysBean{" +
                        "id='" + id + '\'' +
                        ", title='" + title + '\'' +
                        ", source='" + source + '\'' +
                        ", imgsrc='" + imgsrc + '\'' +
                        ", docID='" + docID + '\'' +
                        ", from='" + from + '\'' +
                        ", type='" + type + '\'' +
                        ", ptime='" + ptime + '\'' +
                        ", href='" + href + '\'' +
                        '}';
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public String getDocID() {
                return docID;
            }

            public void setDocID(String docID) {
                this.docID = docID;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPtime() {
                return ptime;
            }

            public void setPtime(String ptime) {
                this.ptime = ptime;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }
    }
}
/*public class ReadArticleBean {
    private ArticleBean C5TMF89U05278ETH;

    public ArticleBean getArticlebean() {
        return C5TMF89U05278ETH;
    }

    public void setArticleBean(ArticleBean C5TMF89U05278ETH) {
        this.C5TMF89U05278ETH = C5TMF89U05278ETH;
    }

    public static class ArticleBean {
        private String body;
        private int replyCount;
        private String shareLink;
        private String digest;
        private String dkeys;
        private String docid;
        private SourceinfoBean sourceinfo;
        private boolean picnews;
        private String title;
        private String tid;
        private String template;
        private int threadVote;
        private int threadAgainst;
        private String articleTags;
        private String statement;
        private String replyBoard;
        private String source;
        private boolean hasNext;
        private String voicecomment;
        private String ptime;
        private List<?> users;
        private List<?> ydbaike;
        private List<?> link;
        private List<ImgBean> img;
        private List<?> votes;
        private List<?> topiclist_news;
        private List<TopiclistBean> topiclist;
        private List<AskbarBean> askbar;
        private List<?> boboList;
        private List<HuatiBean> huati;
        private List<RelativeSysBean> relative_sys;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public String getShareLink() {
            return shareLink;
        }

        public void setShareLink(String shareLink) {
            this.shareLink = shareLink;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getDkeys() {
            return dkeys;
        }

        public void setDkeys(String dkeys) {
            this.dkeys = dkeys;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public SourceinfoBean getSourceinfo() {
            return sourceinfo;
        }

        public void setSourceinfo(SourceinfoBean sourceinfo) {
            this.sourceinfo = sourceinfo;
        }

        public boolean isPicnews() {
            return picnews;
        }

        public void setPicnews(boolean picnews) {
            this.picnews = picnews;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public int getThreadVote() {
            return threadVote;
        }

        public void setThreadVote(int threadVote) {
            this.threadVote = threadVote;
        }

        public int getThreadAgainst() {
            return threadAgainst;
        }

        public void setThreadAgainst(int threadAgainst) {
            this.threadAgainst = threadAgainst;
        }

        public String getArticleTags() {
            return articleTags;
        }

        public void setArticleTags(String articleTags) {
            this.articleTags = articleTags;
        }

        public String getStatement() {
            return statement;
        }

        public void setStatement(String statement) {
            this.statement = statement;
        }

        public String getReplyBoard() {
            return replyBoard;
        }

        public void setReplyBoard(String replyBoard) {
            this.replyBoard = replyBoard;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public String getVoicecomment() {
            return voicecomment;
        }

        public void setVoicecomment(String voicecomment) {
            this.voicecomment = voicecomment;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<?> getUsers() {
            return users;
        }

        public void setUsers(List<?> users) {
            this.users = users;
        }

        public List<?> getYdbaike() {
            return ydbaike;
        }

        public void setYdbaike(List<?> ydbaike) {
            this.ydbaike = ydbaike;
        }

        public List<?> getLink() {
            return link;
        }

        public void setLink(List<?> link) {
            this.link = link;
        }

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public List<?> getVotes() {
            return votes;
        }

        public void setVotes(List<?> votes) {
            this.votes = votes;
        }

        public List<?> getTopiclist_news() {
            return topiclist_news;
        }

        public void setTopiclist_news(List<?> topiclist_news) {
            this.topiclist_news = topiclist_news;
        }

        public List<TopiclistBean> getTopiclist() {
            return topiclist;
        }

        public void setTopiclist(List<TopiclistBean> topiclist) {
            this.topiclist = topiclist;
        }

        public List<AskbarBean> getAskbar() {
            return askbar;
        }

        public void setAskbar(List<AskbarBean> askbar) {
            this.askbar = askbar;
        }

        public List<?> getBoboList() {
            return boboList;
        }

        public void setBoboList(List<?> boboList) {
            this.boboList = boboList;
        }

        public List<HuatiBean> getHuati() {
            return huati;
        }

        public void setHuati(List<HuatiBean> huati) {
            this.huati = huati;
        }

        public List<RelativeSysBean> getRelative_sys() {
            return relative_sys;
        }

        public void setRelative_sys(List<RelativeSysBean> relative_sys) {
            this.relative_sys = relative_sys;
        }

        public static class SourceinfoBean {
            private String alias;
            private String ename;
            private String tname;
            private String tid;

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getEname() {
                return ename;
            }

            public void setEname(String ename) {
                this.ename = ename;
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

        public static class ImgBean {
            private String ref;
            private String pixel;
            private String alt;
            private String src;

            public String getRef() {
                return ref;
            }

            public void setRef(String ref) {
                this.ref = ref;
            }

            public String getPixel() {
                return pixel;
            }

            public void setPixel(String pixel) {
                this.pixel = pixel;
            }

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }
        }

        public static class TopiclistBean {
            private boolean hasCover;
            private String subnum;
            private String alias;
            private String tname;
            private String ename;
            private String tid;
            private String cid;

            public boolean isHasCover() {
                return hasCover;
            }

            public void setHasCover(boolean hasCover) {
                this.hasCover = hasCover;
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

            public String getTid() {
                return tid;
            }

            public void setTid(String tid) {
                this.tid = tid;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }
        }

        public static class AskbarBean {
            private String title;
            private String headpicurl;
            private String alias;
            private String expertId;
            private String name;
            private int concernCount;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getHeadpicurl() {
                return headpicurl;
            }

            public void setHeadpicurl(String headpicurl) {
                this.headpicurl = headpicurl;
            }

            public String getAlias() {
                return alias;
            }

            public void setAlias(String alias) {
                this.alias = alias;
            }

            public String getExpertId() {
                return expertId;
            }

            public void setExpertId(String expertId) {
                this.expertId = expertId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getConcernCount() {
                return concernCount;
            }

            public void setConcernCount(int concernCount) {
                this.concernCount = concernCount;
            }
        }

        public static class HuatiBean {
            private String topicId;
            private String topicName;

            public String getTopicId() {
                return topicId;
            }

            public void setTopicId(String topicId) {
                this.topicId = topicId;
            }

            public String getTopicName() {
                return topicName;
            }

            public void setTopicName(String topicName) {
                this.topicName = topicName;
            }
        }

        public static class RelativeSysBean {
            private String id;
            private String title;
            private String source;
            private String imgsrc;
            private String docID;
            private String from;
            private String type;
            private String ptime;
            private String href;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public void setDocID(String docID) {
                this.docID = docID;
            }

            public String getDocID() {

                return docID;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPtime() {
                return ptime;
            }

            public void setPtime(String ptime) {
                this.ptime = ptime;
            }

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }
    }
}*/


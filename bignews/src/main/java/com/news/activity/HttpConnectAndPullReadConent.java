package com.news.activity;

import android.util.Log;

import com.news.bean.ReadArticleBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 2016-11-17.
 */
public class HttpConnectAndPullReadConent {

    private String docId;
    private String url;

    public HttpConnectAndPullReadConent(String docId, String url) {
        this.docId = docId;
        this.url = url;
    }

    public ReadArticleBean httpFunction() {

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {

                InputStream is = connection.getInputStream();
                StringBuffer sb = new StringBuffer();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = buffer.readLine()) != null) {
                    sb.append(line);
                }
                String json = sb.toString();

                Log.i("MSL", "httpFunction: " + json);

                JSONObject jsonObject = new JSONObject(json);
                ReadArticleBean readArticleBean = new ReadArticleBean();

                JSONObject articleObject = jsonObject.getJSONObject(docId);//此处docId为动态类名
                ReadArticleBean.ArticleBean articleBean = new ReadArticleBean.ArticleBean();

                String title = (String) articleObject.get("title");
                articleBean.setTitle(title);

                String body = (String) articleObject.get("body");
                articleBean.setBody(body);

                int replyCount = articleObject.getInt("replyCount");
                articleBean.setReplyCount(replyCount);

                String shareLink = articleObject.getString("shareLink");
                articleBean.setShareLink(shareLink);

                String source = articleObject.getString("source");
                articleBean.setSource(source);

                String ptime = articleObject.getString("ptime");
                articleBean.setPtime(ptime);

//                        开始解析imgs数组
                try {
                    JSONArray ImgJsonArray = articleObject.getJSONArray("img");
                    List<ReadArticleBean.ArticleBean.ImgBean> imgs = new ArrayList<>();

                    for (int i = 0; i < ImgJsonArray.length(); i++) {
                        JSONObject imgObject = (JSONObject) ImgJsonArray.get(i);
                        ReadArticleBean.ArticleBean.ImgBean imgBean = new ReadArticleBean.ArticleBean.ImgBean();

                        String ref = imgObject.getString("ref");
                        imgBean.setRef(ref);

                        String alt = imgObject.getString("alt");
                        imgBean.setAlt(alt);

                        String src = imgObject.getString("src");
                        imgBean.setSrc(src);

                        imgs.add(imgBean);
                    }
                    articleBean.setImg(imgs);//img数组解析完毕，添加到articleBean对象中
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //开始解析relative_sys数组
                try {
                    JSONArray relativeArray = articleObject.getJSONArray("relative_sys");
                    List<ReadArticleBean.ArticleBean.RelativeSysBean> relativeSysList = new ArrayList<>();

                    for (int i = 0; i < relativeArray.length(); i++) {
                        JSONObject relativeObj = (JSONObject) relativeArray.get(i);
                        ReadArticleBean.ArticleBean.RelativeSysBean relativeSysBean = new ReadArticleBean.ArticleBean.RelativeSysBean();

                        String title1 = relativeObj.getString("title");
                        relativeSysBean.setTitle(title1);

                        String source1 = relativeObj.getString("source");
                        relativeSysBean.setSource(source1);

                        String ptime1 = relativeObj.getString("ptime");
                        relativeSysBean.setPtime(ptime1);

                        String imgSrc = relativeObj.getString("imgsrc");
                        relativeSysBean.setImgsrc(imgSrc);

                        relativeSysList.add(relativeSysBean);
                    }
                    articleBean.setRelative_sys(relativeSysList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //开始解析huati数组
                //部分文章没有huati数组
                try {
                    JSONArray huatiArray = articleObject.getJSONArray("huati");
                    List<ReadArticleBean.ArticleBean.HuatiBean> huatiList = new ArrayList<>();

                    for (int i = 0; i < huatiArray.length(); i++) {
                        JSONObject huatiObj = (JSONObject) huatiArray.get(i);
                        ReadArticleBean.ArticleBean.HuatiBean huatiBean = new ReadArticleBean.ArticleBean.HuatiBean();

                        String topicId = huatiObj.getString("topicId");
                        huatiBean.setTopicId(topicId);

                        String topicName = huatiObj.getString("topicName");
                        huatiBean.setTopicName(topicName);

                        huatiList.add(huatiBean);
                    }
                    articleBean.setHuati(huatiList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                readArticleBean.setArticleBean(articleBean);
                return readArticleBean;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    return null;
}

}

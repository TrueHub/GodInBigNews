package com.read.utils;

import android.util.Log;

import com.read.bean.AuthorInfoBean;

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
public class HttpConnectAuthorInfo {

    private String eName;
    private String url;

    public HttpConnectAuthorInfo(String eName, String url) {
        this.eName = eName;
        this.url = url;
    }

    public List<AuthorInfoBean.AuthorInfo> httpFunction() {//暂时放在本类中，功能完善后封装为一个独立的类，提高代码可读性

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
                Log.i("MSL", "httpFunction: " + eName);

                JSONObject jsonObject = new JSONObject(json);

                JSONArray authorinfoArray = jsonObject.getJSONArray(eName);
                List<AuthorInfoBean.AuthorInfo> authorInfoList = new ArrayList<>();

                for (int i = 0; i < authorinfoArray.length(); i++) {
                    JSONObject articleObject = authorinfoArray.getJSONObject(i);
                    AuthorInfoBean.AuthorInfo authorInfo = new AuthorInfoBean.AuthorInfo();

                    try {

                    String title = (String) articleObject.get("title");
                    authorInfo.setTitle(title);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String postid = (String) articleObject.get("postid");
                    authorInfo.setPostid(postid);

                    String imgsrc = (String) articleObject.get("imgsrc");
                    authorInfo.setImgsrc(imgsrc);

                    String ptime = (String) articleObject.get("ptime");
                    authorInfo.setPtime(ptime);

                    try {
                        String url = (String) articleObject.get("url");
                        authorInfo.setUrl(url);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int replyCount = (int) articleObject.get("replyCount");
                    authorInfo.setReplyCount(replyCount);

                    authorInfoList.add(authorInfo);
                }

                Log.i("MSL", "httpFunction: "+ authorInfoList.size());
                Log.i("MSL", "httpFunction: " + authorInfoList.get(0).toString());
                return authorInfoList;

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
}

}

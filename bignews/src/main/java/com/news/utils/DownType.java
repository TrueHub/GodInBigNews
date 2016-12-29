package com.news.utils;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by 371964363 on 2016/11/14.
 */
public class DownType extends AsyncTask<String,Void,Void> {

    private ArrayList<String> list = new ArrayList<>();
    private CallBack back;
    public interface CallBack{
        void back(int type);
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(params[0]).openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            final StringBuffer buffer = new StringBuffer();
            if (conn.getResponseCode() == 200) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str = null;
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
            }
            markStr(buffer.toString());
            back.back(list.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void markStr(String str){
        if(str.indexOf("http://") != -1){
            String str1 = str.substring(str.indexOf("http://"));
            String response = str1.substring(0, str1.indexOf('"'));
            if(response.endsWith(".jpeg")){
                if(!list.contains(response)){
                    list.add(response);
                }
            }
            String fin = str1.substring('"');
            markStr(fin);
        }

    }
}

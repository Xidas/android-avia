package com.melorean.avia.utils;

import android.util.Log;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HttpClient<T> {

    public List<T> connectPost(String url, Map<String, String> params, Class<T> typeOfClass) {
        try {
            HttpURLConnection conn = genHttpConnect(url + url, "application/json", "POST");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = reader.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();
            return Collections.singletonList(new ObjectMapper().readValue(sb.toString(), typeOfClass));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<T> connectGet(String url, Map<String, String> params, Class<T> typeOfClass) {
        try {
            HttpURLConnection conn = genHttpConnect(url, "application/json", "GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();

            if(conn.getResponseCode()==200) {
                String output;
                while ((output = reader.readLine()) != null) {
                    sb.append(output);
                }
            }
            Log.d("DEBUGA", sb.toString());
            ObjectMapper om = new ObjectMapper();
            conn.disconnect();
            JavaType type = om.getTypeFactory().constructCollectionType(List.class, typeOfClass);
            return om.readValue(sb.toString(), type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private HttpURLConnection genHttpConnect(String url, String contentType, String method) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
//        String auth = String.format("%s:%s", login, password);
//        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("UTF-8")));
//        conn.setRequestProperty("Authorization", "Basic " + new String(encodedAuth));
        conn.setRequestProperty("Content-Type", contentType + ";charset=UTF-8");
        conn.setRequestMethod(method);
        return conn;
    }
}

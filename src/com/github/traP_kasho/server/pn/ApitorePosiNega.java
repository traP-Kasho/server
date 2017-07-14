package com.github.traP_kasho.server.pn;

import twitter4j.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.URL;
import java.net.HttpURLConnection;

@Deprecated
public final class ApitorePosiNega{
    private static final String ACCESS_TOKEN = "8f0238f6-5ea2-46a1-9ac9-16f2d67d5f5a";
    private static final String END_POINT = "https://api.apitore.com/api/11/sentiment/predict";
    private static final String ENCODING = "UTF-8";

    public static Score getScore(Score score) {
        String text = score.getStatus().getText().toLowerCase();
        try {
            text = URLEncoder.encode(text, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        text = text.replace("*", "%2a")
                .replace("-", "%2d")
                .replace("_", "")
                .replace("+", "%20");
        URL url = null;
        try {
            url = new URL(END_POINT + "?access_token=" + ACCESS_TOKEN + "&text=" + text);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JSONTokener tokener = new JSONTokener(connection.getInputStream());
                JSONObject object = new JSONObject(tokener);
                if(!object.get("log").equals("Success.")) throw new MalformedURLException();
                JSONArray sentiments = object.getJSONArray("sentiments");
                for(int i = 0; i < 3; i++) {
                    JSONObject o = sentiments.getJSONObject(i);
                    switch((String) o.get("sentiment")) {
                        case "positive":
                            score.setPosScore((double) o.get("score"));
                            break;
                        case "negative":
                            score.setNegScore((double) o.get("score"));
                            break;
                        case "neutral":
                            score.setNeuScore((double) o.get("score"));
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) connection.disconnect();
        }
        return score;
    }
}
package com.github.traP_kasho.server;

import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.JSONTokener;

import java.io.InputStream;
import java.util.List;

/**
 * Created by poispois on 2017/07/13.
 */
public class ProfessorJSONConverter {
    public static Professor toProfessor(InputStream stream) {
        Professor professor = new Professor();
        JSONObject obj = null;
        try {
            obj = new JSONObject(new JSONTokener(stream));
            professor.setDisplayName((String) obj.get("displayName"));
            JSONArray names = obj.getJSONArray("names");
            professor.setNames((List<String>) names);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return professor;
    }

    public static JSONObject toJSON(Professor prof) {
        JSONObject res = new JSONObject();
        JSONArray names = new JSONArray(prof.getNames());
        JSONObject score = new JSONObject();

        try {
            score.put("all", prof.getNegScoreSum() + prof.getNeuScoreSum() + prof.getPosScoreSum());
            score.put("positive", prof.getPosScoreSum());
            score.put("neutral", prof.getNeuScoreSum());
            score.put("negative", prof.getNegScoreSum());

            res.put("names", names);
            res.put("displayName", prof.getDisplayName());
            res.put("scores", score);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res;
    }
}

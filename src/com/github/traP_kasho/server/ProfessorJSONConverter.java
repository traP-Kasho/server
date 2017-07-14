package com.github.traP_kasho.server;

import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;
import twitter4j.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by poispois on 2017/07/13.
 */
public class ProfessorJSONConverter {
    public static List<Professor> toProfessor(InputStream stream) {
        List<Professor> professors = new ArrayList<>();

        try {
            JSONArray array = new JSONArray(new JSONTokener(stream));
            for(int i = 0; i< array.length(); i++) {
                Professor p = new Professor();
                JSONObject obj = array.getJSONObject(i);
                p.setDisplayName((String) obj.get("displayName"));
                JSONArray names = obj.getJSONArray("names");
                p.setNames((List<String>) names);
                professors.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return professors;
    }

    public static JSONObject toJSON(Professor prof) {
        JSONObject res = new JSONObject();
        JSONArray names = new JSONArray(prof.getAllNames());
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

package com.github.traP_kasho.server;

import com.github.traP_kasho.server.pn.Score;
import twitter4j.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by poispois on 2017/07/13.
 */
public class ProfessorJSONConverter {
    public static List<Professor> toProfessor(InputStream stream) {
        List<Professor> professors = new LinkedList<>();

        try {
            JSONArray array = new JSONArray(new JSONTokener(stream));
            for(int i = 0; i < array.length(); i++) {
                Professor p = new Professor();
                JSONObject obj = array.getJSONObject(i);
                p.setDisplayName((String) obj.get("displayName"));
                JSONArray names = obj.getJSONArray("names");
                for(int j = 0; j < names.length(); j++) {
                    p.addNames(names.getString(j));
                }
                professors.add(p);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return professors;
    }

    public static List<Professor> getTweet(InputStream stream, Map<String, Professor> map) {
        List<Professor> profs = new LinkedList<>();
        try {
            JSONArray array = new JSONArray(new JSONTokener(stream));
            for(int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Professor p = map.get(obj.getString("displayName"));
                JSONArray statuses = obj.getJSONArray("statusTexts");
                if(statuses.length() != 0)
                    for(int j = 0; j < statuses.length(); j++) {
                        Score score = new Score();
                        score.setText(statuses.getString(j));
                        p.addScore(score);
                    }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return profs;
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

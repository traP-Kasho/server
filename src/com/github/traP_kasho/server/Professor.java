package com.github.traP_kasho.server;

import com.github.traP_kasho.server.pn.Score;

import java.util.List;

/**
 * Created by poispois on 2017/07/13.
 */
public class Professor {
    private String displayName;
    private List<String> names;
    private List<Score> scores;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void addNames(String name) {
        this.names.add(name);
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public void addScore(Score score) {
        this.scores.add(score);
    }

    public double getNegScoreSum() {
        double res = 0;
        for(Score s: scores) {
            res =+ s.getNegScore();
        }
        return res;
    }

    public double getPosScoreSum() {
        double res = 0;
        for(Score s: scores) {
            res =+ s.getPosScore();
        }
        return res;
    }

    public double getNeuScoreSum() {
        double res = 0;
        for(Score s: scores) {
            res =+ s.getNeuScore();
        }
        return res;
    }
}

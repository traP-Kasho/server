package com.github.traP_kasho.server;

import com.github.traP_kasho.server.pn.Score;
import com.github.traP_kasho.server.pn.YahooPosiNega;
import com.github.traP_kasho.server.twitter.Search;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Professor implements ScoreTarget{
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

    public List<String> getAllNames() {
        List<String> n = new ArrayList<>();
        n.addAll(names);
        n.add(displayName);
        return n;
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

    @Override
    public double getNegScoreSum() {
        double res = 0;
        for(Score s: scores) {
            res += s.getNegScore();
        }
        return res;
    }

    @Override
    public double getPosScoreSum() {
        double res = 0;
        for(Score s: scores) {
            res += s.getPosScore();
        }
        return res;
    }

    @Override
    public double getNeuScoreSum() {
        double res = 0;
        for(Score s: scores) {
            res += s.getNeuScore();
        }
        return res;
    }

    public Professor() {
        names = new LinkedList<>();
        scores = new LinkedList<>();
    }


    public void searchStatuses() {
        for(String name : this.getAllNames()) {
            //test!
            System.out.println(name);
            try {
                List<Status> statuses = Search.search(name);
                Score score;
                for(Status status: statuses) {
                    score = new Score();
                    score.setStatus(status);
                    this.scores.add(score);
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }
    }

    public void judgeScores() {
        for(Score score: scores) {
            YahooPosiNega.judge(score);
        }
    }
}

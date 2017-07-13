package com.github.traP_kasho.server.np;

/**
 * Created by poispois on 2017/07/10.
 */
public class Score {
    private String displayName;
    private String[] names;
    private double pScore;
    private double nScore;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public double getpScore() {
        return pScore;
    }

    public void setpScore(double pScore) {
        this.pScore = pScore;
    }

    public double getnScore() {
        return nScore;
    }

    public void setnScore(double nScore) {
        this.nScore = nScore;
    }

    public double getAllScore() {
        return pScore - nScore;
    }
}

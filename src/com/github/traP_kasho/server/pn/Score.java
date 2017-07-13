package com.github.traP_kasho.server.pn;

import twitter4j.Status;

/**
 * Created by poispois on 2017/07/10.
 */
public class Score {
    private Status status;
    private double posScore;
    private double negScore;
    private double neuScore;

    public double getNeuScore() {
        return neuScore;
    }

    public void setNeuScore(double neuScore) {
        this.neuScore = neuScore;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getPosScore() {
        return posScore;
    }

    public void setPosScore(double posScore) {
        this.posScore = posScore;
    }

    public double getNegScore() {
        return negScore;
    }

    public void setNegScore(double negScore) {
        this.negScore = negScore;
    }

    public double getAllScore() {
        return posScore - negScore;
    }
}

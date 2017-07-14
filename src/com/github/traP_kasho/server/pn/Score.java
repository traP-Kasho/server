package com.github.traP_kasho.server.pn;

import twitter4j.Status;


public class Score {
    private Status status;
    private String text;
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

    public Score(){

    }

    public void setText(String string) {
        this.text = string;
    }

    public String getText() {
        return text;
    }
}

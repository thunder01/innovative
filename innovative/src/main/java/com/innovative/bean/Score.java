package com.innovative.bean;

public class Score {
    private  int id;
    private  String workpoints;//工分
    private  String score;//积分
    private  String contents;//评价内容
    private  int intellId;//情报ID


    public Score() {
    }

    public Score(int id, String workpoints, String score, String contents, int intellId) {
        this.id = id;
        this.workpoints = workpoints;
        this.score = score;
        this.contents = contents;
        this.intellId = intellId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkpoints() {
        return workpoints;
    }

    public void setWorkpoints(String workpoints) {
        this.workpoints = workpoints;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getIntellId() {
        return intellId;
    }

    public void setIntellId(int intellId) {
        this.intellId = intellId;
    }
}

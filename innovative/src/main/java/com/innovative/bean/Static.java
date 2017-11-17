package com.innovative.bean;

public class Static {
    private  int id;
    private  String statics;
    private  String date;

    public Static() {
    }

    public Static(int id, String statics, String date) {
        this.id = id;
        this.statics = statics;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatics() {
        return statics;
    }

    public void setStatics(String statics) {
        this.statics = statics;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

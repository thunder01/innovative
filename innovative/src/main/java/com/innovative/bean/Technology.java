package com.innovative.bean;

public class Technology {
    private  int  id;
    private  String title;
    private  String source;
    private  String date;
    private  String abstracts;
    private  String []field;
    private  String []label;
    private  String type;
    private  String content;
    private  String createDate;
    private  String createBy;
    private  int number;

    public Technology(int id, int number,String title, String source, String date, String abstracts, String[] field, String[] label, String type, String content, String createDate, String createBy) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.date = date;
        this.abstracts = abstracts;
        this.field = field;
        this.label = label;
        this.type = type;
        this.content = content;
        this.createDate = createDate;
        this.createBy = createBy;
        this.number=number;
    }

    public Technology() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String[] getField() {
        return field;
    }

    public void setField(String[] field) {
        this.field = field;
    }

    public String[] getLabel() {
        return label;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

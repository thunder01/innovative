package com.innovative.bean;

public class FileLog {
    private int id;
    private String createDate;
    private String fileName;
    private String userid;
    private int numbers;
    private String type;

    public FileLog() {
    }

    public FileLog(int id, String createDate, String fileName, String userid, int numbers, String type) {
        this.id = id;
        this.createDate = createDate;
        this.fileName = fileName;
        this.userid = userid;
        this.numbers = numbers;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

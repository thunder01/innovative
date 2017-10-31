package com.innovative.bean;

import java.util.List;

public class FeedBack {
    private  int id; //uuid
    private  String title;//报告标题
    private  String content;//摘要
    private  int intellId;//主表ID
    private  String path;//文件地址
    private  String  status;//状态
    private List<FileBean> filelist;


    public FeedBack() {
    }


    public FeedBack(String title, String content, int intellId, String path, String status,List<FileBean> filelist,int id) {
        this.title = title;
        this.content = content;
        this.intellId = intellId;
        this.path = path;
        this.status = status;
        this.filelist=filelist;
        this.id=id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIntellId() {
        return intellId;
    }

    public void setIntellId(int intellId) {
        this.intellId = intellId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<FileBean> getFilelist() {
        return filelist;
    }

    public void setFilelist(List<FileBean> filelist) {
        this.filelist = filelist;
    }
}

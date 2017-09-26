package com.innovative.bean;

import java.util.List;

public class Case {
    private int id;
    private String title;
    private String titleImage;
    private String file;
    private List<FileBean> filelist;

    public Case() {
    }

    public Case(int id, String title, String titleImage, String file) {
        this.id = id;
        this.title = title;
        this.titleImage = titleImage;
        this.file = file;
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

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<FileBean> getFilelist() {
        return filelist;
    }

    public void setFilelist(List<FileBean> filelist) {
        this.filelist = filelist;
    }
}







